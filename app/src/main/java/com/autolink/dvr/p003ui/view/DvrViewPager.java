package com.autolink.dvr.p003ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOverlay;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.autolink.dvr.p003ui.file.FileEventFragment;
import com.autolink.dvr.p003ui.file.FileFragmentAdapter;
import com.autolink.dvr.p003ui.file.FileNormalFragment;

/* loaded from: classes.dex */
public class DvrViewPager extends ViewPager implements View.OnLongClickListener {
    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public DvrViewPager(Context context) {
        super(context);
    }

    public DvrViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnLongClickListener(this);
    }

    @Override // android.view.View
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        super.setOnLongClickListener(onLongClickListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Fragment item = ((FileFragmentAdapter) getAdapter()).getItem(getCurrentItem());
        if (motionEvent.getAction() == 1 && item != null) {
            if (item instanceof FileNormalFragment) {
                FileNormalFragment fileNormalFragment = (FileNormalFragment) item;
                fileNormalFragment.isLongPress = false;
                if (fileNormalFragment.isEditState && fileNormalFragment.isLongPress) {
                    return false;
                }
            }
            if (item instanceof FileEventFragment) {
                FileEventFragment fileEventFragment = (FileEventFragment) item;
                fileEventFragment.isLongPress = false;
                if (fileEventFragment.isEditState && fileEventFragment.isLongPress) {
                    return false;
                }
            }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }
}
