package com.autolink.dvr.common.media.codec;

import android.media.MediaCodec;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class MuxerData {
    public MediaCodec.BufferInfo bufferInfo;
    public ByteBuffer byteBuf;
    public int trackIndex;

    public MuxerData(int i, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        this.trackIndex = i;
        this.byteBuf = byteBuffer;
        this.bufferInfo = bufferInfo;
    }
}
