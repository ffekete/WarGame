package com.mygdx.wargame.battle.map.info;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.screen.IsoUtils;

public class MovementPathMarker extends TextureRegionDrawable {

    private IsoUtils isoUtils = new IsoUtils();
    private TextureRegion textureRegion;
    private BattleMap battleMap;
    private int x, y;

    public MovementPathMarker(Texture texture, BattleMap battleMap, int x, int y) {
        this.textureRegion = new TextureRegion(texture);
        this.battleMap = battleMap;
        this.x = x;
        this.y = y;
    }

    public TextureRegion getFrame(int x, int y) {
        return textureRegion;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
