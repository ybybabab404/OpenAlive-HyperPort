package com.miui.keyguard.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ClockTemplateVerticalRecyclerView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ClockTemplateVerticalRecyclerView extends RecyclerView {
    public float initialX;
    public float initialY;
    public boolean settlingOnDown;
    public final int touchSlop;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ClockTemplateVerticalRecyclerView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ClockTemplateVerticalRecyclerView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ClockTemplateVerticalRecyclerView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public /* synthetic */ ClockTemplateVerticalRecyclerView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0066  */
    @Override // androidx.recyclerview.widget.SpringRecyclerView, androidx.recyclerview.widget.RemixRecyclerView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        int actionMasked = e.getActionMasked();
        if (actionMasked == 0) {
            this.initialX = e.getX();
            this.initialY = e.getY();
            if (getScrollState() == 2) {
                this.settlingOnDown = true;
                return false;
            }
            this.settlingOnDown = false;
        } else if (actionMasked == 1) {
            this.settlingOnDown = false;
        } else if (actionMasked != 2) {
            if (actionMasked == 3) {
                this.settlingOnDown = false;
            }
        } else if (this.settlingOnDown) {
            float fAbs = Math.abs(e.getX() - this.initialX);
            float fAbs2 = Math.abs(e.getY() - this.initialY);
            int i = this.touchSlop;
            if (fAbs2 <= i || fAbs2 <= fAbs) {
                if (fAbs > i && fAbs > fAbs2) {
                    this.settlingOnDown = false;
                }
                return false;
            }
            this.settlingOnDown = false;
            MotionEvent motionEventObtain = MotionEvent.obtain(e.getDownTime(), e.getEventTime(), 0, this.initialX, this.initialY, 0);
            super.onInterceptTouchEvent(motionEventObtain);
            motionEventObtain.recycle();
            return super.onInterceptTouchEvent(e);
        }
        return super.onInterceptTouchEvent(e);
    }
}
