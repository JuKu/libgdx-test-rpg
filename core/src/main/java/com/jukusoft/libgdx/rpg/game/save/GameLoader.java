package com.jukusoft.libgdx.rpg.game.save;

/**
 * Created by Justin on 08.02.2017.
 */
@FunctionalInterface
public interface GameLoader {

    /**
    * load game
     *
     * @param savePath path to saved game
    */
    public SavedGameInstance loadGame (final String savePath);

}
