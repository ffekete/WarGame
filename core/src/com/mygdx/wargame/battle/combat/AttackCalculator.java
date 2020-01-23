package com.mygdx.wargame.battle.combat;

import com.mygdx.wargame.battle.unit.AbstractWarrior;

public interface AttackCalculator {
    void calculate(AbstractWarrior attacker, AbstractWarrior defender);
}
