package com.mygdx.wargame.options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.wargame.util.DrawUtils;

import static com.mygdx.wargame.config.Config.HUD_VIEWPORT_HEIGHT;
import static com.mygdx.wargame.config.Config.HUD_VIEWPORT_WIDTH;

public class OptionsScreen extends ScreenAdapter {

    private OptionsMenuFacade optionsMenuFacade;
    private Viewport viewport;
    private Camera camera;
    private Stage stage;
    private AssetManager assetManager;

    @Override
    public void show() {

        assetManager = new AssetManager();
        assetManager.load("details/ButtonBg.png", Texture.class);
        assetManager.load("details/ButtonBgDown.png", Texture.class);
        assetManager.load("details/ButtonBgOver.png", Texture.class);
        assetManager.finishLoading();

        camera = new OrthographicCamera();
        viewport = new StretchViewport(HUD_VIEWPORT_WIDTH, HUD_VIEWPORT_HEIGHT, camera);

        stage = new Stage(viewport);

        optionsMenuFacade = new OptionsMenuFacade(assetManager);
        optionsMenuFacade.create();
        optionsMenuFacade.register(stage);

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
