package com.jukusoft.libgdx.rpg.game.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.jukusoft.libgdx.rpg.engine.exception.MapNotFoundException;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.skybox.SkyBox;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.FileUtils;
import com.jukusoft.libgdx.rpg.engine.world.SectorCoord;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Justin on 09.02.2017.
 */
public class GameWorld {

    protected Map<SectorCoord,GameWorldMap> gameWorldMap = new ConcurrentHashMap<>();

    /**
    * all game world maps which are shown on screen
    */
    protected List<GameWorldMap> visibleMaps = new ArrayList<>();

    //because we cannot add or remove entries to list, while iterating over it, we need an extra list for this
    List<GameWorldMap> mapsToRemove = new ArrayList<>();

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

    protected int NEAR_X_MAPS = 1;
    protected int NEAR_Y_MAPS = 1;

    //map with all cached maps
    protected Map<SectorCoord,GameWorldMap> mapCache = new ConcurrentHashMap<>();

    protected List<SectorChangedListener> sectorChangedListenerList = new ArrayList<>();
    protected BaseGame game = null;

    //https://github.com/libgdx/libgdx/wiki/Tile-maps

    public GameWorld (BaseGame game, SectorCoord coord, Texture texture) {
        this.game = game;

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
        System.out.println("load map: " + coord);

        GameWorldMap map = this.mapCache.get(coord);

        //check, if map is cached
        if (map == null) {
            //load map
            map = new GameWorldMap(this.game, coord);

            try {
                map.load();
            } catch (MapNotFoundException e) {
                e.printStackTrace();
            }

            //add map to cache
            this.mapCache.put(coord, map);
        }

        //check, if map is near current map
        if (isMapNearCurrent(coord)) {
            System.out.println("load new visible map: " + coord);
            this.visibleMaps.add(map);
        }
    }

    public boolean isMapNearCurrent (SectorCoord coord) {
        if (this.currentSector.getLayer() != coord.getLayer()) {
            //maps on other layers arent near by another map
            return false;
        }

        if (currentSector.getX() - NEAR_X_MAPS >= coord.getX() || coord.getX() <= currentSector.getX() + NEAR_X_MAPS) {
            if (currentSector.getY() - NEAR_Y_MAPS >= coord.getY() || coord.getY() <= currentSector.getY() + NEAR_Y_MAPS) {
                return true;
            }
        }

        return false;
    }

    public void cleanUpVisibleMapCache () {
        for (GameWorldMap map : this.visibleMaps) {
            if (!this.isMapNearCurrent(map.getSectorCoord())) {
                System.out.println("cleanUp map: " + map.getSectorCoord());

                //remove map from cache
                this.visibleMaps.remove(map);
                this.mapCache.remove(map.getSectorCoord());

                //dispose map
                map.dispose();

                map = null;
            }
        }
    }

    public void update (BaseGame game, Camera camera, GameTime time) {
        //update skybox
        if (this.skyBox != null) {
            this.skyBox.update(game, time);
        }

        //because we cannot add or remove entries to list, while iterating over it, we need an extra list for this
        this.mapsToRemove.clear();

        //check, if some maps arent visible anymore and remove them from draw queue
        for (GameWorldMap map : this.visibleMaps) {
            if (!map.isMapVisibleInViewPort(camera)) {
                //remove map from render queue

                System.out.println("remove map, because its out of viewport: " + map.getSectorCoord());

                //because we cannot add or remove entries to list, while iterating over it, we need an extra list for this
                this.mapsToRemove.add(map);

                //this.visibleMaps.remove(map);
            }
        }

        for (GameWorldMap map : mapsToRemove) {
            this.visibleMaps.remove(map);
        }

        //cleanUp not visible maps
        this.cleanUpVisibleMapCache();

        //TODO: check, if user walk to near maps, if so, load near maps to draw queue
    }

    public void draw (GameTime time, Camera camera, ShaderProgram shader, SpriteBatch batch) {
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
        batch.setShader(this.currentShader);

        //render all maps which are visible
        this.visibleMaps.stream().forEach(map -> {
            //draw map
            map.draw(time, camera, this.currentShader, batch);
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

    public SectorCoord getCurrentSector () {
        return this.currentSector;
    }

    public void setCurrentSector (int x, int y, int layer) {
        this.currentSector.setX(x);
        this.currentSector.setY(y);
        this.currentSector.setLayer(layer);

        this.notifySectorChangedListener();
    }

    protected void notifySectorChangedListener () {
        this.sectorChangedListenerList.stream().forEach(listener -> {
            listener.sectorChanged(this.currentSector);
        });
    }

    public void addSectorChangedListener (SectorChangedListener listener) {
        this.sectorChangedListenerList.add(listener);
    }

    public void removeSectorChangedListener (SectorChangedListener listener) {
        this.sectorChangedListenerList.remove(listener);
    }

    public synchronized void devOptionLoadMap (String mapFile) {
        if (!(new File(mapFile)).exists()) {
            System.err.println("Map file doesnt exists: " + mapFile);
            return;
        }

        SectorCoord NULL_SECTOR = new SectorCoord(0, 0, 0);

        GameWorldMap map = this.mapCache.get(NULL_SECTOR);

        if (map == null) {
            map = new GameWorldMap(game, NULL_SECTOR);
        }

        try {
            map.load(mapFile);
        } catch (MapNotFoundException e) {
            e.printStackTrace();
        }

        this.mapCache.put(NULL_SECTOR, map);

        if (!this.visibleMaps.contains(map)) {
            this.visibleMaps.add(map);
        }
    }

}
