package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jukusoft.libgdx.rpg.engine.camera.impl.Shake1CameraModification;
import com.jukusoft.libgdx.rpg.engine.camera.impl.Shake3CameraModification;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.impl.component.ShadowComponent;
import com.jukusoft.libgdx.rpg.engine.font.BitmapFontFactory;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.hud.ImageWidget;
import com.jukusoft.libgdx.rpg.engine.hud.WidgetGroup;
import com.jukusoft.libgdx.rpg.engine.hud.actionbar.ActionBar;
import com.jukusoft.libgdx.rpg.engine.hud.actionbar.ActionBarItem;
import com.jukusoft.libgdx.rpg.engine.hud.actionbar.CustomHoverAdapter;
import com.jukusoft.libgdx.rpg.engine.input.InputPriority;
import com.jukusoft.libgdx.rpg.engine.input.listener.KeyListener;
import com.jukusoft.libgdx.rpg.engine.lighting.LightingEnvironment;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.skin.SkinFactory;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.data.CharacterData;
import com.jukusoft.libgdx.rpg.engine.hud.HUD;
import com.jukusoft.libgdx.rpg.engine.hud.FilledIconBar;
import com.jukusoft.libgdx.rpg.game.input.GameActionsConst;
import com.jukusoft.libgdx.rpg.game.shared.SharedDataConst;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;
import com.jukusoft.libgdx.rpg.game.world.GameWorld;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by Justin on 08.02.2017.
 */
public class HUDOverlayScreen extends BaseScreen {

    protected static final String HEART_ICON_PATH = AssetPathUtils.getImagePath("icons/heart/heart_32.png");
    protected static final String DIAMOND_ICON_PATH = AssetPathUtils.getImagePath("icons/diamond/diamond_32.png");
    protected static final String LOGO_PATH = AssetPathUtils.getImagePath("general/icon_transparency_smallest.png");
    protected static final String ACTIONBAR_BG_PATH = AssetPathUtils.getImagePath("actionbar/actionbar_background.png");
    protected static final String ACTIONBAR_BLANK_ITEM_PATH = AssetPathUtils.getImagePath("actionbar/blank.png");
    protected static final String ACTIONBAR_PROJECTIL_PATH = AssetPathUtils.getImagePath("icons/spellset/fire_bolt/lvl1_fire_arrows_64.png");
    protected static final String ACTIONBAR_ICE_SHARDS_PATH = AssetPathUtils.getImagePath("icons/spellset/ice_shards/ice_shards_64.png");

    protected CharacterData characterData = null;

    protected ShapeRenderer shapeRenderer = null;

    //HUD
    protected HUD hud = null;
    protected static final int FONT_SIZE = 18;
    protected static final int FONT_SIZE_SMALL = 14;
    protected FilledIconBar healthBar = null;
    protected FilledIconBar manaBar = null;
    protected ImageWidget logoImageWidget = null;
    protected ActionBar actionBar = null;

    //assets
    protected BitmapFont font = null;
    protected BitmapFont smallFont = null;
    protected Texture heartTexture = null;
    protected Texture diamondTexture = null;
    protected Texture logoTexture = null;
    protected Texture actionBarBGTexture = null;
    protected Texture actionBarBlankTexture = null;
    protected Texture actionBarProjectilTexture = null;
    protected Texture actionBarIceShardsTexture = null;

    //lighting environment
    protected LightingEnvironment lightingEnvironment = null;

    protected Skin uiSkin = null;

    protected Stage stage = null;

    protected VerticalGroup verticalGroup = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        this.font = BitmapFontFactory
            .createFont(AssetPathUtils.getFontPath("arial/arial.ttf"), FONT_SIZE, Color.WHITE);

        this.smallFont = BitmapFontFactory
            .createFont(AssetPathUtils.getFontPath("arial/arial.ttf"), FONT_SIZE_SMALL, Color.WHITE);

