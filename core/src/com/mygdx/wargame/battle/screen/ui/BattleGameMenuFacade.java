package com.mygdx.wargame.battle.screen.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.map.decoration.AnimatedDrawable;
import com.mygdx.wargame.common.ScreenRegister;

import static com.mygdx.wargame.config.Config.SCREEN_HUD_RATIO;

public class BattleGameMenuFacade {

    private TextButton resumeGameButton;
    private TextButton exitGameButton;
    private TextButton optionsMenuButton;

    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton.TextButtonStyle textButtonStyle2;
    private TextButton.TextButtonStyle textButtonStyle3;

    private AssetManager assetManager;

    private Table table;

    private boolean mainMenuShown = false;

    private HUDMediator hudMediator;

    public BattleGameMenuFacade(AssetManager assetManager, HUDMediator hudMediator) {
        this.assetManager = assetManager;
        this.hudMediator = hudMediator;
    }

    public void create() {

        table = new Table();
        table.setFillParent(true);

        table.setBackground(new TextureRegionDrawable(assetManager.get("skin/BigInfoPanel.png", Texture.class)));

        this.textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = FontCreator.getBitmapFont(20);
        textButtonStyle.fontColor = Color.valueOf("FFFFFF");
        textButtonStyle.overFontColor = Color.valueOf("00FF00");
        textButtonStyle.up = new AnimatedDrawable(new TextureRegion(assetManager.get("skin/ButtonUp.png", Texture.class)), 0.1f, 1000, 64 ,32);
        textButtonStyle.down = new AnimatedDrawable(new TextureRegion(assetManager.get("skin/ButtonDown.png", Texture.class)), 0.1f, 1000, 64 ,32);

        this.textButtonStyle2 = new TextButton.TextButtonStyle();
        textButtonStyle2.font = FontCreator.getBitmapFont(20);
        textButtonStyle2.fontColor = Color.valueOf("FFFFFF");
        textButtonStyle2.overFontColor = Color.valueOf("00FF00");
        textButtonStyle2.up = new AnimatedDrawable(new TextureRegion(assetManager.get("skin/ButtonUp.png", Texture.class)), 0.1f, 1000, 64, 32);
        textButtonStyle2.down = new AnimatedDrawable(new TextureRegion(assetManager.get("skin/ButtonDown.png", Texture.class)), 0.1f, 1000, 64, 32);

        this.textButtonStyle3 = new TextButton.TextButtonStyle();
        textButtonStyle3.font = FontCreator.getBitmapFont(20);
        textButtonStyle3.fontColor = Color.valueOf("FFFFFF");
        textButtonStyle3.overFontColor = Color.valueOf("00FF00");
        textButtonStyle3.up = new AnimatedDrawable(new TextureRegion(assetManager.get("skin/ButtonUp.png", Texture.class)), 0.1f, 1000, 64 ,32);
        textButtonStyle3.down = new AnimatedDrawable(new TextureRegion(assetManager.get("skin/ButtonDown.png", Texture.class)), 0.1f, 1000, 64 ,32);

        this.resumeGameButton = new TextButton("RESUME", textButtonStyle);

        this.exitGameButton = new TextButton("EXIT", textButtonStyle2);
        exitGameButton.pad(20 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 20 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO);
        this.optionsMenuButton = new TextButton("OPTIONS", textButtonStyle3);
        optionsMenuButton.pad(20 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 20 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO);

        table.add(resumeGameButton).size(400 / SCREEN_HUD_RATIO, 200 / SCREEN_HUD_RATIO).pad(20 / SCREEN_HUD_RATIO).row();
        table.add(optionsMenuButton).size(400 / SCREEN_HUD_RATIO, 200 / SCREEN_HUD_RATIO).pad(20 / SCREEN_HUD_RATIO).row();
        table.add(exitGameButton).size(400 / SCREEN_HUD_RATIO, 200 / SCREEN_HUD_RATIO).pad(20 / SCREEN_HUD_RATIO).row();

        resumeGameButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggle();
                return true;
            }
        });

        exitGameButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ScreenRegister.I.getGame().showSummaryScreen();
                return true;
            }
        });

        optionsMenuButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ScreenRegister.I.getGame().showOptionsScreen();
                ScreenRegister.I.setLastScreen(ScreenRegister.I.getBattleScreen());
                return true;
            }
        });

    }

    public void registerComponents(Stage stage) {
        stage.addActor(table);
    }

    public void show() {
        table.setVisible(true);
    }

    public void hide() {
        table.setVisible(false);
    }

    public void toggle() {
        if (mainMenuShown) {
            hide();
            mainMenuShown = false;
            hudMediator.getHudElementsFacade().show();
            hudMediator.getHealthInfoPanelFacade().show();
            ScreenRegister.I.getGame().resume();
        } else {
            show();
            mainMenuShown = true;
            hudMediator.getHudElementsFacade().hide();
//            hudMediator.getMechInfoPanelFacade().hide();
//            hudMediator.getEnemyMechInfoPanelFacade().hideLocalMenu();
//            hudMediator.getDetailsPageFacade().hide();
//            hudMediator.getTargetingPanelFacade().hide();
            hudMediator.getHealthInfoPanelFacade().hide();
            ScreenRegister.I.getGame().pause();
        }
    }
}
