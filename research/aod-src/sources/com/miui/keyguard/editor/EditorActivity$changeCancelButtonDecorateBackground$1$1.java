package com.miui.keyguard.editor;

import android.widget.TextView;
import com.miui.keyguard.editor.utils.ViewUtil;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: EditorActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.EditorActivity$changeCancelButtonDecorateBackground$1$1", f = "EditorActivity.kt", l = {556}, m = "invokeSuspend")
public final class EditorActivity$changeCancelButtonDecorateBackground$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ boolean $fullScreenPreview;
    public final /* synthetic */ boolean $isDarkWallpaper;
    public final /* synthetic */ TextView $this_apply;
    public int label;
    public final /* synthetic */ EditorActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditorActivity$changeCancelButtonDecorateBackground$1$1(boolean z, TextView textView, EditorActivity editorActivity, boolean z2, Continuation continuation) {
        super(2, continuation);
        this.$fullScreenPreview = z;
        this.$this_apply = textView;
        this.this$0 = editorActivity;
        this.$isDarkWallpaper = z2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new EditorActivity$changeCancelButtonDecorateBackground$1$1(this.$fullScreenPreview, this.$this_apply, this.this$0, this.$isDarkWallpaper, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((EditorActivity$changeCancelButtonDecorateBackground$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int color;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long j = this.$fullScreenPreview ? 100L : 0L;
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
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        TextView textView = this.$this_apply;
        int i2 = R.dimen.kg_top_button_corner_radius;
        if (this.$fullScreenPreview) {
            color = this.this$0.getColor(this.$isDarkWallpaper ? R.color.kg_editor_cancel_button_background_for_lite_dark : R.color.kg_editor_cancel_button_background_for_lite_light);
        } else {
            color = this.this$0.getColor(R.color.kg_editor_cancel_button_main_item_background_for_lite);
        }
        ViewUtil.decorateBackgroundByDefault$default(viewUtil, textView, i2, 0, 0, Boxing.boxInt(color), false, 6, null);
        return Unit.INSTANCE;
    }
}
