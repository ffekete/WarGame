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
import com.mygdx.wargame.battle.combat.RangedAttackTargetCalculator;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.TerrainType;
import com.mygdx.wargame.mech.Scout;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.battle.input.MechClickInputListener;
import com.mygdx.wargame.util.DrawUtils;

public class BattleScreen implements Screen {

    public static final int WIDTH = 96;
    public static final int HEIGHT = 54;
    private Camera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private SelectionController selectionController;
    private RangedAttackTargetCalculator rangedAttackTargetCalculator;
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    private ActionLock actionLock;

    public BattleScreen(SelectionController selectionController) {
        this.selectionController = selectionController;
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
        assetManager.finishLoading();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        stage = new Stage(viewport, spriteBatch);


        shapeRenderer.setProjectionMatrix(camera.combined);

        BattleMap battleMap = new BattleMap(100, 100, selectionController, stage, actionLock, TerrainType.Desert);

        rangedAttackTargetCalculator = new RangedAttackTargetCalculator(battleMap, actionLock);

        Scout unit3 = new Scout("2", spriteBatch, selectionController, assetManager);
        unit3.setPosition(63, 30);
        unit3.setTeam(Team.own);
        unit3.setMovementPoints(300);
        unit3.addListener(new MechClickInputListener(unit3, selectionController, rangedAttackTargetCalculator, actionLock));
        battleMap.setTemporaryObstacle(63, 30);

        Scout unit2 = new Scout("2", spriteBatch, selectionController, assetManager);
        unit2.setPosition(60, 30);
        unit2.setTeam(Team.own);
        unit2.setMovementPoints(300);
        unit2.addListener(new MechClickInputListener(unit2, selectionController, rangedAttackTargetCalculator, actionLock));
        battleMap.setTemporaryObstacle(60, 30);

        Scout unit = new Scout("1", spriteBatch, selectionController, assetManager);
        unit.setPosition(10, 10);
        unit.setTeam(Team.enemy);
        unit.setMovementPoints(10);
        unit.addListener(new MechClickInputListener(unit, selectionController, rangedAttackTargetCalculator, actionLock));
        battleMap.setTemporaryObstacle(10, 10);

        stage.addActor(unit2);
        stage.addActor(unit3);
        stage.addActor(unit);

        Gdx.input.setInputProcessor(stage);
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
