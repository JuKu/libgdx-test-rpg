package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
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
 * Created by Justin on 09.03.2017.
 */
public class DrawParticlesComponent extends BaseComponent implements IDrawComponent {

    protected PositionComponent positionComponent = null;

    protected float paddingX = 0;
    protected float paddingY = 0;

    protected ParticleEffect particleEffect = null;
    protected String particleEffectFile = "";

    protected boolean looping = false;
    protected boolean autoStart = false;

    public DrawParticlesComponent (String particleEffectFile, boolean looping, boolean autoStart) {
        this.particleEffectFile = particleEffectFile;
        this.looping = looping;
        this.autoStart = autoStart;

        //https://github.com/libgdx/libgdx/wiki/2D-ParticleEffects
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }

        //create new particle effect
        this.particleEffect = new ParticleEffect();

        //load effect
        game.getAssetManager().load(this.particleEffectFile, ParticleEffect.class);
        game.getAssetManager().finishLoadingAsset(this.particleEffectFile);
        this.particleEffect = game.getAssetManager().get(this.particleEffectFile, ParticleEffect.class);

        if (this.autoStart) {
            //start particle effect
            this.particleEffect.start();
        }
    }

    @Override public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //loop particle effect
        if (this.particleEffect.isComplete() && looping) {
            this.particleEffect.start();
        }

        //set position
        this.particleEffect.setPosition(positionComponent.getX() + paddingX, positionComponent.getY() + paddingY);

        //Stop the additive effect restting the blend state
        //this.particleEffect.setEmittersCleanUpBlendFunction(false);

        //draw particle effect
        this.particleEffect.draw(batch, time.getDeltaTime());

        //We need to reset the batch to the original blend state as we have setEmittersCleanUpBlendFunction as false in additiveEffect
        //batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override public ECSPriority getDrawOrder() {
        return ECSPriority.DRAW_PARTICLES;
    }

    public void startParticleEffect () {
        this.particleEffect.start();
    }

    public void stopParticleEffect () {
        this.particleEffect.reset();
    }

    public boolean isLooping () {
        return this.looping;
    }

    public void setLooping (boolean looping) {
        this.looping = looping;
    }

    @Override
    public void dispose () {
        this.game.getAssetManager().unload(this.particleEffectFile);
        this.particleEffect.dispose();
        this.particleEffect = null;
    }

}
