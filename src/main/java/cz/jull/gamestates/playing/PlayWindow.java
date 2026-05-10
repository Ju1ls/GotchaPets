package cz.jull.gamestates.playing;

import javax.swing.*;
import java.awt.*;

public class PlayWindow {
    private JFrame jframe;

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

    public void closeWindow() {
        jframe.dispose();
    }
}
