package com.autolink.dvr.p003ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewOverlay;
import androidx.recyclerview.widget.RecyclerView;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.p003ui.file.FileDataAdapter;
import com.bumptech.glide.Glide;

/* loaded from: classes.dex */
public class DvrRecyclerView extends RecyclerView {
    FileDataAdapter fileDataAdapter;

    @Override // android.view.ViewGroup, android.view.View
    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public DvrRecyclerView(Context context) {
        super(context);
        addScrollListener();
    }

    public DvrRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        addScrollListener();
    }

    public DvrRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        addScrollListener();
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setAdapter(RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);
        init();
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void init() {
        this.fileDataAdapter = (FileDataAdapter) getAdapter();
    }

    public void addScrollListener() {
        addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.autolink.dvr.ui.view.DvrRecyclerView.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    try {
                        if (DvrRecyclerView.this.getContext() != null) {
                            Glide.with(DvrRecyclerView.this.getContext()).resumeRequests();
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                if (i == 1) {
                    try {
                        if (DvrRecyclerView.this.getContext() != null) {
                            Glide.with(DvrRecyclerView.this.getContext()).pauseRequests();
                            return;
                        }
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
                if (i != 2) {
                    return;
                }
                try {
                    if (DvrRecyclerView.this.getContext() != null) {
                        Glide.with(DvrRecyclerView.this.getContext()).pauseRequests();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                LogUtils2.logI("TSSSSSS", " dy " + i2);
            }
        });
    }
}
