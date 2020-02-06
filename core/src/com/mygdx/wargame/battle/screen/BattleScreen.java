package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.battle.combat.RangedAttackTargetCalculator;
import com.mygdx.wargame.battle.input.MechClickInputListener;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.TerrainType;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.ballistic.LargeCannon;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Scout;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.pilot.PilotCreator;
import com.mygdx.wargame.rules.calculator.MovementSpeedCalculator;
import com.mygdx.wargame.rules.calculator.RangeCalculator;
import com.mygdx.wargame.rules.facade.AttackFacade;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;
import com.mygdx.wargame.rules.facade.target.TargetingFacade;
import com.mygdx.wargame.util.DrawUtils;

public class BattleScreen implements Screen {

    public static final int WIDTH = 96;
    public static final int HEIGHT = 54;
    private Camera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private RangedAttackTargetCalculator rangedAttackTargetCalculator;
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private ActionLock actionLock;
    private TurnProcessingFacade turnProcessingFacade;
    private BattleMap battleMap;
    private RangeCalculator rangeCalculator = new RangeCalculator();
    private SelectionMarker selectionMarker;

    public BattleScreen() {
        this.actionLock = new ActionLock();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        viewport.update(WIDTH, HEIGHT, true);
        viewport.apply();

        this.spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);

        assetManager = new AssetManager();
        assetManager.load("Maverick.png", Texture.class);
        assetManager.load("SelectionMarker.png", Texture.class);
        assetManager.finishLoading();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        stage = new Stage(viewport, spriteBatch);

        selectionMarker = new SelectionMarker(assetManager, spriteBatch);

        shapeRenderer.setProjectionMatrix(camera.combined);

        Scout unit3 = new Scout("3", spriteBatch, assetManager);
        unit3.setPosition(63, 30);
        unit3.setTeam(Team.own);
        unit3.setStability(100);
        LargeCannon largeCannon = new LargeCannon();
        largeCannon.setStatus(Status.Selected);
        unit3.addComponent(BodyPart.LeftLeg, largeCannon);
        unit3.setActive(true);


        Scout unit2 = new Scout("2", spriteBatch, assetManager);
        unit2.setPosition(60, 30);
        unit2.setTeam(Team.own);
        unit2.setActive(true);
        unit2.setStability(100);
        LargeCannon largeCannon2 = new LargeCannon();
        largeCannon2.setStatus(Status.Selected);
        unit2.addComponent(BodyPart.LeftLeg, largeCannon2);

        Scout unit = new Scout("1", spriteBatch, assetManager);
        unit.setPosition(10, 10);
        unit.setTeam(Team.enemy);
        unit.setActive(true);
        unit.setStability(100);
        LargeCannon largeCannon3 = new LargeCannon();
        largeCannon3.setStatus(Status.Selected);
        unit.addComponent(BodyPart.LeftLeg, largeCannon3);

        PilotCreator pilotCreator = new PilotCreator();

        Gdx.input.setInputProcessor(stage);

        Pilot p1 = pilotCreator.getPilot();
        Pilot p2 = pilotCreator.getPilot();
        Pilot p3 = pilotCreator.getPilot();

        AttackFacade attackFacade = new AttackFacade(stage, spriteBatch, assetManager);

        this.turnProcessingFacade = new TurnProcessingFacade(actionLock, attackFacade,
                new TargetingFacade(),
                new MovementSpeedCalculator(), ImmutableMap.of(unit2, p2, unit3, p3),
                ImmutableMap.of(unit, p1), rangeCalculator);

        battleMap = new BattleMap(100, 100, stage, actionLock, TerrainType.Desert, turnProcessingFacade, turnProcessingFacade);

        battleMap.setTemporaryObstacle(63, 30);
        battleMap.setTemporaryObstacle(60, 30);
        battleMap.setTemporaryObstacle(10, 10);

        stage.addActor(selectionMarker);

        stage.addActor(unit2);
        stage.addActor(unit3);
        stage.addActor(unit);

        rangedAttackTargetCalculator = new RangedAttackTargetCalculator(battleMap, rangeCalculator, attackFacade, actionLock);

        unit2.addListener(new MechClickInputListener(unit2, p2, turnProcessingFacade, rangedAttackTargetCalculator, actionLock));
        unit3.addListener(new MechClickInputListener(unit3, p3, turnProcessingFacade, rangedAttackTargetCalculator, actionLock));
        unit.addListener(new MechClickInputListener(unit, p1, turnProcessingFacade, rangedAttackTargetCalculator, actionLock));
    }

    @Override
    public void render(float delta) {
        DrawUtils.clearScreen();
        shapeRenderer.begin();

        shapeRenderer.setColor(Color.DARK_GRAY);

        for (int i = 0; i <= HEIGHT; i++) {
            for (int j = 0; j <= WIDTH; j++) {
                shapeRenderer.line(j, 0, j, HEIGHT);
            }
            shapeRenderer.line(0, i, WIDTH, i);

        }
        shapeRenderer.end();

        if(turnProcessingFacade.getNext() != null && !turnProcessingFacade.getNext().getKey().moved() && turnProcessingFacade.getNext().getKey().getState() == State.Idle) {
            selectionMarker.setColor(Color.valueOf("FFFFFF66"));
            selectionMarker.setPosition(turnProcessingFacade.getNext().getKey().getX(), turnProcessingFacade.getNext().getKey().getY());
        }
        else {
            selectionMarker.setColor(Color.valueOf("FFFFFF00"));
        }

        turnProcessingFacade.process(battleMap, stage);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
