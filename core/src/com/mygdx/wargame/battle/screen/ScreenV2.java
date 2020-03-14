package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.TerrainType;
import com.mygdx.wargame.battle.map.render.IsometricTiledMapRendererWithSprites;
import com.mygdx.wargame.battle.rules.calculator.HeatCalculator;
import com.mygdx.wargame.battle.rules.calculator.MovementSpeedCalculator;
import com.mygdx.wargame.battle.rules.calculator.RangeCalculator;
import com.mygdx.wargame.battle.rules.calculator.StabilityDecreaseCalculator;
import com.mygdx.wargame.battle.rules.facade.AttackFacade;
import com.mygdx.wargame.battle.rules.facade.TurnProcessingFacade;
import com.mygdx.wargame.battle.rules.facade.target.TargetingFacade;
import com.mygdx.wargame.battle.screen.ui.HUDMediator;
import com.mygdx.wargame.battle.action.MoveActorAlongPathActionFactory;
import com.mygdx.wargame.battle.screen.ui.HudElementsFacade;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.mech.Templar;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.common.pilot.PilotCreator;
import com.mygdx.wargame.util.DrawUtils;

public class ScreenV2 implements Screen {

    private OrthographicCamera camera;

    private Viewport viewport;
    private Viewport hudViewPort;

    private Stage stage;
    private Stage hudStage;
    private IsometricTiledMapRendererWithSprites isometricTiledMapRenderer;
    private IsoUtils isoUtils;
    private HUDMediator hudMediator;

    public void load(AssetManagerLoaderV2 assetManagerLoader) {

        this.isoUtils = new IsoUtils();

        assetManagerLoader.load();

        camera = new OrthographicCamera();

        viewport = new FitViewport(960, 540, camera);

        stage = new Stage(viewport);

        Camera hudCamera = new OrthographicCamera();
        hudViewPort = new StretchViewport(960, 540, hudCamera);

        hudStage = new Stage(hudViewPort);

        BattleMap battleMap = new BattleMap(assetManagerLoader, TerrainType.Grassland);

        isometricTiledMapRenderer = new IsometricTiledMapRendererWithSprites(battleMap.getTiledMap());

        AbstractMech mech = new Templar("Scout", isometricTiledMapRenderer.getBatch(), assetManagerLoader);
        mech.resetMovementPoints(10);

        AbstractMech mech2 = new Templar("Scout", isometricTiledMapRenderer.getBatch(), assetManagerLoader);
        mech.resetMovementPoints(10);

        StageElementsStorage stageElementsStorage = new StageElementsStorage();
        ActionLock actionLock = new ActionLock();

        hudMediator = new HUDMediator();

        TurnProcessingFacade turnProcessingFacade = new TurnProcessingFacade(actionLock,
                new AttackFacade(stageElementsStorage,assetManagerLoader.getAssetManager(), actionLock),
                new TargetingFacade(stageElementsStorage),
                new MovementSpeedCalculator(),
                ImmutableMap.<Mech, Pilot>builder().put(mech, new PilotCreator().getPilot()).build(),
                ImmutableMap.<Mech, Pilot>builder().put(mech2, new PilotCreator().getPilot()).build(),
                new RangeCalculator(),
                stage,
                assetManagerLoader.getAssetManager(),
                stageElementsStorage,
                new HeatCalculator(),
                new StabilityDecreaseCalculator(),
                hudMediator
        );

        hudMediator.setHudElementsFacade(new HudElementsFacade(assetManagerLoader.getAssetManager(), turnProcessingFacade, actionLock, hudMediator));
        hudMediator.getHudElementsFacade().create();
        hudMediator.getHudElementsFacade().registerComponents(hudStage);

        isometricTiledMapRenderer.addSprite(mech);

        stage.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 newCoords = stage.stageToScreenCoordinates(new Vector2(x, y));
                Vector2 s2c = isoUtils.screenToCell(newCoords.x, newCoords.y, camera);

//                IsoMoveToAction moveToAction = new IsoMoveToAction(mech);
//                moveToAction.setPosition(s2c.x, s2c.y);
//                moveToAction.setDuration(1f);
//                stage.addAction(moveToAction);
                GraphPath<Node> path = battleMap.calculatePath(
                        battleMap.getNodeGraph().getNodeWeb()[(int)mech.getX()][(int)mech.getY()],
                        battleMap.getNodeGraph().getNodeWeb()[(int)s2c.x][(int)s2c.y]
                );
                stage.addAction(new MoveActorAlongPathActionFactory().getMovementAction(path, mech));
                return true;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.S) {
                    camera.position.y--;
                }

                if (keycode == Input.Keys.W) {
                    camera.position.y++;
                }

                return true;
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        hudMediator.getHudElementsFacade().show();
    }


    @Override
    public void render(float delta) {

        DrawUtils.clearScreen();

        //hudMediator.getHudElementsFacade().update();

        viewport.apply();

        stage.act();

        isometricTiledMapRenderer.setView(camera);
        isometricTiledMapRenderer.render();

        hudViewPort.apply();

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
        isometricTiledMapRenderer.dispose();
        stage.dispose();
    }
}