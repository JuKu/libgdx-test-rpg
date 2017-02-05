package com.jukusoft.libgdx.rpg.engine.game;

import com.badlogic.gdx.ApplicationAdapter;
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

}
