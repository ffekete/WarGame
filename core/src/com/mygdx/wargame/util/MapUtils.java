package com.mygdx.wargame.util;

import com.badlogic.gdx.utils.Array;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Edge;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.mech.Mech;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapUtils {

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

}
