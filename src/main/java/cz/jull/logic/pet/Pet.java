package cz.jull.logic.pet;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an individual pet entity, managing its core identity, current stats, and real-time stat decay logic.
 * Implements Serializable to allow the pet's progress to be saved alongside the player profile.
 */
@Data
@NoArgsConstructor
public class Pet implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private PetType type;
    private PetSpecies species;
    private int rarity;

    private int hunger = 1;
    private int energy = 1;
    private int love = 1;

    private final long DECAY_INTERVAL = 150000L;
    private final long MAX_COOLDOWN_TIME = 86400000L;
    private final long FIVE_HOUR_COOLDOWN = 18000000L;

    private boolean isSleeping = false;

    private long lastDecayTime = System.currentTimeMillis();
    private long hungerCooldown = 0;
    private long energyCooldown = 0;
    private long loveCooldown = 0;

    /**
     * Constructs a new Pet with base identity properties.
     *
     * @param name    The name of the pet.
     * @param type    The environmental type of the pet.
     * @param species The specific species.
     * @param rarity  The gacha rarity level.
     */
    public Pet(String name, PetType type, PetSpecies species, int rarity) {
        this.name = name;
        this.type = type;
        this.species = species;
        this.rarity = rarity;
    }

    /**
     * Increases hunger by 20, capping at 100.
     * If hunger hits 100, imposes a 5-hour cooldown before the stat can decay again.
     */
    public void feed() {
        hunger = Math.min(100, hunger + 20);
        if (hunger == 100) {
            hungerCooldown = System.currentTimeMillis() + FIVE_HOUR_COOLDOWN;
        }
    }

    /**
     * Puts the pet to sleep, initiating energy regeneration if energy is not already full.
     */
    public void sleep() {
        if (energy < 100) {
            isSleeping = true;
        }
    }

    /**
     * Manually wakes the pet up, pausing energy regeneration.
     */
    public void wakeUp() {
        isSleeping = false;
    }

    /**
     * Increases love by 20, capping at 100.
     * If love hits 100, imposes a 5-hour cooldown before the stat can decay again.
     */
    public void love() {
        love = Math.min(100, love + 20);
        if (love == 100) {
            loveCooldown = System.currentTimeMillis() + FIVE_HOUR_COOLDOWN;
        }
    }

    /**
     * Processes time-based decay and regeneration for the pet's stats based on the elapsed time
     * since the last update. This method correctly simulates time passed while the game was closed.
     *
     * @return True if the pet finished regenerating energy to 100% during this decay cycle.
     */
    public boolean decayStats() {
        boolean finishedNap = false;
        long currentTime = System.currentTimeMillis();

        while (currentTime - lastDecayTime >= DECAY_INTERVAL) {

            if (currentTime > hungerCooldown) {
                hunger = Math.max(0, hunger - 1);
            }
            if (currentTime > loveCooldown) {
                love = Math.max(0, love - 1);
            }

            if (isSleeping) {
                energy = Math.min(100, energy + 1);

                if (energy == 100) {
                    isSleeping = false;
                    finishedNap = true;
                    energyCooldown = currentTime + MAX_COOLDOWN_TIME;
                }
            } else {
                if (currentTime > energyCooldown) {
                    energy = Math.max(0, energy - 1);
                }
            }

            lastDecayTime += DECAY_INTERVAL;
        }

        return finishedNap;
    }
}
