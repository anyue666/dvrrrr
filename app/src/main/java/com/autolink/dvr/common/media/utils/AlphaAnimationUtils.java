package com.autolink.dvr.common.media.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/* loaded from: classes.dex */
public class AlphaAnimationUtils {
    private AlphaAnimation mHideAnimation;

    public void setHideAnimation(final View view, int i) {
        if (view == null || i < 0) {
            return;
        }
        AlphaAnimation alphaAnimation = this.mHideAnimation;
        if (alphaAnimation != null) {
            alphaAnimation.cancel();
        }
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
        this.mHideAnimation = alphaAnimation2;
        alphaAnimation2.setDuration(i);
        this.mHideAnimation.setFillAfter(true);
        this.mHideAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.autolink.dvr.common.media.utils.AlphaAnimationUtils.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(8);
            }
        });
        view.startAnimation(this.mHideAnimation);
    }
}
