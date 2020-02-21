package com.mygdx.wargame.battle.screen.ui.targeting;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class ColoredImageButton extends ImageButton {

    public ColoredImageButton(ImageButtonStyle style) {
        super(style);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        super.draw(batch, parentAlpha);
    }
}
