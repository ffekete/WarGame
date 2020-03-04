package com.mygdx.wargame.options;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.map.decoration.AnimatedDrawable;
import com.mygdx.wargame.battle.screen.ui.FontCreator;
import com.mygdx.wargame.common.ScreenRegister;

import static com.mygdx.wargame.config.Config.SCREEN_HUD_RATIO;

public class OptionsMenuFacade {

    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton resumeButton;
    private AssetManager assetManager;
    private Table outerTable;

    public OptionsMenuFacade(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void create() {
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = FontCreator.getBitmapFont(13);
        textButtonStyle.overFontColor = Color.valueOf("00FF00");
        textButtonStyle.fontColor = Color.valueOf("FFFFFF");

        textButtonStyle.up = new AnimatedDrawable(new TextureRegion(assetManager.get("details/ButtonBg.png", Texture.class)), 0.1f, 1000);

        outerTable= new Table();
        outerTable.setFillParent(true);
        outerTable.setTouchable(Touchable.enabled);

        resumeButton = new TextButton("RESUME", textButtonStyle);

        resumeButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ScreenRegister.I.getGame().setScreen(ScreenRegister.I.getLastScreen());
                return true;
            }
        });

        outerTable.add(resumeButton).center().size(400 / SCREEN_HUD_RATIO, 200 / SCREEN_HUD_RATIO);
    }


    public void register(Stage stage) {
        stage.addActor(outerTable);
    }

}