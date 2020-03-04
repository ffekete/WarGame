package com.mygdx.wargame.rules.calculator;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.TerrainType;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.pilot.Skill;
import com.mygdx.wargame.util.MapUtils;

public class EvasionCalculator {

    private MapUtils mapUtils = new MapUtils();
    private StageElementsStorage stageElementsStorage;

    public EvasionCalculator(StageElementsStorage stageElementsStorage) {
        this.stageElementsStorage = stageElementsStorage;
    }

    public int calculate(Pilot targetPilot, Mech targetMech, Pilot enemyPilot, BattleMap battleMap) {
        int baseEvasion = targetMech.getRemainingMovementPoints();

        if (targetPilot.hasPerk(Perks.Agile)) {
            baseEvasion += 5;
        }

        baseEvasion += Math.ceil(targetPilot.getSkills().get(Skill.Evading) * 0.05d);

        baseEvasion -= Math.ceil(enemyPilot.getSkills().get(Skill.Targeting) * 0.05d);

        if (battleMap.getTerrainType() == TerrainType.Jungle && targetPilot.hasPerk(Perks.JungleExpert))
            baseEvasion += 5;

        if(!mapUtils.nrOfTreesOnTile(stageElementsStorage, targetMech.getX(), targetMech.getY()).isEmpty()) {
            baseEvasion += 10;
        }

        return baseEvasion;
    }

}
