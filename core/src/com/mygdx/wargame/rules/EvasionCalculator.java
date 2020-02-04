package com.mygdx.wargame.rules;

import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;

public class EvasionCalculator {

    int calculate(Pilot pilot, Mech mech) {
        int baseEvasion = mech.getRemainingMovementPoints();

        if(pilot.hasPerk(Perks.Agile)) {
            baseEvasion += 5;
        }

        return baseEvasion;
    }

}
