package com.miui.keyguard.editor.homepage.view.generator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.core.content.ContextCompat;
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
/* JADX INFO: compiled from: DynamicViewFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class DynamicViewFactory {
    public final Context context;

    public DynamicViewFactory(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public static /* synthetic */ View create$default(DynamicViewFactory dynamicViewFactory, FactoryParams factoryParams, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: create");
        }
        if ((i & 1) != 0) {
            factoryParams = null;
        }
        return dynamicViewFactory.create(factoryParams);
    }

    public abstract View create(FactoryParams factoryParams);

    public final FrameLayout.LayoutParams generateFrameLayoutParams(int i, int i2) {
        return new FrameLayout.LayoutParams(i, i2);
    }

    public final RelativeLayout.LayoutParams generateRelativeLayoutParams(int i, int i2) {
        return new RelativeLayout.LayoutParams(i, i2);
    }

    public final int getColor(int i) {
        return ContextCompat.getColor(this.context, i);
    }

    public final Context getContext() {
        return this.context;
    }

    public final int getDimenPixelSize(int i) {
        return getResources().getDimensionPixelSize(i);
    }

    public final Typeface getFont(int i) {
        String string = getString(i);
        if (string == null) {
            return null;
        }
        return getFont(string);
    }

    public final Typeface getFont(String str) {
        Typeface typefaceCreate = Typeface.create(str, 0);
        Intrinsics.checkNotNullExpressionValue(typefaceCreate, "create(...)");
        return typefaceCreate;
    }

    public final Resources getResources() {
        Resources resources = this.context.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
        return resources;
    }

    public final String getString(int i) {
        return getResources().getString(i);
    }

    public final int matchParent() {
        return -1;
    }

    public final int wrapContent() {
        return -2;
    }
}
