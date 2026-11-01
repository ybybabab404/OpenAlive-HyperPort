package com.miui.keyguard.editor.track;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.task.Task;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.am;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miui.os.Build;

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
/* JADX INFO: compiled from: EditorTracker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class EditorTracker {
    public static final Companion Companion = new Companion(null);
    public static EditorTracker INSTANCE;
    public final OneTrack oneTrack;

    /* JADX INFO: compiled from: EditorTracker.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final OneTrack createOneTrack(Context context) {
            Configuration configurationBuild = new Configuration.Builder().setAppId("31000000235").setChannel(context.getPackageName()).setMode(OneTrack.Mode.APP).setExceptionCatcherEnable(true).build();
            OneTrack.setDebugMode(false);
            OneTrack.setTestMode(false);
            OneTrack.setRestrictGetNetworkInfo(true);
            OneTrack.setUseSystemNetTrafficOnly();
            OneTrack oneTrackCreateInstance = OneTrack.createInstance(context, configurationBuild);
            Intrinsics.checkNotNullExpressionValue(oneTrackCreateInstance, "createInstance(...)");
            return oneTrackCreateInstance;
        }

        public final EditorTracker getInstance(Context context) {
            EditorTracker editorTracker;
            Intrinsics.checkNotNullParameter(context, "context");
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (Build.IS_INTERNATIONAL_BUILD) {
                Log.w("Keyguard-Editor-EditorTracker", "getInstance: IS_INTERNATIONAL_BUILD");
                return null;
            }
            EditorTracker editorTracker2 = EditorTracker.INSTANCE;
            if (editorTracker2 != null) {
                return editorTracker2;
            }
            synchronized (this) {
                editorTracker = EditorTracker.INSTANCE;
                if (editorTracker == null) {
                    editorTracker = new EditorTracker(context, defaultConstructorMarker);
                    EditorTracker.INSTANCE = editorTracker;
                }
            }
            return editorTracker;
        }

        public final void putCommonParams(String tip, Map params) {
            Intrinsics.checkNotNullParameter(tip, "tip");
            Intrinsics.checkNotNullParameter(params, "params");
            params.put("data_version", 23042000);
            params.put(am.af, tip);
        }
    }

    public EditorTracker(Context context) {
        this.oneTrack = Companion.createOneTrack(context);
    }

    public /* synthetic */ EditorTracker(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    public static final void track$lambda$0(EditorTracker editorTracker, String str, Map map) {
        editorTracker.oneTrack.track(str, MapsKt.toMap(map));
    }

    public final void track(final String eventName, String tip, final Map params) {
        Intrinsics.checkNotNullParameter(eventName, "eventName");
        Intrinsics.checkNotNullParameter(tip, "tip");
        Intrinsics.checkNotNullParameter(params, "params");
        if (DeviceUtil.INSTANCE.isInternationalBuild()) {
            return;
        }
        Companion.putCommonParams(tip, params);
        Runnable runnable = new Runnable() { // from class: com.miui.keyguard.editor.track.EditorTracker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                EditorTracker.track$lambda$0(this.f$0, eventName, params);
            }
        };
        if (Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            Task.run(runnable);
        } else {
            runnable.run();
        }
    }
}
