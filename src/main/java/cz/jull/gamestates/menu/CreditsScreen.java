package cz.jull.gamestates.menu;

import cz.jull.gamestates.Button;
import cz.jull.gamestates.StateMethods;
import cz.jull.ui.UtilsUI;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Represents the credits overlay screen shown within the main menu state.
 */
public class CreditsScreen implements StateMethods {
    private MenuState menu;

    private BufferedImage titleImg;
    private BufferedImage foregroundImg;
    private Button backButton;

    /**
     * Constructs the credits screen.
     *
     * @param menu The MenuState that this screen belongs to.
     */
    public CreditsScreen(MenuState menu) {
        this.menu = menu;
        loadImages();
        backButton = UtilsUI.createBackButton(20, 20);
    }

    /**
     * Loads required image assets for the credits screen from the sprite atlas.
     */
    private void loadImages() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.CREDITS_TITLE);
        titleImg = img.getSubimage(380, 20, Constants.CREDITS_TITLE_WIDTH, Constants.BUTTON_DEFAULT_HEIGHT);

        BufferedImage img2 = LoadSave.getSpriteAtlas(LoadSave.CREDITS_FOREGROUND);
        foregroundImg = img2.getSubimage(20, 185, Constants.PET_LIST_WIDTH, Constants.PET_LIST_HEIGHT);
    }

    /**
     * {@inheritDoc}
     * <p>Updates the interactive state of the back button on the credits screen.</p>
     */
    @Override
    public void update() {
        backButton.update();
    }

    /**
     * {@inheritDoc}
     * <p>Renders the credits UI background, foreground scroll, title, and back button.</p>
     */
    @Override
    public void draw(Graphics g) {
        // Background
        g.setColor(new Color(89, 198, 255));
        g.fillRect(0,0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        // Foreground
        int foregroundWidth = Constants.PET_LIST_WIDTH;
        int foregroundHeight = Constants.PET_LIST_HEIGHT;

        g.drawImage(foregroundImg, 20, 185, foregroundWidth, foregroundHeight, null);

        // Title
        int titleWidth = Constants.CREDITS_TITLE_WIDTH;
        int titleHeight = Constants.BUTTON_DEFAULT_HEIGHT;

        g.drawImage(titleImg, 380, 20, titleWidth, titleHeight, null);

        // Button
        backButton.draw(g);

        // might change later
//        g.setColor(Color.WHITE);
//        g.setFont(new Font("Arial", Font.BOLD, 30));
//        g.drawString("lala", 300, foregroundY + 500);
    }

    /**
     * {@inheritDoc}
     * <p>Detects left-clicks to press down the back button.</p>
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (backButton.isMouseOver()) {
                backButton.setMousePressed(true);
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>Detects left-click releases to navigate back to the main menu.</p>
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (backButton.isMouseOver() && backButton.isMousePressed()) {
                menu.setShowCredits(false);
            }
            backButton.setMousePressed(false);
        }
    }

    /**
     * {@inheritDoc}
     * <p>Tracks the mouse position to update the hover state of the back button.</p>
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        backButton.setMouseOver(false);
        if (backButton.getBounds().contains(e.getX(), e.getY())) {
            backButton.setMouseOver(true);
        }
    }

    /**
     * {@inheritDoc}
     * <p>Unused in the Credits screen.</p>
     */
    @Override
    public void KeyPressed(KeyEvent e) {

    }

    /**
     * {@inheritDoc}
     * <p>Unused in the Credits screen.</p>
     */
    @Override
    public void KeyReleased(KeyEvent e) {

    }

    /**
     * {@inheritDoc}
     * <p>Unused in the Credits screen.</p>
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
