package com.mygdx.wargame.common.pilot;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PilotCreator {

    private AssetManager assetManager;

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

    List<String> portraits = ImmutableList.<String>builder()
            .add("portraits/Portrait01.png")
            .add("portraits/Portrait02.png")
            .add("portraits/Portrait03.png")
            .add("portraits/Portrait04.png")
            .add("portraits/Portrait05.png")
            .build();

    public PilotCreator(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

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

        String portrait = portraits.get(new Random().nextInt(portraits.size()));

        Pilot pilot = new Pilot(skills,
                new HashSet<>(),
                new TextureRegionDrawable(assetManager.get(portrait, Texture.class)));

        Arrays.stream(Perks.values()).forEach(perk -> {
            if (new Random().nextInt(10) == 0) {
                pilot.getPerks().add(perk);
            }
        });

        pilot.setName((String)names.toArray()[new Random().nextInt(names.size())]);

        return pilot;
    }
}
