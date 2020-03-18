package com.mygdx.wargame.battle.screen;

import com.mygdx.wargame.common.mech.AbstractMech;
import com.mygdx.wargame.common.pilot.Pilot;

import java.util.HashMap;
import java.util.Map;

public class BattleScreenInputData {

    private Map<AbstractMech, Pilot> playerTeam = new HashMap<>(),
            aiTeam = new HashMap<>();

    public void setPlayerTeam(Map<AbstractMech, Pilot> playerTeam) {
        this.playerTeam.clear();
        this.playerTeam.putAll(playerTeam);
    }

    public void setAiTeam(Map<AbstractMech, Pilot> aiTeam) {
        this.aiTeam.clear();
        this.aiTeam.putAll(aiTeam);
    }

    public Map<AbstractMech, Pilot> getPlayerTeam() {
        return playerTeam;
    }

    public Map<AbstractMech, Pilot> getAiTeam() {
        return aiTeam;
    }
}
