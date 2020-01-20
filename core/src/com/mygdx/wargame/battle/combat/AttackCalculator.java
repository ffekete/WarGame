package com.mygdx.wargame.battle.combat;

import com.mygdx.wargame.battle.unit.Unit;

public interface AttackCalculator {
    void calculate(Unit attacker, Unit defender);
}
