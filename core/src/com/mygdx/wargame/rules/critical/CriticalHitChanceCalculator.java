package com.mygdx.wargame.rules.critical;

import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;

public interface CriticalHitChanceCalculator {
    int calculate(Pilot pilot, Mech mech, Weapon weapon);
}
