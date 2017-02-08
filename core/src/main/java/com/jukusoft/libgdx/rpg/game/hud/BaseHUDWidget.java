package com.jukusoft.libgdx.rpg.game.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 08.02.2017.
 */
public abstract class BaseHUDWidget implements HUDWidget {

    protected float x = 0;
    protected float y = 0;
    protected float width = 100;
    protected float height = 100;

    @Override public void drawLayer1(GameTime time, ShapeRenderer shapeRenderer) {

    }

    @Override public void drawLayer2(GameTime time, SpriteBatch batch) {

    }

    public float getX () {
        return this.x;
    }

    public float getY () {
        return this.y;
    }

    public void setPosition (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getWidth () {
        return this.width;
    }

    public float getHeight () {
        return this.height;
    }

    public void setDimension (float width, float height) {
        this.width = width;
        this.height = height;
    }

}
