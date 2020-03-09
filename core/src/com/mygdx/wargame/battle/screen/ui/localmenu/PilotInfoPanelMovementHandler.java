package com.mygdx.wargame.battle.screen.ui.localmenu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static com.mygdx.wargame.config.Config.HUD_VIEWPORT_HEIGHT;
import static com.mygdx.wargame.config.Config.HUD_VIEWPORT_WIDTH;

public class PilotInfoPanelMovementHandler {

    public boolean moveBigInfoPanelToLocalButton(Button button, Table outerTable, boolean bigInfoPanelHidden) {
        if (bigInfoPanelHidden) {

            ParallelAction parallelAction = new ParallelAction();

            SizeToAction sizeToAction = new SizeToAction();
            sizeToAction.setSize(HUD_VIEWPORT_WIDTH.get(),HUD_VIEWPORT_HEIGHT.get());
            sizeToAction.setSize(225, 260);
            sizeToAction.setDuration(0.25f);
            parallelAction.addAction(sizeToAction);

            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setPosition((HUD_VIEWPORT_WIDTH.get() - 225) / 2, (HUD_VIEWPORT_HEIGHT.get() - 260) / 2);
            moveToAction.setDuration(0.25f);
            parallelAction.addAction(moveToAction);

            outerTable.addAction(parallelAction);
            outerTable.setVisible(true);
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
            moveToAction.setPosition(button.getX(), button.getY());
            moveToAction.setDuration(0.25f);
            parallelAction.addAction(moveToAction);

            VisibleAction visibleAction = new VisibleAction();
            visibleAction.setVisible(false);


            sequenceAction.addAction(parallelAction);
            sequenceAction.addAction(visibleAction);

            outerTable.addAction(sequenceAction);

            return true;
        }
    }
}
