package com.mygdx.wargame.rules.facade.target;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;

import java.util.Map;

public class FirstTargetStrategy implements TargetingStrategy {
    @Override
    public Target findTarget(Pilot pilot, Mech mech, Map<Mech, Pilot> targets, BattleMap battleMap) {
        Map.Entry<Mech, Pilot> target = targets.entrySet().stream().findFirst().get();
        return new Target(target.getKey(), target.getValue());
    }
}
