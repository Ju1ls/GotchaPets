package cz.jull.inputs;

import cz.jull.gamestates.menu.MenuState;
import cz.jull.gamestates.menu.MenuPanel;
import cz.jull.utils.InputUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MenuInputs extends MouseAdapter {

    private MenuPanel panel;
    private MenuState menu;

    public MenuInputs(MenuPanel panel, MenuState menu) {
        this.panel = panel;
        this.menu = menu;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        menu.mousePressed(InputUtils.scaleEvent(panel, e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        menu.mouseReleased(InputUtils.scaleEvent(panel, e));
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
        menu.mouseMoved(InputUtils.scaleEvent(panel, e));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menu.mouseMoved(InputUtils.scaleEvent(panel, e));
    }
}
