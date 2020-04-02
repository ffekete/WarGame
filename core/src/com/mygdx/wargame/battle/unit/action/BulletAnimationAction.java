package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.mygdx.wargame.battle.bullet.*;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.action.DestroyTileAction;
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
import java.util.Random;

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
    private BattleMap battleMap;
    private SequenceAction masterSequenceAction;

    public BulletAnimationAction(Mech attackerMech, Mech defenderMech, AssetManager assetManager, ActionLock actionLock, int minRange, StageElementsStorage stageElementsStorage, IsometricTiledMapRendererWithSprites isometricTiledMapRendererWithSprites, BattleMap battleMap, SequenceAction masterSequenceAction) {
        this.attackerMech = attackerMech;
        this.defenderMech = defenderMech;
        this.assetManager = assetManager;
        this.actionLock = actionLock;
        this.minRange = minRange;
        this.stageElementsStorage = stageElementsStorage;
        this.isometricTiledMapRendererWithSprites = isometricTiledMapRendererWithSprites;
        this.battleMap = battleMap;
        this.masterSequenceAction = masterSequenceAction;
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

        ParallelAction parallelAction = new ParallelAction();
        ParallelAction outerSequenceAction = new SequenceAction();
        outerSequenceAction.addAction(parallelAction);

        for (int i = 0; i < selectedWeapons.size(); i++) {

            Weapon weapon = selectedWeapons.get(i);

            for (int j = 0; j < weapon.getDamageMultiplier(); j++) {

                SequenceAction sequenceAction = new SequenceAction();

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
                    bullet.setRotation(85);

                } else {
                    bullet = new CannonBullet(assetManager);
                }

                MoveToAction moveToAction = null;
                MoveActorByBezierLine moveActorByBezierLine = null;
                Vector2 start = new Vector2(attackerMech.getX(), attackerMech.getY());
                Vector2 end = new Vector2(defenderMech.getX(), defenderMech.getY());

                bullet.setPosition(start.x, start.y);

                float length = (float) MathUtils.getDistance(attackerMech.getX(), attackerMech.getY(), defenderMech.getX(), defenderMech.getY());

                if (weapon.getType() == WeaponType.Flamer) {
                    moveActorByBezierLine = new MoveActorByBezierLine(start.x, start.y, end.x, end.y, 5, -5, false);
                    moveActorByBezierLine.setDuration(length * 0.5f);
                    moveActorByBezierLine.setTarget(bullet);
                } else if (weapon.getType() == WeaponType.Missile) {
                    moveActorByBezierLine = new MoveActorByBezierLine(start.x, start.y, end.x, end.y, true);
                    moveActorByBezierLine.setDuration(length * 0.3f);
                    moveActorByBezierLine.setTarget(bullet);
                } else {
                    bullet.setPosition(start.x, start.y);
                    moveToAction = new MoveToAction();
                    moveToAction.setPosition(end.x, end.y);
                    moveToAction.setDuration(length * 0.1f);
                    moveToAction.setTarget(bullet);
                }

                RotateToAction rotateToAction = new RotateToAction();
                rotateToAction.setRotation(MathUtils.getAngle(new double[]{start.x, start.y}, new double[]{end.x, end.y}));
                rotateToAction.setTarget(bullet);

                //SequenceAction sequenceAction = new SequenceAction();
                sequenceAction.addAction(new LockAction(actionLock));
                VisibleAction hideAction = new VisibleAction();
                hideAction.setVisible(false);
                hideAction.setTarget(bullet);
                sequenceAction.addAction(hideAction);

                if (weapon.getType() != WeaponType.Missile && weapon.getType() != WeaponType.Flamer)
                    sequenceAction.addAction(rotateToAction);

                sequenceAction.addAction(delayAction);

                sequenceAction.addAction(new AddActorAction(isometricTiledMapRendererWithSprites, bullet));

                if (weapon.getType() == WeaponType.Missile || weapon.getType() == WeaponType.Flamer)
                    sequenceAction.addAction(moveActorByBezierLine);
                else
                    sequenceAction.addAction(moveToAction);

                sequenceAction.addAction(new RemoveCustomActorAction(isometricTiledMapRendererWithSprites, bullet, null));

                if (weapon.getType() == WeaponType.Missile) {
                    MissileExplosion explosion = new MissileExplosion(assetManager);
                    explosion.setPosition(defenderMech.getX(), defenderMech.getY());
                    SequenceAction explosionAction = new SequenceAction();
                    explosionAction.addAction(new AddActorAction(isometricTiledMapRendererWithSprites, explosion));
                    explosionAction.addAction(new DelayAction(0.8f));

                    explosionAction.addAction(new RemoveCustomActorAction(isometricTiledMapRendererWithSprites, explosion, null));

                    explosionAction.addAction(new DestroyTileAction(battleMap, (int) end.x, (int) end.y, assetManager));

                    sequenceAction.addAction(explosionAction);
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

                hitEffect.setPosition(end.x, end.y);
                hitEffect.setRotation(new Random().nextInt(360));
                sequenceAction.addAction(new AddActorAction(isometricTiledMapRendererWithSprites, hitEffect));
                sequenceAction.addAction(new DelayAction(0.25f));
                sequenceAction.addAction(new RemoveCustomActorAction(isometricTiledMapRendererWithSprites, hitEffect, null));


                parallelAction.addAction(sequenceAction);
            }
        }

        stageElementsStorage.stage.addAction(outerSequenceAction);
    }
}
