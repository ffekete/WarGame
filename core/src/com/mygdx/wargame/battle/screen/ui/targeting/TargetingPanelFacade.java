package com.mygdx.wargame.battle.screen.ui.targeting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.combat.RangedAttackTargetCalculator;
import com.mygdx.wargame.battle.screen.ui.FontCreator;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.calculator.RangeCalculator;
import com.mygdx.wargame.rules.facade.HitChanceCalculatorFacade;
import com.mygdx.wargame.util.MathUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.wargame.config.Config.SCREEN_HUD_RATIO;

public class TargetingPanelFacade {

    private Image panelImage;
    private ColoredImageButton headImage;
    private ColoredImageButton torsoImage;
    private ColoredImageButton leftLegImage;
    private ColoredImageButton rightLegImage;
    private ColoredImageButton leftArmImage;
    private ColoredImageButton rightArmImage;

    private Mech attackingMech;
    private Pilot attackingPilot;
    private Pilot targetPilot;
    private Mech targetMech;

    private Table panel;

    Label.LabelStyle labelStyle;

    private HitChanceCalculatorFacade hitChanceCalculatorFacade = new HitChanceCalculatorFacade();

    public TargetingPanelFacade(AssetManager assetManager, RangedAttackTargetCalculator rangedAttackTargetCalculator, RangeCalculator rangeCalculator) {
        panelImage = new Image(assetManager.get("skin/BigInfoPanel.png", Texture.class));

        this.headImage = new ColoredImageButton(getImageButtonStyle(assetManager, "targeting/TargetHead.png", 96 / SCREEN_HUD_RATIO, 96 / SCREEN_HUD_RATIO));
        this.torsoImage = new ColoredImageButton(getImageButtonStyle(assetManager, "targeting/TargetTorso.png", 96 / SCREEN_HUD_RATIO, 96 / SCREEN_HUD_RATIO));
        this.leftArmImage = new ColoredImageButton(getImageButtonStyle(assetManager, "targeting/TargetLeftArm.png", 48 / SCREEN_HUD_RATIO, 96 / SCREEN_HUD_RATIO));
        this.leftLegImage = new ColoredImageButton(getImageButtonStyle(assetManager, "targeting/TargetLeftLeg.png", 48 / SCREEN_HUD_RATIO, 96 / SCREEN_HUD_RATIO));
        this.rightArmImage = new ColoredImageButton(getImageButtonStyle(assetManager, "targeting/TargetRightArm.png", 48 / SCREEN_HUD_RATIO, 96 / SCREEN_HUD_RATIO));
        this.rightLegImage = new ColoredImageButton(getImageButtonStyle(assetManager, "targeting/TargetRightLeg.png", 48 / SCREEN_HUD_RATIO, 96 / SCREEN_HUD_RATIO));

        headImage.setClip(true);

        headImage.addListener(new ShadingMouseMoveOverListener(headImage));
        leftArmImage.addListener(new ShadingMouseMoveOverListener(leftArmImage));
        rightArmImage.addListener(new ShadingMouseMoveOverListener(rightArmImage));
        leftLegImage.addListener(new ShadingMouseMoveOverListener(leftLegImage));
        rightLegImage.addListener(new ShadingMouseMoveOverListener(rightLegImage));
        torsoImage.addListener(new ShadingMouseMoveOverListener(torsoImage));

        headImage.setColor(Color.valueOf("FFFFFF44"));
        torsoImage.setColor(Color.valueOf("FFFFFF44"));
        leftLegImage.setColor(Color.valueOf("FFFFFF44"));
        leftArmImage.setColor(Color.valueOf("FFFFFF44"));
        rightLegImage.setColor(Color.valueOf("FFFFFF44"));
        rightArmImage.setColor(Color.valueOf("FFFFFF44"));

        this.panel = new Table();
        panel.setBackground(panelImage.getDrawable());

        //panel.setDebug(true);

        headImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hide();
                int minRange = rangeCalculator.calculateAllWeaponsRange(attackingPilot, attackingMech);

                if (MathUtils.getDistance(attackingMech.getX(), attackingMech.getY(), targetMech.getX(), targetMech.getY()) <= minRange) {
                    rangedAttackTargetCalculator.calculate(attackingPilot, (AbstractMech) attackingMech, (AbstractMech) targetMech, targetPilot, BodyPart.Head);
                }

                return true;
            }
        });

        torsoImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hide();
                int minRange = rangeCalculator.calculateAllWeaponsRange(attackingPilot, attackingMech);

                if (MathUtils.getDistance(attackingMech.getX(), attackingMech.getY(), targetMech.getX(), targetMech.getY()) <= minRange) {
                    rangedAttackTargetCalculator.calculate(attackingPilot, (AbstractMech) attackingMech, (AbstractMech) targetMech, targetPilot, BodyPart.Torso);
                }

                return true;
            }
        });

        leftLegImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hide();
                int minRange = rangeCalculator.calculateAllWeaponsRange(attackingPilot, attackingMech);

                if (MathUtils.getDistance(attackingMech.getX(), attackingMech.getY(), targetMech.getX(), targetMech.getY()) <= minRange) {
                    rangedAttackTargetCalculator.calculate(attackingPilot, (AbstractMech) attackingMech, (AbstractMech) targetMech, targetPilot, BodyPart.LeftLeg);
                }

                return true;
            }
        });

        rightLegImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hide();
                int minRange = rangeCalculator.calculateAllWeaponsRange(attackingPilot, attackingMech);

                if (MathUtils.getDistance(attackingMech.getX(), attackingMech.getY(), targetMech.getX(), targetMech.getY()) <= minRange) {
                    rangedAttackTargetCalculator.calculate(attackingPilot, (AbstractMech) attackingMech, (AbstractMech) targetMech, targetPilot, BodyPart.RightLeg);
                }

                return true;
            }
        });

        leftArmImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hide();
                int minRange = rangeCalculator.calculateAllWeaponsRange(attackingPilot, attackingMech);

                if (MathUtils.getDistance(attackingMech.getX(), attackingMech.getY(), targetMech.getX(), targetMech.getY()) <= minRange) {
                    rangedAttackTargetCalculator.calculate(attackingPilot, (AbstractMech) attackingMech, (AbstractMech) targetMech, targetPilot, BodyPart.LeftArm);
                }

                return true;
            }
        });

        rightArmImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hide();
                int minRange = rangeCalculator.calculateAllWeaponsRange(attackingPilot, attackingMech);

                if (MathUtils.getDistance(attackingMech.getX(), attackingMech.getY(), targetMech.getX(), targetMech.getY()) <= minRange) {
                    rangedAttackTargetCalculator.calculate(attackingPilot, (AbstractMech) attackingMech, (AbstractMech) targetMech, targetPilot, BodyPart.RightArm);
                }

                return true;
            }
        });

        labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(10);

        ImageButton.ImageButtonStyle closeMenuButtonStyle = new ImageButton.ImageButtonStyle();
        closeMenuButtonStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/HideButtonUp.png")));
        closeMenuButtonStyle.imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("skin/HideButtonDown.png")));

        ImageButton closePanelButton = new ImageButton(closeMenuButtonStyle);
        closePanelButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                panel.setVisible(false);
                return true;
            }
        });

        panel.add(closePanelButton).size(20 / SCREEN_HUD_RATIO, 20 / SCREEN_HUD_RATIO).top().right().colspan(5).row();

        panel.add().size(30 / SCREEN_HUD_RATIO, 30 / SCREEN_HUD_RATIO);
        panel.add(headImage).size(30 / SCREEN_HUD_RATIO, 30 / SCREEN_HUD_RATIO).colspan(3);
        panel.add().row();

        panel.add(rightArmImage).size(20 / SCREEN_HUD_RATIO, 50 / SCREEN_HUD_RATIO);
        panel.add(torsoImage).size(40 / SCREEN_HUD_RATIO, 40 / SCREEN_HUD_RATIO).colspan(3);
        panel.add(leftArmImage).size(20 / SCREEN_HUD_RATIO, 50 / SCREEN_HUD_RATIO).row();

        panel.add().size(20 / SCREEN_HUD_RATIO, 50 / SCREEN_HUD_RATIO);
        panel.add(rightLegImage).size(20 / SCREEN_HUD_RATIO, 50 / SCREEN_HUD_RATIO);
        panel.add().size(5 / SCREEN_HUD_RATIO, 50 / SCREEN_HUD_RATIO);
        panel.add(leftLegImage).size(20 / SCREEN_HUD_RATIO, 50 / SCREEN_HUD_RATIO);
        panel.add().size(20 / SCREEN_HUD_RATIO, 50 / SCREEN_HUD_RATIO);
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

    public Table getPanel(Pilot attackingPilot, Mech attackingMech, Pilot targetPilot, Mech targetMech) {
        this.targetMech = targetMech;
        this.attackingMech = attackingMech;
        this.attackingPilot = attackingPilot;
        this.targetPilot = targetPilot;

        addTooltip(BodyPart.Head, headImage);
        addTooltip(BodyPart.Torso, torsoImage);
        addTooltip(BodyPart.LeftLeg, leftLegImage);
        addTooltip(BodyPart.LeftArm, leftArmImage);
        addTooltip(BodyPart.RightLeg, rightLegImage);
        addTooltip(BodyPart.RightArm, rightArmImage);

        return panel;
    }

    public void addTooltip(BodyPart bodyPart, ImageButton imageButton) {
        Table tooltipContent = new Table();
        Container<Table> container = new Container<>(tooltipContent);

        tooltipContent.pad(20 / SCREEN_HUD_RATIO, 20 / SCREEN_HUD_RATIO, 20 / SCREEN_HUD_RATIO, 20 / SCREEN_HUD_RATIO);

        Tooltip<Container> tooltip = new Tooltip<>(container);
        tooltipContent.background(panelImage.getDrawable());
        tooltip.setInstant(true);

        List<EventListener> toRemove = new ArrayList<>();
        for (Object e : imageButton.getListeners()) {
            if (Tooltip.class.isAssignableFrom(e.getClass())) {
                toRemove.add((EventListener) e);
            }
        }

        toRemove.stream().forEach(e -> imageButton.getListeners().removeValue(e, true));

        attackingMech.getSelectedWeapons().stream().forEach(w -> {
            tooltipContent.add(new Label(w.getName() + " to hit:", labelStyle)).padRight(20 / SCREEN_HUD_RATIO).left();
            tooltipContent.add(new Label("" + hitChanceCalculatorFacade.getHitChance(w, attackingPilot, attackingMech, targetMech, bodyPart) + "%", labelStyle)).left()
                    .row();
        });
        imageButton.addListener(tooltip);
    }

    public Table getPanel() {
        return panel;
    }

    private class ShadingMouseMoveOverListener extends InputListener {

        private ColoredImageButton coloredImageButton;

        public ShadingMouseMoveOverListener(ColoredImageButton coloredImageButton) {
            this.coloredImageButton = coloredImageButton;
        }

        @Override
        public boolean mouseMoved(InputEvent event, float x, float y) {
            coloredImageButton.setColor(Color.valueOf("FFFFFF"));
            return super.mouseMoved(event, x, y);
        }


        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            coloredImageButton.setColor(Color.valueOf("FFFFFF22"));
        }
    }

    public void hide() {
        panel.setVisible(false);
    }
}
