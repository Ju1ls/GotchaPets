package cz.jull.utils;

import java.awt.*;
import java.awt.event.MouseEvent;

public class InputUtils {
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
