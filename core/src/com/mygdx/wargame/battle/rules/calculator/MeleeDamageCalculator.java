package com.mygdx.wargame.battle.rules.calculator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.bullet.Explosion;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.render.IsometricTiledMapRendererWithSprites;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.unit.action.AddActorAction;
import com.mygdx.wargame.battle.unit.action.RemoveCustomActorAction;
import com.mygdx.wargame.common.component.armor.Armor;
import com.mygdx.wargame.common.component.shield.Shield;
import com.mygdx.wargame.common.component.weapon.Status;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Perks;
import com.mygdx.wargame.common.pilot.Pilot;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class MeleeDamageCalculator {

    private BodyPartDestructionHandler bodyPartDestructionHandler;
    private FlankingCalculator flankingCalculator;

    public MeleeDamageCalculator(BodyPartDestructionHandler bodyPartDestructionHandler) {
        this.bodyPartDestructionHandler = bodyPartDestructionHandler;
        this.flankingCalculator = new FlankingCalculator();
    }

    public void calculate(Pilot attackingPilot, Mech attackingMech, Pilot targetPilot, Mech targetMech, BodyPart targetedBodyPart) {
        BodyPart bodyPart;

        // calculate critical
        boolean critical = new Random().nextInt(100) < 5;

        // select body part to damage
        if (targetedBodyPart == null) {
            bodyPart = BodyPart.values()[new Random().nextInt(BodyPart.values().length)];
        } else {
            bodyPart = targetedBodyPart;
        }


        boolean isFlanked = flankingCalculator.isFlankedFromPosition(attackingMech.getX(), attackingMech.getY(), targetMech);

        // get armor damage
        int armorValue = targetMech.getComponents(bodyPart).stream()
                .filter(c -> c.getStatus() != Status.Destroyed)
                .filter(c -> Armor.class.isAssignableFrom(c.getClass()))
                .map(s -> ((Armor) s).getHitPoint())
                .reduce((a, b) -> a + b)
                .orElse(0);
        if (armorValue > 0) {
            reduceArmorValue(targetPilot, targetMech, attackingMech.getMeleeDamage() * (critical ? 2 : 1) * (int) (isFlanked ? 1.2f : 1f), bodyPart);
        } else {
            // get hp damage

            int damage = attackingMech.getMeleeDamage() * (critical ? 2 : 1) * (int) (isFlanked ? 1.2f : 1f);

            if(attackingPilot.hasPerk(Perks.Brawler)) {
                damage++;
            }

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
