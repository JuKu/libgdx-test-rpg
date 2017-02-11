package com.jukusoft.libgdx.rpg.engine.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.input.InputManager;
import com.jukusoft.libgdx.rpg.engine.input.impl.DefaultInputManager;
import com.jukusoft.libgdx.rpg.engine.save.SavedGameManager;
import com.jukusoft.libgdx.rpg.engine.settings.GameSettings;
import com.jukusoft.libgdx.rpg.engine.settings.IniGameSettings;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.FileUtils;
import com.jukusoft.libgdx.rpg.engine.version.GameVersion;
import com.jukusoft.libgdx.rpg.engine.window.ResizeListener;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Justin on 06.02.2017.
 */
public abstract class BaseGame extends ApplicationAdapter {

    /**
    * list with resize listeners
    */
    protected List<ResizeListener> resizeListeners = new ArrayList<>();

    /**
    * instance of game time
    */
    protected GameTime time = GameTime.getInstance();

    /**
    * backround color
    */
    protected Color bgColor = Color.BLACK;

    /**
    * sprite batcher
    */
    protected SpriteBatch batch = null;

    /**
    * instance of asset manager
    */
    protected final AssetManager assetManager = new AssetManager();

    protected static int VIEWPORT_WIDTH = 1280;
    protected static int VIEWPORT_HEIGHT = 720;

    protected OrthographicCamera camera = null;
    protected CameraWrapper cameraWrapper = null;
    private Camera uiCamera = null;

    protected AtomicBoolean useCamera = new AtomicBoolean(false);

    protected String settingsDir = "./data/config/";

    /**
    * map with all game settings
    */
    protected Map<String,GameSettings> settingsMap = new ConcurrentHashMap<>();

    /**
    * version information
    */
    protected GameVersion version = null;

    protected SavedGameManager savedGameManager = null;

    protected FPSLogger fpsLogger = new FPSLogger();
    protected String shaderPath = "./data/shader/";

    /**
    * instance of input manager
    */
    protected InputManager inputManager = null;

    //tasks which should be executed in OpenGL context thread
    protected Queue<Runnable> uiQueue = new ConcurrentLinkedQueue<>();

    protected long lastFPSWarning = 0;

    @Override
    public void resize(final int width, final int height) {
        this.resizeListeners.stream().forEach(consumer -> {
            consumer.onResize(width, height);
        });
    }

    public void addResizeListener (ResizeListener listener) {
        this.resizeListeners.add(listener);
    }

    public void removeResizeListener (ResizeListener listener) {
        this.resizeListeners.remove(listener);
    }

    public AssetManager getAssetManager () {
        return this.assetManager;
    }

