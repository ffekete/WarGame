package com.mygdx.wargame.battle.rules.calculator;

import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.common.component.weapon.Status;
import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.common.mech.Mech;

public class BodyPartDestructionHandler {

    public void destroy(Mech mech, BodyPart bodyPart) {

        System.out.println("Destroyed " + mech.getName() + " " + bodyPart);

        mech.getComponents(bodyPart).forEach(c -> {
            c.setStatus(Status.Destroyed);
        });

        mech.getAllWeapons(bodyPart).forEach(w -> {
            w.setStatus(Status.Destroyed);
        });

        if (bodyPart == BodyPart.Head || bodyPart == BodyPart.Torso) {
            mech.setState(State.Dead);
        }
    }

}
