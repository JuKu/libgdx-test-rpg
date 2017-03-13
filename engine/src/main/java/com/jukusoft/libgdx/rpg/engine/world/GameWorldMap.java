package com.jukusoft.libgdx.rpg.engine.world;

import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.exception.MapNotFoundException;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.BaseAssetPathUtils;
import com.jukusoft.libgdx.rpg.engine.utils.SpriteBatcherUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    //map renderer
    OrthogonalTiledMapRenderer mapRenderer = null;

    //list with all collision layers
    protected List<MapLayer> collisionLayers = new ArrayList<>();

    //list with all collision objects
    protected List<Rectangle> collisionRectangles = new ArrayList<>();

    //list with no lighting rectangles
    protected List<MapLayer> noLightingLayers = new ArrayList<>();
    protected List<Rectangle> noLightingRectangles = new ArrayList<>();

    public GameWorldMap (BaseGame game, SectorCoord coord) {
        super(0, 0);

        this.game = game;
        this.coord = coord;

        String dirName = "map_" + coord.getX() + "_" + coord.getY() + "_" + coord.getLayer() + "";
        this.mapDirPath = BaseAssetPathUtils.getMapPath(dirName + "/");
        this.mapPath = BaseAssetPathUtils.getMapPath(dirName + "/" + dirName + ".tmx");

        if (mapLoader == null) {
            mapLoader = new TmxMapLoader();
        }

        //load map information (water, collision, NPCs and so on)
    }

    /**
    * load tmx map
    */
    public void load (String mapPath) throws MapNotFoundException {
        this.mapPath = mapPath;

        if (!(new File(this.mapPath).exists())) {
            throw new MapNotFoundException("Couldnt found tmx map for sector " + getSectorCoord() + ", map path: " + this.mapPath);
        }

        System.out.println("load tmx map for sector " + getSectorCoord() + ", tmx map path: " + this.mapPath);
        //game.getAssetManager().load(this.mapPath, TiledMap.class);

        //game.getAssetManager().finishLoadingAsset(this.mapPath);

        this.tiledMap = new TmxMapLoader(new AbsoluteFileHandleResolver()).load(this.mapPath);
        MapProperties prop = tiledMap.getProperties();

        //get properties
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        //set values
        this.cols = mapWidth;
        this.rows = mapHeight;
        this.tileWidth = tilePixelWidth;
        this.tileHeight = tilePixelHeight;

        System.out.println("tmx map loaded for " + getSectorCoord() + ", tmx map: " + this.mapPath);

        if (this.mapRenderer != null) {
            this.mapRenderer.dispose();
            this.mapRenderer = null;
        }

        //update bounding box
        this.boundingBox = new BoundingBox(new Vector3(x, y, 0), new Vector3(x + getWidthInPixels(), y + getHeightInPixels(), 0));

        //create map renderer (every map needs its own renderer)
        float unitScale = 1f;//1 / 32f;
        this.mapRenderer = new OrthogonalTiledMapRenderer(this.tiledMap, unitScale);

        //clear lists
        this.collisionLayers.clear();
        this.noLightingRectangles.clear();

        //find collision layers
        for (MapLayer collisionLayer : this.tiledMap.getLayers()) {
            if (collisionLayer.getName().contains("collision")) {
                //add layer to list
                this.collisionLayers.add(collisionLayer);
            }
        }

        //find collision rectangles
        for (MapLayer collisionLayer : this.collisionLayers) {
            System.out.println("collision layer.");

            //get all map objects on layer
            for (RectangleMapObject rectangleObject : collisionLayer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rectangle = rectangleObject.getRectangle();

                System.out.println("rectangle");

                //add rectangle to list
                this.collisionRectangles.add(rectangle);
            }
        }

        //find no lighting layers
        for (MapLayer noLightingLayer : this.tiledMap.getLayers()) {
            if (noLightingLayer.getName().contains("no_lighting")) {
                //add layer to list
                this.noLightingLayers.add(noLightingLayer);
            }
        }

        //find no lighting rectangles
        for (MapLayer noLightingLayer : this.noLightingLayers) {
            System.out.println("no_lighting layer.");

            //get all map objects on layer
            for (RectangleMapObject rectangleObject : noLightingLayer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rectangle = rectangleObject.getRectangle();

                System.out.println("rectangle");

                //add rectangle to list
                this.noLightingRectangles.add(rectangle);
            }
        }
    }

    public void load () throws MapNotFoundException {
        this.load(this.mapPath);
    }

    public void update (BaseGame game, CameraWrapper camera, GameTime time) {
        //update bounding box
    }

    public void draw (GameTime time, CameraWrapper camera, ShaderProgram shader, SpriteBatch batch) {
        float offsetX = getX() - game.getCamera().getX();
        float offsetY = getY() - game.getCamera().getY();
        float width = getWidthInPixels() - offsetX;
        float height = getHeightInPixels() - offsetY;

        //set projection matrix
        this.mapRenderer.setView(game.getCamera().getCombined(), getX(), getY(), width, height);

        this.mapRenderer.getBatch().setShader(shader);

        //TODO: render only specific layers which arent water
        this.mapRenderer.render();
    }

    public void drawWater (GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //TODO: render layers with water
    }

    public void drawNoLightingHitboxes (GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //draw hitboxes
        for (Rectangle rectangle : this.noLightingRectangles) {
            //draw hitbox
            SpriteBatcherUtils.drawRect(batch, rectangle, 1, Color.YELLOW);
        }
    }

    public void drawHitboxes (GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //draw hitboxes
        for (Rectangle rectangle : this.collisionRectangles) {
            //draw hitbox
            SpriteBatcherUtils.drawRect(batch, rectangle, 1, Color.RED);
        }
    }

    public List<Rectangle> listNoLightingRectangles () {
        return this.noLightingRectangles;
    }

    public boolean isColliding (Rectangle rectangle) {
        for (Rectangle collisionRectangle : this.collisionRectangles) {
            if (rectangle.overlaps(collisionRectangle)) {
                return true;
            }
        }

        return false;
    }

    public SectorCoord getSectorCoord () {
        return this.coord;
    }

    public void dispose () {
        this.mapRenderer.dispose();
        this.mapRenderer = null;

        this.tiledMap.dispose();
        this.tiledMap = null;
    }

}
