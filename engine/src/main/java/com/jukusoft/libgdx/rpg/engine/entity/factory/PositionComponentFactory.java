package com.jukusoft.libgdx.rpg.engine.entity.factory;

import com.jukusoft.libgdx.rpg.engine.entity.impl.PositionComponent;
import org.json.JSONObject;

/**
 * Created by Justin on 10.02.2017.
 */
public class PositionComponentFactory {

    public static PositionComponent createFromJSON (JSONObject json) {
        PositionComponent component = new PositionComponent();
        component.loadFromJSON(json);

        return component;
    }

}
