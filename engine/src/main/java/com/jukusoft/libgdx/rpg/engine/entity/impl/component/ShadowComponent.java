package com.jukusoft.libgdx.rpg.engine.entity.impl.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import com.jukusoft.libgdx.rpg.engine.camera.CameraWrapper;
import com.jukusoft.libgdx.rpg.engine.entity.BaseComponent;
import com.jukusoft.libgdx.rpg.engine.entity.Entity;
import com.jukusoft.libgdx.rpg.engine.entity.IDrawComponent;
import com.jukusoft.libgdx.rpg.engine.entity.listener.TextureChangedListener;
import com.jukusoft.libgdx.rpg.engine.entity.priority.ECSPriority;
import com.jukusoft.libgdx.rpg.engine.game.BaseGame;
import com.jukusoft.libgdx.rpg.engine.time.GameTime;
import com.jukusoft.libgdx.rpg.engine.utils.ByteUtils;
import com.jukusoft.libgdx.rpg.engine.utils.FastMath;
import com.jukusoft.libgdx.rpg.engine.world.ShadowEnv;

/**
 * Created by Justin on 11.02.2017.
 */
public class ShadowComponent extends BaseComponent implements IDrawComponent {

    //components
    protected PositionComponent positionComponent = null;
    protected DrawTextureComponent textureComponent = null;
    protected DrawTextureRegionComponent textureRegionComponent = null;

    protected Texture shadowTexture = null;
    protected TextureRegion shadowTextureRegion = null;
    protected Color shadowColor = Color.GRAY;
    protected float shadowAngleDegree = 90;

    //shadow width & height in pixels
    protected int shadowWidth = 100;
    protected int shadowHeight = 100;

    protected Affine2 transform = new Affine2();

    //http://stackoverflow.com/questions/32146442/libgdx-overlapping-2d-shadows

    //https://gamedevelopment.tutsplus.com/tutorials/how-to-generate-shockingly-good-2d-lightning-effects--gamedev-2681

    //https://github.com/libgdx/libgdx/wiki/Spritebatch%2C-Textureregions%2C-and-Sprites

    //https://github.com/libgdx/libgdx/wiki/2D-Animation

    public ShadowComponent () {
        //
    }

    @Override
    public void init (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);
        this.textureComponent = entity.getComponent(DrawTextureComponent.class);
        this.textureRegionComponent = entity.getComponent(DrawTextureRegionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }

        if (textureComponent == null && textureRegionComponent == null) {
            throw new IllegalStateException("You have to set an TextureComponent or an TextureRegionComponent to entity to use ShadowComponent.");
        }

        if (this.textureComponent != null) {
            this.textureComponent.addTextureChangedListener((Texture oldTexture, Texture newTexture) -> {
                //delete old shadow texture and generate an new one
                generateShadowTexture();
            });
        }

        if (this.textureRegionComponent != null) {
            this.textureRegionComponent.addTextureRegionChangedListener((TextureRegion oldTextureRegion, TextureRegion textureRegion) -> {
                //delete old shadow texture and generate an new one
                generateShadowTexture();
            });
        }

