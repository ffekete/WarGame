package com.mygdx.wargame.rules.facade.target;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.TerrainType;
import com.mygdx.wargame.battle.unit.Direction;
import com.mygdx.wargame.component.weapon.laser.LargeLaser;
import com.mygdx.wargame.mech.BodyPart;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.pilot.PilotCreator;
import com.mygdx.wargame.rules.calculator.FlankingCalculator;
import com.mygdx.wargame.util.MapUtils;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;

public class FlankingTargetStrategyTest {


    @Test
    public void test() {
        Mech mech01 = mock(Mech.class);
        Mech mech02 = mock(Mech.class);
        Mech mech03 = mock(Mech.class);

        when(mech01.getHp(BodyPart.Head)).thenReturn(1);
        when(mech01.getHp(BodyPart.Torso)).thenReturn(1);
        when(mech01.isActive()).thenReturn(true);
        when(mech01.getDirection()).thenReturn(Direction.Left);
        when(mech01.getX()).thenReturn(10f);
        when(mech01.getY()).thenReturn(10f);

        when(mech02.getHp(BodyPart.Head)).thenReturn(1);
        when(mech02.getHp(BodyPart.Torso)).thenReturn(2);
        when(mech02.isActive()).thenReturn(true);
        when(mech02.getDirection()).thenReturn(Direction.Left);
        when(mech02.getX()).thenReturn(10f);
        when(mech02.getY()).thenReturn(10f);

        when(mech03.getHp(BodyPart.Head)).thenReturn(1);
        when(mech03.getHp(BodyPart.Torso)).thenReturn(2);
        when(mech03.isActive()).thenReturn(true);
        when(mech03.getX()).thenReturn(12f);
        when(mech03.getY()).thenReturn(10f);
        when(mech03.getMovementPoints()).thenReturn(10);
        when(mech03.getSelectedWeapons()).thenReturn(ImmutableSet.of(new LargeLaser()));

        assertThat(new FlankingCalculator().isFlankedFromPosition(10, 15, mech01), is(true));
        assertThat(new FlankingCalculator().isFlankedFromPosition(9, 15, mech01), is(false));

        BattleMap.TextureRegionSelector textureRegionSelector = mock(BattleMap.TextureRegionSelector.class);
        when(textureRegionSelector.select(TerrainType.Grassland)).thenReturn(new TextureRegion());

        BattleMap battleMap = new BattleMap(100, 100, new ActionLock(), TerrainType.Grassland, null, textureRegionSelector);

        FlankingTargetStrategy flankingTargetStrategy = new FlankingTargetStrategy();
        Optional<Target> target = flankingTargetStrategy.findTarget(new PilotCreator().getPilot(), mech03, ImmutableMap.of(mech01, new Pilot(null, null), mech02, new Pilot(null, null)), battleMap, new WeakestTargetStrategy());

        assertThat(target.isPresent(), is(true));
        assertThat(target.get().getMech(), is(mech01));

        assertThat(target.get().getTargetNode().getX(), is(20f));
        assertThat(target.get().getTargetNode().getY(), is(2f));

        MapUtils mapUtils = new MapUtils();

        List<Node> allAvailableNodes = mapUtils.getAllAvailable(battleMap, mech03);

        assertThat(allAvailableNodes.size(), is(441));
    }

}