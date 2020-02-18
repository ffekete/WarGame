package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.ui.WayPoint;

public class RemoveOneWayPointAction extends Action {

    private StageElementsStorage stageElementsStorage;
    private WayPoint wayPoint;
private float nx, ny;

    public RemoveOneWayPointAction(StageElementsStorage stageElementsStorage, float nx, float ny) {
        this.stageElementsStorage = stageElementsStorage;
        this.nx = nx;
        this.ny = ny;
    }

    @Override
    public boolean act(float delta) {
        wayPoint = stageElementsStorage.wayPoints.stream().filter(wayPoint -> wayPoint.getX() == nx && wayPoint.getY() == ny).findFirst().get();
        stageElementsStorage.groundLevel.removeActor(wayPoint);
        stageElementsStorage.wayPoints.remove(wayPoint);
        return true;
    }
}
