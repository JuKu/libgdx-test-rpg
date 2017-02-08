package com.jukusoft.libgdx.rpg.engine.version;

import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.IOException;

/**
 * Created by Justin on 08.02.2017.
 */
public class GameVersion {

    protected File cfgFile = null;

    //version information
    protected int buildNumber = 0;
    protected int version = 0;
    protected String versionString = "";
    protected VersionChannel channel = VersionChannel.UNKNOWN_CHANNEL;

    public GameVersion (final String cfgPath) {
        if (!(new File(cfgPath).exists())) {
            throw new IllegalArgumentException("version configuration file '" + cfgPath + "' doesnt exists!");
        }

        this.cfgFile = new File(cfgPath);

        if (!this.cfgFile.canRead()) {
            throw new IllegalStateException("version configuration file '" + cfgPath + "' isnt readable, file permissions are wrong.");
        }

        //load configuration file
        try {
            this.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not load version configuration file: " + cfgPath + ",\nexception: " + e.getLocalizedMessage());
        }
    }

    protected void load () throws IOException {
        Ini ini = new Ini(this.cfgFile);

        if (!ini.containsKey("Version")) {
            throw new IllegalStateException("version configuration file '" + this.cfgFile.getAbsolutePath() + "' is invalide, no section 'Version' found.");
        }

        Profile.Section section = ini.get("Version");
        this.buildNumber = Integer.parseInt(section.get("build"));
        this.version = Integer.parseInt(section.getOrDefault("version", "0"));
        this.versionString = section.get("versionString");
        this.channel = VersionChannel.valueOf(section.get("channel").toUpperCase());
    }

    public int getBuild () {
        return this.buildNumber;
    }

    public int getVersion () {
        return this.version;
    }

    public String getVersionString () {
        return this.versionString;
    }

    public VersionChannel getChannel () {
        return this.channel;
    }

}
