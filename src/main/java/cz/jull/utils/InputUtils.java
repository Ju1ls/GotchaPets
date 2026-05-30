package cz.jull.utils;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A utility class designed to translate raw OS mouse coordinates to the game's internal coordinate
 * resolution when window scaling is applied via Graphics2D.
 */
public class InputUtils {
    /**
     * Translates the X and Y coordinates of a MouseEvent based on the ratio between
     * the actual window size and the intended internal game resolution.
     *
     * @param panel The active Swing Component/Panel that received the event.
     * @param e     The original raw MouseEvent.
     * @return A new MouseEvent with the modified (scaled) X and Y integer coordinates.
     */
    public static MouseEvent scaleEvent(Component panel, MouseEvent e) {
        float scaleX = (float) panel.getWidth() / Constants.GAME_WIDTH;
        float scaleY = (float) panel.getHeight() / Constants.GAME_HEIGHT;

        int realX = (int) (e.getX() / scaleX);
        int realY = (int) (e.getY() / scaleY);

        return new MouseEvent(
                e.getComponent(), e.getID(), e.getWhen(),
                e.getModifiersEx(), realX, realY,
                e.getClickCount(), e.isPopupTrigger(),
                e.getButton()
        );
    }
}
