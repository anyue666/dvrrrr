package com.autolink.dvr.model.usecase;

import com.autolink.dvr.common.base.hmi.UsecaseSingleBase;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.model.FileNormalListResult;
import com.autolink.dvr.model.repository.FileRepository;
import java.util.List;

/* loaded from: classes.dex */
public class FileNormalUseCase extends UsecaseSingleBase<String, FileNormalListResult> {
    private final String TAG;
    private FileRepository fileRepository;
    private FileNormalListResult mResult;

    public FileNormalUseCase() {
        String str = LogUtils2.DEFAULT_TAG + "FileNormalUseCase";
        this.TAG = str;
        LogUtils2.logI(str, "FileNormalUseCase ");
        this.fileRepository = FileRepository.getInstance();
    }

    @Override // com.autolink.dvr.common.base.hmi.UsecaseSingleBase
    public FileNormalListResult getResult(boolean z) {
        try {
            this.mResult = this.fileRepository.getNormalListResult(z);
            LogUtils2.logI(this.TAG, "getResult mResult = " + this.mResult.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.mResult;
    }

    @Override // com.autolink.dvr.common.base.hmi.UsecaseSingleBase
    public void deleteFile(List<String> list) {
        try {
            this.fileRepository.onDeleteVideoFile(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.autolink.dvr.common.base.hmi.UsecaseSingleBase
    public boolean dowork(String str, FileNormalListResult fileNormalListResult) {
        LogUtils2.logI(this.TAG, "dowork result = " + fileNormalListResult.toString());
        return false;
    }
}
