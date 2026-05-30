package cz.jull.gamestates.pet_list;

import javax.swing.*;
import java.awt.*;

/**
 * The JFrame window that houses the pet list (inventory) panel.
 */
public class PetListWindow {

    private JFrame jframe;

    /**
     * Constructs the PetListWindow and sets it to full screen.
     *
     * @param petListPanel The JPanel containing the inventory content.
     */
    public PetListWindow(PetListPanel petListPanel) {
        this.jframe = new JFrame();

        this.jframe.setUndecorated(true);
        this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.jframe.add(petListPanel);

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

