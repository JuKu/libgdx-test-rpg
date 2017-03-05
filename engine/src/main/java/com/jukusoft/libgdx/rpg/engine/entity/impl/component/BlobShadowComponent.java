package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IDrawComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 05.03.2017.
 */
public class BlobShadowComponent extends BaseComponent implements IDrawComponent {

    protected PositionComponent positionComponent = null;
    protected DrawTextureComponent textureComponent = null;
    protected DrawTextureRegionComponent textureRegionComponent = null;

    protected Texture shadowTexture = null;
    protected Color shadowColor = Color.GRAY;
    protected int shadowWidth = 0;
    protected int shadowHeight = 0;

    /**
    * default constructor
     *
     * @param shadowColor color of blob shadow
    */
    public BlobShadowComponent (Color shadowColor) {
        this.shadowColor = shadowColor;
    }

    /**
    * default constructor
    */
    public BlobShadowComponent () {
        //
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);
        this.textureComponent = entity.getComponent(DrawTextureComponent.class);
        this.textureRegionComponent = entity.getComponent(DrawTextureRegionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }

        if (textureComponent == null && textureRegionComponent == null) {
            throw new IllegalStateException("You have to set an TextureComponent or an TextureRegionComponent to entity to use ShadowComponent.");
        }

        if (this.textureComponent != null) {
            this.textureComponent.addTextureChangedListener((Texture oldTexture, Texture newTexture) -> {
                //delete old shadow texture and generate an new one
                notifyDimensionChanged();
            });
        }

        if (this.textureRegionComponent != null) {
            this.textureRegionComponent.addTextureRegionChangedListener((TextureRegion oldTextureRegion, TextureRegion textureRegion) -> {
                //delete old shadow texture and generate an new one
                notifyDimensionChanged();
            });
        }

        //generate shadow texture
        this.generateShadow(false);
    }

    @Override public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        if (this.shadowTexture != null) {
            batch.draw(this.shadowTexture, this.positionComponent.getX(), this.positionComponent.getY() - (this.shadowHeight / 2), this.shadowWidth, this.shadowHeight);
        } else {
            throw new IllegalStateException("No shadow texture generated yet.");
        }
    }

    @Override public ECSPriority getDrawOrder() {
        return ECSPriority.DRAW_SHADOW;
    }

    protected void generateShadow (boolean checkDimension) {
        //update shadow dimensions first
        boolean dimensionChanged = this.updateShadowDimension();

        if (checkDimension && !dimensionChanged && this.shadowTexture != null) {
            //we can use old texture and dont need to generate an new shadow texture
            return;
        }

        //generate new shadow texture

        //create new pixmap, important: we use 2x width of shadow, so we have an quadrat
        Pixmap shadowPixmap = new Pixmap(shadowWidth, shadowWidth, Pixmap.Format.RGBA8888);

        //set shadow color
        shadowPixmap.setColor(this.shadowColor);

        int centerX = shadowWidth / 2;
        int centerY = shadowWidth / 2;

        //draw circle
        shadowPixmap.fillCircle(centerX, centerY, shadowWidth / 2);

        //cleanUp old shadow texture
        if (this.shadowTexture != null) {
            this.shadowTexture.dispose();
            this.shadowTexture = null;
        }

        //generate texture from Pixmap
        this.shadowTexture = new Texture(shadowPixmap);

        //dispose pixmap
        shadowPixmap.dispose();
    }

    protected void generateShadow () {
        this.generateShadow(true);
    }

    /**
    * update dimensions of shadow texture
     *
     * @return true, if shadow was changed
    */
    protected boolean updateShadowDimension () {
        //backup old values
        int oldWidth = this.shadowWidth;
        int oldHeight = this.shadowHeight;

        if (this.textureRegionComponent != null) {
            this.shadowWidth = this.textureRegionComponent.getTextureRegion().getRegionWidth();
            this.shadowHeight = this.textureRegionComponent.getTextureRegion().getRegionHeight();
        } else if (this.textureComponent != null) {
            this.shadowWidth = this.textureComponent.getTexture().getWidth();
            this.shadowHeight = this.textureComponent.getTexture().getHeight();
        } else {
            throw new IllegalStateException("No texture component or texture region component is set to entity.");
        }

        this.shadowHeight = shadowHeight / 4;

        return this.shadowWidth != oldWidth || this.shadowHeight != oldHeight;
    }

    public void notifyDimensionChanged () {
        //generate shadow texture
        this.generateShadow(true);
    }

}
