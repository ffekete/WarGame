package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ScreenElements {

    private Label.LabelStyle infoLabelStyle;
    private MechInfoPanelFacade mechInfoPanelFacade;

    public ScreenElements(MechInfoPanelFacade mechInfoPanelFacade, BitmapFont font12) {
        this.mechInfoPanelFacade = mechInfoPanelFacade;
        infoLabelStyle = new Label.LabelStyle();
        infoLabelStyle.font = font12;
    }

    public MechInfoPanelFacade getMechInfoPanelFacade() {
        return mechInfoPanelFacade;
    }

    public Label.LabelStyle getInfoLabelStyle() {
        return infoLabelStyle;
    }
}
