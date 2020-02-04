package com.mygdx.wargame.pilot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mygdx.wargame.component.weapon.missile.MissileLauncher;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.rules.hitchance.MissileHitChanceCalculator;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class PilotTest {

    @Mock
    Mech mech;

    @Mock
    Mech target;

    @BeforeTest
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getMissileSkillTest_moTargetingModule_hasMissileExpertPerk() {

        Pilot pilot = new Pilot(ImmutableMap.of(Skill.Missiles, 10), ImmutableSet.of(Perks.MissileExpert));

        pilot.setMech(mech);

        when(mech.getAllComponents()).thenReturn(ImmutableSet.of(new MissileLauncher()));
        when(mech.getX()).thenReturn(0f);
        when(mech.getY()).thenReturn(0f);

        when(target.getX()).thenReturn(2f);
        when(target.getY()).thenReturn(2f);

        int value = new MissileHitChanceCalculator().calculate(pilot, mech, target, new MissileLauncher());

        assertThat(value, is(53));
    }
}