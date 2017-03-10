package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.listener.PositionChangedListener;
import com.jukusoft.libgdx.rpg.engine.entity.listener.TextureRegionChangedListener;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.lighting.Lighting;
import com.jukusoft.libgdx.rpg.engine.lighting.TextureLighting;

/**
 * Created by Justin on 15.02.2017.
 */
public class LightMapComponent extends BaseComponent {

    protected PositionComponent positionComponent = null;
    protected DrawTextureRegionComponent textureRegionComponent = null;

    protected Texture lightMapTexture = null;
    protected TextureLighting textureLighting = null;

    protected float offsetX = 25f;
    protected float offsetY = 25f;

    public LightMapComponent (Texture lightMap, float offsetX, float offsetY) {
        this.lightMapTexture = lightMap;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);
        this.textureRegionComponent = entity.getComponent(DrawTextureRegionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }

        //create new textured lighting
        this.textureLighting = new TextureLighting(this.lightMapTexture, positionComponent.getX() - (positionComponent.getWidth() / 2), positionComponent.getY() - (positionComponent.getHeight() / 2));

        //register position changed listener to set new position of lighting
        this.positionComponent.addPositionChangedListener(new PositionChangedListener() {
            @Override public void onPositionChanged(float oldX, float oldY, float newX, float newY) {
                textureLighting.setPosition(newX + offsetX, newY + offsetY);
            }
        });

        float newX = positionComponent.getX();
        float newY = positionComponent.getY();

        textureLighting.setPosition(newX + offsetX, newY + offsetY);
    }

    public Lighting getLighting () {
        return this.textureLighting;
    }

}
