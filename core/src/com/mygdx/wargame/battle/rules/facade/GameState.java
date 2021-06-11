package com.mygdx.wargame.battle.rules.facade;

import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.rules.facade.target.Target;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Pilot;

import javax.swing.text.html.Option;
import java.util.Optional;

public class GameState {

    public static State state = State.Deploy;
    public static boolean paused = false;

    public enum State {
        Deploy,
        Battle;
    }

    public static ActionLock actionLock;
    public static Mech selectedMech;
    public static Pilot selectedPilot;

    public static Optional<Target> target;

    public static ParallelAction fireSingleWeaponAction;
    public static float messageDelayDuringSingleAttack = 0f;

}
