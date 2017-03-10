package com.jukusoft.libgdx.rpg.engine.utils;

import com.badlogic.gdx.math.Rectangle;
import com.sun.xml.internal.ws.util.Pool;

/**
 * Created by Justin on 10.03.2017.
 */
public class RectanglePoolFactory {

    protected static Pool<Rectangle> rectPool = null;

    public static Pool<Rectangle> createRectanglePool () {
        if (rectPool == null) {
            rectPool = new Pool<Rectangle>() {

                @Override
                protected Rectangle create () {
                    return new Rectangle();
                }

            };
        }

        return rectPool;
    }

    public static Pool<Rectangle> createNewRectanglePool () {
        return new Pool<Rectangle>() {

            @Override
            protected Rectangle create () {
                return new Rectangle();
            }

        };
    }

}
