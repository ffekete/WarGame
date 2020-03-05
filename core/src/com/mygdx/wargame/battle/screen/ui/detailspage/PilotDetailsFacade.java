package com.mygdx.wargame.battle.screen.ui.detailspage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.wargame.battle.map.decoration.AnimatedDrawable;
import com.mygdx.wargame.battle.screen.ui.FontCreator;
import com.mygdx.wargame.battle.screen.ui.HUDMediator;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.common.pilot.Skill;

import static com.mygdx.wargame.config.Config.*;

public class PilotDetailsFacade {

    private AssetManager assetManager;
    private Label.LabelStyle labelStyle;
    private HUDMediator hudMediator;

    private Table outerTable;
    private ScrollPane skillScrollPane;
    private ScrollPane perkScrollPane;
    private Label pilotNameLabel;
    private TextButton textButton;
    private TextButton.TextButtonStyle textButtonStyle;
    private Table skillTable;
    private Table perkTable;
    private Pool<Label> labelPool;
    private Pool<Image> imagePool;
    private TextureRegionDrawable bg;
    private TextButton skillsButton;
    private TextButton perksButton;
    private Pilot pilot;
    private TextButton exitPanelButton;

    private boolean skillsShown = true;

    public PilotDetailsFacade(AssetManager assetManager, HUDMediator hudMediator) {
        this.assetManager = assetManager;
        this.hudMediator = hudMediator;
    }

