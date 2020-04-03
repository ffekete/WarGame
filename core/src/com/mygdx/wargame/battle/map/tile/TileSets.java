package com.mygdx.wargame.battle.map.tile;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.List;
import java.util.Set;

public enum TileSets {

    GrassLand(
            ImmutableList.<TileLayers>builder()
                    .add(new TileLayers(GrassTile.class, null))
                    .add(new TileLayers(GrassTile.class, null))
                    .add(new TileLayers(GrassTile.class, null))
                    .add(new TileLayers(GrassTile.class, null))
                    .add(new TileLayers(GrassTile.class, null))
                    .add(new TileLayers(GrassTile.class, null))
                    .add(new TileLayers(GrassTile.class, null))
                    .add(new TileLayers(GrassTile.class, null))
                    .add(new TileLayers(GrassTile.class, null))
                    .add(new TileLayers(GrassTile.class, null))
                    .add(new TileLayers(GrassCityTile.class, null))
//                    .add(GrassForestLakeTile.class)

                    .add(new TileLayers(GrassTileLowLand.class, null))

                    .add(new TileLayers(GrassHillsTile.class, null))

                    .add(new TileLayers(GrassForestTile.class, null))
                    .add(new TileLayers(GrassForestTile.class, null))
                    .add(new TileLayers(GrassForestTile.class, null))
                    .add(new TileLayers(GrassForestTile.class, null))
                    .add(new TileLayers(GrassForestTile.class, null))

                    .add(new TileLayers(GrassMountainTile.class, null))
//                    .add(GrassPowerPlantTile.class)
                    .build()
    ),
    Desert(
            ImmutableList.<TileLayers>builder()
                    .add(new TileLayers(GrassTile.class, null))
                    .build()
    ),
    Swamp(
            ImmutableList.<TileLayers>builder()
                    .add(new TileLayers(GrassTile.class, null))
                    .build()
    ),
    Jungle(
            ImmutableList.<TileLayers>builder()
                    .add(new TileLayers(GrassTile.class, null))
                    .build()
    ),
    Snow(
            ImmutableList.<TileLayers>builder()
                    .add(new TileLayers(GrassTile.class, null))
                    .build()
    );

    private List<TileLayers> texturePaths;

    TileSets(List<TileLayers> texturePaths) {
        this.texturePaths = texturePaths;
    }

    public List<TileLayers> getTexturePaths() {
        return texturePaths;
    }
}
