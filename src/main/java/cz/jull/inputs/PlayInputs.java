package cz.jull.inputs;

import cz.jull.gamestates.playing.PlayPanel;
import cz.jull.gamestates.playing.PlayState;
import cz.jull.utils.InputUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Listens for mouse events on the PlayPanel, scales the event coordinates to match
 * the internal game resolution, and delegates them to the PlayState.
 */
public class PlayInputs extends MouseAdapter {
    private PlayPanel panel;
    private PlayState play;

    /**
     * Constructs the PlayInputs adapter.
     *
     * @param panel The PlayPanel where events originate.
     * @param play  The PlayState to which events are delegated.
     */
    public PlayInputs(PlayPanel panel, PlayState play) {
        this.panel = panel;
        this.play = play;
    }

    /**
     * {@inheritDoc}
     * <p>Scales the coordinates of the press event and delegates it to the PlayState.</p>
     */
    @Override
    public void mousePressed(MouseEvent e) {
        play.mousePressed(InputUtils.scaleEvent(panel, e));
    }

    /**
     * {@inheritDoc}
     * <p>Scales the coordinates of the release event and delegates it to the PlayState.</p>
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        play.mouseReleased(InputUtils.scaleEvent(panel, e));
    }

    /**
     * {@inheritDoc}
     * <p>Scales the coordinates of the movement event and delegates it to the PlayState.</p>
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        play.mouseMoved(InputUtils.scaleEvent(panel, e));
    }

    /**
     * {@inheritDoc}
     * <p>Scales the coordinates of the drag event and delegates it as a move event to the PlayState.</p>
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        play.mouseMoved(InputUtils.scaleEvent(panel, e));
    }
}
