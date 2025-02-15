package com.autolink.dvr.common.base.p002my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.autolink.dvr.common.utils.LogUtils2;

/* loaded from: classes.dex */
public abstract class MyBaseFragment extends Fragment {
    private final String TAG = LogUtils2.DEFAULT_TAG + getClass().getSimpleName();

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        LogUtils2.logV(this.TAG, "[onActivityCreated]");
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LogUtils2.logV(this.TAG, "[onCreate]");
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        LogUtils2.logV(this.TAG, "[onCreateView]");
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils2.logV(this.TAG, "[onAttach] content " + context);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        LogUtils2.logV(this.TAG, "[onAttachFragment] childFragment " + fragment);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        LogUtils2.logV(this.TAG, "[onStart]");
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        LogUtils2.logV(this.TAG, "[onResume]");
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        LogUtils2.logV(this.TAG, "[onActivityResult] requestCode:" + i + ";resultCode:" + i);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        LogUtils2.logV(this.TAG, "[onPause]");
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        LogUtils2.logV(this.TAG, "[onSaveInstanceState]");
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        LogUtils2.logV(this.TAG, "[onStop]");
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        LogUtils2.logV(this.TAG, "[onDetach]");
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils2.logV(this.TAG, "[onDestroyView]");
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        LogUtils2.logV(this.TAG, "[onDestroy]");
    }
}
