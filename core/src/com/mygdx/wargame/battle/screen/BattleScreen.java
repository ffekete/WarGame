package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.unit.AbstractWarrior;
import com.mygdx.wargame.battle.unit.SpearMen;
import com.mygdx.wargame.battle.unit.Unit;
import com.mygdx.wargame.util.DrawUtils;

public class BattleScreen implements Screen {

    private Camera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private SelectionController selectionController;

    public BattleScreen(SelectionController selectionController) {
        this.selectionController = selectionController;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport  = new FitViewport(1920, 1080, camera);
        viewport.apply();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        stage = new Stage();

        AbstractWarrior spearman  =new SpearMen(shapeRenderer, selectionController);
        Unit unit = new Unit();
        spearman.setUnit(unit);
        spearman.setPosition(10,10);
        unit.add(spearman);
        stage.addActor(spearman);

        AbstractWarrior spearman2  =new SpearMen(shapeRenderer, selectionController);
        spearman2.setUnit(unit);
        spearman2.setPosition(35,10);
        unit.add(spearman2);
        stage.addActor(spearman2);

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
