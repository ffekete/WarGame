package com.mygdx.wargame.battle.screen.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
import com.mygdx.wargame.common.component.armor.Armor;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.config.Config;
import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.battle.rules.facade.TurnProcessingFacade;

import java.util.Arrays;
import java.util.Optional;

import static com.mygdx.wargame.config.Config.*;

public class HudElementsFacade {

    private ImageButton endTurnButton;
    private AssetManager assetManager;
    private TurnProcessingFacade turnProcessingFacade;
    private ActionLock actionLock;

    private Table upperHud;
    private Label.LabelStyle labelStyle;

    private Tooltip<Table> shieldToolTip;
    private Table shieldTooltipTable;
    private Image shieldImage;
    private Label shieldValueLabel;

    private Image armorImage;
    private Label armorValueLabel;
    private Tooltip<Table> armorToolTip;
    private Table armorTooltipTable;

    private Image ammoImage;
    private Label ammoValueLabel;

    private Tooltip<Table> ammoToolTip;
    private Table ammoTooltipTable;

    private Image healthImage;
    private Label healthValueLabel;

    private Tooltip<Table> healthToolTip;
    private Table healthTooltipTable;

    private Image heatImage;
    private Label heatValueLabel;

    private Tooltip<Table> heatToolTip;
    private Table heatTooltipTable;

    private Image stabilityImage;
    private Label stabilityValueLabel;

    private Tooltip<Table> stabilityToolTip;
    private Table stabilityTooltipTable;

    private Pool<Label> labelPool;

    private Label pilotNameLabel;
    private Label mechNameLabel;

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

        endTurnButton.setPosition(Config.HUD_VIEWPORT_WIDTH.get() - (80 / SCREEN_HUD_RATIO), 0);
        endTurnButton.setSize(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);

        upperHud = new Table();
        upperHud.left();

        //upperHud.setDebug(true);
        upperHud.setSize(HUD_VIEWPORT_WIDTH.get(), 60 / SCREEN_HUD_RATIO);
        upperHud.setPosition(0, HUD_VIEWPORT_HEIGHT.get() - 60 / SCREEN_HUD_RATIO);

