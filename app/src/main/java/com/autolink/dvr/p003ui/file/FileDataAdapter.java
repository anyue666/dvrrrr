package com.autolink.dvr.p003ui.file;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.bean.EventBusCheckBox;
import com.autolink.dvr.bean.EventBusLongClick;
import com.autolink.dvr.bean.EventBusUpdateQuantity;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.databinding.ItemListNormalBinding;
import com.autolink.dvr.model.FileNormalListResult;
import com.autolink.dvr.p003ui.file.FileDataAdapter;
import com.autolink.dvr.p003ui.view.DvrCheckBox;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.cookie.ClientCookie;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.EventBus;

/* compiled from: FileDataAdapter.kt */
@Metadata(m530d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002HIB\u0005¢\u0006\u0002\u0010\u0003J\u0016\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u00052\u0006\u0010,\u001a\u00020-J\b\u0010.\u001a\u00020\u0005H\u0016J\u001a\u0010/\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001aj\n\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u001bJ\u0010\u00100\u001a\u00020\u00072\u0006\u00101\u001a\u00020&H\u0002J\u0018\u00102\u001a\u00020*2\u0006\u00103\u001a\u00020\u00022\u0006\u00104\u001a\u00020\u0005H\u0016J\u0018\u00105\u001a\u00020\u00022\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\u0005H\u0016J\u0018\u00109\u001a\u00020*2\u0006\u00103\u001a\u00020\u00022\u0006\u0010:\u001a\u00020\u0007H\u0002J\u001e\u0010;\u001a\u00020*2\u0006\u0010<\u001a\u00020\u00052\u0006\u0010=\u001a\u00020\u00052\u0006\u0010,\u001a\u00020-J\u0006\u0010>\u001a\u00020*J\u0006\u0010?\u001a\u00020*J\u000e\u0010@\u001a\u00020*2\u0006\u0010A\u001a\u00020\u0010J\u0018\u0010B\u001a\u00020*2\u0006\u00103\u001a\u00020\u00022\u0006\u00104\u001a\u00020\u0005H\u0002J\u000e\u0010C\u001a\u00020*2\u0006\u0010D\u001a\u00020 J\u0006\u0010E\u001a\u00020*J\u0014\u0010F\u001a\u00020*2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\n0\tR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\"\u0010\u0019\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001aj\n\u0012\u0004\u0012\u00020\u0005\u0018\u0001`\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u001c\u001a\n \u001e*\u0004\u0018\u00010\u001d0\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u001f\u001a\u0004\u0018\u00010 X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010(X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006J"}, m531d2 = {"Lcom/autolink/dvr/ui/file/FileDataAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/autolink/dvr/ui/file/FileDataAdapter$MyViewHolder;", "()V", "MAX_CORE", "", "TAG", "", "dataItem", "", "Lcom/autolink/dvr/model/FileNormalListResult$Item;", "getDataItem", "()Ljava/util/List;", "setDataItem", "(Ljava/util/List;)V", "mFragment", "Landroidx/fragment/app/Fragment;", "getMFragment", "()Landroidx/fragment/app/Fragment;", "setMFragment", "(Landroidx/fragment/app/Fragment;)V", "mScope", "Lkotlinx/coroutines/CoroutineScope;", "getMScope", "()Lkotlinx/coroutines/CoroutineScope;", "mSelected", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "pool", "Ljava/util/concurrent/ScheduledExecutorService;", "kotlin.jvm.PlatformType", "rvlistenner", "Lcom/autolink/dvr/ui/file/FileDataAdapter$RecyclerItemViewClickListener;", "getRvlistenner", "()Lcom/autolink/dvr/ui/file/FileDataAdapter$RecyclerItemViewClickListener;", "setRvlistenner", "(Lcom/autolink/dvr/ui/file/FileDataAdapter$RecyclerItemViewClickListener;)V", "sf", "Ljava/text/SimpleDateFormat;", "tDate", "Ljava/util/Date;", "addRange", "", "id", "selected", "", "getItemCount", "getSelection", "getTheDayBefore", "df", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "previewImg", ClientCookie.PATH_ATTR, "selectRange", "start", "end", "setAllSelection", "setCleanSelection", "setFragment", "fragment", "setItemTitleName", "setViewClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "stopExecutors", "unifiedData", "items", "MyViewHolder", "RecyclerItemViewClickListener", "ALDVR_T1JRelease"}, m532k = 1, m533mv = {1, 8, 0}, m535xi = 48)
/* loaded from: classes.dex */
public final class FileDataAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private final int MAX_CORE;
    private final String TAG = LogUtils2.DEFAULT_TAG + getClass().getSimpleName();
    private List<? extends FileNormalListResult.Item> dataItem;
    private Fragment mFragment;
    private final CoroutineScope mScope;
    private HashSet<Integer> mSelected;
    private ScheduledExecutorService pool;
    private RecyclerItemViewClickListener rvlistenner;
    private SimpleDateFormat sf;
    private Date tDate;

    /* compiled from: FileDataAdapter.kt */
    @Metadata(m530d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u001a\u0010\b\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\t\u001a\u00020\u0007H&¨\u0006\n"}, m531d2 = {"Lcom/autolink/dvr/ui/file/FileDataAdapter$RecyclerItemViewClickListener;", "", "OnItemClickListener", "", "view", "Landroid/view/View;", "id", "", "OnItemLongClickListener", "position", "ALDVR_T1JRelease"}, m532k = 1, m533mv = {1, 8, 0}, m535xi = 48)
    public interface RecyclerItemViewClickListener {
        void OnItemClickListener(View view, int id);

        void OnItemLongClickListener(View view, int position);
    }

    public FileDataAdapter() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        this.MAX_CORE = availableProcessors;
        this.dataItem = new ArrayList();
        this.pool = Executors.newScheduledThreadPool(availableProcessors);
        this.mSelected = new HashSet<>();
        this.mScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getMain());
    }

    public final List<FileNormalListResult.Item> getDataItem() {
        return this.dataItem;
    }

    public final void setDataItem(List<? extends FileNormalListResult.Item> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.dataItem = list;
    }

    public final Fragment getMFragment() {
        return this.mFragment;
    }

    public final void setMFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LogUtils2.logI(this.TAG, "onCreateViewHolder ");
        ItemListNormalBinding inflate = ItemListNormalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(\n            Lay…, parent, false\n        )");
        return new MyViewHolder(inflate);
    }

    public final void setFragment(Fragment fragment) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        this.mFragment = fragment;
    }

    public final void unifiedData(List<? extends FileNormalListResult.Item> items) {
        Intrinsics.checkNotNullParameter(items, "items");
        this.dataItem = items;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dataItem.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder holder, int position) {
        boolean z;
        Intrinsics.checkNotNullParameter(holder, "holder");
        LogUtils2.logI("=======qqq ", "position " + position);
        if (this.dataItem.get(position).isFalseData()) {
            holder.getBinding().itemLayout.setVisibility(8);
            return;
        }
        holder.getBinding().itemLayout.setVisibility(0);
        if (this.dataItem.get(position).isFirstPlace()) {
            setItemTitleName(holder, position);
        } else {
            holder.getBinding().title.setVisibility(8);
        }
        holder.getBinding().itemListNormalDateTv.setText("");
        if (this.dataItem.get(position).getPath() != null) {
            String path = this.dataItem.get(position).getPath();
            Intrinsics.checkNotNullExpressionValue(path, "dataItem[position].path");
            previewImg(holder, path);
        }
        Fragment fragment = this.mFragment;
        if (fragment instanceof FileNormalFragment) {
            Intrinsics.checkNotNull(fragment, "null cannot be cast to non-null type com.autolink.dvr.ui.file.FileNormalFragment");
            z = ((FileNormalFragment) fragment).isIsEditState();
        } else {
            z = false;
        }
        Fragment fragment2 = this.mFragment;
        if (fragment2 instanceof FileEventFragment) {
            Intrinsics.checkNotNull(fragment2, "null cannot be cast to non-null type com.autolink.dvr.ui.file.FileEventFragment");
            z = ((FileEventFragment) fragment2).isIsEditState();
        }
        if (z) {
            if (this.dataItem.get(position).isChoose()) {
                LogUtils2.logI("选择的项目 ", "position " + position);
                holder.getBinding().itemListNormalCheckbox.setChecked(true);
                holder.getBinding().itemListNormalCheckbox.setVisibility(0);
                holder.getBinding().viewSelect.setVisibility(0);
            } else {
                holder.getBinding().itemListNormalCheckbox.setChecked(false);
                LogUtils2.logI("选择的项目 ", "no position  " + position);
                holder.getBinding().itemListNormalCheckbox.setVisibility(8);
                holder.getBinding().viewSelect.setVisibility(8);
            }
        } else {
            holder.getBinding().itemListNormalCheckbox.setVisibility(8);
            holder.getBinding().viewSelect.setVisibility(8);
            holder.getBinding().itemListNormalCheckbox.setChecked(false);
        }
        holder.getBinding().itemListNormalCheckbox.setDataItem(this.dataItem.get(position));
        holder.getBinding().viewSelect.setDataItem(this.dataItem.get(position));
        holder.bind(this.dataItem.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.file.FileDataAdapter$$ExternalSyntheticLambda0
            public final /* synthetic */ FileDataAdapter f$1;

            public /* synthetic */ FileDataAdapter$$ExternalSyntheticLambda0(FileDataAdapter this) {
                r2 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FileDataAdapter.onBindViewHolder$lambda$0(FileDataAdapter.MyViewHolder.this, r2, view);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.autolink.dvr.ui.file.FileDataAdapter$$ExternalSyntheticLambda1
            public final /* synthetic */ FileDataAdapter f$1;

            public /* synthetic */ FileDataAdapter$$ExternalSyntheticLambda1(FileDataAdapter this) {
                r2 = this;
            }

            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                boolean onBindViewHolder$lambda$1;
                onBindViewHolder$lambda$1 = FileDataAdapter.onBindViewHolder$lambda$1(FileDataAdapter.MyViewHolder.this, r2, view);
                return onBindViewHolder$lambda$1;
            }
        });
    }

    public static final void onBindViewHolder$lambda$0(MyViewHolder holder, FileDataAdapter this$0, View view) {
        boolean z;
        Intrinsics.checkNotNullParameter(holder, "$holder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DvrCheckBox dvrCheckBox = holder.getBinding().itemListNormalCheckbox;
        Intrinsics.checkNotNull(dvrCheckBox, "null cannot be cast to non-null type com.autolink.dvr.ui.view.DvrCheckBox");
        int layoutPosition = holder.getLayoutPosition();
        Fragment fragment = this$0.mFragment;
        if (fragment instanceof FileNormalFragment) {
            Intrinsics.checkNotNull(fragment, "null cannot be cast to non-null type com.autolink.dvr.ui.file.FileNormalFragment");
            z = ((FileNormalFragment) fragment).isIsEditState();
        } else {
            z = false;
        }
        Fragment fragment2 = this$0.mFragment;
        if (fragment2 instanceof FileEventFragment) {
            Intrinsics.checkNotNull(fragment2, "null cannot be cast to non-null type com.autolink.dvr.ui.file.FileEventFragment");
            z = ((FileEventFragment) fragment2).isIsEditState();
        }
        if (z) {
            if (!dvrCheckBox.isChecked()) {
                this$0.dataItem.get(layoutPosition).setChoose(true);
                dvrCheckBox.setChecked(true);
                this$0.addRange(layoutPosition, true);
                dvrCheckBox.setVisibility(0);
                return;
            }
            this$0.dataItem.get(layoutPosition).setChoose(false);
            this$0.addRange(layoutPosition, false);
            dvrCheckBox.setChecked(false);
            dvrCheckBox.setVisibility(8);
            return;
        }
        RecyclerItemViewClickListener recyclerItemViewClickListener = this$0.rvlistenner;
        if (recyclerItemViewClickListener == null || recyclerItemViewClickListener == null) {
            return;
        }
        recyclerItemViewClickListener.OnItemClickListener(holder.itemView, layoutPosition);
    }

    public static final boolean onBindViewHolder$lambda$1(MyViewHolder holder, FileDataAdapter this$0, View view) {
        Intrinsics.checkNotNullParameter(holder, "$holder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int layoutPosition = holder.getLayoutPosition();
        Fragment fragment = this$0.mFragment;
        if (fragment instanceof FileNormalFragment) {
            Intrinsics.checkNotNull(fragment, "null cannot be cast to non-null type com.autolink.dvr.ui.file.FileNormalFragment");
            ((FileNormalFragment) fragment).isIsEditState();
        }
        Fragment fragment2 = this$0.mFragment;
        if (fragment2 instanceof FileEventFragment) {
            Intrinsics.checkNotNull(fragment2, "null cannot be cast to non-null type com.autolink.dvr.ui.file.FileEventFragment");
            ((FileEventFragment) fragment2).isIsEditState();
        }
        RecyclerItemViewClickListener recyclerItemViewClickListener = this$0.rvlistenner;
        if (recyclerItemViewClickListener == null) {
            return true;
        }
        if (recyclerItemViewClickListener != null) {
            recyclerItemViewClickListener.OnItemLongClickListener(holder.itemView, layoutPosition);
        }
        EventBus.getDefault().post(new EventBusLongClick());
        return true;
    }

    public final RecyclerItemViewClickListener getRvlistenner() {
        return this.rvlistenner;
    }

    public final void setRvlistenner(RecyclerItemViewClickListener recyclerItemViewClickListener) {
        this.rvlistenner = recyclerItemViewClickListener;
    }

    public final void setViewClickListener(RecyclerItemViewClickListener r2) {
        Intrinsics.checkNotNullParameter(r2, "listener");
        this.rvlistenner = r2;
    }

    private final void previewImg(MyViewHolder holder, String r5) {
        holder.getBinding().itemListNormalBackgroundIv.setTag(C0903R.id.preview_image_path_tag, r5);
        holder.getBinding().itemListNormalDateTv.setTag(r5);
        holder.getBinding().itemListNormalBackgroundIv.setImageDrawable(holder.getBinding().itemListNormalBackgroundIv.getContext().getDrawable(C0903R.drawable.ic_video_def_bg));
        if (this.pool.isShutdown()) {
            return;
        }
        this.pool.schedule(new Runnable() { // from class: com.autolink.dvr.ui.file.FileDataAdapter$$ExternalSyntheticLambda2
            public final /* synthetic */ String f$1;
            public final /* synthetic */ FileDataAdapter.MyViewHolder f$2;

            public /* synthetic */ FileDataAdapter$$ExternalSyntheticLambda2(String r52, FileDataAdapter.MyViewHolder holder2) {
                r2 = r52;
                r3 = holder2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                FileDataAdapter.previewImg$lambda$2(FileDataAdapter.this, r2, r3);
            }
        }, 0L, TimeUnit.MILLISECONDS);
    }

    public static final void previewImg$lambda$2(FileDataAdapter this$0, String path, MyViewHolder holder) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(path, "$path");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        LogUtils2.logI(this$0.TAG, "startPreviewTime  " + path);
        holder.getBinding().itemLayout.startPreviewTime(path);
        holder.getBinding().itemLayout.startPreviewImg(path);
    }

    public final void stopExecutors() {
        LogUtils2.logI(this.TAG, "stopExecutors");
        try {
            if (this.pool.isShutdown()) {
                return;
            }
            this.pool.shutdownNow();
        } catch (Exception unused) {
        }
    }

    public final HashSet<Integer> getSelection() {
        return this.mSelected;
    }

    public final void setAllSelection() {
        HashSet<Integer> hashSet;
        HashSet<Integer> hashSet2 = this.mSelected;
        if (hashSet2 != null) {
            hashSet2.clear();
        }
        int size = this.dataItem.size();
        for (int i = 0; i < size; i++) {
            if (!this.dataItem.get(i).isFalseData() && (hashSet = this.mSelected) != null) {
                hashSet.add(Integer.valueOf(i));
            }
        }
    }

    public final void setCleanSelection() {
        HashSet<Integer> hashSet = this.mSelected;
        if (hashSet != null) {
            hashSet.clear();
        }
    }

    public final void selectRange(int start, int end, boolean selected) {
        if (start > end) {
            return;
        }
        while (true) {
            if (!this.dataItem.get(start).isFalseData()) {
                addRange(start, selected);
            }
            if (start == end) {
                return;
            } else {
                start++;
            }
        }
    }

    public final void addRange(int id, boolean selected) {
        if (selected) {
            HashSet<Integer> hashSet = this.mSelected;
            Intrinsics.checkNotNull(hashSet);
            hashSet.add(Integer.valueOf(id));
            this.dataItem.get(id).setChoose(true);
        } else {
            HashSet<Integer> hashSet2 = this.mSelected;
            Intrinsics.checkNotNull(hashSet2);
            hashSet2.remove(Integer.valueOf(id));
            this.dataItem.get(id).setChoose(false);
        }
        LogUtils2.logI(this.TAG, "testsets " + id);
        EventBus.getDefault().post(new EventBusCheckBox());
        EventBus.getDefault().post(new EventBusUpdateQuantity());
    }

    public final CoroutineScope getMScope() {
        return this.mScope;
    }

    private final void setItemTitleName(MyViewHolder holder, int position) {
        holder.getBinding().title.setVisibility(0);
        if (position % 4 == 0) {
            if (this.sf == null) {
                this.tDate = new Date();
                this.sf = new SimpleDateFormat("yyyyMMdd");
            }
            String name = this.dataItem.get(position).getName();
            Intrinsics.checkNotNull(name);
            List split$default = StringsKt.split$default((CharSequence) name, new String[]{"_"}, false, 0, 6, (Object) null);
            Object obj = split$default.get(0);
            SimpleDateFormat simpleDateFormat = this.sf;
            Intrinsics.checkNotNull(simpleDateFormat);
            if (Intrinsics.areEqual(obj, simpleDateFormat.format(this.tDate))) {
                holder.getBinding().title.setText("今天");
                return;
            }
            SimpleDateFormat simpleDateFormat2 = this.sf;
            Intrinsics.checkNotNull(simpleDateFormat2);
            if (Intrinsics.areEqual(getTheDayBefore(simpleDateFormat2), split$default.get(0))) {
                holder.getBinding().title.setText("昨天");
                return;
            } else {
                holder.getBinding().title.setText((CharSequence) split$default.get(0));
                return;
            }
        }
        holder.getBinding().title.setText("");
    }

    private final String getTheDayBefore(SimpleDateFormat df) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, -1);
        Date time = calendar.getTime();
        df.format(time);
        String format = df.format(time);
        Intrinsics.checkNotNullExpressionValue(format, "df.format(date)");
        return format;
    }

    /* compiled from: FileDataAdapter.kt */
    @Metadata(m530d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, m531d2 = {"Lcom/autolink/dvr/ui/file/FileDataAdapter$MyViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/autolink/dvr/databinding/ItemListNormalBinding;", "(Lcom/autolink/dvr/databinding/ItemListNormalBinding;)V", "getBinding", "()Lcom/autolink/dvr/databinding/ItemListNormalBinding;", "bind", "", "resultItem", "Lcom/autolink/dvr/model/FileNormalListResult$Item;", "ALDVR_T1JRelease"}, m532k = 1, m533mv = {1, 8, 0}, m535xi = 48)
    public static final class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemListNormalBinding binding;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MyViewHolder(ItemListNormalBinding binding) {
            super(binding.getRoot());
            Intrinsics.checkNotNullParameter(binding, "binding");
            this.binding = binding;
        }

        public final ItemListNormalBinding getBinding() {
            return this.binding;
        }

        public final void bind(FileNormalListResult.Item resultItem) {
            this.binding.setResultItem(resultItem);
        }
    }
}
