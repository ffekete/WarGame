package com.mygdx.wargame.battle.screen.ui.localmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.battle.combat.RangedAttackTargetCalculator;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.targeting.TargetingPanelFacade;
import com.mygdx.wargame.config.Config;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Pilot;

import static com.mygdx.wargame.config.Config.SCREEN_HUD_RATIO;

public class EnemyMechInfoPanelFacade {

    private ImageButton hideMenuButton;
    private ImageButton attackButton;
    private ImageButton aimedAttackButton;

    private boolean localMenuVisible = false;


    private StageElementsStorage stageElementsStorage;
    private ActionLock actionLock;
    private TargetingPanelFacade targetingPanelFacade;
    private RangedAttackTargetCalculator rangedAttackTargetCalculator;


    private Pilot attackingPilot;
    private Pilot defendingPilot;

    private Mech attackingMech;
    private Mech defendingMech;

    private BattleMap battleMap;

    public EnemyMechInfoPanelFacade(StageElementsStorage stageElementsStorage, ActionLock actionLock, TargetingPanelFacade targetingPanelFacade, RangedAttackTargetCalculator rangedAttackTargetCalculator) {
        this.stageElementsStorage = stageElementsStorage;
        this.actionLock = actionLock;
        this.targetingPanelFacade = targetingPanelFacade;
        this.rangedAttackTargetCalculator = rangedAttackTargetCalculator;

        ImageButton.ImageButtonStyle hideMenuButtonsSelectionStyle = new ImageButton.ImageButtonStyle();
        hideMenuButtonsSelectionStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("localmenu/ExitLocalMenuButton.png")));

        hideMenuButton = new ImageButton(hideMenuButtonsSelectionStyle);
        hideMenuButton.setSize(hideMenuButton.getWidth() / 2, hideMenuButton.getHeight() / 2);
        hideMenuButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // hide all other panels
                hideLocalMenu();
                return true;
            }
        });

        ImageButton.ImageButtonStyle attackButtonsSelectionStyle = new ImageButton.ImageButtonStyle();
        attackButtonsSelectionStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("localmenu/AttackButton.png")));

        attackButton = new ImageButton(attackButtonsSelectionStyle);
        attackButton.setSize(attackButton.getWidth() / 2, attackButton.getHeight() / 2);
        attackButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // hide all other panels
                hideLocalMenu();
                targetingPanelFacade.hide();
                EnemyMechInfoPanelFacade.this.rangedAttackTargetCalculator.calculate(attackingPilot, (AbstractMech) attackingMech, (AbstractMech) defendingMech, defendingPilot, null);
                return true;
            }
        });


        ImageButton.ImageButtonStyle calledShotButtonStyle = new ImageButton.ImageButtonStyle();
        calledShotButtonStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("localmenu/CalledShotUp.png")));

        aimedAttackButton = new ImageButton(calledShotButtonStyle);
        aimedAttackButton.setSize(aimedAttackButton.getWidth() / 2, aimedAttackButton.getHeight() / 2);

        aimedAttackButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // hide all other panels
                hideLocalMenu();
                targetingPanelFacade.hide();
                stageElementsStorage.hudStage.addActor(targetingPanelFacade.getPanel(attackingPilot, attackingMech, defendingPilot, defendingMech));
                targetingPanelFacade.getPanel().setPosition(Math.min(Math.max(aimedAttackButton.getX() - 250 / SCREEN_HUD_RATIO, 0), Config.HUD_VIEWPORT_WIDTH.get()), Math.min(Math.max(aimedAttackButton.getY() - 100 / SCREEN_HUD_RATIO, 0), Config.HUD_VIEWPORT_HEIGHT.get()));
                targetingPanelFacade.getPanel().setSize(200 / SCREEN_HUD_RATIO, 200 / SCREEN_HUD_RATIO);
                targetingPanelFacade.getPanel().setVisible(true);
                //EnemyMechInfoPanelFacade.this.rangedAttackTargetCalculator.calculate(attackingPilot, (AbstractMech) attackingMech, (AbstractMech) defendingMech, defendingPilot, null);
                return true;
            }
        });
    }

    public ImageButton getHideMenuButton() {
        return hideMenuButton;
    }

    public void registerComponents(Stage stage) {
        stage.addActor(hideMenuButton);
        stage.addActor(attackButton);
        stage.addActor(aimedAttackButton);
        hideLocalMenu();
    }

    public void hideLocalMenu() {
        localMenuVisible = false;

        SequenceAction sequenceAction;

        VisibleAction visibleAction = new VisibleAction();
        visibleAction.setVisible(false);

        MoveByAction moveTo = new MoveByAction();

        moveTo.setAmount(0, 60 / SCREEN_HUD_RATIO);
        moveTo.setDuration(0.25f);
        sequenceAction = new SequenceAction();
        visibleAction = new VisibleAction();
        visibleAction.setVisible(false);
        sequenceAction.addAction(moveTo);
        sequenceAction.addAction(visibleAction);
        attackButton.addAction(sequenceAction);

        moveTo = new MoveByAction();
        moveTo.setAmount(0, -60 / SCREEN_HUD_RATIO);
        moveTo.setDuration(0.25f);
        sequenceAction = new SequenceAction();
        visibleAction = new VisibleAction();
        visibleAction.setVisible(false);
        sequenceAction.addAction(moveTo);
        sequenceAction.addAction(visibleAction);
        hideMenuButton.addAction(sequenceAction);

        moveTo = new MoveByAction();
        moveTo.setAmount(-60 / SCREEN_HUD_RATIO, 0);
        moveTo.setDuration(0.25f);
        sequenceAction = new SequenceAction();
        visibleAction = new VisibleAction();
        visibleAction.setVisible(false);
        sequenceAction.addAction(moveTo);
        sequenceAction.addAction(visibleAction);
        aimedAttackButton.addAction(sequenceAction);
//
//        moveTo = new MoveByAction();
//        moveTo.setAmount(60 / SCREEN_HUD_RATIO, 0);
//        moveTo.setDuration(0.25f);
//        sequenceAction = new SequenceAction();
//        visibleAction = new VisibleAction();
//        visibleAction.setVisible(false);
//        sequenceAction.addAction(moveTo);
//        sequenceAction.addAction(visibleAction);
//        detailsButton.addAction(sequenceAction);
    }

    public boolean isLocalMenuVisible() {
        return localMenuVisible;
    }

    public void showLocalMenu() {
        localMenuVisible = true;
        MoveByAction moveTo = new MoveByAction();
        moveTo.setAmount(0, -60 / SCREEN_HUD_RATIO);
        moveTo.setDuration(0.25f);
        attackButton.addAction(moveTo);

        moveTo = new MoveByAction();
        moveTo.setAmount(0, 60 / SCREEN_HUD_RATIO);
        moveTo.setDuration(0.25f);
        hideMenuButton.addAction(moveTo);

        moveTo = new MoveByAction();
        moveTo.setAmount(60 / SCREEN_HUD_RATIO, 0);
        moveTo.setDuration(0.25f);
        aimedAttackButton.addAction(moveTo);
//
//        moveTo = new MoveByAction();
//        moveTo.setAmount(-60 / SCREEN_HUD_RATIO, 0);
//        moveTo.setDuration(0.25f);
//        aimedAttackButton.addAction(moveTo);
//
    }

    public void setAttackingPilot(Pilot attackingPilot) {
        this.attackingPilot = attackingPilot;
    }

    public void setDefendingPilot(Pilot defendingPilot) {
        this.defendingPilot = defendingPilot;
    }

    public void setAttackingMech(Mech attackingMech) {
        this.attackingMech = attackingMech;
    }

    public void setDefendingMech(Mech defendingMech) {
        this.defendingMech = defendingMech;
    }

    public void setBattleMap(BattleMap battleMap) {
        this.battleMap = battleMap;
    }

    public ImageButton getAttackButton() {
        return attackButton;
    }

    public ImageButton getAimedAttackButton() {
        return aimedAttackButton;
    }
}
