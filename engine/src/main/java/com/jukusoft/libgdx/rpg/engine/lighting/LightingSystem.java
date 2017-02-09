package com.jukusoft.libgdx.rpg.engine.lighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.shader.ShaderFactory;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.window.ResizeListener;

import java.io.IOException;

/**
 * Created by Justin on 09.02.2017.
 */
public class LightingSystem {

    protected FrameBuffer fbo = null;
    protected ShaderProgram defaultShader = null;

    //shader values
    public static final float ambientIntensity = .7f;
    public static final Vector3 ambientColor = new Vector3(0.3f, 0.3f, 0.7f);

    //http://www.alcove-games.com/opengl-es-2-tutorials/lightmap-shader-fire-effect-glsl/

    protected ShaderProgram finalLightingShader = null;

    //used to make the light flicker
    public float zAngle;
    public static final float zSpeed = 15.0f;
    public static final float PI2 = 3.1415926535897932384626433832795f * 2.0f;

    //used for drawing
    private boolean lightOscillate = false;

    public LightingSystem (BaseGame game, int width, int height) {
        //create new frame buffer
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);

        //register resize listener
        game.addResizeListener((newWidth, newHeight) -> {
            //remove old framebuffer
            this.fbo.dispose();
            this.fbo = null;

            //create new framebuffer
            this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, newWidth, newHeight, false);
        });

        //create shader
        try {
            this.finalLightingShader = ShaderFactory.createShader(game.getShaderDir() + "lighting/vertexShader.glsl", game.getShaderDir() + "lighting/pixelShader.glsl");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldnt not initialize lighting shaders: " + e.getLocalizedMessage());
        }
    }

    public void update (BaseGame game, Camera camera, GameTime time) {
        //
    }

    public void drawFBO (GameTime time, Camera camera, SpriteBatch batch) {
        boolean wasDrawing = false;

        if (batch.isDrawing()) {
            batch.end();
            wasDrawing = true;
        }

        //draw lights to framebuffer
        fbo.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.setShader(defaultShader);

        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //setBlendFunction(GL20.GL_SRC_COLOR, GL20.GL_SRC_ALPHA)

        batch.begin();

        //draw lights
        this.drawLights(time, batch);

        batch.end();

        fbo.end();

        if (wasDrawing) {
            batch.begin();
        }
    }

    protected void drawLights (GameTime time, SpriteBatch batch) {
        float lightSize = lightOscillate ? (4.75f + 0.25f * (float) Math.sin(zAngle) + .2f * MathUtils.random()) : 5.0f;
        //batch.draw(light, tilemap.campFirePosition.x - lightSize*0.5f + 0.5f,tilemap.campFirePosition.y + 0.5f - lightSize*0.5f, lightSize, lightSize);
    }

    public void dispose () {
        this.fbo.dispose();
        this.fbo = null;
    }

}
