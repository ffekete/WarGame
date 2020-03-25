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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.decoration.AnimatedDrawable;
import com.mygdx.wargame.battle.map.decoration.AnimatedImage;
import com.mygdx.wargame.battle.rules.facade.TurnProcessingFacade;
import com.mygdx.wargame.common.component.armor.Armor;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.config.Config;

import java.util.Arrays;
import java.util.Optional;

import static com.mygdx.wargame.config.Config.*;

public class HudElementsFacade {

    private TextButton endTurnButton;
    private AssetManager assetManager;
    private TurnProcessingFacade turnProcessingFacade;
    private ActionLock actionLock;

    private Table upperHud;
    private Label.LabelStyle labelStyle;

    private Tooltip<Table> shieldToolTip;
    private Table shieldTooltipTable;
    private  TextButton shieldImage;
    private Label shieldValueLabel;

    private TextButton armorImage;
    private Label armorValueLabel;
    private Tooltip<Table> armorToolTip;
    private Table armorTooltipTable;

    private TextButton ammoImage;
    private Label ammoValueLabel;

    private Tooltip<Table> ammoToolTip;
    private Table ammoTooltipTable;

    private TextButton healthImage;
    private Label healthValueLabel;

    private Tooltip<Table> healthToolTip;
    private Table healthTooltipTable;

    private TextButton heatImage;
    private Label heatValueLabel;

    private Tooltip<Table> heatToolTip;
    private Table heatTooltipTable;

    private TextButton stabilityImage;
    private Label stabilityValueLabel;

    private Tooltip<Table> stabilityToolTip;
    private Table stabilityTooltipTable;

    private Pool<Label> labelPool;

    private Label pilotNameLabel;
    private Label mechNameLabel;

    private TextButton showMovementMarkersButton;
    private TextButton dontShowMovementMarkersButton;

    private TextButton showMovementDirectionsButton;
    private TextButton hideMovementDirectionsButton;

    private TextButton mainMenuButton;

    private Table sidePanel;

    private HUDMediator hudMediator;

    public HudElementsFacade(AssetManager assetManager, TurnProcessingFacade turnProcessingFacade, ActionLock actionLock, HUDMediator hudMediator) {
        this.assetManager = assetManager;
        this.turnProcessingFacade = turnProcessingFacade;
        this.actionLock = actionLock;
        this.hudMediator = hudMediator;
    }

