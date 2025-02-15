package com.autolink.dvr.common.media.surface;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.Matrix;
import com.autolink.dvr.common.media.codec.VideoEncodeRender;
import com.autolink.dvr.common.media.surface.EglSurfaceView;
import com.autolink.dvr.common.media.utils.DisplayUtils;
import com.autolink.dvr.common.media.utils.ShaderUtils;
import com.autolink.dvr.common.utils.LogUtils2;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes.dex */
public class VideoFBORender implements EglSurfaceView.Render, SurfaceTexture.OnFrameAvailableListener {
    private int cameraTextureId;
    private Context context;
    private int fboId;
    private int fboTextureId;
    private OnSurfaceListener onSurfaceListener;
    private int program;
    private int screenHeight;
    private int screenWidth;
    private SurfaceTexture surfaceTexture;
    private int tPosition;
    private FloatBuffer textureBuffer;
    private int uMatrix;
    private int vPosition;
    private int vboId;
    private FloatBuffer vertexBuffer;
    private VideoEncodeRender videoEncodeRender;
    private final String TAG = "DVR_VideoFBORender";
    private float[] vertexPoint = {-1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f};
    private float[] texturePoint = {0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};
    private int coordinateVertex = 3;
    private float[] matrix = new float[16];

    public interface OnSurfaceListener {
        void onSurfaceCreate(SurfaceTexture surfaceTexture, int i);
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
    }

