package cz.jull.utils;

import cz.jull.logic.Player;

import java.io.*;

/**
 * A utility class handling the serialization and deserialization of the Player's state
 * to and from a local binary data file.
 */
public class SaveManager {
    private static final String SAVE_FILE = "savegame.dat";

    /**
     * Saves the current player profile state to disk.
     *
     * @param player The Player object containing the current game progress to save.
     */
    public static void saveGame(Player player) {
        if (player == null) return;

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(player);
        } catch (IOException e) {
            System.out.println("Failed to save game: " + e.getMessage());
        }
    }

    /**
     * Loads the player profile state from disk.
     *
     * @return The restored Player object, or null if no save file exists or reading failed.
     */
    public static Player loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (Player) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
