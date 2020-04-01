package com.mygdx.wargame.battle.rules.facade;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.decoration.SelectionMarker;
import com.mygdx.wargame.battle.map.render.IsometricTiledMapRendererWithSprites;
import com.mygdx.wargame.battle.rules.calculator.BodyPartDestructionHandler;
import com.mygdx.wargame.battle.rules.calculator.CriticalHitChanceCalculator;
import com.mygdx.wargame.battle.rules.calculator.DamageCalculator;
import com.mygdx.wargame.battle.rules.calculator.EvasionCalculator;
import com.mygdx.wargame.battle.rules.calculator.HeatDamageCalculator;
import com.mygdx.wargame.battle.rules.calculator.MeleeDamageCalculator;
import com.mygdx.wargame.battle.rules.calculator.WeaponStabilityDecreaseCalculator;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Perks;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.common.pilot.Skill;
import com.mygdx.wargame.util.MapUtils;
import com.mygdx.wargame.util.MathUtils;

import java.util.Map;
import java.util.Random;

public class AttackFacade {

    private HitChanceCalculatorFacade hitChanceCalculatorFacade = new HitChanceCalculatorFacade();
    private CriticalHitChanceCalculator criticalHitChanceCalculator = new CriticalHitChanceCalculator();
    private BodyPartDestructionHandler bodyPartDestructionHandler = new BodyPartDestructionHandler();
    private DamageCalculator damageCalculator;
    private EvasionCalculator evasionCalculator;
    private WeaponStabilityDecreaseCalculator weaponStabilityDecreaseCalculator = new WeaponStabilityDecreaseCalculator(criticalHitChanceCalculator);
    private ActionLock actionLock;
    private SequenceAction messageQue = new SequenceAction();
    private HeatDamageCalculator heatDamageCalculator;
    private IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites;
    private MeleeDamageCalculator meleeDamageCalculator;
    private AssetManager assetManager;
    private WeaponRangeMarkerUpdater weaponRangeMarkerUpdater;

    public AttackFacade(StageElementsStorage stageElementsStorage, AssetManager assetManager, ActionLock actionLock, IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites) {

        this.actionLock = actionLock;
        this.isometricTiledMapRendererWithSprites = isometricTiledMapRendererWithSprites;
        damageCalculator = new DamageCalculator(criticalHitChanceCalculator, bodyPartDestructionHandler, stageElementsStorage, assetManager, this.actionLock, this.isometricTiledMapRendererWithSprites);
        heatDamageCalculator = new HeatDamageCalculator(stageElementsStorage, actionLock);
        this.evasionCalculator = new EvasionCalculator();
        this.meleeDamageCalculator = new MeleeDamageCalculator(bodyPartDestructionHandler);
        this.assetManager = assetManager;
        this.weaponRangeMarkerUpdater = new WeaponRangeMarkerUpdater();
    }

    public void attack(Pilot attackingPilot, Mech attackingMech, Pilot defendingPilot, Mech defendingMech, BattleMap battleMap, BodyPart bodyPart) {
        messageQue.reset();

        if (!attackingMech.isRangedAttack()) {

            int baseHit = 60;
            baseHit += attackingPilot.getSkills().get(Skill.Melee) * 3;
            int hitRoll = new Random().nextInt(100);
            if (hitRoll < baseHit) {
                meleeDamageCalculator.calculate(attackingPilot, attackingMech, defendingPilot, defendingMech, bodyPart);
            }
        } else {

            attackingMech.getSelectedWeapons().forEach(weapon -> {

                if (weapon.getAmmo().isPresent() && weapon.getAmmo().get() == 0) {
                    // no ammo, skip
                } else {

                    if (MathUtils.getDistance(attackingMech.getX(), attackingMech.getY(), defendingMech.getX(), defendingMech.getY()) <= weapon.getRange()) {

                        if (attackingMech.getHeatLevel() > 100) {
                            if (new Random().nextInt(100) - (attackingPilot.hasPerk(Perks.Hazardous) ? 10 : 0) >= 80) {
                                System.out.println("Heat");
                                heatDamageCalculator.calculate(attackingMech, weapon, messageQue);
                            }
                        }

                        int chance = hitChanceCalculatorFacade.getHitChance(weapon, attackingPilot, attackingMech, defendingMech, bodyPart, battleMap);

                        for (int i = 0; i < weapon.getDamageMultiplier(); i++) {

                            // if no ammo, skip
                            if (weapon.getAmmo().isPresent() && weapon.getAmmo().get() < 1) {
                                continue;
                            }

                            // reduce ammo of weapon
                            weapon.reduceAmmo();


                            if (new Random().nextInt(100) < chance - evasionCalculator.calculate(attackingPilot, attackingMech, defendingPilot, battleMap)) {
                                // hit!
                                damageCalculator.calculate(attackingPilot, attackingMech, defendingPilot, defendingMech, weapon, bodyPart, messageQue);
                                int stabilityAfterHit = weaponStabilityDecreaseCalculator.calculate(attackingPilot, attackingMech, defendingPilot, defendingMech, battleMap, weapon);
                                defendingMech.setStability(defendingMech.getStability() - stabilityAfterHit);
                            }

                            attackingMech.setHeatLevel(attackingMech.getHeatLevel() + weapon.getHeat());
                        }
                    }
                }
            });
        }

        attackingMech.setAttacked(true);
        if(attackingMech.canMoveAfterAttack() && attackingMech.getRemainingMovementPoints() > 0) {
            attackingMech.setMoved(false);
        } else {
            attackingMech.setMoved(true);
        }

        if (defendingMech.getHp(BodyPart.Torso) <= 0 || defendingMech.getHp(BodyPart.Head) <= 0) {
            defendingMech.setActive(false);
        }
    }

}
