package com.mygdx.wargame.rules;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.google.common.collect.ImmutableSet;
import com.mygdx.wargame.component.armor.Armor;
import com.mygdx.wargame.component.armor.CompositeMaterialArmor;
import com.mygdx.wargame.component.armor.LivingMetalArmor;
import com.mygdx.wargame.component.shield.Shield;
import com.mygdx.wargame.component.shield.SmallShieldModule;
import com.mygdx.wargame.component.weapon.laser.DoubleBarrelMediumLaser;
import com.mygdx.wargame.component.weapon.laser.LargeLaser;
import com.mygdx.wargame.component.weapon.laser.SmallLaser;
import com.mygdx.wargame.component.weapon.laser.TripleBarrelSmallLaser;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Perks;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.calculator.BodyPartDestructionHandler;
import com.mygdx.wargame.rules.calculator.CriticalHitChanceCalculator;
import com.mygdx.wargame.rules.calculator.DamageCalculator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class DamageCalculatorTest {

    private CriticalHitChanceCalculator criticalHitChanceCalculator = mock(CriticalHitChanceCalculator.class);
    private BodyPartDestructionHandler bodyPartDestructionHandler = mock(BodyPartDestructionHandler.class);
    private final DamageCalculator damageCalculator = new DamageCalculator(criticalHitChanceCalculator, bodyPartDestructionHandler, new Stage(), null, null);

    @BeforeMethod
    public void setUp() {
    when(criticalHitChanceCalculator.calculate(any(), any(), any())).thenReturn(-100); // jinxing critical chance
    }

    @Test
    public void test_noShield_noArmor() {

        Pilot attacker = new Pilot(new HashMap<>(), ImmutableSet.of());
        Pilot defender = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech defenderMech = mock(Mech.class);
        when(defenderMech.getHp(BodyPart.Head)).thenReturn(11);

        Mech attackerMech = mock(Mech.class);
        when(attackerMech.getHp(BodyPart.Head)).thenReturn(11);

        damageCalculator.calculate(attacker, attackerMech, defender, defenderMech, new LargeLaser(), BodyPart.Head);

        verify(defenderMech).setHp(BodyPart.Head, -1);
    }

    @Test
    public void test_noShield_noArmor_criticalHit() {

        Pilot attacker = new Pilot(new HashMap<>(), ImmutableSet.of());
        Pilot defender = new Pilot(new HashMap<>(), ImmutableSet.of());

        when(criticalHitChanceCalculator.calculate(any(), any(), any())).thenReturn(100);

        Mech defenderMech = mock(Mech.class);
        when(defenderMech.getHp(BodyPart.Head)).thenReturn(11);

        Mech attackerMech = mock(Mech.class);
        when(attackerMech.getHp(BodyPart.Head)).thenReturn(11);

        damageCalculator.calculate(attacker, attackerMech, defender, defenderMech, new LargeLaser(), BodyPart.Head);

        verify(defenderMech).setHp(BodyPart.Head, -13);
    }

    @Test
    public void test_shield_noArmor() {

        Pilot attacker = new Pilot(new HashMap<>(), ImmutableSet.of());
        Pilot defender = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech defenderMech = mock(Mech.class);
        when(defenderMech.getHp(BodyPart.Head)).thenReturn(11);

        Mech attackerMech = mock(Mech.class);
        when(attackerMech.getHp(BodyPart.Head)).thenReturn(11);

        when(defenderMech.getAllComponents()).thenReturn(ImmutableSet.of(new SmallShieldModule()));

        assertThat(((Shield) defenderMech.getAllComponents().stream().findFirst().get()).getShieldValue(), is(10));

        damageCalculator.calculate(attacker, attackerMech, defender, defenderMech, new LargeLaser(), BodyPart.Head);

        assertThat(((Shield) defenderMech.getAllComponents().stream().findFirst().get()).getShieldValue(), is(4));
    }

    @Test
    public void test_noShield_armor() {

        Pilot attacker = new Pilot(new HashMap<>(), ImmutableSet.of());
        Pilot defender = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech defenderMech = mock(Mech.class);
        when(defenderMech.getHp(BodyPart.Head)).thenReturn(11);

        Mech attackerMech = mock(Mech.class);
        when(attackerMech.getHp(BodyPart.Head)).thenReturn(11);


        when(defenderMech.getComponents(BodyPart.Head)).thenReturn(ImmutableSet.of(new CompositeMaterialArmor()));

        assertThat(((Armor) defenderMech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(20));

        damageCalculator.calculate(attacker, attackerMech, defender, defenderMech, new LargeLaser(), BodyPart.Head);

        assertThat(((Armor) defenderMech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(8));
    }

    @Test
    public void test_noShield_armor_criticalHit() {

        Pilot attacker = new Pilot(new HashMap<>(), ImmutableSet.of());
        Pilot defender = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech defenderMech = mock(Mech.class);
        when(defenderMech.getHp(BodyPart.Head)).thenReturn(11);

        Mech attackerMech = mock(Mech.class);
        when(attackerMech.getHp(BodyPart.Head)).thenReturn(11);

        when(criticalHitChanceCalculator.calculate(any(), any(), any())).thenReturn(100);

        when(defenderMech.getComponents(BodyPart.Head)).thenReturn(ImmutableSet.of(new CompositeMaterialArmor()));

        assertThat(((Armor) defenderMech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(20));

        damageCalculator.calculate(attacker, attackerMech, defender, defenderMech, new SmallLaser(), BodyPart.Head);

        assertThat(((Armor) defenderMech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(0));
    }

    @Test
    public void test_noShield_armor_moreDamage() {

        Pilot attacker = new Pilot(new HashMap<>(), ImmutableSet.of());
        Pilot defender = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech defenderMech = mock(Mech.class);
        when(defenderMech.getHp(BodyPart.Head)).thenReturn(11);

        Mech attackerMech = mock(Mech.class);
        when(attackerMech.getHp(BodyPart.Head)).thenReturn(11);


        when(defenderMech.getComponents(BodyPart.Head)).thenReturn(ImmutableSet.of(new LivingMetalArmor()));

        assertThat(((Armor) defenderMech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(10));

        damageCalculator.calculate(attacker, attackerMech, defender, defenderMech, new LargeLaser(), BodyPart.Head);

        assertThat(((Armor) defenderMech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(0));
    }


    @Test
    public void test_noShield_armor_moreDamage_multipleShots() {

        Pilot attacker = new Pilot(new HashMap<>(), ImmutableSet.of());
        Pilot defender = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech defenderMech = mock(Mech.class);
        when(defenderMech.getHp(BodyPart.Head)).thenReturn(11);

        Mech attackerMech = mock(Mech.class);
        when(attackerMech.getHp(BodyPart.Head)).thenReturn(11);

        when(defenderMech.getComponents(BodyPart.Head)).thenReturn(ImmutableSet.of(new LivingMetalArmor()));

        assertThat(((Armor) defenderMech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(10));

        damageCalculator.calculate(attacker, attackerMech, defender, defenderMech, new DoubleBarrelMediumLaser(), BodyPart.Head);

        assertThat(((Armor) defenderMech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(0));

        verify(defenderMech).setHp(BodyPart.Head, 1);
    }

    @Test
    public void test_noShield_armor_moreDamage_multipleShotsMoreWeapons() {

        Pilot attacker = new Pilot(new HashMap<>(), ImmutableSet.of());
        Pilot defender = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech defenderMech = mock(Mech.class);
        when(defenderMech.getHp(BodyPart.Head)).thenReturn(11).thenReturn(1).thenReturn(0);

        Mech attackerMech = mock(Mech.class);
        when(attackerMech.getHp(BodyPart.Head)).thenReturn(11);

        when(defenderMech.getComponents(BodyPart.Head)).thenReturn(ImmutableSet.of(new LivingMetalArmor()));

        assertThat(((Armor) defenderMech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(10));

        damageCalculator.calculate(attacker, attackerMech, defender, defenderMech, new DoubleBarrelMediumLaser(), BodyPart.Head);

        assertThat(((Armor) defenderMech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(0));

        verify(defenderMech).setHp(BodyPart.Head, 1);

        damageCalculator.calculate(attacker, attackerMech, defender, defenderMech, new LargeLaser(), BodyPart.Head);

        verify(bodyPartDestructionHandler).destroy(defenderMech, BodyPart.Head);
        verify(defenderMech).setHp(BodyPart.Head, -12);
    }

    @Test
    public void test_moreShields_noArmor() {

        Pilot attacker = new Pilot(new HashMap<>(), ImmutableSet.of());
        Pilot defender = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech defenderMech = mock(Mech.class);
        when(defenderMech.getHp(BodyPart.Head)).thenReturn(11);

        Mech attackerMech = mock(Mech.class);
        when(attackerMech.getHp(BodyPart.Head)).thenReturn(11);

        when(defenderMech.getAllComponents()).thenReturn(ImmutableSet.of(new SmallShieldModule(), new SmallShieldModule()));

        assertThat((defenderMech.getAllComponents().stream().map(c -> ((Shield) c).getShieldValue()).reduce((a, b) -> a + b)).get(), is(20));

        damageCalculator.calculate(attacker, attackerMech, defender, defenderMech, new LargeLaser(), BodyPart.Head);

        assertThat(defenderMech.getAllComponents().stream().anyMatch(c -> ((Shield) c).getShieldValue() == 4), is(true));
        assertThat(defenderMech.getAllComponents().stream().anyMatch(c -> ((Shield) c).getShieldValue() == 10), is(true));
    }

    @Test
    public void test_shields_armor() {

        Pilot attacker = new Pilot(new HashMap<>(), ImmutableSet.of());
        Pilot defender = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech defenderMech = mock(Mech.class);
        when(defenderMech.getHp(BodyPart.Head)).thenReturn(11);

        Mech attackerMech = mock(Mech.class);
        when(attackerMech.getHp(BodyPart.Head)).thenReturn(11);

        when(defenderMech.getAllComponents()).thenReturn(ImmutableSet.of(new SmallShieldModule()));
        when(defenderMech.getComponents(BodyPart.Head)).thenReturn(ImmutableSet.of(new LivingMetalArmor()));

        assertThat((defenderMech.getAllComponents().stream().map(c -> ((Shield) c).getShieldValue()).reduce((a, b) -> a + b)).get(), is(10));
        assertThat(((Armor) defenderMech.getComponents(BodyPart.Head).stream().findFirst().get()).getHitPoint(), is(10));

        damageCalculator.calculate(attacker, attackerMech, defender, defenderMech, new DoubleBarrelMediumLaser(), BodyPart.Head);
        damageCalculator.calculate(attacker, attackerMech, defender, defenderMech, new DoubleBarrelMediumLaser(), BodyPart.Head);

        assertThat(defenderMech.getAllComponents().stream().filter(c -> ((Shield) c).getShieldValue() == 0).count(), is(1L));
        assertThat(defenderMech.getComponents(BodyPart.Head).stream().filter(c -> ((Armor) c).getHitPoint() == 0).count(), is(1L));
        verify(defenderMech).setHp(BodyPart.Head, 1);
    }

    @Test
    public void test_moreShields_noArmor_multipleShots() {

        Pilot attacker = new Pilot(new HashMap<>(), ImmutableSet.of());
        Pilot defender = new Pilot(new HashMap<>(), ImmutableSet.of());

        Mech defenderMech = mock(Mech.class);
        when(defenderMech.getHp(BodyPart.Head)).thenReturn(11);

        Mech attackerMech = mock(Mech.class);
        when(attackerMech.getHp(BodyPart.Head)).thenReturn(11);

        when(defenderMech.getAllComponents()).thenReturn(ImmutableSet.of(new SmallShieldModule(), new SmallShieldModule()));

        assertThat((defenderMech.getAllComponents().stream().map(c -> ((Shield) c).getShieldValue()).reduce((a, b) -> a + b)).get(), is(20));

        damageCalculator.calculate(attacker, attackerMech, defender, defenderMech, new TripleBarrelSmallLaser(), BodyPart.Head);

        assertThat(defenderMech.getAllComponents().stream().anyMatch(c -> ((Shield) c).getShieldValue() == 0), is(true));
        assertThat(defenderMech.getAllComponents().stream().anyMatch(c -> ((Shield) c).getShieldValue() == 5), is(true));
    }

    @Test
    public void test_moreShields_noArmor_multipleShots_pilotIsRobust() {

        Pilot attacker = new Pilot(new HashMap<>(), ImmutableSet.of());
        Pilot defender = new Pilot(new HashMap<>(), ImmutableSet.of(Perks.Robust));

        Mech defenderMech = mock(Mech.class);
        when(defenderMech.getHp(BodyPart.Head)).thenReturn(11);

        Mech attackerMech = mock(Mech.class);
        when(attackerMech.getHp(BodyPart.Head)).thenReturn(11);

        when(defenderMech.getAllComponents()).thenReturn(ImmutableSet.of(new SmallShieldModule(), new SmallShieldModule()));

        assertThat((defenderMech.getAllComponents().stream().map(c -> ((Shield) c).getShieldValue()).reduce((a, b) -> a + b)).get(), is(20));

        damageCalculator.calculate(attacker,attackerMech,defender, defenderMech, new TripleBarrelSmallLaser(), BodyPart.Head);

        assertThat(defenderMech.getAllComponents().stream().anyMatch(c -> ((Shield) c).getShieldValue() == 0), is(true));
        assertThat(defenderMech.getAllComponents().stream().anyMatch(c -> ((Shield) c).getShieldValue() == 8), is(true));
    }

}