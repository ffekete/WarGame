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

import java.util.concurrent.atomic.AtomicInteger;

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
        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPane = new ScrollPane(innerTable, scrollPaneStyle);

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
        outerTable.clear();
        innerTable.setSize(350, 380);
//
//        innerTable.setDebug(true);
//        outerTable.setDebug(true);

        int initialHeat = abstractMech.getHeatLevel();
        AtomicInteger nextHeatLevel = new AtomicInteger(initialHeat);
        Label nextHeat = new Label("Heat after firing: " + nextHeatLevel, labelStyle);
        outerTable.add(new Label("Initial heat: " + initialHeat, labelStyle));
        outerTable.add(nextHeat).row();

        AtomicInteger initialArmorDamage = new AtomicInteger(abstractMech.getSelectedWeapons().stream().map(weapon -> weapon.getDamageMultiplier() * weapon.getArmorDamage()).reduce((a,b) -> a+b).orElse(0));
        Label maxArmorDamageLabel = new Label("Total armor damage: " + initialArmorDamage, labelStyle);
        outerTable.add(maxArmorDamageLabel).colspan(2).pad(5).row();

        AtomicInteger initialShieldDamage = new AtomicInteger(abstractMech.getSelectedWeapons().stream().map(weapon -> weapon.getDamageMultiplier() * weapon.getShieldDamage()).reduce((a,b) -> a+b).orElse(0));
        Label maxShieldDamageLabel = new Label("Total shield damage: " + initialShieldDamage, labelStyle);
        outerTable.add(maxShieldDamageLabel).colspan(2).pad(5).row();

        AtomicInteger initialBodyDamage = new AtomicInteger(abstractMech.getSelectedWeapons().stream().map(weapon -> weapon.getDamageMultiplier() * weapon.getBodyDamage()).reduce((a,b) -> a+b).orElse(0));
        Label maxBodyDamageLabel = new Label("Total body damage: " + initialBodyDamage, labelStyle);
        outerTable.add(maxBodyDamageLabel).colspan(2).pad(5).row();

        AtomicInteger initialStabilityDamage = new AtomicInteger(abstractMech.getSelectedWeapons().stream().map(weapon -> weapon.getDamageMultiplier() * weapon.getStabilityHit()).reduce((a,b) -> a+b).orElse(0));
        Label maxStabilityDamageLabel = new Label("Total stability damage: " + initialStabilityDamage, labelStyle);
        outerTable.add(maxStabilityDamageLabel).colspan(2).pad(5).row();

        AtomicInteger initialHeatDamage = new AtomicInteger(abstractMech.getSelectedWeapons().stream().map(weapon -> weapon.getDamageMultiplier() * weapon.getAdditionalHeatToEnemy()).reduce((a,b) -> a+b).orElse(0));
        Label maxHeatDamageLabel = new Label("Total heat damage: " + initialHeatDamage, labelStyle);
        outerTable.add(maxHeatDamageLabel).colspan(2).pad(5).row();

        outerTable.add(scrollPane).colspan(2).size(380, 380).row();
        outerTable.add(exitButton).colspan(2).bottom();

        abstractMech.getAllComponents().stream()
                .filter(component -> Weapon.class.isAssignableFrom(component.getClass()))
                .map(component -> (Weapon)component)
                .filter(weapon -> weapon.getStatus() != Status.Destroyed)
                .forEach(weapon -> {
                    CheckBox checkBox = new CheckBox("", checkBoxStyle);

                    checkBox.setChecked(weapon.getStatus() == Status.Selected);
                    nextHeatLevel.addAndGet(weapon.getStatus() == Status.Selected ? weapon.getHeat() : 0);

                    checkBox.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            if(checkBox.isChecked()) {
                                weapon.setStatus(Status.Selected);
                                nextHeatLevel.addAndGet(weapon.getHeat());
                                initialArmorDamage.addAndGet(weapon.getArmorDamage());
                                initialShieldDamage.addAndGet(weapon.getShieldDamage());
                                initialBodyDamage.addAndGet(weapon.getBodyDamage());
                                initialStabilityDamage.addAndGet(weapon.getStabilityHit());
                                initialHeatDamage.addAndGet(weapon.getAdditionalHeatToEnemy());
                            } else {
                                nextHeatLevel.addAndGet(-weapon.getHeat());
                                initialArmorDamage.addAndGet(-weapon.getArmorDamage());
                                initialShieldDamage.addAndGet(-weapon.getShieldDamage());
                                initialBodyDamage.addAndGet(-weapon.getBodyDamage());
                                initialStabilityDamage.addAndGet(-weapon.getStabilityHit());
                                initialHeatDamage.addAndGet(-weapon.getAdditionalHeatToEnemy());

                                weapon.setStatus(Status.Active);
                            }
                            nextHeat.setText("Heat after firing: " + nextHeatLevel);
                            colorHeatLabel(nextHeatLevel, nextHeat);
                            maxArmorDamageLabel.setText("Total armor damage: " + initialArmorDamage);
                            maxShieldDamageLabel.setText("Total shield damage: " + initialShieldDamage);
                            maxBodyDamageLabel.setText("Total body damage: " + initialBodyDamage);
                            maxStabilityDamageLabel.setText("Total stability damage: " + initialStabilityDamage);
                            maxHeatDamageLabel.setText("Total heat damage: " + initialHeatDamage);
                        }
                    });

                    innerTable.add(checkBox).padRight(10);
                    innerTable.add(new Label(weapon.getName(), labelStyle)).left().padRight(10);
                    innerTable.add(new Label("Ammo: " + weapon.getAmmo().orElse(0), labelStyle)).left().padRight(10);
                    innerTable.add(new Label("Heat: " + weapon.getHeat(), labelStyle)).left().padRight(10);
                    innerTable.add(new Label("Range: " + weapon.getRange(), labelStyle)).left().padRight(10);
                    innerTable.row();
                });

        nextHeat.setText("Heat after firing: " + nextHeatLevel);
        colorHeatLabel(nextHeatLevel, nextHeat);
        maxArmorDamageLabel.setText("Total armor damage: " + initialArmorDamage);
        maxShieldDamageLabel.setText("Total shield damage: " + initialShieldDamage);
        maxBodyDamageLabel.setText("Total body damage: " + initialBodyDamage);
        maxStabilityDamageLabel.setText("Total stability damage: " + initialStabilityDamage);

    }

    private void colorHeatLabel(AtomicInteger nextHeatLevel, Label nextHeat) {
        if (nextHeatLevel.intValue() >= 100) {
            nextHeat.setColor(Color.RED);
        } else if (nextHeatLevel.intValue() >= 75) {
            nextHeat.setColor(Color.YELLOW);
        } else {
            nextHeat.setColor(Color.GREEN);
        }
    }

}
