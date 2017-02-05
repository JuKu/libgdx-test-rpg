package com.jukusoft.libgdx.rpg.engine.screen.impl;

import com.jukusoft.libgdx.rpg.engine.exception.ScreenNotFoundException;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.screen.IScreen;
import com.jukusoft.libgdx.rpg.engine.screen.ScreenManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Justin on 06.02.2017.
 */
public class DefaultScreenManager implements ScreenManager<IScreen> {

    /**
    * map with all initialized screens
    */
    protected Map<String,IScreen> screens = new ConcurrentHashMap<>();

    /**
    * list with all active screens
    */
    protected Deque<IScreen> activeScreens = new ConcurrentLinkedDeque<>();

    /**
    * only for performance improvements!
     *
     * caching list
    */
    protected List<IScreen> cachedScreenList = new ArrayList<>();

    protected final BaseGame game;

    public DefaultScreenManager (BaseGame game) {
        this.game = game;
    }

    @Override public void addScreen(String name, IScreen screen) {
        //initialize screen first
        screen.init(game);

        this.screens.put(name, screen);

        this.cachedScreenList.add(screen);
    }

    @Override public void removeScreen(String name) {
        IScreen screen = this.screens.get(name);

        this.screens.remove(screen);

        if (screen != null) {
            screen.destroy();

            this.cachedScreenList.remove(screen);
        }
    }

    @Override public void push(String name) {
        IScreen screen = this.screens.get(name);

        if (screen == null) {
            throw new ScreenNotFoundException("Couldnt found initialized screen '" + name + "', add screen with method addScreen() first.");
        }

        screen.onResume();

        this.activeScreens.push(screen);
    }

    @Override public void pop() {
        this.activeScreens.pop();
    }

    @Override public Collection<IScreen> listScreens() {
        return this.cachedScreenList;
    }

    @Override public Collection<IScreen> listActiveScreens() {
        return this.activeScreens;
    }

}
