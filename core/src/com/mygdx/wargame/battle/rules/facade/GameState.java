package com.mygdx.wargame.battle.rules.facade;

import com.mygdx.wargame.battle.lock.ActionLock;

public class GameState {

    public static State state = State.Deploy;
    public static boolean paused = false;

    public enum State {
        Deploy,
        Battle;
    }

    public static ActionLock actionLock;

}
