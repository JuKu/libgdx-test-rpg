package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.libgdx.rpg.engine.projectile.ProjectileSpawner;
import com.jukusoft.libgdx.rpg.engine.camera.impl.Shake2CameraModification;
import com.jukusoft.libgdx.rpg.engine.camera.impl.Shake3CameraModification;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.EntityManager;
import com.jukusoft.libgdx.rpg.engine.entity.factory.AnimatedEnvObjectFactory;
import com.jukusoft.libgdx.rpg.engine.entity.factory.NPCFactory;
import com.jukusoft.libgdx.rpg.engine.entity.factory.PlayerFactory;
import com.jukusoft.libgdx.rpg.engine.entity.impl.ECS;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.draw.LightMapComponent;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.input.impl.CameraZoomListener;
import com.jukusoft.libgdx.rpg.engine.lighting.LightingSystem;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.skybox.SimpleSkyBox;
import com.jukusoft.libgdx.rpg.engine.skybox.SkyBox;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.DevMode;
import com.jukusoft.libgdx.rpg.engine.world.SectorCoord;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.GameWorldCollisionComponent;
import com.jukusoft.libgdx.rpg.game.data.CharacterData;
import com.jukusoft.libgdx.rpg.game.shared.SharedDataConst;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;
import com.jukusoft.libgdx.rpg.engine.world.GameWorld;

/**
 * Created by Justin on 07.02.2017.
 */
public class GameScreen extends BaseScreen {

    protected CharacterData characterData = null;

    protected GameWorld gameWorld = null;

    protected Texture testTexture = null;
    protected String testTexturePath = AssetPathUtils.getImagePath("test/water.png");
    protected String lightMapPath = AssetPathUtils.getLightMapPath("lightmap1/light.png");
    protected Texture lightMap = null;
    protected String skyBoxPath = AssetPathUtils.getWallpaperPath("ocean/Ocean_large.png");
    protected Texture skyBoxTexture = null;
    protected String characterTexturePath = AssetPathUtils.getImagePath("test/character.png");
    protected Texture characterTexture = null;
    protected String characterTexturePath2 = AssetPathUtils.getImagePath("test/character2.png");
    protected Texture characterTexture2 = null;
    protected String cursorPath = AssetPathUtils.getCursorPath("attack/attack.png");
    protected Pixmap cursorImage = null;
    protected String campfireTexturePath = AssetPathUtils.getSpritesheetPath("campfire/campfire1.png");
    protected Texture campfireTexture = null;
    protected String blackTexturePath = AssetPathUtils.getLightMapPath("blackmap/blackmap.png");
    protected Texture blackTexture = null;
    //protected String character2AtlasFile = AssetPathUtils.getSpritesheetPath("pentaquin/player_walk.atlas");
    protected String character2AtlasFile = AssetPathUtils.getSpritesheetPath("reinertilesets/T_grey_caveman/output/T_grey_caveman.atlas");

    protected String fireParticleEffectFile = AssetPathUtils.getParticleEffectPath("fire1.p");

    //lighting system
    LightingSystem lightingSystem = null;

    protected CameraZoomListener zoomListener = null;

    protected EntityManager ecs = null;
    protected Entity playerEntity = null;
    protected ProjectileSpawner projectileSpawner = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        game.getAssetManager().load(testTexturePath, Texture.class);
        game.getAssetManager().load(lightMapPath, Texture.class);
        game.getAssetManager().load(skyBoxPath, Texture.class);
        game.getAssetManager().load(blackTexturePath, Texture.class);
        game.getAssetManager().load(cursorPath, Pixmap.class);
        game.getAssetManager().load(campfireTexturePath, Texture.class);
        game.getAssetManager().load(characterTexturePath, Texture.class);
        game.getAssetManager().load(characterTexturePath2, Texture.class);
        game.getAssetManager().finishLoading();

