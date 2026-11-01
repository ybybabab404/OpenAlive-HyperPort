package com.miui.aod;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.miui.aod.category.ImageSelectorCategoryInfo;
import com.miui.aod.util.FileUtils;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class ImageShareReceiver extends BroadcastReceiver {
    public static volatile boolean sHasRegistered = false;

    public static void registerImageShareReceiver(Context context) {
        if (sHasRegistered) {
            return;
        }
        synchronized (ImageShareReceiver.class) {
            try {
                if (!sHasRegistered) {
                    context.getApplicationContext().registerReceiver(new ImageShareReceiver(), new IntentFilter("com.miui.aod.DELETE_IMAGE_SHARE"), 2);
                    sHasRegistered = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if ("com.miui.aod.DELETE_IMAGE_SHARE".equals(intent.getAction())) {
            try {
                FileUtils.deleteFileAll(new File(ImageSelectorCategoryInfo.ALBUM_PATH));
            } catch (Exception e) {
                Log.e("ImageShareReceiver", "onReceive: ", e);
            }
        }
    }
}
