package com.mygdx.wargame.battle.map.decoration;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.map.BattleMap;

public class DrawableMarker extends TextureRegionDrawable {

    private TextureRegion textureRegion;

    public DrawableMarker(Texture texture) {
        this.textureRegion = new TextureRegion(texture);
    }

    @Override
    public TextureRegion getRegion() {
        return textureRegion;
    }
}
