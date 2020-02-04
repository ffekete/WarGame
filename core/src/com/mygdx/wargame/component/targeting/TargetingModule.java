package com.mygdx.wargame.component.targeting;

import com.mygdx.wargame.component.Component;
import com.mygdx.wargame.component.weapon.WeaponType;

public interface TargetingModule extends Component {

    int getAdditionalAccuracy(WeaponType weaponType);

    int getAdditionalCriticalChance();

}
