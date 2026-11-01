package com.miui.keyguard.editor;

import android.util.Log;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.lang.ref.WeakReference;
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
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;

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
/* JADX INFO: compiled from: EditorServiceManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class EditorServiceWatchDog {
    public final long SERVICE_TIME_OUT;
    public final String TAG;
    public final WeakReference activityRef;
    public Job watchDogJob;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.EditorServiceWatchDog$1, reason: invalid class name */
    /* JADX INFO: compiled from: EditorServiceManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.EditorServiceWatchDog$1", f = "EditorServiceManager.kt", l = {332}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EditorServiceWatchDog.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long j = EditorServiceWatchDog.this.SERVICE_TIME_OUT;
                this.label = 1;
                if (DelayKt.delay(j, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            if (!EditorServiceManager.Companion.getInstance().isKeyguardEditorCallbackReady()) {
                Log.e(EditorServiceWatchDog.this.TAG, "keyguard callback not ready in " + EditorServiceWatchDog.this.SERVICE_TIME_OUT + "ms");
                EditorActivity editorActivity = (EditorActivity) EditorServiceWatchDog.this.activityRef.get();
                if (editorActivity != null) {
                    editorActivity.finishActivity();
                }
            }
            return Unit.INSTANCE;
        }
    }

    public EditorServiceWatchDog(EditorActivity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.TAG = "EditorServiceWatchDog";
        this.SERVICE_TIME_OUT = 3000L;
        this.activityRef = new WeakReference(activity);
        this.watchDogJob = BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(activity), null, null, new AnonymousClass1(null), 3, null);
    }

    public final void cancel() {
        Job job = this.watchDogJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
    }
}
