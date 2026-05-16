package cz.jull.gamestates.menu;

import cz.jull.Game;
import cz.jull.inputs.MenuInputs;
import cz.jull.utils.Constants;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private Game game;

    public MenuPanel(Game game) {
        this.game = game;
        setPanelSize();

        MenuInputs menuInputs = new MenuInputs(this, game.getMenu());
        addMouseListener(menuInputs);
        addMouseMotionListener(menuInputs);
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

        if (game.getMenu() != null) {
            game.getMenu().draw(g2d);
        }
    }

    public Game getGame() {
        return game;
    }
}
