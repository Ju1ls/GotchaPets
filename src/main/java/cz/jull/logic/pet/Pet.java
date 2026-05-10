package cz.jull.logic.pet;

public class Pet {
    private String name;
    private PetType type;

    private int hunger = 55;
    private int energy = 35;
    private int love = 75;

    public Pet(String name, PetType type) {
        this.name = name;
        this.type = type;
    }

    public void feed() {
        hunger = Math.min(100, hunger + 20);
    }

    public void sleep() {
        energy = Math.min(100, energy + 20);
    }

    public void love() {
        love = Math.min(100, love + 20);
    }

    public void decayStats() {
        hunger = Math.max(0, hunger - 1);
        energy = Math.max(0, energy - 1);
        love = Math.max(0, love - 1);
    }

    public String getName() {
        return name;
    }

    public PetType getType() {
        return type;
    }

    public int getHunger() {
        return hunger;
    }

    public int getEnergy() {
        return energy;
    }

    public int getLove() {
        return love;
    }
}
