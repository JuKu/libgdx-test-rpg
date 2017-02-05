package com.jukusoft.libgdx.rpg.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by Justin on 05.02.2017.
 */
public class DesktopLauncher {

    public static void main (String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Test RPG Game";
        config.height = 640;
        config.width = 960;
        config.addIcon("./data/images/icon.png", Files.FileType.Absolute);
        //new LwjglApplication(new Game(), config);
    }

}
