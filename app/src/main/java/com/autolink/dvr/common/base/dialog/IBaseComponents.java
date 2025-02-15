package com.autolink.dvr.common.base.dialog;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.common.base.hmi.BaseViewModel;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: IBaseComponents.kt */
@Metadata(m530d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u00020\u0005J\b\u0010\u0010\u001a\u00020\u0011H&J\b\u0010\u0012\u001a\u00020\u0011H\u0016J\b\u0010\u0013\u001a\u00020\u0011H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0017J\b\u0010\u0016\u001a\u00020\u0017H&R\u0018\u0010\u0006\u001a\u00028\u0000X¦\u000e¢\u0006\f\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\u00028\u0001X¦\u000e¢\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0018"}, m531d2 = {"Lcom/autolink/dvr/common/base/dialog/IBaseComponents;", "VB", "Landroidx/databinding/ViewDataBinding;", "VM", "Lcom/autolink/dvr/common/base/hmi/BaseViewModel;", "", "mVB", "getMVB", "()Landroidx/databinding/ViewDataBinding;", "setMVB", "(Landroidx/databinding/ViewDataBinding;)V", "mVM", "getMVM", "()Lcom/autolink/dvr/common/base/hmi/BaseViewModel;", "setMVM", "(Lcom/autolink/dvr/common/base/hmi/BaseViewModel;)V", "initView", "", "initViewBeforeSuperMethod", "initViewModel", "onLayoutId", "", "onStore", "Landroidx/lifecycle/ViewModelStoreOwner;", "ALDVR_T1JRelease"}, m532k = 1, m533mv = {1, 8, 0}, m535xi = 48)
/* loaded from: classes.dex */
public interface IBaseComponents<VB extends ViewDataBinding, VM extends BaseViewModel> {
    VB getMVB();

    VM getMVM();

    void initView();

    void initViewBeforeSuperMethod();

    void initViewModel();

    int onLayoutId();

    ViewModelStoreOwner onStore();

    void setMVB(VB vb);

    void setMVM(VM vm);

    /* compiled from: IBaseComponents.kt */
    @Metadata(m532k = 3, m533mv = {1, 8, 0}, m535xi = 48)
    public static final class DefaultImpls {
        public static <VB extends ViewDataBinding, VM extends BaseViewModel> void initViewBeforeSuperMethod(IBaseComponents<VB, VM> iBaseComponents) {
        }

        public static <VB extends ViewDataBinding, VM extends BaseViewModel> int onLayoutId(IBaseComponents<VB, VM> iBaseComponents) {
            Type genericSuperclass = iBaseComponents.getClass().getGenericSuperclass();
            if (!(genericSuperclass instanceof ParameterizedType)) {
                return -1;
            }
            int i = 0;
            String obj = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0].toString();
            String substring = obj.substring(StringsKt.lastIndexOf$default((CharSequence) obj, ".", 0, false, 6, (Object) null) + 1);
            Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
            String substring2 = substring.substring(0, StringsKt.lastIndexOf$default((CharSequence) substring, "Binding", 0, false, 6, (Object) null));
            Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String…ing(startIndex, endIndex)");
            StringBuilder sb = new StringBuilder();
            char[] charArray = substring2.toCharArray();
            Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
            int length = charArray.length;
            int i2 = 0;
            while (i < length) {
                char c = charArray[i];
                int i3 = i2 + 1;
                if (Character.isUpperCase(c) && i2 != 0) {
                    sb.append("_");
                }
                String valueOf = String.valueOf(c);
                Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
                String lowerCase = valueOf.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                sb.append(lowerCase);
                i++;
                i2 = i3;
            }
            String sb2 = sb.toString();
            Intrinsics.checkNotNullExpressionValue(sb2, "binding.lastIndexOf(\".\")….toString()\n            }");
            DVRApplication dVRApplication = DVRApplication.getInstance();
            return dVRApplication.getResources().getIdentifier(sb2, "layout", dVRApplication.getPackageName());
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static <VB extends ViewDataBinding, VM extends BaseViewModel> void initViewModel(IBaseComponents<VB, VM> iBaseComponents) {
            Type genericSuperclass = iBaseComponents.getClass().getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[1];
                Class<BaseViewModel> cls = type instanceof Class ? (Class) type : null;
                if (cls == null) {
                    cls = BaseViewModel.class;
                }
                ViewModel viewModel = new ViewModelProvider(iBaseComponents.onStore()).get(cls);
                Intrinsics.checkNotNull(viewModel, "null cannot be cast to non-null type VM of com.autolink.dvr.common.base.dialog.IBaseComponents");
                iBaseComponents.setMVM((BaseViewModel) viewModel);
            }
        }
    }
}
