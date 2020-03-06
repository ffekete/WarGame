package com.mygdx.wargame.summary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.wargame.options.OptionsMenuFacade;
import com.mygdx.wargame.util.DrawUtils;

import static com.mygdx.wargame.config.Config.HUD_VIEWPORT_HEIGHT;
import static com.mygdx.wargame.config.Config.HUD_VIEWPORT_WIDTH;

public class SummaryScreen extends ScreenAdapter {

    private SummaryScreenFacade summaryScreenFacade;
    private Viewport viewport;
    private Camera camera;
    private Stage stage;
    private AssetManager assetManager;

    public void load(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void show() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(HUD_VIEWPORT_WIDTH.get(), HUD_VIEWPORT_HEIGHT.get(), camera);

        stage = new Stage(viewport);

        summaryScreenFacade = new SummaryScreenFacade(assetManager);
        summaryScreenFacade.create();
        summaryScreenFacade.register(stage);

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
