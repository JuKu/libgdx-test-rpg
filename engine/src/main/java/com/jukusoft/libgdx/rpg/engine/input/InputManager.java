package com.jukusoft.libgdx.rpg.engine.input;

import com.badlogic.gdx.InputProcessor;

/**
 * Created by Justin on 09.02.2017.
 */
public interface InputManager {

    public void setInputProcessor ();

    public void addCustomInputProcessor (InputProcessor inputProcessor);

    public void removeCustomInputProcessor (InputProcessor inputProcessor);

}
