package com.jukusoft.libgdx.rpg.game.save;

/**
 * Created by Justin on 08.02.2017.
 */
@FunctionalInterface
public interface GameSaver {

    /**
    * save game
     *
     * @param savePath path to saved game
     * @param savedGameInstance instance of game
    */
    public void save (final String savePath, SavedGameInstance savedGameInstance);

}
