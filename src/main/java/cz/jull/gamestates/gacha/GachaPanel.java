package cz.jull.gamestates.gacha;

import cz.jull.Game;
import cz.jull.inputs.GachaInputs;
import cz.jull.utils.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * The JPanel responsible for rendering the gacha screen and listening to gacha inputs.
 */
public class GachaPanel extends JPanel {
    private Game game;

    /**
     * Constructs the GachaPanel.
     *
     * @param game The main game instance.
     */
    public GachaPanel(Game game) {
        this.game = game;

        setPanelSize();

        GachaInputs gachaInputs = new GachaInputs(this, game.getGacha());
        addMouseListener(gachaInputs);
        addMouseMotionListener(gachaInputs);
    }

    /**
     * Sets the preferred size of the panel based on constants.
     */
    private void setPanelSize() {
        Dimension size = new Dimension(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        setPreferredSize(size);
    }

    /**
     * Overrides the default Swing painting to render the gacha state.
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

        game.getGacha().draw(g2d);
    }
}
