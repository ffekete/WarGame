package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Overlay;
import com.mygdx.wargame.battle.map.TileOverlayType;

public class SetOverlayAction extends Action {

    private BattleMap battleMap;
    private int x;
    private int y;
    private TileOverlayType tileOverlayType;
    private AssetManager assetManager;

    public SetOverlayAction(BattleMap battleMap, int x, int y, TileOverlayType tileOverlayType, AssetManager assetManager) {
        this.battleMap = battleMap;
        this.x = x;
        this.y = y;
        this.tileOverlayType = tileOverlayType;
        this.assetManager = assetManager;
    }

    @Override
    public boolean act(float delta) {
        battleMap.getNodeGraphLv1().getNodeWeb()[x][y].setDecorationOverlay(new Overlay(tileOverlayType));
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

        cell.setTile(new StaticTiledMapTile(new TextureRegion(assetManager.get("objects/Crater.png", Texture.class))));

        battleMap.getLayer(2).setCell(x, y, cell);
        return true;
    }
}
