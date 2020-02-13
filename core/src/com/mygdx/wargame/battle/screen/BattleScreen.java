package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.wargame.GlobalState;
import com.mygdx.wargame.battle.combat.RangedAttackTargetCalculator;
import com.mygdx.wargame.battle.input.GroundInputListener;
import com.mygdx.wargame.battle.input.MechClickInputListener;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.BattleMapConfig;
import com.mygdx.wargame.battle.map.TerrainType;
import com.mygdx.wargame.battle.map.decorator.TerrainTypeAwareBattleMapDecorator;
import com.mygdx.wargame.battle.screen.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.rules.calculator.MovementSpeedCalculator;
import com.mygdx.wargame.rules.calculator.RangeCalculator;
import com.mygdx.wargame.rules.facade.AttackFacade;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;
import com.mygdx.wargame.rules.facade.target.TargetingFacade;
import com.mygdx.wargame.util.DrawUtils;

import static com.mygdx.wargame.config.Config.SCREEN_SIZE_X;
import static com.mygdx.wargame.config.Config.SCREEN_SIZE_Y;

public class BattleScreen implements Screen {

    public static final int WIDTH = (int)(45 * 0.5);
    public static final int HEIGHT = (int)(28 * 0.5);
    private OrthographicCamera camera;
    private Camera hudCamera;
    private Viewport viewport;
    private Viewport hudViewport;
    private Stage stage;
    private Stage hudStage;
    private RangedAttackTargetCalculator rangedAttackTargetCalculator;
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private ActionLock actionLock;
    private TurnProcessingFacade turnProcessingFacade;
    private BattleMap battleMap;
    private RangeCalculator rangeCalculator = new RangeCalculator();
    private SelectionMarker selectionMarker;
    private ScreenConfiguration screenConfiguration;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    public BattleScreen() {
        this.actionLock = new ActionLock();
    }

    public Map tiledMap;

    @Override
    public void show() {
        StageStorage stageStorage = new StageStorage();

        screenConfiguration = new ScreenConfiguration(0, 0, 0);

        camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        viewport.update(WIDTH, HEIGHT, true);
        viewport.apply();

        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(SCREEN_SIZE_X, SCREEN_SIZE_Y, hudCamera);

        this.spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);
        //spriteBatch.maxSpritesInBatch = 10;

        assetManager = new AssetManager();
        assetManager.load("Maverick.png", Texture.class);
        assetManager.load("tileset/Dirt.png", Texture.class);
        assetManager.load("SelectionMarker.png", Texture.class);
        assetManager.load("DesertTile.png", Texture.class);
        assetManager.load("Grassland.png", Texture.class);
        assetManager.load("objects/Crater.png", Texture.class);
        assetManager.load("variation/Trees.png", Texture.class);
        assetManager.load("variation/Trees03.png", Texture.class);
        assetManager.load("variation/Trees02.png", Texture.class);
        assetManager.load("variation/Trees04.png", Texture.class);
        assetManager.load("PlasmaBullet.png", Texture.class);
        assetManager.load("CannonBullet.png", Texture.class);
        assetManager.load("Missile.png", Texture.class);
        assetManager.load("Explosion.png", Texture.class);
        assetManager.load("Laser.png", Texture.class);
        assetManager.load("Ion.png", Texture.class);
        assetManager.load("MachineGun.png", Texture.class);
        assetManager.load("Shield.png", Texture.class);
        assetManager.load("skin/EndTurnButtonUp.png", Texture.class);
        assetManager.load("skin/EndTurnButtonDown.png", Texture.class);
        assetManager.finishLoading();

        stage = new Stage(viewport, spriteBatch);

        hudStage = new Stage(hudViewport, spriteBatch);

        selectionMarker = new SelectionMarker(assetManager, spriteBatch);

        BattleScreenInputData battleScreenInputData = new BattleScreenInputData();
        BattleScreenInputDataStubber battleScreenInputDataStubber = new BattleScreenInputDataStubber(spriteBatch, assetManager);

        battleScreenInputDataStubber.stub(battleScreenInputData);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(new BasicMouseHandlingInputAdapter(screenConfiguration));

        inputMultiplexer.addProcessor(hudStage);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        AttackFacade attackFacade = new AttackFacade(stageStorage, spriteBatch, assetManager);

