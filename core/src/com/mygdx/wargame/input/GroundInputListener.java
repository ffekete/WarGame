package com.mygdx.wargame.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.wargame.battle.controller.SelectionController;
import com.mygdx.wargame.battle.unit.Man;

public class GroundInputListener extends InputListener {

        private Man man;
        private SelectionController selectionController;

        public GroundInputListener(Man man, SelectionController selectionController) {
            this.man = man;
            this.selectionController = selectionController;
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

            return true;
        }
    }