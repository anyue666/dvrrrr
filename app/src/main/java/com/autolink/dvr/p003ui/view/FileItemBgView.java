package com.autolink.dvr.p003ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.autolink.dvr.bean.EventBusCheckBox;
import com.autolink.dvr.model.FileNormalListResult;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes.dex */
public class FileItemBgView extends View {
    FileNormalListResult.Item mItemData;

    public FileItemBgView(Context context) {
        super(context);
    }

    public FileItemBgView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FileItemBgView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setDataItem(FileNormalListResult.Item item) {
        this.mItemData = item;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusMessage(EventBusCheckBox eventBusCheckBox) {
        refreshCheckedState();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setTag(null);
        EventBus.getDefault().unregister(this);
    }

    private void refreshCheckedState() {
        FileNormalListResult.Item item = this.mItemData;
        if (item != null) {
            if (item.isChoose()) {
                setVisibility(0);
            } else {
                setVisibility(8);
            }
        }
    }
}
