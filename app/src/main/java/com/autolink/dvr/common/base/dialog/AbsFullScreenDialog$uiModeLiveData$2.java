package com.autolink.dvr.common.base.dialog;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: AbsFullScreenDialog.kt */
@Metadata(m530d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\b\b\u0000\u0010\u0003*\u00020\u0004\"\b\b\u0001\u0010\u0005*\u00020\u0006H\n¢\u0006\u0002\b\u0007"}, m531d2 = {"<anonymous>", "Landroidx/lifecycle/MutableLiveData;", "", "VB", "Landroidx/databinding/ViewDataBinding;", "VM", "Lcom/autolink/dvr/common/base/hmi/BaseViewModel;", "invoke"}, m532k = 3, m533mv = {1, 8, 0}, m535xi = 48)
/* loaded from: classes.dex */
final class AbsFullScreenDialog$uiModeLiveData$2 extends Lambda implements Function0<MutableLiveData<Integer>> {
    final /* synthetic */ AbsFullScreenDialog<VB, VM> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AbsFullScreenDialog$uiModeLiveData$2(AbsFullScreenDialog<VB, VM> absFullScreenDialog) {
        super(0);
        this.this$0 = absFullScreenDialog;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    public final MutableLiveData<Integer> invoke() {
        final Function1 function1;
        MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>();
        function1 = ((AbsFullScreenDialog) this.this$0).uiModeObserver;
        mutableLiveData.observeForever(new Observer() { // from class: com.autolink.dvr.common.base.dialog.AbsFullScreenDialog$uiModeLiveData$2$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AbsFullScreenDialog$uiModeLiveData$2.invoke$lambda$0(Function1.this, obj);
            }
        });
        AbsFullScreenDialog.INSTANCE.getUiModeListenerList().add(mutableLiveData);
        return mutableLiveData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }
}
