package com.mygdx.wargame.battle.map;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathFinder {

    public static final float DEFAULT_EFFORT = 4;

    private static final int[][] availableNodesForCheck =
            new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

    private int width;
    private int height;

    private Node[][] obstacleMap;
    private ShapeRenderer shapeRenderer;

    public PathFinder() {
    }

    public PathFinder(int width, int height, ShapeRenderer shapeRenderer) {
        this.width = width;
        this.height = height;
        this.shapeRenderer = shapeRenderer;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Node[][] getObstacleMap() {
        return obstacleMap;
    }

    public void addObstacle(int x, int y, int value) {
        obstacleMap[x][y].setTile(value);
    }

    public List<Node> findAStar(Node start, Node target) {

        Node[][] map = getObstacleMap();
        List<Node> openNodes = new ArrayList<>();
        List<Node> closedNodes = new ArrayList<>();
        List<Node> path = new ArrayList<>();

        Node startNode = new Node(map[(int)start.getX()][(int)start.getY()].getTile(), start.getX(), start.getY(), DEFAULT_EFFORT, shapeRenderer);

        startNode.setX(start.getX());
        startNode.setY(start.getY());
        startNode.effort = map[(int)startNode.getX()][(int)startNode.getY()].effort;

        startNode.g = startNode.f = startNode.h = 0f;
        startNode.setParent(null);

        Node end = new Node(0, 0, 0, DEFAULT_EFFORT, shapeRenderer);
        end.setTile(map[(int)target.getX()][(int)target.getY()].getTile());
        end.setX(target.getX());
        end.setY(target.getY());

        end.g = end.h = end.f = 0f;

        end.effort = map[(int)end.getX()][(int)end.getY()].effort;
        end.setParent(null);

        openNodes.add(startNode);

        while (!openNodes.isEmpty()) {
            Node current = openNodes.get(0);

            for (int i = 1; i < openNodes.size(); i++) {
                if (openNodes.get(i).f < current.f) {
                    current = openNodes.get(i);
                }
            }

            openNodes.remove(current);
            closedNodes.add(current);

            if ((distance(current, new Node(0, target.getX(), target.getY(), 0.f, shapeRenderer)) < 30) ||
                    map[(int)target.getX()][(int)target.getY()].getTile() == 1 && distance(current, new Node(0, target.getX(), target.getY(), 0.f, shapeRenderer)) < 10
            ) {
                // hurra
                Node c = current;
                while (c != null) {
                    Node c1 = new Node(c.getTile(), c.getX(), c.getY(), c.effort, shapeRenderer);
                    path.add(c1);
                    c = c.getParentNode();
                }
                return path;
            }

            List<Node> children = new ArrayList<>();
            // add children
            for (int[] i : availableNodesForCheck) {
                float x = current.getX() - i[0];
                float y = current.getY() - i[1];

                if (x >= 0 && x < width && y >= 0 && y < height) {

                    if (map[(int)x][(int)y].getTile() == 1) {
                        continue;
                    }

                    Node child = new Node(0, 0, 0, DEFAULT_EFFORT, shapeRenderer);
                    child.setTile(map[(int)x][(int)y].getTile());
                    child.setX(x);
                    child.setY(y);
                    child.effort = map[(int)x][(int)y].effort;

                    child.setParent(current);

                    children.add(child);
                }
            }

            // calculate f,g,h
            for (Node child : children) {
                if (closedNodes.contains(child))
                    continue;
                child.g = current.g + child.effort;
                child.h = (float) distance(child, end);
                child.f = child.g + child.h;

                if (openNodes.contains(child)) {
                    if (child.g > openNodes.get(openNodes.indexOf(child)).g) {
                        continue;

                    }
                } else
                    openNodes.add(child);
            }
        }

        return Collections.emptyList();
    }

    public static void main(String[] args) {
        PathFinder pathFinder = new PathFinder(100, 100, null);
        int[][] obstacleMap = new int[pathFinder.getWidth()][pathFinder.getHeight()];
        pathFinder.init(obstacleMap);

        pathFinder.getObstacleMap()[0][0].effort = 1f;
        pathFinder.getObstacleMap()[0][1].effort = 1f;
        pathFinder.getObstacleMap()[0][2].effort = 1f;
        pathFinder.getObstacleMap()[0][3].effort = 1f;
        pathFinder.getObstacleMap()[0][4].effort = 1f;
        pathFinder.getObstacleMap()[0][5].effort = 1f;
        pathFinder.getObstacleMap()[0][6].effort = 1f;
        pathFinder.getObstacleMap()[0][7].effort = 1f;
        pathFinder.getObstacleMap()[0][8].effort = 1f;
        pathFinder.getObstacleMap()[0][9].effort = 1f;
        pathFinder.getObstacleMap()[0][10].effort = 1f;
        pathFinder.getObstacleMap()[0][11].effort = 1f;
        pathFinder.getObstacleMap()[0][12].effort = 1f;

        int[][] resultMap = new int[pathFinder.getWidth()][pathFinder.getHeight()];
        addObstacle(pathFinder, resultMap, 98, 98);

        addObstacle(pathFinder, resultMap, 97, 98);
        addObstacle(pathFinder, resultMap, 97, 99);

        addObstacle(pathFinder, resultMap, 1, 0);

        addObstacle(pathFinder, resultMap, 1, 1);

        addObstacle(pathFinder, resultMap, 1, 2);


        long start = System.currentTimeMillis();
        List<Node> path = pathFinder.findAStar(new Node(0, 0, 0, 0, null), new Node(0, 98, 99, 0, null));
        System.out.println(System.currentTimeMillis() - start);

        int i = 1;
        for (Node node : path) {
            resultMap[(int)node.getX()][(int)node.getY()] = 5;
            i++;
        }

        pathFinder.print(resultMap);
    }

    private static void addObstacle(PathFinder pathFinder, int[][] resultMap, int i2, int i3) {
        pathFinder.addObstacle(i2, i3, 1);
        resultMap[i2][i3] = 1;
    }

    private void print(int[][] resultMap) {

        for (int k = 0; k < width; k++) {
            for (int l = 0; l < height; l++) {
                System.out.print(resultMap[l][k]);
            }
            System.out.println("");
        }
    }

    public void init(int[][] map) {
        obstacleMap = new Node[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                obstacleMap[i][j] = new Node(map[i][j], i, j, DEFAULT_EFFORT, shapeRenderer);
            }
        }
    }

    private static float distance(Node n1, Node n2) {
        float a = Math.abs(n2.getX() - n1.getX());
        float b = Math.abs(n2.getY() - n1.getY());
        return a * a + b * b;
    }
}