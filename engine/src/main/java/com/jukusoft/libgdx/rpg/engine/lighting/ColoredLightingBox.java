package com.jukusoft.libgdx.rpg.engine.lighting;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public class ColoredLightingBox {

    protected Color color = Color.BLACK;

    public void draw (GameTime time, ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(this.color);
    }

}
