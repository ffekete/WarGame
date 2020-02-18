package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.movement.MovementMarkerFactory;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.mech.Mech;

public class RemoveMovementMarkersAction extends Action {

    private StageElementsStorage stageElementsStorage;
    private MovementMarkerFactory movementMarkerFactory;

    public RemoveMovementMarkersAction(StageElementsStorage stageElementsStorage, MovementMarkerFactory movementMarkerFactory) {
        this.stageElementsStorage = stageElementsStorage;
        this.movementMarkerFactory = movementMarkerFactory;
    }

    @Override
    public boolean act(float delta) {
        stageElementsStorage.movementMarkerList.forEach(movementMarker -> {
            movementMarkerFactory.remove(movementMarker);
        });
        stageElementsStorage.movementMarkerList.clear();
        return true;
    }
}
