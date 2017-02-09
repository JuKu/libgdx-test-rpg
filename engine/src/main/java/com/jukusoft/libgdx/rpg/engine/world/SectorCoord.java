package com.jukusoft.libgdx.rpg.engine.world;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Data Holder for sector coordinates
 *
 * Created by Justin on 09.02.2017.
 */
public class SectorCoord {

    protected int x = 0;
    protected int y = 0;
    protected int layer = 0;

    public SectorCoord () {
        //
    }

    public int getX () {
        return this.x;
    }

    public void setX (int x) {
        this.x = x;
    }

    public int getY () {
        return this.y;
    }

    public void setY (int y) {
        this.y = y;
    }

    public int getLayer () {
        return this.layer;
    }

    public void setLayer (int layer) {
        this.layer = layer;
    }

    @Override
    public boolean equals (Object obj) {
        if (!(obj instanceof SectorCoord)) {
            throw new IllegalArgumentException("object has to be an instance of SectorCoord to check, if equals.");
        }

        SectorCoord coord = (SectorCoord) obj;

        return this.x == coord.x && this.y == coord.y && this.layer == coord.layer;
    }

    @Override
    public int hashCode () {
        return new HashCodeBuilder(17, 37).
            append(this.x).
            append(this.y).
            append(this.layer).
            toHashCode();
    }

}
