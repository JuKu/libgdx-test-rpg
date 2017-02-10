package com.jukusoft.libgdx.rpg.engine.entity.priority;

/**
 * Created by Justin on 10.02.2017.
 */
public enum ComponentPriority {

    VERY_LOW(1),

    LOW(2),

    NORMAL(3),

    HIGH(4),

    VERY_HIGHT(5),

    HUD(6);

    private final int id;

    ComponentPriority (int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }

}
