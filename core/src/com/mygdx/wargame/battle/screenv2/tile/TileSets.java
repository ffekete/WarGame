package com.mygdx.wargame.battle.screenv2.tile;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public enum TileSets {

    GrassLand(
            ImmutableSet.<Class<? extends Tile>>builder()
                    .add(GrassTile.class)
                    .build()
    );

    private Set<Class<? extends Tile>> texturePaths;

    TileSets(Set<Class<? extends Tile>> texturePaths) {
        this.texturePaths = texturePaths;
    }

    public Set<Class<? extends Tile>> getTexturePaths() {
        return texturePaths;
    }
}
