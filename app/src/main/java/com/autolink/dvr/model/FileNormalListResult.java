package com.autolink.dvr.model;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes.dex */
public class FileNormalListResult implements Serializable {
    private List<Item> items;
    private long totalCount;

    public static class Item implements Serializable {
        private String date;
        private boolean falseData;
        private boolean firstPlace;
        private boolean isChoose;
        private String name;
        private String path;

        public boolean isFirstPlace() {
            return this.firstPlace;
        }

        public void setFirstPlace(boolean z) {
            this.firstPlace = z;
        }

        public boolean isChoose() {
            return this.isChoose;
        }

        public void setChoose(boolean z) {
            this.isChoose = z;
        }

        public boolean isFalseData() {
            return this.falseData;
        }

        public void setFalseData(boolean z) {
            this.falseData = z;
        }

        public String getPath() {
            return this.path;
        }

        public void setPath(String str) {
            this.path = str;
        }

        public String getDate() {
            return this.date;
        }

        public void setDate(String str) {
            this.date = str;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public String toString() {
            return "Item{date='" + this.date + "', name='" + this.name + "'}";
        }
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long j) {
        this.totalCount = j;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> list) {
        this.items = list;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("totalCount", this.totalCount).add("items", this.items).toString();
    }
}
