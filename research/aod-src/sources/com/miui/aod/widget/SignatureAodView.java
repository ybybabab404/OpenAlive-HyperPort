package com.miui.aod.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.text.TextUtilsCompat;
import androidx.core.util.Consumer;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.IPreAodView;
import com.miui.aod.components.view.SquareVerticalTextView;
import com.miui.aod.components.view.VerticalTextView;
import com.miui.aod.resource.FontCache;
import com.miui.aod.util.AnimTracer;
import com.miui.aod.util.MiuiPreDrawListener;
import com.miui.aod.util.ShaderUtils;
import com.miui.aod.util.SystemPropertiesUtils;
import com.miui.aod.util.ViewUtilsKt;
import com.miui.aod.util.WindowUtils;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.maml.folme.AnimatedProperty;
import java.util.Locale;
import kotlin.jvm.functions.Function1;
import miui.os.Build;
import miuix.view.animation.SineEaseInInterpolator;

/* JADX INFO: loaded from: classes.dex */
public class SignatureAodView extends FrameLayout implements IPreAodView, IAnimatable {
    public View mCity;
    public View mCityDual;
    public Animator mContentAlphaAnimator;
    public View mDate;
    public View mDateDual;
    public Animator mDateTimeAlphaAnimator;
    public SignatureTimeAndNotificationView mDateTimeContainer;
    public View mDualContainer;
    public View mHorizontalClock;
    public Animator mHueAnimator;
    public ITextSizeListener mITextSizeListener;
    public SquareVerticalTextView mSignatureContent;
    public TextStyleCategoryInfo mTextStyleCategoryInfo;
    public int mTimeMargin;

    public interface ITextSizeListener {
        void onTextSizeChanged(int i);
    }

