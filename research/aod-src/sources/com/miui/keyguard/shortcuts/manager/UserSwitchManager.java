package com.miui.keyguard.shortcuts.manager;

import android.annotation.SuppressLint;
import android.app.IActivityManager;
import android.app.UserSwitchObserver;
import android.content.Context;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

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
/* JADX INFO: compiled from: UserSwitchManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SuppressLint({"StaticFieldLeak"})
@SourceDebugExtension
public final class UserSwitchManager {
    public static final UserSwitchManager INSTANCE = new UserSwitchManager();
    public static Context context;
    public static IActivityManager iActivityManager;
    public static UserSwitchObserver userSwitchObserver;

    public final void init(Context sysuiContext) {
        Intrinsics.checkNotNullParameter(sysuiContext, "sysuiContext");
        context = sysuiContext;
        registerUserSwitchObserver();
    }

    public final void registerUserSwitchObserver() {
        try {
            Method declaredMethod = Class.forName("android.app.ActivityManager").getDeclaredMethod("getService", new Class[0]);
            declaredMethod.setAccessible(true);
            userSwitchObserver = new UserSwitchObserver() { // from class: com.miui.keyguard.shortcuts.manager.UserSwitchManager.registerUserSwitchObserver.1
                public void onUserSwitchComplete(int i) {
                    MiuiShortcutManager miuiShortcutManager = MiuiShortcutManager.INSTANCE;
                    miuiShortcutManager.release();
                    UserHandleUtils.refresh();
                    LogUtils.logD$default(LogUtils.INSTANCE, "UserSwitchManager", "onUserSwitchComplete, currentUserId = " + UserHandleUtils.USER_CURRENT, null, 4, null);
                    Context context2 = UserSwitchManager.context;
                    if (context2 != null) {
                        ShortcutManager.init$default(miuiShortcutManager, context2, false, 2, null);
                    }
                }
            };
            Object objInvoke = declaredMethod.invoke(null, new Object[0]);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type android.app.IActivityManager");
            IActivityManager iActivityManager2 = (IActivityManager) objInvoke;
            iActivityManager = iActivityManager2;
            if (iActivityManager2 != null) {
                iActivityManager2.registerUserSwitchObserver(userSwitchObserver, "UserSwitchManager");
            }
        } catch (Throwable th) {
            LogUtils.logE$default(LogUtils.INSTANCE, "UserSwitchManager", String.valueOf(th.getMessage()), null, 4, null);
        }
    }

    public final void release() {
        IActivityManager iActivityManager2;
        UserSwitchObserver userSwitchObserver2 = userSwitchObserver;
        if (userSwitchObserver2 != null && (iActivityManager2 = iActivityManager) != null) {
            iActivityManager2.unregisterUserSwitchObserver(userSwitchObserver2);
        }
        iActivityManager = null;
        userSwitchObserver = null;
        context = null;
    }
}
