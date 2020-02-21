package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.wargame.battle.combat.RangedAttackTargetCalculator;
import com.mygdx.wargame.battle.input.GroundInputListener;
import com.mygdx.wargame.battle.input.MechClickInputListener;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.BattleMapConfig;
import com.mygdx.wargame.battle.map.TerrainType;
import com.mygdx.wargame.battle.map.decorator.TerrainTypeAwareBattleMapDecorator;
import com.mygdx.wargame.battle.map.movement.MovementMarkerFactory;
import com.mygdx.wargame.battle.screen.input.BasicMouseHandlingInputAdapter;
import com.mygdx.wargame.battle.screen.ui.HudElementsFacade;
import com.mygdx.wargame.battle.screen.ui.SelectionMarker;
import com.mygdx.wargame.battle.screen.ui.localmenu.EnemyMechInfoPanelFacade;
import com.mygdx.wargame.battle.screen.ui.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.battle.screen.ui.targeting.TargetingPanelFacade;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.config.Config;
import com.mygdx.wargame.rules.calculator.HeatCalculator;
import com.mygdx.wargame.rules.calculator.MovementSpeedCalculator;
import com.mygdx.wargame.rules.calculator.RangeCalculator;
import com.mygdx.wargame.rules.facade.AttackFacade;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;
import com.mygdx.wargame.rules.facade.target.TargetingFacade;
import com.mygdx.wargame.util.DrawUtils;

import static com.mygdx.wargame.config.Config.SCREEN_SIZE_X;
import static com.mygdx.wargame.config.Config.SCREEN_SIZE_Y;

public class BattleScreen implements Screen {

    private OrthographicCamera camera;
    private Camera hudCamera;
    private Viewport viewport;
    private Viewport hudViewport;
    private Stage stage;
    private Stage hudStage;
    private RangedAttackTargetCalculator rangedAttackTargetCalculator;
    private SpriteBatch spriteBatch;
    private ActionLock actionLock;
    private TurnProcessingFacade turnProcessingFacade;
    private BattleMap battleMap;
    private RangeCalculator rangeCalculator = new RangeCalculator();
    private SelectionMarker selectionMarker;
    private ScreenConfiguration screenConfiguration;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TargetingPanelFacade targetingPanelFacade;

    public BattleScreen() {
        this.actionLock = new ActionLock();
    }

    @Override
    public void show() {
        StageElementsStorage stageElementsStorage = new StageElementsStorage();

        screenConfiguration = new ScreenConfiguration(0, 0, 0);

        camera = new OrthographicCamera();
        viewport = new FitViewport(Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT, camera);
        viewport.update(Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT, true);
        viewport.apply();

        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(SCREEN_SIZE_X, SCREEN_SIZE_Y, hudCamera);

        this.spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);
        //spriteBatch.maxSpritesInBatch = 10;

        ScreenLoader screenLoader = new ScreenLoader();
        screenLoader.load();

        stage = new Stage(viewport, spriteBatch);
        hudStage = new Stage(hudViewport, spriteBatch);

        stageElementsStorage.stage = stage;
        stageElementsStorage.hudStage = hudStage;


        selectionMarker = new SelectionMarker(screenLoader.getAssetManager(), spriteBatch);

        BattleScreenInputData battleScreenInputData = new BattleScreenInputData();
        BattleScreenInputDataStubber battleScreenInputDataStubber = new BattleScreenInputDataStubber(spriteBatch, screenLoader.getAssetManager());

        battleScreenInputDataStubber.stub(battleScreenInputData);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(new BasicMouseHandlingInputAdapter(screenConfiguration, actionLock));

        inputMultiplexer.addProcessor(hudStage);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        MechInfoPanelFacade mechInfoPanelFacade = new MechInfoPanelFacade(stageElementsStorage, actionLock);

        MovementMarkerFactory movementMarkerFactory = new MovementMarkerFactory(stageElementsStorage, screenLoader.getAssetManager(), mechInfoPanelFacade);

        AttackFacade attackFacade = new AttackFacade(stageElementsStorage, screenLoader.getAssetManager(), mechInfoPanelFacade, actionLock);

        this.turnProcessingFacade = new TurnProcessingFacade(actionLock, attackFacade,
                new TargetingFacade(),
                new MovementSpeedCalculator(), battleScreenInputData.getGroup1(),
                battleScreenInputData.getGroup2(), rangeCalculator, stage, hudStage, screenLoader.getAssetManager(), stageElementsStorage, movementMarkerFactory, new HeatCalculator(), mechInfoPanelFacade, camera);

        // display

        mechInfoPanelFacade.setTouchable(Touchable.enabled);

        TerrainTypeAwareBattleMapDecorator terrainTypeAwareBattleMapDecorator = new TerrainTypeAwareBattleMapDecorator(screenLoader.getAssetManager(), stageElementsStorage);

        BattleMap.TextureRegionSelector textureRegionSelector = new BattleMap.TextureRegionSelector(screenLoader.getAssetManager());

