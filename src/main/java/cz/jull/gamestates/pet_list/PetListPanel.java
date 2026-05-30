package cz.jull.gamestates.pet_list;

import cz.jull.Game;
import cz.jull.inputs.PetListInputs;
import cz.jull.utils.Constants;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

/**
 * The JPanel responsible for rendering the pet list screen and listening to inputs.
 */
@Getter
@Setter
public class PetListPanel extends JPanel {
    private Game game;

    /**
     * Constructs the PetListPanel.
     *
     * @param game The main game instance.
     */
    public PetListPanel(Game game) {
        this.game = game;
        setPanelSize();

        PetListInputs petListInputs = new PetListInputs(this, game.getPetList());
        addMouseListener(petListInputs);
        addMouseMotionListener(petListInputs);
    }

    /**
     * Sets the preferred size of the panel based on constants.
     */
    private void setPanelSize() {
        Dimension size = new Dimension(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        setPreferredSize(size);
    }

    /**
     * Overrides the default Swing painting to render the pet list inventory state.
     * Applies scaling to fit the current window size before delegating to the state's draw method.
     *
     * @param g The Graphics context provided by Swing.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        float scaleX = (float) getWidth() / Constants.GAME_WIDTH;
        float scaleY = (float) getHeight() / Constants.GAME_HEIGHT;
        g2d.scale(scaleX, scaleY);

        if (game.getPetList() != null) {
            game.getPetList().draw(g2d);
        }
    }
}
