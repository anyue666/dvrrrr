package com.autolink.dvr.model;

import com.autolink.dvr.model.FileNormalListResult;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes.dex */
public class FileListResultBean implements Serializable {
    List<FileNormalListResult.Item> data;
    String fileName;

    public void addData(FileNormalListResult.Item item) {
        List<FileNormalListResult.Item> list = this.data;
        if (list != null) {
            list.add(item);
        }
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public List<FileNormalListResult.Item> getData() {
        return this.data;
    }

    public void setData(List<FileNormalListResult.Item> list) {
        this.data = list;
    }
}
