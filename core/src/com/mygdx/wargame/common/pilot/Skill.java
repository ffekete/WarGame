package com.mygdx.wargame.common.pilot;

public enum Skill {

    Melee("Melee", "For increased mech melee hit chance"),
    Evading("evading", "For increased attack evasion"),
    Targeting("targeting", "For increased hit chance"),
    Lasers("laser weapons", "Increased hit chance for laser"),
    Missiles("missile weapons", "Increased hit chance for missiles"),
    Ballistics("ballistic weapons", "Increased hit chance for ballistic weapon"),
    IonWeapons("ion weapons", "Increased hit chance for ion weapons"),
    PlasmaWeapons("plasma weapons", "Increased hit chance for plasma weapons"),
    Flamer("flamer", "Increased hit chance for flamer"),
    Entrenching("entrenching", "Lower stability damage for mech");

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
