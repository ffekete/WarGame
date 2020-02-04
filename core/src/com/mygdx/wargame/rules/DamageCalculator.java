package com.mygdx.wargame.rules;

import com.mygdx.wargame.component.armor.Armor;
import com.mygdx.wargame.component.shield.Shield;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class DamageCalculator {

    public void calculate(Pilot targetPilot, Mech targetMech, Weapon weapon, BodyPart targetedBodyPart) {
        BodyPart bodyPart;

        for (int i = 0; i < weapon.getDamageMultiplier(); i++) {
            // select body part to damage
            if (targetedBodyPart == null) {
                bodyPart = BodyPart.values()[new Random().nextInt(BodyPart.values().length)];
            } else {
                bodyPart = targetedBodyPart;
            }

            // get shield sum damage
            int shieldedValue = targetMech.getAllComponents().stream()
                    .filter(c -> Shield.class.isAssignableFrom(c.getClass()))
                    .map(s -> ((Shield) s).getShieldValue())
                    .reduce((a, b) -> a + b)
                    .orElse(0);

            if (shieldedValue > 0) {
                reduceShieldValue(targetPilot, targetMech, weapon.getShieldDamage());
            } else {
                // get armor damage
                int armorValue = targetMech.getComponents(bodyPart).stream()
                        .filter(c -> Armor.class.isAssignableFrom(c.getClass()))
                        .map(s -> ((Armor) s).getHitPoint())
                        .reduce((a, b) -> a + b)
                        .orElse(0);
                if (armorValue > 0)
                    reduceArmorValue(targetPilot, targetMech, weapon.getArmorDamage(), bodyPart);
                else {
                    // get hp damage
                    int damage = weapon.getBodyDamage();

                    if(targetPilot.hasPerk(Perks.Robust)) {
                        float reduction = damage * 0.05f;
                        if(reduction < 1)
                            damage -= 1;
                        else {
                            damage -= (int) reduction;
                        }
                    }

                    targetMech.setHp(bodyPart, targetMech.getHp(bodyPart) - damage);
                }
            }
        }

    }

    private void reduceShieldValue(Pilot pilot, Mech mech, int shieldDamage) {
        int maxDamage = shieldDamage;

        if(pilot.hasPerk(Perks.Robust)) {
            float reduction = maxDamage * 0.05f;
            if(reduction < 1)
                maxDamage -= 1;
            else {
                maxDamage -= (int) reduction;
            }
        }

        Set<Shield> shields = mech.getAllComponents().stream().filter(c -> Shield.class.isAssignableFrom(c.getClass())).map(c -> (Shield) c).collect(Collectors.toSet());

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

        if(pilot.hasPerk(Perks.Robust)) {
            float reduction = maxDamage * 0.05f;
            if(reduction < 1)
                maxDamage -= 1;
            else {
                maxDamage -= (int) reduction;
            }
        }

        Set<Armor> armors = mech.getComponents(bodyPart).stream().filter(c -> Armor.class.isAssignableFrom(c.getClass())).map(c -> (Armor) c).collect(Collectors.toSet());

        for (Armor armor : armors) {
            if (armor.getHitPoint() > 0) {

                if (maxDamage <= 0)
                    return;

                int damage = Math.min(armor.getHitPoint(), maxDamage);
                maxDamage -= damage;
                armor.reduceHitPoint(damage);
            }
        }
    }

}
