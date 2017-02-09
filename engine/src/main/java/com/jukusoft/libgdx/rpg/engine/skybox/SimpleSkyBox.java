package com.jukusoft.libgdx.rpg.engine.skybox;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;

/**
 * Created by Justin on 09.02.2017.
 */
public class SimpleSkyBox implements SkyBox {

    protected Texture skyBoxTexture = null;

    protected float x = 0;
    protected float y = 0;

    protected float offsetX = 0;

    protected float lastCameraX = 0;
    protected float lastCameraY = 0;

    protected float skyBoxWidth = 0;

    //points, when skybox has to be moved
    protected float pointX1 = 0;
    protected float pointX2 = 0;

    protected float worldPosX = 0;
    protected float worldPosY = 0;

    public SimpleSkyBox (Texture texture) {
        this.skyBoxTexture = texture;
        this.x = skyBoxWidth / 2;

        this.skyBoxWidth = skyBoxTexture.getWidth();

        this.pointX1 = x - skyBoxWidth;
        this.pointX2 = x + skyBoxWidth;
    }

    @Override public void update(BaseGame game, GameTime time) {
        if (worldPosX < pointX1) {
            //move points left
            pointX1 -= skyBoxWidth;
            pointX2 -= skyBoxWidth;

            //move skybox
            x -= skyBoxWidth;
        } else if (worldPosX > pointX2) {
            //move points
            pointX1 += skyBoxWidth;
            pointX2 += skyBoxWidth;

            //move skybox
            x += skyBoxWidth;
        }
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        System.out.println("draw skybox, x: " + x + ", y: " + y + ", offsetX: " + offsetX + ", skybox width: " + skyBoxTexture.getWidth());
        System.out.println("pointX1: " + pointX1 + ", pointX2: " + pointX2);

        //draw 3 skyboxes near
        batch.draw(this.skyBoxTexture, x - skyBoxWidth, y);
        batch.draw(this.skyBoxTexture, x, y);
        batch.draw(this.skyBoxTexture, x + skyBoxWidth, y);
    }

    @Override public void translate(float x, float y) {
        this.worldPosX += x;
        this.worldPosY += y;
    }

}
