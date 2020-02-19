package com.mygdx.wargame.battle.screen.ui.localmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.screen.ui.FontCreator;
import com.mygdx.wargame.battle.ui.HealthOverlay;
import com.mygdx.wargame.config.Config;

public class MechInfoPanelFacade extends Actor {

    private Table ibTable = new Table();
    private ScrollPane weaponSelectionScrollPane;
    private Container<Table> weaponSelectionContainer;
    private ProgressBar heatProgressBar;
    private ImageButton detailsButton;
    private ImageButton hideMenuButton;
    private ImageButton pilotButton;
    private ImageButton weaponSelectionButton;

    private Container<Table> bigInfoPanelContainer;
    private Table mechInfoTable;
    private boolean bigInfoPanelHidden = true;
    private boolean weaponSelectionContainerHidden = true;
    private boolean localMenuVisible = false;
    private BigInfoPanelMovementHandler bigInfoPanelMovementHandler = new BigInfoPanelMovementHandler();
    private WeaponSelectionPanelMovementHandler weaponSelectionPanelMovementHandler = new WeaponSelectionPanelMovementHandler();
    private CheckBox.CheckBoxStyle checkBoxStyle;
    private BitmapFont font;
    Label.LabelStyle labelStyle;
    Label.LabelStyle smallLabelStyle;
    ProgressBar.ProgressBarStyle heatInfoProgressBarStyle;
    ProgressBar.ProgressBarStyle smallHeatInfoProgressBarStyle;
    ProgressBar.ProgressBarStyle stabilityProgressBarStyle;
    private HealthOverlay healthOverlayImage;

