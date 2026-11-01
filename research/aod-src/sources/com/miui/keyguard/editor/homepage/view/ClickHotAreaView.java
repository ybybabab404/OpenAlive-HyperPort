package com.miui.keyguard.editor.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: ClickHotAreaView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ClickHotAreaView extends View {
    public int mHeight;
    public float mUpRawX;
    public float mUpRawY;
    public int mWidth;

    /* JADX INFO: compiled from: ClickHotAreaView.kt */
    @Metadata
    public final class InternalCheckClickListenerWrapper implements View.OnClickListener {
        public final View.OnClickListener l;
        public final int[] rawLocation;
        public final /* synthetic */ ClickHotAreaView this$0;

        public InternalCheckClickListenerWrapper(ClickHotAreaView clickHotAreaView, View.OnClickListener l) {
            Intrinsics.checkNotNullParameter(l, "l");
            this.this$0 = clickHotAreaView;
            this.l = l;
            this.rawLocation = new int[2];
        }

        public final boolean isTouchInTargetView() {
            int[] iArr = this.rawLocation;
            int i = iArr[0];
            int i2 = iArr[1];
            return this.this$0.mUpRawX >= ((float) i) && this.this$0.mUpRawX <= ((float) (i + this.this$0.mWidth)) && this.this$0.mUpRawY >= ((float) i2) && this.this$0.mUpRawY <= ((float) (i2 + this.this$0.mHeight));
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            if (this.this$0.mUpRawX == -1.0f || this.this$0.mUpRawY == -1.0f || this.this$0.mWidth == -1 || this.this$0.mHeight == -1) {
                Log.w("Keyguard-Editor-ClickHotAreaView", "onInternalClick: mUpRawX = " + this.this$0.mUpRawX + ", mUpRawY = " + this.this$0.mUpRawY + ", mWidth = " + this.this$0.mWidth + ", mHeight = " + this.this$0.mHeight);
                this.l.onClick(view);
                return;
            }
            view.getLocationInWindow(this.rawLocation);
            if (isTouchInTargetView()) {
                this.l.onClick(view);
                return;
            }
            float f = this.this$0.mUpRawX;
            float f2 = this.this$0.mUpRawY;
            int[] iArr = this.rawLocation;
            Log.w("Keyguard-Editor-ClickHotAreaView", "onInternalClick ignored: outside click. mUpRawX = " + f + ", mUpRawY = " + f2 + ", rawLeft = " + iArr[0] + ", rawTop = " + iArr[1] + ", mWidth = " + this.this$0.mWidth + ", mHeight = " + this.this$0.mHeight);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ClickHotAreaView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ClickHotAreaView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ClickHotAreaView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mUpRawX = -1.0f;
        this.mUpRawY = -1.0f;
        this.mWidth = -1;
        this.mHeight = -1;
    }

    public /* synthetic */ ClickHotAreaView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public static final void onTouchEvent$lambda$1(ClickHotAreaView clickHotAreaView) {
        clickHotAreaView.mUpRawX = -1.0f;
        clickHotAreaView.mUpRawY = -1.0f;
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mWidth = i;
        this.mHeight = i2;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent == null) {
            Log.w("Keyguard-Editor-ClickHotAreaView", "Unexpected null MotionEvent");
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            Log.i("Keyguard-Editor-ClickHotAreaView", "Down");
            this.mUpRawX = motionEvent.getRawX();
            this.mUpRawY = motionEvent.getRawY();
        }
        boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
        if (action != 1 && action != 3) {
            return zOnTouchEvent;
        }
        Log.i("Keyguard-Editor-ClickHotAreaView", "onTouchEvent -> " + (action == 1 ? "Up" : "Cancel"));
        post(new Runnable() { // from class: com.miui.keyguard.editor.homepage.view.ClickHotAreaView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ClickHotAreaView.onTouchEvent$lambda$1(this.f$0);
            }
        });
        return zOnTouchEvent;
    }

    @Override // android.view.View
    public void setOnClickListener(@Nullable View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            super.setOnClickListener(new InternalCheckClickListenerWrapper(this, onClickListener));
        }
    }
}
