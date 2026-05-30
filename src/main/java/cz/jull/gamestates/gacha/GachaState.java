package cz.jull.gamestates.gacha;

import cz.jull.Game;
import cz.jull.gamestates.Button;
import cz.jull.gamestates.State;
import cz.jull.gamestates.StateMethods;
import cz.jull.logic.pet.Pet;
import cz.jull.logic.pet.PetType;
import cz.jull.ui.BannerUI;
import cz.jull.ui.CurrencyUI;
import cz.jull.ui.UtilsUI;
import cz.jull.utils.Constants;
import cz.jull.utils.SaveManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Manages the gacha system logic, where players can spend coins to roll for random pets.
 */
public class GachaState extends State implements StateMethods {
    // Buttons
    private Button backButton;
    private Button homeGachaButton;
    private Button waterGachaButton;
    private Button buyButton;

    private Button[] buttons;

    // UI
    private CurrencyUI currencyUI;
    private BannerUI bannerUI;

    // Banners
    private PetType currentBanner = PetType.HOME;
    private final int GACHA_PRICE = 160;
    private Random random = new Random();

    private List<Pet> allAvailablePets;

    /**
     * Constructs the gacha state.
     *
     * @param game The main game instance.
     */
    public GachaState(Game game) {
        super(game);

        currencyUI = new CurrencyUI();
        bannerUI = new BannerUI();
        loadButtons();

        allAvailablePets = game.getPetCatalog();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates the interactive state of the gacha buttons, ensuring inactive buttons
     * are not processed.
     * </p>
     */
    @Override
    public void update() {
        for (Button button : buttons) {
            if (button != null && isButtonActive(button)) {
                button.update();
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Renders the current gacha banner, active UI buttons, and the player's current coin balance.
     * </p>
     */
    @Override
    public void draw(Graphics g) {
        // Background
        g.setColor(new Color(89, 198, 255));
        g.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        // Banners
        bannerUI.draw(g, currentBanner);

        // Buttons
        for (Button button : buttons) {
            if (button != null && isButtonActive(button)) {
                button.draw(g);
            }
        }

        // Currency
        currencyUI.draw(g, game.getPlayer().getCoins());
    }

    /**
     * Initializes all interactive buttons on the gacha screen.
     */
    private void loadButtons() {
        backButton = UtilsUI.createBackButton(20, 20);
        homeGachaButton = UtilsUI.createGachaButton(390, 20, 0);
        waterGachaButton = UtilsUI.createGachaButton(410 + Constants.BUTTON_WIDTH_BACK, 20, 1);
        buyButton = UtilsUI.createGachaButton(Constants.GAME_WIDTH - 45 - Constants.BUTTON_WIDTH_BACK, 700, 2);

        buttons = new Button[] {backButton, homeGachaButton, waterGachaButton, buyButton};
    }

    /**
     * Executes a gacha roll, deducting currency and adding a random pet from the current banner to the player's inventory.
     *
     * @throws IllegalStateException if the player does not have enough coins or owns all pets in the banner.
     */
    private void performGachaRoll() {
        List<Pet> fullPool = getBannerPool(currentBanner);
        List<Pet> availablePets = new ArrayList<>();

        for (Pet pet : fullPool) {
            if (!playerOwnsPet(pet.getName())) {
                availablePets.add(pet);
            }
        }

        if (availablePets.isEmpty()) {
            throw new IllegalStateException("You already own all pets in the " + currentBanner + " banner!");
        }

        if (game.getPlayer().getCoins() < GACHA_PRICE) {
            throw new IllegalStateException("Not enough coins! You need 160.");
        }

        game.getPlayer().spendCoins(GACHA_PRICE);

        Pet wonPet = pickRandomPetBasedOnRarity(availablePets);
        game.getPlayer().addPetToInventory(wonPet);
        SaveManager.saveGame(game.getPlayer());

        // TODO: show player what they won
    }

    /**
     * Randomly selects a pet from the available pool based on rarity drop rates.
     *
     * @param availablePets A list of pets the player does not currently own from the current banner.
     * @return The randomly selected pet.
     */
    private Pet pickRandomPetBasedOnRarity(List<Pet> availablePets) {
        int roll = random.nextInt(100);

        int targetRarity;
        if (roll < 10) {
            targetRarity = 3;
        } else if (roll < 40) {
            targetRarity = 2;
        } else {
            targetRarity = 1;
        }

        List<Pet> matchingRarityPets = new ArrayList<>();
        for (Pet pet : availablePets) {
            if (pet.getRarity() == targetRarity) {
                matchingRarityPets.add(pet);
            }
        }

        if (matchingRarityPets.isEmpty()) {
            return availablePets.get(random.nextInt(availablePets.size()));
        }

        return matchingRarityPets.get(random.nextInt(matchingRarityPets.size()));
    }

    /**
     * Checks if the player already owns a pet with the specified name.
     *
     * @param petName The name of the pet to check.
     * @return True if the player owns the pet, false otherwise.
     */
    private boolean playerOwnsPet(String petName) {
        for (Pet pet : game.getPlayer().getInventory()) {
            if (pet.getName().equals(petName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves all possible pets available in a specific banner.
     *
     * @param type The type of banner (e.g., HOME, WATER).
     * @return A list of pets available in that banner.
     */
    private List<Pet> getBannerPool(PetType type) {
        List<Pet> pool = new ArrayList<>();

        for (Pet pet : allAvailablePets) {
            if (pet.getType() == type) {
                pool.add(pet);
            }
        }

        return pool;
    }

    /**
     * Determines whether a specific banner button should be active based on the currently selected banner.
     *
     * @param b The button to check.
     * @return True if the button is active and should be clickable, false otherwise.
     */
    private boolean isButtonActive(Button b) {
        if (b == homeGachaButton && currentBanner == PetType.HOME) {
            return false;
        }
        if (b == waterGachaButton && currentBanner == PetType.WATER) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Processes left-clicks to change banners, execute a gacha roll, or return to gameplay.
     * </p>
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (backButton.isMouseOver() && backButton.isMousePressed()) {
                game.returnToPlaying();
            }

            if (isButtonActive(homeGachaButton) && homeGachaButton.isMouseOver() && homeGachaButton.isMousePressed()) {
                currentBanner = PetType.HOME;
            }
            if (isButtonActive(waterGachaButton) && waterGachaButton.isMouseOver() && waterGachaButton.isMousePressed()) {
                currentBanner = PetType.WATER;
            }

            if (buyButton.isMouseOver() && buyButton.isMousePressed()) {
                try {
                    performGachaRoll();
                } catch (IllegalStateException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
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
     * Detects initial left-clicks to press down UI buttons within the gacha screen.
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
     * Tracks the mouse position to update the hover state of active gacha buttons.
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
            if (button != null && isButtonActive(button) && button.getBounds().contains(e.getX(), e.getY())) {
                button.setMouseOver(true);
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>Unused in the Gacha state.</p>
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * {@inheritDoc}
     * <p>Unused in the Gacha state.</p>
     */
    @Override
    public void KeyPressed(KeyEvent e) {}

    /**
     * {@inheritDoc}
     * <p>Unused in the Gacha state.</p>
     */
    @Override
    public void KeyReleased(KeyEvent e) {}
}
