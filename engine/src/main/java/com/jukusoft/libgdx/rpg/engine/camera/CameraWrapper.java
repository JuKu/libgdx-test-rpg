package com.jukusoft.libgdx.rpg.engine.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Matrix4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 10.02.2017.
 */
public class CameraWrapper implements ModificationFinishedListener {

    protected float x = 0;
    protected float y = 0;
    protected float zoom = 1;

    protected float cameraOffsetX = 0;
    protected float cameraOffsetY = 0;

    protected OrthographicCamera camera = null;

    protected Map<Class,CameraModification> cameraModificationMap = new ConcurrentHashMap<>();
    protected List<CameraModification> activeModifications = new ArrayList<>();

    protected TempCameraPosition tempCameraPosition = null;

    public CameraWrapper (OrthographicCamera camera) {
        this.camera = camera;

        this.cameraOffsetX = this.camera.position.x;
        this.cameraOffsetY = this.camera.position.y;

        this.tempCameraPosition = new TempCameraPosition(0, 0, 1);

        //this.sync();
    }

    public void translate (float x, float y, float zoom) {
        this.x += x;
        this.y += y;
        this.zoom += zoom;

        camera.translate(x, y, zoom);
    }

    public float getX () {
        return this.x;
    }

    public void setX (float x) {
        this.x = x;

        this.syncPosToCamera();
    }

    public float getY () {
        return this.y;
    }

    public void setY (float y) {
        this.y = y;

        this.syncPosToCamera();
    }

    public float getZoom () {
        return this.zoom;
    }

    public void setZoom (float zoom) {
        this.zoom = zoom;

        this.syncPosToCamera();
    }

    public void setPosition (float x, float y, float zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;

        this.syncPosToCamera();
    }

    public void setPosition (float x, float y) {
        this.x = x;
        this.y = y;

        this.syncPosToCamera();
    }

    protected void sync () {
        this.x = camera.position.x;
        this.y = camera.position.y;
        this.zoom = camera.zoom;

        this.cameraOffsetX = 0;
        this.cameraOffsetY = 0;
    }

    protected void syncPosToCamera () {
        this.camera.position.x = x + cameraOffsetX;
        this.camera.position.y = y + cameraOffsetY;
        this.camera.zoom = zoom;

        //System.out.println("offsetX: " + this.cameraOffsetX + ", offsetY: " + this.cameraOffsetY);
    }

    public Matrix4 getCombined () {
        return this.camera.combined;
    }

    public Frustum getFrustum () {
        return this.camera.frustum;
    }

    public void update () {
        //reset temporary camera position
        this.tempCameraPosition.reset(getX(), getY(), getZoom());

        //update modifications first
        for (CameraModification mod : this.activeModifications) {
            mod.onUpdate(this.tempCameraPosition, this);
        }

        this.camera.position.x = this.tempCameraPosition.getX() + cameraOffsetX;
        this.camera.position.y = this.tempCameraPosition.getY() + cameraOffsetY;
        this.camera.zoom = this.tempCameraPosition.getZoom();

        this.camera.update();
    }

    @Deprecated
    public OrthographicCamera getOriginalCamera () {
        return this.camera;
    }

    @Override public <T extends CameraModification> void onModificationFinished(T mod, Class<T> cls) {
        if (mod == null) {
            throw new NullPointerException("mod cannot be null.");
        }

        this.deactivateMod(cls);
        //this.activeModifications.remove(mod);
    }

    public <T extends CameraModification> void registerMod (T mod, Class<T> cls) {
        if (mod == null) {
            throw new NullPointerException("mod cannot be null.");
        }

        if (cls == null) {
            throw new NullPointerException("class cannot be null.");
        }

        this.cameraModificationMap.put(cls, mod);
    }

    public <T extends CameraModification> void removeMod (Class<T> cls) {
        if (cls == null) {
            throw new NullPointerException("class cannot be null.");
        }

        CameraModification mod = this.cameraModificationMap.get(cls);

        if (mod != null) {
            this.activeModifications.remove(mod);
        }

        this.cameraModificationMap.remove(cls);
    }

    public <T extends CameraModification> T getMod (Class<T> cls) {
        if (cls == null) {
            throw new NullPointerException("class cannot be null.");
        }

        CameraModification mod = this.cameraModificationMap.get(cls);

        if (mod == null) {
            return null;
        }

        return cls.cast(mod);
    }

    public <T extends CameraModification> void activateMod (Class<T> cls) {
        if (cls == null) {
            throw new NullPointerException("class cannot be null.");
        }

        CameraModification mod = this.cameraModificationMap.get(cls);

        if (mod == null) {
            throw new IllegalStateException("camera modification is not registered: " + cls.getName());
        }

        if (!this.activeModifications.contains(mod)) {
            this.activeModifications.add(mod);
        }
    }

    public <T extends CameraModification> void deactivateMod (Class<T> cls) {
        if (cls == null) {
            throw new NullPointerException("class cannot be null.");
        }

        CameraModification mod = this.cameraModificationMap.get(cls);

        if (mod == null) {
            throw new IllegalStateException("camera modification is not registered: " + cls.getName());
        }

        if (this.activeModifications.contains(mod)) {
            this.activeModifications.remove(mod);
        }
    }

    public int countActiveMods () {
        return this.activeModifications.size();
    }

}
