package cz.jull.gamestates.playing;

import cz.jull.Game;
import cz.jull.gamestates.State;
import cz.jull.gamestates.StateMethods;
import cz.jull.gamestates.Button;
import cz.jull.logic.pet.Pet;
import cz.jull.logic.pet.PetSpecies;
import cz.jull.logic.pet.PetType;
import cz.jull.ui.*;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;
import cz.jull.utils.SaveManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

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
    private CurrencyUI currencyUI;

    // Pets
    private PetRenderer petRenderer;

    // Pet Actions
    private int actionSpriteIndex = Constants.ACTION_IDLE;
    private int actionTimer = 0;

    // Pet Stats
    private PetStatsUI petStatsUI;

    // Backgrounds
    private BackgroundUI backgroundUI;

    private int tickCount = 0;
    private final int DECAY_RATE_TICKS = 1000;

    public PlayState(Game game) {
        super(game);

        backgroundUI = new BackgroundUI();
        petStatsUI = new PetStatsUI();
        currencyUI = new CurrencyUI();
        petRenderer = new PetRenderer();
        loadButtons();
    }

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

    @Override
    public void draw(Graphics g) {
        Pet currentPet = game.getPlayer().getEquippedPet();

        if (currentPet == null) {
            drawSelectionScreen(g);
        } else {
            drawPlayScreen(g, currentPet);
        }
    }

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

    private void drawSelectionScreen(Graphics g) {
        // Background
        g.setColor(new Color(89, 198, 255));
        g.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        // Title
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString("Choose your pet", (Constants.GAME_WIDTH / 2) - 220, 100);

        // Cat
        int catX = (Constants.GAME_WIDTH / 2) - 350;
        g.fillRect(catX, 200, 400, 600);
        g.drawImage(petRenderer.getIdleSprite(PetSpecies.CAT), catX, 215, Constants.CAT_WIDTH, Constants.CAT_HEIGHT, null);

        // Dog
        int dogX = (Constants.GAME_WIDTH / 2) + 50;
        g.fillRect(dogX, 200, 400, 600);
        g.drawImage(petRenderer.getIdleSprite(PetSpecies.DOG), dogX, 215, Constants.DOG_WIDTH, Constants.DOG_HEIGHT, null);
    }

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
                    game.getPlayer().setEquippedPet(new Pet("Cat", PetType.HOME, PetSpecies.CAT));
                    SaveManager.saveGame(game.getPlayer());
                }

                int dogX = (Constants.GAME_WIDTH / 2) + 50;
                if (mouseX >= dogX && mouseX <= dogX + 265 && mouseY >= 200 && mouseY <= 730) {
                    game.getPlayer().setEquippedPet(new Pet("Golden Retriever", PetType.HOME, PetSpecies.DOG));
                    SaveManager.saveGame(game.getPlayer());
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
                // Open Gacha
            }

            if (listButton.isMouseOver() && listButton.isMousePressed()) {
                // Open Pet List
            }

            for (Button button : buttons) {
                if (button != null) {
                    button.setMousePressed(false);
                }
            }
        }
    }

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

    @Override
    public void KeyPressed(KeyEvent e) {

    }

    @Override
    public void KeyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
