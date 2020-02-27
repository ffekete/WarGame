package com.mygdx.wargame.component.shield;

import com.mygdx.wargame.component.Component;

public interface Shield extends Component {

    int getShieldValue();

    void reduceShieldValue(int amount);

    void resetShieldValue();

}
