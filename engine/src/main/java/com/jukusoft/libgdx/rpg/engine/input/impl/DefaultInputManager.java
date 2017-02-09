package com.jukusoft.libgdx.rpg.engine.input.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.jukusoft.libgdx.rpg.engine.input.BasicInputProcessor;
import com.jukusoft.libgdx.rpg.engine.input.InputManager;

/**
 * Created by Justin on 09.02.2017.
 */
public class DefaultInputManager implements InputManager {

    protected BasicInputProcessor inputProcessor = null;
    protected InputMultiplexer inputMultiplexer = null;

    public DefaultInputManager () {
        //create new input processor
        this.inputProcessor = new BasicInputProcessor();

        //create input multiplizer to allow more than one input processor
        this.inputMultiplexer = new InputMultiplexer();
        this.inputMultiplexer.addProcessor(this.inputProcessor);

        //set input processor
        this.setInputProcessor();
    }

    @Override public void setInputProcessor() {
        //set input processor
        Gdx.input.setInputProcessor(this.inputMultiplexer);
    }

    @Override public void addCustomInputProcessor(InputProcessor inputProcessor) {
        this.inputMultiplexer.addProcessor(0, inputProcessor);
    }

    @Override public void removeCustomInputProcessor(InputProcessor inputProcessor) {
        this.inputMultiplexer.removeProcessor(inputProcessor);
    }
}
