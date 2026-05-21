package cz.jull.logic;

import cz.jull.logic.pet.Pet;
import cz.jull.utils.SaveManager;

import java.io.Serial;
import java.io.Serializable;

public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int coins = 500;
    private Pet equippedPet;

    public Player() {
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
