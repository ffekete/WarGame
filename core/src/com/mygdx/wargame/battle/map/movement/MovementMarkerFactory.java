package com.mygdx.wargame.battle.map.movement;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.ui.MovementMarker;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.util.MapUtils;

import java.util.List;

public class MovementMarkerFactory {

    private Pool<MovementMarker> movementMarkerPool;
    private StageElementsStorage stageElementsStorage;
    private AssetManager assetManager;

    public MovementMarkerFactory(StageElementsStorage stageElementsStorage, AssetManager assetManager) {
        this.stageElementsStorage = stageElementsStorage;
        this.assetManager = assetManager;
        movementMarkerPool = new Pool<MovementMarker>() {
            @Override
            protected MovementMarker newObject() {
                return new MovementMarker(assetManager);
            }
        };
    }

    public void createMovementMarkers(BattleMap battleMap, Mech selectedMech) {
        stageElementsStorage.movementMarkerList.forEach(movementMarker -> stageElementsStorage.groundLevel.removeActor(movementMarker));

        battleMap.getNodeGraphLv1().reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()]);

        List<Node> availableNodes = new MapUtils().getAllAvailable(battleMap, selectedMech);
        availableNodes.forEach(node -> {
            MovementMarker movementMarker = movementMarkerPool.obtain();
            movementMarker.setPosition(node.getX(), node.getY());
            stageElementsStorage.groundLevel.addActor(movementMarker);
            stageElementsStorage.movementMarkerList.add(movementMarker);
        });
    }
}
