package com.miui.aod.components.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowInsets;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.DrawableData;
import com.miui.aod.components.DrawableGravityData;
import com.miui.aod.components.widget.ColorSelectView;
import com.miui.aod.components.widget.TextAlignStyleSelectView;
import com.miui.aod.components.widget.WidgetSwitcher;
import com.miui.aod.icu.ICUManager;
import com.miui.aod.resource.AodDrawables;
import com.miui.aod.resource.ImagePool;
import com.miui.aod.util.MiLengthFilter;
import com.miui.aod.widget.FontWeightAdjustView;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.ISignatureAodClock;
import com.miui.aod.widget.SignatureAodView;
import com.miui.aod.widget.SignatureClock;
import com.miui.aod.widget.SlidingButtonWithMainSubTitle;
import com.miui.aod.widget.SunSelector;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import miui.os.Build;
import miuix.androidbasewidget.widget.SeekBar;

/* JADX INFO: loaded from: classes.dex */
public class TextStyleSelectView extends BaseStyleSelectView implements View.OnClickListener, SignatureAodView.ITextSizeListener {
    private static final float HORIZONTAL_LETTER_SPACE_DEFAULT = 1.0f;
    private static final float HORIZONTAL_LETTER_SPACE_OFFSET = 0.0f;
    private static final float VERTICAL_LETTER_SPACE_DEFAULT = 0.0f;
    public static final float VERTICAL_LETTER_SPACE_OFFSET = 1.0f;
    private SlidingButtonWithMainSubTitle mChangeColorAlongTimeSb;
    private ColorSelectView mColorSelectView;
    private TextView mExceedLengthAlertTv;
    private SeekBar mFontThicknessSeekBar;
    private FontWeightAdjustView mFontWeightAdjustView;
    private WidgetSwitcher mFontWidgetSwitcher;
    private final Handler mHandler;
    private EditText mInputEt;
    private ImageView mLetterSpaceAddIv;
    private ImageView mLetterSpaceDelIv;
    private float mLetterSpaceValue;
    private float mLetterSpaceValueTmp;
    private TextView mLetterSpaceValueTv;
    private ImageView mLineSpaceAddIv;
    private ImageView mLineSpaceDelIv;
    private float mLineSpaceValue;
    private float mLineSpaceValueTmp;
    private TextView mLineSpaceValueTv;
    NumberFormat mNF;
    private SignatureAodView mSignatureAodView;
    private VerticalTextView mSignatureTextView;
    private TextAlignStyleSelectView mTextAlignStyleSelectView;
    private SeekBar mTextSizeSeekBar;
    private TextStyleCategoryInfo mTextStyleCategoryInfo;
    private final Runnable mUpdateTask;
    int quantity_letter;
    int quantity_line;
    private ScheduledExecutorService scheduledExecutor;

    public static /* synthetic */ void $r8$lambda$9d6HUz1RGu7qW5bN1mEhh8zxv4o(TextStyleSelectView textStyleSelectView, CompoundButton compoundButton, boolean z) {
        textStyleSelectView.getClass();
        if (z) {
            textStyleSelectView.setFontWeight(ICUManager.INSTANCE.getWeightInJapan(), false);
        } else {
            textStyleSelectView.setFontWeight(ICUManager.INSTANCE.getThickInJapan(), false);
        }
        textStyleSelectView.mFontWidgetSwitcher.initContentDescrition();
    }

