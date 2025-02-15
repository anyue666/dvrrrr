package com.autolink.dvr.model.domain;

import com.autolink.dvr.common.media.MediaCodecConstant;
import com.autolink.dvr.common.media.utils.FileUtils;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.common.utils.USBUtil;
import com.autolink.dvr.model.FileNormalListResult;
import com.autolink.dvr.model.interfaces.NormalListDataSource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class LocalNormalListDataSource implements NormalListDataSource {
    private final String TAG = LogUtils2.DEFAULT_TAG + getClass().getSimpleName();
    private StringBuffer stringBuffer = new StringBuffer();

    @Override // com.autolink.dvr.model.interfaces.NormalListDataSource
    public FileNormalListResult getNormalListResult(boolean z) throws Exception {
        String str;
        Pattern compile;
        LogUtils2.logI(this.TAG, "getNormalListResult");
        if (USBUtil.USBPath != null) {
            String str2 = USBUtil.USBPath;
            LogUtils2.logI(this.TAG, " file path exist");
            if (z) {
                str = str2 + "/normal/";
                compile = Pattern.compile("^\\d{8}_\\d{6}.mp4");
            } else {
                str = str2 + "/event/";
                compile = Pattern.compile("^\\d{8}_\\d{6}_\\d{4}.mp4");
            }
            File[] listFiles = new File(str).listFiles();
            ArrayList arrayList = new ArrayList();
            if (listFiles != null) {
                LogUtils2.logI(this.TAG, " files length " + listFiles.length);
                for (int i = 0; i < listFiles.length; i++) {
                    String name = listFiles[i].getName();
                    LogUtils2.logI(this.TAG, "文件夹下的文件：" + name);
                    FileNormalListResult.Item item = new FileNormalListResult.Item();
                    if (MediaCodecConstant.isStartRecord && name.equals(FileUtils.recordVideoFileName)) {
                        LogUtils2.logI(this.TAG, "recordVideoFileName：" + name);
                    } else if (compile.matcher(name).find()) {
                        item.setPath(listFiles[i].getAbsolutePath());
                        item.setName(name);
                        arrayList.add(item);
                    } else {
                        LogUtils2.logI(this.TAG, "  name match fail, file is " + name);
                    }
                }
            } else {
                LogUtils2.logI(this.TAG, " files is null ");
            }
            FileNormalListResult fileNormalListResult = new FileNormalListResult();
            fileNormalListResult.setItems(arrayList);
            fileNormalListResult.setTotalCount(arrayList.size());
            return fileNormalListResult;
        }
        LogUtils2.logI(this.TAG, " file path no exist");
        return new FileNormalListResult();
    }

    @Override // com.autolink.dvr.model.interfaces.NormalListDataSource
    public void onDeleteVideoFile(List<String> list) throws Exception {
        LogUtils2.logI(this.TAG, "deleteVideoFile");
        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i));
            if (file.exists()) {
                boolean delete = file.delete();
                LogUtils2.logI(this.TAG, "onDeleteVideoFile " + file.getName() + " type " + delete);
                File file2 = new File(USBUtil.USBPath + File.separator + "thumbnail" + File.separator + file.getName() + ".jpg");
                if (file2.exists()) {
                    boolean delete2 = file2.delete();
                    LogUtils2.logI(this.TAG, "onDeleteVideoThumbnailFile " + file2.getName() + " type " + delete2);
                }
            }
        }
    }
}
