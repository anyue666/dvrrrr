package com.autolink.dvr.common.base.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.WindowManager;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelKt;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.common.base.dialog.IBaseComponents;
import com.autolink.dvr.common.base.hmi.BaseViewModel;
import com.autolink.dvr.common.utils.LogUtils2;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;

/* compiled from: AbsFullScreenDialog.kt */
@Metadata(m530d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\b&\u0018\u0000 C*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u00052\u00020\u00062\u00020\u0007:\u0001CB\u0007\b\u0016¢\u0006\u0002\u0010\bJ\r\u00104\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0018J\u0006\u00105\u001a\u00020-J\b\u00106\u001a\u000207H\u0016J\b\u00108\u001a\u000209H\u0016J\b\u0010:\u001a\u00020-H\u0016J\b\u0010;\u001a\u00020-H\u0002J\b\u0010<\u001a\u00020-H\u0016J\b\u0010=\u001a\u00020-H\u0016J\b\u0010>\u001a\u00020-H\u0016J\b\u0010?\u001a\u00020-H\u0016J\b\u0010@\u001a\u00020-H\u0016J\b\u0010A\u001a\u00020-H\u0016J\b\u0010B\u001a\u00020\u0007H\u0016R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u000b\"\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0016\u001a\u00028\u0000X\u0096.¢\u0006\u0010\n\u0002\u0010\u001b\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001c\u001a\u00028\u0001X\u0096\u000e¢\u0006\u0010\n\u0002\u0010!\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b'\u0010(R\u001a\u0010+\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020-0,X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010.\u001a\u00028\u00018BX\u0082\u0084\u0002¢\u0006\u0012\n\u0004\b1\u0010*\u0012\u0004\b/\u0010\b\u001a\u0004\b0\u0010\u001eR\u000e\u00102\u001a\u000203X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006D"}, m531d2 = {"Lcom/autolink/dvr/common/base/dialog/AbsFullScreenDialog;", "VB", "Landroidx/databinding/ViewDataBinding;", "VM", "Lcom/autolink/dvr/common/base/hmi/BaseViewModel;", "Lcom/autolink/dvr/common/base/dialog/IBaseComponents;", "Landroidx/lifecycle/LifecycleOwner;", "Landroidx/lifecycle/ViewModelStoreOwner;", "()V", "isRemoved", "", "()Z", "setRemoved", "(Z)V", "job", "Lkotlinx/coroutines/Job;", "getJob", "()Lkotlinx/coroutines/Job;", "setJob", "(Lkotlinx/coroutines/Job;)V", "lifecycleRegistry", "Landroidx/lifecycle/LifecycleRegistry;", "mVB", "getMVB", "()Landroidx/databinding/ViewDataBinding;", "setMVB", "(Landroidx/databinding/ViewDataBinding;)V", "Landroidx/databinding/ViewDataBinding;", "mVM", "getMVM", "()Lcom/autolink/dvr/common/base/hmi/BaseViewModel;", "setMVM", "(Lcom/autolink/dvr/common/base/hmi/BaseViewModel;)V", "Lcom/autolink/dvr/common/base/hmi/BaseViewModel;", "mainScope", "Lkotlinx/coroutines/CoroutineScope;", "uiModeLiveData", "Landroidx/lifecycle/MutableLiveData;", "", "getUiModeLiveData", "()Landroidx/lifecycle/MutableLiveData;", "uiModeLiveData$delegate", "Lkotlin/Lazy;", "uiModeObserver", "Lkotlin/Function1;", "", "viewModel", "getViewModel$annotations", "getViewModel", "viewModel$delegate", "windowManager", "Landroid/view/WindowManager;", "createVB", "finish", "getLifecycle", "Landroidx/lifecycle/Lifecycle;", "getViewModelStore", "Landroidx/lifecycle/ViewModelStore;", "initViewModel", "initWindow", "onCreate", "onDestroy", "onDestroyView", "onResume", "onStart", "onStop", "onStore", "Companion", "ALDVR_T1JRelease"}, m532k = 1, m533mv = {1, 8, 0}, m535xi = 48)
/* loaded from: classes.dex */
public abstract class AbsFullScreenDialog<VB extends ViewDataBinding, VM extends BaseViewModel> implements IBaseComponents<VB, VM>, LifecycleOwner, ViewModelStoreOwner {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "DVR_AbsFullScreenDialog";
    private static final CopyOnWriteArrayList<MutableLiveData<Integer>> uiModeListenerList = new CopyOnWriteArrayList<>();
    private boolean isRemoved;
    private Job job;
    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    public VB mVB;
    private VM mVM;
    private final CoroutineScope mainScope;

