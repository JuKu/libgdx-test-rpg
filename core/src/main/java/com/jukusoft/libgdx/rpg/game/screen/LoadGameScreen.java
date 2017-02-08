package com.jukusoft.libgdx.rpg.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.jukusoft.libgdx.rpg.engine.font.BitmapFontFactory;
import com.jukusoft.libgdx.rpg.engine.game.ScreenBasedGame;
import com.jukusoft.libgdx.rpg.engine.save.BrokenSavedGameInfo;
import com.jukusoft.libgdx.rpg.engine.save.IBrokenSavedGameInfo;
import com.jukusoft.libgdx.rpg.engine.save.SavedGameInfo;
import com.jukusoft.libgdx.rpg.engine.save.SavedGameInstance;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.skin.SkinFactory;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.shared.SharedDataConst;
import com.jukusoft.libgdx.rpg.game.ui.ImageButton;
import com.jukusoft.libgdx.rpg.game.ui.LoadButton;
import com.jukusoft.libgdx.rpg.game.ui.LoadEntityButton;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Justin on 08.02.2017.
 */
public class LoadGameScreen extends BaseScreen {

    protected final String BG_IMAGE_PATH = AssetPathUtils.getWallpaperPath("ocean_sunset/ocean.png");
    protected final String MUSIC_PATH = AssetPathUtils.getMusicPath("EssentialGameAudiopack/Loops/Drum_Only_Loops/Ove_Melaa-DrumLoop_1_64BPM.mp3");
    protected final String BUTTON_BG_PATH = AssetPathUtils.getUIWidgetPath("loadbutton", "loadbutton.png");
    protected final String BUTTON_HOVER_PATH = AssetPathUtils.getUIWidgetPath("loadbutton", "loadbutton_clicked.png");
    protected final String BUTTON_BACK_BG_PATH = AssetPathUtils.getUIWidgetPath("backbutton", "backbutton.png");
    protected final String BUTTON_BACK_HOVER_PATH = AssetPathUtils.getUIWidgetPath("backbutton", "backbutton_hovered.png");
    protected final String ICON_BACK_PATH = AssetPathUtils.getImagePath("icons/back/back_64.png");

    //assets
    protected Texture backgroundTexture = null;
    protected Music backgroundMusic = null;
    protected Texture buttonTexture = null;
    protected Texture hoveredTexture = null;
    protected Texture backButtonTexture = null;
    protected Texture backButtonHoveredTexture = null;

    protected float mouseX = 0;
    protected float mouseY = 0;

    protected Stage stage = null;
    protected BitmapFont arialFont = null;

    protected Map<String,Texture> iconTextureMap = new HashMap<>();

    protected List<LoadButton> loadButtonList = new ArrayList<>();

