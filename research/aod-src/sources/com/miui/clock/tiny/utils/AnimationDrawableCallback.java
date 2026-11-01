package com.miui.clock.tiny.utils;

import android.graphics.drawable.Drawable;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: AnimationDrawableCallback.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public abstract class AnimationDrawableCallback implements Drawable.Callback {
    public int mCurrentFrame;
    public boolean mIsCallbackTriggered;
    public final Drawable mLastFrame;
    public final int mTotalFrames;
    public final Drawable.Callback mWrappedCallback;

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable who) {
        Drawable drawable;
        Intrinsics.checkNotNullParameter(who, "who");
        Drawable.Callback callback = this.mWrappedCallback;
        if (callback != null) {
            callback.invalidateDrawable(who);
        }
        if (this.mIsCallbackTriggered || (drawable = this.mLastFrame) == null || !Intrinsics.areEqual(drawable, who.getCurrent())) {
            return;
        }
        this.mIsCallbackTriggered = true;
        onAnimationCompleted();
    }

    public abstract void onAnimationAdvanced(int i, int i2);

    public abstract void onAnimationCompleted();

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable who, Runnable what, long j) {
        Intrinsics.checkNotNullParameter(who, "who");
        Intrinsics.checkNotNullParameter(what, "what");
        Drawable.Callback callback = this.mWrappedCallback;
        if (callback != null) {
            callback.scheduleDrawable(who, what, j);
        }
        onAnimationAdvanced(this.mCurrentFrame, this.mTotalFrames);
        this.mCurrentFrame++;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable who, Runnable what) {
        Intrinsics.checkNotNullParameter(who, "who");
        Intrinsics.checkNotNullParameter(what, "what");
        Drawable.Callback callback = this.mWrappedCallback;
        if (callback != null) {
            callback.unscheduleDrawable(who, what);
        }
    }
}
