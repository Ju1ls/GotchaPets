package cz.jull.gamestates.menu;

import cz.jull.Game;
import cz.jull.gamestates.Button;
import cz.jull.gamestates.State;
import cz.jull.gamestates.StateMethods;
import cz.jull.ui.BackgroundUI;
import cz.jull.ui.UtilsUI;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MenuState extends State implements StateMethods {
    private CreditsScreen creditsScreen;
    private boolean showCredits = false;

    private Button[] buttons = new Button[3];

    private BackgroundUI backgroundUI;

    public MenuState(Game game) {
        super(game);
        loadButtons();
        backgroundUI = new BackgroundUI();
        creditsScreen = new CreditsScreen(this);
    }

    private void loadButtons() {
        int srcWidth = Constants.BUTTON_WIDTH_MENU;
        int drawWidth = (int) (srcWidth * Constants.SCALE);

        int yPos = 500;
        int spacing = 40; // Gap between buttons

        int totalRowWidth = (drawWidth * 3) + (spacing * 2);
        int startX = (Constants.GAME_WIDTH / 2) - (totalRowWidth / 2);

        int leftX = startX;
        int middleX = startX + drawWidth + spacing;
        int rightX = startX + (drawWidth * 2) + (spacing * 2);

        buttons[Constants.BUTTON_QUIT] = UtilsUI.createMenuButton(leftX, yPos, Constants.BUTTON_QUIT);
        buttons[Constants.BUTTON_PLAY] = UtilsUI.createMenuButton(middleX, yPos, Constants.BUTTON_PLAY);
        buttons[Constants.BUTTON_CREDITS] = UtilsUI.createMenuButton(rightX, yPos, Constants.BUTTON_CREDITS);
    }

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

    @Override
    public void KeyPressed(KeyEvent e) {

    }

    @Override
    public void KeyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void setShowCredits(boolean showCredits) {
        this.showCredits = showCredits;
    }
}
