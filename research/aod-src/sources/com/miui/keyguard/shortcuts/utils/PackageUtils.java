package com.miui.keyguard.shortcuts.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: PackageUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class PackageUtils {
    public static final PackageUtils INSTANCE = new PackageUtils();

    public final boolean checkMagazineLeftScreenActivityExist(Context context, String leftScreenActivityName) {
        List listEmptyList;
        ResolveInfo resolveInfoResolveActivityAsUser;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(leftScreenActivityName, "leftScreenActivityName");
        if (leftScreenActivityName.length() == 0) {
            return false;
        }
        try {
            List listSplit = new Regex("/").split(leftScreenActivityName, 0);
            if (!listSplit.isEmpty()) {
                ListIterator listIterator = listSplit.listIterator(listSplit.size());
                while (true) {
                    if (!listIterator.hasPrevious()) {
                        listEmptyList = CollectionsKt.emptyList();
                        break;
                    }
                    if (((String) listIterator.previous()).length() != 0) {
                        listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                        break;
                    }
                }
            } else {
                listEmptyList = CollectionsKt.emptyList();
                break;
            }
            String[] strArr = (String[]) listEmptyList.toArray(new String[0]);
            String str = strArr.length > 1 ? strArr[1] : "";
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.miui.android.fashiongallery", str));
            try {
                resolveInfoResolveActivityAsUser = context.getPackageManager().resolveActivityAsUser(intent, 0, ActivityManager.getCurrentUser());
            } catch (Exception e) {
                LogUtils.logE$default(LogUtils.INSTANCE, "PackageUtils", "resolveActivityAsUser exception" + e.getMessage(), null, 4, null);
                resolveInfoResolveActivityAsUser = null;
            }
            return resolveInfoResolveActivityAsUser != null;
        } catch (Exception e2) {
            LogUtils.logE$default(LogUtils.INSTANCE, "PackageUtils", "handlePreLeftScreenActivityName failed: " + e2, null, 4, null);
            return false;
        }
    }

    public final ApplicationInfo getApplicationInfo(Context context, String packageName) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        try {
            return context.getPackageManager().getApplicationInfo(packageName, 128);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public final Drawable getDrawableFromApplication(Context context, Resources resources, String appPackageName, String str) {
        Object objM1061constructorimpl;
        int resourceId;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resources, "resources");
        Intrinsics.checkNotNullParameter(appPackageName, "appPackageName");
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl((str == null || (resourceId = getResourceId(resources, appPackageName, str, "drawable")) == 0) ? null : ResourcesCompat.getDrawable(resources, resourceId, context.getTheme()));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logE$default(LogUtils.INSTANCE, "PackageUtils", "Error loading drawable: " + appPackageName + "-" + str + ": " + thM1063exceptionOrNullimpl, null, 4, null);
        }
        return (Drawable) (Result.m1065isFailureimpl(objM1061constructorimpl) ? null : objM1061constructorimpl);
    }

    public final List getInstalledApplications(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        PackageManager packageManager = context.getPackageManager();
        Intrinsics.checkNotNullExpressionValue(packageManager, "getPackageManager(...)");
        return getInstalledApplicationsAsUserByReflection(packageManager, 128, UserHandleUtils.USER_CURRENT);
    }

    public final List getInstalledApplicationsAsUserByReflection(PackageManager packageManager, int i, int i2) {
        Object objM1061constructorimpl;
        Intrinsics.checkNotNullParameter(packageManager, "<this>");
        try {
            Result.Companion companion = Result.Companion;
            Class cls = Integer.TYPE;
            Method method = PackageManager.class.getMethod("getInstalledApplicationsAsUser", cls, cls);
            method.setAccessible(true);
            Object objInvoke = method.invoke(packageManager, Integer.valueOf(i), Integer.valueOf(i2));
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.collections.List<android.content.pm.ApplicationInfo>");
            objM1061constructorimpl = Result.m1061constructorimpl((List) objInvoke);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logD$default(LogUtils.INSTANCE, "PackageUtils", "getInstalledApplicationsAsUserByReflection error: " + thM1063exceptionOrNullimpl, null, 4, null);
            objM1061constructorimpl = CollectionsKt.emptyList();
        }
        return (List) objM1061constructorimpl;
    }

    public final int getResourceId(Resources resources, String str, String str2, String str3) {
        Object objM1061constructorimpl;
        if (StringsKt.startsWith$default(str2, "@", false, 2, null)) {
            String strSubstring = str2.substring(1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            Integer intOrNull = StringsKt.toIntOrNull(strSubstring);
            if (intOrNull != null) {
                return intOrNull.intValue();
            }
            return 0;
        }
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(Integer.valueOf(resources.getIdentifier(str2, str3, str)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logE$default(LogUtils.INSTANCE, "PackageUtils", "getResourceId error " + str3 + " " + str + "-" + str2 + ": " + thM1063exceptionOrNullimpl.getMessage(), null, 4, null);
            objM1061constructorimpl = 0;
        }
        return ((Number) objM1061constructorimpl).intValue();
    }

    public final Resources getResourcesForApplication(Context context, ApplicationInfo appInfo) {
        Object objM1061constructorimpl;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(appInfo, "appInfo");
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(context.getPackageManager().getResourcesForApplication(appInfo));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logD$default(LogUtils.INSTANCE, "PackageUtils", "getResourcesForApplication error: " + thM1063exceptionOrNullimpl, null, 4, null);
            objM1061constructorimpl = null;
        }
        return (Resources) objM1061constructorimpl;
    }

    public final Resources getResourcesForApplication(Context context, String packageName) {
        Object objM1061constructorimpl;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(context.getPackageManager().getResourcesForApplicationAsUser(packageName, UserHandleUtils.USER_CURRENT));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logD$default(LogUtils.INSTANCE, "PackageUtils", "getResourcesForApplication " + packageName + " error: " + thM1063exceptionOrNullimpl, null, 4, null);
            objM1061constructorimpl = null;
        }
        return (Resources) objM1061constructorimpl;
    }

    public final String getShortcutNameForLanguage(Context context, String packageName, Locale locale, String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(locale, "locale");
        try {
            Result.Companion companion = Result.Companion;
            Context contextCreatePackageContext = context.createPackageContext(packageName, 3);
            Configuration configuration = new Configuration(contextCreatePackageContext.getResources().getConfiguration());
            configuration.locale = locale;
            Resources resources = contextCreatePackageContext.createConfigurationContext(configuration).getResources();
            Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
            return getStringFromApplication(resources, packageName, str);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            Object objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
            Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
            if (thM1063exceptionOrNullimpl != null) {
                LogUtils.logE$default(LogUtils.INSTANCE, "PackageUtils", "getShortcutNameForLanguage error: " + thM1063exceptionOrNullimpl, null, 4, null);
            }
            if (Result.m1065isFailureimpl(objM1061constructorimpl)) {
                objM1061constructorimpl = null;
            }
            return (String) objM1061constructorimpl;
        }
    }

    public final String getStringFromApplication(Resources resources, String appPackageName, String str) {
        Object objM1061constructorimpl;
        int resourceId;
        Intrinsics.checkNotNullParameter(resources, "resources");
        Intrinsics.checkNotNullParameter(appPackageName, "appPackageName");
        try {
            Result.Companion companion = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl((str == null || (resourceId = getResourceId(resources, appPackageName, str, "string")) == 0) ? null : resources.getString(resourceId));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logE$default(LogUtils.INSTANCE, "PackageUtils", "Error loading string: " + str + ": " + thM1063exceptionOrNullimpl, null, 4, null);
        }
        return (String) (Result.m1065isFailureimpl(objM1061constructorimpl) ? null : objM1061constructorimpl);
    }

    public final String loadJsonFromAssets(Context context) {
        Object objM1061constructorimpl;
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            Result.Companion companion = Result.Companion;
            if (!Intrinsics.areEqual(context.getPackageName(), "com.miui.aod")) {
                context = context.createPackageContext("com.miui.aod", 3);
            }
            InputStream inputStreamOpen = context.getAssets().open("default_white_list.json");
            Intrinsics.checkNotNullExpressionValue(inputStreamOpen, "open(...)");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamOpen, Charsets.UTF_8), AutoBottomSheetKt.ALIGN_SCREEN_START);
            try {
                String text = TextStreamsKt.readText(bufferedReader);
                CloseableKt.closeFinally(bufferedReader, null);
                objM1061constructorimpl = Result.m1061constructorimpl(text);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(bufferedReader, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th3));
        }
        Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
        if (thM1063exceptionOrNullimpl != null) {
            LogUtils.logD$default(LogUtils.INSTANCE, "PackageUtils", "loadJsonFromAssets error: " + thM1063exceptionOrNullimpl, null, 4, null);
        }
        if (Result.m1065isFailureimpl(objM1061constructorimpl)) {
            objM1061constructorimpl = "";
        }
        return (String) objM1061constructorimpl;
    }
}
