package com.jukusoft.libgdx.rpg.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jukusoft.libgdx.rpg.engine.utils.FileUtils;
import com.jukusoft.libgdx.rpg.game.Game;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

        for (String param : args) {
            if (param.contains("--fullscreen")) {
                config.fullscreen = true;
            }
        }

        try {
            //start game
            new LwjglApplication(new Game(), config);
        } catch (Exception e) {
            e.printStackTrace();

            try {
                //write crash dump
                FileUtils.writeFile("./crash.log", e.getLocalizedMessage(), StandardCharsets.UTF_8);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            System.exit(0);
        }
    }

}
