package com.jukusoft.libgdx.rpg.engine.input.impl;

import com.jukusoft.libgdx.rpg.engine.input.InputMapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 10.02.2017.
 */
public class DefaultInputMapper implements InputMapper {

    protected Map<Integer,Integer> map = new ConcurrentHashMap<>();

    @Override public void addMapping(int keyCode, int actionCode) {
        this.map.put(keyCode, actionCode);
    }

    @Override public void removeMapping(int keyCode, int actionCode) {
        this.map.remove(keyCode, actionCode);
    }

    @Override public int getActionCode(int keyCode) {
        Integer i = map.get(keyCode);

        if (i == null) {
            return -1;
        } else {
            return i;
        }
    }

}
