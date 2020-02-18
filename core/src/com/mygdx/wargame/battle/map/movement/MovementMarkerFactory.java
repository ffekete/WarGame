package com.mygdx.wargame.battle.map.movement;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.battle.ui.MovementMarker;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.util.MapUtils;

import java.util.Map;

public class MovementMarkerFactory {

    private Pool<MovementMarker> movementMarkerPool;
    private StageElementsStorage stageElementsStorage;
    private AssetManager assetManager;
    private MechInfoPanelFacade mechInfoPanelFacade;

    public MovementMarkerFactory(StageElementsStorage stageElementsStorage, AssetManager assetManager, MechInfoPanelFacade mechInfoPanelFacade) {
        this.stageElementsStorage = stageElementsStorage;
        this.assetManager = assetManager;
        movementMarkerPool = new Pool<MovementMarker>() {
            @Override
            protected MovementMarker newObject() {
                return new MovementMarker(assetManager, MovementMarkerFactory.this.mechInfoPanelFacade.getLabelStyle(), stageElementsStorage);
            }
        };
        this.mechInfoPanelFacade = mechInfoPanelFacade;
    }

    public void createMovementMarkers(BattleMap battleMap, Mech selectedMech) {
        battleMap.getNodeGraphLv1().reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int) selectedMech.getX()][(int) selectedMech.getY()]);

        Map<Node, Integer> availableNodes = new MapUtils().getAllAvailableWithMovementPointsCost(battleMap, selectedMech);

        availableNodes.forEach((node, movementPointsCost) -> {
            MovementMarker movementMarker = movementMarkerPool.obtain();
            movementMarker.setPosition(node.getX(), node.getY());
            movementMarker.setLabel(movementPointsCost);
            stageElementsStorage.groundLevel.addActor(movementMarker);
            stageElementsStorage.movementMarkerList.add(movementMarker);
        });
    }

    public void remove(MovementMarker movementMarker) {
        stageElementsStorage.groundLevel.removeActor(movementMarker);
        //stageElementsStorage.movementMarkerList.remove(movementMarker);
        movementMarkerPool.free(movementMarker);
    }
}
