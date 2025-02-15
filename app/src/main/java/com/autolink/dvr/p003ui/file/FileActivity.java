package com.autolink.dvr.p003ui.file;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.TooltipCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.autolink.buryservice.BuryServiceManager;
import com.autolink.buryservice.bury.consts.BuryEventConst;
import com.autolink.dvr.C0903R;
import com.autolink.dvr.DVRApplication;
import com.autolink.dvr.bean.EventBusCheckBox;
import com.autolink.dvr.bean.EventBusLongClick;
import com.autolink.dvr.bean.EventBusUpdateQuantity;
import com.autolink.dvr.common.base.BaseMvvmActivity;
import com.autolink.dvr.common.receiver.USBReceiver;
import com.autolink.dvr.common.utils.Check;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.RotateDownPageTransformer;
import com.autolink.dvr.databinding.ActivityFileBinding;
import com.autolink.dvr.model.FileNormalListResult;
import com.autolink.dvr.p003ui.setting.DeleteVideoWindow;
import com.autolink.dvr.p003ui.setting.DeletingWindow;
import com.autolink.dvr.viewmodel.FileViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpStatus;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes.dex */
public class FileActivity extends BaseMvvmActivity<FileViewModel, ActivityFileBinding> implements TabLayout.OnTabSelectedListener {
    private static final int DELETE_FILE = 1;
    private static final int REFRESH_UI = 2;
    private FileFragmentAdapter fileFragmentAdapter;
    List<Fragment> fragments;
    private long lastClickTime;
    private DeleteVideoWindow mDeleteVideoDialog;
    private DeletingWindow mDeletingWindow;
    private HandlerThread mHandlerThread;
    private Handler mMainHandler;
    private WorkHandler mWorkHandler;
    private List<String> pathList;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private final String TAG = LogUtils2.DEFAULT_TAG + getClass().getSimpleName();
    private final int CLICK_CHECK_TIME = HttpStatus.SC_INTERNAL_SERVER_ERROR;
    BroadcastReceiver receiver = new BroadcastReceiver() { // from class: com.autolink.dvr.ui.file.FileActivity.3
        C09543() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            LogUtils2.logI(FileActivity.this.TAG, "onReceive  " + intent.getAction());
            FileActivity.this.finish();
        }
    };

    @Override // com.autolink.dvr.common.base.BaseMvvmActivity
    protected int getLayoutId() {
        return C0903R.layout.activity_file;
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseActivity, com.autolink.dvr.common.base.hmi.IBaseView
    public void initViewObservable() {
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
    }

    @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseActivity, com.autolink.dvr.common.base.hmi.IBaseView
    @Subscribe
    public void initView() {
        LogUtils2.logI(this.TAG, "initView ");
        this.viewPager = (ViewPager) findViewById(C0903R.id.activity_file_content_vp);
        this.tabLayout = (TabLayout) findViewById(C0903R.id.activity_file_top_tb);
        this.mMainHandler = new Handler(Looper.getMainLooper()) { // from class: com.autolink.dvr.ui.file.FileActivity.1
            HandlerC09511(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what != 2) {
                    return;
                }
                LogUtils2.logI(FileActivity.this.TAG, "delete finished, refresh ui");
                FileActivity.this.refreshUI();
            }
        };
        HandlerThread handlerThread = new HandlerThread("FileActivity_handlerThread");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mWorkHandler = new WorkHandler(this.mHandlerThread.getLooper(), this);
        ArrayList arrayList = new ArrayList();
        this.fragments = arrayList;
        arrayList.add(new FileNormalFragment());
        this.fragments.add(new FileEventFragment());
        FileFragmentAdapter fileFragmentAdapter = new FileFragmentAdapter(getSupportFragmentManager(), 0);
        this.fileFragmentAdapter = fileFragmentAdapter;
        fileFragmentAdapter.setFragmentList(this.fragments);
        this.viewPager.setAdapter(this.fileFragmentAdapter);
        this.viewPager.setOffscreenPageLimit(this.fragments.size());
        this.viewPager.setPageTransformer(true, new RotateDownPageTransformer());
        this.tabLayout.setupWithViewPager(this.viewPager);
        for (int i = 0; i < this.tabLayout.getTabCount(); i++) {
            if (this.tabLayout.getTabAt(i) != null) {
                TabLayout.Tab tabAt = this.tabLayout.getTabAt(i);
                Objects.requireNonNull(tabAt);
                TooltipCompat.setTooltipText(tabAt.view, null);
            }
        }
        this.tabLayout.addOnTabSelectedListener((TabLayout.OnTabSelectedListener) this);
        EventBus.getDefault().register(this);
        click();
        registerUsbState();
        this.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.autolink.dvr.ui.file.FileActivity.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float f, int i3) {
            }

            C09532() {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) {
                FileActivity.this.updateSelectIcon();
            }
        });
    }

    /* renamed from: com.autolink.dvr.ui.file.FileActivity$1 */
    class HandlerC09511 extends Handler {
        HandlerC09511(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 2) {
                return;
            }
            LogUtils2.logI(FileActivity.this.TAG, "delete finished, refresh ui");
            FileActivity.this.refreshUI();
        }
    }

    /* renamed from: com.autolink.dvr.ui.file.FileActivity$2 */
    class C09532 implements ViewPager.OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i2) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i2, float f, int i3) {
        }

        C09532() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i2) {
            FileActivity.this.updateSelectIcon();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusMessage(EventBusLongClick eventBusLongClick) {
        enterEdit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusMessage(EventBusUpdateQuantity eventBusUpdateQuantity) {
        setSelectNumber();
        updateSelectIcon();
    }

    public int setSelectNumber() {
        int i;
        Fragment fragment;
        Fragment fragment2;
        Fragment fragment3 = this.fragments.get(this.viewPager.getCurrentItem());
        if (fragment3 != null) {
            i = (!(fragment3 instanceof FileNormalFragment) || (fragment2 = this.fragments.get(0)) == null || fragment2.isDetached() || !(fragment2 instanceof FileNormalFragment)) ? 0 : ((FileNormalFragment) fragment2).getChooseNum();
            if ((fragment3 instanceof FileEventFragment) && (fragment = this.fragments.get(1)) != null && !fragment.isDetached() && (fragment instanceof FileEventFragment)) {
                i += ((FileEventFragment) fragment).getChooseNum();
            }
        } else {
            i = 0;
        }
        ((ActivityFileBinding) this.mBinding).activityFileContentTv.setText(String.format(getString(C0903R.string.choose_value), Integer.valueOf(i)));
        return i;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        DeleteVideoWindow deleteVideoWindow = this.mDeleteVideoDialog;
        if (deleteVideoWindow != null) {
            deleteVideoWindow.finish();
            this.mDeleteVideoDialog = null;
        }
        DeletingWindow deletingWindow = this.mDeletingWindow;
        if (deletingWindow != null) {
            deletingWindow.finish();
            this.mDeletingWindow = null;
        }
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        LogUtils2.logI(this.TAG, "onDestroy ");
        EventBus.getDefault().unregister(this);
        unregisterReceiver(this.receiver);
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
        }
    }

    private void selectAllOrCleanFile() {
        Fragment fragment = this.fragments.get(this.viewPager.getCurrentItem());
        if (fragment != null) {
            if (fragment instanceof FileNormalFragment) {
                setListStatus(((FileNormalFragment) fragment).getItemList(), 1);
            }
            if (fragment instanceof FileEventFragment) {
                setListStatus(((FileEventFragment) fragment).getItemList(), 2);
            }
        }
    }

    private void setListStatus(List<FileNormalListResult.Item> list, int i) {
        boolean z;
        if (list != null) {
            int i2 = 0;
            while (true) {
                if (i2 >= list.size()) {
                    z = true;
                    break;
                } else {
                    if (!list.get(i2).isChoose()) {
                        z = false;
                        break;
                    }
                    i2++;
                }
            }
            for (int i3 = 0; i3 < list.size(); i3++) {
                list.get(i3).setChoose(!z);
            }
            if (!z) {
                setSelectedStatus(i, true);
            } else {
                setSelectedStatus(i, false);
            }
        }
    }

    private void setSelectedStatus(int i, boolean z) {
        Fragment fragment;
        Fragment fragment2;
        if (i == 1 && (fragment2 = this.fragments.get(0)) != null && !fragment2.isDetached() && (fragment2 instanceof FileNormalFragment)) {
            if (z) {
                ((FileNormalFragment) fragment2).setAllSelection();
            } else {
                ((FileNormalFragment) fragment2).clearSelection();
            }
        }
        if (i != 2 || (fragment = this.fragments.get(1)) == null || fragment.isDetached() || !(fragment instanceof FileEventFragment)) {
            return;
        }
        if (z) {
            ((FileEventFragment) fragment).setAllSelection();
        } else {
            ((FileEventFragment) fragment).clearSelection();
        }
    }

    public void updateSelectIcon() {
        boolean z;
        int i;
        Fragment fragment;
        Fragment fragment2;
        Fragment fragment3 = this.fragments.get(this.viewPager.getCurrentItem());
        if (fragment3 != null) {
            if (!(fragment3 instanceof FileNormalFragment) || (fragment2 = this.fragments.get(0)) == null || fragment2.isDetached() || !(fragment2 instanceof FileNormalFragment)) {
                z = false;
                i = 0;
            } else {
                FileNormalFragment fileNormalFragment = (FileNormalFragment) fragment2;
                i = fileNormalFragment.getChooseNum();
                z = i != 0 && i == fileNormalFragment.getItemDataNumber();
            }
            if ((fragment3 instanceof FileEventFragment) && (fragment = this.fragments.get(1)) != null && !fragment.isDetached() && (fragment instanceof FileEventFragment)) {
                FileEventFragment fileEventFragment = (FileEventFragment) fragment;
                i += fileEventFragment.getChooseNum();
                if (i != 0 && i == fileEventFragment.getItemDataNumber()) {
                    z = true;
                }
            }
        } else {
            z = false;
            i = 0;
        }
        setSelectNumber();
        if (z) {
            ((ActivityFileBinding) this.mBinding).activityFileLlTop.activityFileTopMultipleIv.setImageDrawable(getDrawable(C0903R.drawable.ic_sa_press));
        } else {
            ((ActivityFileBinding) this.mBinding).activityFileLlTop.activityFileTopMultipleIv.setImageDrawable(getDrawable(C0903R.drawable.ic_sa_nor));
        }
        if (i != 0) {
            ((ActivityFileBinding) this.mBinding).activityFileLlTop.activityFileTopDeleteIv.setImageDrawable(getResources().getDrawable(C0903R.drawable.ic_delete_press));
            ((ActivityFileBinding) this.mBinding).activityFileContentTv.setVisibility(0);
            ((ActivityFileBinding) this.mBinding).activityFileLlTop.activityFileTopDeleteIv.setSoundEffectsEnabled(true);
            return;
        }
        exitEdit();
    }

    public void exitOrEnterEdit() {
        Fragment fragment = this.fragments.get(this.viewPager.getCurrentItem());
        boolean z = true;
        if (fragment != null) {
            z = fragment instanceof FileEventFragment ? ((FileEventFragment) this.fragments.get(1)).isEditState : fragment instanceof FileNormalFragment ? ((FileNormalFragment) this.fragments.get(0)).isEditState : true;
        }
        if (!z) {
            enterEdit();
        }
        selectAllOrCleanFile();
        updateSelectIcon();
        EventBus.getDefault().post(new EventBusCheckBox());
    }

    public void enterEdit() {
        Fragment fragment = this.fragments.get(this.viewPager.getCurrentItem());
        if (fragment != null) {
            if (fragment instanceof FileNormalFragment) {
                ((FileNormalFragment) this.fragments.get(0)).isEditState = true;
            }
            if (fragment instanceof FileEventFragment) {
                ((FileEventFragment) this.fragments.get(1)).isEditState = true;
            }
        }
    }

    public void exitEdit() {
        Fragment fragment = this.fragments.get(this.viewPager.getCurrentItem());
        if (fragment != null) {
            if (fragment instanceof FileNormalFragment) {
                FileNormalFragment fileNormalFragment = (FileNormalFragment) this.fragments.get(0);
                fileNormalFragment.isEditState = false;
                fileNormalFragment.clearSelection();
            }
            if (fragment instanceof FileEventFragment) {
                FileEventFragment fileEventFragment = (FileEventFragment) this.fragments.get(1);
                fileEventFragment.isEditState = false;
                fileEventFragment.clearSelection();
            }
        }
        EventBus.getDefault().post(new EventBusCheckBox());
        ((ActivityFileBinding) this.mBinding).activityFileLlTop.activityFileTopDeleteIv.setImageDrawable(getResources().getDrawable(C0903R.drawable.ic_delete));
        ((ActivityFileBinding) this.mBinding).activityFileLlTop.activityFileTopMultipleIv.setImageDrawable(getDrawable(C0903R.drawable.ic_sa_nor));
        ((ActivityFileBinding) this.mBinding).activityFileContentTv.setVisibility(8);
        ((ActivityFileBinding) this.mBinding).activityFileLlTop.activityFileTopDeleteIv.setSoundEffectsEnabled(false);
    }

    /* renamed from: com.autolink.dvr.ui.file.FileActivity$3 */
    class C09543 extends BroadcastReceiver {
        C09543() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            LogUtils2.logI(FileActivity.this.TAG, "onReceive  " + intent.getAction());
            FileActivity.this.finish();
        }
    }

    private void registerUsbState() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(USBReceiver.USB_STATUS);
        registerReceiver(this.receiver, intentFilter);
    }

    /* renamed from: com.autolink.dvr.ui.file.FileActivity$4 */
    class ViewOnClickListenerC09554 implements View.OnClickListener {
        ViewOnClickListenerC09554() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LogUtils2.logI(FileActivity.this.TAG, "onClick activityFileTopBackIv");
            FileActivity.this.finish();
            BuryServiceManager.getInstance().panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_FileManager_Quit, null, 0);
        }
    }

    private void click() {
        ((ActivityFileBinding) this.mBinding).activityFileLlTop.activityFileTopBackIv.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.file.FileActivity.4
            ViewOnClickListenerC09554() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LogUtils2.logI(FileActivity.this.TAG, "onClick activityFileTopBackIv");
                FileActivity.this.finish();
                BuryServiceManager.getInstance().panoramicImageDVRBury(BuryEventConst.CH_BI_Event_540PanoramicImage_DVR_FileManager_Quit, null, 0);
            }
        });
        ((ActivityFileBinding) this.mBinding).activityFileLlTop.activityFileTopMultipleIv.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.file.FileActivity.5
            ViewOnClickListenerC09565() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FileActivity.this.exitOrEnterEdit();
            }
        });
        ((ActivityFileBinding) this.mBinding).activityFileLlTop.activityFileTopMoreIv.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.file.FileActivity.6
            ViewOnClickListenerC09576() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FileActivity.this.showPopWindow();
            }
        });
        ((ActivityFileBinding) this.mBinding).viewSwitchLayout.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.file.FileActivity.7
            ViewOnClickListenerC09587() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Fragment fragment = FileActivity.this.fragments.get(FileActivity.this.viewPager.getCurrentItem());
                if (fragment != null) {
                    if (fragment instanceof FileNormalFragment) {
                        FileNormalFragment fileNormalFragment = (FileNormalFragment) FileActivity.this.fragments.get(0);
                        if (!fileNormalFragment.isDisplayMode) {
                            fileNormalFragment.isDisplayMode = true;
                            FileActivity.this.exitEdit();
                            FileActivity.this.switchDisplay();
                        }
                    }
                    if (fragment instanceof FileEventFragment) {
                        FileEventFragment fileEventFragment = (FileEventFragment) FileActivity.this.fragments.get(1);
                        if (fileEventFragment.isDisplayMode) {
                            return;
                        }
                        fileEventFragment.isDisplayMode = true;
                        FileActivity.this.exitEdit();
                        FileActivity.this.switchDisplay();
                    }
                }
            }
        });
        ((ActivityFileBinding) this.mBinding).tipsLayoutBg.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.file.FileActivity.8
            ViewOnClickListenerC09598() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActivityFileBinding) FileActivity.this.mBinding).tipsLayoutBg.setVisibility(8);
                ((ActivityFileBinding) FileActivity.this.mBinding).activityFileLlTop.activityFileTopMoreIv.setImageDrawable(FileActivity.this.getDrawable(C0903R.drawable.ic_list_nor));
            }
        });
        ((ActivityFileBinding) this.mBinding).listSwitchLayout.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.file.FileActivity.9
            ViewOnClickListenerC09609() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Fragment fragment = FileActivity.this.fragments.get(FileActivity.this.viewPager.getCurrentItem());
                if (fragment != null) {
                    if (fragment instanceof FileNormalFragment) {
                        FileNormalFragment fileNormalFragment = (FileNormalFragment) FileActivity.this.fragments.get(0);
                        if (fileNormalFragment.isDisplayMode) {
                            fileNormalFragment.isDisplayMode = false;
                            FileActivity.this.exitEdit();
                            FileActivity.this.switchDisplay();
                        }
                    }
                    if (fragment instanceof FileEventFragment) {
                        FileEventFragment fileEventFragment = (FileEventFragment) FileActivity.this.fragments.get(1);
                        if (fileEventFragment.isDisplayMode) {
                            fileEventFragment.isDisplayMode = false;
                            FileActivity.this.exitEdit();
                            FileActivity.this.switchDisplay();
                        }
                    }
                }
            }
        });
        ((ActivityFileBinding) this.mBinding).activityFileLlTop.activityFileTopDeleteIv.setOnClickListener(new View.OnClickListener() { // from class: com.autolink.dvr.ui.file.FileActivity.10
            ViewOnClickListenerC095210() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (FileActivity.this.isFastClick()) {
                    LogUtils2.logI(FileActivity.this.TAG, "mBinding.activityFileLlTop.activityFileTopDeleteIv return ");
                    return;
                }
                int selectNumber = FileActivity.this.setSelectNumber();
                if (selectNumber == 0) {
                    return;
                }
                FileActivity.this.mDeleteVideoDialog = new DeleteVideoWindow(FileActivity.this);
                FileActivity.this.mDeleteVideoDialog.setDeleteNumber(selectNumber);
                FileActivity.this.mDeleteVideoDialog.setOnClickListener(new DeleteVideoWindow.ClickListener() { // from class: com.autolink.dvr.ui.file.FileActivity.10.1
                    AnonymousClass1() {
                    }

                    @Override // com.autolink.dvr.ui.setting.DeleteVideoWindow.ClickListener
                    public void onConfirm() {
                        FileActivity.this.mDeleteVideoDialog.finish();
                        FileActivity.this.mDeleteVideoDialog = null;
                        FileActivity.this.deleteSelectVideoFile();
                        FileActivity.this.exitEdit();
                    }
                });
            }

            /* renamed from: com.autolink.dvr.ui.file.FileActivity$10$1 */
            class AnonymousClass1 implements DeleteVideoWindow.ClickListener {
                AnonymousClass1() {
                }

                @Override // com.autolink.dvr.ui.setting.DeleteVideoWindow.ClickListener
                public void onConfirm() {
                    FileActivity.this.mDeleteVideoDialog.finish();
                    FileActivity.this.mDeleteVideoDialog = null;
                    FileActivity.this.deleteSelectVideoFile();
                    FileActivity.this.exitEdit();
                }
            }
        });
    }

    /* renamed from: com.autolink.dvr.ui.file.FileActivity$5 */
    class ViewOnClickListenerC09565 implements View.OnClickListener {
        ViewOnClickListenerC09565() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            FileActivity.this.exitOrEnterEdit();
        }
    }

    /* renamed from: com.autolink.dvr.ui.file.FileActivity$6 */
    class ViewOnClickListenerC09576 implements View.OnClickListener {
        ViewOnClickListenerC09576() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            FileActivity.this.showPopWindow();
        }
    }

    /* renamed from: com.autolink.dvr.ui.file.FileActivity$7 */
    class ViewOnClickListenerC09587 implements View.OnClickListener {
        ViewOnClickListenerC09587() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Fragment fragment = FileActivity.this.fragments.get(FileActivity.this.viewPager.getCurrentItem());
            if (fragment != null) {
                if (fragment instanceof FileNormalFragment) {
                    FileNormalFragment fileNormalFragment = (FileNormalFragment) FileActivity.this.fragments.get(0);
                    if (!fileNormalFragment.isDisplayMode) {
                        fileNormalFragment.isDisplayMode = true;
                        FileActivity.this.exitEdit();
                        FileActivity.this.switchDisplay();
                    }
                }
                if (fragment instanceof FileEventFragment) {
                    FileEventFragment fileEventFragment = (FileEventFragment) FileActivity.this.fragments.get(1);
                    if (fileEventFragment.isDisplayMode) {
                        return;
                    }
                    fileEventFragment.isDisplayMode = true;
                    FileActivity.this.exitEdit();
                    FileActivity.this.switchDisplay();
                }
            }
        }
    }

    /* renamed from: com.autolink.dvr.ui.file.FileActivity$8 */
    class ViewOnClickListenerC09598 implements View.OnClickListener {
        ViewOnClickListenerC09598() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ((ActivityFileBinding) FileActivity.this.mBinding).tipsLayoutBg.setVisibility(8);
            ((ActivityFileBinding) FileActivity.this.mBinding).activityFileLlTop.activityFileTopMoreIv.setImageDrawable(FileActivity.this.getDrawable(C0903R.drawable.ic_list_nor));
        }
    }

    /* renamed from: com.autolink.dvr.ui.file.FileActivity$9 */
    class ViewOnClickListenerC09609 implements View.OnClickListener {
        ViewOnClickListenerC09609() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Fragment fragment = FileActivity.this.fragments.get(FileActivity.this.viewPager.getCurrentItem());
            if (fragment != null) {
                if (fragment instanceof FileNormalFragment) {
                    FileNormalFragment fileNormalFragment = (FileNormalFragment) FileActivity.this.fragments.get(0);
                    if (fileNormalFragment.isDisplayMode) {
                        fileNormalFragment.isDisplayMode = false;
                        FileActivity.this.exitEdit();
                        FileActivity.this.switchDisplay();
                    }
                }
                if (fragment instanceof FileEventFragment) {
                    FileEventFragment fileEventFragment = (FileEventFragment) FileActivity.this.fragments.get(1);
                    if (fileEventFragment.isDisplayMode) {
                        fileEventFragment.isDisplayMode = false;
                        FileActivity.this.exitEdit();
                        FileActivity.this.switchDisplay();
                    }
                }
            }
        }
    }

    /* renamed from: com.autolink.dvr.ui.file.FileActivity$10 */
    class ViewOnClickListenerC095210 implements View.OnClickListener {
        ViewOnClickListenerC095210() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (FileActivity.this.isFastClick()) {
                LogUtils2.logI(FileActivity.this.TAG, "mBinding.activityFileLlTop.activityFileTopDeleteIv return ");
                return;
            }
            int selectNumber = FileActivity.this.setSelectNumber();
            if (selectNumber == 0) {
                return;
            }
            FileActivity.this.mDeleteVideoDialog = new DeleteVideoWindow(FileActivity.this);
            FileActivity.this.mDeleteVideoDialog.setDeleteNumber(selectNumber);
            FileActivity.this.mDeleteVideoDialog.setOnClickListener(new DeleteVideoWindow.ClickListener() { // from class: com.autolink.dvr.ui.file.FileActivity.10.1
                AnonymousClass1() {
                }

                @Override // com.autolink.dvr.ui.setting.DeleteVideoWindow.ClickListener
                public void onConfirm() {
                    FileActivity.this.mDeleteVideoDialog.finish();
                    FileActivity.this.mDeleteVideoDialog = null;
                    FileActivity.this.deleteSelectVideoFile();
                    FileActivity.this.exitEdit();
                }
            });
        }

        /* renamed from: com.autolink.dvr.ui.file.FileActivity$10$1 */
        class AnonymousClass1 implements DeleteVideoWindow.ClickListener {
            AnonymousClass1() {
            }

            @Override // com.autolink.dvr.ui.setting.DeleteVideoWindow.ClickListener
            public void onConfirm() {
                FileActivity.this.mDeleteVideoDialog.finish();
                FileActivity.this.mDeleteVideoDialog = null;
                FileActivity.this.deleteSelectVideoFile();
                FileActivity.this.exitEdit();
            }
        }
    }

    public boolean isFastClick() {
        long currentTimeMillis = System.currentTimeMillis();
        boolean z = currentTimeMillis - this.lastClickTime < 500;
        this.lastClickTime = currentTimeMillis;
        LogUtils2.logI(this.TAG, "isFastClick = " + z);
        return z;
    }

    public void switchDisplay() {
        Fragment fragment = this.fragments.get(this.viewPager.getCurrentItem());
        if (fragment != null) {
            if (fragment instanceof FileNormalFragment) {
                ((FileNormalFragment) this.fragments.get(0)).initViewObservable();
            }
            if (fragment instanceof FileEventFragment) {
                ((FileEventFragment) this.fragments.get(1)).initViewObservable();
            }
        }
        setSelectNumber();
        setTipsView();
        ((ActivityFileBinding) this.mBinding).tipsLayoutBg.setVisibility(4);
        ((ActivityFileBinding) this.mBinding).activityFileLlTop.activityFileTopMoreIv.setImageDrawable(getDrawable(C0903R.drawable.ic_list_nor));
    }

    public void showPopWindow() {
        if (((ActivityFileBinding) this.mBinding).topTipsWindow.isShown()) {
            ((ActivityFileBinding) this.mBinding).tipsLayoutBg.setVisibility(4);
            ((ActivityFileBinding) this.mBinding).activityFileLlTop.activityFileTopMoreIv.setImageDrawable(getDrawable(C0903R.drawable.ic_list_nor));
        } else {
            setTipsView();
            ((ActivityFileBinding) this.mBinding).tipsLayoutBg.setVisibility(0);
            ((ActivityFileBinding) this.mBinding).activityFileLlTop.activityFileTopMoreIv.setImageDrawable(getDrawable(C0903R.drawable.ic_list_press));
        }
    }

    private void setTipsView() {
        Fragment fragment = this.fragments.get(this.viewPager.getCurrentItem());
        boolean z = true;
        if (fragment != null) {
            z = fragment instanceof FileEventFragment ? ((FileEventFragment) this.fragments.get(1)).isDisplayMode : fragment instanceof FileNormalFragment ? ((FileNormalFragment) this.fragments.get(0)).isDisplayMode : true;
        }
        if (z) {
            ((ActivityFileBinding) this.mBinding).viewSwitchLayout.setBackgroundDrawable(getDrawable(C0903R.drawable.top_pop_item_bg_shape));
            ((ActivityFileBinding) this.mBinding).tipsView.setTextColor(getColor(C0903R.color.file_color_05));
            ((ActivityFileBinding) this.mBinding).tipsPoint1.setBackgroundDrawable(getDrawable(C0903R.drawable.tips_point_select_shape));
            ((ActivityFileBinding) this.mBinding).listSwitchLayout.setBackground(null);
            ((ActivityFileBinding) this.mBinding).tipsList.setTextColor(getColor(C0903R.color.file_color_06));
            ((ActivityFileBinding) this.mBinding).tipsPoint2.setBackgroundDrawable(getDrawable(C0903R.drawable.tips_point_unselect_shape));
            return;
        }
        ((ActivityFileBinding) this.mBinding).listSwitchLayout.setBackgroundDrawable(getDrawable(C0903R.drawable.top_pop_item_bg_shape));
        ((ActivityFileBinding) this.mBinding).tipsList.setTextColor(getColor(C0903R.color.file_color_05));
        ((ActivityFileBinding) this.mBinding).tipsPoint2.setBackgroundDrawable(getDrawable(C0903R.drawable.tips_point_select_shape));
        ((ActivityFileBinding) this.mBinding).viewSwitchLayout.setBackground(null);
        ((ActivityFileBinding) this.mBinding).tipsView.setTextColor(getColor(C0903R.color.file_color_06));
        ((ActivityFileBinding) this.mBinding).tipsPoint1.setBackgroundDrawable(getDrawable(C0903R.drawable.tips_point_unselect_shape));
    }

    public void deleteSelectVideoFile() {
        List<FileNormalListResult.Item> list;
        Fragment fragment;
        Fragment fragment2;
        if (Check.isFastClick()) {
            this.mDeletingWindow = new DeletingWindow();
            Fragment fragment3 = this.fragments.get(this.viewPager.getCurrentItem());
            List<FileNormalListResult.Item> list2 = null;
            if (fragment3 != null) {
                List<FileNormalListResult.Item> itemList = (!(fragment3 instanceof FileNormalFragment) || (fragment2 = this.fragments.get(0)) == null || fragment2.isDetached() || !(fragment2 instanceof FileNormalFragment)) ? null : ((FileNormalFragment) fragment2).getItemList();
                if ((fragment3 instanceof FileEventFragment) && (fragment = this.fragments.get(1)) != null && !fragment.isDetached() && (fragment instanceof FileEventFragment)) {
                    list2 = ((FileEventFragment) fragment).getItemList();
                }
                list = list2;
                list2 = itemList;
            } else {
                list = null;
            }
            if (this.pathList == null) {
                this.pathList = new ArrayList();
            }
            this.pathList.clear();
            if (list2 != null) {
                for (int i = 0; i < list2.size(); i++) {
                    if (list2.get(i).isChoose() && !list2.get(i).isFalseData()) {
                        this.pathList.add(list2.get(i).getPath());
                    }
                }
            } else {
                LogUtils2.logI(this.TAG, "fragmentData_01 is null");
            }
            if (list != null) {
                for (int i2 = 0; i2 < list.size(); i2++) {
                    if (list.get(i2).isChoose() && !list.get(i2).isFalseData()) {
                        this.pathList.add(list.get(i2).getPath());
                    }
                }
            } else {
                LogUtils2.logI(this.TAG, "fragmentData_02 is null");
            }
            this.mWorkHandler.sendEmptyMessage(1);
        }
    }

    @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
    public void onTabSelected(TabLayout.Tab tab) {
        for (int i = 0; i < this.tabLayout.getTabCount(); i++) {
            if (this.tabLayout.getTabAt(i) != null && Build.VERSION.SDK_INT >= 26) {
                TabLayout.Tab tabAt = this.tabLayout.getTabAt(i);
                Objects.requireNonNull(tabAt);
                tabAt.view.setTooltipText(null);
            }
        }
    }

    public void refreshUI() {
        DeletingWindow deletingWindow = this.mDeletingWindow;
        if (deletingWindow != null && !deletingWindow.getIsRemoved()) {
            this.mDeletingWindow.finish();
        }
        Toast.makeText(DVRApplication.getInstance(), getString(C0903R.string.delete_complete, new Object[]{Integer.valueOf(this.pathList.size())}), 0).show();
        Fragment fragment = this.fragments.get(this.viewPager.getCurrentItem());
        if (fragment != null) {
            if (fragment instanceof FileNormalFragment) {
                ((FileNormalFragment) fragment).initViewObservable();
            }
            if (fragment instanceof FileEventFragment) {
                ((FileEventFragment) fragment).initViewObservable();
            }
        }
    }

    private static class WorkHandler extends Handler {
        private final String TAG;
        private final WeakReference<FileActivity> reference;

        public WorkHandler(Looper looper, FileActivity fileActivity) {
            super(looper);
            this.TAG = LogUtils2.DEFAULT_TAG + getClass().getSimpleName();
            this.reference = new WeakReference<>(fileActivity);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 1) {
                return;
            }
            LogUtils2.logI(this.TAG, "starting delete file list");
            FileActivity fileActivity = this.reference.get();
            if (fileActivity != null) {
                ((FileViewModel) fileActivity.mViewModel).deleteFileList(fileActivity.pathList);
                fileActivity.mMainHandler.sendEmptyMessageDelayed(2, 500L);
            }
        }
    }
}
