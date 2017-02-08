package com.jukusoft.libgdx.rpg.engine.save;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 08.02.2017.
 */
public class GameInstance {

    protected Map<String,Object> attrMap = new ConcurrentHashMap<>();

    public void setAttr (final String name, Object obj) {
        this.attrMap.put(name, obj);
    }

    public <T> T getAttr (final String name, Class<T> cls) {
        Object obj = this.attrMap.get(cls);

        //check, if object is null, then we cannot cast object
        if (obj == null) {
            return null;
        }

        //check, if we can cast object
        if (!cls.isInstance(obj)) {
            throw new IllegalStateException("object isnt instance of class " + cls.getCanonicalName());
        }

        //cast and return object
        return cls.cast(obj);
    }

}
