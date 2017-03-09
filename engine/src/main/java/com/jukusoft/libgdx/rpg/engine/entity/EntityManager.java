package com.jukusoft.libgdx.rpg.engine.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public interface EntityManager {

    public void addEntity (final String uniqueName, final Entity entity);

    public void addEntity (Entity entity);

    public void removeEntity (Entity entity);

    public void removeEntity (final long entityID);

    public void removeEntity (final String uniqueName);

    public void removeAllEntities ();

    public void update (BaseGame game, GameTime time);

    public void draw (GameTime time, CameraWrapper camera, SpriteBatch batch);

    public void drawUILayer (GameTime time, CameraWrapper camera, SpriteBatch batch);

    public void onEntityUpdateOrderChanged ();

    public void onEntityDrawOrderChanged ();

    public void onEntityUILayerDrawOrderChanged ();

    public <T extends IComponent> void onComponentAdded (Entity entity, T component, Class<T> cls);

    public <T extends IComponent> void onComponentRemoved (Entity entity, T component, Class<T> cls);

    public BaseGame getGame ();

    public void dispose ();

}
