package com.autolink.dvr;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.autolink.dvr.databinding.ActivityBtestBindingImpl;
import com.autolink.dvr.databinding.ActivityFileBindingImpl;
import com.autolink.dvr.databinding.ActivityFileTopBindingImpl;
import com.autolink.dvr.databinding.ActivityMainBindingImpl;
import com.autolink.dvr.databinding.ActivityTestBindingImpl;
import com.autolink.dvr.databinding.ActivityTestItemBindingImpl;
import com.autolink.dvr.databinding.ActivityVideoBindingImpl;
import com.autolink.dvr.databinding.ActivityVideoBottomBindingImpl;
import com.autolink.dvr.databinding.ActivityVideoControlBindingImpl;
import com.autolink.dvr.databinding.ActivityVideoPlayBindingImpl;
import com.autolink.dvr.databinding.ActivityVideoTopBindingImpl;
import com.autolink.dvr.databinding.CheckUsbRebootBindingImpl;
import com.autolink.dvr.databinding.ClearAllPopBindingImpl;
import com.autolink.dvr.databinding.DeletingLayoutBindingImpl;
import com.autolink.dvr.databinding.DialogDeleteLayoutBindingImpl;
import com.autolink.dvr.databinding.FileDataLayoutBindingImpl;
import com.autolink.dvr.databinding.FormatUsbPopBindingImpl;
import com.autolink.dvr.databinding.FragmentFileEventBindingImpl;
import com.autolink.dvr.databinding.FragmentFileNormalBindingImpl;
import com.autolink.dvr.databinding.ItemListEventBindingImpl;
import com.autolink.dvr.databinding.ItemListNormalBindingImpl;
import com.autolink.dvr.databinding.PopVideoWindowBindingImpl;
import com.autolink.dvr.databinding.SettingLayoutBindingImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;
    private static final int LAYOUT_ACTIVITYBTEST = 1;
    private static final int LAYOUT_ACTIVITYFILE = 2;
    private static final int LAYOUT_ACTIVITYFILETOP = 3;
    private static final int LAYOUT_ACTIVITYMAIN = 4;
    private static final int LAYOUT_ACTIVITYTEST = 5;
    private static final int LAYOUT_ACTIVITYTESTITEM = 6;
    private static final int LAYOUT_ACTIVITYVIDEO = 7;
    private static final int LAYOUT_ACTIVITYVIDEOBOTTOM = 8;
    private static final int LAYOUT_ACTIVITYVIDEOCONTROL = 9;
    private static final int LAYOUT_ACTIVITYVIDEOPLAY = 10;
    private static final int LAYOUT_ACTIVITYVIDEOTOP = 11;
    private static final int LAYOUT_CHECKUSBREBOOT = 12;
    private static final int LAYOUT_CLEARALLPOP = 13;
    private static final int LAYOUT_DELETINGLAYOUT = 14;
    private static final int LAYOUT_DIALOGDELETELAYOUT = 15;
    private static final int LAYOUT_FILEDATALAYOUT = 16;
    private static final int LAYOUT_FORMATUSBPOP = 17;
    private static final int LAYOUT_FRAGMENTFILEEVENT = 18;
    private static final int LAYOUT_FRAGMENTFILENORMAL = 19;
    private static final int LAYOUT_ITEMLISTEVENT = 20;
    private static final int LAYOUT_ITEMLISTNORMAL = 21;
    private static final int LAYOUT_POPVIDEOWINDOW = 22;
    private static final int LAYOUT_SETTINGLAYOUT = 23;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(23);
        INTERNAL_LAYOUT_ID_LOOKUP = sparseIntArray;
        sparseIntArray.put(C0903R.layout.activity_btest, 1);
        sparseIntArray.put(C0903R.layout.activity_file, 2);
        sparseIntArray.put(C0903R.layout.activity_file_top, 3);
        sparseIntArray.put(C0903R.layout.activity_main, 4);
        sparseIntArray.put(C0903R.layout.activity_test, 5);
        sparseIntArray.put(C0903R.layout.activity_test_item, 6);
        sparseIntArray.put(C0903R.layout.activity_video, 7);
        sparseIntArray.put(C0903R.layout.activity_video_bottom, 8);
        sparseIntArray.put(C0903R.layout.activity_video_control, 9);
        sparseIntArray.put(C0903R.layout.activity_video_play, 10);
        sparseIntArray.put(C0903R.layout.activity_video_top, 11);
        sparseIntArray.put(C0903R.layout.check_usb_reboot, 12);
        sparseIntArray.put(C0903R.layout.clear_all_pop, 13);
        sparseIntArray.put(C0903R.layout.deleting_layout, 14);
        sparseIntArray.put(C0903R.layout.dialog_delete_layout, 15);
        sparseIntArray.put(C0903R.layout.file_data_layout, 16);
        sparseIntArray.put(C0903R.layout.format_usb_pop, 17);
        sparseIntArray.put(C0903R.layout.fragment_file_event, 18);
        sparseIntArray.put(C0903R.layout.fragment_file_normal, 19);
        sparseIntArray.put(C0903R.layout.item_list_event, 20);
        sparseIntArray.put(C0903R.layout.item_list_normal, 21);
        sparseIntArray.put(C0903R.layout.pop_video_window, 22);
        sparseIntArray.put(C0903R.layout.setting_layout, 23);
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View view, int i) {
        int i2 = INTERNAL_LAYOUT_ID_LOOKUP.get(i);
        if (i2 <= 0) {
            return null;
        }
        Object tag = view.getTag();
        if (tag == null) {
            throw new RuntimeException("view must have a tag");
        }
        switch (i2) {
            case 1:
                if ("layout/activity_btest_0".equals(tag)) {
                    return new ActivityBtestBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for activity_btest is invalid. Received: " + tag);
            case 2:
                if ("layout/activity_file_0".equals(tag)) {
                    return new ActivityFileBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for activity_file is invalid. Received: " + tag);
            case 3:
                if ("layout/activity_file_top_0".equals(tag)) {
                    return new ActivityFileTopBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for activity_file_top is invalid. Received: " + tag);
            case 4:
                if ("layout/activity_main_0".equals(tag)) {
                    return new ActivityMainBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
            case 5:
                if ("layout/activity_test_0".equals(tag)) {
                    return new ActivityTestBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for activity_test is invalid. Received: " + tag);
            case 6:
                if ("layout/activity_test_item_0".equals(tag)) {
                    return new ActivityTestItemBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for activity_test_item is invalid. Received: " + tag);
            case 7:
                if ("layout/activity_video_0".equals(tag)) {
                    return new ActivityVideoBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for activity_video is invalid. Received: " + tag);
            case 8:
                if ("layout/activity_video_bottom_0".equals(tag)) {
                    return new ActivityVideoBottomBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for activity_video_bottom is invalid. Received: " + tag);
            case 9:
                if ("layout/activity_video_control_0".equals(tag)) {
                    return new ActivityVideoControlBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for activity_video_control is invalid. Received: " + tag);
            case 10:
                if ("layout/activity_video_play_0".equals(tag)) {
                    return new ActivityVideoPlayBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for activity_video_play is invalid. Received: " + tag);
            case 11:
                if ("layout/activity_video_top_0".equals(tag)) {
                    return new ActivityVideoTopBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for activity_video_top is invalid. Received: " + tag);
            case 12:
                if ("layout/check_usb_reboot_0".equals(tag)) {
                    return new CheckUsbRebootBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for check_usb_reboot is invalid. Received: " + tag);
            case 13:
                if ("layout/clear_all_pop_0".equals(tag)) {
                    return new ClearAllPopBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for clear_all_pop is invalid. Received: " + tag);
            case 14:
                if ("layout/deleting_layout_0".equals(tag)) {
                    return new DeletingLayoutBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for deleting_layout is invalid. Received: " + tag);
            case 15:
                if ("layout/dialog_delete_layout_0".equals(tag)) {
                    return new DialogDeleteLayoutBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for dialog_delete_layout is invalid. Received: " + tag);
            case 16:
                if ("layout/file_data_layout_0".equals(tag)) {
                    return new FileDataLayoutBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for file_data_layout is invalid. Received: " + tag);
            case 17:
                if ("layout/format_usb_pop_0".equals(tag)) {
                    return new FormatUsbPopBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for format_usb_pop is invalid. Received: " + tag);
            case 18:
                if ("layout/fragment_file_event_0".equals(tag)) {
                    return new FragmentFileEventBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for fragment_file_event is invalid. Received: " + tag);
            case 19:
                if ("layout/fragment_file_normal_0".equals(tag)) {
                    return new FragmentFileNormalBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for fragment_file_normal is invalid. Received: " + tag);
            case 20:
                if ("layout/item_list_event_0".equals(tag)) {
                    return new ItemListEventBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for item_list_event is invalid. Received: " + tag);
            case 21:
                if ("layout/item_list_normal_0".equals(tag)) {
                    return new ItemListNormalBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for item_list_normal is invalid. Received: " + tag);
            case 22:
                if ("layout/pop_video_window_0".equals(tag)) {
                    return new PopVideoWindowBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for pop_video_window is invalid. Received: " + tag);
            case 23:
                if ("layout/setting_layout_0".equals(tag)) {
                    return new SettingLayoutBindingImpl(dataBindingComponent, view);
                }
                throw new IllegalArgumentException("The tag for setting_layout is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View[] viewArr, int i) {
        if (viewArr == null || viewArr.length == 0 || INTERNAL_LAYOUT_ID_LOOKUP.get(i) <= 0 || viewArr[0].getTag() != null) {
            return null;
        }
        throw new RuntimeException("view must have a tag");
    }

    @Override // androidx.databinding.DataBinderMapper
    public int getLayoutId(String str) {
        Integer num;
        if (str == null || (num = InnerLayoutIdLookup.sKeys.get(str)) == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // androidx.databinding.DataBinderMapper
    public String convertBrIdToString(int i) {
        return InnerBrLookup.sKeys.get(i);
    }

    @Override // androidx.databinding.DataBinderMapper
    public List<DataBinderMapper> collectDependencies() {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        return arrayList;
    }

    private static class InnerBrLookup {
        static final SparseArray<String> sKeys;

        private InnerBrLookup() {
        }

        static {
            SparseArray<String> sparseArray = new SparseArray<>(4);
            sKeys = sparseArray;
            sparseArray.put(0, "_all");
            sparseArray.put(1, "resultItem");
            sparseArray.put(2, "viewModel");
            sparseArray.put(3, "viewModelChild");
        }
    }

    private static class InnerLayoutIdLookup {
        static final HashMap<String, Integer> sKeys;

        private InnerLayoutIdLookup() {
        }

        static {
            HashMap<String, Integer> hashMap = new HashMap<>(23);
            sKeys = hashMap;
            hashMap.put("layout/activity_btest_0", Integer.valueOf(C0903R.layout.activity_btest));
            hashMap.put("layout/activity_file_0", Integer.valueOf(C0903R.layout.activity_file));
            hashMap.put("layout/activity_file_top_0", Integer.valueOf(C0903R.layout.activity_file_top));
            hashMap.put("layout/activity_main_0", Integer.valueOf(C0903R.layout.activity_main));
            hashMap.put("layout/activity_test_0", Integer.valueOf(C0903R.layout.activity_test));
            hashMap.put("layout/activity_test_item_0", Integer.valueOf(C0903R.layout.activity_test_item));
            hashMap.put("layout/activity_video_0", Integer.valueOf(C0903R.layout.activity_video));
            hashMap.put("layout/activity_video_bottom_0", Integer.valueOf(C0903R.layout.activity_video_bottom));
            hashMap.put("layout/activity_video_control_0", Integer.valueOf(C0903R.layout.activity_video_control));
            hashMap.put("layout/activity_video_play_0", Integer.valueOf(C0903R.layout.activity_video_play));
            hashMap.put("layout/activity_video_top_0", Integer.valueOf(C0903R.layout.activity_video_top));
            hashMap.put("layout/check_usb_reboot_0", Integer.valueOf(C0903R.layout.check_usb_reboot));
            hashMap.put("layout/clear_all_pop_0", Integer.valueOf(C0903R.layout.clear_all_pop));
            hashMap.put("layout/deleting_layout_0", Integer.valueOf(C0903R.layout.deleting_layout));
            hashMap.put("layout/dialog_delete_layout_0", Integer.valueOf(C0903R.layout.dialog_delete_layout));
            hashMap.put("layout/file_data_layout_0", Integer.valueOf(C0903R.layout.file_data_layout));
            hashMap.put("layout/format_usb_pop_0", Integer.valueOf(C0903R.layout.format_usb_pop));
            hashMap.put("layout/fragment_file_event_0", Integer.valueOf(C0903R.layout.fragment_file_event));
            hashMap.put("layout/fragment_file_normal_0", Integer.valueOf(C0903R.layout.fragment_file_normal));
            hashMap.put("layout/item_list_event_0", Integer.valueOf(C0903R.layout.item_list_event));
            hashMap.put("layout/item_list_normal_0", Integer.valueOf(C0903R.layout.item_list_normal));
            hashMap.put("layout/pop_video_window_0", Integer.valueOf(C0903R.layout.pop_video_window));
            hashMap.put("layout/setting_layout_0", Integer.valueOf(C0903R.layout.setting_layout));
        }
    }
}
