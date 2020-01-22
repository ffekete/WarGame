package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.wargame.battle.combat.MeleeAttackTargetCalculator;
import com.mygdx.wargame.battle.combat.UnitSelectionUtils;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.PathFinder;
import com.mygdx.wargame.battle.unit.AbstractWarrior;
import com.mygdx.wargame.battle.unit.SpearMen;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.battle.unit.Unit;
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
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        stage = new Stage();


        BattleMap battleMap = new BattleMap(new PathFinder(1000, 1000, shapeRenderer), 1000, 1000);
        meleeAttackTargetCalculator = new MeleeAttackTargetCalculator(battleMap, stage, shapeRenderer, new UnitSelectionUtils());

        Unit unit = new Unit(16, 10);
        unit.setTeam(Team.own);

        Unit unit2 = new Unit(16, 10);
        unit2.setTeam(Team.enemy);


        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                createSpearmanUnit(unit, String.valueOf(i), 50 + i * 10, 50 + j * 10, battleMap);
            }
        }

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                createSpearmanUnit(unit2, String.valueOf(i), 500 + i * 10, 500 + j * 10, battleMap);
            }
        }

        stage.addActor(unit);
        stage.addActor(unit2);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    private void createSpearmanUnit(Unit unit, String s, int i, int i2, BattleMap battleMap) {
        AbstractWarrior spearman2 = new SpearMen(s, shapeRenderer, selectionController, meleeAttackTargetCalculator);
        spearman2.setUnit(unit);
        spearman2.setPosition(i, i2);
        unit.add(spearman2);
        stage.addActor(spearman2);
        battleMap.setObstacle(spearman2.getX(), spearman2.getY(), 1);
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
        this.viewport.update(width, height, true);
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
