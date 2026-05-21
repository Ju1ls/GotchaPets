package cz.jull.gamestates.playing;

import cz.jull.Game;
import cz.jull.gamestates.State;
import cz.jull.gamestates.StateMethods;
import cz.jull.gamestates.Button;
import cz.jull.logic.pet.Pet;
import cz.jull.logic.pet.PetSpecies;
import cz.jull.logic.pet.PetType;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;
import cz.jull.utils.SaveManager;

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

    private BufferedImage currency;
    private BufferedImage currencyBackground;

    // Pets
    private BufferedImage[] catSprites = new BufferedImage[4];
    private BufferedImage[] dogSprites = new BufferedImage[4];
    private BufferedImage zzzImg;
    private BufferedImage petBedImg;

    // Pet Actions
    private int actionSpriteIndex = Constants.ACTION_IDLE;
    private int actionTimer = 0;

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

        //loadBackgrounds();
        loadStatsImages();
        loadButtons();
        loadCurrencyImg();
        loadPetImages();
    }

    private void loadBackgrounds() {
        homeBgImg = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND);
    }

    private void loadPetImages() {
        BufferedImage spriteSheet = LoadSave.getSpriteAtlas(LoadSave.DEFAULT_PET_SPRITES);
        BufferedImage fullBedCanvas = LoadSave.getSpriteAtlas(LoadSave.PET_BED);
        petBedImg = fullBedCanvas.getSubimage(0, 0, 690, 300);

        int catWidth = Constants.CAT_WIDTH, catHeight = Constants.CAT_HEIGHT;
        for (int i = 0; i < 4; i++) {
            catSprites[i] = spriteSheet.getSubimage(i * catWidth, 0, catWidth, catHeight);
        }

        int dogWidth = Constants.DOG_WIDTH, dogHeight = Constants.DOG_HEIGHT;
        for (int i = 0; i < 4; i++) {
            dogSprites[i] = spriteSheet.getSubimage(i * dogWidth, catHeight, dogWidth, dogHeight);
        }

        zzzImg = spriteSheet.getSubimage(1260, 0, Constants.ZZZ_WIDTH, Constants.ZZZ_HEIGHT);
    }

    private void loadCurrencyImg() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.CURRENCY);

        int bgWidth = Constants.CURRENCY_BACKGROUND_WIDTH;
        int bgHeight = Constants.BUTTON_DEFAULT_HEIGHT;

        int coinSize = Constants.COIN_SIZE;
        int coinXOffset = 416;

        currencyBackground = img.getSubimage(0,0, bgWidth, bgHeight);
        currency = img.getSubimage(coinXOffset, 0, coinSize, coinSize);
    }

    private void loadStatsImages() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PET_HEALTH_STATES);

        int fullBarWidth = Constants.PET_STAT_WIDTH;
        int barHeight = Constants.BUTTON_DEFAULT_HEIGHT;

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

        int backButtonWidth = Constants.BUTTON_WIDTH_BACK;
        int backButtonHeight = Constants.BUTTON_DEFAULT_HEIGHT;

        BufferedImage[] backImgs = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            backImgs[i] = backButtonSprite.getSubimage(i * backButtonWidth, 0, backButtonWidth, backButtonHeight);
        }

        int playButtonWidth = Constants.BUTTON_WIDTH_GAME;
        int playButtonHeight = Constants.BUTTON_DEFAULT_HEIGHT;

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
        Pet currentPet = game.getPlayer().getEquippedPet();

        if (currentPet == null) {
            return;
        }

        for (Button button : buttons) {
            if (button != null) button.update();
        }

        if (currentPet.decayStats()) {
            game.getPlayer().addCoins(100);
        }

        if (currentPet.isSleeping()) {
            actionSpriteIndex = Constants.ACTION_SLEEP;
        } else if (actionTimer > 0) {
            actionTimer--;
        } else {
            actionSpriteIndex = Constants.ACTION_IDLE;
        }
    }

    @Override
    public void draw(Graphics g) {
        Pet currentPet = game.getPlayer().getEquippedPet();

        if (currentPet == null) {
            drawSelectionScreen(g);
        } else {
            drawPlayScreen(g, currentPet);
        }
    }

    private void drawSelectionScreen(Graphics g) {
        // Background
        g.setColor(new Color(89, 198, 255));
        g.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        // Title
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString("Choose your pet", (Constants.GAME_WIDTH / 2) - 220, 100);

        // Cat
        int catX = (Constants.GAME_WIDTH / 2) - 350;
        g.fillRect(catX, 200, 400, 600);
        g.drawImage(catSprites[0], catX, 215, Constants.CAT_WIDTH, Constants.CAT_HEIGHT, null);

        // Dog
        int dogX = (Constants.GAME_WIDTH / 2) + 50;
        g.fillRect(dogX, 200, 400, 600);
        g.drawImage(dogSprites[0], dogX, 215, Constants.DOG_WIDTH, Constants.DOG_HEIGHT, null);
    }

    private void drawPlayScreen(Graphics g, Pet currentPet) {
        currentPet = game.getPlayer().getEquippedPet();

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
        drawCurrency(g);

        // Pet Stats
        if (currentPet != null) {
            drawPetStatsBar(g);
        }

        // Pet Bed
        drawPetBed(g);

        // Pet
        if (currentPet != null) {
            drawCurrentPet(g, currentPet);
        }
    }

    private void drawPetBed(Graphics g) {
        int bedWidth = Constants.PET_BED_WIDTH, bedHeight = Constants.PET_BED_HEIGHT;
        int bedX = Constants.BED_X;
        int bedY = Constants.BED_Y;
        g.drawImage(petBedImg, bedX, bedY, bedWidth, bedHeight, null);
    }

    private void drawCurrentPet(Graphics g, Pet currentPet) {
        BufferedImage spriteToDraw = null;
        int petWidth = 0;
        int petHeight = 0;
        int petX = 0;

        switch (currentPet.getSpecies()) {
            case CAT -> {
                spriteToDraw = catSprites[actionSpriteIndex];
                petWidth = Constants.CAT_WIDTH;
                petHeight = Constants.CAT_HEIGHT;
                petX = Constants.BED_X + 375 - (petWidth / 2);
            }
            case DOG -> {
                spriteToDraw = dogSprites[actionSpriteIndex];
                petWidth = Constants.DOG_WIDTH;
                petHeight = Constants.DOG_HEIGHT;
                petX = Constants.BED_X + 345 - (petWidth / 2);
            }
        }

        int petY = Constants.BED_Y - petHeight + 190;

        g.drawImage(spriteToDraw, petX, petY, petWidth, petHeight, null);

        if (currentPet.isSleeping()) {
            g.drawImage(zzzImg, petX + petWidth - 50, petY + 20, Constants.ZZZ_WIDTH, Constants.ZZZ_HEIGHT, null);
        }
    }

    private void drawCurrency(Graphics g) {
        int backgroundX = Constants.GAME_WIDTH - 435;
        int backgroundY = 20;

        int backgroundWidth = Constants.CURRENCY_BACKGROUND_WIDTH;
        int backgroundHeight = Constants.BUTTON_DEFAULT_HEIGHT;

        g.drawImage(currencyBackground, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);

        int coinSize = Constants.COIN_SIZE;

        int coinX = backgroundX + 20;
        int coinY = backgroundY + 20;

        g.drawImage(currency, coinX, coinY, coinSize, coinSize, null);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 60));

        int textX = coinX + coinSize + 20;
        int textY = backgroundY + 95;

        g.drawString(game.getPlayer().getCoins() + "", textX, textY);
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
            return Constants.STATE_GREEN;
        }
        if (statValue >= 61) {
            return Constants.STATE_LIGHT_GREEN;
        }
        if (statValue >= 41) {
            return Constants.STATE_YELLOW;
        }
        if (statValue >= 21) {
            return Constants.STATE_ORANGE;
        }
        return Constants.STATE_RED;
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

            int mouseX = e.getX();
            int mouseY = e.getY();

            // No Pet
            if (currentPet == null) {
                int catX = (Constants.GAME_WIDTH / 2) - 350;
                if (mouseX >= catX && mouseX <= catX + 315 && mouseY >= 200 && mouseY <= 600) {
                    game.getPlayer().setEquippedPet(new Pet("Cat", PetType.HOME, PetSpecies.CAT));
                    SaveManager.saveGame(game.getPlayer());
                }

                int dogX = (Constants.GAME_WIDTH / 2) + 50;
                if (mouseX >= dogX && mouseX <= dogX + 265 && mouseY >= 200 && mouseY <= 730) {
                    game.getPlayer().setEquippedPet(new Pet("Golden Retriever", PetType.HOME, PetSpecies.DOG));
                    SaveManager.saveGame(game.getPlayer());
                }
                return;
            }

            // Pet Already Picked
            if (sleepButton.isMouseOver() && sleepButton.isMousePressed()) {
                if (currentPet.isSleeping()) {
                    currentPet.wakeUp();
                    actionSpriteIndex = Constants.ACTION_IDLE;
                } else if (currentPet.getEnergy() < 100) {
                    currentPet.sleep();
                    actionSpriteIndex = Constants.ACTION_SLEEP;
                }
            }

            if (!currentPet.isSleeping()) {
                int rewardAmount = 5;

                if (feedButton.isMouseOver() && feedButton.isMousePressed()) {
                    if (currentPet.getHunger() <= 80) {
                        currentPet.feed();
                        game.getPlayer().addCoins(rewardAmount);
                        actionSpriteIndex = Constants.ACTION_FEED;
                        actionTimer = 50;
                    }
                }
                if (loveButton.isMouseOver() && loveButton.isMousePressed()) {
                    if (currentPet.getLove() <= 80) {
                        currentPet.love();
                        game.getPlayer().addCoins(rewardAmount);
                        actionSpriteIndex = Constants.ACTION_LOVE;
                        actionTimer = 75;
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
