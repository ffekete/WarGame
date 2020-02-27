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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mygdx.wargame.config.Config.SCREEN_HUD_RATIO;

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

        this.headImage = new ColoredImageButton(getImageButtonStyle(assetManager, "health/HeadHealthIcon.png", 96 / SCREEN_HUD_RATIO, 96 / SCREEN_HUD_RATIO));
        this.torsoImage = new ColoredImageButton(getImageButtonStyle(assetManager, "health/TorsoHealthIcon.png", 96 / SCREEN_HUD_RATIO, 96 / SCREEN_HUD_RATIO));
        this.leftArmImage = new ColoredImageButton(getImageButtonStyle(assetManager, "health/LeftArmHealthIcon.png", 48 / SCREEN_HUD_RATIO, 96 / SCREEN_HUD_RATIO));
        this.leftLegImage = new ColoredImageButton(getImageButtonStyle(assetManager, "health/LeftLegHealthIcon.png", 48 / SCREEN_HUD_RATIO, 96 / SCREEN_HUD_RATIO));
        this.rightArmImage = new ColoredImageButton(getImageButtonStyle(assetManager, "health/RightArmHealthIcon.png", 48 / SCREEN_HUD_RATIO, 96 / SCREEN_HUD_RATIO));
        this.rightLegImage = new ColoredImageButton(getImageButtonStyle(assetManager, "health/RightLegHealthIcon.png", 48 / SCREEN_HUD_RATIO, 96 / SCREEN_HUD_RATIO));

        headImage.setClip(true);

        this.panel = new Table();
        panel.setBackground(panelImage.getDrawable());

        //panel.setDebug(true);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(10);

        this.nameLabel = new Label("N/A", labelStyle);

        panel.add(nameLabel).colspan(7).row();

        panel.add();
        panel.add().size(30 / SCREEN_HUD_RATIO, 30 / SCREEN_HUD_RATIO);
        panel.add(headImage).size(30 / SCREEN_HUD_RATIO, 30 / SCREEN_HUD_RATIO).colspan(3);
        panel.add().size(30 / SCREEN_HUD_RATIO, 30 / SCREEN_HUD_RATIO);
        panel.add().row();

        panel.add();
        panel.add(rightArmImage).size(20 / SCREEN_HUD_RATIO, 50 / SCREEN_HUD_RATIO);
        panel.add(torsoImage).size(40 / SCREEN_HUD_RATIO, 40 / SCREEN_HUD_RATIO).colspan(3);
        panel.add(leftArmImage).size(20 / SCREEN_HUD_RATIO, 50 / SCREEN_HUD_RATIO);
        panel.add().row();

        panel.add().size(10);
        panel.add().size(20 / SCREEN_HUD_RATIO, 50 / SCREEN_HUD_RATIO);
        panel.add(rightLegImage).size(20 / SCREEN_HUD_RATIO, 50 / SCREEN_HUD_RATIO);
        panel.add().size(5 / SCREEN_HUD_RATIO, 50 / SCREEN_HUD_RATIO);
        panel.add(leftLegImage).size(20 / SCREEN_HUD_RATIO, 50 / SCREEN_HUD_RATIO);
        panel.add().size(20 / SCREEN_HUD_RATIO, 50 / SCREEN_HUD_RATIO);
        panel.add().size(10);

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
            Arrays.stream(imageButton.getChildren().items).filter(i -> Image.class.isAssignableFrom(i.getClass())).findFirst().get().setColor(Color.valueOf("006600"));
        } else if (ratio >= 0.5f) {
            Arrays.stream(imageButton.getChildren().items).filter(i -> Image.class.isAssignableFrom(i.getClass())).findFirst().get().setColor(Color.valueOf("00AA00"));
        } else if (ratio >= 0.25f) {
            Arrays.stream(imageButton.getChildren().items).filter(i -> Image.class.isAssignableFrom(i.getClass())).findFirst().get().setColor(Color.valueOf("AAAA00"));
        } else if (ratio > 0f) {
            Arrays.stream(imageButton.getChildren().items).filter(i -> Image.class.isAssignableFrom(i.getClass())).findFirst().get().setColor(Color.valueOf("AA0000"));
        } else {
            Arrays.stream(imageButton.getChildren().items).filter(i -> Image.class.isAssignableFrom(i.getClass())).findFirst().get().setColor(Color.valueOf("333333"));
        }

    }

    public void addTooltip(BodyPart bodyPart, ImageButton imageButton) {
        Table tooltipContent = new Table();

        Container<Table> container = new Container<>(tooltipContent);

        container.maxHeight(40);
        container.maxWidth(50);

        tooltipContent.pad(20 / SCREEN_HUD_RATIO, 20 / SCREEN_HUD_RATIO, 20 / SCREEN_HUD_RATIO, 20 / SCREEN_HUD_RATIO);

        Tooltip<Container> tooltip = new Tooltip<>(container);
        tooltipContent.background(panelImage.getDrawable());
        tooltip.setInstant(true);
        tooltip.getManager().offsetX = 3f;
        tooltip.getManager().offsetY = 3f;

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
            ((Tooltip) e).hide();
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