        this.turnProcessingFacade = new TurnProcessingFacade(actionLock, attackFacade,
                new TargetingFacade(),
                new MovementSpeedCalculator(), battleScreenInputData.getGroup1(),
                battleScreenInputData.getGroup2(), rangeCalculator, stage, hudStage, assetManager, stageStorage);

        // display


        MechInfoPanelFacade mechInfoPanelFacade = new MechInfoPanelFacade();
        mechInfoPanelFacade.setTouchable(Touchable.enabled);

        TerrainTypeAwareBattleMapDecorator terrainTypeAwareBattleMapDecorator = new TerrainTypeAwareBattleMapDecorator(assetManager);
        battleMap = new BattleMap(BattleMapConfig.WIDTH, BattleMapConfig.HEIGHT, actionLock, TerrainType.Grassland, turnProcessingFacade, turnProcessingFacade, assetManager, mechInfoPanelFacade);

        terrainTypeAwareBattleMapDecorator.decorate(battleMap);

        rangedAttackTargetCalculator = new RangedAttackTargetCalculator(battleMap, rangeCalculator, attackFacade, actionLock, stage, hudStage, assetManager, stageStorage);

        battleMap.setTemporaryObstacle(1, 1);
        battleMap.setTemporaryObstacle(5, 2);
        battleMap.setTemporaryObstacle(6, 5);

        stage.addActor(stageStorage.groundLevel);
        stage.addActor(stageStorage.mechLevel);
        stage.addActor(stageStorage.treeLevel);
        stage.addActor(stageStorage.airLevel);

        stage.addListener( new GroundInputListener(turnProcessingFacade, battleMap, actionLock, mechInfoPanelFacade));

        battleScreenInputData.getGroup1().entrySet().forEach((entry -> {
            stageStorage.mechLevel.addActor((Actor) entry.getKey());
            ((Actor) entry.getKey()).addListener(new MechClickInputListener(entry.getKey(), entry.getValue(), turnProcessingFacade, rangedAttackTargetCalculator, actionLock, mechInfoPanelFacade.getLabelStyle(), mechInfoPanelFacade.getCheckBoxStyle(), mechInfoPanelFacade, hudStage, stage));
        }));

        battleScreenInputData.getGroup2().entrySet().forEach((entry -> {
            stageStorage.mechLevel.addActor((Actor) entry.getKey());
            ((Actor) entry.getKey()).addListener(new MechClickInputListener(entry.getKey(), entry.getValue(), turnProcessingFacade, rangedAttackTargetCalculator, actionLock, mechInfoPanelFacade.getLabelStyle(), mechInfoPanelFacade.getCheckBoxStyle(), mechInfoPanelFacade, hudStage, stage));
        }));

        stage.addActor(selectionMarker);
        mechInfoPanelFacade.getWeaponSelectionButton().setVisible(false);
        mechInfoPanelFacade.getDetailsButton().setVisible(false);
        mechInfoPanelFacade.getHideMenuButton().setVisible(false);
        mechInfoPanelFacade.getPilotButton().setVisible(false);
        mechInfoPanelFacade.registerComponents(hudStage);

        HudElementsFacade hudElementsFacade = new HudElementsFacade(assetManager, turnProcessingFacade, actionLock);
        hudElementsFacade.registerComponents(hudStage);

        // listeners


//        unit2.addListener();
//        unit3.addListener(new MechClickInputListener(unit3, p3, turnProcessingFacade, rangedAttackTargetCalculator, actionLock, labelStyle, checkBoxStyle, mechInfoPanelFacade, hudStage, stage));
//        unit.addListener(new MechClickInputListener(unit, p1, turnProcessingFacade, rangedAttackTargetCalculator, actionLock, labelStyle, checkBoxStyle, mechInfoPanelFacade, hudStage, stage));

        float unitScale = 1 / 32f;
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(battleMap.getTiledMap(), unitScale);

    }

    @Override
    public void render(float delta) {
        GlobalState.i = 0;
        camera.position.x += screenConfiguration.scrollX;
        camera.position.y += screenConfiguration.scrollY;

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

        System.out.println(Gdx.graphics.getFramesPerSecond());
        System.out.println(GlobalState.i);
        System.out.println(spriteBatch.renderCalls);
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
