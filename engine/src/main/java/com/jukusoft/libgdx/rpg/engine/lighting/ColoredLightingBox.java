package com.jukusoft.libgdx.rpg.engine.lighting;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public class ColoredLightingBox {

    protected float x = 0;
    protected float y = 0;
    protected float width = 0;
    protected float height = 0;

    protected Color color = Color.BLACK;

    public ColoredLightingBox (float x, float y, float width, float height, Color fillColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = fillColor;
    }

    public ColoredLightingBox (float x, float y, float width, float height) {
        this(x, y, width, height, Color.BLACK);
    }

    public void draw (GameTime time, ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(this.color);
        shapeRenderer.rect(this.x, this.y, this.width, this.height);
    }

}
