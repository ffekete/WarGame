package com.mygdx.wargame.battle.combat;

import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.common.pilot.Pilot;

public interface AttackCalculator {
    void calculate(Pilot attackerPilot, AbstractMech attacker, AbstractMech defender, Pilot defenderPilot, BodyPart targetedBodyPart);
}
