package com.mygdx.wargame.battle.screen.ui.localmenu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static com.mygdx.wargame.config.Config.*;

public class BigInfoPanelMovementHandler {

    public boolean moveBigInfoPanelToLocalButton(Actor detailsButton, Actor bigInfoPanelContainer, Table mechInfoTable, boolean bigInfoPanelHidden) {
        if (bigInfoPanelHidden) {

            ParallelAction parallelAction = new ParallelAction();

            SizeToAction sizeToAction = new SizeToAction();
            sizeToAction.setSize(HUD_VIEWPORT_WIDTH, HUD_VIEWPORT_HEIGHT);
            sizeToAction.setDuration(0.25f);
            parallelAction.addAction(sizeToAction);

            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setPosition(0, 0);
            moveToAction.setDuration(0.25f);
            parallelAction.addAction(moveToAction);

            bigInfoPanelContainer.addAction(parallelAction);
            mechInfoTable.addAction(parallelAction);
            bigInfoPanelContainer.setVisible(true);
            mechInfoTable.setVisible(true);
            return false;
        } else {
            SequenceAction sequenceAction = new SequenceAction();

            ParallelAction parallelAction = new ParallelAction();

            SizeToAction sizeToAction = new SizeToAction();
            sizeToAction.setHeight(0);
            sizeToAction.setWidth(0);
            sizeToAction.setDuration(0.25f);
            parallelAction.addAction(sizeToAction);

            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setPosition(detailsButton.getX(), detailsButton.getY());
            moveToAction.setDuration(0.25f);
            parallelAction.addAction(moveToAction);

            VisibleAction visibleAction = new VisibleAction();
            visibleAction.setVisible(false);


            sequenceAction.addAction(parallelAction);
            sequenceAction.addAction(visibleAction);

            bigInfoPanelContainer.addAction(sequenceAction);
            mechInfoTable.setVisible(false);
            return true;
        }
    }
}