    public static /* synthetic */ void $r8$lambda$PamIikZg0GaZNIZTIVZOwfaGudA(SignatureTimeAndNotificationView signatureTimeAndNotificationView) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) signatureTimeAndNotificationView.getLayoutParams();
        layoutParams.gravity = 17;
        layoutParams.width = -1;
        layoutParams.topMargin = 15;
        signatureTimeAndNotificationView.setRotation(90.0f);
        signatureTimeAndNotificationView.setGravity(8388611);
    }

    /* JADX INFO: renamed from: $r8$lambda$WhrmZWJjU-eOP4TUOPSH7UBh--U, reason: not valid java name */
    public static /* synthetic */ Boolean m601$r8$lambda$WhrmZWJjUeOP4TUOPSH7UBhU(SignatureAodView signatureAodView, MiuiPreDrawListener miuiPreDrawListener) {
        Rect contentRect = signatureAodView.mSignatureContent.getContentRect();
        if (!contentRect.equals(VerticalTextView.CONTENT_INITIAL_RECT)) {
            if (TextUtils.isEmpty(signatureAodView.mSignatureContent.getText().toString().trim())) {
                signatureAodView.changeTextViewLayoutForEmpty();
            } else if (contentRect.bottom == 0) {
                signatureAodView.changeTextViewLayoutForZero(contentRect);
            } else {
                signatureAodView.changeTextViewLayout(contentRect);
            }
            miuiPreDrawListener.removeListener();
        }
        return Boolean.TRUE;
    }

    public static /* synthetic */ void $r8$lambda$d2KKdm6HawTsUXUWcdhMXAKK1aM(SignatureAodView signatureAodView, SignatureTimeAndNotificationView signatureTimeAndNotificationView) {
        signatureAodView.getClass();
        ObjectAnimator duration = ObjectAnimator.ofFloat(signatureTimeAndNotificationView, AnimatedProperty.PROPERTY_NAME_ALPHA, 0.0f, 1.0f).setDuration(2000L);
        signatureAodView.mDateTimeAlphaAnimator = duration;
        duration.setInterpolator(new SineEaseInInterpolator());
        signatureAodView.mDateTimeAlphaAnimator.addListener(AnimTracer.Companion.animatorListener("Keyguard#AodEnter-ContainerAlpha"));
        signatureAodView.mDateTimeAlphaAnimator.start();
    }

    public static /* synthetic */ void $r8$lambda$dPg5iv98NG2ggaZG0y_FiTgUUaE(View view) {
        if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            if (TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1) {
                layoutParams.setMarginStart(15);
            } else {
                layoutParams.setMarginStart(0);
            }
            view.setLayoutParams(layoutParams);
        }
    }

    public static /* synthetic */ void $r8$lambda$tCC1Ffe3vDoDr6o_6LUDEUh_epU(SignatureAodView signatureAodView, View view) {
        signatureAodView.getClass();
        if (view instanceof TextView) {
            ((TextView) view).setGravity(signatureAodView.mTextStyleCategoryInfo.getPureGravity());
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$zkW-wF1QSXWvwkjK05eWys8la7o, reason: not valid java name */
    public static /* synthetic */ void m602$r8$lambda$zkWwF1QSXWvwkjK05eWys8la7o(SignatureAodView signatureAodView, SignatureTimeAndNotificationView signatureTimeAndNotificationView) {
        signatureAodView.getClass();
        signatureTimeAndNotificationView.setRotation(0.0f);
        signatureTimeAndNotificationView.setGravity(17);
        ((FrameLayout.LayoutParams) signatureTimeAndNotificationView.getLayoutParams()).gravity = signatureAodView.mTextStyleCategoryInfo.getPureGravity() | 80;
        signatureTimeAndNotificationView.setGravity(signatureAodView.mTextStyleCategoryInfo.getPureGravity());
    }

    public SignatureAodView(Context context) {
        this(context, null);
    }

    public SignatureAodView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SignatureAodView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTimeMargin = 0;
        init();
    }

    public static int calculateMaxLinesForHeight(Layout layout, int i) {
        if (i <= 0) {
            return 1;
        }
        int lineCount = layout.getLineCount();
        int i2 = 0;
        int i3 = 0;
        while (i2 < lineCount && layout.getLineBottom(i2) <= i) {
            i3 = i2 + 1;
            i2 = i3;
        }
        if (i3 > 0) {
            return i3;
        }
        return 1;
    }

    private float getLineSpaceDefaultValue() {
        return this.mTextStyleCategoryInfo.isVertical() ? 1.2f : 1.0f;
    }

    private void init() {
        this.mTimeMargin = getContext().getResources().getDimensionPixelSize(R.dimen.signature_time_margin);
    }

    private void resetForAnimation() {
        SignatureTimeAndNotificationView signatureTimeAndNotificationView = this.mDateTimeContainer;
        if (signatureTimeAndNotificationView != null) {
            signatureTimeAndNotificationView.setAlpha(0.0f);
        }
        SquareVerticalTextView squareVerticalTextView = this.mSignatureContent;
        if (squareVerticalTextView != null) {
            squareVerticalTextView.setAlpha(0.0f);
        }
    }

    public void addLayoutChangeListener() {
        addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.miui.aod.widget.SignatureAodView.3
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                Layout layout = SignatureAodView.this.mSignatureContent.getLayout();
                if (layout == null) {
                    return;
                }
                int iCalculateMaxLinesForHeight = SignatureAodView.calculateMaxLinesForHeight(layout, SignatureAodView.this.mSignatureContent.getHeight());
                if (SignatureAodView.this.mSignatureContent.getMaxLines() != iCalculateMaxLinesForHeight) {
                    SignatureAodView.this.mSignatureContent.setMaxLines(iCalculateMaxLinesForHeight);
                    SignatureAodView.this.mSignatureContent.setEllipsize(TextUtils.TruncateAt.END);
                }
                SignatureAodView.this.removeOnLayoutChangeListener(this);
            }
        });
    }

    public final void calculateAndSetTranslation(float f, int i, int i2, int i3) {
        int notificationOffScreenWidth = this.mTextStyleCategoryInfo.isForAodNotification() ? Utils.getNotificationOffScreenWidth(this) : Utils.getStyleItemWidth(getContext());
        int i4 = (((int) ((notificationOffScreenWidth - (i * f)) / 2.0f)) - (notificationOffScreenWidth > i2 ? (notificationOffScreenWidth - i2) / 2 : 0)) - i3;
        if (Utils.isLayoutRtl()) {
            setTranslationX(-i4);
        } else {
            setTranslationX(i4);
        }
        if (this.mTextStyleCategoryInfo.isForAodNotification()) {
            setTranslationY((int) ((((ViewGroup.MarginLayoutParams) getLayoutParams()).height * (1.0f - f)) / 2.0f));
        }
    }

    public final float calculateScale(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        if (this.mTextStyleCategoryInfo.isForAodNotification()) {
            return Math.min(Utils.getNotificationOffScreenWidth(this) / WindowUtils.INSTANCE.getWidthPixel(getContext()), 1.0f);
        }
        int i2 = i * 2;
        return (Utils.getSmallViewWidth(getContext()) - i2) / (marginLayoutParams.width - i2);
    }

    public void changeTextViewLayout(Rect rect) {
        SignatureTimeAndNotificationView signatureTimeAndNotificationView = this.mDateTimeContainer;
        if (signatureTimeAndNotificationView != null) {
            if (signatureTimeAndNotificationView.getRotation() != 90.0f) {
                this.mDateTimeContainer.setTranslationY(-(((this.mSignatureContent.getMeasuredHeight() + rect.top) - rect.bottom) / 2));
                return;
            }
            int measuredWidth = this.mDateTimeContainer.getMeasuredWidth();
            int measuredHeight = this.mDateTimeContainer.getMeasuredHeight();
            int measuredWidth2 = this.mSignatureContent.getMeasuredWidth();
            if (measuredWidth == 0 && measuredHeight == 0) {
                return;
            }
            float f = (measuredWidth2 / 2.0f) - (measuredHeight / 2.0f);
            if (rect.bottom != 0) {
                if (this.mTextStyleCategoryInfo.getPureGravity() == 3) {
                    int i = ((measuredWidth2 - measuredHeight) - rect.right) - this.mTimeMargin;
                    if (i > 0) {
                        f -= i;
                    }
                } else {
                    int i2 = (rect.left - measuredHeight) - this.mTimeMargin;
                    f = -f;
                    if (i2 > 0) {
                        f += i2;
                    }
                }
            } else if (this.mTextStyleCategoryInfo.getPureGravity() != 3) {
                f = -f;
            }
            this.mDateTimeContainer.setTranslationX(f);
        }
    }

    public void changeTextViewLayoutForEmpty() {
        SignatureTimeAndNotificationView signatureTimeAndNotificationView = this.mDateTimeContainer;
        if (signatureTimeAndNotificationView != null) {
            if (signatureTimeAndNotificationView.getRotation() != 90.0f) {
                this.mDateTimeContainer.setTranslationY(((-Math.max(0, this.mSignatureContent.getMeasuredHeight())) / 2) - Math.max(0, this.mDateTimeContainer.getMeasuredHeight()));
                return;
            }
            int measuredWidth = this.mDateTimeContainer.getMeasuredWidth();
            int measuredHeight = this.mDateTimeContainer.getMeasuredHeight();
            int measuredWidth2 = this.mSignatureContent.getMeasuredWidth();
            if (measuredWidth == 0 && measuredHeight == 0) {
                return;
            }
            float f = (measuredWidth2 / 2.0f) - (measuredHeight / 2.0f);
            if (this.mTextStyleCategoryInfo.getPureGravity() == 3) {
                f = -f;
            } else if (this.mTextStyleCategoryInfo.getPureGravity() != 5) {
                f = 0.0f;
            }
            this.mDateTimeContainer.setTranslationX(f);
        }
    }

    public void changeTextViewLayoutForZero(Rect rect) {
        SignatureTimeAndNotificationView signatureTimeAndNotificationView = this.mDateTimeContainer;
        if (signatureTimeAndNotificationView == null || signatureTimeAndNotificationView.getRotation() != 90.0f) {
            return;
        }
        int width = this.mSignatureContent.getWidth();
        int measuredHeight = this.mDateTimeContainer.getMeasuredHeight();
        if (this.mTextStyleCategoryInfo.getPureGravity() == 3) {
            int i = width / 2;
            if (rect.right > i) {
                this.mDateTimeContainer.setTranslationX(i - measuredHeight);
                return;
            }
            return;
        }
        if (this.mTextStyleCategoryInfo.getPureGravity() != 5) {
            this.mDateTimeContainer.setTranslationX(measuredHeight - (width / 2));
            return;
        }
        int i2 = width / 2;
        if (rect.left < i2) {
            this.mDateTimeContainer.setTranslationX(measuredHeight - i2);
        }
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void clearAnimationView() {
        Animator animator = this.mHueAnimator;
        if (animator != null) {
            animator.cancel();
            this.mHueAnimator = null;
        }
        Animator animator2 = this.mDateTimeAlphaAnimator;
        if (animator2 != null) {
            animator2.cancel();
            this.mDateTimeAlphaAnimator = null;
        }
        Animator animator3 = this.mContentAlphaAnimator;
        if (animator3 != null) {
            animator3.cancel();
            this.mContentAlphaAnimator = null;
        }
        resetForAnimation();
    }

    public final void configFixSizeSignatureContent() {
        this.mSignatureContent.setTextSize(1, this.mTextStyleCategoryInfo.getTextSize());
        this.mSignatureContent.setAutoSizeTextTypeWithDefaults(0);
        this.mSignatureContent.setAutoFormatText(false, null);
    }

    public String getShowContent() {
        return this.mSignatureContent.getShowText();
    }

    public VerticalTextView.TextFormat getShowTextFormat() {
        return this.mSignatureContent.getTextFormat();
    }

    public SquareVerticalTextView getSignatureContent() {
        return this.mSignatureContent;
    }

    public final boolean hasEmoji() {
        String showContent = getShowContent();
        int iCharCount = 0;
        while (iCharCount < showContent.length()) {
            int iCodePointAt = Character.codePointAt(showContent, iCharCount);
            if (Utils.isEmojiCharacter(iCodePointAt)) {
                return true;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return false;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mSignatureContent = (SquareVerticalTextView) findViewById(R.id.signature_content_tv);
        this.mDateTimeContainer = (SignatureTimeAndNotificationView) findViewById(R.id.clock_horizontal_container);
        this.mHorizontalClock = findViewById(R.id.clock_horizontal);
        this.mDate = findViewById(R.id.date);
        this.mDateDual = findViewById(R.id.date_dual);
        this.mCity = findViewById(R.id.city);
        this.mCityDual = findViewById(R.id.city2);
        this.mDualContainer = findViewById(R.id.signature_dual_time);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        TextStyleCategoryInfo textStyleCategoryInfo = this.mTextStyleCategoryInfo;
        if (textStyleCategoryInfo == null || textStyleCategoryInfo.isVertical()) {
            return;
        }
        SignatureTimeAndNotificationView signatureTimeAndNotificationView = this.mDateTimeContainer;
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + (signatureTimeAndNotificationView != null ? signatureTimeAndNotificationView.getMeasuredHeight() : 0) + this.mTimeMargin);
    }

    public void set24GradientColor(int i) {
        TextStyleCategoryInfo textStyleCategoryInfo = this.mTextStyleCategoryInfo;
        if (textStyleCategoryInfo == null || !textStyleCategoryInfo.isChangeColorAlongTime() || i == this.mSignatureContent.getDrawableMask()) {
            return;
        }
        this.mSignatureContent.setDrawableMask(i, 0);
        this.mSignatureContent.invalidate();
    }

    public void setTextSizeChangedListener(ITextSizeListener iTextSizeListener) {
        this.mITextSizeListener = iTextSizeListener;
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void startAnimation(final AnimatorListenerAdapter animatorListenerAdapter) {
        resetForAnimation();
        ViewUtil.doIfNotNull(this.mDateTimeContainer, new Consumer() { // from class: com.miui.aod.widget.SignatureAodView$$ExternalSyntheticLambda6
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                SignatureAodView.$r8$lambda$d2KKdm6HawTsUXUWcdhMXAKK1aM(this.f$0, (SignatureTimeAndNotificationView) obj);
            }
        });
        if (!hasEmoji()) {
            this.mSignatureContent.setDrawableMask(this.mTextStyleCategoryInfo.getClockMask());
            this.mHueAnimator = ShaderUtils.hueAnimateIn(new AnimatorListenerAdapter() { // from class: com.miui.aod.widget.SignatureAodView.2
                public final AnimTracer.Manual tracer = AnimTracer.manual("Keyguard#AodEnter-HueIn");

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    AnimatorListenerAdapter animatorListenerAdapter2 = animatorListenerAdapter;
                    if (animatorListenerAdapter2 != null) {
                        animatorListenerAdapter2.onAnimationEnd(animator);
                    }
                    SignatureAodView.this.mSignatureContent.setDrawableMask(SignatureAodView.this.mTextStyleCategoryInfo.getClockMask(), 0);
                    this.tracer.end();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    AnimatorListenerAdapter animatorListenerAdapter2 = animatorListenerAdapter;
                    if (animatorListenerAdapter2 != null) {
                        animatorListenerAdapter2.onAnimationStart(animator);
                    }
                    this.tracer.begin();
                }
            }, this.mSignatureContent);
            return;
        }
        ObjectAnimator duration = ObjectAnimator.ofFloat(this.mSignatureContent, AnimatedProperty.PROPERTY_NAME_ALPHA, 0.0f, 1.0f).setDuration(1500L);
        this.mContentAlphaAnimator = duration;
        duration.addListener(AnimTracer.Companion.animatorListener("Keyguard#AodEnter-SignatureAlpha"));
        if (animatorListenerAdapter != null) {
            this.mContentAlphaAnimator.addListener(animatorListenerAdapter);
        }
        this.mContentAlphaAnimator.start();
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        if (styleInfo instanceof TextStyleCategoryInfo) {
            this.mTextStyleCategoryInfo = (TextStyleCategoryInfo) styleInfo;
            boolean zIsDateAndTimeSwitchOn = styleInfo.isDateAndTimeSwitchOn();
            if (!this.mTextStyleCategoryInfo.canShowExtraInfo() || i == 2) {
                ViewUtil.setVisibility(this.mDateTimeContainer, 4);
            }
            ViewUtil.setVisibility(this.mHorizontalClock, zIsDateAndTimeSwitchOn ? 0 : 8);
            ViewUtil.setVisibility(this.mDate, zIsDateAndTimeSwitchOn ? 0 : 8);
            ViewUtil.setVisibility(this.mCity, zIsDateAndTimeSwitchOn ? 0 : 8);
            ViewUtil.setVisibility(this.mDateDual, zIsDateAndTimeSwitchOn ? 0 : 8);
            ViewUtil.setVisibility(this.mCityDual, zIsDateAndTimeSwitchOn ? 0 : 8);
            this.mSignatureContent.setVerticalMode(this.mTextStyleCategoryInfo.isVertical());
            ViewUtil.doIfNotNull(this.mDateTimeContainer, new Consumer() { // from class: com.miui.aod.widget.SignatureAodView$$ExternalSyntheticLambda0
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ((SignatureTimeAndNotificationView) obj).setV(false);
                }
            });
            this.mSignatureContent.setIsSquare(true);
            this.mSignatureContent.setGravity(this.mTextStyleCategoryInfo.getPureGravity());
            if (this.mTextStyleCategoryInfo.getFontWeight() <= 0) {
                this.mSignatureContent.setTypeface(Typeface.defaultFromStyle(0));
            } else if (Build.IS_INTERNATIONAL_BUILD) {
                this.mSignatureContent.setTypeface(FontCache.getInstance().getTypeface("system/fonts/Roboto-Regular.ttf", "'wght'" + this.mTextStyleCategoryInfo.getFontWeight()));
            } else {
                String str = SystemPropertiesUtils.INSTANCE.get("ro.miui.ui.font.mi_font_path", "system/fonts/MiLanProVF.ttf");
                this.mSignatureContent.setTypeface(FontCache.getInstance().getTypeface(str, "'wght'" + this.mTextStyleCategoryInfo.getFontWeight()));
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            this.mSignatureContent.setLineSpacing(0.0f, this.mTextStyleCategoryInfo.getLineSpacing() == Float.MAX_VALUE ? getLineSpaceDefaultValue() : this.mTextStyleCategoryInfo.getLineSpacing());
            if (this.mTextStyleCategoryInfo.isVertical()) {
                ViewUtil.doIfNotNull(this.mDateTimeContainer, new Consumer() { // from class: com.miui.aod.widget.SignatureAodView$$ExternalSyntheticLambda3
                    @Override // androidx.core.util.Consumer
                    public final void accept(Object obj) {
                        SignatureAodView.$r8$lambda$PamIikZg0GaZNIZTIVZOwfaGudA((SignatureTimeAndNotificationView) obj);
                    }
                });
                this.mSignatureContent.setCJKVerticalSpace(0.0f, this.mTextStyleCategoryInfo.getLetterSpacing() + 1.0f);
                if (styleInfo.isDateAndTimeSwitchOn() || styleInfo.isBatterySwitchOn() || styleInfo.isNotificationSwitchOn()) {
                    int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.signature_padding);
                    int pureGravity = this.mTextStyleCategoryInfo.getPureGravity();
                    if (pureGravity == 1) {
                        this.mSignatureContent.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
                    } else if (pureGravity == 3) {
                        this.mSignatureContent.setPadding(0, 0, dimensionPixelSize, 0);
                    } else if (pureGravity == 5) {
                        this.mSignatureContent.setPadding(dimensionPixelSize, 0, 0, 0);
                    }
                } else {
                    this.mSignatureContent.setPadding(0, 0, 0, 0);
                }
                View view = this.mDualContainer;
                if (view != null && (view.getLayoutParams() instanceof LinearLayout.LayoutParams)) {
                    ((LinearLayout.LayoutParams) this.mDualContainer.getLayoutParams()).gravity = 8388611;
                }
                ViewUtil.doIfNotNull(this.mHorizontalClock, new Consumer() { // from class: com.miui.aod.widget.SignatureAodView$$ExternalSyntheticLambda4
                    @Override // androidx.core.util.Consumer
                    public final void accept(Object obj) {
                        SignatureAodView.$r8$lambda$dPg5iv98NG2ggaZG0y_FiTgUUaE((View) obj);
                    }
                });
            } else {
                ViewUtil.doIfNotNull(this.mDateTimeContainer, new Consumer() { // from class: com.miui.aod.widget.SignatureAodView$$ExternalSyntheticLambda1
                    @Override // androidx.core.util.Consumer
                    public final void accept(Object obj) {
                        SignatureAodView.m602$r8$lambda$zkWwF1QSXWvwkjK05eWys8la7o(this.f$0, (SignatureTimeAndNotificationView) obj);
                    }
                });
                ViewUtil.doIfNotNull(this.mCity, new Consumer() { // from class: com.miui.aod.widget.SignatureAodView$$ExternalSyntheticLambda2
                    @Override // androidx.core.util.Consumer
                    public final void accept(Object obj) {
                        SignatureAodView.$r8$lambda$tCC1Ffe3vDoDr6o_6LUDEUh_epU(this.f$0, (View) obj);
                    }
                });
                this.mSignatureContent.setPadding(0, 0, 0, 0);
                this.mSignatureContent.setLetterSpacing(this.mTextStyleCategoryInfo.getLetterSpacing());
                if (this.mTextStyleCategoryInfo.getTextSize() != -1.0f || this.mTextStyleCategoryInfo.getTextFormat() == null) {
                    addLayoutChangeListener();
                } else {
                    this.mSignatureContent.setMaxLines(this.mTextStyleCategoryInfo.getTextFormat().lineCount);
                    this.mSignatureContent.setEllipsize(TextUtils.TruncateAt.END);
                }
                View view2 = this.mDualContainer;
                if (view2 != null && (view2.getLayoutParams() instanceof LinearLayout.LayoutParams)) {
                    ((LinearLayout.LayoutParams) this.mDualContainer.getLayoutParams()).gravity = this.mTextStyleCategoryInfo.getPureGravity();
                }
            }
            this.mSignatureContent.setDrawableMask(this.mTextStyleCategoryInfo.getClockMask(), 0);
            if (i != 1) {
                if (i == 2) {
                    int dimensionPixelOffset = getContext().getResources().getDimensionPixelOffset(R.dimen.aod_textstyle_width);
                    int dimensionPixelOffset2 = getContext().getResources().getDimensionPixelOffset(R.dimen.aod_signature_notification_margin);
                    marginLayoutParams.width = (dimensionPixelOffset2 * 2) + dimensionPixelOffset;
                    marginLayoutParams.height = getContext().getResources().getDimensionPixelOffset(R.dimen.aod_textstyle_height);
                    float fCalculateScale = calculateScale(marginLayoutParams, dimensionPixelOffset2);
                    if (!this.mTextStyleCategoryInfo.isForAodNotification()) {
                        marginLayoutParams.topMargin = (int) ((Utils.getSmallViewHeight(getContext()) - (marginLayoutParams.height * fCalculateScale)) / 2.0f);
                    }
                    calculateAndSetTranslation(fCalculateScale, dimensionPixelOffset, marginLayoutParams.width, dimensionPixelOffset2);
                    setPadding(dimensionPixelOffset2, 0, dimensionPixelOffset2, 0);
                    if (this.mTextStyleCategoryInfo.getTextSize() == -1.0f) {
                        this.mSignatureContent.setAutoFormatText(true, this.mTextStyleCategoryInfo.getTextFormat());
                        this.mSignatureContent.setAutoSizeTextTypeUniformWithConfiguration(8, (int) TextStyleCategoryInfo.MAX_TEXTSIZE, 1, 1);
                    } else {
                        configFixSizeSignatureContent();
                    }
                    this.mSignatureContent.setScale(fCalculateScale);
                    this.mSignatureContent.setText(this.mTextStyleCategoryInfo.getContent().replaceAll("\\s+$", ""));
                    return;
                }
                if (i != 3 && i != 5) {
                    return;
                }
            }
            marginLayoutParams.width = getContext().getResources().getDimensionPixelOffset(R.dimen.aod_textstyle_width);
            marginLayoutParams.height = getContext().getResources().getDimensionPixelOffset(R.dimen.aod_textstyle_height);
            marginLayoutParams.topMargin = Utils.dp2Px(20);
            if (this.mTextStyleCategoryInfo.getTextSize() == -1.0f && this.mTextStyleCategoryInfo.getTextFormat() != null) {
                this.mSignatureContent.setAutoFormatText(true, this.mTextStyleCategoryInfo.getTextFormat());
                this.mSignatureContent.setAutoSizeTextTypeUniformWithConfiguration(15, (int) TextStyleCategoryInfo.MAX_TEXTSIZE, 1, 1);
                if (!TextUtils.isEmpty(this.mTextStyleCategoryInfo.getContent())) {
                    this.mSignatureContent.setText(this.mTextStyleCategoryInfo.getContent().replaceAll("\\s+$", ""));
                }
                this.mSignatureContent.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.miui.aod.widget.SignatureAodView.1
                    @Override // android.view.View.OnLayoutChangeListener
                    public void onLayoutChange(View view3, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                        SignatureAodView.this.mSignatureContent.removeOnLayoutChangeListener(this);
                        SignatureAodView.this.mTextStyleCategoryInfo.setTextSize(Utils.px2Dp((int) SignatureAodView.this.mSignatureContent.getTextSize()));
                        SignatureAodView.this.configFixSizeSignatureContent();
                        if (SignatureAodView.this.mITextSizeListener != null) {
                            SignatureAodView.this.mITextSizeListener.onTextSizeChanged((int) SignatureAodView.this.mTextStyleCategoryInfo.getTextSize());
                        }
                    }
                });
                return;
            }
            if (this.mTextStyleCategoryInfo.getTextSize() == -1.0f) {
                this.mTextStyleCategoryInfo.setTextSize(46.0f);
            }
            configFixSizeSignatureContent();
            if (!TextUtils.isEmpty(this.mTextStyleCategoryInfo.getContent())) {
                this.mSignatureContent.setText(this.mTextStyleCategoryInfo.getContent().replaceAll("\\s+$", ""));
            }
            ViewUtilsKt.doOnMiuiPreDraw(this, new Function1() { // from class: com.miui.aod.widget.SignatureAodView$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return SignatureAodView.m601$r8$lambda$WhrmZWJjUeOP4TUOPSH7UBhU(this.f$0, (MiuiPreDrawListener) obj);
                }
            });
        }
    }
}
