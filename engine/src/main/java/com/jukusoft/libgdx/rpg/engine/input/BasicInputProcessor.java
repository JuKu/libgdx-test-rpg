package com.jukusoft.libgdx.rpg.engine.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.jukusoft.libgdx.rpg.engine.input.listener.KeyListener;
import com.jukusoft.libgdx.rpg.engine.input.listener.MouseListener;
import com.jukusoft.libgdx.rpg.engine.input.listener.ScrollListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Justin on 09.02.2017.
 */
public class BasicInputProcessor implements InputProcessor {

    protected List<ScrollListener> scrollListenerList = new ArrayList<>();
    protected List<KeyListener> keyListenerList = new ArrayList<>();
    protected List<MouseListener> mouseListenerList = new ArrayList<>();

    /**
    * list with active game actions
    */
    protected List<Integer> activeGameActions = new ArrayList<>();

    @Override public boolean keyDown(int keycode) {
        for (KeyListener listener : this.keyListenerList) {
            boolean resolved = listener.keyDown(keycode);

            if (resolved) {
                //dont execute other listeners
                return true;
            }
        }

        return false;
    }

    @Override public boolean keyUp(int keycode) {
        for (KeyListener listener : this.keyListenerList) {
            boolean resolved = listener.keyUp(keycode);

            if (resolved) {
                //dont execute other listeners
                return true;
            }
        }

        return false;
    }

    @Override public boolean keyTyped(char character) {
        for (KeyListener listener : this.keyListenerList) {
            boolean resolved = listener.keyTyped(character);

            if (resolved) {
                //dont execute other listeners
                return true;
            }
        }

        return false;
    }

    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (MouseListener listener : this.mouseListenerList) {
            boolean resolved = listener.touchDown(screenX, screenY, pointer, button);

            if (resolved) {
                //dont execute other listeners
                return true;
            }
        }

        return false;
    }

    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (MouseListener listener : this.mouseListenerList) {
            boolean resolved = listener.touchUp(screenX, screenY, pointer, button);

            if (resolved) {
                //dont execute other listeners
                return true;
            }
        }

        return false;
    }

    @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (MouseListener listener : this.mouseListenerList) {
            boolean resolved = listener.touchDragged(screenX, screenY, pointer);

            if (resolved) {
                //dont execute other listeners
                return true;
            }
        }

        return false;
    }

    @Override public boolean mouseMoved(int screenX, int screenY) {
        for (MouseListener listener : this.mouseListenerList) {
            boolean resolved = listener.mouseMoved(screenX, screenY);

            if (resolved) {
                //dont execute other listeners
                return true;
            }
        }

        return false;
    }

    @Override public boolean scrolled(int amount) {
        for (ScrollListener listener : this.scrollListenerList) {
            boolean resolved = listener.scrolled(amount);

            if (resolved) {
                //dont execute other listeners
                return true;
            }
        }

        return false;
    }

    public void addScrollListener (ScrollListener listener) {
        this.scrollListenerList.add(listener);

        //sort list
        Collections.sort(this.scrollListenerList);
    }

    public void removeScrollListener (ScrollListener listener) {
        this.scrollListenerList.remove(listener);
    }

    public void addKeyListener (KeyListener listener) {
        this.keyListenerList.add(listener);

        //sort list
        Collections.sort(this.keyListenerList);
    }

    public void removeKeyListener (KeyListener listener) {
        this.keyListenerList.remove(listener);
    }

    public void addMouseListener (MouseListener listener) {
        this.mouseListenerList.add(listener);

        //sort list
        Collections.sort(this.mouseListenerList);
    }

    public void removeMouseListener (MouseListener listener) {
        this.mouseListenerList.remove(listener);
    }

}
