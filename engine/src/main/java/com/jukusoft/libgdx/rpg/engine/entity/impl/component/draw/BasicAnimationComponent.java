package com.jukusoft.libgdx.rpg.engine.entity.impl.component.draw;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.AnimationTextureUtils;

/**
 * An basic animation with only one animation state
 *
 * Created by Justin on 11.02.2017.
 */
public class BasicAnimationComponent extends BaseComponent implements IUpdateComponent {

    protected DrawTextureRegionComponent textureRegionComponent = null;

    protected Animation<TextureRegion> animation = null;

    protected int rows = 0;
    protected int cols = 0;

    //https://github.com/libgdx/libgdx/wiki/2D-Animation

    protected float elapsed = 0;
    protected TextureRegion currentFrame = null;

    public BasicAnimationComponent(Texture texture, float duration, int rows, int cols) {
        if (texture == null) {
            throw new NullPointerException("texture cannot be null.");
        }

        if (!texture.isManaged()) {
            throw new IllegalStateException("texture isnt managed or wasnt loaded.");
        }

        this.rows = rows;
        this.cols = cols;

        //create animation
        this.animation = AnimationTextureUtils.createAnimationFromTexture(texture, duration / 1000, rows, cols);
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.textureRegionComponent = entity.getComponent(DrawTextureRegionComponent.class);

        if (this.textureRegionComponent == null) {
            throw new IllegalStateException("entity doesnt have an DrawTextureRegionComponent.");
        }

        //set current frame
        this.currentFrame = this.animation.getKeyFrame(1);
        this.textureRegionComponent.setTextureRegion(this.currentFrame, true);
    }

    @Override public void update(BaseGame game, GameTime time) {
        //calculate elapsed time in milliseconds
        this.elapsed += time.getDeltaTime();

        //get current frame of animation
        TextureRegion region = animation.getKeyFrame(elapsed, true);

        if (this.currentFrame != region) {
            this.textureRegionComponent.setTextureRegion(this.currentFrame, true);
            //positionComponent.setDimension(region.getRegionWidth(), region.getRegionHeight());
        }

        this.currentFrame = region;
    }

    @Override public ECSPriority getUpdateOrder() {
        return ECSPriority.NORMAL;
    }

    public TextureRegion getCurrentFrame () {
        return this.currentFrame;
    }

}
