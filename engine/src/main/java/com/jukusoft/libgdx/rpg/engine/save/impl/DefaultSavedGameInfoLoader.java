package com.jukusoft.libgdx.rpg.engine.save.impl;

import com.jukusoft.libgdx.rpg.engine.exception.FilePermissionException;
import com.jukusoft.libgdx.rpg.engine.exception.InvaildeSavedGameException;
import com.jukusoft.libgdx.rpg.engine.save.SavedGameInfo;
import com.jukusoft.libgdx.rpg.engine.save.SavedGameInfoLoader;
import com.jukusoft.libgdx.rpg.engine.utils.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

        String savePath = saveDir.substring(0, saveDir.length() - 1);
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

        String fileContent = "";

        try {
            fileContent = FileUtils.readFile(infoFile.getAbsolutePath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvaildeSavedGameException("saved game in path '" + saveDir + "' is broken, IOException thrown: " + e.getLocalizedMessage());
        }

        JSONObject json = new JSONObject(fileContent);

        //create new instance of saved game info
        SavedGameInfo gameInfo = new SavedGameInfo(new File(saveDir), saveName);

        try {
            //parse info.json

            //get version of game client which has saved this game
            int gameVersion = json.getInt("game_version");
            gameInfo.setGameVersion(gameVersion);

            //get character name
            String characterName = json.getString("character_name");
            gameInfo.setCharacterName(characterName);

            //get game title
            String title = json.getString("title");
            gameInfo.setTitle(title);

            //get game icon path
            String iconPath = json.getString("icon_path");
            gameInfo.setGameIcon(iconPath);

            //get last played timestamp
            long lastPlayedTimestamp = json.getLong("last_played_timestamp");
            gameInfo.setLastPlayedTimestamp(lastPlayedTimestamp);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new InvaildeSavedGameException("JSONException while loading game info file '" + infoFile.getAbsolutePath() + "': " + e.getLocalizedMessage());
        }

        //return instance of game info
        return gameInfo;
    }

}
