package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ScreenElements {

    private Label.LabelStyle infoLabelStyle;
    private MechInfoPanel mechInfoPanel;

    public ScreenElements(MechInfoPanel mechInfoPanel, BitmapFont font12) {
        this.mechInfoPanel = mechInfoPanel;
        infoLabelStyle = new Label.LabelStyle();
        infoLabelStyle.font = font12;
    }

    public MechInfoPanel getMechInfoPanel() {
        return mechInfoPanel;
    }

    public Label.LabelStyle getInfoLabelStyle() {
        return infoLabelStyle;
    }
}
