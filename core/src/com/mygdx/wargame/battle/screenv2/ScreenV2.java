package com.mygdx.wargame.battle.screenv2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.wargame.battle.screenv2.render.IsometricTiledMapRendererWithSprites;
import com.mygdx.wargame.battle.screenv2.tile.TileSets;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.mech.Scout;
import com.mygdx.wargame.util.DrawUtils;

public class ScreenV2 implements Screen {

    private OrthographicCamera camera;

    private Viewport viewport;

    private Stage stage;
    private IsometricTiledMapRendererWithSprites isometricTiledMapRenderer;
    private IsoUtils isoUtils;

    public void load(AssetManagerLoaderV2 assetManagerLoader) {

        this.isoUtils = new IsoUtils();

        assetManagerLoader.load();

        camera = new OrthographicCamera();

        viewport = new FitViewport(480, 270, camera);

        stage = new Stage(viewport);

        TiledMap tiledMap = new TiledMapGenerator(assetManagerLoader).generate(15, 15, TileSets.GrassLand);

        isometricTiledMapRenderer = new IsometricTiledMapRendererWithSprites(tiledMap);

        Mech mech = new Scout("Scout", isometricTiledMapRenderer.getBatch(), assetManagerLoader);

        isometricTiledMapRenderer.addSprite(mech);

        stage.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 newCoords = stage.stageToScreenCoordinates(new Vector2(x, y));
                Vector2 s2c = isoUtils.screenToCell(newCoords.x, newCoords.y, camera);
                IsoMoveToAction moveToAction = new IsoMoveToAction(mech);
                moveToAction.setPosition(s2c.x, s2c.y);
                moveToAction.setDuration(1f);
                stage.addAction(moveToAction);
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
    }


    @Override
    public void render(float delta) {

        DrawUtils.clearScreen();

        viewport.apply();

        stage.act();

        isometricTiledMapRenderer.setView(camera);
        isometricTiledMapRenderer.render();

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