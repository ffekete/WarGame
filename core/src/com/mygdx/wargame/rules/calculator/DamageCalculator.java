package com.mygdx.wargame.rules.calculator;

import com.mygdx.wargame.component.armor.Armor;
import com.mygdx.wargame.component.shield.Shield;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class DamageCalculator {

    private CriticalHitChanceCalculator criticalHitChanceCalculator;
    private BodyPartDestructionHandler bodyPartDestructionHandler;

    public DamageCalculator(CriticalHitChanceCalculator criticalHitChanceCalculator, BodyPartDestructionHandler bodyPartDestructionHandler) {
        this.criticalHitChanceCalculator = criticalHitChanceCalculator;
        this.bodyPartDestructionHandler = bodyPartDestructionHandler;
    }

    public void calculate(Pilot attackingPilot, Mech attackingMech, Pilot targetPilot, Mech targetMech, Weapon weapon, BodyPart targetedBodyPart) {
        BodyPart bodyPart;

        for (int i = 0; i < weapon.getDamageMultiplier(); i++) {

            // calculate critical
            boolean critical = new Random().nextInt(100) < weapon.getCriticalChance() + criticalHitChanceCalculator.calculate(attackingPilot, attackingMech, weapon) ? true : false;

            // select body part to damage
            if (targetedBodyPart == null) {
                bodyPart = BodyPart.values()[new Random().nextInt(BodyPart.values().length)];
            } else {
                bodyPart = targetedBodyPart;
            }

            // get shield sum damage
            int shieldedValue = targetMech.getAllComponents().stream()
                    .filter(c -> c.getStatus() != Status.Destroyed)
                    .filter(c -> Shield.class.isAssignableFrom(c.getClass()))
                    .map(s -> ((Shield) s).getShieldValue())
                    .reduce((a, b) -> a + b)
                    .orElse(0);

            if (shieldedValue > 0) {
                reduceShieldValue(targetPilot, targetMech, weapon.getShieldDamage() * (critical ? 2 : 1));
            } else {

                // add heat
                targetMech.setHeatLevel(targetMech.getHeatLevel() + weapon.getAdditionalHeatToEnemy() * (critical ? 2 : 1));

                // get armor damage
                int armorValue = targetMech.getComponents(bodyPart).stream()
                        .filter(c -> c.getStatus() != Status.Destroyed)
                        .filter(c -> Armor.class.isAssignableFrom(c.getClass()))
                        .map(s -> ((Armor) s).getHitPoint())
                        .reduce((a, b) -> a + b)
                        .orElse(0);
                if (armorValue > 0)
                    reduceArmorValue(targetPilot, targetMech, weapon.getArmorDamage() * (critical ? 2 : 1), bodyPart);
                else {
                    // get hp damage
                    int damage = weapon.getBodyDamage() * (critical ? 2 : 1);

                    if (targetPilot.hasPerk(Perks.Robust)) {
                        double reduction = Math.ceil(damage * 0.05f);
                        damage -= (int) reduction;
                    }

                    targetMech.setHp(bodyPart, targetMech.getHp(bodyPart) - damage);

                    // destroy body part and all of its components
                    if (targetMech.getHp(bodyPart) <= 0) {
                        bodyPartDestructionHandler.destroy(targetMech, bodyPart);
                    }
                }
            }
        }
    }

    private void reduceShieldValue(Pilot pilot, Mech mech, int shieldDamage) {
        int maxDamage = shieldDamage;

        if (pilot.hasPerk(Perks.Robust)) {
            float reduction = maxDamage * 0.05f;
            if (reduction < 1)
                maxDamage -= 1;
            else {
                maxDamage -= (int) reduction;
            }
        }

        Set<Shield> shields = mech.getAllComponents().stream()
                .filter(c -> Shield.class.isAssignableFrom(c.getClass()))
                .filter(c -> c.getStatus() != Status.Destroyed)
                .map(c -> (Shield) c)
                .collect(Collectors.toSet());

        for (Shield s : shields) {
            if (s.getShieldValue() > 0) {

                if (maxDamage <= 0)
                    return;

                int damage = Math.min(s.getShieldValue(), maxDamage);
                maxDamage -= damage;
                s.reduceShieldValue(damage);
            }
        }
    }

    private void reduceArmorValue(Pilot pilot, Mech mech, int shieldDamage, BodyPart bodyPart) {
        int maxDamage = shieldDamage;

        if (pilot.hasPerk(Perks.Robust)) {
            float reduction = maxDamage * 0.05f;
            if (reduction < 1)
                maxDamage -= 1;
            else {
                maxDamage -= (int) reduction;
            }
        }

        Set<Armor> armors = mech.getComponents(bodyPart).stream()
                .filter(c -> Armor.class.isAssignableFrom(c.getClass()))
                .filter(c -> c.getStatus() != Status.Destroyed)
                .map(c -> (Armor) c)
                .collect(Collectors.toSet());

        for (Armor armor : armors) {
            if (armor.getHitPoint() > 0) {

                if (maxDamage <= 0)
                    return;

                int damage = Math.min(armor.getHitPoint(), maxDamage);
                maxDamage -= damage;
                armor.reduceHitPoint(damage);

                if (armor.getHitPoint() <= 0)
                    armor.setStatus(Status.Destroyed);
            }
        }
    }

}
