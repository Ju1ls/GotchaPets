package cz.jull.ui.box;

import cz.jull.logic.pet.PetType;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Handles the loading and rendering of Gacha box sprites.
 */
public class BoxUI {
    private BufferedImage homeBox;
    private BufferedImage homeBoxClosed;
    private BufferedImage homeBoxOpen;

    private BufferedImage waterBox;
    private BufferedImage waterBoxClosed;
    private BufferedImage waterBoxOpen;

    /**
     * Constructs the BoxUI and loads all required box images into memory.
     */
    public BoxUI() {
        loadImages();
    }

    /**
     * Loads and assigns the required sub-images for all interactive box sprites.
     * <p>
     * This method fetches the "Home" and "Water" boxes in their normal, closed,
     * and open states, assigning them to their respective class fields using
     * dimensions defined in the {@link Constants} class.
     * </p>
     */
    private void loadImages() {
        // HOME
        homeBox = loadBoxSprite(LoadSave.HOME_BOX, Constants.BOX_WIDTH, Constants.BOX_HEIGHT);
        homeBoxClosed = loadBoxSprite(LoadSave.HOME_BOX_CLOSED, Constants.BOX_CLOSED_WIDTH, Constants.BOX_CLOSED_HEIGHT);
        homeBoxOpen = loadBoxSprite(LoadSave.HOME_BOX_OPEN, Constants.BOX_OPEN_WIDTH, Constants.BOX_OPEN_HEIGHT);

        // WATER
        waterBox = loadBoxSprite(LoadSave.WATER_BOX, Constants.BOX_WIDTH, Constants.BOX_HEIGHT);
        waterBoxClosed = loadBoxSprite(LoadSave.WATER_BOX_CLOSED, Constants.BOX_CLOSED_WIDTH, Constants.BOX_CLOSED_HEIGHT);
        waterBoxOpen = loadBoxSprite(LoadSave.WATER_BOX_OPEN, Constants.BOX_OPEN_WIDTH, Constants.BOX_OPEN_HEIGHT);
    }

    /**
     * Helper method to load a sprite atlas and extract a specific sub-image.
     * @param atlasPath The file path or identifier for the sprite atlas defined in {@link LoadSave}.
     * @param width     The target width of the sub-image to extract.
     * @param height    The target height of the sub-image to extract.
     * @return A {@link BufferedImage} containing the extracted box sprite.
     */
    private BufferedImage loadBoxSprite(String atlasPath, int width, int height) {
        BufferedImage atlas = LoadSave.getSpriteAtlas(atlasPath);
        return atlas.getSubimage(0, 0, width, height);
    }

    /**
     * Draws the appropriate box image based on the banner type and the requested image state.
     *
     * @param g     The graphics context to draw on.
     * @param x     The X coordinate to draw the box.
     * @param y     The Y coordinate to draw the box.
     * @param type  The banner type (e.g., HOME, WATER).
     * @param state The visual state of the box (BASE, CLOSED, OPEN).
     */
    public void drawBox(Graphics g, int x, int y, PetType type, BoxImageState state) {
        BufferedImage img = null;

        switch (type) {
            case HOME -> {
                switch (state) {
                    case BASE -> img = homeBox;
                    case CLOSED -> img = homeBoxClosed;
                    case OPEN -> img = homeBoxOpen;
                }
            }
            case WATER -> {
                switch (state) {
                    case BASE -> img = waterBox;
                    case CLOSED -> img = waterBoxClosed;
                    case OPEN -> img = waterBoxOpen;
                }
            }
        }

        if (img != null) {
            int drawX = (Constants.GAME_WIDTH / 2) - (img.getWidth() / 2);
            int drawY = (Constants.GAME_HEIGHT / 2) - (img.getHeight() / 2);
            g.drawImage(img, drawX, drawY, null);
        }
    }
}
