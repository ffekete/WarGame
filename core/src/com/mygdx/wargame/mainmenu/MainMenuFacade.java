package com.mygdx.wargame.mainmenu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.wargame.battle.map.decoration.AnimatedDrawable;
import com.mygdx.wargame.battle.screen.ui.FontCreator;

public class MainMenuFacade {

    private TextButton.TextButtonStyle newGameStyle;
    private TextButton.TextButtonStyle optionsStyle;
    private TextButton.TextButtonStyle exitButtonStyle;

    private TextButton newGameButton;
    private TextButton optionsButton;
    private TextButton exitButton;

    private Image backgroundImage;

    private AssetManager assetManager;

    private Table outerTable;

    public MainMenuFacade(AssetManager assetManager) {
        this.assetManager = assetManager;

        newGameStyle = new TextButton.TextButtonStyle();
        newGameStyle.font = FontCreator.getBitmapFont(20);
        newGameStyle.up = new AnimatedDrawable(new TextureRegion(assetManager.get("details/ButtonBg.png", Texture.class)), 0.1f, 1000);

        outerTable = new Table();
        outerTable.setFillParent(true);


    }
}
