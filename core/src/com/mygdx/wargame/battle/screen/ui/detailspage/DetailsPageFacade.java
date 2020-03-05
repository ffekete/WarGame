package com.mygdx.wargame.battle.screen.ui.detailspage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.map.decoration.AnimatedDrawable;
import com.mygdx.wargame.battle.map.decoration.AnimatedImage;
import com.mygdx.wargame.battle.screen.ui.FontCreator;
import com.mygdx.wargame.battle.screen.ui.HUDMediator;
import com.mygdx.wargame.battle.screen.ui.localmenu.BigInfoPanelMovementHandler;
import com.mygdx.wargame.battle.screen.ui.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.common.component.armor.Armor;
import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.common.mech.Mech;

import static com.mygdx.wargame.config.Config.SCREEN_HUD_RATIO;

public class DetailsPageFacade {

    private boolean bigInfoPanelHidden = true;
    private BigInfoPanelMovementHandler bigInfoPanelMovementHandler = new BigInfoPanelMovementHandler();
    private Container<Table> bigInfoPanelContainer;
    private Table mechInfoOuterTable;
    private Table mechInfoInnerTable;
    private HUDMediator hudMediator;
    private MechInfoPanelFacade mechInfoPanelFacade;
    private AssetManager assetManager;
    private Label.LabelStyle labelStyle;
    private Image headImage;
    private Image torsoImage;
    private Image leftLegImage;
    private Image rightLegImage;
    private Image leftArmImage;
    private Image rightArmImage;
    private TextTooltip.TextTooltipStyle textTooltipStyle;


    public DetailsPageFacade(MechInfoPanelFacade mechInfoPanelFacade, HUDMediator hudMediator, AssetManager assetManager) {
        this.hudMediator = hudMediator;
        this.mechInfoPanelFacade = mechInfoPanelFacade;
        this.assetManager = assetManager;
        this.labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(12);
        ;
    }

