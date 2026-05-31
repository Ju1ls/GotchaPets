package cz.jull.inputs;

import cz.jull.gamestates.pet_list.PetListPanel;
import cz.jull.gamestates.pet_list.PetListState;
import cz.jull.utils.InputUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Listens for mouse events on the PetListPanel, scales the event coordinates,
 * and delegates them to the PetListState.
 */
public class PetListInputs extends MouseAdapter {
    private final PetListPanel panel;
    private final PetListState petList;

    /**
     * Constructs the PetListInputs adapter.
     *
     * @param panel   The PetListPanel where events originate.
     * @param petList The PetListState to which events are delegated.
     */
    public PetListInputs(PetListPanel panel, PetListState petList) {
        this.panel = panel;
        this.petList = petList;
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
     * <p>Scales the coordinates of the press event and delegates it to the PetListState.</p>
     */
    @Override
    public void mousePressed(MouseEvent e) {
        petList.mousePressed(InputUtils.scaleEvent(panel, e));
    }

    /**
     * {@inheritDoc}
     * <p>Scales the coordinates of the release event and delegates it to the PetListState.</p>
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        petList.mouseReleased(InputUtils.scaleEvent(panel, e));
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
     * <p>Scales the coordinates of the drag event and delegates it as a move event to the PetListState.</p>
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        petList.mouseMoved(InputUtils.scaleEvent(panel, e));
    }

    /**
     * {@inheritDoc}
     * <p>Scales the coordinates of the movement event and delegates it to the PetListState.</p>
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        petList.mouseMoved(InputUtils.scaleEvent(panel, e));
    }
}
