package com.autolink.dvr.common.media.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.autolink.dvr.common.utils.LogUtils2;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.p006io.IOUtils;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes.dex */
public class ShaderUtils {
    private static final String TAG = "DVR_ShaderUtils";

    private ShaderUtils() {
    }

    public static int createProgram(Resources resources, String str, String str2) {
        return createProgram(loadAssetSource(resources, str), loadAssetSource(resources, str2));
    }

    private static String loadAssetSource(Resources resources, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream open = resources.getAssets().open(str);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (-1 != read) {
                    sb.append(new String(bArr, 0, read));
                } else {
                    return sb.toString().replaceAll("\\r\\n", IOUtils.LINE_SEPARATOR_UNIX);
                }
            }
        } catch (Exception unused) {
            return null;
        }
    }

    private static int createProgram(String str, String str2) {
        int loadShader;
        int loadShader2 = loadShader(35633, str);
        if (loadShader2 == 0 || (loadShader = loadShader(35632, str2)) == 0) {
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        if (glCreateProgram != 0) {
            GLES20.glAttachShader(glCreateProgram, loadShader2);
            GLES20.glAttachShader(glCreateProgram, loadShader);
            GLES20.glLinkProgram(glCreateProgram);
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
            if (iArr[0] != 1) {
                GLES20.glDeleteProgram(glCreateProgram);
                return 0;
            }
        }
        return glCreateProgram;
    }

    private static int loadShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        if (glCreateShader == 0) {
            return glCreateShader;
        }
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        GLES20.glDeleteShader(glCreateShader);
        return 0;
    }

    public static Bitmap createTextImage(String str, int i, String str2, String str3, int i2) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(str2));
        paint.setTextSize(i);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        float measureText = paint.measureText(str, 0, str.length());
        float f = paint.getFontMetrics().top;
        float f2 = i2 * 2;
        Bitmap createBitmap = Bitmap.createBitmap((int) (measureText + f2), (int) ((paint.getFontMetrics().bottom - f) + f2), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(Color.parseColor(str3));
        float f3 = i2;
        canvas.drawText(str, f3, (-f) + f3, paint);
        return createBitmap;
    }

    public static Bitmap createDateWaterMark() {
        Paint paint = new Paint();
        paint.setTextSize(13.0f);
        paint.setFakeBoldText(false);
        paint.setAntiAlias(true);
        paint.setColor(-1);
        paint.setShadowLayer(4.0f, 0.0f, 0.0f, ViewCompat.MEASURED_STATE_MASK);
        String format = new SimpleDateFormat("HH:mm:ss.SSS", Locale.CHINA).format(new Date());
        Bitmap createBitmap = Bitmap.createBitmap((int) paint.measureText(format, 0, format.length()), (int) (paint.getFontMetrics().bottom - paint.getFontMetrics().top), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(0);
        canvas.drawText(format, 1.0f, (createBitmap.getHeight() * 0.75f) + 1.0f, paint);
        return createBitmap;
    }

    public static Bitmap createImageWaterMark(Context context, int i) {
        LogUtils2.logI(TAG, "createImageWaterMark drawableID = " + i);
        Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), i);
        new Canvas().drawBitmap(decodeResource, new Matrix(), new Paint());
        return decodeResource;
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int i) {
        Drawable drawable = ContextCompat.getDrawable(context, i);
        if (Build.VERSION.SDK_INT < 21) {
            drawable = DrawableCompat.wrap(drawable).mutate();
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }
}
