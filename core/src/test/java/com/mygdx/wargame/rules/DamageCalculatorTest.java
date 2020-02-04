package com.mygdx.wargame.rules;

import com.google.common.collect.ImmutableSet;
import com.mygdx.wargame.component.armor.Armor;
import com.mygdx.wargame.component.armor.CompositeMaterialArmor;
import com.mygdx.wargame.component.armor.LivingMetalArmor;
import com.mygdx.wargame.component.armor.PlateArmor;
import com.mygdx.wargame.component.shield.Shield;
import com.mygdx.wargame.component.shield.SmallShieldModule;
import com.mygdx.wargame.component.weapon.laser.DoubleBarrelMediumLaser;
import com.mygdx.wargame.component.weapon.laser.LargeLaser;
import com.mygdx.wargame.component.weapon.laser.TripleBarrelSmallLaser;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class DamageCalculatorTest {

    private final DamageCalculator damageCalculator = new DamageCalculator();

    @Test
    public void test_noShield_noArmor() {

        Pilot pilot = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech mech = mock(Mech.class);
        when(mech.getHp(BodyPart.Head)).thenReturn(11);

        damageCalculator.calculate(pilot, mech, new LargeLaser(), BodyPart.Head);

        verify(mech).setHp(BodyPart.Head, -1);
    }

    @Test
    public void test_shield_noArmor() {

        Pilot pilot = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech mech = mock(Mech.class);
        when(mech.getHp(BodyPart.Head)).thenReturn(11);
        when(mech.getAllComponents()).thenReturn(ImmutableSet.of(new SmallShieldModule()));

        assertThat(((Shield)mech.getAllComponents().stream().findFirst().get()).getShieldValue(), is(10));

        damageCalculator.calculate(pilot, mech, new LargeLaser(), BodyPart.Head);

       assertThat(((Shield)mech.getAllComponents().stream().findFirst().get()).getShieldValue(), is(4));
    }

    @Test
    public void test_noShield_armor() {

        Pilot pilot = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech mech = mock(Mech.class);
        when(mech.getHp(BodyPart.Head)).thenReturn(11);
        when(mech.getComponents(BodyPart.Head)).thenReturn(ImmutableSet.of(new CompositeMaterialArmor()));

        assertThat(((Armor)mech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(20));

        damageCalculator.calculate(pilot, mech, new LargeLaser(), BodyPart.Head);

        assertThat(((Armor)mech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(8));
    }

    @Test
    public void test_noShield_armor_moreDamage() {

        Pilot pilot = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech mech = mock(Mech.class);
        when(mech.getHp(BodyPart.Head)).thenReturn(11);
        when(mech.getComponents(BodyPart.Head)).thenReturn(ImmutableSet.of(new LivingMetalArmor()));

        assertThat(((Armor)mech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(10));

        damageCalculator.calculate(pilot, mech, new LargeLaser(), BodyPart.Head);

        assertThat(((Armor)mech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(0));
    }


    @Test
    public void test_noShield_armor_moreDamage_multipleShots() {

        Pilot pilot = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech mech = mock(Mech.class);
        when(mech.getHp(BodyPart.Head)).thenReturn(11);
        when(mech.getComponents(BodyPart.Head)).thenReturn(ImmutableSet.of(new LivingMetalArmor()));

        assertThat(((Armor)mech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(10));

        damageCalculator.calculate(pilot, mech, new DoubleBarrelMediumLaser(), BodyPart.Head);

        assertThat(((Armor)mech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(0));

        verify(mech).setHp(BodyPart.Head, 1);
    }

    @Test
    public void test_moreShields_noArmor() {

        Pilot pilot = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech mech = mock(Mech.class);
        when(mech.getHp(BodyPart.Head)).thenReturn(11);
        when(mech.getAllComponents()).thenReturn(ImmutableSet.of(new SmallShieldModule(), new SmallShieldModule()));

        assertThat((mech.getAllComponents().stream().map(c -> ((Shield)c).getShieldValue()).reduce((a,b) -> a+b)).get(), is(20));

        damageCalculator.calculate(pilot, mech, new LargeLaser(), BodyPart.Head);

        assertThat(mech.getAllComponents().stream().anyMatch(c-> ((Shield)c).getShieldValue() == 4), is(true));
        assertThat(mech.getAllComponents().stream().anyMatch(c-> ((Shield)c).getShieldValue() == 10), is(true));
    }

    @Test
    public void test_moreShields_noArmor_multipleShots() {

        Pilot pilot = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech mech = mock(Mech.class);
        when(mech.getHp(BodyPart.Head)).thenReturn(11);
        when(mech.getAllComponents()).thenReturn(ImmutableSet.of(new SmallShieldModule(), new SmallShieldModule()));

        assertThat((mech.getAllComponents().stream().map(c -> ((Shield)c).getShieldValue()).reduce((a,b) -> a+b)).get(), is(20));

        damageCalculator.calculate(pilot, mech, new TripleBarrelSmallLaser(), BodyPart.Head);

        assertThat(mech.getAllComponents().stream().anyMatch(c-> ((Shield)c).getShieldValue() == 0), is(true));
        assertThat(mech.getAllComponents().stream().anyMatch(c-> ((Shield)c).getShieldValue() == 5), is(true));
    }

    @Test
    public void test_moreShields_noArmor_multipleShots_pilotIsRobust() {

        Pilot pilot = new Pilot(new HashMap<>(), ImmutableSet.of(Perks.Robust));

        Mech mech = mock(Mech.class);
        when(mech.getHp(BodyPart.Head)).thenReturn(11);
        when(mech.getAllComponents()).thenReturn(ImmutableSet.of(new SmallShieldModule(), new SmallShieldModule()));

        assertThat((mech.getAllComponents().stream().map(c -> ((Shield)c).getShieldValue()).reduce((a,b) -> a+b)).get(), is(20));

        damageCalculator.calculate(pilot, mech, new TripleBarrelSmallLaser(), BodyPart.Head);

        assertThat(mech.getAllComponents().stream().anyMatch(c-> ((Shield)c).getShieldValue() == 0), is(true));
        assertThat(mech.getAllComponents().stream().anyMatch(c-> ((Shield)c).getShieldValue() == 8), is(true));
    }

}