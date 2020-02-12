package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.battle.combat.RangedAttackTargetCalculator;
import com.mygdx.wargame.battle.input.MechClickInputListener;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.decorator.BattleMapTreeSpreadDecorator;
import com.mygdx.wargame.battle.map.TerrainType;
import com.mygdx.wargame.battle.map.decorator.TerrainTypeAwareBattleMapDecorator;
import com.mygdx.wargame.battle.screen.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.battle.unit.State;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.component.shield.SmallShieldModule;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.ballistic.LargeCannon;
import com.mygdx.wargame.component.weapon.ballistic.MachineGun;
import com.mygdx.wargame.component.weapon.ion.LargeIonCannon;
import com.mygdx.wargame.component.weapon.laser.LargeLaser;
import com.mygdx.wargame.component.weapon.missile.SwarmMissile;
import com.mygdx.wargame.component.weapon.plasma.PlasmaCannon;
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

import static com.mygdx.wargame.config.Config.SCREEN_SIZE_X;
import static com.mygdx.wargame.config.Config.SCREEN_SIZE_Y;

public class BattleScreen implements Screen {

    public static final int WIDTH = (int)(45 * 0.5);
    public static final int HEIGHT = (int)(28 * 0.5);
    private Camera camera;
    private Camera hudCamera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer shapeRenderer;
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

    public BattleScreen() {
        this.actionLock = new ActionLock();
    }

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

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        stage = new Stage(viewport, spriteBatch);

        hudStage = new Stage(hudViewport, spriteBatch);

        selectionMarker = new SelectionMarker(assetManager, spriteBatch);

        shapeRenderer.setProjectionMatrix(camera.combined);

        Scout unit3 = new Scout("3", spriteBatch, assetManager);
        unit3.setPosition(60, 45);
        unit3.setTeam(Team.own);
        unit3.setStability(100);
        SwarmMissile swarmMissile = new SwarmMissile();
        swarmMissile.setStatus(Status.Selected);
        LargeLaser largeLaser = new LargeLaser();
        largeLaser.setStatus(Status.Selected);
        LargeLaser largeLaser4 = new LargeLaser();
        largeLaser4.setStatus(Status.Selected);
        unit3.addComponent(BodyPart.LeftLeg, swarmMissile);
        unit3.addComponent(BodyPart.RightHand, largeLaser);
        unit3.addComponent(BodyPart.Torso, largeLaser4);
        unit3.setActive(true);
        unit3.addComponent(BodyPart.Torso, new SmallShieldModule());

        PlasmaCannon plasmaCannon = new PlasmaCannon();
        plasmaCannon.setStatus(Status.Active);
        unit3.addComponent(BodyPart.LeftLeg, plasmaCannon);

        LargeIonCannon largeIonCannon = new LargeIonCannon();
        largeIonCannon.setStatus(Status.Selected);

        MachineGun machineGun = new MachineGun();
        machineGun.setStatus(Status.Selected);

        unit3.addComponent(BodyPart.Torso, largeIonCannon);
        unit3.addComponent(BodyPart.Torso, machineGun);

        Scout unit2 = new Scout("2", spriteBatch, assetManager);
        unit2.setPosition(30, 30);
        unit2.setTeam(Team.own);
        unit2.setActive(true);
        unit2.setStability(100);
        LargeCannon largeCannon2 = new LargeCannon();
        largeCannon2.setStatus(Status.Selected);

        LargeCannon largeCannon3 = new LargeCannon();
        largeCannon3.setStatus(Status.Selected);

        LargeCannon largeCannon4 = new LargeCannon();
        largeCannon4.setStatus(Status.Selected);

        LargeCannon largeCannon5 = new LargeCannon();
        largeCannon5.setStatus(Status.Selected);

        LargeCannon largeCannon6 = new LargeCannon();
        largeCannon6.setStatus(Status.Selected);

        LargeCannon largeCannon7 = new LargeCannon();
        largeCannon7.setStatus(Status.Selected);

        LargeCannon largeCannon8 = new LargeCannon();
        largeCannon8.setStatus(Status.Selected);

        LargeCannon largeCannon9 = new LargeCannon();
        largeCannon9.setStatus(Status.Selected);

        LargeCannon largeCannon10 = new LargeCannon();
        largeCannon10.setStatus(Status.Selected);

        LargeCannon largeCannon11 = new LargeCannon();
        largeCannon11.setStatus(Status.Selected);

        unit2.addComponent(BodyPart.LeftLeg, largeCannon2);
        unit2.addComponent(BodyPart.LeftLeg, largeCannon3);
        unit2.addComponent(BodyPart.RightLeg, largeCannon4);
        unit2.addComponent(BodyPart.RightLeg, largeCannon5);
        unit2.addComponent(BodyPart.LeftLeg, largeCannon6);
        unit2.addComponent(BodyPart.LeftLeg, largeCannon7);
        unit2.addComponent(BodyPart.LeftLeg, largeCannon8);
        unit2.addComponent(BodyPart.Torso, largeCannon9);
        unit2.addComponent(BodyPart.Torso, largeCannon10);
        unit2.addComponent(BodyPart.Torso, largeCannon11);

