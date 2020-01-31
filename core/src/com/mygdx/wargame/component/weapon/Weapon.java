package com.mygdx.wargame.component.weapon;

import com.mygdx.wargame.component.Component;
import com.mygdx.wargame.mech.AbstractMech;

import java.util.Optional;

public interface Weapon extends Component {

    int getShieldDamage();

    int getArmorDamage();

    int getStructuralDamage();

    int getRange();

    WeaponType getType();

    String getName();

    int getPrice();

    int getHeat();

    int getWeight();

    int getSlot();

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