        //load assets
        game.getAssetManager().load(HEART_ICON_PATH, Texture.class);
        game.getAssetManager().load(DIAMOND_ICON_PATH, Texture.class);
        game.getAssetManager().load(LOGO_PATH, Texture.class);
        game.getAssetManager().load(ACTIONBAR_BLANK_ITEM_PATH, Texture.class);
        game.getAssetManager().load(ACTIONBAR_BG_PATH, Texture.class);
        game.getAssetManager().load(ACTIONBAR_PROJECTIL_PATH, Texture.class);
        game.getAssetManager().load(ACTIONBAR_ICE_SHARDS_PATH, Texture.class);

        //wait while all assets was loaded
        game.getAssetManager().finishLoading();

        this.heartTexture = game.getAssetManager().get(HEART_ICON_PATH, Texture.class);
        this.diamondTexture = game.getAssetManager().get(DIAMOND_ICON_PATH, Texture.class);
        this.logoTexture = game.getAssetManager().get(LOGO_PATH, Texture.class);
        this.actionBarBlankTexture = game.getAssetManager().get(ACTIONBAR_BLANK_ITEM_PATH, Texture.class);
        this.actionBarBGTexture = game.getAssetManager().get(ACTIONBAR_BG_PATH, Texture.class);
        this.actionBarProjectilTexture = game.getAssetManager().get(ACTIONBAR_PROJECTIL_PATH, Texture.class);
        this.actionBarIceShardsTexture = game.getAssetManager().get(ACTIONBAR_ICE_SHARDS_PATH, Texture.class);

