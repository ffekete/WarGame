package com.mygdx.wargame.battle.map.action;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.decoration.AnimatedDrawableTile;
import com.mygdx.wargame.battle.map.decoration.AnimatedTiledMapTile;
import com.mygdx.wargame.battle.map.tile.Tile;
import com.mygdx.wargame.battle.screen.IsoUtils;

public class DestroyTileAction extends Action {

    private BattleMap battleMap;
    private int x, y;
    private AssetManager assetManager;

    public DestroyTileAction(BattleMap battleMap, int x, int y, AssetManager assetManager) {
        this.battleMap = battleMap;
        this.x = x;
        this.y = y;
        this.assetManager = assetManager;
    }

    @Override
    public boolean act(float delta) {
        TiledMapTileLayer layer = (TiledMapTileLayer) battleMap.getTiledMap().getLayers().get("groundLayer");

        Tile destroyedTile = battleMap.getTile(x,y).getDestroyedTile();
        if (destroyedTile != null) {
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(new AnimatedTiledMapTile(new AnimatedDrawableTile(assetManager, destroyedTile, 0.1f, 1, IsoUtils.TILE_WIDTH, IsoUtils.TILE_HEIGHT)));
            if(destroyedTile.isImpassable()) {
                battleMap.getNodeGraph().setImpassable(x, y);
            }
            battleMap.getNodeGraph().getNodeWeb()[x][y].setTile(destroyedTile);
            layer.setCell(x, y, cell);


        }
        return true;
    }
}
