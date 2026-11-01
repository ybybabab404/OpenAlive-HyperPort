package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.widget.Toast;
import com.miui.keyguard.editor.EditorApplicationProxy;
import java.lang.ref.WeakReference;
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
/* JADX INFO: compiled from: ToastUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ToastUtil {
    public static final ToastUtil INSTANCE = new ToastUtil();
    public static final Lazy mCancelable$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.utils.ToastUtil$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.valueOf(ToastUtil.mCancelable_delegate$lambda$0());
        }
    });
    public static CharSequence mLastToastCharSequence;
    public static long mLastToastTime;
    public static WeakReference mToastReference;

    public static final boolean mCancelable_delegate$lambda$0() {
        return DeviceUtil.INSTANCE.isFlip();
    }

    public static /* synthetic */ void showText$default(ToastUtil toastUtil, Context context, CharSequence charSequence, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            context = EditorApplicationProxy.Companion.getApplication();
        }
        toastUtil.showText(context, charSequence, i);
    }

    public final void dismiss() {
        WeakReference weakReference;
        Toast toast;
        if (!getMCancelable() || (weakReference = mToastReference) == null || (toast = (Toast) weakReference.get()) == null) {
            return;
        }
        toast.cancel();
    }

    public final boolean getMCancelable() {
        return ((Boolean) mCancelable$delegate.getValue()).booleanValue();
    }

    public final void showText(int i, int i2) {
        String string = EditorApplicationProxy.Companion.getApplication().getResources().getString(i);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        showText$default(this, null, string, i2, 1, null);
    }

    public final void showText(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = context.getResources().getString(i);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        showText(context, string, 0);
    }

    public final void showText(Context context, CharSequence charSequence, int i) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = jCurrentTimeMillis - mLastToastTime;
        if (!Intrinsics.areEqual(charSequence, mLastToastCharSequence) || j > 2000 || j < 0) {
            mLastToastCharSequence = charSequence;
            mLastToastTime = jCurrentTimeMillis;
            Toast toastMakeText = null;
            if (getMCancelable()) {
                WeakReference weakReference = mToastReference;
                toastMakeText = weakReference != null ? (Toast) weakReference.get() : null;
                if (toastMakeText != null) {
                    toastMakeText.setText(charSequence);
                }
            }
            if (toastMakeText == null) {
                toastMakeText = Toast.makeText(context, charSequence, i);
            }
            mToastReference = new WeakReference(toastMakeText);
            Intrinsics.checkNotNull(toastMakeText);
            toastMakeText.show();
        }
    }

    public final void showText(Context context, String text) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(text, "text");
        showText(context, text, 0);
    }
}
