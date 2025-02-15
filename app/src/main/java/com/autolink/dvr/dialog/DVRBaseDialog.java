package com.autolink.dvr.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/* loaded from: classes.dex */
public abstract class DVRBaseDialog<V extends ViewDataBinding> extends Dialog {
    private static final String LOTTERY_PS = "Lottery_BaseDialog";
    protected V mDataBinding;
    private SharedPreferences mSharedPreferences;

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return true;
    }

    public float setDimAmount() {
        return 0.8f;
    }

    public abstract int setLayout();

    public abstract float setSize();

    public DVRBaseDialog(Context context, int i) {
        super(context, i);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSharedPreferences = getContext().getSharedPreferences(LOTTERY_PS, 0);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        V v = (V) DataBindingUtil.inflate(LayoutInflater.from(getContext()), setLayout(), null, true);
        this.mDataBinding = v;
        setContentView(v.getRoot());
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * setSize());
        attributes.dimAmount = setDimAmount();
        window.setAttributes(attributes);
        window.setGravity(17);
    }

    public void show(Context context) {
        if (context == null || !(context instanceof Activity)) {
            return;
        }
        Activity activity = (Activity) context;
        if (activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        super.show();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
    }

    public SharedPreferences.Editor getEditor() {
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences != null) {
            return sharedPreferences.edit();
        }
        return null;
    }

    public SharedPreferences getSharedPreferences() {
        return this.mSharedPreferences;
    }
}
