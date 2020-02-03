package com.mygdx.wargame.component.armor;

import com.mygdx.wargame.component.Component;

public interface Armor extends Component {

    int getHitPoint();
    int getMaxHitpoint();
    int reduceHitPoint(int amount);

}
