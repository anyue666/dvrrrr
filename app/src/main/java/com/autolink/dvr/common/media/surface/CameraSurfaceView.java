package com.autolink.dvr.common.media.surface;

import android.content.Context;
import android.util.AttributeSet;
import com.autolink.dvr.common.utils.LogUtils2;

/* loaded from: classes.dex */
public class CameraSurfaceView extends EglSurfaceView {
    private final String TAG;
    private Context context;
    private CameraRender render;

    public CameraSurfaceView(Context context) {
        this(context, null);
    }

    public CameraSurfaceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CameraSurfaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "DVR_CameraSurfaceView";
        LogUtils2.logD("DVR_CameraSurfaceView", "CameraSurfaceView ");
        this.context = context;
        CameraRender cameraRender = new CameraRender(context);
        this.render = cameraRender;
        setSurfaceRender(cameraRender);
        setRenderMode(1);
    }
}
