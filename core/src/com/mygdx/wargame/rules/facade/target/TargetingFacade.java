package com.mygdx.wargame.rules.facade.target;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;

import java.util.Map;
import java.util.Optional;

public class TargetingFacade {

    private FirstTargetStrategy firstTargetStrategy = new FirstTargetStrategy();
    private WeakestTargetStrategy weakestTargetStrategy = new WeakestTargetStrategy();

    public Optional<Target> findTarget(Pilot pilot, Mech mech, Map<Mech, Pilot> targets, BattleMap battleMap) {

        if(pilot.hasPerk(Perks.Cautious)) {
            return weakestTargetStrategy.findTarget(pilot, mech, targets, battleMap, null);
        }
        return new FlankingTargetStrategy().findTarget(pilot, mech, targets, battleMap, firstTargetStrategy);

    }

}
