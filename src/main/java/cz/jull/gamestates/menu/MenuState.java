package cz.jull.gamestates.menu;

import cz.jull.Game;
import cz.jull.gamestates.Button;
import cz.jull.gamestates.State;
import cz.jull.gamestates.StateMethods;
import cz.jull.ui.BackgroundUI;
import cz.jull.ui.UtilsUI;
import cz.jull.utils.Constants;
import lombok.Setter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Manages the main menu state, allowing the player to start the game, view credits, or quit.
 */
public class MenuState extends State implements StateMethods {
    private final CreditsScreen creditsScreen;
    @Setter
    private boolean showCredits = false;

    private final Button[] buttons = new Button[3];

    private final BackgroundUI backgroundUI;

    /**
     * Constructs the main menu state.
     *
     * @param game The main game instance.
     */
    public MenuState(Game game) {
        super(game);
        loadButtons();
        backgroundUI = new BackgroundUI();
        creditsScreen = new CreditsScreen(this);
    }

    /**
     * Initializes all interactive buttons for the menu screen.
     */
    private void loadButtons() {
        int srcWidth = Constants.BUTTON_WIDTH_MENU;
        int drawWidth = (int) (srcWidth * Constants.SCALE);

        int yPos = 500;
        int spacing = 40; // Gap between buttons

        int totalRowWidth = (drawWidth * 3) + (spacing * 2);
        int startX = (Constants.GAME_WIDTH / 2) - (totalRowWidth / 2);

        int middleX = startX + drawWidth + spacing;
        int rightX = startX + (drawWidth * 2) + (spacing * 2);

        buttons[Constants.BUTTON_QUIT] = UtilsUI.createMenuButton(startX, yPos, Constants.BUTTON_QUIT);
        buttons[Constants.BUTTON_PLAY] = UtilsUI.createMenuButton(middleX, yPos, Constants.BUTTON_PLAY);
        buttons[Constants.BUTTON_CREDITS] = UtilsUI.createMenuButton(rightX, yPos, Constants.BUTTON_CREDITS);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates either the credits screen sub-state (if visible) or the main menu button states.
     * </p>
     */
    @Override
    public void update() {
        if (showCredits) {
            creditsScreen.update();
        } else {
            for (Button mb : buttons) {
                mb.update();
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Renders either the credits overlay (if toggled) or the main menu background and buttons.
     * </p>
     */
    @Override
    public void draw(Graphics g) {
        if (showCredits) {
            creditsScreen.draw(g);
        } else {
            backgroundUI.drawMenu(g);
            for (Button mb : buttons) {
                mb.draw(g);
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Detects initial left-clicks to press down UI buttons or delegates to the credits screen.
     * </p>
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (showCredits) {
            creditsScreen.mousePressed(e);
        } else if (e.getButton() == MouseEvent.BUTTON1){
            for (Button mb : buttons) {
                if (mb.isMouseOver()) {
                    mb.setMousePressed(true);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Processes left-click releases to start the game, exit the application, or show credits.
     * </p>
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (showCredits) {
            creditsScreen.mouseReleased(e);
        } else if (e.getButton() == MouseEvent.BUTTON1){
            for (int i = 0; i < buttons.length; i++) {
                Button mb = buttons[i];
                if (mb.isMouseOver() && mb.isMousePressed()) {
                    if (i == 0) {
                        game.startGame();
                    } else if (i == 1) {
                        System.exit(0);
                    } else if (i == 2) {
                        showCredits = true;
                    }
                }
                mb.setMousePressed(false);
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Tracks the mouse position to update the hover state of menu buttons or delegates to the credits screen.
     * </p>
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (showCredits) {
            creditsScreen.mouseMoved(e);
        } else {
            for (Button mb : buttons) {
                mb.setMouseOver(false);
            }

            for (Button mb : buttons) {
                if (mb.getBounds().contains(e.getX(), e.getY())) {
                    mb.setMouseOver(true);
                    break;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>Unused in the Menu state.</p>
     */
    @Override
    public void KeyPressed(KeyEvent e) {

    }

    /**
     * {@inheritDoc}
     * <p>Unused in the Menu state.</p>
     */
    @Override
    public void KeyReleased(KeyEvent e) {

    }

    /**
     * {@inheritDoc}
     * <p>Unused in the Menu state.</p>
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
