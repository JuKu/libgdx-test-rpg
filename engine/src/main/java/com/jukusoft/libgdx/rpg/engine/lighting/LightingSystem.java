package com.jukusoft.libgdx.rpg.engine.lighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.shader.ShaderFactory;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.SpriteBatcherUtils;
import com.jukusoft.libgdx.rpg.engine.world.GameWorld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 09.02.2017.
 */
public class LightingSystem implements LightingEnvironment {

    protected FrameBuffer fbo = null;
    protected ShaderProgram defaultShader = null;

    //shader values
    public float ambientIntensity = .7f;
    public Vector3 ambientColor = new Vector3(0.3f, 0.3f, 0.7f);

    //http://www.alcove-games.com/opengl-es-2-tutorials/lightmap-shader-fire-effect-glsl/

    protected ShaderProgram finalLightingShader = null;

    //used to make the light flicker
    public float zAngle;
    public static final float zSpeed = 15.0f;
    public static final float PI2 = 3.1415926535897932384626433832795f * 2.0f;

    //used for drawing
    private boolean lightOscillate = true;

    protected List<Lighting> lightings = new ArrayList<>();

    //list with all visible lightings in viewport
    protected List<Lighting> visibleLightings = new ArrayList<>();

    protected List<AmbientLightChangedListener> ambientLightChangedListenerList = new ArrayList<>();

    //flag if lighting is enabled
    protected boolean lightingEnabled = true;

    //shape renderer to draw black rectangles on framebuffer
    protected ShapeRenderer shapeRenderer = null;

    protected BaseGame game = null;
    protected Texture blackTexture = null;

    //black rectangles
    protected List<ColoredLightingBox> coloredFilledRectList = new ArrayList<>();

    public LightingSystem (BaseGame game, Texture blackTexture, int width, int height) {
        this.game = game;
        this.blackTexture = blackTexture;

        //create new frame buffer
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);

