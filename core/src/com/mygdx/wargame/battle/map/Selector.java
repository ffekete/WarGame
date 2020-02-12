package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Selector {
    TextureRegion getFor(NodeGraph map, int x, int y, int skip);
}
