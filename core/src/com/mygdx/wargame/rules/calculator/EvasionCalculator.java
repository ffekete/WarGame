package com.mygdx.wargame.rules.calculator;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.TerrainType;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.pilot.Skill;

public class EvasionCalculator {

    public int calculate(Pilot targetPilot, Mech targetMech, Pilot enemyPilot, BattleMap battleMap) {
        int baseEvasion = targetMech.getRemainingMovementPoints();

        if(targetPilot.hasPerk(Perks.Agile)) {
            baseEvasion += 5;
        }

        baseEvasion += Math.ceil(targetPilot.getSkills().get(Skill.Evading) * 0.05d);

        baseEvasion -= Math.ceil(enemyPilot.getSkills().get(Skill.Targeting) * 0.05d);

        if(battleMap.getTerrainType() == TerrainType.Jungle && targetPilot.hasPerk(Perks.JungleExpert))
            baseEvasion += 5;

        return baseEvasion;
    }

}
