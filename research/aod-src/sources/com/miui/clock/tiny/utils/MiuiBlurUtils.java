package com.miui.clock.tiny.utils;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import com.miui.maml.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MiuiBlurUtils {
    public static void clearContainerPassBlur(View view) {
        if (view == null) {
            Log.d("MiuiBlurUtils", "clearContainerMiBackgroundBlur view is null");
            return;
        }
        try {
            setMiBackgroundBlurMode(view, 0);
            Log.d("MiuiBlurUtils", "clearContainerPassBlur result :" + setPassWindowBlurEnabled(view, false) + ", view: " + view);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "clearContainerMiBackgroundBlur error , view :" + view);
            e.printStackTrace();
        }
    }

    public static void clearMemberBlendColor(View view) {
        if (view == null) {
            Log.d("MiuiBlurUtils", "clearMemberBlendColor view is null");
            return;
        }
        try {
            setMiViewBlurMode(view, 0);
            clearMiBackgroundBlendColors(view);
            Log.d("MiuiBlurUtils", "clearMemberBlendColor view :" + view);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "clearMemberBlendColor error , view :" + view);
            e.printStackTrace();
        }
    }

    public static void clearMiBackgroundBlendColors(View view) {
        ReflectUtils.invokeObject(View.class, view, "setMiBackgroundBlendColors", Void.TYPE, new Class[]{ArrayList.class}, null);
    }

    public static void setCameraBlendColors(View view, int i, int i2, int i3, int i4) {
        if (view == null) {
            Log.d("MiuiBlurUtils", "setCameraBlendColors view = " + view);
            return;
        }
        try {
            setMiViewBlurMode(view, 3);
            ArrayList arrayList = new ArrayList();
            arrayList.add(new Point(i, i2));
            arrayList.add(new Point(i3, i4));
            setMiBackgroundBlendColors(view, arrayList);
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setCameraBlendColors error , view :" + view);
            e.printStackTrace();
        }
    }

    public static boolean setContainerPassBlur(View view, int i) {
        if (view == null) {
            Log.d("MiuiBlurUtils", "setPassBlur view is null");
            return false;
        }
        try {
            boolean passWindowBlurEnabled = setPassWindowBlurEnabled(view, false);
            setMiBackgroundBlurMode(view, 1);
            setMiBackgroundBlurRadius(view, i);
            Log.i("MiuiBlurUtils", "setContainerPassBlur result :" + passWindowBlurEnabled + ",view : " + view);
            return passWindowBlurEnabled;
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setContainerPassBlur error , view :" + view);
            e.printStackTrace();
            return false;
        }
    }

    public static void setMemberBlendColors(View view, boolean z, int i, int i2) {
        setMemberBlendColors(view, z, i, 255, i2, true);
    }

    public static void setMemberBlendColors(View view, boolean z, int i, int i2, int i3, boolean z2) {
        if (view == null) {
            Log.d("MiuiBlurUtils", "setMemberBlendColor view is null");
            return;
        }
        try {
            setMiViewBlurMode(view, 3);
            int iArgb = Color.argb(i2, Color.red(i), Color.green(i), Color.blue(i));
            int iArgb2 = Color.argb(i2, 0, 0, 0);
            ArrayList arrayList = new ArrayList();
            arrayList.add(new Point(iArgb, 101));
            if (z) {
                arrayList.add(new Point(iArgb2, R.styleable.AppCompatTheme_textAppearanceListItemSmall));
            } else {
                arrayList.add(new Point(iArgb2, 103));
            }
            if (z2 && DeviceConfig.supportBlurMode1000()) {
                arrayList.add(new Point(i3, 1000));
            }
            setMiBackgroundBlendColors(view, arrayList);
            Log.i("MiuiBlurUtils", "setMemberBlendColor: view:" + view + ",colorDark:" + z + ",color:" + Integer.toHexString(iArgb) + ",labColor:" + Integer.toHexString(iArgb2));
        } catch (Exception e) {
            Log.e("MiuiBlurUtils", "setMemberBlendColor error , view :" + view);
            e.printStackTrace();
        }
    }

    public static void setMiBackgroundBlendColors(View view, List list) {
        ReflectUtils.invokeObject(View.class, view, "setMiBackgroundBlendColors", Void.TYPE, new Class[]{ArrayList.class}, list);
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
