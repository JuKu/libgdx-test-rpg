package com.jukusoft.libgdx.rpg.engine.utils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Justin on 09.03.2017.
 */
public class DevMode {

    protected static AtomicBoolean isDevModeEnabled = new AtomicBoolean(false);

    public static boolean isEnabled () {
        return isDevModeEnabled.get();
    }

    public void setEnabled (boolean enabled) {
        isDevModeEnabled.set(enabled);
    }

}
