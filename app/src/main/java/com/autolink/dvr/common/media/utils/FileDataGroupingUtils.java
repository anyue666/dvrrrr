package com.autolink.dvr.common.media.utils;

import android.util.Log;
import com.autolink.dvr.model.FileListResultBean;
import com.autolink.dvr.model.FileNormalListResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes.dex */
public class FileDataGroupingUtils {
    public static List<FileListResultBean> setData(List<FileNormalListResult.Item> list) {
        ArrayList arrayList = new ArrayList();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i).getName();
            try {
                String[] split = name.split("_");
                stringBuffer.append(split[0]);
                boolean z = true;
                stringBuffer.append(split[1]);
                int i2 = 0;
                while (true) {
                    if (i2 >= arrayList.size()) {
                        z = false;
                        break;
                    }
                    if (((FileListResultBean) arrayList.get(i2)).getFileName().equals(split[0])) {
                        ((FileListResultBean) arrayList.get(i2)).addData(list.get(i));
                        break;
                    }
                    i2++;
                }
                if (!z) {
                    FileListResultBean fileListResultBean = new FileListResultBean();
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(list.get(i));
                    fileListResultBean.setFileName(split[0]);
                    fileListResultBean.setData(arrayList2);
                    arrayList.add(fileListResultBean);
                }
                stringBuffer.setLength(0);
            } catch (Exception unused) {
                Log.e("file exception  e", name);
            }
        }
        Collections.sort(arrayList, new Comparator<FileListResultBean>() { // from class: com.autolink.dvr.common.media.utils.FileDataGroupingUtils.1
            @Override // java.util.Comparator
            public boolean equals(Object obj) {
                return true;
            }

            @Override // java.util.Comparator
            public int compare(FileListResultBean fileListResultBean2, FileListResultBean fileListResultBean3) {
                long parseInt = Integer.parseInt(fileListResultBean2.getFileName()) - Integer.parseInt(fileListResultBean3.getFileName());
                if (parseInt > 0) {
                    return -1;
                }
                return parseInt == 0 ? 0 : 1;
            }
        });
        return arrayList;
    }

    public static List<FileNormalListResult.Item> setDataSize(List<FileListResultBean> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            int size = 4 - (list.get(i).getData().size() % 4);
            for (int i2 = 0; i2 < list.get(i).getData().size(); i2++) {
                if (i2 < 4) {
                    list.get(i).getData().get(i2).setFirstPlace(true);
                }
                arrayList.add(list.get(i).getData().get(i2));
            }
            if (size < 4) {
                for (int i3 = 0; i3 < size; i3++) {
                    FileNormalListResult.Item item = new FileNormalListResult.Item();
                    item.setFalseData(true);
                    list.get(i).getData().add(item);
                    arrayList.add(item);
                }
            }
        }
        return arrayList;
    }
}
