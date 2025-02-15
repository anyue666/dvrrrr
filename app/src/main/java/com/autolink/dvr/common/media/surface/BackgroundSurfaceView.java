package com.autolink.dvr.common.media.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.common.utils.LogUtils2;

/* loaded from: classes.dex */
public class BackgroundSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private final String TAG;
    private int mBgColor;

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public BackgroundSurfaceView(Context context) {
        this(context, null);
    }

    public BackgroundSurfaceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BackgroundSurfaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "DVR_BackgroundSurfaceView";
        LogUtils2.logI("DVR_BackgroundSurfaceView", "BackgroundSurfaceView");
        getHolder().addCallback(this);
        setZOrderOnTop(false);
        setZOrderMediaOverlay(false);
        this.mBgColor = getResources().getColor(C0903R.color.content_bg);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        LogUtils2.logI("DVR_BackgroundSurfaceView", "BackgroundSurfaceView   surfaceCreated");
        Canvas lockCanvas = surfaceHolder.lockCanvas();
        if (lockCanvas != null) {
            lockCanvas.drawColor(this.mBgColor);
        }
        surfaceHolder.unlockCanvasAndPost(lockCanvas);
    }
}
