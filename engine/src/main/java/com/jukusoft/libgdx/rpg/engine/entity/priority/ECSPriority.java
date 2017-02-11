package com.jukusoft.libgdx.rpg.engine.entity.priority;

/**
 * Created by Justin on 10.02.2017.
 */
public enum ECSPriority {

    BACKGROUND(1),

    VERY_LOW(2),

    LOW(3),

    NORMAL(4),

    HIGH(5),

    VERY_HIGHT(6),

    HUD(7);

    private final int id;

    ECSPriority(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }

}