        Scout unit = new Scout("1", spriteBatch, assetManager);
        unit.setPosition(65, 45);
        unit.setTeam(Team.enemy);
        unit.setActive(true);
        unit.setStability(100);
        LargeLaser largeLaser2 = new LargeLaser();
        largeLaser2.setStatus(Status.Selected);
        LargeLaser largeLaser3 = new LargeLaser();
        largeLaser3.setStatus(Status.Selected);
        unit.addComponent(BodyPart.LeftLeg, largeLaser2);
        unit.addComponent(BodyPart.Torso, largeLaser3);

        PilotCreator pilotCreator = new PilotCreator();

        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(new BasicMouseHandlingInputAdapter(screenConfiguration));

        inputMultiplexer.addProcessor(hudStage);
        inputMultiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(inputMultiplexer);

        Pilot p1 = pilotCreator.getPilot();
        Pilot p2 = pilotCreator.getPilot();
        Pilot p3 = pilotCreator.getPilot();

        AttackFacade attackFacade = new AttackFacade(stageStorage, spriteBatch, assetManager);

        this.turnProcessingFacade = new TurnProcessingFacade(actionLock, attackFacade,
                new TargetingFacade(),
                new MovementSpeedCalculator(), ImmutableMap.of(unit2, p2, unit3, p3),
                ImmutableMap.of(unit, p1), rangeCalculator, stage, hudStage, assetManager, stageStorage);

        // display

        BitmapFont font = FontCreator.getBitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.font = font;
        checkBoxStyle.checkboxOn = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/CheckboxChecked.png")));
        checkBoxStyle.checkboxOff = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/CheckboxUnchecked.png")));

        MechInfoPanelFacade mechInfoPanelFacade = new MechInfoPanelFacade();

        mechInfoPanelFacade.setTouchable(Touchable.enabled);

        TerrainTypeAwareBattleMapDecorator terrainTypeAwareBattleMapDecorator = new TerrainTypeAwareBattleMapDecorator(assetManager);
        battleMap = new BattleMap(100, 100, stageStorage, actionLock, TerrainType.Grassland, turnProcessingFacade, turnProcessingFacade, assetManager, mechInfoPanelFacade);

        terrainTypeAwareBattleMapDecorator.decorate(battleMap);

        //battleMapTreeSpreadDecorator.decorate(3, battleMap.getNodeGraphLv1());

        rangedAttackTargetCalculator = new RangedAttackTargetCalculator(battleMap, rangeCalculator, attackFacade, actionLock, stage, hudStage, assetManager, stageStorage);

        battleMap.setTemporaryObstacle(63, 30);
        battleMap.setTemporaryObstacle(60, 30);
        battleMap.setTemporaryObstacle(10, 10);

        stage.addActor(stageStorage.groundLevel);
        stage.addActor(stageStorage.mechLevel);
        stage.addActor(stageStorage.treeLevel);
        stage.addActor(stageStorage.airLevel);


        stageStorage.mechLevel.addActor(unit);
        stageStorage.mechLevel.addActor(unit2);
        stageStorage.mechLevel.addActor(unit3);

        stage.addActor(selectionMarker);
        mechInfoPanelFacade.getWeaponSelectionButton().setVisible(false);
        mechInfoPanelFacade.getDetailsButton().setVisible(false);
        mechInfoPanelFacade.getHideMenuButton().setVisible(false);
        mechInfoPanelFacade.getPilotButton().setVisible(false);
        mechInfoPanelFacade.registerComponents(hudStage);

        HudElementsFacade hudElementsFacade = new HudElementsFacade(assetManager, turnProcessingFacade, actionLock);
        hudElementsFacade.registerComponents(hudStage);



        // listeners
        unit2.addListener(new MechClickInputListener(unit2, p2, turnProcessingFacade, rangedAttackTargetCalculator, actionLock, labelStyle, checkBoxStyle, mechInfoPanelFacade, hudStage, stage));
        unit3.addListener(new MechClickInputListener(unit3, p3, turnProcessingFacade, rangedAttackTargetCalculator, actionLock, labelStyle, checkBoxStyle, mechInfoPanelFacade, hudStage, stage));
        unit.addListener(new MechClickInputListener(unit, p1, turnProcessingFacade, rangedAttackTargetCalculator, actionLock, labelStyle, checkBoxStyle, mechInfoPanelFacade, hudStage, stage));
    }

    @Override
    public void render(float delta) {

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
        spriteBatch.setProjectionMatrix(camera.combined);
        stage.act();
        stage.draw();

        hudViewport.apply();
        spriteBatch.setProjectionMatrix(hudCamera.combined);
        spriteBatch.setColor(Color.WHITE);
        hudStage.act();
        hudStage.draw();
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
