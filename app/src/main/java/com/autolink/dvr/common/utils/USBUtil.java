package com.autolink.dvr.common.utils;

import android.content.Context;
import android.os.StatFs;
import android.os.storage.StorageManager;
import com.autolink.dvr.common.media.MediaCodecConstant;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.p006io.FileUtils;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.cookie.ClientCookie;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/* loaded from: classes.dex */
public class USBUtil {
    public static final String FAT32 = "vfat";
    private static final String HOST_MODE = "host";
    public static final String NTFS = "ntfs";
    private static final String PERIPHERAL_MODE = "peripheral";
    private static final String TAG = "DVR_USBUtil";
    public static String USBPath = null;
    public static final String USB_J8 = "usb-J8";
    public static final String USB_PATH = "/storage/usb-J8";
    public static final long USB_SPACE = 8160437863L;
    private static final String VENDOR_BOSCH_USB2_MODE = "persist.vendor.bosch.usb2.mode";
    private static final String VENDOR_BOSCH_USB3_MODE = "persist.vendor.bosch.usb3.mode";
    public static long avaibleSize = 0;
    public static final String event = "event";
    public static final String exFAT = "ufsd";
    public static boolean isNeedFormatUSB = false;
    public static final String normal = "normal";
    public static int percentage;
    public static long totalSize;

    public static boolean isUSB2Mode() {
        return getProperty(VENDOR_BOSCH_USB2_MODE).equals(HOST_MODE);
    }

    public static String getProperty(String str) {
        try {
            try {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                return (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, "None");
            } catch (Exception e) {
                e.printStackTrace();
                return "None";
            }
        } catch (Throwable unused) {
            return "None";
        }
    }

    public static String byteToString(long j) {
        LogUtils2.logI(TAG, "byteToString: size = " + j);
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        if (j / FileUtils.ONE_GB >= 1) {
            return decimalFormat.format(j / FileUtils.ONE_GB) + " GB   ";
        }
        if (j / FileUtils.ONE_MB >= 1) {
            return decimalFormat.format(j / FileUtils.ONE_MB) + " MB   ";
        }
        if (j / FileUtils.ONE_KB >= 1) {
            return decimalFormat.format(j / FileUtils.ONE_KB) + " KB   ";
        }
        return j + " B   ";
    }

