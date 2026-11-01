package com.miui.keyguard.editor.video;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes2.dex */
public class VideoWallpaperUtils {
    public static boolean isDolbyVisionVideo(String str) {
        return TextUtils.equals(str, "video/dolby-vision");
    }
}
