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


    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        Vector2 v2 = isoUtils.worldToScreen(x, y);

        getFrame((int) x, (int) y);

        batch.draw(textureRegion, v2.x + 10, v2.y + 20);
    }

    public TextureRegion getFrame(int x, int y) {
        int value = 0;
        if (battleMap.getMarker(x, y + 1)) value += 1;
        if (battleMap.getMarker(x + 1, y )) value += 2;
        if (battleMap.getMarker(x, y - 1)) value += 4;
        if (battleMap.getMarker(x - 1, y)) value += 8;

        textureRegion.setRegion( (value % 4)  * 128, (value / 4) * 64, 128, 64);

        return textureRegion;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
