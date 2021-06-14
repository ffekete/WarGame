package com.mygdx.wargame.util;

import com.badlogic.gdx.math.Vector2;

public class MathUtils {

    public static double getDistance(float x, float y, float x2, float y2) {
        return Math.sqrt(Math.abs(x - x2) * Math.abs(x - x2) + Math.abs(y - y2) * Math.abs(y - y2));
    }

    public static float getAngle(double[] from, double[] target) {
        float angle = (float) Math.toDegrees(Math.atan2(target[1] - from[1], target[0] - from[0]));

        if (angle < 0) {
            angle += 360;
        }

        return 1 * angle;
    }

    public static float getAngle(Vector2 s, Vector2 e) {
        return (float) (Math.atan2(
                e.y - s.y,
                e.x - s.x
        ) * 180.0d / Math.PI);
    }

    public static double ISO = (-45 - 90) / 180 * Math.PI;

    public static float convertToIso(float angle) {
        return (float) Math.atan2(Math.sin(angle), Math.cos(angle - (ISO)));
    }
}
