package com.jukusoft.libgdx.rpg.engine.entity.priority;

/**
 * Created by Justin on 10.02.2017.
 */
public enum ECSPriority {

    BACKGROUND(1),

    VERY_LOW(2),

    LOW(3),

    DRAW_SHADOW(4),

    DRAW_HOVER_EFFECT(5),

    NORMAL(6),

    HIGH(7),

    VERY_HIGHT(8),

    HUD(9);

    private final int id;

    ECSPriority(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }

}