    /* renamed from: uiModeLiveData$delegate, reason: from kotlin metadata */
    private final Lazy uiModeLiveData;
    private final Function1<Integer, Unit> uiModeObserver;

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;
    private final WindowManager windowManager;

    private static /* synthetic */ void getViewModel$annotations() {
    }

    @Override // com.autolink.dvr.common.base.dialog.IBaseComponents
    public void initViewModel() {
    }

    public void onDestroyView() {
    }

    @Override // com.autolink.dvr.common.base.dialog.IBaseComponents
    public void initViewBeforeSuperMethod() {
        IBaseComponents.DefaultImpls.initViewBeforeSuperMethod(this);
    }

    @Override // com.autolink.dvr.common.base.dialog.IBaseComponents
    public int onLayoutId() {
        return IBaseComponents.DefaultImpls.onLayoutId(this);
    }

    /* renamed from: isRemoved, reason: from getter */
    public final boolean getIsRemoved() {
        return this.isRemoved;
    }

    public final void setRemoved(boolean z) {
        this.isRemoved = z;
    }

    /* compiled from: AbsFullScreenDialog.kt */
    @Metadata(m530d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, m531d2 = {"Lcom/autolink/dvr/common/base/dialog/AbsFullScreenDialog$Companion;", "", "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "uiModeListenerList", "Ljava/util/concurrent/CopyOnWriteArrayList;", "Landroidx/lifecycle/MutableLiveData;", "", "getUiModeListenerList", "()Ljava/util/concurrent/CopyOnWriteArrayList;", "ALDVR_T1JRelease"}, m532k = 1, m533mv = {1, 8, 0}, m535xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return AbsFullScreenDialog.TAG;
        }

        public final CopyOnWriteArrayList<MutableLiveData<Integer>> getUiModeListenerList() {
            return AbsFullScreenDialog.uiModeListenerList;
        }
    }

    public final Job getJob() {
        return this.job;
    }

    public final void setJob(Job job) {
        this.job = job;
    }

