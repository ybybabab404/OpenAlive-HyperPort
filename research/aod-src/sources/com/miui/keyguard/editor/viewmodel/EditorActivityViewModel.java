package com.miui.keyguard.editor.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.template.TemplateChangeRecorder;
import com.miui.keyguard.editor.homepage.bean.GalleryStatusBean;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.view.LockScreenTransformerAnimationListener;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
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
/* JADX INFO: compiled from: EditorActivityViewModel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class EditorActivityViewModel extends ViewModel {
    public static final Companion Companion = new Companion(null);
    public CommonConfig applyTemplateConfig;
    public LockScreenTransformerAnimationListener onTransformerCallback;
    public final TemplateChangeRecorder templateChangeRecorder = new TemplateChangeRecorder();
    public volatile boolean isTransformFinished = true;
    public final MutableLiveData mApplyStatus = new MutableLiveData();
    public GalleryStatusBean galleryStatusBean = new GalleryStatusBean(null, null, null, null, 15, null);
    public final Map colorModesCache = new LinkedHashMap();

    /* JADX INFO: compiled from: EditorActivityViewModel.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final CoroutineScope getCoroutineScope(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (context instanceof LifecycleOwner) {
                return LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) context);
            }
            if (context instanceof DeviceScreenshotHelper.ScreenshotContext) {
                return CoroutineScopeKt.CoroutineScope(Dispatchers.getMain());
            }
            throw new IllegalArgumentException("error context " + context);
        }

        public final EditorActivityViewModel getViewModel(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            EditorActivity editorActivity = context instanceof EditorActivity ? (EditorActivity) context : null;
            if (editorActivity != null) {
                return (EditorActivityViewModel) new ViewModelProvider(editorActivity).get(EditorActivityViewModel.class);
            }
            Log.d("EditorActivityViewModel", "Convert context error:" + context);
            return null;
        }

        public final EditorActivityViewModel getViewModel(EditorActivity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            return (EditorActivityViewModel) new ViewModelProvider(activity).get(EditorActivityViewModel.class);
        }

        public final void updateApplyStatusClone(int i) {
            ApplyStatusManager.INSTANCE.updateStatus(i);
        }
    }

    public final CommonConfig getApplyTemplateConfig() {
        return this.applyTemplateConfig;
    }

    public final LockScreenTransformerAnimationListener getOnTransformerCallback() {
        return this.onTransformerCallback;
    }

    public final TemplateChangeRecorder getTemplateChangeRecorder() {
        return this.templateChangeRecorder;
    }

    public final boolean isTransformFinished() {
        return this.isTransformFinished;
    }

    public final void observeApplyStatus(LifecycleOwner lifecycleOwner, Observer observer) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        this.mApplyStatus.observe(lifecycleOwner, observer);
    }

    public final void setApplyTemplateConfig(CommonConfig commonConfig) {
        this.applyTemplateConfig = commonConfig;
    }

    public final void setOnTransformerCallback(LockScreenTransformerAnimationListener lockScreenTransformerAnimationListener) {
        this.onTransformerCallback = lockScreenTransformerAnimationListener;
    }

    public final void setTransformFinished(boolean z) {
        this.isTransformFinished = z;
    }

    public final void updateApplyStatus(int i) {
        this.mApplyStatus.postValue(Integer.valueOf(i));
        ApplyStatusManager.INSTANCE.updateStatus(i);
    }
}
