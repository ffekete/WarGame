package com.mygdx.wargame.util;

public class MathUtils {

    public static float getAngle(double[] from, double[] target) {
        float angle = (float) Math.toDegrees(Math.atan2(target[1] - from[1], target[0] - from[0]));

        if(angle < 0){
            angle += 360;
        }

        return -1 * angle;
    }

}
