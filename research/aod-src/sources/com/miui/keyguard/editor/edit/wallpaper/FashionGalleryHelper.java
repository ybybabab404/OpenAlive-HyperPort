package com.miui.keyguard.editor.edit.wallpaper;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import com.miui.keyguard.editor.homepage.bean.GalleryStatusBean;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.GsonSingletonKt;
import com.miui.keyguard.editor.utils.task.Task;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

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
/* JADX INFO: compiled from: FashionGalleryHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class FashionGalleryHelper {
    public static int CLOSE_SOURCE;
    public static GalleryStatusBean galleryStatusBean;
    public static volatile Boolean isFashionGalleryOpened;
    public static final FashionGalleryHelper INSTANCE = new FashionGalleryHelper();
    public static final ExecutorService providerExecutor = Executors.newCachedThreadPool(new ThreadFactory() { // from class: com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper$$ExternalSyntheticLambda0
        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            return FashionGalleryHelper.providerExecutor$lambda$4(runnable);
        }
    });

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper$isFashionGalleryOpened$2, reason: invalid class name */
    /* JADX INFO: compiled from: FashionGalleryHelper.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper$isFashionGalleryOpened$2", f = "FashionGalleryHelper.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
        public final /* synthetic */ Context $context;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Context context, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean zIsGalleryInstalledAndOpen = FashionGalleryHelper.INSTANCE.isGalleryInstalledAndOpen(this.$context);
            FashionGalleryHelper.isFashionGalleryOpened = Boxing.boxBoolean(zIsGalleryInstalledAndOpen);
            return Boxing.boxBoolean(zIsGalleryInstalledAndOpen);
        }
    }

    public static /* synthetic */ void addWallpaperFromEditorAsync$default(FashionGalleryHelper fashionGalleryHelper, Context context, String str, String str2, String str3, boolean z, String str4, boolean z2, Integer num, int i, Object obj) {
        if ((i & 4) != 0) {
            str2 = null;
        }
        if ((i & 8) != 0) {
            str3 = null;
        }
        fashionGalleryHelper.addWallpaperFromEditorAsync(context, str, str2, str3, z, str4, z2, num);
    }

    public static final void addWallpaperFromEditorAsync$lambda$0(Context context, String str, String str2, String str3, boolean z, String str4, boolean z2, Integer num) {
        INSTANCE.addWallpaperFromEditor(context, str, str2, str3, z, str4, z2, num);
    }

    public static final Bundle callProviderMethod$lambda$5(Context context, String str, String str2, Bundle bundle) {
        return context.getContentResolver().call(str, str2, (String) null, bundle);
    }

    public static /* synthetic */ GalleryStatusBean getGalleryStatus$default(FashionGalleryHelper fashionGalleryHelper, Context context, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return fashionGalleryHelper.getGalleryStatus(context, z);
    }

    public static final Thread providerExecutor$lambda$4(Runnable runnable) {
        Thread thread = new Thread(runnable, "FashionGalleryProvider");
        thread.setDaemon(true);
        return thread;
    }

    public final Bundle addWallpaperFromEditor(Context context, String wallpaperType, String str, String str2, boolean z, String str3, boolean z2, Integer num) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(wallpaperType, "wallpaperType");
        Log.i("Keyguard-Editor-FashionGalleryHelper", "addWallpaperFromEditor -> isLock = " + z2 + ", looptype = " + num + ", wallpaperType = " + wallpaperType + ", wallpaperParcel = " + str + ", wallpaperOriginUri = " + str2 + ", cropped = " + z + ", galleryContent = " + str3 + ", closeSource = " + CLOSE_SOURCE);
        Bundle bundle = new Bundle();
        bundle.putString("wallpaper_type", wallpaperType);
        if (str == null) {
            str = "";
        }
        bundle.putString("wallpaper_uri", str);
        bundle.putString("wallpaper_origin_uri", str2);
        bundle.putBoolean("cropped", z);
        if (str3 == null) {
            str3 = "";
        }
        bundle.putString("gallery_json", str3);
        bundle.putBoolean("is_lock", z2);
        bundle.putInt("loop_type", num != null ? num.intValue() : -1);
        bundle.putInt("closeSource", CLOSE_SOURCE);
        Log.i("Keyguard-Editor-FashionGalleryHelper", "call addWallpaperFromEditor -> " + bundle);
        return callProviderMethod(context, "addWallpaperFromEditor", bundle);
    }

    public final void addWallpaperFromEditorAsync(final Context context, final String wallpaperType, final String str, final String str2, final boolean z, final String str3, final boolean z2, final Integer num) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(wallpaperType, "wallpaperType");
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                FashionGalleryHelper.addWallpaperFromEditorAsync$lambda$0(context, wallpaperType, str, str2, z, str3, z2, num);
            }
        });
    }

    public final Bundle callProviderMethod(final Context context, final String str, final Bundle bundle) {
        long jUptimeMillis;
        StringBuilder sb;
        long jUptimeMillis2 = SystemClock.uptimeMillis();
        try {
            try {
                try {
                    final String providerAuthorities = getProviderAuthorities();
                    Bundle bundle2 = (Bundle) providerExecutor.submit(new Callable() { // from class: com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper$$ExternalSyntheticLambda2
                        @Override // java.util.concurrent.Callable
                        public final Object call() {
                            return FashionGalleryHelper.callProviderMethod$lambda$5(context, providerAuthorities, str, bundle);
                        }
                    }).get(5000L, TimeUnit.MILLISECONDS);
                    Log.i("Keyguard-Editor-FashionGalleryHelper", "callProviderMethod: " + str + " used " + (SystemClock.uptimeMillis() - jUptimeMillis2) + "ms.");
                    return bundle2;
                } catch (Exception e) {
                    Log.e("Keyguard-Editor-FashionGalleryHelper", "callProviderMethod: " + str + " error", e);
                    jUptimeMillis = SystemClock.uptimeMillis() - jUptimeMillis2;
                    sb = new StringBuilder();
                    sb.append("callProviderMethod: ");
                    sb.append(str);
                    sb.append(" used ");
                    sb.append(jUptimeMillis);
                    sb.append("ms.");
                    Log.i("Keyguard-Editor-FashionGalleryHelper", sb.toString());
                    return null;
                }
            } catch (TimeoutException unused) {
                Log.e("Keyguard-Editor-FashionGalleryHelper", "callProviderMethod: " + str + " TIMEOUT after 5000ms");
                jUptimeMillis = SystemClock.uptimeMillis() - jUptimeMillis2;
                sb = new StringBuilder();
                sb.append("callProviderMethod: ");
                sb.append(str);
                sb.append(" used ");
                sb.append(jUptimeMillis);
                sb.append("ms.");
                Log.i("Keyguard-Editor-FashionGalleryHelper", sb.toString());
                return null;
            }
        } catch (Throwable th) {
            Log.i("Keyguard-Editor-FashionGalleryHelper", "callProviderMethod: " + str + " used " + (SystemClock.uptimeMillis() - jUptimeMillis2) + "ms.");
            throw th;
        }
    }

    public final void closeLockscreenMagazine(Context context, String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        Log.d("Keyguard-Editor-FashionGalleryHelper", "closeLockscreenMagazine, wallpaperType= " + str);
        Bundle bundle = new Bundle();
        bundle.putString("wallpaper_type", str);
        callProviderMethod(context, "closeLockscreenMagazine", bundle);
        isFashionGalleryOpened = Boolean.FALSE;
    }

    public final Uri createWallpaperContentUri(File file, String path) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(path, "path");
        if (!file.exists()) {
            Log.w("Keyguard-Editor-FashionGalleryHelper", "createContentUri failed: file not exists.");
            return null;
        }
        String str = "content://miui.keyguard.editor.templatefileprovider" + path;
        Log.i("Keyguard-Editor-FashionGalleryHelper", "contentUri = " + str);
        return Uri.parse(str);
    }

    public final String getDeskGalleryJson(Context context, String str, Integer num) {
        Intrinsics.checkNotNullParameter(context, "context");
        return getGalleryJsonIfAvailable(context, str, 1, num);
    }

    public final String getFashionGalleryPackage() {
        return DeviceUtil.INSTANCE.isInternationalBuild() ? "com.miui.android.fashiongallery" : "com.mfashiongallery.emag";
    }

    public final String getGalleryJsonIfAvailable(Context context, String str, int i, Integer num) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (str == null) {
            str = WallpaperController.Companion.getInstance(context).getLockWallpaperType();
        }
        boolean zIsGalleryAvailable = isGalleryAvailable(context, str, num, i == 2);
        Log.i("Keyguard-Editor-FashionGalleryHelper", "getGalleryJsonIfAvailable -> wallpaperType = " + str + ", isGalleryAvailable = " + zIsGalleryAvailable + ", singleWhich = " + i);
        String galleryJson = zIsGalleryAvailable ? WallpaperController.Companion.getInstance(context).getGalleryJson(i) : null;
        if (galleryJson == null) {
            Log.i("Keyguard-Editor-FashionGalleryHelper", "getGalleryJsonIfAvailable: null content");
        }
        return galleryJson;
    }

    public final int getGalleryMagazineStatus(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (context instanceof Activity) {
            context = ((Activity) context).getApplicationContext();
        }
        Intrinsics.checkNotNull(context);
        boolean zIsFashionGalleryInstalled = isFashionGalleryInstalled(context);
        boolean zIsDeskMagazineOpen = isDeskMagazineOpen(context);
        boolean zIsLockscreenMagazineOpen = isLockscreenMagazineOpen(context);
        int i = 0;
        if (zIsFashionGalleryInstalled) {
            i = (zIsLockscreenMagazineOpen ? 2 : 0) | (zIsDeskMagazineOpen ? 1 : 0);
        }
        Log.i("Keyguard-Editor-FashionGalleryHelper", "isGalleryInstalledAndDeskOpen -> isInstalled: " + zIsFashionGalleryInstalled + ", isDeskOpen: " + zIsDeskMagazineOpen + " isLockOpen: " + zIsLockscreenMagazineOpen + " status=" + i);
        return i;
    }

    public final GalleryStatusBean getGalleryStatus(Context context, boolean z) {
        if (z) {
            galleryStatusBean = null;
        }
        if (context == null) {
            return new GalleryStatusBean(null, null, null, null, 15, null);
        }
        if (galleryStatusBean == null) {
            Context applicationContext = context.getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            if (isFashionGalleryInstalled(applicationContext)) {
                try {
                    Context applicationContext2 = context.getApplicationContext();
                    Intrinsics.checkNotNullExpressionValue(applicationContext2, "getApplicationContext(...)");
                    Bundle bundleCallProviderMethod = callProviderMethod(applicationContext2, "getWallpaperCarouselInfoOs3", null);
                    String string = bundleCallProviderMethod != null ? bundleCallProviderMethod.getString("wcInfo") : null;
                    Log.i("Keyguard-Editor-FashionGalleryHelper", "getGalleryStatusBean: jsonStr == " + string);
                    galleryStatusBean = (string == null || string.length() == 0) ? new GalleryStatusBean(null, null, null, null, 15, null) : (GalleryStatusBean) GsonSingletonKt.getGSON_SINGLETON().fromJson(string, GalleryStatusBean.class);
                } catch (Exception e) {
                    Log.d("Keyguard-Editor-FashionGalleryHelper", "getGalleryStatusBean: error", e);
                    galleryStatusBean = new GalleryStatusBean(null, null, null, null, 15, null);
                }
            }
        }
        GalleryStatusBean galleryStatusBean2 = galleryStatusBean;
        return galleryStatusBean2 == null ? new GalleryStatusBean(null, null, null, null, 15, null) : galleryStatusBean2;
    }

    public final String getLockGalleryJson(Context context, String str, Integer num) {
        Intrinsics.checkNotNullParameter(context, "context");
        return getGalleryJsonIfAvailable(context, str, 2, num);
    }

    public final String getProviderAuthorities() {
        return DeviceUtil.INSTANCE.isInternationalBuild() ? "com.miui.android.fashiongallery.thirdPartyWallpaperProvider" : "com.xiaomi.tv.gallerylockscreen.lockscreen_magazine_provider";
    }

    public final boolean isDeskMagazineOpen(Context context) {
        Bundle bundleCallProviderMethod = callProviderMethod(context, "getMagazineServiceStatus", null);
        if (bundleCallProviderMethod != null) {
            return bundleCallProviderMethod.getBoolean("desk", false);
        }
        Log.w("Keyguard-Editor-FashionGalleryHelper", "isWallpaperMagazineOpen: outBundle = null");
        return false;
    }

    public final boolean isFashionGalleryInstalled(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            return context.getPackageManager().getPackageInfo(getFashionGalleryPackage(), 0) != null;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-FashionGalleryHelper", "isFashionGalleryInstalled " + e.getMessage());
            return false;
        }
    }

    public final Object isFashionGalleryOpened(Context context, Continuation continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass2(context, null), continuation);
    }

    public final boolean isFashionGalleryWallpaper(String str) {
        return Intrinsics.areEqual(str, "gallery");
    }

    public final boolean isGalleryAvailable(Context context, String str, Integer num, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        boolean zIsFashionGalleryWallpaper = isFashionGalleryWallpaper(str);
        boolean zIsLoop = isLoop(context, z, num);
        Log.i("Keyguard-Editor-FashionGalleryHelper", "isGalleryAvailable -> isInstallAndOpen: " + zIsLoop + ", isGalleryWallpaper: " + zIsFashionGalleryWallpaper + ", wallpaperType: " + str);
        return zIsLoop && zIsFashionGalleryWallpaper;
    }

    public final boolean isGalleryInstalledAndOpen(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (context instanceof Activity) {
            context = ((Activity) context).getApplicationContext();
        }
        Intrinsics.checkNotNull(context);
        boolean zIsFashionGalleryInstalled = isFashionGalleryInstalled(context);
        boolean zIsLockscreenMagazineOpen = isLockscreenMagazineOpen(context);
        Log.i("Keyguard-Editor-FashionGalleryHelper", "isInstalledAndOpen -> isInstalled: " + zIsFashionGalleryInstalled + ", isOpen: " + zIsLockscreenMagazineOpen);
        return zIsFashionGalleryInstalled && zIsLockscreenMagazineOpen;
    }

    public final boolean isLockscreenMagazineOpen(Context context) {
        Bundle bundleCallProviderMethod = callProviderMethod(context, "isLockscreenMagazineOpen", null);
        if (bundleCallProviderMethod != null) {
            return bundleCallProviderMethod.getBoolean("open", false);
        }
        Log.w("Keyguard-Editor-FashionGalleryHelper", "isLockscreenMagazineOpen: outBundle = null");
        return false;
    }

    public final boolean isLoop(Context context, boolean z, Integer num) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (DeviceUtil.INSTANCE.isInternationalBuild()) {
            return z && isGalleryInstalledAndOpen(context);
        }
        if (!isFashionGalleryInstalled(context)) {
            return false;
        }
        if (z) {
            return (num != null && num.intValue() == 2) || (num != null && num.intValue() == 0);
        }
        return (num != null && num.intValue() == 2) || (num != null && num.intValue() == 1);
    }

    public final boolean isLoopTemplate(Integer num) {
        return (num != null ? num.intValue() : -1) >= 0;
    }

    public final boolean obtainFashionGalleryOpened(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (isFashionGalleryOpened == null) {
            isFashionGalleryOpened = Boolean.valueOf(isGalleryInstalledAndOpen(context));
        }
        Boolean bool = isFashionGalleryOpened;
        Intrinsics.checkNotNull(bool);
        return bool.booleanValue();
    }

    public final void resetFashionGalleryOpened() {
        isFashionGalleryOpened = null;
    }

    public final void setCLOSE_SOURCE(int i) {
        CLOSE_SOURCE = i;
    }
}
