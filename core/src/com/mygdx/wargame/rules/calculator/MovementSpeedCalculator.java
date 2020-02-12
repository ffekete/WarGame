package com.mygdx.wargame.rules.calculator;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.TerrainType;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.pilot.Skill;

public class MovementSpeedCalculator {

    public int calculate(Pilot pilot, Mech mech, BattleMap battleMap) {

        if(mech.getStability() <= 0)
            return 0;

        if (mech.getHp(BodyPart.LeftLeg) <= 0 && mech.getHp(BodyPart.LeftLeg) <= 0)
            return 0;

        int baseSpeed = mech.getMaxMovementPoints();

        // perks
        if (pilot.hasPerk(Perks.ExpertPilot))
            baseSpeed++;

        if (battleMap.getTerrainType() == TerrainType.Jungle && pilot.hasPerk(Perks.JungleExpert)) {
            baseSpeed++;
        }

        if (battleMap.getTerrainType() == TerrainType.Desert && pilot.hasPerk(Perks.DesertExpert)) {
            baseSpeed++;
        }

        if (battleMap.getTerrainType() == TerrainType.Swamp && pilot.hasPerk(Perks.SwampExpert)) {
            baseSpeed++;
        }

        // terrain
        baseSpeed += battleMap.getTerrainType().getMovementModifier();

        // skills
        baseSpeed += Math.ceil(pilot.getSkills().get(Skill.Piloting) * 0.05d);

        // damage
        if (mech.getHp(BodyPart.LeftLeg) <= 0 || mech.getHp(BodyPart.LeftLeg) <= 0) {
            baseSpeed *= 0.5f;
        }

        // Stability
        if(mech.getStability() <= 50 && mech.getStability() > 25) {
            baseSpeed *= 0.75f;
        }

        if(mech.getStability() < 25) {
            baseSpeed *= 0.5f;
        }

        return baseSpeed;
    }
}
