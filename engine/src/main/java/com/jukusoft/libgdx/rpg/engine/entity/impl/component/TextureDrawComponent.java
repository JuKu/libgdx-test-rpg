package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IDrawComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public class TextureDrawComponent extends BaseComponent implements IDrawComponent {

    protected PositionComponent positionComponent = null;

    protected Texture texture = null;

    public TextureDrawComponent (Texture texture) {
        this.texture = texture;
    }

    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }
    }

    @Override public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //draw texture
        batch.draw(this.texture, this.positionComponent.getX(), this.positionComponent.getY());
    }

    @Override public ECSPriority getDrawOrder() {
        return ECSPriority.LOW;
    }

}
