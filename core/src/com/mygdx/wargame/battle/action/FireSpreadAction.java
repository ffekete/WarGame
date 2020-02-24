package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.BattleMapConfig;
import com.mygdx.wargame.battle.screen.StageElementsStorage;

public class FireSpreadAction extends Action {

    private BattleMap battleMap;
    private StageElementsStorage stageElementsStorage;
    private AssetManager assetManager;

    public FireSpreadAction(BattleMap battleMap, StageElementsStorage stageElementsStorage, AssetManager assetManager) {
        this.battleMap = battleMap;
        this.stageElementsStorage = stageElementsStorage;
        this.assetManager = assetManager;
    }

    @Override
    public boolean act(float delta) {

        int[][] newMap = new int[BattleMapConfig.WIDTH][BattleMapConfig.HEIGHT];

        for (int i = 0; i < BattleMapConfig.WIDTH; i++)
            for (int j = 0; j < BattleMapConfig.HEIGHT; j++) {
                if (battleMap.getFireMap()[i][j] > 0) {
                    spreadFire(newMap, i - 1, j);
                    spreadFire(newMap, i - 1, j - 1);
                    spreadFire(newMap, i - 1, j + 1);
                    spreadFire(newMap, i + 1, j);
                    spreadFire(newMap, i + 1, j - 1);
                    spreadFire(newMap, i + 1, j + 1);
                    spreadFire(newMap, i, j + 1);
                    spreadFire(newMap, i, j - 1);
                }
            }

        for (int i = 0; i < BattleMapConfig.WIDTH; i++)
            for (int j = 0; j < BattleMapConfig.HEIGHT; j++) {
                if (battleMap.getFireMap()[i][j] == 0 && newMap[i][j] > 0) {
                    AddFireEffectAction fireEffectAction = new AddFireEffectAction(stageElementsStorage, assetManager, i, j, battleMap);
                    stageElementsStorage.mechLevel.addAction(fireEffectAction);
                }
            }

        return true;
    }

    private void spreadFire(int[][] newMap, int x, int y) {
        if (x >= 0 && x < BattleMapConfig.WIDTH && y >= 0 && y < BattleMapConfig.HEIGHT) {
            //stageElementsStorage.mechLevel.addAction(new AddFireEffectAction(stageElementsStorage, assetManager, x, y, battleMap));
            newMap[x][y] = 1;
        }
    }
}
