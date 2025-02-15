package com.autolink.dvr.p003ui.setting;

import android.content.Context;
import android.view.View;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.common.base.dialog.AbsFullScreenDialog;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.databinding.FormatUsbPopBinding;
import com.autolink.dvr.viewmodel.FormatUSBDialogViewModel;

/* loaded from: classes.dex */
public class FormatUSBDialog extends AbsFullScreenDialog<FormatUsbPopBinding, FormatUSBDialogViewModel> {
    private final String TAG = "DVR_FormatUSBDialog";
    private Context mContext;
    private USBFormatListener mListener;

    public interface USBFormatListener {
        void onUSBEject();

        void onUSBFromat();
    }

    @Override // com.autolink.dvr.common.base.dialog.IBaseComponents
    public void initView() {
        this.mContext = DVRApplication.getInstance();
        ((FormatUsbPopBinding) this.mVB).formatPopBtnConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.setting.FormatUSBDialog.1
            ViewOnClickListenerC09761() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LogUtils2.logI("DVR_FormatUSBDialog", "onClick Confirm");
                FormatUSBDialog.this.mListener.onUSBFromat();
                FormatUSBDialog.this.finish();
            }
        });
        ((FormatUsbPopBinding) this.mVB).formatPopBtnUnmount.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.setting.FormatUSBDialog.2
            ViewOnClickListenerC09772() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LogUtils2.logI("DVR_FormatUSBDialog", "onClick unmount");
                FormatUSBDialog.this.mListener.onUSBEject();
                FormatUSBDialog.this.finish();
            }
        });
    }

    /* renamed from: com.autolink.dvr.ui.setting.FormatUSBDialog$1 */
    class ViewOnClickListenerC09761 implements View.OnClickListener {
        ViewOnClickListenerC09761() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LogUtils2.logI("DVR_FormatUSBDialog", "onClick Confirm");
            FormatUSBDialog.this.mListener.onUSBFromat();
            FormatUSBDialog.this.finish();
        }
    }

    /* renamed from: com.autolink.dvr.ui.setting.FormatUSBDialog$2 */
    class ViewOnClickListenerC09772 implements View.OnClickListener {
        ViewOnClickListenerC09772() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LogUtils2.logI("DVR_FormatUSBDialog", "onClick unmount");
            FormatUSBDialog.this.mListener.onUSBEject();
            FormatUSBDialog.this.finish();
        }
    }

    public void setUSBFormatListener(USBFormatListener uSBFormatListener) {
        this.mListener = uSBFormatListener;
    }
}
