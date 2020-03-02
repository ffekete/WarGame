package com.mygdx.wargame.battle.action;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.awt.*;
import java.util.function.Supplier;

/**
 * An action that has an int, whose value is transitioned over time.
 *
 * @author Nathan Sweet
 */
public class IntAction extends TemporalAction {
    private int start;
    private Supplier<Integer> end;
    private int value;
    private Label label;

    /**
     * Creates an IntAction that transitions from start to end.
     */
    public IntAction(int start, Supplier<Integer> end, float duration, Label label) {
        super(duration);
        this.start = start;
        this.end = end;
        this.label = label;
    }

    protected void begin() {
        value = start;
    }

    protected void update(float percent) {

        if(value == end.get()) {
            setDuration(0f);
        }

        if (percent == 0)
            value = start;
        else if (percent == 1)
            value = end.get();
        else
            value = (int) (start + (end.get() - start) * percent);
        label.setText(value);
    }

    /**
     * Gets the current int value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the current int value.
     */
    public void setValue(int value) {
        this.value = value;
    }
}
