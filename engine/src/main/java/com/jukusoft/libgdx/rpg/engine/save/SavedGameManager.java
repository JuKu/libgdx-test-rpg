package com.jukusoft.libgdx.rpg.engine.save;

import java.util.List;

/**
 * Created by Justin on 08.02.2017.
 */
public interface SavedGameManager {

    /**
    * list all saved games
    */
    public List<String> listSavedGameNames ();

    public <T extends SavedGameInstance> GameLoader<T> getGameLoader (Class<T> cls);

    public <T extends SavedGameInstance> void registerLoader (GameLoader<T> loader, Class<T> cls);

    public <T extends SavedGameInstance> void removeLoader (Class<T> cls);

    public <T extends SavedGameInstance> GameSaver<T> getGameSaver (Class<T> cls);

    public <T extends SavedGameInstance> void registerSaver (GameSaver<T> loader, Class<T> cls);

    public <T extends SavedGameInstance> void removeSaver (Class<T> cls);

}
