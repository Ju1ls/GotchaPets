package cz.jull.ui;

import cz.jull.logic.pet.Pet;
import cz.jull.logic.pet.PetSpecies;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PetRenderer {
    private BufferedImage[] catSprites = new BufferedImage[4];
    private BufferedImage[] dogSprites = new BufferedImage[4];
    private BufferedImage[] rabbitSprites = new BufferedImage[4];
    private BufferedImage[] hamsterSprites = new BufferedImage[4];
    private BufferedImage[] parrotSprites = new BufferedImage[4];
    private BufferedImage[] sealSprites = new BufferedImage[4];
    private BufferedImage[] jellyfishSprites = new BufferedImage[4];
    private BufferedImage[] sharkSprites = new BufferedImage[4];
    private BufferedImage[] turtleSprites = new BufferedImage[4];
    private BufferedImage[] octopusSprites = new BufferedImage[4];

    private BufferedImage zzzImg;
    private BufferedImage petBedImgHome;
    private BufferedImage petBedImgWater;

    public PetRenderer() {
        loadImages();
    }

    private void loadImages() {
        BufferedImage spriteSheet = LoadSave.getSpriteAtlas(LoadSave.DEFAULT_PET_SPRITES);
        BufferedImage hamsterRabbitSprites = LoadSave.getSpriteAtlas(LoadSave.HAMSTER_RABBIT_SPRITES);
        BufferedImage jellyfishSealSprites = LoadSave.getSpriteAtlas(LoadSave.JELLYFISH_SEAL_SPRITES);
        BufferedImage octopusTurtleSprites = LoadSave.getSpriteAtlas(LoadSave.OCTOPUS_TURTLE_SPRITES);
        BufferedImage parrotSheet = LoadSave.getSpriteAtlas(LoadSave.PARROT_SPRITES);
        BufferedImage sharkSheet = LoadSave.getSpriteAtlas(LoadSave.SHARK_SPRITES);

        BufferedImage beds = LoadSave.getSpriteAtlas(LoadSave.PET_BED);
        petBedImgHome = beds.getSubimage(0, 0, Constants.PET_BED_HOME_WIDTH, Constants.PET_BED_HOME_HEIGHT);
        petBedImgWater = beds.getSubimage(690, 0, Constants.PET_BED_WATER_WIDTH, Constants.PET_BED_WATER_HEIGHT);

        // CAT
        int catWidth = Constants.CAT_WIDTH;
        int catHeight = Constants.CAT_HEIGHT;
        for (int i = 0; i < 4; i++) {
            catSprites[i] = spriteSheet.getSubimage(i * catWidth, 0, catWidth, catHeight);
        }

        // DOG
        int dogWidth = Constants.DOG_WIDTH;
        int dogHeight = Constants.DOG_HEIGHT;
        for (int i = 0; i < 4; i++) {
            dogSprites[i] = spriteSheet.getSubimage(i * dogWidth, catHeight, dogWidth, dogHeight);
        }

        // HAMSTER
        int hamsterWidth = Constants.HAMSTER_WIDTH;
        int hamsterHeight = Constants.HAMSTER_HEIGHT;
        for (int i = 0; i < 4; i++) {
            hamsterSprites[i] = hamsterRabbitSprites.getSubimage(i * hamsterWidth, 0, hamsterWidth, hamsterHeight);
        }

        // RABBIT
        int rabbitWidth = Constants.RABBIT_WIDTH;
        int rabbitHeight = Constants.RABBIT_HEIGHT;
        for (int i = 0; i < 4; i++) {
            rabbitSprites[i] = hamsterRabbitSprites.getSubimage(i * rabbitWidth, hamsterHeight, rabbitWidth, rabbitHeight);
        }

        // PARROT
        int parrotWidth = Constants.PARROT_WIDTH;
        int parrotHeight = Constants.PARROT_HEIGHT;
        for (int i = 0; i < 4; i++) {
            parrotSprites[i] = parrotSheet.getSubimage(i * parrotWidth, 0, parrotWidth, parrotHeight);
        }

        // JELLYFISH
        int jellyfishWidth = Constants.JELLYFISH_WIDTH;
        int jellyfishHeight = Constants.JELLYFISH_HEIGHT;
        for (int i = 0; i < 4; i++) {
            jellyfishSprites[i] = jellyfishSealSprites.getSubimage(i * jellyfishWidth, 0, jellyfishWidth, jellyfishHeight);
        }

        // SEAL
        int sealWidth = Constants.SEAL_WIDTH;
        int sealHeight = Constants.SEAL_HEIGHT;
        for (int i = 0; i < 4; i++) {
            sealSprites[i] = jellyfishSealSprites.getSubimage(i * sealWidth, jellyfishHeight, sealWidth, sealHeight);
        }

        // SHARK
        int sharkWidth = Constants.SHARK_WIDTH;
        int sharkHeight = Constants.SHARK_HEIGHT;
        for (int i = 0; i < 3; i++) {
            sharkSprites[i] = sharkSheet.getSubimage(i * sharkWidth, 0, sharkWidth, sharkHeight);
        }
        sharkSprites[3] = sharkSheet.getSubimage(0, sharkHeight, sharkWidth, sharkHeight);

        // OCTOPUS
        int octopusWidth = Constants.OCTOPUS_WIDTH;
        int octopusHeight = Constants.OCTOPUS_HEIGHT;
        for (int i = 0; i < 4; i++) {
            octopusSprites[i] = octopusTurtleSprites.getSubimage(i * octopusWidth, 0, octopusWidth, octopusHeight);
        }

        // TURTLE
        int turtleWidth = Constants.TURTLE_WIDTH;
        int turtleHeight = Constants.TURTLE_HEIGHT;
        for (int i = 0; i < 4; i++) {
            turtleSprites[i] = octopusTurtleSprites.getSubimage(i * turtleWidth, octopusHeight, turtleWidth, turtleHeight);
        }

        zzzImg = spriteSheet.getSubimage(1260, 0, Constants.ZZZ_WIDTH, Constants.ZZZ_HEIGHT);
    }

    public void draw(Graphics g, Pet currentPet, int actionSpriteIndex) {
        switch (currentPet.getType()) {
            case HOME -> g.drawImage(petBedImgHome, Constants.BED_X_HOME, Constants.BED_Y_HOME, Constants.PET_BED_HOME_WIDTH, Constants.PET_BED_HOME_HEIGHT, null);
            case WATER -> g.drawImage(petBedImgWater, Constants.BED_X_HOME, Constants.BED_Y_HOME, Constants.PET_BED_HOME_WIDTH, Constants.PET_BED_HOME_HEIGHT, null);
        }

        BufferedImage spriteToDraw = null;
        int petWidth = 0;
        int petHeight = 0;
        int petX = 0;
        int petY = 0;

        switch (currentPet.getSpecies()) {
            case CAT -> {
                spriteToDraw = catSprites[actionSpriteIndex];
                petWidth = Constants.CAT_WIDTH;
                petHeight = Constants.CAT_HEIGHT;
                petX = Constants.BED_X_HOME + 375 - (petWidth / 2);
                petY = Constants.BED_Y_HOME - petHeight + 190;
            }
            case DOG -> {
                spriteToDraw = dogSprites[actionSpriteIndex];
                petWidth = Constants.DOG_WIDTH;
                petHeight = Constants.DOG_HEIGHT;
                petX = Constants.BED_X_HOME + 345 - (petWidth / 2);
                petY = Constants.BED_Y_HOME - petHeight + 190;
            }
            case RABBIT -> {
                spriteToDraw = rabbitSprites[actionSpriteIndex];
                petWidth = Constants.RABBIT_WIDTH;
                petHeight = Constants.RABBIT_HEIGHT;
                petX = Constants.BED_X_HOME + 345 - (petWidth / 2);
                petY = Constants.BED_Y_HOME - petHeight + 190;
            }
            case HAMSTER -> {
                spriteToDraw = hamsterSprites[actionSpriteIndex];
                petWidth = Constants.HAMSTER_WIDTH;
                petHeight = Constants.HAMSTER_HEIGHT;
                petX = Constants.BED_X_HOME + 345 - (petWidth / 2);
                petY = Constants.BED_Y_HOME - petHeight + 190;
            }
            case PARROT -> {
                spriteToDraw = parrotSprites[actionSpriteIndex];
                petWidth = Constants.PARROT_WIDTH;
                petHeight = Constants.PARROT_HEIGHT;
                petX = Constants.BED_X_HOME + 345 - (petWidth / 2);
                petY = Constants.PET_BED_HOME_HEIGHT;
            }
            case SEAL -> {
                spriteToDraw = sealSprites[actionSpriteIndex];
                petWidth = Constants.SEAL_WIDTH;
                petHeight = Constants.SEAL_HEIGHT;
                petX = Constants.BED_X_HOME + 345 - (petWidth / 2);
                petY = Constants.BED_Y_HOME - petHeight + 225;
            }
            case JELLYFISH -> {
                spriteToDraw = jellyfishSprites[actionSpriteIndex];
                petWidth = Constants.JELLYFISH_WIDTH;
                petHeight = Constants.JELLYFISH_HEIGHT;
                petX = Constants.BED_X_HOME + 345 - (petWidth / 2);
                petY = Constants.BED_Y_HOME - petHeight + 190;
            }
            case SHARK -> {
                spriteToDraw = sharkSprites[actionSpriteIndex];
                petWidth = Constants.SHARK_WIDTH;
                petHeight = Constants.SHARK_HEIGHT;
                petX = Constants.BED_X_HOME + 345 - (petWidth / 2);
                petY = Constants.BED_Y_HOME - petHeight + 190;
            }
            case TURTLE -> {
                spriteToDraw = turtleSprites[actionSpriteIndex];
                petWidth = Constants.TURTLE_WIDTH;
                petHeight = Constants.TURTLE_HEIGHT;
                petX = Constants.BED_X_HOME + 310 - (petWidth / 2);
                petY = Constants.BED_Y_HOME - petHeight + 190;
            }
            case OCTOPUS -> {
                spriteToDraw = octopusSprites[actionSpriteIndex];
                petWidth = Constants.OCTOPUS_WIDTH;
                petHeight = Constants.OCTOPUS_HEIGHT;
                petX = Constants.BED_X_HOME + 345 - (petWidth / 2);
                petY = Constants.BED_Y_HOME - petHeight + 190;
            }
        }

        g.drawImage(spriteToDraw, petX, petY, petWidth, petHeight, null);

        if (currentPet.isSleeping()) {
            g.drawImage(zzzImg, petX + petWidth - 50, petY + 20, Constants.ZZZ_WIDTH, Constants.ZZZ_HEIGHT, null);
        }
    }

    public BufferedImage getIdleSprite(PetSpecies species) {
        switch (species) {
            case CAT -> {
                return catSprites[Constants.ACTION_IDLE];
            }
            case DOG -> {
                return dogSprites[Constants.ACTION_IDLE];
            }
            default -> {
                return null;
            }
        }
    }
}
