package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MechInfoPanel extends Actor {

    private Button imageButton;
    private Table ibTable = new Table();
    private Label.LabelStyle labelStyle;

    public MechInfoPanel(Label.LabelStyle labelStyle, BitmapFont font12) {
        this.labelStyle = labelStyle;
        TextButton.TextButtonStyle imageButtonStyle = new TextButton.TextButtonStyle();
        imageButtonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/InfoPanel.png")));
        imageButtonStyle.down = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/InfoPanel.png")));
        imageButtonStyle.font = font12;

        Button imageButton = new Button(imageButtonStyle);

        imageButton.setDebug(true);
        imageButton.setHeight(200);
        imageButton.setWidth(600);
        imageButton.setY(-200);
        imageButton.row();

        ibTable.left();

        imageButton.add(ibTable);

        this.imageButton = imageButton;
    }

    public Table getIbTable() {
        return ibTable;
    }

    @Override
    public void act(float delta) {
        imageButton.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        imageButton.draw(batch, parentAlpha);
    }

    @Override
    public void setVisible(boolean visible) {
        if(visible) {
            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setY(0);
            moveToAction.setDuration(0.5f);
            imageButton.addAction(moveToAction);
        } else {
            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setY(-200);
            moveToAction.setDuration(0.25f);
            imageButton.addAction(moveToAction);
        }
        //super.setVisible(visible);
    }
}