        //register resize listener
        game.addResizeListener((newWidth, newHeight) -> {
            //remove old framebuffer
            this.fbo.dispose();
            this.fbo = null;

            //create new framebuffer
            this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, newWidth, newHeight, false);

            //change resolution on lighting shader
            this.finalLightingShader.begin();
            this.finalLightingShader.setUniformf("resolution", newWidth, newHeight);
            this.finalLightingShader.end();
        });

        //create new default shader
        this.defaultShader = SpriteBatch.createDefaultShader();

        //create shader
        try {
            this.finalLightingShader = ShaderFactory.createShader(game.getShaderDir() + "lighting/vertexShader.glsl", game.getShaderDir() + "lighting/pixelShader.glsl");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldnt not initialize lighting shaders: " + e.getLocalizedMessage());
        }

        //initialize lighting shader
        this.updateShaderUniforms();

        //create shape renderer
        this.shapeRenderer = new ShapeRenderer();
    }

    protected void updateShaderUniforms () {
        this.finalLightingShader.begin();
        this.finalLightingShader.setUniformi("u_lightmap", 1);
        this.finalLightingShader.setUniformf("ambientColor", ambientColor.x, ambientColor.y,
            ambientColor.z, ambientIntensity);
        this.finalLightingShader.end();
    }

    public void update (BaseGame game, CameraWrapper camera, GameTime time) {
        if (!isLightingEnabled()) {
            //because lighting isnt enabled, we dont have to update lighting system
            return;
        }

        float dt = time.getDeltaTime();

        //calculate zAngle
        zAngle += dt * zSpeed;
        while(zAngle > PI2)
            zAngle -= PI2;

        float lightSize = lightOscillate ? (4.75f + 0.25f * (float) Math.sin(zAngle) + .2f * MathUtils.random()) * 50 : 200.0f;
        //System.out.println("lightSize: " + lightSize);

        //update lightings
        this.visibleLightings.stream().forEach(lighting -> {
            lighting.update(game, lightSize, zAngle, time);
        });
    }

    public void drawFBO (GameTime time, GameWorld gameWorld, CameraWrapper camera, SpriteBatch batch) {
        if (!this.isLightingEnabled()) {
            //we dont have to do anything, because lighting isnt enabled
            return;
        }

        boolean wasDrawing = false;

        if (batch.isDrawing()) {
            batch.end();
            wasDrawing = true;
        }

        //set shape renderer camera
        this.shapeRenderer.setProjectionMatrix(camera.getCombined());

        //draw lights to framebuffer
        fbo.begin();

        //clear framebuffer
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.getCombined());
        batch.setShader(defaultShader);

        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //setBlendFunction(GL20.GL_SRC_COLOR, GL20.GL_SRC_ALPHA)

        batch.begin();

        //draw black background
        batch.draw(this.blackTexture, camera.getX(), camera.getY(), game.getViewportWidth(), game.getViewportHeight());

        //draw lights
        this.drawLights(time, batch);

        //draw no lighting rectangles
        List<Rectangle> list = gameWorld.listNoLightingRectangles(camera);

        //disable blending
        batch.disableBlending();

        for (Rectangle rectangle : list) {
            SpriteBatcherUtils.fillRectangle(batch, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), Color.BLACK);
        }

        //enable blending
        batch.enableBlending();

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        //draw colored boxes (overlay), for example for walls which shouldnt be lighted
        this.drawColoredLightingBoxes(time, this.shapeRenderer);

        shapeRenderer.end();

        fbo.end();

        if (wasDrawing) {
            batch.begin();
        }

        //we have to clear buffer, else it will also drawn to actual buffer instad only to framebuffer
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //after drawing instead of using shaders, you can also use shading:
        //batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_SRC_ALPHA);
        //theLightSprite.draw(batch, parentAlpha);
        //batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        //see also: http://stackoverflow.com/questions/21278229/libgdx-light-without-box2d
    }

    protected void drawLights (GameTime time, SpriteBatch batch) {
        //draw lightings
        this.visibleLightings.stream().forEach(lighting -> {
            lighting.draw(time, batch);
        });
    }

    protected void drawColoredLightingBoxes (GameTime time, ShapeRenderer shapeRenderer) {
        //draw black rectangles (for example for wallets, which shouldnt be lighted)
        this.coloredFilledRectList.stream().forEach(box -> {
            box.draw(time, shapeRenderer);
        });
    }

    public void dispose () {
        this.fbo.dispose();
        this.fbo = null;
    }

    public Vector3 getAmbientColor () {
        return this.ambientColor;
    }

    @Override public void setAmbientColor(float r, float g, float b) {
        this.ambientColor.x = r;
        this.ambientColor.y = g;
        this.ambientColor.z = b;

        this.notifyAmbientLightChanged();
    }

    public float getAmbientIntensity () {
        return this.ambientIntensity;
    }

    @Override public void setAmbientIntensity(float ambientIntensity) {
        this.ambientIntensity = ambientIntensity;

        this.notifyAmbientLightChanged();
    }

    @Override public void addAmbientLightListener(AmbientLightChangedListener listener) {
        this.ambientLightChangedListenerList.add(listener);
    }

    @Override public void removeAmbientLightListener(AmbientLightChangedListener listener) {
        this.ambientLightChangedListenerList.remove(listener);
    }

    @Override public void addLighting(Lighting lighting) {
        this.lightings.add(lighting);

        //TODO: remove this line
        this.visibleLightings.add(lighting);
    }

    @Override public void removeLighting(Lighting lighting) {
        this.visibleLightings.remove(lighting);
        this.lightings.remove(lighting);
    }

    @Override public void addColoredLightBox(ColoredLightingBox box) {
        this.coloredFilledRectList.add(box);
    }

    @Override public void removeColoredLightBox(ColoredLightingBox box) {
        this.coloredFilledRectList.remove(box);
    }

    @Override public boolean isLightingEnabled() {
        return this.lightingEnabled;
    }

    @Override public void setLightingEnabled(boolean lightingEnabled) {
        this.lightingEnabled = lightingEnabled;
    }

    protected void notifyAmbientLightChanged () {
        this.ambientLightChangedListenerList.stream().forEach(listener -> {
            listener.changedAmbientLight(ambientColor.x, ambientColor.y, ambientColor.z, ambientIntensity);
        });

        this.updateShaderUniforms();
    }

    public FrameBuffer getFBO () {
        return this.fbo;
    }

    public ShaderProgram getLightingShader () {
        return this.finalLightingShader;
    }

    public ShaderProgram getDefaultShader () {
        return this.defaultShader;
    }

}
