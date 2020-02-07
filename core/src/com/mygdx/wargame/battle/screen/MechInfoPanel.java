package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MechInfoPanel extends Actor {

    private Table ibTable = new Table();
    private ScrollPane scrollPane;
    Container<ScrollPane> container;

    public MechInfoPanel(BitmapFont font12) {
        TextButton.TextButtonStyle imageButtonStyle = new TextButton.TextButtonStyle();
        imageButtonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/InfoPanel.png")));
        imageButtonStyle.down = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/InfoPanel.png")));
        imageButtonStyle.font = font12;

        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.background = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/PanelBackground.png")));
        scrollPaneStyle.vScrollKnob = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/ScrollBarKnob.png")));
        scrollPaneStyle.vScroll = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/ScrollBar.png")));
        scrollPaneStyle.hScroll = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/ScrollBar.png")));
        scrollPaneStyle.hScrollKnob = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/ScrollBarKnob.png")));

        scrollPane = new ScrollPane(ibTable, scrollPaneStyle);
        container = new Container<>(scrollPane);
        container.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/InfoPanel.png"))));
        container.fillX().pad(20);

        container.setDebug(true);
        container.setSize(600, 200);
        container.setY(-200);
        scrollPane.setScrollbarsVisible(true);
    }

    public Table getIbTable() {
        return ibTable;
    }

    @Override
    public void act(float delta) {
        scrollPane.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        scrollPane.draw(batch, parentAlpha);
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setY(0);
            moveToAction.setDuration(0.5f);
            container.addAction(moveToAction);
        } else {
            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setY(-200);
            moveToAction.setDuration(0.25f);
            container.addAction(moveToAction);
        }
        //super.setVisible(visible);
    }

    public Container getContainer() {
        return container;
    }
}
