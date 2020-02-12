package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

public class Node extends Actor {

    private int index;
    private TextureRegion texture;
    private Overlay decorationOverlay;
    private Overlay groundOverlay;
    private GroundOverlayConfig groundOverlayConfig;
    private NodeGraph nodeGraph;

    public Node(float x, float y, AssetManager assetManager, NodeGraph nodeGraph) {
        this.nodeGraph = nodeGraph;
        this.setX(x);
        this.setY(y);
        groundOverlayConfig = new GroundOverlayConfig(assetManager);

        this.texture = new TextureRegion(assetManager.get("Grassland.png", Texture.class));

        int n = new Random().nextInt(32);
        texture.setRegion(n, n, 32, 32);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        batch.draw(texture, getX(), getY(), 1 ,1);

        if(groundOverlay != null) {
            batch.draw(groundOverlayConfig.getFor(groundOverlay.getTileOverlayType()).getFor(nodeGraph, (int)getX(), (int)getY(), 1), getX(), getY(), 1 ,1);
        }

        if(decorationOverlay != null) {
            batch.draw(decorationOverlay.getOverlay(), getX(), getY(), 1 ,1);
        }
    }

    public void setDecorationOverlay(Overlay decorationOverlay) {
        this.decorationOverlay = decorationOverlay;
    }

    public void setGroundOverlay(Overlay groundOverlay) {
        this.groundOverlay = groundOverlay;
    }

    public Overlay getGroundOverlay() {
        return groundOverlay;
    }
}