package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.decoration.SelectionMarker;
import com.mygdx.wargame.battle.map.render.IsometricTiledMapRendererWithSprites;
import com.mygdx.wargame.battle.screen.AssetManagerLoaderV2;
import com.mygdx.wargame.common.mech.AbstractMech;

public class CreateSelectionMarkerAction extends Action {

    private IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites;
    private AssetManagerLoaderV2 assetManagerLoaderV2;
    private AbstractMech abstractMech;

    public CreateSelectionMarkerAction(IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites, AssetManagerLoaderV2 assetManagerLoaderV2, AbstractMech abstractMech) {
        this.isometricTiledMapRendererWithSprites = isometricTiledMapRendererWithSprites;
        this.assetManagerLoaderV2 = assetManagerLoaderV2;
        this.abstractMech = abstractMech;
    }

    @Override
    public boolean act(float delta) {
        isometricTiledMapRendererWithSprites.removeAll(SelectionMarker.class);
        SelectionMarker selectionMarker = new SelectionMarker(assetManagerLoaderV2.getAssetManager().get("info/SelectionMarker.png", Texture.class));
        selectionMarker.setPosition(abstractMech.getX() - 0.75f, abstractMech.getY() + 0.75f);
        isometricTiledMapRendererWithSprites.addObject(selectionMarker);
        return true;
    }
}
