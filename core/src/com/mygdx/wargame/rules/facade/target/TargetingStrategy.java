package com.mygdx.wargame.rules.facade.target;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;

import java.util.Map;
import java.util.Optional;

public interface TargetingStrategy {
    Optional<Target> findTarget(Pilot pilot, Mech mech, Map<Mech, Pilot> targets, BattleMap battleMap, TargetingStrategy additionalStrategy);
}
