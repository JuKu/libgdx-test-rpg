package com.jukusoft.libgdx.rpg.engine.utils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Justin on 09.03.2017.
 */
public class DevMode {

    protected static AtomicBoolean isDevModeEnabled = new AtomicBoolean(false);
    protected static AtomicBoolean showHitboxes = new AtomicBoolean(false);

    public static boolean isEnabled () {
        return isDevModeEnabled.get();
    }

    public void setEnabled (boolean enabled) {
        isDevModeEnabled.set(enabled);
    }

    public static boolean isDrawHitboxEnabled () {
        return showHitboxes.get();
    }

    public static void setDrawHitboxEnabled (boolean drawHitboxEnabled) {
        showHitboxes.set(drawHitboxEnabled);
    }

}
