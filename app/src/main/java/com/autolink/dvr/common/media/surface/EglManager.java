package com.autolink.dvr.common.media.surface;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.view.Surface;
import com.autolink.dvr.common.media.utils.DisplayUtils;
import com.autolink.dvr.common.utils.LogUtils2;

/* loaded from: classes.dex */
public class EglManager {
    private final String TAG = "DVR_EglManager";
    private EGLContext eglContext;
    private EGLDisplay eglDisplay;
    private EGLSurface eglSurface;

    public boolean init(Surface surface, EGLContext eGLContext) {
        LogUtils2.logI("DVR_EglManager", "init surface = " + surface + " , context=" + eGLContext);
        EGLDisplay eglGetDisplay = EGL14.eglGetDisplay(0);
        this.eglDisplay = eglGetDisplay;
        if (eglGetDisplay == EGL14.EGL_NO_DISPLAY) {
            LogUtils2.logE("DVR_EglManager", "获取显示设备失败 ");
            return false;
        }
        int[] iArr = new int[2];
        if (!EGL14.eglInitialize(this.eglDisplay, iArr, 0, iArr, 1)) {
            LogUtils2.logE("DVR_EglManager", "EGL14初始化失败 ");
            return false;
        }
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        int[] iArr2 = new int[1];
        if (!EGL14.eglChooseConfig(this.eglDisplay, new int[]{12320, 32, 12321, 8, 12324, 8, 12322, 8, 12323, 8, 12352, 4, 12339, 4, 12344}, 0, eGLConfigArr, 0, 1, iArr2, 0) || iArr2[0] < 0) {
            LogUtils2.logE("DVR_EglManager", "config参数配置失败");
            return false;
        }
        int[] iArr3 = {12440, 2, 12344};
        if (eGLContext == null) {
            EGLContext eglCreateContext = EGL14.eglCreateContext(this.eglDisplay, eGLConfigArr[0], EGL14.EGL_NO_CONTEXT, iArr3, 0);
            this.eglContext = eglCreateContext;
            DisplayUtils.eglContext = eglCreateContext;
        } else {
            this.eglContext = EGL14.eglCreateContext(this.eglDisplay, eGLConfigArr[0], eGLContext, iArr3, 0);
        }
        if (this.eglContext == EGL14.EGL_NO_CONTEXT) {
            LogUtils2.logE("DVR_EglManager", "EGLContext 创建失败");
            return false;
        }
        if (surface == null || !surface.isValid()) {
            LogUtils2.logE("DVR_EglManager", "surface is null or not valid");
            return false;
        }
        try {
            EGLSurface eglCreateWindowSurface = EGL14.eglCreateWindowSurface(this.eglDisplay, eGLConfigArr[0], surface, new int[]{12344}, 0);
            this.eglSurface = eglCreateWindowSurface;
            if (eglCreateWindowSurface == EGL14.EGL_NO_SURFACE) {
                LogUtils2.logE("DVR_EglManager", "连接EGL和设备屏幕失败");
                return false;
            }
            EGLDisplay eGLDisplay = this.eglDisplay;
            EGLSurface eGLSurface = this.eglSurface;
            if (EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.eglContext)) {
                return true;
            }
            LogUtils2.logE("DVR_EglManager", "eglContext绑定失败");
            return false;
        } catch (Exception e) {
            LogUtils2.logE("DVR_EglManager", "连接EGL和设备屏幕失败, e = " + e.toString());
            return false;
        }
    }

    public boolean swapBuffer() {
        return EGL14.eglSwapBuffers(this.eglDisplay, this.eglSurface);
    }

    public void release() {
        LogUtils2.logI("DVR_EglManager", "release ");
        if (this.eglSurface != null) {
            EGL14.eglMakeCurrent(this.eglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroySurface(this.eglDisplay, this.eglSurface);
            this.eglSurface = null;
        }
        EGLContext eGLContext = this.eglContext;
        if (eGLContext != null) {
            EGL14.eglDestroyContext(this.eglDisplay, eGLContext);
            this.eglContext = null;
        }
        EGLDisplay eGLDisplay = this.eglDisplay;
        if (eGLDisplay != null) {
            EGL14.eglTerminate(eGLDisplay);
            this.eglDisplay = null;
        }
    }

    public EGLContext getEglContext() {
        LogUtils2.logI("DVR_EglManager", "getEglContext ");
        return this.eglContext;
    }
}