        //generate shadow texture
        this.generateShadowTexture();
    }

    @Override public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        /*Color c = new Color(batch.getColor());
        batch.setColor(a,b,c,d);
        batch.draw(yourSprite, x, y);
        batch.setColor(c);*/

        Affine2 affine = new Affine2();

        if (shadowTexture != null) {
            //http://stackoverflow.com/questions/24034352/libgdx-change-color-of-texture-at-runtime
            //batch.draw(this.shadowTexture, positionComponent.getX(), positionComponent.getY(), shadowWidth, shadowHeight);

            float angle = FastMath.toRadians(this.shadowAngleDegree);
            float sin = (float) Math.sin(angle);
            float cos = (float) Math.cos(angle);

            transform.setToTranslation(positionComponent.getX(), positionComponent.getY());
            transform.shear(/*0.5f*//*-1f*/sin, 0);  // <- modify skew here

            //TextureRegion tex = new TextureRegion(this.shadowTexture, 0, 0, shadowWidth, shadowHeight);
            batch.draw(this.shadowTextureRegion, shadowWidth, shadowHeight, transform);

            //batch.draw(this.shadowTexture, positionComponent.getX(), positionComponent.getY(), shadowWidth, shadowHeight);
        } else {
            throw new IllegalStateException("no shadow texture is set.");
        }
    }

    @Override public ECSPriority getDrawOrder() {
        return ECSPriority.DRAW_SHADOW;
    }

    public void generateShadowTexture () {
        Pixmap halfPixmap = null;
        TextureData textureData = null;

        float lightIntensity = 1;

        Color shadowColor = this.shadowColor;
        float angle = FastMath.toRadians(shadowAngleDegree);
        Color tmpColor = new Color();

        if (this.textureRegionComponent != null) {
            //get texture region of entity
            TextureRegion texture = this.textureRegionComponent.getTextureRegion();

            //calculate shadow width and height
            this.shadowWidth = texture.getRegionWidth() * 2;
            this.shadowHeight = texture.getRegionHeight() / 2;

            int regionX = texture.getRegionX();
            int regionY = texture.getRegionY();
            int regionWidth = texture.getRegionWidth();
            int regionHeight = texture.getRegionHeight();

            //http://stackoverflow.com/questions/24034352/libgdx-change-color-of-texture-at-runtime

            //get and prepare texture data
            textureData = texture.getTexture().getTextureData();
            textureData.prepare();

            halfPixmap = new Pixmap(regionWidth, regionHeight / 2, Pixmap.Format.RGBA8888);

            //draw entity texture with half height into temporary pixmap, reduce height of texture by 50%
            halfPixmap.drawPixmap(textureData.consumePixmap(), regionX, regionY, regionWidth, regionHeight, 0, 0, halfPixmap.getWidth(), halfPixmap.getHeight());
        } else if (this.textureComponent != null) {
            //get texture of entity
            Texture texture = this.textureComponent.getTexture();

            //calculate shadow width and height
            this.shadowWidth = texture.getWidth() * 2;
            this.shadowHeight = texture.getHeight() / 2;

            //http://stackoverflow.com/questions/24034352/libgdx-change-color-of-texture-at-runtime

            //get and prepare texture data
            textureData = texture.getTextureData();
            textureData.prepare();

            halfPixmap = new Pixmap(texture.getWidth(), texture.getHeight() / 2, Pixmap.Format.RGBA8888);

            //draw entity texture with half height into temporary pixmap, reduce height of texture by 50%
            halfPixmap.drawPixmap(textureData.consumePixmap(), 0, 0, texture.getWidth(), texture.getHeight(), 0, 0, halfPixmap.getWidth(), halfPixmap.getHeight());
        } else {
            throw new IllegalStateException("No texture component or texture region component is set to entity.");
        }

        Vector2 vector = new Vector2(0, 0);
        Color color = new Color();

        //create new Pixmap and put all pixels from texture to pixmap
        Pixmap shadowPixmap = new Pixmap(shadowWidth, shadowHeight, Pixmap.Format.RGBA8888);

        //shadowPixmap.drawPixmap();

        //set shadow color
        shadowPixmap.setColor(shadowColor);

        for (int x = 0; x < halfPixmap.getWidth(); x++) {
            for (int y = 0; y < halfPixmap.getHeight(); y++) {
                int colorInt = halfPixmap.getPixel(x, y);
                color.set(colorInt);

                //get color alpha value
                float alpha = color.a;

                if (alpha == 1) {
                    shadowPixmap.setColor(this.shadowColor);

                    //draw pixel in shadow color
                    shadowPixmap.fillRectangle(x, y, 1, 1);
                } else if (alpha > 0f) {
                    //calculate alpha value
                    float newAlpha = alpha * lightIntensity * shadowColor.a;
                    System.out.println(newAlpha + "");

                    //set new shadow color for this pixel
                    tmpColor.set(shadowColor.r, shadowColor.g, shadowColor.b, newAlpha);
                    System.out.println(tmpColor.toString());

                    shadowPixmap.setColor(tmpColor.r, tmpColor.g, tmpColor.b, newAlpha);

                    //get current position with point (0, 0)
                    int x2 = x;
                    int y2 = y;

                    //draw pixel in shadow color
                    shadowPixmap.fillRectangle(x2, y2, 1, 1);
                } else {
                    shadowPixmap.setColor(new Color(0, 0, 0, 0));
                    shadowPixmap.fillRectangle(x, y, 1, 1);
                }
            }
        }

        //PixmapIO.writePNG(Gdx.files.absolute("./shadowMap.png"), shadowPixmap);

        //get pixmap from entity texture
        /*Pixmap textureMap = texture.getTextureData().consumePixmap();

        //use soft rendering to create map for shadow
        for (int x = 0; x < textureMap.getWidth(); x++) {
            for (int y = 0; y < textureMap.getHeight(); y++) {
                int colorInt = textureMap.getPixel(x, y);
                color.set(colorInt);

                //get alpha color value
                float alpha = color.a;

                if (alpha > 0f) {
                    int x2 = x * 2;
                    int y2 = y;

                    //shadow

                    //draw pixel in shadow color
                    shadowPixmap.fillRectangle(x2, y2, 1, 1);
                }

                System.out.println("alpha value of (" + x + ", " + y + "): " + color.a);
            }
        }

        //generate texture
        this.shadowTexture = new Texture(shadowPixmap);*/

        //generate texture
        this.shadowTexture = new Texture(shadowPixmap);
        this.shadowTextureRegion = new TextureRegion(this.shadowTexture, 0, 0, this.shadowTexture.getWidth(), this.shadowTexture.getHeight());

        //dispose texture data pixmap
        textureData.disposePixmap();

        //dispose pixmaps
        shadowPixmap.dispose();
        halfPixmap.dispose();
    }

    public float getShadowAngle () {
        return this.shadowAngleDegree;
    }

    public void setShadowAngle (float angle) {
        angle = angle % 360;
        this.shadowAngleDegree = angle;

        this.generateShadowTexture();
    }

}
