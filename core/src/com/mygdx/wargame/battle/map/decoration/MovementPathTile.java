package com.mygdx.wargame.battle.map.decoration;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.info.MovementPathMarker;

public class MovementPathTile extends TextureRegionDrawable {

    private MovementPathMarker movementPathMarker;

    public MovementPathTile(Texture texture, BattleMap battleMap, int x, int y) {
        this.movementPathMarker = new MovementPathMarker(texture, battleMap, x, y);
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        movementPathMarker.draw(batch, x, y, width, height);
        
    }

    @Override
    public TextureRegion getRegion() {
        return movementPathMarker.getFrame(movementPathMarker.getX(), movementPathMarker.getY());
    }
}
