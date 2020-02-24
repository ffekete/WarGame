package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.decoration.TreeImage;
import com.mygdx.wargame.battle.map.fire.FireEffect;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.util.MapUtils;

import java.util.ArrayList;
import java.util.List;

public class AddFireEffectAction extends Action {

    private StageElementsStorage stageElementsStorage;
    private AssetManager assetManager;
    private float x, y;
    private BattleMap battleMap;
    private MapUtils mapUtils;
    private List<TreeImage> treesOnTile;

    public AddFireEffectAction(StageElementsStorage stageElementsStorage, AssetManager assetManager, float x, float y, BattleMap battleMap) {
        this.stageElementsStorage = stageElementsStorage;
        this.assetManager = assetManager;
        this.x = x;
        this.y = y;
        this.battleMap = battleMap;
        this.mapUtils = new MapUtils();
        treesOnTile = mapUtils.nrOfTreesOnTile(stageElementsStorage, x, y);
    }

    @Override
    public boolean act(float delta) {



        if (battleMap.getFireMap()[(int) x][(int) y] == 0 && treesOnTile.size() > 0) {

            treesOnTile.forEach(treeImage -> {
                stageElementsStorage.mechLevel.addActor(new FireEffect(assetManager, treeImage.getX(), treeImage.getY()));
                battleMap.getFireMap()[(int) x][(int) y] = 1;
            });

        }
        return true;
    }

}
