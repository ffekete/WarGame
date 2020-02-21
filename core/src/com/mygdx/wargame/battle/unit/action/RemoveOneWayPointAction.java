package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.movement.WayPoint;

import java.util.Optional;

public class RemoveOneWayPointAction extends Action {

    private StageElementsStorage stageElementsStorage;
    private float nx, ny;

    public RemoveOneWayPointAction(StageElementsStorage stageElementsStorage, float nx, float ny) {
        this.stageElementsStorage = stageElementsStorage;
        this.nx = nx;
        this.ny = ny;
    }

    @Override
    public boolean act(float delta) {
        Optional<WayPoint> wayPointOptional = stageElementsStorage.wayPoints.stream().filter(wayPoint -> wayPoint.getX() == nx && wayPoint.getY() == ny).findFirst();
        if (wayPointOptional.isPresent()) {
            stageElementsStorage.groundLevel.removeActor(wayPointOptional.get());
            stageElementsStorage.wayPoints.remove(wayPointOptional.get());
        }
        return true;
    }
}
