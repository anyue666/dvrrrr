package com.autolink.dvr.p003ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewOverlay;
import androidx.constraintlayout.widget.ConstraintLayout;

/* loaded from: classes.dex */
public class CrossConstraintLayout extends ConstraintLayout {
    @Override // android.view.ViewGroup, android.view.View
    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public CrossConstraintLayout(Context context) {
        super(context);
    }

    public CrossConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CrossConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }
}
