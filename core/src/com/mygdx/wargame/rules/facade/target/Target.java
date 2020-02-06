package com.mygdx.wargame.rules.facade.target;

import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;

public class Target {

    private Pilot pilot;
    private Mech mech;

    public Target(Mech mech, Pilot pilot) {
        this.pilot = pilot;
        this.mech = mech;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public Mech getMech() {
        return mech;
    }
}
