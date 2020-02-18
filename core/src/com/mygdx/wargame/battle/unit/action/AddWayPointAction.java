package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.ui.WayPoint;

public class AddWayPointAction extends Action {

    private StageElementsStorage stageElementsStorage;
    private WayPoint wayPoint;

    public AddWayPointAction(StageElementsStorage stageElementsStorage, WayPoint wayPoint) {
        this.stageElementsStorage = stageElementsStorage;
        this.wayPoint = wayPoint;
    }

    @Override
    public boolean act(float delta) {
        stageElementsStorage.groundLevel.addActor(wayPoint);
        stageElementsStorage.wayPoints.add(wayPoint);
        return true;
    }
}
