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

    public static String getFontPath (String fileName) {
        return "./data/font/" + fileName;
    }

    public static String getUISkinPath (String fileName) {
        return "./data/ui/skin/" + fileName;
    }

    public static String getUISkinPath (String skinName, String fileName) {
        return "./data/ui/skin/" + skinName + "/" + fileName;
    }

    public static String getSavePath(String charName, String fileName) {
        return "./data/save/" + charName + "/" + fileName;
    }

    public static String getSavePath(String fileName) {
        return "./data/save/" + fileName;
    }

    public static String getStoryPath(String lang, String fileName) {
        return "./data/story/" + lang + "/" + fileName;
    }

}
