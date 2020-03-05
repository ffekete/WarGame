package com.mygdx.wargame.common.component.armor;

import com.mygdx.wargame.common.component.Component;

public interface Armor extends Component {

    int getHitPoint();

    int getMaxHitpoint();

    int reduceHitPoint(int amount);

    void resetHitpoints();

}
