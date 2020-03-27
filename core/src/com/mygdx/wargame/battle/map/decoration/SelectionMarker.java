package com.mygdx.wargame.battle.map.decoration;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.wargame.battle.screen.IsoUtils;
import com.mygdx.wargame.battle.screen.IsometricAnimatedSprite;

public class SelectionMarker extends IsometricAnimatedSprite {

    public SelectionMarker(Texture texture) {
        super(texture, 30);
    }
}
