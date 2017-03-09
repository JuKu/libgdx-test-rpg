package com.jukusoft.libgdx.rpg.engine.utils;

/**
 * Created by Justin on 09.03.2017.
 */
public class SpeedUtils {

    public static float getSpeedX (Direction direction, float speed) {
        if (direction == Direction.UP_LEFT || direction == Direction.DOWN_LEFT || direction == Direction.LEFT) {
            return -speed;
        } else if (direction == Direction.UP_RIGHT || direction == Direction.DOWN_RIGHT || direction == Direction.RIGHT) {
            return speed;
        } else {
            return 0;
        }
    }

    public static float getSpeedY (Direction direction, float speed) {
        if (direction == Direction.UP_LEFT || direction == Direction.UP_RIGHT || direction == Direction.UP) {
            return speed;
        } else if (direction == Direction.DOWN_LEFT || direction == Direction.DOWN_RIGHT || direction == Direction.DOWN) {
            return -speed;
        } else {
            return 0;
        }
    }

}