    public VideoFBORender(Context context) {
        LogUtils2.logD("DVR_VideoFBORender", "VideoFBORender ");
        this.context = context;
        this.screenWidth = DisplayUtils.getScreenWidth(context);
        this.screenHeight = DisplayUtils.getScreenHeight(context);
        this.videoEncodeRender = new VideoEncodeRender(context);
        FloatBuffer put = ByteBuffer.allocateDirect(this.vertexPoint.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(this.vertexPoint);
        this.vertexBuffer = put;
        put.position(0);
        FloatBuffer put2 = ByteBuffer.allocateDirect(this.texturePoint.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(this.texturePoint);
        this.textureBuffer = put2;
        put2.position(0);
    }

    @Override // com.autolink.dvr.common.media.surface.EglSurfaceView.Render
    public void onSurfaceCreated() {
        LogUtils2.logD("DVR_VideoFBORender", "onSurfaceCreated ");
        int createProgram = ShaderUtils.createProgram(this.context.getResources(), "vertex_shader.glsl", "fragment_shader.glsl");
        this.program = createProgram;
        if (createProgram > 0) {
            this.vPosition = GLES20.glGetAttribLocation(createProgram, "av_Position");
            this.tPosition = GLES20.glGetAttribLocation(this.program, "af_Position");
            this.uMatrix = GLES20.glGetUniformLocation(this.program, "u_Matrix");
            createVBO();
            createFBO(this.screenWidth, this.screenHeight);
            createCameraTexture();
        }
        this.videoEncodeRender.onSurfaceCreated();
    }

    @Override // com.autolink.dvr.common.media.surface.EglSurfaceView.Render
    public void onSurfaceChanged(int i, int i2) {
        LogUtils2.logD("DVR_VideoFBORender", "onSurfaceChanged ");
        GLES20.glViewport(0, 0, i, i2);
        this.videoEncodeRender.onSurfaceChanged(i, i2);
    }

    @Override // com.autolink.dvr.common.media.surface.EglSurfaceView.Render
    public void onDrawFrame() {
        updateTextImage();
        GLES20.glClear(16384);
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glUseProgram(this.program);
        GLES20.glBindFramebuffer(36160, this.fboId);
        GLES20.glBindTexture(36197, this.cameraTextureId);
        GLES20.glEnableVertexAttribArray(this.vPosition);
        GLES20.glEnableVertexAttribArray(this.tPosition);
        GLES20.glUniformMatrix4fv(this.uMatrix, 1, false, this.matrix, 0);
        useVboSetVertext();
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(this.vPosition);
        GLES20.glDisableVertexAttribArray(this.tPosition);
        GLES20.glBindFramebuffer(36160, 0);
        this.videoEncodeRender.onDraw(this.fboTextureId);
    }

    public void resetMatrix() {
        Matrix.setIdentityM(this.matrix, 0);
    }

    public void setAngle(float f, float f2, float f3, float f4) {
        Matrix.rotateM(this.matrix, 0, f, f2, f3, f4);
    }

    public void updateTextImage() {
        this.surfaceTexture.updateTexImage();
    }

    private void createVBO() {
        LogUtils2.logD("DVR_VideoFBORender", "createVBO ");
        int[] iArr = new int[1];
        GLES20.glGenBuffers(1, iArr, 0);
        int i = iArr[0];
        this.vboId = i;
        GLES20.glBindBuffer(34962, i);
        GLES20.glBufferData(34962, (this.vertexPoint.length * 4) + (this.texturePoint.length * 4), null, 35044);
        GLES20.glBufferSubData(34962, 0, this.vertexPoint.length * 4, this.vertexBuffer);
        GLES20.glBufferSubData(34962, this.vertexPoint.length * 4, this.texturePoint.length * 4, this.textureBuffer);
        GLES20.glBindBuffer(34962, 0);
    }

    private void useVboSetVertext() {
        GLES20.glBindBuffer(34962, this.vboId);
        GLES20.glVertexAttribPointer(this.vPosition, 3, 5126, false, 12, 0);
        GLES20.glVertexAttribPointer(this.tPosition, 3, 5126, false, 12, this.vertexPoint.length * 4);
        GLES20.glBindBuffer(34962, 0);
    }

    private void createFBO(int i, int i2) {
        LogUtils2.logD("DVR_VideoFBORender", "createFBO ");
        int[] iArr = new int[1];
        GLES20.glGenFramebuffers(1, iArr, 0);
        int i3 = iArr[0];
        this.fboId = i3;
        GLES20.glBindFramebuffer(36160, i3);
        int[] iArr2 = new int[1];
        GLES20.glGenTextures(1, iArr2, 0);
        int i4 = iArr2[0];
        this.fboTextureId = i4;
        DisplayUtils.fboTextureId = i4;
        GLES20.glBindTexture(3553, this.fboTextureId);
        GLES20.glTexParameteri(3553, 10242, 10497);
        GLES20.glTexParameteri(3553, 10243, 10497);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.fboTextureId, 0);
        GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, null);
        if (GLES20.glCheckFramebufferStatus(36160) != 36053) {
            LogUtils2.logD("DVR_VideoFBORender", "createFBO: 纹理绑定FBO失败");
        }
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindFramebuffer(36160, 0);
    }

    private void createCameraTexture() {
        LogUtils2.logD("DVR_VideoFBORender", "createCameraTexture ");
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        int i = iArr[0];
        this.cameraTextureId = i;
        GLES20.glBindTexture(36197, i);
        GLES20.glTexParameteri(36197, 10242, 10497);
        GLES20.glTexParameteri(36197, 10243, 10497);
        GLES20.glTexParameteri(36197, 10241, 9729);
        GLES20.glTexParameteri(36197, 10240, 9729);
        SurfaceTexture surfaceTexture = new SurfaceTexture(this.cameraTextureId);
        this.surfaceTexture = surfaceTexture;
        surfaceTexture.setOnFrameAvailableListener(this);
        OnSurfaceListener onSurfaceListener = this.onSurfaceListener;
        if (onSurfaceListener != null) {
            onSurfaceListener.onSurfaceCreate(this.surfaceTexture, this.fboTextureId);
        }
        GLES20.glBindTexture(36197, 0);
    }

    public void setOnSurfaceListener(OnSurfaceListener onSurfaceListener) {
        this.onSurfaceListener = onSurfaceListener;
    }
}
