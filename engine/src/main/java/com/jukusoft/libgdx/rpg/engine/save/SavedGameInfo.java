package com.jukusoft.libgdx.rpg.engine.save;

import java.io.File;

/**
 * Created by Justin on 08.02.2017.
 */
public class SavedGameInfo implements Comparable<SavedGameInfo> {

    protected File saveDir = null;
    protected String name = "";
    protected String characterName = "";

    //version of game client, which has saved this game
    protected int gameVersion = 1;

    protected String title = "";

    //path to specific game icon, for example every level can have an own icon
    protected String gameIcon = "";

    protected long lastPlayedTimestamp = 0l;

    public SavedGameInfo (File saveDir, String name) {
        this.saveDir = saveDir;
        this.name = name;
    }

    public File getSaveDir () {
        return this.saveDir;
    }

    public String getName () {
        return this.name;
    }

    public String getCharacterName () {
        return this.characterName;
    }

    public void setCharacterName (String characterName) {
        this.characterName = characterName;
    }

    public int getGameVersion () {
        return this.gameVersion;
    }

    public void setGameVersion (int version) {
        this.gameVersion = version;
    }

    public String getTitle () {
        return this.title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getGameIcon () {
        return this.gameIcon;
    }

    public void setGameIcon (String iconPath) {
        this.gameIcon = iconPath;
    }

    public long getLastPlayedTimestamp () {
        return this.lastPlayedTimestamp;
    }

    public void setLastPlayedTimestamp (final long timestamp) {
        this.lastPlayedTimestamp = timestamp;
    }

    @Override public int compareTo(SavedGameInfo o) {
        return ((Long) this.lastPlayedTimestamp).compareTo(o.getLastPlayedTimestamp());
    }
}
