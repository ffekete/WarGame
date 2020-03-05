package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.unit.Team;
import com.mygdx.wargame.common.component.armor.CompositeMaterialArmor;
import com.mygdx.wargame.common.component.shield.Shield;
import com.mygdx.wargame.common.component.shield.SmallShieldModule;
import com.mygdx.wargame.common.component.weapon.Status;
import com.mygdx.wargame.common.component.weapon.ballistic.GaussCannon;
import com.mygdx.wargame.common.component.weapon.ballistic.LargeCannon;
import com.mygdx.wargame.common.component.weapon.ballistic.MachineGun;
import com.mygdx.wargame.common.component.weapon.flamer.Flamer;
import com.mygdx.wargame.common.component.weapon.ion.LargeIonCannon;
import com.mygdx.wargame.common.component.weapon.laser.LargeLaser;
import com.mygdx.wargame.common.component.weapon.laser.SmallLaser;
import com.mygdx.wargame.common.component.weapon.missile.SwarmMissile;
import com.mygdx.wargame.common.component.weapon.plasma.PlasmaCannon;
import com.mygdx.wargame.common.mech.BodyPart;
import com.mygdx.wargame.common.mech.Colossus;
import com.mygdx.wargame.common.mech.Giant;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.mech.Scout;
import com.mygdx.wargame.common.mech.WreckingBall;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.common.pilot.PilotCreator;

public class BattleScreenInputDataStubber {

    private SpriteBatch spriteBatch;
    private AssetManager assetManager;

    public BattleScreenInputDataStubber(SpriteBatch spriteBatch, AssetManager assetManager) {
        this.spriteBatch = spriteBatch;
        this.assetManager = assetManager;
    }

    public void stub(BattleScreenInputData battleScreenInputData, BattleMap battleMap, TurnProcessingFacadeStore turnProcessingFacadeStore) {
        Mech unit3 = new Scout("Ace", spriteBatch, assetManager, battleMap, turnProcessingFacadeStore);
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
        unit3.addComponent(BodyPart.RightArm, largeLaser);
        unit3.addComponent(BodyPart.Torso, largeLaser4);
        unit3.setActive(true);
        Shield smallShield = new SmallShieldModule();
        smallShield.reduceShieldValue(10);
        unit3.addComponent(BodyPart.Torso, smallShield);
        Flamer flamer = new Flamer();
        flamer.setStatus(Status.Selected);
        unit3.addComponent(BodyPart.LeftLeg, flamer);
        unit3.setHeatLevel(0);

        PlasmaCannon plasmaCannon = new PlasmaCannon();
        plasmaCannon.setStatus(Status.Active);
        unit3.addComponent(BodyPart.LeftLeg, plasmaCannon);

        LargeIonCannon largeIonCannon = new LargeIonCannon();
        largeIonCannon.setStatus(Status.Selected);

        MachineGun machineGun = new MachineGun();
        machineGun.setStatus(Status.Selected);

        unit3.addComponent(BodyPart.Torso, largeIonCannon);
        unit3.addComponent(BodyPart.Torso, machineGun);

        Colossus unit2 = new Colossus("ReadEye111", spriteBatch, assetManager, battleMap, turnProcessingFacadeStore);
        unit2.setPosition(1, 1);
        unit2.setTeam(Team.own);
        unit2.setActive(true);
        unit2.setStability(100);
        LargeCannon largeCannon2 = new LargeCannon();
        largeCannon2.setStatus(Status.Selected);

        LargeCannon largeCannon3 = new LargeCannon();
        largeCannon3.setStatus(Status.Selected);


        unit2.addComponent(BodyPart.LeftArm, largeCannon2);
        unit2.addComponent(BodyPart.RightArm, largeCannon3);

        Mech unit = new WreckingBall("Dirty", spriteBatch, assetManager, battleMap, turnProcessingFacadeStore);
        unit.setPosition(5, 2);
        unit.setTeam(Team.enemy);
        unit.setActive(true);

        unit.addComponent(BodyPart.Torso, new CompositeMaterialArmor());
        unit.addComponent(BodyPart.Torso, new CompositeMaterialArmor());
        unit.addComponent(BodyPart.Torso, new CompositeMaterialArmor());
        LargeLaser largeLaser2 = new LargeLaser();
        largeLaser2.setStatus(Status.Selected);
        LargeLaser largeLaser3 = new LargeLaser();
        largeLaser3.setStatus(Status.Selected);
        unit.addComponent(BodyPart.LeftLeg, largeLaser2);
        unit.addComponent(BodyPart.Torso, largeLaser3);
        unit.addComponent(BodyPart.LeftLeg, new SmallShieldModule());
        unit.addComponent(BodyPart.LeftLeg, new SmallShieldModule());
        unit.addComponent(BodyPart.LeftLeg, new SmallShieldModule());

        Mech unit4 = new Giant("", spriteBatch, assetManager, battleMap, turnProcessingFacadeStore);
        unit4.setPosition(7, 2);
        unit4.setTeam(Team.enemy);
        unit4.setActive(true);
        unit4.addComponent(BodyPart.Torso, new CompositeMaterialArmor());
        unit4.addComponent(BodyPart.Torso, new CompositeMaterialArmor());
        unit4.addComponent(BodyPart.Torso, new CompositeMaterialArmor());
        unit4.addComponent(BodyPart.LeftLeg, new SmallShieldModule());
        unit4.addComponent(BodyPart.LeftLeg, new SmallShieldModule());
        unit4.addComponent(BodyPart.LeftLeg, new SmallShieldModule());

        MachineGun machineGun1 = new MachineGun();
        machineGun1.setStatus(Status.Selected);

        GaussCannon gaussCannon = new GaussCannon();
        gaussCannon.setStatus(Status.Selected);

        SmallLaser smallLaser = new SmallLaser();
        smallLaser.setStatus(Status.Selected);

        SmallLaser smallLaser2 = new SmallLaser();
        smallLaser.setStatus(Status.Selected);

        unit4.addComponent(BodyPart.LeftLeg, machineGun1);
        unit4.addComponent(BodyPart.Torso, gaussCannon);
        unit4.addComponent(BodyPart.Torso, smallLaser);
        unit4.addComponent(BodyPart.Torso, smallLaser2);


        PilotCreator pilotCreator = new PilotCreator();

        Pilot p1 = pilotCreator.getPilot();
        Pilot p2 = pilotCreator.getPilot();
        Pilot p3 = pilotCreator.getPilot();
        Pilot p4 = pilotCreator.getPilot();

        battleScreenInputData.getGroup1().clear();
        battleScreenInputData.getGroup2().clear();

        battleScreenInputData.getGroup1().put(unit2, p2);
        battleScreenInputData.getGroup1().put(unit3, p3);
        battleScreenInputData.getGroup2().put(unit, p1);
        battleScreenInputData.getGroup2().put(unit4, p4);
    }

}
