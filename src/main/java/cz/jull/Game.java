package cz.jull;

import cz.jull.gamestates.menu.Menu;
import cz.jull.gamestates.menu.MenuPanel;
import cz.jull.gamestates.menu.MenuWindow;

import java.awt.*;

public class Game implements Runnable{

    private MenuWindow menuWindow;
    private MenuPanel menuPanel;
    private Menu menu;

    private Thread gameThread;

    public static final int GAME_WIDTH = 1600;
    public static final int GAME_HEIGHT = 900;
    public static final float SCALE = 0.9f;

    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    public Game() {
        menu = new Menu(this);
        menuPanel = new MenuPanel(this);
        menuWindow = new MenuWindow(menuPanel);

        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                deltaU--;
            }

            if (deltaF >= 1) {
                menuPanel.repaint();
                deltaF--;
            }
        }
    }

    public void update() {
        menu.update();
    }

    public void render(Graphics g) {
        menu.draw(g);
    }

    public Menu getMenu() {
        return menu;
    }
}
