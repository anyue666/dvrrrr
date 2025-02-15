package com.autolink.dvr.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.autolink.dvr.common.base.hmi.BaseViewModel;
import com.autolink.dvr.common.base.hmi.SingleCallback;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.model.FileNormalListResult;
import com.autolink.dvr.model.usecase.FileNormalUseCase;
import java.util.List;

/* loaded from: classes.dex */
public class FileViewModel extends BaseViewModel {
    private final String TAG;
    private FileNormalUseCase fIleNormalUseCase;
    private boolean isCleared;
    private SingleCallback<FileNormalListResult> normalListResultSingleCallback;
    public final MutableLiveData<List<FileNormalListResult.Item>> resultList;

    public FileViewModel() {
        String str = LogUtils2.DEFAULT_TAG + "FileViewModel";
        this.TAG = str;
        this.isCleared = false;
        this.resultList = new MutableLiveData<>();
        this.normalListResultSingleCallback = new SingleCallback<FileNormalListResult>() { // from class: com.autolink.dvr.viewmodel.FileViewModel.1
            @Override // com.autolink.dvr.common.base.hmi.SingleCallback
            public void SingleResult(int i, FileNormalListResult fileNormalListResult) {
                LogUtils2.logI(FileViewModel.this.TAG, "SingleResult state = " + i + " , TotalCount = " + fileNormalListResult.getTotalCount());
                if (i == 1) {
                    FileViewModel.this.resultList.setValue(fileNormalListResult.getItems());
                }
            }
        };
        LogUtils2.logI(str, "FileViewModel");
        init();
    }

    private void init() {
        FileNormalUseCase fileNormalUseCase = new FileNormalUseCase();
        this.fIleNormalUseCase = fileNormalUseCase;
        fileNormalUseCase.registerObservable(this.normalListResultSingleCallback);
    }

    public MutableLiveData<List<FileNormalListResult.Item>> getResultList(boolean z) {
        synchronized (FileViewModel.class) {
            if (this.isCleared) {
                return null;
            }
            FileNormalListResult result = this.fIleNormalUseCase.getResult(z);
            this.resultList.postValue(result.getItems());
            LogUtils2.logI(this.TAG, "getResultList TotalCount = " + result.getTotalCount());
            return this.resultList;
        }
    }

    public void deleteFileList(List<String> list) {
        this.fIleNormalUseCase.deleteFile(list);
    }

    @Override // com.autolink.dvr.common.base.hmi.BaseViewModel, androidx.lifecycle.ViewModel
    protected void onCleared() {
        super.onCleared();
        LogUtils2.logI(this.TAG, "onCleared");
        this.isCleared = true;
        this.fIleNormalUseCase.unregisterObservable(this.normalListResultSingleCallback);
    }
}
