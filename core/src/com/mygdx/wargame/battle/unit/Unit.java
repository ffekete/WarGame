package com.mygdx.wargame.battle.unit;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Unit extends Actor {

    private Set<AbstractWarrior> entities;
    private Team team;
    private AbstractWarrior[][] layout;
    private int cx = 0, cy = 0;
    private int widht, height;

    public Unit(int x, int y) {
        this.entities = new HashSet<>();
        this.layout = new AbstractWarrior[x][y];
        this.widht = x;
        this.height = y;
    }

    public void add(AbstractWarrior man) {

        if(cy >= height) {
            System.out.println("cx: " + cx + " cy: " + cy);
            throw new IllegalStateException();
        }

        this.entities.add(man);
        layout[cx][cy] = man;

        cx++;
        if(cx >= widht) {
            cx = 0;
            cy++;
        }
    }

    public Set<AbstractWarrior> getAll() {
        return entities;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public AbstractWarrior[][] getLayout() {
        return layout;
    }

    public double[] getCenter() {
        double x = (getRight(this) + getLeft(this)) / 2;
        double y = (getUpper(this) + getLower(this)) / 2;
        return new double[]{x,y};
    }

    private double getLeft(Unit unit) {
        return unit.getAll().stream().min(new Comparator<AbstractWarrior>() {
            @Override
            public int compare(AbstractWarrior o1, AbstractWarrior o2) {
                return Double.compare(o1.getX(),o2.getX());
            }
        }).get().getX();
    }

    private double getRight(Unit unit) {
        return unit.getAll().stream().min(new Comparator<AbstractWarrior>() {
            @Override
            public int compare(AbstractWarrior o1, AbstractWarrior o2) {
                return Double.compare(o2.getX(),o1.getX());
            }
        }).get().getX();
    }

    private double getUpper(Unit unit) {
        return unit.getAll().stream().min(new Comparator<AbstractWarrior>() {
            @Override
            public int compare(AbstractWarrior o1, AbstractWarrior o2) {
                return Double.compare(o1.getY(),o2.getY());
            }
        }).get().getY();
    }

    private double getLower(Unit unit) {
        return unit.getAll().stream().min(new Comparator<AbstractWarrior>() {
            @Override
            public int compare(AbstractWarrior o1, AbstractWarrior o2) {
                return Double.compare(o2.getY(),o1.getY());
            }
        }).get().getY();
    }
}
