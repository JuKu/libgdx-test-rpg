package com.jukusoft.libgdx.rpg.engine.save.impl;

import com.jukusoft.libgdx.rpg.engine.exception.InvaildeSavedGameException;
import com.jukusoft.libgdx.rpg.engine.exception.NoGameLoaderFoundException;
import com.jukusoft.libgdx.rpg.engine.exception.NoGameSaverFoundException;
import com.jukusoft.libgdx.rpg.engine.save.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
    * map with all available game info loaders
    */
    protected Map<Class,SavedGameInfoLoader> infoLoaderMap = new ConcurrentHashMap<>();

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

        //add default saved game info loader
        this.registerInfoLoader(new DefaultSavedGameInfoLoader(), SavedGameInfo.class);
    }

    @Override public List<String> listSavedGameNames() {
        List<String> list = new ArrayList<>();

        //list all directories and files in save directory
        File[] files = this.savesDir.listFiles();

        for (File file : files) {
            //only directories should be listed
            if (file.isDirectory()) {
                String path = file.getAbsolutePath();

                if (path.endsWith("/")) {
                    path = path.substring(0, path.length() - 1);
                }

                String[] strArray = path.replace("\\", "/").split("/");

                //only add filename to list
                list.add(strArray[strArray.length - 1]);
            }
        }

        return list;
    }

    @Override public <T extends SavedGameInfo,V extends T> List<T> listSavedGames(Class<T> cls, Class<V> brokenGameInfoClass) {
        //get info loader
        SavedGameInfoLoader<T> loader = this.getInfoLoader(cls);

        //create new empty list
        List<T> list = new ArrayList<T>();

        //get list of all available saved games
        List<String> savedGameNames = this.listSavedGameNames();

        //iterate through all saved game directories
        for (String saveName : savedGameNames) {
            T info = null;

            try {
                info = loader.load(this.savesPath + saveName);
            } catch (InvaildeSavedGameException e) {
                e.printStackTrace();

                try {
                    info = brokenGameInfoClass.newInstance();

                    if (info instanceof IBrokenSavedGameInfo) {
                        IBrokenSavedGameInfo brokenSavedGameInfo = (IBrokenSavedGameInfo) info;
                        brokenSavedGameInfo.setSaveDir(new File(this.savesDir + saveName));
                        brokenSavedGameInfo.setName(saveName);
                    }
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                    throw new RuntimeException("InstantiationException: " + e1.getLocalizedMessage());
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                    throw new RuntimeException("IllegalAccessException: " + e1.getLocalizedMessage());
                }
            }

            list.add(info);
        }

        //sort list with last played date
        Collections.sort(list);

        return list;
    }

    @Override public <T extends SavedGameInfo> SavedGameInfoLoader<T> getInfoLoader(Class<T> cls) {
        SavedGameInfoLoader<T> loader = this.infoLoaderMap.get(cls);

        if (loader == null) {
            throw new NoGameLoaderFoundException("Cound not found any game info loader for class: " + cls.getCanonicalName());
        }

        return loader;
    }

    @Override public <T extends SavedGameInfo> void registerInfoLoader(SavedGameInfoLoader<T> loader, Class<T> cls) {
        this.infoLoaderMap.put(cls, loader);
    }

    @Override public <T extends SavedGameInfo> void removeInfoLoader(Class<T> cls) {
        this.infoLoaderMap.remove(cls);
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
