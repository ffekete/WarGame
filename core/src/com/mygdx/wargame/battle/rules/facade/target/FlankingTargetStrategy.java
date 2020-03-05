package com.mygdx.wargame.battle.rules.facade.target;

import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.overlay.TileOverlayType;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Pilot;
import com.mygdx.wargame.battle.rules.calculator.FlankingCalculator;
import com.mygdx.wargame.battle.rules.calculator.RangeCalculator;
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
    public Optional<Target> findTarget(Pilot pilot, Mech mech, Map<Mech, Pilot> targets, BattleMap battleMap, TargetingStrategy targetingStrategy) {

        Optional<Node> flankingNode;

        if(mech.getSelectedWeapons().isEmpty()) {

            System.out.println("Need to chill " + mech);

            Target target = new Target(null, null);

            flankingNode = findPerfectWaterAreaToCoolDown(mech, battleMap, targets);
            if(flankingNode.isPresent()) {
                target.setTargetNode(flankingNode.get());
                System.out.println(mech + " findPerfectWaterAreaToCoolDown");
                return Optional.of(target);
            }

            flankingNode = findPerfectWaterAreaToCoolDown_canBeFlanked(mech, battleMap);
            if(flankingNode.isPresent()) {
                target.setTargetNode(flankingNode.get());
                System.out.println(mech + " findPerfectWaterAreaToCoolDown_canBeFlanked");
                return Optional.of(target);
            }

            flankingNode = findPerfectWaterAreaToCoolDown_canBeFlanked_notOnWater(mech, battleMap);
            if(flankingNode.isPresent()) {
                target.setTargetNode(flankingNode.get());
                System.out.println(mech + " findPerfectWaterAreaToCoolDown_canBeFlanked_notOnWater");
                return Optional.of(target);
            }

            flankingNode = findPerfectWaterAreaToCoolDown_canBeFlanked_notOnWater_burning(mech, battleMap);
            if(flankingNode.isPresent()) {
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

            if (flankingCalculator.isFlankedFromPosition(mech.getX(), mech.getY(), target.get().getMech()) && battleMap.getFireMap()[(int) mech.getX()][(int) mech.getY()] == 0) {
                System.out.println(mech + " flankingCalculator.isFlankedFromPosition");
                return target;
            }

            int minRange = rangeCalculator.calculateAllWeaponsRange(pilot, mech);

            // find flanking position that is accessible in this turn, not burning
            flankingNode = findNotBurningAccessibleInOneTurnSpace(mech, battleMap, target, minRange);

            if (flankingNode.isPresent()) {
                System.out.println(mech + " findNotBurningAccessibleInOneTurnSpace");
                target.get().setTargetNode(flankingNode.get());
                return target;
            } else {
                // find flanking position around enemy, accessible in multiple turns
                flankingNode = getFlankingAreaAroundEnemyInWater(mech, battleMap, target, minRange);
            }


            if (flankingNode.isPresent()) {
                System.out.println(mech + " getFlankingAreaAroundEnemyInWater");
                target.get().setTargetNode(flankingNode.get());
            } else {
                flankingNode = getFlankingAreaAroundEnemyNotBurning(mech, battleMap, target, minRange);
            }

            if (flankingNode.isPresent()) {
                System.out.println(mech + " getFlankingAreaAroundEnemyNotBurning");
                target.get().setTargetNode(flankingNode.get());
            } else {
                flankingNode = getFlankingAreaAroundEnemyCoveredWithTrees(mech, battleMap, target, minRange);
            }

            if (flankingNode.isPresent()) {
                System.out.println(mech + " getFlankingAreaAroundEnemyCoveredWithTrees");
                target.get().setTargetNode(flankingNode.get());
            } else {
                flankingNode = getFlankingAreaAroundEnemy(mech, battleMap, target, minRange);
            }

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

    private Optional<Node> getFlankingAreaAroundEnemyNotBurning(Mech mech, BattleMap battleMap, Optional<Target> target, int minRange) {
        List<Node> availableNodes;
        Optional<Node> flankingNode;
        availableNodes = mapUtils.getAllAvailable(battleMap, target.get().getMech(), minRange);

        // find flanking position that is not accessible in this turn
        flankingNode = Optional.of(availableNodes.stream()
                .filter(node -> flankingCalculator.isFlankedFromPosition(node.getX(), node.getY(), target.get().getMech()))
                .filter(node -> MathUtils.getDistance(node.getX(), node.getY(), target.get().getMech().getX(), target.get().getMech().getY()) <= minRange)
                .filter(node -> battleMap.getFireMap()[(int) node.getX()][(int) node.getY()] == 0)
                .max(Comparator.comparingInt(o -> (int) MathUtils.getDistance(o.getX(), o.getY(), mech.getX(), mech.getY())))).get();
        return flankingNode;
    }

    private Optional<Node> getFlankingAreaAroundEnemyCoveredWithTrees(Mech mech, BattleMap battleMap, Optional<Target> target, int minRange) {
        List<Node> availableNodes;
        Optional<Node> flankingNode;
        availableNodes = mapUtils.getAllAvailable(battleMap, target.get().getMech(), minRange);

        // find flanking position that is accessible in this turn in wooden area
        flankingNode = Optional.of(availableNodes.stream()
                .filter(node -> flankingCalculator.isFlankedFromPosition(node.getX(), node.getY(), target.get().getMech()))
                .filter(node -> MathUtils.getDistance(node.getX(), node.getY(), target.get().getMech().getX(), target.get().getMech().getY()) <= minRange)
                .filter(node -> !mapUtils.nrOfTreesOnTile(stageElementsStorage, node.getX(), node.getY()).isEmpty())
                .max(Comparator.comparingInt(o -> (int) MathUtils.getDistance(o.getX(), o.getY(), mech.getX(), mech.getY())))).get();
        return flankingNode;
    }

    private Optional<Node> getFlankingAreaAroundEnemyInWater(Mech mech, BattleMap battleMap, Optional<Target> target, int minRange) {
        List<Node> availableNodes;
        Optional<Node> flankingNode;
        availableNodes = mapUtils.getAllAvailable(battleMap, target.get().getMech(), minRange);

        // find flanking position that is accessible in this turn in water
        flankingNode = Optional.of(availableNodes.stream()
                .filter(node -> flankingCalculator.isFlankedFromPosition(node.getX(), node.getY(), target.get().getMech()))
                .filter(node -> MathUtils.getDistance(node.getX(), node.getY(), target.get().getMech().getX(), target.get().getMech().getY()) <= minRange)
                .filter(node -> battleMap.getNodeGraphLv1().getNodeWeb()[(int) mech.getX()][(int) mech.getY()].getGroundOverlay() != null &&
                        battleMap.getNodeGraphLv1().getNodeWeb()[(int) mech.getX()][(int) mech.getY()].getGroundOverlay().getTileOverlayType() == TileOverlayType.Water)
                .max(Comparator.comparingInt(o -> (int) MathUtils.getDistance(o.getX(), o.getY(), mech.getX(), mech.getY())))).get();
        return flankingNode;
    }

    private Optional<Node> findNotBurningAccessibleInOneTurnSpace(Mech mech, BattleMap battleMap, Optional<Target> target, int minRange) {
        List<Node> availableNodes = mapUtils.getAllAvailable(battleMap, mech, mech.getMovementPoints());

        return Optional.of(availableNodes.stream()
                .filter(node -> flankingCalculator.isFlankedFromPosition(node.getX(), node.getY(), target.get().getMech()))
                .filter(node -> MathUtils.getDistance(node.getX(), node.getY(), target.get().getMech().getX(), target.get().getMech().getY()) <= minRange)

                // covered with trees
                .filter(node -> !mapUtils.nrOfTreesOnTile(stageElementsStorage, node.getX(), node.getY()).isEmpty())

                // not burning
                .filter(node -> battleMap.getFireMap()[(int) node.getX()][(int) node.getY()] == 0)

                .max(Comparator.comparingInt(o -> (int) MathUtils.getDistance(o.getX(), o.getY(), mech.getX(), mech.getY())))).get();
    }


    private Optional<Node> findPerfectWaterAreaToCoolDown(Mech mech, BattleMap battleMap, Map<Mech, Pilot> targets) {
        List<Node> availableNodes = mapUtils.getAllAvailable(battleMap, mech, mech.getMovementPoints());

        return Optional.of(availableNodes.stream()
                // nobody is flanking
                .filter(node -> targets.keySet().stream().noneMatch(target -> flankingCalculator.isFlankedFromPosition(target.getX(), target.getY(), mech)))
                // not burning
                .filter(node -> battleMap.getFireMap()[(int) node.getX()][(int) node.getY()] == 0)
                // on water
                .filter(node -> battleMap.getNodeGraphLv1().getNodeWeb()[(int) mech.getX()][(int) mech.getY()].getGroundOverlay() != null &&
                        battleMap.getNodeGraphLv1().getNodeWeb()[(int) mech.getX()][(int) mech.getY()].getGroundOverlay().getTileOverlayType() == TileOverlayType.Water)

                .max(Comparator.comparingInt(o -> (int) MathUtils.getDistance(o.getX(), o.getY(), mech.getX(), mech.getY())))).get();
    }

    private Optional<Node> findPerfectWaterAreaToCoolDown_canBeFlanked(Mech mech, BattleMap battleMap) {
        List<Node> availableNodes = mapUtils.getAllAvailable(battleMap, mech, mech.getMovementPoints());

        return Optional.of(availableNodes.stream()
                // not burning
                .filter(node -> battleMap.getFireMap()[(int) node.getX()][(int) node.getY()] == 0)
                // on water
                .filter(node -> battleMap.getNodeGraphLv1().getNodeWeb()[(int) mech.getX()][(int) mech.getY()].getGroundOverlay() != null &&
                        battleMap.getNodeGraphLv1().getNodeWeb()[(int) mech.getX()][(int) mech.getY()].getGroundOverlay().getTileOverlayType() == TileOverlayType.Water)

                .max(Comparator.comparingInt(o -> (int) MathUtils.getDistance(o.getX(), o.getY(), mech.getX(), mech.getY())))).get();
    }

    private Optional<Node> findPerfectWaterAreaToCoolDown_canBeFlanked_notOnWater(Mech mech, BattleMap battleMap) {
        List<Node> availableNodes = mapUtils.getAllAvailable(battleMap, mech, mech.getMovementPoints());

        return Optional.of(availableNodes.stream()
                // not burning
                .filter(node -> battleMap.getFireMap()[(int) node.getX()][(int) node.getY()] == 0)

                .max(Comparator.comparingInt(o -> (int) MathUtils.getDistance(o.getX(), o.getY(), mech.getX(), mech.getY())))).get();
    }

    private Optional<Node> findPerfectWaterAreaToCoolDown_canBeFlanked_notOnWater_burning(Mech mech, BattleMap battleMap) {
        List<Node> availableNodes = mapUtils.getAllAvailable(battleMap, mech, mech.getMovementPoints());

        return Optional.of(availableNodes.stream()
                .max(Comparator.comparingInt(o -> (int) MathUtils.getDistance(o.getX(), o.getY(), mech.getX(), mech.getY())))).get();
    }

}
