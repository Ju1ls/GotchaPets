package cz.jull.logic.pet;

import java.io.Serial;
import java.io.Serializable;

public class Pet implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private PetType type;
    private PetSpecies species;

    private int hunger = 1;
    private int energy = 1;
    private int love = 1;

    private final long DECAY_INTERVAL = 150000L;
    private final long MAX_COOLDOWN_TIME = 86400000L;
    private final long FIVE_HOUR_COOLDOWN = 18000000L;

    private boolean isSleeping = false;

    private long lastDecayTime;
    private long hungerCooldown = 0;
    private long energyCooldown = 0;
    private long loveCooldown = 0;

    public Pet(String name, PetType type, PetSpecies species) {
        this.name = name;
        this.type = type;
        this.species = species;
        this.lastDecayTime = System.currentTimeMillis();
    }

    public void feed() {
        hunger = Math.min(100, hunger + 20);
        if (hunger == 100) {
            hungerCooldown = System.currentTimeMillis() + FIVE_HOUR_COOLDOWN;
        }
    }

    public void sleep() {
        if (energy < 100) {
            isSleeping = true;
        }
    }

    public void wakeUp() {
        isSleeping = false;
    }

    public void love() {
        love = Math.min(100, love + 20);
        if (love == 100) {
            loveCooldown = System.currentTimeMillis() + FIVE_HOUR_COOLDOWN;
        }
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getLove() {
        return love;
    }

    public boolean isSleeping() {
        return isSleeping;
    }

    public void setSleeping(boolean sleeping) {
        isSleeping = sleeping;
    }

    public PetSpecies getSpecies() {
        return species;
    }

    public void setSpecies(PetSpecies species) {
        this.species = species;
    }
}
