package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.movement.MovementMarkerFactory;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.mech.Mech;

public class AddMovementMarkersAction extends Action {

    private StageElementsStorage stageElementsStorage;
    private MovementMarkerFactory movementMarkerFactory;
    private BattleMap battleMap;
    private Mech mech;

    public AddMovementMarkersAction(StageElementsStorage stageElementsStorage, MovementMarkerFactory movementMarkerFactory, BattleMap battleMap, Mech mech) {
        this.stageElementsStorage = stageElementsStorage;
        this.movementMarkerFactory = movementMarkerFactory;
        this.battleMap = battleMap;
        this.mech = mech;
    }

    @Override
    public boolean act(float delta) {
        stageElementsStorage.movementMarkerList.forEach(movementMarker -> stageElementsStorage.groundLevel.removeActor(movementMarker));
        movementMarkerFactory.createMovementMarkers(battleMap, mech);
        return true;
    }
}
