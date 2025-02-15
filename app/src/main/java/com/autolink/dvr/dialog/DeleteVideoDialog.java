package com.autolink.dvr.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.databinding.DialogDeleteLayoutBinding;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class DeleteVideoDialog extends DVRBaseDialog<DialogDeleteLayoutBinding> implements DialogInterface.OnDismissListener {
    private int mNum;
    private ClickListener onClickListener;
    ShowTimeHandler showTimeHandler;
    private long timeData;

    public interface ClickListener {
        void onConfirm();
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
    }

    @Override // com.autolink.dvr.dialog.DVRBaseDialog
    public float setDimAmount() {
        return 0.8f;
    }

    @Override // com.autolink.dvr.dialog.DVRBaseDialog
    public int setLayout() {
        return C0903R.layout.dialog_delete_layout;
    }

    @Override // com.autolink.dvr.dialog.DVRBaseDialog
    public float setSize() {
        return 0.6f;
    }

    public DeleteVideoDialog(Context context) {
        super(context, C0903R.style.dialogTransparent);
        this.timeData = 0L;
        this.mNum = 0;
        this.showTimeHandler = new ShowTimeHandler(this);
    }

    @Override // com.autolink.dvr.dialog.DVRBaseDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.autolink.dvr.dialog.DeleteVideoDialog.1
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                DeleteVideoDialog.this.showTimeHandler.removeMessages(1);
                DeleteVideoDialog.this.showTimeHandler.removeCallbacksAndMessages(null);
                DeleteVideoDialog.this.timeData = 0L;
            }
        });
        initView();
    }

    public void setDeleteNumber(int i) {
        this.mNum = i;
    }

    public void setUpdateNumber(int i) {
        this.mNum = i;
        setViewCopyWriting();
    }

    private void setViewCopyWriting() {
        ((DialogDeleteLayoutBinding) this.mDataBinding).info.setText(String.format(getContext().getString(C0903R.string.delete_info), Integer.valueOf(this.mNum)));
    }

    private void initView() {
        setViewCopyWriting();
        ((DialogDeleteLayoutBinding) this.mDataBinding).cancel.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.dialog.DeleteVideoDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DeleteVideoDialog.this.dismiss();
            }
        });
        ((DialogDeleteLayoutBinding) this.mDataBinding).confirm.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.dialog.DeleteVideoDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DeleteVideoDialog.this.onClickListener != null) {
                    DeleteVideoDialog.this.onClickListener.onConfirm();
                }
            }
        });
    }

    public void setOnClickListener(ClickListener clickListener) {
        this.onClickListener = clickListener;
    }

    private static class ShowTimeHandler extends Handler {
        private WeakReference<DeleteVideoDialog> reference;

        ShowTimeHandler(DeleteVideoDialog deleteVideoDialog) {
            this.reference = new WeakReference<>(deleteVideoDialog);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
        }
    }
}
