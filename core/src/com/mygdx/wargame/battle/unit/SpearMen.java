package com.mygdx.wargame.battle.unit;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.wargame.battle.combat.MeleeAttackTargetCalculator;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.input.ManInputListener;

public class SpearMen extends AbstractWarrior {

    private ShapeRenderer shapeRenderer;
    private SelectionController selectionController;
    private String name;

    public SpearMen(String name, ShapeRenderer shapeRenderer, SelectionController selectionController, MeleeAttackTargetCalculator meleeAttackTargetCalculator) {
        this.shapeRenderer = shapeRenderer;
        this.selectionController =  selectionController;
        this.name = name;
        addListener(new ManInputListener(this, selectionController, meleeAttackTargetCalculator));

        setTouchable(Touchable.enabled);
        setSize(10, 10);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(getX(), getY(), shapeRenderer, selectionController);
    }
}
