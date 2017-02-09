package com.jukusoft.libgdx.rpg.engine.input.listener;

import com.jukusoft.libgdx.rpg.engine.input.InputPriority;

/**
 * Created by Justin on 09.02.2017.
 */
public interface ScrollListener extends Comparable<ScrollListener> {

    public boolean scrolled (float amount);

    public InputPriority getInputOrder ();

}
