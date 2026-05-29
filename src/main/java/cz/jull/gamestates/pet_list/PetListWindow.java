package cz.jull.gamestates.pet_list;

import javax.swing.*;
import java.awt.*;

public class PetListWindow {

    private JFrame jframe;

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

    public void closeWindow() {
        jframe.dispose();
    }
}

