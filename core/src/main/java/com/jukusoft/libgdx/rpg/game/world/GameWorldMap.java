package com.jukusoft.libgdx.rpg.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.jukusoft.libgdx.rpg.engine.exception.MapNotFoundException;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.world.SectorCoord;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

import java.io.File;

/**
 * Created by Justin on 09.02.2017.
 */
public class GameWorldMap extends BaseMap {

    //if map has an background tiledmap (for example water)
    protected String backgroundMapPath = "";

    protected SectorCoord coord = null;

    protected String mapDirPath = "";
    protected String mapPath = "";
    protected TiledMap tiledMap = null;

    protected static TmxMapLoader mapLoader = null;
    protected BaseGame game = null;

    public GameWorldMap (BaseGame game, SectorCoord coord) {
        this.game = game;
        this.coord = coord;

        String dirName = "map_" + coord.getX() + "_" + coord.getY() + "_" + coord.getLayer() + "";
        this.mapDirPath = AssetPathUtils.getMapPath(dirName + "/");
        this.mapPath = AssetPathUtils.getMapPath(dirName + "/" + dirName + ".tmx");

        if (mapLoader == null) {
            mapLoader = new TmxMapLoader();
        }

        //load map information (water, collision, NPCs and so on)
    }

    /**
    * load tmx map
    */
    public void load () throws MapNotFoundException {
        if (!(new File(this.mapPath).exists())) {
            throw new MapNotFoundException("Couldnt found tmx map for sector " + getSectorCoord() + ", map path: " + this.mapPath);
        }

        System.out.println("load tmx map for sector " + getSectorCoord() + ", tmx map path: " + this.mapPath);
        //game.getAssetManager().load(this.mapPath, TiledMap.class);

        //game.getAssetManager().finishLoadingAsset(this.mapPath);

        this.tiledMap = new TmxMapLoader(new AbsoluteFileHandleResolver()).load(this.mapPath);

        System.out.println("tmx map loaded for " + getSectorCoord() + ", tmx map: " + this.mapPath);
    }

    public void update (BaseGame game, Camera camera, GameTime time) {
        //
    }

    public void draw (GameTime time, Camera camera, SpriteBatch batch) {
        //
    }

    public void drawWater (GameTime time, Camera camera, SpriteBatch batch) {
        //
    }

    public SectorCoord getSectorCoord () {
        return this.coord;
    }

    public void dispose () {
        //
    }

}
