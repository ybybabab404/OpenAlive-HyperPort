package com.miui.keyguard.editor.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.DeviceUtil;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: ActivityUIAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ActivityUIAdapter {
    public static final Companion Companion = new Companion(null);
    public final EditorDisplayListener mDisplayListener;
    public int orientation;
    public int screenWidthDp;

    /* JADX INFO: compiled from: ActivityUIAdapter.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void correctActivityResource(Context context) {
            if (context != null) {
                context.getResources().updateConfiguration(context.getResources().getConfiguration(), ActivityUIAdapter.Companion.displayMetrics(context));
            }
        }

        public final DisplayMetrics displayMetrics(Context context) {
            if (!DeviceUtil.INSTANCE.isFold()) {
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                Intrinsics.checkNotNullExpressionValue(displayMetrics, "getDisplayMetrics(...)");
                return displayMetrics;
            }
            Activity activity = context instanceof Activity ? (Activity) context : null;
            if (activity == null) {
                DisplayMetrics displayMetrics2 = context.getResources().getDisplayMetrics();
                Intrinsics.checkNotNullExpressionValue(displayMetrics2, "getDisplayMetrics(...)");
                return displayMetrics2;
            }
            DisplayMetrics displayMetrics3 = new DisplayMetrics();
            Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
            if (defaultDisplay != null) {
                defaultDisplay.getRealMetrics(displayMetrics3);
            }
            return displayMetrics3;
        }

        public final boolean isLightTheme(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.isLightTheme});
            boolean z = typedArrayObtainStyledAttributes.getBoolean(0, true);
            typedArrayObtainStyledAttributes.recycle();
            return z;
        }
    }

    public ActivityUIAdapter(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.mDisplayListener = new EditorDisplayListener(new WeakReference(activity));
        this.screenWidthDp = activity.getResources().getConfiguration().screenWidthDp;
        this.orientation = activity.getResources().getConfiguration().orientation;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isJ18() || deviceUtil.isPhone()) {
            activity.setRequestedOrientation(1);
        }
    }

    public static /* synthetic */ void onConfigurationChanged$default(ActivityUIAdapter activityUIAdapter, Activity activity, Configuration configuration, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        activityUIAdapter.onConfigurationChanged(activity, configuration, z);
    }

    public final void clearAllDrawable(EditorActivity editorActivity) {
        Object objM1061constructorimpl;
        Resources resources = editorActivity.getResources();
        try {
            Result.Companion companion = Result.Companion;
            Method method = resources.getClass().getMethod("getImpl", new Class[0]);
            method.setAccessible(true);
            Object objInvoke = method.invoke(resources, new Object[0]);
            Method method2 = objInvoke.getClass().getMethod("clearAllCaches", new Class[0]);
            method2.setAccessible(true);
            objM1061constructorimpl = Result.m1061constructorimpl(method2.invoke(objInvoke, new Object[0]));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl == null) {
            return;
        }
        Log.w("ActivityUIAdapter", "clear drawable error:" + thM1063exceptionOrNullimpl);
    }

    public final void initNightMode(EditorActivity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if ((activity.getApplicationContext().getResources().getConfiguration().uiMode & 48) == 32) {
            Log.d("ActivityUIAdapter", "initNightMode: UI_MODE_NIGHT");
            activity.setTheme(activity.getTheme(true));
        } else if ((activity.getApplicationContext().getResources().getConfiguration().uiMode & 48) == 16) {
            Log.d("ActivityUIAdapter", "initNightMode: UI_MODE_LIGHT");
            activity.setTheme(activity.getTheme(false));
        }
    }

    public final void onConfigurationChanged(Activity activity, Configuration newConfig, boolean z) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        if (this.screenWidthDp != newConfig.screenWidthDp || this.orientation != newConfig.orientation || z) {
            resetNewConfig(activity, newConfig);
            this.screenWidthDp = newConfig.screenWidthDp;
            this.orientation = newConfig.orientation;
        }
        activity.getResources().updateConfiguration(newConfig, Companion.displayMetrics(activity));
    }

    public final void registerDisplayListener(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (DeviceUtil.INSTANCE.isPhone()) {
            return;
        }
        Object systemService = context.getSystemService("display");
        DisplayManager displayManager = systemService instanceof DisplayManager ? (DisplayManager) systemService : null;
        if (displayManager != null) {
            displayManager.registerDisplayListener(this.mDisplayListener, new Handler(Looper.getMainLooper()));
        }
    }

    public final void resetNewConfig(Activity activity, Configuration configuration) {
        Display display = activity.getDisplay();
        Integer numValueOf = display != null ? Integer.valueOf(display.getDisplayId()) : null;
        if (numValueOf == null) {
            Log.w("ActivityUIAdapter", "can't get displayId");
            return;
        }
        Log.i("ActivityUIAdapter", "old config " + configuration);
        int i = configuration.densityDpi;
        int iInitialDensityDpi = DeviceUtil.INSTANCE.initialDensityDpi(numValueOf.intValue());
        configuration.densityDpi = iInitialDensityDpi;
        float f = i;
        configuration.screenWidthDp = (int) (((configuration.screenWidthDp * 1.0f) * f) / iInitialDensityDpi);
        configuration.screenHeightDp = (int) (((configuration.screenHeightDp * 1.0f) * f) / iInitialDensityDpi);
        configuration.smallestScreenWidthDp = (int) Math.ceil(((configuration.smallestScreenWidthDp * 1.0f) * f) / iInitialDensityDpi);
        Log.i("ActivityUIAdapter", "new config " + configuration);
    }

    public final void unregisterDisplayListener(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (DeviceUtil.INSTANCE.isPhone()) {
            return;
        }
        Object systemService = context.getSystemService("display");
        DisplayManager displayManager = systemService instanceof DisplayManager ? (DisplayManager) systemService : null;
        if (displayManager != null) {
            displayManager.unregisterDisplayListener(this.mDisplayListener);
        }
    }

    public final void updateNightMode(EditorActivity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        boolean zIsLightTheme = Companion.isLightTheme(activity);
        if ((activity.getApplicationContext().getResources().getConfiguration().uiMode & 48) == 32) {
            Log.d("ActivityUIAdapter", "updateNightMode: UI_MODE_NIGHT");
            if (zIsLightTheme) {
                activity.setTheme(activity.getTheme(true));
                Configuration configuration = activity.getResources().getConfiguration();
                Intrinsics.checkNotNullExpressionValue(configuration, "getConfiguration(...)");
                onConfigurationChanged(activity, configuration, true);
                clearAllDrawable(activity);
                return;
            }
            return;
        }
        if ((activity.getApplicationContext().getResources().getConfiguration().uiMode & 48) == 16) {
            Log.d("ActivityUIAdapter", "updateNightMode: UI_MODE_LIGHT");
            if (zIsLightTheme) {
                return;
            }
            activity.setTheme(activity.getTheme(false));
            Configuration configuration2 = activity.getResources().getConfiguration();
            Intrinsics.checkNotNullExpressionValue(configuration2, "getConfiguration(...)");
            onConfigurationChanged(activity, configuration2, true);
            clearAllDrawable(activity);
        }
    }
}
