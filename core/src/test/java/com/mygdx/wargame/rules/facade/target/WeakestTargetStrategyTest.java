package com.mygdx.wargame.rules.facade.target;

import com.google.common.collect.ImmutableMap;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeakestTargetStrategyTest {

    @Test
    public void test() {

        Mech mech01 = mock(Mech.class);
        Mech mech02 = mock(Mech.class);

        when(mech01.getHp(BodyPart.Head)).thenReturn(1);
        when(mech01.getHp(BodyPart.Torso)).thenReturn(1);
        when(mech01.isActive()).thenReturn(true);

        when(mech02.getHp(BodyPart.Head)).thenReturn(1);
        when(mech02.getHp(BodyPart.Torso)).thenReturn(2);
        when(mech02.isActive()).thenReturn(true);

        WeakestTargetStrategy weakestTargetStrategy = new WeakestTargetStrategy();
        Optional<Target> target = weakestTargetStrategy.findTarget(null, null, ImmutableMap.of(mech01, new Pilot(null, null), mech02, new Pilot(null, null)), null, null);

        assertThat(target.isPresent(), is(true));
        assertThat(target.get().getMech(), is(mech01));
    }


}