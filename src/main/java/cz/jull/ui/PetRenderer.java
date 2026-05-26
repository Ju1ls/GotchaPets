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
    private BufferedImage zzzImg;
    private BufferedImage petBedImg;

    public PetRenderer() {
        loadImages();
    }

    private void loadImages() {
        BufferedImage spriteSheet = LoadSave.getSpriteAtlas(LoadSave.DEFAULT_PET_SPRITES);
        BufferedImage fullBedCanvas = LoadSave.getSpriteAtlas(LoadSave.PET_BED);
        petBedImg = fullBedCanvas.getSubimage(0, 0, 690, 300);

        // CAT
        int catWidth = Constants.CAT_WIDTH, catHeight = Constants.CAT_HEIGHT;
        for (int i = 0; i < 4; i++) {
            catSprites[i] = spriteSheet.getSubimage(i * catWidth, 0, catWidth, catHeight);
        }

        // DOG
        int dogWidth = Constants.DOG_WIDTH, dogHeight = Constants.DOG_HEIGHT;
        for (int i = 0; i < 4; i++) {
            dogSprites[i] = spriteSheet.getSubimage(i * dogWidth, catHeight, dogWidth, dogHeight);
        }

        zzzImg = spriteSheet.getSubimage(1260, 0, Constants.ZZZ_WIDTH, Constants.ZZZ_HEIGHT);
    }

    public void draw(Graphics g, Pet currentPet, int actionSpriteIndex) {
        int bedWidth = Constants.PET_BED_WIDTH, bedHeight = Constants.PET_BED_HEIGHT;
        int bedX = Constants.BED_X;
        int bedY = Constants.BED_Y;
        g.drawImage(petBedImg, bedX, bedY, bedWidth, bedHeight, null);

        if (currentPet == null) return;

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
