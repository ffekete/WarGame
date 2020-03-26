package com.mygdx.wargame.common.pilot;

import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PilotCreator {

    Set<String> names = ImmutableSet.<String>builder()
            .add("Ace")
            .add("Brave")
            .add("Huston")
            .add("Bravo")
            .add("Charlie")
            .add("Alpha")
            .add("Taurus")
            .add("Eagle")
            .add("Hawk")
            .add("Patch")
            .add("Dirk")
            .add("Kirk")
            .add("Longshot")
            .add("Dragon")
            .add("Slime")
            .add("Snail")
            .add("Croc")
            .add("Diamond")
            .build();

    public Pilot getPilot() {

        Map<Skill, Integer> skills = new HashMap<>();

        skills.put(Skill.Entrenching, new Random().nextInt(10));
        skills.put(Skill.Targeting, new Random().nextInt(10));
        skills.put(Skill.PlasmaWeapons, new Random().nextInt(10));
        skills.put(Skill.Ballistics, new Random().nextInt(10));
        skills.put(Skill.Flamer, new Random().nextInt(10));
        skills.put(Skill.IonWeapons, new Random().nextInt(10));
        skills.put(Skill.Lasers, new Random().nextInt(10));
        skills.put(Skill.Missiles, new Random().nextInt(10));
        skills.put(Skill.Evading, new Random().nextInt(10));
        skills.put(Skill.Melee, new Random().nextInt(10));

        Pilot pilot = new Pilot(skills,
                new HashSet<>()
        );

        Arrays.stream(Perks.values()).forEach(perk -> {
            if (new Random().nextInt(10) == 0) {
                pilot.getPerks().add(perk);
            }
        });

        pilot.setName((String)names.toArray()[new Random().nextInt(names.size())]);

        return pilot;
    }
}
