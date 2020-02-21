package com.mygdx.wargame.battle.screen.ui.targeting;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.wargame.battle.combat.RangedAttackTargetCalculator;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.calculator.RangeCalculator;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;
import com.mygdx.wargame.util.MathUtils;

public class TargetingPanelFacade {

    private Image panelImage;
    private ColoredImageButton headImage;
    private ColoredImageButton torsoImage;
    private ColoredImageButton leftLegImage;
    private ColoredImageButton rightLegImage;
    private ColoredImageButton leftArmImage;
    private ColoredImageButton rightArmImage;
    private RangedAttackTargetCalculator rangedAttackTargetCalculator;
    private RangeCalculator rangeCalculator;
    private TurnProcessingFacade turnProcessingFacade;

    private Mech  attackingMech;
    private Pilot attackingPilot;
    private Pilot targetPilot;
    private Mech targetMech;

    private Table panel;

    public TargetingPanelFacade(AssetManager assetManager, RangedAttackTargetCalculator rangedAttackTargetCalculator, RangeCalculator rangeCalculator, TurnProcessingFacade turnProcessingFacade) {
        panelImage = new Image(assetManager.get("skin/BigInfoPanel.png", Texture.class));

        this.headImage = new ColoredImageButton(getImageButtonStyle(assetManager, "targeting/TargetHead.png", 96, 96));
        this.torsoImage = new ColoredImageButton(getImageButtonStyle(assetManager, "targeting/TargetTorso.png", 96, 96));
        this.leftArmImage = new ColoredImageButton(getImageButtonStyle(assetManager, "targeting/TargetLeftArm.png", 48, 96));
        this.leftLegImage = new ColoredImageButton(getImageButtonStyle(assetManager, "targeting/TargetLeftLeg.png", 48, 96));
        this.rightArmImage = new ColoredImageButton(getImageButtonStyle(assetManager, "targeting/TargetRightArm.png", 48, 96));
        this.rightLegImage = new ColoredImageButton(getImageButtonStyle(assetManager, "targeting/TargetRightLeg.png", 48, 96));
        this.rangedAttackTargetCalculator = rangedAttackTargetCalculator;
        this.rangeCalculator = rangeCalculator;
        this.turnProcessingFacade = turnProcessingFacade;

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

        panel.add().size(30, 30);
        panel.add(headImage).size(30, 30).colspan(3);
        panel.add().row();

        panel.add(rightArmImage).size(20, 50);
        panel.add(torsoImage).size(40, 40).colspan(3);
        panel.add(leftArmImage).size(20, 50).row();

        panel.add().size(20, 50);
        panel.add(rightLegImage).size(20, 50);
        panel.add().size(5, 50);
        panel.add(leftLegImage).size(20, 50);
        panel.add().size(20, 50);

        headImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int minRange = rangeCalculator.calculateAllWeaponsRange(attackingPilot, attackingMech);

                if(MathUtils.getDistance(attackingMech.getX(),  attackingMech.getY(), targetMech.getX(), targetMech.getY()) <= minRange) {
                    rangedAttackTargetCalculator.calculate(attackingPilot, (AbstractMech) attackingMech, (AbstractMech) targetMech, targetPilot, BodyPart.Head);
                }

                return true;
            }
        });

        torsoImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int minRange = rangeCalculator.calculateAllWeaponsRange(attackingPilot, attackingMech);

                if(MathUtils.getDistance(attackingMech.getX(),  attackingMech.getY(), targetMech.getX(), targetMech.getY()) <= minRange) {
                    rangedAttackTargetCalculator.calculate(attackingPilot, (AbstractMech) attackingMech, (AbstractMech) targetMech, targetPilot, BodyPart.Torso);
                }

                return true;
            }
        });

        leftLegImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int minRange = rangeCalculator.calculateAllWeaponsRange(attackingPilot, attackingMech);

                if(MathUtils.getDistance(attackingMech.getX(),  attackingMech.getY(), targetMech.getX(), targetMech.getY()) <= minRange) {
                    rangedAttackTargetCalculator.calculate(attackingPilot, (AbstractMech) attackingMech, (AbstractMech) targetMech, targetPilot, BodyPart.LeftLeg);
                }

                return true;
            }
        });

        rightLegImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int minRange = rangeCalculator.calculateAllWeaponsRange(attackingPilot, attackingMech);

                if(MathUtils.getDistance(attackingMech.getX(),  attackingMech.getY(), targetMech.getX(), targetMech.getY()) <= minRange) {
                    rangedAttackTargetCalculator.calculate(attackingPilot, (AbstractMech) attackingMech, (AbstractMech) targetMech, targetPilot, BodyPart.RightLeg);
                }

                return true;
            }
        });

        leftArmImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int minRange = rangeCalculator.calculateAllWeaponsRange(attackingPilot, attackingMech);

                if(MathUtils.getDistance(attackingMech.getX(),  attackingMech.getY(), targetMech.getX(), targetMech.getY()) <= minRange) {
                    rangedAttackTargetCalculator.calculate(attackingPilot, (AbstractMech) attackingMech, (AbstractMech) targetMech, targetPilot, BodyPart.LeftArm);
                }

                return true;
            }
        });

        rightArmImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int minRange = rangeCalculator.calculateAllWeaponsRange(attackingPilot, attackingMech);

                if(MathUtils.getDistance(attackingMech.getX(),  attackingMech.getY(), targetMech.getX(), targetMech.getY()) <= minRange) {
                    rangedAttackTargetCalculator.calculate(attackingPilot, (AbstractMech) attackingMech, (AbstractMech) targetMech, targetPilot, BodyPart.RightArm);
                }

                return true;
            }
        });
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
        return panel;
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

}
