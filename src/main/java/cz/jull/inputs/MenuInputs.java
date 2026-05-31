package cz.jull.inputs;

import cz.jull.gamestates.menu.MenuState;
import cz.jull.gamestates.menu.MenuPanel;
import cz.jull.utils.InputUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Listens for mouse events on the MenuPanel, scales the event coordinates,
 * and delegates them to the MenuState.
 */
public class MenuInputs extends MouseAdapter {
    private final MenuPanel panel;
    private final MenuState menu;

    /**
     * Constructs the MenuInputs adapter.
     *
     * @param panel The MenuPanel where events originate.
     * @param menu  The MenuState to which events are delegated.
     */
    public MenuInputs(MenuPanel panel, MenuState menu) {
        this.panel = panel;
        this.menu = menu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    /**
     * {@inheritDoc}
     * <p>Scales the coordinates of the press event and delegates it to the MenuState.</p>
     */
    @Override
    public void mousePressed(MouseEvent e) {
        menu.mousePressed(InputUtils.scaleEvent(panel, e));
    }

    /**
     * {@inheritDoc}
     * <p>Scales the coordinates of the release event and delegates it to the MenuState.</p>
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        menu.mouseReleased(InputUtils.scaleEvent(panel, e));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);
    }

    /**
     * {@inheritDoc}
     * <p>Scales the coordinates of the drag event and delegates it as a move event to the MenuState.</p>
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        menu.mouseMoved(InputUtils.scaleEvent(panel, e));
    }

    /**
     * {@inheritDoc}
     * <p>Scales the coordinates of the movement event and delegates it to the MenuState.</p>
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        menu.mouseMoved(InputUtils.scaleEvent(panel, e));
    }
}
