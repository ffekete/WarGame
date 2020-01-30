package com.mygdx.wargame.battle.combat;

import com.mygdx.wargame.battle.unit.AbstractMech;

public interface AttackCalculator {
    void calculate(AbstractMech attacker, AbstractMech defender);
}
