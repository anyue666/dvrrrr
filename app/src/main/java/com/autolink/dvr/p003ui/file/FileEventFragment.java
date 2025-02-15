package com.autolink.dvr.p003ui.file;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.autolink.buryservice.BuryServiceManager;
import com.autolink.buryservice.bury.consts.BuryEventConst;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.common.base.BaseMvvmFragment;
import com.autolink.dvr.common.can.CanOperation;
import com.autolink.dvr.common.media.utils.FileDataGroupingUtils;
import com.autolink.dvr.common.media.utils.FileUtils;
import com.autolink.dvr.common.utils.LogUtils;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.databinding.FragmentFileNormalBinding;
import com.autolink.dvr.model.FileNormalListResult;
import com.autolink.dvr.p003ui.VideoActivity;
import com.autolink.dvr.p003ui.file.FileDataAdapter;
import com.autolink.dvr.p003ui.view.DvrRecyclerView;
import com.autolink.dvr.viewmodel.FileViewModel;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.cli.HelpFormatter;
import com.michaelflisar.dragselectrecyclerview.DragSelectTouchListener;
import com.michaelflisar.dragselectrecyclerview.DragSelectionProcessor;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes.dex */
public class FileEventFragment extends BaseMvvmFragment<FileViewModel, FragmentFileNormalBinding> {
    public static int ACTIVITY_FOR_RESULT_VIDEO_CODE = 202;
    public static List<FileNormalListResult.Item> allData;
    private FileDataAdapter mFileActivityAdapter;
    private DvrRecyclerView mRecyclerView;
    private final String TAG = LogUtils.DEFAULT_TAG + getClass().getSimpleName();
    private DragSelectionProcessor.Mode mMode = DragSelectionProcessor.Mode.Simple;
    DragSelectionProcessor mDragSelectionProcessor = new DragSelectionProcessor(new DragSelectionProcessor.ISelectionHandler() { // from class: com.autolink.dvr.ui.file.FileEventFragment.3
        C09633() {
        }

        @Override // com.michaelflisar.dragselectrecyclerview.DragSelectionProcessor.ISelectionHandler
        public HashSet<Integer> getSelection() {
            return FileEventFragment.this.mFileActivityAdapter.getSelection();
        }

        @Override // com.michaelflisar.dragselectrecyclerview.DragSelectionProcessor.ISelectionHandler
        public boolean isSelected(int i) {
            return FileEventFragment.this.mFileActivityAdapter.getSelection().contains(Integer.valueOf(i));
        }

        @Override // com.michaelflisar.dragselectrecyclerview.DragSelectionProcessor.ISelectionHandler
        public void updateSelection(int i, int i2, boolean z, boolean z2) {
            LogUtils2.logI(FileEventFragment.this.TAG, "start end " + i + HelpFormatter.DEFAULT_LONG_OPT_SEPARATOR + i2);
            FileEventFragment.this.mFileActivityAdapter.selectRange(i, i2, z);
        }
    }).withMode(this.mMode);

    @Override // com.autolink.dvr.common.base.BaseMvvmFragment
    protected int getLayoutId() {
        return C0903R.layout.fragment_file_normal;
    }

    @Override // com.autolink.dvr.common.base.BaseMvvmFragment, com.autolink.dvr.common.base.hmi.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        LogUtils2.logI(this.TAG, "onCreateView");
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseFragment, com.autolink.dvr.common.base.hmi.IBaseView
    public void initView() {
        LogUtils2.logI(this.TAG, "initView");
        this.mRecyclerView = (DvrRecyclerView) findViewById(C0903R.id.fragment_file_normal_recyclerview);
        initData();
        ((FileViewModel) this.mViewModel).resultList.observe(this, new Observer<List<FileNormalListResult.Item>>() { // from class: com.autolink.dvr.ui.file.FileEventFragment.1
            C09611() {
            }

            @Override // androidx.lifecycle.Observer
            public void onChanged(List<FileNormalListResult.Item> list) {
                if (list == null) {
                    LogUtils2.logI(FileEventFragment.this.TAG, "getResultList normal data is null");
                    return;
                }
                Collections.reverse(list);
                LogUtils2.logI(FileEventFragment.this.TAG, "Thread.name " + Thread.currentThread().getName());
                if (FileEventFragment.this.isDisplayMode) {
                    FileUtils.sortByTime(list);
                    FileEventFragment.this.mFileActivityAdapter.unifiedData(list);
                } else {
                    FileEventFragment.this.mFileActivityAdapter.unifiedData(FileDataGroupingUtils.setDataSize(FileDataGroupingUtils.setData(list)));
                }
            }
        });
    }

