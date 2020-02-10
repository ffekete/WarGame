package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.config.Config;

public class MechInfoPanelFacade extends Actor {

    private Table ibTable = new Table();
    private ScrollPane weaponSelectionScrollPane;
    Container<Table> weaponSelectionContainer;
    ProgressBar heatProgressBar;
    ImageButton detailsButton;
    ImageButton hideWeaponSelectionButton;

    Container<Table> bigInfoPanelContainer;
    Table mechInfoTable;
    boolean bigInfoPanelHidden = true;


    public MechInfoPanelFacade() {
        ScrollPane.ScrollPaneStyle weaponsListScrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        weaponsListScrollPaneStyle.background = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/PanelBackground.png")));

        Drawable scrollKnob = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/ScrollBarKnob.png")));
        weaponsListScrollPaneStyle.vScrollKnob = scrollKnob;

        Drawable weaponSelectionScrollBar = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/ScrollBar.png")));
        weaponsListScrollPaneStyle.vScroll = weaponSelectionScrollBar;
        weaponsListScrollPaneStyle.hScroll = weaponSelectionScrollBar;
        weaponsListScrollPaneStyle.hScrollKnob = scrollKnob;

        ProgressBar.ProgressBarStyle heatInfoProgressBarStyle = new ProgressBar.ProgressBarStyle();
        heatInfoProgressBarStyle.background = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/ProgressBar.png")));
        heatInfoProgressBarStyle.knob = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/HeatKnob.png")));
        heatInfoProgressBarStyle.knobBefore = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/HeatBefore.png")));
        heatInfoProgressBarStyle.knobAfter = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/HeatAfter.png")));

        heatProgressBar = new ProgressBar(0, 100, 1, false, heatInfoProgressBarStyle);
        heatProgressBar.setValue(50);

        ImageButton.ImageButtonStyle detailsImageButtonStyle = new ImageButton.ImageButtonStyle();
        detailsImageButtonStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/DetailsButtonUp.png")));
        detailsImageButtonStyle.imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/DetailsButtonDown.png")));

        detailsButton = new ImageButton(detailsImageButtonStyle);
        detailsButton.setSize(detailsButton.getWidth() * Config.UI_SCALING, detailsButton.getHeight() * Config.UI_SCALING);
        detailsButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (bigInfoPanelHidden) {

                    ParallelAction parallelAction = new ParallelAction();

                    SizeToAction sizeToAction = new SizeToAction();
                    sizeToAction.setSize(300 * Config.UI_SCALING, 300 * Config.UI_SCALING);
                    sizeToAction.setDuration(0.25f);
                    parallelAction.addAction(sizeToAction);

                    MoveToAction moveToAction = new MoveToAction();
                    moveToAction.setPosition(detailsButton.getX() - 260 * Config.UI_SCALING, detailsButton.getY() - 260 * Config.UI_SCALING);
                    moveToAction.setDuration(0.25f);
                    parallelAction.addAction(moveToAction);

                    bigInfoPanelContainer.addAction(parallelAction);
                    bigInfoPanelContainer.setVisible(true);
                    bigInfoPanelHidden = false;
                } else {
                    SequenceAction sequenceAction = new SequenceAction();

                    ParallelAction parallelAction = new ParallelAction();

                    SizeToAction sizeToAction = new SizeToAction();
                    sizeToAction.setHeight(0);
                    sizeToAction.setWidth(0);
                    sizeToAction.setDuration(0.25f);
                    parallelAction.addAction(sizeToAction);

                    MoveToAction moveToAction = new MoveToAction();
                    moveToAction.setPosition(detailsButton.getX(), detailsButton.getY());
                    moveToAction.setDuration(0.25f);
                    parallelAction.addAction(moveToAction);

                    VisibleAction visibleAction = new VisibleAction();
                    visibleAction.setVisible(false);


                    sequenceAction.addAction(parallelAction);
                    sequenceAction.addAction(visibleAction);

                    bigInfoPanelContainer.addAction(sequenceAction);

                    bigInfoPanelHidden = true;
                }
                return true;
            }
        });
        detailsButton.setVisible(true);

        ImageButton.ImageButtonStyle hideWeaponSelectionStyle = new ImageButton.ImageButtonStyle();
        hideWeaponSelectionStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/HideButtonUp.png")));
        hideWeaponSelectionStyle.imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/HideButtonDown.png")));

        hideWeaponSelectionButton = new ImageButton(hideWeaponSelectionStyle);

        Image fireImage = new Image(new Texture(Gdx.files.internal("skin/fire.png")));

        weaponSelectionScrollPane = new ScrollPane(ibTable, weaponsListScrollPaneStyle);
        Table weaponSelectionOuterTable = new Table();
        //outer.setDebug(true);
        weaponSelectionOuterTable.add(fireImage).padRight(10);
        weaponSelectionOuterTable.add(heatProgressBar).width(400 * Config.UI_SCALING).padRight(10);
        //weaponSelectionOuterTable.add(detailsButton).row();
        weaponSelectionOuterTable.add(weaponSelectionScrollPane).colspan(2);
        weaponSelectionOuterTable.add(hideWeaponSelectionButton);

        weaponSelectionContainer = new Container<>(weaponSelectionOuterTable);
        weaponSelectionContainer.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/InfoPanel.png"))));
        weaponSelectionContainer.fillX().pad(30 * Config.UI_SCALING);

        //container.setDebug(true);
        weaponSelectionContainer.setSize(600 * Config.UI_SCALING, 200 * Config.UI_SCALING);
        weaponSelectionContainer.setY(-200 * Config.UI_SCALING);
        weaponSelectionScrollPane.setScrollbarsVisible(true);

        // Mech info
        mechInfoTable = new Table();
        bigInfoPanelContainer = new Container<>(mechInfoTable);
        bigInfoPanelContainer.setSize(300 * Config.UI_SCALING, 300 * Config.UI_SCALING);
        bigInfoPanelContainer.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/BigInfoPanel.png"))));
        bigInfoPanelContainer.setVisible(false);


        mechInfoTable.background(new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/StatusBackground.png"))));


    }

    public Table getIbTable() {
        return ibTable;
    }

    @Override
    public void act(float delta) {
        weaponSelectionScrollPane.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        weaponSelectionScrollPane.draw(batch, parentAlpha);
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setY(0);
            moveToAction.setDuration(0.5f);
            weaponSelectionContainer.addAction(moveToAction);
        } else {
            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setY(-200 * Config.UI_SCALING);
            moveToAction.setDuration(0.25f);
            weaponSelectionContainer.addAction(moveToAction);

            detailsButton.setVisible(false);
        }
        //super.setVisible(visible);
    }

    public Container getWeaponSelectionContainer() {
        return weaponSelectionContainer;
    }

    public ProgressBar getHeatProgressBar() {
        return heatProgressBar;
    }

    public Container<Table> getBigInfoPanelContainer() {
        return bigInfoPanelContainer;
    }

    public ImageButton getDetailsButton() {
        return detailsButton;
    }
}