    public static void getUSBInfo(Context context) {
        LogUtils2.logI(TAG, "getUSBInfo ");
        StorageManager storageManager = (StorageManager) context.getSystemService("storage");
        try {
            Class<?> cls = Class.forName("android.os.storage.StorageManager");
            List list = (List) cls.getMethod("getVolumes", new Class[0]).invoke(storageManager, new Object[0]);
            Class<?> cls2 = Class.forName("android.os.storage.VolumeInfo");
            Method method = cls2.getMethod("getFsUuid", new Class[0]);
            Field declaredField = cls2.getDeclaredField(ClientCookie.PATH_ATTR);
            Field declaredField2 = cls2.getDeclaredField("internalPath");
            if (list != null) {
                for (Object obj : list) {
                    String str = (String) method.invoke(obj, new Object[0]);
                    LogUtils2.logI(TAG, "getUSBInfo: uuid = " + str);
                    if (USB_J8.equals(str)) {
                        String str2 = (String) cls.getMethod("getVolumeState", String.class).invoke(storageManager, USB_PATH);
                        LogUtils2.logI(TAG, "getUSBInfo: state = " + str2.toString());
                        if ("mounted".equals(str2)) {
                            StatFs statFs = new StatFs((String) declaredField2.get(obj));
                            avaibleSize = statFs.getAvailableBytes();
                            totalSize = statFs.getTotalBytes();
                            String str3 = (String) obj.getClass().getField("fsType").get(obj);
                            LogUtils2.logI(TAG, "getUSBInfo: totalSize = " + byteToString(totalSize) + " ,avaibleSize = " + byteToString(avaibleSize));
                            if (totalSize >= USB_SPACE) {
                                LogUtils2.logI(TAG, "getUSBInfo: fsType = " + str3);
                                percentage = (int) (((((float) avaibleSize) * 1.0f) / ((float) totalSize)) * 1.0f * 100.0f);
                                USBPath = (String) declaredField.get(obj);
                                SaveMsg.isMountedUSB = true;
                                LogUtils2.logI(TAG, "getUSBInfo: USBPath = " + USBPath + " ,percentage = " + percentage);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtils2.logE(TAG, "getUSBInfo: Exception = " + e.toString());
        }
    }

    public static void unmountUSB(Context context) {
        LogUtils2.logI(TAG, "unmountUSB: ");
        StorageManager storageManager = (StorageManager) context.getSystemService("storage");
        try {
            Class<?> cls = Class.forName("android.os.storage.VolumeInfo");
            Method method = StorageManager.class.getMethod("getVolumes", new Class[0]);
            Method method2 = cls.getMethod("isMountedReadable", new Class[0]);
            Method method3 = cls.getMethod("getType", new Class[0]);
            Method method4 = cls.getMethod("getFsUuid", new Class[0]);
            Method method5 = cls.getMethod("getId", new Class[0]);
            Method method6 = storageManager.getClass().getMethod("unmount", String.class);
            List list = (List) method.invoke(storageManager, new Object[0]);
            if (list.size() == 0) {
                return;
            }
            for (Object obj : list) {
                if (obj != null && ((Boolean) method2.invoke(obj, new Object[0])).booleanValue() && ((Integer) method3.invoke(obj, new Object[0])).intValue() == 0) {
                    String str = (String) method4.invoke(obj, new Object[0]);
                    LogUtils2.logI(TAG, "unmountUSB: uuid = " + str);
                    if (USB_J8.equals(str)) {
                        String str2 = (String) method5.invoke(obj, new Object[0]);
                        LogUtils2.logI(TAG, "unmountUSB: unmount USB ");
                        SaveMsg.isMountedUSB = false;
                        percentage = 0;
                        method6.invoke(storageManager, str2);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils2.logE(TAG, "unmountUSB: Exception = " + e.toString());
        }
    }

    public static void formatUSB(Context context) {
        LogUtils2.logI(TAG, "formatUSB: ");
        StorageManager storageManager = (StorageManager) context.getSystemService("storage");
        try {
            Class<?> cls = Class.forName("android.os.storage.VolumeInfo");
            Method method = StorageManager.class.getMethod("getVolumes", new Class[0]);
            Method method2 = cls.getMethod("isMountedReadable", new Class[0]);
            Method method3 = cls.getMethod("getType", new Class[0]);
            Method method4 = cls.getMethod("getFsUuid", new Class[0]);
            Method method5 = cls.getMethod("getId", new Class[0]);
            Method method6 = storageManager.getClass().getMethod("format", String.class);
            List list = (List) method.invoke(storageManager, new Object[0]);
            if (list.size() == 0) {
                return;
            }
            for (Object obj : list) {
                if (obj != null && ((Boolean) method2.invoke(obj, new Object[0])).booleanValue() && ((Integer) method3.invoke(obj, new Object[0])).intValue() == 0) {
                    String str = (String) method4.invoke(obj, new Object[0]);
                    LogUtils2.logI(TAG, "formatUSB: uuid = " + str);
                    if (USB_J8.equals(str)) {
                        String str2 = (String) method5.invoke(obj, new Object[0]);
                        LogUtils2.logI(TAG, "formatUSB: format USB ");
                        isNeedFormatUSB = false;
                        SaveMsg.isMountedUSB = false;
                        method6.invoke(storageManager, str2);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils2.logE(TAG, "formatUSB: Exception = " + e.toString());
        }
    }

    public static boolean deleteFileOrDirectory(String str) {
        LogUtils2.logI(TAG, "deleteFileOrDirectory: fileName = " + str);
        File file = new File(str);
        if (!file.exists()) {
            LogUtils2.logI(TAG, "deleteFileOrDirectory 删除文件失败: " + str + " 不存在！");
            return false;
        }
        if (file.isFile()) {
            return deleteFile(str);
        }
        return deleteDirectory(str);
    }

    public static boolean deleteFile(String str) {
        File file = new File(str);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                LogUtils2.logI(TAG, "deleteFile 删除单个文件 " + str + " 成功！");
                File file2 = new File(USBPath + File.separator + "thumbnail" + File.separator + file.getName() + ".jpg");
                if (file2.exists() && file2.delete()) {
                    LogUtils2.logI(TAG, "deleteFile 删除单个文件 " + file2.getName() + " 成功！");
                    return true;
                }
                LogUtils2.logI(TAG, "deleteFile 删除单个文件 " + file2.getName() + " 不存在！");
                return true;
            }
            LogUtils2.logI(TAG, "deleteFile 删除单个文件 " + str + " 失败！");
            return false;
        }
        LogUtils2.logI(TAG, "deleteFile 删除单个文件失败：" + str + " 不存在！");
        return false;
    }

    public static boolean deleteDirectory(String str) {
        if (!str.endsWith(File.separator)) {
            str = str + File.separator;
        }
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            LogUtils2.logI(TAG, "deleteDirectory 删除目录失败：" + str + " 不存在！");
            return false;
        }
        File[] listFiles = file.listFiles();
        boolean z = true;
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].isFile()) {
                z = deleteFile(listFiles[i].getAbsolutePath());
                if (!z) {
                    break;
                }
            } else {
                if (listFiles[i].isDirectory() && !(z = deleteDirectory(listFiles[i].getAbsolutePath()))) {
                    break;
                }
            }
        }
        if (!z) {
            LogUtils2.logI(TAG, "deleteDirectory 删除目录失败！");
            return false;
        }
        if (!file.delete()) {
            return false;
        }
        LogUtils2.logI(TAG, "deleteDirectory 删除目录 " + str + " 成功！");
        return true;
    }

    public static boolean deleteAllVideo(String str) {
        if (!str.endsWith(File.separator)) {
            str = str + File.separator;
        }
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            LogUtils2.logI(TAG, "deleteAllVideo 删除目录失败：" + str + " 不存在！");
            return false;
        }
        File[] listFiles = file.listFiles();
        boolean z = true;
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].isFile()) {
                LogUtils2.logI(TAG, "deleteAllVideo files[i] = " + listFiles[i].getName());
                if (MediaCodecConstant.isStartRecord && com.autolink.dvr.common.media.utils.FileUtils.recordVideoFileName.equals(listFiles[i].getName())) {
                    LogUtils2.logI(TAG, "deleteAllVideo 正在录制的视频，不能删除");
                } else {
                    z = deleteFile(listFiles[i].getAbsolutePath());
                    if (!z) {
                        break;
                    }
                }
            }
        }
        if (z) {
            return true;
        }
        LogUtils2.logI(TAG, "deleteAllVideo 删除目录失败！");
        return false;
    }

    public static boolean deleteSomeVideo(String str, boolean z) {
        if (!str.endsWith(File.separator)) {
            str = str + File.separator;
        }
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            LogUtils2.logI(TAG, "deleteSomeVideo 删除目录失败：" + str + " 不存在！");
            return false;
        }
        File[] orderByDate = orderByDate(str);
        long j = 0;
        int i = 0;
        boolean z2 = true;
        while (true) {
            if (i >= orderByDate.length) {
                break;
            }
            if (orderByDate[i].isFile()) {
                LogUtils2.logI(TAG, "deleteSomeVideo files[i] = " + orderByDate[i].getName());
                if (MediaCodecConstant.isStartRecord && com.autolink.dvr.common.media.utils.FileUtils.recordVideoFileName.equals(orderByDate[i].getName())) {
                    LogUtils2.logI(TAG, "deleteSomeVideo 正在录制的视频，不能删除");
                } else {
                    j += orderByDate[i].length();
                    z2 = deleteFile(orderByDate[i].getAbsolutePath());
                    if (!z2) {
                        break;
                    }
                    if (!z && j > 47185920) {
                        LogUtils2.logI(TAG, "deleteSomeVideo size = " + byteToString(j));
                        break;
                    }
                    if (z && j > 8388608) {
                        LogUtils2.logI(TAG, "deleteSomeVideo size = " + byteToString(j));
                        break;
                    }
                }
            }
            i++;
        }
        if (z2) {
            return true;
        }
        LogUtils2.logI(TAG, "deleteSomeVideo 删除目录失败！");
        return false;
    }

