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
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.config.Config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    private List<AbstractMech> deployed = new ArrayList<>();
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private Label deployMessageLabel;
    private BattleMap battleMap;

    public DeploymentFacade(IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites, StageElementsStorage stageElementsStorage, HUDMediator hudMediator, Map<AbstractMech, Pilot> playerGroup, Map<AbstractMech, Pilot> aiGroup, BattleMap battleMap) {
        this.isometricTiledMapRendererWithSprites = isometricTiledMapRendererWithSprites;
        this.stageElementsStorage = stageElementsStorage;
        this.hudMediator = hudMediator;
        this.playerGroup = playerGroup;
        this.aiGroup = aiGroup;
        this.battleMap = battleMap;
        this.shapeRenderer = new ShapeRenderer();
        this.spriteBatch = new SpriteBatch();
        this.battleMap = battleMap;
    }

    public void process() {
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

            SequenceAction sequenceAction1 = new SequenceAction();
            Label.LabelStyle labelStyle1 = new Label.LabelStyle();
            labelStyle1.font = FontCreator.getBitmapFont(60);
            labelStyle1.fontColor = Color.DARK_GRAY;
            Label label2 = new Label("DEPLOY PHASE", labelStyle1);
            label2.setPosition(-295, Config.HUD_VIEWPORT_HEIGHT.get() / 2f - 5);

            sequenceAction1.addAction(new DelayAction(0.5f));

            MoveToAction moveToActionS = getMoveToAction(label2, Config.HUD_VIEWPORT_WIDTH.get() / 2f - 5, Config.HUD_VIEWPORT_HEIGHT.get() / 2f - 5);
            moveToActionS.setDuration(0.15f);
            sequenceAction1.addAction(moveToActionS);

            MoveToAction moveToAction3S = getMoveToAction(label2, Config.HUD_VIEWPORT_WIDTH.get() / 2f - 155, Config.HUD_VIEWPORT_HEIGHT.get() / 2f - 5);
            moveToAction3S.setDuration(2f);
            sequenceAction1.addAction(moveToAction3S);

            MoveToAction moveToAction2S = getMoveToAction(label2, Config.HUD_VIEWPORT_WIDTH.get() - 5, Config.HUD_VIEWPORT_HEIGHT.get() / 2f - 5);
            moveToAction2S.setDuration(0.15f);
            sequenceAction1.addAction(moveToAction2S);

            RemoveAction removeActionS = new RemoveAction();
            removeActionS.setTarget(label2);
            sequenceAction1.addAction(removeAction);


            startup = false;

            stageElementsStorage.hudStage.addActor(label2);
            stageElementsStorage.hudStage.addActor(label);
            stageElementsStorage.hudStage.addAction(sequenceAction);
            stageElementsStorage.hudStage.addAction(sequenceAction1);

            toDeploy.addAll(playerGroup.keySet());

            Label.LabelStyle messageLabelStyle= new Label.LabelStyle();
            messageLabelStyle.font = FontCreator.getBitmapFont(20);

            deployMessageLabel = new Label("Units to deploy:", messageLabelStyle);
            stageElementsStorage.hudStage.addActor(deployMessageLabel);
            deployMessageLabel.setPosition(20, 80);



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
                toDeploy.get(i).getIsometricSprite().getTextureRegion().setRegion(0, 0, 64, 64);
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

    private void deployEnemyMechs() {
        aiGroup.keySet().forEach(mech -> {
            int sx = BattleMap.WIDTH / 2;
            int sy = BattleMap.HEIGHT - 1;

            mech.setPosition(sx, sy);

            while(battleMap.getNodeGraph().getNodeWeb()[sx][sy].isImpassable() ||
                    isometricTiledMapRendererWithSprites.getObjects().stream().filter(o -> Mech.class.isAssignableFrom(o.getClass()))
                            .map(o -> (AbstractMech)o)
                    .anyMatch(m -> m != mech && (int)m.getX() == mech.getX() && (int)m.getY() == mech.getY())) {
                int nx = new Random().nextInt(BattleMap.WIDTH);
                int ny = BattleMap.HEIGHT- new Random().nextInt(2) - 1;
                mech.setPosition(nx, ny);
            }
        });
    }

    public void back() {
        if(deployed.isEmpty())
            return;

        isometricTiledMapRendererWithSprites.getObjects().remove(nextMech);

        iterator = playerGroup.entrySet().iterator();
        nextMech = deployed.get(0);
        deployed.remove(nextMech);
        toDeploy.add(0, nextMech);

        isometricTiledMapRendererWithSprites.getObjects().remove(nextMech);

        while (iterator.hasNext()) {
            Map.Entry<AbstractMech, Pilot> entry = iterator.next();
            pilot = entry.getValue();
            if(entry.getKey() == nextMech)
                break;
        }
    }

    public void deployed(AbstractMech abstractMech) {
        toDeploy.remove(abstractMech);
        deployed.add(0, abstractMech);

        if (iterator.hasNext()) {
            Map.Entry<AbstractMech, Pilot> entry = iterator.next();

            nextMech = entry.getKey();
            pilot = entry.getValue();
            isometricTiledMapRendererWithSprites.addObject(nextMech);
        } else {


        }
    }

    public void finishDeployment() {
        stageElementsStorage.hudStage.getActors().removeValue(deployMessageLabel, true);
        addBattleLabelShadow();
        addBattleLabel();

        deployEnemyMechs();

        GameState.state = GameState.State.Battle;
    }

    private void addBattleLabel() {
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
    }

    private void addBattleLabelShadow() {
        SequenceAction sequenceAction = new SequenceAction();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(60);
        labelStyle.fontColor = Color.DARK_GRAY;

        Label label = new Label("BATTLE PHASE", labelStyle);
        label.setPosition(-300 - 5, Config.HUD_VIEWPORT_HEIGHT.get() / 2f - 5);

        sequenceAction.addAction(new DelayAction(0.5f));

        MoveToAction moveToAction = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get() / 2f - 5, Config.HUD_VIEWPORT_HEIGHT.get() / 2f - 5);
        moveToAction.setDuration(0.15f);
        sequenceAction.addAction(moveToAction);

        MoveToAction moveToAction3 = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get() / 2f - 150 - 5, Config.HUD_VIEWPORT_HEIGHT.get() / 2f - 5);
        moveToAction3.setDuration(2f);
        sequenceAction.addAction(moveToAction3);

        MoveToAction moveToAction2 = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get() - 5, Config.HUD_VIEWPORT_HEIGHT.get() / 2f - 5);
        moveToAction2.setDuration(0.15f);
        sequenceAction.addAction(moveToAction2);

        RemoveAction removeAction = new RemoveAction();
        removeAction.setTarget(label);
        sequenceAction.addAction(removeAction);

        stageElementsStorage.hudStage.addActor(label);
        stageElementsStorage.hudStage.addAction(sequenceAction);
    }

    public List<AbstractMech> getToDeploy() {
        return toDeploy;
    }
}
