package com.miui.clock.allInOne;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.gson.Gson;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.classic.ClassicTextAreaView;
import com.miui.clock.module.BaseFontStyle;
import com.miui.clock.module.ClassicContentStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.FontNeumaticCompressedB;
import com.miui.clock.module.HealthBean;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorParams;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontSetData;
import com.miui.clock.utils.FontSetParser;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.HealthFetcherController;
import com.miui.clock.utils.MiuiBlurUtils;
import com.miui.clock.utils.VariableFontQueryUtils;
import com.miui.clock.utils.WeatherFetcherController;
import com.miui.clock.utils.avoid.ClockDepthAvoidRuleUtils;
import com.miui.clock.utils.avoid.DepthAvoidEvaluatorDebug;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import miuix.androidbasewidget.widget.ClearableEditText;
import miuix.pickerwidget.date.DateUtils;
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
/* JADX INFO: compiled from: AllInOneBase.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public abstract class AllInOneBase extends MiuiGalleryBaseClock {
    public static final Companion Companion = new Companion(null);
    public static final RectQueryLruCache rectQueryCache = new RectQueryLruCache(64);
    public final String COLON_TEXT;
    public String TAG;
    public BaseFontStyle fontStyle;
    public HashSet healthType;
    public AccessibilityManager mAccessibilityManager;
    public float mAllInOneWeight;
    public String mAllTimeText;
    public String mAllTimeTextNoColon;
    public View mAnimationContainer;
    public View mBlurBlendContainer;
    public AllInOneBaseInfo mClockStyleInfo;
    public Rect mClockViewRect;
    public int mColonIdx;
    public boolean mColonShow;
    public TimeView mColonView;
    public int[] mCurrentTimeArray;
    public boolean mDoubleRow;
    public boolean mEditMode;
    public FontSetData mFontSetData;
    public final ColorParams mGlassColorParams;
    public Gson mGson;
    public boolean mHasPresetData;
    public HealthFetcherController mHealthFetcherController;
    public TimeView mHourView;
    public boolean mInNotificationState;
    public TimeView mMinuteView;
    public KeyListener mOriginalKeyListener;
    public final ColorParams mSecondaryGlassColorParams;
    public final int[] mSegmentLengths;
    public final float[] mSegmentWidths;
    public boolean mShouldPersistParams;
    public ClearableEditText mSignatureText;
    public FrameLayout mSignatureTextContainer;
    public TextWatcher mSignatureTextWatcher;
    public final TextPaint mStaticLayoutPaint;
    public ClassicTextAreaView mTextArea;
    public final Paint mTextMeasurePaint;
    public View mTimeAccessibilityOverlay;
    public View mTimeGroup;
    public VariableFontQueryUtils mVariableFontQueryUtils;
    public WeatherFetcherController mWeatherFetcherController;
    public TimeView shareTimeView;

    /* JADX INFO: compiled from: AllInOneBase.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: AllInOneBase.kt */
    @Metadata
    public static final class RectQueryKey {
        public final int bottom;
        public final boolean layoutWithColon;
        public final int left;
        public final int right;
        public final boolean singleRow;
        public final int top;

        public RectQueryKey(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            this.left = i;
            this.top = i2;
            this.right = i3;
            this.bottom = i4;
            this.layoutWithColon = z;
            this.singleRow = z2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RectQueryKey)) {
                return false;
            }
            RectQueryKey rectQueryKey = (RectQueryKey) obj;
            return this.left == rectQueryKey.left && this.top == rectQueryKey.top && this.right == rectQueryKey.right && this.bottom == rectQueryKey.bottom && this.layoutWithColon == rectQueryKey.layoutWithColon && this.singleRow == rectQueryKey.singleRow;
        }

        public int hashCode() {
            return (((((((((Integer.hashCode(this.left) * 31) + Integer.hashCode(this.top)) * 31) + Integer.hashCode(this.right)) * 31) + Integer.hashCode(this.bottom)) * 31) + Boolean.hashCode(this.layoutWithColon)) * 31) + Boolean.hashCode(this.singleRow);
        }

        public String toString() {
            return "RectQueryKey(left=" + this.left + ", top=" + this.top + ", right=" + this.right + ", bottom=" + this.bottom + ", layoutWithColon=" + this.layoutWithColon + ", singleRow=" + this.singleRow + ')';
        }
    }

    /* JADX INFO: compiled from: AllInOneBase.kt */
    @Metadata
    public static final class RectQueryLruCache {
        public final AllInOneBase$RectQueryLruCache$map$1 map = new AllInOneBase$RectQueryLruCache$map$1(this);
        public final int maxSize;

        public RectQueryLruCache(int i) {
            this.maxSize = i;
        }

        public final synchronized float[] get(RectQueryKey key) {
            Intrinsics.checkNotNullParameter(key, "key");
            return (float[]) this.map.get((Object) key);
        }

        public final synchronized void put(RectQueryKey key, float[] value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            this.map.put(key, value);
        }
    }

    /* JADX INFO: compiled from: AllInOneBase.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            try {
                iArr[ClockViewType.FULL_HOUR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClockViewType.FULL_MINUTE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClockViewType.FULL_COLON.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ClockViewType.TEXT_AREA.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ClockViewType.SIGNATURE_AREA.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ClockViewType.SIGNATURE_EDIT_AREA.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[ClockViewType.ANIMATION_CONTAINER.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[ClockViewType.TIME_GROUP.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public AllInOneBase(@Nullable Context context) {
        super(context);
        this.TAG = "AllInOneBase";
        this.mCurrentTimeArray = new int[4];
        this.fontStyle = new FontNeumaticCompressedB();
        this.mClockViewRect = new Rect();
        this.mColonShow = true;
        this.mAllInOneWeight = -1.0f;
        this.mGlassColorParams = new ColorParams();
        this.mSecondaryGlassColorParams = new ColorParams();
        this.healthType = new HashSet();
        this.mStaticLayoutPaint = new TextPaint();
        this.mTextMeasurePaint = new Paint(64);
        this.COLON_TEXT = ":";
        this.mAllTimeText = "";
        this.mAllTimeTextNoColon = "";
        this.mColonIdx = -1;
        this.mSegmentLengths = new int[3];
        this.mSegmentWidths = new float[3];
    }

    public AllInOneBase(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "AllInOneBase";
        this.mCurrentTimeArray = new int[4];
        this.fontStyle = new FontNeumaticCompressedB();
        this.mClockViewRect = new Rect();
        this.mColonShow = true;
        this.mAllInOneWeight = -1.0f;
        this.mGlassColorParams = new ColorParams();
        this.mSecondaryGlassColorParams = new ColorParams();
        this.healthType = new HashSet();
        this.mStaticLayoutPaint = new TextPaint();
        this.mTextMeasurePaint = new Paint(64);
        this.COLON_TEXT = ":";
        this.mAllTimeText = "";
        this.mAllTimeTextNoColon = "";
        this.mColonIdx = -1;
        this.mSegmentLengths = new int[3];
        this.mSegmentWidths = new float[3];
    }

    private final void checkGson() {
        if (this.mGson == null) {
            this.mGson = new Gson();
        }
    }

    private final float getAnimationContainerTranslationY() {
        View view = this.mAnimationContainer;
        return (view != null ? view.getTranslationY() : 0.0f) + getTranslationY();
    }

    private final View[] getEffectChildViews() {
        return new View[]{this.mAnimationContainer, this.mHourView, this.mMinuteView, this.mColonView, this.mTextArea, this.mSignatureText};
    }

    private final void setSignatureEditable(boolean z) {
        ClearableEditText clearableEditText = this.mSignatureText;
        if (clearableEditText == null) {
            return;
        }
        if (z) {
            clearableEditText.setKeyListener(this.mOriginalKeyListener);
            clearableEditText.setFocusable(true);
            clearableEditText.setFocusableInTouchMode(true);
            clearableEditText.setCursorVisible(true);
            clearableEditText.setLongClickable(true);
            clearableEditText.setHint(getContext().getString(R.string.miui_clock_classic_signature_area_hint));
            return;
        }
        clearableEditText.setKeyListener(null);
        clearableEditText.setFocusable(false);
        clearableEditText.setFocusableInTouchMode(false);
        clearableEditText.setCursorVisible(false);
        clearableEditText.setLongClickable(false);
        clearableEditText.setHint((CharSequence) null);
    }

    public static /* synthetic */ void toNotificationState$default(AllInOneBase allInOneBase, boolean z, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toNotificationState");
        }
        if ((i2 & 1) != 0) {
            z = false;
        }
        if ((i2 & 2) != 0) {
            i = -1;
        }
        allInOneBase.toNotificationState(z, i);
    }

    public static final void updateBlurBlendContainerBounds$applyHeight(int i, View view) {
        ViewGroup.LayoutParams layoutParams = view != null ? view.getLayoutParams() : null;
        if (layoutParams == null) {
            return;
        }
        if (!(layoutParams instanceof ConstraintLayout.LayoutParams)) {
            layoutParams.height = i;
            view.setLayoutParams(layoutParams);
            return;
        }
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
        layoutParams2.topToTop = 0;
        layoutParams2.bottomToBottom = -1;
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = 0;
        layoutParams.height = i;
        view.setLayoutParams(layoutParams);
    }

    public static final void updateBlurBlendContainerBounds$applyMatchParent(View view) {
        ViewGroup.LayoutParams layoutParams = view != null ? view.getLayoutParams() : null;
        if (layoutParams == null) {
            return;
        }
        if (!(layoutParams instanceof ConstraintLayout.LayoutParams)) {
            layoutParams.height = -1;
            view.setLayoutParams(layoutParams);
            return;
        }
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) layoutParams;
        layoutParams2.topToTop = 0;
        layoutParams2.bottomToBottom = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = 0;
        layoutParams.height = -1;
        view.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateWeather(WeatherBean weatherBean) {
        ClassicTextAreaView classicTextAreaView = this.mTextArea;
        if (classicTextAreaView != null) {
            Intrinsics.checkNotNull(classicTextAreaView);
            classicTextAreaView.updateWeather(weatherBean);
        }
    }

    public final void addSignatureTextWatcher() {
        final ClearableEditText clearableEditText = this.mSignatureText;
        if (clearableEditText == null) {
            return;
        }
        TextWatcher textWatcher = this.mSignatureTextWatcher;
        if (textWatcher != null) {
            clearableEditText.removeTextChangedListener(textWatcher);
        }
        final int i = 2;
        TextWatcher textWatcher2 = new TextWatcher() { // from class: com.miui.clock.allInOne.AllInOneBase.addSignatureTextWatcher.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                Editable text;
                String string;
                String string2;
                if (editable == null || AllInOneBase.this.getMClockStyleInfo() == null || (text = clearableEditText.getText()) == null || (string = text.toString()) == null) {
                    return;
                }
                TextPaint paint = clearableEditText.getPaint();
                int dimen = (AllInOneBase.this.getDimen(R.dimen.classic_text_signature_max_width) - clearableEditText.getCompoundPaddingLeft()) - clearableEditText.getCompoundPaddingRight();
                if (dimen <= 0) {
                    return;
                }
                StaticLayout staticLayoutBuild = StaticLayout.Builder.obtain(string, 0, string.length(), paint, dimen).build();
                Intrinsics.checkNotNullExpressionValue(staticLayoutBuild, "build(...)");
                int lineCount = staticLayoutBuild.getLineCount();
                int i2 = i;
                if (lineCount > i2) {
                    int lineEnd = staticLayoutBuild.getLineEnd(i2 - 1);
                    if (lineEnd < string.length() && Character.isLowSurrogate(string.charAt(lineEnd))) {
                        lineEnd = (lineEnd <= 0 || !Character.isHighSurrogate(string.charAt(lineEnd + (-1)))) ? lineEnd - 1 : lineEnd - 2;
                    }
                    String strSubstring = string.substring(0, lineEnd);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    if (StringsKt.endsWith$default(strSubstring, "\n", false, 2, (Object) null)) {
                        strSubstring = strSubstring.substring(0, StringsKt.lastIndexOf$default((CharSequence) strSubstring, "\n", 0, false, 6, (Object) null));
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    }
                    if (!Intrinsics.areEqual(strSubstring, string)) {
                        editable.replace(0, editable.length(), strSubstring);
                        clearableEditText.setSelection(strSubstring.length());
                    }
                }
                Editable text2 = clearableEditText.getText();
                if (text2 == null || (string2 = text2.toString()) == null) {
                    string2 = "";
                }
                AllInOneBaseInfo mClockStyleInfo = AllInOneBase.this.getMClockStyleInfo();
                if (mClockStyleInfo != null) {
                    mClockStyleInfo.setSignature(string2);
                }
                AllInOneBaseInfo mClockStyleInfo2 = AllInOneBase.this.getMClockStyleInfo();
                int signatureLineCount = mClockStyleInfo2 != null ? mClockStyleInfo2.getSignatureLineCount() : 0;
                int signatureLines = AllInOneUtil.INSTANCE.getSignatureLines(clearableEditText, string2);
                if (signatureLineCount != signatureLines) {
                    AllInOneBaseInfo mClockStyleInfo3 = AllInOneBase.this.getMClockStyleInfo();
                    if (mClockStyleInfo3 != null) {
                        mClockStyleInfo3.setSignatureLineCount(signatureLines);
                    }
                    AllInOneBase.this.applyAllInOneRect(null);
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }
        };
        this.mSignatureTextWatcher = textWatcher2;
        clearableEditText.addTextChangedListener(textWatcher2);
    }

    public final void addType(int i) {
        if (ClassicContentStyle.Health.isHealthType(i)) {
            if (i != 507) {
                this.healthType.add(Integer.valueOf(i));
                return;
            }
            this.healthType.add(502);
            this.healthType.add(500);
            this.healthType.add(508);
        }
    }

    public final void applyAllInOneRect(AllInOneUtil.RectComputeResult rectComputeResult) {
        float clockTopRatio;
        Number numberValueOf;
        Number numberValueOf2;
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        if (allInOneBaseInfo == null) {
            return;
        }
        if (this.mInNotificationState && rectComputeResult == null) {
            toNotificationState$default(this, true, 0, 2, null);
            return;
        }
        if (rectComputeResult != null) {
            Intrinsics.checkNotNull(allInOneBaseInfo);
            allInOneBaseInfo.setRectParams(getContext(), rectComputeResult.getRectParams());
            Rect rect = rectComputeResult.getRect();
            if (rect != null) {
                setClockViewRect(rect);
                return;
            }
            return;
        }
        Intrinsics.checkNotNull(allInOneBaseInfo);
        AllInOneBaseInfo.AllInOneRectParams rectParamsOrNull = allInOneBaseInfo.getRectParamsOrNull();
        if (rectParamsOrNull != null) {
            clockTopRatio = rectParamsOrNull.clockTopRatio;
        } else {
            AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
            Intrinsics.checkNotNull(allInOneBaseInfo2);
            clockTopRatio = allInOneBaseInfo2.getClockTopRatio();
        }
        if (rectParamsOrNull != null) {
            numberValueOf = Integer.valueOf(rectParamsOrNull.timeWidth);
        } else {
            AllInOneBaseInfo allInOneBaseInfo3 = this.mClockStyleInfo;
            Intrinsics.checkNotNull(allInOneBaseInfo3);
            numberValueOf = Float.valueOf(allInOneBaseInfo3.getTimeWidth());
        }
        if (rectParamsOrNull != null) {
            numberValueOf2 = Integer.valueOf(rectParamsOrNull.timeHeight);
        } else {
            AllInOneBaseInfo allInOneBaseInfo4 = this.mClockStyleInfo;
            Intrinsics.checkNotNull(allInOneBaseInfo4);
            numberValueOf2 = Float.valueOf(allInOneBaseInfo4.getTimeHeight());
        }
        int[] screenWH = DeviceConfig.getScreenWH(getContext());
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        Rect rectCreateEditRect$default = ClockDepthAvoidRuleUtils.createEditRect$default(context, screenWH[0], screenWH[1], ClockDepthAvoidRuleUtils.DEFAULT_CONFIG, null, null, 48, null);
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        Rect rectComputeTimeRectFromEditRectClipped = ClockDepthAvoidRuleUtils.computeTimeRectFromEditRectClipped(context2, rectCreateEditRect$default, clockTopRatio, numberValueOf.floatValue(), numberValueOf2.floatValue());
        if (rectComputeTimeRectFromEditRectClipped != null) {
            setClockViewRect(rectComputeTimeRectFromEditRectClipped);
        }
    }

    public final void applyMergedRectParams(AllInOneUtil.RectComputeResult rectComputeResult) {
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        Intrinsics.checkNotNull(allInOneBaseInfo);
        AllInOneBaseInfo.AllInOneRectParams rectParamsOrNull = allInOneBaseInfo.getRectParamsOrNull();
        Intrinsics.checkNotNull(rectParamsOrNull);
        AllInOneBaseInfo.AllInOneRectParams rectParams = rectComputeResult.getRectParams();
        if (rectParams == null) {
            applyAllInOneRect(rectComputeResult);
            return;
        }
        AllInOneBaseInfo.AllInOneRectParams allInOneRectParams = new AllInOneBaseInfo.AllInOneRectParams(rectParams.clockDepthType, rectParams.clockTopRatio, rectParams.clockBottomRatio, rectParamsOrNull.timeWidth, rectParamsOrNull.timeHeight);
        AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
        Intrinsics.checkNotNull(allInOneBaseInfo2);
        allInOneBaseInfo2.setRectParams(getContext(), allInOneRectParams);
        applyAllInOneRect(null);
    }

    public final void applyNotificationMinimalState() {
        Rect rectComputeNotificationMinimalRect = computeNotificationMinimalRect();
        if (rectComputeNotificationMinimalRect == null) {
            return;
        }
        setClockViewRect(rectComputeNotificationMinimalRect);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ Rect calculateColorPickupRect(Boolean bool, Rect rect) {
        return calculateColorPickupRect(bool.booleanValue(), rect);
    }

    /* JADX WARN: Code duplicated, block: B:9:0x0026  */
    public Rect calculateColorPickupRect(boolean z, Rect galleryRect) {
        Rect rect;
        Editable text;
        CharSequence text2;
        Intrinsics.checkNotNullParameter(galleryRect, "galleryRect");
        if (z) {
            AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
            if (allInOneBaseInfo != null) {
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                AllInOneUtil.RectComputeResult rectComputeResultComputeLayoutResult = allInOneBaseInfo.computeLayoutResult(context);
                if (rectComputeResultComputeLayoutResult != null) {
                    rect = rectComputeResultComputeLayoutResult.getRect();
                } else {
                    rect = null;
                }
            } else {
                rect = null;
            }
        } else {
            rect = this.mClockViewRect;
        }
        if (rect == null || rect.isEmpty()) {
            Log.d(this.TAG, "calculateColorPickupRect: clockViewRect is empty, return empty");
            return new Rect();
        }
        int i = this.mScreenWidth;
        int i2 = this.mScreenHeight;
        if (i <= 0 || i2 <= 0) {
            Log.d(this.TAG, "calculateColorPickupRect: invalid mScreenWidth/mScreenHeight w=" + i + " h=" + i2 + ", fallback to DisplayMetrics");
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int i3 = displayMetrics.widthPixels;
            i2 = displayMetrics.heightPixels;
            i = i3;
        }
        boolean z2 = false;
        int animationContainerTranslationY = z ? 0 : (int) getAnimationContainerTranslationY();
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        ClassicTextAreaView classicTextAreaView = this.mTextArea;
        TextPaint paint = classicTextAreaView != null ? classicTextAreaView.getPaint() : null;
        AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
        int classicLine1 = allInOneBaseInfo2 != null ? allInOneBaseInfo2.getClassicLine1() : 0;
        ClassicTextAreaView classicTextAreaView2 = this.mTextArea;
        String string = (classicTextAreaView2 == null || (text2 = classicTextAreaView2.getText()) == null) ? null : text2.toString();
        AllInOneBaseInfo allInOneBaseInfo3 = this.mClockStyleInfo;
        if (allInOneBaseInfo3 != null && allInOneBaseInfo3.hasSignature()) {
            z2 = true;
        }
        ClearableEditText clearableEditText = this.mSignatureText;
        TextPaint paint2 = clearableEditText != null ? clearableEditText.getPaint() : null;
        ClearableEditText clearableEditText2 = this.mSignatureText;
        String string2 = (clearableEditText2 == null || (text = clearableEditText2.getText()) == null) ? null : text.toString();
        AllInOneBaseInfo allInOneBaseInfo4 = this.mClockStyleInfo;
        int i4 = i2;
        Rect rect2 = rect;
        int i5 = i;
        Rect rectBuildColorPickupSourceRect = AllInOneUtil.buildColorPickupSourceRect(context2, rect2, i5, i4, paint, classicLine1, string, z2, paint2, string2, allInOneBaseInfo4 != null ? allInOneBaseInfo4.getSignature() : null, galleryRect, animationContainerTranslationY);
        Log.d(this.TAG, "calculateColorPickupRect: isKeyguardState=" + z + ", resultRect=" + rectBuildColorPickupSourceRect + ", clockViewRect=" + rect2 + ", galleryRect=" + galleryRect + ", translationY=" + animationContainerTranslationY);
        return AllInOneUtil.calculateColorPickupFinalRect(rectBuildColorPickupSourceRect, i5, i4);
    }

    public final void checkAndCorrectSignatureText(TextView textView, String str, int i) {
        Log.d(this.TAG, "checkAndCorrectSignatureText signatureContent=" + str);
        if (textView == null || TextUtils.isEmpty(str) || i <= 0) {
            return;
        }
        int dimen = getDimen(R.dimen.classic_text_signature_max_width);
        textView.setMaxWidth(dimen);
        TextPaint paint = textView.getPaint();
        int compoundPaddingLeft = (dimen - textView.getCompoundPaddingLeft()) - textView.getCompoundPaddingRight();
        Intrinsics.checkNotNull(str);
        StaticLayout staticLayoutBuild = StaticLayout.Builder.obtain(str, 0, str.length(), paint, compoundPaddingLeft).setMaxLines(Integer.MAX_VALUE).build();
        Intrinsics.checkNotNullExpressionValue(staticLayoutBuild, "build(...)");
        if (staticLayoutBuild.getLineCount() <= i) {
            textView.setText(str);
            textView.setMaxLines(i);
            return;
        }
        StaticLayout staticLayoutBuild2 = StaticLayout.Builder.obtain(str, 0, str.length(), paint, compoundPaddingLeft).setMaxLines(i).setEllipsize(null).build();
        Intrinsics.checkNotNullExpressionValue(staticLayoutBuild2, "build(...)");
        int lineEnd = staticLayoutBuild2.getLineEnd(i - 1);
        if (lineEnd < str.length() && Character.isLowSurrogate(str.charAt(lineEnd))) {
            lineEnd = (lineEnd <= 0 || !Character.isHighSurrogate(str.charAt(lineEnd + (-1)))) ? lineEnd - 1 : lineEnd - 2;
        }
        String strSubstring = str.substring(0, lineEnd);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        textView.setText(strSubstring);
        textView.setMaxLines(i);
        Log.d(this.TAG, "checkAndCorrectSignatureText newText=" + strSubstring);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        if (allInOneBaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(resolveBlurBlendContainer(), allInOneBaseInfo, z);
        for (View view : getEffectChildViews()) {
            ClockEffectUtils.clearClockEffectsView(view, allInOneBaseInfo, z);
        }
        AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
        Intrinsics.checkNotNull(allInOneBaseInfo2);
        int clockEffect = allInOneBaseInfo2.getClockEffect();
        if (z || !ClockEffectUtils.isGradualType(clockEffect)) {
            resetGradientParams();
        }
    }

    public final Rect computeNotificationMinimalRect() {
        int[] screenWH = DeviceConfig.getScreenWH(getContext());
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        Rect rectCreateEditRect$default = ClockDepthAvoidRuleUtils.createEditRect$default(context, screenWH[0], screenWH[1], ClockDepthAvoidRuleUtils.DEFAULT_CONFIG, null, null, 48, null);
        AllInOneUtil allInOneUtil = AllInOneUtil.INSTANCE;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        int dateTopToTime = allInOneUtil.getDateTopToTime(context2);
        boolean z = this.mDoubleRow;
        int i = !z ? R.dimen.miui_time_all_in_one_single_line_min_width : R.dimen.miui_time_all_in_one_double_line_min_width;
        int i2 = !z ? R.dimen.miui_time_all_in_one_single_line_min_height : R.dimen.miui_time_all_in_one_double_line_min_height;
        int dimen = getDimen(i);
        int dimen2 = getDimen(i2);
        int i3 = rectCreateEditRect$default.top + dateTopToTime;
        float f = (rectCreateEditRect$default.left + rectCreateEditRect$default.right) / 2.0f;
        float f2 = dimen / 2.0f;
        return new Rect((int) (f - f2), i3, (int) (f + f2), dimen2 + i3);
    }

    @Nullable
    public final ClearableEditText getActiveSignatureView() {
        return this.mSignatureText;
    }

    @Nullable
    public final View getAnimationContainer() {
        return this.mAnimationContainer;
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Nullable
    public View getBackgroundBlurContainer() {
        return resolveBlurBlendContainer();
    }

    @NotNull
    public final String getCOLON_TEXT() {
        return this.COLON_TEXT;
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Nullable
    public ClockStyleInfo getClockStyleInfo() {
        return this.mClockStyleInfo;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @NotNull
    public Rect getDepthAvoidRect() {
        String str;
        int i;
        int i2;
        float f;
        float f2;
        int i3;
        int i4;
        AllInOneClockStyle.Region clockDepthType;
        int i5;
        if (this.mClockStyleInfo == null) {
            if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
                Log.d("AllInOneBase", "getDepthAvoidRect: mClockStyleInfo is null, return empty");
            }
            return new Rect();
        }
        int i6 = this.mScreenWidth;
        int i7 = this.mScreenHeight;
        if (i6 <= 0 || i7 <= 0) {
            if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
                Log.d("AllInOneBase", "getDepthAvoidRect: invalid mScreenWidth/mScreenHeight w=" + i6 + " h=" + i7 + ", fallback to DisplayMetrics");
            }
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int i8 = displayMetrics.widthPixels;
            str = "displayMetrics";
            i = displayMetrics.heightPixels;
            i2 = i8;
        } else {
            str = "member";
            i2 = i6;
            i = i7;
        }
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        ClockDepthAvoidRuleUtils.Config config = ClockDepthAvoidRuleUtils.DEFAULT_CONFIG;
        Rect rectCreateEditRect$default = ClockDepthAvoidRuleUtils.createEditRect$default(context, i2, i, config, null, null, 48, null);
        boolean z = DepthAvoidEvaluatorDebug.DEBUG_LOG;
        if (z) {
            Log.d("AllInOneBase", "getDepthAvoidRect: screen=(" + i2 + ',' + i + ") from=" + str + " editRect=" + rectCreateEditRect$default + " translationY=" + getAnimationContainerTranslationY());
        }
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        Intrinsics.checkNotNull(allInOneBaseInfo);
        AllInOneBaseInfo.AllInOneRectParams rectParamsOrNull = allInOneBaseInfo.getRectParamsOrNull();
        AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
        Intrinsics.checkNotNull(allInOneBaseInfo2);
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        AllInOneBaseInfo.AllInOneRectParams allInOneRectParamsComputeInitParamsRect = allInOneBaseInfo2.computeInitParamsRect(context2);
        if (rectParamsOrNull != null) {
            f = rectParamsOrNull.clockTopRatio;
        } else {
            Intrinsics.checkNotNull(allInOneRectParamsComputeInitParamsRect);
            f = allInOneRectParamsComputeInitParamsRect.clockTopRatio;
        }
        if (rectParamsOrNull != null) {
            f2 = rectParamsOrNull.clockBottomRatio;
        } else {
            Intrinsics.checkNotNull(allInOneRectParamsComputeInitParamsRect);
            f2 = allInOneRectParamsComputeInitParamsRect.clockBottomRatio;
        }
        if (rectParamsOrNull != null) {
            i3 = rectParamsOrNull.timeWidth;
        } else {
            Intrinsics.checkNotNull(allInOneRectParamsComputeInitParamsRect);
            i3 = allInOneRectParamsComputeInitParamsRect.timeWidth;
        }
        if (rectParamsOrNull != null) {
            i4 = rectParamsOrNull.timeHeight;
        } else {
            Intrinsics.checkNotNull(allInOneRectParamsComputeInitParamsRect);
            i4 = allInOneRectParamsComputeInitParamsRect.timeHeight;
        }
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        Rect rectComputeTimeRectFromEditRectClipped = ClockDepthAvoidRuleUtils.computeTimeRectFromEditRectClipped(context3, rectCreateEditRect$default, f, i3, i4);
        if (rectComputeTimeRectFromEditRectClipped == null || rectComputeTimeRectFromEditRectClipped.isEmpty()) {
            if (z) {
                Log.d("AllInOneBase", "getDepthAvoidRect: correctedClockRect is empty, return empty. editRect=" + rectCreateEditRect$default + " topRatio=" + f + " bottomRatio=" + f2 + " timeW=" + i3 + " timeH=" + i4);
            }
            return new Rect();
        }
        int i9 = i2;
        if (z) {
            Log.d("AllInOneBase", "getDepthAvoidRect: correctedClockRect=" + rectComputeTimeRectFromEditRectClipped + " topRatio=" + f + " bottomRatio=" + f2 + " timeW=" + i3 + " timeH=" + i4);
        }
        if (rectParamsOrNull != null) {
            clockDepthType = rectParamsOrNull.clockDepthType;
        } else {
            AllInOneBaseInfo allInOneBaseInfo3 = this.mClockStyleInfo;
            Intrinsics.checkNotNull(allInOneBaseInfo3);
            clockDepthType = allInOneBaseInfo3.getClockDepthType();
        }
        if (clockDepthType == null) {
            Context context4 = getContext();
            Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
            clockDepthType = ClockDepthAvoidRuleUtils.decideRegion(context4, i9, i5, rectComputeTimeRectFromEditRectClipped, config);
            if (z) {
                i5 = i;
                Log.d("AllInOneBase", "getDepthAvoidRect: region decided=" + clockDepthType);
            }
        } else if (z) {
            Log.d("AllInOneBase", "getDepthAvoidRect: region from params/style=" + clockDepthType);
        }
        i5 = i;
        Rect rectComputeAvoidRect = ClockDepthAvoidRuleUtils.computeAvoidRect(rectComputeTimeRectFromEditRectClipped, clockDepthType, config);
        if (rectComputeAvoidRect == null) {
            if (z) {
                Log.d("AllInOneBase", "getDepthAvoidRect: avoidRect is null, return empty. correctedClockRect=" + rectComputeTimeRectFromEditRectClipped + " region=" + clockDepthType);
            }
            return new Rect();
        }
        Rect rect = new Rect(rectComputeAvoidRect);
        int animationContainerTranslationY = (int) getAnimationContainerTranslationY();
        if (animationContainerTranslationY != 0) {
            rectComputeAvoidRect.offset(0, animationContainerTranslationY);
        }
        if (z) {
            Log.d("AllInOneBase", "getDepthAvoidRect: avoidRect(beforeOffset)=" + rect + " ty=" + animationContainerTranslationY + " -> avoidRect=" + rectComputeAvoidRect);
        }
        return rectComputeAvoidRect;
    }

    @NotNull
    public final BaseFontStyle getFontStyle() {
        return this.fontStyle;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getGalleryGravity() {
        return 1;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType viewType) {
        Intrinsics.checkNotNullParameter(viewType, "viewType");
        switch (WhenMappings.$EnumSwitchMapping$0[viewType.ordinal()]) {
            case 1:
                return this.mHourView;
            case 2:
                return this.mMinuteView;
            case 3:
                return this.mColonView;
            case 4:
                return this.mTextArea;
            case 5:
                return this.mSignatureText;
            case 6:
                return this.mSignatureText;
            case 7:
                return this.mAnimationContainer;
            case 8:
                return this.mTimeGroup;
            default:
                return super.getIClockView(viewType);
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    public final float getMAllInOneWeight() {
        return this.mAllInOneWeight;
    }

    @Nullable
    public final View getMAnimationContainer() {
        return this.mAnimationContainer;
    }

    @Nullable
    public final View getMBlurBlendContainer() {
        return this.mBlurBlendContainer;
    }

    @Nullable
    public final AllInOneBaseInfo getMClockStyleInfo() {
        return this.mClockStyleInfo;
    }

    @NotNull
    public final Rect getMClockViewRect() {
        return this.mClockViewRect;
    }

    public final boolean getMColonShow() {
        return this.mColonShow;
    }

    @Nullable
    public final TimeView getMColonView() {
        return this.mColonView;
    }

    @NotNull
    public final int[] getMCurrentTimeArray() {
        return this.mCurrentTimeArray;
    }

    public final boolean getMDoubleRow() {
        return this.mDoubleRow;
    }

    public final boolean getMEditMode() {
        return this.mEditMode;
    }

    @Nullable
    public final TimeView getMHourView() {
        return this.mHourView;
    }

    @Nullable
    public final TimeView getMMinuteView() {
        return this.mMinuteView;
    }

    @Nullable
    public final ClearableEditText getMSignatureText() {
        return this.mSignatureText;
    }

    @Nullable
    public final FrameLayout getMSignatureTextContainer() {
        return this.mSignatureTextContainer;
    }

    @Nullable
    public final ClassicTextAreaView getMTextArea() {
        return this.mTextArea;
    }

    @Nullable
    public final View getMTimeAccessibilityOverlay() {
        return this.mTimeAccessibilityOverlay;
    }

    @Nullable
    public final View getMTimeGroup() {
        return this.mTimeGroup;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        if (allInOneBaseInfo == null) {
            return super.getMagazineColor();
        }
        Intrinsics.checkNotNull(allInOneBaseInfo);
        if (allInOneBaseInfo.isSuperSaveOpen()) {
            return -1;
        }
        AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
        Intrinsics.checkNotNull(allInOneBaseInfo2);
        return ColorUtils.blendColor(allInOneBaseInfo2.getOriginMagazineColor(), 0.6f);
    }

    public int getMagazinePositionY(boolean z) {
        Rect rect;
        if (z) {
            rect = computeNotificationMinimalRect();
        } else {
            AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
            Intrinsics.checkNotNull(allInOneBaseInfo);
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            AllInOneUtil.RectComputeResult rectComputeResultComputeLayoutResult = allInOneBaseInfo.computeLayoutResult(context);
            rect = rectComputeResultComputeLayoutResult != null ? rectComputeResultComputeLayoutResult.getRect() : null;
        }
        if (rect == null) {
            return 0;
        }
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        int i = rect.bottom;
        AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
        Intrinsics.checkNotNull(allInOneBaseInfo2);
        boolean zHasSignature = allInOneBaseInfo2.hasSignature();
        AllInOneBaseInfo allInOneBaseInfo3 = this.mClockStyleInfo;
        Intrinsics.checkNotNull(allInOneBaseInfo3);
        int iComputeMagazinePositionY = AllInOneUtil.computeMagazinePositionY(context2, i, zHasSignature, allInOneBaseInfo3.getSignatureLineCount());
        Log.w(this.TAG, "getMagazinePositionY: notificationShow=" + z + ", resultRect=" + rect + ", positionY=" + iComputeMagazinePositionY);
        return iComputeMagazinePositionY;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        int magazinePositionY = getMagazinePositionY(true);
        Log.w(this.TAG, "getNotificationClockBottom: bottom=" + magazinePositionY + " mInNotificationState=" + this.mInNotificationState + " mDoubleRow=" + this.mDoubleRow);
        return magazinePositionY;
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        int[] screenWH = DeviceConfig.getScreenWH(getContext());
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return ClockDepthAvoidRuleUtils.createEditRect$default(context, screenWH[0], screenWH[1], null, null, null, 56, null).top;
    }

    @Override // com.miui.clock.MiuiBaseClock2
    @Nullable
    public View getNotificationClockTopView() {
        return this.mTextArea;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Nullable
    public final TimeView getShareTimeView() {
        return this.shareTimeView;
    }

    @NotNull
    public final String getTAG() {
        return this.TAG;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void init(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.init(context);
        this.mVariableFontQueryUtils = new VariableFontQueryUtils(context);
        FontSetData fontSetDataLoadFromAssets = FontSetParser.loadFromAssets(context, "font_set.xml");
        this.mFontSetData = fontSetDataLoadFromAssets;
        if (fontSetDataLoadFromAssets != null) {
            VariableFontQueryUtils variableFontQueryUtils = this.mVariableFontQueryUtils;
            Intrinsics.checkNotNull(variableFontQueryUtils);
            FontSetData fontSetData = this.mFontSetData;
            Intrinsics.checkNotNull(fontSetData);
            variableFontQueryUtils.setFontSetData(fontSetData);
        }
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        this.shareTimeView = new TimeView(mContext, null, 0, 6, null);
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00fc  */
    public final void initTextAreaAndSignatureText() {
        TextWatcher textWatcher;
        ClearableEditText clearableEditText;
        ClearableEditText clearableEditText2;
        TextWatcher textWatcher2 = this.mSignatureTextWatcher;
        if (textWatcher2 != null && (clearableEditText2 = this.mSignatureText) != null) {
            clearableEditText2.removeTextChangedListener(textWatcher2);
        }
        ClassicTextAreaView classicTextAreaView = this.mTextArea;
        Intrinsics.checkNotNull(classicTextAreaView);
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        Intrinsics.checkNotNull(allInOneBaseInfo);
        classicTextAreaView.initData(allInOneBaseInfo, allInOneBaseInfo.getClassicLine1());
        if (!this.mHasPresetData) {
            AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
            Intrinsics.checkNotNull(allInOneBaseInfo2);
            if (!allInOneBaseInfo2.isUnablePresetData()) {
                AllInOneBaseInfo allInOneBaseInfo3 = this.mClockStyleInfo;
                Intrinsics.checkNotNull(allInOneBaseInfo3);
                String weatherJson = allInOneBaseInfo3.getWeatherJson();
                AllInOneBaseInfo allInOneBaseInfo4 = this.mClockStyleInfo;
                Intrinsics.checkNotNull(allInOneBaseInfo4);
                presetData(weatherJson, allInOneBaseInfo4.getHealthJson());
            }
        }
        refreshSignatureLayout();
        ClearableEditText clearableEditText3 = this.mSignatureText;
        if (clearableEditText3 != null) {
            Intrinsics.checkNotNull(clearableEditText3);
            AllInOneBaseInfo allInOneBaseInfo5 = this.mClockStyleInfo;
            Intrinsics.checkNotNull(allInOneBaseInfo5);
            clearableEditText3.setVisibility(ClockStyleInfo.isAODType(allInOneBaseInfo5.getDisplayType()) ? 8 : 4);
        }
        queryDataAsync();
        setSignatureEditable(this.mEditMode);
        if (!this.mEditMode) {
            ClearableEditText clearableEditText4 = this.mSignatureText;
            Intrinsics.checkNotNull(clearableEditText4);
            clearableEditText4.setVisibility(0);
            onScreenSizeChange();
        }
        AllInOneBaseInfo allInOneBaseInfo6 = this.mClockStyleInfo;
        Intrinsics.checkNotNull(allInOneBaseInfo6);
        if (allInOneBaseInfo6.getClassicLine5() != 0) {
            AllInOneBaseInfo allInOneBaseInfo7 = this.mClockStyleInfo;
            Intrinsics.checkNotNull(allInOneBaseInfo7);
            if (ClockStyleInfo.isAODType(allInOneBaseInfo7.getDisplayType())) {
                ClearableEditText clearableEditText5 = this.mSignatureText;
                Intrinsics.checkNotNull(clearableEditText5);
                clearableEditText5.setVisibility(8);
                ClearableEditText clearableEditText6 = this.mSignatureText;
                Intrinsics.checkNotNull(clearableEditText6);
                clearableEditText6.setText("");
            } else {
                AllInOneBaseInfo allInOneBaseInfo8 = this.mClockStyleInfo;
                Intrinsics.checkNotNull(allInOneBaseInfo8);
                String signature = allInOneBaseInfo8.getSignature();
                if (TextUtils.isEmpty(signature)) {
                    ClearableEditText clearableEditText7 = this.mSignatureText;
                    Intrinsics.checkNotNull(clearableEditText7);
                    clearableEditText7.setVisibility(8);
                    ClearableEditText clearableEditText8 = this.mSignatureText;
                    Intrinsics.checkNotNull(clearableEditText8);
                    clearableEditText8.setText("");
                } else {
                    ClearableEditText clearableEditText9 = this.mSignatureText;
                    Intrinsics.checkNotNull(clearableEditText9);
                    clearableEditText9.setVisibility(0);
                    checkAndCorrectSignatureText(this.mSignatureText, signature, 2);
                    if (this.mEditMode) {
                        ClearableEditText clearableEditText10 = this.mSignatureText;
                        Intrinsics.checkNotNull(clearableEditText10);
                        ClearableEditText clearableEditText11 = this.mSignatureText;
                        Intrinsics.checkNotNull(clearableEditText11);
                        Editable text = clearableEditText11.getText();
                        Intrinsics.checkNotNull(text);
                        clearableEditText10.setSelection(text.length());
                    }
                }
                Log.d(this.TAG, "set signature=[" + signature + ']');
            }
        } else {
            ClearableEditText clearableEditText12 = this.mSignatureText;
            Intrinsics.checkNotNull(clearableEditText12);
            clearableEditText12.setVisibility(8);
            ClearableEditText clearableEditText13 = this.mSignatureText;
            Intrinsics.checkNotNull(clearableEditText13);
            clearableEditText13.setText("");
        }
        if (!this.mEditMode || (textWatcher = this.mSignatureTextWatcher) == null || (clearableEditText = this.mSignatureText) == null) {
            return;
        }
        clearableEditText.addTextChangedListener(textWatcher);
    }

    public final boolean isLayoutWithColon() {
        if (this.mDoubleRow) {
            return false;
        }
        return !this.m24HourFormat || this.mColonShow;
    }

    public final boolean isWeatherType() {
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        if (allInOneBaseInfo == null) {
            return false;
        }
        Intrinsics.checkNotNull(allInOneBaseInfo);
        return ClassicContentStyle.Weather.isWeatherType(allInOneBaseInfo.getClassicLine1());
    }

    /* JADX WARN: Code duplicated, block: B:53:0x0150  */
    public final void logTimeRectParams(String str, AllInOneUtil.RectComputeResult rectComputeResult) {
        String str2;
        Resources resources = getContext().getResources();
        Configuration configuration = resources != null ? resources.getConfiguration() : null;
        Resources resources2 = getContext().getResources();
        DisplayMetrics displayMetrics = resources2 != null ? resources2.getDisplayMetrics() : null;
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        AllInOneBaseInfo.AllInOneRectParams rectParamsOrNull = allInOneBaseInfo != null ? allInOneBaseInfo.getRectParamsOrNull() : null;
        AllInOneBaseInfo.AllInOneRectParams rectParams = rectComputeResult != null ? rectComputeResult.getRectParams() : null;
        StringBuilder sb = new StringBuilder();
        sb.append("logTimeRectParams from=");
        sb.append(str);
        sb.append(" isSystemUI=");
        sb.append(DateFormatUtils.isSystemUI(getContext()));
        sb.append(" isQ18=");
        sb.append(DeviceConfig.isQ18());
        sb.append(" inNotif=");
        sb.append(this.mInNotificationState);
        sb.append(" cfg[densityDpi=");
        sb.append(configuration != null ? Integer.valueOf(configuration.densityDpi) : null);
        sb.append(" orientation=");
        sb.append(configuration != null ? Integer.valueOf(configuration.orientation) : null);
        sb.append(" screenW=");
        sb.append(configuration != null ? Integer.valueOf(configuration.screenWidthDp) : null);
        sb.append("dp screenH=");
        sb.append(configuration != null ? Integer.valueOf(configuration.screenHeightDp) : null);
        sb.append("dp fontScale=");
        sb.append(configuration != null ? Float.valueOf(configuration.fontScale) : null);
        sb.append("] metrics[density=");
        sb.append(displayMetrics != null ? Float.valueOf(displayMetrics.density) : null);
        sb.append(" densityDpi=");
        sb.append(displayMetrics != null ? Integer.valueOf(displayMetrics.densityDpi) : null);
        sb.append("] mScreen[w=");
        sb.append(this.mScreenWidth);
        sb.append(" h=");
        sb.append(this.mScreenHeight);
        sb.append("] depthType=");
        AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
        sb.append(allInOneBaseInfo2 != null ? allInOneBaseInfo2.getClockDepthType() : null);
        sb.append(" lockscreenParams=[");
        String str3 = "null";
        if (rectParamsOrNull != null) {
            str2 = "depthType=" + rectParamsOrNull.clockDepthType + " top=" + rectParamsOrNull.clockTopRatio + " bottom=" + rectParamsOrNull.clockBottomRatio + " timeW=" + rectParamsOrNull.timeWidth + " timeH=" + rectParamsOrNull.timeHeight;
            if (str2 == null) {
                str2 = "null";
            }
        } else {
            str2 = "null";
        }
        sb.append(str2);
        sb.append("] computedRect=");
        sb.append(rectComputeResult != null ? rectComputeResult.getRect() : null);
        sb.append(" computedParams=[");
        if (rectParams != null) {
            String str4 = "depthType=" + rectParams.clockDepthType + " top=" + rectParams.clockTopRatio + " bottom=" + rectParams.clockBottomRatio + " timeW=" + rectParams.timeWidth + " timeH=" + rectParams.timeHeight;
            if (str4 != null) {
                str3 = str4;
            }
        }
        sb.append(str3);
        sb.append(']');
        Log.d("AllInOneConvertDataDebug", sb.toString());
        if (AllInOneDebug.DEBUG_LOG) {
            Log.d("AllInOneConvertDataDebug", "logTimeRectParams from=" + str + " callstack:", new Throwable());
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        WeatherFetcherController weatherFetcherController = this.mWeatherFetcherController;
        if (weatherFetcherController != null) {
            weatherFetcherController.reset();
            this.mWeatherFetcherController = null;
        }
        HealthFetcherController healthFetcherController = this.mHealthFetcherController;
        if (healthFetcherController != null) {
            healthFetcherController.reset();
            this.mHealthFetcherController = null;
        }
        this.healthType.clear();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mHourView = (TimeView) findViewById(R.id.hour_view);
        this.mMinuteView = (TimeView) findViewById(R.id.minute_view);
        TimeView timeView = (TimeView) findViewById(R.id.colon_view);
        this.mColonView = timeView;
        if (timeView != null) {
            timeView.setVisibility(8);
        }
        ClassicTextAreaView classicTextAreaView = (ClassicTextAreaView) findViewById(R.id.text_area);
        this.mTextArea = classicTextAreaView;
        Intrinsics.checkNotNull(classicTextAreaView);
        classicTextAreaView.setCalendar(this.mCalendar);
        this.mTimeAccessibilityOverlay = findViewById(R.id.time_accessibility_overlay);
        Object systemService = getContext().getSystemService("accessibility");
        this.mAccessibilityManager = systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null;
        this.mAnimationContainer = findViewById(R.id.clock_animation_container);
        this.mTimeGroup = findViewById(R.id.time_group);
        this.mBlurBlendContainer = findViewById(R.id.blur_blend_container);
        this.mSignatureTextContainer = (FrameLayout) findViewById(R.id.signature_text_container);
        ClearableEditText clearableEditText = (ClearableEditText) findViewById(R.id.signature_text);
        this.mSignatureText = clearableEditText;
        if (clearableEditText != null) {
            Intrinsics.checkNotNull(clearableEditText);
            clearableEditText.setTypeface(FontUtils.getMiSans(380));
            ClearableEditText clearableEditText2 = this.mSignatureText;
            Intrinsics.checkNotNull(clearableEditText2);
            if (clearableEditText2.getInputType() != 131073) {
                ClearableEditText clearableEditText3 = this.mSignatureText;
                Intrinsics.checkNotNull(clearableEditText3);
                clearableEditText3.setInputType(131073);
            }
            ClearableEditText clearableEditText4 = this.mSignatureText;
            Intrinsics.checkNotNull(clearableEditText4);
            clearableEditText4.setBackground(null);
            ClearableEditText clearableEditText5 = this.mSignatureText;
            Intrinsics.checkNotNull(clearableEditText5);
            clearableEditText5.setPadding(0, 0, 0, 0);
            ClearableEditText clearableEditText6 = this.mSignatureText;
            Intrinsics.checkNotNull(clearableEditText6);
            this.mOriginalKeyListener = clearableEditText6.getKeyListener();
            setSignatureEditable(false);
        }
        this.mTextMeasurePaint.setTypeface(FontUtils.getMiSansRCF(this.mContext));
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        ClassicTextAreaView classicTextAreaView = this.mTextArea;
        Intrinsics.checkNotNull(classicTextAreaView);
        classicTextAreaView.onLanguageChanged();
    }

    public final void onScreenSizeChange() {
    }

    @Override // com.miui.clock.MiuiBaseClock2, android.view.View
    public void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i == 0) {
            updateTime();
        }
    }

    public final void presetData(String str, String str2) {
        WeatherBean weatherBean;
        boolean zIsEmpty = TextUtils.isEmpty(str);
        boolean zIsEmpty2 = TextUtils.isEmpty(str2);
        Log.i(this.TAG, "presetData this = " + this + ", weatherEmpty = " + zIsEmpty + ", healthEmpty = " + zIsEmpty2);
        if (zIsEmpty && zIsEmpty2) {
            return;
        }
        checkGson();
        HealthBean healthBean = null;
        if (zIsEmpty) {
            weatherBean = null;
        } else {
            try {
                Gson gson = this.mGson;
                Intrinsics.checkNotNull(gson);
                weatherBean = (WeatherBean) gson.fromJson(str, WeatherBean.class);
            } catch (Exception unused) {
                Log.i(this.TAG, "presetData weather convert fail");
                weatherBean = null;
            }
        }
        if (!zIsEmpty2) {
            try {
                Gson gson2 = this.mGson;
                Intrinsics.checkNotNull(gson2);
                healthBean = (HealthBean) gson2.fromJson(str2, HealthBean.class);
            } catch (Exception unused2) {
                Log.i(this.TAG, "presetData health convert fail");
            }
        }
        if (healthBean == null && weatherBean == null) {
            return;
        }
        this.mHasPresetData = true;
        ClassicTextAreaView classicTextAreaView = this.mTextArea;
        Intrinsics.checkNotNull(classicTextAreaView);
        classicTextAreaView.presetData(healthBean, weatherBean);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void queryDataAsync() {
        super.queryDataAsync();
        if (isWeatherType()) {
            WeatherFetcherController.Companion companion = WeatherFetcherController.Companion;
            Context mContext = this.mContext;
            Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
            Context specialContext = companion.getSpecialContext(mContext, 0);
            if (this.mWeatherFetcherController == null) {
                this.mWeatherFetcherController = new WeatherFetcherController();
            }
            WeatherFetcherController weatherFetcherController = this.mWeatherFetcherController;
            Intrinsics.checkNotNull(weatherFetcherController);
            weatherFetcherController.fetchWeatherData(specialContext, this.mHasPresetData, new WeatherFetcherController.Callback() { // from class: com.miui.clock.allInOne.AllInOneBase.queryDataAsync.1
                @Override // com.miui.clock.utils.WeatherFetcherController.Callback
                public void updateWeatherInfo(WeatherBean weatherBean) {
                    if (AllInOneBase.this.isAttachedToWindow()) {
                        AllInOneBase.this.updateWeather(weatherBean);
                    }
                }
            });
        }
        updateHealthTypeList();
        if (this.healthType.isEmpty()) {
            return;
        }
        WeatherFetcherController.Companion companion2 = WeatherFetcherController.Companion;
        Context mContext2 = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext2, "mContext");
        Context specialContext2 = companion2.getSpecialContext(mContext2, 0);
        if (this.mHealthFetcherController == null) {
            this.mHealthFetcherController = new HealthFetcherController();
        }
        HealthFetcherController healthFetcherController = this.mHealthFetcherController;
        Intrinsics.checkNotNull(healthFetcherController);
        healthFetcherController.fetchHealthData(specialContext2, this.healthType, new HealthFetcherController.Callback() { // from class: com.miui.clock.allInOne.AllInOneBase.queryDataAsync.2
            @Override // com.miui.clock.utils.HealthFetcherController.Callback
            public void updateHealth(HealthBean healthBean) {
                if (AllInOneBase.this.isAttachedToWindow()) {
                    AllInOneBase.this.updateHealth(healthBean);
                }
            }
        });
    }

    public final void refreshFullTimeStrings() {
        boolean z = this.m24HourFormat;
        String timeString = ClassicClockTimeUtils.getTimeString(ClassicClockTimeUtils.getHourInt(z, this.mCalendar), z || this.mDoubleRow);
        String timeString2 = ClassicClockTimeUtils.getTimeString(this.mCalendar.get(20), true);
        this.mAllTimeText = timeString + this.COLON_TEXT + timeString2;
        StringBuilder sb = new StringBuilder();
        sb.append(timeString);
        sb.append(timeString2);
        this.mAllTimeTextNoColon = sb.toString();
        this.mColonIdx = timeString.length();
        TimeView timeView = this.mHourView;
        if (timeView != null) {
            Intrinsics.checkNotNull(timeString);
            timeView.setText(timeString);
        }
        syncMinuteViewText(isLayoutWithColon());
        TimeView timeView2 = this.mColonView;
        if (timeView2 == null) {
            return;
        }
        timeView2.setText(this.COLON_TEXT);
    }

    public final void refreshSignatureLayout() {
        updateSignatureText();
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        if (allInOneBaseInfo != null) {
            ClearableEditText clearableEditText = this.mSignatureText;
            Intrinsics.checkNotNull(allInOneBaseInfo);
            checkAndCorrectSignatureText(clearableEditText, allInOneBaseInfo.getSignature(), 2);
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void resetClock() {
        super.resetClock();
        this.mInNotificationState = false;
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        if (allInOneBaseInfo != null) {
            allInOneBaseInfo.setRectParams(getContext(), null);
        }
    }

    public final View resolveBlurBlendContainer() {
        View view;
        return (!DateFormatUtils.isSystemUI(getContext()) || (view = this.mBlurBlendContainer) == null) ? this : view;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setAllInOneClockRectParams(@Nullable AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        int i4;
        int i5;
        float f3;
        float f4;
        AllInOneClockStyle.Region region2;
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        if (allInOneBaseInfo != null) {
            Intrinsics.checkNotNull(allInOneBaseInfo);
            AllInOneBaseInfo.AllInOneRectParams rectParamsOrNull = allInOneBaseInfo.getRectParamsOrNull();
            AllInOneClockStyle.Region region3 = rectParamsOrNull != null ? rectParamsOrNull.clockDepthType : AllInOneClockStyle.Region.REGION_1;
            if (region == null) {
                region = region3;
            }
            if (f == -1.0f) {
                f = rectParamsOrNull != null ? rectParamsOrNull.clockTopRatio : -1.0f;
            }
            if (f2 == -1.0f) {
                f2 = rectParamsOrNull != null ? rectParamsOrNull.clockBottomRatio : -1.0f;
            }
            Number numberValueOf = rectParamsOrNull != null ? Integer.valueOf(rectParamsOrNull.timeWidth) : Float.valueOf(-1.0f);
            Number numberValueOf2 = rectParamsOrNull != null ? Integer.valueOf(rectParamsOrNull.timeHeight) : Float.valueOf(-1.0f);
            if (i == -1) {
                i = numberValueOf.intValue();
            }
            if (i2 == -1) {
                i2 = numberValueOf2.intValue();
            }
            this.mAllInOneWeight = i3 != -1 ? i3 : this.mAllInOneWeight;
            if (this.mShouldPersistParams && i3 != -1) {
                AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
                Intrinsics.checkNotNull(allInOneBaseInfo2);
                allInOneBaseInfo2.setClockWeight(i3);
            }
            if (DepthAvoidEvaluatorDebug.DEBUG_LOG) {
                String str = this.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("setAllInOneClockRectParams: rect=");
                i4 = i2;
                i5 = i;
                f3 = f2;
                f4 = f;
                region2 = region;
                sb.append(new AllInOneBaseInfo.AllInOneRectParams(region2, f4, f3, i5, i4));
                Log.d(str, sb.toString());
            } else {
                i4 = i2;
                i5 = i;
                f3 = f2;
                f4 = f;
                region2 = region;
            }
            AllInOneBaseInfo allInOneBaseInfo3 = this.mClockStyleInfo;
            Intrinsics.checkNotNull(allInOneBaseInfo3);
            allInOneBaseInfo3.setRectParams(getContext(), new AllInOneBaseInfo.AllInOneRectParams(region2, f4, f3, i5, i4), this.mShouldPersistParams);
        }
        updateDepthContentVisibility();
        applyAllInOneRect(null);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setAllInOneClockRectParams(@Nullable AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        if (allInOneBaseInfo != null) {
            allInOneBaseInfo.setDoubleRow(z);
        }
        if (z != this.mDoubleRow) {
            this.mDoubleRow = z;
            refreshFullTimeStrings();
            AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
            if (allInOneBaseInfo2 != null) {
                updateHollowStyle(allInOneBaseInfo2);
            }
        }
        setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setAllInOneClockRectParams(@Nullable AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, @Nullable Boolean bool) {
        AllInOneBaseInfo allInOneBaseInfo;
        if (bool != null && (allInOneBaseInfo = this.mClockStyleInfo) != null) {
            allInOneBaseInfo.setSkipScreenTransform(bool.booleanValue());
        }
        setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setBackgroundBlurContainer(@Nullable View view) {
        if (view != null) {
            ClockEffectUtils.clearClockEffectsContainer(this, this.mClockStyleInfo);
        }
        super.setBackgroundBlurContainer(view);
        if (view == null) {
            updateClockEffectsContainer();
            return;
        }
        for (View view2 : getEffectChildViews()) {
            if (view2 != null) {
                MiuiBlurUtils.chooseBackgroundBlurContainer(view2, view);
            }
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, @Nullable Map<String, Integer> map, boolean z2) {
        int secondaryBlendColor;
        super.setClockPalette(i, z, map, z2);
        if (this.mClockStyleInfo == null) {
            return;
        }
        updateGlassEnable();
        if (!DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
            Intrinsics.checkNotNull(allInOneBaseInfo);
            if (!allInOneBaseInfo.isSuperSaveOpen()) {
                updateClockEffectsContainer();
                Context context = getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                new AllInOneColor(context).processingClockPalette(this.mClockStyleInfo, i, z, map, z2);
                AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
                Intrinsics.checkNotNull(allInOneBaseInfo2);
                allInOneBaseInfo2.snapshotCurrentColors();
                AllInOneBaseInfo allInOneBaseInfo3 = this.mClockStyleInfo;
                Intrinsics.checkNotNull(allInOneBaseInfo3);
                boolean zIsGlassType = ClockEffectUtils.isGlassType(allInOneBaseInfo3.getClockEffect());
                updateColor();
                boolean zSupportBackgroundBlur = DeviceConfig.supportBackgroundBlur(this.mContext);
                if (zSupportBackgroundBlur) {
                    AllInOneBaseInfo allInOneBaseInfo4 = this.mClockStyleInfo;
                    Intrinsics.checkNotNull(allInOneBaseInfo4);
                    if (ClockEffectUtils.needPickSolidColor(allInOneBaseInfo4.getClockEffect(), zSupportBackgroundBlur)) {
                        return;
                    }
                    AllInOneBaseInfo allInOneBaseInfo5 = this.mClockStyleInfo;
                    Intrinsics.checkNotNull(allInOneBaseInfo5);
                    boolean zIsFullAODType = ClockStyleInfo.isFullAODType(allInOneBaseInfo5.getDisplayType());
                    AllInOneBaseInfo allInOneBaseInfo6 = this.mClockStyleInfo;
                    Intrinsics.checkNotNull(allInOneBaseInfo6);
                    int fullAodBlendColor = zIsFullAODType ? allInOneBaseInfo6.getFullAodBlendColor() : allInOneBaseInfo6.getBlendColor();
                    if (zIsFullAODType) {
                        AllInOneBaseInfo allInOneBaseInfo7 = this.mClockStyleInfo;
                        Intrinsics.checkNotNull(allInOneBaseInfo7);
                        secondaryBlendColor = allInOneBaseInfo7.getFullAodSecondaryBlendColor();
                    } else {
                        AllInOneBaseInfo allInOneBaseInfo8 = this.mClockStyleInfo;
                        Intrinsics.checkNotNull(allInOneBaseInfo8);
                        secondaryBlendColor = allInOneBaseInfo8.getSecondaryBlendColor();
                    }
                    updateClockBlendColor(fullAodBlendColor, secondaryBlendColor);
                    if (zIsGlassType) {
                        updateClockGlassBrightness(z);
                        AllInOneBaseInfo allInOneBaseInfo9 = this.mClockStyleInfo;
                        Intrinsics.checkNotNull(allInOneBaseInfo9);
                        int glassColor = allInOneBaseInfo9.getGlassColor();
                        AllInOneBaseInfo allInOneBaseInfo10 = this.mClockStyleInfo;
                        Intrinsics.checkNotNull(allInOneBaseInfo10);
                        updateClockGlassColor(glassColor, allInOneBaseInfo10.getSecondaryGlassColor());
                        ColorParams colorParams = this.mGlassColorParams;
                        AllInOneBaseInfo allInOneBaseInfo11 = this.mClockStyleInfo;
                        Intrinsics.checkNotNull(allInOneBaseInfo11);
                        colorParams.setStartColor(allInOneBaseInfo11.getGlassColor());
                        ColorParams colorParams2 = this.mGlassColorParams;
                        AllInOneBaseInfo allInOneBaseInfo12 = this.mClockStyleInfo;
                        Intrinsics.checkNotNull(allInOneBaseInfo12);
                        colorParams2.setEndColor(allInOneBaseInfo12.getAodGlassColor());
                        this.mGlassColorParams.setFraction(0.0f);
                        ColorParams colorParams3 = this.mSecondaryGlassColorParams;
                        AllInOneBaseInfo allInOneBaseInfo13 = this.mClockStyleInfo;
                        Intrinsics.checkNotNull(allInOneBaseInfo13);
                        colorParams3.setStartColor(allInOneBaseInfo13.getSecondaryGlassColor());
                        ColorParams colorParams4 = this.mSecondaryGlassColorParams;
                        AllInOneBaseInfo allInOneBaseInfo14 = this.mClockStyleInfo;
                        Intrinsics.checkNotNull(allInOneBaseInfo14);
                        colorParams4.setEndColor(allInOneBaseInfo14.getAodSecondaryGlassColor());
                        this.mSecondaryGlassColorParams.setFraction(0.0f);
                        return;
                    }
                    return;
                }
                return;
            }
        }
        updateColor();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(@Nullable ClockStyleInfo clockStyleInfo) {
        int signatureLines;
        Float glassTransparency;
        super.setClockStyleInfo(clockStyleInfo);
        if (clockStyleInfo == null) {
            return;
        }
        AllInOneBaseInfo allInOneBaseInfo = (AllInOneBaseInfo) clockStyleInfo;
        this.mClockStyleInfo = allInOneBaseInfo;
        this.mShouldPersistParams = DateFormatUtils.isAOD(getContext()) && !(ClockStyleInfo.isAODType(allInOneBaseInfo.getDisplayType()) || ClockStyleInfo.isFullAODType(allInOneBaseInfo.getDisplayType()));
        this.mDoubleRow = allInOneBaseInfo.isDoubleRow();
        this.mColonShow = allInOneBaseInfo.isColonShow();
        AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
        if (allInOneBaseInfo2 != null) {
            this.mAllInOneWeight = ClockStyleInfo.isFullAODType(allInOneBaseInfo.getDisplayType()) ? AllInOneAodLinkageUtils.getAodClockWeight$default(allInOneBaseInfo2.getClockWeight(), allInOneBaseInfo2.getHollowStyle(), false, 4, null) : allInOneBaseInfo2.getClockWeight();
        }
        AllInOneBaseInfo allInOneBaseInfo3 = this.mClockStyleInfo;
        Intrinsics.checkNotNull(allInOneBaseInfo3);
        if (allInOneBaseInfo3.hasSignature()) {
            AllInOneUtil allInOneUtil = AllInOneUtil.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            AllInOneBaseInfo allInOneBaseInfo4 = this.mClockStyleInfo;
            Intrinsics.checkNotNull(allInOneBaseInfo4);
            String signature = allInOneBaseInfo4.getSignature();
            Intrinsics.checkNotNullExpressionValue(signature, "getSignature(...)");
            signatureLines = allInOneUtil.getSignatureLines(context, signature);
        } else {
            signatureLines = 0;
        }
        AllInOneBaseInfo allInOneBaseInfo5 = this.mClockStyleInfo;
        Intrinsics.checkNotNull(allInOneBaseInfo5);
        allInOneBaseInfo5.setSignatureLineCount(signatureLines);
        AllInOneBaseInfo allInOneBaseInfo6 = this.mClockStyleInfo;
        if (allInOneBaseInfo6 != null) {
            updateFontType(allInOneBaseInfo6.getFontStyle());
        }
        this.mInNotificationState = false;
        updateDepthContentVisibility();
        initTextAreaAndSignatureText();
        updateViewsLayoutParams();
        updateTime();
        clearColorEffect(false);
        updateColor();
        updateGlassEnable();
        updateClockEffectsContainer();
        updateHollowStyle(clockStyleInfo);
        AllInOneBaseInfo allInOneBaseInfo7 = this.mClockStyleInfo;
        if (allInOneBaseInfo7 != null && (glassTransparency = allInOneBaseInfo7.getGlassTransparency()) != null) {
            updateTransparency(glassTransparency.floatValue());
        }
        updateColonVisibility();
    }

    /* JADX WARN: Code duplicated, block: B:40:0x010f  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v19 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3, types: [boolean, int] */
    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockViewRect(@NotNull Rect rect) {
        float[] fArrCopyOf;
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        Intrinsics.checkNotNullParameter(rect, "rect");
        this.mClockViewRect = rect;
        String str = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("setClockViewRect: rect=");
        sb.append(rect);
        sb.append(", type=");
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        sb.append(allInOneBaseInfo != null ? Integer.valueOf(allInOneBaseInfo.getDisplayType()) : null);
        sb.append(", notif=");
        sb.append(this.mInNotificationState);
        Log.d(str, sb.toString());
        float f6 = this.mAllInOneWeight;
        if (this.mInNotificationState && f6 > 0.0f) {
            f6 *= 0.85f;
        }
        float fWidth = rect.width();
        float fHeight = rect.height();
        if (fHeight == 0.0f) {
            Log.d(this.TAG, "setClockViewRect error: rectHeight=0");
            return;
        }
        float f7 = fWidth / fHeight;
        AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
        Intrinsics.checkNotNull(allInOneBaseInfo2);
        int displayType = allInOneBaseInfo2.getDisplayType();
        if (!ClockStyleInfo.isAODType(displayType)) {
            ClockStyleInfo.isFullAODType(displayType);
        }
        Log.d(this.TAG, "setClockViewRect: rect=" + rect + ", ratio=" + f7 + ", mDoubleRow=" + this.mDoubleRow + " mAllInOneWeight " + this.mAllInOneWeight);
        boolean z = this.mDoubleRow;
        ?? r10 = (z || (this.m24HourFormat && !this.mColonShow)) ? 0 : 1;
        boolean z2 = (this.m24HourFormat || z) ? false : true;
        RectQueryKey rectQueryKey = new RectQueryKey(rect.left, rect.top, rect.right, rect.bottom, z2, !z);
        boolean z3 = z2;
        if (this.mVariableFontQueryUtils == null || this.mFontSetData == null) {
            fArrCopyOf = null;
        } else {
            RectQueryLruCache rectQueryLruCache = rectQueryCache;
            fArrCopyOf = rectQueryLruCache.get(rectQueryKey);
            if (fArrCopyOf == null) {
                VariableFontQueryUtils variableFontQueryUtils = this.mVariableFontQueryUtils;
                Intrinsics.checkNotNull(variableFontQueryUtils);
                float[] fArrQuery = variableFontQueryUtils.query(new RectF(rect), z3, Boolean.valueOf(!this.mDoubleRow));
                if (fArrQuery != null) {
                    fArrCopyOf = Arrays.copyOf(fArrQuery, fArrQuery.length);
                    Intrinsics.checkNotNullExpressionValue(fArrCopyOf, "copyOf(...)");
                    rectQueryLruCache.put(rectQueryKey, fArrCopyOf);
                } else {
                    fArrCopyOf = null;
                }
            }
        }
        if (fArrCopyOf != null && fArrCopyOf.length >= 4) {
            float f8 = fArrCopyOf[0];
            float f9 = fArrCopyOf[1];
            float fSimpleDpToPx = DeviceConfig.simpleDpToPx(fArrCopyOf[2], this.mContext);
            if (f6 == -1.0f) {
                f6 = fArrCopyOf[3];
            }
            if (this.mAllTimeText.length() == 0) {
                refreshFullTimeStrings();
            }
            syncMinuteViewText(r10);
            String str2 = r10 != 0 ? this.mAllTimeText : this.mAllTimeTextNoColon;
            int i = this.mColonIdx;
            int length = (str2.length() - i) - r10;
            TimeView timeView = this.mHourView;
            if (timeView != null) {
                if (timeView.getVisibility() == 8) {
                    timeView = null;
                }
                if (timeView != null) {
                    timeView.setClockParamsInternal(f6, f8, f9);
                    timeView.setSizeInternal(fSimpleDpToPx);
                }
            }
            TimeView timeView2 = this.mMinuteView;
            if (timeView2 != null) {
                if (timeView2.getVisibility() == 8) {
                    timeView2 = null;
                }
                if (timeView2 != null) {
                    timeView2.setClockParamsInternal(f6, f8, f9);
                    timeView2.setSizeInternal(fSimpleDpToPx);
                }
            }
            TimeView timeView3 = this.mColonView;
            if (timeView3 != null) {
                timeView3.setClockParamsInternal(f6, f8, f9);
                timeView3.setSizeInternal(fSimpleDpToPx);
            }
            TextMeasureUtils.preparePaint(this.mTextMeasurePaint, fSimpleDpToPx, f6, f8, f9);
            float fMeasureText = this.mTextMeasurePaint.measureText(str2);
            int[] iArr = this.mSegmentLengths;
            iArr[0] = i;
            iArr[1] = r10;
            iArr[2] = length;
            TextMeasureUtils.getSegmentWidths(this.mTextMeasurePaint, str2, iArr, this.mSegmentWidths);
            float[] fArr = this.mSegmentWidths;
            float f10 = fArr[0];
            float f11 = fArr[1];
            float f12 = fArr[2];
            TimeView timeView4 = this.mHourView;
            if (timeView4 != null) {
                if (timeView4.getVisibility() == 8) {
                    timeView4 = null;
                }
                if (timeView4 != null) {
                    timeView4.setAdvanceWidth(f10);
                }
            }
            TimeView timeView5 = this.mMinuteView;
            if (timeView5 != null) {
                if (timeView5.getVisibility() == 8) {
                    timeView5 = null;
                }
                if (timeView5 != null) {
                    timeView5.setAdvanceWidth(r10 != 0 ? f11 + f12 : f12);
                    timeView5.setColonAdvanceWidth(r10 != 0 ? f11 : 0.0f);
                }
            }
            TimeView timeView6 = this.mColonView;
            if (timeView6 != null) {
                timeView6.setAdvanceWidth(f11);
            }
            float f13 = this.mClockViewRect.left + ((fWidth - fMeasureText) / 2.0f);
            Paint.FontMetrics fontMetrics = this.mTextMeasurePaint.getFontMetrics();
            TimeView timeView7 = this.mHourView;
            if (timeView7 != null) {
                if (timeView7.getVisibility() == 8) {
                    timeView7 = null;
                }
                if (timeView7 != null) {
                    if (this.mDoubleRow) {
                        f5 = this.mClockViewRect.left + ((fWidth - f10) / 2.0f);
                        f4 = rect.top - fontMetrics.top;
                    } else {
                        f4 = rect.top - fontMetrics.top;
                        f5 = f13;
                    }
                    timeView7.setDrawTextX(f5);
                    timeView7.setDrawTextY(f4);
                    timeView7.invalidate();
                }
            }
            TimeView timeView8 = this.mMinuteView;
            if (timeView8 != null) {
                TimeView timeView9 = timeView8.getVisibility() != 8 ? timeView8 : null;
                if (timeView9 != null) {
                    if (this.mDoubleRow) {
                        f = this.mClockViewRect.left + ((fWidth - f12) / 2.0f);
                        f2 = rect.top + (fHeight * 0.51f);
                        f3 = fontMetrics.top;
                    } else {
                        f = f13 + f10;
                        f2 = rect.top;
                        f3 = fontMetrics.top;
                    }
                    float f14 = f2 - f3;
                    timeView9.setDrawTextX(f);
                    timeView9.setDrawTextY(f14);
                    timeView9.invalidate();
                }
            }
            TimeView timeView10 = this.mColonView;
            if (timeView10 != null) {
                timeView10.setDrawTextX(f13 + f10);
                timeView10.setDrawTextY(rect.top - fontMetrics.top);
                timeView10.invalidate();
            }
        }
        updateTextAreaAndSignaturePosition(rect);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setEditMode(boolean z) {
        Editable text;
        ClearableEditText clearableEditText;
        this.mEditMode = z;
        int length = 0;
        if (!z) {
            TextWatcher textWatcher = this.mSignatureTextWatcher;
            if (textWatcher != null && (clearableEditText = this.mSignatureText) != null) {
                clearableEditText.removeTextChangedListener(textWatcher);
            }
            this.mSignatureTextWatcher = null;
            setSignatureEditable(false);
        }
        if (!z || this.mClockStyleInfo == null) {
            return;
        }
        setSignatureEditable(true);
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        Intrinsics.checkNotNull(allInOneBaseInfo);
        String signature = allInOneBaseInfo.getSignature();
        ClearableEditText clearableEditText2 = this.mSignatureText;
        if (clearableEditText2 != null) {
            clearableEditText2.setText(signature == null ? "" : signature);
        }
        ClearableEditText clearableEditText3 = this.mSignatureText;
        if (clearableEditText3 != null) {
            if (clearableEditText3 != null && (text = clearableEditText3.getText()) != null) {
                length = text.length();
            }
            clearableEditText3.setSelection(length);
        }
        ClearableEditText clearableEditText4 = this.mSignatureText;
        if (clearableEditText4 != null) {
            clearableEditText4.setMaxWidth(getDimen(R.dimen.classic_text_signature_max_width));
        }
        checkAndCorrectSignatureText(this.mSignatureText, signature, 2);
        addSignatureTextWatcher();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setEditorColonShow(boolean z) {
        if (this.mColonShow != z) {
            this.mColonShow = z;
            updateColonVisibility();
            if (this.mAllTimeText.length() > 0) {
                refreshFullTimeStrings();
            }
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setEditorColonViewVisible(boolean z) {
        TimeView timeView = this.mColonView;
        if (timeView == null) {
            return;
        }
        timeView.setVisibility(z ? 0 : 8);
    }

    public final void setFontStyle(@NotNull BaseFontStyle baseFontStyle) {
        Intrinsics.checkNotNullParameter(baseFontStyle, "<set-?>");
        this.fontStyle = baseFontStyle;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
        TimeView timeView = this.mHourView;
        if (timeView != null) {
            timeView.setGpuOptimize(z);
        }
        TimeView timeView2 = this.mMinuteView;
        if (timeView2 != null) {
            timeView2.setGpuOptimize(z);
        }
        TimeView timeView3 = this.mColonView;
        if (timeView3 != null) {
            timeView3.setGpuOptimize(z);
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    public final void setMAllInOneWeight(float f) {
        this.mAllInOneWeight = f;
    }

    public final void setMAnimationContainer(@Nullable View view) {
        this.mAnimationContainer = view;
    }

    public final void setMBlurBlendContainer(@Nullable View view) {
        this.mBlurBlendContainer = view;
    }

    public final void setMClockStyleInfo(@Nullable AllInOneBaseInfo allInOneBaseInfo) {
        this.mClockStyleInfo = allInOneBaseInfo;
    }

    public final void setMClockViewRect(@NotNull Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "<set-?>");
        this.mClockViewRect = rect;
    }

    public final void setMColonShow(boolean z) {
        this.mColonShow = z;
    }

    public final void setMColonView(@Nullable TimeView timeView) {
        this.mColonView = timeView;
    }

    public final void setMCurrentTimeArray(@NotNull int[] iArr) {
        Intrinsics.checkNotNullParameter(iArr, "<set-?>");
        this.mCurrentTimeArray = iArr;
    }

    public final void setMDoubleRow(boolean z) {
        this.mDoubleRow = z;
    }

    public final void setMEditMode(boolean z) {
        this.mEditMode = z;
    }

    public final void setMHourView(@Nullable TimeView timeView) {
        this.mHourView = timeView;
    }

    public final void setMMinuteView(@Nullable TimeView timeView) {
        this.mMinuteView = timeView;
    }

    public final void setMSignatureText(@Nullable ClearableEditText clearableEditText) {
        this.mSignatureText = clearableEditText;
    }

    public final void setMSignatureTextContainer(@Nullable FrameLayout frameLayout) {
        this.mSignatureTextContainer = frameLayout;
    }

    public final void setMTextArea(@Nullable ClassicTextAreaView classicTextAreaView) {
        this.mTextArea = classicTextAreaView;
    }

    public final void setMTimeAccessibilityOverlay(@Nullable View view) {
        this.mTimeAccessibilityOverlay = view;
    }

    public final void setMTimeGroup(@Nullable View view) {
        this.mTimeGroup = view;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setMinuteColonAlpha(float f) {
        TimeView timeView = this.mMinuteView;
        if (timeView != null) {
            timeView.setColonAlpha(f);
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    public final void setShareTimeView(@Nullable TimeView timeView) {
        this.shareTimeView = timeView;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setSuperSaveOpen(boolean z) {
        super.setSuperSaveOpen(z);
        if (this.mClockStyleInfo != null) {
            updateColor();
        }
    }

    public final void setTAG(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.TAG = str;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    public final void syncMinuteViewText(boolean z) {
        String strSubstring = this.mAllTimeText.substring(this.mColonIdx + 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        if (!z) {
            TimeView timeView = this.mMinuteView;
            if (timeView != null) {
                timeView.setText(strSubstring);
            }
            TimeView timeView2 = this.mMinuteView;
            if (timeView2 != null) {
                timeView2.setColonSplitIndex(0);
                return;
            }
            return;
        }
        TimeView timeView3 = this.mMinuteView;
        if (timeView3 != null) {
            timeView3.setText(this.COLON_TEXT + strSubstring);
        }
        TimeView timeView4 = this.mMinuteView;
        if (timeView4 != null) {
            timeView4.setColonSplitIndex(this.COLON_TEXT.length());
        }
    }

    public final void toNotificationState(boolean z, int i) {
        AllInOneBaseInfo allInOneBaseInfo;
        this.mInNotificationState = z;
        if (i >= 0 && (allInOneBaseInfo = this.mClockStyleInfo) != null) {
            allInOneBaseInfo.setMagazineHeight(i);
        }
        String str = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("toNotificationState: hasNotification=");
        sb.append(z);
        sb.append(", magazineHeight=");
        AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
        AllInOneUtil.RectComputeResult rectComputeResultComputeLayoutResult = null;
        sb.append(allInOneBaseInfo2 != null ? Integer.valueOf(allInOneBaseInfo2.getMagazineHeight()) : null);
        sb.append(", type=");
        AllInOneBaseInfo allInOneBaseInfo3 = this.mClockStyleInfo;
        sb.append(allInOneBaseInfo3 != null ? Integer.valueOf(allInOneBaseInfo3.getDisplayType()) : null);
        sb.append(", infoHash=");
        AllInOneBaseInfo allInOneBaseInfo4 = this.mClockStyleInfo;
        sb.append(allInOneBaseInfo4 != null ? Integer.valueOf(allInOneBaseInfo4.hashCode()) : null);
        sb.append(", viewHash=");
        sb.append(hashCode());
        Log.d(str, sb.toString());
        if (z) {
            applyNotificationMinimalState();
            return;
        }
        AllInOneBaseInfo allInOneBaseInfo5 = this.mClockStyleInfo;
        if (allInOneBaseInfo5 != null) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            rectComputeResultComputeLayoutResult = allInOneBaseInfo5.computeLayoutResult(context);
        }
        applyAllInOneRect(rectComputeResultComputeLayoutResult);
    }

    public final void updateBlurBlendContainerBounds() {
        Rect rect;
        int signatureLines;
        View view = this.mBlurBlendContainer;
        if (view == null) {
            return;
        }
        if (DateFormatUtils.isAOD(getContext())) {
            updateBlurBlendContainerBounds$applyMatchParent(view);
            updateBlurBlendContainerBounds$applyMatchParent(this.mAnimationContainer);
            updateBlurBlendContainerBounds$applyMatchParent(this);
            return;
        }
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        if (allInOneBaseInfo == null) {
            return;
        }
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        AllInOneUtil.RectComputeResult rectComputeResultComputeLayoutResult = allInOneBaseInfo.computeLayoutResult(context);
        if (rectComputeResultComputeLayoutResult == null || (rect = rectComputeResultComputeLayoutResult.getRect()) == null) {
            return;
        }
        if (allInOneBaseInfo.hasSignature()) {
            AllInOneUtil allInOneUtil = AllInOneUtil.INSTANCE;
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            String signature = allInOneBaseInfo.getSignature();
            Intrinsics.checkNotNullExpressionValue(signature, "getSignature(...)");
            signatureLines = allInOneUtil.getSignatureLines(context2, signature);
        } else {
            signatureLines = 0;
        }
        AllInOneUtil allInOneUtil2 = AllInOneUtil.INSTANCE;
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        int signatureBottomToTime = rect.bottom + allInOneUtil2.getSignatureBottomToTime(context3, signatureLines) + DeviceConfig.dpToPxRounded(20.0f, getContext());
        updateBlurBlendContainerBounds$applyHeight(signatureBottomToTime, view);
        updateBlurBlendContainerBounds$applyHeight(signatureBottomToTime, this.mAnimationContainer);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        super.updateClockColor(i, i2);
        TimeView timeView = this.mHourView;
        if (timeView != null) {
            timeView.setTextColor(i);
        }
        TimeView timeView2 = this.mColonView;
        if (timeView2 != null) {
            timeView2.setTextColor(i2);
        }
        TimeView timeView3 = this.mMinuteView;
        if (timeView3 != null) {
            timeView3.setTextColor(i2);
        }
        ClassicTextAreaView classicTextAreaView = this.mTextArea;
        if (classicTextAreaView != null) {
            classicTextAreaView.setTextColor(i);
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockEffectsContainer() {
        int dimen;
        if (DeviceConfig.supportBackgroundBlur(this.mContext)) {
            ClockStyleInfo clockStyleInfo = getClockStyleInfo();
            int clockEffect = clockStyleInfo != null ? clockStyleInfo.getClockEffect() : 0;
            if (ClockEffectUtils.isSolidType(clockEffect)) {
                return;
            }
            super.updateClockEffectsContainer();
            if (ClockEffectUtils.isGlassType(clockEffect)) {
                AllInOneUtil allInOneUtil = AllInOneUtil.INSTANCE;
                ClockStyleInfo clockStyleInfo2 = getClockStyleInfo();
                Float glassTransparency = clockStyleInfo2 != null ? clockStyleInfo2.getGlassTransparency() : null;
                dimen = allInOneUtil.getGlassSmallBlurRadius(glassTransparency == null ? 0.0f : glassTransparency.floatValue());
            } else {
                dimen = getDimen(R.dimen.miui_eastern_art_c_clock_blur_radius);
            }
            View viewResolveBlurBlendContainer = resolveBlurBlendContainer();
            View[] effectChildViews = getEffectChildViews();
            updateViewBackgroundBlurContainer(viewResolveBlurBlendContainer, dimen, (View[]) Arrays.copyOf(effectChildViews, effectChildViews.length));
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateClockGlassBrightness(boolean z) {
        super.updateClockGlassBrightness(z);
        TimeView timeView = this.mHourView;
        if (timeView != null) {
            timeView.setBrightness(z);
        }
        TimeView timeView2 = this.mMinuteView;
        if (timeView2 != null) {
            timeView2.setBrightness(z);
        }
        TimeView timeView3 = this.mColonView;
        if (timeView3 != null) {
            timeView3.setBrightness(z);
        }
    }

    public final void updateClockGlassColor(int i, int i2) {
        Context context = this.mContext;
        if (context == null || !DeviceConfig.isGlassEffectEnable(context) || getClockStyleInfo() == null) {
            return;
        }
        ClockStyleInfo clockStyleInfo = getClockStyleInfo();
        if (clockStyleInfo != null) {
            if (ClockStyleInfo.isAODType(clockStyleInfo.getDisplayType()) || clockStyleInfo.isSuperSaveOpen()) {
                return;
            }
            TimeView timeView = this.mHourView;
            if (timeView != null) {
                timeView.setGlassColor(i, clockStyleInfo.isAutoPrimaryColor());
            }
            TimeView timeView2 = this.mMinuteView;
            if (timeView2 != null) {
                timeView2.setGlassColor(i2, clockStyleInfo.isAutoSecondaryColor());
            }
            TimeView timeView3 = this.mColonView;
            if (timeView3 != null) {
                timeView3.setGlassColor(i2, clockStyleInfo.isAutoSecondaryColor());
            }
        }
        TimeView timeView4 = this.mHourView;
        if (timeView4 != null) {
            MiuiBlurUtils.setGlassEffectMethod(timeView4, timeView4.getGlassData());
        }
        TimeView timeView5 = this.mMinuteView;
        if (timeView5 != null) {
            MiuiBlurUtils.setGlassEffectMethod(timeView5, timeView5.getGlassData());
        }
        TimeView timeView6 = this.mColonView;
        if (timeView6 != null) {
            MiuiBlurUtils.setGlassEffectMethod(timeView6, timeView6.getGlassData());
        }
    }

    public final void updateColonVisibility() {
        boolean zIsLayoutWithColon = isLayoutWithColon();
        TimeView timeView = this.mMinuteView;
        if (timeView != null) {
            timeView.setColonSplitIndex(zIsLayoutWithColon ? this.COLON_TEXT.length() : 0);
        }
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        if (this.mClockStyleInfo != null) {
            ClearableEditText activeSignatureView = getActiveSignatureView();
            if (activeSignatureView != null) {
                activeSignatureView.setTextColor(getMagazineColor());
            }
            ClassicTextAreaView classicTextAreaView = this.mTextArea;
            if (classicTextAreaView != null) {
                AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
                Intrinsics.checkNotNull(allInOneBaseInfo);
                classicTextAreaView.setTextColor(allInOneBaseInfo.getDateColor());
            }
        }
    }

    public void updateDepthContentVisibility() {
    }

    public final void updateFontType(int i) {
        Typeface miSansRCF;
        switch (i) {
            case 21:
                miSansRCF = FontUtils.getMiSansRCF(this.mContext);
                break;
            case 22:
                miSansRCF = FontUtils.getMiSansRoundedF(this.mContext);
                break;
            case 23:
                miSansRCF = FontUtils.getMiSerifF(this.mContext);
                break;
            case 24:
                miSansRCF = FontUtils.getMiSansSquareF(this.mContext);
                break;
            case 25:
                miSansRCF = FontUtils.getMiHausF(this.mContext);
                break;
            default:
                miSansRCF = FontUtils.getMiSansRCF(this.mContext);
                break;
        }
        TimeView timeView = this.mHourView;
        if (timeView != null) {
            Intrinsics.checkNotNull(miSansRCF);
            timeView.setTypeface(miSansRCF);
        }
        TimeView timeView2 = this.mMinuteView;
        if (timeView2 != null) {
            Intrinsics.checkNotNull(miSansRCF);
            timeView2.setTypeface(miSansRCF);
        }
        TimeView timeView3 = this.mColonView;
        if (timeView3 != null) {
            Intrinsics.checkNotNull(miSansRCF);
            timeView3.setTypeface(miSansRCF);
        }
        TimeView timeView4 = this.mHourView;
        if (timeView4 != null) {
            timeView4.setFontStyle(i);
        }
        TimeView timeView5 = this.mMinuteView;
        if (timeView5 != null) {
            timeView5.setFontStyle(i);
        }
        TimeView timeView6 = this.mColonView;
        if (timeView6 != null) {
            timeView6.setFontStyle(i);
        }
        this.mTextMeasurePaint.setTypeface(miSansRCF);
    }

    public final void updateGlassEnable() {
        boolean zIsHighTextContrastEnabled = DeviceConfig.isHighTextContrastEnabled(getContext());
        ClockStyleInfo clockStyleInfo = getClockStyleInfo();
        boolean z = clockStyleInfo != null && clockStyleInfo.isSuperSaveOpen();
        ClockStyleInfo clockStyleInfo2 = getClockStyleInfo();
        Intrinsics.checkNotNull(clockStyleInfo2);
        boolean z2 = z || zIsHighTextContrastEnabled || ClockStyleInfo.isAODType(clockStyleInfo2.getDisplayType()) || !DeviceConfig.isGlassEffectEnable(getContext());
        ClockStyleInfo clockStyleInfo3 = getClockStyleInfo();
        boolean z3 = ClockEffectUtils.isGlassType(clockStyleInfo3 != null ? clockStyleInfo3.getClockEffect() : 0) && !z2;
        TimeView timeView = this.mHourView;
        if (timeView != null) {
            timeView.setIsMiGlassEffectEnable(z3);
        }
        TimeView timeView2 = this.mMinuteView;
        if (timeView2 != null) {
            timeView2.setIsMiGlassEffectEnable(z3);
        }
        TimeView timeView3 = this.mColonView;
        if (timeView3 != null) {
            timeView3.setIsMiGlassEffectEnable(z3);
        }
    }

    public final void updateHealth(HealthBean healthBean) {
        ClassicTextAreaView classicTextAreaView = this.mTextArea;
        if (classicTextAreaView != null) {
            Intrinsics.checkNotNull(classicTextAreaView);
            classicTextAreaView.updateHealth(healthBean);
        }
    }

    public final void updateHealthTypeList() {
        this.healthType.clear();
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        if (allInOneBaseInfo != null) {
            Intrinsics.checkNotNull(allInOneBaseInfo);
            addType(allInOneBaseInfo.getClassicLine1());
        }
    }

    public final void updateHollowStyle(ClockStyleInfo clockStyleInfo) {
        Pair pair;
        int hollowStyle = clockStyleInfo.getHollowStyle();
        boolean z = true;
        if (hollowStyle == 1) {
            Boolean bool = Boolean.FALSE;
            pair = TuplesKt.to(bool, bool);
        } else if (hollowStyle == 2) {
            pair = TuplesKt.to(Boolean.TRUE, Boolean.FALSE);
        } else if (hollowStyle == 3) {
            pair = TuplesKt.to(Boolean.FALSE, Boolean.TRUE);
        } else if (hollowStyle != 4) {
            Boolean bool2 = Boolean.FALSE;
            pair = TuplesKt.to(bool2, bool2);
        } else {
            Boolean bool3 = Boolean.TRUE;
            pair = TuplesKt.to(bool3, bool3);
        }
        boolean zBooleanValue = ((Boolean) pair.component1()).booleanValue();
        boolean zBooleanValue2 = ((Boolean) pair.component2()).booleanValue();
        TimeView timeView = this.mHourView;
        if (timeView != null) {
            timeView.updateFontFeatures(zBooleanValue, this.mDoubleRow);
        }
        TimeView timeView2 = this.mMinuteView;
        if (timeView2 != null) {
            timeView2.updateFontFeatures(zBooleanValue2, this.mDoubleRow);
        }
        TimeView timeView3 = this.mColonView;
        if (timeView3 != null) {
            timeView3.updateFontFeatures(zBooleanValue2, this.mDoubleRow);
        }
        AllInOneUtil allInOneUtil = AllInOneUtil.INSTANCE;
        Paint paint = this.mTextMeasurePaint;
        if (!zBooleanValue && !zBooleanValue2) {
            z = false;
        }
        allInOneUtil.updateFontFeatureSettings(paint, z, this.mDoubleRow);
    }

    public final void updateSignatureText() {
        ClearableEditText clearableEditText = this.mSignatureText;
        if (clearableEditText == null) {
            return;
        }
        Intrinsics.checkNotNull(clearableEditText);
        clearableEditText.setTextSize(0, getDimensionFloat(R.dimen.classic_signature_text_size));
        ClearableEditText clearableEditText2 = this.mSignatureText;
        Intrinsics.checkNotNull(clearableEditText2);
        clearableEditText2.setTypeface(FontUtils.getMiSans(380));
        ClearableEditText clearableEditText3 = this.mSignatureText;
        Intrinsics.checkNotNull(clearableEditText3);
        clearableEditText3.setMaxWidth(getDimen(R.dimen.classic_text_signature_max_width));
    }

    public final void updateTextAreaAndSignaturePosition(Rect rect) {
        FrameLayout frameLayout;
        int dimen = getDimen(R.dimen.miui_all_in_one_text_area_height);
        int dimen2 = getDimen(R.dimen.miui_all_in_one_gap_between_text_area_and_time);
        int dimen3 = getDimen(R.dimen.miui_all_in_one_gap_between_time_and_sigature);
        int i = (rect.top - dimen) - dimen2;
        Log.w(this.TAG, "[FrameDebug] AAR dateTop=" + i + ", timeRect.top=" + rect.top + ", dateAreaH=" + dimen + ", gapDateTime=" + dimen2 + ", dateTopToTime=" + (dimen + dimen2));
        ClassicTextAreaView classicTextAreaView = this.mTextArea;
        if (classicTextAreaView != null) {
            Intrinsics.checkNotNull(classicTextAreaView);
            ViewGroup.LayoutParams layoutParams = classicTextAreaView.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                if (marginLayoutParams.topMargin != i) {
                    marginLayoutParams.topMargin = i;
                    ClassicTextAreaView classicTextAreaView2 = this.mTextArea;
                    Intrinsics.checkNotNull(classicTextAreaView2);
                    classicTextAreaView2.setLayoutParams(layoutParams);
                }
            }
        }
        int i2 = rect.bottom + dimen3;
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        if (((allInOneBaseInfo != null && allInOneBaseInfo.hasSignature()) || this.mEditMode) && (frameLayout = this.mSignatureTextContainer) != null) {
            Intrinsics.checkNotNull(frameLayout);
            ViewGroup.LayoutParams layoutParams2 = frameLayout.getLayoutParams();
            if (layoutParams2 instanceof ConstraintLayout.LayoutParams) {
                ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) layoutParams2;
                if (((ViewGroup.MarginLayoutParams) layoutParams3).topMargin != i2 || layoutParams3.topToTop != 0) {
                    layoutParams3.bottomToBottom = -1;
                    layoutParams3.topToTop = 0;
                    ((ViewGroup.MarginLayoutParams) layoutParams3).bottomMargin = 0;
                    ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = i2;
                    FrameLayout frameLayout2 = this.mSignatureTextContainer;
                    Intrinsics.checkNotNull(frameLayout2);
                    frameLayout2.setLayoutParams(layoutParams2);
                }
            }
        }
        View view = this.mTimeAccessibilityOverlay;
        if (view != null) {
            AccessibilityManager accessibilityManager = this.mAccessibilityManager;
            if (accessibilityManager != null ? accessibilityManager.isTouchExplorationEnabled() : false) {
                ViewGroup.LayoutParams layoutParams4 = view.getLayoutParams();
                if (layoutParams4 instanceof ConstraintLayout.LayoutParams) {
                    ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) layoutParams4;
                    layoutParams5.topToTop = 0;
                    layoutParams5.bottomToBottom = -1;
                    layoutParams5.topToBottom = -1;
                    layoutParams5.bottomToTop = -1;
                    layoutParams5.startToStart = 0;
                    layoutParams5.endToEnd = 0;
                    ((ViewGroup.MarginLayoutParams) layoutParams5).topMargin = rect.top;
                    layoutParams4.height = rect.height();
                    view.setLayoutParams(layoutParams4);
                }
            }
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        if (getWindowVisibility() != 0) {
            return;
        }
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        ClassicTextAreaView classicTextAreaView = this.mTextArea;
        Intrinsics.checkNotNull(classicTextAreaView);
        classicTextAreaView.updateTime();
        String time = DateFormatUtils.formatTime(this.m24HourFormat ? "HHmm" : "hhmm");
        int length = time.length();
        for (int i = 0; i < length; i++) {
            this.mCurrentTimeArray[i] = Integer.parseInt(String.valueOf(time.charAt(i)));
        }
        String dateTime = DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76);
        TimeView timeView = this.mHourView;
        if (timeView != null) {
            timeView.setContentDescription(dateTime);
        }
        View view = this.mTimeAccessibilityOverlay;
        if (view != null) {
            view.setContentDescription(dateTime);
        }
        refreshFullTimeStrings();
        applyAllInOneRect(null);
    }

    public final void updateTransparency(float f) {
        TimeView timeView = this.mHourView;
        if (timeView != null) {
            timeView.setTransparency(f);
        }
        TimeView timeView2 = this.mMinuteView;
        if (timeView2 != null) {
            timeView2.setTransparency(f);
        }
        TimeView timeView3 = this.mColonView;
        if (timeView3 != null) {
            timeView3.setTransparency(f);
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x004a  */
    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        AllInOneBaseInfo allInOneBaseInfo = this.mClockStyleInfo;
        if (allInOneBaseInfo == null) {
            return;
        }
        boolean z = this.mInNotificationState;
        if (z) {
            toNotificationState$default(this, z, 0, 2, null);
        } else {
            Intrinsics.checkNotNull(allInOneBaseInfo);
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            AllInOneUtil.RectComputeResult rectComputeResultComputeLayoutResult = allInOneBaseInfo.computeLayoutResult(context);
            logTimeRectParams("updateViewsLayoutParams", rectComputeResultComputeLayoutResult);
            if (DateFormatUtils.isSystemUI(getContext()) && DeviceConfig.supportVariableFont()) {
                AllInOneBaseInfo allInOneBaseInfo2 = this.mClockStyleInfo;
                Intrinsics.checkNotNull(allInOneBaseInfo2);
                if (allInOneBaseInfo2.getRectParamsOrNull() == null) {
                    applyAllInOneRect(rectComputeResultComputeLayoutResult);
                } else {
                    if ((rectComputeResultComputeLayoutResult != null ? rectComputeResultComputeLayoutResult.getRectParams() : null) != null) {
                        applyMergedRectParams(rectComputeResultComputeLayoutResult);
                    } else {
                        applyAllInOneRect(rectComputeResultComputeLayoutResult);
                    }
                }
            } else {
                applyAllInOneRect(rectComputeResultComputeLayoutResult);
            }
        }
        refreshSignatureLayout();
        updateBlurBlendContainerBounds();
    }
}
