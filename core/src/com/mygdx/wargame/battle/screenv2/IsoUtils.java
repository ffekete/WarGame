package com.mygdx.wargame.battle.screenv2;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class IsoUtils {

    private static final float TILE_WIDTH = 32;
    private static final float TILE_HEIGHT = 16;

    private Matrix4 isoTransform;
    private Matrix4 invIsotransform;

    public IsoUtils() {
        //create the isometric transform
        isoTransform = new Matrix4();
        isoTransform.idt();
        isoTransform.translate(0.0f, 0.25f, 0.0f);
        isoTransform.scale((float) (Math.sqrt(2.0) / 2.0), (float) (Math.sqrt(2.0) / 4.0), 1.0f);
        isoTransform.rotate(0.0f, 0.0f, 1.0f, -45.0f);

        //... and the inverse matrix
        invIsotransform = new Matrix4(isoTransform);
        invIsotransform.inv();
    }

    public Vector2 worldToCell(float x, float y) {
        float halfTileWidth = TILE_WIDTH * 0.5f;
        float halfTileHeight = TILE_HEIGHT * 0.5f;

        float row = (1.0f / 2) * (x / halfTileWidth + y / halfTileHeight);
        float col = (1.0f / 2) * (x / halfTileWidth - y / halfTileHeight);

        return new Vector2((int) col, (int) row);
    }

    public Vector2 screenToWorld(float x, float y, Camera camera) {
        Vector3 touch = new Vector3(x, y, 0);
        camera.unproject(touch);
        touch.mul(invIsotransform);
        touch.mul(isoTransform);
        return new Vector2(touch.x, touch.y);
    }


    public Vector2 screenToCell(float x, float y, Camera camera) {
        Vector2 world = screenToWorld(x, y, camera);
        world.y -= TILE_HEIGHT * 0.5f;
        return worldToCell(world.x, world.y);
    }
}
