package com.mygdx.wargame.battle.screen.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;

public class HudElementsFacade {

    private ImageButton endTurnButton;
    private AssetManager assetManager;
    private TurnProcessingFacade turnProcessingFacade;
    private ActionLock actionLock;

    public HudElementsFacade(AssetManager assetManager, TurnProcessingFacade turnProcessingFacade, ActionLock actionLock) {
        this.assetManager = assetManager;
        this.turnProcessingFacade = turnProcessingFacade;
        this.actionLock = actionLock;
    }

    public void registerComponents(Stage stage) {

        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.imageUp = new TextureRegionDrawable(assetManager.get("skin/EndTurnButtonUp.png", Texture.class));
        imageButtonStyle.imageDown = new TextureRegionDrawable(assetManager.get("skin/EndTurnButtonDown.png", Texture.class));

        endTurnButton = new ImageButton(imageButtonStyle);

        endTurnButton.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if(!actionLock.isLocked() && turnProcessingFacade.isNextPlayerControlled()) {
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

        stage.addActor(endTurnButton);
    }

}
