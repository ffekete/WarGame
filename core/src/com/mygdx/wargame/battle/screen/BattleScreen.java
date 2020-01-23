package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.wargame.battle.combat.MeleeAttackTargetCalculator;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.unit.SpearMen;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.input.ManInputListener;
import com.mygdx.wargame.util.DrawUtils;

public class BattleScreen implements Screen {

    private Camera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private SelectionController selectionController;
    private MeleeAttackTargetCalculator meleeAttackTargetCalculator;

    public BattleScreen(SelectionController selectionController) {
        this.selectionController = selectionController;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(160, 120, camera);
        viewport.update(160, 120, true);
        viewport.apply();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        stage = new Stage();
        stage.setViewport(viewport);

        shapeRenderer.setProjectionMatrix(camera.combined);

        BattleMap battleMap = new BattleMap(100, 100);
        meleeAttackTargetCalculator = new MeleeAttackTargetCalculator(battleMap, stage, battleMap.getNodeGraphLv1());

        SpearMen unit = new SpearMen("1", shapeRenderer, selectionController);
        unit.setPosition(10, 10);
        unit.setTeam(Team.enemy);
        unit.setMovementPoints(10);
        unit.addListener(new ManInputListener(unit, selectionController, meleeAttackTargetCalculator));

        SpearMen unit2 = new SpearMen("2", shapeRenderer, selectionController);
        unit2.setPosition(60, 30);
        unit2.setTeam(Team.own);
        unit2.setMovementPoints(30);
        unit2.addListener(new ManInputListener(unit2, selectionController, meleeAttackTargetCalculator));

        stage.addActor(unit);
        stage.addActor(unit2);

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println(x + " " + y);
                return true;
            }
        });

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        DrawUtils.clearScreen();
        shapeRenderer.begin();
        stage.act();
        stage.draw();
        shapeRenderer.end();
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
