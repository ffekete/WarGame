package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.wargame.battle.map.BattleMapConfig;
import com.mygdx.wargame.battle.ui.MovementMarker;
import com.mygdx.wargame.battle.ui.WayPoint;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StageElementsStorage {

    public Group groundLevel = new Group();
    public Group mechLevel = new SortedGroup();
    public Group airLevel = new Group();

    public Stage stage;
    public Stage hudStage;

    public List<MovementMarker> movementMarkerList = new ArrayList<>();
    public List<WayPoint> wayPoints = new ArrayList<>();

    public static class SortedGroup extends Group {

        @Override
        protected void drawChildren (Batch batch, float parentAlpha) {
            parentAlpha *= super.getColor().a;
            SnapshotArray<Actor> children = this.getChildren();

            long st = System.currentTimeMillis();
            children.sort(new Comparator<Actor>() {
                @Override
                public int compare(Actor o1, Actor o2) {
                    return Float.compare(o2.getY(), o1.getY());
                }
            });

            System.out.println("Elapsed: " + (System.currentTimeMillis() - st));

            Actor[] actors = children.begin();
            Rectangle cullingArea = this.getCullingArea();
            if (cullingArea != null) {
                // Draw children only if inside culling area.
                float cullLeft = cullingArea.getX();
                float cullRight = cullLeft + cullingArea.getWidth();
                float cullBottom = cullingArea.getY();
                float cullTop = cullBottom + cullingArea.getHeight();
                if (super.isTransform()) {
                    for (int i = 0, n = children.size; i < n; i++) {
                        Actor child = actors[i];
                        if (!child.isVisible()) continue;
                        float cx = child.getX(), cy = child.getY();
                        if (cx <= cullRight && cy <= cullTop && cx + child.getWidth() >= cullLeft && cy + child.getHeight() >= cullBottom)
                            child.draw(batch, parentAlpha);
                    }
                } else {
                    // No transform for this group, offset each child.
                    float offsetX = getX(), offsetY = getY();
                    setX(0);
                    setY(0);
                    for (int i = 0, n = children.size; i < n; i++) {
                        Actor child = actors[i];
                        if (!child.isVisible()) continue;
                        float cx = child.getX(), cy = child.getY();
                        if (cx <= cullRight && cy <= cullTop && cx + child.getWidth() >= cullLeft && cy + child.getHeight() >= cullBottom) {
                            child.setX(cx + offsetX);
                            child.setY(cy + offsetY);
                            child.draw(batch, parentAlpha);
                            child.setX(cx);
                            child.setY(cy);
                        }
                    }
                    setX(offsetX);
                    setY(offsetY);
                }
            } else {
                // No culling, draw all children.
                if (isTransform()) {
                    for (int i = 0, n = children.size; i < n; i++) {
                        Actor child = actors[i];
                        if (!child.isVisible()) continue;
                        child.draw(batch, parentAlpha);
                    }
                } else {
                    // No transform for this group, offset each child.
                    float offsetX = getX(), offsetY = getY();
                    setX(0);
                    setY(0);
                    for (int i = 0, n = children.size; i < n; i++) {
                        Actor child = actors[i];
                        if (!child.isVisible()) continue;
                        float cx = child.getX(), cy = child.getY();
                        child.setX(cx + offsetX);
                        child.setY(cy + offsetY);
                        child.draw(batch, parentAlpha);
                        child.setX(cx);
                        child.setY(cy);
                    }
                    setX(offsetX);
                    setY(offsetY);
                }
            }
            children.end();
        }
        

    }


}