    /* renamed from: com.autolink.dvr.ui.file.FileEventFragment$1 */
    class C09611 implements Observer<List<FileNormalListResult.Item>> {
        C09611() {
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(List<FileNormalListResult.Item> list) {
            if (list == null) {
                LogUtils2.logI(FileEventFragment.this.TAG, "getResultList normal data is null");
                return;
            }
            Collections.reverse(list);
            LogUtils2.logI(FileEventFragment.this.TAG, "Thread.name " + Thread.currentThread().getName());
            if (FileEventFragment.this.isDisplayMode) {
                FileUtils.sortByTime(list);
                FileEventFragment.this.mFileActivityAdapter.unifiedData(list);
            } else {
                FileEventFragment.this.mFileActivityAdapter.unifiedData(FileDataGroupingUtils.setDataSize(FileDataGroupingUtils.setData(list)));
            }
        }
    }

    /* renamed from: com.autolink.dvr.ui.file.FileEventFragment$2 */
    class RunnableC09622 implements Runnable {
        RunnableC09622() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ((FileViewModel) FileEventFragment.this.mViewModel).getResultList(false);
        }
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseFragment, com.autolink.dvr.common.base.hmi.IBaseView
    public void initViewObservable() {
        if (this.mViewModel != 0) {
            new Thread(new Runnable() { // from class: com.autolink.dvr.ui.file.FileEventFragment.2
                RunnableC09622() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    ((FileViewModel) FileEventFragment.this.mViewModel).getResultList(false);
                }
            }).start();
        }
    }

