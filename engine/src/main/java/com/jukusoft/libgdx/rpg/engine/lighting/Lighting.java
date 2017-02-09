package com.jukusoft.libgdx.rpg.engine.lighting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 09.02.2017.
 */
public interface Lighting {

    public void update (BaseGame game, GameTime time);

    public void draw (GameTime time, float lightSize, SpriteBatch batch);

    public float getX ();

    public float getY ();

    public void setPosition (float x, float y);

}
