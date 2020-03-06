package com.mygdx.wargame.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.wargame.config.Config;
import com.mygdx.wargame.util.DrawUtils;

public class MainMenuScreen implements Screen {

    private Camera camera;
    private Viewport viewport;
    private Stage stage;
    private MainMenuFacade mainMenuFacade;

    public void load(AssetManager assetManager) {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(Config.HUD_VIEWPORT_WIDTH.get(), Config.HUD_VIEWPORT_HEIGHT.get(), camera);
        stage = new Stage(viewport);
        this.mainMenuFacade = new MainMenuFacade(assetManager);
        this.mainMenuFacade.create();
        this.mainMenuFacade.registerComponents(stage);
    }

    @Override
    public void show() {
        mainMenuFacade.show();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        DrawUtils.clearScreen();
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize to :" + width + " " + height);
        viewport.update(width, height);
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
