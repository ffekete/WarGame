package com.mygdx.wargame.battle.rules.facade.target;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Pilot;

import java.util.Map;
import java.util.Optional;

public interface TargetingStrategy {
    Optional<Target> findTarget(Pilot pilot, AbstractMech mech, Map<AbstractMech, Pilot> targets, BattleMap battleMap, TargetingStrategy additionalStrategy);
}
