package com.mygdx.wargame.battle.rules.facade;

public class GameState {

    public static State state = State.Deploy;
    public static boolean paused = false;

    public enum State {
        Deploy,
        Battle;
    }

}