    static {
        DVRApplication.getInstance().registerReceiver(new BroadcastReceiver() { // from class: com.autolink.dvr.common.base.dialog.AbsFullScreenDialog$Companion$nightModeReceiver$1
            AbsFullScreenDialog$Companion$nightModeReceiver$1() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(intent, "intent");
                if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.CONFIGURATION_CHANGED")) {
                    int i = context.getResources().getConfiguration().uiMode & 48;
                    LogUtils2.logI(AbsFullScreenDialog.INSTANCE.getTAG(), "onReceive  currentNightMode:" + i);
                    Iterator<T> it = AbsFullScreenDialog.INSTANCE.getUiModeListenerList().iterator();
                    while (it.hasNext()) {
                        MutableLiveData mutableLiveData = (MutableLiveData) it.next();
                        LogUtils2.logI(AbsFullScreenDialog.INSTANCE.getTAG(), "onReceive uiModeListenerList.size:" + AbsFullScreenDialog.INSTANCE.getUiModeListenerList().size());
                        mutableLiveData.setValue(Integer.valueOf(i));
                    }
                }
            }
        }, new IntentFilter("android.intent.action.CONFIGURATION_CHANGED"));
    }

    @Override // com.autolink.dvr.common.base.dialog.IBaseComponents
    public ViewModelStoreOwner onStore() {
        return this;
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    public ViewModelStore getViewModelStore() {
        return new ViewModelStore();
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        return this.lifecycleRegistry;
    }

    @Override // com.autolink.dvr.common.base.dialog.IBaseComponents
    public VB getMVB() {
        VB vb = this.mVB;
        if (vb != null) {
            return vb;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mVB");
        return null;
    }

    @Override // com.autolink.dvr.common.base.dialog.IBaseComponents
    public void setMVB(VB vb) {
        Intrinsics.checkNotNullParameter(vb, "<set-?>");
        this.mVB = vb;
    }

    public final VM getViewModel() {
        return (VM) this.viewModel.getValue();
    }

    @Override // com.autolink.dvr.common.base.dialog.IBaseComponents
    public VM getMVM() {
        return this.mVM;
    }

    @Override // com.autolink.dvr.common.base.dialog.IBaseComponents
    public void setMVM(VM vm) {
        Intrinsics.checkNotNullParameter(vm, "<set-?>");
        this.mVM = vm;
    }

    private final MutableLiveData<Integer> getUiModeLiveData() {
        return (MutableLiveData) this.uiModeLiveData.getValue();
    }

    public AbsFullScreenDialog() {
        Job launch$default;
        CoroutineScope MainScope = CoroutineScopeKt.MainScope();
        this.mainScope = MainScope;
        Object systemService = DVRApplication.getInstance().getSystemService(WindowManager.class);
        Intrinsics.checkNotNullExpressionValue(systemService, "getInstance().getSystemS…indowManager::class.java)");
        this.windowManager = (WindowManager) systemService;
        this.viewModel = LazyKt.lazy(new Function0<VM>(this) { // from class: com.autolink.dvr.common.base.dialog.AbsFullScreenDialog$viewModel$2
            final /* synthetic */ AbsFullScreenDialog<VB, VM> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AbsFullScreenDialog$viewModel$2(AbsFullScreenDialog<VB, VM> this) {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final BaseViewModel invoke() {
                Type genericSuperclass = this.this$0.getClass().getGenericSuperclass();
                if (genericSuperclass instanceof ParameterizedType) {
                    Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[1];
                    Class<BaseViewModel> cls = type instanceof Class ? (Class) type : null;
                    if (cls == null) {
                        cls = BaseViewModel.class;
                    }
                    BaseViewModel newInstance = cls.newInstance();
                    Intrinsics.checkNotNull(newInstance, "null cannot be cast to non-null type VM of com.autolink.dvr.common.base.dialog.AbsFullScreenDialog");
                    return newInstance;
                }
                return new BaseViewModel();
            }
        });
        this.mVM = getViewModel();
        this.uiModeObserver = new Function1<Integer, Unit>(this) { // from class: com.autolink.dvr.common.base.dialog.AbsFullScreenDialog$uiModeObserver$1
            final /* synthetic */ AbsFullScreenDialog<VB, VM> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AbsFullScreenDialog$uiModeObserver$1(AbsFullScreenDialog<VB, VM> this) {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke(num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                BaseViewModel viewModel;
                LogUtils2.logI(AbsFullScreenDialog.INSTANCE.getTAG(), "uiModeObserver uiMode");
                viewModel = this.this$0.getViewModel();
                BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(viewModel), null, null, new C09051(this.this$0, null), 3, null);
                LogUtils2.logI(AbsFullScreenDialog.INSTANCE.getTAG(), "uiModeObserver end");
            }

            /* compiled from: AbsFullScreenDialog.kt */
            @Metadata(m530d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\b\b\u0001\u0010\u0004*\u00020\u0005*\u00020\u0006H\u008a@"}, m531d2 = {"<anonymous>", "", "VB", "Landroidx/databinding/ViewDataBinding;", "VM", "Lcom/autolink/dvr/common/base/hmi/BaseViewModel;", "Lkotlinx/coroutines/CoroutineScope;"}, m532k = 3, m533mv = {1, 8, 0}, m535xi = 48)
            @DebugMetadata(m546c = "com.autolink.dvr.common.base.dialog.AbsFullScreenDialog$uiModeObserver$1$1", m547f = "AbsFullScreenDialog.kt", m548i = {}, m549l = {}, m550m = "invokeSuspend", m551n = {}, m552s = {})
            /* renamed from: com.autolink.dvr.common.base.dialog.AbsFullScreenDialog$uiModeObserver$1$1 */
            static final class C09051 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                private /* synthetic */ Object L$0;
                int label;
                final /* synthetic */ AbsFullScreenDialog<VB, VM> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C09051(AbsFullScreenDialog<VB, VM> absFullScreenDialog, Continuation<? super C09051> continuation) {
                    super(2, continuation);
                    this.this$0 = absFullScreenDialog;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                    C09051 c09051 = new C09051(this.this$0, continuation);
                    c09051.L$0 = obj;
                    return c09051;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((C09051) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                /* compiled from: AbsFullScreenDialog.kt */
                @Metadata(m530d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\b\b\u0001\u0010\u0004*\u00020\u0005*\u00020\u0006H\u008a@"}, m531d2 = {"<anonymous>", "", "VB", "Landroidx/databinding/ViewDataBinding;", "VM", "Lcom/autolink/dvr/common/base/hmi/BaseViewModel;", "Lkotlinx/coroutines/CoroutineScope;"}, m532k = 3, m533mv = {1, 8, 0}, m535xi = 48)
                @DebugMetadata(m546c = "com.autolink.dvr.common.base.dialog.AbsFullScreenDialog$uiModeObserver$1$1$1", m547f = "AbsFullScreenDialog.kt", m548i = {}, m549l = {}, m550m = "invokeSuspend", m551n = {}, m552s = {})
                /* renamed from: com.autolink.dvr.common.base.dialog.AbsFullScreenDialog$uiModeObserver$1$1$1 */
                static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                    int label;
                    final /* synthetic */ AbsFullScreenDialog<VB, VM> this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    AnonymousClass1(AbsFullScreenDialog<VB, VM> absFullScreenDialog, Continuation<? super AnonymousClass1> continuation) {
                        super(2, continuation);
                        this.this$0 = absFullScreenDialog;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                        return new AnonymousClass1(this.this$0, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                        return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        WindowManager windowManager;
                        IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        if (this.label == 0) {
                            ResultKt.throwOnFailure(obj);
                            windowManager = ((AbsFullScreenDialog) this.this$0).windowManager;
                            windowManager.removeView(this.this$0.getMVB().getRoot());
                            this.this$0.onStop();
                            this.this$0.onDestroyView();
                            this.this$0.initWindow();
                            return Unit.INSTANCE;
                        }
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    if (this.label == 0) {
                        ResultKt.throwOnFailure(obj);
                        BuildersKt__Builders_commonKt.launch$default((CoroutineScope) this.L$0, Dispatchers.getMain(), null, new AnonymousClass1(this.this$0, null), 2, null);
                        return Unit.INSTANCE;
                    }
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        };
        this.uiModeLiveData = LazyKt.lazy(new AbsFullScreenDialog$uiModeLiveData$2(this));
        LogUtils2.logI(TAG, "init uiModeLiveData:" + getUiModeLiveData());
        launch$default = BuildersKt__Builders_commonKt.launch$default(MainScope, null, null, new C09041(this, null), 3, null);
        this.job = launch$default;
    }

    /* compiled from: AbsFullScreenDialog.kt */
    @Metadata(m530d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\b\b\u0001\u0010\u0004*\u00020\u0005*\u00020\u0006H\u008a@"}, m531d2 = {"<anonymous>", "", "VB", "Landroidx/databinding/ViewDataBinding;", "VM", "Lcom/autolink/dvr/common/base/hmi/BaseViewModel;", "Lkotlinx/coroutines/CoroutineScope;"}, m532k = 3, m533mv = {1, 8, 0}, m535xi = 48)
    @DebugMetadata(m546c = "com.autolink.dvr.common.base.dialog.AbsFullScreenDialog$1", m547f = "AbsFullScreenDialog.kt", m548i = {}, m549l = {}, m550m = "invokeSuspend", m551n = {}, m552s = {})
    /* renamed from: com.autolink.dvr.common.base.dialog.AbsFullScreenDialog$1 */
    static final class C09041 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;
        final /* synthetic */ AbsFullScreenDialog<VB, VM> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C09041(AbsFullScreenDialog<VB, VM> absFullScreenDialog, Continuation<? super C09041> continuation) {
            super(2, continuation);
            this.this$0 = absFullScreenDialog;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C09041(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C09041) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                this.this$0.initWindow();
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    private final VB createVB() {
        VB vb = (VB) DataBindingUtil.inflate(LayoutInflater.from(DVRApplication.getInstance()), onLayoutId(), null, false);
        Intrinsics.checkNotNullExpressionValue(vb, "inflate(\n            Lay…(), null, false\n        )");
        return vb;
    }

    public final void initWindow() {
        setMVB(createVB());
        LogUtils2.logI(TAG, "nVB.root " + getMVB().getRoot());
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2038, 8650792, -3);
        layoutParams.setTitle("OverlayDialog");
        if (Build.VERSION.SDK_INT >= 30) {
            layoutParams.setFitInsetsTypes(0);
        }
        layoutParams.windowAnimations = 0;
        layoutParams.gravity = 17;
        if (this.isRemoved) {
            return;
        }
        this.windowManager.addView(getMVB().getRoot(), layoutParams);
        onCreate();
        onStart();
        onResume();
    }

    public void onCreate() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        initView();
    }

    public void onStart() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    public void onResume() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    public void onStop() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    public void onDestroy() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        CoroutineScopeKt.cancel$default(ViewModelKt.getViewModelScope(getMVM()), null, 1, null);
    }

    public final void finish() {
        boolean z = this.mVB != null;
        LogUtils2.logI(TAG, "finish isVbInit:" + z);
        Job job = this.job;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        CoroutineScopeKt.cancel$default(this.mainScope, null, 1, null);
        if (z) {
            if (getMVB().getRoot().isAttachedToWindow()) {
                this.windowManager.removeView(getMVB().getRoot());
                this.isRemoved = true;
            } else if (!this.isRemoved) {
                try {
                    this.windowManager.removeViewImmediate(getMVB().getRoot());
                    this.isRemoved = true;
                } catch (Exception unused) {
                }
            }
        }
        getUiModeLiveData().removeObserver(new Observer() { // from class: com.autolink.dvr.common.base.dialog.AbsFullScreenDialog$$ExternalSyntheticLambda0
            public /* synthetic */ AbsFullScreenDialog$$ExternalSyntheticLambda0() {
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AbsFullScreenDialog.finish$lambda$0(Function1.this, obj);
            }
        });
        uiModeListenerList.remove(getUiModeLiveData());
        onStop();
        onDestroyView();
        onDestroy();
    }

    public static final void finish$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }
}
