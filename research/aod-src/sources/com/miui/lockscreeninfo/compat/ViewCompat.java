package com.miui.lockscreeninfo.compat;

import android.util.Log;
import android.view.View;
import com.miui.lockscreeninfo.ReflectUtils;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
public class ViewCompat {
    public static void chooseBackgroundBlurContainer(View view, View view2) {
        try {
            ReflectUtils.invokeObject(View.class, view, "chooseBackgroundBlurContainer", Void.TYPE, new Class[]{View.class}, view2);
            Log.d("ViewCompat", "chooseBackgroundBlurContainer: successfully");
        } catch (Exception e) {
            Log.e("ViewCompat", "chooseBackgroundBlurContainer: ");
            e.printStackTrace();
        }
    }

    public static void clearMiBackgroundBlendColors(View view) {
        setMiBackgroundBlendColors(view, null);
    }

    public static void disableMiBackgroundContainBelow(View view, boolean z) {
        ReflectUtils.invokeObject(View.class, view, "disableMiBackgroundContainBelow", Void.TYPE, new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
    }

    public static void setMiBackgroundBlendColors(View view, ArrayList arrayList) {
        ReflectUtils.invokeObject(View.class, view, "setMiBackgroundBlendColors", Void.TYPE, new Class[]{ArrayList.class}, arrayList);
    }

    public static void setMiBackgroundBlurMode(View view, int i) {
        ReflectUtils.invokeObject(View.class, view, "setMiBackgroundBlurMode", Void.TYPE, new Class[]{Integer.TYPE}, Integer.valueOf(i));
    }

    public static void setMiBackgroundBlurRadius(View view, int i) {
        ReflectUtils.invokeObject(View.class, view, "setMiBackgroundBlurRadius", Void.TYPE, new Class[]{Integer.TYPE}, Integer.valueOf(i));
    }

    public static void setMiViewBlurMode(View view, int i) {
        ReflectUtils.invokeObject(View.class, view, "setMiViewBlurMode", Void.TYPE, new Class[]{Integer.TYPE}, Integer.valueOf(i));
    }

    public static boolean setPassWindowBlurEnabled(View view, boolean z) {
        Boolean bool = Boolean.FALSE;
        Class cls = Boolean.TYPE;
        return ((Boolean) ReflectUtils.invokeObject(View.class, view, "setPassWindowBlurEnabled", cls, bool, new Class[]{cls}, Boolean.valueOf(z))).booleanValue();
    }
}
