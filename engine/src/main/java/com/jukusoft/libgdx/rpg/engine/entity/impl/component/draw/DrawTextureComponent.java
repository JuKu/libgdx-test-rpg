package com.jukusoft.libgdx.rpg.engine.entity.impl.component.draw;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IDrawComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.PositionComponent;
import com.jukusoft.libgdx.rpg.engine.entity.listener.TextureChangedListener;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 10.02.2017.
 */
public class DrawTextureComponent extends BaseComponent implements IDrawComponent {

    protected PositionComponent positionComponent = null;
    protected List<TextureChangedListener> textureChangedListenerList = new ArrayList<>();

    protected Texture texture = null;

    public DrawTextureComponent(Texture texture) {
        if (texture == null) {
            throw new NullPointerException("texture cannot be null.");
        }

        if (!texture.isManaged()) {
            throw new IllegalStateException("texture isnt loaded.");
        }

        this.texture = texture;
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }

        //set new width and height of entity
        positionComponent.setDimension(texture.getWidth(), texture.getHeight());
    }

    @Override public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //draw texture
        batch.draw(this.texture, this.positionComponent.getX(), this.positionComponent.getY(), this.positionComponent.getWidth(), this.positionComponent.getHeight());
    }

    @Override public ECSPriority getDrawOrder() {
        return ECSPriority.LOW;
    }

    public Texture getTexture () {
        return this.texture;
    }

    public void setTexture (Texture texture, boolean setNewDimension) {
        Texture oldTexture = this.texture;
        this.texture = texture;

        if (setNewDimension) {
            //update width and height
            this.positionComponent.setDimension(texture.getWidth(), texture.getHeight());
        }

        this.textureChangedListenerList.stream().forEach(listener -> {
            listener.onTextureChanged(oldTexture, this.texture);
        });
    }

    public void addTextureChangedListener (TextureChangedListener listener) {
        this.textureChangedListenerList.add(listener);
    }

    public void removeTextureChangedListener (TextureChangedListener listener) {
        this.textureChangedListenerList.remove(listener);
    }

}
