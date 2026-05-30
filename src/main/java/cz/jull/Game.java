package cz.jull;

import cz.jull.gamestates.GameState;
import cz.jull.gamestates.gacha.GachaPanel;
import cz.jull.gamestates.gacha.GachaState;
import cz.jull.gamestates.gacha.GachaWindow;
import cz.jull.gamestates.menu.MenuState;
import cz.jull.gamestates.menu.MenuPanel;
import cz.jull.gamestates.menu.MenuWindow;
import cz.jull.gamestates.pet_list.PetListPanel;
import cz.jull.gamestates.pet_list.PetListState;
import cz.jull.gamestates.pet_list.PetListWindow;
import cz.jull.gamestates.playing.PlayPanel;
import cz.jull.gamestates.playing.PlayState;
import cz.jull.gamestates.playing.PlayWindow;
import cz.jull.logic.Player;
import cz.jull.logic.pet.Pet;
import cz.jull.utils.PetLoader;
import cz.jull.utils.SaveManager;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The core engine controller for the application.
 * Manages the main game loop, tracking the active {@link GameState}, and handles the transition
 * (swapping Windows/Panels) between different screens. It also retains central references
 * to the global Pet Catalog and the Player profile.
 */
@Getter
@Setter
public class Game implements Runnable{
    private Player player;

    private MenuWindow menuWindow;
    private MenuPanel menuPanel;
    private MenuState menu;

    private PlayWindow playWindow;
    private PlayPanel playPanel;
    private PlayState play;

    private GachaWindow gachaWindow;
    private GachaPanel gachaPanel;
    private GachaState gacha;

    private PetListWindow petListWindow;
    private PetListPanel petListPanel;
    private PetListState petList;

    private List<Pet> petCatalog;

    private Thread gameThread;

    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private GameState gameState = GameState.MENU;

    /**
     * Initializes the central Game engine, loads the pet configurations, retrieves the local
     * save file (or generates a new player profile), and boots into the main menu state.
     */
    public Game() {
        petCatalog = PetLoader.loadPetsFromJson("pets.json");

        player = SaveManager.loadGame();

        if (player == null) {
            player = new Player();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            SaveManager.saveGame(player);
        }));

        menu = new MenuState(this);
        menuPanel = new MenuPanel(this);
        menuWindow = new MenuWindow(menuPanel);

        startGameLoop();
    }

    /**
     * Instantiates and starts the main Runnable thread executing the game loop.
     */
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Transitions the application from the Menu into the active Play state.
     */
    public void startGame() {
        menuWindow.closeWindow();

        play = new PlayState(this);
        playPanel = new PlayPanel(this);
        playWindow = new PlayWindow(playPanel);

        gameState = GameState.PLAYING;
    }

    /**
     * Transitions the application into the Gacha rolling state.
     */
    public void startGacha() {
        playWindow.closeWindow();

        gacha = new GachaState(this);
        gachaPanel = new GachaPanel(this);
        gachaWindow = new GachaWindow(gachaPanel);

        gameState = GameState.GACHA;
    }

    /**
     * Transitions the application into the Pet List (inventory) viewing state.
     */
    public void startPetList() {
        playWindow.closeWindow();

        petList = new PetListState(this);
        petListPanel = new PetListPanel(this);
        petListWindow = new PetListWindow(petListPanel);

        gameState = GameState.PET_LIST;
    }

    /**
     * Dismantles active substates and returns the application back to the Main Menu.
     */
    public void returnToMenu() {
        if (playWindow != null) {
            playWindow.closeWindow();
        }

        menuWindow = new MenuWindow(menuPanel);
        gameState = GameState.MENU;
    }

    /**
     * Dismantles sub-menus (like Gacha or Pet List) and returns to the primary Play state.
     */
    public void returnToPlaying() {
        if (gachaWindow != null) {
            gachaWindow.closeWindow();
            gachaWindow = null;
        }

        if (petListWindow != null) {
            petListWindow.closeWindow();
            petListWindow = null;
        }

        playWindow = new PlayWindow(playPanel);
        gameState = GameState.PLAYING;
    }

    /**
     * The core application tick loop. Ensures deterministic logic updates bounded by {@code UPS_SET}
     * and triggers UI repaints bounded by {@code FPS_SET}.
     */
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
                switch (gameState) {
                    case MENU -> {
                        if (menuPanel != null) {
                            menuPanel.repaint();
                        }
                    }
                    case PLAYING -> {
                        if (playPanel != null) {
                            playPanel.repaint();
                        }
                    }
                    case GACHA -> {
                        if (gachaPanel != null) {
                            gachaPanel.repaint();
                        }
                    }
                    case PET_LIST -> {
                        if (petListPanel != null) {
                            petListPanel.repaint();
                        }
                    }
                }
                deltaF--;
            }
        }
    }

    /**
     * Routes the logic tick payload to the currently active GameState object.
     */
    public void update() {
        switch (gameState) {
            case MENU -> {
                if (menu != null) {
                    menu.update();
                }
            }
            case PLAYING -> {
                if (play != null) {
                    play.update();
                }
            }
            case GACHA -> {
                if (gacha != null) {
                    gacha.update();
                }
            }
            case PET_LIST -> {
                if (petList != null) {
                    petList.update();
                }
            }
        }
    }
}
