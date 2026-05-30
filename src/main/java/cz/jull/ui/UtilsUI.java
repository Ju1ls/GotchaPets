package cz.jull.ui;

import cz.jull.gamestates.Button;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;

import java.awt.image.BufferedImage;

/**
 * A utility class responsible for instantiating UI Buttons with their correct
 * sprites, dimensions, and coordinates extracted from the respective sprite atlases.
 */
public class UtilsUI {
    /**
     * Creates a standardized "Back" button used across various screens.
     *
     * @param x The X coordinate for the button.
     * @param y The Y coordinate for the button.
     * @return A newly configured back Button object.
     */
    public static Button createBackButton(int x, int y) {
        BufferedImage backButtonSprite = LoadSave.getSpriteAtlas(LoadSave.BACK_BUTTON);

        int backButtonWidth = Constants.BUTTON_WIDTH_BACK;
        int backButtonHeight = Constants.BUTTON_DEFAULT_HEIGHT;

        BufferedImage[] backImgs = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            backImgs[i] = backButtonSprite.getSubimage(i * backButtonWidth, 0, backButtonWidth, backButtonHeight);
        }

        return new Button(x, y, backButtonWidth, backButtonHeight, backImgs);
    }

    /**
     * Creates a gacha-related button (e.g., banner selectors, buy button).
     *
     * @param x        The X coordinate for the button.
     * @param y        The Y coordinate for the button.
     * @param rowIndex The row in the sprite sheet corresponding to the desired button type.
     * @return A newly configured gacha Button object.
     */
    public static Button createGachaButton(int x, int y, int rowIndex) {
        BufferedImage spriteSheet = LoadSave.getSpriteAtlas(LoadSave.GACHA_BUTTONS);

        int width = Constants.BUTTON_WIDTH_BACK;
        int height = Constants.BUTTON_DEFAULT_HEIGHT;

        BufferedImage[] imgs = new BufferedImage[3];

        for (int i = 0; i < 3; i++) {
            imgs[i] = spriteSheet.getSubimage(i * width, rowIndex * height, width, height);
        }

        return new Button(x, y, width, height, imgs);
    }

    /**
     * Creates an in-game action button (e.g., feed, sleep, love, list, gacha shortcut).
     *
     * @param x        The X coordinate for the button.
     * @param y        The Y coordinate for the button.
     * @param rowIndex The row in the sprite sheet corresponding to the desired action.
     * @return A newly configured game Button object.
     */
    public static Button createGameButton(int x, int y, int rowIndex) {
        BufferedImage spriteSheet = LoadSave.getSpriteAtlas(LoadSave.GAME_BUTTONS);

        int width = Constants.BUTTON_WIDTH_GAME;
        int height = Constants.BUTTON_DEFAULT_HEIGHT;

        BufferedImage[] imgs = new BufferedImage[3];

        for (int i = 0; i < 3; i++) {
            imgs[i] = spriteSheet.getSubimage(i * width, rowIndex * height, width, height);
        }

        return new Button(x, y, width, height, imgs);
    }

    /**
     * Creates a main menu button (e.g., Play, Credits, Quit).
     * Applies scaling based on global constants.
     *
     * @param x        The X coordinate for the button.
     * @param y        The Y coordinate for the button.
     * @param rowIndex The row in the sprite sheet corresponding to the desired button type.
     * @return A newly configured menu Button object.
     */
    public static Button createMenuButton(int x, int y, int rowIndex) {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.MENU_BUTTONS);

        int srcWidth = Constants.BUTTON_WIDTH_MENU;
        int srcHeight = Constants.BUTTON_HEIGHT_MENU;

        int drawWidth = (int) (srcWidth * Constants.SCALE);
        int drawHeight = (int) (srcHeight * Constants.SCALE);

        BufferedImage[] tempImgs = new BufferedImage[3];

        int[] xStarts = {0, 534, 1068};
        int safeWidth = srcWidth - 1;

        for (int j = 0; j < 3; j++) {
            tempImgs[j] = img.getSubimage(xStarts[j], rowIndex * srcHeight, safeWidth, srcHeight);
        }

        return new Button(x, y, drawWidth, drawHeight, tempImgs);
    }
}
