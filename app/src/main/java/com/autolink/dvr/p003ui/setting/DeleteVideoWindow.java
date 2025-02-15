package com.autolink.dvr.p003ui.setting;

import android.content.Context;
import android.view.View;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.common.base.dialog.AbsFullScreenDialog;
import com.autolink.dvr.databinding.DialogDeleteLayoutBinding;
import com.autolink.dvr.viewmodel.DeleteVideoViewModel;

/* loaded from: classes.dex */
public class DeleteVideoWindow extends AbsFullScreenDialog<DialogDeleteLayoutBinding, DeleteVideoViewModel> {
    private Context mContext;
    private int mNum = 0;
    private ClickListener onClickListener;

    public interface ClickListener {
        void onConfirm();
    }

    public DeleteVideoWindow(Context context) {
        this.mContext = context;
    }

    @Override // com.autolink.dvr.common.base.dialog.IBaseComponents
    public void initView() {
        setViewCopyWriting();
        ((DialogDeleteLayoutBinding) this.mVB).cancel.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.setting.DeleteVideoWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DeleteVideoWindow.this.finish();
            }
        });
        ((DialogDeleteLayoutBinding) this.mVB).confirm.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.setting.DeleteVideoWindow.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DeleteVideoWindow.this.onClickListener != null) {
                    DeleteVideoWindow.this.onClickListener.onConfirm();
                }
            }
        });
    }

    private void setViewCopyWriting() {
        ((DialogDeleteLayoutBinding) this.mVB).info.setText(String.format(this.mContext.getString(C0903R.string.delete_info), Integer.valueOf(this.mNum)));
    }

    public void setDeleteNumber(int i) {
        this.mNum = i;
    }

    public void setUpdateNumber(int i) {
        this.mNum = i;
        setViewCopyWriting();
    }

    public void setOnClickListener(ClickListener clickListener) {
        this.onClickListener = clickListener;
    }
}
