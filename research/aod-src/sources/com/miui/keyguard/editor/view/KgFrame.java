package com.miui.keyguard.editor.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.AnimatorKt;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
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
/* JADX INFO: compiled from: KgFrame.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class KgFrame extends View {
    public static final Companion Companion = new Companion(null);
    public final Path borderPath;
    public int borderRadius;
    public int borderWidth;
    public int currentColor;
    public int darkColor;
    public final Paint framePaint;
    public boolean fullyRounded;
    public final RectF innerRect;
    public int lightColor;
    public KgFrameParam mParam;
    public final RectF outerRect;
    public OnSelectListener selectListener;

    /* JADX INFO: compiled from: KgFrame.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: KgFrame.kt */
    @Metadata
    public static final class DefaultSelectListener implements OnSelectListener {
        @Override // com.miui.keyguard.editor.view.OnSelectListener
        public void onSelectChanged(View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            if (view.isSelected()) {
                AnimatorKt.alphaTo$default(view, 0.8f, 0.0f, 0.0f, null, 14, null);
            } else {
                AnimatorKt.alphaTo$default(view, 0.3f, 0.0f, 0.0f, null, 14, null);
            }
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public KgFrame(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public KgFrame(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        Paint paint = new Paint(1);
        paint.setStyle(Paint.Style.FILL);
        this.framePaint = paint;
        this.outerRect = new RectF();
        this.innerRect = new RectF();
        this.borderPath = new Path();
        setWillNotDraw(false);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.KgFrame);
        this.borderWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.KgFrame_kg_border_width, getResources().getDimensionPixelSize(R.dimen.kg_area_frame_border_width));
        this.borderRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.KgFrame_kg_border_radius, getResources().getDimensionPixelSize(R.dimen.kg_area_frame_border_radius));
        int i = R.styleable.KgFrame_kg_dark_color;
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        this.darkColor = typedArrayObtainStyledAttributes.getColor(i, viewUtil.getColor(this, R.color.kg_dark_wallpaper_edit_frame_color));
        this.lightColor = typedArrayObtainStyledAttributes.getColor(R.styleable.KgFrame_kg_light_color, viewUtil.getColor(this, R.color.kg_light_wallpaper_edit_frame_color));
        typedArrayObtainStyledAttributes.recycle();
    }

    public /* synthetic */ KgFrame(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    @Override // android.view.View
    public void dispatchSetSelected(boolean z) {
        OnSelectListener onSelectListener = this.selectListener;
        if (onSelectListener != null) {
            onSelectListener.onSelectChanged(this);
        }
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public final int effectiveBorderRadius() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            Integer numValueOf = Integer.valueOf(layoutParams.height);
            if (numValueOf.intValue() <= 0) {
                numValueOf = null;
            }
            if (numValueOf != null) {
                int iIntValue = numValueOf.intValue();
                return this.fullyRounded ? iIntValue / 2 : Math.min(this.borderRadius, iIntValue / 2);
            }
        }
        return this.borderRadius;
    }

    @Nullable
    public final KgFrameParam getFrameParam() {
        return this.mParam;
    }

    public final boolean getFullyRounded() {
        return this.fullyRounded;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public final boolean inRect(int i, int i2, int i3, boolean z, int i4) {
        Rect rect;
        KgFrameParam kgFrameParam = this.mParam;
        if (kgFrameParam != null && (rect = kgFrameParam.getRect()) != null) {
            if (z && i4 == 1) {
                i = i3 - i;
            }
            int i5 = rect.left;
            if (i <= rect.right && i5 <= i && i2 >= rect.top && i2 <= rect.bottom) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        int i = this.currentColor;
        if (i == 0 || this.borderWidth == 0) {
            return;
        }
        this.framePaint.setColor(i);
        float f = this.borderWidth;
        float fHeight = this.fullyRounded ? this.outerRect.height() / 2.0f : effectiveBorderRadius();
        float fMax = Math.max(0.0f, fHeight - f);
        this.borderPath.reset();
        this.borderPath.addRoundRect(this.outerRect, fHeight, fHeight, Path.Direction.CW);
        this.borderPath.addRoundRect(this.innerRect, fMax, fMax, Path.Direction.CCW);
        canvas.drawPath(this.borderPath, this.framePaint);
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i <= 0 || i2 <= 0) {
            return;
        }
        float f = this.borderWidth;
        float f2 = i;
        float f3 = i2;
        this.outerRect.set(0.0f, 0.0f, f2, f3);
        this.innerRect.set(f, f, f2 - f, f3 - f);
    }

    public final void setBorderRadius(int i) {
        this.borderRadius = i;
    }

    public final void setBorderWidth(int i) {
        this.borderWidth = i;
    }

    public final void setColorMode(int i) {
        int i2 = i == 0 ? this.darkColor : this.lightColor;
        setAlpha(isSelected() ? 0.8f : 0.3f);
        this.currentColor = i2;
        setBackground(null);
        invalidate();
    }

    public final void setDarkColor(int i) {
        this.darkColor = i;
    }

    public final void setFrameParam(@Nullable KgFrameParam kgFrameParam) {
        this.mParam = kgFrameParam;
    }

    public final void setFullyRounded(boolean z) {
        this.fullyRounded = z;
    }

    public final void setLightColor(int i) {
        this.lightColor = i;
    }

    public final void setOnSelectListener(@NotNull OnSelectListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.selectListener = listener;
    }

    public final void updateIfNeeded(KgFrameParam kgFrameParam) {
        KgFrameParam kgFrameParam2;
        if (Objects.equals(this.mParam, kgFrameParam)) {
            return;
        }
        this.mParam = kgFrameParam;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        KgFrameParam kgFrameParam3 = this.mParam;
        if ((kgFrameParam3 != null ? kgFrameParam3.getRect() : null) != null) {
            KgFrameParam kgFrameParam4 = this.mParam;
            Rect rect = kgFrameParam4 != null ? kgFrameParam4.getRect() : null;
            Intrinsics.checkNotNull(rect);
            marginLayoutParams.width = rect.width();
            marginLayoutParams.height = rect.height();
            marginLayoutParams.topMargin = rect.top;
            marginLayoutParams.setMarginStart(rect.left);
        }
        KgFrameParam kgFrameParam5 = this.mParam;
        if ((kgFrameParam5 != null ? Integer.valueOf(kgFrameParam5.getGravity()) : null) != null && ((kgFrameParam2 = this.mParam) == null || kgFrameParam2.getGravity() != 0)) {
            ViewUtil viewUtil = ViewUtil.INSTANCE;
            KgFrameParam kgFrameParam6 = this.mParam;
            Integer numValueOf = kgFrameParam6 != null ? Integer.valueOf(kgFrameParam6.getGravity()) : null;
            Intrinsics.checkNotNull(numValueOf);
            viewUtil.setGravity(this, numValueOf.intValue());
        }
        KgFrameParam kgFrameParam7 = this.mParam;
        if (kgFrameParam7 == null || getLayoutDirection() != kgFrameParam7.getLayoutDirection()) {
            KgFrameParam kgFrameParam8 = this.mParam;
            if ((kgFrameParam8 != null ? Integer.valueOf(kgFrameParam8.getLayoutDirection()) : null) != null) {
                KgFrameParam kgFrameParam9 = this.mParam;
                Integer numValueOf2 = kgFrameParam9 != null ? Integer.valueOf(kgFrameParam9.getLayoutDirection()) : null;
                Intrinsics.checkNotNull(numValueOf2);
                setLayoutDirection(numValueOf2.intValue());
            }
        }
        setLayoutParams(marginLayoutParams);
    }
}
