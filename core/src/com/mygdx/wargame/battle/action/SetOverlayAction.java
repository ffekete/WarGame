package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;

public class SetOverlayAction extends Action {

    private BattleMap battleMap;
    private int x;
    private int y;
    private AssetManager assetManager;

    public SetOverlayAction(BattleMap battleMap, int x, int y, AssetManager assetManager) {
        this.battleMap = battleMap;
        this.x = x;
        this.y = y;
        this.assetManager = assetManager;
    }

    @Override
    public boolean act(float delta) {
        battleMap.getNodeGraphLv1().getNodeWeb()[x][y].setOverlay(assetManager.get("objects/Crater.png", Texture.class));
        return true;
    }
}
