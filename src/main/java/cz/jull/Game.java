package cz.jull;

import cz.jull.gamestates.menu.MenuState;
import cz.jull.gamestates.menu.MenuPanel;
import cz.jull.gamestates.menu.MenuWindow;
import cz.jull.gamestates.playing.PlayPanel;
import cz.jull.gamestates.playing.PlayState;
import cz.jull.gamestates.playing.PlayWindow;
import cz.jull.logic.Player;

public class Game implements Runnable{
    private Player player;

    private MenuWindow menuWindow;
    private MenuPanel menuPanel;
    private MenuState menu;

    private PlayWindow playWindow;
    private PlayPanel playPanel;
    private PlayState play;

    private Thread gameThread;

    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private boolean isPlaying = false;

    public Game() {
        player = new Player();

        menu = new MenuState(this);
        menuPanel = new MenuPanel(this);
        menuWindow = new MenuWindow(menuPanel);

        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void startGame() {
        menuWindow.closeWindow();

        play = new PlayState(this);
        playPanel = new PlayPanel(this);
        playWindow = new PlayWindow(playPanel);

        isPlaying = true;
    }

    public void returnToMenu() {
        if (playWindow != null) {
            playWindow.closeWindow();
        }

        menuWindow = new MenuWindow(menuPanel);
        isPlaying = false;
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
                if (!isPlaying && menuPanel != null) {
                    menuPanel.repaint();
                } else if (isPlaying && playPanel != null) {
                    playPanel.repaint();
                }
                deltaF--;
            }
        }
    }

    public void update() {
        if (!isPlaying) {
            menu.update();
        } else if (play != null) {
            play.update();
        }
    }

    public MenuState getMenu() {
        return menu;
    }

    public PlayState getPlay() {
        return play;
    }

    public Player getPlayer() {
        return player;
    }
}
