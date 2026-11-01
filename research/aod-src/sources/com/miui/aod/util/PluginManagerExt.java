package com.miui.aod.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.core.util.RomUtils;

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
/* JADX INFO: compiled from: PluginManagerExt.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SuppressLint({"PrivateApi"})
public final class PluginManagerExt {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: PluginManagerExt.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Object callObjectMethod(Object target, String str, Class[] parameterTypes, Object... values) {
            Intrinsics.checkNotNullParameter(target, "target");
            Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
            Intrinsics.checkNotNullParameter(values, "values");
            return target.getClass().getDeclaredMethod(str, (Class[]) Arrays.copyOf(parameterTypes, parameterTypes.length)).invoke(target, Arrays.copyOf(values, values.length));
        }

        public final Object callStaticObjectMethod(Class clazz, String str, Class[] parameterTypes, Object... values) throws NoSuchMethodException {
            Intrinsics.checkNotNullParameter(clazz, "clazz");
            Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
            Intrinsics.checkNotNullParameter(values, "values");
            Method declaredMethod = clazz.getDeclaredMethod(str, (Class[]) Arrays.copyOf(parameterTypes, parameterTypes.length));
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(null, Arrays.copyOf(values, values.length));
        }

        public final void checkVersionIfSupport(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            boolean zIsHyperOsRom = RomUtils.isHyperOsRom();
            int hyperOsVersion = RomUtils.getHyperOsVersion();
            boolean z = !zIsHyperOsRom || hyperOsVersion < 2;
            Log.w("AODPluginManagerExt", "init " + zIsHyperOsRom + " " + hyperOsVersion + " " + z);
            if (z) {
                PackageManager packageManager = context.getPackageManager();
                Intrinsics.checkNotNullExpressionValue(packageManager, "getPackageManager(...)");
                int appVersionCode = getAppVersionCode(packageManager, "com.miui.aod");
                Log.w("AODPluginManagerExt", "uninstall " + appVersionCode);
                try {
                    Intrinsics.checkNotNullExpressionValue(ServiceManager.class, "forName(...)");
                    Object objCallStaticObjectMethod = callStaticObjectMethod(ServiceManager.class, "getService", new Class[]{String.class}, "package");
                    Intrinsics.checkNotNull(objCallStaticObjectMethod, "null cannot be cast to non-null type android.os.IBinder");
                    Class<?> cls = Class.forName("android.content.pm.IPackageManager$Stub");
                    Intrinsics.checkNotNullExpressionValue(cls, "forName(...)");
                    Object objCallStaticObjectMethod2 = callStaticObjectMethod(cls, "asInterface", new Class[]{IBinder.class}, (IBinder) objCallStaticObjectMethod);
                    Intrinsics.checkNotNull(objCallStaticObjectMethod2);
                    deletePackageAsUser(objCallStaticObjectMethod2, "com.miui.aod", appVersionCode, null, 0, 0);
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    Log.e("AODPluginManagerExt", "uninstall failed.", th);
                }
            }
        }

        public final void deletePackageAsUser(Object obj, String str, int i, IPackageDeleteObserver iPackageDeleteObserver, int i2, int i3) {
            try {
                Class cls = Integer.TYPE;
                callObjectMethod(obj, "deletePackageAsUser", new Class[]{String.class, cls, IPackageDeleteObserver.class, cls, cls}, str, Integer.valueOf(i), iPackageDeleteObserver, Integer.valueOf(i2), Integer.valueOf(i3));
            } catch (Throwable th) {
                Log.e("AODPluginManagerExt", "delete " + str + " failed.", th);
            }
        }

        public final int getAppVersionCode(PackageManager packageManager, String str) {
            try {
                return packageManager.getPackageInfo(str, 0).versionCode;
            } catch (Throwable th) {
                Log.e("AODPluginManagerExt", "getAppVersionCode of " + str + " failed.", th);
                return 0;
            }
        }
    }
}
