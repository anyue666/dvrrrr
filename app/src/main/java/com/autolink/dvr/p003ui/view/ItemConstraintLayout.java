package com.autolink.dvr.p003ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.util.AttributeSet;
import android.view.ViewOverlay;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.common.media.utils.FileUtils;
import com.autolink.dvr.common.utils.CommonToolUtils;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.USBUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import java.io.File;
import org.apache.log4j.spi.Configurator;

/* loaded from: classes.dex */
public class ItemConstraintLayout extends ConstraintLayout {
    private final String TAG;
    DvrImageView dvrImageView;
    TextView timeTextView;

    @Override // android.view.ViewGroup, android.view.View
    public /* bridge */ /* synthetic */ ViewOverlay getOverlay() {
        return super.getOverlay();
    }

    public ItemConstraintLayout(Context context) {
        super(context);
        this.TAG = "DVR_ItemConstraintLayout";
    }

    public ItemConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "DVR_ItemConstraintLayout";
    }

    public ItemConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "DVR_ItemConstraintLayout";
    }

    public void startPreviewTime(final String str) {
        if (this.timeTextView == null) {
            this.timeTextView = (TextView) findViewById(C0903R.id.item_list_normal_date_tv);
        }
        final String vedioTotalTime = getVedioTotalTime(new File(str));
        post(new Runnable() { // from class: com.autolink.dvr.ui.view.ItemConstraintLayout$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ItemConstraintLayout.this.m236xba777566(str, vedioTotalTime);
            }
        });
    }

    /* renamed from: lambda$startPreviewTime$0$com-autolink-dvr-ui-view-ItemConstraintLayout */
    /* synthetic */ void m236xba777566(String str, String str2) {
        if (str.equals(this.timeTextView.getTag())) {
            this.timeTextView.setText(str2);
            return;
        }
        LogUtils2.logW("DVR_ItemConstraintLayout", "timeTextView.getTag() != pathtimeTextView.getTag() = " + this.timeTextView.getTag() + ",path = " + str);
    }

    public void startPreviewImg(final String str) {
        if (this.dvrImageView == null) {
            this.dvrImageView = (DvrImageView) findViewById(C0903R.id.item_list_normal_background_iv);
        }
        final File file = new File(USBUtil.USBPath + File.separator + "thumbnail" + File.separator + new File(str).getName() + ".jpg");
        try {
            LogUtils2.logW("DVR_ItemConstraintLayout", "thumbnailFile.exists() = " + file.exists() + ",path = " + str);
            if (file.exists() || FileUtils.saveVideoThumbnail(str)) {
                post(new Runnable() { // from class: com.autolink.dvr.ui.view.ItemConstraintLayout$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ItemConstraintLayout.this.m235xbce2e577(str, file);
                    }
                });
            }
        } catch (Exception unused) {
            LogUtils2.logI("DVR_ItemConstraintLayout", "startPreviewImg  error " + str);
        }
    }

    /* renamed from: lambda$startPreviewImg$1$com-autolink-dvr-ui-view-ItemConstraintLayout */
    /* synthetic */ void m235xbce2e577(final String str, File file) {
        LogUtils2.logW("DVR_ItemConstraintLayout", "Glide into this,path = " + str + ",dvrImageView tag " + this.dvrImageView.getTag(C0903R.id.preview_image_path_tag));
        DvrImageView dvrImageView = this.dvrImageView;
        if (dvrImageView == null || !str.equals(dvrImageView.getTag(C0903R.id.preview_image_path_tag))) {
            return;
        }
        Context context = this.dvrImageView.getContext();
        if (context == null) {
            LogUtils2.logW("DVR_ItemConstraintLayout", "context is finish");
            return;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing() || activity.isDestroyed()) {
                LogUtils2.logW("DVR_ItemConstraintLayout", "activity is finish");
                return;
            }
        }
        try {
            LogUtils2.logW("DVR_ItemConstraintLayout", "Glide into this Glide.with(dvrImageView).load(thumbnailFile),path = " + str);
            Glide.with(this.dvrImageView).load(file).listener(new RequestListener<Drawable>() { // from class: com.autolink.dvr.ui.view.ItemConstraintLayout.1
                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("加载失败 errorMsg:");
                    sb.append(glideException != null ? glideException.getMessage() : Configurator.NULL);
                    LogUtils2.logW("DVR_ItemConstraintLayout", sb.toString());
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    LogUtils2.logW("DVR_ItemConstraintLayout", "成功  path:" + str);
                    return false;
                }
            }).into(this.dvrImageView);
        } catch (Throwable th) {
            LogUtils2.logE("DVR_ItemConstraintLayout", "load image fail:" + th + ",path = " + str);
        }
    }

    public String getVedioTotalTime(File file) {
        String str;
        if (!file.exists()) {
            return null;
        }
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
        if (mediaMetadataRetriever.extractMetadata(9) != null) {
            str = CommonToolUtils.INSTANCE.getSingleton().stringForTime(Integer.parseInt(r1));
        } else {
            LogUtils2.logI("DVR_ItemConstraintLayout", "getVedioTotalTime error " + file.getAbsolutePath());
            str = "";
        }
        try {
            mediaMetadataRetriever.release();
        } catch (Exception unused) {
        }
        return str;
    }
}
