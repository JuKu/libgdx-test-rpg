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
import com.jukusoft.libgdx.rpg.engine.save.SavedGameInfo;
import com.jukusoft.libgdx.rpg.engine.save.SavedGameInstance;
import com.jukusoft.libgdx.rpg.engine.screen.impl.BaseScreen;
import com.jukusoft.libgdx.rpg.engine.skin.SkinFactory;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.game.ui.LoadEntityButton;
import com.jukusoft.libgdx.rpg.game.utils.AssetPathUtils;

import java.util.List;

/**
 * Created by Justin on 08.02.2017.
 */
public class LoadGameScreen extends BaseScreen {

    protected final String BG_IMAGE_PATH = AssetPathUtils.getWallpaperPath("ocean_sunset/ocean.png");
    protected final String MUSIC_PATH = AssetPathUtils.getMusicPath("EssentialGameAudiopack/Loops/Drum_Only_Loops/Ove_Melaa-DrumLoop_1_64BPM.mp3");
    protected final String BUTTON_BG_PATH = AssetPathUtils.getUIWidgetPath("loadbutton", "loadbutton.png");

    //assets
    protected Texture backgroundTexture = null;
    protected Music backgroundMusic = null;
    protected Texture buttonTexture = null;

    protected SavedGameInfo[] savedGameInfos = new SavedGameInfo[5];
    protected ShapeRenderer shapeRenderer = null;

    protected float mouseX = 0;
    protected float mouseY = 0;

    protected Stage stage = null;
    protected BitmapFont arialFont = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //
    }

    @Override
    public void onResume () {
        this.shapeRenderer = new ShapeRenderer();

        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage);

        //load assets
        this.assetManager.load(BG_IMAGE_PATH, Texture.class);
        this.assetManager.load(MUSIC_PATH, Music.class);
        this.assetManager.load(BUTTON_BG_PATH, Texture.class);

        this.arialFont = BitmapFontFactory
            .createFont(AssetPathUtils.getFontPath("arial/arial.ttf"), 26, Color.WHITE);

        //wait while all assets was loaded
        this.assetManager.finishLoading();

        //get assets
        this.backgroundTexture = this.assetManager.get(BG_IMAGE_PATH, Texture.class);
        this.backgroundMusic = this.assetManager.get(MUSIC_PATH, Music.class);
        this.buttonTexture = this.assetManager.get(BUTTON_BG_PATH, Texture.class);

        //play background music
        this.backgroundMusic.setVolume(game.getVolume());
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.play();

        List<SavedGameInfo> savedGameInfoList = game.getSavedGameManager().listSavedGames(SavedGameInfo.class,
            BrokenSavedGameInfo.class);

        int i = 0;

        for (SavedGameInfo gameInfo : savedGameInfoList) {
            //only 5 entries can be shown without scrollbar
            if (i >= 5) {
                break;
            }

            /*LoadEntityButton button = new LoadEntityButton(this.backgroundTexture, gameInfo, this.uiSkin);
            this.verticalGroup.addActor(button);*/

            this.savedGameInfos[i] = gameInfo;

            i++;
        }
    }

    @Override
    public void onPause () {
        //stop background music
        this.backgroundMusic.stop();

        //free memory for assets
        this.backgroundTexture.dispose();
        this.backgroundTexture = null;

        this.backgroundMusic.dispose();
        this.backgroundMusic = null;

        this.shapeRenderer.dispose();
        this.shapeRenderer = null;
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {
        //get mouse coordinates
        this.mouseX = Gdx.input.getX();
        this.mouseY = game.getViewportHeight() - Gdx.input.getY();
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        //draw background image
        batch.draw(this.backgroundTexture, 0, 0);

        //execute OpenGL render calls, because stage uses its own sprite batcher
        batch.flush();
        batch.end();

        float startX = (game.getViewportWidth() / 2) - 200;
        float startY = 500;
        float startYBackup = startY;

        //set camera matrix to shape renderer
        this.shapeRenderer.setProjectionMatrix(game.getUICamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < savedGameInfos.length; i++) {
            SavedGameInfo gameInfo = savedGameInfos[i];

            if (gameInfo != null) {
                float width = 400;
                float height = 100;

                boolean hovered = false;

                if (mouseX >= startX && mouseX <= (startX + width)) {
                    if (mouseY >= startY && mouseY <= (startY + height)) {
                        hovered = true;
                    }
                }

                if (hovered) {
                    shapeRenderer.setColor(Color.GREEN);
                } else {
                    shapeRenderer.setColor(new Color(0x0000ff88));
                }

                //draw rectangle
                shapeRenderer.rect(startX, startY, width, height);

                startY = startY - 120;
            }
        }

        shapeRenderer.end();

        batch.begin();

        startY = startYBackup;

        //render text
        for (int i = 0; i < savedGameInfos.length; i++) {
            SavedGameInfo gameInfo = savedGameInfos[i];

            if (gameInfo != null) {
                this.arialFont.draw(batch, "Name: " + gameInfo.getName(), startX + 20, startY + 80);

                startY = startY - 120;
            }
        }

        //draw user interface
        //this.stage.draw();
    }

    @Override public void destroy() {

    }

}
