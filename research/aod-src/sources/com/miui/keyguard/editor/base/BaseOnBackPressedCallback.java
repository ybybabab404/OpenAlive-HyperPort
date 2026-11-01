package com.miui.keyguard.editor.base;

import android.util.Log;
import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
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
/* JADX INFO: compiled from: BaseOnBackPressedCallback.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public class BaseOnBackPressedCallback extends OnBackPressedCallback {
    public final FragmentActivity activity;
    public final String tag;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseOnBackPressedCallback(FragmentActivity activity) {
        super(true);
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.activity = activity;
        this.tag = "BaseOnBackPressedCallback";
    }

    @Override // androidx.activity.OnBackPressedCallback
    public void handleOnBackPressed() {
        Log.i(this.tag, "handleOnBackPressed");
        if (this.activity.getSupportFragmentManager().getBackStackEntryCount() > 1) {
            popBackStackImmediateSafely();
            Log.i(this.tag, "handleOnBackPressed popBackStack");
        } else {
            Log.i(this.tag, "handleOnBackPressed finish");
            this.activity.finish();
        }
    }

    public final void popBackStackImmediateSafely() {
        FragmentManager supportFragmentManager = this.activity.getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "getSupportFragmentManager(...)");
        boolean zIsStateSaved = supportFragmentManager.isStateSaved();
        boolean zIsDestroyed = supportFragmentManager.isDestroyed();
        if (!zIsStateSaved && !zIsDestroyed) {
            supportFragmentManager.popBackStackImmediate();
            return;
        }
        Log.i(this.tag, "popBackStackImmediateSafely can't pop back stack " + zIsStateSaved + " " + zIsDestroyed);
    }
}
