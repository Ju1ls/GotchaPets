package cz.jull.logic;

import cz.jull.logic.pet.Pet;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the persistent state of the player profile.
 * Stores the player's currency, currently active pet, and list of all owned pets.
 * This object is fully serializable for saving/loading the game state.
 */
@Data
public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int coins = 2000;
    private Pet equippedPet;
    private List<Pet> inventory = new ArrayList<>();

    /**
     * Adds coins to the player's balance.
     *
     * @param amount The number of coins to add.
     */
    public void addCoins(int amount) {
        coins += amount;
    }

    /**
     * Attempts to deduct coins from the player's balance.
     *
     * @param amount The number of coins required.
     * @return True if the player had enough coins and the transaction succeeded, false otherwise.
     */
    public boolean spendCoins(int amount) {
        if (coins >= amount) {
            coins -= amount;
            return true;
        }
        return false;
    }

    /**
     * Adds a newly acquired pet to the player's inventory list.
     *
     * @param pet The Pet object to store.
     */
    public void addPetToInventory(Pet pet) {
        inventory.add(pet);
    }

    /**
     * Sets the player's active pet. If the pet is not already in the inventory, it is added.
     *
     * @param equippedPet The Pet object to set as active.
     */
    public void setEquippedPet(Pet equippedPet) {
        this.equippedPet = equippedPet;

        if (!inventory.contains(equippedPet)) {
            inventory.add(equippedPet);
        }
    }
}
