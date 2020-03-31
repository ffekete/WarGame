package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.util.MapUtils;

import java.util.Map;

public class AddMovementMarkersAction extends Action {

    private BattleMap battleMap;
    private AbstractMech attackingMech;

    public AddMovementMarkersAction(BattleMap battleMap, AbstractMech attackingMech) {
        this.battleMap = battleMap;
        this.attackingMech = attackingMech;
    }


    @Override
    public boolean act(float delta) {

        //battleMap.getNodeGraph().reconnectCities((int)attackingMech.getX(), (int)attackingMech.getY());
        Map<Node, Integer> allAvailable = new MapUtils().getAllAvailableWithMovementPointsCost(battleMap, attackingMech);
        //battleMap.getNodeGraph().disconnectCities((int)attackingMech.getX(), (int)attackingMech.getY());
        allAvailable.forEach((k,v) -> {
            battleMap.addMovementMarker((int)k.getX(), (int)k.getY());
        });
        return true;
    }
}
