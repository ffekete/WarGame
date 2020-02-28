package com.mygdx.wargame.mech;

public enum BodyPart {
    LeftLeg("Left leg"), RightLeg("Right leg"), Torso("Torso"), LeftArm("Left arm"), RightArm("Right arm"), Head("Head");

    private String name;

    BodyPart(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
