package com.mygdx.wargame.battle.rules.facade.target;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Perks;
import com.mygdx.wargame.common.pilot.Pilot;

import java.util.Map;
import java.util.Optional;

public class TargetingFacade {

    private FirstTargetStrategy firstTargetStrategy = new FirstTargetStrategy();
    private WeakestTargetStrategy weakestTargetStrategy = new WeakestTargetStrategy();
    private FlankingTargetStrategy flankingTargetStrategy;
    private StageElementsStorage stageElementsStorage;

    public TargetingFacade(StageElementsStorage stageElementsStorage) {
        this.stageElementsStorage = stageElementsStorage;
    }

    public Optional<Target> findTarget(Pilot pilot, Mech mech, Map<Mech, Pilot> targets, BattleMap battleMap) {

        this.flankingTargetStrategy = new FlankingTargetStrategy(stageElementsStorage);

        if (pilot.hasPerk(Perks.Cautious)) {
            return flankingTargetStrategy.findTarget(pilot, mech, targets, battleMap, weakestTargetStrategy);
        }

        return flankingTargetStrategy.findTarget(pilot, mech, targets, battleMap, firstTargetStrategy);

    }

}
