package com.mygdx.wargame.common.component.targeting;

import com.mygdx.wargame.common.component.Component;
import com.mygdx.wargame.common.component.weapon.WeaponType;

public interface TargetingModule extends Component {

    int getAdditionalAccuracy(WeaponType weaponType);

    int getAdditionalCriticalChance();

}
