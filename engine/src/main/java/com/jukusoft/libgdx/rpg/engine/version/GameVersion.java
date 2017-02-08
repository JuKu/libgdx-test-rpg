package com.jukusoft.libgdx.rpg.engine.version;

import java.io.File;

/**
 * Created by Justin on 08.02.2017.
 */
public class GameVersion {

    protected File cfgFile = null;

    public GameVersion (final String cfgPath) {
        if (!(new File(cfgPath).exists())) {
            throw new IllegalArgumentException("version configuration file '" + cfgPath + "' doesnt exists!");
        }

        this.cfgFile = new File(cfgPath);

        if (!this.cfgFile.canRead()) {
            throw new IllegalStateException("version configuration file '" + cfgPath + "' isnt readable, file permissions are wrong.");
        }

        //load configuration file
        this.load();
    }

    protected void load () {
        //
    }

}
