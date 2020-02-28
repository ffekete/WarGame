package com.mygdx.wargame.battle.map.decorator;

import com.badlogic.gdx.assets.AssetManager;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.TerrainType;
import com.mygdx.wargame.battle.screen.StageElementsStorage;

import java.util.List;
import java.util.Map;

public class TerrainTypeAwareBattleMapDecorator {

    private AssetManager assetManager;
    private StageElementsStorage stageElementsStorage;

    private Map<TerrainType, List<Decorator>> decorators;

    private Map<Class<? extends Decorator>, Integer> stepsConfig;

    public TerrainTypeAwareBattleMapDecorator(AssetManager assetManager, StageElementsStorage stageElementsStorage) {
        this.assetManager = assetManager;
        decorators = ImmutableMap.<TerrainType, List<Decorator>>builder()
                .put(TerrainType.Grassland, ImmutableList.of(
                        new BattleMapWaterSpreadDecorator(assetManager),
                        new BattleMapTreeSpreadDecorator(assetManager, stageElementsStorage),
                        new BattleMapDirtSpreadDecorator(assetManager)))
                .build();
        this.stageElementsStorage = stageElementsStorage;

        stepsConfig = ImmutableMap.<Class<? extends Decorator>, Integer>builder()
                .put(BattleMapDirtSpreadDecorator.class, 3)
                .put(BattleMapWaterSpreadDecorator.class, 3)
                .put(BattleMapTreeSpreadDecorator.class, 3)
                .build();
    }

    public void decorate(BattleMap battleMap) {
        decorators.get(battleMap.getTerrainType()).forEach(decorator -> {
            int step = stepsConfig.get(decorator.getClass());
            decorator.decorate(step, battleMap);
        });
    }
}
