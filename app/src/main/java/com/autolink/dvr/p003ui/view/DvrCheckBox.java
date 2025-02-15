package com.autolink.dvr.p003ui.view;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatCheckBox;
import com.autolink.dvr.bean.EventBusCheckBox;
import com.autolink.dvr.model.FileNormalListResult;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes.dex */
public class DvrCheckBox extends AppCompatCheckBox {
    FileNormalListResult.Item mItemData;

    public DvrCheckBox(Context context) {
        this(context, null);
    }

    public DvrCheckBox(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DvrCheckBox(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setDataItem(FileNormalListResult.Item item) {
        this.mItemData = item;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusMessage(EventBusCheckBox eventBusCheckBox) {
        refreshCheckedState();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        refreshCheckedState();
        EventBus.getDefault().register(this);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    private void refreshCheckedState() {
        FileNormalListResult.Item item = this.mItemData;
        if (item != null) {
            if (item.isChoose()) {
                setVisibility(0);
                setChecked(true);
            } else {
                setVisibility(8);
                setChecked(false);
            }
        }
    }
}
