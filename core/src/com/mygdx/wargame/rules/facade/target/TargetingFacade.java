package com.mygdx.wargame.rules.facade.target;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;

import java.util.Map;

public class TargetingFacade {

    public Target findTarget(Pilot pilot, Mech mech, Map<Mech, Pilot> targets, BattleMap battleMap) {
        return new FirstTargetStrategy().findTarget(pilot, mech, targets, battleMap);
    }

}