    public static /* synthetic */ boolean $r8$lambda$LPOrMOkvK8yi16y3QHmR8b_T3Oo(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
        }
        if (motionEvent.getAction() == 1) {
            view.getParent().requestDisallowInterceptTouchEvent(false);
        }
        return false;
    }

    public static /* synthetic */ WindowInsetsCompat $r8$lambda$Q4BzxNcEfD5jeVnHWNrP4l7bDF4(TextStyleSelectView textStyleSelectView, View view, WindowInsetsCompat windowInsetsCompat) {
        textStyleSelectView.getClass();
        boolean zIsVisible = windowInsetsCompat.isVisible(WindowInsetsCompat.Type.ime());
        int i = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.ime()).bottom;
        int i2 = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom;
        if (zIsVisible) {
            textStyleSelectView.setSelectContainerViewHeight(((i + textStyleSelectView.mInputEt.getHeight()) - i2) + textStyleSelectView.calculateAboveEditTextVisibleHeight());
            return windowInsetsCompat;
        }
        textStyleSelectView.resetSelectContainerViewHeight();
        return windowInsetsCompat;
    }

    /* JADX INFO: renamed from: $r8$lambda$aG62p5FEXK2dx_5Txa2-RfaDcBY, reason: not valid java name */
    public static /* synthetic */ void m274$r8$lambda$aG62p5FEXK2dx_5Txa2RfaDcBY(TextStyleSelectView textStyleSelectView) {
        textStyleSelectView.updateColorSelectorState(textStyleSelectView.mChangeColorAlongTimeSb.getChecked());
        textStyleSelectView.mStyleInfo.setChangeColorAlongTime(textStyleSelectView.mChangeColorAlongTimeSb.getChecked());
    }

    public static /* synthetic */ void $r8$lambda$bnVy34ck9_mcDHZZr7KmOcS8OxI(TextStyleSelectView textStyleSelectView, int i) {
        textStyleSelectView.getClass();
        textStyleSelectView.setFontWeight(Utils.convertLevel2Weight(i), true);
    }

    public static /* synthetic */ boolean $r8$lambda$oBTBwzBrtqQ9odU1gg64grrrSrA(TextStyleSelectView textStyleSelectView, View view, MotionEvent motionEvent) {
        textStyleSelectView.getClass();
        if (motionEvent.getActionMasked() == 0) {
            view.setAlpha(0.4f);
            textStyleSelectView.scheduleTouch(view);
            return false;
        }
        if (motionEvent.getActionMasked() != 1 && motionEvent.getActionMasked() != 3) {
            return false;
        }
        view.setAlpha(1.0f);
        textStyleSelectView.stopTouch();
        return false;
    }

    public static /* synthetic */ void $r8$lambda$rMf7xJUke9k81dQbdxBV6W_jWsg(final TextStyleSelectView textStyleSelectView, final View view) {
        textStyleSelectView.getClass();
        view.post(new Runnable() { // from class: com.miui.aod.components.view.TextStyleSelectView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.changeViewStep(view, 0.1f);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$tRQWf2X6gT0wyVTBxzrpwpEWHzE(TextStyleSelectView textStyleSelectView, ViewGroup viewGroup, CompoundButton compoundButton, final boolean z) {
        textStyleSelectView.mChangeColorAlongTimeSb.initContentdescription();
        textStyleSelectView.mTextStyleCategoryInfo.setChangeColorAlongTime(z);
        SunSelector.updateSunRiseTime(viewGroup.getContext());
        textStyleSelectView.scheduleUpdateTask(true);
        textStyleSelectView.mChangeColorAlongTimeSb.post(new Runnable() { // from class: com.miui.aod.components.view.TextStyleSelectView.1
            @Override // java.lang.Runnable
            public void run() {
                TextStyleSelectView.this.changeColorSelectorState(z);
            }
        });
    }

    /* JADX INFO: renamed from: $r8$lambda$xPgzuQ74-dK8Ise8xUrDs2v-B-M, reason: not valid java name */
    public static /* synthetic */ void m276$r8$lambda$xPgzuQ74dK8Ise8xUrDs2vBM(TextStyleSelectView textStyleSelectView, IAodClock iAodClock) {
        ISignatureAodClock iSignatureAodClock = (ISignatureAodClock) iAodClock;
        textStyleSelectView.mTextStyleCategoryInfo.setShowedContent(iSignatureAodClock.getShowedContent());
        textStyleSelectView.mTextStyleCategoryInfo.setTextFormat(iSignatureAodClock.getTextFormat());
    }

    public TextStyleSelectView(Context context) {
        super(context);
        this.mNF = NumberFormat.getNumberInstance();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mUpdateTask = new Runnable() { // from class: com.miui.aod.components.view.TextStyleSelectView$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updateStyleInfoForPreview();
            }
        };
    }

    private int calculateAboveEditTextVisibleHeight() {
        int[] iArr = new int[2];
        this.mInputEt.getLocationInWindow(iArr);
        int i = iArr[1];
        int selectContainerViewTopLocation = getSelectContainerViewTopLocation();
        if (selectContainerViewTopLocation > 0) {
            return Math.max(i - selectContainerViewTopLocation, 0);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeColorSelectorState(boolean z) {
        if (z) {
            this.mColorSelectView.setAlpha(0.4f);
            this.mColorSelectView.setEnabled(false);
        } else {
            this.mColorSelectView.setAlpha(1.0f);
            this.mColorSelectView.setEnabled(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeViewStep(View view, float f) {
        if (view.getId() == R.id.linespace_add) {
            float f2 = this.mLineSpaceValue;
            if (f2 + f <= 5.0f) {
                this.mLineSpaceValue = f2 + f;
                updateLineSpaceValue();
            }
        } else if (view.getId() == R.id.linespace_del) {
            float f3 = this.mLineSpaceValue;
            if (f3 - f >= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
                this.mLineSpaceValue = f3 - f;
                updateLineSpaceValue();
            }
        } else if (view.getId() == R.id.letterspace_add) {
            float f4 = this.mLetterSpaceValue;
            if (f4 + f <= 3.0f) {
                this.mLetterSpaceValue = f4 + f;
                updateLetterSpaceValue();
            }
        } else if (view.getId() == R.id.letterspace_del) {
            float f5 = this.mLetterSpaceValue;
            if (f5 - f >= -1.0f) {
                this.mLetterSpaceValue = f5 - f;
                updateLetterSpaceValue();
            }
        }
        scheduleUpdateTask(false);
    }

    private int findDrawableById(List<DrawableData> list, String str) {
        if (list == null) {
            return -1;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i).mName.equals(str)) {
                return i;
            }
        }
        return -1;
    }

    private String getFormattedValue(float f) {
        return new DecimalFormat("0.0").format(f);
    }

    private float getLineSpaceDefaultValue() {
        return this.mTextStyleCategoryInfo.isVertical() ? 1.2f : 1.0f;
    }

    private void initChangeSpaceBtn(View view) {
        view.setOnClickListener(this);
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.aod.components.view.TextStyleSelectView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return TextStyleSelectView.$r8$lambda$oBTBwzBrtqQ9odU1gg64grrrSrA(this.f$0, view2, motionEvent);
            }
        });
    }

    private void initWindowInsetsListener() {
        ViewCompat.setOnApplyWindowInsetsListener(this.mRootView, new OnApplyWindowInsetsListener() { // from class: com.miui.aod.components.view.TextStyleSelectView$$ExternalSyntheticLambda2
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return TextStyleSelectView.$r8$lambda$Q4BzxNcEfD5jeVnHWNrP4l7bDF4(this.f$0, view, windowInsetsCompat);
            }
        });
    }

    private boolean isImeVisible() {
        WindowInsets rootWindowInsets;
        EditText editText = this.mInputEt;
        if (editText == null || (rootWindowInsets = editText.getRootWindowInsets()) == null) {
            return false;
        }
        return rootWindowInsets.isVisible(WindowInsets.Type.ime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onColorSelect(int i, DrawableData drawableData) {
        String colorName = this.mStyleInfo.getColorName();
        if (TextUtils.isEmpty(drawableData.mName) || drawableData.mName.equals(colorName)) {
            return;
        }
        this.mStyleInfo.setColorName(drawableData.mName);
        scheduleUpdateTask(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTextAlignSelect(int i, DrawableGravityData drawableGravityData) {
        int gravity = this.mTextStyleCategoryInfo.getGravity();
        int i2 = drawableGravityData.gravity;
        if (gravity != i2) {
            if ((gravity & 512) != (i2 & 512)) {
                float f = this.mLineSpaceValue;
                this.mLineSpaceValue = this.mLineSpaceValueTmp;
                this.mLineSpaceValueTmp = f;
                float f2 = this.mLetterSpaceValue;
                this.mLetterSpaceValue = this.mLetterSpaceValueTmp;
                this.mLetterSpaceValueTmp = f2;
            }
            ((TextStyleCategoryInfo) this.mStyleInfo).setGravity(i2);
            scheduleUpdateTask(false);
            updateLineSpaceValue();
            updateLetterSpaceValue();
        }
    }

    private void scheduleTouch(final View view) {
        if (this.scheduledExecutor == null) {
            this.scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        }
        this.scheduledExecutor.scheduleWithFixedDelay(new Runnable() { // from class: com.miui.aod.components.view.TextStyleSelectView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                TextStyleSelectView.$r8$lambda$rMf7xJUke9k81dQbdxBV6W_jWsg(this.f$0, view);
            }
        }, 500L, 100L, TimeUnit.MILLISECONDS);
    }

    private void scheduleUpdateTask(boolean z) {
        this.mHandler.removeCallbacks(this.mUpdateTask);
        if (z) {
            this.mHandler.postDelayed(this.mUpdateTask, 300L);
        } else {
            this.mUpdateTask.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFontWeight(int i, boolean z) {
        if (i != this.mTextStyleCategoryInfo.getFontWeight()) {
            this.mTextStyleCategoryInfo.setFontWeight(i);
            scheduleUpdateTask(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTextSize(float f, boolean z) {
        float textSize = this.mTextStyleCategoryInfo.getTextSize();
        if (f < 14.0f || f == textSize) {
            return;
        }
        this.mTextStyleCategoryInfo.setTextSize(f);
        scheduleUpdateTask(z);
    }

    private float solveNegativeZero(float f) {
        if (f <= -0.01f || f >= 0.01f) {
            return f;
        }
        return 0.0f;
    }

    private void stopTouch() {
        ScheduledExecutorService scheduledExecutorService = this.scheduledExecutor;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
            this.scheduledExecutor = null;
        }
    }

    private void updateColorSelectorState(boolean z) {
        if (z) {
            getPresenter().getIAodClock().updateTime(DateFormat.is24HourFormat(this.mContext));
        }
        changeColorSelectorState(z);
    }

    private void updateLetterSpaceValue() {
        float fSolveNegativeZero = solveNegativeZero(this.mLetterSpaceValue);
        this.mLetterSpaceValue = fSolveNegativeZero;
        this.mLetterSpaceValueTv.setText(this.mNF.format(fSolveNegativeZero));
        this.mLetterSpaceAddIv.setContentDescription(this.mContext.getResources().getQuantityString(R.plurals.character_pitch_plus, this.quantity_letter, this.mNF.format(this.mLetterSpaceValue)));
        this.mLetterSpaceDelIv.setContentDescription(this.mContext.getResources().getQuantityString(R.plurals.character_pitch_minus, this.quantity_letter, this.mNF.format(this.mLetterSpaceValue)));
        this.mSignatureAodView.addLayoutChangeListener();
        if (this.mTextStyleCategoryInfo.isVertical()) {
            this.mSignatureTextView.setCJKVerticalSpace(0.0f, this.mLetterSpaceValue);
        } else {
            this.mSignatureTextView.setLetterSpacing(this.mLetterSpaceValue);
        }
        TextStyleCategoryInfo textStyleCategoryInfo = this.mTextStyleCategoryInfo;
        textStyleCategoryInfo.setLetterSpacing(this.mLetterSpaceValue - (textStyleCategoryInfo.isVertical() ? 1.0f : 0.0f));
    }

    private void updateLineSpaceValue() {
        this.mTextStyleCategoryInfo.setLineSpacing(this.mLineSpaceValue);
        this.mLineSpaceValueTv.setText(this.mNF.format(this.mLineSpaceValue));
        this.mLineSpaceAddIv.setContentDescription(this.mContext.getResources().getQuantityString(R.plurals.line_spacing_puls, this.quantity_line, this.mNF.format(this.mLineSpaceValue)));
        this.mLineSpaceDelIv.setContentDescription(this.mContext.getResources().getQuantityString(R.plurals.lines_pacing_minus, this.quantity_line, this.mNF.format(this.mLineSpaceValue)));
        this.mSignatureAodView.addLayoutChangeListener();
        this.mSignatureTextView.setLineSpacing(0.0f, this.mLineSpaceValue);
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void applyStyle() {
        final IAodClock iAodClock = getPresenter().getIAodClock();
        if (iAodClock instanceof SignatureClock) {
            SquareVerticalTextView signatureContent = ((SignatureClock) iAodClock).getView().getSignatureContent();
            this.mSignatureTextView = signatureContent;
            if (signatureContent != null) {
                signatureContent.post(new Runnable() { // from class: com.miui.aod.components.view.TextStyleSelectView$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        TextStyleSelectView.m276$r8$lambda$xPgzuQ74dK8Ise8xUrDs2vBM(this.f$0, iAodClock);
                    }
                });
            }
        }
        super.applyStyle();
    }

    public void fitFontScale() {
        float fontScale = Utils.getFontScale(this.mContext);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.aod_text_style_space_width_default);
        double d = fontScale;
        if (d > 1.3d) {
            if (d < 1.5d) {
                dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.aod_text_style_space_width_font1_3);
            } else {
                dimensionPixelSize = d < 1.8d ? this.mContext.getResources().getDimensionPixelSize(R.dimen.aod_text_style_space_width_font1_5) : this.mContext.getResources().getDimensionPixelSize(R.dimen.aod_text_style_space_width_font1_8);
            }
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mLetterSpaceValueTv.getLayoutParams();
        layoutParams.width = dimensionPixelSize;
        this.mLetterSpaceValueTv.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mLineSpaceValueTv.getLayoutParams();
        layoutParams2.width = dimensionPixelSize;
        this.mLineSpaceValueTv.setLayoutParams(layoutParams2);
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void inflateStyleSelectPanelView(final ViewGroup viewGroup) {
        this.mLayoutInflater.inflate(R.layout.text_style_select_layout, viewGroup, true);
        ColorSelectView colorSelectView = (ColorSelectView) this.mRootView.findViewById(R.id.color_select_view);
        this.mColorSelectView = colorSelectView;
        colorSelectView.setOnItemClickListener(new ColorSelectView.OnItemClickListener() { // from class: com.miui.aod.components.view.TextStyleSelectView$$ExternalSyntheticLambda7
            @Override // com.miui.aod.components.widget.ColorSelectView.OnItemClickListener
            public final void onItemClick(int i, DrawableData drawableData) {
                this.f$0.onColorSelect(i, drawableData);
            }
        });
        TextAlignStyleSelectView textAlignStyleSelectView = (TextAlignStyleSelectView) this.mRootView.findViewById(R.id.text_align_selector);
        this.mTextAlignStyleSelectView = textAlignStyleSelectView;
        textAlignStyleSelectView.setDrawableData(ImagePool.sAlignTypeDrawableData);
        this.mTextAlignStyleSelectView.setOnItemClickListener(new TextAlignStyleSelectView.OnItemClickListener() { // from class: com.miui.aod.components.view.TextStyleSelectView$$ExternalSyntheticLambda8
            @Override // com.miui.aod.components.widget.TextAlignStyleSelectView.OnItemClickListener
            public final void onItemClick(int i, DrawableGravityData drawableGravityData) {
                this.f$0.onTextAlignSelect(i, drawableGravityData);
            }
        });
        this.mChangeColorAlongTimeSb = (SlidingButtonWithMainSubTitle) this.mRootView.findViewById(R.id.changecolor_switch_container);
        this.mChangeColorAlongTimeSb.setOnCheckChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.components.view.TextStyleSelectView$$ExternalSyntheticLambda9
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                TextStyleSelectView.$r8$lambda$tRQWf2X6gT0wyVTBxzrpwpEWHzE(this.f$0, viewGroup, compoundButton, z);
            }
        });
        this.mChangeColorAlongTimeSb.initContentdescription();
        EditText editText = (EditText) this.mRootView.findViewById(R.id.input_et);
        this.mInputEt = editText;
        editText.addTextChangedListener(new TextWatcher() { // from class: com.miui.aod.components.view.TextStyleSelectView.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    TextStyleSelectView.this.mApplyButton.setEnabled(false);
                } else {
                    TextStyleSelectView.this.mApplyButton.setEnabled(true);
                }
                ((TextStyleCategoryInfo) TextStyleSelectView.this.mStyleInfo).setContent(editable.toString());
                TextStyleSelectView.this.updateStyleInfoForPreview();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.mInputEt.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.aod.components.view.TextStyleSelectView$$ExternalSyntheticLambda10
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return TextStyleSelectView.$r8$lambda$LPOrMOkvK8yi16y3QHmR8b_T3Oo(view, motionEvent);
            }
        });
        this.mInputEt.setFilters(new InputFilter[]{new MiLengthFilter(60) { // from class: com.miui.aod.components.view.TextStyleSelectView.3
            @Override // com.miui.aod.util.MiLengthFilter
            public void onExccedLimit(boolean z) {
                if (!z) {
                    TextStyleSelectView.this.mExceedLengthAlertTv.setVisibility(8);
                    return;
                }
                TextStyleSelectView.this.mExceedLengthAlertTv.setVisibility(0);
                TextStyleSelectView.this.mInputEt.clearFocus();
                TextStyleSelectView textStyleSelectView = TextStyleSelectView.this;
                Utils.hideSoftInput(textStyleSelectView.mContext, textStyleSelectView.mInputEt);
            }
        }});
        this.mExceedLengthAlertTv = (TextView) this.mRootView.findViewById(R.id.excceed_length_alert_tv);
        SeekBar seekBar = (SeekBar) this.mRootView.findViewById(R.id.textsize_sb);
        this.mTextSizeSeekBar = seekBar;
        seekBar.setContentDescription(this.mContext.getResources().getString(R.string.text_size));
        this.mTextSizeSeekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() { // from class: com.miui.aod.components.view.TextStyleSelectView.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(android.widget.SeekBar seekBar2, int i, boolean z) {
                TextStyleSelectView.this.setTextSize(((i / 100.0f) * (TextStyleCategoryInfo.MAX_TEXTSIZE - 15.0f)) + 15.0f, z);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(android.widget.SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(android.widget.SeekBar seekBar2) {
            }
        });
        if (ICUManager.INSTANCE.isJapanBuild()) {
            this.mRootView.findViewById(R.id.font_thickness_tv).setVisibility(8);
            View viewFindViewById = this.mRootView.findViewById(R.id.view_letter_line_space_adjust_include);
            if (viewFindViewById.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) viewFindViewById.getLayoutParams()).bottomMargin = 0;
                viewFindViewById.requestLayout();
            }
            WidgetSwitcher widgetSwitcher = (WidgetSwitcher) ((ViewStub) this.mRootView.findViewById(R.id.font_widget_switcher)).inflate();
            this.mFontWidgetSwitcher = widgetSwitcher;
            widgetSwitcher.setTitle(this.mContext.getResources().getString(R.string.bold_font), null);
            this.mFontWidgetSwitcher.setChecked(true);
            this.mFontWidgetSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.components.view.TextStyleSelectView$$ExternalSyntheticLambda11
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    TextStyleSelectView.$r8$lambda$9d6HUz1RGu7qW5bN1mEhh8zxv4o(this.f$0, compoundButton, z);
                }
            });
        } else if (Build.IS_INTERNATIONAL_BUILD) {
            FontWeightAdjustView fontWeightAdjustView = (FontWeightAdjustView) ((ViewStub) this.mRootView.findViewById(R.id.font_thickness_sb_international)).inflate();
            this.mFontWeightAdjustView = fontWeightAdjustView;
            fontWeightAdjustView.setViewText(this.mContext.getResources().getString(R.string.font_thickness));
            this.mFontWeightAdjustView.setFontWeightChangeListener(new FontWeightAdjustView.FontWeightChangeListener() { // from class: com.miui.aod.components.view.TextStyleSelectView$$ExternalSyntheticLambda12
                @Override // com.miui.aod.widget.FontWeightAdjustView.FontWeightChangeListener
                public final void onWeightChange(int i) {
                    TextStyleSelectView.$r8$lambda$bnVy34ck9_mcDHZZr7KmOcS8OxI(this.f$0, i);
                }
            });
        } else {
            SeekBar seekBar2 = (SeekBar) ((ViewStub) this.mRootView.findViewById(R.id.font_thickness_sb)).inflate();
            this.mFontThicknessSeekBar = seekBar2;
            seekBar2.setContentDescription(this.mContext.getResources().getString(R.string.font_thickness));
            this.mFontThicknessSeekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() { // from class: com.miui.aod.components.view.TextStyleSelectView.5
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(android.widget.SeekBar seekBar3, int i, boolean z) {
                    TextStyleSelectView.this.setFontWeight((int) (((i / 100.0f) * 550.0f) + 150.0f), z);
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(android.widget.SeekBar seekBar3) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(android.widget.SeekBar seekBar3) {
                }
            });
        }
        this.mLineSpaceAddIv = (ImageView) this.mRootView.findViewById(R.id.linespace_add);
        this.mLineSpaceDelIv = (ImageView) this.mRootView.findViewById(R.id.linespace_del);
        this.mLetterSpaceAddIv = (ImageView) this.mRootView.findViewById(R.id.letterspace_add);
        this.mLetterSpaceDelIv = (ImageView) this.mRootView.findViewById(R.id.letterspace_del);
        initChangeSpaceBtn(this.mLineSpaceAddIv);
        initChangeSpaceBtn(this.mLineSpaceDelIv);
        initChangeSpaceBtn(this.mLetterSpaceAddIv);
        initChangeSpaceBtn(this.mLetterSpaceDelIv);
        this.mLetterSpaceValueTv = (TextView) this.mRootView.findViewById(R.id.letterspace_value);
        this.mLetterSpaceAddIv.setContentDescription(this.mContext.getResources().getQuantityString(R.plurals.character_pitch_plus, this.quantity_letter, this.mNF.format(this.mLetterSpaceValue)));
        this.mLetterSpaceDelIv.setContentDescription(this.mContext.getResources().getQuantityString(R.plurals.character_pitch_minus, this.quantity_letter, this.mNF.format(this.mLetterSpaceValue)));
        this.mLineSpaceValueTv = (TextView) this.mRootView.findViewById(R.id.linespace_value);
        this.mLineSpaceAddIv.setContentDescription(this.mContext.getResources().getQuantityString(R.plurals.line_spacing_puls, this.quantity_line, this.mNF.format(this.mLineSpaceValue)));
        this.mLineSpaceDelIv.setContentDescription(this.mContext.getResources().getQuantityString(R.plurals.lines_pacing_minus, this.quantity_line, this.mNF.format(this.mLineSpaceValue)));
        this.mNF.setMaximumFractionDigits(2);
        this.mNF.setMinimumFractionDigits(2);
        initAnchorView(R.id.textsize_sb_title, 1, 0);
        initWindowInsetsListener();
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public boolean initPreviewView(ViewGroup viewGroup) {
        return false;
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void initStyleInfoSelectedInner(StyleInfo styleInfo) {
        TextStyleCategoryInfo textStyleCategoryInfo = (TextStyleCategoryInfo) styleInfo;
        this.mTextStyleCategoryInfo = textStyleCategoryInfo;
        textStyleCategoryInfo.setClockBgName(null);
        if (this.mColorSelectView != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(0, (DrawableData) AodDrawables.sPureDrawableList.get(0));
            arrayList.addAll(AodDrawables.getGradientDrawables());
            for (int i = 1; i < AodDrawables.sPureDrawableList.size(); i++) {
                arrayList.add((DrawableData) AodDrawables.sPureDrawableList.get(i));
            }
            this.mColorSelectView.setDrawableData(arrayList);
            int iMax = Math.max(findDrawableById(arrayList, styleInfo.getColorName()), 0);
            this.mColorSelectView.setItemSelected(iMax);
            ColorSelectView colorSelectView = this.mColorSelectView;
            colorSelectView.smoothScrollToPosition(colorSelectView.getSelection());
            if (!arrayList.isEmpty()) {
                onColorSelect(iMax, arrayList.get(iMax));
            }
        }
        if (this.mTextAlignStyleSelectView != null) {
            int drawableIndexByGravity = ImagePool.getDrawableIndexByGravity(textStyleCategoryInfo.getGravity());
            this.mTextAlignStyleSelectView.setItemSelected(drawableIndexByGravity >= 0 ? drawableIndexByGravity : 1);
            TextAlignStyleSelectView textAlignStyleSelectView = this.mTextAlignStyleSelectView;
            textAlignStyleSelectView.scrollToPosition(textAlignStyleSelectView.getSelection());
        }
        this.mInputEt.setText(textStyleCategoryInfo.getContent());
        this.mTextSizeSeekBar.setProgress((int) (((this.mTextStyleCategoryInfo.getTextSize() - 15.0f) / (TextStyleCategoryInfo.MAX_TEXTSIZE - 15.0f)) * 100.0f));
        ICUManager iCUManager = ICUManager.INSTANCE;
        if (iCUManager.isJapanBuild()) {
            this.mFontWidgetSwitcher.setChecked(iCUManager.isBoldInJapan(this.mTextStyleCategoryInfo.getFontWeight()));
        } else if (Build.IS_INTERNATIONAL_BUILD) {
            this.mFontWeightAdjustView.setCurrentPointIndex(Utils.convertWeight2Level(this.mTextStyleCategoryInfo.getFontWeight()));
        } else {
            this.mFontThicknessSeekBar.setProgress(((this.mTextStyleCategoryInfo.getFontWeight() - 150) * 100) / 550);
        }
        this.mChangeColorAlongTimeSb.setChecked(this.mTextStyleCategoryInfo.isChangeColorAlongTime());
        this.mChangeColorAlongTimeSb.post(new Runnable() { // from class: com.miui.aod.components.view.TextStyleSelectView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                TextStyleSelectView.m274$r8$lambda$aG62p5FEXK2dx_5Txa2RfaDcBY(this.f$0);
            }
        });
        this.mLineSpaceValue = this.mTextStyleCategoryInfo.getLineSpacing() == Float.MAX_VALUE ? getLineSpaceDefaultValue() : this.mTextStyleCategoryInfo.getLineSpacing();
        this.mLineSpaceValueTmp = this.mTextStyleCategoryInfo.isVertical() ? 1.0f : 1.2f;
        this.mLetterSpaceValue = this.mTextStyleCategoryInfo.getLetterSpacing() + (this.mTextStyleCategoryInfo.isVertical() ? 1.0f : 0.0f);
        this.mLetterSpaceValueTmp = this.mTextStyleCategoryInfo.isVertical() ? 0.0f : 1.0f;
        updateLetterSpaceValue();
        updateLineSpaceValue();
        fitFontScale();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        changeViewStep(view, 0.01f);
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView, com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
    public void onStateChanged(@NonNull View view, int i) {
        super.onStateChanged(view, i);
        if (i != 4) {
            return;
        }
        Utils.hideSoftInput(this.mContext, this.mInputEt);
    }

    @Override // com.miui.aod.widget.SignatureAodView.ITextSizeListener
    public void onTextSizeChanged(int i) {
        this.mTextSizeSeekBar.setProgress((int) (((i - 15.0f) / (TextStyleCategoryInfo.MAX_TEXTSIZE - 15.0f)) * 100.0f));
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void showPreviewMode(boolean z) {
        Log.d("AodStyleSelect", "TextStyleSelectView.showPreviewMode: showPreview=" + z + ", imeVisible=" + isImeVisible());
        super.showPreviewMode(z);
        Utils.hideSoftInput(this.mContext, this.mInputEt);
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public IAodClock updateStyleInfoForPreview() {
        IAodClock iAodClockUpdateStyleInfoForPreview = super.updateStyleInfoForPreview();
        SignatureAodView view = ((SignatureClock) iAodClockUpdateStyleInfoForPreview).getView();
        this.mSignatureAodView = view;
        this.mSignatureTextView = view.getSignatureContent();
        this.mSignatureAodView.setTextSizeChangedListener(this);
        return iAodClockUpdateStyleInfoForPreview;
    }
}
