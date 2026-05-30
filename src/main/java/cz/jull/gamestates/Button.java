package cz.jull.gamestates;

import cz.jull.utils.Constants;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents an interactive graphical button in the user interface.
 * Handles its own bounding box, visual state (default, hover, pressed), and rendering.
 */
public class Button {
    private int xPos;
    private int yPos;
    private int width;
    private int height;

    private BufferedImage[] imgs;

    private int state = Constants.BUTTON_STATE_DEFAULT;
    @Setter
    @Getter
    private boolean mouseOver;
    @Setter
    @Getter
    private boolean mousePressed;
    @Getter
    private Rectangle bounds;

    /**
     * Constructs a Button.
     *
     * @param xPos   The X coordinate of the button's top-left corner.
     * @param yPos   The Y coordinate of the button's top-left corner.
     * @param width  The width of the button.
     * @param height The height of the button.
     * @param imgs   An array of BufferedImages representing different button states.
     */
    public Button(int xPos, int yPos, int width, int height, BufferedImage[] imgs) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.imgs = imgs;
        initBounds();
    }

    /**
     * Initializes the collision bounding box based on the button's dimensions and position.
     */
    private void initBounds() {
        bounds = new Rectangle(xPos, yPos, width, height);
    }

    /**
     * Updates the internal state of the button based on mouse interactions.
     */
    public void update() {
        state = Constants.BUTTON_STATE_DEFAULT;
        if (mouseOver) {
            state = Constants.BUTTON_STATE_HOVER;
        }
        if (mousePressed) {
            state = Constants.BUTTON_STATE_PRESSED;
        }
    }

    /**
     * Draws the button to the screen using the image corresponding to its current state.
     *
     * @param g The Graphics object used for drawing.
     */
    public void draw(Graphics g) {
        g.drawImage(imgs[state], xPos, yPos, width, height, null);
    }

}
