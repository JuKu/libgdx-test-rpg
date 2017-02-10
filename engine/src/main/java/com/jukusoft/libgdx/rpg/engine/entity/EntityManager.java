package com.jukusoft.libgdx.rpg.engine.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public interface EntityManager {

    public void addEntity (Entity entity);

    public void removeEntity (Entity entity);

    public void removeEntity (final long entityID);

    public void update (BaseGame game, GameTime time);

    public void draw (GameTime time, Camera camera, SpriteBatch batch);

}
