package com.miui.clock.allInOne;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MiuiBlurUtils;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
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
    */
/* JADX INFO: compiled from: TimeView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class TimeView extends View {
    public final float DARK_BRIGHTNESS;
    public final int GLASS_BRIGHTNESS_INDEX;
    public final int GLASS_COLOR_A_INDEX;
    public final int GLASS_COLOR_B_INDEX;
    public final int GLASS_COLOR_G_INDEX;
    public final int GLASS_COLOR_MIX_INDEX;
    public final int GLASS_COLOR_POW_INDEX;
    public final int GLASS_COLOR_R_INDEX;
    public final int GLASS_EDGE_POW_INDEX;
    public final int GLASS_EDGE_PX_INDEX;
    public final int GLASS_SATURATION_INDEX;
    public final float LIGHT_BRIGHTNESS;
    public final String TAG;
    public ClockStyleInfo clockStyleInfo;
    public float fontAlpha;
    public final float[] glassData;
    public float height;
    public boolean isMiGlassEffectEnable;
    public float mAdvanceWidth;
    public float mColonAdvanceWidth;
    public float mColonAlpha;
    public final Path mColonPath;
    public int mColonSplitIndex;
    public float mDrawTextX;
    public float mDrawTextY;
    public int mFontStyle;
    public final Path mMinuteOnlyPath;
    public String mText;
    public final RectF mTextBounds;
    public int mTextColor;
    public int mTextHeight;
    public final Path mTextPath;
    public int mTextWidth;
    public int size;
    public final Paint textPaint;
    public float textSizePx;
    public int textTop;
    public float weight;
    public float width;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TimeView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TimeView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TimeView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.TAG = "TimeView";
        this.weight = 700.0f;
        this.width = 100.0f;
        this.height = 28.0f;
        this.size = 180;
        this.fontAlpha = 1.0f;
        this.mTextBounds = new RectF();
        this.mTextPath = new Path();
        this.mText = "00";
        this.mAdvanceWidth = -1.0f;
        this.mColonPath = new Path();
        this.mMinuteOnlyPath = new Path();
        this.mColonAlpha = 1.0f;
        this.mTextColor = -197380;
        this.textPaint = new Paint(65);
        this.mFontStyle = 21;
        this.glassData = new float[]{0.05f, 0.35f, 0.5f, 0.55f, 1.0f, 2.0f, 0.3f, 0.0f, 0.0f, 1.0f, 0.05f, 1.0f, 1.0f, 1.0f, 0.4f, 0.8f, 0.0f, 1.1f, 1.0f, 30.0f, 2.0f, 200.0f, 400.0f, 0.3f, 2.0f, -2.0f, 2.0f, -1.0f, 6.0f, 3.0f, 0.3f, 1.1764705f, 1.33f, 1.0f, 1.0f, 1.0f, 0.0f, 0.8f, 0.82f, 0.0f, 0.0f, 0.0f};
        init(context);
        this.LIGHT_BRIGHTNESS = 0.3f;
        this.GLASS_SATURATION_INDEX = 5;
        this.GLASS_BRIGHTNESS_INDEX = 6;
        this.GLASS_COLOR_POW_INDEX = 17;
        this.GLASS_COLOR_R_INDEX = 11;
        this.GLASS_COLOR_G_INDEX = 12;
        this.GLASS_COLOR_B_INDEX = 13;
        this.GLASS_COLOR_A_INDEX = 14;
        this.GLASS_COLOR_MIX_INDEX = 16;
        this.GLASS_EDGE_PX_INDEX = 19;
        this.GLASS_EDGE_POW_INDEX = 20;
    }

    public /* synthetic */ TimeView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    private final void setColorPow(float f) {
        this.glassData[this.GLASS_COLOR_POW_INDEX] = f;
    }

    public static /* synthetic */ void setGlassColor$default(TimeView timeView, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        timeView.setGlassColor(i, z);
    }

    private final void setSaturation(float f) {
        this.glassData[this.GLASS_SATURATION_INDEX] = f;
    }

    @Nullable
    public final ClockStyleInfo getClockStyleInfo() {
        return this.clockStyleInfo;
    }

    @JvmName
    public final float getDrawTextX() {
        return this.mDrawTextX;
    }

    @JvmName
    public final float getDrawTextY() {
        return this.mDrawTextY;
    }

    public final float getFontAlpha() {
        return this.fontAlpha;
    }

    @JvmName
    @NotNull
    public final float[] getGlassData() {
        return this.glassData;
    }

    public final int getSize() {
        return this.size;
    }

    @JvmName
    @NotNull
    public final String getText() {
        return this.mText;
    }

    @NotNull
    public final RectF getTextBounds() {
        return new RectF(this.mTextBounds);
    }

    @NotNull
    public final RectF getTextBoundsWithPosition() {
        RectF rectF = new RectF(this.mTextBounds);
        rectF.offset(this.mDrawTextX, this.mDrawTextY);
        return rectF;
    }

    public final int getTextTop() {
        return this.textTop;
    }

    public final float getTextWidth() {
        float f = this.mAdvanceWidth;
        return f >= 0.0f ? f : this.mTextBounds.width();
    }

    public final float getWeight() {
        return this.weight;
    }

    public final void handleParams() {
        TextMeasureUtils.applyFontVariation(this.textPaint, this.weight, this.width, this.height);
        MiuiBlurUtils.setPaintGlassEffect(this.textPaint, this.isMiGlassEffectEnable);
        measureAndUpdateTextParms();
    }

    public final void init(Context context) {
        this.textPaint.setTypeface(FontUtils.getMiSansRCF(context));
        this.textPaint.setTextSize(this.textSizePx);
        this.textPaint.setColor(this.mTextColor);
    }

    public final void invokeMiGlass(float[] fArr) {
        MiuiBlurUtils.setMiGlass(this, fArr);
    }

    public final void invokeMiGlassClip(float f, float f2, float f3, float f4) {
        MiuiBlurUtils.setMiGlassClip(this, f, f2, f3, f4);
    }

    public final void measureAndUpdateTextParms() {
        this.textPaint.setTextSize(this.textSizePx);
        Paint paint = this.textPaint;
        String str = this.mText;
        paint.getTextPath(str, 0, str.length(), 0.0f, 0.0f, this.mTextPath);
        if (needSimplifyPath()) {
            MiuiBlurUtils.simplifyPath(this.textPaint, this.mTextPath);
        }
        this.mTextPath.computeBounds(this.mTextBounds, true);
        this.mTextWidth = (int) this.mTextBounds.width();
        this.mTextHeight = (int) this.mTextBounds.height();
        this.textTop = (int) (this.mTextBounds.top + this.mDrawTextY);
        int i = this.mColonSplitIndex;
        if (i > 0 && this.mColonAlpha < 1.0f) {
            this.textPaint.getTextPath(this.mText, 0, i, 0.0f, 0.0f, this.mColonPath);
            Paint paint2 = this.textPaint;
            String str2 = this.mText;
            paint2.getTextPath(str2, this.mColonSplitIndex, str2.length(), 0.0f, 0.0f, this.mMinuteOnlyPath);
            if (needSimplifyPath()) {
                MiuiBlurUtils.simplifyPath(this.textPaint, this.mColonPath);
                MiuiBlurUtils.simplifyPath(this.textPaint, this.mMinuteOnlyPath);
            }
        }
        if (this.isMiGlassEffectEnable) {
            RectF rectF = new RectF();
            this.mTextPath.computeBounds(rectF, true);
            rectF.offset(this.mDrawTextX, this.mDrawTextY);
            float f = 50;
            invokeMiGlassClip(rectF.left - f, rectF.top - f, rectF.right + f, rectF.bottom + f);
            invokeMiGlass(this.glassData);
        }
    }

    public final boolean needSimplifyPath() {
        if (!this.isMiGlassEffectEnable) {
            return false;
        }
        int i = this.mFontStyle;
        return i == 23 || i == 24 || i == 25;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        handleParams();
        this.textPaint.setStrokeWidth(this.glassData[this.GLASS_EDGE_PX_INDEX]);
        this.textPaint.setStrokeMiter(this.glassData[this.GLASS_EDGE_POW_INDEX]);
        if (this.mColonSplitIndex <= 0 || this.mColonAlpha >= 1.0f) {
            this.textPaint.setAlpha((int) (this.fontAlpha * 255));
            canvas.translate(this.mDrawTextX, this.mDrawTextY);
            canvas.drawPath(this.mTextPath, this.textPaint);
            return;
        }
        canvas.translate(this.mDrawTextX, this.mDrawTextY);
        float f = this.mColonAlpha;
        if (f > 0.0f) {
            this.textPaint.setAlpha((int) (this.fontAlpha * f * 255));
            canvas.drawPath(this.mColonPath, this.textPaint);
        }
        this.textPaint.setAlpha((int) (this.fontAlpha * 255));
        canvas.translate(this.mColonAdvanceWidth, 0.0f);
        canvas.drawPath(this.mMinuteOnlyPath, this.textPaint);
    }

    public final void setAdvanceWidth(float f) {
        this.mAdvanceWidth = f;
    }

    public final void setBrightness(boolean z) {
        if (z) {
            this.glassData[this.GLASS_BRIGHTNESS_INDEX] = this.DARK_BRIGHTNESS;
        } else {
            this.glassData[this.GLASS_BRIGHTNESS_INDEX] = this.LIGHT_BRIGHTNESS;
        }
        invokeMiGlass(this.glassData);
    }

    public final void setClockParams(float f, float f2, float f3) {
        this.weight = f;
        this.width = f2;
        this.height = f3;
        invalidate();
    }

    public final void setClockParamsInternal(float f, float f2, float f3) {
        this.weight = f;
        this.width = f2;
        this.height = f3;
    }

    public final void setClockStyleInfo(@Nullable ClockStyleInfo clockStyleInfo) {
        this.clockStyleInfo = clockStyleInfo;
    }

    public final void setColonAdvanceWidth(float f) {
        this.mColonAdvanceWidth = f;
    }

    public final void setColonAlpha(float f) {
        if (this.mColonAlpha == f) {
            return;
        }
        this.mColonAlpha = f;
        invalidate();
    }

    public final void setColonSplitIndex(int i) {
        if (this.mColonSplitIndex != i) {
            this.mColonSplitIndex = i;
            invalidate();
        }
    }

    @JvmName
    public final void setDrawTextX(float f) {
        if (this.mDrawTextX == f) {
            return;
        }
        this.mDrawTextX = f;
        invalidate();
    }

    @JvmName
    public final void setDrawTextY(float f) {
        if (this.mDrawTextY == f) {
            return;
        }
        this.mDrawTextY = f;
        invalidate();
    }

    public final void setFontAlpha(float f) {
        this.fontAlpha = f;
        invalidate();
    }

    public final void setFontStyle(int i) {
        this.mFontStyle = i;
    }

    public final void setGlassColor(int i, boolean z) {
        float[] fArr = this.glassData;
        fArr[this.GLASS_COLOR_R_INDEX] = ((i >>> 16) & 255) / 255.0f;
        fArr[this.GLASS_COLOR_G_INDEX] = ((i >>> 8) & 255) / 255.0f;
        fArr[this.GLASS_COLOR_B_INDEX] = (i & 255) / 255.0f;
        fArr[this.GLASS_COLOR_A_INDEX] = z ? 0.0f : 0.7f;
        fArr[this.GLASS_COLOR_MIX_INDEX] = z ? 0.0f : 0.7f;
        invokeMiGlass(fArr);
    }

    public final void setGpuOptimize(boolean z) {
        int i = z ? AutoBottomSheetKt.ALIGN_SCREEN_END : 1;
        MiuiBlurUtils.setAtlasMaxPathSize(this.textPaint, i, i);
    }

    public final void setHeight(float f) {
        if (this.height == f) {
            return;
        }
        this.height = f;
        invalidate();
    }

    public final void setIsMiGlassEffectEnable(boolean z) {
        this.isMiGlassEffectEnable = z;
    }

    public final void setMiGlassEffectEnable(boolean z) {
        this.isMiGlassEffectEnable = z;
    }

    public final void setSize(int i) {
        if (this.size != i) {
            this.size = i;
            this.textPaint.setTextSize(TypedValue.applyDimension(1, i, getResources().getDisplayMetrics()));
            invalidate();
        }
    }

    public final void setSizeInternal(float f) {
        if (this.textSizePx == f) {
            return;
        }
        this.textSizePx = f;
        this.textPaint.setTextSize(f);
    }

    @JvmName
    public final void setText(@NotNull String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (Intrinsics.areEqual(this.mText, value)) {
            return;
        }
        this.mText = value;
        invalidate();
    }

    public final void setTextColor(int i) {
        this.mTextColor = i;
        this.textPaint.setColor(i);
        invalidate();
    }

    public final void setTransparency(float f) {
        setColorPow(1.5f - (1.1f * f));
        setSaturation((f * 0.5f) + 1.0f);
        invokeMiGlass(this.glassData);
    }

    public final void setTypeface(@NotNull Typeface fontTypeface) {
        Intrinsics.checkNotNullParameter(fontTypeface, "fontTypeface");
        this.textPaint.setTypeface(fontTypeface);
    }

    public final void setUnShade(float f) {
        float[] fArr = this.glassData;
        fArr[fArr.length - 6] = f;
        invokeMiGlass(fArr);
    }

    public final void setWeight(float f) {
        if (this.weight == f) {
            return;
        }
        this.weight = f;
        invalidate();
    }

    public final void setWidth(float f) {
        if (this.width == f) {
            return;
        }
        this.width = f;
        invalidate();
    }

    public final void setmTextTop(int i) {
        this.textTop = i;
        invalidate();
    }

    public final void updateFontFeatures(boolean z, boolean z2) {
        AllInOneUtil.INSTANCE.updateFontFeatureSettings(this.textPaint, z, z2);
    }
}
