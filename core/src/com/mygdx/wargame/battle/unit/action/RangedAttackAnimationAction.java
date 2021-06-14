package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.mygdx.wargame.battle.bullet.*;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.action.DestroyTileAction;
import com.mygdx.wargame.battle.map.render.IsometricTiledMapRendererWithSprites;
import com.mygdx.wargame.battle.rules.facade.GameState;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.component.weapon.WeaponType;
import com.mygdx.wargame.common.component.weapon.ballistic.MachineGun;
import com.mygdx.wargame.common.component.weapon.ballistic.MachineGunMk2;
import com.mygdx.wargame.common.component.weapon.ballistic.MachineGunMk3;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.util.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mygdx.wargame.battle.rules.facade.Facades.attackFacade;
import static com.mygdx.wargame.battle.rules.facade.Facades.hitChanceCalculatorFacade;

public class
RangedAttackAnimationAction extends Action {

    private Mech attackerMech;
    private Mech defenderMech;
    private AssetManager assetManager;
    private ActionLock actionLock;
    private boolean done = false;
    private int minRange;
    private IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites;
    private BattleMap battleMap;

    public RangedAttackAnimationAction(Mech attackerMech, Mech defenderMech, AssetManager assetManager, int minRange, IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites, BattleMap battleMap) {
        this.attackerMech = attackerMech;
        this.defenderMech = defenderMech;
        this.assetManager = assetManager;
        this.actionLock = GameState.actionLock;
        this.minRange = minRange;
        this.isometricTiledMapRendererWithSprites = isometricTiledMapRendererWithSprites;
        this.battleMap = battleMap;
    }

    @Override
    public boolean act(float delta) {

        if (done)
            return true;

        if (MathUtils.getDistance(attackerMech.getX(), attackerMech.getY(), defenderMech.getX(), defenderMech.getY()) <= minRange) {
            startBullet(attackerMech);
        }
        done = true;

        return true;
    }

    private void startBullet(Mech mech) {

        List<Weapon> selectedWeapons = new ArrayList<>(mech.getSelectedWeapons());
        int delay = -1;

        Vector2 start = new Vector2(attackerMech.getX(), attackerMech.getY());
        Vector2 end = new Vector2(defenderMech.getX(), defenderMech.getY());

        boolean tileDamageInflicted = false;

        ParallelAction parallelAction = new ParallelAction();
        ParallelAction outerSequenceAction = new SequenceAction();
        outerSequenceAction.addAction(parallelAction);

        boolean flipBezierControlPoint = false;

        GameState.messageDelayDuringSingleAttack = 0f;

        for (int i = 0; i < selectedWeapons.size(); i++) {

            Weapon weapon = selectedWeapons.get(i);

            attackFacade.checkHeatDamage(weapon);

            // calculate hit chance
            int chance = hitChanceCalculatorFacade.getHitChance(weapon, GameState.selectedPilot, GameState.selectedMech, GameState.target.get().getMech(), null, battleMap);

            for (int j = 0; j < weapon.getDamageMultiplier(); j++) {

                if (weapon.getAmmo().isPresent() && weapon.getAmmo().get() == 0) {
                    // no ammo, skip
                    continue;
                }

                // reduce ammo of weapon
                weapon.reduceAmmo();

                boolean weaponDidHit = false;

                if (new Random().nextInt(100) < chance - attackFacade.getEvasionCalculator().calculate(GameState.selectedPilot, GameState.selectedMech, GameState.target.get().getPilot(), battleMap)) {
                    weaponDidHit = true;
                }

                float ex;
                float ey;

                if (weaponDidHit) {
                    ex = end.x + new Random().nextFloat() / 5f;
                    ey = end.y + new Random().nextFloat() / 5f;
                } else {
                    ex = end.x + (new Random().nextBoolean() ? -0.5f : 0.5f);
                    ey = end.y + (new Random().nextBoolean() ? -0.5f : 0.5f);
                }

                SequenceAction selectedWeaponFiringAction = new SequenceAction();
                GameState.fireSingleWeaponAction = selectedWeaponFiringAction; // save this to use it in DamageCalculator

                delay++;
                DelayAction delayAction = new DelayAction(0.05f * delay);

                AbstractBullet bullet;
                if (weapon.getType() == WeaponType.Plasma)
                    bullet = new PlasmaBullet(assetManager);
                else if (weapon.getType() == WeaponType.Ballistic) {
                    if (MachineGun.class.isAssignableFrom(weapon.getClass()) || MachineGunMk2.class.isAssignableFrom(weapon.getClass()) || MachineGunMk3.class.isAssignableFrom(weapon.getClass())) {
                        bullet = new MachineGunBullet(assetManager);
                    } else {
                        bullet = new CannonBullet(assetManager);
                    }
                } else if (weapon.getType() == WeaponType.Missile) {
                    bullet = new MissileBullet(assetManager);
                } else if (weapon.getType() == WeaponType.Laser) {
                    bullet = new LaserBullet(assetManager);
                } else if (weapon.getType() == WeaponType.Ion) {
                    bullet = new IonBullet(assetManager);
                } else if (weapon.getType() == WeaponType.Flamer) {
                    bullet = new FlameBullet(assetManager);
                    bullet.setRotation(85);

                } else {
                    bullet = new CannonBullet(assetManager);
                }

                MoveToAction moveToAction = null;
                MoveActorByBezierLine moveActorByBezierLine = null;

                bullet.setPosition(start.x, start.y);

                float length = (float) MathUtils.getDistance(attackerMech.getX(), attackerMech.getY(), defenderMech.getX(), defenderMech.getY());

                if (weapon.getType() == WeaponType.Flamer) {
                    float angle = start.angle(end);

                    moveActorByBezierLine = new MoveActorByBezierLine(start.x, start.y, ex, ey, 5, -5, angle);
                    moveActorByBezierLine.setDuration(length * 0.5f);
                    moveActorByBezierLine.setTarget(bullet);

                } else if (weapon.getType() == WeaponType.Missile) {

                    float angle = MathUtils.getAngle(start, end);

                    float xOffset = flipBezierControlPoint ? -(length / 4f) : (length / 4f);
                    float yOffset = flipBezierControlPoint ? -(length / 8f) : (length / 8f);

                    moveActorByBezierLine = new MoveActorByBezierLine(start.x, start.y, ex, ey, xOffset, yOffset + new Random().nextInt(2), angle);
                    moveActorByBezierLine.setDuration(length * 0.33f);
                    moveActorByBezierLine.setTarget(bullet);

                } else {
                    bullet.setPosition(start.x, start.y);
                    moveToAction = new MoveToAction();
                    moveToAction.setPosition(ex, ey);

                    moveToAction.setDuration(length * 0.1f);
                    moveToAction.setTarget(bullet);
                }

                RotateToAction rotateToAction = new RotateToAction();
                rotateToAction.setRotation(MathUtils.getAngle(new double[]{start.x, start.y}, new double[]{ex, ey}));
                rotateToAction.setTarget(bullet);

                //SequenceAction sequenceAction = new SequenceAction();
                selectedWeaponFiringAction.addAction(new LockAction());
                VisibleAction hideAction = new VisibleAction();
                hideAction.setVisible(false);
                hideAction.setTarget(bullet);
                selectedWeaponFiringAction.addAction(hideAction);

                if (weapon.getType() != WeaponType.Missile && weapon.getType() != WeaponType.Flamer)
                    selectedWeaponFiringAction.addAction(rotateToAction);

                selectedWeaponFiringAction.addAction(delayAction);

                selectedWeaponFiringAction.addAction(new AddActorAction(isometricTiledMapRendererWithSprites, bullet));

                if (weapon.getType() == WeaponType.Missile || weapon.getType() == WeaponType.Flamer)
                    selectedWeaponFiringAction.addAction(moveActorByBezierLine);
                else
                    selectedWeaponFiringAction.addAction(moveToAction);

                selectedWeaponFiringAction.addAction(new RemoveCustomActorAction(isometricTiledMapRendererWithSprites, bullet, null));

                if (weaponDidHit) {
                    selectedWeaponFiringAction.addAction(new MechHitAction(weapon, null));
                }

                selectedWeaponFiringAction.addAction(new SetHeatLevelAction(GameState.selectedMech, weapon.getHeat()));

                if (weapon.getType() == WeaponType.Missile) {
                    MissileExplosion explosion = new MissileExplosion(assetManager);
                    explosion.setPosition(ex, ey);
                    SequenceAction explosionAction = new SequenceAction();
                    explosionAction.addAction(new AddActorAction(isometricTiledMapRendererWithSprites, explosion));
                    explosionAction.addAction(new DelayAction(0.8f));

                    explosionAction.addAction(new RemoveCustomActorAction(isometricTiledMapRendererWithSprites, explosion, null));

                    tileDamageInflicted = true;

                    selectedWeaponFiringAction.addAction(explosionAction);
                }

                Actor hitEffect = null;
                switch (weapon.getType()) {
                    case Ballistic:
                        hitEffect = new CannonHitEffect(assetManager);
                        break;
                    case Missile:
                        hitEffect = new MissileHitEffect(assetManager);
                        break;
                    case Laser:
                        hitEffect = new LaserHitEffect(assetManager);
                        break;
                    case Plasma:
                        hitEffect = new PlasmaHitEffect(assetManager);
                        break;
                    case Ion:
                        hitEffect = new IonHitEffect(assetManager);
                        break;
                    case Flamer:
                        hitEffect = new FlamerHitEffect(assetManager);
                        break;
                }

                hitEffect.setPosition(ex, ey);
                hitEffect.setRotation(new Random().nextInt(360));
                selectedWeaponFiringAction.addAction(new AddActorAction(isometricTiledMapRendererWithSprites, hitEffect));
                selectedWeaponFiringAction.addAction(new DelayAction(0.25f));
                selectedWeaponFiringAction.addAction(new RemoveCustomActorAction(isometricTiledMapRendererWithSprites, hitEffect, null));


                parallelAction.addAction(selectedWeaponFiringAction);

            }
            delay += 5;
            flipBezierControlPoint = !flipBezierControlPoint;
        }

        if (tileDamageInflicted)
            outerSequenceAction.addAction(new DestroyTileAction(battleMap, (int) end.x, (int) end.y, assetManager));

        StageElementsStorage.stage.addAction(outerSequenceAction);
    }
}
