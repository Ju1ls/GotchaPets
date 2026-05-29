package cz.jull.gamestates.gacha;

import cz.jull.Game;
import cz.jull.inputs.GachaInputs;
import cz.jull.utils.Constants;

import javax.swing.*;
import java.awt.*;

public class GachaPanel extends JPanel {
    private Game game;

    public GachaPanel(Game game) {
        this.game = game;

        setPanelSize();

        GachaInputs gachaInputs = new GachaInputs(this, game.getGacha());
        addMouseListener(gachaInputs);
        addMouseMotionListener(gachaInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        setPreferredSize(size);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        float scaleX = (float) getWidth() / Constants.GAME_WIDTH;
        float scaleY = (float) getHeight() / Constants.GAME_HEIGHT;
        g2d.scale(scaleX, scaleY);

        game.getGacha().draw(g2d);
    }
}
