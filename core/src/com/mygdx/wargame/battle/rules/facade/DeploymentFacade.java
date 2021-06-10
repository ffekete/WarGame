package com.mygdx.wargame.battle.rules.facade;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.render.IsometricTiledMapRendererWithSprites;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.FontCreator;
import com.mygdx.wargame.battle.screen.ui.HUDMediator;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.battle.unit.action.UnlockAction;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.mech.Role;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.config.Config;

import java.util.*;

public class DeploymentFacade {

    private IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites;

    private AbstractMech nextMech = null;
    private Pilot pilot;
    private boolean startup = true;
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
    private ActionLock actionLock;

    public DeploymentFacade(IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites, HUDMediator hudMediator, Map<AbstractMech, Pilot> playerGroup, Map<AbstractMech, Pilot> aiGroup, BattleMap battleMap) {
        this.isometricTiledMapRendererWithSprites = isometricTiledMapRendererWithSprites;
        this.hudMediator = hudMediator;
        this.playerGroup = playerGroup;
        this.aiGroup = aiGroup;
        this.battleMap = battleMap;
        this.actionLock = GameState.actionLock;
        this.shapeRenderer = new ShapeRenderer();
        this.spriteBatch = new SpriteBatch();
        this.battleMap = battleMap;
    }

