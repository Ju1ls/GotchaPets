package cz.jull.gamestates;

import cz.jull.utils.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Button {
    private int xPos;
    private int yPos;
    private int width;
    private int height;

    private BufferedImage[] imgs;

    private int state = Constants.BUTTON_STATE_DEFAULT;
    private boolean mouseOver;
    private boolean mousePressed;
    private Rectangle bounds;

    public Button(int xPos, int yPos, int width, int height, BufferedImage[] imgs) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.imgs = imgs;
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos, yPos, width, height);
    }

    public void update() {
        state = Constants.BUTTON_STATE_DEFAULT;
        if (mouseOver) {
            state = Constants.BUTTON_STATE_HOVER;
        }
        if (mousePressed) {
            state = Constants.BUTTON_STATE_PRESSED;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[state], xPos, yPos, width, height, null);
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
