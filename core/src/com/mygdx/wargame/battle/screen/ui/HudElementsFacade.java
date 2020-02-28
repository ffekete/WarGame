package com.mygdx.wargame.battle.screen.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.decoration.AnimatedImage;
import com.mygdx.wargame.component.armor.Armor;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.config.Config;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;

import java.util.Arrays;
import java.util.Optional;

import static com.mygdx.wargame.config.Config.*;

public class HudElementsFacade {

    private ImageButton endTurnButton;
    private AssetManager assetManager;
    private TurnProcessingFacade turnProcessingFacade;
    private ActionLock actionLock;
    private Image shieldImage;
    private Label shieldValueLabel;
    private HorizontalGroup upperHud;
    private Label.LabelStyle labelStyle;

    private Image armorImage;
    private Label armorValueLabel;
    private Tooltip<Table> armorToolTip;
    private Table armorTooltipTable;

    private Image ammoImage;
    private Label ammoValueLabel;

    private Tooltip<Table> ammoToolTip;
    private Table tooltipTable;

    private Image healthImage;
    private Label healthValueLabel;

    private Tooltip<Table> healthToolTip;
    private Table healthTooltipTable;

    private Pool<Label> labelPool;

    public HudElementsFacade(AssetManager assetManager, TurnProcessingFacade turnProcessingFacade, ActionLock actionLock) {
        this.assetManager = assetManager;
        this.turnProcessingFacade = turnProcessingFacade;
        this.actionLock = actionLock;
    }

