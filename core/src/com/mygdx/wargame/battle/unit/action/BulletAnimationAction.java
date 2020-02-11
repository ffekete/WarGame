package com.mygdx.wargame.battle.unit.action;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.mygdx.wargame.battle.bullet.AbstractBullet;
import com.mygdx.wargame.battle.bullet.CannonBullet;
import com.mygdx.wargame.battle.bullet.Explosion;
import com.mygdx.wargame.battle.bullet.IonBullet;
import com.mygdx.wargame.battle.bullet.LaserBullet;
import com.mygdx.wargame.battle.bullet.MachineGunBullet;
import com.mygdx.wargame.battle.bullet.MissileBullet;
import com.mygdx.wargame.battle.bullet.PlasmaBullet;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.component.weapon.ballistic.MachineGun;
import com.mygdx.wargame.component.weapon.ballistic.MachineGunMk2;
import com.mygdx.wargame.component.weapon.ballistic.MachineGunMk3;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.util.MathUtils;
import com.mygdx.wargame.util.StageUtils;

import java.util.ArrayList;
import java.util.List;

public class BulletAnimationAction extends Action {

    private Mech attackerMech;
    private Mech defenderMech;
    private double counter = 0;
    private int duration = 0;
    private Stage stage;
    private Stage hudStage;
    private AssetManager assetManager;
    private boolean firstStart = true;
    private ActionLock actionLock;
    private boolean done = false;
    private int minRange;

    public BulletAnimationAction(Mech attackerMech, Mech defenderMech, Stage stage, Stage hudStage, AssetManager assetManager, ActionLock actionLock, int minRange) {
        this.attackerMech = attackerMech;
        this.defenderMech = defenderMech;
        this.stage = stage;
        this.hudStage = hudStage;
        this.assetManager = assetManager;
        this.actionLock = actionLock;
        this.minRange = minRange;
    }

    @Override
    public boolean act(float delta) {

        if(done)
            return true;

        if (MathUtils.getDistance(attackerMech.getX(), attackerMech.getY(), defenderMech.getX(), defenderMech.getY()) <= minRange){
            startBullet(attackerMech);
        } else {
            stage.addAction(new UnlockAction(actionLock));
        }
        done = true;

        return true;
    }

    private void startBullet(Mech mech) {

        List<Weapon> selectedWeapons = new ArrayList<>(mech.getSelectedWeapons());

        int delay = -1;

        for (int i = 0; i < selectedWeapons.size(); i++) {

            Weapon weapon = selectedWeapons.get(i);

            for(int j = 0; j < weapon.getDamageMultiplier(); j++) {
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
                } else {
                    bullet = new CannonBullet(assetManager);
                }

                Vector2 start = StageUtils.convertBetweenStages(stage, hudStage, attackerMech.getX(), attackerMech.getY());
                Vector2 end = StageUtils.convertBetweenStages(stage, hudStage, defenderMech.getX(), defenderMech.getY());

                bullet.setPosition(start.x, start.y);
                MoveToAction moveToAction = new MoveToAction();
                moveToAction.setPosition(end.x, end.y);
                moveToAction.setStartPosition(start.x, start.y);

                if (weapon.getType() == WeaponType.Missile)
                    moveToAction.setDuration(0.3f);
                else
                    moveToAction.setDuration(0.15f);

                RotateToAction rotateToAction = new RotateToAction();
                rotateToAction.setRotation(MathUtils.getAngle(new double[]{start.x, start.y}, new double[]{end.x, end.y}));

                SequenceAction sequenceAction = new SequenceAction();
                VisibleAction hideAction = new VisibleAction();
                hideAction.setVisible(false);
                sequenceAction.addAction(hideAction);
                sequenceAction.addAction(rotateToAction);
                sequenceAction.addAction(delayAction);
                VisibleAction visibleAction = new VisibleAction();
                visibleAction.setVisible(true);
                sequenceAction.addAction(visibleAction);
                sequenceAction.addAction(moveToAction);

                boolean finished = false;
                if (weapon.getType() == WeaponType.Missile) {
                    Explosion explosion = new Explosion(assetManager);
                    explosion.setPosition(defenderMech.getX(), defenderMech.getY());
                    SequenceAction sequenceAction1 = new SequenceAction();
                    sequenceAction1.addAction(new DelayAction(0.25f * delay + 0.3f));
                    sequenceAction1.addAction(new AddActorAction(stage, explosion));
                    sequenceAction1.addAction(new DelayAction(0.5f));
                    sequenceAction1.addAction(new RemoveCustomActorAction(stage, explosion));

                    if(i == selectedWeapons.size() - 1 && j == weapon.getDamageMultiplier() -1) {
                        sequenceAction1.addAction(new UnlockAction(actionLock));
                        finished = true;
                    }

                    stage.addAction(sequenceAction1);
                }

                if(i == selectedWeapons.size() - 1 && !finished) {
                    sequenceAction.addAction(new UnlockAction(actionLock));
                }

                sequenceAction.addAction(new RemoveActorAction());
                bullet.addAction(sequenceAction);

                hudStage.addActor(bullet);
            }
        }
    }
}
