package com.mygdx.wargame.rules.calculator.hitchance;

import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;

public interface HitChanceCalculator {
    int calculate(Pilot pilot, Mech mech, Mech target, Weapon weapon);
}