    @Override
    public final void create() {
        //create sprite batcher
        this.batch = new SpriteBatch();

        this.VIEWPORT_WIDTH = Gdx.graphics.getWidth();
        this.VIEWPORT_HEIGHT = Gdx.graphics.getHeight();

        //initialize camera
        this.camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        this.camera.translate(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2, 0);
        //this.camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);

        this.cameraWrapper = new CameraWrapper(this.camera);
        this.cameraWrapper.update();

        //initialize UI camera
        this.uiCamera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        this.uiCamera.translate(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2, 0);
        this.uiCamera.update();

        //create new input manager
        this.inputManager = new DefaultInputManager();

        //add loader for tmx maps
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new AbsoluteFileHandleResolver()));

        try {
            this.initGame();
        } catch (Exception e) {
            e.printStackTrace();

            try {
                //write crash dump
                FileUtils.writeFile("./crash.log", e.getLocalizedMessage(), StandardCharsets.UTF_8);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            System.exit(0);
        }
    }

    @Override
    public final void render() {
        //update time
        this.time.update();

        int fps = getFPS();
        if (fps <= 55 && fps != 0) {
            //check, if warning was already log this second
            long now = System.currentTimeMillis();
            long nowWarnSecond = now / 1000;
            long lastWarnSecond = lastFPSWarning / 1000;

            if (nowWarnSecond != lastWarnSecond) {
                System.err.println("Warning! FPS is <= 55, FPS: " + fps);

                lastFPSWarning = System.currentTimeMillis();
            }
        }

        //execute tasks, which should be executed in OpenGL context thread
        Runnable runnable = uiQueue.poll();

        while (runnable != null) {
            runnable.run();

            runnable = uiQueue.poll();
        }

        //update game
        this.update(this.time);

        //update camera
        //this.camera.update();
        this.cameraWrapper.update();

        //update UI camera
        this.uiCamera.update();

        //clear all color buffer bits and clear screen
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.batch.begin();

        this.batch.setProjectionMatrix(this.camera.combined);

        //draw game
        this.draw(time, this.batch);

        this.batch.end();

        //this.fpsLogger.log();
    }

    protected abstract void initGame ();

    public CameraWrapper getCamera () {
        return this.cameraWrapper;
    }

    public /*OrthographicCamera*/CameraWrapper getCamera2D () {
        return this.cameraWrapper;
    }

    public Camera getUICamera () {
        return this.uiCamera;
    }

    public int getFPS () {
        return Gdx.graphics.getFramesPerSecond();
    }

    public InputManager getInputManager () {
        return this.inputManager;
    }

    public String getShaderDir () {
        return this.shaderPath;
    }

    public void runOnUIThread (Runnable runnable) {
        this.uiQueue.offer(runnable);
    }

    /**
    * get instance of settings or null, if instance doesnst exists
     *
     * @param name name of settings
     *
     * @return instance of settings
    */
    public GameSettings getSettings (String name) {
        name = name.toLowerCase();

        GameSettings settings = this.settingsMap.get(name);

        if (settings == null) {
            throw new NullPointerException("instance of settings (name: " + name + ") is null.");
        }

        return settings;
    }

    /**
     * get instance of global settings
     *
     * @return instance of global settings
     */
    public GameSettings getSettings () {
        //get global settings
        return this.getSettings("game");
    }

    /**
    * load settings
    */
    public boolean loadSettings (String name, String path) {
        name = name.toLowerCase();
        path = path.toLowerCase();

        GameSettings settings = new IniGameSettings();

        try {
            if (new File(path).exists()) {
                settings.loadFromFile(new File(path));
            } else {
                if (new File(settingsDir + path).exists()) {
                    settings.loadFromFile(new File(settingsDir + path));
                } else {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        this.settingsMap.put(name, settings);

        return true;
    }

    public float getVolume () {
        return this.getSettings().getFloat("Music", "volume");
    }

    public String getLang () {
        return this.getSettings().getOrDefault("Game", "lang", "en");
    }

    /**
    * get version information
     *
     * @return version information
    */
    public GameVersion getVersion () {
        if (this.version == null) {
            throw new IllegalStateException("Cannot get version, you have to set version information with method setVersion() first.");
        }

        return this.version;
    }

    /**
    * set version information
     *
     * @param version version information
    */
    public void setVersion (GameVersion version) {
        if (version == null) {
            throw new NullPointerException("version is null.");
        }

        this.version = version;
    }

    public SavedGameManager getSavedGameManager () {
        if (this.savedGameManager == null) {
            throw new IllegalStateException("Cannot get saved game manager, you have to set saved game manager with method setSavedGameManager() first.");
        }

        return this.savedGameManager;
    }

    public void setSavedGameManager (SavedGameManager savedGameManager) {
        if (savedGameManager == null) {
            throw new NullPointerException("saved game manager is null.");
        }

        this.savedGameManager = savedGameManager;
    }

    public int getViewportWidth () {
        return this.VIEWPORT_WIDTH;
    }

    public int getViewportHeight () {
        return this.VIEWPORT_HEIGHT;
    }

    protected abstract void update (GameTime time);

    protected abstract void draw (GameTime time, SpriteBatch batch);

    @Override
    public final void dispose() {
        this.destroyGame();

        this.batch.dispose();
    }

    protected abstract void destroyGame ();

}
