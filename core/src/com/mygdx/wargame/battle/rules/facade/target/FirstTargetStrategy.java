package com.mygdx.wargame.battle.rules.facade.target;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Pilot;

import java.util.Map;
import java.util.Optional;

public class FirstTargetStrategy implements TargetingStrategy {
    @Override
    public Optional<Target> findTarget(Pilot pilot, AbstractMech mech, Map<AbstractMech, Pilot> targets, BattleMap battleMap, TargetingStrategy targetingStrategy) {
        Optional<Map.Entry<AbstractMech, Pilot>> target = targets.entrySet().stream().filter(m -> m.getKey().isActive()).findFirst();

        if (target.isPresent())
            return Optional.of(new Target(target.get().getKey(), target.get().getValue()));

        return Optional.empty();
    }
}