    public void create() {

        labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(11);

        labelPool = new Pool<Label>() {
            @Override
            protected Label newObject() {
                return new Label("0", labelStyle);
            }
        };

        TextButton.TextButtonStyle endTurnButtonStyle = new TextButton.TextButtonStyle();
        endTurnButtonStyle.up = new TextureRegionDrawable(assetManager.get("hud/SmallButtonUp.png", Texture.class));
        endTurnButtonStyle.down = new TextureRegionDrawable(assetManager.get("hud/SmallButtonDown.png", Texture.class));
        endTurnButtonStyle.over = new AnimatedDrawable(new TextureRegion(assetManager.get("hud/SmallButtonOver.png", Texture.class)), 0.1f, 150, 64, 32);
        endTurnButtonStyle.font = labelStyle.font;

        sidePanel = new Table();
        //sidePanel.background(new TextureRegionDrawable(assetManager.get("hud/SidePanel.png", Texture.class)));

        endTurnButton = new TextButton("end turn", endTurnButtonStyle);

        endTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                turnProcessingFacade.getNext().getKey().setMoved(true);
                turnProcessingFacade.getNext().getKey().setAttacked(true);
            }
        });

        endTurnButton.setPosition(Config.HUD_VIEWPORT_WIDTH.get() - (20), 0);
        endTurnButton.setSize(20, 20);


        upperHud = new Table();
        upperHud.left();

        //upperHud.setDebug(true);
        upperHud.setSize(HUD_VIEWPORT_WIDTH.get(), 15);
        upperHud.setPosition(0, HUD_VIEWPORT_HEIGHT.get() - 35);
        upperHud.pad(10);

        shieldTooltipTable = new Table();
        shieldToolTip = new Tooltip<Table>(shieldTooltipTable);
        shieldToolTip.setInstant(true);
        //shieldTooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);
        shieldTooltipTable.setColor(Color.valueOf("FFFFFFEE"));
        shieldTooltipTable.add(new Label("Shield protects against energy based attacks.\nIt slowly regenerates over time until the component is destroyed.", labelStyle));

        TextButton.TextButtonStyle upperHudIconStyle = new TextButton.TextButtonStyle();
        upperHudIconStyle.up = new TextureRegionDrawable(assetManager.get("hud/SmallButtonUp.png", Texture.class));
        upperHudIconStyle.down = new TextureRegionDrawable(assetManager.get("hud/SmallButtonDown.png", Texture.class));
        upperHudIconStyle.font = labelStyle.font;
        upperHudIconStyle.over = new AnimatedDrawable(new TextureRegion(assetManager.get("hud/SmallButtonOver.png", Texture.class)), 0.1f, 150, 64, 32);


        shieldImage = new TextButton("", upperHudIconStyle);
        upperHud.add(shieldImage);
        shieldValueLabel = labelPool.obtain();

        shieldImage.addListener(shieldToolTip);

        armorImage = new TextButton("", upperHudIconStyle);
        armorValueLabel = labelPool.obtain();
        upperHud.add(armorImage);

        armorTooltipTable = new Table();
        armorToolTip = new Tooltip<>(armorTooltipTable);
        armorToolTip.setInstant(true);
        //armorTooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);
        armorTooltipTable.setColor(Color.valueOf("FFFFFFEE"));

        armorValueLabel.addListener(armorToolTip);
        armorImage.addListener(armorToolTip);

        ammoImage = new TextButton("", upperHudIconStyle);
        ammoValueLabel = labelPool.obtain();

        upperHud.add(ammoImage);

        ammoTooltipTable = new Table();
        ammoToolTip = new Tooltip<Table>(ammoTooltipTable);
        ammoToolTip.setInstant(true);
        //ammoTooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);
        ammoTooltipTable.setColor(Color.valueOf("FFFFFFEE"));
        ammoValueLabel.addListener(ammoToolTip);
        ammoImage.addListener(ammoToolTip);

        healthImage = new TextButton("", upperHudIconStyle);
        healthValueLabel = labelPool.obtain();

        upperHud.add(healthImage);

        healthTooltipTable = new Table();
        healthToolTip = new Tooltip<Table>(healthTooltipTable);
        healthToolTip.setInstant(true);
        //healthTooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);
        healthTooltipTable.setColor(Color.valueOf("FFFFFFEE"));

        healthValueLabel.addListener(healthToolTip);
        healthImage.addListener(healthToolTip);

        heatImage = new TextButton("", upperHudIconStyle);
        heatValueLabel = labelPool.obtain();

        upperHud.add(heatImage);

        heatTooltipTable = new Table();
        heatToolTip = new Tooltip<Table>(heatTooltipTable);
        heatToolTip.setInstant(true);
        //heatTooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);
        heatTooltipTable.setColor(Color.valueOf("FFFFFFEE"));

        heatValueLabel.addListener(heatToolTip);
        heatImage.addListener(heatToolTip);
        heatTooltipTable.add(new Label("When heat level reaches 100 the mech may blow up.", labelStyle));

        stabilityImage = new TextButton("", upperHudIconStyle);
        stabilityValueLabel = labelPool.obtain();

        upperHud.add(stabilityImage);

        stabilityTooltipTable = new Table();
        stabilityToolTip = new Tooltip<Table>(stabilityTooltipTable);
        stabilityToolTip.setInstant(true);
        //stabilityTooltipTable.background(new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class))).pad(80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO, 80 / SCREEN_HUD_RATIO);
        stabilityTooltipTable.setColor(Color.valueOf("FFFFFFEE"));

        stabilityValueLabel.addListener(stabilityToolTip);
        stabilityImage.addListener(stabilityToolTip);
        stabilityTooltipTable.add(new Label("When stability level reaches 0 the mech cannot move anymore.", labelStyle));

        mechNameLabel = labelPool.obtain();
        pilotNameLabel = labelPool.obtain();

        upperHud.add(mechNameLabel).padRight(5);
        upperHud.add(pilotNameLabel).padRight(5);

        TextButton.TextButtonStyle showMarkersStyle = new TextButton.TextButtonStyle();
        showMarkersStyle.up = new TextureRegionDrawable(assetManager.get("hud/SmallButtonUp.png", Texture.class));
        showMarkersStyle.down = new TextureRegionDrawable(assetManager.get("hud/SmallButtonDown.png", Texture.class));
        showMarkersStyle.font = labelStyle.font;
        showMarkersStyle.over = new AnimatedDrawable(new TextureRegion(assetManager.get("hud/SmallButtonOver.png", Texture.class)), 0.1f, 150, 64, 32);

        TextButton.TextButtonStyle dontShowMarkersStyle = new TextButton.TextButtonStyle();
        dontShowMarkersStyle.up = new TextureRegionDrawable(assetManager.get("hud/SmallButtonUp.png", Texture.class));
        dontShowMarkersStyle.down = new TextureRegionDrawable(assetManager.get("hud/SmallButtonUp.png", Texture.class));
        dontShowMarkersStyle.font = labelStyle.font;
        dontShowMarkersStyle.over = new AnimatedDrawable(new TextureRegion(assetManager.get("hud/SmallButtonOver.png", Texture.class)), 0.1f, 150, 64, 32);

        showMovementMarkersButton = new TextButton("show markers", showMarkersStyle);
        dontShowMovementMarkersButton = new TextButton("no markers", dontShowMarkersStyle);

        showMovementMarkersButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                cfg.showMovementMarkers = true;
                Config.save();
                showMovementMarkers = true;
                populateSidePanel();
            }
        });

        dontShowMovementMarkersButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cfg.showMovementMarkers = false;
                Config.save();
                showMovementMarkers = false;
                populateSidePanel();
            }
        });

        sidePanel.setPosition(Config.HUD_VIEWPORT_WIDTH.get() - (128) - 5, 0);
        sidePanel.setSize(70, 70);

        TextButton.TextButtonStyle showDirectionsStyle = new TextButton.TextButtonStyle();
        showDirectionsStyle.up = new TextureRegionDrawable(assetManager.get("hud/SmallButtonUp.png", Texture.class));
        showDirectionsStyle.up = new TextureRegionDrawable(assetManager.get("hud/SmallButtonUp.png", Texture.class));
        showDirectionsStyle.font = labelStyle.font;
        showDirectionsStyle.over = new AnimatedDrawable(new TextureRegion(assetManager.get("hud/SmallButtonOver.png", Texture.class)), 0.1f, 150, 64, 32);

        TextButton.TextButtonStyle hideDirectionsStyle = new TextButton.TextButtonStyle();
        hideDirectionsStyle.up = new TextureRegionDrawable(assetManager.get("hud/SmallButtonUp.png", Texture.class));
        hideDirectionsStyle.down = new TextureRegionDrawable(assetManager.get("hud/SmallButtonUp.png", Texture.class));
        hideDirectionsStyle.font = labelStyle.font;
        hideDirectionsStyle.over = new AnimatedDrawable(new TextureRegion(assetManager.get("hud/SmallButtonOver.png", Texture.class)), 0.1f, 150, 64, 32);

        showMovementDirectionsButton = new TextButton("directions", showDirectionsStyle);
        hideMovementDirectionsButton = new TextButton("no directions", hideDirectionsStyle);

        TextButton.TextButtonStyle mainMenuButtonStyle = new TextButton.TextButtonStyle();
        mainMenuButtonStyle.up = new TextureRegionDrawable(assetManager.get("hud/SmallButtonUp.png", Texture.class));
        mainMenuButtonStyle.down = new TextureRegionDrawable(assetManager.get("hud/SmallButtonUp.png", Texture.class));
        mainMenuButtonStyle.font = labelStyle.font;
        mainMenuButtonStyle.over = new AnimatedDrawable(new TextureRegion(assetManager.get("hud/SmallButtonOver.png", Texture.class)), 0.1f, 150, 64, 32);

        mainMenuButton = new TextButton("menu", mainMenuButtonStyle);

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hudMediator.getBattleGameMenuFacade().toggle();
            }
        });

        showMovementDirectionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Config.showDirectionMarkers = !Config.showDirectionMarkers;
                populateSidePanel();
            }
        });

        hideMovementDirectionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Config.showDirectionMarkers = !Config.showDirectionMarkers;
                populateSidePanel();
            }
        });

        sidePanel.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if (!actionLock.isLocked() && turnProcessingFacade.isNextPlayerControlled()) {
                    sidePanel.setVisible(true);
                } else {
                    sidePanel.setVisible(false);
                }
                return false;
            }
        });

        populateSidePanel();
        show();
    }

    public void registerComponents(Stage stage) {
        stage.addActor(upperHud);
        stage.addActor(sidePanel);
        //stage.addActor(dontShowMovementMarkersButton);
    }

    public void hide() {
        upperHud.setVisible(false);
        sidePanel.setVisible(false);
    }

    public void show() {
        upperHud.setVisible(true);
        sidePanel.setVisible(true);
    }

    private void populateSidePanel() {
        sidePanel.clear();
        sidePanel.add(endTurnButton);

        if (!showMovementMarkers) {
            sidePanel.add(showMovementMarkersButton);
        } else {
            sidePanel.add(dontShowMovementMarkersButton);
        }
        sidePanel.row();

        if (showDirectionMarkers) {
            sidePanel.add(hideMovementDirectionsButton);
        } else {
            sidePanel.add(showMovementDirectionsButton);
        }

        sidePanel.add(mainMenuButton);

    }

    public void update() {
        shieldValueLabel.setText(turnProcessingFacade.getNext().getKey().getShieldValue());
        shieldImage.setText("shield: " + shieldValueLabel.getText());

        armorValueLabel.setText(turnProcessingFacade.getNext().getKey().getArmorValue());
        armorImage.setText("armor: " + armorValueLabel.getText());

        ammoValueLabel.setText("" + getAmmoCount().orElse(0));
        ammoImage.setText("ammo: " + ammoValueLabel.getText());

        armorTooltipTable.clear();
        Arrays.stream(BodyPart.values()).forEach(bodyPart -> {
            Label armorLabel = labelPool.obtain();
            Label partLabel = labelPool.obtain();
            partLabel.setText(bodyPart.name());
            armorLabel.setText(turnProcessingFacade.getNext().getKey().getComponents(bodyPart).stream().filter(c -> Armor.class.isAssignableFrom(c.getClass())).map(a -> ((Armor) a).getHitPoint()).reduce((a, b) -> a + b).orElse(0) + " / " + turnProcessingFacade.getNext().getKey().getComponents(bodyPart).stream().filter(c -> Armor.class.isAssignableFrom(c.getClass())).map(a -> ((Armor) a).getMaxHitpoint()).reduce((a, b) -> a + b).orElse(0));
            armorTooltipTable.add(partLabel).padRight(20);
            armorTooltipTable.add(armorLabel).row();
        });

        ammoTooltipTable.clear();
        turnProcessingFacade.getNext().getKey().getAllComponents().stream().filter(c -> Weapon.class.isAssignableFrom(c.getClass())).map(c -> (Weapon) c).forEach(w -> {
            Label nameLabel = labelPool.obtain();
            nameLabel.setText(w.getShortName());
            Label ammoLabel = labelPool.obtain();
            ammoLabel.setText("" + (w.getAmmo().isPresent() ? w.getAmmo().get() : "N/A"));
            ammoTooltipTable.add(nameLabel).padRight(20);
            ammoTooltipTable.add(ammoLabel).row();
        });

        healthValueLabel.setText("" + (int) (100f * Arrays.stream(BodyPart.values()).map(b -> turnProcessingFacade.getNext().getKey().getHp(b)).reduce((a, b) -> a + b).orElse(0) / (float) Arrays.stream(BodyPart.values()).map(b -> turnProcessingFacade.getNext().getKey().getMaxHp(b)).reduce((a, b) -> a + b).orElse(0)));
        healthImage.setText("HP: " + healthValueLabel.getText() + "%");
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
        heatImage.setText("heat: " + heatValueLabel.getText());

        stabilityValueLabel.setText(turnProcessingFacade.getNext().getKey().getStability());
        stabilityImage.setText("stability: " + stabilityValueLabel.getText());

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

    public Label getStabilityValueLabel() {
        return stabilityValueLabel;
    }

    public Label getShieldValueLabel() {
        return this.shieldValueLabel;
    }

    public TextButton getShieldImage() {
        return shieldImage;
    }

    public TextButton getHeatImage() {
        return heatImage;
    }

    public TextButton getStabilityImage() {
        return stabilityImage;
    }

    public TextButton getAmmoImage() {
        return ammoImage;
    }
}
