package com.mygdx.wargame.battle.input;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.screen.ui.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.battle.unit.action.MoveActorAlongPathActionCreator;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;

public class GroundInputListener extends InputListener {

    private TurnProcessingFacade turnProcessingFacade;
    private BattleMap battleMap;
    private ActionLock actionLock;
    private MechInfoPanelFacade mechInfoPanelFacade;

    public GroundInputListener(TurnProcessingFacade turnProcessingFacade, BattleMap battleMap, ActionLock actionLock, MechInfoPanelFacade mechInfoPanelFacade) {
        this.turnProcessingFacade = turnProcessingFacade;
        this.battleMap = battleMap;

        this.actionLock = actionLock;
        this.mechInfoPanelFacade = mechInfoPanelFacade;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        mechInfoPanelFacade.hideLocalMenu();

        if (actionLock.isLocked()) {
            event.stop();
            return true;
        }

        AbstractMech attacker = (AbstractMech) turnProcessingFacade.getNext().getKey();

        if (attacker != null) {

            Node start = battleMap.getNodeGraphLv1().getNodeWeb()[(int) attacker.getX()][(int) attacker.getY()];
            Node end = battleMap.getNodeGraphLv1().getNodeWeb()[(int)x][(int)y];

            // reconnect so that attacker can move
            battleMap.getNodeGraphLv1().reconnectCities(battleMap.getNodeGraphLv1().getNodeWeb()[(int)attacker.getX()][(int)attacker.getY()]);

            GraphPath<Node> paths = battleMap.calculatePath(start, end);
            battleMap.addPath(attacker, paths);

            attacker.addAction(new MoveActorAlongPathActionCreator(paths, attacker, 0, battleMap).act());
            // attacker.addAction(new MovementAction(battleMap, attacker));
        }

        event.stop();
        return true;
    }


}