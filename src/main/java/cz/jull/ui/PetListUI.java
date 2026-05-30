package cz.jull.ui;

import cz.jull.logic.pet.Pet;
import cz.jull.logic.pet.PetSpecies;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Handles the visual rendering of the player's pet inventory screen.
 * Displays owned pets alongside their basic stats, and placeholders for unowned pets.
 */
public class PetListUI {
    private BufferedImage petListImg;
    private BufferedImage petNotOwned;

    PetSpecies[] slotSpecies = {
            PetSpecies.CAT, PetSpecies.DOG, PetSpecies.RABBIT, PetSpecies.HAMSTER, PetSpecies.PARROT,
            PetSpecies.OCTOPUS, PetSpecies.SHARK, PetSpecies.TURTLE, PetSpecies.SEAL, PetSpecies.JELLYFISH
    };

    int imgX = 20;
    int imgY = 185;

    /**
     * Constructs the PetListUI and pre-loads its required images.
     */
    public PetListUI() {
        loadImages();
    }

    /**
     * Loads the image assets required for the pet list inventory interface.
     * Extracts the main inventory background and the placeholder frame used for unowned pets.
     */
    private void loadImages() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PET_LIST);
        petListImg = img.getSubimage(0, 0, Constants.PET_LIST_WIDTH, Constants.PET_LIST_HEIGHT);

        BufferedImage img2 = LoadSave.getSpriteAtlas(LoadSave.PET_NOT_OWNED);
        petNotOwned = img2.getSubimage(0,0, Constants.PET_FRAME_WIDTH, Constants.PET_FRAME_HEIGHT);
    }

    /**
     * Draws the inventory UI overlay and populates the grid slots based on the player's ownership.
     *
     * @param g         The Graphics context used for drawing.
     * @param inventory A list of pets currently owned by the player.
     */
    public void draw(Graphics g, List<Pet> inventory) {
        // Background
        g.setColor(new Color(89, 198, 255));
        g.fillRect(0,0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        // Pet List
        if (petListImg != null) {
            g.drawImage(petListImg, imgX, imgY, Constants.PET_LIST_WIDTH, Constants.PET_LIST_HEIGHT, null);
        } else {
            g.setColor(new Color(255, 182, 193));
            g.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        }

        int cols = 5;
        int xOffsetStart = 90 + imgX;
        int yOffsetStart = 275 + imgY;
        int xSpacing = 320;
        int ySpacing = 345;

        int frameXStart = 70;
        int frameYStart = 235;

        for (int i = 0; i < 10; i++) {

            Pet petToDraw = null;
            for (Pet pet : inventory) {
                if (pet.getSpecies() == slotSpecies[i]) {
                    petToDraw = pet;
                    break;
                }
            }

            int row = i / cols;
            int col = i % cols;

            int xCorrection = 0;
            if (col == 3 || col == 4) {
                xCorrection = -5;
            }

            if (petToDraw != null) {
                int drawX = xOffsetStart + (col * xSpacing) + xCorrection;
                int drawY = yOffsetStart + (row * ySpacing);
                drawMiniStats(g, petToDraw, drawX, drawY);
            } else {
                int frameX = frameXStart + (col * xSpacing) + xCorrection;
                int frameY = frameYStart + (row * ySpacing);

                if (petNotOwned != null) {
                    g.drawImage(petNotOwned, frameX, frameY, Constants.PET_FRAME_WIDTH, Constants.PET_FRAME_HEIGHT, null);
                }
            }
        }
    }

    /**
     * Draws simplified, color-coded squares representing the pet's current stats underneath their portrait.
     *
     * @param g   The Graphics context.
     * @param pet The pet whose stats are being drawn.
     * @param x   The X coordinate for the mini stats box.
     * @param y   The Y coordinate for the mini stats box.
     */
    private void drawMiniStats(Graphics g, Pet pet, int x, int y) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 160, 45);

        Color hungerColor = getStatColor(pet.getHunger());
        Color energyColor = getStatColor(pet.getEnergy());
        Color loveColor = getStatColor(pet.getLove());

        g.setColor(hungerColor);
        g.fillRect(x + 5, y + 5, 45, 35);

        g.setColor(energyColor);
        g.fillRect(x + 55, y + 5, 50, 35);

        g.setColor(loveColor);
        g.fillRect(x + 110, y + 5, 45, 35);
    }

    /**
     * Maps a stat value (0-100) to an RGB color used in the mini stats box.
     *
     * @param statValue The pet's stat value.
     * @return A Color representing the health/quality of the stat.
     */
    private Color getStatColor(int statValue) {
        if (statValue >= 81) {
            return new Color(72, 210, 110);
        } else if (statValue >= 61) {
            return new Color(177, 210, 78);
        } else if (statValue >= 41) {
            return new Color(210, 210, 45);
        } else if (statValue >= 21) {
            return new Color(210, 157, 45);
        } else {
            return new Color(210, 87, 55);
        }
    }
}
