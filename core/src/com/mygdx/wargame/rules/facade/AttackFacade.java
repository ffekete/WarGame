package com.mygdx.wargame.rules.facade;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.wargame.battle.map.BattleMap;
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

    private MissileHitChanceCalculator missileHitChanceCalculator = new MissileHitChanceCalculator();
    private LaserHitChanceCalculator laserHitChanceCalculator = new LaserHitChanceCalculator();
    private BallisticHitChanceCalculator ballisticHitChanceCalculator = new BallisticHitChanceCalculator();
    private FlamerHitChanceCalculator flamerHitChanceCalculator = new FlamerHitChanceCalculator();
    private IonHitChanceCalculator ionHitChanceCalculator = new IonHitChanceCalculator();
    private PlasmaHitChanceCalculator plasmaHitChanceCalculator = new PlasmaHitChanceCalculator();
    private CriticalHitChanceCalculator criticalHitChanceCalculator = new CriticalHitChanceCalculator();
    private BodyPartDestructionHandler bodyPartDestructionHandler = new BodyPartDestructionHandler();
    private DamageCalculator damageCalculator;
    private EvasionCalculator evasionCalculator = new EvasionCalculator();
    private StabilityCalculator stabilityCalculator = new StabilityCalculator(criticalHitChanceCalculator);

    public AttackFacade(Stage stage, SpriteBatch spriteBatch, AssetManager assetManager) {
        damageCalculator = new DamageCalculator(criticalHitChanceCalculator, bodyPartDestructionHandler, stage, spriteBatch, assetManager);
    }

    public void attack(Pilot attackingPilot, Mech attackingMech, Pilot defendingPilot, Mech defendingMech, BattleMap battleMap, BodyPart bodyPart) {

        attackingMech.getSelectedWeapons().forEach(weapon -> {

            if (MathUtils.getDistance(attackingMech.getX(), attackingMech.getY(), defendingMech.getX(), defendingMech.getY()) <= weapon.getRange()) {

                int chance = 0;

                switch (weapon.getType()) {
                    case Ballistic:
                        chance = ballisticHitChanceCalculator.calculate(attackingPilot, attackingMech, defendingMech, weapon);
                        break;
                    case Missile:
                        chance = missileHitChanceCalculator.calculate(attackingPilot, attackingMech, defendingMech, weapon);
                        break;
                    case Laser:
                        chance = laserHitChanceCalculator.calculate(attackingPilot, attackingMech, defendingMech, weapon);
                        break;
                    case Plasma:
                        chance = plasmaHitChanceCalculator.calculate(attackingPilot, attackingMech, defendingMech, weapon);
                        break;
                    case Ion:
                        chance = ionHitChanceCalculator.calculate(attackingPilot, attackingMech, defendingMech, weapon);
                        break;
                    case Flamer:
                        chance = flamerHitChanceCalculator.calculate(attackingPilot, attackingMech, defendingMech, weapon);
                        break;
                }

                if (new Random().nextInt(100) < chance - evasionCalculator.calculate(attackingPilot, attackingMech, defendingPilot, battleMap)) {
                    // hit!
                    damageCalculator.calculate(attackingPilot, attackingMech, defendingPilot, defendingMech, weapon, bodyPart);
                }

                attackingMech.setHeatLevel(attackingMech.getHeatLevel() + weapon.getHeat());

                int stabilityAfterHit = stabilityCalculator.calculate(attackingPilot, attackingMech, defendingPilot, defendingMech, battleMap, weapon);

                defendingMech.setStability(stabilityAfterHit);
            }
        });

        attackingMech.setAttacked(true);
        attackingMech.setMoved(true);

        if (defendingMech.getHp(BodyPart.Torso) <= 0 || defendingMech.getHp(BodyPart.Head) <= 0) {
            defendingMech.setActive(false);
        }
    }

}
