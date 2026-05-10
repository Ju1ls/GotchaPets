package cz.jull.inputs;

import cz.jull.gamestates.playing.PlayPanel;
import cz.jull.gamestates.playing.PlayState;
import cz.jull.utilz.InputUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayInputs extends MouseAdapter {
    private PlayPanel panel;
    private PlayState play;

    public PlayInputs(PlayPanel panel, PlayState play) {
        this.panel = panel;
        this.play = play;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        play.mousePressed(InputUtils.scaleEvent(panel, e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        play.mouseReleased(InputUtils.scaleEvent(panel, e));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        play.mouseMoved(InputUtils.scaleEvent(panel, e));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        play.mouseMoved(InputUtils.scaleEvent(panel, e));
    }
}
