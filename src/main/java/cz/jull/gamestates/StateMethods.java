package cz.jull.gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface StateMethods {
    void update();
    void draw(Graphics g);

    void mouseClicked(MouseEvent e);
    void mousePressed(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void mouseMoved(MouseEvent e);

    void KeyPressed(KeyEvent e);
    void KeyReleased(KeyEvent e);
}