    public void create() {

        bg = new TextureRegionDrawable(assetManager.get("skin/SimplePanel.png", Texture.class));

        labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(13);

        labelPool = new Pool<Label>() {
            @Override
            protected Label newObject() {
                return new Label("", labelStyle);
            }
        };
        imagePool = new Pool<Image>() {
            @Override
            protected Image newObject() {
                return new Image(assetManager.get("InfoIcon.png", Texture.class));
            }
        };

        pilotNameLabel = new Label("N/A", labelStyle);
        outerTable = new Table();
        outerTable.background(new TextureRegionDrawable(assetManager.get("skin/BigInfoPanel.png", Texture.class)));
        outerTable.setFillParent(true);

        this.textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = FontCreator.getBitmapFont(13);
        textButtonStyle.fontColor = Color.valueOf("FFFFFF");
        textButtonStyle.overFontColor = Color.valueOf("00FF00");

        textButtonStyle.up = new AnimatedDrawable(new TextureRegion(assetManager.get("details/ButtonBg.png", Texture.class)), 0.1f, 1000);

        TextButton.TextButtonStyle anotherButtonStyle = new TextButton.TextButtonStyle();
        anotherButtonStyle.font = FontCreator.getBitmapFont(13);

        perksButton = new TextButton("show perks", anotherButtonStyle);
        skillsButton = new TextButton("show skills", anotherButtonStyle);

        outerTable.add(perksButton);
        outerTable.add(skillsButton).row();

        skillsButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                skillsShown = true;
                update(pilot);
                return true;
            }
        });

        perksButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                skillsShown = false;
                update(pilot);
                return true;
            }
        });

        exitPanelButton = new TextButton("Exit", textButtonStyle);

        exitPanelButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hide();
                hudMediator.getHudElementsFacade().show();
                hudMediator.getHealthInfoPanelFacade().show();
                return true;
            }
        });

        skillTable = new Table();
        skillTable.setSize(HUD_VIEWPORT_WIDTH, HUD_VIEWPORT_HEIGHT / 2);
        perkTable = new Table();

        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.vScrollKnob = new TextureRegionDrawable(assetManager.get("skin/ScrollBarKnob.png", Texture.class));

        skillScrollPane = new ScrollPane(skillTable, scrollPaneStyle);
        skillScrollPane.setScrollbarsVisible(true);

        perkScrollPane = new ScrollPane(perkTable, scrollPaneStyle);
        perkScrollPane.setScrollbarsVisible(true);

        outerTable.add();
        outerTable.add(pilotNameLabel).pad(60 / SCREEN_HUD_RATIO);
        outerTable.add().row();
        outerTable.add(skillScrollPane).colspan(2).row();
        outerTable.add(perkScrollPane).colspan(2).row();
        outerTable.add(exitPanelButton).colspan(2).center().size(240 / SCREEN_HUD_RATIO, 120 / SCREEN_HUD_RATIO).pad(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO);

        outerTable.pad(60 / SCREEN_HUD_RATIO);

        skillTable.pad(60 / SCREEN_HUD_RATIO);

        perkTable.setSize(HUD_VIEWPORT_WIDTH, HUD_VIEWPORT_HEIGHT / 2);
        perkTable.pad(60 / SCREEN_HUD_RATIO);

        //outerTable.setDebug(true);
    }

    public void register(Stage stage) {
        stage.addActor(outerTable);
    }

    public void show() {
        outerTable.setVisible(true);
    }

    public void hide() {
        outerTable.setVisible(false);
    }

    public void update(Pilot pilot) {

        this.pilot = pilot;

        pilotNameLabel.setText(pilot.getName());
        skillTable.clear();
        for (Skill value : Skill.values()) {
            Label skillLabel = labelPool.obtain();
            Label descLabel = labelPool.obtain();
            Label valueLabel = labelPool.obtain();

            skillLabel.setText(value.getName());
            descLabel.setText(value.getDescription());
            valueLabel.setText(pilot.getSkills().getOrDefault(value, 0));

            skillTable.add(skillLabel).left().padRight(40 / SCREEN_HUD_RATIO);
            skillTable.add(valueLabel).center().maxWidth(400 / SCREEN_HUD_RATIO);
            Image image = imagePool.obtain();
            skillTable.add(image).right().padLeft(40 / SCREEN_HUD_RATIO).maxWidth(16).row();
            Table table = new Table();
            table.setBackground(bg);
            table.add(descLabel);
            table.pad(60 / SCREEN_HUD_RATIO);
            image.addListener(new Tooltip<>(table));
        }

        perkTable.clear();

        pilot.getPerks().forEach(perk -> {
            Label perkName = labelPool.obtain();
            Label description = labelPool.obtain();

            perkName.setText(perk.getName());
            description.setText(perk.getDescription());

            perkTable.add(perkName).left().padRight(40 / SCREEN_HUD_RATIO);

            Image image = imagePool.obtain();
            perkTable.add(image).right().padLeft(40 / SCREEN_HUD_RATIO).maxWidth(16).row();
            Table table = new Table();
            table.setBackground(bg);
            table.add(description);
            table.pad(60 / SCREEN_HUD_RATIO);
            image.addListener(new Tooltip<>(table));

            //perkTable.add(description).right().padLeft(40 / SCREEN_HUD_RATIO).colspan(2).maxWidth(HUD_VIEWPORT_WIDTH).row();
        });

        if (skillsShown) {
            outerTable.clear();
            outerTable.add(perksButton).colspan(2).row();
            //outerTable.add(skillsButton).row();
            outerTable.add(skillScrollPane).minHeight(HUD_VIEWPORT_HEIGHT - 340 / SCREEN_HUD_RATIO).row();
            outerTable.add(exitPanelButton).colspan(2).center().size(240 / SCREEN_HUD_RATIO, 120 / SCREEN_HUD_RATIO).pad(20 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 20 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO);
            //skillTable.setFillParent(true);
        } else {
            outerTable.clear();
            //outerTable.add(perksButton);
            outerTable.add(skillsButton).colspan(2).row();
            outerTable.add(perkScrollPane).minHeight(HUD_VIEWPORT_HEIGHT -340 / SCREEN_HUD_RATIO).row();
            outerTable.add(exitPanelButton).colspan(3).center().size(240 / SCREEN_HUD_RATIO, 120 / SCREEN_HUD_RATIO).pad(20 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 20 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO);
            //perkTable.setFillParent(true);
        }
    }

}
