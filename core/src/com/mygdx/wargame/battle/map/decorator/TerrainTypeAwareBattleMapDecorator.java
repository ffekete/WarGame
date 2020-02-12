package com.mygdx.wargame.battle.map.decorator;

import com.badlogic.gdx.assets.AssetManager;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.TerrainType;

import java.util.List;
import java.util.Map;

public class TerrainTypeAwareBattleMapDecorator {

    private AssetManager assetManager;

    private Map<TerrainType, List<Decorator>> decorators;

    private Map<Class<? extends Decorator>, Integer> stepsConfig;

    public TerrainTypeAwareBattleMapDecorator(AssetManager assetManager) {
        this.assetManager = assetManager;
        decorators = ImmutableMap.<TerrainType, List<Decorator>>builder()
                .put(TerrainType.Grassland, ImmutableList.of(new BattleMapTreeSpreadDecorator(assetManager), new BattleMapDirtSpreadDecorator(assetManager)))
                .build();

        stepsConfig = ImmutableMap.<Class<? extends Decorator>, Integer>builder()
                .put(BattleMapTreeSpreadDecorator.class, 3)
                .put(BattleMapDirtSpreadDecorator.class, 3)
                .build();
    }

    public void decorate(BattleMap battleMap) {
        decorators.get(battleMap.getTerrainType()).forEach(decorator -> {
            int step = stepsConfig.get(decorator.getClass());
            decorator.decorate(step, battleMap.getNodeGraphLv1());
        });
    }
}
