package com.mygdx.wargame.pilot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class PilotCreator {
    public Pilot getPilot() {
        return new Pilot(ImmutableMap.
                <Skill, Integer>builder()
                .put(Skill.Entrenching, 5)
                .put(Skill.Targeting, 5)
                .put(Skill.PlasmaWeapons, 5)
                .put(Skill.Ballistics, 5)
                .put(Skill.Flamer, 5)
                .put(Skill.IonWeapons, 5)
                .put(Skill.Lasers, 5)
                .put(Skill.Missiles, 5)
                .put(Skill.Evading, 5)
                .put(Skill.Piloting, 5)
                .put(Skill.Shields, 5)
                .build(),
                ImmutableSet.of()
        );
    }
}