    public void process() {
        if (startup) {

            addDeployZoneMarkers();

            toDeploy.clear();

            ParallelAction parallelAction = new ParallelAction();
            SequenceAction sequenceAction = new SequenceAction();
            this.addMovingLabelShadow(sequenceAction, "DEPLOY PHASE");
            SequenceAction sequenceAction1 = new SequenceAction();
            this.addMovingLabel(sequenceAction1, "DEPLOY PHASE");
            parallelAction.addAction(sequenceAction);
            parallelAction.addAction(sequenceAction1);

            startup = false;

            toDeploy.addAll(playerGroup.keySet());

            Label.LabelStyle messageLabelStyle = new Label.LabelStyle();
            messageLabelStyle.font = FontCreator.getBitmapFont(20);
            messageLabelStyle.fontColor = Color.WHITE;

            deployMessageLabel = new Label("Units to deploy:", messageLabelStyle);
            StageElementsStorage.hudStage.addAction(parallelAction);
            deployMessageLabel.setPosition(20, 80);
            StageElementsStorage.hudStage.addActor(deployMessageLabel);

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

            isometricTiledMapRendererWithSprites.addObject(mech);
            mech.setDirection(Direction.Right);
            battleMap.addDirectionMarker(mech.getDirection(), (int) mech.getX(), (int) mech.getY());

            while (battleMap.getNodeGraph().getNodeWeb()[(int) mech.getX()][(int) mech.getY()].isImpassable() ||
                    isometricTiledMapRendererWithSprites.getObjects().stream().filter(o -> Mech.class.isAssignableFrom(o.getClass()))
                            .map(o -> (AbstractMech) o)
                            .anyMatch(m -> m != mech && (int) m.getX() == mech.getX() && (int) m.getY() == mech.getY())) {
                int nx = new Random().nextInt(BattleMap.WIDTH);
                int ny = BattleMap.HEIGHT - new Random().nextInt(isBackLine(mech) ? 3 : 2) - 1;
                mech.setPosition(nx, ny);
            }
        });
    }

    private boolean isBackLine(AbstractMech mech) {
        return mech.getRole() == Role.Artillery || mech.getRole() == Role.Aircraft;
    }

    public void back() {
        if (deployed.isEmpty())
            return;

        isometricTiledMapRendererWithSprites.getObjects().remove(nextMech);

        nextMech.setPosition(-1, -1);

        iterator = playerGroup.entrySet().iterator();
        nextMech = deployed.get(0);
        deployed.remove(nextMech);
        toDeploy.add(0, nextMech);

        //isometricTiledMapRendererWithSprites.getObjects().remove(nextMech);

        while (iterator.hasNext()) {
            Map.Entry<AbstractMech, Pilot> entry = iterator.next();
            pilot = entry.getValue();
            if (entry.getKey() == nextMech)
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
            nextMech.setDirection(Direction.Left);
            battleMap.addDirectionMarker(nextMech.getDirection(), (int) nextMech.getX(), (int) nextMech.getY());
        } else {


        }
    }

    public void finishDeployment() {
        StageElementsStorage.hudStage.getActors().removeValue(deployMessageLabel, true);

        SequenceAction outer = new SequenceAction();

        ParallelAction parallelAction = new ParallelAction();

        SequenceAction sequenceAction = new SequenceAction();
        addMovingLabelShadow(sequenceAction, "BATTLE PHASE");

        SequenceAction sequenceAction1 = new SequenceAction();
        addMovingLabel(sequenceAction1, "BATTLE PHASE");
        parallelAction.addAction(sequenceAction);
        parallelAction.addAction(sequenceAction1);

        ParallelAction parallelAction1 = new ParallelAction();
        SequenceAction sequenceAction2 = new SequenceAction();
        addMovingLabelShadow(sequenceAction2, "TURN 1");
        SequenceAction sequenceAction3 = new SequenceAction();
        addMovingLabel(sequenceAction3, "TURN 1");

        parallelAction1.addAction(sequenceAction2);
        parallelAction1.addAction(sequenceAction3);

        outer.addAction(parallelAction);
        outer.addAction(parallelAction1);

        outer.addAction(new UnlockAction("End of deployment"));

        StageElementsStorage.hudStage.addAction(outer);
        deployEnemyMechs();

        GameState.state = GameState.State.Battle;
        actionLock.setLocked(true);
        hudMediator.getHudElementsFacade().populateSidePanel();
    }

    public void addMovingLabel(ParallelAction sequenceAction, String text) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(60);
        labelStyle.fontColor = Color.GREEN;

        Label label = new Label(text, labelStyle);
        label.setPosition(-300, Config.HUD_VIEWPORT_HEIGHT.get() / 2f);

        sequenceAction.addAction(new DelayAction(0.5f));

        MoveToAction moveToAction = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get() / 2f - 150, Config.HUD_VIEWPORT_HEIGHT.get() / 2f);
        moveToAction.setDuration(0.15f);
        sequenceAction.addAction(moveToAction);

        MoveToAction moveToAction3 = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get() / 2f, Config.HUD_VIEWPORT_HEIGHT.get() / 2f);
        moveToAction3.setDuration(2f);
        sequenceAction.addAction(moveToAction3);

        MoveToAction moveToAction2 = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get(), Config.HUD_VIEWPORT_HEIGHT.get() / 2f);
        moveToAction2.setDuration(0.15f);
        sequenceAction.addAction(moveToAction2);

        RemoveActorAction removeAction = new RemoveActorAction();
        removeAction.setTarget(label);
        sequenceAction.addAction(removeAction);

        StageElementsStorage.hudStage.addActor(label);
    }

    public void addMovingLabelShadow(ParallelAction sequenceAction, String text) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(60);
        labelStyle.fontColor = Color.DARK_GRAY;

        Label label = new Label(text, labelStyle);
        label.setPosition(Config.HUD_VIEWPORT_WIDTH.get() + 300 - 5, Config.HUD_VIEWPORT_HEIGHT.get() / 2f - 5);

        sequenceAction.addAction(new DelayAction(0.5f));

        MoveToAction moveToAction = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get() / 2f - 150 - 5, Config.HUD_VIEWPORT_HEIGHT.get() / 2f - 5);
        moveToAction.setDuration(0.15f);
        sequenceAction.addAction(moveToAction);

        MoveToAction moveToAction3 = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get() / 2f - 5, Config.HUD_VIEWPORT_HEIGHT.get() / 2f - 5);
        moveToAction3.setDuration(2f);
        sequenceAction.addAction(moveToAction3);

        MoveToAction moveToAction2 = getMoveToAction(label, Config.HUD_VIEWPORT_WIDTH.get() - 5, Config.HUD_VIEWPORT_HEIGHT.get() / 2f - 5);
        moveToAction2.setDuration(0.15f);
        sequenceAction.addAction(moveToAction2);

        RemoveActorAction removeAction = new RemoveActorAction();
        removeAction.setTarget(label);
        sequenceAction.addAction(removeAction);

        StageElementsStorage.hudStage.addActor(label);
    }

    public List<AbstractMech> getToDeploy() {
        return toDeploy;
    }

    private void addDeployZoneMarkers() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < BattleMap.WIDTH; j++) {
                battleMap.addMovementMarker(j, i);
            }
        }
    }
}
