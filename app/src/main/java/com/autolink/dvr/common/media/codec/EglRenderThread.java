package com.autolink.dvr.common.media.codec;

import android.opengl.EGLContext;
import android.os.SystemClock;
import android.view.Surface;
import com.autolink.dvr.common.media.MediaCodecConstant;
import com.autolink.dvr.common.media.surface.EglManager;
import com.autolink.dvr.common.media.surface.EglSurfaceView;
import com.autolink.dvr.common.utils.LogUtils2;

/* loaded from: classes.dex */
public class EglRenderThread extends Thread {
    private final String TAG = "DVR_EglRenderThread";
    private EGLContext eglContext;
    private EglManager eglManager;
    private int height;
    private boolean isStart;
    private boolean isStop;
    private Object object;
    private int renderMode;
    private Surface surface;
    private EglSurfaceView.Render surfaceRender;
    private int width;

    public EglRenderThread(Surface surface, EglSurfaceView.Render render, int i, int i2, int i3) {
        LogUtils2.logI("DVR_EglRenderThread", "EglRenderThread , surface = " + surface + " , surfaceRender = " + render);
        this.surface = surface;
        this.surfaceRender = render;
        this.renderMode = i;
        this.width = i2;
        this.height = i3;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        super.run();
        LogUtils2.logI("DVR_EglRenderThread", "run() ");
        this.isStart = false;
        this.isStop = false;
        this.object = new Object();
        EglManager eglManager = new EglManager();
        this.eglManager = eglManager;
        if (eglManager.init(this.surface, this.eglContext)) {
            while (!this.isStop) {
                if (this.isStart) {
                    int i = this.renderMode;
                    if (i == 0) {
                        synchronized (this.object) {
                            try {
                                LogUtils2.logI("DVR_EglRenderThread", "wait ");
                                this.object.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (i == 1) {
                        try {
                            Thread.sleep(16L);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
                if (MediaCodecConstant.surfaceCreate && this.surfaceRender != null) {
                    LogUtils2.logI("DVR_EglRenderThread", "surfaceRender.onSurfaceCreated() ");
                    this.surfaceRender.onSurfaceCreated();
                    MediaCodecConstant.surfaceCreate = false;
                }
                if (MediaCodecConstant.surfaceChange && this.surfaceRender != null) {
                    LogUtils2.logI("DVR_EglRenderThread", "surfaceRender.surfaceChange() ");
                    this.surfaceRender.onSurfaceChanged(this.width, this.height);
                    MediaCodecConstant.surfaceChange = false;
                }
                EglSurfaceView.Render render = this.surfaceRender;
                if (render != null) {
                    render.onDrawFrame();
                    if (!this.isStart) {
                        this.surfaceRender.onDrawFrame();
                    }
                }
                this.eglManager.swapBuffer();
                this.isStart = true;
                SystemClock.sleep(45L);
            }
            release();
        }
    }

    private void release() {
        LogUtils2.logI("DVR_EglRenderThread", "release ");
        this.eglManager.release();
    }

    public void requestRender() {
        LogUtils2.logI("DVR_EglRenderThread", "requestRender ");
        Object obj = this.object;
        if (obj != null) {
            synchronized (obj) {
                this.object.notifyAll();
            }
        }
    }

    public void stopEglRender() {
        LogUtils2.logI("DVR_EglRenderThread", "stopEglRender ");
        this.isStop = true;
        requestRender();
    }

    public EGLContext getEglContext() {
        EglManager eglManager = this.eglManager;
        if (eglManager != null) {
            return eglManager.getEglContext();
        }
        return null;
    }
}
