package cz.jull.gamestates.gacha;

import cz.jull.gamestates.menu.MenuPanel;

import javax.swing.*;
import java.awt.*;

public class GachaWindow {

    private JFrame jframe;

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

    public void closeWindow() {
        jframe.dispose();
    }
}