    public void clearSelection() {
        for (int i = 0; i < this.mFileActivityAdapter.getDataItem().size(); i++) {
            try {
                if (this.mFileActivityAdapter.getDataItem().get(i).isChoose()) {
                    this.mFileActivityAdapter.getDataItem().get(i).setChoose(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        this.mFileActivityAdapter.getSelection().clear();
    }

    /* renamed from: com.autolink.dvr.ui.file.FileEventFragment$3 */
    class C09633 implements DragSelectionProcessor.ISelectionHandler {
        C09633() {
        }

        @Override // com.michaelflisar.dragselectrecyclerview.DragSelectionProcessor.ISelectionHandler
        public HashSet<Integer> getSelection() {
            return FileEventFragment.this.mFileActivityAdapter.getSelection();
        }

        @Override // com.michaelflisar.dragselectrecyclerview.DragSelectionProcessor.ISelectionHandler
        public boolean isSelected(int i) {
            return FileEventFragment.this.mFileActivityAdapter.getSelection().contains(Integer.valueOf(i));
        }

        @Override // com.michaelflisar.dragselectrecyclerview.DragSelectionProcessor.ISelectionHandler
        public void updateSelection(int i, int i2, boolean z, boolean z2) {
            LogUtils2.logI(FileEventFragment.this.TAG, "start end " + i + HelpFormatter.DEFAULT_LONG_OPT_SEPARATOR + i2);
            FileEventFragment.this.mFileActivityAdapter.selectRange(i, i2, z);
        }
    }

    public void setAllSelection() {
        FileDataAdapter fileDataAdapter = this.mFileActivityAdapter;
        if (fileDataAdapter != null) {
            fileDataAdapter.setAllSelection();
        }
    }

    public void setCleanSelection() {
        FileDataAdapter fileDataAdapter = this.mFileActivityAdapter;
        if (fileDataAdapter != null) {
            fileDataAdapter.setCleanSelection();
        }
    }

    public int getChooseNum() {
        try {
            FileDataAdapter fileDataAdapter = this.mFileActivityAdapter;
            if (fileDataAdapter != null) {
                return fileDataAdapter.getSelection().size();
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override // com.autolink.dvr.common.base.BaseMvvmFragment, com.autolink.dvr.common.base.hmi.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mFileActivityAdapter.stopExecutors();
    }

    private void initData() {
        FileDataAdapter fileDataAdapter = new FileDataAdapter();
        this.mFileActivityAdapter = fileDataAdapter;
        fileDataAdapter.setFragment(this);
        DragSelectTouchListener withSelectListener = new DragSelectTouchListener().withSelectListener(this.mDragSelectionProcessor);
        this.mRecyclerView.addOnItemTouchListener(withSelectListener);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        this.mRecyclerView.setAdapter(this.mFileActivityAdapter);
        this.mFileActivityAdapter.setViewClickListener(new FileDataAdapter.RecyclerItemViewClickListener() { // from class: com.autolink.dvr.ui.file.FileEventFragment.4
            final /* synthetic */ DragSelectTouchListener val$dragSelectTouchListener;

            C09644(DragSelectTouchListener withSelectListener2) {
                r2 = withSelectListener2;
            }

            @Override // com.autolink.dvr.ui.file.FileDataAdapter.RecyclerItemViewClickListener
            public void OnItemClickListener(View view, int i) {
                if (!CanOperation.isSpeedOver15) {
                    LogUtils2.logI(FileEventFragment.this.TAG, "OnItemClickListener position = " + i);
                    FileEventFragment.allData = FileEventFragment.this.mFileActivityAdapter.getDataItem();
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", i);
                    bundle.putString("playtype", "event");
                    FileEventFragment.this.startActivityForResult(VideoActivity.class, bundle, FileEventFragment.ACTIVITY_FOR_RESULT_VIDEO_CODE);
                } else {
                    Toast.makeText(DVRApplication.getInstance(), C0903R.string.main_cover_speed, 0).show();
                }
                BuryServiceManager.getInstance().panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_FileManager_LookBack, null, 0);
            }

            @Override // com.autolink.dvr.ui.file.FileDataAdapter.RecyclerItemViewClickListener
            public void OnItemLongClickListener(View view, int i) {
                r2.startDragSelection(i);
            }
        });
    }

    /* renamed from: com.autolink.dvr.ui.file.FileEventFragment$4 */
    class C09644 implements FileDataAdapter.RecyclerItemViewClickListener {
        final /* synthetic */ DragSelectTouchListener val$dragSelectTouchListener;

        C09644(DragSelectTouchListener withSelectListener2) {
            r2 = withSelectListener2;
        }

        @Override // com.autolink.dvr.ui.file.FileDataAdapter.RecyclerItemViewClickListener
        public void OnItemClickListener(View view, int i) {
            if (!CanOperation.isSpeedOver15) {
                LogUtils2.logI(FileEventFragment.this.TAG, "OnItemClickListener position = " + i);
                FileEventFragment.allData = FileEventFragment.this.mFileActivityAdapter.getDataItem();
                Bundle bundle = new Bundle();
                bundle.putInt("position", i);
                bundle.putString("playtype", "event");
                FileEventFragment.this.startActivityForResult(VideoActivity.class, bundle, FileEventFragment.ACTIVITY_FOR_RESULT_VIDEO_CODE);
            } else {
                Toast.makeText(DVRApplication.getInstance(), C0903R.string.main_cover_speed, 0).show();
            }
            BuryServiceManager.getInstance().panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_FileManager_LookBack, null, 0);
        }

        @Override // com.autolink.dvr.ui.file.FileDataAdapter.RecyclerItemViewClickListener
        public void OnItemLongClickListener(View view, int i) {
            r2.startDragSelection(i);
        }
    }

    public List<FileNormalListResult.Item> getItemList() {
        return this.mFileActivityAdapter.getDataItem();
    }

    public int getItemDataNumber() {
        if (this.mFileActivityAdapter == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.mFileActivityAdapter.getDataItem().size(); i2++) {
            if (!this.mFileActivityAdapter.getDataItem().get(i2).isFalseData()) {
                i++;
            }
        }
        return i;
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        LogUtils2.logI(this.TAG, "onResume");
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == ACTIVITY_FOR_RESULT_VIDEO_CODE) {
            initViewObservable();
            allData = null;
        }
    }
}
