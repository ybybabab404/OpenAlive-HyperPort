package com.google.android.material.color;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RestrictTo;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public interface ColorResourcesOverride {
    static ColorResourcesOverride getInstance() {
        int i = Build.VERSION.SDK_INT;
        if (i > 33 && i < 34) {
            return null;
        }
        return ResourcesLoaderColorResourcesOverride.getInstance();
    }

    boolean applyIfPossible(Context context, Map map);
}
