package cz.jull.gamestates.playing;

import javax.swing.*;
import java.awt.*;

/**
 * The JFrame window that houses the main gameplay panel.
 */
public class PlayWindow {
    private final JFrame jframe;

    /**
     * Constructs the PlayWindow and sets it to full screen.
     *
     * @param playPanel The JPanel containing the gameplay content.
     */
    public PlayWindow(PlayPanel playPanel) {
        this.jframe = new JFrame();

        this.jframe.setUndecorated(true);
        this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.jframe.add(playPanel);

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
