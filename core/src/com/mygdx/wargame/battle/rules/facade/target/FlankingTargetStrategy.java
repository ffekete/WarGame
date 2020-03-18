package com.mygdx.wargame.battle.rules.facade.target;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.rules.calculator.FlankingCalculator;
import com.mygdx.wargame.battle.rules.calculator.RangeCalculator;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Pilot;
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
    private StageElementsStorage stageElementsStorage;

    public FlankingTargetStrategy(StageElementsStorage stageElementsStorage) {
        this.stageElementsStorage = stageElementsStorage;
    }

    @Override
    public Optional<Target> findTarget(Pilot pilot, AbstractMech mech, Map<AbstractMech, Pilot> targets, BattleMap battleMap, TargetingStrategy targetingStrategy) {

        Optional<Node> flankingNode;

        if (mech.getSelectedWeapons().isEmpty()) {

            System.out.println("Need to chill " + mech);

            Target target = new Target(null, null);

            flankingNode = findPerfectWaterAreaToCoolDown_canBeFlanked_notOnWater_burning(mech, battleMap);
            if (flankingNode.isPresent()) {
                target.setTargetNode(flankingNode.get());
                System.out.println(mech + " findPerfectWaterAreaToCoolDown_canBeFlanked_notOnWater_burning");
                return Optional.of(target);
            }
            System.out.println("But cnnot chill for some reason " + mech);
        }

        // if no place to chill, look for attack!

        System.out.println("Attacking instead of chill " + mech);
        Optional<Target> target = targetingStrategy.findTarget(pilot, mech, targets, battleMap, null);

        System.out.println(mech + " found target: " + (target.isPresent() ? target.get() : target));

        if (target.isPresent()) {

            int minRange = rangeCalculator.calculateAllWeaponsRange(pilot, mech);
            flankingNode = getFlankingAreaAroundEnemy(mech, battleMap, target, minRange);
            System.out.println(mech + " getFlankingAreaAroundEnemy: " + flankingNode);

            flankingNode.ifPresent(node -> target.get().setTargetNode(node));

            return target;
        }

        return Optional.empty();
    }

    private Optional<Node> getFlankingAreaAroundEnemy(Mech mech, BattleMap battleMap, Optional<Target> target, int minRange) {
        List<Node> availableNodes;
        Optional<Node> flankingNode;
        availableNodes = mapUtils.getAllAvailable(battleMap, target.get().getMech(), minRange);

        // find flanking position that is not accessible in this turn
        flankingNode = Optional.of(availableNodes.stream()
                .filter(node -> flankingCalculator.isFlankedFromPosition(node.getX(), node.getY(), target.get().getMech()))
                .filter(node -> MathUtils.getDistance(node.getX(), node.getY(), target.get().getMech().getX(), target.get().getMech().getY()) <= minRange)
                .max(Comparator.comparingInt(o -> (int) MathUtils.getDistance(o.getX(), o.getY(), mech.getX(), mech.getY())))).get();
        return flankingNode;
    }


    private Optional<Node> findPerfectWaterAreaToCoolDown_canBeFlanked_notOnWater_burning(Mech mech, BattleMap battleMap) {
        List<Node> availableNodes = mapUtils.getAllAvailable(battleMap, mech, mech.getMovementPoints());

        return Optional.of(availableNodes.stream()
                .max(Comparator.comparingInt(o -> (int) MathUtils.getDistance(o.getX(), o.getY(), mech.getX(), mech.getY())))).get();
    }

}
