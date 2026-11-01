package com.miui.keyguard.editor.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TransitionKt;
import com.miui.keyguard.editor.utils.ClickUtil;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import miuix.animation.Folme;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
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
/* JADX INFO: compiled from: FilterIconLayout.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FilterIconLayout extends FrameLayout {
    public long animToken;
    public boolean isAnim;
    public boolean mIsSelected;
    public float ringBorder;
    public float ringHeight;
    public final Paint ringPaint;
    public float ringRadius;
    public final RectF ringRect;
    public float ringWidth;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FilterIconLayout(@NotNull Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FilterIconLayout(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.filterIconLayoutStyle);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FilterIconLayout(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.ringBorder = getResources().getDimensionPixelSize(R.dimen.kg_filter_icon_view_ring_width);
        this.ringRadius = getResources().getDimensionPixelSize(R.dimen.kg_filter_drawable_selector_item_radius);
        this.ringRect = new RectF();
        this.ringWidth = getResources().getDimensionPixelSize(R.dimen.kg_filter_icon_item_width);
        this.ringHeight = getResources().getDimensionPixelSize(R.dimen.kg_filter_icon_item_height);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FilterIconLayout, i, 0);
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.FilterIconLayout_ringColor, -16776961);
        typedArrayObtainStyledAttributes.recycle();
        Paint paint = new Paint();
        paint.setStrokeWidth(this.ringBorder);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setAlpha(0);
        this.ringPaint = paint;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float getRingCenterX() {
        return this.ringWidth / 2.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float getRingCenterY() {
        return this.ringHeight / 2.0f;
    }

    public final void changeSelect(boolean z, boolean z2) {
        final long jNanoTime = System.nanoTime();
        this.animToken = jNanoTime;
        this.mIsSelected = z;
        String string = z ? getContext().getString(R.string.kg_talkback_selected) : getContext().getString(R.string.kg_talkback_not_selected);
        Button button = (Button) findViewById(R.id.filter_title);
        if (button != null) {
            ViewCompat.setStateDescription(button, string);
        }
        ImageView imageView = (ImageView) findViewById(R.id.drawable_selector_item_icon);
        if (imageView != null) {
            ViewCompat.setStateDescription(imageView, string);
        }
        this.isAnim = z2;
        if (!z2) {
            this.ringPaint.setAlpha(z ? 255 : 0);
            ClickUtil.INSTANCE.isClickProcessing().set(false);
        } else if (z) {
            Folme.useValue(new Object[0]).setTo("show_ring_alpha", Float.valueOf(0.0f), "show_ring_scale", Float.valueOf(0.9f)).to("show_ring_alpha", Float.valueOf(1.0f), "show_ring_scale", Float.valueOf(1.0f), TransitionKt.getAnimConfig(1.0f, 0.28f, new TransitionListener() { // from class: com.miui.keyguard.editor.view.FilterIconLayout.changeSelect.3
                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    FilterIconLayout.this.isAnim = false;
                    ClickUtil.INSTANCE.isClickProcessing().set(false);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    FilterIconLayout.this.isAnim = false;
                    ClickUtil.INSTANCE.isClickProcessing().set(false);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onUpdate(Object obj, Collection collection) {
                    UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "show_ring_alpha");
                    UpdateInfo updateInfoFindByName2 = UpdateInfo.findByName(collection, "show_ring_scale");
                    if (FilterIconLayout.this.animToken != jNanoTime || updateInfoFindByName == null || updateInfoFindByName2 == null) {
                        return;
                    }
                    float fCoerceAtMost = RangesKt.coerceAtMost(updateInfoFindByName.getFloatValue(), 1.0f);
                    float fCoerceAtMost2 = RangesKt.coerceAtMost(updateInfoFindByName2.getFloatValue(), 1.0f);
                    FilterIconLayout.this.ringPaint.setAlpha((int) (fCoerceAtMost * 255));
                    float f = (FilterIconLayout.this.ringWidth * fCoerceAtMost2) / 2.0f;
                    float f2 = (FilterIconLayout.this.ringHeight * fCoerceAtMost2) / 2.0f;
                    float f3 = FilterIconLayout.this.ringBorder / 2.0f;
                    FilterIconLayout.this.ringRect.set((FilterIconLayout.this.getRingCenterX() - f) + f3, (FilterIconLayout.this.getRingCenterY() - f2) + f3, (FilterIconLayout.this.getRingCenterX() + f) - f3, FilterIconLayout.this.getRingCenterY() + (f2 - f3));
                    FilterIconLayout.this.invalidate();
                }
            }));
        } else {
            Folme.useValue(new Object[0]).setTo("dismiss_ring_alpha", Float.valueOf(1.0f), "dismiss_ring_scale", Float.valueOf(1.0f)).to("dismiss_ring_alpha", Float.valueOf(0.0f), "dismiss_ring_scale", Float.valueOf(0.9f), TransitionKt.getAnimConfig(1.0f, 0.28f, new TransitionListener() { // from class: com.miui.keyguard.editor.view.FilterIconLayout.changeSelect.4
                @Override // miuix.animation.listener.TransitionListener
                public void onCancel(Object obj) {
                    FilterIconLayout.this.isAnim = false;
                    ClickUtil.INSTANCE.isClickProcessing().set(false);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    FilterIconLayout.this.isAnim = false;
                    ClickUtil.INSTANCE.isClickProcessing().set(false);
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onUpdate(Object obj, Collection collection) {
                    UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "dismiss_ring_alpha");
                    UpdateInfo updateInfoFindByName2 = UpdateInfo.findByName(collection, "dismiss_ring_scale");
                    if (FilterIconLayout.this.animToken != jNanoTime || updateInfoFindByName == null || updateInfoFindByName2 == null) {
                        return;
                    }
                    float fCoerceAtMost = RangesKt.coerceAtMost(updateInfoFindByName.getFloatValue(), 1.0f);
                    float fCoerceAtMost2 = RangesKt.coerceAtMost(updateInfoFindByName2.getFloatValue(), 1.0f);
                    FilterIconLayout.this.ringPaint.setAlpha((int) (fCoerceAtMost * 255));
                    float f = (FilterIconLayout.this.ringWidth * fCoerceAtMost2) / 2.0f;
                    float f2 = (FilterIconLayout.this.ringHeight * fCoerceAtMost2) / 2.0f;
                    float f3 = FilterIconLayout.this.ringBorder / 2.0f;
                    FilterIconLayout.this.ringRect.set((FilterIconLayout.this.getRingCenterX() - f) + f3, (FilterIconLayout.this.getRingCenterY() - f2) + f3, (FilterIconLayout.this.getRingCenterX() + f) - f3, FilterIconLayout.this.getRingCenterY() + (f2 - f3));
                    FilterIconLayout.this.invalidate();
                }
            }));
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        if (this.mIsSelected || this.isAnim) {
            RectF rectF = this.ringRect;
            float f = this.ringRadius;
            canvas.drawRoundRect(rectF, f, f, this.ringPaint);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.ringWidth = getWidth();
        float height = getHeight();
        this.ringHeight = height;
        RectF rectF = this.ringRect;
        float f = this.ringBorder;
        float f2 = 2;
        rectF.set(f / f2, f / f2, this.ringWidth - (f / f2), height - (f / f2));
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    public final void setRadius(float f) {
        this.ringRadius = f;
    }
}
