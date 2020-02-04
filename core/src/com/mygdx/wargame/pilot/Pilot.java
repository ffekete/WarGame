package com.mygdx.wargame.pilot;

import com.mygdx.wargame.component.targeting.TargetingModule;
import com.mygdx.wargame.component.weapon.Weapon;
import com.mygdx.wargame.component.weapon.WeaponType;
import com.mygdx.wargame.mech.Mech;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Pilot {

    private Map<Skill, Integer> skills;
    private Set<Perks> perks;
    private Mech mech;

    public Pilot(Map<Skill, Integer> skills, Set<Perks> perks) {
        this.skills = skills;
        this.perks = perks;
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
}
