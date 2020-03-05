package com.mygdx.wargame.battle.rules.facade.target;

import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.common.mech.Mech;
import com.mygdx.wargame.common.pilot.Pilot;

public class Target {

    private Pilot pilot;
    private Mech mech;
    private Node targetNode;

    public Target(Mech mech, Pilot pilot) {
        this.pilot = pilot;
        this.mech = mech;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public Mech getMech() {
        return mech;
    }

    public Node getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(Node targetNode) {
        this.targetNode = targetNode;
    }
}
