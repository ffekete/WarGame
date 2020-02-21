package com.mygdx.wargame.battle.combat;

import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.pilot.Pilot;

public interface AttackCalculator {
    void calculate(Pilot attackerPilot, AbstractMech attacker, AbstractMech defender, Pilot defenderPilot, BodyPart targetedBodyPart);
}
