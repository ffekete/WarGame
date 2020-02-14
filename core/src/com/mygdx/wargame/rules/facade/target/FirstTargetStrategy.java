package com.mygdx.wargame.rules.facade.target;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;

import java.util.Map;
import java.util.Optional;

public class FirstTargetStrategy implements TargetingStrategy {
    @Override
    public Optional<Target> findTarget(Pilot pilot, Mech mech, Map<Mech, Pilot> targets, BattleMap battleMap, TargetingStrategy targetingStrategy) {
        Optional<Map.Entry<Mech, Pilot>> target = targets.entrySet().stream().filter(m -> m.getKey().isActive()).findFirst();

        if(target.isPresent())
            return Optional.of(new Target(target.get().getKey(), target.get().getValue()));

        return Optional.empty();
    }
}
