package com.mygdx.wargame.battle.screen.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.decoration.AnimatedDrawable;
import com.mygdx.wargame.common.ScreenRegister;

import static com.mygdx.wargame.config.Config.*;

public class GameEndFacade {

    private Table resultTable;
    private boolean won;
    private Label.LabelStyle labelStyle;
    private Label label;
    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton textButton;
    private AssetManager assetManager;
    private Table outerTable;
    private ActionLock actionLock;

    public GameEndFacade(AssetManager assetManager, ActionLock actionLock) {
        this.assetManager = assetManager;
        this.actionLock = actionLock;
    }

    public void create() {

        outerTable = new Table();
        resultTable = new Table();

        labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(15);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = FontCreator.getBitmapFont(13);
        textButtonStyle.fontColor = Color.valueOf("FFFFFF");
        textButtonStyle.overFontColor = Color.valueOf("00FF00");

        textButtonStyle.up = new AnimatedDrawable(new TextureRegion(assetManager.get("details/ButtonBg.png", Texture.class)), 0.1f, 1000, 32 ,16);


        textButton = new TextButton("NEXT >>", textButtonStyle);
        Label resultLabel = new Label(won ? "You won" : "You lost", labelStyle);
        resultTable.pad(40 / SCREEN_HUD_RATIO);
        resultTable.add(resultLabel).center().row();
        resultTable.add(textButton).size(200 / SCREEN_HUD_RATIO, 100 / SCREEN_HUD_RATIO).center().pad(40 / SCREEN_HUD_RATIO);
        resultTable.background(new TextureRegionDrawable(assetManager.get("skin/InfoPanel.png", Texture.class)));
        resultTable.setSize(HUD_VIEWPORT_WIDTH.get() / 2f, HUD_VIEWPORT_HEIGHT.get() / 2f);

        outerTable.add(resultTable).center().size(HUD_VIEWPORT_WIDTH.get() / 2f, HUD_VIEWPORT_HEIGHT.get() / 2f);
        outerTable.setFillParent(true);

        textButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ScreenRegister.I.getGame().showSummaryScreen();
                return true;
            }
        });
    }

    public void show() {
        ScreenRegister.I.getGame().pause();
        actionLock.setLocked(true);
        outerTable.setVisible(true);
    }

    public void hide() {
        actionLock.setLocked(false);
        ScreenRegister.I.getGame().resume();
        outerTable.setVisible(false);
    }

    public void register(Stage stage) {
        stage.addActor(outerTable);
    }

    public void setWon(boolean won) {
        this.won = won;
    }
}