        //create and load ui skin from json file
        this.uiSkin = SkinFactory.createSkin(AssetPathUtils.getUISkinPath("create_character", "uiskin.json"));
    }

    @Override
    public void onResume () {
        this.characterData = game.getSharedData().get("character_data", CharacterData.class);

        if (this.characterData == null) {
            throw new IllegalStateException("character data wasnt initialized yet.");
        }

        this.shapeRenderer = new ShapeRenderer();

        //get lighting environment
        this.lightingEnvironment = game.getSharedData().get(SharedDataConst.LIGHTING_ENV, LightingEnvironment.class);

        //create new Head-up-Display (HUD)
        this.hud = new HUD();

        //create new widget group for health bar
        this.healthBar = new FilledIconBar(this.heartTexture, this.font);
        this.healthBar.setPosition(game.getViewportWidth() - 540, game.getViewportHeight() - /*106*/60);
        this.healthBar.setMaxValue(this.characterData.getMaxHealth());
        this.healthBar.setValue(this.characterData.getHealth());
        this.hud.addWidget(this.healthBar);

        //create new mana bar
        this.manaBar = new FilledIconBar(this.diamondTexture, this.font);
        this.manaBar.setPosition(game.getViewportWidth() - 680, game.getViewportHeight() - /*156*/60);
        this.manaBar.setMaxValue(this.characterData.getMaxMana());
        this.manaBar.setValue(this.characterData.getMana());
        this.hud.addWidget(this.manaBar);

        //add logo image widget
        this.logoImageWidget = new ImageWidget(this.logoTexture);
        this.logoImageWidget.setPosition(20, game.getViewportHeight() - 70);
        this.hud.addWidget(this.logoImageWidget);

        //add actionbar
        this.actionBar = new ActionBar(64, 64, 1, 10, this.actionBarBGTexture, actionBarBlankTexture, this.smallFont);
        this.actionBar.setPosition(40, 50);
        this.hud.addWidget(this.actionBar);

        //initialize actionbar
        this.initActionBar(actionBar);

        //create stage
        this.stage = new Stage();

        //set stage input processor
        game.getInputManager().addCustomInputProcessor(stage);

        game.getInputManager().getGameInputProcessor().addKeyListener(new KeyListener() {
            @Override public boolean keyDown(int keycode) {
                return false;
            }

            @Override public boolean keyUp(int keycode) {
                return false;
            }

            @Override public boolean keyTyped(char character) {
                if (character == 'o') {
                    toggleControlsVisible();
                }

                return false;
            }

            @Override public InputPriority getInputOrder() {
                return null;
            }

            @Override public int compareTo(KeyListener o) {
                return 0;
            }
        });

        //add lighting controls
        this.addLightingControls(this.hud);
    }

    @Override
    public void onPause () {
        if (this.shapeRenderer != null) {
            this.shapeRenderer.dispose();
            this.shapeRenderer = null;
        }

        if (this.stage != null) {
            this.stage.dispose();
            this.stage = null;
        }
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {
        //update health bar
        this.healthBar.setMaxValue(this.characterData.getMaxHealth());
        this.healthBar.setValue(this.characterData.getHealth());
        this.healthBar.setText(this.healthBar.getPercent() + "%");

        //update mana bar
        this.manaBar.setMaxValue(this.characterData.getMaxMana());
        this.manaBar.setValue(this.characterData.getMana());
        this.manaBar.setText(this.manaBar.getPercent() + "%");

        this.hud.update(game, time);
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        //set user interface camera
        batch.setProjectionMatrix(game.getUICamera().combined);

        //reset shader, so default shader will be used
        //batch.setShader(null);

        this.hud.drawLayer0(time, batch);

        batch.end();

        //draw stage
        this.stage.draw();

        //set camera matrix to shape renderer
        this.shapeRenderer.setProjectionMatrix(game.getUICamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        this.hud.drawLayer1(time, shapeRenderer);

        shapeRenderer.end();

        batch.begin();

        this.hud.drawLayer2(time, batch);
    }

    @Override public void destroy() {
        this.heartTexture.dispose();
        this.heartTexture = null;

        this.diamondTexture.dispose();
        this.diamondTexture = null;

        this.logoTexture.dispose();
        this.logoTexture = null;
    }

    protected void addLightingControls (HUD hud) {
        //create and add new widget group to HUD
        /*WidgetGroup controlGroup = new WidgetGroup();
        controlGroup.setPosition(400, 400);
        hud.addWidget(controlGroup);*/

        this.verticalGroup = new VerticalGroup();
        verticalGroup.setPosition(1100, 500);

        //add checkbox to enable lighting
        CheckBox checkBox = new CheckBox("Lighting enabled", this.uiSkin);
        //checkBox.setPosition(0, 0);
        checkBox.addCaptureListener(new EventListener() {
            @Override public boolean handle(Event event) {
                if (checkBox.isChecked()) {
                    lightingEnvironment.setLightingEnabled(true);
                } else {
                    lightingEnvironment.setLightingEnabled(false);
                }

                return false;
            }
        });
        verticalGroup.addActor(checkBox);

        if (lightingEnvironment.isLightingEnabled()) {
            checkBox.setChecked(true);
        }

        Label label = new Label("Light Intensity", this.uiSkin);
        verticalGroup.addActor(label);

        Slider slider = new Slider(0, 3, 0.01f, false, this.uiSkin);
        slider.setValue(lightingEnvironment.getAmbientIntensity());
        slider.addCaptureListener(event -> {
            lightingEnvironment.setAmbientIntensity(slider.getValue());

            return true;
        });
        verticalGroup.addActor(slider);

        Label redLabel = new Label("Red Color: " + lightingEnvironment.getAmbientColor().x, this.uiSkin);
        verticalGroup.addActor(redLabel);

        Slider redSlider = new Slider(0, 1, 0.01f, false, this.uiSkin);
        redSlider.setValue(lightingEnvironment.getAmbientColor().x);
        redSlider.addCaptureListener(event -> {
            Vector3 oldColor = lightingEnvironment.getAmbientColor();
            lightingEnvironment.setAmbientColor(redSlider.getValue(), oldColor.y, oldColor.z);
            redLabel.setText("Red Color: " + lightingEnvironment.getAmbientColor().x);

            return true;
        });
        verticalGroup.addActor(redSlider);

        Label greenLabel = new Label("Green Color: " + lightingEnvironment.getAmbientColor().y, this.uiSkin);
        verticalGroup.addActor(greenLabel);

        Slider greenSlider = new Slider(0, 1, 0.01f, false, this.uiSkin);
        greenSlider.setValue(lightingEnvironment.getAmbientColor().y);
        greenSlider.addCaptureListener(event -> {
            Vector3 oldColor = lightingEnvironment.getAmbientColor();
            lightingEnvironment.setAmbientColor(oldColor.x, greenSlider.getValue(), oldColor.z);
            greenLabel.setText("Green Color: " + lightingEnvironment.getAmbientColor().y);

            return true;
        });
        verticalGroup.addActor(greenSlider);

        Label blueLabel = new Label("Blue Color: " + lightingEnvironment.getAmbientColor().z, this.uiSkin);
        verticalGroup.addActor(blueLabel);

        Slider blueSlider = new Slider(0, 1, 0.01f, false, this.uiSkin);
        blueSlider.setValue(lightingEnvironment.getAmbientColor().z);
        blueSlider.addCaptureListener(event -> {
            Vector3 oldColor = lightingEnvironment.getAmbientColor();
            lightingEnvironment.setAmbientColor(oldColor.x, oldColor.y, blueSlider.getValue());
            blueLabel.setText("Blue Color: " + lightingEnvironment.getAmbientColor().z);

            return true;
        });
        verticalGroup.addActor(blueSlider);

        TextButton button = new TextButton("Reset", this.uiSkin);
        button.addCaptureListener(new ClickListener() {

            public void clicked (InputEvent event, float x, float y) {
                System.out.println("reset lighting control.");

                lightingEnvironment.setAmbientIntensity(0.7f);
                lightingEnvironment.setAmbientColor(0.3f, 0.3f, 0.7f);

                slider.setValue(0.7f);

                redSlider.setValue(0.3f);
                greenSlider.setValue(0.3f);
                blueSlider.setValue(0.7f);
            }

        });
        verticalGroup.addActor(button);

        //only developer mode
        TextButton loadMapButton = new TextButton("Load own map", this.uiSkin);
        loadMapButton.addCaptureListener(new ClickListener() {

            public void clicked (InputEvent event, float x, float y) {
                System.out.println("load own map");

                Thread thread = new Thread(new Runnable() {
                    @Override public void run() {
                        // JFileChooser-Objekt erstellen
                        JFileChooser chooser = new JFileChooser();

                        chooser.setFileFilter(new FileFilter() {
                            @Override public boolean accept(File f) {
                                return f.getName().endsWith(".tmx") || f.isDirectory();
                            }

                            @Override public String getDescription() {
                                return "TMX Map";
                            }
                        });

                        chooser.setDialogType(JFileChooser.OPEN_DIALOG);

                        // Dialog zum Speichern von Dateien anzeigen
                        int i = chooser.showDialog(null, "Choose tmx map");

                        //check, if OPEN was clicked
                        if (i == JFileChooser.APPROVE_OPTION) {
                            // Ausgabe der ausgewaehlten Datei
                            System.out.println("open tmx map: " + chooser.getSelectedFile().getAbsolutePath());

                            game.runOnUIThread(() -> {
                                GameWorld gameWorld = game.getSharedData().get(SharedDataConst.GAME_WORLD, GameWorld.class);
                                gameWorld.devOptionLoadMap(chooser.getSelectedFile().getAbsolutePath());
                            });
                        } else {
                            System.out.println("No open button clicked.");
                        }
                    }
                });
                thread.start();
            }

        });
        verticalGroup.addActor(loadMapButton);

        //only developer mode
        TextButton loadSkyBoxButton = new TextButton("Load own SkyBox", this.uiSkin);
        loadSkyBoxButton.addCaptureListener(new ClickListener() {

            public void clicked (InputEvent event, float x, float y) {
                System.out.println("load own map");

                Thread thread = new Thread(new Runnable() {
                    @Override public void run() {
                        // JFileChooser-Objekt erstellen
                        JFileChooser chooser = new JFileChooser();

                        chooser.setFileFilter(new FileFilter() {
                            @Override public boolean accept(File f) {
                                return f.getName().endsWith(".png") || f.isDirectory();
                            }

                            @Override public String getDescription() {
                                return "PNG Graphics File";
                            }
                        });

                        chooser.setDialogType(JFileChooser.OPEN_DIALOG);

                        // Dialog zum Speichern von Dateien anzeigen
                        int i = chooser.showDialog(null, "Choose PNG Graphic for SkyBox");

                        //check, if OPEN was clicked
                        if (i == JFileChooser.APPROVE_OPTION) {
                            // Ausgabe der ausgewaehlten Datei
                            System.out.println("open skybox: " + chooser.getSelectedFile().getAbsolutePath());

                            final String filePath = chooser.getSelectedFile().getAbsolutePath();

                            if (!(new File(filePath).exists())) {
                                System.err.println("PNG graphic doesnt exists.");
                                return;
                            }

                            final GameWorld gameWorld = game.getSharedData().get(SharedDataConst.GAME_WORLD, GameWorld.class);

                            game.runOnUIThread(() -> {
                                //load texture
                                FileHandle handle = Gdx.files.absolute(filePath);
                                Texture skyBoxTexture = new Texture(handle);

                                gameWorld.getSkyBox().setTexture(skyBoxTexture);
                            });
                        } else {
                            System.out.println("No skybox open button clicked.");
                        }
                    }
                });
                thread.start();
            }

        });
        verticalGroup.addActor(loadSkyBoxButton);

        Label shakeIntensityLabel = new Label("Shake Intensity: " + 10, this.uiSkin);
        verticalGroup.addActor(shakeIntensityLabel);

        Slider shakeIntensitySlider = new Slider(0, 50, 0.1f, false, this.uiSkin);
        shakeIntensitySlider.setValue(10);
        shakeIntensitySlider.addCaptureListener(event -> {
            shakeIntensityLabel.setText("Shake Intensity: " + shakeIntensitySlider.getValue());

            return true;
        });
        verticalGroup.addActor(shakeIntensitySlider);

        Label shakeDurationLabel = new Label("Shake Duration: " + 5000 + "ms", this.uiSkin);
        verticalGroup.addActor(shakeDurationLabel);

        Slider shakeDurationSlider = new Slider(0, 10000, 1f, false, this.uiSkin);
        shakeDurationSlider.setValue(500);
        shakeDurationSlider.addCaptureListener(event -> {
            shakeDurationLabel.setText("Shake Duration: " + shakeDurationSlider.getValue() + "ms");

            return true;
        });
        verticalGroup.addActor(shakeDurationSlider);

        TextButton shakeCameraButton = new TextButton("Shake Camera", this.uiSkin);
        shakeCameraButton.addCaptureListener(new ClickListener() {

            public void clicked (InputEvent event, float x, float y) {
                float intensity = shakeIntensitySlider.getValue();
                float duration = shakeDurationSlider.getValue();

                Shake1CameraModification mod = game.getCamera().getMod(Shake1CameraModification.class);

                if (!mod.isShaking()) {
                    System.out.println("shake camera, intensity: " + intensity + ", duration: " + duration + "ms");

                    //shake camera 3 seconds
                    mod.shake(intensity, duration);
                }
            }

        });
        verticalGroup.addActor(shakeCameraButton);

        TextButton shake2CameraButton = new TextButton("Shake Camera Variation 3", this.uiSkin);
        shake2CameraButton.addCaptureListener(new ClickListener() {

            public void clicked (InputEvent event, float x, float y) {
                float intensity = shakeIntensitySlider.getValue();
                float duration = shakeDurationSlider.getValue();

                Shake3CameraModification mod = game.getCamera().getMod(Shake3CameraModification.class);

                if (!mod.isShaking()) {
                    System.out.println("shake camera, intensity: " + intensity + ", duration: " + duration + "ms");

                    //shake camera 3 seconds
                    mod.shake(intensity, duration);
                }
            }

        });
        verticalGroup.addActor(shake2CameraButton);

        Label shadowLabel = new Label("Shadow Angle: " + 315 + " degree", this.uiSkin);
        verticalGroup.addActor(shadowLabel);

        Slider shadowAngleSlider = new Slider(0, 360, 1f, false, this.uiSkin);
        shadowAngleSlider.setValue(315);
        shadowAngleSlider.addCaptureListener(event -> {
            shadowLabel.setText("Shadow Angle: " + shadowAngleSlider.getValue() + " degree");

            Entity playerEntity = game.getSharedData().get(SharedDataConst.PLAYER_ENTITY, Entity.class);
            ShadowComponent shadowComponent = playerEntity.getComponent(ShadowComponent.class);
            shadowComponent.setShadowAngle(shadowAngleSlider.getValue());

            return true;
        });
        verticalGroup.addActor(shadowAngleSlider);

        stage.addActor(verticalGroup);
    }

    protected void setControlsVisible (boolean visible) {
        this.verticalGroup.setVisible(visible);
    }

    protected void toggleControlsVisible () {
        this.verticalGroup.setVisible(!this.verticalGroup.isVisible());
    }

    protected void initActionBar (ActionBar actionBar) {
        //set actionbar items

        //left mouse button
        this.actionBar.getItem(0, 0).setTexture(this.actionBarProjectilTexture);
        this.actionBar.getItem(0, 0).setKeyText("LMB");
        this.actionBar.getItem(0, 0).setCustomHoverAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isLeftMouseButtonPressed();
        });
        this.actionBar.getItem(0, 0).setCustomClickAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isLeftMouseButtonPressed();
        });

        //right mouse button
        this.actionBar.getItem(1, 0).setTexture(this.actionBarIceShardsTexture);
        this.actionBar.getItem(1, 0).setKeyText("RMB");
        this.actionBar.getItem(1, 0).setCustomHoverAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isRightMouseButtonPressed();
        });
        this.actionBar.getItem(1, 0).setCustomClickAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isRightMouseButtonPressed();
        });

        //actions
        this.actionBar.getItem(2, 0).setKeyText("1");
        this.actionBar.getItem(2, 0).setCustomHoverAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_3);
        });
        this.actionBar.getItem(2, 0).setCustomClickAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_3);
        });

        this.actionBar.getItem(3, 0).setKeyText("2");
        this.actionBar.getItem(3, 0).setCustomHoverAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_4);
        });
        this.actionBar.getItem(3, 0).setCustomClickAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_4);
        });

        this.actionBar.getItem(4, 0).setKeyText("3");
        this.actionBar.getItem(4, 0).setCustomHoverAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_5);
        });
        this.actionBar.getItem(4, 0).setCustomClickAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_5);
        });

        this.actionBar.getItem(5, 0).setKeyText("4");
        this.actionBar.getItem(5, 0).setCustomHoverAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_6);
        });
        this.actionBar.getItem(5, 0).setCustomClickAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_6);
        });

        this.actionBar.getItem(6, 0).setKeyText("5");
        this.actionBar.getItem(6, 0).setCustomHoverAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_7);
        });
        this.actionBar.getItem(6, 0).setCustomClickAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_7);
        });

        this.actionBar.getItem(7, 0).setKeyText("Q");
        this.actionBar.getItem(7, 0).setCustomHoverAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_8);
        });
        this.actionBar.getItem(7, 0).setCustomClickAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_8);
        });

        this.actionBar.getItem(8, 0).setKeyText("E");
        this.actionBar.getItem(8, 0).setCustomHoverAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_9);
        });
        this.actionBar.getItem(8, 0).setCustomClickAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_9);
        });

        this.actionBar.getItem(9, 0).setKeyText("R");
        this.actionBar.getItem(9, 0).setCustomHoverAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_10);
        });
        this.actionBar.getItem(9, 0).setCustomClickAdapter((BaseGame game, ActionBarItem item, GameTime time) -> {
            return game.getInputManager().isActionKeyPressed(GameActionsConst.ACTION_10);
        });
    }

}
