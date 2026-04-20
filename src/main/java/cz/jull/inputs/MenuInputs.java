package cz.jull.inputs;

import cz.jull.gamestates.menu.Menu;
import cz.jull.gamestates.menu.MenuPanel;
import cz.jull.utilz.InputUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MenuInputs extends MouseAdapter {

    private MenuPanel panel;
    private Menu menu;

    public MenuInputs(MenuPanel panel, Menu menu) {
        this.panel = panel;
        this.menu = menu;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        MouseEvent scaledEvent = InputUtils.scaleEvent(panel, e);
        menu.mousePressed(scaledEvent);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        MouseEvent scaledEvent = InputUtils.scaleEvent(panel, e);
        menu.mouseReleased(scaledEvent);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        MouseEvent scaledEvent = InputUtils.scaleEvent(panel, e);
        menu.mouseMoved(scaledEvent);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        MouseEvent scaledEvent = InputUtils.scaleEvent(panel, e);
        menu.mouseMoved(scaledEvent);
    }
}
