package com.autolink.dvr.p003ui.setting;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.autolink.buryservice.BuryServiceManager;
import com.autolink.buryservice.bury.consts.BuryEventConst;
import com.autolink.buryservice.bury.consts.BuryKeyConst;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.common.base.dialog.AbsFullScreenDialog;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.SaveMsg;
import com.autolink.dvr.common.utils.USBUtil;
import com.autolink.dvr.databinding.SettingLayoutBinding;
import com.autolink.dvr.p003ui.setting.ClearAllDialog;
import com.autolink.dvr.viewmodel.SettingPopupViewModel;

/* loaded from: classes.dex */
public class SettingPopupWindow extends AbsFullScreenDialog<SettingLayoutBinding, SettingPopupViewModel> implements View.OnClickListener, ClearAllDialog.ClearAllListener {
    private final String TAG = "DVR_SettingPopupWindow";
    private OnWindowItemClickListener listener;
    private BuryServiceManager mBuryServiceManager;
    private Context mContext;

    public interface OnWindowItemClickListener {
        void onClearAll();

        void onClickClearAll(ClearAllDialog clearAllDialog);

        void onClickCloseRec();

        void onClickEjectUSB();

        void onClickOpenRec();

        void onClickRestore();
    }

    @Override // com.autolink.dvr.common.base.dialog.IBaseComponents
    public void initView() {
        LogUtils2.logI("DVR_SettingPopupWindow", "SettingPopupWindow ");
        this.mContext = DVRApplication.getInstance();
        this.mBuryServiceManager = BuryServiceManager.getInstance();
        ((SettingLayoutBinding) this.mVB).activitySettingCloseIv.setOnClickListener(this);
        ((SettingLayoutBinding) this.mVB).activitySettingBackground.setOnClickListener(this);
        ((SettingLayoutBinding) this.mVB).activitySettingRecBtn1Close.setOnClickListener(this);
        ((SettingLayoutBinding) this.mVB).activitySettingRecBtn2Min1.setOnClickListener(this);
        ((SettingLayoutBinding) this.mVB).activitySettingRecBtn3Min2.setOnClickListener(this);
        ((SettingLayoutBinding) this.mVB).activitySettingRecBtn4Min3.setOnClickListener(this);
        ((SettingLayoutBinding) this.mVB).activitySettingSetBtn1ClearAll.setOnClickListener(this);
        ((SettingLayoutBinding) this.mVB).activitySettingSetBtn2EjectUsb.setOnClickListener(this);
        ((SettingLayoutBinding) this.mVB).activitySettingSetBtn3RestoreDefault.setOnClickListener(this);
        setRecBg(SaveMsg.getRecMsg(this.mContext));
        setStoreSpace();
    }

    private void clearAllRecBg() {
        ((SettingLayoutBinding) this.mVB).activitySettingRecBtn1Close.setBackground(null);
        ((SettingLayoutBinding) this.mVB).activitySettingRecBtn1Close.setTextColor(ContextCompat.getColor(this.mContext, C0903R.color.setting_content_text));
        ((SettingLayoutBinding) this.mVB).activitySettingRecBtn2Min1.setBackground(null);
        ((SettingLayoutBinding) this.mVB).activitySettingRecBtn2Min1.setTextColor(ContextCompat.getColor(this.mContext, C0903R.color.setting_content_text));
        ((SettingLayoutBinding) this.mVB).activitySettingRecBtn3Min2.setBackground(null);
        ((SettingLayoutBinding) this.mVB).activitySettingRecBtn3Min2.setTextColor(ContextCompat.getColor(this.mContext, C0903R.color.setting_content_text));
        ((SettingLayoutBinding) this.mVB).activitySettingRecBtn4Min3.setBackground(null);
        ((SettingLayoutBinding) this.mVB).activitySettingRecBtn4Min3.setTextColor(ContextCompat.getColor(this.mContext, C0903R.color.setting_content_text));
    }

    private void setRecBg(int i) {
        if (i == 0) {
            ((SettingLayoutBinding) this.mVB).activitySettingRecBtn1Close.setBackgroundResource(C0903R.drawable.setting_btn_bg_blue);
            ((SettingLayoutBinding) this.mVB).activitySettingRecBtn1Close.setTextColor(ContextCompat.getColor(this.mContext, C0903R.color.setting_content_selected_text));
            return;
        }
        if (i == 1) {
            ((SettingLayoutBinding) this.mVB).activitySettingRecBtn2Min1.setBackgroundResource(C0903R.drawable.setting_btn_bg_blue);
            ((SettingLayoutBinding) this.mVB).activitySettingRecBtn2Min1.setTextColor(ContextCompat.getColor(this.mContext, C0903R.color.setting_content_selected_text));
        } else if (i == 3) {
            ((SettingLayoutBinding) this.mVB).activitySettingRecBtn3Min2.setBackgroundResource(C0903R.drawable.setting_btn_bg_blue);
            ((SettingLayoutBinding) this.mVB).activitySettingRecBtn3Min2.setTextColor(ContextCompat.getColor(this.mContext, C0903R.color.setting_content_selected_text));
        } else if (i == 5) {
            ((SettingLayoutBinding) this.mVB).activitySettingRecBtn4Min3.setBackgroundResource(C0903R.drawable.setting_btn_bg_blue);
            ((SettingLayoutBinding) this.mVB).activitySettingRecBtn4Min3.setTextColor(ContextCompat.getColor(this.mContext, C0903R.color.setting_content_selected_text));
        }
    }