        shieldTooltipTable = new Table();
        shieldToolTip = new Tooltip<Table>(shieldTooltipTable);
        shieldToolTip.setInstant(true);
        shieldTooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);
        shieldTooltipTable.setColor(Color.valueOf("FFFFFFEE"));
        shieldTooltipTable.add(new Label("Shield protects against energy based attacks.\nIt slowly regenerates over time until the component is destroyed.", labelStyle));

        shieldImage = new AnimatedImage(new TextureRegion(assetManager.get("details/ShieldComponentIcon.png", Texture.class)), 0.15f, 100);
        upperHud.add(shieldImage);
        shieldValueLabel = labelPool.obtain();

        shieldImage.addListener(shieldToolTip);
        shieldValueLabel.addListener(shieldToolTip);

        upperHud.add(shieldValueLabel).width(60 / SCREEN_HUD_RATIO).right();

        armorImage = new AnimatedImage(new TextureRegion(assetManager.get("details/ShieldIcon.png", Texture.class)), 0.15f, 100);
        armorValueLabel = labelPool.obtain();
        upperHud.add(armorImage);
        upperHud.add(armorValueLabel).width(60 / SCREEN_HUD_RATIO).right();

        armorTooltipTable = new Table();
        armorToolTip = new Tooltip<>(armorTooltipTable);
        armorToolTip.setInstant(true);
        armorTooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);
        armorTooltipTable.setColor(Color.valueOf("FFFFFFEE"));

        armorValueLabel.addListener(armorToolTip);
        armorImage.addListener(armorToolTip);

        ammoImage = new AnimatedImage(new TextureRegion(assetManager.get("hud/AmmoIcon.png", Texture.class)), 0.15f, 100);
        ammoValueLabel = labelPool.obtain();

        upperHud.add(ammoImage);
        upperHud.add(ammoValueLabel).width(60 / SCREEN_HUD_RATIO).right();

        ammoTooltipTable = new Table();
        ammoToolTip = new Tooltip<Table>(ammoTooltipTable);
        ammoToolTip.setInstant(true);
        ammoTooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);
        ammoTooltipTable.setColor(Color.valueOf("FFFFFFEE"));
        ammoValueLabel.addListener(ammoToolTip);
        ammoImage.addListener(ammoToolTip);

        healthImage = new AnimatedImage(new TextureRegion(assetManager.get("hud/HealthIcon.png", Texture.class)), 0.15f, 100);
        healthValueLabel = labelPool.obtain();

        upperHud.add(healthImage);
        upperHud.add(healthValueLabel).width(60 / SCREEN_HUD_RATIO).right();

        healthTooltipTable = new Table();
        healthToolTip = new Tooltip<Table>(healthTooltipTable);
        healthToolTip.setInstant(true);
        healthTooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);
        healthTooltipTable.setColor(Color.valueOf("FFFFFFEE"));

        healthValueLabel.addListener(healthToolTip);
        healthImage.addListener(healthToolTip);

        heatImage = new AnimatedImage(new TextureRegion(assetManager.get("hud/HeatIcon.png", Texture.class)), 0.15f, 100);
        heatValueLabel = labelPool.obtain();

        upperHud.add(heatImage);
        upperHud.add(heatValueLabel).width(60 / SCREEN_HUD_RATIO).right();

        heatTooltipTable = new Table();
        heatToolTip = new Tooltip<Table>(heatTooltipTable);
        heatToolTip.setInstant(true);
        heatTooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);
        heatTooltipTable.setColor(Color.valueOf("FFFFFFEE"));

        heatValueLabel.addListener(heatToolTip);
        heatImage.addListener(heatToolTip);
        heatTooltipTable.add(new Label("When heat level reaches 100 the mech may blow up.", labelStyle));

        stabilityImage = new AnimatedImage(new TextureRegion(assetManager.get("hud/StabilityIcon.png", Texture.class)), 0.15f, 100);
        stabilityValueLabel = labelPool.obtain();

        upperHud.add(stabilityImage);
        upperHud.add(stabilityValueLabel).width(60 / SCREEN_HUD_RATIO).right();

        stabilityTooltipTable = new Table();
        stabilityToolTip = new Tooltip<Table>(stabilityTooltipTable);
        stabilityToolTip.setInstant(true);
        stabilityTooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);
        stabilityTooltipTable.setColor(Color.valueOf("FFFFFFEE"));

        stabilityValueLabel.addListener(stabilityToolTip);
        stabilityImage.addListener(stabilityToolTip);
        stabilityTooltipTable.add(new Label("When stability level reaches 0 the mech cannot move anymore.", labelStyle));

        mechNameLabel = labelPool.obtain();
        pilotNameLabel = labelPool.obtain();

        upperHud.add(mechNameLabel).padRight(20 / SCREEN_HUD_RATIO);
        upperHud.add(pilotNameLabel).padRight(20 / SCREEN_HUD_RATIO);

        show();
    }

    public void registerComponents(Stage stage) {
        stage.addActor(endTurnButton);
        stage.addActor(upperHud);
    }

    public void hide() {
        endTurnButton.setVisible(false);
        upperHud.setVisible(false);
    }

    public void show() {
        endTurnButton.setVisible(true);
        upperHud.setVisible(true);
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
            armorLabel.setText(turnProcessingFacade.getNext().getKey().getComponents(bodyPart).stream().filter(c -> Armor.class.isAssignableFrom(c.getClass())).map(a -> ((Armor) a).getHitPoint()).reduce((a, b) -> a + b).orElse(0) + " / " + turnProcessingFacade.getNext().getKey().getComponents(bodyPart).stream().filter(c -> Armor.class.isAssignableFrom(c.getClass())).map(a -> ((Armor) a).getMaxHitpoint()).reduce((a, b) -> a + b).orElse(0));
            armorTooltipTable.add(partLabel).padRight(40 / SCREEN_HUD_RATIO);
            armorTooltipTable.add(armorLabel).row();
        });

        ammoTooltipTable.clear();
        turnProcessingFacade.getNext().getKey().getAllComponents().stream().filter(c -> Weapon.class.isAssignableFrom(c.getClass())).map(c -> (Weapon) c).forEach(w -> {
            Label nameLabel = labelPool.obtain();
            nameLabel.setText(w.getShortName());
            Label ammoLabel = labelPool.obtain();
            ammoLabel.setText("" + (w.getAmmo().isPresent() ? w.getAmmo().get() : "N/A"));
            ammoTooltipTable.add(nameLabel).padRight(40 / SCREEN_HUD_RATIO);
            ammoTooltipTable.add(ammoLabel).row();
        });

        healthValueLabel.setText("" + (int) (100f * Arrays.stream(BodyPart.values()).map(b -> turnProcessingFacade.getNext().getKey().getHp(b)).reduce((a, b) -> a + b).orElse(0) / (float) Arrays.stream(BodyPart.values()).map(b -> turnProcessingFacade.getNext().getKey().getMaxHp(b)).reduce((a, b) -> a + b).orElse(0)));

        healthTooltipTable.clear();
        Arrays.stream(BodyPart.values()).forEach(bodyPart -> {
            Label hpLabel = labelPool.obtain();
            Label partLabel = labelPool.obtain();
            partLabel.setText(bodyPart.name());
            hpLabel.setText(+turnProcessingFacade.getNext().getKey().getHp(bodyPart) + " / " + turnProcessingFacade.getNext().getKey().getMaxHp(bodyPart));
            healthTooltipTable.add(partLabel);
            healthTooltipTable.add(hpLabel).row();
        });

        heatValueLabel.setText(turnProcessingFacade.getNext().getKey().getHeatLevel());

        stabilityValueLabel.setText(turnProcessingFacade.getNext().getKey().getStability());

        pilotNameLabel.setText(turnProcessingFacade.getNext().getValue().getName());
        mechNameLabel.setText("(" + turnProcessingFacade.getNext().getKey().getName() + ")");
    }

    private Optional<Integer> getAmmoCount() {
        return turnProcessingFacade.getNext().getKey().getAllComponents().stream().filter(c -> Weapon.class.isAssignableFrom(c.getClass())).map(w -> ((Weapon) w).getAmmo()).reduce((a, b) -> {
            return Optional.of(a.orElse(0) + b.orElse(0));
        }).orElse(Optional.of(0));
    }

    public Label getHeatValueLabel() {
        return heatValueLabel;
    }

    public Label getAmmoValueLabel() {
        return ammoValueLabel;
    }

    public Label getStabilityValueLabel() {
        return stabilityValueLabel;
    }

    public Label getShieldValueLabel() {
        return this.shieldValueLabel;
    }
}
