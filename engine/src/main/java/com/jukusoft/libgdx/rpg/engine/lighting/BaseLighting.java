package com.jukusoft.libgdx.rpg.engine.lighting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 09.02.2017.
 */
public abstract class BaseLighting implements Lighting {

    protected float x = 0;
    protected float y = 0;

    public BaseLighting (float x, float y) {
        this.x = y;
        this.y = y;
    }

    @Override public float getX() {
        return this.x;
    }

    @Override public float getY() {
        return this.y;
    }

    @Override public void setPosition(float x, float y) {
        //notify listener
        this.onPositionChanged(this.x, this.y, x, y);

        //set new position
        this.x = x;
        this.y = y;
    }

    protected void onPositionChanged (float oldX, float oldY, float newX, float newY) {
        //
    }

}
