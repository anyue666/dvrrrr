package com.autolink.dvr.common.base.p002my;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.autolink.dvr.common.utils.LogUtils2;

/* loaded from: classes.dex */
public abstract class MyBaseActivity extends AppCompatActivity {
    private final String TAG = LogUtils2.DEFAULT_TAG + getClass().getSimpleName();

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LogUtils2.logV(this.TAG, "[onCreate]");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        LogUtils2.logV(this.TAG, "[onStart]");
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
        LogUtils2.logV(this.TAG, "[onRestart]");
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        LogUtils2.logV(this.TAG, "[onSaveInstanceState]");
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            LogUtils2.logV(this.TAG, "[onNewIntent]" + intent.toString());
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        LogUtils2.logV(this.TAG, "[onActivityResult] requestCode:" + i + ";resultCode:" + i);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        LogUtils2.logV(this.TAG, "[onResume]");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        LogUtils2.logV(this.TAG, "[onPause]");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        LogUtils2.logV(this.TAG, "[onStop]");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        LogUtils2.logV(this.TAG, "[onDestroy]");
    }
}
