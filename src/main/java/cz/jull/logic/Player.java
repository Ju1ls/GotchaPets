package cz.jull.logic;

import cz.jull.logic.pet.Pet;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int coins = 2000;
    private Pet equippedPet;
    private List<Pet> inventory = new ArrayList<>();

    public Player() {
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public boolean spendCoins(int amount) {
        if (coins >= amount) {
            coins -= amount;
            return true;
        }
        return false;
    }

    public void addPetToInventory(Pet pet) {
        inventory.add(pet);
    }

    public void setEquippedPet(Pet equippedPet) {
        this.equippedPet = equippedPet;

        if (!inventory.contains(equippedPet)) {
            inventory.add(equippedPet);
        }

    }

    public int getCoins() {
        return coins;
    }

    public Pet getEquippedPet() {
        return equippedPet;
    }

    public List<Pet> getInventory() {
        return inventory;
    }
}
