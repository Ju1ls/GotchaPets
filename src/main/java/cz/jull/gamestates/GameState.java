package cz.jull.gamestates;

/**
 * Enumerates the various overarching states the game can be in.
 */
public enum GameState {
    /** The main menu state. */
    MENU,
    /** The active gameplay state where the player interacts with their pet. */
    PLAYING,
    /** The gacha screen where the player can roll for new pets. */
    GACHA,
    /** The inventory screen showing the player's collection of pets. */
    PET_LIST;
}
