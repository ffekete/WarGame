package com.mygdx.wargame.battle.map.decorator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Pool;
import com.google.common.collect.ImmutableList;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.BattleMapConfig;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.unit.action.AddActorAction;
import com.mygdx.wargame.battle.unit.action.RemoveCustomActorAction;
import com.mygdx.wargame.config.Config;

import java.util.List;
import java.util.Random;

import static com.mygdx.wargame.config.Config.VIEWPORT_WIDTH;

public class CloudGenerator {

    private List<Texture> samples;
    private Pool<Image> cloudPool;

    private AssetManager assetManager;
    private StageElementsStorage stageElementsStorage;

    public CloudGenerator(AssetManager assetManager, StageElementsStorage stageElementsStorage) {

        this.assetManager = assetManager;
        this.stageElementsStorage = stageElementsStorage;

        this.samples = ImmutableList.<Texture>builder()
                .add(assetManager.get("cloud/Cloud01.png", Texture.class))
                .add(assetManager.get("cloud/Cloud02.png", Texture.class))
                .add(assetManager.get("cloud/Cloud03.png", Texture.class))
                .add(assetManager.get("cloud/Cloud04.png", Texture.class))
                .build();

        cloudPool = new Pool<Image>() {
            @Override
            protected Image newObject() {
                Image image =  new Image(samples.get(new Random().nextInt(samples.size())));
                image.setTouchable(Touchable.disabled);
                return image;
            }
        };
    }

    public void update() {
        if (new Random().nextInt(Config.CLOUD_DENSITY) == 0) {
            float y = new Random().nextFloat() * BattleMapConfig.HEIGHT;

            boolean flip = new Random().nextBoolean();
            SequenceAction sequenceAction = addCloud(y, "FFFFFF66", flip);
            SequenceAction sequenceAction2 = addShadow(y - 5, "00000044", flip, sequenceAction.getActor());

            stageElementsStorage.airLevel.addAction(sequenceAction);
            stageElementsStorage.airLevel.addAction(sequenceAction2);
        }
    }

    private SequenceAction addCloud(float y, String color, boolean flipx) {
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(BattleMapConfig.WIDTH, y);
        moveToAction.setStartPosition(0, y);

        moveToAction.setDuration(200f);

        Image image = cloudPool.obtain();

        image.setSize(2 * image.getWidth() / 16, 2 * image.getHeight() / 16);

        image.setColor(Color.valueOf(color));
        image.setPosition(0, y);

        image.setRotation(flipx ? 180 : 0);

        moveToAction.setActor(image);

        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(new AddActorAction(stageElementsStorage.airLevel, image));
        sequenceAction.addAction(moveToAction);
        sequenceAction.addAction(new RemoveCustomActorAction(stageElementsStorage.airLevel, image, cloudPool));
        sequenceAction.setActor(image);
        return sequenceAction;
    }

    private SequenceAction addShadow(float y, String color, boolean flipx, Actor forThis) {
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(BattleMapConfig.WIDTH, y);
        moveToAction.setStartPosition(0, y);

        moveToAction.setDuration(200f);

        Image image = new Image(((Image)forThis).getDrawable());

        image.setSize(2 * image.getWidth() / 16, 2 * image.getHeight() / 16);

        image.setColor(Color.valueOf(color));
        image.setPosition(0, y);

        image.setRotation(flipx ? 180 : 0);

        moveToAction.setActor(image);

        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(new AddActorAction(stageElementsStorage.airLevel, image));
        sequenceAction.addAction(moveToAction);
        sequenceAction.addAction(new RemoveCustomActorAction(stageElementsStorage.airLevel, image, cloudPool));
        return sequenceAction;
    }

}
