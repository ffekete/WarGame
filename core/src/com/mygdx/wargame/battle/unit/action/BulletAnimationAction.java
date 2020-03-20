package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.mygdx.wargame.battle.bullet.*;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.render.IsometricTiledMapRendererWithSprites;
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

public class
BulletAnimationAction extends Action {

    private Mech attackerMech;
    private Mech defenderMech;
    private AssetManager assetManager;
    private ActionLock actionLock;
    private boolean done = false;
    private int minRange;
    private StageElementsStorage stageElementsStorage;
    private IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites;

    public BulletAnimationAction(Mech attackerMech, Mech defenderMech, AssetManager assetManager, ActionLock actionLock, int minRange, StageElementsStorage stageElementsStorage, IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites) {
        this.attackerMech = attackerMech;
        this.defenderMech = defenderMech;
        this.assetManager = assetManager;
        this.actionLock = actionLock;
        this.minRange = minRange;
        this.stageElementsStorage = stageElementsStorage;
        this.isometricTiledMapRendererWithSprites = isometricTiledMapRendererWithSprites;
    }

    @Override
    public boolean act(float delta) {

        if (done)
            return true;

        if (MathUtils.getDistance(attackerMech.getX(), attackerMech.getY(), defenderMech.getX(), defenderMech.getY()) <= minRange) {
            startBullet(attackerMech);
        } else {
            stageElementsStorage.groundLevel.addAction(new UnlockAction(actionLock, attackerMech.getName() + " " + MathUtils.getDistance(attackerMech.getX(), attackerMech.getY(), defenderMech.getX(), defenderMech.getY()) + " out of range (" + minRange + ")"));
        }
        done = true;

        return true;
    }

    private void startBullet(Mech mech) {

        List<Weapon> selectedWeapons = new ArrayList<>(mech.getSelectedWeapons());
        boolean finishedByExplosion = false;
        int delay = -1;

        for (int i = 0; i < selectedWeapons.size(); i++) {

            Weapon weapon = selectedWeapons.get(i);

            for (int j = 0; j < weapon.getDamageMultiplier(); j++) {
                delay++;
                DelayAction delayAction = new DelayAction(0.25f * delay);

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

                } else {
                    bullet = new CannonBullet(assetManager);
                }

                MoveToAction moveToAction = null;
                MoveActorByBezierLine moveActorByBezierLine = null;
                Vector2 start = new Vector2(attackerMech.getX(), attackerMech.getY());
                Vector2 end = new Vector2(defenderMech.getX(), defenderMech.getY());
                float length = (float) MathUtils.getDistance(attackerMech.getX(), attackerMech.getY(), defenderMech.getX(), defenderMech.getY());

                if (weapon.getType() == WeaponType.Flamer) {
                    moveActorByBezierLine = new MoveActorByBezierLine(start.x, start.y, end.x, end.y, true);
                    moveActorByBezierLine.setDuration(length * 0.3f);
                } else if (weapon.getType() == WeaponType.Missile) {
                    moveActorByBezierLine = new MoveActorByBezierLine(start.x, start.y, end.x, end.y, true);
                    moveActorByBezierLine.setDuration(length * 0.3f);
                } else {
                    bullet.setPosition(start.x, start.y);
                    moveToAction = new MoveToAction();
                    moveToAction.setPosition(end.x, end.y);
                    moveToAction.setDuration(length * 0.1f);
                }

                RotateToAction rotateToAction = new RotateToAction();
                rotateToAction.setRotation(MathUtils.getAngle(new double[]{start.x, start.y}, new double[]{end.x, end.y}));

                SequenceAction sequenceAction = new SequenceAction();
                sequenceAction.addAction(new LockAction(actionLock));
                VisibleAction hideAction = new VisibleAction();
                hideAction.setVisible(false);
                sequenceAction.addAction(hideAction);

                if (weapon.getType() != WeaponType.Missile && weapon.getType() != WeaponType.Flamer)
                    sequenceAction.addAction(rotateToAction);

                sequenceAction.addAction(delayAction);

                sequenceAction.addAction(new AddActorAction(isometricTiledMapRendererWithSprites, bullet));

                if (weapon.getType() == WeaponType.Missile || weapon.getType() == WeaponType.Flamer)
                    sequenceAction.addAction(moveActorByBezierLine);
                else
                    sequenceAction.addAction(moveToAction);

                if (weapon.getType() == WeaponType.Missile) {
                    MissileExplosion explosion = new MissileExplosion(assetManager);
                    explosion.setPosition(defenderMech.getX(), defenderMech.getY());
                    SequenceAction explosionAction = new SequenceAction();
                    explosionAction.addAction(new DelayAction(0.25f * delay + 0.3f * length));
                    explosionAction.addAction(new AddActorAction(isometricTiledMapRendererWithSprites, explosion));

                    ParallelAction waitAndShake = new ParallelAction();
                    explosionAction.addAction(waitAndShake);

                    explosionAction.addAction(new DelayAction(0.8f));

                    explosionAction.addAction(new RemoveCustomActorAction(isometricTiledMapRendererWithSprites, explosion, null));

                    if (i == selectedWeapons.size() - 1 && j == weapon.getDamageMultiplier() - 1) {
                        explosionAction.addAction(new UnlockAction(actionLock, "Explosion"));
                        finishedByExplosion = true;
                    }

                    stageElementsStorage.stage.addAction(explosionAction);
                }

                sequenceAction.addAction(new RemoveCustomActorAction(isometricTiledMapRendererWithSprites, bullet, null));

                if (i == selectedWeapons.size() - 1 && j == weapon.getDamageMultiplier() - 1 && !finishedByExplosion) {
                    sequenceAction.addAction(new UnlockAction(actionLock, "Explosion"));
                }
                sequenceAction.addAction(new RemoveActorAction());

                bullet.addAction(sequenceAction);
                stageElementsStorage.stage.addActor(bullet);
            }
        }
    }
}
