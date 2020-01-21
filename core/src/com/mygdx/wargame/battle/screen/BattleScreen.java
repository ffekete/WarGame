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
        meleeAttackTargetCalculator = new MeleeAttackTargetCalculator(battleMap, stage, shapeRenderer);

        AbstractWarrior spearman = new SpearMen("1", shapeRenderer, selectionController, meleeAttackTargetCalculator);
        Unit unit = new Unit();
        unit.setTeam(Team.own);
        spearman.setUnit(unit);
        spearman.setPosition(10, 10);
        unit.add(spearman);
        stage.addActor(spearman);

        AbstractWarrior spearman2 = new SpearMen("2", shapeRenderer, selectionController, meleeAttackTargetCalculator);
        spearman2.setUnit(unit);
        spearman2.setPosition(35, 10);
        unit.add(spearman2);
        stage.addActor(spearman2);


        AbstractWarrior spearman3 = new SpearMen("3", shapeRenderer, selectionController, meleeAttackTargetCalculator);
        Unit unit2 = new Unit();
        unit2.setTeam(Team.enemy);
        spearman3.setUnit(unit2);
        spearman3.setPosition(500, 500);
        unit2.add(spearman3);
        stage.addActor(spearman3);

        AbstractWarrior spearman4 = new SpearMen("4", shapeRenderer, selectionController, meleeAttackTargetCalculator);
        spearman4.setUnit(unit2);
        spearman4.setPosition(535, 500);
        unit2.add(spearman4);
        stage.addActor(spearman4);

        AbstractWarrior spearman5 = new SpearMen("5", shapeRenderer, selectionController, meleeAttackTargetCalculator);
        spearman5.setUnit(unit);
        spearman5.setPosition(80, 10);
        unit.add(spearman5);
        stage.addActor(spearman5);





        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

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
