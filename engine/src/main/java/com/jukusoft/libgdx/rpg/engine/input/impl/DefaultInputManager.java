package com.jukusoft.libgdx.rpg.engine.input.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.jukusoft.libgdx.rpg.engine.input.BasicInputProcessor;
import com.jukusoft.libgdx.rpg.engine.input.InputManager;
import com.jukusoft.libgdx.rpg.engine.input.InputMapper;
import com.jukusoft.libgdx.rpg.engine.input.InputPriority;
import com.jukusoft.libgdx.rpg.engine.input.listener.KeyListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 09.02.2017.
 */
public class DefaultInputManager implements InputManager {

    protected BasicInputProcessor inputProcessor = null;
    protected InputMultiplexer inputMultiplexer = null;

    protected InputMapper inputMapper = null;

    /**
    * list with all actions codes (mapped keys) which are pressed
    */
    protected List<Long> pressedActionKeys = new ArrayList<>();

    public DefaultInputManager () {
        //create new input processor
        this.inputProcessor = new BasicInputProcessor();

        //create input multiplizer to allow more than one input processor
        this.inputMultiplexer = new InputMultiplexer();
        this.inputMultiplexer.addProcessor(this.inputProcessor); //Gdx.input.isKeyPressed()

        //set input processor
        this.setInputProcessor();

        //create new input mapper
        this.inputMapper = new DefaultInputMapper();

        this.inputProcessor.addKeyListener(new KeyListener() {
            @Override public boolean keyDown(int keycode) {
                //get action code for key
                long actionCode = inputMapper.getActionCode(keycode);

                //add action code to pressed action keys list
                pressedActionKeys.add(actionCode);

                return false;
            }

            @Override public boolean keyUp(int keycode) {
                //get action code for key
                long actionCode = inputMapper.getActionCode(keycode);

                //remove action code from pressed action keys list
                pressedActionKeys.remove(actionCode);

                return false;
            }

            @Override public boolean keyTyped(char character) {
                return false;
            }

            @Override public InputPriority getInputOrder() {
                return null;
            }

            @Override public int compareTo(KeyListener o) {
                return 0;
            }
        });
    }

    @Override public void setInputProcessor() {
        //set input processor
        Gdx.input.setInputProcessor(this.inputMultiplexer);
    }

    @Override public BasicInputProcessor getGameInputProcessor() {
        return this.inputProcessor;
    }

    @Override public boolean isActionKeyPressed(int actionCode) {
        return this.pressedActionKeys.contains(actionCode);
    }

    @Override public void addCustomInputProcessor(int index, InputProcessor inputProcessor) {
        this.inputMultiplexer.addProcessor(index, inputProcessor);
    }

    @Override public void addCustomInputProcessor(InputProcessor inputProcessor) {
        this.inputMultiplexer.addProcessor(0, inputProcessor);
    }

    @Override public void removeCustomInputProcessor(InputProcessor inputProcessor) {
        this.inputMultiplexer.removeProcessor(inputProcessor);
    }

    @Override public InputMapper getInputMapper() {
        return this.inputMapper;
    }
}
