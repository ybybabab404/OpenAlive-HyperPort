package com.miui.keyguard.shortcuts.manager;

import android.content.Context;
import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: MiuiShortcutManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutManager$onShortcutOverlayChanged$1$1", f = "MiuiShortcutManager.kt", l = {292, 302}, m = "invokeSuspend")
public final class ShortcutManager$onShortcutOverlayChanged$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Context $currentContext;
    public final /* synthetic */ ShortcutEntity $currentShortcut;
    public final /* synthetic */ boolean $isLeft;
    public Object L$0;
    public int label;
    public final /* synthetic */ ShortcutManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutManager$onShortcutOverlayChanged$1$1(Context context, ShortcutEntity shortcutEntity, boolean z, ShortcutManager shortcutManager, Continuation continuation) {
        super(2, continuation);
        this.$currentContext = context;
        this.$currentShortcut = shortcutEntity;
        this.$isLeft = z;
        this.this$0 = shortcutManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShortcutManager$onShortcutOverlayChanged$1$1(this.$currentContext, this.$currentShortcut, this.$isLeft, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((ShortcutManager$onShortcutOverlayChanged$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0083, code lost:
    
        if (r3.saveShortcutDataToSettingsAsync(r4, r5, r6, "overlay removed", r8) == r0) goto L26;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r11.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L24
            if (r1 == r3) goto L1f
            if (r1 != r2) goto L17
            java.lang.Object r11 = r11.L$0
            android.content.Context r11 = (android.content.Context) r11
            kotlin.ResultKt.throwOnFailure(r12)
            goto L86
        L17:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L1f:
            kotlin.ResultKt.throwOnFailure(r12)
            r8 = r11
            goto L46
        L24:
            kotlin.ResultKt.throwOnFailure(r12)
            r12 = r3
            com.miui.keyguard.shortcuts.utils.DataUtils r3 = com.miui.keyguard.shortcuts.utils.DataUtils.INSTANCE
            android.content.Context r4 = r11.$currentContext
            com.miui.keyguard.shortcuts.data.ShortcutEntity r1 = r11.$currentShortcut
            java.lang.String r5 = r1.getPackageName()
            com.miui.keyguard.shortcuts.data.ShortcutEntity r1 = r11.$currentShortcut
            java.lang.String r6 = r1.getTag()
            r11.label = r12
            r7 = 0
            r9 = 8
            r10 = 0
            r8 = r11
            java.lang.Object r12 = com.miui.keyguard.shortcuts.utils.DataUtils.fetchShortcutFromAppAsync$default(r3, r4, r5, r6, r7, r8, r9, r10)
            if (r12 != r0) goto L46
            goto L85
        L46:
            com.miui.keyguard.shortcuts.data.ShortcutEntity r12 = (com.miui.keyguard.shortcuts.data.ShortcutEntity) r12
            if (r12 == 0) goto L55
            com.miui.keyguard.shortcuts.data.ShortcutEntity r11 = r8.$currentShortcut
            boolean r11 = r11.isSystemDefault()
            r12.setSystemDefault(r11)
        L53:
            r6 = r12
            goto L5c
        L55:
            com.miui.keyguard.shortcuts.utils.DataUtils r11 = com.miui.keyguard.shortcuts.utils.DataUtils.INSTANCE
            com.miui.keyguard.shortcuts.data.ShortcutEntity r12 = r11.getEMPTY_ENTITY()
            goto L53
        L5c:
            boolean r11 = r8.$isLeft
            if (r11 == 0) goto L67
            com.miui.keyguard.shortcuts.manager.ShortcutManager r11 = r8.this$0
            com.miui.keyguard.shortcuts.data.ShortcutEntity r11 = r11.getShortcutDataLeft()
            goto L6d
        L67:
            com.miui.keyguard.shortcuts.manager.ShortcutManager r11 = r8.this$0
            com.miui.keyguard.shortcuts.data.ShortcutEntity r11 = r11.getShortcutDataRight()
        L6d:
            boolean r11 = r11.isUniqueTagSame(r6)
            if (r11 == 0) goto L86
            android.content.Context r4 = r8.$currentContext
            boolean r5 = r8.$isLeft
            com.miui.keyguard.shortcuts.utils.DataUtils r3 = com.miui.keyguard.shortcuts.utils.DataUtils.INSTANCE
            r8.L$0 = r4
            r8.label = r2
            java.lang.String r7 = "overlay removed"
            java.lang.Object r11 = r3.saveShortcutDataToSettingsAsync(r4, r5, r6, r7, r8)
            if (r11 != r0) goto L86
        L85:
            return r0
        L86:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.shortcuts.manager.ShortcutManager$onShortcutOverlayChanged$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
