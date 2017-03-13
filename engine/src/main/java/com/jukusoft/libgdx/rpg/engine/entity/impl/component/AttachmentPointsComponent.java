package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.draw.DrawTextureComponent;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.draw.DrawTextureRegionComponent;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.points.AttachmentPoint;
import com.jukusoft.libgdx.rpg.engine.utils.Direction;

/**
 * data component (model)
 *
 * Created by Justin on 07.03.2017.
 */
public class AttachmentPointsComponent extends BaseComponent {

    protected PositionComponent positionComponent = null;
    protected DrawTextureComponent textureComponent = null;
    protected DrawTextureRegionComponent textureRegionComponent = null;

    protected AttachmentPoint[] attachmentPoints = new AttachmentPoint[8];

    private float textureWidth = 0;
    private float textureHeight = 0;

    public AttachmentPointsComponent () {
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

        if (this.textureRegionComponent != null) {
            this.textureRegionComponent.addTextureRegionChangedListener((TextureRegion oldTextureRegion,
                TextureRegion newTextureRegion) -> {
                //update width, height and attachment points
                updateDimension();

                //update attachment points
                updateAttachmentPoints();
            });
        } else if (this.textureComponent != null) {
            this.textureComponent.addTextureChangedListener((Texture oldTexture, Texture newTexture) -> {
                //update width, height and attachment points
                updateDimension();

                //update attachment points
                updateAttachmentPoints();
            });
        } else {
            throw new IllegalStateException("You have to set an TextureComponent or an TextureRegionComponent to entity to use AttachmentPointsComponent.");
        }

        //update width, height and attachment points
        updateDimension();

        //create attachment points
        float width = this.textureWidth;
        float height = this.textureHeight;

        //create new attachment points
        attachmentPoints[Direction.DOWN.ordinal()] = new AttachmentPoint(0 + (width / 2), 0);
        attachmentPoints[Direction.UP.ordinal()] = new AttachmentPoint(0 + (width / 2), height);
        attachmentPoints[Direction.LEFT.ordinal()] = new AttachmentPoint(0, height / 2);
        attachmentPoints[Direction.RIGHT.ordinal()] = new AttachmentPoint(width, height / 2);

        attachmentPoints[Direction.UP_LEFT.ordinal()] = new AttachmentPoint(0, height);
        attachmentPoints[Direction.UP_RIGHT.ordinal()] = new AttachmentPoint(width, height);
        attachmentPoints[Direction.DOWN_LEFT.ordinal()] = new AttachmentPoint(0, 0);
        attachmentPoints[Direction.DOWN_RIGHT.ordinal()] = new AttachmentPoint(width, 0);
    }

    protected void updateDimension () {
        if (this.textureRegionComponent != null) {
            if (this.textureRegionComponent.getTextureRegion() == null) {
                //we dont need to update dimension, if no texture region is set
                return;
            }

            //update width and height of texture
            this.textureWidth = this.textureRegionComponent.getTextureRegion().getRegionWidth();
            this.textureHeight = this.textureRegionComponent.getTextureRegion().getRegionHeight();
        } else if (this.textureComponent != null) {
            if (this.textureComponent.getTexture() == null) {
                //we dont need to update dimension, if no texture is set
                return;
            }

            //update width and height of texture
            this.textureWidth = this.textureComponent.getTexture().getWidth();
            this.textureHeight = this.textureComponent.getTexture().getHeight();
        } else {
            throw new IllegalStateException("You have to set an TextureComponent or an TextureRegionComponent to entity to use AttachmentPointsComponent.");
        }
    }

    public void updateAttachmentPoints () {
        float width = this.textureWidth;
        float height = this.textureHeight;

        //create new attachment points
        attachmentPoints[Direction.DOWN.ordinal()].setPos(0 + (width / 2), 0);
        attachmentPoints[Direction.UP.ordinal()].setPos(0 + (width / 2), height);
        attachmentPoints[Direction.LEFT.ordinal()].setPos(0, height / 2);
        attachmentPoints[Direction.RIGHT.ordinal()].setPos(width, height / 2);

        attachmentPoints[Direction.UP_LEFT.ordinal()].setPos(0, height);
        attachmentPoints[Direction.UP_RIGHT.ordinal()].setPos(width, height);
        attachmentPoints[Direction.DOWN_LEFT.ordinal()].setPos(0, 0);
        attachmentPoints[Direction.DOWN_RIGHT.ordinal()].setPos(width, 0);
    }

    public AttachmentPoint getAttachmentPoint (Direction direction) {
        return this.getAttachmentPoint(direction.ordinal());
    }

    public AttachmentPoint getAttachmentPoint (int index) {
        if (index < 0 || index >= 8) {
            throw new IllegalArgumentException("index isnt in range (0 - 7).");
        }

        //get attachment point
        AttachmentPoint point = this.attachmentPoints[index];

        if (point == null) {
            throw new NullPointerException("attachment point is null.");
        }

        //update offset
        point.setOffset(this.positionComponent.getX(), this.positionComponent.getY());

        return point;
    }

}