        this.testTexture = game.getAssetManager().get(testTexturePath, Texture.class);
        this.lightMap = game.getAssetManager().get(lightMapPath, Texture.class);
        this.skyBoxTexture = game.getAssetManager().get(skyBoxPath, Texture.class);
        this.cursorImage = game.getAssetManager().get(cursorPath, Pixmap.class);
        this.campfireTexture = game.getAssetManager().get(campfireTexturePath, Texture.class);
        this.characterTexture = game.getAssetManager().get(characterTexturePath, Texture.class);
        this.characterTexture2 = game.getAssetManager().get(characterTexturePath2, Texture.class);
        this.blackTexture = game.getAssetManager().get(blackTexturePath, Texture.class);

        if (this.campfireTexture == null) {
            throw new NullPointerException("campfire texture is null, path: " + campfireTexturePath);
        }

        //create new lighting system
        this.lightingSystem = new LightingSystem(game, blackTexture, game.getViewportWidth(), game.getViewportHeight());

        //create new test lighting
        //this.testLighting = new TextureLighting(this.lightMap, 200, 200);
        //this.lightingSystem.addLighting(this.testLighting);

        //save lighting environment to shared data, so HUD can change ambient color & intensity
        game.getSharedData().put(SharedDataConst.LIGHTING_ENV, this.lightingSystem);

        //create zoom listener to support camera zoom
        this.zoomListener = new CameraZoomListener(game.getCamera2D());
        game.getInputManager().getGameInputProcessor().addScrollListener(this.zoomListener);

