package com.jukusoft.libgdx.rpg.engine.save;

import com.jukusoft.libgdx.rpg.engine.exception.InvaildeSavedGameException;

/**
 * Created by Justin on 08.02.2017.
 */
@FunctionalInterface
public interface SavedGameInfoLoader<T extends SavedGameInfo> {

    public T load (final String saveDir) throws InvaildeSavedGameException;

}
