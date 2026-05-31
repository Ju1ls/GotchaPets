package cz.jull.ui;

import cz.jull.logic.pet.Pet;
import cz.jull.utils.Constants;
import cz.jull.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Handles the rendering of the active pet's stats (Hunger, Energy, Love).
 * Draws dynamic segmented health bars whose colors change based on the current stat values.
 */
public class PetStatsUI {
    private final BufferedImage[][] statSegments = new BufferedImage[5][3];
    private BufferedImage statBackground;
    private BufferedImage statForeground;

    /**
     * Constructs the PetStatsUI and pre-loads its image resources.
     */
    public PetStatsUI() {
        loadImages();
    }

    /**
     * Loads and slices the stat bar background, foreground overlay, and color segments.
     */
    private void loadImages() {
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

    /**
     * Draws the stat frame and the colored inner segments based on the current pet's stats.
     *
     * @param g The Graphics context used for drawing.
     * @param currentPet The currently equipped pet whose stats should be displayed.
     */
    public void draw(Graphics g, Pet currentPet) {
        if (currentPet == null) return;

        int drawWidth = 415;
        int drawHeight = 145;
        int startX = 1165;
        int startY = 735;

        int[] segWidths = {140, 135, 140};
        int[] segXOffsets = {0, 140, 275};

        // Background
        g.drawImage(statBackground, startX, startY, drawWidth, drawHeight, null);

        // Stats
        int hunger = getColorRow(currentPet.getHunger());
        g.drawImage(statSegments[hunger][0], startX + segXOffsets[0], startY, segWidths[0], drawHeight, null);

        int energy = getColorRow(currentPet.getEnergy());
        g.drawImage(statSegments[energy][1], startX + segXOffsets[1], startY, segWidths[1], drawHeight, null);

        int love = getColorRow(currentPet.getLove());
        g.drawImage(statSegments[love][2], startX + segXOffsets[2], startY, segWidths[2], drawHeight, null);

        // Foreground
        g.drawImage(statForeground, startX, startY, drawWidth, drawHeight, null);
    }

    /**
     * Determines which color segment row (green to red) to use based on the stat value.
     *
     * @param statValue The pet's current stat amount (0-100).
     * @return The constant integer representing the color state row.
     */
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
}
