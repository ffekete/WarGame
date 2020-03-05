package com.mygdx.wargame.common.component.shield;

import com.mygdx.wargame.common.component.Component;

public interface Shield extends Component {

    int getShieldValue();

    void reduceShieldValue(int amount);

    void resetShieldValue();

}