    public void setStoreSpace() {
        LogUtils2.logI("DVR_SettingPopupWindow", "setStoreSpace");
        if (SaveMsg.isMountedUSB && !USBUtil.isNeedFormatUSB) {
            ((SettingLayoutBinding) this.mVB).activitySettingStoreProgressBar.setProgress(USBUtil.percentage);
            ((SettingLayoutBinding) this.mVB).activitySettingStoreSpaceTv.setText(USBUtil.percentage + "% 可用");
            this.mBuryServiceManager.panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_Option, BuryKeyConst.CH_BI_Key_storage_space, USBUtil.percentage);
            return;
        }
        ((SettingLayoutBinding) this.mVB).activitySettingStoreProgressBar.setProgress(0);
        ((SettingLayoutBinding) this.mVB).activitySettingStoreSpaceTv.setText("不可用");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        LogUtils2.logI("DVR_SettingPopupWindow", "onClick view = " + view.getId());
        switch (view.getId()) {
            case C0903R.id.activity_setting_background /* 2131230813 */:
            case C0903R.id.activity_setting_close_iv /* 2131230814 */:
                finish();
                break;
            case C0903R.id.activity_setting_rec_btn1_close /* 2131230817 */:
                clearAllRecBg();
                setRecBg(0);
                SaveMsg.setRecMsg(this.mContext, 0);
                this.listener.onClickCloseRec();
                break;
            case C0903R.id.activity_setting_rec_btn2_min1 /* 2131230818 */:
                clearAllRecBg();
                setRecBg(1);
                SaveMsg.setRecMsg(this.mContext, 1);
                this.listener.onClickOpenRec();
                this.mBuryServiceManager.panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_Option, BuryKeyConst.CH_BI_Key_recordtime, 1);
                break;
            case C0903R.id.activity_setting_rec_btn3_min2 /* 2131230819 */:
                clearAllRecBg();
                setRecBg(3);
                SaveMsg.setRecMsg(this.mContext, 3);
                this.listener.onClickOpenRec();
                this.mBuryServiceManager.panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_Option, BuryKeyConst.CH_BI_Key_recordtime, 3);
                break;
            case C0903R.id.activity_setting_rec_btn4_min3 /* 2131230820 */:
                clearAllRecBg();
                setRecBg(5);
                SaveMsg.setRecMsg(this.mContext, 5);
                this.listener.onClickOpenRec();
                this.mBuryServiceManager.panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_Option, BuryKeyConst.CH_BI_Key_recordtime, 5);
                break;
            case C0903R.id.activity_setting_set_btn1_clear_all /* 2131230824 */:
                finish();
                clearAllCheck();
                break;
            case C0903R.id.activity_setting_set_btn2_eject_usb /* 2131230825 */:
                LogUtils2.logI("DVR_SettingPopupWindow", "onClickEjectUSB, isMountedUSB = " + SaveMsg.isMountedUSB);
                finish();
                if (SaveMsg.isMountedUSB && !USBUtil.isNeedFormatUSB) {
                    this.listener.onClickEjectUSB();
                    break;
                } else {
                    Toast.makeText(DVRApplication.getInstance(), C0903R.string.settings_eject_usb_text2, 0).show();
                    break;
                }
            case C0903R.id.activity_setting_set_btn3_restore_default /* 2131230826 */:
                finish();
                restoreDefault();
                break;
        }
    }

    private void clearAllCheck() {
        if (SaveMsg.isMountedUSB && !USBUtil.isNeedFormatUSB) {
            ClearAllDialog clearAllDialog = new ClearAllDialog();
            clearAllDialog.setClearAllListener(this);
            this.listener.onClickClearAll(clearAllDialog);
            return;
        }
        Toast.makeText(DVRApplication.getInstance(), C0903R.string.settings_eject_usb_text2, 0).show();
    }

    private void restoreDefault() {
        SaveMsg.setRecMsg(this.mContext, 3);
        this.listener.onClickOpenRec();
        clearAllRecBg();
        setRecBg(3);
        SaveMsg.setSensitivityMsg(this.mContext, 1);
        Toast.makeText(DVRApplication.getInstance(), C0903R.string.settings_restore_default_settings_text, 0).show();
    }

    public void setOnWindowItemClickListener(OnWindowItemClickListener onWindowItemClickListener) {
        this.listener = onWindowItemClickListener;
    }

    @Override // com.autolink.dvr.ui.setting.ClearAllDialog.ClearAllListener
    public void onClearAll() {
        this.listener.onClearAll();
    }
}
