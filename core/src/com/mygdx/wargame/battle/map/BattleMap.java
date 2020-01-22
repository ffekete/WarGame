package com.mygdx.wargame.battle.map;

import com.mygdx.wargame.battle.unit.AbstractWarrior;
import com.mygdx.wargame.battle.unit.Man;
import com.sun.org.apache.xalan.internal.xsltc.dom.NodeSortRecord;

import java.util.*;

public class BattleMap {

    private int[][] map;
    private PathFinder pathFinder;

    private Map<AbstractWarrior, List<Node>> paths = new HashMap<>();

    public BattleMap(PathFinder pathFinder, int x, int y) {
        map = new int[x][y];
        this.pathFinder = pathFinder;
        this.pathFinder.init(map);
    }

    public List<Node> calculatePath(Node s, Node g, int proximity) {
        return pathFinder.findAStar(s, g, proximity);
    }

    public void addPath(AbstractWarrior man, Collection<Node> path) {
        paths.computeIfAbsent(man, value -> new ArrayList<>());
        paths.get(man).clear();
        paths.get(man).addAll(path);
    }

    public void setObstacle(float x, float y, int value) {
        this.pathFinder.getObstacleMap()[(int)x][(int)y].setTile(value);
    }

    public List<Node> getPath(AbstractWarrior abstractWarrior) {
        return paths.get(abstractWarrior);
    }

}
