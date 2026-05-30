package cz.jull.ui;

import cz.jull.logic.pet.PetType;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Manages and renders full-screen background images based on the active game state
 * and the pet's environment type.
 */
public class BackgroundUI {
    private BufferedImage homeBgImg;
    private BufferedImage waterBgImg;
    private BufferedImage menuBgImg;

    /**
     * Constructs the BackgroundUI and pre-loads the environment background images.
     */
    public BackgroundUI() {
        loadImages();
    }

    /**
     * Loads the full-screen background images for the various game environments.
     * Retrieves the backgrounds for the main menu, home habitat, and water habitat.
     */
    private void loadImages() {
        homeBgImg = LoadSave.getSpriteAtlas(LoadSave.HOME_BACKGROUND);
        waterBgImg = LoadSave.getSpriteAtlas(LoadSave.WATER_BACKGROUND);
        menuBgImg = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND);
    }

    /**
     * Draws the main menu background.
     *
     * @param g The Graphics context used for drawing.
     */
    public void drawMenu(Graphics g) {
        g.drawImage(menuBgImg, 0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, null);
    }

    /**
     * Draws the appropriate gameplay background based on the equipped pet's native environment.
     *
     * @param g The Graphics context used for drawing.
     * @param type The PetType indicating which environment background to render.
     */
    public void drawPlay(Graphics g, PetType type) {
        if (type == null) {
            return;
        }

        switch (type) {
            case HOME -> g.drawImage(homeBgImg, 0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, null);
            case WATER -> {
                if (waterBgImg != null) {
                    g.drawImage(waterBgImg, 0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, null);
                }
            }
            default -> {
                g.setColor(new Color(89, 198, 255));
                g.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
            }
        }
    }
}
