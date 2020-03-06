package com.mygdx.wargame.battle.rules;

import com.google.common.collect.ImmutableSet;
import com.mygdx.wargame.common.component.weapon.Weapon;
import com.mygdx.wargame.common.component.weapon.laser.SniperLargeLaser;
import com.mygdx.wargame.common.component.weapon.laser.SniperMediumLaser;
import com.mygdx.wargame.common.component.weapon.missile.MissileLauncher;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Perks;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.battle.rules.calculator.RangeCalculator;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RangeCalculatorTest {

    private final RangeCalculator rangeCalculator = new RangeCalculator();

    @Test
    public void testSingleWeaponRange() {
        Weapon weapon = new SniperLargeLaser();

        Pilot pilot = new Pilot(new HashMap<>(), new HashSet<>());
        int range = rangeCalculator.calculate(pilot, weapon);

        assertThat(range, is(16));
    }

    @Test
    public void testSingleWeaponRange_pilotHasCautiousPerk() {
        Weapon weapon = new SniperLargeLaser();

        Pilot pilot = new Pilot(new HashMap<>(), ImmutableSet.of(Perks.Cautious));
        int range = rangeCalculator.calculate(pilot, weapon);

        assertThat(range, is(17));
    }

    @Test
    public void testSelectedWeaponsRange() {

        Weapon laser = new SniperLargeLaser();
        Weapon missile = new MissileLauncher();

        Mech mech = mock(Mech.class);

        when(mech.getSelectedWeapons()).thenReturn(ImmutableSet.<Weapon>builder()
                .add(new SniperMediumLaser())
                .add(new MissileLauncher())
                .add(new SniperLargeLaser())
                .build());

        Pilot pilot = new Pilot(new HashMap<>(), new HashSet<>());
        int range = rangeCalculator.calculateAllWeaponsRange(pilot, mech);

        assertThat(range, is(8));
    }

    @Test
    public void testSelectedWeaponsRange_noWeaponsSelected() {

        Weapon laser = new SniperLargeLaser();
        Weapon missile = new MissileLauncher();

        Mech mech = mock(Mech.class);

        when(mech.getSelectedWeapons()).thenReturn(ImmutableSet.<Weapon>builder().build());

        Pilot pilot = new Pilot(new HashMap<>(), new HashSet<>());
        int range = rangeCalculator.calculateAllWeaponsRange(pilot, mech);

        assertThat(range, is(0));
    }
}