    public void create() {

        textTooltipStyle = new TextTooltip.TextTooltipStyle(labelStyle, new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class)));

        headImage = new Image(assetManager.get("details/HeadIcon.png", Texture.class));
        headImage.addListener(new TextTooltip(" Head health ", textTooltipStyle));

        torsoImage = new Image(assetManager.get("details/TorsoIcon.png", Texture.class));
        torsoImage.addListener(new TextTooltip(" Torso health ", textTooltipStyle));

        leftArmImage = new Image(assetManager.get("details/LeftArmIcon.png", Texture.class));
        leftArmImage.addListener(new TextTooltip(" Left arm health ", textTooltipStyle));

        rightArmImage = new Image(assetManager.get("details/RightArmIcon.png", Texture.class));
        rightArmImage.addListener(new TextTooltip(" Right arm health ", textTooltipStyle));

        leftLegImage = new Image(assetManager.get("details/LeftLegIcon.png", Texture.class));
        leftLegImage.addListener(new TextTooltip(" Left leg health ", textTooltipStyle));

        rightLegImage = new Image(assetManager.get("details/RightLegIcon.png", Texture.class));
        rightLegImage.addListener(new TextTooltip(" Right leg health ", textTooltipStyle));


        mechInfoPanelFacade.getDetailsButton().addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // hide all other panels
                hudMediator.getHealthInfoPanelFacade().hide();
                hudMediator.getHudElementsFacade().hide();
                mechInfoPanelFacade.hideLocalMenu();
                // show this one
                bigInfoPanelHidden = bigInfoPanelMovementHandler.moveBigInfoPanelToLocalButton(mechInfoPanelFacade.getDetailsButton(), bigInfoPanelContainer, mechInfoOuterTable, bigInfoPanelHidden);
                return true;
            }
        });

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = FontCreator.getBitmapFont(13);
        textButtonStyle.fontColor = Color.valueOf("FFFFFF");
        textButtonStyle.overFontColor = Color.valueOf("00FF00");


        textButtonStyle.up = new AnimatedDrawable(new TextureRegion(assetManager.get("details/ButtonBg.png", Texture.class)), 0.1f, 1000);


        TextButton exitMechInfoTablePanelButton = new TextButton("Exit", textButtonStyle);

        exitMechInfoTablePanelButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hudMediator.getHealthInfoPanelFacade().show();
                hudMediator.getHudElementsFacade().show();
                hudMediator.getDetailsPageFacade().hide();
                return true;
            }
        });

        mechInfoOuterTable = new Table();
        mechInfoInnerTable = new Table();

        mechInfoOuterTable.add(mechInfoInnerTable).center().fill().row();
        mechInfoOuterTable.add(exitMechInfoTablePanelButton).size(240 / SCREEN_HUD_RATIO, 120 / SCREEN_HUD_RATIO).pad(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO).center();

        bigInfoPanelContainer = new Container<>(mechInfoOuterTable);
        //bigInfoPanelContainer.setFillParent(true);
        bigInfoPanelContainer.setSize(0, 0);
        bigInfoPanelContainer.setBackground(new TextureRegionDrawable(assetManager.get("skin/BigInfoPanel.png", Texture.class)));
        bigInfoPanelContainer.setVisible(false);
    }

    private Image getArmorImage(TextTooltip.TextTooltipStyle textTooltipStyle) {
        Image armorImage = new AnimatedImage(new TextureRegion(assetManager.get("details/ShieldIcon.png", Texture.class)), 0.15f, 300);
        armorImage.addListener(new TextTooltip("Armor", textTooltipStyle));
        return armorImage;
    }

    private Image getShieldImage(TextTooltip.TextTooltipStyle textTooltipStyle) {
        Image armorImage = new AnimatedImage(new TextureRegion(assetManager.get("details/ShieldComponentIcon.png", Texture.class)), 0.15f, 300);
        armorImage.addListener(new TextTooltip("Shield", textTooltipStyle));
        return armorImage;
    }

    public void hide() {
        bigInfoPanelHidden = bigInfoPanelMovementHandler.moveBigInfoPanelToLocalButton(mechInfoPanelFacade.getDetailsButton(), bigInfoPanelContainer, mechInfoOuterTable, bigInfoPanelHidden);
    }

    public Container<Table> getBigInfoPanelContainer() {
        return bigInfoPanelContainer;
    }

    public void registerComponents(Stage stage) {
        stage.addActor(getBigInfoPanelContainer());
    }

    public void update(Mech mec) {
        mechInfoInnerTable.clear();

        mechInfoInnerTable.add(headImage).size(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO);
        mechInfoInnerTable.add(new Label(mec.getHp(BodyPart.Head) + "/" + mec.getHeadMaxHp(), labelStyle)).center().pad(20 / SCREEN_HUD_RATIO);

        mechInfoInnerTable.add(getArmorImage(textTooltipStyle)).size(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO);
        mechInfoInnerTable.add(new Label("" + getArmor(BodyPart.Head, mec), labelStyle)).center().pad(20 / SCREEN_HUD_RATIO);
        mechInfoInnerTable.add();
        mechInfoInnerTable.add(torsoImage).size(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO).center();
        mechInfoInnerTable.add(new Label(mec.getHp(BodyPart.Torso) + "/" + mec.getTorsoMaxHp(), labelStyle)).center().pad(20 / SCREEN_HUD_RATIO);
        mechInfoInnerTable.add(getArmorImage(textTooltipStyle)).size(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO).center();
        mechInfoInnerTable.add(new Label("" + getArmor(BodyPart.Torso, mec), labelStyle)).center().pad(20 / SCREEN_HUD_RATIO).row();

        mechInfoInnerTable.add(leftArmImage).size(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO).center();
        mechInfoInnerTable.add(new Label(mec.getHp(BodyPart.LeftArm) + "/" + mec.getLeftHandMaxHp(), labelStyle)).center().pad(20 / SCREEN_HUD_RATIO);
        mechInfoInnerTable.add(getArmorImage(textTooltipStyle)).size(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO).center();
        mechInfoInnerTable.add(new Label("" + getArmor(BodyPart.LeftArm, mec), labelStyle)).center().pad(20 / SCREEN_HUD_RATIO);
        mechInfoInnerTable.add();
        mechInfoInnerTable.add(rightArmImage).size(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO).center();
        mechInfoInnerTable.add(new Label(mec.getHp(BodyPart.RightArm) + "/" + mec.getRightHandMaxHp(), labelStyle)).center().pad(20 / SCREEN_HUD_RATIO);
        mechInfoInnerTable.add(getArmorImage(textTooltipStyle)).size(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO).center();
        mechInfoInnerTable.add(new Label("" + getArmor(BodyPart.RightArm, mec), labelStyle)).center().pad(20 / SCREEN_HUD_RATIO).row();

        mechInfoInnerTable.add(leftLegImage).size(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO).center();
        mechInfoInnerTable.add(new Label(mec.getHp(BodyPart.LeftLeg) + "/" + mec.getLeftLegMaxHp(), labelStyle)).center().pad(20 / SCREEN_HUD_RATIO);
        mechInfoInnerTable.add(getArmorImage(textTooltipStyle)).size(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO).center();
        mechInfoInnerTable.add(new Label("" + getArmor(BodyPart.LeftLeg, mec), labelStyle)).center().pad(20 / SCREEN_HUD_RATIO);
        mechInfoInnerTable.add();
        mechInfoInnerTable.add(rightLegImage).size(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO).center();
        mechInfoInnerTable.add(new Label(mec.getHp(BodyPart.RightLeg) + "/" + mec.getRightLegMaxHp(), labelStyle)).pad(20 / SCREEN_HUD_RATIO).center();
        mechInfoInnerTable.add(getArmorImage(textTooltipStyle)).size(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO).center();
        mechInfoInnerTable.add(new Label("" + getArmor(BodyPart.RightLeg, mec), labelStyle)).pad(20 / SCREEN_HUD_RATIO).center().row();


        mechInfoInnerTable.add(getShieldImage(textTooltipStyle)).size(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO).center();
        mechInfoInnerTable.add(new Label("" + mec.getShieldValue(), labelStyle)).pad(20 / SCREEN_HUD_RATIO).colspan(4).left();


    }

    private Integer getArmor(BodyPart bodyPart, Mech mec) {
        return mec.getComponents(bodyPart).stream().filter(c -> Armor.class.isAssignableFrom(c.getClass())).map(c -> ((Armor) c).getHitPoint()).reduce((a, b) -> a + b).orElse(0);
    }
}
