package com.mygdx.wargame.rules.range;

import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;

public interface RangeCalculator {

    int calculate(Pilot pilot, Mech mech);

}
