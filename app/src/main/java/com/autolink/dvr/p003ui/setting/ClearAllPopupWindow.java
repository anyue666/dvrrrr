package com.autolink.dvr.p003ui.setting;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.common.media.utils.FileUtils;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.USBUtil;
import java.io.File;

/* loaded from: classes.dex */
public class ClearAllPopupWindow extends PopupWindow implements View.OnClickListener {
    private Button btn_cancel;
    private Button btn_confirm;
    private Context mContext;
    private View rootView;
    private final String TAG = "DVR_ClearAllPopupWindow";
    CountDownTimer timer = new CountDownTimer(5000, 10) { // from class: com.autolink.dvr.ui.setting.ClearAllPopupWindow.1
        @Override // android.os.CountDownTimer
        public void onTick(long j) {
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            if (ClearAllPopupWindow.this.isShowing()) {
                ClearAllPopupWindow.this.dismiss();
            }
        }
    };

    public ClearAllPopupWindow(Context context) {
        LogUtils2.logI("DVR_ClearAllPopupWindow", "ClearAllPopupWindow ");
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(C0903R.layout.clear_all_pop, (ViewGroup) null);
        this.rootView = inflate;
        setContentView(inflate);
        this.btn_cancel = (Button) this.rootView.findViewById(C0903R.id.clear_all_pop_btn_cancel);
        this.btn_confirm = (Button) this.rootView.findViewById(C0903R.id.clear_all_pop_btn_confirm);
        this.btn_cancel.setOnClickListener(this);
        this.btn_confirm.setOnClickListener(this);
        setHeight(-2);
        setWidth(880);
        setOutsideTouchable(false);
        setFocusable(false);
        setAnimationStyle(C0903R.style.SettingPopupAnimStyle);
        setBackgroundDrawable(new ColorDrawable(0));
        this.timer.start();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        LogUtils2.logI("DVR_ClearAllPopupWindow", "onClick view = " + view.getId());
        switch (view.getId()) {
            case C0903R.id.clear_all_pop_btn_cancel /* 2131230908 */:
                this.timer.cancel();
                dismiss();
                break;
            case C0903R.id.clear_all_pop_btn_confirm /* 2131230909 */:
                this.timer.cancel();
                dismiss();
                clearAll();
                break;
        }
    }

    private void clearAll() {
        LogUtils2.logI("DVR_ClearAllPopupWindow", "clearAll");
        USBUtil.deleteAllVideo(USBUtil.USBPath + File.separator + "event");
        USBUtil.deleteAllVideo(USBUtil.USBPath + File.separator + USBUtil.normal);
        USBUtil.deleteAllVideo(FileUtils.getExternalPath(this.mContext) + File.separator + "event");
        USBUtil.deleteAllVideo(FileUtils.getExternalPath(this.mContext) + File.separator + USBUtil.normal);
        Toast.makeText(DVRApplication.getInstance(), C0903R.string.settings_restore_default_settings_text, 0).show();
    }
}
