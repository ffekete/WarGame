package com.mygdx.wargame.battle.map;

import com.mygdx.wargame.battle.unit.Man;

import java.util.*;

public class BattleMap {

    private int[][] map;
    private PathFinder pathFinder;

    private Map<Man, List<Node>> paths = new HashMap<>();


    public BattleMap(PathFinder pathFinder, int x, int y) {
        map = new int[x][y];
        this.pathFinder = pathFinder;
        this.pathFinder.init(map);
    }

    public List<Node> calculatePath(Node s, Node g) {
        return pathFinder.findAStar(s, g);
    }

    public void addPath(Man man, Collection<Node> path) {
        paths.computeIfAbsent(man, value -> new ArrayList<>());
        paths.get(man).clear();
        paths.get(man).addAll(path);
    }

}
