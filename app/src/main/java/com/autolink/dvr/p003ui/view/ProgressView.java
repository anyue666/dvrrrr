package com.autolink.dvr.p003ui.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.appcompat.widget.AppCompatImageView;
import com.autolink.dvr.C0903R;

/* loaded from: classes.dex */
public class ProgressView extends AppCompatImageView {
    private static final long ANIMATOR_DURATION = 600;
    private ObjectAnimator mAnimator;

    public ProgressView(Context context) {
        super(context);
        init();
    }

    public ProgressView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        if (getDrawable() == null) {
            setImageResource(C0903R.drawable.loading);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimator();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimator();
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, final int i) {
        super.onVisibilityChanged(view, i);
        post(new Runnable() { // from class: com.autolink.dvr.ui.view.ProgressView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ProgressView.this.m237x8480dfd4(i);
            }
        });
    }

    /* renamed from: lambda$onVisibilityChanged$0$com-autolink-dvr-ui-view-ProgressView */
    /* synthetic */ void m237x8480dfd4(int i) {
        if (i == 0 && isShown()) {
            ObjectAnimator objectAnimator = this.mAnimator;
            if (objectAnimator == null) {
                startAnimator();
                return;
            } else {
                if (objectAnimator.isPaused()) {
                    this.mAnimator.resume();
                    return;
                }
                return;
            }
        }
        ObjectAnimator objectAnimator2 = this.mAnimator;
        if (objectAnimator2 == null || objectAnimator2.isPaused()) {
            return;
        }
        this.mAnimator.pause();
    }

    private void startAnimator() {
        cancelAnimator();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, (Property<ProgressView, Float>) View.ROTATION, 0.0f, 360.0f);
        this.mAnimator = ofFloat;
        ofFloat.setDuration(ANIMATOR_DURATION);
        this.mAnimator.setRepeatCount(-1);
        this.mAnimator.setInterpolator(new LinearInterpolator());
        this.mAnimator.start();
    }

    private void cancelAnimator() {
        ObjectAnimator objectAnimator = this.mAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.mAnimator = null;
        }
    }
}
