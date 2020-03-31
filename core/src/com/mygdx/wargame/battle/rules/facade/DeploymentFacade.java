package com.mygdx.wargame.battle.rules.facade;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.render.IsometricTiledMapRendererWithSprites;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.FontCreator;
import com.mygdx.wargame.battle.screen.ui.HUDMediator;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.config.Config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DeploymentFacade {

    private IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites;

    private AbstractMech nextMech = null;
    private Pilot pilot;
    private boolean startup = true;
    private StageElementsStorage stageElementsStorage;
    private HUDMediator hudMediator;
    private Iterator<Map.Entry<AbstractMech, Pilot>> iterator;
    private Map<AbstractMech, Pilot> playerGroup;
    private Map<AbstractMech, Pilot> aiGroup;
    private List<AbstractMech> toDeploy = new ArrayList<>();
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    public DeploymentFacade(IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites, StageElementsStorage stageElementsStorage, HUDMediator hudMediator, Map<AbstractMech, Pilot> playerGroup, Map<AbstractMech, Pilot> aiGroup) {
        this.isometricTiledMapRendererWithSprites = isometricTiledMapRendererWithSprites;
        this.stageElementsStorage = stageElementsStorage;
        this.hudMediator = hudMediator;
        this.playerGroup = playerGroup;
        this.aiGroup = aiGroup;
        this.shapeRenderer = new ShapeRenderer();
        this.spriteBatch = new SpriteBatch();
    }

    public void process(BattleMap battleMap) {
        if (startup) {
            toDeploy.clear();
            SequenceAction sequenceAction = new SequenceAction();
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = FontCreator.getBitmapFont(60);
            labelStyle.fontColor = Color.GREEN;

            Label label = new Label("DEPLOY PHASE", labelStyle);
            label.setPosition(-300, Config.HUD_VIEWPORT_HEIGHT.get() / 2f);

            sequenceAction.addAction(new DelayAction(0.5f));

            MoveToAction moveToAction = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get() / 2f, Config.HUD_VIEWPORT_HEIGHT.get() / 2f);
            moveToAction.setDuration(0.15f);
            sequenceAction.addAction(moveToAction);

            MoveToAction moveToAction3 = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get() / 2f - 150, Config.HUD_VIEWPORT_HEIGHT.get() / 2f);
            moveToAction3.setDuration(2f);
            sequenceAction.addAction(moveToAction3);

            MoveToAction moveToAction2 = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get(), Config.HUD_VIEWPORT_HEIGHT.get() / 2f);
            moveToAction2.setDuration(0.15f);
            sequenceAction.addAction(moveToAction2);

            RemoveAction removeAction = new RemoveAction();
            removeAction.setTarget(label);
            sequenceAction.addAction(removeAction);

            startup = false;

            stageElementsStorage.hudStage.addActor(label);
            stageElementsStorage.hudStage.addAction(sequenceAction);

            toDeploy.addAll(playerGroup.keySet());


        } else {
            if (nextMech == null) {
                iterator = playerGroup.entrySet().iterator();
                Map.Entry<AbstractMech, Pilot> entry = iterator.next();
                nextMech = entry.getKey();
                pilot = entry.getValue();
                isometricTiledMapRendererWithSprites.addObject(nextMech);
            }

            shapeRenderer.setAutoShapeType(true);

            shapeRenderer.begin();
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.valueOf("666666"));
            for (int i = 0; i < toDeploy.size(); i++) {
                shapeRenderer.rect(i * 70 + 20, 10, 64, 64);
            }
            shapeRenderer.end();

            spriteBatch.begin();
            for (int i = 0; i < toDeploy.size(); i++) {
                spriteBatch.draw(toDeploy.get(i).getIsometricSprite().getTextureRegion(), i * 70 + 20, 10, 64, 64);
            }
            spriteBatch.end();
        }
    }

    private MoveToAction getMoveToAction(Label label, float tx, float ty) {
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setTarget(label);
        moveToAction.setPosition(tx, ty);
        return moveToAction;
    }

    public AbstractMech getNextMech() {
        return nextMech;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void deployed(AbstractMech abstractMech) {
        toDeploy.remove(abstractMech);

        if (iterator.hasNext()) {
            Map.Entry<AbstractMech, Pilot> entry = iterator.next();

            nextMech = entry.getKey();
            pilot = entry.getValue();
            isometricTiledMapRendererWithSprites.addObject(nextMech);
        } else {

            SequenceAction sequenceAction = new SequenceAction();
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = FontCreator.getBitmapFont(60);
            labelStyle.fontColor = Color.GREEN;

            Label label = new Label("BATTLE PHASE", labelStyle);
            label.setPosition(-300, Config.HUD_VIEWPORT_HEIGHT.get() / 2f);

            sequenceAction.addAction(new DelayAction(0.5f));

            MoveToAction moveToAction = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get() / 2f, Config.HUD_VIEWPORT_HEIGHT.get() / 2f);
            moveToAction.setDuration(0.15f);
            sequenceAction.addAction(moveToAction);

            MoveToAction moveToAction3 = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get() / 2f - 150, Config.HUD_VIEWPORT_HEIGHT.get() / 2f);
            moveToAction3.setDuration(2f);
            sequenceAction.addAction(moveToAction3);

            MoveToAction moveToAction2 = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get(), Config.HUD_VIEWPORT_HEIGHT.get() / 2f);
            moveToAction2.setDuration(0.15f);
            sequenceAction.addAction(moveToAction2);

            RemoveAction removeAction = new RemoveAction();
            removeAction.setTarget(label);
            sequenceAction.addAction(removeAction);

            stageElementsStorage.hudStage.addActor(label);
            stageElementsStorage.hudStage.addAction(sequenceAction);

            GameState.state = GameState.State.Battle;
        }
    }

    public List<AbstractMech> getToDeploy() {
        return toDeploy;
    }
}
