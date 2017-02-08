package com.jukusoft.libgdx.rpg.engine.save.impl;

import com.jukusoft.libgdx.rpg.engine.exception.GameSaverException;
import com.jukusoft.libgdx.rpg.engine.save.GameInfoSaver;
import com.jukusoft.libgdx.rpg.engine.save.SavedGameInfo;
import com.jukusoft.libgdx.rpg.engine.utils.FileUtils;
import com.jukusoft.libgdx.rpg.engine.version.GameVersion;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by Justin on 08.02.2017.
 */
public class DefaultGameInfoSaver implements GameInfoSaver<SavedGameInfo> {

    @Override public void saveGameInfo(String saveDir, GameVersion version, SavedGameInfo gameInfo) throws GameSaverException {
        //for windows compatibility
        saveDir = saveDir.replace("\\", "/");

        //add slash if neccessary
        if (!saveDir.endsWith("/")) {
            saveDir = saveDir + "/";
        }

        File infoFile = new File(saveDir + "info.json");

        JSONObject json = new JSONObject();

        //save data
        json.put("name", gameInfo.getName());
        json.put("game_version", version.getVersion());
        json.put("game_build", version.getBuild());
        json.put("character_name", gameInfo.getCharacterName());
        json.put("title", gameInfo.getTitle());
        json.put("icon_path", gameInfo.getGameIcon());
        json.put("last_played_timestamp", System.currentTimeMillis());

        try {
            //write file
            FileUtils.writeFile(infoFile.getAbsolutePath(), json.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GameSaverException("Cannot save game info in path '" + infoFile.getAbsolutePath() + "', IOException: " + e.getLocalizedMessage());
        }
    }

}
