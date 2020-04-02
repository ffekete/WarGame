package com.mygdx.wargame.common.pilot;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.wargame.common.mech.Mech;

import java.util.Map;
import java.util.Set;

public class Pilot {

    private Map<Skill, Integer> skills;
    private Set<Perks> perks;
    private Mech mech;
    private String name;
    private TextureRegionDrawable textureRegionDrawable;

    public Pilot(Map<Skill, Integer> skills, Set<Perks> perks, TextureRegionDrawable textureRegionDrawable) {
        this.skills = skills;
        this.perks = perks;
        this.textureRegionDrawable = textureRegionDrawable;
    }

    public Map<Skill, Integer> getSkills() {
        return skills;
    }

    public boolean hasPerk(Perks perk) {
        return perks.contains(perk);
    }


    public Set<Perks> getPerks() {
        return perks;
    }

    public Mech getMech() {
        return mech;
    }

    public void setSkills(Map<Skill, Integer> skills) {
        this.skills = skills;
    }

    public void setPerks(Set<Perks> perks) {
        this.perks = perks;
    }

    public void setMech(Mech mech) {
        this.mech = mech;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TextureRegionDrawable getTextureRegionDrawable() {
        return textureRegionDrawable;
    }
}
