package com.mygdx.wargame.battle.screen.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.common.ScreenRegister;

import static com.mygdx.wargame.config.Config.SCREEN_HUD_RATIO;

public class MainMenuFacade {

    private TextButton resumeGameButton;
    private TextButton exitGameButton;
    private TextButton optionsMenuButton;

    private TextButton.TextButtonStyle textButtonStyle;

    private AssetManager assetManager;

    private Table table;

    private boolean mainMenuShown = false;

    private HUDMediator hudMediator;

    public MainMenuFacade(AssetManager assetManager, HUDMediator hudMediator) {
        this.assetManager = assetManager;
        this.hudMediator = hudMediator;
    }

    public void create() {

        table = new Table();
        table.setFillParent(true);

        table.setBackground(new TextureRegionDrawable(assetManager.get("skin/BigInfoPanel.png", Texture.class)));

        this.textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = FontCreator.getBitmapFont(20);
        textButtonStyle.down = new TextureRegionDrawable(this.assetManager.get("details/ButtonBgDown.png", Texture.class));
        textButtonStyle.up = new TextureRegionDrawable(this.assetManager.get("details/ButtonBg.png", Texture.class));
        textButtonStyle.over = new TextureRegionDrawable(this.assetManager.get("details/ButtonBgOver.png", Texture.class));

        this.resumeGameButton = new TextButton("RESUME", textButtonStyle);

        this.exitGameButton = new TextButton("EXIT", textButtonStyle);
        exitGameButton.pad(20 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 20 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO);
        this.optionsMenuButton = new TextButton("OPTIONS", textButtonStyle);
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
                ScreenRegister.I.getGame().setScreen(ScreenRegister.I.getSummaryScreen());
                return true;
            }
        });

        optionsMenuButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hide();
                ScreenRegister.I.getGame().setScreen(ScreenRegister.I.getOptionsScreen());
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
