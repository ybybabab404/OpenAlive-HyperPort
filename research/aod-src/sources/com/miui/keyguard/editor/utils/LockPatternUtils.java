package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: LockPatternUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class LockPatternUtils {
    public static final LockPatternUtils INSTANCE = new LockPatternUtils();

    public final Object getLockPatternUtils(Context context) {
        try {
            Constructor<?> declaredConstructor = Class.forName("com.android.internal.widget.LockPatternUtils").getDeclaredConstructor(Context.class);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(context);
        } catch (Exception e) {
            Log.e("LockPatternUtils", "getLockPatternUtils catch a exception : " + e.getMessage());
            return null;
        }
    }

    public final String getOwnerInfo(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            Object lockPatternUtils = getLockPatternUtils(context);
            if (lockPatternUtils != null) {
                Method declaredMethod = lockPatternUtils.getClass().getDeclaredMethod("isDeviceOwnerInfoEnabled", new Class[0]);
                declaredMethod.setAccessible(true);
                Object objInvoke = declaredMethod.invoke(lockPatternUtils, new Object[0]);
                int iMyUserId = UserHandle.myUserId();
                Class<?> cls = lockPatternUtils.getClass();
                Class cls2 = Integer.TYPE;
                Method declaredMethod2 = cls.getDeclaredMethod("isOwnerInfoEnabled", cls2);
                declaredMethod2.setAccessible(true);
                Object objInvoke2 = declaredMethod2.invoke(lockPatternUtils, Integer.valueOf(iMyUserId));
                if ((objInvoke instanceof Boolean) && ((Boolean) objInvoke).booleanValue()) {
                    Method declaredMethod3 = lockPatternUtils.getClass().getDeclaredMethod("getDeviceOwnerInfo", new Class[0]);
                    declaredMethod3.setAccessible(true);
                    Object objInvoke3 = declaredMethod3.invoke(lockPatternUtils, new Object[0]);
                    if (objInvoke3 != null) {
                        return objInvoke3.toString();
                    }
                    return null;
                }
                if ((objInvoke2 instanceof Boolean) && ((Boolean) objInvoke2).booleanValue()) {
                    Method declaredMethod4 = lockPatternUtils.getClass().getDeclaredMethod("getOwnerInfo", cls2);
                    declaredMethod4.setAccessible(true);
                    Object objInvoke4 = declaredMethod4.invoke(lockPatternUtils, Integer.valueOf(iMyUserId));
                    if (objInvoke4 != null) {
                        return objInvoke4.toString();
                    }
                    return null;
                }
            }
        } catch (Exception e) {
            Log.e("LockPatternUtils", "getOwnerInfo catch a exception : " + e.getMessage());
        }
        return null;
    }

    public final void setOwnerInfo(Context context, String infoString) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(infoString, "infoString");
        try {
            Object lockPatternUtils = getLockPatternUtils(context);
            if (lockPatternUtils != null) {
                int iMyUserId = UserHandle.myUserId();
                Class<?> cls = lockPatternUtils.getClass();
                Class cls2 = Boolean.TYPE;
                Class cls3 = Integer.TYPE;
                Method declaredMethod = cls.getDeclaredMethod("setOwnerInfoEnabled", cls2, cls3);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(lockPatternUtils, Boolean.valueOf(!TextUtils.isEmpty(infoString)), Integer.valueOf(iMyUserId));
                Method declaredMethod2 = lockPatternUtils.getClass().getDeclaredMethod("setOwnerInfo", String.class, cls3);
                declaredMethod2.setAccessible(true);
                declaredMethod2.invoke(lockPatternUtils, infoString, Integer.valueOf(iMyUserId));
            }
        } catch (Exception e) {
            Log.e("LockPatternUtils", "setOwnerInfo catch a exception : " + e.getMessage());
        }
    }
}
