package cz.jull.gamestates.playing;

import cz.jull.Game;
import cz.jull.gamestates.State;
import cz.jull.gamestates.StateMethods;
import cz.jull.gamestates.Button;
import cz.jull.logic.pet.Pet;
import cz.jull.utilz.Constants;
import cz.jull.utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PlayState extends State implements StateMethods {
    // Buttons
    private Button backButton;
    private Button gachaButton;
    private Button listButton;
    private Button feedButton;
    private Button sleepButton;
    private Button loveButton;

    private Button[] buttons;

    // Pet Stats
    private BufferedImage[][] statSegments = new BufferedImage[5][3];
    private BufferedImage statBackground;
    private BufferedImage statForeground;

    // Backgrounds
    private BufferedImage homeBgImg;
    private BufferedImage waterBgImg;
    private BufferedImage forestBgImg;

    private int tickCount = 0;
    private final int DECAY_RATE_TICKS = 1000;

    public PlayState(Game game) {
        super(game);

        loadBackgrounds();
        loadStatsImages();
        loadButtons();
    }

    private void loadBackgrounds() {
        homeBgImg = LoadSave.getSpriteAtlas(LoadSave.CREDITS_BACKGROUND);
    }

    private void loadStatsImages() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PET_HEALTH_STATES);

        int fullBarWidth = Constants.PET_STATE_WIDTH;
        int barHeight = Constants.B_DEFAULT_HEIGHT;

        int[] segmentWidths = {140, 135, 140};
        int[] segmentXOffsets = {0, 140, 275};

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                statSegments[i][j] = img.getSubimage(segmentXOffsets[j], i * barHeight, segmentWidths[j], barHeight);
            }
        }

        statBackground = img.getSubimage(0, 5 * barHeight, fullBarWidth, barHeight);
        statForeground = img.getSubimage(fullBarWidth, 0, fullBarWidth, barHeight);
    }

    private void loadButtons() {
        BufferedImage backButtonSprite = LoadSave.getSpriteAtlas(LoadSave.BACK_BUTTON);
        BufferedImage playButtonsSprite = LoadSave.getSpriteAtlas(LoadSave.GAME_BUTTONS);

        int backButtonWidth = Constants.B_WIDTH_BACK;
        int backButtonHeight = Constants.B_DEFAULT_HEIGHT;

        BufferedImage[] backImgs = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            backImgs[i] = backButtonSprite.getSubimage(i * backButtonWidth, 0, backButtonWidth, backButtonHeight);
        }

        int playButtonWidth = Constants.B_WIDTH_GAME;
        int playButtonHeight = Constants.B_DEFAULT_HEIGHT;

        BufferedImage[][] playImgs = new BufferedImage[5][3];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                playImgs[i][j] = playButtonsSprite.getSubimage(j * playButtonWidth, i * playButtonHeight, playButtonWidth, playButtonHeight);
            }
        }

        backButton = new Button(20, 20, backButtonWidth, backButtonHeight, backImgs);
        gachaButton = new Button(390, 20, playButtonWidth, playButtonHeight, playImgs[1]);
        listButton = new Button(620, 20, playButtonWidth, playButtonHeight, playImgs[0]);

        int rightX = Constants.GAME_WIDTH - 230;

        feedButton = new Button(rightX, Constants.GAME_HEIGHT - 660, playButtonWidth, playButtonHeight, playImgs[4]);
        sleepButton = new Button(rightX, Constants.GAME_HEIGHT - 495, playButtonWidth, playButtonHeight, playImgs[3]);
        loveButton = new Button(rightX, Constants.GAME_HEIGHT - 330, playButtonWidth, playButtonHeight, playImgs[2]);

        buttons = new Button[] {backButton, gachaButton, listButton, feedButton, sleepButton, loveButton};
    }

    @Override
    public void update() {
        for (Button button : buttons) {
            if (button != null) button.update();
        }

        tickCount++;
        if (tickCount >= DECAY_RATE_TICKS) {
            tickCount = 0;
            if (game.getPlayer().getEquippedPet() != null) {
                game.getPlayer().getEquippedPet().decayStats();
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        Pet currentPet = game.getPlayer().getEquippedPet();

        // Background
        if (currentPet != null) {
            switch (currentPet.getType()) {
                case HOME -> g.drawImage(homeBgImg, 0,0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, null);
                case WATER -> g.drawImage(waterBgImg, 0,0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, null);
                case FOREST -> g.drawImage(forestBgImg, 0,0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, null);
                default -> {
                    g.setColor(new Color(89, 198, 255));
                    g.fillRect(0,0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
                }
            }
        }

        // Buttons
        for (Button button : buttons) {
            if (button != null) button.draw(g);
        }

        // Currency
        // will change later (graphic designer didn't give me the png)
        g.setColor(Color.PINK);
        g.fillRoundRect(Constants.GAME_WIDTH - 320, 20, 300, 60, 30, 30);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(game.getPlayer().getCoins() + " Coins", Constants.GAME_WIDTH - 280, 65);

        // Pet Stats
        if (currentPet != null) {
            drawPetStatsBar(g);
        }
    }

    private void drawPetStatsBar(Graphics g) {
        Pet currentPet = game.getPlayer().getEquippedPet();

        if (currentPet == null) {
            return;
        }

        int drawWidth = 415;
        int drawHeight = 145;

        int[] segWidths = {140, 135, 140};
        int[] segXOffsets = {0, 140, 275};

        // Background
        g.drawImage(statBackground, 1165, 735, drawWidth, drawHeight, null);

        // Stats
        int hunger = getColorRow(currentPet.getHunger());
        g.drawImage(statSegments[hunger][0], 1165 + segXOffsets[0], 735, segWidths[0], drawHeight, null);

        int energy = getColorRow(currentPet.getEnergy());
        g.drawImage(statSegments[energy][1], 1165 + segXOffsets[1], 735, segWidths[1], drawHeight, null);

        int love = getColorRow(currentPet.getLove());
        g.drawImage(statSegments[love][2], 1165 + segXOffsets[2], 735, segWidths[2], drawHeight, null);

        // Foreground
        g.drawImage(statForeground, 1165, 735, drawWidth, drawHeight, null);
    }

    private int getColorRow(int statValue) {
        if (statValue >= 81) {
            return 0; // Green
        }
        if (statValue >= 61) {
            return 1; // Light Green
        }
        if (statValue >= 41) {
            return 2; // Yellow
        }
        if (statValue >= 21) {
            return 3; // Orange
        }
        return 4;     // Red
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (Button button : buttons) {
                if (button != null && button.isMouseOver()) {
                    button.setMousePressed(true);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (backButton.isMouseOver() && backButton.isMousePressed()) {
                game.returnToMenu();
            }

            Pet currentPet = game.getPlayer().getEquippedPet();

            if (currentPet != null) {
                int rewardAmount = 5;

                if (feedButton.isMouseOver() && feedButton.isMousePressed()) {
                    if (currentPet.getHunger() <= 80) {
                        currentPet.feed();
                        game.getPlayer().addCoins(rewardAmount);
                    } else {
                        System.out.println("pet is full"); // add sound effect prob.
                    }
                }

                if (sleepButton.isMouseOver() && sleepButton.isMousePressed()) {
                    if (currentPet.getEnergy() <= 80) {
                        currentPet.sleep();
                        game.getPlayer().addCoins(rewardAmount);
                    } else {
                        System.out.println("pet aint tired"); // add sound effect prob.
                    }
                }

                if (loveButton.isMouseOver() && loveButton.isMousePressed()) {
                    if (currentPet.getLove() <= 80) {
                        currentPet.love();
                        game.getPlayer().addCoins(rewardAmount);
                    } else {
                        System.out.println("the pet is loved"); // add sound effect prob.
                    }
                }
            }

            if (gachaButton.isMouseOver() && gachaButton.isMousePressed()) {
                // Open Gacha
            }

            if (listButton.isMouseOver() && listButton.isMousePressed()) {
                // Open Pet List
            }

            for (Button button : buttons) {
                if (button != null) button.setMousePressed(false);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (Button button : buttons) {
            if (button != null) button.setMouseOver(false);
        }

        for (Button button : buttons) {
            if (button != null && button.getBounds().contains(e.getX(), e.getY())) {
                button.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void KeyPressed(KeyEvent e) {

    }

    @Override
    public void KeyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
