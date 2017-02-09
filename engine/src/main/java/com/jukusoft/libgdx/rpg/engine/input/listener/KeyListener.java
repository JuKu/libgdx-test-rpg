package com.jukusoft.libgdx.rpg.engine.input.listener;

import com.jukusoft.libgdx.rpg.engine.input.InputPriority;

/**
 * Created by Justin on 09.02.2017.
 */
public interface KeyListener extends Comparable<KeyListener> {

    public boolean keyDown (int keycode);

    public boolean keyUp (int keycode);

    public boolean keyTyped (char character);

    public InputPriority getInputOrder ();

}
