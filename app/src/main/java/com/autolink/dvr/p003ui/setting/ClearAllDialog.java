package com.autolink.dvr.p003ui.setting;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.common.base.dialog.AbsFullScreenDialog;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.databinding.ClearAllPopBinding;
import com.autolink.dvr.viewmodel.ClearAllDialogViewModel;

/* loaded from: classes.dex */
public class ClearAllDialog extends AbsFullScreenDialog<ClearAllPopBinding, ClearAllDialogViewModel> {
    private Context mContext;
    private ClearAllListener mListener;
    private final String TAG = "DVR_ClearAllDialog";
    CountDownTimer timer = new CountDownTimer(5000, 10) { // from class: com.autolink.dvr.ui.setting.ClearAllDialog.3
        @Override // android.os.CountDownTimer
        public void onTick(long j) {
        }

        CountDownTimerC09723(long j, long j2) {
            super(j, j2);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            ClearAllDialog.this.finish();
        }
    };

    public interface ClearAllListener {
        void onClearAll();
    }

    @Override // com.autolink.dvr.common.base.dialog.IBaseComponents
    public void initView() {
        this.mContext = DVRApplication.getInstance();
        ((ClearAllPopBinding) this.mVB).clearAllPopBtnConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.setting.ClearAllDialog.1
            ViewOnClickListenerC09701() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LogUtils2.logI("DVR_ClearAllDialog", "onClick Confirm");
                ClearAllDialog.this.timer.cancel();
                ClearAllDialog.this.mListener.onClearAll();
                ClearAllDialog.this.finish();
            }
        });
        ((ClearAllPopBinding) this.mVB).clearAllPopBtnCancel.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.setting.ClearAllDialog.2
            ViewOnClickListenerC09712() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LogUtils2.logI("DVR_ClearAllDialog", "onClick Cancel");
                ClearAllDialog.this.timer.cancel();
                ClearAllDialog.this.finish();
            }
        });
        this.timer.start();
    }

    /* renamed from: com.autolink.dvr.ui.setting.ClearAllDialog$1 */
    class ViewOnClickListenerC09701 implements View.OnClickListener {
        ViewOnClickListenerC09701() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LogUtils2.logI("DVR_ClearAllDialog", "onClick Confirm");
            ClearAllDialog.this.timer.cancel();
            ClearAllDialog.this.mListener.onClearAll();
            ClearAllDialog.this.finish();
        }
    }

    /* renamed from: com.autolink.dvr.ui.setting.ClearAllDialog$2 */
    class ViewOnClickListenerC09712 implements View.OnClickListener {
        ViewOnClickListenerC09712() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LogUtils2.logI("DVR_ClearAllDialog", "onClick Cancel");
            ClearAllDialog.this.timer.cancel();
            ClearAllDialog.this.finish();
        }
    }

    /* renamed from: com.autolink.dvr.ui.setting.ClearAllDialog$3 */
    class CountDownTimerC09723 extends CountDownTimer {
        @Override // android.os.CountDownTimer
        public void onTick(long j) {
        }

        CountDownTimerC09723(long j, long j2) {
            super(j, j2);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            ClearAllDialog.this.finish();
        }
    }

    public void setClearAllListener(ClearAllListener clearAllListener) {
        this.mListener = clearAllListener;
    }
}
