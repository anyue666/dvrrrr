package com.autolink.dvr.model.interfaces;

import com.autolink.dvr.model.FileNormalListResult;
import java.util.List;

/* loaded from: classes.dex */
public interface NormalListDataSource {
    FileNormalListResult getNormalListResult(boolean z) throws Exception;

    void onDeleteVideoFile(List<String> list) throws Exception;
}
