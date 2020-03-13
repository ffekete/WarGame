package com.mygdx.wargame.battle.map.tile;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public enum TileSets {

    GrassLand(
            ImmutableSet.<Class<? extends Tile>>builder()
                    .add(GrassTile.class)
                    .add(GrassCityTile.class)
                    .add(GrassForestLakeTile.class)
                    .add(GrassForestTile.class)
                    .add(GrassMountainTile.class)
                    .add(GrassPowerPlantTile.class)
                    .build()
    ),
    Desert(
            ImmutableSet.<Class<? extends Tile>>builder()
                    .add(GrassTile.class)
                    .build()
    ),
    Swamp(
            ImmutableSet.<Class<? extends Tile>>builder()
                    .add(GrassTile.class)
                    .build()
    ),
    Jungle(
            ImmutableSet.<Class<? extends Tile>>builder()
                    .add(GrassTile.class)
                    .build()
    ),
    Snow(
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
