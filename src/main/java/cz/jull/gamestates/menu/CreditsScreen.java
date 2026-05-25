package cz.jull.gamestates.menu;

import cz.jull.gamestates.Button;
import cz.jull.gamestates.StateMethods;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class CreditsScreen implements StateMethods {
    private MenuState menu;

    private BufferedImage backgroundImg;
    private BufferedImage titleImg;
    private BufferedImage foregroundImg;
    private Button backButton;

    public CreditsScreen(MenuState menu) {
        this.menu = menu;
        loadImages();
        loadBackButton();
    }

    private void loadImages() {
        titleImg = LoadSave.getSpriteAtlas(LoadSave.CREDITS_TITLE);
        foregroundImg = LoadSave.getSpriteAtlas(LoadSave.CREDITS_FOREGROUND);
    }

    private void loadBackButton() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.BACK_BUTTON);

        int srcWidth = Constants.BUTTON_WIDTH_BACK;
        int srcHeight = Constants.BUTTON_DEFAULT_HEIGHT;

        BufferedImage[] temp = new BufferedImage[3];

        for (int i = 0; i < 3; i++) {
            temp[i] = img.getSubimage(i * srcWidth, 0, srcWidth, srcHeight);
        }

        int xPos = 20;
        int yPos = 20;

        backButton = new Button(xPos, yPos, srcWidth, srcHeight, temp);
    }

    @Override
    public void update() {
        backButton.update();
    }

    @Override
    public void draw(Graphics g) {
        // Background
        g.setColor(new Color(89, 198, 255));
        g.fillRect(0,0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        // Foreground
        int foregroundWidth = Constants.GAME_WIDTH;
        int foregroundHeight = Constants.GAME_HEIGHT;

        g.drawImage(foregroundImg, 0, 0, foregroundWidth, foregroundHeight, null);

        // Title
        int titleWidth = Constants.GAME_WIDTH;
        int titleHeight = Constants.GAME_HEIGHT;

        g.drawImage(titleImg, 0, 0, titleWidth, titleHeight, null);

        // Button
        backButton.draw(g);

        // might change later
//        g.setColor(Color.WHITE);
//        g.setFont(new Font("Arial", Font.BOLD, 30));
//        g.drawString("lala", 300, foregroundY + 500);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (backButton.isMouseOver()) {
                backButton.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (backButton.isMouseOver() && backButton.isMousePressed()) {
                menu.setShowCredits(false);
            }
            backButton.setMousePressed(false);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        backButton.setMouseOver(false);
        if (backButton.getBounds().contains(e.getX(), e.getY())) {
            backButton.setMouseOver(true);
        }
    }

    @Override
    public void KeyPressed(KeyEvent e) {

    }

    @Override
    public void KeyReleased(KeyEvent e) {

    }
}
