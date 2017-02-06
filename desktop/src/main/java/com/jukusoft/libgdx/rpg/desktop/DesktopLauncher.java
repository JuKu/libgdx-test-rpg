package com.jukusoft.libgdx.rpg.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jukusoft.libgdx.rpg.game.Game;

/**
 * Created by Justin on 05.02.2017.
 */
public class DesktopLauncher {

    public static void main (String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Test RPG Game";
        config.height = 720;
        config.width = 1280;
        config.addIcon("./data/images/general/icon.png", Files.FileType.Absolute);

        //start game
        new LwjglApplication(new Game(), config);
    }

}
