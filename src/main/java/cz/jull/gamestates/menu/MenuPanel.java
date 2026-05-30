package cz.jull.gamestates.menu;

import cz.jull.Game;
import cz.jull.inputs.MenuInputs;
import cz.jull.utils.Constants;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

/**
 * The JPanel responsible for rendering the main menu screen and listening to inputs.
 */
@Getter
@Setter
public class MenuPanel extends JPanel {
    private Game game;

    /**
     * Constructs the MenuPanel.
     *
     * @param game The main game instance.
     */
    public MenuPanel(Game game) {
        this.game = game;
        setPanelSize();

        MenuInputs menuInputs = new MenuInputs(this, game.getMenu());
        addMouseListener(menuInputs);
        addMouseMotionListener(menuInputs);
    }

    /**
     * Sets the preferred size of the panel based on constants.
     */
    private void setPanelSize() {
        Dimension size = new Dimension(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        setPreferredSize(size);
    }

    /**
     * Overrides the default Swing painting to render the menu state.
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

        if (game.getMenu() != null) {
            game.getMenu().draw(g2d);
        }
    }
}
