package com.autolink.dvr.common.media.utils;

/* loaded from: classes.dex */
public class ByteUtils {
    private ByteUtils() {
    }

    public static double calcDecibelValue(byte[] bArr, int i) {
        if (i <= 0) {
            return 0.0d;
        }
        long j = 0;
        for (short s : byteToShort(bArr)) {
            j += s * s;
        }
        return Math.log10(j / i) * 10.0d;
    }

    public static short[] byteToShort(byte[] bArr) {
        int length = bArr.length >> 1;
        short[] sArr = new short[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            sArr[i] = (short) (((bArr[i2 + 1] & 255) << 8) | (bArr[i2] & 255));
        }
        return sArr;
    }
}