    public void create() {

        labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(12);

        labelPool = new Pool<Label>() {
            @Override
            protected Label newObject() {
                return new Label("0", labelStyle);
            }
        };

        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.imageUp = new TextureRegionDrawable(assetManager.get("skin/EndTurnButtonUp.png", Texture.class));
        imageButtonStyle.imageDown = new TextureRegionDrawable(assetManager.get("skin/EndTurnButtonDown.png", Texture.class));

        endTurnButton = new ImageButton(imageButtonStyle);

        endTurnButton.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if (!actionLock.isLocked() && turnProcessingFacade.isNextPlayerControlled()) {
                    endTurnButton.setVisible(true);
                } else {
                    endTurnButton.setVisible(false);
                }
                return false;
            }
        });

        endTurnButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                turnProcessingFacade.getNext().getKey().setMoved(true);
                turnProcessingFacade.getNext().getKey().setAttacked(true);
                return true;
            }
        });

        upperHud = new HorizontalGroup();
        //upperHud.setDebug(true);
        upperHud.setSize(HUD_VIEWPORT_WIDTH, 60 / SCREEN_HUD_RATIO);
        upperHud.setPosition(0, HUD_VIEWPORT_HEIGHT - 60 / SCREEN_HUD_RATIO);
        shieldImage = new AnimatedImage(new TextureRegion(assetManager.get("details/ShieldComponentIcon.png", Texture.class)), 0.15f, 350);
        upperHud.addActor(shieldImage);
        shieldValueLabel = labelPool.obtain();
        upperHud.addActor(shieldValueLabel);

        armorImage = new AnimatedImage(new TextureRegion(assetManager.get("details/ShieldIcon.png", Texture.class)), 0.15f, 300);
        armorValueLabel = labelPool.obtain();
        upperHud.addActor(armorImage);
        upperHud.addActor(armorValueLabel);

        armorTooltipTable = new Table();
        armorToolTip = new Tooltip<>(armorTooltipTable);
        armorToolTip.setInstant(true);
        armorTooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/InfoPanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);

        armorValueLabel.addListener(armorToolTip);
        armorImage.addListener(armorToolTip);

        ammoImage = new AnimatedImage(new TextureRegion(assetManager.get("hud/AmmoIcon.png", Texture.class)), 0.15f, 250);
        ammoValueLabel = labelPool.obtain();

        upperHud.addActor(ammoImage);
        upperHud.addActor(ammoValueLabel);

        tooltipTable = new Table();
        ammoToolTip = new Tooltip<Table>(tooltipTable);
        ammoToolTip.setInstant(true);
        tooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/InfoPanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);

        ammoValueLabel.addListener(ammoToolTip);
        ammoImage.addListener(ammoToolTip);

        healthImage = new AnimatedImage(new TextureRegion(assetManager.get("hud/HealthIcon.png", Texture.class)), 0.15f, 100);
        healthValueLabel = labelPool.obtain();

        upperHud.addActor(healthImage);
        upperHud.addActor(healthValueLabel);

        healthTooltipTable = new Table();
        healthToolTip = new Tooltip<Table>(healthTooltipTable);
        healthToolTip.setInstant(true);
        healthTooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/InfoPanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);

        healthValueLabel.addListener(healthToolTip);
        healthImage.addListener(healthToolTip);

        show();
    }

    public void registerComponents(Stage stage) {
        stage.addActor(endTurnButton);
        stage.addActor(upperHud);
    }

    public void hide() {
        endTurnButton.setSize(0, 0);
        upperHud.setSize(0, 0);
    }

    public void show() {
        endTurnButton.setPosition(Config.HUD_VIEWPORT_WIDTH - (80 / SCREEN_HUD_RATIO), 0);
        endTurnButton.setSize(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);
        upperHud.setSize(HUD_VIEWPORT_WIDTH, 60 / SCREEN_HUD_RATIO);
    }

    public void update() {
        shieldValueLabel.setText(turnProcessingFacade.getNext().getKey().getShieldValue());
        armorValueLabel.setText(turnProcessingFacade.getNext().getKey().getArmorValue());
        ammoValueLabel.setText("" + getAmmoCount().orElse(0));


        armorTooltipTable.clear();
        Arrays.stream(BodyPart.values()).forEach(bodyPart -> {
            Label armorLabel = labelPool.obtain();
            Label partLabel = labelPool.obtain();
            partLabel.setText(bodyPart.name());
            armorLabel.setText(turnProcessingFacade.getNext().getKey().getComponents(bodyPart).stream().filter(c -> Armor.class.isAssignableFrom(c.getClass())).map(a -> ((Armor) a).getHitPoint()).reduce((a,b) -> a+b).orElse(0) + " / " + turnProcessingFacade.getNext().getKey().getComponents(bodyPart).stream().filter(c -> Armor.class.isAssignableFrom(c.getClass())).map(a -> ((Armor) a).getMaxHitpoint()).reduce((a,b) -> a+b).orElse(0));
            armorTooltipTable.add(partLabel).padRight(40 / SCREEN_HUD_RATIO);
            armorTooltipTable.add(armorLabel).row();
        });

        tooltipTable.clear();
        turnProcessingFacade.getNext().getKey().getAllComponents().stream().filter(c -> Weapon.class.isAssignableFrom(c.getClass())).map(c -> (Weapon) c).forEach(w -> {
            Label nameLabel = labelPool.obtain();
            nameLabel.setText(w.getShortName());
            Label ammoLabel = labelPool.obtain();
            ammoLabel.setText("" + (w.getAmmo().isPresent() ? w.getAmmo().get() : "N/A"));
            tooltipTable.add(nameLabel).padRight(40 / SCREEN_HUD_RATIO);
            tooltipTable.add(ammoLabel).row();
        });

        healthValueLabel.setText("" + (int)(100f * Arrays.stream(BodyPart.values()).map(b -> turnProcessingFacade.getNext().getKey().getHp(b)).reduce((a,b)->a+b).orElse(0) / (float)Arrays.stream(BodyPart.values()).map(b -> turnProcessingFacade.getNext().getKey().getMaxHp(b)).reduce((a,b)->a+b).orElse(0)));

        healthTooltipTable.clear();
        Arrays.stream(BodyPart.values()).forEach(bodyPart -> {
            Label hpLabel = labelPool.obtain();
            Label partLabel = labelPool.obtain();
            partLabel.setText(bodyPart.name());
            hpLabel.setText(+ turnProcessingFacade.getNext().getKey().getHp(bodyPart) + " / " + turnProcessingFacade.getNext().getKey().getMaxHp(bodyPart));
            healthTooltipTable.add(partLabel);
            healthTooltipTable.add(hpLabel).row();
        });


    }

    private Optional<Integer> getAmmoCount() {
        return turnProcessingFacade.getNext().getKey().getAllComponents().stream().filter(c -> Weapon.class.isAssignableFrom(c.getClass())).map(w -> ((Weapon) w).getAmmo()).reduce((a, b) -> {
            return Optional.of(a.orElse(0) + b.orElse(0));
        }).get();
    }
}
