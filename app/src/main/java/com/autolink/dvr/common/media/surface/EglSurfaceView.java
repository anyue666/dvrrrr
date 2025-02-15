package com.autolink.dvr.common.media.surface;

import android.content.Context;
import android.opengl.EGLContext;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.autolink.dvr.common.media.utils.DisplayUtils;
import com.autolink.dvr.common.utils.LogUtils2;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public class EglSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private final String TAG;
    private EGLContext eglContext;
    private EglManager eglManager;
    private Thread eglThread;
    private final ExecutorService mEglSurfaceViewExecutor;
    private int renderMode;
    private EglRunnable runnable;
    private Surface surface;
    private CameraRender surfaceRender;

    public interface Render {
        void onDrawFrame();

        void onSurfaceChanged(int i, int i2);

        void onSurfaceCreated();
    }

    public EglSurfaceView(Context context) {
        this(context, null);
    }

    public EglSurfaceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EglSurfaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "DVR_EglSurfaceView";
        LogUtils2.logI("DVR_EglSurfaceView", "EglSurfaceView ");
        getHolder().addCallback(this);
        this.mEglSurfaceViewExecutor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("EglSurfaceView").build());
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        LogUtils2.logI("DVR_EglSurfaceView", "surfaceCreated surface = " + this.surface);
        Surface surface = this.surface;
        if (surface == null || !surface.isValid()) {
            this.surface = surfaceHolder.getSurface();
            setZOrderMediaOverlay(true);
        }
        this.eglContext = DisplayUtils.eglContext;
        EglRunnable eglRunnable = this.runnable;
        if (eglRunnable != null) {
            eglRunnable.isStop = true;
        }
        this.runnable = new EglRunnable();
        this.eglThread = new Thread(this.runnable);
        this.runnable.isSurfaceCreate = true;
        this.eglThread.setName("EglSurfaceView");
        this.mEglSurfaceViewExecutor.execute(this.eglThread);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        LogUtils2.logI("DVR_EglSurfaceView", "surfaceChanged ");
        this.runnable.width = i2;
        this.runnable.height = i3;
        this.runnable.isSurfaceChange = true;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        LogUtils2.logI("DVR_EglSurfaceView", "surfaceDestroyed ");
        EglRunnable eglRunnable = this.runnable;
        if (eglRunnable != null) {
            eglRunnable.isStop = true;
            this.runnable.requestRender();
        }
        this.eglThread.isInterrupted();
        this.surface = null;
        this.eglContext = null;
    }

    private class EglRunnable implements Runnable {
        private int height;
        private boolean isStart;
        private boolean isStop;
        private boolean isSurfaceChange;
        private boolean isSurfaceCreate;
        private Object object;
        private int width;

        private EglRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            LogUtils2.logI("DVR_EglSurfaceView", "EglRunnable run() surface = " + EglSurfaceView.this.surface + " , eglContext=" + EglSurfaceView.this.eglContext);
            this.isStart = false;
            this.isStop = false;
            this.object = new Object();
            while (EglSurfaceView.this.eglContext == null) {
                LogUtils2.logD("DVR_EglSurfaceView", "EglRunnable wait eglContext");
                SystemClock.sleep(10L);
                EglSurfaceView.this.eglContext = DisplayUtils.eglContext;
            }
            if (this.isStop) {
                return;
            }
            EglSurfaceView.this.eglManager = new EglManager();
            if (!EglSurfaceView.this.eglManager.init(EglSurfaceView.this.surface, EglSurfaceView.this.eglContext)) {
                release();
                return;
            }
            while (!this.isStop) {
                if (this.isStart) {
                    if (EglSurfaceView.this.renderMode != 0) {
                        if (EglSurfaceView.this.renderMode == 1) {
                            try {
                                Thread.sleep(16L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        synchronized (this.object) {
                            try {
                                LogUtils2.logI("DVR_EglSurfaceView", "EglRunnable wait ");
                                this.object.wait();
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
                if (this.isSurfaceCreate && EglSurfaceView.this.surfaceRender != null) {
                    LogUtils2.logI("DVR_EglSurfaceView", "EglRunnable  surfaceRender.onSurfaceCreated() ");
                    EglSurfaceView.this.surfaceRender.onSurfaceCreated();
                    this.isSurfaceCreate = false;
                }
                if (this.isSurfaceChange && EglSurfaceView.this.surfaceRender != null) {
                    LogUtils2.logI("DVR_EglSurfaceView", "EglRunnable  surfaceRender.onSurfaceChanged() ");
                    EglSurfaceView.this.surfaceRender.onSurfaceChanged(this.width, this.height);
                    this.isSurfaceChange = false;
                }
                if (EglSurfaceView.this.surfaceRender != null) {
                    EglSurfaceView.this.surfaceRender.onDraw(DisplayUtils.fboTextureId);
                    if (!this.isStart) {
                        EglSurfaceView.this.surfaceRender.onDraw(DisplayUtils.fboTextureId);
                    }
                }
                EglSurfaceView.this.eglManager.swapBuffer();
                this.isStart = true;
                SystemClock.sleep(30L);
            }
            release();
        }

        private void release() {
            LogUtils2.logI("DVR_EglSurfaceView", "EglRunnable release ");
            EglSurfaceView.this.eglManager.release();
        }

        public void requestRender() {
            Object obj = this.object;
            if (obj != null) {
                synchronized (obj) {
                    LogUtils2.logI("DVR_EglSurfaceView", "EglRunnable requestRender notifyAll");
                    this.object.notifyAll();
                }
            }
        }
    }

    public void stopThread() {
        LogUtils2.logI("DVR_EglSurfaceView", "stopThread ");
        this.runnable.isStop = true;
        this.eglThread.isInterrupted();
    }

    public Render getSurfaceRender() {
        return this.surfaceRender;
    }

    public void setRenderMode(int i) {
        this.renderMode = i;
    }

    public int getRenderMode() {
        return this.renderMode;
    }

    public void requestRender() {
        LogUtils2.logI("DVR_EglSurfaceView", "requestRender ");
        EglRunnable eglRunnable = this.runnable;
        if (eglRunnable != null) {
            eglRunnable.requestRender();
        }
    }

    public void setEglContext(EGLContext eGLContext) {
        this.eglContext = eGLContext;
    }

    public EGLContext getEglContext() {
        EglManager eglManager = this.eglManager;
        if (eglManager != null) {
            return eglManager.getEglContext();
        }
        return null;
    }

    public void setSurfaceRender(CameraRender cameraRender) {
        this.surfaceRender = cameraRender;
    }
}
