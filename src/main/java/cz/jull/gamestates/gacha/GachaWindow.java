package cz.jull.gamestates.gacha;

import javax.swing.*;
import java.awt.*;

/**
 * The JFrame window that houses the gacha panel.
 */
public class GachaWindow {

    private final JFrame jframe;

    /**
     * Constructs the GachaWindow and sets it to full screen.
     *
     * @param gachaPanel The JPanel containing the gacha content.
     */
    public GachaWindow(GachaPanel gachaPanel) {
        this.jframe = new JFrame();

        this.jframe.setUndecorated(true);
        this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.jframe.add(gachaPanel);

        this.jframe.setLocationRelativeTo(null);
        this.jframe.setResizable(false);

        jframe.pack();

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        device.setFullScreenWindow(jframe);

        this.jframe.setVisible(true);
    }

    /**
     * Closes and disposes of the window.
     */
    public void closeWindow() {
        jframe.dispose();
    }
}
