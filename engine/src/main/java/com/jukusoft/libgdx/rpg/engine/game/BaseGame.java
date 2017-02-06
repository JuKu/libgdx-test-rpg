package com.jukusoft.libgdx.rpg.engine.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.window.ResizeListener;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public final void create() {
        //create sprite batcher
        this.batch = new SpriteBatch();

        this.initGame();
    }

    @Override
    public final void render() {
        //update time
        this.time.update();

        //update game
        this.update(this.time);

        //clear all color buffer bits and clear screen
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.batch.begin();

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
