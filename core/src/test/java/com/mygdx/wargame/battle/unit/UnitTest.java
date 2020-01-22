package com.mygdx.wargame.battle.unit;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UnitTest {

    @Test
    public void testUnitLength() {
        Unit unit = new Unit(100, 20);
        assertThat(unit.getUnitWidth(), is(0));

        unit.getLayout()[10][10] = new SpearMen("null", null, null, null);

        assertThat(unit.getUnitWidth(), is(1));

        unit.getLayout()[10][12] = new SpearMen("null", null, null, null);

        assertThat(unit.getUnitWidth(), is(1));

        unit.getLayout()[11][12] = new SpearMen("null", null, null, null);

        assertThat(unit.getUnitWidth(), is(2));
    }

    @Test
    public void testUnitHeight() {
        Unit unit = new Unit(100, 20);
        assertThat(unit.getUnitHeight(), is(0));

        unit.getLayout()[10][10] = new SpearMen("null", null, null, null);

        assertThat(unit.getUnitHeight(), is(1));

        unit.getLayout()[12][10] = new SpearMen("null", null, null, null);

        assertThat(unit.getUnitHeight(), is(1));

        unit.getLayout()[12][11] = new SpearMen("null", null, null, null);

        assertThat(unit.getUnitHeight(), is(2));
    }

}