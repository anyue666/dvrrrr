package com.autolink.dvr.common.media;

/* loaded from: classes.dex */
public class MediaCodecConstant {
    public static final int LARGE_SCALE = 1;
    public static final int LITTLE_SCALE = 2;
    public static final int MEDIA_MUXER_STARTED = 2;
    public static final int MEDIA_MUXER_STARTING = 1;
    public static final int MEDIA_MUXER_STOPED = 4;
    public static final int MEDIA_MUXER_STOPING = 3;
    public static final int MUXER_ERROR = 3;
    public static final int MUXER_INIT = 4;
    public static final int MUXER_START = 1;
    public static final int MUXER_STOP = 2;
    public static final int NOT_SCALE = 0;
    public static boolean audioFirst = true;
    public static boolean audioStart = false;
    public static boolean audioStop = false;
    public static boolean audioStop2 = false;
    public static int audioTrackIndex = -1;
    public static int audioTrackIndex2 = -1;
    public static boolean encodeStart = false;
    public static boolean encodeStart2 = false;
    public static boolean isStartRecord = false;
    public static volatile int mediaMuxerState = 0;
    public static int mediaMuxerState2 = 0;
    public static long reStartRecordPTS = 0;
    public static boolean surfaceChange = false;
    public static boolean surfaceCreate = false;
    public static int threadNum = 1;
    public static boolean videoFirst = true;
    public static volatile boolean videoStart = false;
    public static boolean videoStop = false;
    public static boolean videoStop2 = false;
    public static int videoTrackIndex = -1;
    public static int videoTrackIndex2 = -1;
}
