package com.jukusoft.libgdx.rpg.engine.input.impl;

import com.jukusoft.libgdx.rpg.engine.input.InputMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 10.02.2017.
 */
public class DefaultInputMapper implements InputMapper {

    protected Map<Integer,Integer> map = new ConcurrentHashMap<>();
    protected Map<Integer,List<Integer>> actionCodeToKeyCodeMap = new ConcurrentHashMap<>();

    @Override public void addMapping(int keyCode, int actionCode) {
        this.map.put(keyCode, actionCode);

        List<Integer> list = actionCodeToKeyCodeMap.get(actionCode);

        if (list == null) {
            list = new ArrayList<>();
        }

        list.add(keyCode);

        this.actionCodeToKeyCodeMap.put(actionCode, list);
    }

    @Override public void removeMapping(int keyCode, int actionCode) {
        this.map.remove(keyCode, actionCode);

        List<Integer> list = actionCodeToKeyCodeMap.get(actionCode);

        if (list != null) {
            list.remove(keyCode);
        }
    }

    @Override public long getActionCode(int keyCode) {
        Integer i = map.get(keyCode);

        if (i == null) {
            return -1;
        } else {
            return i;
        }
    }

}
