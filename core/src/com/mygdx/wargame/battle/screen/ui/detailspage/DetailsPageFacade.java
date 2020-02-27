package com.mygdx.wargame.battle.screen.ui.detailspage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.HUDMediator;
import com.mygdx.wargame.battle.screen.ui.localmenu.BigInfoPanelMovementHandler;
import com.mygdx.wargame.battle.screen.ui.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.util.StageUtils;

public class DetailsPageFacade {

    private boolean bigInfoPanelHidden = true;
    private BigInfoPanelMovementHandler bigInfoPanelMovementHandler = new BigInfoPanelMovementHandler();
    private Container<Table> bigInfoPanelContainer;
    private Table mechInfoOuterTable;
    private Table mechInfoInnerTable;
    private HUDMediator hudMediator;
    private MechInfoPanelFacade mechInfoPanelFacade;
    private AssetManager assetManager;


    public DetailsPageFacade(MechInfoPanelFacade mechInfoPanelFacade, HUDMediator hudMediator, AssetManager assetManager) {
        this.hudMediator = hudMediator;
        this.mechInfoPanelFacade = mechInfoPanelFacade;
        this.assetManager = assetManager;
    }

    public void create() {

        ImageButton.ImageButtonStyle hideMenuButtonsSelectionStyle = new ImageButton.ImageButtonStyle();
        hideMenuButtonsSelectionStyle.imageUp = new TextureRegionDrawable(assetManager.get("skin/HideButtonUp.png", Texture.class));
        hideMenuButtonsSelectionStyle.imageDown = new TextureRegionDrawable(assetManager.get("skin/HideButtonDown.png", Texture.class));

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

        ImageButton exitMechInfoTablePanelButton = new ImageButton(hideMenuButtonsSelectionStyle);

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
        mechInfoOuterTable.add(exitMechInfoTablePanelButton).size(10, 10).fill().right().top().row();
        mechInfoOuterTable.add(mechInfoInnerTable).center().fill();

        bigInfoPanelContainer = new Container<>(mechInfoOuterTable);
        bigInfoPanelContainer.setSize(0, 0);
        bigInfoPanelContainer.setBackground(new TextureRegionDrawable(assetManager.get("skin/BigInfoPanel.png", Texture.class)));
        bigInfoPanelContainer.setVisible(false);
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

    public Table getMechInfoTable() {
        return mechInfoInnerTable;
    }

}
