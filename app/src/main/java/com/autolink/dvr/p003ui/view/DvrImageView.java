package com.autolink.dvr.p003ui.view;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.material.imageview.ShapeableImageView;

/* loaded from: classes.dex */
public class DvrImageView extends ShapeableImageView {
    public DvrImageView(Context context) {
        super(context);
    }

    public DvrImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DvrImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setTag(null);
    }
}
