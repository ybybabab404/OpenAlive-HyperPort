package com.miui.keyguard.editor.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TransitionKt;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
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
/* JADX INFO: compiled from: ColorSelectView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ColorSelectView extends View {
    public final RectF circleRect;
    public Paint colorPaint;
    public ColorSource colorSource;
    public Paint colorStrokePaint;
    public final float colorViewBorderWidth;
    public final float[] defaultColorHSL;
    public boolean hslSolidColorEnabled;
    public Paint hslSolidPaint;
    public int hslSolidRadius;
    public boolean mIsSelected;
    public Integer placeholderDrawable;
    public int radius;
    public float ringCenter;
    public int ringGap;
    public Paint ringPaint;
    public float ringRadius;
    public final RectF ringRect;
    public int ringWidth;
    public int size;

    /* JADX INFO: compiled from: ColorSelectView.kt */
    @Metadata
    public enum ColorSource {
        AUTO_PICK,
        MANUAL_SET,
        HSL,
        DEFAULT;

        public static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    }

    /* JADX INFO: compiled from: ColorSelectView.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ColorSource.values().length];
            try {
                iArr[ColorSource.MANUAL_SET.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ColorSource.AUTO_PICK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ColorSource.HSL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ColorSelectView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ColorSelectView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public ColorSelectView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.colorSource = ColorSource.DEFAULT;
        this.defaultColorHSL = new float[3];
        this.circleRect = new RectF();
        this.ringRect = new RectF();
        float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_color_select_view_border_width);
        this.colorViewBorderWidth = dimensionPixelSize;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.ColorItem, R.attr.colorItemStyle, 0);
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.ColorItem_itemStrokeColor, typedArrayObtainStyledAttributes.getResources().getColor(R.color.kg_black_10));
        typedArrayObtainStyledAttributes.recycle();
        initDimens();
        Paint paint = new Paint();
        paint.setStrokeWidth(this.radius);
        Paint.Style style = Paint.Style.FILL;
        paint.setStyle(style);
        paint.setAntiAlias(true);
        paint.setColor(-16776961);
        this.colorPaint = paint;
        Paint paint2 = new Paint();
        paint2.setStrokeWidth(dimensionPixelSize);
        Paint.Style style2 = Paint.Style.STROKE;
        paint2.setStyle(style2);
        paint2.setAntiAlias(true);
        paint2.setColor(color);
        this.colorStrokePaint = paint2;
        Paint paint3 = new Paint();
        paint3.setStrokeWidth(this.ringWidth);
        paint3.setStyle(style2);
        paint3.setAntiAlias(true);
        paint3.setColor(getResources().getColor(R.color.kg_editor_apply_button_background_for_lite));
        paint3.setAlpha(0);
        this.ringPaint = paint3;
        Paint paint4 = new Paint();
        paint4.setStrokeWidth(this.hslSolidRadius);
        paint4.setStyle(style);
        paint4.setAntiAlias(true);
        this.hslSolidPaint = paint4;
    }

    public /* synthetic */ ColorSelectView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public final void changeSelect(boolean z) {
        setStateDescription(z ? getContext().getString(R.string.kg_talkback_selected) : getContext().getString(R.string.kg_talkback_not_selected));
        if (this.mIsSelected == z) {
            return;
        }
        this.mIsSelected = z;
        if (z) {
            Folme.useValue(new Object[0]).setTo("show_ring_alpha", Float.valueOf(0.0f), "show_ring_scale", Float.valueOf(0.9f)).to("show_ring_alpha", Float.valueOf(1.0f), "show_ring_scale", Float.valueOf(1.0f), TransitionKt.getAnimConfig(1.0f, 0.28f, new TransitionListener() { // from class: com.miui.keyguard.editor.view.ColorSelectView.changeSelect.1
                @Override // miuix.animation.listener.TransitionListener
                public void onUpdate(Object obj, Collection collection) {
                    UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "show_ring_alpha");
                    UpdateInfo updateInfoFindByName2 = UpdateInfo.findByName(collection, "show_ring_scale");
                    if (updateInfoFindByName == null || updateInfoFindByName2 == null) {
                        return;
                    }
                    float fCoerceAtMost = RangesKt.coerceAtMost(updateInfoFindByName.getFloatValue(), 1.0f);
                    float fCoerceAtMost2 = RangesKt.coerceAtMost(updateInfoFindByName2.getFloatValue(), 1.0f);
                    ColorSelectView.this.ringPaint.setAlpha((int) (fCoerceAtMost * 255));
                    float f = ColorSelectView.this.ringRadius * fCoerceAtMost2;
                    ColorSelectView.this.ringRect.set(ColorSelectView.this.ringCenter - f, ColorSelectView.this.ringCenter - f, ColorSelectView.this.ringCenter + f, ColorSelectView.this.ringCenter + f);
                    ColorSelectView.this.invalidate();
                }
            }));
        } else {
            Folme.useValue(new Object[0]).setTo("dismiss_ring_alpha", Float.valueOf(1.0f), "dismiss_ring_scale", Float.valueOf(1.0f)).to("dismiss_ring_alpha", Float.valueOf(0.0f), "dismiss_ring_scale", Float.valueOf(0.9f), TransitionKt.getAnimConfig(1.0f, 0.28f, new TransitionListener() { // from class: com.miui.keyguard.editor.view.ColorSelectView.changeSelect.2
                @Override // miuix.animation.listener.TransitionListener
                public void onUpdate(Object obj, Collection collection) {
                    UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "show_ring_alpha");
                    UpdateInfo updateInfoFindByName2 = UpdateInfo.findByName(collection, "show_ring_scale");
                    if (updateInfoFindByName == null || updateInfoFindByName2 == null) {
                        return;
                    }
                    float fCoerceAtMost = RangesKt.coerceAtMost(updateInfoFindByName.getFloatValue(), 1.0f);
                    float fCoerceAtMost2 = RangesKt.coerceAtMost(updateInfoFindByName2.getFloatValue(), 1.0f);
                    ColorSelectView.this.ringPaint.setAlpha((int) (fCoerceAtMost * 255));
                    float f = ColorSelectView.this.ringRadius * fCoerceAtMost2;
                    ColorSelectView.this.ringRect.set(ColorSelectView.this.ringCenter - f, ColorSelectView.this.ringCenter - f, ColorSelectView.this.ringCenter + f, ColorSelectView.this.ringCenter + f);
                    ColorSelectView.this.invalidate();
                }
            }));
        }
    }

    public final void drawPlaceholderDrawable(Canvas canvas) {
        Integer num = this.placeholderDrawable;
        if (num != null) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), num.intValue());
            if (drawable != null) {
                RectF rectF = this.circleRect;
                Rect rect = new Rect();
                rectF.roundOut(rect);
                drawable.setBounds(rect);
            }
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }

    public final void initDimens() {
        this.size = getResources().getDimensionPixelSize(R.dimen.kg_select_color_view_size);
        this.ringWidth = (getResources().getDimensionPixelSize(R.dimen.kg_select_color_view_ring_width) / 2) * 2;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_select_color_view_gap);
        this.ringGap = dimensionPixelSize;
        int i = this.size;
        int i2 = this.ringWidth;
        int i3 = ((i - (i2 * 2)) - (dimensionPixelSize * 2)) / 2;
        this.radius = i3;
        this.size = (i2 + i3 + dimensionPixelSize) * 2;
        this.hslSolidRadius = i3 - getResources().getDimensionPixelSize(R.dimen.kg_select_color_view_hsl_solid_padding);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        int i = WhenMappings.$EnumSwitchMapping$0[this.colorSource.ordinal()];
        if (i == 1) {
            canvas.drawCircle(this.circleRect.centerX(), this.circleRect.centerY(), this.radius + (this.colorViewBorderWidth / 2), this.colorPaint);
            canvas.drawCircle(this.circleRect.centerX(), this.circleRect.centerY(), this.radius, this.colorStrokePaint);
        } else if (i == 2) {
            drawPlaceholderDrawable(canvas);
        } else if (i == 3) {
            drawPlaceholderDrawable(canvas);
            if (this.hslSolidColorEnabled) {
                int i2 = this.size;
                canvas.drawCircle(i2 / 2.0f, i2 / 2.0f, this.hslSolidRadius, this.hslSolidPaint);
            }
        }
        if (this.mIsSelected) {
            canvas.drawArc(this.ringRect, 0.0f, 360.0f, false, this.ringPaint);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int i3 = this.size;
        setMeasuredDimension(i3, i3);
        RectF rectF = this.circleRect;
        int i4 = this.ringWidth;
        int i5 = this.ringGap;
        int i6 = this.size;
        rectF.set(i4 + i5, i4 + i5, i6 - (i4 + i5), i6 - (i4 + i5));
        RectF rectF2 = this.ringRect;
        int i7 = this.ringWidth;
        int i8 = this.size;
        rectF2.set(i7 / 2, i7 / 2, i8 - (i7 / 2), i8 - (i7 / 2));
        float fWidth = this.ringRect.width() / 2.0f;
        this.ringRadius = fWidth;
        this.ringCenter = this.ringRect.left + fWidth;
    }

    public final void setColor(@ColorInt int i) {
        this.colorPaint.setColor(i);
        this.colorSource = ColorSource.MANUAL_SET;
        ColorUtils.colorToHSL(i, this.defaultColorHSL);
        invalidate();
    }

    public final void setColorSource(@DrawableRes int i, @NotNull ColorSource source) {
        Intrinsics.checkNotNullParameter(source, "source");
        this.placeholderDrawable = Integer.valueOf(i);
        this.colorSource = source;
        invalidate();
    }

    public final void setHslSolidColor(@Nullable Integer num, boolean z) {
        if (num == null) {
            this.hslSolidColorEnabled = false;
        } else {
            this.hslSolidColorEnabled = true;
            this.hslSolidPaint.setColor(num.intValue());
        }
        if (z) {
            invalidate();
        }
    }
}
