package com.mygdx.wargame.battle.rules.calculator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.bullet.Explosion;
import com.mygdx.wargame.battle.map.render.IsometricTiledMapRendererWithSprites;
import com.mygdx.wargame.battle.rules.facade.GameState;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.HudElementsFacade;
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

public class DamageCalculator {

    private CriticalHitChanceCalculator criticalHitChanceCalculator;
    private BodyPartDestructionHandler bodyPartDestructionHandler;
    private AssetManager assetManager;
    private FlankingCalculator flankingCalculator;
    private IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites;

    public DamageCalculator(CriticalHitChanceCalculator criticalHitChanceCalculator, BodyPartDestructionHandler bodyPartDestructionHandler, AssetManager assetManager, IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites) {
        this.criticalHitChanceCalculator = criticalHitChanceCalculator;
        this.bodyPartDestructionHandler = bodyPartDestructionHandler;
        this.assetManager = assetManager;
        this.isometricTiledMapRendererWithSprites = isometricTiledMapRendererWithSprites;
        this.flankingCalculator = new FlankingCalculator();
    }

    public void calculate(Pilot attackingPilot, Mech attackingMech, Pilot targetPilot, Mech targetMech, Weapon weapon, BodyPart targetedBodyPart) {
        BodyPart bodyPart;

            // calculate critical
            boolean critical = new Random().nextInt(100) < weapon.getCriticalChance() + criticalHitChanceCalculator.calculate(attackingPilot, attackingMech, weapon) ? true : false;

            // select body part to damage
            if (targetedBodyPart == null) {
                bodyPart = (BodyPart) targetMech.getDefinedBodyParts().keySet().stream().filter(bodyPart1 -> bodyPart1 != null && targetMech.getHp(bodyPart1) > 0).toArray()[new Random().nextInt((int)targetMech.getDefinedBodyParts().keySet().stream().filter(bodyPart1 -> bodyPart1 != null && targetMech.getHp(bodyPart1) > 0).count())];
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

            boolean isFlanked = flankingCalculator.isFlankedFromPosition(attackingMech.getX(), attackingMech.getY(), targetMech);

            if (shieldedValue > 0) {
                reduceShieldValue(targetPilot, targetMech, weapon.getShieldDamage() * (critical ? 2 : 1) * (int) (isFlanked ? 1.2f : 1f));
            } else {

                // add heat
                targetMech.setHeatLevel(targetMech.getHeatLevel() + weapon.getAdditionalHeatToEnemy() * (critical ? 2 : 1) * (int) (isFlanked ? 1.2f : 1f));

                // get armor damage
                int armorValue = targetMech.getComponents(bodyPart).stream()
                        .filter(c -> c.getStatus() != Status.Destroyed)
                        .filter(c -> Armor.class.isAssignableFrom(c.getClass()))
                        .map(s -> ((Armor) s).getHitPoint())
                        .reduce((a, b) -> a + b)
                        .orElse(0);
                if (armorValue > 0) {
                    addExplosion(targetMech);

                    reduceArmorValue(targetPilot, targetMech, weapon.getArmorDamage() * (critical ? 2 : 1) * (int) (isFlanked ? 1.2f : 1f), bodyPart);
                } else {
                    // get hp damage
                    addExplosion(targetMech);

                    int damage = weapon.getBodyDamage() * (critical ? 2 : 1) * (int) (isFlanked ? 1.2f : 1f);

                    if (targetPilot.hasPerk(Perks.Robust)) {
                        double reduction = Math.ceil(damage * 0.05f);
                        damage -= (int) reduction;
                    }

                    targetMech.setHp(bodyPart, targetMech.getHp(bodyPart) - damage);

                    HudElementsFacade.createDamageIndicatorFloatingLabelFromString(String.format("%s", damage), String.format(" %s", targetMech.getDefinedBodyParts().get(bodyPart)), targetMech.getX(), targetMech.getY(), GameState.fireSingleWeaponAction);

                    // destroy body part and all of its components
                    if (targetMech.getHp(bodyPart) <= 0) {
                        bodyPartDestructionHandler.destroy(targetMech, bodyPart);
                    }
                }
            }
    }

    private void addExplosion(Mech target) {
        SequenceAction sequenceAction = new SequenceAction();

        Explosion explosion = new Explosion(assetManager);
        explosion.setPosition(target.getX(), target.getY());
        sequenceAction.addAction(new AddActorAction(isometricTiledMapRendererWithSprites, explosion));
        sequenceAction.addAction(new DelayAction(1f));
        sequenceAction.addAction(new RemoveCustomActorAction(isometricTiledMapRendererWithSprites, explosion, null));

        StageElementsStorage.airLevel.addAction(sequenceAction);
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

                HudElementsFacade.createDamageIndicatorFloatingLabelFromString("Shield damage: ", Integer.toString(damage), mech.getX(), mech.getY(), GameState.fireSingleWeaponAction);
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
                HudElementsFacade.createDamageIndicatorFloatingLabelFromString("Armor damage: ", Integer.toString(damage), mech.getX(), mech.getY(), GameState.fireSingleWeaponAction);

                if (armor.getHitPoint() <= 0)
                    armor.setStatus(Status.Destroyed);
            }
        }
    }

}
