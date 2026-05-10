package cz.jull.gamestates.menu;

import cz.jull.Game;
import cz.jull.gamestates.Button;
import cz.jull.gamestates.State;
import cz.jull.gamestates.StateMethods;
import cz.jull.utilz.Constants;
import cz.jull.utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MenuState extends State implements StateMethods {
    private CreditsScreen creditsScreen;
    private boolean showCredits = false;

    private Button[] buttons = new Button[3];
    private BufferedImage backgroundImg;

    private int menuWidth;
    private int menuHeight;

    public MenuState(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        creditsScreen = new CreditsScreen(game, this);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND);

        menuWidth = Constants.GAME_WIDTH;
        menuHeight = Constants.GAME_HEIGHT;
    }

    private void loadButtons() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.MENU_BUTTONS);

        int srcWidth = Constants.B_WIDTH_MENU;
        int srcHeight = Constants.B_HEIGHT_MENU;

        int drawWidth = (int) (srcWidth * Constants.SCALE);
        int drawHeight = (int) (srcHeight * Constants.SCALE);

        int yPos = 500;
        int spacing = 40; // Gap between buttons

        int totalRowWidth = (drawWidth * 3) + (spacing * 2);
        int startX = (Constants.GAME_WIDTH / 2) - (totalRowWidth / 2);

        // i = row; j = column
        for (int i = 0; i < buttons.length; i++) {
            BufferedImage[] tempImgs = new BufferedImage[3];

            for (int j = 0; j < tempImgs.length; j++) {
                tempImgs[j] = img.getSubimage(j * srcWidth, i * srcHeight, srcWidth, srcHeight);
            }

            int xPos = 0;
            if (i == 1) { // QUIT
                xPos = startX;
            } else if (i == 0) { // PLAY
                xPos = startX + drawWidth + spacing;
            } else if (i == 2) { // CREDITS
                xPos = startX + (drawWidth * 2) + (spacing * 2);
            }

            buttons[i] = new Button(xPos, yPos, drawWidth, drawHeight, tempImgs);
        }
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
            g.drawImage(backgroundImg, 0, 0, menuWidth, menuHeight, null);
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
