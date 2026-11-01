package com.miui.aod.widget;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.core.content.res.ResourcesCompat;
import com.miui.aod.R;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.aod.util.FrameworkReflectUtils;
import com.miui.aod.util.SVGUtils;
import com.miui.aod.utils.CommonUtils;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.CharsKt;
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
/* JADX INFO: compiled from: ClockSVGTimeView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class ClockSVGTimeView extends FrameLayout implements IClockSVGView {
    public static final Companion Companion = new Companion(null);
    public final String TAG;
    public int currentColonResId;
    public int currentHourOnesResId;
    public int currentHourTensResId;
    public int currentMinuteOnesResId;
    public int currentMinuteTensResId;
    public boolean currentNeedAnimate;
    public ClockTimeStyle currentStyle;
    public Map drawableAnimateMap;
    public final float heightRatio;
    public final GradientImageView imageColon;
    public final GradientImageView imageHourOnes;
    public final GradientImageView imageHourTens;
    public final GradientImageView imageMinuteOnes;
    public final GradientImageView imageMinuteTens;
    public final SVGLayoutChangeListener layoutChangeListener;
    public PowerManager.WakeLock wakeLock;

    /* JADX INFO: compiled from: ClockSVGTimeView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ClockTimeStyle findStyle(String str) {
            if (str == null) {
                return ClockTimeStyle.CLOCK_TIME_STYLE_1;
            }
            for (ClockTimeStyle clockTimeStyle : ClockTimeStyle.getEntries()) {
                if (Intrinsics.areEqual(clockTimeStyle.getValue(), str)) {
                    return clockTimeStyle;
                }
            }
            return ClockTimeStyle.CLOCK_TIME_STYLE_1;
        }
    }

    /* JADX INFO: compiled from: ClockSVGTimeView.kt */
    @Metadata
    public final class SVGLayoutChangeListener implements View.OnLayoutChangeListener {
        public SVGLayoutChangeListener() {
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View v, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            Intrinsics.checkNotNullParameter(v, "v");
            if (i7 - i5 == i3 - i && i8 - i6 == i4 - i2) {
                return;
            }
            ClockSVGTimeView.this.measureImage();
        }
    }

    /* JADX INFO: compiled from: ClockSVGTimeView.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClockTimeStyle.values().length];
            try {
                iArr[ClockTimeStyle.CLOCK_TIME_STYLE_1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClockTimeStyle.CLOCK_TIME_STYLE_2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClockTimeStyle.CLOCK_TIME_STYLE_3.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ClockTimeStyle.CLOCK_TIME_STYLE_4.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ClockSVGTimeView(@NotNull Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockSVGTimeView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.TAG = "ClockSVGTimeView";
        this.currentHourTensResId = -1;
        this.currentHourOnesResId = -1;
        this.currentMinuteTensResId = -1;
        this.currentMinuteOnesResId = -1;
        this.currentColonResId = -1;
        this.drawableAnimateMap = new LinkedHashMap();
        LayoutInflater.from(context).inflate(R.layout.aod_content_clock_svg, (ViewGroup) this, true);
        this.imageHourTens = (GradientImageView) findViewById(R.id.image_clock_hour_tens);
        this.imageHourOnes = (GradientImageView) findViewById(R.id.image_clock_hour_ones);
        this.imageMinuteTens = (GradientImageView) findViewById(R.id.image_clock_minute_tens);
        this.imageMinuteOnes = (GradientImageView) findViewById(R.id.image_clock_minute_ones);
        this.imageColon = (GradientImageView) findViewById(R.id.image_clock_colon);
        this.heightRatio = (context.getResources().getDimensionPixelSize(R.dimen.big_time_clock_height_small) * 1.0f) / context.getResources().getDimensionPixelSize(R.dimen.big_time_clock_width_small);
        this.layoutChangeListener = new SVGLayoutChangeListener();
        requestDrawWakeLock();
    }

    public static final Unit setTimeAndStyle$lambda$10(ClockSVGTimeView clockSVGTimeView, int i) {
        clockSVGTimeView.currentMinuteOnesResId = i;
        return Unit.INSTANCE;
    }

    public static final Unit setTimeAndStyle$lambda$11(ClockSVGTimeView clockSVGTimeView, int i) {
        clockSVGTimeView.currentColonResId = i;
        return Unit.INSTANCE;
    }

    public static final Unit setTimeAndStyle$lambda$7(ClockSVGTimeView clockSVGTimeView, int i) {
        clockSVGTimeView.currentHourTensResId = i;
        return Unit.INSTANCE;
    }

    public static final Unit setTimeAndStyle$lambda$8(ClockSVGTimeView clockSVGTimeView, int i) {
        clockSVGTimeView.currentHourOnesResId = i;
        return Unit.INSTANCE;
    }

    public static final Unit setTimeAndStyle$lambda$9(ClockSVGTimeView clockSVGTimeView, int i) {
        clockSVGTimeView.currentMinuteTensResId = i;
        return Unit.INSTANCE;
    }

    public final Integer[] calculateDimensions(int i, int i2, float f, float f2, ClockTimeStyle clockTimeStyle) {
        float sVGTotalMargin = (i - getSVGTotalMargin(i, clockTimeStyle)) / ((4 * f) + f2);
        return new Integer[]{Integer.valueOf((int) Math.floor(f * sVGTotalMargin)), Integer.valueOf(i2), Integer.valueOf((int) Math.floor(sVGTotalMargin * f2)), Integer.valueOf(i2)};
    }

    public final float getAspectRatio(Integer num) {
        if ((num == null || num.intValue() != -1) && num != null) {
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), num.intValue(), null);
            if (drawable != null) {
                return (drawable.getIntrinsicWidth() * 1.0f) / drawable.getIntrinsicHeight();
            }
        }
        return 0.0f;
    }

    public final int getColonResId(ClockTimeStyle clockTimeStyle) {
        int i = clockTimeStyle == null ? -1 : WhenMappings.$EnumSwitchMapping$0[clockTimeStyle.ordinal()];
        if (i == 1) {
            return R.drawable.aod_clock_time_style_1_colon;
        }
        if (i != 2) {
            return -1;
        }
        return R.drawable.aod_clock_time_style_2_colon;
    }

    public final int[] getFrameViewPaddingHV(int i, int i2, ClockTimeStyle clockTimeStyle) {
        int i3 = WhenMappings.$EnumSwitchMapping$0[clockTimeStyle.ordinal()];
        if (i3 == 1) {
            return new int[]{(int) Math.floor(((double) i) * 0.11455d), (int) Math.floor(((double) i2) * 0.19091d)};
        }
        if (i3 == 2) {
            return new int[]{(int) Math.floor(((double) i) * 0.01727d), (int) Math.floor(((double) i2) * 0.25227d)};
        }
        if (i3 == 3) {
            return new int[]{(int) Math.floor(((double) i) * 0.11636d), (int) Math.floor(((double) i2) * 0.17273d)};
        }
        if (i3 == 4) {
            return new int[]{(int) Math.floor(((double) i) * 0.08d), (int) Math.floor(((double) i2) * 0.22727d)};
        }
        throw new NoWhenBranchMatchedException();
    }

    public final int[] getNumberResIds(ClockTimeStyle clockTimeStyle) {
        int i = clockTimeStyle == null ? -1 : WhenMappings.$EnumSwitchMapping$0[clockTimeStyle.ordinal()];
        if (i == 1) {
            int[] SVG_CLOCK_TIME_STYLE_1_RESOURCE = SVGUtils.SVG_CLOCK_TIME_STYLE_1_RESOURCE;
            Intrinsics.checkNotNullExpressionValue(SVG_CLOCK_TIME_STYLE_1_RESOURCE, "SVG_CLOCK_TIME_STYLE_1_RESOURCE");
            return SVG_CLOCK_TIME_STYLE_1_RESOURCE;
        }
        if (i == 2) {
            int[] SVG_CLOCK_TIME_STYLE_2_RESOURCE = SVGUtils.SVG_CLOCK_TIME_STYLE_2_RESOURCE;
            Intrinsics.checkNotNullExpressionValue(SVG_CLOCK_TIME_STYLE_2_RESOURCE, "SVG_CLOCK_TIME_STYLE_2_RESOURCE");
            return SVG_CLOCK_TIME_STYLE_2_RESOURCE;
        }
        if (i == 3) {
            int[] SVG_CLOCK_TIME_STYLE_3_RESOURCE = SVGUtils.SVG_CLOCK_TIME_STYLE_3_RESOURCE;
            Intrinsics.checkNotNullExpressionValue(SVG_CLOCK_TIME_STYLE_3_RESOURCE, "SVG_CLOCK_TIME_STYLE_3_RESOURCE");
            return SVG_CLOCK_TIME_STYLE_3_RESOURCE;
        }
        if (i != 4) {
            int[] SVG_CLOCK_TIME_STYLE_1_RESOURCE2 = SVGUtils.SVG_CLOCK_TIME_STYLE_1_RESOURCE;
            Intrinsics.checkNotNullExpressionValue(SVG_CLOCK_TIME_STYLE_1_RESOURCE2, "SVG_CLOCK_TIME_STYLE_1_RESOURCE");
            return SVG_CLOCK_TIME_STYLE_1_RESOURCE2;
        }
        int[] SVG_CLOCK_TIME_STYLE_4_RESOURCE = SVGUtils.SVG_CLOCK_TIME_STYLE_4_RESOURCE;
        Intrinsics.checkNotNullExpressionValue(SVG_CLOCK_TIME_STYLE_4_RESOURCE, "SVG_CLOCK_TIME_STYLE_4_RESOURCE");
        return SVG_CLOCK_TIME_STYLE_4_RESOURCE;
    }

    public final int getSVGMargin(int i, ClockTimeStyle clockTimeStyle) {
        double dFloor;
        int i2 = WhenMappings.$EnumSwitchMapping$0[clockTimeStyle.ordinal()];
        if (i2 == 1) {
            dFloor = Math.floor(((double) i) * 0.00629d);
        } else if (i2 == 2) {
            dFloor = Math.floor(((double) i) * 0.01674d);
        } else if (i2 == 3) {
            dFloor = Math.floor(((double) i) * 0.01053d);
        } else {
            if (i2 != 4) {
                throw new NoWhenBranchMatchedException();
            }
            dFloor = Math.floor(((double) i) * BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        }
        return (int) dFloor;
    }

    public final int getSVGTotalMargin(int i, ClockTimeStyle clockTimeStyle) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[clockTimeStyle.ordinal()];
        if (i2 != 1 && i2 != 2) {
            if (i2 != 3 && i2 != 4) {
                throw new NoWhenBranchMatchedException();
            }
            return getSVGMargin(i, clockTimeStyle) * 3;
        }
        return getSVGMargin(i, clockTimeStyle) * 4;
    }

    public final Integer[] getTimeResIds(boolean z, int i, int i2, ClockTimeStyle clockTimeStyle) {
        String str = String.format("%02d", Arrays.copyOf(new Object[]{Integer.valueOf(CommonUtils.resolveHour(i, z))}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        String str2 = String.format("%02d", Arrays.copyOf(new Object[]{Integer.valueOf(i2)}, 1));
        Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
        int iDigitToInt = CharsKt.digitToInt(str.charAt(0));
        int iDigitToInt2 = CharsKt.digitToInt(str.charAt(1));
        int iDigitToInt3 = CharsKt.digitToInt(str2.charAt(0));
        int iDigitToInt4 = CharsKt.digitToInt(str2.charAt(1));
        int[] numberResIds = getNumberResIds(clockTimeStyle);
        return new Integer[]{Integer.valueOf(numberResIds[iDigitToInt]), Integer.valueOf(numberResIds[iDigitToInt2]), Integer.valueOf(numberResIds[iDigitToInt3]), Integer.valueOf(numberResIds[iDigitToInt4]), Integer.valueOf(getColonResId(clockTimeStyle))};
    }

    public final void handleAnimation(boolean z) {
        GradientImageView[] gradientImageViewArr = {this.imageHourTens, this.imageHourOnes, this.imageMinuteTens, this.imageMinuteOnes, this.imageColon};
        for (int i = 0; i < 5; i++) {
            Drawable drawable = gradientImageViewArr[i].getDrawable();
            if (drawable != null && (drawable instanceof AnimatedVectorDrawable)) {
                if (!z && Intrinsics.areEqual(this.drawableAnimateMap.get(Integer.valueOf(drawable.hashCode())), Boolean.TRUE)) {
                    this.drawableAnimateMap.put(Integer.valueOf(drawable.hashCode()), Boolean.FALSE);
                    ((AnimatedVectorDrawable) drawable).stop();
                } else if (z) {
                    Object obj = this.drawableAnimateMap.get(Integer.valueOf(drawable.hashCode()));
                    Boolean bool = Boolean.TRUE;
                    if (!Intrinsics.areEqual(obj, bool)) {
                        this.drawableAnimateMap.put(Integer.valueOf(drawable.hashCode()), bool);
                        ((AnimatedVectorDrawable) drawable).start();
                    }
                }
            }
        }
    }

    public final String intToHexColor(int[] iArr) {
        StringBuilder sb = new StringBuilder();
        for (int i : iArr) {
            sb.append(Integer.toHexString(i) + ", ");
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public final void measureImage() {
        if (this.currentStyle == null || getMeasuredWidth() <= 0) {
            return;
        }
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        ClockTimeStyle clockTimeStyle = this.currentStyle;
        Intrinsics.checkNotNull(clockTimeStyle);
        int[] frameViewPaddingHV = getFrameViewPaddingHV(measuredWidth, measuredHeight, clockTimeStyle);
        int i = measuredWidth - (frameViewPaddingHV[0] * 2);
        int i2 = measuredHeight - (frameViewPaddingHV[1] * 2);
        ClockTimeStyle clockTimeStyle2 = this.currentStyle;
        Intrinsics.checkNotNull(clockTimeStyle2);
        int sVGMargin = getSVGMargin(i, clockTimeStyle2);
        int[] numberResIds = getNumberResIds(this.currentStyle);
        int colonResId = getColonResId(this.currentStyle);
        float aspectRatio = getAspectRatio(Integer.valueOf(numberResIds[0]));
        float aspectRatio2 = getAspectRatio(Integer.valueOf(colonResId));
        ClockTimeStyle clockTimeStyle3 = this.currentStyle;
        Intrinsics.checkNotNull(clockTimeStyle3);
        Integer[] numArrCalculateDimensions = calculateDimensions(i, i2, aspectRatio, aspectRatio2, clockTimeStyle3);
        int iIntValue = numArrCalculateDimensions[0].intValue();
        int iIntValue2 = numArrCalculateDimensions[1].intValue();
        int iIntValue3 = numArrCalculateDimensions[2].intValue();
        int iIntValue4 = numArrCalculateDimensions[3].intValue();
        updateImageLayoutParams(this.imageHourTens, iIntValue, iIntValue2, sVGMargin);
        updateImageLayoutParams(this.imageHourOnes, iIntValue, iIntValue2, sVGMargin);
        updateImageLayoutParams(this.imageMinuteTens, iIntValue, iIntValue2, sVGMargin);
        updateImageLayoutParams(this.imageMinuteOnes, iIntValue, iIntValue2, 0);
        updateImageLayoutParams(this.imageColon, iIntValue3, iIntValue4, iIntValue3 != 0 ? sVGMargin : 0);
        post(new Runnable() { // from class: com.miui.aod.widget.ClockSVGTimeView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.requestLayout();
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        addOnLayoutChangeListener(this.layoutChangeListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeOnLayoutChangeListener(this.layoutChangeListener);
        release();
        this.drawableAnimateMap.clear();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        if (View.MeasureSpec.getMode(i2) != 1073741824) {
            i2 = View.MeasureSpec.makeMeasureSpec((int) (size * this.heightRatio), 1073741824);
        }
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        if (!z) {
            release();
        } else {
            requestDrawWakeLock();
            startAnimation();
        }
    }

    public void release() {
        releaseDrawWakeLock();
        stopAnimation();
    }

    public final void releaseDrawWakeLock() {
        PowerManager.WakeLock wakeLock = this.wakeLock;
        if (wakeLock != null) {
            if (wakeLock.isHeld()) {
                Log.i(this.TAG, "release");
                wakeLock.release();
            }
            this.wakeLock = null;
        }
    }

    public final void releaseImageViewDrawable(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable == null || !(drawable instanceof AnimatedVectorDrawable)) {
            return;
        }
        this.drawableAnimateMap.remove(Integer.valueOf(drawable.hashCode()));
        ((AnimatedVectorDrawable) drawable).stop();
    }

    public final void requestDrawWakeLock() {
        if (this.wakeLock == null) {
            this.wakeLock = ((PowerManager) getContext().getSystemService(PowerManager.class)).newWakeLock(128, "ClockSVGTimeView:requestDraw");
        }
        PowerManager.WakeLock wakeLock = this.wakeLock;
        if (wakeLock == null || wakeLock.isHeld()) {
            return;
        }
        Log.i(this.TAG, "acquire");
        PowerManager.WakeLock wakeLock2 = this.wakeLock;
        if (wakeLock2 != null) {
            wakeLock2.acquire();
        }
    }

    public void setColor(@NotNull int[] colors) {
        Intrinsics.checkNotNullParameter(colors, "colors");
        Log.i(this.TAG, "current svg time color, [HourTens, HourOnes, MinuteTens, MinuteOnes, Colon] -> [" + intToHexColor(colors) + "]");
        for (IndexedValue indexedValue : ArraysKt.withIndex(new GradientImageView[]{this.imageHourTens, this.imageHourOnes, this.imageMinuteTens, this.imageMinuteOnes, this.imageColon})) {
            int iComponent1 = indexedValue.component1();
            GradientImageView gradientImageView = (GradientImageView) indexedValue.component2();
            gradientImageView.setGradientOverlayDrawable(new ColorDrawable(colors[iComponent1]));
            gradientImageView.invalidate();
        }
    }

    public void setTimeAndStyle(boolean z, int i, int i2, @NotNull ClockTimeStyle style, boolean z2) {
        Intrinsics.checkNotNullParameter(style, "style");
        this.currentStyle = style;
        this.currentNeedAnimate = z2;
        measureImage();
        Integer[] timeResIds = getTimeResIds(z, i, i2, style);
        int iIntValue = timeResIds[0].intValue();
        int iIntValue2 = timeResIds[1].intValue();
        int iIntValue3 = timeResIds[2].intValue();
        int iIntValue4 = timeResIds[3].intValue();
        int iIntValue5 = timeResIds[4].intValue();
        updateImageRes(this.imageHourTens, iIntValue, this.currentHourTensResId, new Function1() { // from class: com.miui.aod.widget.ClockSVGTimeView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ClockSVGTimeView.setTimeAndStyle$lambda$7(this.f$0, ((Integer) obj).intValue());
            }
        });
        updateImageRes(this.imageHourOnes, iIntValue2, this.currentHourOnesResId, new Function1() { // from class: com.miui.aod.widget.ClockSVGTimeView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ClockSVGTimeView.setTimeAndStyle$lambda$8(this.f$0, ((Integer) obj).intValue());
            }
        });
        updateImageRes(this.imageMinuteTens, iIntValue3, this.currentMinuteTensResId, new Function1() { // from class: com.miui.aod.widget.ClockSVGTimeView$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ClockSVGTimeView.setTimeAndStyle$lambda$9(this.f$0, ((Integer) obj).intValue());
            }
        });
        updateImageRes(this.imageMinuteOnes, iIntValue4, this.currentMinuteOnesResId, new Function1() { // from class: com.miui.aod.widget.ClockSVGTimeView$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ClockSVGTimeView.setTimeAndStyle$lambda$10(this.f$0, ((Integer) obj).intValue());
            }
        });
        if (iIntValue5 != -1) {
            this.imageColon.setVisibility(0);
            updateImageRes(this.imageColon, iIntValue5, this.currentColonResId, new Function1() { // from class: com.miui.aod.widget.ClockSVGTimeView$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ClockSVGTimeView.setTimeAndStyle$lambda$11(this.f$0, ((Integer) obj).intValue());
                }
            });
        } else {
            this.imageColon.setVisibility(8);
            this.currentColonResId = -1;
        }
        if (z2) {
            startAnimation();
        } else {
            stopAnimation();
        }
    }

    public final void startAnimation() {
        if (this.currentNeedAnimate) {
            handleAnimation(true);
        }
    }

    public final void stopAnimation() {
        handleAnimation(false);
    }

    public final void updateImageLayoutParams(ImageView imageView, int i, int i2, int i3) {
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
        layoutParams2.width = i;
        layoutParams2.height = i2;
        layoutParams2.setMarginEnd(i3);
        imageView.setLayoutParams(layoutParams2);
    }

    public final void updateImageRes(ImageView imageView, int i, int i2, Function1 function1) {
        if (i != i2) {
            releaseImageViewDrawable(imageView);
            imageView.setImageResource(i);
            if (imageView.getDrawable() instanceof AnimatedVectorDrawable) {
                Drawable drawable = imageView.getDrawable();
                Intrinsics.checkNotNull(drawable, "null cannot be cast to non-null type android.graphics.drawable.AnimatedVectorDrawable");
                FrameworkReflectUtils.setIgnoreAnimationScale((AnimatedVectorDrawable) drawable, true);
            }
            function1.invoke(Integer.valueOf(i));
        }
    }
}
