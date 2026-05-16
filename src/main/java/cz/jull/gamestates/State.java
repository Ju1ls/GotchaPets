package cz.jull.gamestates;

import cz.jull.Game;

public abstract class State {
    protected Game game;

    public State(Game game) {
        this.game = game;
    }
}
