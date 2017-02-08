package com.jukusoft.libgdx.rpg.engine.save.impl;

import com.jukusoft.libgdx.rpg.engine.exception.NoGameLoaderFoundException;
import com.jukusoft.libgdx.rpg.engine.exception.NoGameSaverFoundException;
import com.jukusoft.libgdx.rpg.engine.save.GameLoader;
import com.jukusoft.libgdx.rpg.engine.save.GameSaver;
import com.jukusoft.libgdx.rpg.engine.save.SavedGameInstance;
import com.jukusoft.libgdx.rpg.engine.save.SavedGameManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 08.02.2017.
 */
public class DefaultSavedGameManager implements SavedGameManager {

    protected String savesPath = "";

    protected File savesDir = null;

    /**
    * map with all available game loaders
    */
    protected Map<Class,GameLoader> gameLoaderMap = new ConcurrentHashMap<>();

    /**
    * map with all available game savers
    */
    protected Map<Class,GameSaver> gameSaverMap = new ConcurrentHashMap<>();

    /**
    * default constructor
    */
    public DefaultSavedGameManager (final String savesPath) {
        this.savesPath = savesPath;
        this.savesDir = new File(savesPath);

        //check, if path exists
        if (!this.savesDir.exists()) {
            //create directory
            this.savesDir.mkdirs();

            System.out.println("Create new saves directory: " + this.savesDir.getAbsolutePath());
        }
    }

    @Override public List<String> listSavedGameNames() {
        List<String> list = new ArrayList<>();

        //list all directories and files in save directory
        File[] files = this.savesDir.listFiles();

        for (File file : files) {
            //only directories should be listed
            if (file.isDirectory()) {
                String[] strArray = file.getAbsolutePath().replace("\\", "/").split("/");

                //only add filename to list
                list.add(strArray[strArray.length - 1]);
            }
        }

        return list;
    }

    @Override public <T extends SavedGameInstance> GameLoader<T> getGameLoader(Class<T> cls) {
        GameLoader<T> loader = this.gameLoaderMap.get(cls);

        if (loader == null) {
            throw new NoGameLoaderFoundException("Could not found any game loader for class: " + cls.getCanonicalName());
        }

        return loader;
    }

    @Override public <T extends SavedGameInstance> void registerLoader(GameLoader<T> loader, Class<T> cls) {
        this.gameLoaderMap.put(cls, loader);
    }

    @Override public <T extends SavedGameInstance> void removeLoader(Class<T> cls) {
        this.gameLoaderMap.remove(cls);
    }

    @Override public <T extends SavedGameInstance> GameSaver<T> getGameSaver(Class<T> cls) {
        GameSaver<T> saver = this.gameSaverMap.get(cls);

        if (saver == null) {
            throw new NoGameSaverFoundException("Could not found any game saver for class: " + cls.getCanonicalName());
        }

        return saver;
    }

    @Override public <T extends SavedGameInstance> void registerSaver(GameSaver<T> loader, Class<T> cls) {
        this.gameSaverMap.put(cls, loader);
    }

    @Override public <T extends SavedGameInstance> void removeSaver(Class<T> cls) {
        this.gameSaverMap.remove(cls);
    }

}