package com.mygdx.wargame.battle.rules.facade;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.BattleMapStore;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.battle.rules.calculator.hitchance.BallisticHitChanceCalculator;
import com.mygdx.wargame.battle.rules.calculator.hitchance.FlamerHitChanceCalculator;
import com.mygdx.wargame.battle.rules.calculator.hitchance.IonHitChanceCalculator;
import com.mygdx.wargame.battle.rules.calculator.hitchance.LaserHitChanceCalculator;
import com.mygdx.wargame.battle.rules.calculator.hitchance.MissileHitChanceCalculator;
import com.mygdx.wargame.battle.rules.calculator.hitchance.PlasmaHitChanceCalculator;

public class HitChanceCalculatorFacade {

    private MissileHitChanceCalculator missileHitChanceCalculator = new MissileHitChanceCalculator();
    private LaserHitChanceCalculator laserHitChanceCalculator = new LaserHitChanceCalculator();
    private BallisticHitChanceCalculator ballisticHitChanceCalculator = new BallisticHitChanceCalculator();
    private FlamerHitChanceCalculator flamerHitChanceCalculator = new FlamerHitChanceCalculator();
    private IonHitChanceCalculator ionHitChanceCalculator = new IonHitChanceCalculator();
    private PlasmaHitChanceCalculator plasmaHitChanceCalculator = new PlasmaHitChanceCalculator();

    public int getHitChance(Weapon weapon, Pilot attackingPilot, Mech attackingMech, Mech defendingMech, BodyPart targetBodyPart, BattleMap battleMap) {

        int modifier = 0;
        if (targetBodyPart != null) {
            switch (targetBodyPart) {
                case LeftLeg:
                case RightLeg:
                case LeftArm:
                case RightArm:
                    modifier = -10;
                    break;
                case Torso:
                    modifier = 0;
                    break;
                case Head:
                    modifier = -20;
            }
        }

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

        int groundModifier = battleMap.getTile(defendingMech.getX(), defendingMech.getY()).getHitChanceModifierForAttackers();
        groundModifier += battleMap.getTile(attackingMech.getX(), attackingMech.getY()).getHitChanceModifierForDefenders();

        return chance + modifier + (defendingMech.canFly() ? groundModifier : 0);
    }

}
