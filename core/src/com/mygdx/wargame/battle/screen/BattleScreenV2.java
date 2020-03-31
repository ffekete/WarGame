package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.wargame.battle.action.*;
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
import com.mygdx.wargame.battle.rules.facade.WeaponRangeMarkerUpdater;
import com.mygdx.wargame.battle.rules.facade.target.TargetingFacade;
import com.mygdx.wargame.battle.screen.ui.HUDMediator;
import com.mygdx.wargame.battle.screen.ui.HudElementsFacade;
import com.mygdx.wargame.battle.screen.ui.WeaponSelectionFacade;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.battle.unit.action.*;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.config.Config;
import com.mygdx.wargame.util.DrawUtils;
import com.mygdx.wargame.util.MathUtils;

import java.util.Map;
import java.util.Optional;

public class BattleScreenV2 implements Screen {

    private OrthographicCamera camera;

    private Viewport viewport;
    private Viewport hudViewPort;

    private Stage stage;
    private Stage hudStage;
    private IsometricTiledMapRendererWithSprites isometricTiledMapRenderer;
    private IsoUtils isoUtils;
    private HUDMediator hudMediator;
    private TurnProcessingFacade turnProcessingFacade;
    private BattleMap battleMap;
    private RangeCalculator rangeCalculator = new RangeCalculator();
    private WeaponRangeMarkerUpdater weaponRangeMarkerUpdater = new WeaponRangeMarkerUpdater();
    private WeaponSelectionFacade weaponSelectionFacade;

