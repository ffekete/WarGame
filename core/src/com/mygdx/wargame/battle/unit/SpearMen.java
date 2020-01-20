package com.mygdx.wargame.battle.unit;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.input.ManInputListener;

public class SpearMen extends AbstractWarrior {

    private ShapeRenderer shapeRenderer;
    private SelectionController selectionController;

    public SpearMen(ShapeRenderer shapeRenderer, SelectionController selectionController) {
        this.shapeRenderer = shapeRenderer;
        this.selectionController =  selectionController;
        addListener(new ManInputListener(this, selectionController));

        setTouchable(Touchable.enabled);
        setSize(20, 20);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(getX(), getY(), shapeRenderer, selectionController);
    }

    @Override
    public float getX() {
        return super.getX();
    }
}
