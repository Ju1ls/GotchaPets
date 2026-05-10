package cz.jull.logic;

import cz.jull.logic.pet.Pet;
import cz.jull.logic.pet.PetType;

public class Player {
    private int coins = 500;
    private Pet equippedPet;

    public Player() {
        this.equippedPet = new Pet("Starter Pet", PetType.HOME);
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public int getCoins() {
        return coins;
    }

    public Pet getEquippedPet() {
        return equippedPet;
    }

    public void setEquippedPet(Pet equippedPet) {
        this.equippedPet = equippedPet;
    }
}
