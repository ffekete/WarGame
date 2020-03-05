package com.mygdx.wargame.battle.rules.calculator.hitchance;

import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Pilot;

public interface HitChanceCalculator {
    int calculate(Pilot pilot, Mech mech, Mech target, Weapon weapon);
}
