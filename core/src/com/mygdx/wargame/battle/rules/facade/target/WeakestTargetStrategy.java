package com.mygdx.wargame.battle.rules.facade.target;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Pilot;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class WeakestTargetStrategy implements TargetingStrategy {

    @Override
    public Optional<Target> findTarget(Pilot pilot, AbstractMech mech, Map<AbstractMech, Pilot> targets, BattleMap battleMap, TargetingStrategy targetingStrategy) {
        Optional<Map.Entry<AbstractMech, Pilot>> target = targets.entrySet()
                .stream()
                .filter(m -> m.getKey().isActive())
                .sorted(new Comparator<Map.Entry<AbstractMech, Pilot>>() {
                    @Override
                    public int compare(Map.Entry<AbstractMech, Pilot> o1, Map.Entry<AbstractMech, Pilot> o2) {
                        return Integer.compare(o1.getKey().getHp(BodyPart.Head) + o1.getKey().getHp(BodyPart.Torso), o2.getKey().getHp(BodyPart.Head) + o2.getKey().getHp(BodyPart.Torso));
                    }
                })
                .findFirst();

        if (target.isPresent())
            return Optional.of(new Target(target.get().getKey(), target.get().getValue()));

        return Optional.empty();
    }
}
