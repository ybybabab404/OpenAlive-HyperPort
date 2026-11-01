package com.miui.aod.util;

import android.animation.Animator;
import android.os.Trace;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.listener.TransitionListener;

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
/* JADX INFO: compiled from: AnimTracer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public interface AnimTracer {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: AnimTracer.kt */
    @Metadata
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        public final ValueAnimListener animatorListener(String tag) {
            Intrinsics.checkNotNullParameter(tag, "tag");
            return new ValueAnimListener(tag);
        }

        public final Manual manual(String tag) {
            Intrinsics.checkNotNullParameter(tag, "tag");
            return new Manual(tag);
        }
    }

    /* JADX INFO: compiled from: AnimTracer.kt */
    @Metadata
    public static final class DefaultImpls {
        public static void beginTrace(AnimTracer animTracer) {
            animTracer.setStarted(true);
            Trace.beginAsyncSection(animTracer.getTag(), animTracer.getCookie());
        }

        public static void endTrace(AnimTracer animTracer) {
            if (animTracer.getStarted()) {
                Trace.endAsyncSection(animTracer.getTag(), animTracer.getCookie());
                animTracer.setStarted(false);
            }
        }
    }

    /* JADX INFO: compiled from: AnimTracer.kt */
    @Metadata
    public static final class FolmeListener extends TransitionListener implements AnimTracer {
        public final int cookie;
        public final Function2 onUpdate;
        public boolean started;
        public final String tag;

        public void beginTrace() {
            DefaultImpls.beginTrace(this);
        }

        public void endTrace() {
            DefaultImpls.endTrace(this);
        }

        @Override // com.miui.aod.util.AnimTracer
        public int getCookie() {
            return this.cookie;
        }

        @Override // com.miui.aod.util.AnimTracer
        public boolean getStarted() {
            return this.started;
        }

        @Override // com.miui.aod.util.AnimTracer
        public String getTag() {
            return this.tag;
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object obj) {
            beginTrace();
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onCancel(Object obj) {
            endTrace();
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            endTrace();
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object obj, Collection collection) {
            Function2 function2 = this.onUpdate;
            if (function2 != null) {
                function2.invoke(obj, collection);
            }
        }

        @Override // com.miui.aod.util.AnimTracer
        public void setStarted(boolean z) {
            this.started = z;
        }
    }

    /* JADX INFO: compiled from: AnimTracer.kt */
    @Metadata
    public static final class Manual implements AnimTracer {
        public final int cookie;
        public boolean started;
        public final String tag;

        public Manual(String tag) {
            Intrinsics.checkNotNullParameter(tag, "tag");
            this.tag = tag;
            this.cookie = (int) System.nanoTime();
        }

        public final void begin() {
            beginTrace();
        }

        public void beginTrace() {
            DefaultImpls.beginTrace(this);
        }

        public final void end() {
            endTrace();
        }

        public void endTrace() {
            DefaultImpls.endTrace(this);
        }

        @Override // com.miui.aod.util.AnimTracer
        public int getCookie() {
            return this.cookie;
        }

        @Override // com.miui.aod.util.AnimTracer
        public boolean getStarted() {
            return this.started;
        }

        @Override // com.miui.aod.util.AnimTracer
        public String getTag() {
            return this.tag;
        }

        @Override // com.miui.aod.util.AnimTracer
        public void setStarted(boolean z) {
            this.started = z;
        }
    }

    /* JADX INFO: compiled from: AnimTracer.kt */
    @Metadata
    public static final class ValueAnimListener implements Animator.AnimatorListener, AnimTracer {
        public final int cookie;
        public boolean started;
        public final String tag;

        public ValueAnimListener(String tag) {
            Intrinsics.checkNotNullParameter(tag, "tag");
            this.tag = tag;
            this.cookie = (int) System.nanoTime();
        }

        public void beginTrace() {
            DefaultImpls.beginTrace(this);
        }

        public void endTrace() {
            DefaultImpls.endTrace(this);
        }

        @Override // com.miui.aod.util.AnimTracer
        public int getCookie() {
            return this.cookie;
        }

        @Override // com.miui.aod.util.AnimTracer
        public boolean getStarted() {
            return this.started;
        }

        @Override // com.miui.aod.util.AnimTracer
        public String getTag() {
            return this.tag;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            endTrace();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            beginTrace();
        }

        @Override // com.miui.aod.util.AnimTracer
        public void setStarted(boolean z) {
            this.started = z;
        }
    }

    static Manual manual(String str) {
        return Companion.manual(str);
    }

    int getCookie();

    boolean getStarted();

    String getTag();

    void setStarted(boolean z);
}
