package com.autolink.dvr.common.utils;

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
@Metadata(m530d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\u0010\u0000\u001a\u00020\u0001*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002H\u008a@"}, m531d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", ""}, m532k = 3, m533mv = {1, 8, 0}, m535xi = 48)
@DebugMetadata(m546c = "com.autolink.dvr.common.utils.CommonToolUtils$getPreviewTime$1", m547f = "CommonToolUtils.kt", m548i = {}, m549l = {47}, m550m = "invokeSuspend", m551n = {}, m552s = {})
/* loaded from: classes.dex */
final class CommonToolUtils$getPreviewTime$1 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $path;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommonToolUtils this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CommonToolUtils$getPreviewTime$1(String str, CommonToolUtils commonToolUtils, Continuation<? super CommonToolUtils$getPreviewTime$1> continuation) {
        super(2, continuation);
        this.$path = str;
        this.this$0 = commonToolUtils;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        CommonToolUtils$getPreviewTime$1 commonToolUtils$getPreviewTime$1 = new CommonToolUtils$getPreviewTime$1(this.$path, this.this$0, continuation);
        commonToolUtils$getPreviewTime$1.L$0 = obj;
        return commonToolUtils$getPreviewTime$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(FlowCollector<? super String> flowCollector, Continuation<? super Unit> continuation) {
        return ((CommonToolUtils$getPreviewTime$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
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
            String extractMetadata = mediaMetadataRetriever.extractMetadata(9);
            CommonToolUtils commonToolUtils = this.this$0;
            Intrinsics.checkNotNull(extractMetadata);
            String stringForTime = commonToolUtils.stringForTime(Long.parseLong(extractMetadata));
            mediaMetadataRetriever.release();
            this.label = 1;
            if (flowCollector.emit(stringForTime, this) == coroutine_suspended) {
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
