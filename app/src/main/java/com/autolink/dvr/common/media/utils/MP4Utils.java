package com.autolink.dvr.common.media.utils;

import android.media.MediaMetadataRetriever;
import com.autolink.dvr.common.utils.LogUtils2;
import java.io.IOException;

/* loaded from: classes.dex */
public class MP4Utils {
    private static final String TAG = "DVR_MP4Utils";

    public static boolean isPlayable(String str) throws IOException {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        boolean z = false;
        try {
            try {
                mediaMetadataRetriever.setDataSource(str);
                String extractMetadata = mediaMetadataRetriever.extractMetadata(9);
                LogUtils2.logI(TAG, "isPlayable path = " + str + ",durationStr = " + extractMetadata);
                if (extractMetadata != null && !extractMetadata.isEmpty()) {
                    if (Long.valueOf(extractMetadata).longValue() > 0) {
                        z = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z;
        } finally {
            mediaMetadataRetriever.release();
            LogUtils2.logI(TAG, "retriever.release() ");
        }
    }
}
