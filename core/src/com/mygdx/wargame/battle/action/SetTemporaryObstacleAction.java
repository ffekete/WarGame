package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;

public class SetTemporaryObstacleAction extends Action {

    private BattleMap battleMap;
    private int x,y;

    public SetTemporaryObstacleAction(BattleMap battleMap, int x, int y) {
        this.battleMap = battleMap;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean act(float delta) {
        battleMap.setTemporaryObstacle(x, y);
        return true;
    }
}
