package com.jukusoft.libgdx.rpg.engine.save;

import com.jukusoft.libgdx.rpg.engine.exception.GameSaverException;
import com.jukusoft.libgdx.rpg.engine.version.GameVersion;

/**
 * Created by Justin on 08.02.2017.
 */
@FunctionalInterface
public interface GameInfoSaver<T extends SavedGameInfo> {

    public void saveGameInfo (final String saveDir, GameVersion version, T gameInfo) throws GameSaverException;

}
