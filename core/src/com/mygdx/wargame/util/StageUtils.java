package com.mygdx.wargame.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StageUtils {

    public static Vector2 convertBetweenStages(Stage fromStage, Stage toStage, float x, float y) {
        Vector2 v = fromStage.stageToScreenCoordinates(new Vector2(x, y));
        return toStage.screenToStageCoordinates(v);
    }
}
