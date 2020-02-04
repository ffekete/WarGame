package com.mygdx.wargame.rules.movement;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;

public interface MovementSpeedCalculator {
    int calculate(Pilot pilot, Mech mech, BattleMap battleMap);
}