    public void load(AssetManagerLoaderV2 assetManagerLoader, BattleScreenInputData battleScreenInputData) {

        this.isoUtils = new IsoUtils();

        assetManagerLoader.load();

        camera = new OrthographicCamera();

        viewport = new StretchViewport(960, 540, camera);

        stage = new Stage(viewport);

        Camera hudCamera = new OrthographicCamera();
        hudViewPort = new StretchViewport(Config.HUD_VIEWPORT_WIDTH.get(), Config.HUD_VIEWPORT_HEIGHT.get(), hudCamera);

        hudStage = new Stage(hudViewPort);

        battleMap = new BattleMap(assetManagerLoader, TerrainType.Grassland, assetManagerLoader);

        isometricTiledMapRenderer = new IsometricTiledMapRendererWithSprites(battleMap.getTiledMap());

        battleMap.getNodeGraph().setIsometricTiledMapRendererWithSprites(isometricTiledMapRenderer);

        StageElementsStorage stageElementsStorage = new StageElementsStorage();
        stageElementsStorage.stage = stage;
        ActionLock actionLock = new ActionLock();

        hudMediator = new HUDMediator();

        turnProcessingFacade = new TurnProcessingFacade(actionLock,
                new AttackFacade(stageElementsStorage, assetManagerLoader.getAssetManager(), actionLock, isometricTiledMapRenderer),
                new TargetingFacade(stageElementsStorage),
                new MovementSpeedCalculator(),
                battleScreenInputData.getPlayerTeam(),
                battleScreenInputData.getAiTeam(),
                new RangeCalculator(),
                stageElementsStorage,
                new HeatCalculator(),
                new StabilityDecreaseCalculator(),
                hudMediator,
                battleMap, assetManagerLoader, isometricTiledMapRenderer);

        hudMediator.setHudElementsFacade(new HudElementsFacade(assetManagerLoader.getAssetManager(), turnProcessingFacade, actionLock, hudMediator));
        hudMediator.getHudElementsFacade().create();
        hudMediator.getHudElementsFacade().registerComponents(hudStage);

        battleScreenInputData.getAiTeam().keySet().forEach(mech -> {
            isometricTiledMapRenderer.addObject(mech);
            battleMap.addDirectionMarker(mech.getDirection(), (int) mech.getX(), (int) mech.getY());
            mech.setTeam(Team.enemy);
            //battleMap.setTemporaryObstacle(mech.getX(), mech.getY());
        });
        battleScreenInputData.getPlayerTeam().keySet().forEach(mech -> {
            isometricTiledMapRenderer.addObject(mech);
            battleMap.addDirectionMarker(mech.getDirection(), (int) mech.getX(), (int) mech.getY());
            mech.setTeam(Team.own);
            //battleMap.setTemporaryObstacle(mech.getX(), mech.getY());
        });

        camera.zoom = 1.4f;
        camera.position.x = 640;
        camera.position.y = 20;

        stage.addListener(new InputListener() {

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {

                if (actionLock.isLocked() || turnProcessingFacade.getNext().getKey().moved())
                    return;

                battleMap.clearPathMarkers();

                Vector2 newCoords = stage.stageToScreenCoordinates(new Vector2(x, y));
                Vector2 s2c = isoUtils.screenToCell(newCoords.x, newCoords.y, camera);

                if (s2c.x < 0 || s2c.x >= BattleMap.WIDTH || s2c.y < 0 || s2c.y >= BattleMap.HEIGHT) {
                    battleMap.clearPathMarkers();
                    return;
                }

                Optional<AbstractMech> enemyMechAtCoordinates = battleScreenInputData.getAiTeam().keySet().stream().filter(mech -> mech.getX() == s2c.x && mech.getY() == s2c.y).findFirst();
                Optional<AbstractMech> ownMechAtCoordinates = battleScreenInputData.getPlayerTeam().keySet().stream().filter(mech -> mech.getX() == s2c.x && mech.getY() == s2c.y).findFirst();

                if (!enemyMechAtCoordinates.isPresent() && !ownMechAtCoordinates.isPresent()) {
                    GraphPath<Node> path = battleMap.calculatePath(
                            turnProcessingFacade.getNext().getKey(), battleMap.getNodeGraph().getNodeWeb()[(int) turnProcessingFacade.getNext().getKey().getX()][(int) turnProcessingFacade.getNext().getKey().getY()],
                            battleMap.getNodeGraph().getNodeWeb()[(int) s2c.x][(int) s2c.y]
                    );

                    int nodeToCheck = Math.min(turnProcessingFacade.getNext().getKey().getMovementPoints(), path.getCount() -1);
                    if (path.getCount() == 0 || path.get( nodeToCheck).isImpassable()  || isometricTiledMapRenderer.getObjects()
                            .stream()
                            .filter(o -> Mech.class.isAssignableFrom(o.getClass()))
                            .map(o -> (Mech) o)
                            .anyMatch(m -> (int) m.getX() == (int) path.get(nodeToCheck).getX() && (int) m.getY() == (int) path.get(nodeToCheck).getY())) {
                        battleMap.clearPathMarkers();
                        return;
                    }

                    path.forEach(p -> {
                        battleMap.toggleMarker((int) p.getX(), (int) p.getY(), true);
                        battleMap.addPathMarker((int) p.getX(), (int) p.getY());

                        // stage.addActor(new MovementPathMarker(assetManagerLoader.getAssetManager().get("info/MovementPath.png", Texture.class), battleMap));
                    });
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {


                if (actionLock.isLocked())
                    return;

                Vector2 newCoords = stage.stageToScreenCoordinates(new Vector2(x, y));
                Vector2 s2c = isoUtils.screenToCell(newCoords.x, newCoords.y, camera);

                Optional<AbstractMech> mechAtCoordinates = battleScreenInputData.getAiTeam().keySet().stream().filter(mech -> mech.getX() == s2c.x && mech.getY() == s2c.y).findFirst();

                if (mechAtCoordinates.isPresent()) {
                    SequenceAction sequenceAction = new SequenceAction();
                    sequenceAction.addAction(new LockAction(actionLock));
                    Optional<Map.Entry<AbstractMech, Pilot>> pilotAtCoordinates = battleScreenInputData.getAiTeam().entrySet().stream().filter(entry -> mechAtCoordinates.get() == entry.getKey()).findFirst();
                    int minRange = rangeCalculator.calculateAllWeaponsRange(turnProcessingFacade.getNext().getValue(), turnProcessingFacade.getNext().getKey());
                    if(minRange < MathUtils.getDistance(turnProcessingFacade.getNext().getKey().getX(), turnProcessingFacade.getNext().getKey().getY(), mechAtCoordinates.get().getX(), mechAtCoordinates.get().getY())) {
                        return;
                    }

                    ParallelAction attackActions = new ParallelAction();
                    attackActions.addAction(new ChangeDirectionAction(mechAtCoordinates.get().getX(), mechAtCoordinates.get().getY(), turnProcessingFacade.getNext().getKey()));
                    attackActions.addAction(new RemoveDirectionMarkerAction(turnProcessingFacade.getNext().getKey().getX(), turnProcessingFacade.getNext().getKey().getY(), battleMap));
                    attackActions.addAction(new AddDirectionMarkerAction(turnProcessingFacade.getNext().getKey(), battleMap));

                    if(!turnProcessingFacade.getNext().getKey().isRangedAttack()) {
                        attackActions.addAction(new AttackAnimationAction(turnProcessingFacade.getNext().getKey(), mechAtCoordinates.get()));
                    } else {
                        attackActions.addAction(new BulletAnimationAction(turnProcessingFacade.getNext().getKey(), mechAtCoordinates.get(), assetManagerLoader.getAssetManager(), actionLock, minRange, stageElementsStorage, isometricTiledMapRenderer, battleMap, sequenceAction));
                    }

                    int heatBeforeAttack = turnProcessingFacade.getNext().getKey().getHeatLevel();
                    int ammoBeforeAttack = turnProcessingFacade.getNext().getKey().getAmmoCount();
                    AttackAction attackAction = new AttackAction(turnProcessingFacade.getAttackFacade(), turnProcessingFacade.getNext().getKey(), turnProcessingFacade.getNext().getValue(), mechAtCoordinates.get(), pilotAtCoordinates.get().getValue(), battleMap, minRange, null);

                    sequenceAction.addAction(attackActions);
                    sequenceAction.addAction(attackAction);
                    sequenceAction.addAction(new IntAction(ammoBeforeAttack, turnProcessingFacade.getNext().getKey()::getAmmoCount, 1f, hudMediator.getHudElementsFacade().getAmmoImage().getLabel(), "ammo: "));
                    sequenceAction.addAction(new DelayAction(0.5f));
                    sequenceAction.addAction(new IntAction(heatBeforeAttack, turnProcessingFacade.getNext().getKey()::getHeatLevel, 1f, hudMediator.getHudElementsFacade().getHeatImage().getLabel(), "heat: "));

                    sequenceAction.addAction(new ClearRangeMarkersAction(battleMap));

                    if(turnProcessingFacade.getNext().getKey().canMoveAfterAttack()) {
                        sequenceAction.addAction(new ClearMovementMarkersAction(battleMap));

                        sequenceAction.addAction(new AddMovementMarkersAction(battleMap, turnProcessingFacade.getNext().getKey()));
                    }
                    sequenceAction.addAction(new DelayAction(1f));
                    sequenceAction.addAction(new UnlockAction(actionLock, "end of attack"));
                    stageElementsStorage.stage.addAction(sequenceAction);
                    //battleMap.clearRangeMarkers();
                    //battleMap.getNodeGraph().disconnectCities(battleMap.getNodeGraph().getNodeWeb()[(int) turnProcessingFacade.getNext().getKey().getX()][(int) turnProcessingFacade.getNext().getKey().getY()]);
                } else {

                    if(turnProcessingFacade.getNext().getKey().moved()) {
                        return;
                    }

                    GraphPath<Node> path = battleMap.calculatePath( turnProcessingFacade.getNext().getKey(),
                            battleMap.getNodeGraph().getNodeWeb()[(int) turnProcessingFacade.getNext().getKey().getX()][(int) turnProcessingFacade.getNext().getKey().getY()],
                            battleMap.getNodeGraph().getNodeWeb()[(int) s2c.x][(int) s2c.y]
                    );

                    if (path.getCount() == 0) {
                        return;
                    }

                    battleMap.clearMovementMarkers();
                    battleMap.clearPathMarkers();
                    battleMap.clearRangeMarkers();

                    SequenceAction moveThenUpdateAction = new SequenceAction();
                    moveThenUpdateAction.addAction(new RemoveSelectionMarkerAction(isometricTiledMapRenderer));
                    moveThenUpdateAction.addAction(new MoveActorAlongPathActionFactory(battleMap).getMovementAction(path, turnProcessingFacade.getNext().getKey()));
                    moveThenUpdateAction.addAction(new RepopulateRangeMarkersAction(battleMap, turnProcessingFacade.getNext().getValue(), turnProcessingFacade.getNext().getKey(), weaponRangeMarkerUpdater));
                    moveThenUpdateAction.addAction(new CreateSelectionMarkerAction(isometricTiledMapRenderer, assetManagerLoader, turnProcessingFacade.getNext().getKey()));
                    moveThenUpdateAction.addAction(new SetMovedAction(turnProcessingFacade.getNext().getKey()));
                    stage.addAction(moveThenUpdateAction);
                    //battleMap.getNodeGraph().disconnectCities(battleMap.getNodeGraph().getNodeWeb()[(int) s2c.x][(int) s2c.y]);

                    //turnProcessingFacade.getNext().getKey().setMoved(true);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 newCoords = stage.stageToScreenCoordinates(new Vector2(x, y));
                Vector2 s2c = isoUtils.screenToCell(newCoords.x, newCoords.y, camera);
                //battleMap.getNodeGraph().reconnectCities(battleMap.getNodeGraph().getNodeWeb()[(int) turnProcessingFacade.getNext().getKey().getX()][(int) turnProcessingFacade.getNext().getKey().getY()]);
                return true;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.S) {
                    camera.position.y -= 10;
                }

                if (keycode == Input.Keys.W) {
                    camera.position.y += 10;
                }

                return true;
            }
        });

        weaponSelectionFacade = new WeaponSelectionFacade(assetManagerLoader, hudMediator, turnProcessingFacade);
        weaponSelectionFacade.create();
        weaponSelectionFacade.registerComponents(hudStage);
        hudMediator.setWeaponSelectionFacade(weaponSelectionFacade);

    }

    @Override
    public void show() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hudStage);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        hudMediator.getHudElementsFacade().show();
    }


    @Override
    public void render(float delta) {

        DrawUtils.clearScreen();

        //hudMediator.getHudElementsFacade().update();


        turnProcessingFacade.process(battleMap);

        hudMediator.getHudElementsFacade().update();

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
        stage.getViewport().update(width, height, false);
        hudViewPort.update(width, height, false);
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