        //create new entity component system
        this.ecs = new ECS(game, this.lightingSystem);
    }

    @Override
    public void onResume () {
        //TODO: maybe load data
        this.characterData = new CharacterData();

        game.getSharedData().put("character_data", this.characterData);

        //get current sector
        SectorCoord coord = this.characterData.getCurrentSector();

        //create game world
        this.gameWorld = new GameWorld(game, coord, this.testTexture);

        game.getSharedData().put(SharedDataConst.GAME_WORLD, this.gameWorld);

        //set correct input processor
        game.getInputManager().setInputProcessor();

        //add hud screen overlay
        game.getScreenManager().push("hud");

        //create skybox
        SkyBox skyBox = new SimpleSkyBox(this.skyBoxTexture);
        this.gameWorld.setSkyBox(skyBox);

        //initialize entity component system

        //create an entity for player
        this.playerEntity = PlayerFactory.createPlayer(this.ecs, this.character2AtlasFile, "standDown", 200, 200);
        this.playerEntity.addComponent(new LightMapComponent(this.lightMap, 0, 0, false), LightMapComponent.class);
        this.playerEntity.addComponent(new GameWorldCollisionComponent(this.gameWorld), GameWorldCollisionComponent.class);
        this.ecs.addEntity(this.playerEntity);
        game.getSharedData().put(SharedDataConst.PLAYER_ENTITY, this.playerEntity);

        //save current entity component system to shared data
        game.getSharedData().put(SharedDataConst.ENTITY_COMPONENT_SYSTEM, this.ecs);

        //create an entity for dummy NPC
        Entity npcEntity = NPCFactory.createDummyNPC(this.ecs, this.characterTexture, this.cursorImage, 400, 400);
        this.ecs.addEntity(npcEntity);

        //create an entity for dummy NPC
        Entity npcEntity1 = NPCFactory.createDummyWithBlobShadowNPC(this.ecs, this.characterTexture2, this.cursorImage, 300, 600);
        this.ecs.addEntity(npcEntity1);

        //create campfire
        Entity campfireEntity = AnimatedEnvObjectFactory.createBasicAnimatedLightingEntity(this.ecs, this.campfireTexture, this.lightMap, 300, 300, 150, 1, 5);
        this.ecs.addEntity(campfireEntity);

        //create campfire with lighting
        Entity campfireEntity1 = AnimatedEnvObjectFactory.createBasicAnimatedLightingEntity(this.ecs, this.campfireTexture, this.lightMap, 200, 600, 150, 1, 5);
        this.ecs.addEntity(campfireEntity1);

        //create particle effect
        Entity fireParticleEffectEntity = AnimatedEnvObjectFactory.createParticlesEntity(this.ecs, this.fireParticleEffectFile, this.lightMap, 100, 100);
        this.ecs.addEntity(fireParticleEffectEntity);

        //enable hitbox drawing
        DevMode.setDrawHitboxEnabled(true);

        //create projectile spawner and add to shared data
        this.projectileSpawner = new ProjectileSpawner(this.ecs, this.gameWorld);
        game.getSharedData().put(SharedDataConst.PROJECTILE_SPAWNER, this.projectileSpawner);
    }

    @Override
    public void onPause () {
        //remove hud screen overlay
        game.getScreenManager().pop();

        //remove all entitites
        this.ecs.removeAllEntities();
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {
        /*if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            //move camera
            game.getCamera().translate(-5, 0, 0);

            //move skybox
            gameWorld.getSkyBox().translate(-5, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            //move camera
            game.getCamera().translate(5, 0, 0);

            //move skybox
            gameWorld.getSkyBox().translate(5, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            //move camera
            game.getCamera().translate(0, 5, 0);

            //move skybox
            gameWorld.getSkyBox().translate(0, 5);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            //move camera
            game.getCamera().translate(0, -5, 0);

            //move skybox
            gameWorld.getSkyBox().translate(0, -5);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            //disable lighting
            lightingSystem.setLightingEnabled(false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            //enable lighting
            lightingSystem.setLightingEnabled(true);
        }*/

        if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            Shake2CameraModification mod = game.getCamera().getMod(Shake2CameraModification.class);

            if (!mod.isShaking()) {
                System.out.println("shake camera.");

                //shake camera 50ms
                mod.shake(30, 500);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.V)) {
            Shake3CameraModification mod = game.getCamera().getMod(Shake3CameraModification.class);

            if (!mod.isShaking()) {
                System.out.println("shake camera.");

                //shake camera 500ms
                mod.shake(10, 500);
            }
        }

        //update game world
        this.gameWorld.update(game, game.getCamera(), time);

        //update entities
        this.ecs.update(game, time);

        //update lighting system
        this.lightingSystem.update(game, game.getCamera(), time);
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        //draw lighting framebuffer first
        this.lightingSystem.drawFBO(time, this.gameWorld, game.getCamera(), batch);

        batch.setProjectionMatrix(game.getCamera().getCombined());

        //check, if lighting is enabled
        if (this.lightingSystem.isLightingEnabled()) {
            //set lighting shader, so lighting shader will be used
            this.gameWorld.setCurrentShader(this.lightingSystem.getLightingShader());
        } else {
            //reset shader
            this.gameWorld.setCurrentShader(null);
        }

        lightingSystem.getFBO().getColorBufferTexture().bind(1); //this is important! bind the FBO to the 2nd texture unit
        this.lightMap.bind(0); //we force the binding of a texture on first texture unit to avoid artefacts
        //this is because our default and ambiant shader dont use multi texturing...
        //youc can basically bind anything, it doesnt matter

        //batch.draw(testTexture, 0, 0);

        //draw game world
        if (this.lightingSystem.isLightingEnabled()) {
            this.gameWorld.draw(time, game.getCamera(), this.lightingSystem.getLightingShader(), batch);
        } else {
            this.gameWorld.draw(time, game.getCamera(), null, batch);
        }

        batch.end();
        batch.begin();
        batch.setProjectionMatrix(game.getCamera().getCombined());

        if (DevMode.isDrawHitboxEnabled()) {
            //draw hitboxes
            this.gameWorld.drawHitboxes(time, game.getCamera(), batch);
        }

        if (DevMode.isNoLightingHitboxEnabled()) {
            //draw hitboxes
            this.gameWorld.drawNoLightingHitboxes(time, game.getCamera(), batch);
        }

        //draw entities
        this.ecs.draw(time, game.getCamera(), batch);

        //reset shader
        batch.setShader(null);
        batch.setProjectionMatrix(game.getCamera().getCombined());

        //draw abilities and son on
        this.ecs.drawUILayer(time, game.getCamera(), batch);

        //draw lightmap (only for testing purposes)
        //batch.draw(lightingSystem.getFBO().getColorBufferTexture(), 0, 0);

        batch.flush();

        //reset shader, so default shader is used
        batch.setShader(null);
    }

    @Override public void destroy() {
        this.gameWorld.dispose();
        this.gameWorld = null;

        this.lightingSystem.dispose();
        this.lightingSystem = null;

        this.skyBoxTexture.dispose();
        this.skyBoxTexture = null;
    }

}