    protected ImageButton backButton = null;
    protected Texture iconBackTexture = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //
    }

    @Override
    public void onResume () {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage);

        //load assets
        this.assetManager.load(BG_IMAGE_PATH, Texture.class);
        this.assetManager.load(MUSIC_PATH, Music.class);
        this.assetManager.load(BUTTON_BG_PATH, Texture.class);
        this.assetManager.load(BUTTON_HOVER_PATH, Texture.class);
        this.assetManager.load(BUTTON_BACK_BG_PATH, Texture.class);
        this.assetManager.load(BUTTON_BACK_HOVER_PATH, Texture.class);
        this.assetManager.load(ICON_BACK_PATH, Texture.class);

        this.arialFont = BitmapFontFactory
            .createFont(AssetPathUtils.getFontPath("arial/arial.ttf"), 26, Color.WHITE);

        //wait while all assets was loaded
        game.getAssetManager().finishLoading();
        this.assetManager.finishLoadingAsset(BG_IMAGE_PATH);

        //get assets
        this.backgroundTexture = this.assetManager.get(BG_IMAGE_PATH, Texture.class);
        this.backgroundMusic = this.assetManager.get(MUSIC_PATH, Music.class);
        this.buttonTexture = this.assetManager.get(BUTTON_BG_PATH, Texture.class);
        this.hoveredTexture = this.assetManager.get(BUTTON_HOVER_PATH, Texture.class);
        this.backButtonTexture = this.assetManager.get(BUTTON_BACK_BG_PATH, Texture.class);
        this.backButtonHoveredTexture = this.assetManager.get(BUTTON_BACK_HOVER_PATH, Texture.class);
        this.iconBackTexture = this.assetManager.get(ICON_BACK_PATH, Texture.class);

        this.assetManager.finishLoading();

        //play background music
        this.backgroundMusic.setVolume(game.getVolume());
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.play();

        List<SavedGameInfo> savedGameInfoList = game.getSavedGameManager().listSavedGames(SavedGameInfo.class,
            BrokenSavedGameInfo.class);

        int i = 0;

        float startX = (game.getViewportWidth() / 2) - 200;
        float startY = 500;

        for (SavedGameInfo gameInfo : savedGameInfoList) {
            //only 5 entries can be shown without scrollbar
            if (i >= 5) {
                break;
            }

            /*LoadEntityButton button = new LoadEntityButton(this.backgroundTexture, gameInfo, this.uiSkin);
            this.verticalGroup.addActor(button);*/

            //create new load button
            LoadButton<SavedGameInfo> button = new LoadButton<>(this.buttonTexture, this.hoveredTexture, this.arialFont, gameInfo);
            button.setPosition(startX, startY);

            if (!gameInfo.getGameIcon().isEmpty()) {
                String iconPath = AssetPathUtils.getImagePath(gameInfo.getGameIcon());

                Texture iconTexture = getIcon(iconPath);

                if (iconTexture != null) {
                    button.setIcon(iconTexture);
                }
            } else {
                if (gameInfo instanceof IBrokenSavedGameInfo) {
                    String iconPath = AssetPathUtils.getImagePath("icons/cancel/cancel_64.png");

                    Texture iconTexture = getIcon(iconPath);

                    if (iconTexture != null) {
                        button.setIcon(iconTexture);
                    }

                    button.setIcon(iconTexture);
                }
            }

            if (!(gameInfo instanceof IBrokenSavedGameInfo)) {
                button.setClickListener(() -> {
                    //save character name
                    game.getSharedData().put(SharedDataConst.CHARACTER_NAME, gameInfo.getCharacterName());
                    game.getSharedData().put(SharedDataConst.SAVE_PATH, gameInfo.getSaveDir());

                    //next screen
                    game.getScreenManager().leaveAllAndEnter("game");
                });
            }

            this.loadButtonList.add(button);

            startY = startY - 120;

            //this.savedGameInfos[i] = gameInfo;

            i++;
        }

        this.backButton = new ImageButton(this.backButtonTexture, this.backButtonHoveredTexture, this.arialFont, "BACK");
        this.backButton.setPosition(50, 50);
        this.backButton.setIcon(this.iconBackTexture);
        this.backButton.setClickListener(() -> {
            //leave and enter new game state
            game.getScreenManager().leaveAllAndEnter("menu");
        });
    }

    @Override
    public void onPause () {
        //stop background music
        this.backgroundMusic.stop();

        //free memory for assets
        //this.backgroundTexture.dispose();
        //this.backgroundTexture = null;

        this.backgroundMusic.dispose();
        this.backgroundMusic = null;

        /*this.backButtonTexture.dispose();
        this.backButtonTexture = null;

        this.backButtonHoveredTexture.dispose();
        this.backButtonHoveredTexture = null;

        this.iconBackTexture.dispose();
        this.iconBackTexture = null;*/

        //dispose all icon textures
        for (Map.Entry<String,Texture> entry : this.iconTextureMap.entrySet()) {
            Texture texture = entry.getValue();

            this.iconTextureMap.remove(entry.getKey());

            texture.dispose();
            texture = null;
        }
    }

    protected Texture getIcon (String iconPath) {
        if (!this.iconTextureMap.containsKey(iconPath)) {
            //check, if icon exists
            if (new File(iconPath).exists()) {
                assetManager.load(iconPath, Texture.class);
                assetManager.finishLoading();

                Texture iconTexture = assetManager.get(iconPath, Texture.class);
                this.iconTextureMap.put(iconPath, iconTexture);
            } else {
                System.out.println("icon doesnt exists: " + iconPath);
                return null;
            }
        }

        Texture iconTexture = this.iconTextureMap.get(iconPath);

        return iconTexture;
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {
        //get mouse coordinates
        this.mouseX = Gdx.input.getX();
        this.mouseY = game.getViewportHeight() - Gdx.input.getY();

        //update buttons
        for (LoadButton<SavedGameInfo> button : this.loadButtonList) {
            button.update(game, time);
        }

        this.backButton.update(game, time);
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        if (backgroundTexture == null) {
            throw new NullPointerException("background texture is null.");
        }

        if (!backgroundTexture.isManaged()) {
            throw new IllegalStateException("background texture isnt managed.");
        }

        batch.setProjectionMatrix(game.getUICamera().combined);

        //draw background image
        batch.draw(this.backgroundTexture, 0, 0);

        //draw buttons
        for (LoadButton<SavedGameInfo> button : this.loadButtonList) {
            button.draw(time, batch);
        }

        //draw back button
        this.backButton.draw(time, batch);

        //draw user interface
        //this.stage.draw();
    }

    @Override public void destroy() {

    }

}
