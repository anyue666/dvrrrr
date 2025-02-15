package com.autolink.dvr.model.repository;

import com.autolink.dvr.common.base.p002my.MyBaseRepository;
import com.autolink.dvr.common.utils.LogUtils2;
import com.autolink.dvr.model.FileNormalListResult;
import com.autolink.dvr.model.domain.LocalNormalListDataSource;
import com.autolink.dvr.model.interfaces.NormalListDataSource;
import java.util.List;

/* loaded from: classes.dex */
public class FileRepository extends MyBaseRepository implements NormalListDataSource {
    private final String TAG = LogUtils2.DEFAULT_TAG + getClass().getSimpleName();
    private static LocalNormalListDataSource mSource = new LocalNormalListDataSource();
    private static FileRepository fileRepository = new FileRepository();

    public static FileRepository getInstance() {
        return fileRepository;
    }

    @Override // com.autolink.dvr.model.interfaces.NormalListDataSource
    public FileNormalListResult getNormalListResult(boolean z) throws Exception {
        LogUtils2.logI(this.TAG, "getNormalListResult ");
        return mSource.getNormalListResult(z);
    }

    @Override // com.autolink.dvr.model.interfaces.NormalListDataSource
    public void onDeleteVideoFile(List<String> list) throws Exception {
        mSource.onDeleteVideoFile(list);
    }
}
