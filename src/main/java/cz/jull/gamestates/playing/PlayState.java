package cz.jull.gamestates.playing;

import cz.jull.Game;
import cz.jull.gamestates.State;
import cz.jull.gamestates.StateMethods;
import cz.jull.gamestates.Button;
import cz.jull.logic.pet.Pet;
import cz.jull.logic.pet.PetSpecies;
import cz.jull.ui.*;
import cz.jull.utils.Constants;
import cz.jull.utils.SaveManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Manages the main gameplay loop, including pet interactions, stats decay, and UI interactions.
 */
public class PlayState extends State implements StateMethods {
    // Buttons
    private Button backButton;
    private Button gachaButton;
    private Button listButton;
    private Button feedButton;
    private Button sleepButton;
    private Button loveButton;

    private Button[] buttons;

    // Currency
    private final CurrencyUI currencyUI;

    // Pets
    private final PetRenderer petRenderer;

    // Pet Actions
    private int actionSpriteIndex = Constants.ACTION_IDLE;
    private int actionTimer = 0;

    // Pet Stats
    private final PetStatsUI petStatsUI;

    // Backgrounds
    private final BackgroundUI backgroundUI;

    private final List<Pet> allAvailablePets;

    /**
     * Constructs the main play state.
     *
     * @param game The main game instance.
     */
    public PlayState(Game game) {
        super(game);

        backgroundUI = new BackgroundUI();
        petStatsUI = new PetStatsUI();
        currencyUI = new CurrencyUI();
        petRenderer = new PetRenderer();
        loadButtons();

        allAvailablePets = game.getPetCatalog();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Specifically updates the currently equipped pet's status, handles stat decay over time,
     * manages action timers for animations, and updates UI button states.
     * </p>
     */
    @Override
    public void update() {
        Pet currentPet = game.getPlayer().getEquippedPet();

        if (currentPet == null) {
            return;
        }

        for (Button button : buttons) {
            if (button != null) {
                button.update();
            }
        }

        if (currentPet.decayStats()) {
            game.getPlayer().addCoins(100);
        }

        if (currentPet.isSleeping()) {
            actionSpriteIndex = Constants.ACTION_SLEEP;
        } else if (actionTimer > 0) {
            actionTimer--;
        } else {
            actionSpriteIndex = Constants.ACTION_IDLE;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Renders either the initial pet selection screen (if no pet is equipped) or the main
     * interactive play screen with the background, UI elements, and the pet itself.
     * </p>
     */
    @Override
    public void draw(Graphics g) {
        Pet currentPet = game.getPlayer().getEquippedPet();

        if (currentPet == null) {
            drawSelectionScreen(g);
        } else {
            drawPlayScreen(g, currentPet);
        }
    }

    /**
     * Initializes all the interactive buttons for the play state.
     */
    private void loadButtons() {
        backButton = UtilsUI.createBackButton(20, 20);
        gachaButton = UtilsUI.createGameButton(390, 20, 1);
        listButton = UtilsUI.createGameButton(620, 20, 0);

        int rightX = Constants.GAME_WIDTH - 230;

        feedButton = UtilsUI.createGameButton(rightX, Constants.GAME_HEIGHT - 660, 4);
        sleepButton = UtilsUI.createGameButton(rightX, Constants.GAME_HEIGHT - 495, 3);
        loveButton = UtilsUI.createGameButton(rightX, Constants.GAME_HEIGHT - 330, 2);

        buttons = new Button[] {backButton, gachaButton, listButton, feedButton, sleepButton, loveButton};
    }

    /**
     * Draws the initial pet selection screen if the player hasn't picked a starter pet.
     *
     * @param g The Graphics object used for drawing.
     */
    private void drawSelectionScreen(Graphics g) {
        // Background
        g.setColor(new Color(89, 198, 255));
        g.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        // Title
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString("Choose your pet", (Constants.GAME_WIDTH / 2) - 220, 100);

        int boxY = 200;
        int boxWidth = 400;
        int boxHeight = 600;
        int paddingBottom = 30;

        int catX = (Constants.GAME_WIDTH / 2) - 350;
        g.setColor(Color.WHITE);
        g.fillRect(catX, boxY, boxWidth, boxHeight);

        int catDrawX = catX + (boxWidth - Constants.CAT_WIDTH) / 2;
        int catDrawY = boxY + boxHeight - Constants.CAT_HEIGHT - paddingBottom;
        g.drawImage(petRenderer.getIdleSprite(PetSpecies.CAT), catDrawX, catDrawY, Constants.CAT_WIDTH, Constants.CAT_HEIGHT, null);

        int dogX = (Constants.GAME_WIDTH / 2) + 50;
        g.setColor(Color.WHITE);
        g.fillRect(dogX, boxY, boxWidth, boxHeight);

        int dogDrawX = dogX + (boxWidth - Constants.DOG_WIDTH) / 2;
        int dogDrawY = boxY + boxHeight - Constants.DOG_HEIGHT - paddingBottom;
        g.drawImage(petRenderer.getIdleSprite(PetSpecies.DOG), dogDrawX, dogDrawY, Constants.DOG_WIDTH, Constants.DOG_HEIGHT, null);
    }

    /**
     * Draws the main gameplay interface and pet.
     *
     * @param g The Graphics object used for drawing.
     * @param currentPet The currently equipped pet.
     */
    private void drawPlayScreen(Graphics g, Pet currentPet) {
        currentPet = game.getPlayer().getEquippedPet();

        // Background
        backgroundUI.drawPlay(g, currentPet.getType());

        // Buttons
        for (Button button : buttons) {
            if (button != null) {
                button.draw(g);
            }
        }

        // Currency
        currencyUI.draw(g, game.getPlayer().getCoins());

        // Pet Stats
        petStatsUI.draw(g, currentPet);

        // Draw Bed and Pet
        petRenderer.draw(g, currentPet, actionSpriteIndex);
    }

    /**
     * Retrieves a pet from the global catalog by its name.
     *
     * @param name The name of the pet to find.
     * @return The Pet object if found, otherwise null.
     */
    private Pet getPetFromCatalog(String name) {
        for (Pet pet : allAvailablePets) {
            if (pet.getName().equals(name)) {
                return pet;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Detects initial left-clicks to press down UI buttons within the play screen.
     * </p>
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (Button button : buttons) {
                if (button != null && button.isMouseOver()) {
                    button.setMousePressed(true);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Triggers actions when a mouse click is released, such as selecting a starter pet,
     * interacting with the current pet (feed, sleep, love), or navigating to other game states.
     * </p>
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (backButton.isMouseOver() && backButton.isMousePressed()) {
                game.returnToMenu();
            }

            Pet currentPet = game.getPlayer().getEquippedPet();

            int mouseX = e.getX();
            int mouseY = e.getY();

            // No Pet
            if (currentPet == null) {
                int catX = (Constants.GAME_WIDTH / 2) - 350;
                if (mouseX >= catX && mouseX <= catX + 315 && mouseY >= 200 && mouseY <= 600) {
                    Pet starterCat = getPetFromCatalog("Cat");
                    if (starterCat != null) {
                        game.getPlayer().setEquippedPet(starterCat);
                        SaveManager.saveGame(game.getPlayer());
                    }
                }

                int dogX = (Constants.GAME_WIDTH / 2) + 50;
                if (mouseX >= dogX && mouseX <= dogX + 265 && mouseY >= 200 && mouseY <= 730) {
                    Pet starterDog = getPetFromCatalog("Golden Retriever");
                    if (starterDog != null) {
                        game.getPlayer().setEquippedPet(starterDog);
                        SaveManager.saveGame(game.getPlayer());
                    }
                }
                return;
            }

            // Pet Already Picked
            if (sleepButton.isMouseOver() && sleepButton.isMousePressed()) {
                if (currentPet.isSleeping()) {
                    currentPet.wakeUp();
                    actionSpriteIndex = Constants.ACTION_IDLE;
                } else if (currentPet.getEnergy() < 100) {
                    currentPet.sleep();
                    actionSpriteIndex = Constants.ACTION_SLEEP;
                }
            }

            if (!currentPet.isSleeping()) {
                int rewardAmount = 5;

                if (feedButton.isMouseOver() && feedButton.isMousePressed()) {
                    if (currentPet.getHunger() <= 80) {
                        currentPet.feed();
                        game.getPlayer().addCoins(rewardAmount);
                        actionSpriteIndex = Constants.ACTION_FEED;
                        actionTimer = 50;
                    }
                }
                if (loveButton.isMouseOver() && loveButton.isMousePressed()) {
                    if (currentPet.getLove() <= 80) {
                        currentPet.love();
                        game.getPlayer().addCoins(rewardAmount);
                        actionSpriteIndex = Constants.ACTION_LOVE;
                        actionTimer = 75;
                    }
                }
            }

            if (gachaButton.isMouseOver() && gachaButton.isMousePressed()) {
                game.startGacha();
            }

            if (listButton.isMouseOver() && listButton.isMousePressed()) {
                game.startPetList();
            }

            for (Button button : buttons) {
                if (button != null) {
                    button.setMousePressed(false);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Tracks the mouse position to update the hover state of interactive UI buttons.
     * </p>
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        for (Button button : buttons) {
            if (button != null) {
                button.setMouseOver(false);
            }
        }

        for (Button button : buttons) {
            if (button != null && button.getBounds().contains(e.getX(), e.getY())) {
                button.setMouseOver(true);
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>Unused in the Play state.</p>
     */
    @Override
    public void KeyPressed(KeyEvent e) {

    }

    /**
     * {@inheritDoc}
     * <p>Unused in the Play state.</p>
     */
    @Override
    public void KeyReleased(KeyEvent e) {

    }

    /**
     * {@inheritDoc}
     * <p>Unused in the Play state.</p>
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
