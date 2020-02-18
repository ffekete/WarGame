package com.mygdx.wargame.battle.screen;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.wargame.battle.ui.MovementMarker;

import java.util.ArrayList;
import java.util.List;

public class StageElementsStorage {

    public Group groundLevel = new Group();
    public Group mechLevel = new Group();
    public Group treeLevel = new Group();
    public Group airLevel = new Group();

    public Stage stage;
    public Stage hudStage;
    public Stage textStage;

    public List<MovementMarker> movementMarkerList = new ArrayList<>();

}
