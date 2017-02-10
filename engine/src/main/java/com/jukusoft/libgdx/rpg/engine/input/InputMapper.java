package com.jukusoft.libgdx.rpg.engine.input;

/**
 * Created by Justin on 10.02.2017.
 */
public interface InputMapper {

    /**
    * add input mapping
     *
     * @param keyCode keyCode of key on keyboard
     * @param actionCode actionCode of game action
    */
    public void addMapping (int keyCode, int actionCode);

    /**
     * remove input mapping
     *
     * @param keyCode keyCode of key on keyboard
     * @param actionCode actionCode of game action
     */
    public void removeMapping (int keyCode, int actionCode);

    /**
    * get action code by key code
     *
     * @param keyCode keyCode of key on keyboard
    */
    public int getActionCode (int keyCode);

}
