package com.mygdx.wargame.battle.screen.ui;

import com.mygdx.wargame.battle.screen.ui.detailspage.DetailsPageFacade;
import com.mygdx.wargame.battle.screen.ui.localmenu.EnemyMechInfoPanelFacade;
import com.mygdx.wargame.battle.screen.ui.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.battle.screen.ui.targeting.TargetingPanelFacade;

public class HUDMediator {

    private HudElementsFacade hudElementsFacade;
    private MechInfoPanelFacade mechInfoPanelFacade;
    private EnemyMechInfoPanelFacade enemyMechInfoPanelFacade;
    private TargetingPanelFacade targetingPanelFacade;
    private HealthInfoPanelFacade healthInfoPanelFacade;
    private DetailsPageFacade detailsPageFacade;

    public HUDMediator() {
    }

    public void hideAll() {
        hudElementsFacade.hide();
        mechInfoPanelFacade.hide();
        enemyMechInfoPanelFacade.hideLocalMenu();
        targetingPanelFacade.hide();
        healthInfoPanelFacade.hide();
    }

    public void setHudElementsFacade(HudElementsFacade hudElementsFacade) {
        this.hudElementsFacade = hudElementsFacade;
    }

    public void setMechInfoPanelFacade(MechInfoPanelFacade mechInfoPanelFacade) {
        this.mechInfoPanelFacade = mechInfoPanelFacade;
    }

    public void setEnemyMechInfoPanelFacade(EnemyMechInfoPanelFacade enemyMechInfoPanelFacade) {
        this.enemyMechInfoPanelFacade = enemyMechInfoPanelFacade;
    }

    public void setTargetingPanelFacade(TargetingPanelFacade targetingPanelFacade) {
        this.targetingPanelFacade = targetingPanelFacade;
    }

    public void setHealthInfoPanelFacade(HealthInfoPanelFacade healthInfoPanelFacade) {
        this.healthInfoPanelFacade = healthInfoPanelFacade;
    }

    public HudElementsFacade getHudElementsFacade() {
        return hudElementsFacade;
    }

    public MechInfoPanelFacade getMechInfoPanelFacade() {
        return mechInfoPanelFacade;
    }

    public EnemyMechInfoPanelFacade getEnemyMechInfoPanelFacade() {
        return enemyMechInfoPanelFacade;
    }

    public TargetingPanelFacade getTargetingPanelFacade() {
        return targetingPanelFacade;
    }

    public HealthInfoPanelFacade getHealthInfoPanelFacade() {
        return healthInfoPanelFacade;
    }

    public DetailsPageFacade getDetailsPageFacade() {
        return detailsPageFacade;
    }

    public void setDetailsPageFacade(DetailsPageFacade detailsPageFacade) {
        this.detailsPageFacade = detailsPageFacade;
    }
}
