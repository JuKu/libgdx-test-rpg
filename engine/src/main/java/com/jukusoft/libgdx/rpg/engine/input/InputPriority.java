package com.jukusoft.libgdx.rpg.engine.input;

/**
 * Created by Justin on 09.02.2017.
 */
public enum InputPriority {

    VERY_LOW(1),

    LOW(2),

    NORMAL(3),

    HIGH(4),

    VERY_HIGHT(5),

    HUD(6);

    private final int id;

    InputPriority (int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }

}
