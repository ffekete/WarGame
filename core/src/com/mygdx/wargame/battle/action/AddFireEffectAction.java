package com.mygdx.wargame.battle.action;

import box2dLight.RayHandler;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.decoration.TreeImage;
import com.mygdx.wargame.battle.map.fire.FireEffect;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.util.MapUtils;

import java.util.List;

public class AddFireEffectAction extends Action {

    private StageElementsStorage stageElementsStorage;
    private AssetManager assetManager;
    private float x, y;
    private BattleMap battleMap;
    private MapUtils mapUtils;
    private List<TreeImage> treesOnTile;
    private RayHandler rayHandler;

    public AddFireEffectAction(StageElementsStorage stageElementsStorage, AssetManager assetManager, float x, float y, BattleMap battleMap, RayHandler rayHandler) {
        this.stageElementsStorage = stageElementsStorage;
        this.assetManager = assetManager;
        this.x = x;
        this.y = y;
        this.battleMap = battleMap;
        this.rayHandler = rayHandler;
        this.mapUtils = new MapUtils();
        treesOnTile = mapUtils.nrOfTreesOnTile(stageElementsStorage, x, y);
    }

    @Override
    public boolean act(float delta) {


        if (battleMap.getFireMap()[(int) x][(int) y] == 0 && treesOnTile.size() > 0) {

            treesOnTile.forEach(treeImage -> {
                stageElementsStorage.mechLevel.addActor(new FireEffect(assetManager, treeImage.getX(), treeImage.getY(), rayHandler));
                battleMap.getFireMap()[(int) x][(int) y] = 1;
            });

        }
        return true;
    }

}
