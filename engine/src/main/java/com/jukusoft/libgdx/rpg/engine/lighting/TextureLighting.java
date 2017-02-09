package com.jukusoft.libgdx.rpg.engine.lighting;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.math.MathUtils;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 09.02.2017.
 */
public class TextureLighting extends BaseLighting {

    protected Texture lightMapTexture = null;
    protected boolean lightOscillate = false;
    protected float lightSize = 1;
    protected float zAngle = 0;

    public TextureLighting (Texture lightMap) {
        this.lightMapTexture = lightMap;
    }

    @Override public void update(BaseGame game, float lightSize, float zAngle, GameTime time) {
        //we dont want to calculate this values for every lighting, so we set them only
        this.lightSize = lightSize;
        this.zAngle = zAngle;
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        float lightSize = lightOscillate ? (4.75f + 0.25f * (float)Math.sin(zAngle) + .2f* MathUtils.random()):5.0f;
        batch.draw(this.lightMapTexture, getX() - lightSize * 0.5f + 0.5f, getY() + 0.5f - lightSize*0.5f, lightSize, lightSize);
    }

}
