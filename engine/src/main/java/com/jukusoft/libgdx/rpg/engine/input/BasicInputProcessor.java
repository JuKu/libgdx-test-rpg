package com.jukusoft.libgdx.rpg.engine.input;

import com.badlogic.gdx.InputProcessor;
import com.jukusoft.libgdx.rpg.engine.input.listener.ScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 09.02.2017.
 */
public class BasicInputProcessor implements InputProcessor {

    protected List<ScrollListener> scrollListenerList = new ArrayList<>();

    @Override public boolean keyDown(int keycode) {
        return false;
    }

    @Override public boolean keyUp(int keycode) {
        return false;
    }

    @Override public boolean keyTyped(char character) {
        return false;
    }

    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override public boolean scrolled(int amount) {
        for (ScrollListener listener : this.scrollListenerList) {
            boolean resolved = listener.scrolled(amount);

            if (resolved) {
                //dont execute other listeners
                break;
            }
        }

        return false;
    }

}
