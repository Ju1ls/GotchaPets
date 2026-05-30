package cz.jull.gamestates.playing;

import cz.jull.Game;
import cz.jull.inputs.PlayInputs;
import cz.jull.utils.Constants;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

/**
 * The JPanel responsible for rendering the play state and listening to play inputs.
 */
@Getter
@Setter
public class PlayPanel extends JPanel {
    private Game game;

    /**
     * Constructs the PlayPanel.
     *
     * @param game The main game instance.
     */
    public PlayPanel(Game game) {
        this.game = game;
        setPanelSize();

        PlayInputs playInputs = new PlayInputs(this, game.getPlay());
        addMouseListener(playInputs);
        addMouseMotionListener(playInputs);
    }

    /**
     * Sets the preferred size of the panel based on constants.
     */
    private void setPanelSize() {
        Dimension size = new Dimension(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        setPreferredSize(size);
    }

    /**
     * Overrides the default Swing painting to render the play state.
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

        if (game.getPlay() != null) {
            game.getPlay().draw(g2d);
        }
    }
}
