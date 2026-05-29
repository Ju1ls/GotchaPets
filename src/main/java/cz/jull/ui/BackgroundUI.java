package cz.jull.ui;

import cz.jull.logic.pet.PetType;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundUI {
    private BufferedImage homeBgImg;
    private BufferedImage waterBgImg;
    private BufferedImage menuBgImg;

    public BackgroundUI() {
        loadImages();
    }

    private void loadImages() {
        homeBgImg = LoadSave.getSpriteAtlas(LoadSave.HOME_BACKGROUND);
        waterBgImg = LoadSave.getSpriteAtlas(LoadSave.WATER_BACKGROUND);
        menuBgImg = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND);
    }

    public void drawMenu(Graphics g) {
        g.drawImage(menuBgImg, 0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, null);
    }

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
