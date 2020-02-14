package com.mygdx.wargame.rules.calculator;

import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.mech.Mech;

public class FlankingCalculator {

    public boolean isFlankedFromPosition(float x, float y, Mech targetMech) {
        if (targetMech.getDirection() == Direction.Left) {
            if(x >= targetMech.getX()) return true;
        } else if (targetMech.getDirection() == Direction.Right) {
            if(x <= targetMech.getX()) return true;
        }

        return false;
    }

}
