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
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.decoration.AnimatedDrawable;
import com.mygdx.wargame.battle.rules.facade.GameState;
import com.mygdx.wargame.common.ScreenRegister;
import com.mygdx.wargame.config.Config;

import static com.mygdx.wargame.config.Config.SCREEN_HUD_RATIO;

public class BattleGameMenuFacade {

    public static final int MAIN_MENU_BUTTON_WIDTH = 256;
    public static final int MAIN_MENU_BUTTON_HEIGHT = 128;

    private TextButton resumeGameButton;
    private TextButton exitGameButton;
    private TextButton optionsMenuButton;

    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton.TextButtonStyle textButtonStyle2;
    private TextButton.TextButtonStyle textButtonStyle3;

    private ActionLock actionLock;

    private AssetManager assetManager;

    private Table table;

    private boolean mainMenuShown = false;

    private HUDMediator hudMediator;

    public BattleGameMenuFacade(AssetManager assetManager, HUDMediator hudMediator) {
        this.actionLock = GameState.actionLock;
        this.assetManager = assetManager;
        this.hudMediator = hudMediator;
    }

    public void create() {

        table = new Table();

        table.setSize(400, 600);
        table.setPosition(Config.HUD_VIEWPORT_WIDTH.get() / 2 - 200, Config.HUD_VIEWPORT_HEIGHT.get() / 2 - 300);

        table.setBackground(new TextureRegionDrawable(assetManager.get("mainmenu/MainMenuButtonBackground.png", Texture.class)));

        //table.setBackground(new TextureRegionDrawable(assetManager.get("skin/BigInfoPanel.png", Texture.class)));

        this.textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = FontCreator.getBitmapFont(20);
        textButtonStyle.fontColor = Color.valueOf("FFFFFF");
        textButtonStyle.overFontColor = Color.valueOf("00FF00");
        textButtonStyle.up = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonUp.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);
        textButtonStyle.down = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonDown.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);
        textButtonStyle.over = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonOver.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);


        this.textButtonStyle2 = new TextButton.TextButtonStyle();
        textButtonStyle2.font = FontCreator.getBitmapFont(20);
        textButtonStyle2.fontColor = Color.valueOf("FFFFFF");
        textButtonStyle2.overFontColor = Color.valueOf("00FF00");
        textButtonStyle2.up = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonUp.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);
        textButtonStyle2.down = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonDown.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);
        textButtonStyle2.over = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonOver.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);

        this.textButtonStyle3 = new TextButton.TextButtonStyle();
        textButtonStyle3.font = FontCreator.getBitmapFont(20);
        textButtonStyle3.fontColor = Color.valueOf("FFFFFF");
        textButtonStyle3.overFontColor = Color.valueOf("00FF00");
        textButtonStyle3.up = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonUp.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);
        textButtonStyle3.down = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonDown.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);
        textButtonStyle3.over = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonOver.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);

        this.resumeGameButton = new TextButton("RESUME", textButtonStyle);

        this.exitGameButton = new TextButton("EXIT", textButtonStyle2);
        exitGameButton.pad(5, 15, 5, 15);
        this.optionsMenuButton = new TextButton("OPTIONS", textButtonStyle3);
        optionsMenuButton.pad(5, 15, 5, 15);

        table.add(resumeGameButton).size(200, 100).pad(5).row();
        table.add(optionsMenuButton).size(200, 100).pad(5).row();
        table.add(exitGameButton).size(200, 100).pad(5).row();

        resumeGameButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggle();
                GameState.paused = false;
                return true;
            }
        });

        exitGameButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //ScreenRegister.I.getGame().showSummaryScreen();
                System.exit(1);
                return true;
            }
        });

        optionsMenuButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ScreenRegister.I.getGame().showOptionsScreen();
                ScreenRegister.I.setLastScreen(ScreenRegister.I.getBattleScreenV2());
                return true;
            }
        });

    }

    public void registerComponents(Stage stage) {
        stage.addActor(table);
    }

    public void show() {
        table.setVisible(true);
        actionLock.setLocked(true);
    }

    public void hide() {
        table.setVisible(false);
        actionLock.setLocked(false);
    }

    public void toggle() {
        if (mainMenuShown) {
            hide();
            mainMenuShown = false;
            hudMediator.getHudElementsFacade().show();
            ScreenRegister.I.getGame().resume();
        } else {
            show();
            mainMenuShown = true;
            hudMediator.getHudElementsFacade().hide();
            ScreenRegister.I.getGame().pause();
        }
    }
}
