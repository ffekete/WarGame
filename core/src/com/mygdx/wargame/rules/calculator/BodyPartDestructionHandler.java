package com.mygdx.wargame.rules.calculator;

import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Mech;

public class BodyPartDestructionHandler {

    public void destroy(Mech mech, BodyPart bodyPart) {

        //System.out.println("Destroyed " + mech.getName() + " " + bodyPart);

        mech.getComponents(bodyPart).forEach(b -> {
            b.setStatus(Status.Destroyed);
        });

    }

}
