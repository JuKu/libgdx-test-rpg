package com.jukusoft.libgdx.rpg.engine.screen;

import java.util.Collection;

/**
 * Created by Justin on 06.02.2017.
 */
public interface ScreenManager<T extends IScreen> {

    /**
    * add screen
     *
     * @param name name of screen
     * @param screen instance of screen
    */
    public void addScreen (final String name, T screen);

    /**
    * remove screen
     *
     * @param name name of screen
    */
    public void removeScreen (final String name);

    /**
    * push screen
    */
    public void push (final String name);

    /**
    * pop screen
    */
    public T pop ();

    /**
    * list all initialized screens
    */
    public Collection<T> listScreens ();

    /**
    * list all active screens
    */
    public Collection<T> listActiveScreens ();

}
