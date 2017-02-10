package com.jukusoft.libgdx.rpg.engine.input;

import com.badlogic.gdx.InputProcessor;

/**
 * Created by Justin on 09.02.2017.
 */
public interface InputManager {

    public void setInputProcessor ();

    public BasicInputProcessor getGameInputProcessor ();

    public boolean isActionKeyPressed (int actionCode);

    public void addCustomInputProcessor (int index, InputProcessor inputProcessor);

    public void addCustomInputProcessor (InputProcessor inputProcessor);

    public void removeCustomInputProcessor (InputProcessor inputProcessor);

    public InputMapper getInputMapper ();

}
