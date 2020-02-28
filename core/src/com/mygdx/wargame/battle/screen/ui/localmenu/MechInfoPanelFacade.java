package com.mygdx.wargame.battle.screen.ui.localmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.screen.ui.FontCreator;
import com.mygdx.wargame.battle.screen.ui.HUDMediator;

import static com.mygdx.wargame.config.Config.SCREEN_HUD_RATIO;

public class MechInfoPanelFacade extends Actor {

    private Table ibTable = new Table();
    private ScrollPane weaponSelectionScrollPane;
    private Container<Table> weaponSelectionContainer;
    private ImageButton detailsButton;
    private ImageButton hideMenuButton;
    private ImageButton pilotButton;
    private ImageButton weaponSelectionButton;

    private boolean weaponSelectionContainerHidden = true;
    private boolean localMenuVisible = false;

    private WeaponSelectionPanelMovementHandler weaponSelectionPanelMovementHandler = new WeaponSelectionPanelMovementHandler();
    private CheckBox.CheckBoxStyle checkBoxStyle;
    private BitmapFont font;
    private BitmapFont smallFont;
    private Label.LabelStyle labelStyle;
    private Label.LabelStyle smallLabelStyle;
    private ProgressBar.ProgressBarStyle smallHeatInfoProgressBarStyle;
    private ProgressBar.ProgressBarStyle stabilityProgressBarStyle;
    private AssetManager assetManager;

