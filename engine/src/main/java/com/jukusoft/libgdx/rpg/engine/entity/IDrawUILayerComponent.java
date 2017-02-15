package com.jukusoft.libgdx.rpg.engine.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 12.02.2017.
 */
public interface IDrawUILayerComponent extends IComponent {

    public void drawUILayer (GameTime time, CameraWrapper camera, SpriteBatch batch);

    public ECSPriority getUILayerDrawOrder ();

}
