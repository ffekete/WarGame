package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.screen.StageElementsStorage;

public class RemoveWayPointAction extends Action {

    private StageElementsStorage stageElementsStorage;

    public RemoveWayPointAction(StageElementsStorage stageElementsStorage) {
        this.stageElementsStorage = stageElementsStorage;
    }

    @Override
    public boolean act(float delta) {

        stageElementsStorage.wayPoints.forEach(wayPoint -> {
            stageElementsStorage.groundLevel.removeActor(wayPoint);
        });
        stageElementsStorage.wayPoints.clear();
        return true;
    }
}
