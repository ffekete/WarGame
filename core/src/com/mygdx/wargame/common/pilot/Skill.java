package com.mygdx.wargame.common.pilot;

public enum Skill {

    Piloting("piloting", "For increased mech movement (%s)"),
    Evading("evading", "For increased attack evasion (%s)"),
    Targeting("targeting", "For increased hit chance (%s)"),
    Lasers("laser weapons", "Increased hit chance for lasers (%s)"),
    Missiles("missile weapons", "Increased hit chance for missiles (%s)"),
    Ballistics("ballistic weapons", "Increased hit chance for ballistic weapons (%s)"),
    IonWeapons("ion weapons", "Increased hit chance for ion weapons (%s)"),
    PlasmaWeapons("plasma weapons", "Increased hit chance for plasma weapons (%s)"),
    Flamer("flamer", "Increased hit chance for flamer (%s)"),
    Entrenching("entrenching", "Lower stability damage for mech (%s)");

    private String name;
    private String description;

    Skill(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