    public static File[] orderByDate(String str) {
        LogUtils2.logI(TAG, "orderByDate filePath = " + str);
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            LogUtils2.logI(TAG, "orderByDate 目录" + str + " 不存在！");
            return null;
        }
        File[] listFiles = file.listFiles();
        Arrays.sort(listFiles, new Comparator<File>() { // from class: com.autolink.dvr.common.utils.USBUtil.1
            @Override // java.util.Comparator
            public boolean equals(Object obj) {
                return true;
            }

            C09231() {
            }

            @Override // java.util.Comparator
            public int compare(File file2, File file3) {
                long lastModified = file2.lastModified() - file3.lastModified();
                if (lastModified > 0) {
                    return 1;
                }
                return lastModified == 0 ? 0 : -1;
            }
        });
        for (int i = 0; i < listFiles.length; i++) {
            LogUtils2.logI(TAG, "orderByDate file = " + listFiles[i].getName() + " , size = " + listFiles[i].length() + " , lastModified= " + new Date(listFiles[i].lastModified()));
        }
        return listFiles;
    }

    /* renamed from: com.autolink.dvr.common.utils.USBUtil$1 */
    class C09231 implements Comparator<File> {
        @Override // java.util.Comparator
        public boolean equals(Object obj) {
            return true;
        }

        C09231() {
        }

        @Override // java.util.Comparator
        public int compare(File file2, File file3) {
            long lastModified = file2.lastModified() - file3.lastModified();
            if (lastModified > 0) {
                return 1;
            }
            return lastModified == 0 ? 0 : -1;
        }
    }

    public static boolean isHaveOtherFile(String str) {
        if (str == null) {
            LogUtils2.logI(TAG, "isHaveOtherFile 目录" + str + " 不存在！");
            return false;
        }
        if (!str.endsWith(File.separator)) {
            str = str + File.separator;
        }
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            LogUtils2.logI(TAG, "isHaveOtherFile 目录" + str + " 不存在！");
            return false;
        }
        File[] listFiles = file.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            LogUtils2.logI(TAG, "isHaveOtherFile files = " + listFiles[i]);
            if (listFiles[i].isFile()) {
                LogUtils2.logI(TAG, "isHaveOtherFile 有文件，返回 true");
                isNeedFormatUSB = true;
                return true;
            }
            if (listFiles[i].isDirectory() && !normal.equals(listFiles[i].getName()) && !"event".equals(listFiles[i].getName()) && !"Alarms".equals(listFiles[i].getName()) && !"Audiobooks".equals(listFiles[i].getName()) && !"DCIM".equals(listFiles[i].getName()) && !"Documents".equals(listFiles[i].getName()) && !"Download".equals(listFiles[i].getName()) && !"Ringtones".equals(listFiles[i].getName()) && !"Notifications".equals(listFiles[i].getName()) && !"LOST.DIR".equals(listFiles[i].getName()) && !"LOST.dir".equals(listFiles[i].getName()) && !"Android".equals(listFiles[i].getName()) && !"Movies".equals(listFiles[i].getName()) && !"Music".equals(listFiles[i].getName()) && !"Pictures".equals(listFiles[i].getName()) && !"System Volume Information".equals(listFiles[i].getName()) && !"Podcasts".equals(listFiles[i].getName()) && !listFiles[i].getName().startsWith("found.") && !listFiles[i].getName().startsWith("FOUND.") && !"thumbnail".equals(listFiles[i].getName())) {
                LogUtils2.logI(TAG, "isHaveOtherFile 有别的文件夹，返回 true");
                isNeedFormatUSB = true;
                return true;
            }
        }
        LogUtils2.logI(TAG, "isHaveOtherFile 返回 false");
        isNeedFormatUSB = false;
        return false;
    }

    public static long getDirOrFileSize(String str) {
        File file = new File(str);
        if (!file.exists()) {
            return 0L;
        }
        if (file.isFile()) {
            long length = file.length();
            LogUtils2.logI(TAG, String.format("File size: %s", byteToString(length)));
            return length;
        }
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return 0L;
        }
        long j = 0;
        for (File file2 : listFiles) {
            if (file2 != null) {
                j += file2.isDirectory() ? 0L : file2.length();
            }
        }
        LogUtils2.logI(TAG, String.format("Folder size: %s", byteToString(j)));
        return j;
    }
}
