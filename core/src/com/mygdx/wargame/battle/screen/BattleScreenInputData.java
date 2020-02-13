package com.mygdx.wargame.battle.screen;

import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;

import java.util.HashMap;
import java.util.Map;

public class BattleScreenInputData {

    private Map<Mech, Pilot> group1 = new HashMap<>();
    private Map<Mech, Pilot> group2 = new HashMap<>();

    public Map<Mech, Pilot> getGroup1() {
        return group1;
    }

    public Map<Mech, Pilot> getGroup2() {
        return group2;
    }
}
