package com.autolink.dvr.p003ui.file;

/* loaded from: classes.dex */
public class DeleteVideoUtils {
    private static volatile DeleteVideoUtils instance;

    public static void deleteFile(String str) {
    }

    private DeleteVideoUtils() {
    }

    public static DeleteVideoUtils getInstance() {
        if (instance == null) {
            synchronized (DeleteVideoUtils.class) {
                if (instance == null) {
                    instance = new DeleteVideoUtils();
                }
            }
        }
        return instance;
    }
}
