package com.jukusoft.libgdx.rpg.engine.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ComponentPriority;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public interface IDrawComponent extends IComponent {

    public void draw (GameTime time, Camera camera, SpriteBatch batch);

    public ComponentPriority getDrawOrder ();

}
