package com.autolink.dvr.common.media.codec;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.common.can.CanOperation;
import com.autolink.dvr.common.media.surface.EglSurfaceView;
import com.autolink.dvr.common.media.utils.ShaderUtils;
import com.autolink.dvr.common.utils.LogUtils2;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes.dex */
public class VideoEncodeRender implements EglSurfaceView.Render {
    private int afPosition;
    private int avPosition;
    private Canvas bitmapCanvas;
    private Bitmap bitmapTime;
    private int changeColor;
    private int changeType;
    private Context context;
    private int program;
    private SimpleDateFormat textFormat;
    private Paint textPaint;
    private int texture;
    private FloatBuffer textureBuffer;
    private int textureId;
    private int type;
    private int vboId;
    private FloatBuffer vertexBuffer;
    private int waterTextureId2;
    private final String TAG = "DVR_VideoEncodeRender";
    private float[] vertexData = {-1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    private float[] textureData = {0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};
    private final int COORDS_PER_VERTEX = 3;
    private int vertexStride = 12;
    private float[] color = {0.0f, 0.0f, 0.0f};
    private int[] waterTextureId = new int[6];

    public VideoEncodeRender(Context context) {
        LogUtils2.logI("DVR_VideoEncodeRender", "VideoEncodeRender ");
        this.context = context;
        initTimeWaterMark();
        initImageWaterMark();
        FloatBuffer put = ByteBuffer.allocateDirect(this.vertexData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(this.vertexData);
        this.vertexBuffer = put;
        put.position(0);
        FloatBuffer put2 = ByteBuffer.allocateDirect(this.textureData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(this.textureData);
        this.textureBuffer = put2;
        put2.position(0);
    }

    @Override // com.autolink.dvr.common.media.surface.EglSurfaceView.Render
    public void onSurfaceCreated() {
        LogUtils2.logI("DVR_VideoEncodeRender", "onSurfaceCreated ");
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(770, 771);
        int createProgram = ShaderUtils.createProgram(this.context.getResources(), "vertex_shader_screen.glsl", "fragment_shader_screen.glsl");
        this.program = createProgram;
        if (createProgram > 0) {
            this.avPosition = GLES20.glGetAttribLocation(createProgram, "av_Position");
            this.afPosition = GLES20.glGetAttribLocation(this.program, "af_Position");
            this.texture = GLES20.glGetUniformLocation(this.program, "sTexture");
            this.changeType = GLES20.glGetUniformLocation(this.program, "vChangeType");
            this.changeColor = GLES20.glGetUniformLocation(this.program, "vChangeColor");
            createVBO();
            createWaterTextureId(0);
            createWaterTextureId(1);
            createWaterTextureId(2);
            createWaterTextureId(3);
            createWaterTextureId(4);
            createWaterTextureId(5);
        }
    }

    @Override // com.autolink.dvr.common.media.surface.EglSurfaceView.Render
    public void onSurfaceChanged(int i, int i2) {
        LogUtils2.logI("DVR_VideoEncodeRender", "onSurfaceChanged ");
        GLES20.glViewport(0, 0, i, i2);
    }

    @Override // com.autolink.dvr.common.media.surface.EglSurfaceView.Render
    public void onDrawFrame() {
        GLES20.glClear(16384);
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glUseProgram(this.program);
        GLES20.glUniform1i(this.changeType, this.type);
        GLES20.glUniform3fv(this.changeColor, 1, this.color, 0);
        GLES20.glBindTexture(3553, this.textureId);
        GLES20.glEnableVertexAttribArray(this.avPosition);
        GLES20.glEnableVertexAttribArray(this.afPosition);
        useVboSetVertext();
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(this.avPosition);
        GLES20.glDisableVertexAttribArray(this.afPosition);
        GLES20.glBindTexture(3553, 0);
        drawDateWaterMark();
        if (CanOperation.turnLeftLight == 1) {
            drawImageWaterMark(2, 0);
        }
        if (CanOperation.turnRightLight == 2) {
            drawImageWaterMark(3, 1);
        }
        if (CanOperation.OverlayAIRBAGInformation) {
            drawImageWaterMark(5, 3);
        }
        if (CanOperation.OverlayAEBInformation) {
            drawImageWaterMark(4, 4);
        }
    }

    public void onDraw(int i) {
        this.textureId = i;
        onDrawFrame();
    }

    private void createVBO() {
        LogUtils2.logI("DVR_VideoEncodeRender", "createVBO ");
        int[] iArr = new int[1];
        GLES20.glGenBuffers(1, iArr, 0);
        int i = iArr[0];
        this.vboId = i;
        GLES20.glBindBuffer(34962, i);
        GLES20.glBufferData(34962, (this.vertexData.length * 4) + (this.textureData.length * 4), null, 35044);
        GLES20.glBufferSubData(34962, 0, this.vertexData.length * 4, this.vertexBuffer);
        GLES20.glBufferSubData(34962, this.vertexData.length * 4, this.textureData.length * 4, this.textureBuffer);
        GLES20.glBindBuffer(34962, 0);
    }

    private void useVboSetVertext() {
        GLES20.glBindBuffer(34962, this.vboId);
        GLES20.glVertexAttribPointer(this.avPosition, 3, 5126, false, this.vertexStride, 0);
        GLES20.glVertexAttribPointer(this.afPosition, 3, 5126, false, this.vertexStride, this.vertexData.length * 4);
        GLES20.glBindBuffer(34962, 0);
    }

    private void initTimeWaterMark() {
        Paint paint = new Paint();
        this.textPaint = paint;
        paint.setTextSize(28.0f);
        this.textPaint.setFakeBoldText(false);
        this.textPaint.setAntiAlias(true);
        this.textPaint.setColor(-1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss", Locale.CHINA);
        this.textFormat = simpleDateFormat;
        String format = simpleDateFormat.format(new Date());
        this.bitmapTime = Bitmap.createBitmap(((int) this.textPaint.measureText(format, 0, format.length())) + 10, (int) (this.textPaint.getFontMetrics().bottom - this.textPaint.getFontMetrics().top), Bitmap.Config.ARGB_8888);
        this.bitmapCanvas = new Canvas(this.bitmapTime);
        float[] fArr = this.vertexData;
        fArr[12] = -0.12f;
        fArr[13] = 0.8f;
        fArr[14] = 0.0f;
        float width = ((((this.bitmapTime.getWidth() * 1.0f) / this.bitmapTime.getHeight()) * 0.1f) * 0.66314197f) - 0.12f;
        fArr[15] = width;
        fArr[16] = 0.8f;
        fArr[17] = 0.0f;
        fArr[18] = -0.12f;
        fArr[19] = 0.9f;
        fArr[20] = 0.0f;
        fArr[21] = width;
        fArr[22] = 0.9f;
        fArr[23] = 0.0f;
    }

    private void initImageWaterMark() {
        float[] fArr = this.vertexData;
        fArr[24] = -0.48000002f;
        fArr[25] = 0.8f;
        fArr[27] = -0.4f;
        fArr[28] = 0.8f;
        fArr[30] = -0.48000002f;
        fArr[31] = 0.9f;
        fArr[33] = -0.4f;
        fArr[34] = 0.9f;
        fArr[36] = 0.4f;
        fArr[37] = 0.8f;
        fArr[39] = 0.48000002f;
        fArr[40] = 0.8f;
        fArr[42] = 0.4f;
        fArr[43] = 0.9f;
        fArr[45] = 0.48000002f;
        fArr[46] = 0.9f;
        fArr[48] = 0.52f;
        fArr[49] = 0.8f;
        fArr[51] = 0.593f;
        fArr[52] = 0.8f;
        fArr[54] = 0.52f;
        fArr[55] = 0.91f;
        fArr[57] = 0.593f;
        fArr[58] = 0.91f;
        fArr[60] = 0.62f;
        fArr[61] = 0.79f;
        fArr[63] = 0.70600003f;
        fArr[64] = 0.79f;
        fArr[66] = 0.62f;
        fArr[67] = 0.92f;
        fArr[69] = 0.70600003f;
        fArr[70] = 0.92f;
    }

    private void createWaterTextureId(int i) {
        Bitmap bitmap;
        Bitmap decodeResource;
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        if (i == 0) {
            bitmap = ShaderUtils.getBitmapFromVectorDrawable(this.context, C0903R.drawable.ic_arrow_left);
            this.waterTextureId[0] = iArr[0];
        } else {
            if (i == 1) {
                decodeResource = ShaderUtils.getBitmapFromVectorDrawable(this.context, C0903R.drawable.ic_arrow_right);
                this.waterTextureId[1] = iArr[0];
            } else if (i == 2) {
                decodeResource = BitmapFactory.decodeResource(this.context.getResources(), C0903R.drawable.activity_main_bottom_icon_file);
                this.waterTextureId[2] = iArr[0];
            } else if (i == 3) {
                bitmap = ShaderUtils.getBitmapFromVectorDrawable(this.context, C0903R.drawable.ic_airba_opening);
                this.waterTextureId[3] = iArr[0];
            } else if (i == 4) {
                bitmap = ShaderUtils.getBitmapFromVectorDrawable(this.context, C0903R.drawable.ic_led_aeb_red);
                this.waterTextureId[4] = iArr[0];
            } else if (i == 5) {
                bitmap = ShaderUtils.createTextImage("陀螺仪", 28, "#ffffff", "#00000000", 2);
                this.waterTextureId[5] = iArr[0];
            } else {
                bitmap = null;
            }
            bitmap = decodeResource;
        }
        GLES20.glBindTexture(3553, this.waterTextureId[i]);
        GLES20.glTexParameteri(3553, 10242, 10497);
        GLES20.glTexParameteri(3553, 10243, 10497);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLUtils.texImage2D(3553, 0, bitmap, 0);
        bitmap.recycle();
        GLES20.glBindTexture(3553, 0);
    }

    public void drawDateWaterMark() {
        String format = this.textFormat.format(new Date());
        this.bitmapTime.eraseColor(0);
        this.bitmapCanvas.drawText(format, 1.0f, (this.bitmapTime.getHeight() * 0.75f) + 1.0f, this.textPaint);
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        int i = iArr[0];
        this.waterTextureId2 = i;
        GLES20.glBindTexture(3553, i);
        GLES20.glTexParameteri(3553, 10242, 10497);
        GLES20.glTexParameteri(3553, 10243, 10497);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLUtils.texImage2D(3553, 0, this.bitmapTime, 0);
        GLES20.glGenerateMipmap(3553);
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindBuffer(34962, this.vboId);
        GLES20.glBindTexture(3553, this.waterTextureId2);
        GLES20.glEnableVertexAttribArray(this.avPosition);
        GLES20.glEnableVertexAttribArray(this.afPosition);
        int i2 = this.avPosition;
        int i3 = this.vertexStride;
        GLES20.glVertexAttribPointer(i2, 3, 5126, false, i3, i3 * 4);
        GLES20.glVertexAttribPointer(this.afPosition, 3, 5126, false, this.vertexStride, this.vertexData.length * 4);
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(this.avPosition);
        GLES20.glDisableVertexAttribArray(this.afPosition);
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindBuffer(34962, 0);
        GLES20.glDeleteTextures(1, iArr, 0);
    }

    private void drawImageWaterMark(int i, int i2) {
        GLES20.glBindBuffer(34962, this.vboId);
        GLES20.glBindTexture(3553, this.waterTextureId[i2]);
        GLES20.glEnableVertexAttribArray(this.avPosition);
        GLES20.glEnableVertexAttribArray(this.afPosition);
        GLES20.glVertexAttribPointer(this.avPosition, 3, 5126, false, this.vertexStride, i * 48);
        GLES20.glVertexAttribPointer(this.afPosition, 3, 5126, false, this.vertexStride, this.vertexData.length * 4);
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(this.avPosition);
        GLES20.glDisableVertexAttribArray(this.afPosition);
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindBuffer(34962, 0);
    }
}
