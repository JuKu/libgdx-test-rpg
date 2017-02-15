package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IDrawComponent;
import com.jukusoft.libgdx.rpg.engine.entity.listener.TextureRegionChangedListener;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 11.02.2017.
 */
public class DrawTextureRegionComponent extends BaseComponent implements IDrawComponent {

    protected PositionComponent positionComponent = null;

    protected TextureRegion textureRegion = null;
    protected List<TextureRegionChangedListener> textureRegionChangedListenerList = new ArrayList<>();

    public DrawTextureRegionComponent (TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public DrawTextureRegionComponent () {
        //
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }

        if (this.textureRegion != null) {
            //set new width and height of entity
            positionComponent.setDimension(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        }
    }

    @Override public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        if (this.textureRegion == null) {
            System.err.println("texture region of DrawTextureRegionComponent is null.");

            return;
        }

        //https://github.com/libgdx/libgdx/wiki/2D-Animation

        batch.draw(this.textureRegion, this.positionComponent.getX(), this.positionComponent.getY(), this.positionComponent.getWidth(), this.positionComponent.getHeight());
    }

    @Override public ECSPriority getDrawOrder() {
        return ECSPriority.LOW;
    }

    public TextureRegion getTextureRegion () {
        return this.textureRegion;
    }

    public void setTextureRegion (TextureRegion textureRegion, boolean setNewDimension) {
        TextureRegion oldTextureRegion = this.textureRegion;
        this.textureRegion = textureRegion;

        if (setNewDimension) {
            //set new width and height
            this.positionComponent.setDimension(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        }

        this.textureRegionChangedListenerList.stream().forEach(listener -> {
            listener.onTextureRegionChanged(oldTextureRegion, this.textureRegion);
        });
    }

    public void addTextureRegionChangedListener (TextureRegionChangedListener listener) {
        this.textureRegionChangedListenerList.add(listener);
    }

    public void removeTextureRegionChangedListener (TextureRegionChangedListener listener) {
        this.textureRegionChangedListenerList.remove(listener);
    }

}
