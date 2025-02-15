package com.autolink.dvr.p003ui.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.USBUtil;

/* loaded from: classes.dex */
public class FormatUSBWindow extends PopupWindow implements View.OnClickListener {
    private final String TAG = "DVR_FormatUSBWindow";
    private Button btn_cancel;
    private Button btn_confirm;
    private Context mContext;
    private View rootView;

    public FormatUSBWindow(Context context) {
        LogUtils2.logI("DVR_FormatUSBWindow", "FormatUSBWindow ");
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(C0903R.layout.format_usb_pop, (ViewGroup) null);
        this.rootView = inflate;
        setContentView(inflate);
        this.btn_cancel = (Button) this.rootView.findViewById(C0903R.id.format_pop_btn_unmount);
        this.btn_confirm = (Button) this.rootView.findViewById(C0903R.id.format_pop_btn_confirm);
        this.btn_cancel.setOnClickListener(this);
        this.btn_confirm.setOnClickListener(this);
        setHeight(-2);
        setWidth(-2);
        setOutsideTouchable(false);
        setFocusable(false);
        setAnimationStyle(C0903R.style.SettingPopupAnimStyle);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        LogUtils2.logI("DVR_FormatUSBWindow", "onClick view = " + view.getId());
        switch (view.getId()) {
            case C0903R.id.format_pop_btn_confirm /* 2131231011 */:
                dismiss();
                formatUSB();
                break;
            case C0903R.id.format_pop_btn_unmount /* 2131231012 */:
                dismiss();
                Toast.makeText(DVRApplication.getInstance(), C0903R.string.usb_format_no, 0).show();
                break;
        }
    }

    private void formatUSB() {
        USBUtil.formatUSB(this.mContext);
        Toast.makeText(DVRApplication.getInstance(), C0903R.string.usb_format_yes, 0).show();
    }
}
