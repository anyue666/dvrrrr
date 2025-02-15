package com.autolink.dvr.common.base.hmi;

/* loaded from: classes.dex */
public interface ObserverCallback<V> {
    public static final int complet = 0;
    public static final int error = -1;
    public static final int success = 1;

    void ObserverResult(int i, V v);
}
