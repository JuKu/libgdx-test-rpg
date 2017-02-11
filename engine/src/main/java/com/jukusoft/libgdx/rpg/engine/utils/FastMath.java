package com.jukusoft.libgdx.rpg.engine.utils;

/**
 * Created by Justin on 11.02.2017.
 */
public class FastMath {

    public static final float PI = 3.1415926535897932384626433832795f;
    public static final float PI2 = 3.1415926535897932384626433832795f * 2.0f;

    /**
    * convert degree to "bogenmaß" radians
    */
    public static final float toRadians (float angleGrad) {
        angleGrad = angleGrad % 360;
        return FastMath.PI2 / 360 * angleGrad;
    }

}
