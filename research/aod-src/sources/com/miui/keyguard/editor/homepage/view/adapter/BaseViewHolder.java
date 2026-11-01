package com.miui.keyguard.editor.homepage.view.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import java.util.List;
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
/* JADX INFO: compiled from: BaseAdapterAndHolder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class BaseViewHolder<DataType, Callback> extends RecyclerView.ViewHolder {
    public Object mCallback;
    public Object mItemData;
    public int mX;
    public int mY;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseViewHolder(View itemView) {
        super(itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        this.mY = -1;
        this.mX = -1;
    }

    public final Object getCallback() {
        return this.mCallback;
    }

    public final Object getItemData() {
        return this.mItemData;
    }

    public abstract View getTargetContentView();

    public final int getX() {
        return this.mX;
    }

    public final int getY() {
        return this.mY;
    }

    public void onPageTransformed(int i, float f, float f2, int i2) {
        Object callback = getCallback();
        if (callback instanceof CrossViewPagerCallback) {
            View itemView = this.itemView;
            Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
            ((CrossViewPagerCallback) callback).onPageTransformed(i, this, itemView, f, f2, i2);
        }
    }

    public void onViewHolderBound(Object obj, int i) {
        this.mItemData = obj;
    }

    public void onViewHolderBound(Object obj, int i, List payloads) {
        Intrinsics.checkNotNullParameter(payloads, "payloads");
    }

    public abstract void onViewHolderCreated(View view);

    public final void setCallback(Object obj) {
        this.mCallback = obj;
    }

    public final void setX(int i) {
        this.mX = i;
    }

    public final void setY(int i) {
        this.mY = i;
    }
}
