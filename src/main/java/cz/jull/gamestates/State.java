package cz.jull.gamestates;

import cz.jull.Game;

/**
 * Abstract base class for all game states.
 * Provides a common reference to the main Game instance.
 */
public abstract class State {
    /** Reference to the main game object. */
    protected Game game;

    /**
     * Constructs a new State.
     *
     * @param game The main game instance.
     */
    public State(Game game) {
        this.game = game;
    }
}
