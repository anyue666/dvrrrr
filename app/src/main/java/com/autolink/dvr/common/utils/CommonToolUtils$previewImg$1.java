package com.autolink.dvr.common.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: CommonToolUtils.kt */
@Metadata(m530d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, m531d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Landroid/graphics/Bitmap;"}, m532k = 3, m533mv = {1, 8, 0}, m535xi = 48)
@DebugMetadata(m546c = "com.autolink.dvr.common.utils.CommonToolUtils$previewImg$1", m547f = "CommonToolUtils.kt", m548i = {}, m549l = {37}, m550m = "invokeSuspend", m551n = {}, m552s = {})
/* loaded from: classes.dex */
final class CommonToolUtils$previewImg$1 extends SuspendLambda implements Function2<FlowCollector<? super Bitmap>, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $path;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CommonToolUtils$previewImg$1(String str, Continuation<? super CommonToolUtils$previewImg$1> continuation) {
        super(2, continuation);
        this.$path = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        CommonToolUtils$previewImg$1 commonToolUtils$previewImg$1 = new CommonToolUtils$previewImg$1(this.$path, continuation);
        commonToolUtils$previewImg$1.L$0 = obj;
        return commonToolUtils$previewImg$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(FlowCollector<? super Bitmap> flowCollector, Continuation<? super Unit> continuation) {
        return ((CommonToolUtils$previewImg$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(this.$path);
            Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime(0L, 2);
            Intrinsics.checkNotNull(frameAtTime, "null cannot be cast to non-null type android.graphics.Bitmap");
            mediaMetadataRetriever.release();
            this.label = 1;
            if (flowCollector.emit(frameAtTime, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
