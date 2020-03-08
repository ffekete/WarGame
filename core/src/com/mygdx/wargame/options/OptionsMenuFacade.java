package com.mygdx.wargame.options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.wargame.battle.map.decoration.AnimatedDrawable;
import com.mygdx.wargame.battle.screen.ui.FontCreator;
import com.mygdx.wargame.common.ScreenRegister;
import com.mygdx.wargame.config.Config;

import java.util.Arrays;
import java.util.Comparator;

import static com.mygdx.wargame.config.Config.SCREEN_HUD_RATIO;

public class OptionsMenuFacade {

    private TextButton.TextButtonStyle textButtonStyle;
    private AssetManager assetManager;
    private Table outerTable;
    private Table warningTable;
    private TextButton okButton;

    public OptionsMenuFacade(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void create() {
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = FontCreator.getBitmapFont(13);
        textButtonStyle.overFontColor = Color.valueOf("00FF00");
        textButtonStyle.fontColor = Color.valueOf("FFFFFF");

        textButtonStyle.up = new AnimatedDrawable(new TextureRegion(assetManager.get("details/ButtonBg.png", Texture.class)), 0.1f, 1000, 32, 16);

        outerTable = new Table();
        outerTable.setFillParent(true);
        outerTable.setTouchable(Touchable.enabled);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(13);

        List.ListStyle listStyle = new List.ListStyle();
        listStyle.font = FontCreator.getBitmapFont(13);
        listStyle.selection = new TextureRegionDrawable(assetManager.get("SelectionBg.png", Texture.class));
        listStyle.background = new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class));

        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();

        SelectBox.SelectBoxStyle selectBoxStyle = new SelectBox.SelectBoxStyle();
        selectBoxStyle.background = new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class));
        selectBoxStyle.font = FontCreator.getBitmapFont(13);
        selectBoxStyle.listStyle = listStyle;
        selectBoxStyle.scrollStyle = scrollPaneStyle;

        SelectBox<Graphics.DisplayMode> resolutionBox = new SelectBox<>(selectBoxStyle);
        Array<Graphics.DisplayMode> modes = new Array<>();
        Arrays.stream(Gdx.graphics.getDisplayModes()).forEach(mode -> modes.add(mode));

        modes.sort(new Comparator<Graphics.DisplayMode>() {
            @Override
            public int compare(Graphics.DisplayMode o1, Graphics.DisplayMode o2) {
                return Integer.compare(o1.width, o2.width);
            }
        });

        resolutionBox.setItems(modes);


        resolutionBox.addListener(new ChangeListener() {
            @Override
            public boolean handle(Event event) {
                return super.handle(event);
            }

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Config.SCREEN_SIZE_X = resolutionBox.getSelected().width;
                Config.cfg.screenSizeX = resolutionBox.getSelected().width;
                Config.SCREEN_SIZE_Y = resolutionBox.getSelected().height;
                Config.cfg.screenSizeY = resolutionBox.getSelected().height;
                Config.save();
                warningTable.setVisible(true);
                outerTable.setVisible(false);

            }
        });

        TextButton resumeButton = new TextButton("BACK", textButtonStyle);

        resumeButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // we don't want to load here, just go back
                ScreenRegister.I.getGame().setScreen(ScreenRegister.I.getLastScreen());
                return true;
            }
        });

        outerTable.add(resolutionBox).row();
        outerTable.add(resumeButton).center().size(300 / SCREEN_HUD_RATIO, 150 / SCREEN_HUD_RATIO);

        warningTable = new Table();
        warningTable.add(new Label("Changes will apply after the game is restarted!", labelStyle)).row();
        okButton = new TextButton("", textButtonStyle);
        warningTable.add(okButton).center();

        warningTable.setVisible(false);

        okButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                warningTable.setVisible(false);
                outerTable.setVisible(true);
                return true;
            }
        });
    }


    public void register(Stage stage) {
        stage.addActor(outerTable);
        stage.addActor(warningTable);
    }

}
