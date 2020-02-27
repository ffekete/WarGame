package com.mygdx.wargame.battle.screen.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.mygdx.wargame.battle.screen.ui.targeting.ColoredImageButton;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.facade.HitChanceCalculatorFacade;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class HealthInfoPanelFacade {

    private Image panelImage;
    private ColoredImageButton headImage;
    private ColoredImageButton torsoImage;
    private ColoredImageButton leftLegImage;
    private ColoredImageButton rightLegImage;
    private ColoredImageButton leftArmImage;
    private ColoredImageButton rightArmImage;

    private Pilot targetPilot;
    private Mech targetMech;

    private Table panel;

    Label.LabelStyle labelStyle;
    private Label nameLabel;
    private boolean locked = false;

    private HitChanceCalculatorFacade hitChanceCalculatorFacade = new HitChanceCalculatorFacade();

    public HealthInfoPanelFacade(AssetManager assetManager) {
        panelImage = new Image(assetManager.get("skin/BigInfoPanel.png", Texture.class));

        this.headImage = new ColoredImageButton(getImageButtonStyle(assetManager, "health/HeadHealthIcon.png", 96, 96));
        this.torsoImage = new ColoredImageButton(getImageButtonStyle(assetManager, "health/TorsoHealthIcon.png", 96, 96));
        this.leftArmImage = new ColoredImageButton(getImageButtonStyle(assetManager, "health/LeftArmHealthIcon.png", 48, 96));
        this.leftLegImage = new ColoredImageButton(getImageButtonStyle(assetManager, "health/LeftLegHealthIcon.png", 48, 96));
        this.rightArmImage = new ColoredImageButton(getImageButtonStyle(assetManager, "health/RightArmHealthIcon.png", 48, 96));
        this.rightLegImage = new ColoredImageButton(getImageButtonStyle(assetManager, "health/RightLegHealthIcon.png", 48, 96));

        headImage.setClip(true);

        this.panel = new Table();
        panel.setBackground(panelImage.getDrawable());

        //panel.setDebug(true);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(12);

        this.nameLabel = new Label("N/A", labelStyle);

        panel.add(nameLabel).colspan(5).row();

        panel.add().size(30, 30);
        panel.add(headImage).size(30, 30).colspan(3);
        panel.add().size(30, 30).row();

        panel.add(rightArmImage).size(20, 50);
        panel.add(torsoImage).size(40, 40).colspan(3);
        panel.add(leftArmImage).size(20, 50).row();

        panel.add().size(20, 50);
        panel.add(rightLegImage).size(20, 50);
        panel.add().size(5, 50);
        panel.add(leftLegImage).size(20, 50);
        panel.add().size(20, 50);

        panel.setColor(Color.valueOf("FFFFFFBB"));
    }

    private ImageButton.ImageButtonStyle getImageButtonStyle(AssetManager assetManager, String path, float minWidth, float minHeight) {
        ImageButton.ImageButtonStyle headStyle = new ImageButton.ImageButtonStyle();

        Image imageUp = new Image(assetManager.get(path, Texture.class));
        imageUp.setFillParent(true);

        headStyle.imageUp = imageUp.getDrawable();
        headStyle.imageUp.setMinWidth(minWidth);
        headStyle.imageUp.setMinHeight(minHeight);
        headStyle.imageUp.setMinHeight(minHeight);

        headStyle.imageOver = imageUp.getDrawable();
        return headStyle;
    }

    public void update(Pilot targetPilot, Mech targetMech) {
        if (this.targetMech != targetMech) {

            removeToolTips(headImage);
            removeToolTips(torsoImage);
            removeToolTips(leftArmImage);
            removeToolTips(leftLegImage);
            removeToolTips(rightArmImage);
            removeToolTips(rightLegImage);

            this.targetMech = targetMech;
            this.targetPilot = targetPilot;

            nameLabel.setText(targetMech.getName());

            addTooltip(BodyPart.Head, headImage);
            addTooltip(BodyPart.Torso, torsoImage);
            addTooltip(BodyPart.LeftLeg, leftLegImage);
            addTooltip(BodyPart.LeftArm, leftArmImage);
            addTooltip(BodyPart.RightLeg, rightLegImage);
            addTooltip(BodyPart.RightArm, rightArmImage);

            setColor(BodyPart.Head, headImage);
            setColor(BodyPart.Torso, torsoImage);
            setColor(BodyPart.LeftLeg, leftLegImage);
            setColor(BodyPart.LeftArm, leftArmImage);
            setColor(BodyPart.RightLeg, rightLegImage);
            setColor(BodyPart.RightArm, rightArmImage);
        }
    }

    public void setColor(BodyPart bodyPart, ColoredImageButton imageButton) {
        int actualHp = targetMech.getHp(bodyPart);
        int maxHp = targetMech.getMaxHp(bodyPart);

        float ratio = (float) actualHp / maxHp;

        if (ratio >= 0.75f) {
            imageButton.getChildren().items[0].setColor(Color.valueOf("006600"));
        } else if (ratio >= 0.5f) {
            imageButton.getChildren().items[0].setColor(Color.valueOf("00AA00"));
        } else if (ratio >= 0.25f) {
            imageButton.getChildren().items[0].setColor(Color.valueOf("AAAA00"));
        } else if (ratio > 0f) {
            imageButton.getChildren().items[0].setColor(Color.valueOf("AA0000"));
        } else {
            imageButton.getChildren().items[0].setColor(Color.valueOf("333333"));
        }

    }

    public void addTooltip(BodyPart bodyPart, ImageButton imageButton) {
        Table tooltipContent = new Table();
        Container<Table> container = new Container<>(tooltipContent);

        tooltipContent.pad(20, 20, 20, 20);

        Tooltip<Container> tooltip = new Tooltip<>(container);
        tooltipContent.background(panelImage.getDrawable());
        tooltip.setInstant(true);

        removeToolTips(imageButton);

        tooltipContent.add(new Label("HP: " + targetMech.getHp(bodyPart), labelStyle)).left()
                .row();

        imageButton.addListener(tooltip);
    }

    private void removeToolTips(ImageButton imageButton) {
        List<EventListener> toRemove = new ArrayList<>();
        for (Object e : imageButton.getListeners()) {
            if (Tooltip.class.isAssignableFrom(e.getClass())) {
                toRemove.add((EventListener) e);
            }
        }

        toRemove.stream().forEach(e -> {
            ((Tooltip)e).hide();
            imageButton.getListeners().removeValue(e, true);

        });
    }

    public Table getPanel() {
        return panel;
    }

    public void hide() {
        panel.setVisible(false);
    }

    public void show() {
        panel.setVisible(true);
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
