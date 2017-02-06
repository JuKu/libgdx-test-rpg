package com.jukusoft.libgdx.rpg.engine.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.window.ResizeListener;

import java.util.ArrayList;
import java.util.List;
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

    protected Camera camera = null;

    protected AtomicBoolean useCamera = new AtomicBoolean(false);

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

        //initialize camera
        this.camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        this.camera.translate(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2, 0);
        this.camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);

        this.initGame();
    }

    @Override
    public final void render() {
        //update time
        this.time.update();

        //update game
        this.update(this.time);

        //update camera
        this.camera.update();

        //clear all color buffer bits and clear screen
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.batch.begin();

        //this.batch.setProjectionMatrix(this.camera.combined);

        //draw game
        this.draw(time, this.batch);

        this.batch.end();
    }

    protected abstract void initGame ();

    protected abstract void update (GameTime time);

    protected abstract void draw (GameTime time, SpriteBatch batch);

    @Override
    public final void dispose() {
        this.destroyGame();

        this.batch.dispose();
    }

    protected abstract void destroyGame ();

}
