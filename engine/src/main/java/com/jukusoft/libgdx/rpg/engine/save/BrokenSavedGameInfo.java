package com.jukusoft.libgdx.rpg.engine.save;

import java.io.File;

/**
 * Created by Justin on 08.02.2017.
 */
public class BrokenSavedGameInfo extends SavedGameInfo implements IBrokenSavedGameInfo {

    public BrokenSavedGameInfo() {
        super(new File("."), "");
    }

    public void setSaveDir (File dir) {
        this.saveDir = dir;
    }

    public void setName (String name) {
        this.name = name;
    }

}
