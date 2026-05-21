package cz.jull.utils;

import cz.jull.logic.Player;

import java.io.*;

public class SaveManager {
    private static final String SAVE_FILE = "savegame.dat";

    public static void saveGame(Player player) {
        if (player == null) return;

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(player);
        } catch (IOException e) {
            System.out.println("Failed to save game: " + e.getMessage());
        }
    }

    public static Player loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            Player loadedPlayer = (Player) in.readObject();
            return loadedPlayer;

        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
