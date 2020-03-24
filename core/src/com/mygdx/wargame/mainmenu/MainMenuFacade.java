package com.mygdx.wargame.mainmenu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.map.decoration.AnimatedDrawable;
import com.mygdx.wargame.battle.screen.ui.FontCreator;
import com.mygdx.wargame.common.ScreenRegister;

import static com.mygdx.wargame.config.Config.SCREEN_HUD_RATIO;

public class MainMenuFacade {

    public static final int MAIN_MENU_BUTTON_WIDTH = 256;
    public static final int MAIN_MENU_BUTTON_HEIGHT = 128;
    private TextButton newGameGameButton;
    private TextButton exitGameButton;
    private TextButton optionsMenuButton;

    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton.TextButtonStyle textButtonStyle2;
    private TextButton.TextButtonStyle textButtonStyle3;

    private AssetManager assetManager;

    private Table table;

    private boolean stopRestart = false;


    public MainMenuFacade(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void create() {

        table = new Table();
        table.setFillParent(true);

        table.setBackground(new TextureRegionDrawable(assetManager.get("mainmenu/BackGround.jpg", Texture.class)));

        Table innerTable = new Table();
        table.add(innerTable);

        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(assetManager.get("mainmenu/MainMenuButtonBackground.png", Texture.class));
        innerTable.setBackground(textureRegionDrawable.tint(Color.valueOf("FFFFFFAA")));

        this.textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = FontCreator.getBitmapFont(15);
        textButtonStyle.fontColor = Color.valueOf("FFFFFF");
        textButtonStyle.overFontColor = Color.valueOf("00FF00");
        textButtonStyle.up = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonUp.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);
        textButtonStyle.down = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonDown.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);
        textButtonStyle.over = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonOver.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);

        this.textButtonStyle2 = new TextButton.TextButtonStyle();
        textButtonStyle2.font = FontCreator.getBitmapFont(15);
        textButtonStyle2.fontColor = Color.valueOf("FFFFFF");
        textButtonStyle2.overFontColor = Color.valueOf("00FF00");
        textButtonStyle2.up = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonUp.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);
        textButtonStyle2.down = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonDown.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);
        textButtonStyle2.over = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonOver.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);

        this.textButtonStyle3 = new TextButton.TextButtonStyle();
        textButtonStyle3.font = FontCreator.getBitmapFont(15);
        textButtonStyle3.fontColor = Color.valueOf("FFFFFF");
        textButtonStyle3.overFontColor = Color.valueOf("00FF00");
        textButtonStyle3.up = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonUp.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);
        textButtonStyle3.down = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonDown.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);
        textButtonStyle3.over = new AnimatedDrawable(new TextureRegion(assetManager.get("mainmenu/MainMenuButtonOver.png", Texture.class)), 0.2f, 10, MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT);

        this.newGameGameButton = new TextButton("NEW GAME", textButtonStyle);

        this.exitGameButton = new TextButton("EXIT", textButtonStyle3);
        exitGameButton.pad(20 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 20 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO);
        this.optionsMenuButton = new TextButton("OPTIONS", textButtonStyle2);
        optionsMenuButton.pad(20 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 20 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO);
        optionsMenuButton.center();

        innerTable.add(newGameGameButton).size(400 / SCREEN_HUD_RATIO, 250 / SCREEN_HUD_RATIO).pad(20 / SCREEN_HUD_RATIO).row();
        innerTable.add(optionsMenuButton).size(400 / SCREEN_HUD_RATIO, 250 / SCREEN_HUD_RATIO).pad(20 / SCREEN_HUD_RATIO).row();
        innerTable.add(exitGameButton).size(400 / SCREEN_HUD_RATIO, 250 / SCREEN_HUD_RATIO).pad(20 / SCREEN_HUD_RATIO).row();

        newGameGameButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ScreenRegister.I.getGame().showBattleScreen();
                return true;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (!stopRestart) {
                    ((AnimatedDrawable) newGameGameButton.getBackground()).restart();
                    stopRestart = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                stopRestart = false;
            }
        });

        exitGameButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
                return true;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (!stopRestart) {
                    ((AnimatedDrawable) exitGameButton.getBackground()).restart();
                    stopRestart = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                stopRestart = false;
            }
        });

        optionsMenuButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hide();
                ScreenRegister.I.setLastScreen(ScreenRegister.I.getMainMenuScreen());
                ScreenRegister.I.getGame().showOptionsScreen();
                return true;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (!stopRestart) {
                    ((AnimatedDrawable) optionsMenuButton.getBackground()).restart();
                    stopRestart = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                stopRestart = false;
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
}
