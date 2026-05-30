package cz.jull.ui;

import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Handles rendering the player's current coin total on screen.
 */
public class CurrencyUI {
    private BufferedImage currency;
    private BufferedImage currencyBackground;

    /**
     * Constructs the CurrencyUI and pre-loads the background and coin images.
     */
    public CurrencyUI() {
        loadImages();
    }

    /**
     * Loads and slices the currency UI assets from the sprite atlas.
     * Extracts the background panel for the currency display and the individual coin icon.
     */
    private void loadImages() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.CURRENCY);

        int bgWidth = Constants.CURRENCY_BACKGROUND_WIDTH;
        int bgHeight = Constants.BUTTON_DEFAULT_HEIGHT;
        int coinSize = Constants.COIN_SIZE;
        int coinXOffset = 416;

        currencyBackground = img.getSubimage(0, 0, bgWidth, bgHeight);
        currency = img.getSubimage(coinXOffset, 0, coinSize, coinSize);
    }

    /**
     * Draws the currency box and the text representing the player's coin balance.
     *
     * @param g     The Graphics context used for drawing.
     * @param coins The player's total amount of coins.
     */
    public void draw(Graphics g, int coins) {
        int backgroundX = Constants.GAME_WIDTH - 435;
        int backgroundY = 20;
        int backgroundWidth = Constants.CURRENCY_BACKGROUND_WIDTH;
        int backgroundHeight = Constants.BUTTON_DEFAULT_HEIGHT;

        g.drawImage(currencyBackground, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);

        int coinSize = Constants.COIN_SIZE;
        int coinX = backgroundX + 20;
        int coinY = backgroundY + 20;

        g.drawImage(currency, coinX, coinY, coinSize, coinSize, null);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 60));

        int textX = coinX + coinSize + 20;
        int textY = backgroundY + 95;

        g.drawString(coins + "", textX, textY);
    }
}
