package com.jukusoft.libgdx.rpg.engine.save.impl;

import com.jukusoft.libgdx.rpg.engine.exception.FilePermissionException;
import com.jukusoft.libgdx.rpg.engine.exception.InvaildeSavedGameException;
import com.jukusoft.libgdx.rpg.engine.save.SavedGameInfo;
import com.jukusoft.libgdx.rpg.engine.save.SavedGameInfoLoader;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Justin on 08.02.2017.
 */
public class DefaultSavedGameInfoLoader implements SavedGameInfoLoader<SavedGameInfo> {

    @Override public SavedGameInfo load(String saveDir) throws InvaildeSavedGameException {
        //for windows compatibility
        saveDir = saveDir.replace("\\", "/");

        //add slash if neccessary
        if (!saveDir.endsWith("/")) {
            saveDir = saveDir + "/";
        }

        String savePath = saveDir.substring(0, -1);
        String[] strArray = savePath.split("/");
        String saveName = strArray[strArray.length - 1];

        //check, if info file exists
        File infoFile = new File(saveDir + "info.json");

        //check, if info file exists
        if (!infoFile.exists()) {
            throw new InvaildeSavedGameException("saved game in path '" + saveDir + "' is broken, no info file found.");
        }

        //check, if info file is readable
        if (!infoFile.canRead()) {
            throw new FilePermissionException("Cannot read saved game info file: " + infoFile.getAbsolutePath() + ".");
        }

        JSONObject json = new JSONObject();

        //create new instance of saved game info
        SavedGameInfo gameInfo = new SavedGameInfo(new File(saveDir), saveName);

        //parse info.json

        //get version of game client which has saved this game
        int gameVersion = json.getInt("game_version");
        gameInfo.setGameVersion(gameVersion);

        //get game title
        String title = json.getString("title");
        gameInfo.setTitle(title);

        //get game icon path
        String iconPath = json.getString("icon_path");
        gameInfo.setGameIcon(iconPath);

        //get last played timestamp
        long lastPlayedTimestamp = json.getLong("last_played_timestamp");
        gameInfo.setLastPlayedTimestamp(lastPlayedTimestamp);

        return gameInfo;
    }

}
