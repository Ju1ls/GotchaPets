package cz.jull.ui;

import cz.jull.gamestates.Button;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;

import java.awt.image.BufferedImage;

public class UtilsUI {
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
