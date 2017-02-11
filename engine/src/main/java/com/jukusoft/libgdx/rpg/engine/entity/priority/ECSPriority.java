package com.jukusoft.libgdx.rpg.engine.entity.priority;

/**
 * Created by Justin on 10.02.2017.
 */
public enum ECSPriority {

    BACKGROUND(1),

    VERY_LOW(2),

    LOW(3),

    DRAW_SHADOW(4),

    NORMAL(5),

    HIGH(6),

    VERY_HIGHT(7),

    HUD(8);

    private final int id;

    ECSPriority(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }

}
