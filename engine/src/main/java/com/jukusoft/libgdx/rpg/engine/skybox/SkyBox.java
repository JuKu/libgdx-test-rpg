package com.jukusoft.libgdx.rpg.engine.skybox;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 09.02.2017.
 */
public interface SkyBox {

    public void update (BaseGame game, GameTime time);

    public void draw (GameTime time, SpriteBatch batch);

    public void translate (float x, float y);

    public void setSmoothFactor (float x, float y);

    public void setTexture (Texture texture);

}
