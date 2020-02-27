package com.mygdx.wargame.rules.facade.target;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.config.Config;
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

            if(flankingCalculator.isFlankedFromPosition(mech.getX(), mech.getY(), target.get().getMech()) && battleMap.getFireMap()[(int)mech.getX()][(int)mech.getY()] == 0) {
                return target;
            }

            List<Node> availableNodes = mapUtils.getAllAvailable(battleMap, mech, mech.getMovementPoints());

            int minRange = rangeCalculator.calculateAllWeaponsRange(pilot, mech);

            Node start = new Node(mech.getX(), mech.getY());

            // find flanking position that is accessible in this turn, not burning
            Optional<Node> flankingNode = Optional.of(availableNodes.stream()
                    .filter(node -> flankingCalculator.isFlankedFromPosition(node.getX(), node.getY(), target.get().getMech()))
                    .filter(node -> MathUtils.getDistance(node.getX(), node.getY(), target.get().getMech().getX(), target.get().getMech().getY()) <= minRange)
                    .filter(node -> battleMap.getFireMap()[(int)node.getX()][(int)node.getY()] == 0)
                    //.filter(node -> battleMap.getNodeGraphLv1().findPath(start, node).getCount() -1 <= mech.getMovementPoints())
                    .max(Comparator.comparingInt(o -> (int) MathUtils.getDistance(o.getX(), o.getY(), mech.getX(), mech.getY())))).get();

            if (flankingNode.isPresent()) {
                target.get().setTargetNode(flankingNode.get());
                return target;
            } else {
                // find flanking position around enemy, accessible in multiple turns
                availableNodes = mapUtils.getAllAvailable(battleMap, target.get().getMech(), minRange);

                // find flanking position that is accessible in this turn
                flankingNode = Optional.of(availableNodes.stream()
                        .filter(node -> flankingCalculator.isFlankedFromPosition(node.getX(), node.getY(), target.get().getMech()))
                        .filter(node -> MathUtils.getDistance(node.getX(), node.getY(), target.get().getMech().getX(), target.get().getMech().getY()) <= minRange)
                        .filter(node -> battleMap.getFireMap()[(int)node.getX()][(int)node.getY()] == 0)
                        .max(Comparator.comparingInt(o -> (int) MathUtils.getDistance(o.getX(), o.getY(), mech.getX(), mech.getY())))).get();
            }


            flankingNode.ifPresent(node -> target.get().setTargetNode(node));

            return target;
        }

        return Optional.empty();
    }

}
