package com.miui.aod.util;

import android.view.View;
import android.view.ViewTreeObserver;
import androidx.annotation.RequiresApi;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
/* JADX INFO: compiled from: ViewUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@Metadata
public final class MiuiPreDrawListener implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
    public static final Companion Companion = new Companion(null);
    public final Function1 onPreDraw;
    public final View view;
    public ViewTreeObserver viewTreeObserver;

    /* JADX INFO: compiled from: ViewUtils.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final MiuiPreDrawListener add(View view, Function1 onPreDraw) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(onPreDraw, "onPreDraw");
            MiuiPreDrawListener miuiPreDrawListener = new MiuiPreDrawListener(view, onPreDraw, null);
            view.getViewTreeObserver().addOnPreDrawListener(miuiPreDrawListener);
            view.addOnAttachStateChangeListener(miuiPreDrawListener);
            return miuiPreDrawListener;
        }
    }

    public MiuiPreDrawListener(View view, Function1 function1) {
        this.view = view;
        this.onPreDraw = function1;
        this.viewTreeObserver = view.getViewTreeObserver();
    }

    public /* synthetic */ MiuiPreDrawListener(View view, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(view, function1);
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public boolean onPreDraw() {
        return ((Boolean) this.onPreDraw.invoke(this)).booleanValue();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        this.viewTreeObserver = v.getViewTreeObserver();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        removeListener();
    }

    public final void removeListener() {
        if (this.viewTreeObserver.isAlive()) {
            this.viewTreeObserver.removeOnPreDrawListener(this);
        } else {
            this.view.getViewTreeObserver().removeOnPreDrawListener(this);
        }
        this.view.removeOnAttachStateChangeListener(this);
    }
}
