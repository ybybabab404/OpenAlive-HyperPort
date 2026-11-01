package com.miui.keyguard.editor.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miui.util.FeatureParser;

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
/* JADX INFO: compiled from: WideColorGamutManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class WideColorGamutManager {
    public static final int BT2020_COLOR_SPACE_ID;
    public static final WideColorGamutManager INSTANCE = new WideColorGamutManager();
    public static final int defaultScreenOptimizeMode;
    public static final CopyOnWriteArrayList mColorModeChangeListeners;
    public static ContentObserver mColorModeObserver;
    public static int mColorSpaceMode;
    public static ContentObserver mScreenModeObserver;
    public static int mScreenOptimizeMode;
    public static boolean mSupportBT2020;
    public static boolean mSupportWideColor;

    static {
        int integer = FeatureParser.getInteger("default_display_color_mode", -1);
        defaultScreenOptimizeMode = integer;
        mScreenOptimizeMode = integer;
        mColorSpaceMode = -1;
        mColorModeChangeListeners = new CopyOnWriteArrayList();
        BT2020_COLOR_SPACE_ID = ColorSpace.get(ColorSpace.Named.BT2020).getId();
    }

    public final void addColorModeChangeListener(WideColorGamutInterface wideColorGamutInterface) {
        if (wideColorGamutInterface == null || containsListener(wideColorGamutInterface)) {
            return;
        }
        mColorModeChangeListeners.add(new WeakReference(wideColorGamutInterface));
    }

    public final boolean containsListener(WideColorGamutInterface wideColorGamutInterface) {
        Iterator it = mColorModeChangeListeners.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((WeakReference) it.next()).get(), wideColorGamutInterface)) {
                return true;
            }
        }
        return false;
    }

    public final int getDeviceMaxColorMode(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (supportBT2020()) {
            return 5;
        }
        return supportWideColor() ? 1 : 0;
    }

    public final boolean getIsStandardMode() {
        return SystemProperties.getBoolean("ro.vendor.display.is_standard_mode", false);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0035  */
    public final boolean isBt2020File(String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        if (filePath.length() == 0) {
            return false;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            boolean z = true;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            ColorSpace colorSpace = options.outColorSpace;
            if (colorSpace != null) {
                String string = colorSpace.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                if (!StringsKt.contains$default((CharSequence) string, (CharSequence) "2020", false, 2, (Object) null)) {
                    z = false;
                }
            } else {
                z = false;
            }
            Log.d("WideColorGamutManager", "isBt2020File: colorSpace=" + colorSpace + ", isBt2020=" + z + ", filePath=" + filePath);
            return z;
        } catch (Exception e) {
            Log.e("WideColorGamutManager", "isBt2020File error: filePath=" + filePath, e);
            return false;
        }
    }

    public final boolean isP3File(String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            ColorSpace colorSpace = options.outColorSpace;
            if (colorSpace != null) {
                Log.d("WideColorGamutManager", "filePath: " + filePath + ", colorSpaceName:" + colorSpace.getName() + " , isWideGamut:" + colorSpace.isWideGamut());
            }
            return colorSpace != null && colorSpace.isWideGamut();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean isScreenBt2020Supported(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return SystemProperties.getBoolean("ro.vendor.display.bt2020.supported", false);
    }

    public final boolean isScreenWCGSupported(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getResources().getConfiguration().isScreenWideColorGamut();
    }

    public final boolean isWideColorBitmap(Bitmap bitmap) {
        ColorSpace colorSpace;
        if (bitmap == null || (colorSpace = bitmap.getColorSpace()) == null) {
            return false;
        }
        return colorSpace.isWideGamut();
    }

    public final boolean isWideColorGamutModeOn(int i, int i2) {
        return (i == 3 && !getIsStandardMode()) || (i == 4 && i2 == 1);
    }

    public final void notifyColorModeChange() {
        Iterator it = mColorModeChangeListeners.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            WideColorGamutInterface wideColorGamutInterface = (WideColorGamutInterface) ((WeakReference) it.next()).get();
            if (wideColorGamutInterface != null) {
                wideColorGamutInterface.onColorModeChanged();
            }
        }
    }

    public final void registerObservers(final Context context) {
        if (mScreenModeObserver == null) {
            mScreenModeObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.miui.keyguard.editor.utils.WideColorGamutManager.registerObservers.1
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    WideColorGamutManager wideColorGamutManager = WideColorGamutManager.INSTANCE;
                    WideColorGamutManager.mScreenOptimizeMode = Settings.System.getInt(context.getContentResolver(), "screen_optimize_mode", 0);
                    WideColorGamutManager.INSTANCE.notifyColorModeChange();
                }
            };
            ContentResolver contentResolver = context.getContentResolver();
            Uri uriFor = Settings.System.getUriFor("screen_optimize_mode");
            ContentObserver contentObserver = mScreenModeObserver;
            Intrinsics.checkNotNull(contentObserver, "null cannot be cast to non-null type android.database.ContentObserver");
            contentResolver.registerContentObserver(uriFor, false, contentObserver);
        }
        if (mColorModeObserver == null) {
            mColorModeObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.miui.keyguard.editor.utils.WideColorGamutManager.registerObservers.2
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    WideColorGamutManager wideColorGamutManager = WideColorGamutManager.INSTANCE;
                    WideColorGamutManager.mColorSpaceMode = Settings.System.getInt(context.getContentResolver(), "color_space_mode", -1);
                    WideColorGamutManager.INSTANCE.notifyColorModeChange();
                }
            };
            ContentResolver contentResolver2 = context.getContentResolver();
            Uri uriFor2 = Settings.System.getUriFor("color_space_mode");
            ContentObserver contentObserver2 = mColorModeObserver;
            Intrinsics.checkNotNull(contentObserver2, "null cannot be cast to non-null type android.database.ContentObserver");
            contentResolver2.registerContentObserver(uriFor2, false, contentObserver2);
        }
    }

    public final void removeColorModeChangeListener(WideColorGamutInterface wideColorGamutInterface) {
        if (wideColorGamutInterface == null) {
            return;
        }
        removeListener(wideColorGamutInterface);
    }

    public final boolean removeListener(WideColorGamutInterface wideColorGamutInterface) {
        Iterator it = mColorModeChangeListeners.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            if (Intrinsics.areEqual(weakReference.get(), wideColorGamutInterface)) {
                return mColorModeChangeListeners.remove(weakReference);
            }
        }
        return false;
    }

    public final boolean supportBT2020() {
        return mSupportBT2020;
    }

    public final boolean supportWideColor() {
        return mSupportWideColor;
    }

    public final void trySetPreferredColorSpace(String str, BitmapFactory.Options options) {
        if (str == null || options == null) {
            return;
        }
        Log.d("WideColorGamutManager", "trySetPreferredColorSpace: supportWideColor=" + supportWideColor() + ", filePath=" + str);
        if (supportWideColor()) {
            if (isBt2020File(str)) {
                options.inPreferredColorSpace = ColorSpace.get(ColorSpace.Named.BT2020);
            } else if (isP3File(str)) {
                options.inPreferredColorSpace = ColorSpace.get(ColorSpace.Named.DISPLAY_P3);
            }
        }
    }

    public final void unregisterObservers(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        ContentObserver contentObserver = mScreenModeObserver;
        if (contentObserver != null) {
            context.getContentResolver().unregisterContentObserver(contentObserver);
            mScreenModeObserver = null;
        }
        ContentObserver contentObserver2 = mColorModeObserver;
        if (contentObserver2 != null) {
            context.getContentResolver().unregisterContentObserver(contentObserver2);
            mColorModeObserver = null;
        }
    }

    public final void updateSupportWideColor(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        mScreenOptimizeMode = Settings.System.getInt(context.getContentResolver(), "screen_optimize_mode", defaultScreenOptimizeMode);
        mColorSpaceMode = Settings.System.getInt(context.getContentResolver(), "color_space_mode", -1);
        boolean z = false;
        mSupportWideColor = isScreenWCGSupported(context) && isWideColorGamutModeOn(mScreenOptimizeMode, mColorSpaceMode);
        if (isScreenBt2020Supported(context) && isWideColorGamutModeOn(mScreenOptimizeMode, mColorSpaceMode)) {
            z = true;
        }
        mSupportBT2020 = z;
        registerObservers(context);
        Log.d("WideColorGamutManager", "supportWideColor: " + mSupportWideColor + ", supportBT2020: " + mSupportBT2020 + ", mScreenOptimizeMode=" + mScreenOptimizeMode + ", mColorSpaceMode=" + mColorSpaceMode + ", isScreenWideColorGamut=" + isScreenWCGSupported(context) + ", isStandardMode=" + getIsStandardMode());
    }
}
