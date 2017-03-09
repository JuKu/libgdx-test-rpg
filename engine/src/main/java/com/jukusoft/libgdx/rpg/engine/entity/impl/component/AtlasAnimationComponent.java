package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IUpdateComponent;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.AtlasUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 09.03.2017.
 */
public class AtlasAnimationComponent extends BaseComponent implements IUpdateComponent {

    protected String textureAtlasPath = "";

    protected TextureAtlas atlas = null;
    protected String currentAnimationName = "";
    protected Animation<TextureRegion> currentAnimation = null;
    protected float sumDuration = 1f;

    //map with all cached animations of entity
    protected Map<String,Animation<TextureRegion>> animationMap = new ConcurrentHashMap<>();

    protected float elapsed = 0;
    protected DrawTextureRegionComponent textureRegionComponent = null;

    public AtlasAnimationComponent (String atlasPath, String startAnimationName, float sumDuration) {
        this.textureAtlasPath = atlasPath;
        this.currentAnimationName = startAnimationName;
        this.sumDuration = sumDuration;
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        //load texture atlas
        game.getAssetManager().load(this.textureAtlasPath, TextureAtlas.class);
        game.getAssetManager().finishLoadingAsset(this.textureAtlasPath);
        this.atlas = game.getAssetManager().get(this.textureAtlasPath, TextureAtlas.class);

        Map<String,Integer> map = null;

        try {
            //parse all available animations
            map = AtlasUtils.getAvailableAnimations(this.textureAtlasPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldnt parse texture altas: " + this.textureAtlasPath + ", exception: " + e.getLocalizedMessage());
        }

        //create all animations in cache
        this.createCachedAnimations(map);

        //set first animation
        this.setCurrentAnimationName(this.currentAnimationName);
    }

    protected void createCachedAnimations (Map<String,Integer> map) {
        int i = 0;

        for (Map.Entry<String,Integer> entry : map.entrySet()) {
            String animationName = entry.getKey();
            int frameCounter = entry.getValue();

            //calculate duration per frame
            float durationPerFrame = this.sumDuration / frameCounter;

            //get regions
            Array<TextureAtlas.AtlasRegion> regions = this.atlas.findRegions(animationName);

            //create animation
            Animation<TextureRegion> anim = new Animation<>(durationPerFrame, regions, Animation.PlayMode.LOOP);

            //add animation to map
            this.animationMap.put(animationName, anim);

            i++;
        }

        Logger.getRootLogger().debug("animations loaded from atlas file: " + i);
    }

    public Animation<TextureRegion> getAnimationByName (String animationName) {
        //get animation
        Animation<TextureRegion> animation = this.animationMap.get(animationName);

        if (animation == null) {
            throw new IllegalStateException("Could not found animation: " + animationName);
        }

        return animation;
    }

    public String getTextureAtlasPath () {
        return this.textureAtlasPath;
    }

    public TextureAtlas getTextureAtlas () {
        return this.atlas;
    }

    public String getCurrentAnimationName () {
        return this.currentAnimationName;
    }

    public void setCurrentAnimationName (String animationName) {
        String oldAnimationName = this.currentAnimationName;
        this.currentAnimationName = animationName;

        //get animation
        this.currentAnimation = this.getAnimationByName(animationName);

        this.onAnimationStateChanged(oldAnimationName, animationName);
    }

    @Override public void update(BaseGame game, GameTime time) {
        //calculate elapsed time
        this.elapsed += time.getDeltaTime();

        //set current frame
        this.textureRegionComponent.setTextureRegion(this.currentAnimation.getKeyFrame(this.elapsed), true);
    }

    @Override public ECSPriority getUpdateOrder() {
        return ECSPriority.VERY_LOW;
    }

    protected void onAnimationStateChanged (String oldAnimationName, String newAnimationName) {
        //TODO: call listeners
    }

}
