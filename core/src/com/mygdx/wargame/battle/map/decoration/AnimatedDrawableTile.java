package com.mygdx.wargame.battle.map.decoration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.map.tile.Tile;
import com.mygdx.wargame.battle.map.tile.TileState;

import java.util.Random;

public class AnimatedDrawableTile extends TextureRegionDrawable {

    private float speed;
    private int idle;
    private int size;
    private int damagedSize;
    private int idleCounter = 0;
    private TextureRegion textureRegionIntact;
    private TextureRegion textureRegionDamaged;
    private float counter = 0f;
    private int step;
    private int frameWidth;
    private int frameHeight;
    private Tile tile;

    public AnimatedDrawableTile(AssetManager assetManager, Tile tile, float speed, int idle, int width, int height) {
        super(getTextureRegionIntact(assetManager, tile, width, height));
        this.idle = idle;
        this.speed = speed;

        textureRegionIntact = getTextureRegionIntact(assetManager, tile, width, height);
        textureRegionDamaged = getTextureRegionDamaged(assetManager, tile, width, height);

        size = textureRegionIntact.getTexture().getWidth() / width;
        damagedSize = tile.canBeDestroyed() ? textureRegionDamaged.getTexture().getWidth() / width : 0;
        this.idle = idle;
        this.idleCounter = new Random().nextInt(idle);
        this.frameWidth = width;
        this.frameHeight = height;

        this.tile = tile;
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        stepCounter();

        if(tile.getTileState() == TileState.Intact) {
            textureRegionIntact.setRegion(step * frameWidth, 0, frameWidth, frameHeight);
            batch.draw(textureRegionIntact, x, y, width, height);
        }
        else {
            textureRegionDamaged.setRegion(step * frameWidth, 0, frameWidth, frameHeight);
            batch.draw(textureRegionDamaged, x, y, width, height);
        }
    }

    private static TextureRegion getTextureRegionIntact(AssetManager assetManager, Tile tile, int frameWidth, int frameHeight) {
        TextureRegion textureRegion = new TextureRegion(assetManager.get(tile.getPath(), Texture.class));

        textureRegion.setRegion(0, 0, frameWidth, frameHeight);
        return textureRegion;
    }

    private static TextureRegion getTextureRegionDamaged(AssetManager assetManager, Tile tile, int frameWidth, int frameHeight) {
        if(!tile.canBeDestroyed())
            return null;

        TextureRegion textureRegion = new TextureRegion(assetManager.get(tile.getDestroyedPath(), Texture.class));

        textureRegion.setRegion(0, 0, frameWidth, frameHeight);
        return textureRegion;
    }

    public void restart() {
        idleCounter = 0;
        step = 0;
    }

    @Override
    public TextureRegion getRegion() {

        if(tile.getTileState() == TileState.Intact)
            stepCounter();
        else
            stepDamagedAnimationCounter();

        if(tile.getTileState() == TileState.Intact) {
            textureRegionIntact.setRegion(step * frameWidth, 0, frameWidth, frameHeight);
            return textureRegionIntact;
        }
        else {
            textureRegionDamaged.setRegion(step * frameWidth, 0, frameWidth, frameHeight);
            return textureRegionDamaged;
        }
    }

    private void stepCounter() {
        counter += Gdx.graphics.getDeltaTime();

        if (idleCounter == 0) {
            if (counter >= speed) {
                step = (step + 1);
                counter = 0;
                if (step >= size) {
                    idleCounter++;
                    step = 0;
                }
            }
        } else {
            idleCounter = (idleCounter + 1) % idle;
        }
    }

    private void stepDamagedAnimationCounter() {
        counter += Gdx.graphics.getDeltaTime();

        if (idleCounter == 0) {
            if (counter >= speed) {
                step = (step + 1);
                counter = 0;
                if (step >= damagedSize) {
                    idleCounter++;
                    step = 0;
                }
            }
        } else {
            idleCounter = (idleCounter + 1) % idle;
        }
    }

    public Tile getTile() {
        return tile;
    }
}
