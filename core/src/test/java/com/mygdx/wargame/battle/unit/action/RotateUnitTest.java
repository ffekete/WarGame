package com.mygdx.wargame.battle.unit.action;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.unit.AbstractWarrior;
import com.mygdx.wargame.battle.unit.SpearMen;
import com.mygdx.wargame.battle.unit.Unit;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RotateUnitTest {

    Unit unit = new Unit(16, 10);
    BattleMap battleMap = new BattleMap(1000, 1000);
    private RotateUnit rotateUnit;

    @Test
    public void test() {

         rotateUnit = new RotateUnit(unit, unit.getCenter(), battleMap, null, null, null);

        for(int i = 0; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                createSpearmanUnit(unit, "", i * 10, j * 10, battleMap);
            }
        }
        //rotate.act(0.5f);
        double[] result = unit.getCenter();

        assertThat(result[0], is(75.0d));
        assertThat(result[1], is(45.d));
    }

    @Test
    public void test2() {

        unit = new Unit(2,2);
        rotateUnit = new RotateUnit(unit, unit.getCenter(), battleMap, null, null, null);

        createSpearmanUnit(unit, "", 10, 10, battleMap);
        createSpearmanUnit(unit, "", 180, 15, battleMap);
        createSpearmanUnit(unit, "", 15, 0, battleMap);
        createSpearmanUnit(unit, "", 18, 120, battleMap);

        //rotate.act(0.5f);
        double[] result = unit.getCenter();

        assertThat(result[0], is(95.0d));
        assertThat(result[1], is(60.d));
    }

    private void createSpearmanUnit(Unit unit, String s, int i, int i2, BattleMap battleMap) {
        AbstractWarrior spearman2 = new SpearMen(s, null, null, null);
        spearman2.setUnit(unit);
        spearman2.setPosition(i, i2);
        unit.add(spearman2);

        battleMap.setObstacle(spearman2.getX(), spearman2.getY(), 1);
    }
}