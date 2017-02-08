package com.jukusoft.libgdx.rpg.engine.save;

/**
 * Created by Justin on 08.02.2017.
 */
@FunctionalInterface
public interface GameLoader<T extends SavedGameInstance> {

    /**
    * load game
     *
     * @param savePath path to saved game
    */
    public T loadGame (final String savePath);

}
