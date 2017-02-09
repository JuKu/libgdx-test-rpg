package com.jukusoft.libgdx.rpg.game.world;

import com.jukusoft.libgdx.rpg.engine.world.SectorCoord;

/**
 * Created by Justin on 09.02.2017.
 */
public interface SectorChangedListener {

    public void sectorChanged (SectorCoord newSector);

}
