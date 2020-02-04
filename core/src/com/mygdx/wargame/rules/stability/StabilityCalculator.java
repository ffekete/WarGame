package com.mygdx.wargame.rules.stability;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;

public interface StabilityCalculator {

    int calculate(Pilot pilot, Mech mech, BattleMap battleMap, Weapon targetingWeapon);

}
