package com.miui.aod.monitor;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import androidx.collection.ArrayMap;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.util.Assert;
import com.miui.aod.util.MiSight.MiSightHelper;
import com.miui.maml.folme.AnimatedProperty;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class VisibilitySampler {
    public static /* synthetic */ void $r8$lambda$1A2SpKkcGSSY6EL5ZcSzEm7eDaM(Integer num, Map map, String str) {
        try {
            MiSightHelper.INSTANCE.createAndSendEvent(num.intValue(), map);
        } catch (Throwable th) {
            Log.e("VisibilitySampler", "report event failed (bg): name=" + str + ", id=" + num + ", params=" + map, th);
        }
    }

    public static Integer eventIdFor(String str) {
        return str == null ? null : 923040201;
    }

    public static boolean isAnomalous(View view) {
        Assert.isMainThread();
        if (view != null && view.getVisibility() == 0 && view.getAlpha() > 0.0f && view.getWidth() != 0 && view.getHeight() != 0 && view.getScaleX() != 0.0f && view.getScaleY() != 0.0f) {
            try {
                return (view.isShown() && view.getGlobalVisibleRect(new Rect())) ? false : true;
            } catch (Exception unused) {
            }
        }
        return true;
    }

    public static void report(final String str, View view) {
        Assert.isMainThread();
        if (view == null) {
            return;
        }
        final ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("viewName", str);
        arrayMap.put("visibility", Integer.valueOf(view.getVisibility()));
        arrayMap.put(AnimatedProperty.PROPERTY_NAME_ALPHA, Float.valueOf(view.getAlpha()));
        arrayMap.put("width", Integer.valueOf(view.getWidth()));
        arrayMap.put("height", Integer.valueOf(view.getHeight()));
        arrayMap.put(AnimatedProperty.PROPERTY_NAME_SCALE_X, Float.valueOf(view.getScaleX()));
        arrayMap.put(AnimatedProperty.PROPERTY_NAME_SCALE_Y, Float.valueOf(view.getScaleY()));
        final Integer numEventIdFor = eventIdFor(str);
        if (numEventIdFor != null) {
            try {
                BackgroundThread.post(new Runnable() { // from class: com.miui.aod.monitor.VisibilitySampler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        VisibilitySampler.$r8$lambda$1A2SpKkcGSSY6EL5ZcSzEm7eDaM(numEventIdFor, arrayMap, str);
                    }
                });
            } catch (Exception e) {
                Log.e("VisibilitySampler", "report schedule failed: name=" + str + ", id=" + numEventIdFor + ", params=" + arrayMap, e);
            }
        }
    }
}
