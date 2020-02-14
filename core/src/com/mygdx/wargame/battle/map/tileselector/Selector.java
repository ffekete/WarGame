package com.mygdx.wargame.battle.map.tileselector;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.wargame.battle.map.NodeGraph;

public interface Selector {
    TextureRegion getFor(NodeGraph map, int x, int y, int skip);
}
