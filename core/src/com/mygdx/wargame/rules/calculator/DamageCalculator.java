package com.mygdx.wargame.rules.calculator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.action.ShowMessageActor;
import com.mygdx.wargame.battle.bullet.Explosion;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.battle.unit.action.AddActorAction;
import com.mygdx.wargame.battle.unit.action.RemoveCustomActorAction;
import com.mygdx.wargame.battle.unit.action.ShakeAction;
import com.mygdx.wargame.component.armor.Armor;
import com.mygdx.wargame.component.shield.Shield;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.util.MapUtils;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class DamageCalculator {

    private CriticalHitChanceCalculator criticalHitChanceCalculator;
    private BodyPartDestructionHandler bodyPartDestructionHandler;
    private StageElementsStorage stageElementsStorage;
    private AssetManager assetManager;
    private MechInfoPanelFacade mechInfoPanelFacade;
    private ActionLock actionLock;
    private FlankingCalculator flankingCalculator;

    public DamageCalculator(CriticalHitChanceCalculator criticalHitChanceCalculator, BodyPartDestructionHandler bodyPartDestructionHandler, StageElementsStorage stageElementsStorage, AssetManager assetManager, MechInfoPanelFacade mechInfoPanelFacade, ActionLock actionLock) {
        this.criticalHitChanceCalculator = criticalHitChanceCalculator;
        this.bodyPartDestructionHandler = bodyPartDestructionHandler;
        this.stageElementsStorage = stageElementsStorage;
        this.assetManager = assetManager;
        this.mechInfoPanelFacade = mechInfoPanelFacade;
        this.actionLock = actionLock;
        this.flankingCalculator = new FlankingCalculator();
    }

    public void calculate(Pilot attackingPilot, Mech attackingMech, Pilot targetPilot, Mech targetMech, Weapon weapon, BodyPart targetedBodyPart, SequenceAction messageQue) {
        BodyPart bodyPart;

        for (int i = 0; i < weapon.getDamageMultiplier(); i++) {

            // if no ammo, skip
            if(weapon.getAmmo().isPresent() && weapon.getAmmo().get() < 1) {
                continue;
            }

            // reduce ammo of weapon
            weapon.reduceAmmo();

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

            boolean isFlanked = flankingCalculator.isFlankedFromPosition(attackingMech.getX(), attackingMech.getY(), targetMech);

            if (shieldedValue > 0) {
                reduceShieldValue(targetPilot, targetMech, weapon.getShieldDamage() * (critical ? 2 : 1) * (int)(isFlanked ? 1.2f : 1f), messageQue);
            } else {

                // add heat
                targetMech.setHeatLevel(targetMech.getHeatLevel() + weapon.getAdditionalHeatToEnemy() * (critical ? 2 : 1)* (int)(isFlanked ? 1.2f : 1f));

                // get armor damage
                int armorValue = targetMech.getComponents(bodyPart).stream()
                        .filter(c -> c.getStatus() != Status.Destroyed)
                        .filter(c -> Armor.class.isAssignableFrom(c.getClass()))
                        .map(s -> ((Armor) s).getHitPoint())
                        .reduce((a, b) -> a + b)
                        .orElse(0);
                if (armorValue > 0) {
                    addExplosion(targetMech);

                    showMessage(targetMech, "Armor damaged: " + weapon.getArmorDamage() * (critical ? 2 : 1)* (int)(isFlanked ? 1.2f : 1f) + (critical ? " (crit) " : "") + (isFlanked ? " (flanked)" : ""), messageQue);
                    reduceArmorValue(targetPilot, targetMech, weapon.getArmorDamage() * (critical ? 2 : 1)* (int)(isFlanked ? 1.2f : 1f), bodyPart);
                } else {
                    // get hp damage
                    addExplosion(targetMech);

                    int damage = weapon.getBodyDamage() * (critical ? 2 : 1) * (int)(isFlanked ? 1.2f : 1f);

                    if (targetPilot.hasPerk(Perks.Robust)) {
                        double reduction = Math.ceil(damage * 0.05f);
                        damage -= (int) reduction;
                    }

                    showMessage(targetMech, "Body damaged: " + damage + (critical ? " (crit) " : "") + (isFlanked ? " (flanked)" : ""), messageQue);

                    targetMech.setHp(bodyPart, targetMech.getHp(bodyPart) - damage);

                    // destroy body part and all of its components
                    if (targetMech.getHp(bodyPart) <= 0) {
                        showMessage(targetMech, "Destroyed: " + bodyPart, messageQue);
                        bodyPartDestructionHandler.destroy(targetMech, bodyPart);
                    }
                }
            }
        }
        stageElementsStorage.airLevel.addAction(messageQue);
    }

    private void showMessage(Mech targetMech, String message, SequenceAction messageQue) {
        ShowMessageActor showMessageActor = new ShowMessageActor(mechInfoPanelFacade.getLabelStyle(), targetMech.getX(), targetMech.getY(), message, stageElementsStorage, actionLock);
        showMessageActor.setDuration(2f);
        messageQue.addAction(showMessageActor);
    }

    private void addExplosion(Mech target) {
        SequenceAction sequenceAction = new SequenceAction();

        Explosion explosion = new Explosion(assetManager);
        explosion.setPosition(target.getX(), target.getY());
        sequenceAction.addAction(new AddActorAction(stageElementsStorage.airLevel, explosion));
        sequenceAction.addAction(new DelayAction(1f));
        sequenceAction.addAction(new RemoveCustomActorAction(stageElementsStorage.airLevel, explosion));

        stageElementsStorage.airLevel.addAction(sequenceAction);
    }

    private void reduceShieldValue(Pilot pilot, Mech mech, int shieldDamage, SequenceAction messageQue) {
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
                showMessage(mech, "Shield damaged: " + damage, messageQue);
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
