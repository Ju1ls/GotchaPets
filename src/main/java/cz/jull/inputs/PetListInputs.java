package cz.jull.inputs;

import cz.jull.gamestates.pet_list.PetListPanel;
import cz.jull.gamestates.pet_list.PetListState;
import cz.jull.utils.InputUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class PetListInputs extends MouseAdapter {
    private PetListPanel panel;
    private PetListState petList;

    public PetListInputs(PetListPanel panel, PetListState petList) {
        this.panel = panel;
        this.petList = petList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        petList.mousePressed(InputUtils.scaleEvent(panel, e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        petList.mouseReleased(InputUtils.scaleEvent(panel, e));
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
        petList.mouseMoved(InputUtils.scaleEvent(panel, e));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        petList.mouseMoved(InputUtils.scaleEvent(panel, e));
    }
}
