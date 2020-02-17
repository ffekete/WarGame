package com.mygdx.wargame.rules.facade.target;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.mech.Mech;
import com.mygdx.wargame.pilot.Pilot;
import com.mygdx.wargame.rules.calculator.FlankingCalculator;
import com.mygdx.wargame.rules.calculator.RangeCalculator;
import com.mygdx.wargame.util.MapUtils;
import com.mygdx.wargame.util.MathUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FlankingTargetStrategy implements TargetingStrategy {

    private MapUtils mapUtils = new MapUtils();
    private FlankingCalculator flankingCalculator = new FlankingCalculator();
    private RangeCalculator rangeCalculator = new RangeCalculator();

    @Override
    public Optional<Target> findTarget(Pilot pilot, Mech mech, Map<Mech, Pilot> targets, BattleMap battleMap, TargetingStrategy targetingStrategy) {

        Optional<Target> target = targetingStrategy.findTarget(pilot, mech, targets, battleMap, null);

        if (target.isPresent()) {
            List<Node> availableNodes = mapUtils.getAllAvailable(battleMap, mech);

            int minRange = rangeCalculator.calculateAllWeaponsRange(pilot, mech);

            Optional<Node> flankingNode = Optional.of(availableNodes.stream()
                    .filter(node -> flankingCalculator.isFlankedFromPosition(node.getX(), node.getY(), target.get().getMech()))
                    .filter(node -> MathUtils.getDistance(node.getX(), node.getY(), mech.getX(), mech.getY()) <= minRange)
                    .max(new Comparator<Node>() {
                        @Override
                        public int compare(Node o1, Node o2) {
                            return Integer.compare((int) MathUtils.getDistance(o1.getX(), o1.getY(), mech.getX(), mech.getY()), (int) MathUtils.getDistance(o2.getX(), o2.getY(), mech.getX(), mech.getY()));
                        }
                    })).get();

            if (flankingNode.isPresent()) {
                target.get().setTargetNode(flankingNode.get());
                return target;
            }
            return target;
        }

        return Optional.empty();
    }

}