    public MechInfoPanelFacade(HUDMediator hudMediator, AssetManager assetManager) {
        this.assetManager = assetManager;
        font = FontCreator.getBitmapFont();
        smallFont = FontCreator.getBitmapFont(10);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        smallLabelStyle = new Label.LabelStyle();
        smallLabelStyle.font = smallFont;

        ImageButton.ImageButtonStyle hideMenuButtonsSelectionStyle = new ImageButton.ImageButtonStyle();
        hideMenuButtonsSelectionStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/HideButtonUp.png")));
        hideMenuButtonsSelectionStyle.imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/HideButtonDown.png")));

        checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.font = smallFont;
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

        smallHeatInfoProgressBarStyle = new ProgressBar.ProgressBarStyle();
        smallHeatInfoProgressBarStyle.background = new TextureRegionDrawable(new Texture(Gdx.files.internal("HeatProgressBarBg.png")));
        smallHeatInfoProgressBarStyle.knob = new TextureRegionDrawable(new Texture(Gdx.files.internal("HeatProgressBarKnob.png")));

        stabilityProgressBarStyle = new ProgressBar.ProgressBarStyle();
        stabilityProgressBarStyle.background = new TextureRegionDrawable(new Texture(Gdx.files.internal("StabilityProgressBarBg.png")));
        stabilityProgressBarStyle.knob = new TextureRegionDrawable(new Texture(Gdx.files.internal("StabilityProgressBarKnob.png")));

        ImageButton.ImageButtonStyle detailsImageButtonStyle = new ImageButton.ImageButtonStyle();
        detailsImageButtonStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/DetailsButtonUp.png")));
        detailsImageButtonStyle.imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/DetailsButtonDown.png")));

        detailsButton = new ImageButton(detailsImageButtonStyle);
        detailsButton.setSize(detailsButton.getWidth() / SCREEN_HUD_RATIO, detailsButton.getHeight() / SCREEN_HUD_RATIO);

        detailsButton.setVisible(true);

        ImageButton.ImageButtonStyle pilotButtonStyle = new ImageButton.ImageButtonStyle();
        pilotButtonStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/PilotButtonUp.png")));
        pilotButtonStyle.imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/PilotButtonDown.png")));
        pilotButton = new ImageButton(pilotButtonStyle);
        pilotButton.setSize(pilotButton.getWidth() / SCREEN_HUD_RATIO, pilotButton.getHeight() / SCREEN_HUD_RATIO);
        pilotButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hideLocalMenu();
                return true;
            }
        });

        ImageButton.ImageButtonStyle weaponSelectionButtonStyle = new ImageButton.ImageButtonStyle();
        weaponSelectionButtonStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/WeaponSelectionButtonUp.png")));
        weaponSelectionButtonStyle.imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/WeaponSelectionButtonDown.png")));
        weaponSelectionButton = new ImageButton(weaponSelectionButtonStyle);

        weaponSelectionButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hudMediator.getHealthInfoPanelFacade().hide();
                hudMediator.getHudElementsFacade().hide();
                hideLocalMenu();
                hideLocalMenu();
                // show this one
                weaponSelectionContainerHidden = weaponSelectionPanelMovementHandler.moveWeaponSelectionButton(weaponSelectionContainerHidden, weaponSelectionButton, weaponSelectionContainer, weaponSelectionScrollPane);
                return true;
            }
        });

        weaponSelectionButton.setSize(weaponSelectionButton.getWidth() / SCREEN_HUD_RATIO, weaponSelectionButton.getHeight() / SCREEN_HUD_RATIO);

        weaponSelectionScrollPane = new ScrollPane(ibTable, weaponsListScrollPaneStyle);
        //weaponSelectionScrollPane.setDebug(true);
        Table weaponSelectionOuterTable = new Table();

        //weaponSelectionOuterTable.setDebug(true);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = FontCreator.getBitmapFont(13);
        textButtonStyle.fontColor = Color.valueOf("007700");
        textButtonStyle.overFontColor = Color.valueOf("00AA00");
        textButtonStyle.down = new TextureRegionDrawable(this.assetManager.get("details/ButtonBgDown.png", Texture.class));
        textButtonStyle.up = new TextureRegionDrawable(this.assetManager.get("details/ButtonBg.png", Texture.class));
        textButtonStyle.over = new TextureRegionDrawable(this.assetManager.get("details/ButtonBgOver.png", Texture.class));

        TextButton exitWeaponSelectionPanelButton = new TextButton("Exit", textButtonStyle);

        exitWeaponSelectionPanelButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hudMediator.getHealthInfoPanelFacade().show();
                weaponSelectionPanelMovementHandler.moveWeaponSelectionButton(weaponSelectionContainerHidden, weaponSelectionButton, weaponSelectionContainer, weaponSelectionScrollPane);
                weaponSelectionContainerHidden = true;
                return true;
            }
        });

        weaponSelectionOuterTable.add(weaponSelectionScrollPane).colspan(2).row();
        weaponSelectionOuterTable.add(exitWeaponSelectionPanelButton).size(240 / SCREEN_HUD_RATIO, 120 / SCREEN_HUD_RATIO).colspan(2).pad(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO);

        weaponSelectionContainer = new Container<>(weaponSelectionOuterTable);
        weaponSelectionContainer.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/InfoPanel.png"))));
        weaponSelectionContainer.fillX().pad(30 / SCREEN_HUD_RATIO);

        weaponSelectionContainer.setSize(0, 0);
        weaponSelectionScrollPane.setScrollbarsVisible(true);



        // hide menu
        hideMenuButton = new ImageButton(hideMenuButtonsSelectionStyle);
        hideMenuButton.setSize(hideMenuButton.getWidth() / SCREEN_HUD_RATIO, hideMenuButton.getHeight() / SCREEN_HUD_RATIO);
        hideMenuButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // hide all other panels
                hudMediator.getDetailsPageFacade().hide();
                weaponSelectionContainerHidden = weaponSelectionPanelMovementHandler.moveWeaponSelectionButton(false, weaponSelectionButton, weaponSelectionContainer, weaponSelectionScrollPane);
                hideLocalMenu();
                return true;
            }
        });
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
        //weaponSelectionScrollPane.draw(batch, parentAlpha);
    }

    public Container getWeaponSelectionContainer() {
        return weaponSelectionContainer;
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
        hideLocalMenu();
    }

    public void hideLocalMenu() {
        localMenuVisible = false;
        weaponSelectionContainerHidden = true;

        SequenceAction sequenceAction;

        VisibleAction visibleAction = new VisibleAction();
        visibleAction.setVisible(false);

        MoveByAction moveTo = new MoveByAction();
        moveTo.setAmount(0, 60 / SCREEN_HUD_RATIO);
        moveTo.setDuration(0.25f);
        sequenceAction = new SequenceAction();
        visibleAction = new VisibleAction();
        visibleAction.setVisible(false);
        sequenceAction.addAction(moveTo);
        sequenceAction.addAction(visibleAction);
        weaponSelectionButton.addAction(sequenceAction);

        moveTo = new MoveByAction();
        moveTo.setAmount(0, -60 / SCREEN_HUD_RATIO);
        moveTo.setDuration(0.25f);
        sequenceAction = new SequenceAction();
        visibleAction = new VisibleAction();
        visibleAction.setVisible(false);
        sequenceAction.addAction(moveTo);
        sequenceAction.addAction(visibleAction);
        hideMenuButton.addAction(sequenceAction);

        moveTo = new MoveByAction();
        moveTo.setAmount(-60 / SCREEN_HUD_RATIO, 0);
        moveTo.setDuration(0.25f);
        sequenceAction = new SequenceAction();
        visibleAction = new VisibleAction();
        visibleAction.setVisible(false);
        sequenceAction.addAction(moveTo);
        sequenceAction.addAction(visibleAction);
        pilotButton.addAction(sequenceAction);

        moveTo = new MoveByAction();
        moveTo.setAmount(60 / SCREEN_HUD_RATIO, 0);
        moveTo.setDuration(0.25f);
        sequenceAction = new SequenceAction();
        visibleAction = new VisibleAction();
        visibleAction.setVisible(false);
        sequenceAction.addAction(moveTo);
        sequenceAction.addAction(visibleAction);
        detailsButton.addAction(sequenceAction);

        weaponSelectionContainer.setVisible(false);
    }

    public boolean isLocalMenuVisible() {
        return localMenuVisible;
    }

    public void showLocalMenu() {
        localMenuVisible = true;
        MoveByAction moveTo = new MoveByAction();
        moveTo.setAmount(0, -60 / SCREEN_HUD_RATIO);
        moveTo.setDuration(0.25f);
        weaponSelectionButton.addAction(moveTo);

        moveTo = new MoveByAction();
        moveTo.setAmount(0, 60 / SCREEN_HUD_RATIO);
        moveTo.setDuration(0.25f);
        hideMenuButton.addAction(moveTo);

        moveTo = new MoveByAction();
        moveTo.setAmount(60 / SCREEN_HUD_RATIO, 0);
        moveTo.setDuration(0.25f);
        pilotButton.addAction(moveTo);

        moveTo = new MoveByAction();
        moveTo.setAmount(-60 / SCREEN_HUD_RATIO, 0);
        moveTo.setDuration(0.25f);
        detailsButton.addAction(moveTo);
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

    public ProgressBar.ProgressBarStyle getSmallHeatInfoProgressBarStyle() {
        return smallHeatInfoProgressBarStyle;
    }

    public ProgressBar.ProgressBarStyle getStabilityProgressBarStyle() {
        return stabilityProgressBarStyle;
    }

    public void hide() {
        hideLocalMenu();
    }


}
