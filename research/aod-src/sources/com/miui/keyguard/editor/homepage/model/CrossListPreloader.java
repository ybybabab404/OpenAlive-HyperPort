package com.miui.keyguard.editor.homepage.model;

import android.app.Application;
import android.app.KeyguardManager;
import android.os.Trace;
import android.util.Log;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateApiImpl;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.utils.task.Task;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
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
/* JADX INFO: compiled from: CrossListDataPreloader.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class CrossListPreloader extends CrossListDataProvider {
    public volatile SynchronousQueue allHistoryTemplatesBean;
    public final Lazy allHistoryTemplatesTask$delegate;
    public final Lazy allPresetTemplateTask$delegate;
    public volatile SynchronousQueue allPresetTemplatesBean;
    public volatile SynchronousQueue currentTemplateBean;
    public final Lazy currentTemplateTask$delegate;
    public final Lazy thirdPartyThemeTask$delegate;
    public volatile SynchronousQueue thirdPartyThemeTemplateBean;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CrossListPreloader(final Application context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.thirdPartyThemeTask$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListPreloader$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListPreloader.thirdPartyThemeTask_delegate$lambda$5(this.f$0);
            }
        });
        this.currentTemplateTask$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListPreloader$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListPreloader.currentTemplateTask_delegate$lambda$9(this.f$0, context);
            }
        });
        this.allHistoryTemplatesTask$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListPreloader$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListPreloader.allHistoryTemplatesTask_delegate$lambda$12(this.f$0);
            }
        });
        this.allPresetTemplateTask$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.homepage.model.CrossListPreloader$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CrossListPreloader.allPresetTemplateTask_delegate$lambda$15(this.f$0);
            }
        });
    }

    public static final Runnable allHistoryTemplatesTask_delegate$lambda$12(final CrossListPreloader crossListPreloader) {
        return new Runnable() { // from class: com.miui.keyguard.editor.homepage.model.CrossListPreloader$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                CrossListPreloader.allHistoryTemplatesTask_delegate$lambda$12$lambda$11(this.f$0);
            }
        };
    }

    public static final void allHistoryTemplatesTask_delegate$lambda$12$lambda$11(CrossListPreloader crossListPreloader) {
        crossListPreloader.allHistoryTemplatesBean = new SynchronousQueue();
        try {
            Log.i("Keyguard-Editor-CrossListPreloader", "allHistoryTemplatesTask preload start");
            List list = (List) crossListPreloader.getAllHistoryTemplatesSupplier().get();
            SynchronousQueue synchronousQueue = crossListPreloader.allHistoryTemplatesBean;
            if (synchronousQueue != null) {
                synchronousQueue.put(new PreloadWrapper(list));
            }
        } catch (Exception e) {
            SynchronousQueue synchronousQueue2 = crossListPreloader.allHistoryTemplatesBean;
            if (synchronousQueue2 != null) {
                synchronousQueue2.put(CrossListDataPreloaderKt.empty());
            }
            Log.e("Keyguard-Editor-CrossListPreloader", "allHistoryTemplatesTask preload error: " + e);
        } finally {
            Log.i("Keyguard-Editor-CrossListPreloader", "allHistoryTemplatesTask preload completed");
        }
    }

    public static final Runnable allPresetTemplateTask_delegate$lambda$15(final CrossListPreloader crossListPreloader) {
        return new Runnable() { // from class: com.miui.keyguard.editor.homepage.model.CrossListPreloader$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                CrossListPreloader.allPresetTemplateTask_delegate$lambda$15$lambda$14(this.f$0);
            }
        };
    }

    public static final void allPresetTemplateTask_delegate$lambda$15$lambda$14(CrossListPreloader crossListPreloader) {
        crossListPreloader.allPresetTemplatesBean = new SynchronousQueue();
        try {
            Log.i("Keyguard-Editor-CrossListPreloader", "allPresetTemplateTask preload start");
            List list = (List) crossListPreloader.getAllPresetTemplateSupplier().get();
            SynchronousQueue synchronousQueue = crossListPreloader.allPresetTemplatesBean;
            if (synchronousQueue != null) {
                synchronousQueue.put(new PreloadWrapper(list));
            }
        } catch (Exception e) {
            SynchronousQueue synchronousQueue2 = crossListPreloader.allPresetTemplatesBean;
            if (synchronousQueue2 != null) {
                synchronousQueue2.put(CrossListDataPreloaderKt.empty());
            }
            Log.e("Keyguard-Editor-CrossListPreloader", "allPresetTemplateTask preload error: ", e);
        } finally {
            Log.i("Keyguard-Editor-CrossListPreloader", "allPresetTemplateTask preload completed");
        }
    }

    public static final Runnable currentTemplateTask_delegate$lambda$9(final CrossListPreloader crossListPreloader, final Application application) {
        return new Runnable() { // from class: com.miui.keyguard.editor.homepage.model.CrossListPreloader$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                CrossListPreloader.currentTemplateTask_delegate$lambda$9$lambda$8(this.f$0, application);
            }
        };
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0040 A[Catch: all -> 0x0039, Exception -> 0x003b, Merged into TryCatch #1 {all -> 0x0039, Exception -> 0x003b, blocks: (B:3:0x000b, B:5:0x001c, B:7:0x002e, B:14:0x0040, B:16:0x0049, B:19:0x0056, B:21:0x005a, B:22:0x0061), top: B:27:0x000b }] */
    /* JADX WARN: Code duplicated, block: B:16:0x0049 A[Catch: all -> 0x0039, Exception -> 0x003b, Merged into TryCatch #1 {all -> 0x0039, Exception -> 0x003b, blocks: (B:3:0x000b, B:5:0x001c, B:7:0x002e, B:14:0x0040, B:16:0x0049, B:19:0x0056, B:21:0x005a, B:22:0x0061), top: B:27:0x000b }, TRY_LEAVE] */
    public static final void currentTemplateTask_delegate$lambda$9$lambda$8(CrossListPreloader crossListPreloader, Application application) {
        SynchronousQueue synchronousQueue;
        Unit unit;
        crossListPreloader.currentTemplateBean = new SynchronousQueue();
        try {
            Log.i("Keyguard-Editor-CrossListPreloader", "currentTemplateTask preload start");
            TemplateItemBean templateItemBean = (TemplateItemBean) crossListPreloader.getCurrentTemplateSupplier().get();
            if (templateItemBean != null) {
                Log.d("Keyguard-Editor-CrossListPreloader", "put new current config");
                TemplateApi.Companion.getInstance(application).enableGetCurrentLockWallpaperFromWM();
                SynchronousQueue synchronousQueue2 = crossListPreloader.currentTemplateBean;
                if (synchronousQueue2 != null) {
                    synchronousQueue2.put(new PreloadWrapper(templateItemBean));
                    unit = Unit.INSTANCE;
                } else {
                    unit = null;
                }
                if (unit == null) {
                    Log.d("Keyguard-Editor-CrossListPreloader", "put empty current config");
                    synchronousQueue = crossListPreloader.currentTemplateBean;
                    if (synchronousQueue != null) {
                        synchronousQueue.put(CrossListDataPreloaderKt.empty());
                        Unit unit2 = Unit.INSTANCE;
                    }
                }
            } else {
                Log.d("Keyguard-Editor-CrossListPreloader", "put empty current config");
                synchronousQueue = crossListPreloader.currentTemplateBean;
                if (synchronousQueue != null) {
                    synchronousQueue.put(CrossListDataPreloaderKt.empty());
                    Unit unit3 = Unit.INSTANCE;
                }
            }
        } catch (Exception e) {
            SynchronousQueue synchronousQueue3 = crossListPreloader.currentTemplateBean;
            if (synchronousQueue3 != null) {
                synchronousQueue3.put(CrossListDataPreloaderKt.empty());
            }
            Log.e("Keyguard-Editor-CrossListPreloader", "currentTemplateTask preload error: " + e);
        } finally {
            Log.i("Keyguard-Editor-CrossListPreloader", "currentTemplateTask preload completed");
        }
    }

    private final Runnable getAllHistoryTemplatesTask() {
        return (Runnable) this.allHistoryTemplatesTask$delegate.getValue();
    }

    private final Runnable getAllPresetTemplateTask() {
        return (Runnable) this.allPresetTemplateTask$delegate.getValue();
    }

    private final Runnable getCurrentTemplateTask() {
        return (Runnable) this.currentTemplateTask$delegate.getValue();
    }

    private final Runnable getThirdPartyThemeTask() {
        return (Runnable) this.thirdPartyThemeTask$delegate.getValue();
    }

    public static final void preload$lambda$1(final CrossListPreloader crossListPreloader) {
        Log.i("Keyguard-Editor-CrossListPreloader", "preload start");
        Object systemService = crossListPreloader.getContext().getSystemService("keyguard");
        KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
        TemplateApiImpl.Companion.setWaitFlushConfig(keyguardManager != null ? keyguardManager.isKeyguardLocked() : false);
        Task.run(crossListPreloader.getThirdPartyThemeTask());
        Task.run(crossListPreloader.getCurrentTemplateTask());
        Task.run(crossListPreloader.getAllHistoryTemplatesTask());
        Task.run(crossListPreloader.getAllPresetTemplateTask());
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.homepage.model.CrossListPreloader$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                CrossListPreloader.preload$lambda$1$lambda$0(this.f$0);
            }
        });
    }

    public static final void preload$lambda$1$lambda$0(CrossListPreloader crossListPreloader) {
        FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
        fashionGalleryHelper.obtainFashionGalleryOpened(crossListPreloader.getContext());
        FashionGalleryHelper.getGalleryStatus$default(fashionGalleryHelper, crossListPreloader.getContext(), false, 2, null);
    }

    public static final Runnable thirdPartyThemeTask_delegate$lambda$5(final CrossListPreloader crossListPreloader) {
        return new Runnable() { // from class: com.miui.keyguard.editor.homepage.model.CrossListPreloader$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                CrossListPreloader.thirdPartyThemeTask_delegate$lambda$5$lambda$4(this.f$0);
            }
        };
    }

    /* JADX WARN: Code duplicated, block: B:14:0x003a A[Catch: all -> 0x0033, Exception -> 0x0035, Merged into TryCatch #0 {all -> 0x0033, Exception -> 0x0035, blocks: (B:3:0x0012, B:5:0x0024, B:7:0x0028, B:14:0x003a, B:16:0x003e, B:19:0x004b, B:21:0x004f, B:22:0x0056), top: B:27:0x0012 }] */
    /* JADX WARN: Code duplicated, block: B:16:0x003e A[Catch: all -> 0x0033, Exception -> 0x0035, Merged into TryCatch #0 {all -> 0x0033, Exception -> 0x0035, blocks: (B:3:0x0012, B:5:0x0024, B:7:0x0028, B:14:0x003a, B:16:0x003e, B:19:0x004b, B:21:0x004f, B:22:0x0056), top: B:27:0x0012 }, TRY_LEAVE] */
    public static final void thirdPartyThemeTask_delegate$lambda$5$lambda$4(CrossListPreloader crossListPreloader) {
        SynchronousQueue synchronousQueue;
        Unit unit;
        Trace.beginSection("Keyguard-Editor-CrossListPreloader.thirdPartyThemeTask");
        crossListPreloader.thirdPartyThemeTemplateBean = new SynchronousQueue(true);
        try {
            Log.i("Keyguard-Editor-CrossListPreloader", "thirdPartyThemeTask preload start");
            TemplateItemBean templateItemBean = (TemplateItemBean) crossListPreloader.getThirdPartyThemeSupplier().get();
            if (templateItemBean == null) {
                synchronousQueue = crossListPreloader.thirdPartyThemeTemplateBean;
                if (synchronousQueue != null) {
                    synchronousQueue.put(CrossListDataPreloaderKt.empty());
                    Unit unit2 = Unit.INSTANCE;
                }
            } else {
                SynchronousQueue synchronousQueue2 = crossListPreloader.thirdPartyThemeTemplateBean;
                if (synchronousQueue2 != null) {
                    synchronousQueue2.put(new PreloadWrapper(templateItemBean));
                    unit = Unit.INSTANCE;
                } else {
                    unit = null;
                }
                if (unit == null) {
                    synchronousQueue = crossListPreloader.thirdPartyThemeTemplateBean;
                    if (synchronousQueue != null) {
                        synchronousQueue.put(CrossListDataPreloaderKt.empty());
                        Unit unit3 = Unit.INSTANCE;
                    }
                }
            }
        } catch (Exception e) {
            SynchronousQueue synchronousQueue3 = crossListPreloader.thirdPartyThemeTemplateBean;
            if (synchronousQueue3 != null) {
                synchronousQueue3.put(CrossListDataPreloaderKt.empty());
            }
            Log.e("Keyguard-Editor-CrossListPreloader", "thirdPartyThemeTask preload error " + e);
        } finally {
            Log.i("Keyguard-Editor-CrossListPreloader", "thirdPartyThemeTask preload completed");
        }
    }

    public final void clear() {
        Log.i("Keyguard-Editor-CrossListPreloader", "clear preload cache");
        this.thirdPartyThemeTemplateBean = null;
        this.currentTemplateBean = null;
        this.allHistoryTemplatesBean = null;
        this.allPresetTemplatesBean = null;
    }

    public final void invalidData(String reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        Log.d("Keyguard-Editor-CrossListPreloader", "invalid data for: " + reason);
        SynchronousQueue synchronousQueue = this.currentTemplateBean;
        if (synchronousQueue != null) {
            synchronousQueue.clear();
        }
        this.currentTemplateBean = null;
        setCurrentTemplateApi(null);
    }

    public final void preload() {
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.homepage.model.CrossListPreloader$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                CrossListPreloader.preload$lambda$1(this.f$0);
            }
        });
    }

    public final List takeAllHistoryTemplates() {
        SynchronousQueue synchronousQueue = this.allHistoryTemplatesBean;
        PreloadWrapper preloadWrapper = synchronousQueue != null ? (PreloadWrapper) synchronousQueue.poll(300L, TimeUnit.MILLISECONDS) : null;
        this.allHistoryTemplatesBean = null;
        if (preloadWrapper != null) {
            return (List) preloadWrapper.getData();
        }
        return null;
    }

    public final TemplateItemBean takeCurrentTemplate() {
        SynchronousQueue synchronousQueue = this.currentTemplateBean;
        Log.e("Keyguard-Editor-CrossListPreloader", "takeCurrentTemplate currentTemplateBean=" + (synchronousQueue != null ? Integer.valueOf(synchronousQueue.size()) : null));
        SynchronousQueue synchronousQueue2 = this.currentTemplateBean;
        PreloadWrapper preloadWrapper = synchronousQueue2 != null ? (PreloadWrapper) synchronousQueue2.poll(300L, TimeUnit.MILLISECONDS) : null;
        this.currentTemplateBean = null;
        setCurrentTemplateApi(null);
        if (preloadWrapper != null) {
            return (TemplateItemBean) preloadWrapper.getData();
        }
        return null;
    }

    public final List takePresetTemplates() {
        SynchronousQueue synchronousQueue = this.allPresetTemplatesBean;
        PreloadWrapper preloadWrapper = synchronousQueue != null ? (PreloadWrapper) synchronousQueue.poll(300L, TimeUnit.MILLISECONDS) : null;
        this.allPresetTemplatesBean = null;
        if (preloadWrapper != null) {
            return (List) preloadWrapper.getData();
        }
        return null;
    }

    public final TemplateItemBean takeThirdPartyThemeTemplate() {
        Log.e("Keyguard-Editor-CrossListPreloader", "takeThirdPartyThemeTemplate");
        SynchronousQueue synchronousQueue = this.thirdPartyThemeTemplateBean;
        PreloadWrapper preloadWrapper = synchronousQueue != null ? (PreloadWrapper) synchronousQueue.poll(300L, TimeUnit.MILLISECONDS) : null;
        this.thirdPartyThemeTemplateBean = null;
        if (preloadWrapper != null) {
            return (TemplateItemBean) preloadWrapper.getData();
        }
        return null;
    }
}
