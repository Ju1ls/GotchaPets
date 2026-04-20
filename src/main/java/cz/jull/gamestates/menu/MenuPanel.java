package cz.jull.gamestates.menu;

import cz.jull.Game;
import cz.jull.inputs.MenuInputs;

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
        Dimension size = new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT);
        setPreferredSize(size);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        float scaleX = (float) getWidth() / Game.GAME_WIDTH;
        float scaleY = (float) getHeight() / Game.GAME_HEIGHT;
        g2d.scale(scaleX, scaleY);
        game.render(g2d);
    }

    public Game getGame() {
        return game;
    }
}
