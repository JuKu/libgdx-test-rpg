package com.jukusoft.libgdx.rpg.engine.save;

/**
 * Created by Justin on 08.02.2017.
 */
public abstract class SavedGameInstance extends GameInstance {

    protected String name = null;

    /**
    * private constructor
    */
    protected SavedGameInstance() {
        //
    }

    /*public static final SavedGameInstance createNewGame (final String saveName) {
        SavedGameInstance game = new SavedGameInstance();

        return game;
    }

    public static final SavedGameInstance loadFromDir (final String saveName) {
        SavedGameInstance game = new SavedGameInstance();

        return game;
    }*/

}
