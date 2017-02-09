package com.jukusoft.libgdx.rpg.engine.input.listener;

import com.jukusoft.libgdx.rpg.engine.input.InputPriority;

/**
 * Created by Justin on 09.02.2017.
 */
public interface MouseListener extends Comparable<MouseListener> {

    public boolean touchDown(int screenX, int screenY, int pointer, int button);

    public boolean touchUp(int screenX, int screenY, int pointer, int button);

    public boolean touchDragged(int screenX, int screenY, int pointer);

    public boolean mouseMoved(int screenX, int screenY);

    public InputPriority getInputOrder ();

}
