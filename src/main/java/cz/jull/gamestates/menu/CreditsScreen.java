package cz.jull.gamestates.menu;

import cz.jull.Game;
import cz.jull.gamestates.Button;
import cz.jull.gamestates.StateMethods;
import cz.jull.utilz.Constants;
import cz.jull.utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class CreditsScreen implements StateMethods {
    private Game game;
    private MenuState menu;

    private BufferedImage backgroundImg;
    private BufferedImage titleImg;
    private BufferedImage foregroundImg;
    private Button backButton;

    public CreditsScreen(Game game, MenuState menu) {
        this.game = game;
        this.menu = menu;
        loadImages();
        loadBackButton();
    }

    private void loadImages() {
        backgroundImg = LoadSave.getSpriteAtlas(LoadSave.CREDITS_BACKGROUND);
        titleImg = LoadSave.getSpriteAtlas(LoadSave.CREDITS_TITLE);
        foregroundImg = LoadSave.getSpriteAtlas(LoadSave.CREDITS_FOREGROUND);
    }

    private void loadBackButton() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.BACK_BUTTON);

        int srcWidth = Constants.B_WIDTH_BACK;
        int srcHeight = Constants.B_DEFAULT_HEIGHT;

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
        g.drawImage(backgroundImg, 0,0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, null);

        // Foreground
        int foregroundWidth = 1600; // here too
        int foregroundHeight = 900;

        g.drawImage(foregroundImg, 0, 0, foregroundWidth, foregroundHeight, null);

        // Title
        int titleWidth = 1600; // change to game.width n height
        int titleHeight = 900;

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