    public MechInfoPanelFacade() {
        mechInfoTable = new Table();

        font = FontCreator.getBitmapFont();
        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        healthOverlayImage = new HealthOverlay("1", labelStyle, new TextureRegion(new Texture(Gdx.files.internal("HealthOverlay.png"))));
        healthOverlayImage.setTouchable(Touchable.disabled);

        smallLabelStyle = new Label.LabelStyle();
        smallLabelStyle.font = FontCreator.getBitmapFont(20);

        checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.font = font;
        checkBoxStyle.checkboxOn = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/CheckboxChecked.png")));
        checkBoxStyle.checkboxOff = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/CheckboxUnchecked.png")));

        ScrollPane.ScrollPaneStyle weaponsListScrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        weaponsListScrollPaneStyle.background = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/PanelBackground.png")));

        Drawable scrollKnob = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/ScrollBarKnob.png")));
        weaponsListScrollPaneStyle.vScrollKnob = scrollKnob;

        Drawable weaponSelectionScrollBar = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/ScrollBar.png")));
        weaponsListScrollPaneStyle.vScroll = weaponSelectionScrollBar;
        weaponsListScrollPaneStyle.hScroll = weaponSelectionScrollBar;
        weaponsListScrollPaneStyle.hScrollKnob = scrollKnob;

        heatInfoProgressBarStyle = new ProgressBar.ProgressBarStyle();
        heatInfoProgressBarStyle.background = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/ProgressBar.png")));
        heatInfoProgressBarStyle.knob = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/HeatKnob.png")));
        //heatInfoProgressBarStyle.knobBefore = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/HeatBefore.png")));
        //heatInfoProgressBarStyle.knobAfter = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/HeatAfter.png")));

        smallHeatInfoProgressBarStyle = new ProgressBar.ProgressBarStyle();
        smallHeatInfoProgressBarStyle.background = new TextureRegionDrawable(new Texture(Gdx.files.internal("HeatProgressBarBg.png")));
        smallHeatInfoProgressBarStyle.knob = new TextureRegionDrawable(new Texture(Gdx.files.internal("HeatProgressBarKnob.png")));

        stabilityProgressBarStyle = new ProgressBar.ProgressBarStyle();
        stabilityProgressBarStyle.background = new TextureRegionDrawable(new Texture(Gdx.files.internal("StabilityProgressBarBg.png")));
        stabilityProgressBarStyle.knob = new TextureRegionDrawable(new Texture(Gdx.files.internal("StabilityProgressBarKnob.png")));



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
                // hide all other panels
                weaponSelectionContainerHidden = weaponSelectionPanelMovementHandler.moveWeaponSelectionButton(false, weaponSelectionButton, weaponSelectionContainer, weaponSelectionScrollPane, heatProgressBar);
                // show this one
                bigInfoPanelHidden = bigInfoPanelMovementHandler.moveBigInfoPanelToLocalButton(detailsButton, bigInfoPanelContainer,mechInfoTable, bigInfoPanelHidden);
                return true;
            }
        });
        detailsButton.setVisible(true);

        ImageButton.ImageButtonStyle pilotButtonStyle = new ImageButton.ImageButtonStyle();
        pilotButtonStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/PilotButtonUp.png")));
        pilotButtonStyle.imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/PilotButtonDown.png")));
        pilotButton = new ImageButton(pilotButtonStyle);
        pilotButton.setSize(pilotButton.getWidth() * Config.UI_SCALING, pilotButton.getHeight() * Config.UI_SCALING);

        ImageButton.ImageButtonStyle weaponSelectionButtonStyle = new ImageButton.ImageButtonStyle();
        weaponSelectionButtonStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/WeaponSelectionButtonUp.png")));
        weaponSelectionButtonStyle.imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/WeaponSelectionButtonDown.png")));
        weaponSelectionButton = new ImageButton(weaponSelectionButtonStyle);

        weaponSelectionButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // hide all other panels
                bigInfoPanelHidden = bigInfoPanelMovementHandler.moveBigInfoPanelToLocalButton(detailsButton, bigInfoPanelContainer, mechInfoTable, false);
                // show this one
                weaponSelectionContainerHidden = weaponSelectionPanelMovementHandler.moveWeaponSelectionButton(weaponSelectionContainerHidden, weaponSelectionButton, weaponSelectionContainer, weaponSelectionScrollPane, heatProgressBar);
                return true;
            }
        });

        weaponSelectionButton.setSize(weaponSelectionButton.getWidth() * Config.UI_SCALING, weaponSelectionButton.getHeight() * Config.UI_SCALING);

        ImageButton.ImageButtonStyle hideMenuButtonsSelectionStyle = new ImageButton.ImageButtonStyle();
        hideMenuButtonsSelectionStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/HideButtonUp.png")));
        hideMenuButtonsSelectionStyle.imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/HideButtonDown.png")));

        hideMenuButton = new ImageButton(hideMenuButtonsSelectionStyle);
        hideMenuButton.setSize(hideMenuButton.getWidth() * Config.UI_SCALING, hideMenuButton.getHeight() * Config.UI_SCALING);
        hideMenuButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // hide all other panels
                bigInfoPanelHidden = bigInfoPanelMovementHandler.moveBigInfoPanelToLocalButton(detailsButton, bigInfoPanelContainer, mechInfoTable, false);
                weaponSelectionContainerHidden = weaponSelectionPanelMovementHandler.moveWeaponSelectionButton(false, weaponSelectionButton, weaponSelectionContainer, weaponSelectionScrollPane, heatProgressBar);
                hideLocalMenu();
                return true;
            }
        });

        Image fireImage = new Image(new Texture(Gdx.files.internal("skin/Fire.png")));

        weaponSelectionScrollPane = new ScrollPane(ibTable, weaponsListScrollPaneStyle);
        weaponSelectionScrollPane.setDebug(true);
        Table weaponSelectionOuterTable = new Table();
        weaponSelectionOuterTable.add(fireImage).padRight(10);
        weaponSelectionOuterTable.add(heatProgressBar).width(400 * Config.UI_SCALING).padRight(10).row();
        weaponSelectionOuterTable.add(weaponSelectionScrollPane).colspan(2);

        weaponSelectionContainer = new Container<>(weaponSelectionOuterTable);
        weaponSelectionContainer.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/InfoPanel.png"))));
        weaponSelectionContainer.fillX().pad(30 * Config.UI_SCALING);

        weaponSelectionContainer.setSize(600 * Config.UI_SCALING, 200 * Config.UI_SCALING);
        weaponSelectionContainer.setY(-200 * Config.UI_SCALING);
        weaponSelectionScrollPane.setScrollbarsVisible(true);

        // Mech info

        bigInfoPanelContainer = new Container<>(mechInfoTable);
        bigInfoPanelContainer.setSize(300 * Config.UI_SCALING, 300 * Config.UI_SCALING);
        bigInfoPanelContainer.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/BigInfoPanel.png"))));
        bigInfoPanelContainer.setVisible(false);
        //mechInfoTable.background(new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/StatusBackground.png"))));
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

    public ImageButton getHideMenuButton() {
        return hideMenuButton;
    }

    public ImageButton getPilotButton() {
        return pilotButton;
    }

    public ImageButton getWeaponSelectionButton() {
        return weaponSelectionButton;
    }

    public void registerComponents(Stage stage) {
        stage.addActor(getDetailsButton());
        stage.addActor(getPilotButton());
        stage.addActor(getHideMenuButton());
        stage.addActor(getWeaponSelectionButton());
        stage.addActor(getWeaponSelectionContainer());
        stage.addActor(getBigInfoPanelContainer());
        hideLocalMenu();
    }

    public void hideLocalMenu() {
        localMenuVisible = false;
        bigInfoPanelHidden = true;
        weaponSelectionContainerHidden = true;

        SequenceAction sequenceAction;

        VisibleAction visibleAction = new VisibleAction();
        visibleAction.setVisible(false);

        MoveByAction moveTo = new MoveByAction();
        moveTo.setAmount( 0, 60 * Config.UI_SCALING);
        moveTo.setDuration(0.25f);
        sequenceAction = new SequenceAction();
        visibleAction = new VisibleAction();
        visibleAction.setVisible(false);
        sequenceAction.addAction(moveTo);
        sequenceAction.addAction(visibleAction);
        weaponSelectionButton.addAction(sequenceAction);

        moveTo = new MoveByAction();
        moveTo.setAmount( 0, -60 * Config.UI_SCALING);
        moveTo.setDuration(0.25f);
        sequenceAction = new SequenceAction();
        visibleAction = new VisibleAction();
        visibleAction.setVisible(false);
        sequenceAction.addAction(moveTo);
        sequenceAction.addAction(visibleAction);
        hideMenuButton.addAction(sequenceAction);

        moveTo = new MoveByAction();
        moveTo.setAmount(-60 * Config.UI_SCALING, 0);
        moveTo.setDuration(0.25f);
        sequenceAction = new SequenceAction();
        visibleAction = new VisibleAction();
        visibleAction.setVisible(false);
        sequenceAction.addAction(moveTo);
        sequenceAction.addAction(visibleAction);
        pilotButton.addAction(sequenceAction);

        moveTo = new MoveByAction();
        moveTo.setAmount(60 * Config.UI_SCALING, 0);
        moveTo.setDuration(0.25f);
        sequenceAction = new SequenceAction();
        visibleAction = new VisibleAction();
        visibleAction.setVisible(false);
        sequenceAction.addAction(moveTo);
        sequenceAction.addAction(visibleAction);
        detailsButton.addAction(sequenceAction);

        weaponSelectionContainer.setVisible(false);
        bigInfoPanelContainer.setVisible(false);
    }

    public boolean isLocalMenuVisible() {
        return localMenuVisible;
    }

    public void showLocalMenu() {
        localMenuVisible = true;
        MoveByAction moveTo = new MoveByAction();
        moveTo.setAmount( 0, -60 * Config.UI_SCALING);
        moveTo.setDuration(0.25f);
        weaponSelectionButton.addAction(moveTo);

        moveTo = new MoveByAction();
        moveTo.setAmount( 0, 60 * Config.UI_SCALING);
        moveTo.setDuration(0.25f);
        hideMenuButton.addAction(moveTo);

        moveTo = new MoveByAction();
        moveTo.setAmount(60 * Config.UI_SCALING, 0);
        moveTo.setDuration(0.25f);
        pilotButton.addAction(moveTo);

        moveTo = new MoveByAction();
        moveTo.setAmount(-60 * Config.UI_SCALING, 0);
        moveTo.setDuration(0.25f);
        detailsButton.addAction(moveTo);
    }

    public Table getMechInfoTable() {
        return mechInfoTable;
    }

    public CheckBox.CheckBoxStyle getCheckBoxStyle() {
        return checkBoxStyle;
    }

    public BitmapFont getFont() {
        return font;
    }

    public Label.LabelStyle getLabelStyle() {
        return labelStyle;
    }

    public Label.LabelStyle getSmallLabelStyle() {
        return smallLabelStyle;
    }

    public ProgressBar.ProgressBarStyle getHeatInfoProgressBarStyle() {
        return heatInfoProgressBarStyle;
    }

    public ProgressBar.ProgressBarStyle getSmallHeatInfoProgressBarStyle() {
        return smallHeatInfoProgressBarStyle;
    }

    public ProgressBar.ProgressBarStyle getStabilityProgressBarStyle() {
        return stabilityProgressBarStyle;
    }

    public HealthOverlay getHealthOverlayImage() {
        return healthOverlayImage;
    }
}
