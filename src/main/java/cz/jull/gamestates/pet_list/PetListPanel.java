package cz.jull.gamestates.pet_list;

import cz.jull.Game;
import cz.jull.inputs.MenuInputs;
import cz.jull.inputs.PetListInputs;
import cz.jull.utils.Constants;

import javax.swing.*;
import java.awt.*;

public class PetListPanel extends JPanel {
    private Game game;

    public PetListPanel(Game game) {
        this.game = game;
        setPanelSize();

        PetListInputs petListInputs = new PetListInputs(this, game.getPetList());
        addMouseListener(petListInputs);
        addMouseMotionListener(petListInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        setPreferredSize(size);
    }

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

    public Game getGame() {
        return game;
    }
}
