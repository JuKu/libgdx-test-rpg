package com.jukusoft.libgdx.rpg.engine.entity.priority;

/**
 * Created by Justin on 10.02.2017.
 */
public enum ECSPriority {

    BACKGROUND(1),

    DRAW_PARTICLES(2),

    VERY_LOW(3),

    LOW(4),

    DRAW_SHADOW(5),

    DRAW_HOVER_EFFECT(6),

    NORMAL(7),

    HIGH(8),

    VERY_HIGHT(9),

    HUD(10);

    private final int id;

    ECSPriority(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }

}
