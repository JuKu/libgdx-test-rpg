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

    public static String getUIWidgetPath (String widgetName, String fileName) {
        return "./data/ui/widget/" + widgetName + "/" + fileName;
    }

    public static String getDataPath () {
        return "./data/";
    }

    public static String getSavePath() {
        return "./data/save/";
    }

    public static String getSavePath(String charName, String fileName) {
        return "./data/save/" + charName + "/" + fileName;
    }

    public static String getSavePath(String fileName) {
        return "./data/save/" + fileName;
    }

    public static String getShaderPath (String shaderFileName) {
        return "./data/shader/" + shaderFileName;
    }

    public static String getCursorPath (String cursorPath) {
        return "./data/cursor/" + cursorPath;
    }

    public static String getSpritesheetPath (String spritesheetPath) {
        return "./data/spritesheets/" + spritesheetPath;
    }

    public static String getParticleEffectPath (String particleEffectPath) {
        return "./data/particles/" + particleEffectPath;
    }

    public static String getLightMapPath (String lightMapPath) {
        return "./data/lightmap/" + lightMapPath;
    }

    public static String getMapPath (String lightMapPath) {
        return "./data/maps/" + lightMapPath;
    }

    public static String getStoryPath(String lang, String fileName) {
        return "./data/story/" + lang + "/" + fileName;
    }

}
