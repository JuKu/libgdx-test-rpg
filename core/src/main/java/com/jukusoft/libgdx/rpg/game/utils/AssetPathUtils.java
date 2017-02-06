package com.jukusoft.libgdx.rpg.game.utils;

/**
 * Created by Justin on 06.02.2017.
 */
public class AssetPathUtils {

    public static String getImagePath (String fileName) {
        return "./data/images/" + fileName;
    }

    public static String getMusicPath (String fileName) {
        return "./data/music/" + fileName;
    }

    public static String getSoundPath (String fileName) {
        return "./data/sound/" + fileName;
    }

    public static String getWallpaperPath (String fileName) {
        return "./data/wallpaper/" + fileName;
    }

}
