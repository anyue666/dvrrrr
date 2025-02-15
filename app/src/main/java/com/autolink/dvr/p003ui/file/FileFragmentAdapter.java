package com.autolink.dvr.p003ui.file;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class FileFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private String[] tables;

    public FileFragmentAdapter(FragmentManager fragmentManager, int i) {
        super(fragmentManager, i);
        this.tables = new String[]{"实时视频", "紧急视频"};
    }

    public void setFragmentList(List<Fragment> list) {
        if (this.fragmentList == null) {
            this.fragmentList = new ArrayList();
        }
        this.fragmentList.addAll(list);
        notifyDataSetChanged();
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        List<Fragment> list = this.fragmentList;
        if (list == null || list.size() <= 0) {
            return null;
        }
        return this.fragmentList.get(i);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        List<Fragment> list = this.fragmentList;
        if (list == null || list.size() <= 0) {
            return 0;
        }
        return this.fragmentList.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public CharSequence getPageTitle(int i) {
        Logger.m498d("FileFragmentAdapter: getPageTitle positon=" + i);
        return this.tables[i];
    }
}
