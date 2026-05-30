package cz.jull.gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Defines the standard lifecycle and input methods required by all game states.
 */
public interface StateMethods {

    /**
     * Updates the logic of the game state.
     * Called once per tick.
     */
    void update();

    /**
     * Renders the game state to the screen.
     *
     * @param g The Graphics object used for drawing.
     */
    void draw(Graphics g);

    /**
     * Handles mouse click events.
     *
     * @param e The MouseEvent triggered.
     */
    void mouseClicked(MouseEvent e);

    /**
     * Handles mouse press events.
     *
     * @param e The MouseEvent triggered.
     */
    void mousePressed(MouseEvent e);

    /**
     * Handles mouse release events.
     *
     * @param e The MouseEvent triggered.
     */
    void mouseReleased(MouseEvent e);

    /**
     * Handles mouse movement events.
     *
     * @param e The MouseEvent triggered.
     */
    void mouseMoved(MouseEvent e);

    /**
     * Handles key press events.
     *
     * @param e The KeyEvent triggered.
     */
    void KeyPressed(KeyEvent e);

    /**
     * Handles key release events.
     *
     * @param e The KeyEvent triggered.
     */
    void KeyReleased(KeyEvent e);
}
