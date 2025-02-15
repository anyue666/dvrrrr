package com.autolink.dvr.common.utils;

import android.graphics.Bitmap;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.cookie.ClientCookie;
import java.util.Formatter;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: CommonToolUtils.kt */
@Metadata(m530d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\u0006\u0010\b\u001a\u00020\u0007J\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00062\u0006\u0010\b\u001a\u00020\u0007J\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, m531d2 = {"Lcom/autolink/dvr/common/utils/CommonToolUtils;", "", "()V", "mScope", "Lkotlinx/coroutines/CoroutineScope;", "getPreviewTime", "Lkotlinx/coroutines/flow/Flow;", "", ClientCookie.PATH_ATTR, "previewImg", "Landroid/graphics/Bitmap;", "stringForTime", "timeMs", "", "Companion", "ALDVR_T1JRelease"}, m532k = 1, m533mv = {1, 8, 0}, m535xi = 48)
/* loaded from: classes.dex */
public final class CommonToolUtils {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final CommonToolUtils singleton = new CommonToolUtils();
    private final CoroutineScope mScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());

    /* compiled from: CommonToolUtils.kt */
    @Metadata(m530d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, m531d2 = {"Lcom/autolink/dvr/common/utils/CommonToolUtils$Companion;", "", "()V", "singleton", "Lcom/autolink/dvr/common/utils/CommonToolUtils;", "getSingleton", "()Lcom/autolink/dvr/common/utils/CommonToolUtils;", "ALDVR_T1JRelease"}, m532k = 1, m533mv = {1, 8, 0}, m535xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final CommonToolUtils getSingleton() {
            return CommonToolUtils.singleton;
        }
    }

    public final String stringForTime(long timeMs) {
        long j = timeMs / 1000;
        long j2 = 60;
        return new Formatter().format("%02d:%02d:%02d", Long.valueOf(j / 3600), Long.valueOf((j / j2) % j2), Long.valueOf(j % j2)).toString();
    }

    public final Flow<Bitmap> previewImg(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return FlowKt.flowOn(FlowKt.flow(new CommonToolUtils$previewImg$1(path, null)), Dispatchers.getIO());
    }

    public final Flow<String> getPreviewTime(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return FlowKt.flowOn(FlowKt.flow(new CommonToolUtils$getPreviewTime$1(path, this, null)), Dispatchers.getIO());
    }
}
