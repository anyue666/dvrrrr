package com.autolink.dvr.common.media.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Environment;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.USBUtil;
import com.autolink.dvr.model.FileNormalListResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class FileUtils {
    private static final String TAG = "DVR_FileUtils";
    public static int cacheTime = 15000000;
    public static boolean isSetTime = false;
    private static int nameNum = 0;
    public static int recordTime1min = 60000000;
    public static int recordTime3min = 180000000;
    public static int recordTime5min = 300000000;
    public static String recordVideoFileName = "";
    public static int saveTime_n = 3;
    private static long time;

    public static String getDiskCachePath(Context context) {
        if ("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir() == null ? "" : context.getExternalCacheDir().getPath();
        }
        return context.getCacheDir().getPath();
    }

    public static String getExternalPath(Context context) {
        String absolutePath;
        if (Build.VERSION.SDK_INT < 26) {
            absolutePath = Environment.getExternalStorageDirectory().getPath();
        } else {
            absolutePath = context.getApplicationContext().getFilesDir().getAbsolutePath();
        }
        LogUtils2.logI(TAG, "getExternalPath path = " + absolutePath);
        return absolutePath;
    }

    public static String get3MinsLaterTimeToString() {
        time += 5000000;
        Date date = new Date();
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(date) + ".mp4";
    }

    public static String getCurrentTimeToString2() {
        int i = nameNum;
        if (i == 9999) {
            nameNum = 1;
        } else {
            nameNum = i + 1;
        }
        return getDateToString2(System.currentTimeMillis(), "yyyyMMdd_HHmmss");
    }

    public static String getDateToString2(long j, String str) {
        Date date = new Date(j);
        return new SimpleDateFormat(str, Locale.getDefault()).format(date) + "_" + getIntToString(nameNum) + ".tmp";
    }

    private static String getIntToString(int i) {
        if (i <= 9) {
            return "000" + i;
        }
        if (i <= 99) {
            return "00" + i;
        }
        if (i <= 999) {
            return "0" + i;
        }
        return "" + i;
    }

    public static String getCurrentTimeToString() {
        setCurrentTimeMillis(System.currentTimeMillis());
        return getDateToString(getCurrentTimeMillis(), "yyyyMMdd_HHmmss");
    }

    public static String getDateToString(long j, String str) {
        Date date = new Date(j);
        return new SimpleDateFormat(str, Locale.getDefault()).format(date) + ".tmp";
    }

    public static int getSaveTime() {
        return saveTime_n * 60 * 1000 * 1000;
    }

    public static void setCurrentTimeMillis(long j) {
        isSetTime = true;
        time = j;
    }

    public static long getCurrentTimeMillis() {
        return time;
    }

    public static boolean saveVideoThumbnail(String str) {
        String str2;
        LogUtils2.logI(TAG, "saveVideoThumbnail " + str);
        File file = new File(str);
        if (!file.exists()) {
            return false;
        }
        Bitmap createVideoThumbnail = ThumbnailUtils.createVideoThumbnail(str, 1);
        if (createVideoThumbnail != null) {
            str2 = bitmap2File(createVideoThumbnail, file.getName());
        } else {
            LogUtils2.logI(TAG, "createVideoThumbnail fail" + str);
            str2 = null;
        }
        return str2 != null;
    }

    public static String bitmap2File(Bitmap bitmap, String str) {
        LogUtils2.logI(TAG, "bitmap2File " + str);
        File file = new File(USBUtil.USBPath + File.separator + "thumbnail");
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(USBUtil.USBPath + File.separator + "thumbnail" + File.separator + str + ".jpg");
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            bitmap.recycle();
            return file2.getAbsolutePath();
        } catch (IOException e) {
            bitmap.recycle();
            LogUtils2.logW(TAG, "bitmap2File error" + e.toString());
            return null;
        }
    }

    /* renamed from: com.autolink.dvr.common.media.utils.FileUtils$1 */
    class C09191 implements Comparator<FileNormalListResult.Item> {
        @Override // java.util.Comparator
        public boolean equals(Object obj) {
            return true;
        }

        C09191() {
        }

        @Override // java.util.Comparator
        public int compare(FileNormalListResult.Item item, FileNormalListResult.Item item2) {
            if (item != null && item.getName() != null && item2 != null && item2.getName() != null) {
                String[] split = item.getName().split("_");
                String[] split2 = item2.getName().split("_");
                if (split.length > 1 && split2.length > 1 && split[1].length() >= 6 && split2[1].length() >= 6) {
                    try {
                        int parseInt = Integer.parseInt(split[0]) - Integer.parseInt(split2[0]);
                        long parseInt2 = parseInt == 0 ? Integer.parseInt(split[1].substring(0, 6)) - Integer.parseInt(split2[1].substring(0, 6)) : parseInt;
                        if (parseInt2 > 0) {
                            return -1;
                        }
                        if (parseInt2 < 0) {
                            return 1;
                        }
                    } catch (NumberFormatException unused) {
                        LogUtils2.logE(FileUtils.TAG, "name parse to int fail, o1 is " + item.getName() + ", o2 is " + item2.getName());
                    }
                }
            }
            return 0;
        }
    }

    public static void sortByTime(List<FileNormalListResult.Item> list) {
        Collections.sort(list, new Comparator<FileNormalListResult.Item>() { // from class: com.autolink.dvr.common.media.utils.FileUtils.1
            @Override // java.util.Comparator
            public boolean equals(Object obj) {
                return true;
            }

            C09191() {
            }

            @Override // java.util.Comparator
            public int compare(FileNormalListResult.Item item, FileNormalListResult.Item item2) {
                if (item != null && item.getName() != null && item2 != null && item2.getName() != null) {
                    String[] split = item.getName().split("_");
                    String[] split2 = item2.getName().split("_");
                    if (split.length > 1 && split2.length > 1 && split[1].length() >= 6 && split2[1].length() >= 6) {
                        try {
                            int parseInt = Integer.parseInt(split[0]) - Integer.parseInt(split2[0]);
                            long parseInt2 = parseInt == 0 ? Integer.parseInt(split[1].substring(0, 6)) - Integer.parseInt(split2[1].substring(0, 6)) : parseInt;
                            if (parseInt2 > 0) {
                                return -1;
                            }
                            if (parseInt2 < 0) {
                                return 1;
                            }
                        } catch (NumberFormatException unused) {
                            LogUtils2.logE(FileUtils.TAG, "name parse to int fail, o1 is " + item.getName() + ", o2 is " + item2.getName());
                        }
                    }
                }
                return 0;
            }
        });
    }
}
