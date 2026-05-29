package cz.jull.ui;

import cz.jull.logic.pet.PetType;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BannerUI {
    private BufferedImage homeBanner;
    private BufferedImage waterBanner;

    public BannerUI() {
        loadImages();
    }

    private void loadImages() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.HOME_BANNER_GACHA);
        homeBanner = img.getSubimage(0, 0, 1560, 860);

        BufferedImage img2 = LoadSave.getSpriteAtlas(LoadSave.WATER_BANNER_GACHA);
        waterBanner = img2.getSubimage(0, 0, 1560, 860);
    }

    public void draw(Graphics g, PetType currentBannerType) {
        int x = 20;
        int y = 20;
        int width = Constants.BANNER_WIDTH;
        int height = Constants.BANNER_HEIGHT;

        switch (currentBannerType) {
            case HOME -> g.drawImage(homeBanner, x, y, width, height, null);
            case WATER -> {
                if (waterBanner != null) {
                    g.drawImage(waterBanner, x, y, width, height, null);
                }
            }
        }
    }
}
