package com.miui.aod.util;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.util.Log;
import android.widget.TextView;
import java.lang.reflect.Method;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: FrameworkReflectUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class FrameworkReflectUtils {
    public static final FrameworkReflectUtils INSTANCE = new FrameworkReflectUtils();
    public static final Lazy setIgnoreAnimationScale$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.util.FrameworkReflectUtils$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FrameworkReflectUtils.setIgnoreAnimationScale_delegate$lambda$0();
        }
    });
    public static final Lazy setTextViewUseBoundsForWidthMethod$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.util.FrameworkReflectUtils$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return FrameworkReflectUtils.setTextViewUseBoundsForWidthMethod_delegate$lambda$1();
        }
    });

    public static final void setIgnoreAnimationScale(AnimatedVectorDrawable animatedVectorDrawable, boolean z) {
        Intrinsics.checkNotNullParameter(animatedVectorDrawable, "<this>");
        try {
            Method setIgnoreAnimationScale = INSTANCE.getSetIgnoreAnimationScale();
            if (setIgnoreAnimationScale != null) {
                setIgnoreAnimationScale.invoke(animatedVectorDrawable, Boolean.valueOf(z));
            }
        } catch (Throwable th) {
            Log.e("FrameworkReflectUtils", "calling setIgnoreAnimationScale failed.", th);
        }
    }

    public static final Method setIgnoreAnimationScale_delegate$lambda$0() {
        try {
            return AnimatedVectorDrawable.class.getMethod("setIgnoreAnimationScale", Boolean.TYPE);
        } catch (Throwable th) {
            Log.e("FrameworkReflectUtils", "get setIgnoreAnimationScale method failed.", th);
            return null;
        }
    }

    public static final Method setTextViewUseBoundsForWidthMethod_delegate$lambda$1() {
        try {
            return TextView.class.getMethod("setUseBoundsForWidth", Boolean.TYPE);
        } catch (Throwable th) {
            Log.w("FrameworkReflectUtils", "get setUseBoundsForWidth method failed.", th);
            return null;
        }
    }

    public static final void setUseBoundsForWidthCompat(TextView textView, boolean z) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        try {
            Method setTextViewUseBoundsForWidthMethod = INSTANCE.getSetTextViewUseBoundsForWidthMethod();
            if (setTextViewUseBoundsForWidthMethod != null) {
                setTextViewUseBoundsForWidthMethod.invoke(textView, Boolean.valueOf(z));
            }
        } catch (Throwable unused) {
        }
    }

    public final Method getSetIgnoreAnimationScale() {
        return (Method) setIgnoreAnimationScale$delegate.getValue();
    }

    public final Method getSetTextViewUseBoundsForWidthMethod() {
        return (Method) setTextViewUseBoundsForWidthMethod$delegate.getValue();
    }
}
