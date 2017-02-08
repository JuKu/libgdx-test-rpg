package com.jukusoft.libgdx.rpg.engine.save;

import java.util.List;

/**
 * Created by Justin on 08.02.2017.
 */
public interface SavedGameManager {

    /**
    * list all saved game names
     *
     * @return list with names of all saved games
    */
    public List<String> listSavedGameNames ();

    /**
     * list all saved games
     *
     * @return list with information for every saved game
     */
    public <T extends SavedGameInfo,V extends T> List<T> listSavedGames(Class<T> cls, Class<V> brokenGameInfoClass);

    public <T extends SavedGameInfo> SavedGameInfoLoader<T> getInfoLoader (Class<T> cls);

    public <T extends SavedGameInfo> void registerInfoLoader (SavedGameInfoLoader<T> loader, Class<T> cls);

    public <T extends SavedGameInfo> void removeInfoLoader (Class<T> cls);

    public <T extends SavedGameInstance> GameLoader<T> getGameLoader (Class<T> cls);

    public <T extends SavedGameInstance> void registerLoader (GameLoader<T> loader, Class<T> cls);

    public <T extends SavedGameInstance> void removeLoader (Class<T> cls);

    public <T extends SavedGameInstance> GameSaver<T> getGameSaver (Class<T> cls);

    public <T extends SavedGameInstance> void registerSaver (GameSaver<T> loader, Class<T> cls);

    public <T extends SavedGameInstance> void removeSaver (Class<T> cls);

}
