package cz.jull.inputs;

import cz.jull.gamestates.gacha.GachaPanel;
import cz.jull.gamestates.gacha.GachaState;
import cz.jull.utils.InputUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GachaInputs extends MouseAdapter {
    private GachaPanel panel;
    private GachaState gacha;

    public GachaInputs(GachaPanel panel, GachaState gacha) {
        this.panel = panel;
        this.gacha = gacha;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        gacha.mousePressed(InputUtils.scaleEvent(panel, e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gacha.mouseReleased(InputUtils.scaleEvent(panel, e));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gacha.mouseMoved(InputUtils.scaleEvent(panel, e));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        gacha.mouseMoved(InputUtils.scaleEvent(panel, e));
    }
}
