package com.mygdx.wargame.battle.input;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.wargame.battle.lock.ActionLock;
import com.mygdx.wargame.battle.map.BattleMap;
import com.mygdx.wargame.battle.map.Node;
import com.mygdx.wargame.battle.map.movement.MovementMarkerFactory;
import com.mygdx.wargame.battle.screen.StageElementsStorage;
import com.mygdx.wargame.battle.screen.ui.localmenu.MechInfoPanelFacade;
import com.mygdx.wargame.battle.ui.WayPoint;
import com.mygdx.wargame.battle.ui.WayPointEnd;
import com.mygdx.wargame.battle.unit.action.AddMovementMarkersAction;
import com.mygdx.wargame.battle.unit.action.AddWayPointAction;
import com.mygdx.wargame.battle.unit.action.LockAction;
import com.mygdx.wargame.battle.unit.action.MoveActorAlongPathActionFactory;
import com.mygdx.wargame.battle.unit.action.RemoveWayPointAction;
import com.mygdx.wargame.battle.unit.action.UnlockAction;
import com.mygdx.wargame.mech.AbstractMech;
import com.mygdx.wargame.rules.facade.TurnProcessingFacade;
import com.mygdx.wargame.util.MathUtils;

public class GroundInputListener extends InputListener {

    private TurnProcessingFacade turnProcessingFacade;
    private BattleMap battleMap;
    private ActionLock actionLock;
    private MechInfoPanelFacade mechInfoPanelFacade;
    private StageElementsStorage stageElementsStorage;
    private MoveActorAlongPathActionFactory moveActorAlongPathActionFactory;
    private MovementMarkerFactory movementMarkerFactory;
    private AssetManager assetManager;


    public GroundInputListener(TurnProcessingFacade turnProcessingFacade, BattleMap battleMap, ActionLock actionLock, MechInfoPanelFacade mechInfoPanelFacade, StageElementsStorage stageElementsStorage, MovementMarkerFactory movementMarkerFactory, AssetManager assetManager) {
        this.turnProcessingFacade = turnProcessingFacade;
        this.battleMap = battleMap;

        this.actionLock = actionLock;
        this.mechInfoPanelFacade = mechInfoPanelFacade;
        this.stageElementsStorage = stageElementsStorage;
        this.movementMarkerFactory = movementMarkerFactory;
        this.moveActorAlongPathActionFactory = new MoveActorAlongPathActionFactory(stageElementsStorage, this.movementMarkerFactory);
        this.assetManager = assetManager;
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



            SequenceAction sequenceAction = new SequenceAction();
            sequenceAction.addAction(new LockAction(actionLock));

            for(int i = 1; i < paths.getCount(); i++) {
                WayPoint wayPoint;

                if(i < paths.getCount() -1)
                    wayPoint = new WayPoint(assetManager, stageElementsStorage);
                else {
                    wayPoint = new WayPointEnd(assetManager, stageElementsStorage);
                    System.out.println("Last angle:" + MathUtils.getAngle(new double[]{paths.get(i).getX(), paths.get(i).getY()}, new double[]{paths.get(i-1).getX(), paths.get(i-1).getY()}));
                    wayPoint.setRotation(180 + MathUtils.getAngle(new double[]{paths.get(i).getX(), paths.get(i).getY()}, new double[]{paths.get(i-1).getX(), paths.get(i-1).getY()}));
                }
                wayPoint.setPosition(paths.get(i).getX(), paths.get(i).getY());

                sequenceAction.addAction(new AddWayPointAction(stageElementsStorage, wayPoint));
            }

            sequenceAction.addAction(moveActorAlongPathActionFactory.act(paths, attacker, 0, battleMap));
            sequenceAction.addAction(new AddMovementMarkersAction(stageElementsStorage, movementMarkerFactory, battleMap, attacker));
            sequenceAction.addAction(new UnlockAction(actionLock, ""));


            sequenceAction.addAction(new RemoveWayPointAction(stageElementsStorage));

            attacker.addAction(sequenceAction);
            // attacker.addAction(new MovementAction(battleMap, attacker));
        }

        event.stop();
        return true;
    }


}