        battleMap = new BattleMap(BattleMapConfig.WIDTH, BattleMapConfig.HEIGHT, actionLock, TerrainType.Grassland, screenLoader.getAssetManager(), textureRegionSelector, 32);

        terrainTypeAwareBattleMapDecorator.decorate(battleMap);

        rangedAttackTargetCalculator = new RangedAttackTargetCalculator(battleMap, rangeCalculator, attackFacade, actionLock, stage, hudStage, screenLoader.getAssetManager(), stageElementsStorage, movementMarkerFactory);

        targetingPanelFacade = new TargetingPanelFacade(screenLoader.getAssetManager(), rangedAttackTargetCalculator, rangeCalculator);

        EnemyMechInfoPanelFacade enemyMechInfoPanelFacade = new EnemyMechInfoPanelFacade(stageElementsStorage, actionLock, targetingPanelFacade, rangedAttackTargetCalculator);

        battleMap.setTemporaryObstacle(1, 1);
        battleMap.setTemporaryObstacle(5, 2);
        battleMap.setTemporaryObstacle(6, 5);

        stage.addActor(stageElementsStorage.groundLevel);
        stage.addActor(stageElementsStorage.mechLevel);
        stage.addActor(stageElementsStorage.airLevel);

        stage.addActor(mechInfoPanelFacade);

        stage.addListener(new GroundInputListener(turnProcessingFacade, battleMap, actionLock, mechInfoPanelFacade, stageElementsStorage, movementMarkerFactory, screenLoader.getAssetManager(), targetingPanelFacade, enemyMechInfoPanelFacade));

        battleScreenInputData.getGroup1().entrySet().forEach((entry -> {
            stageElementsStorage.mechLevel.addActor((Actor) entry.getKey());
            ((Actor) entry.getKey()).addListener(new MechClickInputListener(entry.getKey(), entry.getValue(), turnProcessingFacade, rangedAttackTargetCalculator, actionLock, mechInfoPanelFacade.getSmallLabelStyle(), mechInfoPanelFacade.getCheckBoxStyle(), mechInfoPanelFacade, hudStage, stage, stageElementsStorage, targetingPanelFacade, enemyMechInfoPanelFacade, battleMap));
        }));

        battleScreenInputData.getGroup2().entrySet().forEach((entry -> {
            stageElementsStorage.mechLevel.addActor((Actor) entry.getKey());
            ((Actor) entry.getKey()).addListener(new MechClickInputListener(entry.getKey(), entry.getValue(), turnProcessingFacade, rangedAttackTargetCalculator, actionLock, mechInfoPanelFacade.getSmallLabelStyle(), mechInfoPanelFacade.getCheckBoxStyle(), mechInfoPanelFacade, hudStage, stage, stageElementsStorage, targetingPanelFacade, enemyMechInfoPanelFacade, battleMap));
        }));

        stage.addActor(selectionMarker);
        mechInfoPanelFacade.getWeaponSelectionButton().setVisible(false);
        mechInfoPanelFacade.getDetailsButton().setVisible(false);
        mechInfoPanelFacade.getHideMenuButton().setVisible(false);
        mechInfoPanelFacade.getPilotButton().setVisible(false);
        mechInfoPanelFacade.registerComponents(hudStage);

        enemyMechInfoPanelFacade.registerComponents(hudStage);

        HudElementsFacade hudElementsFacade = new HudElementsFacade(screenLoader.getAssetManager(), turnProcessingFacade, actionLock);
        hudElementsFacade.registerComponents(hudStage);

        float unitScale = 1 / 32f;
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(battleMap.getTiledMap(), unitScale);

    }

    @Override
    public void render(float delta) {
        camera.position.x = Math.min(Math.max(camera.position.x + screenConfiguration.scrollX, 0), SCREEN_SIZE_X);
        camera.position.y = Math.min(Math.max(camera.position.y + screenConfiguration.scrollY, 0), SCREEN_SIZE_Y);

        DrawUtils.clearScreen();

        if (turnProcessingFacade.getNext() != null && !turnProcessingFacade.getNext().getKey().attacked() && turnProcessingFacade.getNext().getKey().getState() == State.Idle) {
            selectionMarker.setColor(Color.valueOf("FFFFFF66"));
            selectionMarker.setPosition(turnProcessingFacade.getNext().getKey().getX(), turnProcessingFacade.getNext().getKey().getY());
        } else {
            selectionMarker.setColor(Color.valueOf("FFFFFF00"));
        }

        turnProcessingFacade.process(battleMap, stage);

        viewport.apply();

        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();


        spriteBatch.setProjectionMatrix(camera.combined);
        stage.act();
        stage.draw();

        hudViewport.apply();
        spriteBatch.setProjectionMatrix(hudCamera.combined);
        spriteBatch.setColor(Color.WHITE);
        hudStage.act();
        hudStage.draw();

        //System.out.println(Gdx.graphics.getFramesPerSecond());
        //System.out.println(spriteBatch.renderCalls);
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
