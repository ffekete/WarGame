package com.mygdx.wargame.rules.facade.target;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class WeakestTargetStrategy implements TargetingStrategy {

    @Override
    public Optional<Target> findTarget(Pilot pilot, Mech mech, Map<Mech, Pilot> targets, BattleMap battleMap, TargetingStrategy targetingStrategy) {
        Optional<Map.Entry<Mech, Pilot>> target = targets.entrySet()
                .stream()
                .filter(m -> m.getKey().isActive())
                .sorted(new Comparator<Map.Entry<Mech, Pilot>>() {
                    @Override
                    public int compare(Map.Entry<Mech, Pilot> o1, Map.Entry<Mech, Pilot> o2) {
                        return Integer.compare(o1.getKey().getHp(BodyPart.Head) + o1.getKey().getHp(BodyPart.Torso), o2.getKey().getHp(BodyPart.Head) + o2.getKey().getHp(BodyPart.Torso));
                    }
                })
                .findFirst();

        if (target.isPresent())
            return Optional.of(new Target(target.get().getKey(), target.get().getValue()));

        return Optional.empty();
    }
}
