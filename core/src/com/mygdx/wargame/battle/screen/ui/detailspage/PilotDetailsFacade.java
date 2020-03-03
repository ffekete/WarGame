package com.mygdx.wargame.battle.screen.ui.detailspage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.wargame.battle.screen.ui.FontCreator;
import com.mygdx.wargame.battle.screen.ui.HUDMediator;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.pilot.Skill;

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

    public PilotDetailsFacade(AssetManager assetManager, HUDMediator hudMediator) {
        this.assetManager = assetManager;
        this.hudMediator = hudMediator;
    }

    public void create() {
        labelStyle = new Label.LabelStyle();
        labelStyle.font = FontCreator.getBitmapFont(13);
        labelPool = new Pool<Label>() {
            @Override
            protected Label newObject() {
                return new Label("", labelStyle);
            }
        };

        pilotNameLabel = new Label("N/A", labelStyle);
        outerTable = new Table();
        outerTable.background(new TextureRegionDrawable(assetManager.get("skin/BigInfoPanel.png", Texture.class)));
        outerTable.setFillParent(true);

        this.textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = FontCreator.getBitmapFont(13);
        textButtonStyle.fontColor = Color.valueOf("007700");
        textButtonStyle.overFontColor = Color.valueOf("00AA00");
        textButtonStyle.down = new TextureRegionDrawable(assetManager.get("details/ButtonBgDown.png", Texture.class));
        textButtonStyle.up = new TextureRegionDrawable(assetManager.get("details/ButtonBg.png", Texture.class));
        textButtonStyle.over = new TextureRegionDrawable(assetManager.get("details/ButtonBgOver.png", Texture.class));

        TextButton exitPanelButton = new TextButton("Exit", textButtonStyle);

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
        skillTable.setSize(HUD_VIEWPORT_WIDTH, HUD_VIEWPORT_HEIGHT / 3);
        perkTable = new Table();

        skillScrollPane = new ScrollPane(skillTable);
        perkScrollPane = new ScrollPane(perkTable);

        outerTable.add();
        outerTable.add(pilotNameLabel).pad(60 / SCREEN_HUD_RATIO);
        outerTable.add().row();
        outerTable.add(skillScrollPane).colspan(3).row();
        outerTable.add(perkScrollPane).colspan(3).row();
        outerTable.add(exitPanelButton).colspan(3).center().size(240 / SCREEN_HUD_RATIO, 120 / SCREEN_HUD_RATIO).pad(60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO, 60 / SCREEN_HUD_RATIO);

        skillTable.pad(60 / SCREEN_HUD_RATIO);
        
        perkTable.setSize(HUD_VIEWPORT_WIDTH, HUD_VIEWPORT_HEIGHT / 3);
        perkTable.pad(60 / SCREEN_HUD_RATIO);
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
            skillTable.add(valueLabel).center();
            skillTable.add(descLabel).right().padLeft(40 / SCREEN_HUD_RATIO).maxWidth(HUD_VIEWPORT_WIDTH).row();
        }

        perkTable.clear();

        pilot.getPerks().forEach(perk -> {
            Label perkName = labelPool.obtain();
            Label description = labelPool.obtain();

            perkName.setText(perk.getName());
            description.setText(perk.getDescription());

            perkTable.add(perkName).left().padRight(40 / SCREEN_HUD_RATIO);
            perkTable.add(description).right().padLeft(40 / SCREEN_HUD_RATIO).colspan(2).maxWidth(HUD_VIEWPORT_WIDTH).row();
        });
    }

}
