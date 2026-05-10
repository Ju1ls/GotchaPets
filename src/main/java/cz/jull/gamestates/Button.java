package cz.jull.gamestates;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Button {
    private int xPos;
    private int yPos;
    private int width;
    private int height;

    private BufferedImage[] imgs;

    private int state = 0; // 0 = Default, 1 = Hover, 2 = Pressed
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
        state = 0; // Default
        if (mouseOver) {
            state = 1; // Hover
        }
        if (mousePressed) {
            state = 2; // Pressed
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
