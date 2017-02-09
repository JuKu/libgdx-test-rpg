package com.jukusoft.libgdx.rpg.game.world;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.skybox.SkyBox;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.FileUtils;
import com.jukusoft.libgdx.rpg.engine.world.SectorCoord;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 09.02.2017.
 */
public class GameWorld {

    protected Map<SectorCoord,GameWorldMap> gameWorldMap = new ConcurrentHashMap<>();

    /**
    * all game world maps which are shown on screen
    */
    protected List<GameWorldMap> visibleMaps = new ArrayList<>();

    //special shader program for water shader
    protected ShaderProgram waterShader = null;
    protected ShaderProgram defaultShader = null;

    //water animation
    private float amplitudeWave = 0.1f;//0.1f;
    private float angleWave = 0.0f;
    private float angleWaveSpeed = 1.0f;//1.0f;
    public static final float PI2 = 3.1415926535897932384626433832795f * 2.0f;

    protected Texture testTexture = null;

    protected volatile boolean animateWater = true;
    protected ShaderProgram currentShader = null;
    protected SkyBox skyBox = null;

    protected SectorCoord currentSector = null;

    //https://github.com/libgdx/libgdx/wiki/Tile-maps

    public GameWorld (SectorCoord coord, Texture texture) {
        //initialize shader programs
        try {
            this.initShaders();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException while loading shaders: " + e.getLocalizedMessage());
        }

        this.testTexture = texture;
        this.currentSector = coord;

        //load first map
        this.loadMap(coord);
    }

    protected void initShaders () throws IOException {
        //create default shader program
        this.defaultShader = SpriteBatch.createDefaultShader();
        this.currentShader = this.defaultShader;

        //read shader programs to string
        final String vertexShader = FileUtils.readFile(AssetPathUtils.getShaderPath("water/vertexShader.glsl"),
            StandardCharsets.ISO_8859_1);

        final String fragmentShader = FileUtils.readFile(AssetPathUtils.getShaderPath("water/defaultPixelShader.glsl"), StandardCharsets.ISO_8859_1);

        //create water shader program
        this.waterShader = new ShaderProgram(vertexShader, fragmentShader);

        //check, if water shader was compiled
        if (!this.waterShader.isCompiled()) {
            //log debug information
            System.out.println("vertex shader:\n\n" + vertexShader);

            System.out.println("\n\nfragment shader:\n" + fragmentShader + "\n");

            throw new RuntimeException("Could not compile water shader: " + this.waterShader.getLog());
        }
    }

    protected void loadMap (SectorCoord coord) {
        //load map from cache or from file

    }

    public void update (BaseGame game, Camera camera, GameTime time) {
        //update skybox
        if (this.skyBox != null) {
            this.skyBox.update(game, time);
        }

        //check, if some maps arent visible anymore and remove them from draw queue
        this.visibleMaps.stream().forEach(map -> {
            if (!map.isMapVisibleInViewPort(camera)) {
                //remove map from render queue
                this.visibleMaps.remove(map);
            }
        });

        //TODO: check, if user walk to near maps, if so, load near maps to draw queue
    }

    public void draw (GameTime time, Camera camera, SpriteBatch batch) {
        //batch.draw(testTexture, 0, 0);

        //first draw water
        this.drawWater(time, camera, batch);

        batch.end();

        //set default shader
        batch.setProjectionMatrix(camera.combined);
        batch.setShader(this.currentShader);
        batch.begin();

        //draw skybox
        if (this.skyBox != null) {
            this.skyBox.draw(time, batch);
        }

        batch.setProjectionMatrix(camera.combined);

        //render all maps which are visible
        this.visibleMaps.stream().forEach(map -> {
            //draw map
            map.draw(time, camera, batch);
        });
    }

    protected void drawWater (GameTime time, Camera camera, SpriteBatch batch) {
        if (!animateWater) {
            //draw water layer of maps
            this.visibleMaps.stream().forEach(map -> {
                map.drawWater(time, camera, batch);
            });

            return;
        }

        /**
        *  special thanks to the author of this tutorial:
         *
         * http://www.alcove-games.com/opengl-es-2-tutorials/vertex-shader-for-tiled-water/
        */

        batch.flush();
        //batch.end();

        final float dt = time.getDeltaTime();

        //calculate angel wave
        angleWave += dt * angleWaveSpeed;
        while(angleWave > PI2)
            angleWave -= PI2;

        /*System.out.println("================");
        System.out.println("Delta: " + dt);
        System.out.println("angleWaveSpeed: " + angleWaveSpeed);
        System.out.println("angleWave: " + angleWave);
        System.out.println("amplitudeWave: " + amplitudeWave);*/

        //feed the shader with the new data
        waterShader.begin();
        waterShader.setUniformf("waveData", angleWave, amplitudeWave);
        waterShader.end();

        //render the first layer (the water) using our special vertex shader
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.setShader(this.waterShader);
        //batch.begin();

        //draw water layer of maps
        this.visibleMaps.stream().forEach(map -> {
            map.drawWater(time, camera, batch);
        });

        //batch.draw(testTexture, 0, 0);

        //tilemap.render(batch, 0, dt);
        batch.flush();
    }

    public void dispose () {
        this.waterShader.dispose();
        this.waterShader = null;

        this.defaultShader.dispose();
        this.defaultShader = null;
    }

    public void setAnimateWater (boolean flag) {
        this.animateWater = flag;
    }

    /**
    * set shader to draw maps (for lighting system)
    */
    public void setCurrentShader (ShaderProgram shader) {
        if (shader == null) {
            this.currentShader = this.defaultShader;
        } else {
            this.currentShader = shader;
        }
    }

    public SkyBox getSkyBox () {
        return this.skyBox;
    }

    public void setSkyBox (SkyBox skyBox) {
        this.skyBox = skyBox;
    }

}
