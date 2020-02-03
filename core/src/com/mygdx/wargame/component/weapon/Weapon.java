package com.mygdx.wargame.component.weapon;

import com.mygdx.wargame.component.Component;
import com.mygdx.wargame.mech.AbstractMech;

import java.util.Optional;

public interface Weapon extends Component {

    int getShieldDamage();

    int getArmorDamage();

    int getBodyDamage();

    int getRange();

    WeaponType getType();

    int getHeat();

    int getAccuracy(AbstractMech target);

    int getAdditionalHeatToEnemy();

    int getStabilityHit();

    int getCriticalChance();

    int getDamageMultiplier();

    boolean canTarget();

    boolean requiresLineOfSight();

    Optional<Integer> getAmmo();

    Optional<Integer> getMaxAmmo();

    void resetAmmo();
}