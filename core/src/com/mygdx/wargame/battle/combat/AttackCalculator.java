package com.mygdx.wargame.battle.combat;

import com.mygdx.wargame.mech.AbstractMech;

public interface AttackCalculator {
    void calculate(AbstractMech attacker, AbstractMech defender);
}
