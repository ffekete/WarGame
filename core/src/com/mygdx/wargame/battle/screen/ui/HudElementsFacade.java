package com.mygdx.wargame.battle.screen.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.config.Config;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;

import static com.mygdx.wargame.config.Config.*;

public class HudElementsFacade {

    private ImageButton endTurnButton;
    private AssetManager assetManager;
    private TurnProcessingFacade turnProcessingFacade;
    private ActionLock actionLock;
    private Image shieldImage;
    private Label shieldValueLabel;
    private HorizontalGroup upperHud;
    private Label.LabelStyle labelStyle;
    private Image armorImage;
    private Label armorValueLabel;

    public HudElementsFacade(AssetManager assetManager, TurnProcessingFacade turnProcessingFacade, ActionLock actionLock) {
        this.assetManager = assetManager;
        this.turnProcessingFacade = turnProcessingFacade;
        this.actionLock = actionLock;
    }

    public void create() {

        labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(12);

        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.imageUp = new TextureRegionDrawable(assetManager.get("skin/EndTurnButtonUp.png", Texture.class));
        imageButtonStyle.imageDown = new TextureRegionDrawable(assetManager.get("skin/EndTurnButtonDown.png", Texture.class));

        endTurnButton = new ImageButton(imageButtonStyle);

        endTurnButton.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if (!actionLock.isLocked() && turnProcessingFacade.isNextPlayerControlled()) {
                    endTurnButton.setVisible(true);
                } else {
                    endTurnButton.setVisible(false);
                }
                return false;
            }
        });

        endTurnButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                turnProcessingFacade.getNext().getKey().setMoved(true);
                turnProcessingFacade.getNext().getKey().setAttacked(true);
                return true;
            }
        });

        upperHud = new HorizontalGroup();
        upperHud.setDebug(true);
        upperHud.setSize(HUD_VIEWPORT_WIDTH, 60 / SCREEN_HUD_RATIO);
        upperHud.setPosition(0, HUD_VIEWPORT_HEIGHT - 60 / SCREEN_HUD_RATIO);
        shieldImage = new Image(new TextureRegionDrawable(assetManager.get("details/ShieldComponentIcon.png", Texture.class)));
        upperHud.addActor(shieldImage);
        shieldValueLabel = new Label("0", labelStyle);
        upperHud.addActor(shieldValueLabel);

        armorImage = new Image(new TextureRegionDrawable(assetManager.get("details/ShieldIcon.png", Texture.class)));
        armorValueLabel = new Label("0", labelStyle);
        upperHud.addActor(armorImage);
        upperHud.addActor(armorValueLabel);
        show();
    }

    public void registerComponents(Stage stage) {
        stage.addActor(endTurnButton);
        stage.addActor(upperHud);
    }

    public void hide() {
        endTurnButton.setSize(0, 0);
        upperHud.setSize(0,0);
    }

    public void show() {
        endTurnButton.setPosition(Config.HUD_VIEWPORT_WIDTH - (80 / SCREEN_HUD_RATIO), 0);
        endTurnButton.setSize(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);
        upperHud.setSize(HUD_VIEWPORT_WIDTH, 60 / SCREEN_HUD_RATIO);
    }

    public void update() {
        shieldValueLabel.setText(turnProcessingFacade.getNext().getKey().getShieldValue());
        armorValueLabel.setText(turnProcessingFacade.getNext().getKey().getArmorValue());
    }
}
