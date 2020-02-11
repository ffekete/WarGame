package com.mygdx.wargame.battle.screen.localmenu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.mygdx.wargame.config.Config;

public class WeaponSelectionPanelMovementHandler {

    public boolean moveWeaponSelectionButton(boolean weaponSelectionContainerHidden, Actor weaponSelectionButton, Actor weaponSelectionContainer, Actor weaponSelectionScrollPane, Actor heatProgressBar) {
        if (weaponSelectionContainerHidden) {

            ParallelAction enlargeAndSetVisibleAction = new ParallelAction();

            SizeToAction sizeToAction = new SizeToAction();
            sizeToAction.setSize(600 * Config.UI_SCALING, 300 * Config.UI_SCALING);
            sizeToAction.setDuration(0.25f);
            enlargeAndSetVisibleAction.addAction(sizeToAction);

            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setPosition(Math.max(weaponSelectionButton.getX() - 560 * Config.UI_SCALING, 0), Math.max(weaponSelectionButton.getY() - 260 * Config.UI_SCALING, 0));
            moveToAction.setDuration(0.25f);
            enlargeAndSetVisibleAction.addAction(moveToAction);

            weaponSelectionContainer.setVisible(true);
            weaponSelectionScrollPane.setVisible(true);

            weaponSelectionContainer.addAction(enlargeAndSetVisibleAction);

            DelayAction delayAction = new DelayAction();
            delayAction.setDuration(0.25f);
            VisibleAction visibleAction = new VisibleAction();
            visibleAction.setVisible(true);

            SequenceAction waitThenShowAction = new SequenceAction();
            waitThenShowAction.addAction(delayAction);
            waitThenShowAction.addAction(visibleAction);

            heatProgressBar.addAction(waitThenShowAction);

            weaponSelectionScrollPane.addAction(enlargeAndSetVisibleAction);

            return false;
        } else {

            SequenceAction shrinkMoveThenSetVisibleAction = new SequenceAction();

            ParallelAction shrinkAndMoveAction = new ParallelAction();

            SizeToAction sizeToAction = new SizeToAction();
            sizeToAction.setHeight(0);
            sizeToAction.setWidth(0);
            sizeToAction.setDuration(0.25f);
            shrinkAndMoveAction.addAction(sizeToAction);

            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setPosition(weaponSelectionButton.getX(), weaponSelectionButton.getY());
            moveToAction.setDuration(0.25f);
            shrinkAndMoveAction.addAction(moveToAction);

            VisibleAction visibleAction = new VisibleAction();
            visibleAction.setVisible(false);

            shrinkMoveThenSetVisibleAction.addAction(shrinkAndMoveAction);
            shrinkMoveThenSetVisibleAction.addAction(visibleAction);

            weaponSelectionContainer.addAction(shrinkMoveThenSetVisibleAction);
            heatProgressBar.setVisible(false);
            weaponSelectionScrollPane.addAction(shrinkMoveThenSetVisibleAction);

            return true;
        }
    }
}
