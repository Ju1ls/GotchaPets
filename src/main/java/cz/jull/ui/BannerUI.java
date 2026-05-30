package cz.jull.ui;

import cz.jull.logic.pet.PetType;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Handles rendering the active banner graphics for the gacha system.
 */
public class BannerUI {
    private BufferedImage homeBanner;
    private BufferedImage waterBanner;

    /**
     * Constructs the BannerUI and pre-loads the banner image overlays.
     */
    public BannerUI() {
        loadImages();
    }

    /**
     * Loads the full-size promotional banner images used in the gacha rolling screen.
     * Extracts both the home and water environment banners.
     */
    private void loadImages() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.HOME_BANNER_GACHA);
        homeBanner = img.getSubimage(0, 0, 1560, 860);

        BufferedImage img2 = LoadSave.getSpriteAtlas(LoadSave.WATER_BANNER_GACHA);
        waterBanner = img2.getSubimage(0, 0, 1560, 860);
    }

    /**
     * Draws the gacha banner corresponding to the currently selected pet type.
     *
     * @param g The Graphics context used for drawing.
     * @param currentBannerType The PetType of the currently active banner.
     */
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
