package com.mygdx.wargame.battle.screen.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.map.decoration.AnimatedDrawable;
import com.mygdx.wargame.battle.rules.facade.TurnProcessingFacade;
import com.mygdx.wargame.battle.rules.facade.WeaponRangeMarkerUpdater;
import com.mygdx.wargame.battle.screen.AssetManagerLoaderV2;
import com.mygdx.wargame.common.component.weapon.Status;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.config.Config;

public class WeaponSelectionFacade {

    private Table outerTable;
    private AssetManagerLoaderV2 assetManagerLoaderV2;
    private TextButton exitButton;
    private HUDMediator hudMediator;
    private CheckBox.CheckBoxStyle checkBoxStyle;
    private Table innerTable;
    private Label.LabelStyle labelStyle;
    private ScrollPane scrollPane;
    private TurnProcessingFacade turnProcessingFacade;
    private WeaponRangeMarkerUpdater weaponRangeMarkerUpdater = new WeaponRangeMarkerUpdater();

    public WeaponSelectionFacade(AssetManagerLoaderV2 assetManagerLoaderV2, HUDMediator hudMediator, TurnProcessingFacade turnProcessingFacade) {
        this.assetManagerLoaderV2 = assetManagerLoaderV2;
        this.hudMediator = hudMediator;
        this.turnProcessingFacade = turnProcessingFacade;
    }

    public void create() {
        outerTable = new Table();
        TextureRegionDrawable backGround = new TextureRegionDrawable(assetManagerLoaderV2.getAssetManager().get("windows/Window.png", Texture.class));
        outerTable.background(backGround.tint(Color.valueOf("FFFFFFEE")));
        outerTable.setVisible(false);
        outerTable.setSize(400, 600);
        outerTable.setPosition(Config.HUD_VIEWPORT_WIDTH.get() / 2  - 200, Config.HUD_VIEWPORT_HEIGHT.get() / 2  - 300);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(16);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(assetManagerLoaderV2.getAssetManager().get("hud/SmallButtonUp.png", Texture.class));
        buttonStyle.down = new TextureRegionDrawable(assetManagerLoaderV2.getAssetManager().get("hud/SmallButtonDown.png", Texture.class));
        buttonStyle.over = new AnimatedDrawable(new TextureRegion(assetManagerLoaderV2.getAssetManager().get("hud/SmallButtonOver.png", Texture.class)), 0.1f, 150, 64, 32);
        buttonStyle.font = labelStyle.font;
        buttonStyle.overFontColor = Color.valueOf("00FF00");
        buttonStyle.fontColor = Color.valueOf("FFFFFF");

        exitButton = new TextButton("exit", buttonStyle);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hudMediator.getHudElementsFacade().show();
                weaponRangeMarkerUpdater.updateWeaponRangeMarkers(turnProcessingFacade.getBattleMap(), turnProcessingFacade.getNext().getKey(), turnProcessingFacade.getNext().getValue());
                hide();
            }
        });

        checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.font = labelStyle.font;
        checkBoxStyle.checkboxOn = new TextureRegionDrawable(assetManagerLoaderV2.getAssetManager().get("common/CheckboxChecked.png", Texture.class));
        checkBoxStyle.checkboxOff = new TextureRegionDrawable(assetManagerLoaderV2.getAssetManager().get("common/CheckboxUnchecked.png", Texture.class));
        checkBoxStyle.checkboxOnOver = new TextureRegionDrawable(assetManagerLoaderV2.getAssetManager().get("common/CheckboxOnChecked.png", Texture.class));
        checkBoxStyle.checkboxOver = new TextureRegionDrawable(assetManagerLoaderV2.getAssetManager().get("common/CheckboxOnUnchecked.png", Texture.class));

        innerTable = new Table();
        scrollPane = new ScrollPane(innerTable);

        outerTable.add(scrollPane).row();
        outerTable.add(exitButton).bottom();

    }

    public void show(){
        outerTable.setVisible(true);
    }

    public void hide() {
        outerTable.setVisible(false);
    }

    public void registerComponents(Stage stage) {
        stage.addActor(outerTable);
    }

    public void update(AbstractMech abstractMech) {
        innerTable.clear();
        abstractMech.getAllComponents().stream()
                .filter(component -> Weapon.class.isAssignableFrom(component.getClass()))
                .map(component -> (Weapon)component)
                .filter(weapon -> weapon.getStatus() != Status.Destroyed)
                .forEach(weapon -> {
                    CheckBox checkBox = new CheckBox(weapon.getName(), checkBoxStyle);

                    checkBox.setChecked(weapon.getStatus() == Status.Selected);

                    checkBox.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if(checkBox.isChecked()) {
                                weapon.setStatus(Status.Selected);
                            } else {
                                weapon.setStatus(Status.Active);
                            }
                        }
                    });

                    innerTable.add(checkBox).padRight(5);
                    innerTable.add(new Label("Ammo: " + weapon.getAmmo().orElse(0), labelStyle)).padRight(5);
                    innerTable.add(new Label("Heat: " + weapon.getHeat(), labelStyle)).padRight(5);
                    innerTable.add(new Label("Range: " + weapon.getRange(), labelStyle)).padRight(5);
                    innerTable.row();
                });

    }

}
