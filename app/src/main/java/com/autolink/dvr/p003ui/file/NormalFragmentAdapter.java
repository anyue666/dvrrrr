package com.autolink.dvr.p003ui.file;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.autolink.dvr.bean.EventBusCheckBox;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.databinding.ItemListNormalBinding;
import com.autolink.dvr.model.FileListResultBean;
import com.autolink.dvr.model.FileNormalListResult;
import com.autolink.dvr.p003ui.file.NormalFragmentAdapter;
import com.autolink.dvr.p003ui.view.DvrCheckBox;
import com.autolink.dvr.p003ui.view.FileItemBgView;
import com.autolink.dvr.viewmodel.FileViewModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.EventBus;

/* compiled from: NormalFragmentAdapter.kt */
@Metadata(m530d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0002BCB\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u001b2\u0006\u0010+\u001a\u00020,J\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\f0\u000bJ\b\u0010.\u001a\u00020\u001bH\u0016J\u000e\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\u001bJ\u001a\u00101\u001a\u0016\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001aj\n\u0012\u0004\u0012\u00020\u001b\u0018\u0001`\u001cJ\u0010\u00102\u001a\u00020\t2\u0006\u00103\u001a\u00020%H\u0002J\u0006\u00104\u001a\u00020)J\u001c\u00105\u001a\u00020)2\n\u00106\u001a\u00060\u0002R\u00020\u00002\u0006\u00100\u001a\u00020\u001bH\u0016J\u001c\u00107\u001a\u00060\u0002R\u00020\u00002\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u001bH\u0016J\u001e\u0010;\u001a\u00020)2\u0006\u0010<\u001a\u00020\u001b2\u0006\u0010=\u001a\u00020\u001b2\u0006\u0010+\u001a\u00020,J\u0016\u0010>\u001a\u00020)2\u0006\u0010*\u001a\u00020\u001b2\u0006\u0010?\u001a\u00020,J\u0010\u0010@\u001a\u00020)2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J\u001c\u0010A\u001a\u00020)2\n\u00106\u001a\u00060\u0002R\u00020\u00002\u0006\u00100\u001a\u00020\u001bH\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\"\u0010\u0019\u001a\u0016\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001aj\n\u0012\u0004\u0012\u00020\u001b\u0018\u0001`\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082D¢\u0006\u0002\n\u0000R\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u0010\u0010$\u001a\u0004\u0018\u00010%X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010'X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006D"}, m531d2 = {"Lcom/autolink/dvr/ui/file/NormalFragmentAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/autolink/dvr/ui/file/NormalFragmentAdapter$MyViewHolder;", "fileDataAdapter", "Lcom/autolink/dvr/ui/file/FileDataAdapter;", "fileViewModel", "Lcom/autolink/dvr/viewmodel/FileViewModel;", "(Lcom/autolink/dvr/ui/file/FileDataAdapter;Lcom/autolink/dvr/viewmodel/FileViewModel;)V", "TAG", "", "allItemData", "", "Lcom/autolink/dvr/model/FileNormalListResult$Item;", "getAllItemData", "()Ljava/util/List;", "data", "Lcom/autolink/dvr/model/FileListResultBean;", "getFileDataAdapter", "()Lcom/autolink/dvr/ui/file/FileDataAdapter;", "getFileViewModel", "()Lcom/autolink/dvr/viewmodel/FileViewModel;", "mScope", "Lkotlinx/coroutines/CoroutineScope;", "getMScope", "()Lkotlinx/coroutines/CoroutineScope;", "mSelected", "Ljava/util/HashSet;", "", "Lkotlin/collections/HashSet;", "mUpperLevelView", "rvlistenner", "Lcom/autolink/dvr/ui/file/NormalFragmentAdapter$RecyclerItemViewClickListener;", "getRvlistenner", "()Lcom/autolink/dvr/ui/file/NormalFragmentAdapter$RecyclerItemViewClickListener;", "setRvlistenner", "(Lcom/autolink/dvr/ui/file/NormalFragmentAdapter$RecyclerItemViewClickListener;)V", "sf", "Ljava/text/SimpleDateFormat;", "tDate", "Ljava/util/Date;", "addRange", "", "id", "selected", "", "getData", "getItemCount", "getItemData", "position", "getSelection", "getTheDayBefore", "df", "initData", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "selectRange", "start", "end", "setChooseItem", "choose", "setData", "setItemTitleName", "MyViewHolder", "RecyclerItemViewClickListener", "ALDVR_T1JRelease"}, m532k = 1, m533mv = {1, 8, 0}, m535xi = 48)
/* loaded from: classes.dex */
public final class NormalFragmentAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private final String TAG;
    private FileListResultBean data;
    private final FileDataAdapter fileDataAdapter;
    private final FileViewModel fileViewModel;
    private final CoroutineScope mScope;
    private HashSet<Integer> mSelected;
    private final int mUpperLevelView;
    private RecyclerItemViewClickListener rvlistenner;
    private SimpleDateFormat sf;
    private Date tDate;

    /* compiled from: NormalFragmentAdapter.kt */
    @Metadata(m530d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u001a\u0010\b\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\t\u001a\u00020\u0007H&¨\u0006\n"}, m531d2 = {"Lcom/autolink/dvr/ui/file/NormalFragmentAdapter$RecyclerItemViewClickListener;", "", "OnItemClickListener", "", "view", "Landroid/view/View;", "id", "", "OnItemLongClickListener", "position", "ALDVR_T1JRelease"}, m532k = 1, m533mv = {1, 8, 0}, m535xi = 48)
    public interface RecyclerItemViewClickListener {
        void OnItemClickListener(View view, int id);

        void OnItemLongClickListener(View view, int position);
    }

    public final FileDataAdapter getFileDataAdapter() {
        return this.fileDataAdapter;
    }

    public final FileViewModel getFileViewModel() {
        return this.fileViewModel;
    }

    public NormalFragmentAdapter(FileDataAdapter fileDataAdapter, FileViewModel fileViewModel) {
        Intrinsics.checkNotNullParameter(fileDataAdapter, "fileDataAdapter");
        Intrinsics.checkNotNullParameter(fileViewModel, "fileViewModel");
        this.fileDataAdapter = fileDataAdapter;
        this.fileViewModel = fileViewModel;
        this.TAG = LogUtils2.DEFAULT_TAG + getClass().getSimpleName();
        this.mScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getMain());
    }

    public final RecyclerItemViewClickListener getRvlistenner() {
        return this.rvlistenner;
    }

    public final void setRvlistenner(RecyclerItemViewClickListener recyclerItemViewClickListener) {
        this.rvlistenner = recyclerItemViewClickListener;
    }

    public final CoroutineScope getMScope() {
        return this.mScope;
    }

    public final void initData() {
        this.mSelected = new HashSet<>();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LogUtils2.logI(this.TAG, "onCreateViewHolder ");
        ItemListNormalBinding inflate = ItemListNormalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(\n            Lay…, parent, false\n        )");
        return new MyViewHolder(this, inflate);
    }

    public final HashSet<Integer> getSelection() {
        return this.mSelected;
    }

    public final void selectRange(int start, int end, boolean selected) {
        if (start > end) {
            return;
        }
        while (true) {
            addRange(start, selected);
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
            FileListResultBean fileListResultBean = this.data;
            Intrinsics.checkNotNull(fileListResultBean);
            fileListResultBean.getData().get(id).setChoose(true);
        } else {
            HashSet<Integer> hashSet2 = this.mSelected;
            Intrinsics.checkNotNull(hashSet2);
            hashSet2.remove(Integer.valueOf(id));
            FileListResultBean fileListResultBean2 = this.data;
            Intrinsics.checkNotNull(fileListResultBean2);
            fileListResultBean2.getData().get(id).setChoose(false);
        }
        LogUtils2.logI(this.TAG, "testsets " + id);
        EventBus.getDefault().post(new EventBusCheckBox());
    }

    public final void setChooseItem(int id, boolean choose) {
        LogUtils2.logI(this.TAG, "setChooseItem id " + id);
        FileListResultBean fileListResultBean = this.data;
        Intrinsics.checkNotNull(fileListResultBean);
        fileListResultBean.getData().get(id).setChoose(choose);
    }

    private final void setItemTitleName(MyViewHolder holder, int position) {
        if (position % 4 == 0) {
            holder.getBinding().title.setVisibility(0);
            if (this.sf == null) {
                this.tDate = new Date();
                this.sf = new SimpleDateFormat("yyyyMMdd");
            }
            FileListResultBean fileListResultBean = this.data;
            Intrinsics.checkNotNull(fileListResultBean);
            String fileName = fileListResultBean.getFileName();
            SimpleDateFormat simpleDateFormat = this.sf;
            Intrinsics.checkNotNull(simpleDateFormat);
            if (Intrinsics.areEqual(fileName, simpleDateFormat.format(this.tDate))) {
                holder.getBinding().title.setText("今天");
                return;
            }
            SimpleDateFormat simpleDateFormat2 = this.sf;
            Intrinsics.checkNotNull(simpleDateFormat2);
            String theDayBefore = getTheDayBefore(simpleDateFormat2);
            FileListResultBean fileListResultBean2 = this.data;
            Intrinsics.checkNotNull(fileListResultBean2);
            if (Intrinsics.areEqual(theDayBefore, fileListResultBean2.getFileName())) {
                holder.getBinding().title.setText("昨天");
                return;
            }
            TextView textView = holder.getBinding().title;
            FileListResultBean fileListResultBean3 = this.data;
            Intrinsics.checkNotNull(fileListResultBean3);
            textView.setText(fileListResultBean3.getFileName());
            return;
        }
        holder.getBinding().title.setVisibility(4);
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

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        LogUtils2.logI(this.TAG, "onBindViewHolder ");
        setItemTitleName(holder, position);
        DvrCheckBox dvrCheckBox = holder.getBinding().itemListNormalCheckbox;
        FileListResultBean fileListResultBean = this.data;
        Intrinsics.checkNotNull(fileListResultBean);
        dvrCheckBox.setDataItem(fileListResultBean.getData().get(position));
        FileItemBgView fileItemBgView = holder.getBinding().viewSelect;
        FileListResultBean fileListResultBean2 = this.data;
        Intrinsics.checkNotNull(fileListResultBean2);
        fileItemBgView.setDataItem(fileListResultBean2.getData().get(position));
        FileListResultBean fileListResultBean3 = this.data;
        Intrinsics.checkNotNull(fileListResultBean3);
        holder.bind(fileListResultBean3.getData().get(position), this.fileViewModel);
        if (this.rvlistenner != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.file.NormalFragmentAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NormalFragmentAdapter.onBindViewHolder$lambda$0(NormalFragmentAdapter.MyViewHolder.this, this, view);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.autolink.dvr.ui.file.NormalFragmentAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    boolean onBindViewHolder$lambda$1;
                    onBindViewHolder$lambda$1 = NormalFragmentAdapter.onBindViewHolder$lambda$1(NormalFragmentAdapter.MyViewHolder.this, this, view);
                    return onBindViewHolder$lambda$1;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(MyViewHolder holder, NormalFragmentAdapter this$0, View view) {
        Intrinsics.checkNotNullParameter(holder, "$holder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNull(holder.getBinding().itemListNormalCheckbox, "null cannot be cast to non-null type com.autolink.dvr.ui.view.DvrCheckBox");
        int layoutPosition = holder.getLayoutPosition();
        RecyclerItemViewClickListener recyclerItemViewClickListener = this$0.rvlistenner;
        Intrinsics.checkNotNull(recyclerItemViewClickListener);
        recyclerItemViewClickListener.OnItemClickListener(holder.itemView, layoutPosition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onBindViewHolder$lambda$1(MyViewHolder holder, NormalFragmentAdapter this$0, View view) {
        Intrinsics.checkNotNullParameter(holder, "$holder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int layoutPosition = holder.getLayoutPosition();
        DvrCheckBox dvrCheckBox = holder.getBinding().itemListNormalCheckbox;
        Intrinsics.checkNotNull(dvrCheckBox, "null cannot be cast to non-null type android.widget.CheckBox");
        DvrCheckBox dvrCheckBox2 = dvrCheckBox;
        FileListResultBean fileListResultBean = this$0.data;
        Intrinsics.checkNotNull(fileListResultBean);
        fileListResultBean.getData().get(layoutPosition).setChoose(true);
        dvrCheckBox2.setChecked(true);
        this$0.addRange(layoutPosition, true);
        dvrCheckBox2.setVisibility(0);
        RecyclerItemViewClickListener recyclerItemViewClickListener = this$0.rvlistenner;
        Intrinsics.checkNotNull(recyclerItemViewClickListener);
        recyclerItemViewClickListener.OnItemLongClickListener(holder.itemView, layoutPosition);
        return true;
    }

    public final FileNormalListResult.Item getItemData(int position) {
        FileListResultBean fileListResultBean = this.data;
        Intrinsics.checkNotNull(fileListResultBean);
        FileNormalListResult.Item item = fileListResultBean.getData().get(position);
        Intrinsics.checkNotNullExpressionValue(item, "data!!.data[position]");
        return item;
    }

    public final List<FileNormalListResult.Item> getAllItemData() {
        FileListResultBean fileListResultBean = this.data;
        Intrinsics.checkNotNull(fileListResultBean);
        List<FileNormalListResult.Item> data = fileListResultBean.getData();
        Intrinsics.checkNotNullExpressionValue(data, "data!!.data");
        return data;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (this.data == null) {
            return 0;
        }
        String str = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("getItemCount data.size()= ");
        FileListResultBean fileListResultBean = this.data;
        Intrinsics.checkNotNull(fileListResultBean);
        sb.append(fileListResultBean.getData().size());
        LogUtils2.logI(str, sb.toString());
        FileListResultBean fileListResultBean2 = this.data;
        Intrinsics.checkNotNull(fileListResultBean2);
        return fileListResultBean2.getData().size();
    }

    /* compiled from: NormalFragmentAdapter.kt */
    @Metadata(m530d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, m531d2 = {"Lcom/autolink/dvr/ui/file/NormalFragmentAdapter$MyViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/autolink/dvr/databinding/ItemListNormalBinding;", "(Lcom/autolink/dvr/ui/file/NormalFragmentAdapter;Lcom/autolink/dvr/databinding/ItemListNormalBinding;)V", "getBinding", "()Lcom/autolink/dvr/databinding/ItemListNormalBinding;", "bind", "", "resultItem", "Lcom/autolink/dvr/model/FileNormalListResult$Item;", "viewModel", "Lcom/autolink/dvr/viewmodel/FileViewModel;", "ALDVR_T1JRelease"}, m532k = 1, m533mv = {1, 8, 0}, m535xi = 48)
    public final class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemListNormalBinding binding;
        final /* synthetic */ NormalFragmentAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MyViewHolder(NormalFragmentAdapter normalFragmentAdapter, ItemListNormalBinding binding) {
            super(binding.getRoot());
            Intrinsics.checkNotNullParameter(binding, "binding");
            this.this$0 = normalFragmentAdapter;
            LogUtils2.logI(normalFragmentAdapter.TAG, "MyViewHolder ");
            this.binding = binding;
        }

        public final ItemListNormalBinding getBinding() {
            return this.binding;
        }

        public final void bind(FileNormalListResult.Item resultItem, FileViewModel viewModel) {
            LogUtils2.logI(this.this$0.TAG, "bind ");
            this.binding.setResultItem(resultItem);
            this.binding.setViewModel(viewModel);
        }
    }

    public final List<FileNormalListResult.Item> getData() {
        FileListResultBean fileListResultBean = this.data;
        Intrinsics.checkNotNull(fileListResultBean);
        List<FileNormalListResult.Item> data = fileListResultBean.getData();
        Intrinsics.checkNotNullExpressionValue(data, "data!!.data");
        return data;
    }

    public final void setData(FileListResultBean data) {
        LogUtils2.logI(this.TAG, "setData ");
        this.data = data;
        notifyDataSetChanged();
    }
}
