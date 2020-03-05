package com.mygdx.wargame.battle.rules.facade;

import com.mygdx.wargame.common.component.weapon.Status;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.mech.Mech;

import java.util.Comparator;
import java.util.Optional;

public class WeaponSelectionOptimizer {

    public void doIt(Mech mech) {

        // deactivate all that is not destroyed yet
        mech.getAllComponents().stream()
                .filter(c -> Weapon.class.isAssignableFrom(c.getClass()))
                .map(c -> ((Weapon)c))
                .filter(w -> w.getStatus() != Status.Destroyed)
                .forEach(w -> w.setStatus(Status.Active));

        // select all that has ammo or does not need ammo
        mech.getAllComponents().stream()
                .filter(c -> Weapon.class.isAssignableFrom(c.getClass()))
                .map(c -> ((Weapon)c))
                .filter(w -> !w.getAmmo().isPresent() || w.getAmmo().get() > 0)
                .forEach(w -> w.setStatus(Status.Selected));

        int allHeat = getAllHeat(mech);

        if(mech.getHeatLevel() + allHeat < 100) {
            // nothing to do here
        } else {
            do {
                deSelectTheMaxHeatingWeapon(mech);
            } while(mech.getHeatLevel() + getAllHeat(mech) >= 100 || mech.getSelectedWeapons().isEmpty());
        }
    }

    private Integer getAllHeat(Mech mech) {
        return mech.getSelectedWeapons()
                .stream()
                .map(w -> w.getHeat())
                .reduce((a,b) -> a+b)
                .orElse(0);
    }

    private void deSelectTheMaxHeatingWeapon(Mech mech) {
        Optional<Weapon> weaponToDeselect = mech.getSelectedWeapons().stream()
                .max(new Comparator<Weapon>() {
                    @Override
                    public int compare(Weapon o1, Weapon o2) {
                        return Integer.compare(o2.getHeat(), o1.getHeat());
                    }
                });
        weaponToDeselect.ifPresent(w -> w.setStatus(Status.Active));
    }


}
