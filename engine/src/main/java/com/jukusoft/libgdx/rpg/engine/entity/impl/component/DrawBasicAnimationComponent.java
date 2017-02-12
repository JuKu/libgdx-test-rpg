package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.IDrawComponent;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 11.02.2017.
 */
public class DrawBasicAnimationComponent extends BaseComponent implements IUpdateComponent, IDrawComponent {

    protected Animation<TextureRegion> animation = null;

    protected float elapsed = 0;

    @Override public void update(BaseGame game, GameTime time) {
        //calculate elapsed time in milliseconds
        this.elapsed += time.getDeltaTime();
    }

    @Override public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {

    }

    @Override public ECSPriority getUpdateOrder() {
        return ECSPriority.LOW;
    }

    @Override public ECSPriority getDrawOrder() {
        return ECSPriority.LOW;
    }
}
