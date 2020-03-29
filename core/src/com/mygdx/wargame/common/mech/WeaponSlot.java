package com.mygdx.wargame.common.mech;

import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.component.weapon.WeaponType;

import java.util.List;

public class WeaponSlot {

    private List<WeaponType> allowedTypes;
    private Weapon weapon = null;

    public WeaponSlot(List<WeaponType> allowedTypes) {
        this.allowedTypes = allowedTypes;
    }

    public List<WeaponType> getAllowedTypes() {

        return allowedTypes;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setAllowedTypes(List<WeaponType> allowedTypes) {
        this.allowedTypes = allowedTypes;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }


}
