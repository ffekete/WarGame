package com.mygdx.wargame.rules.facade;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.calculator.BodyPartDestructionHandler;
import com.mygdx.wargame.rules.calculator.CriticalHitChanceCalculator;
import com.mygdx.wargame.rules.calculator.DamageCalculator;
import com.mygdx.wargame.rules.calculator.EvasionCalculator;
import com.mygdx.wargame.rules.calculator.StabilityCalculator;
import com.mygdx.wargame.rules.calculator.hitchance.BallisticHitChanceCalculator;
import com.mygdx.wargame.rules.calculator.hitchance.FlamerHitChanceCalculator;
import com.mygdx.wargame.rules.calculator.hitchance.IonHitChanceCalculator;
import com.mygdx.wargame.rules.calculator.hitchance.LaserHitChanceCalculator;
import com.mygdx.wargame.rules.calculator.hitchance.MissileHitChanceCalculator;
import com.mygdx.wargame.rules.calculator.hitchance.PlasmaHitChanceCalculator;
import com.mygdx.wargame.util.MathUtils;

import java.util.Random;

public class AttackFacade {

    private HitChanceCalculatorFacade hitChanceCalculatorFacade = new HitChanceCalculatorFacade();
    private CriticalHitChanceCalculator criticalHitChanceCalculator = new CriticalHitChanceCalculator();
    private BodyPartDestructionHandler bodyPartDestructionHandler = new BodyPartDestructionHandler();
    private DamageCalculator damageCalculator;
    private EvasionCalculator evasionCalculator = new EvasionCalculator();
    private StabilityCalculator stabilityCalculator = new StabilityCalculator(criticalHitChanceCalculator);
    private MechInfoPanelFacade mechInfoPanelFacade;
    private ActionLock actionLock;
    SequenceAction messageQue = new SequenceAction();

    public AttackFacade(StageElementsStorage stageElementsStorage, AssetManager assetManager, MechInfoPanelFacade mechInfoPanelFacade, ActionLock actionLock) {
        this.mechInfoPanelFacade = mechInfoPanelFacade;
        this.actionLock = actionLock;
        damageCalculator = new DamageCalculator(criticalHitChanceCalculator, bodyPartDestructionHandler, stageElementsStorage, assetManager, mechInfoPanelFacade, this.actionLock);
    }

    public void attack(Pilot attackingPilot, Mech attackingMech, Pilot defendingPilot, Mech defendingMech, BattleMap battleMap, BodyPart bodyPart) {
        messageQue.reset();

        attackingMech.getSelectedWeapons().forEach(weapon -> {

            if (MathUtils.getDistance(attackingMech.getX(), attackingMech.getY(), defendingMech.getX(), defendingMech.getY()) <= weapon.getRange()) {

                int chance = hitChanceCalculatorFacade.getHitChance(weapon, attackingPilot, attackingMech, defendingMech, bodyPart);

                // reduce ammo of weapon
                for(int i = 0; i < weapon.getDamageMultiplier(); i++)
                    weapon.reduceAmmo();

                if (new Random().nextInt(100) < chance - evasionCalculator.calculate(attackingPilot, attackingMech, defendingPilot, battleMap)) {
                    // hit!
                    damageCalculator.calculate(attackingPilot, attackingMech, defendingPilot, defendingMech, weapon, bodyPart, messageQue);
                    int stabilityAfterHit = stabilityCalculator.calculate(attackingPilot, attackingMech, defendingPilot, defendingMech, battleMap, weapon);
                    defendingMech.setStability(defendingMech.getStability() - stabilityAfterHit);
                }

                attackingMech.setHeatLevel(attackingMech.getHeatLevel() + weapon.getHeat());
            }
        });

        attackingMech.setAttacked(true);
        attackingMech.setMoved(true);

        if (defendingMech.getHp(BodyPart.Torso) <= 0 || defendingMech.getHp(BodyPart.Head) <= 0) {
            defendingMech.setActive(false);
        }
    }

}
