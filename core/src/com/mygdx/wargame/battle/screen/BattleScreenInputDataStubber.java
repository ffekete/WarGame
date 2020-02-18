package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.component.shield.SmallShieldModule;
import com.mygdx.wargame.component.weapon.Status;
import com.mygdx.wargame.component.weapon.ballistic.LargeCannon;
import com.mygdx.wargame.component.weapon.ballistic.MachineGun;
import com.mygdx.wargame.component.weapon.ion.LargeIonCannon;
import com.mygdx.wargame.component.weapon.laser.LargeLaser;
import com.mygdx.wargame.component.weapon.missile.SwarmMissile;
import com.mygdx.wargame.component.weapon.plasma.PlasmaCannon;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Marauder;
import com.mygdx.wargame.mech.WreckingBall;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.pilot.PilotCreator;

public class BattleScreenInputDataStubber {

    private SpriteBatch spriteBatch;
    private AssetManager assetManager;

    public BattleScreenInputDataStubber(SpriteBatch spriteBatch, AssetManager assetManager) {
        this.spriteBatch = spriteBatch;
        this.assetManager = assetManager;
    }

    public void stub(BattleScreenInputData battleScreenInputData) {
        WreckingBall unit3 = new WreckingBall("3", spriteBatch, assetManager);
        unit3.setPosition(6, 5);
        unit3.setTeam(Team.own);
        unit3.setStability(100);
        SwarmMissile swarmMissile = new SwarmMissile();
        swarmMissile.setStatus(Status.Selected);
        LargeLaser largeLaser = new LargeLaser();
        largeLaser.setStatus(Status.Selected);
        LargeLaser largeLaser4 = new LargeLaser();
        largeLaser4.setStatus(Status.Selected);
        unit3.addComponent(BodyPart.LeftLeg, swarmMissile);
        unit3.addComponent(BodyPart.RightHand, largeLaser);
        unit3.addComponent(BodyPart.Torso, largeLaser4);
        unit3.setActive(true);
        unit3.addComponent(BodyPart.Torso, new SmallShieldModule());

        PlasmaCannon plasmaCannon = new PlasmaCannon();
        plasmaCannon.setStatus(Status.Active);
        unit3.addComponent(BodyPart.LeftLeg, plasmaCannon);

        LargeIonCannon largeIonCannon = new LargeIonCannon();
        largeIonCannon.setStatus(Status.Selected);

        MachineGun machineGun = new MachineGun();
        machineGun.setStatus(Status.Selected);

        unit3.addComponent(BodyPart.Torso, largeIonCannon);
        unit3.addComponent(BodyPart.Torso, machineGun);

        Marauder unit2 = new Marauder("2", spriteBatch, assetManager);
        unit2.setPosition(1, 1);
        unit2.setTeam(Team.own);
        unit2.setActive(true);
        unit2.setStability(100);
        LargeCannon largeCannon2 = new LargeCannon();
        largeCannon2.setStatus(Status.Selected);

        LargeCannon largeCannon3 = new LargeCannon();
        largeCannon3.setStatus(Status.Selected);

        LargeCannon largeCannon4 = new LargeCannon();
        largeCannon4.setStatus(Status.Selected);

        LargeCannon largeCannon5 = new LargeCannon();
        largeCannon5.setStatus(Status.Selected);

        LargeCannon largeCannon6 = new LargeCannon();
        largeCannon6.setStatus(Status.Selected);

        LargeCannon largeCannon7 = new LargeCannon();
        largeCannon7.setStatus(Status.Selected);

        LargeCannon largeCannon8 = new LargeCannon();
        largeCannon8.setStatus(Status.Selected);

        LargeCannon largeCannon9 = new LargeCannon();
        largeCannon9.setStatus(Status.Selected);

        LargeCannon largeCannon10 = new LargeCannon();
        largeCannon10.setStatus(Status.Selected);

        LargeCannon largeCannon11 = new LargeCannon();
        largeCannon11.setStatus(Status.Selected);

        unit2.addComponent(BodyPart.LeftLeg, largeCannon2);
        unit2.addComponent(BodyPart.LeftLeg, largeCannon3);
        unit2.addComponent(BodyPart.RightLeg, largeCannon4);
        unit2.addComponent(BodyPart.RightLeg, largeCannon5);
        unit2.addComponent(BodyPart.LeftLeg, largeCannon6);
        unit2.addComponent(BodyPart.LeftLeg, largeCannon7);
        unit2.addComponent(BodyPart.LeftLeg, largeCannon8);
        unit2.addComponent(BodyPart.Torso, largeCannon9);
        unit2.addComponent(BodyPart.Torso, largeCannon10);
        unit2.addComponent(BodyPart.Torso, largeCannon11);

        Marauder unit = new Marauder("1", spriteBatch, assetManager);
        unit.setPosition(5, 2);
        unit.setTeam(Team.enemy);
        unit.setActive(true);
        unit.setStability(100);
        LargeLaser largeLaser2 = new LargeLaser();
        largeLaser2.setStatus(Status.Selected);
        LargeLaser largeLaser3 = new LargeLaser();
        largeLaser3.setStatus(Status.Selected);
        unit.addComponent(BodyPart.LeftLeg, largeLaser2);
        unit.addComponent(BodyPart.Torso, largeLaser3);

        PilotCreator pilotCreator = new PilotCreator();

        Pilot p1 = pilotCreator.getPilot();
        Pilot p2 = pilotCreator.getPilot();
        Pilot p3 = pilotCreator.getPilot();

        battleScreenInputData.getGroup1().clear();
        battleScreenInputData.getGroup2().clear();

        battleScreenInputData.getGroup1().put(unit2, p2);
        battleScreenInputData.getGroup1().put(unit3, p3);
        battleScreenInputData.getGroup2().put(unit, p1);
    }

}
