package cz.jull.gamestates.gacha;

import cz.jull.Game;
import cz.jull.gamestates.Button;
import cz.jull.gamestates.State;
import cz.jull.gamestates.StateMethods;
import cz.jull.logic.pet.Pet;
import cz.jull.logic.pet.PetSpecies;
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

    public GachaState(Game game) {
        super(game);

        currencyUI = new CurrencyUI();
        bannerUI = new BannerUI();
        loadButtons();
    }

    @Override
    public void update() {
        for (Button button : buttons) {
            if (button != null && isButtonActive(button)) {
                button.update();
            }
        }
    }

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

    private void loadButtons() {
        backButton = UtilsUI.createBackButton(20, 20);
        homeGachaButton = UtilsUI.createGachaButton(390, 20, 0);
        waterGachaButton = UtilsUI.createGachaButton(410 + Constants.BUTTON_WIDTH_BACK, 20, 1);
        buyButton = UtilsUI.createGachaButton(Constants.GAME_WIDTH - 45 - Constants.BUTTON_WIDTH_BACK, 700, 2);

        buttons = new Button[] {backButton, homeGachaButton, waterGachaButton, buyButton};
    }

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
            if (getPetRarity(pet.getName()) == targetRarity) {
                matchingRarityPets.add(pet);
            }
        }

        if (matchingRarityPets.isEmpty()) {
            return availablePets.get(random.nextInt(availablePets.size()));
        }

        return matchingRarityPets.get(random.nextInt(matchingRarityPets.size()));
    }

    private boolean playerOwnsPet(String petName) {
        for (Pet pet : game.getPlayer().getInventory()) {
            if (pet.getName().equals(petName)) {
                return true;
            }
        }
        return false;
    }

    private List<Pet> getBannerPool(PetType type) {
        List<Pet> pool = new ArrayList<>();

        switch (type) {
            case HOME -> {
                pool.add(new Pet("Cat", PetType.HOME, PetSpecies.CAT)); // Silver
                pool.add(new Pet("Golden Retriever", PetType.HOME, PetSpecies.DOG)); // Silver
                pool.add(new Pet("Rabbit", PetType.HOME, PetSpecies.RABBIT)); // Gold
                pool.add(new Pet("Hamster", PetType.HOME, PetSpecies.HAMSTER)); // Gold
                pool.add(new Pet("Parrot", PetType.HOME, PetSpecies.PARROT)); // Rainbow
            }
            case WATER -> {
                pool.add(new Pet("Octopus", PetType.WATER, PetSpecies.OCTOPUS));// Silver
                pool.add(new Pet("Shark", PetType.WATER, PetSpecies.SHARK)); // Silver
                pool.add(new Pet("Seal", PetType.WATER, PetSpecies.SEAL)); // Gold
                pool.add(new Pet("Turtle", PetType.WATER, PetSpecies.TURTLE)); // Gold
                pool.add(new Pet("Jellyfish", PetType.WATER, PetSpecies.JELLYFISH)); // Rainbow
            }
        }

        return pool;
    }

    private int getPetRarity(String petName) {
        return switch (petName) {
            case "Cat", "Dog", "Octopus", "Shark" -> 1;     // Silver
            case "Rabbit", "Hamster", "Turtle", "Seal"-> 2; // Gold
            case "Parrot", "Jellyfish" -> 3;                // Ultra Rare
            default -> 1;
        };
    }

    private boolean isButtonActive(Button b) {
        if (b == homeGachaButton && currentBanner == PetType.HOME) {
            return false;
        }
        if (b == waterGachaButton && currentBanner == PetType.WATER) {
            return false;
        }
        return true;
    }

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

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void KeyPressed(KeyEvent e) {}

    @Override
    public void KeyReleased(KeyEvent e) {}
}
