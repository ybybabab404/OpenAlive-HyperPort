package com.miui.keyguard.editor.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.util.Log;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
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
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

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
/* JADX INFO: compiled from: EditorObserver.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class EditorObserver {
    public final Activity activity;
    public boolean callFromKeyguard;
    public boolean shouldTryDismissKeyguardWhenStop;
    public Boolean startByEditor;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.EditorObserver$onEditorStop$1, reason: invalid class name */
    /* JADX INFO: compiled from: EditorObserver.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.EditorObserver$onEditorStop$1", f = "EditorObserver.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EditorObserver.this.new AnonymousClass1(continuation);
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
            Log.i("Keyguard-Editor-EditorObserver", "onActivityStop startByEditor = " + EditorObserver.this.startByEditor);
            if (EditorObserver.this.startByEditor == null || Intrinsics.areEqual(EditorObserver.this.startByEditor, Boxing.boxBoolean(false))) {
                Object systemService = EditorObserver.this.getActivity().getSystemService("keyguard");
                Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.KeyguardManager");
                ((KeyguardManager) systemService).requestDismissKeyguard(EditorObserver.this.getActivity(), null);
            }
            if (Intrinsics.areEqual(EditorObserver.this.startByEditor, Boxing.boxBoolean(true))) {
                EditorObserver.this.startByEditor = Boxing.boxBoolean(false);
            }
            return Unit.INSTANCE;
        }
    }

    public EditorObserver(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.activity = activity;
        this.shouldTryDismissKeyguardWhenStop = true;
    }

    public final Activity getActivity() {
        return this.activity;
    }

    public final void onCreate(boolean z) {
        this.callFromKeyguard = z;
    }

    public final void onEditorDestroy() {
        if (this.activity.isDestroyed()) {
            CoroutineScopeKt.cancel$default(EditorActivityViewModel.Companion.getCoroutineScope(this.activity), null, 1, null);
        }
    }

    public final void onEditorStop() {
        Log.i("Keyguard-Editor-EditorObserver", "onActivityStop shouldTryDismissKg = " + this.shouldTryDismissKeyguardWhenStop);
        if (!this.shouldTryDismissKeyguardWhenStop || !this.callFromKeyguard || this.activity.isFinishing()) {
            this.shouldTryDismissKeyguardWhenStop = true;
            return;
        }
        Log.i("Keyguard-Editor-EditorObserver", "onActivityStop " + this);
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(this.activity), null, null, new AnonymousClass1(null), 3, null);
    }

    public final void onStartActivity() {
        Log.i("Keyguard-Editor-EditorObserver", "startActivityForResult " + this);
        this.startByEditor = Boolean.TRUE;
    }

    public final void setShouldTryDismissKeyguardWhenStop(boolean z) {
        this.shouldTryDismissKeyguardWhenStop = z;
    }
}
