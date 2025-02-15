package com.autolink.dvr.p003ui.setting;

import android.content.Context;
import android.view.View;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.common.base.dialog.AbsFullScreenDialog;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.databinding.CheckUsbRebootBinding;
import com.autolink.dvr.viewmodel.CheckUSBRebootDialogViewModel;

/* loaded from: classes.dex */
public class CheckUSBRebootDialog extends AbsFullScreenDialog<CheckUsbRebootBinding, CheckUSBRebootDialogViewModel> {
    private final String TAG = "DVR_CheckUSBRebootDialog";
    private Context mContext;
    private USBRebootListener mListener;

    public interface USBRebootListener {
        void onUSBReboot();
    }

    @Override // com.autolink.dvr.common.base.dialog.IBaseComponents
    public void initView() {
        this.mContext = DVRApplication.getInstance();
        ((CheckUsbRebootBinding) this.mVB).checkUsbRebootBtnConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.setting.CheckUSBRebootDialog.1
            ViewOnClickListenerC09691() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LogUtils2.logI("DVR_CheckUSBRebootDialog", "onClick Confirm");
                CheckUSBRebootDialog.this.mListener.onUSBReboot();
                CheckUSBRebootDialog.this.finish();
            }
        });
    }

    /* renamed from: com.autolink.dvr.ui.setting.CheckUSBRebootDialog$1 */
    class ViewOnClickListenerC09691 implements View.OnClickListener {
        ViewOnClickListenerC09691() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LogUtils2.logI("DVR_CheckUSBRebootDialog", "onClick Confirm");
            CheckUSBRebootDialog.this.mListener.onUSBReboot();
            CheckUSBRebootDialog.this.finish();
        }
    }

    public void setUSBFormatListener(USBRebootListener uSBRebootListener) {
        this.mListener = uSBRebootListener;
    }
}
