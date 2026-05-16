package cz.jull.gamestates.playing;

import cz.jull.Game;
import cz.jull.inputs.PlayInputs;
import cz.jull.utils.Constants;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {
    private Game game;

    public PlayPanel(Game game) {
        this.game = game;
        setPanelSize();

        PlayInputs playInputs = new PlayInputs(this, game.getPlay());
        addMouseListener(playInputs);
        addMouseMotionListener(playInputs);
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

        if (game.getPlay() != null) {
            game.getPlay().draw(g2d);
        }
    }

    public Game getGame() {
        return game;
    }
}
