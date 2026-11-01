package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.database.ContentObserver;
import android.provider.Settings;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import miuix.autodensity.DensityConfigManager;

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
/* JADX INFO: compiled from: EditActivityTaskHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class EditActivityTaskHelper implements LifecycleEventObserver {
    public final WeakReference editorActivityWeak;
    public volatile boolean isCanRegisterObserver;
    public volatile boolean isRegisteredMiuiOpObserver;
    public final ContentObserver miuiOptimizationObserver;

    /* JADX INFO: compiled from: EditActivityTaskHelper.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Lifecycle.Event.values().length];
            try {
                iArr[Lifecycle.Event.ON_CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Lifecycle.Event.ON_DESTROY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.EditActivityTaskHelper$registerMiuiOptimizationObserver$1, reason: invalid class name */
    /* JADX INFO: compiled from: EditActivityTaskHelper.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.EditActivityTaskHelper$registerMiuiOptimizationObserver$1", f = "EditActivityTaskHelper.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Context context, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EditActivityTaskHelper.this.new AnonymousClass1(this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            try {
                if (!EditActivityTaskHelper.this.isCanRegisterObserver) {
                    return Unit.INSTANCE;
                }
                this.$context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("background_blur_enable"), false, EditActivityTaskHelper.this.miuiOptimizationObserver);
                EditActivityTaskHelper.this.isRegisteredMiuiOpObserver = true;
                Log.i("Keyguard-Editor-:EditActivityTaskHelper", "registerMiuiOptimizationObserver in worker thread");
                return Unit.INSTANCE;
            } catch (Exception unused) {
                Log.e("Keyguard-Editor-:EditActivityTaskHelper", "registerContentObserver for Optimization failure !");
            }
        }
    }

    public EditActivityTaskHelper(WeakReference editorActivityWeak) {
        Intrinsics.checkNotNullParameter(editorActivityWeak, "editorActivityWeak");
        this.editorActivityWeak = editorActivityWeak;
        this.isCanRegisterObserver = true;
        avoidAutoDensityCausingCrash(true);
        this.miuiOptimizationObserver = new ContentObserver() { // from class: com.miui.keyguard.editor.utils.EditActivityTaskHelper$miuiOptimizationObserver$1
            {
                super(null);
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                EditorActivity editorActivity = (EditorActivity) this.this$0.editorActivityWeak.get();
                if (editorActivity == null) {
                    return;
                }
                boolean z2 = Settings.Secure.getInt(editorActivity.getContentResolver(), "background_blur_enable", 0) == 1;
                Log.i("Keyguard-Editor-:EditActivityTaskHelper", "registerMiuiOptimizationObserver selfChange=" + z + " backgroundBlurEnable=" + EditorActivity.backgroundBlurEnable);
                if (editorActivity.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED) || z2 == EditorActivity.backgroundBlurEnable) {
                    EditorActivity.backgroundBlurEnable = z2;
                } else {
                    editorActivity.finish();
                }
            }
        };
    }

    public static final Unit avoidAutoDensityCausingCrash$lambda$0(EditActivityTaskHelper editActivityTaskHelper) {
        EditActivityTaskHelperKt.isAvoidAutoDensityCausingCrash = editActivityTaskHelper.avoidAutoDensityCausingCrash();
        if (EditActivityTaskHelperKt.isAvoidAutoDensityCausingCrash) {
            Log.i("Keyguard-Editor-:EditActivityTaskHelper", "avoidAutoDensityCausingCrash in worker thread");
        }
        return Unit.INSTANCE;
    }

    public final void avoidAutoDensityCausingCrash(boolean z) {
        if (EditActivityTaskHelperKt.isAvoidAutoDensityCausingCrash) {
            return;
        }
        if (z) {
            Task.submit(new Callable() { // from class: com.miui.keyguard.editor.utils.EditActivityTaskHelper$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return EditActivityTaskHelper.avoidAutoDensityCausingCrash$lambda$0(this.f$0);
                }
            });
        } else {
            EditActivityTaskHelperKt.isAvoidAutoDensityCausingCrash = avoidAutoDensityCausingCrash();
        }
    }

    public final boolean avoidAutoDensityCausingCrash() {
        try {
            Field declaredField = DensityConfigManager.class.getDeclaredField("mAutoDensityEnable");
            declaredField.setAccessible(true);
            declaredField.setBoolean(DensityConfigManager.getInstance(), false);
            return true;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-:EditActivityTaskHelper", "avoidAutoDensityCausingCrash error", e);
            return false;
        }
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(event, "event");
        EditorActivity editorActivity = (EditorActivity) this.editorActivityWeak.get();
        if (editorActivity == null) {
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[event.ordinal()];
        if (i == 1) {
            registerMiuiOptimizationObserver(editorActivity);
        } else {
            if (i != 2) {
                return;
            }
            this.isCanRegisterObserver = false;
            unRegisterMiuiOptimizationObserver(editorActivity);
        }
    }

    public final void registerMiuiOptimizationObserver(Context context) {
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), Dispatchers.getIO(), null, new AnonymousClass1(context, null), 2, null);
    }

    public final void unRegisterMiuiOptimizationObserver(Context context) {
        if (this.isRegisteredMiuiOpObserver) {
            context.getContentResolver().unregisterContentObserver(this.miuiOptimizationObserver);
        }
    }
}
