
package com.mygdx.wargame.battle.map.decoration;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;

/**
 * @brief Represents a non changing {@link TiledMapTile} (can be cached)
 */
public class AnimatedTiledMapTile implements TiledMapTile {

    private int id;

    private BlendMode blendMode = BlendMode.ALPHA;

    private MapProperties properties;

    private MapObjects objects;

    private AnimatedDrawableTile animatedDrawableTile;

    private float offsetX;

    private float offsetY;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public BlendMode getBlendMode() {
        return blendMode;
    }

    @Override
    public void setBlendMode(BlendMode blendMode) {
        this.blendMode = blendMode;
    }

    @Override
    public MapProperties getProperties() {
        if (properties == null) {
            properties = new MapProperties();
        }
        return properties;
    }

    @Override
    public MapObjects getObjects() {
        if (objects == null) {
            objects = new MapObjects();
        }
        return objects;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return animatedDrawableTile.getRegion();
    }

    @Override
    public void setTextureRegion(TextureRegion textureRegion) {

    }

    @Override
    public float getOffsetX() {
        return offsetX;
    }

    @Override
    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    @Override
    public float getOffsetY() {
        return offsetY;
    }

    @Override
    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public AnimatedTiledMapTile(AnimatedDrawableTile animatedDrawableTile) {
        this.animatedDrawableTile = animatedDrawableTile;
    }

    public AnimatedDrawableTile getAnimatedDrawableTile() {
        return animatedDrawableTile;
    }
}
