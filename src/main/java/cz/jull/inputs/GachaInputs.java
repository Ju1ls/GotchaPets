package cz.jull.inputs;

import cz.jull.gamestates.gacha.GachaPanel;
import cz.jull.gamestates.gacha.GachaState;
import cz.jull.utils.InputUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Listens for mouse events on the GachaPanel, scales the event coordinates,
 * and delegates them to the GachaState.
 */
public class GachaInputs extends MouseAdapter {
    private GachaPanel panel;
    private GachaState gacha;

    /**
     * Constructs the GachaInputs adapter.
     *
     * @param panel The GachaPanel where events originate.
     * @param gacha The GachaState to which events are delegated.
     */
    public GachaInputs(GachaPanel panel, GachaState gacha) {
        this.panel = panel;
        this.gacha = gacha;
    }

    /**
     * {@inheritDoc}
     * <p>Scales the coordinates of the press event and delegates it to the GachaState.</p>
     */
    @Override
    public void mousePressed(MouseEvent e) {
        gacha.mousePressed(InputUtils.scaleEvent(panel, e));
    }

    /**
     * {@inheritDoc}
     * <p>Scales the coordinates of the release event and delegates it to the GachaState.</p>
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        gacha.mouseReleased(InputUtils.scaleEvent(panel, e));
    }

    /**
     * {@inheritDoc}
     * <p>Scales the coordinates of the movement event and delegates it to the GachaState.</p>
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        gacha.mouseMoved(InputUtils.scaleEvent(panel, e));
    }

    /**
     * {@inheritDoc}
     * <p>Scales the coordinates of the drag event and delegates it as a move event to the GachaState.</p>
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        gacha.mouseMoved(InputUtils.scaleEvent(panel, e));
    }
}
