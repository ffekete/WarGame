package com.mygdx.wargame.battle.map.action;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.decoration.AnimatedTiledMapTile;
import com.mygdx.wargame.battle.map.tile.TileState;

public class DestroyTileAction extends Action {

    private BattleMap battleMap;
    private int x, y;

    public DestroyTileAction(BattleMap battleMap, int x, int y) {
        this.battleMap = battleMap;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean act(float delta) {
        TiledMapTileLayer layer = (TiledMapTileLayer) battleMap.getTiledMap().getLayers().get("groundLayer");

        if (((AnimatedTiledMapTile) layer.getCell(x, y).getTile()).getAnimatedDrawableTile().getTile().canBeDestroyed()) {
            ((AnimatedTiledMapTile) layer.getCell(x, y).getTile()).getAnimatedDrawableTile().getTile().setTileState(TileState.Destroyed);
        }
        return true;
    }
}
