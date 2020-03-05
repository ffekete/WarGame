package com.mygdx.wargame.battle.rules.calculator;

import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Perks;
import com.mygdx.wargame.common.pilot.Pilot;

import java.util.Set;

public class MoraleSavingCalculator {

    public int calculate(Pilot targetPilot, Mech targetMech, Set<Pilot> defenders, Set<Pilot> attackers) {

        if (targetPilot.hasPerk(Perks.Crazy)) {
            return 100;
        }

        int baseValue = 100;

        int remainingHp = targetMech.getHp(BodyPart.Head)
                + targetMech.getHp(BodyPart.Torso)
                + targetMech.getHp(BodyPart.LeftArm)
                + targetMech.getHp(BodyPart.LeftLeg)
                + targetMech.getHp(BodyPart.RightArm)
                + targetMech.getHp(BodyPart.RightLeg);

        int maxHp = targetMech.getLeftHandMaxHp()
                + targetMech.getRightHandMaxHp()
                + targetMech.getLeftLegMaxHp()
                + targetMech.getRightLegMaxHp()
                + targetMech.getTorsoMaxHp()
                + targetMech.getHeadMaxHp();

        if ((float) remainingHp / (float) maxHp < 0.2f) {
            baseValue -= 100; // panic!
        }

        if (targetPilot.hasPerk(Perks.Cautious))
            baseValue -= 10;

        if (targetPilot.hasPerk(Perks.Brave))
            baseValue += 10;

        if (targetPilot.hasPerk(Perks.Leader) || defenders.stream().anyMatch(defender -> defender.hasPerk(Perks.Leader)))
            baseValue += 10 * defenders.stream().filter(a -> a.hasPerk(Perks.Leader)).count();

        if (targetPilot.hasPerk(Perks.Hero) || defenders.stream().anyMatch(defender -> defender.hasPerk(Perks.Hero)))
            baseValue += 20 * defenders.stream().filter(a -> a.hasPerk(Perks.Hero)).count();

        if (attackers.stream().anyMatch(attacker -> attacker.hasPerk(Perks.Dreaded)))
            baseValue -= 10 * attackers.stream().filter(a -> a.hasPerk(Perks.Dreaded)).count();

        return baseValue;

    }

}
