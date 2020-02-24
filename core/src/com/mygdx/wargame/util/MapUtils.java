package com.mygdx.wargame.util;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.utils.Array;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Edge;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.decoration.TreeImage;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.mech.Mech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapUtils {

    public Map<Node, Integer> getAllAvailableWithMovementPointsCost(BattleMap battleMap, Mech mech) {

        Node start = battleMap.getNodeGraphLv1().getNodeWeb()[(int)mech.getX()][(int)mech.getY()];

        Map<Node, Integer> allAvailable = new HashMap();
        List<Node> openNodes = new ArrayList<>();
        Set<Node> closedNodes = new HashSet<>();

        Array<Edge> edges = battleMap.getNodeGraphLv1().getNodeEdges().get(start);
        for(int i = 0; i < edges.size; i++) {
            openNodes.add(edges.get(i).getToNode());
        }
        //closedNodes.add(start);

        while(!openNodes.isEmpty()) {

            Node next = openNodes.remove(0);

            if(!closedNodes.contains(next)) {
                closedNodes.add(next);
                GraphPath graphPath = battleMap.calculatePath(start, next);
                if (graphPath.getCount() <= mech.getMovementPoints() + 1) {
                    allAvailable.put(next, graphPath.getCount() -1);
                    edges = battleMap.getNodeGraphLv1().getNodeEdges().get(next);
                    for (int i = 0; i < edges.size; i++) {
                        openNodes.add(edges.get(i).getToNode());
                    }
                }
            }
        }

        return allAvailable;
    }

    public List<Node> getAllAvailable(BattleMap battleMap, Mech mech) {
        Node start = battleMap.getNodeGraphLv1().getNodeWeb()[(int)mech.getX()][(int)mech.getY()];

        List<Node> allAvailable = new ArrayList<>();
        List<Node> openNodes = new ArrayList<>();
        Set<Node> closedNodes = new HashSet<>();

        Array<Edge> edges = battleMap.getNodeGraphLv1().getNodeEdges().get(start);
        for(int i = 0; i < edges.size; i++) {
            openNodes.add(edges.get(i).getToNode());
        }
        //closedNodes.add(start);

        while(!openNodes.isEmpty()) {

            Node next = openNodes.remove(0);

            if(!closedNodes.contains(next)) {
                closedNodes.add(next);
                if (battleMap.calculatePath(start, next).getCount() <= mech.getMovementPoints() + 1) {
                    allAvailable.add(next);
                    edges = battleMap.getNodeGraphLv1().getNodeEdges().get(next);
                    for (int i = 0; i < edges.size; i++) {
                        openNodes.add(edges.get(i).getToNode());
                    }
                }
            }
        }

        return allAvailable;
    }

    public List<TreeImage> nrOfTreesOnTile(StageElementsStorage stageElementsStorage, float tx, float ty) {

        List<TreeImage> found = new ArrayList<>();

        for (int i = 0; i < stageElementsStorage.mechLevel.getChildren().size; i++) {
            if (stageElementsStorage.mechLevel.getChild(i).getClass().isAssignableFrom(TreeImage.class)
                    && stageElementsStorage.mechLevel.getChild(i).getX() >= tx - 1 && stageElementsStorage.mechLevel.getChild(i).getX() <= tx + 1 &&
                    stageElementsStorage.mechLevel.getChild(i).getY() >= ty - 1 && stageElementsStorage.mechLevel.getChild(i).getY() <= ty + 1) {
                found.add((TreeImage) stageElementsStorage.mechLevel.getChild(i));
            }
        }

        return found;
    }
}
