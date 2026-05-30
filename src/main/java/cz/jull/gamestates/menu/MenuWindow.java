package cz.jull.gamestates.menu;

import javax.swing.*;
import java.awt.*;

/**
 * The JFrame window that houses the main menu panel.
 */
public class MenuWindow {

    private JFrame jframe;

    /**
     * Constructs the MenuWindow and sets it to full screen.
     *
     * @param menuPanel The JPanel containing the menu content.
     */
    public MenuWindow(MenuPanel menuPanel) {
        this.jframe = new JFrame();

        this.jframe.setUndecorated(true);
        this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.jframe.add(menuPanel);

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
