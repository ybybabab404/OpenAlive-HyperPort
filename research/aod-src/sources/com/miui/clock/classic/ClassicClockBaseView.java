package com.miui.clock.classic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintSet;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.MiuiClassicClockInfoBase;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import com.miui.clock.utils.ReflectUtils;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import miuix.androidbasewidget.widget.ClearableEditText;

/* JADX INFO: loaded from: classes.dex */
public abstract class ClassicClockBaseView extends MiuiGalleryBaseClock {
    public String TAG;
    public MiuiClassicClockInfoBase mClockStyleInfo;
    public ConstraintSet mConstraintSet;
    public int mCurrentUserId;
    public boolean mEditMode;
    public TextView mSignatureText;
    public ClassicTextAreaView mTextArea;
    public boolean showFace;

    public ClassicClockBaseView(Context context) {
        super(context);
        this.TAG = "ClassicClockBaseView";
    }

    public ClassicClockBaseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "ClassicClockBaseView";
    }

    public ClassicClockBaseView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "ClassicClockBaseView";
    }

    private void getUnUseDimens() {
        Resources resources = getResources();
        resources.getDimensionPixelSize(R.dimen.classic_text_area_margin_start_end_fold);
        resources.getDimensionPixelSize(R.dimen.classic_text_area_margin_start_end_pad);
        resources.getDimensionPixelSize(R.dimen.classic_text_signature_margin_start_end_fold);
        resources.getDimensionPixelSize(R.dimen.classic_text_signature_margin_start_end_pad);
        resources.getDimensionPixelSize(R.dimen.classic_text_area_margin_top_has_face_fold);
        resources.getDimensionPixelSize(R.dimen.classic_text_area_margin_top_has_face_pad);
        resources.getDimensionPixelSize(R.dimen.classic_area_2_3_4_margin_start_end_fold);
        resources.getDimensionPixelSize(R.dimen.classic_area_2_3_4_margin_start_end_pad);
        resources.getDimensionPixelSize(R.dimen.classic_plus_text_area_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.classic_plus_text_area_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.classic_plus_text_area_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.classic_plus_text_area_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.notification_bottom_classic_fold);
        resources.getDimensionPixelSize(R.dimen.notification_bottom_classic_fold_land);
        resources.getDimensionPixelSize(R.dimen.notification_bottom_classic_pad);
        resources.getDimensionPixelSize(R.dimen.notification_bottom_classic_pad_land);
        resources.getDimensionPixelSize(R.dimen.notification_bottom_classic_plus_fold);
        resources.getDimensionPixelSize(R.dimen.notification_bottom_classic_plus_fold_land);
        resources.getDimensionPixelSize(R.dimen.notification_bottom_classic_plus_pad);
        resources.getDimensionPixelSize(R.dimen.notification_bottom_classic_plus_pad_land);
    }

    public static void setLocalePreferredLineHeightForMinimumUsed(TextView textView, boolean z) {
        ReflectUtils.invokeObject(TextView.class, textView, "setLocalePreferredLineHeightForMinimumUsed", Void.TYPE, new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void asyncInitializeClockStyleAfterInflation(ClockStyleInfo clockStyleInfo) {
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = (MiuiClassicClockInfoBase) clockStyleInfo;
        boolean zEquals = "classic_plus".equals(miuiClassicClockInfoBase.getType());
        int style = miuiClassicClockInfoBase.getStyle();
        ClassicClockTimeUtils.getClassicGlassViewTextInfo(getContext(), miuiClassicClockInfoBase.getTemplateId(), style, miuiClassicClockInfoBase.getClockWeight(), miuiClassicClockInfoBase.getClassicLine2(), zEquals);
        FontUtils.getSmallFontTypeFace(style);
        FontUtils.getTemperatureUnitTypeFace(style);
    }

    public void checkAndCorrectSignatureText(TextView textView, String str, int i) {
        Log.d(this.TAG, "checkAndCorrectSignatureText signatureContent=" + str);
        if (textView == null || TextUtils.isEmpty(str) || i <= 0) {
            return;
        }
        int dimen = getDimen(R.dimen.classic_text_signature_max_width);
        textView.setMaxWidth(dimen);
        TextPaint paint = textView.getPaint();
        int compoundPaddingLeft = (dimen - textView.getCompoundPaddingLeft()) - textView.getCompoundPaddingRight();
        if (StaticLayout.Builder.obtain(str, 0, str.length(), paint, compoundPaddingLeft).setMaxLines(Integer.MAX_VALUE).build().getLineCount() <= i) {
            textView.setText(str);
            return;
        }
        int lineEnd = StaticLayout.Builder.obtain(str, 0, str.length(), paint, compoundPaddingLeft).setMaxLines(i).setEllipsize(null).build().getLineEnd(i - 1);
        if (lineEnd < str.length() && Character.isLowSurrogate(str.charAt(lineEnd))) {
            lineEnd = (lineEnd <= 0 || !Character.isHighSurrogate(str.charAt(lineEnd + (-1)))) ? lineEnd - 1 : lineEnd - 2;
        }
        textView.setText(str.substring(0, lineEnd));
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public ClockStyleInfo getClockStyleInfo() {
        return this.mClockStyleInfo;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
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
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        if (miuiClassicClockInfoBase != null) {
            return ColorUtils.blendColor(miuiClassicClockInfoBase.getOriginMagazineColor(), 0.6f);
        }
        return 0;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        if (this.mMagazineInfoVisible) {
            return isPlus() ? getDimen(R.dimen.classic_plus_notification_margin_top_with_magazine) : getDimen(R.dimen.notification_bottom_classic_with_gallery);
        }
        return isPlus() ? MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.notification_bottom_classic_plus, false) : MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.notification_bottom_classic, false);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return isPlus() ? getDimen(R.dimen.classic_plus_text_area_margin_top) : getDimen(R.dimen.classic_text_area_margin_top_has_face);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.mTextArea;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
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
        super.init(context);
        this.mConstraintSet = new ConstraintSet();
    }

    public void initEditTextView(final ClearableEditText clearableEditText, String str, final int i) {
        if (clearableEditText == null) {
            return;
        }
        clearableEditText.setVisibility(0);
        if (str != null) {
            clearableEditText.setText(str);
            clearableEditText.setSelection(clearableEditText.getText().length());
        }
        clearableEditText.setMaxWidth(getDimen(R.dimen.classic_text_signature_max_width));
        clearableEditText.addTextChangedListener(new TextWatcher() { // from class: com.miui.clock.classic.ClassicClockBaseView.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                Layout layout = clearableEditText.getLayout();
                if (layout == null || layout.getLineCount() <= i) {
                    return;
                }
                Editable text = clearableEditText.getText();
                Objects.requireNonNull(text);
                String string = text.toString();
                int length = string.length();
                int i2 = length - 1;
                if (Character.isLowSurrogate(string.charAt(i2))) {
                    i2 = length - 2;
                }
                String string2 = editable.subSequence(0, i2).toString();
                if (string2.endsWith("\n")) {
                    string2 = string2.substring(0, string2.lastIndexOf("\n"));
                }
                if (string2.equals(clearableEditText.getText().toString())) {
                    return;
                }
                clearableEditText.setText(string2);
                ClearableEditText clearableEditText2 = clearableEditText;
                clearableEditText2.setSelection(clearableEditText2.length());
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }
        });
    }

    public boolean isAODType() {
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        return miuiClassicClockInfoBase != null && ClockStyleInfo.isAODType(miuiClassicClockInfoBase.getDisplayType());
    }

    public boolean isEditMode() {
        return this.mEditMode;
    }

    public abstract boolean isPlus();

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        ClassicTextAreaView classicTextAreaView = (ClassicTextAreaView) findViewById(R.id.text_area);
        this.mTextArea = classicTextAreaView;
        classicTextAreaView.setCalendar(this.mCalendar);
        TextView textView = (TextView) findViewById(R.id.signature_text);
        this.mSignatureText = textView;
        textView.setTypeface(FontUtils.getMiSans(380));
        getUnUseDimens();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        Log.d(this.TAG, "Classic Clock View LanguageChanged");
        FontUtils.clearClassicTypefaceCache();
        this.mTextArea.onLanguageChanged();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onTimeZoneUpdate(TimeZone timeZone) {
        this.mCalendar.setTimeZone(timeZone);
        updateTime();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        String str;
        int aodSecondaryColor;
        int primaryColor;
        int color;
        int color2;
        super.setClockPalette(i, z, map, z2);
        if (map != null) {
            str = "secondary30=" + map.get("secondary30");
        } else {
            str = "null";
        }
        Log.d(this.TAG, "setClockPalette: type = " + i + ", textDark = " + z + ", palette = " + str);
        if (DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            updateHighTextColors(this.mClockStyleInfo);
            return;
        }
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        if (miuiClassicClockInfoBase != null) {
            int clockEffect = miuiClassicClockInfoBase.getClockEffect();
            int primaryColor2 = -1;
            if (!ClockEffectUtils.isGradualType(this.mClockStyleInfo.getClockEffect())) {
                if (ClockEffectUtils.isDifferenceType(this.mClockStyleInfo.getClockEffect())) {
                    if (this.mClockStyleInfo.isAutoPrimaryColor()) {
                        Object obj = z ? "secondary15" : "secondary85";
                        if (map != null && map.get(obj) != null) {
                            int iIntValue = map.get(obj).intValue();
                            MiuiClassicClockInfoBase miuiClassicClockInfoBase2 = this.mClockStyleInfo;
                            miuiClassicClockInfoBase2.setPrimaryColor(ClockStyleInfo.isAODType(miuiClassicClockInfoBase2.getDisplayType()) ? GlobalColorUtils.transformAodColor(iIntValue) : iIntValue);
                            this.mClockStyleInfo.setBlendColor(iIntValue);
                            this.mClockStyleInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(iIntValue));
                            this.mClockStyleInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue));
                        }
                        if (this.mClockStyleInfo.isAutoSecondaryColor()) {
                            String str2 = z ? "secondary15" : "secondary85";
                            if (map != null && map.get(str2) != null) {
                                int iIntValue2 = map.get(str2).intValue();
                                MiuiClassicClockInfoBase miuiClassicClockInfoBase3 = this.mClockStyleInfo;
                                miuiClassicClockInfoBase3.setSecondaryColor(ClockStyleInfo.isAODType(miuiClassicClockInfoBase3.getDisplayType()) ? GlobalColorUtils.transformAodColor(iIntValue2) : iIntValue2);
                                this.mClockStyleInfo.setSecondaryBlendColor(iIntValue2);
                                this.mClockStyleInfo.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(iIntValue2));
                                this.mClockStyleInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue2));
                            }
                        }
                    } else {
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase4 = this.mClockStyleInfo;
                        miuiClassicClockInfoBase4.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(miuiClassicClockInfoBase4.getPrimaryColor()));
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase5 = this.mClockStyleInfo;
                        miuiClassicClockInfoBase5.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(miuiClassicClockInfoBase5.getBlendColor()));
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase6 = this.mClockStyleInfo;
                        miuiClassicClockInfoBase6.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(miuiClassicClockInfoBase6.getClassicPlusSecondaryColor()));
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase7 = this.mClockStyleInfo;
                        miuiClassicClockInfoBase7.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(miuiClassicClockInfoBase7.getClassicPlusSecondaryBlendColor()));
                    }
                    this.mClockStyleInfo.convertAnimationColor();
                } else if (!ClockEffectUtils.isGlowType(this.mClockStyleInfo.getClockEffect()) || !DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                    if (this.mClockStyleInfo.isAutoPrimaryColor()) {
                        if (ClockEffectUtils.isBlurMixType(clockEffect) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
                            if (z) {
                                color = (map == null || map.get("neutral-variant30") == null) ? this.mClockStyleInfo.getPrimaryColor() : map.get("neutral-variant30").intValue();
                            } else {
                                color = Color.parseColor("#FFABABAB");
                            }
                            if (z) {
                                color2 = (map == null || map.get("secondary70") == null) ? color : map.get("secondary70").intValue();
                            } else {
                                color2 = Color.parseColor("#FFBEBEBE");
                            }
                            this.mClockStyleInfo.setPrimaryColor(-1);
                            this.mClockStyleInfo.setFullAodPrimaryColor(-1);
                            this.mClockStyleInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(color));
                            this.mClockStyleInfo.setBlendColor(color);
                            this.mClockStyleInfo.setAodPrimaryColor(color2);
                            this.mClockStyleInfo.setAodBlendColor(color2);
                        } else {
                            if (z) {
                                primaryColor = (map == null || map.get("secondary30") == null) ? this.mClockStyleInfo.getPrimaryColor() : map.get("secondary30").intValue();
                            } else {
                                primaryColor = -1;
                            }
                            this.mClockStyleInfo.setPrimaryColor(primaryColor);
                            this.mClockStyleInfo.setAodPrimaryColor(GlobalColorUtils.transformAodColor(primaryColor));
                            this.mClockStyleInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(primaryColor));
                            this.mClockStyleInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(primaryColor));
                        }
                    } else if (ClockEffectUtils.isBlurMixType(clockEffect) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
                        int iTransformAodColor = GlobalColorUtils.transformAodColor(this.mClockStyleInfo.getBlendColor());
                        int iTransformFullAodColor = GlobalColorUtils.transformFullAodColor(this.mClockStyleInfo.getBlendColor());
                        this.mClockStyleInfo.setPrimaryColor(-1);
                        this.mClockStyleInfo.setAodPrimaryColor(iTransformAodColor);
                        this.mClockStyleInfo.setAodBlendColor(iTransformAodColor);
                        this.mClockStyleInfo.setFullAodPrimaryColor(-1);
                        this.mClockStyleInfo.setFullAodBlendColor(iTransformFullAodColor);
                    } else {
                        this.mClockStyleInfo.setAodBlendColor(this.mClockStyleInfo.getAodPrimaryColor());
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase8 = this.mClockStyleInfo;
                        miuiClassicClockInfoBase8.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(miuiClassicClockInfoBase8.getPrimaryColor()));
                    }
                    int color3 = Color.parseColor("#FFFFFF");
                    if (this.mClockStyleInfo.isAutoSecondaryColor()) {
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase9 = this.mClockStyleInfo;
                        miuiClassicClockInfoBase9.setSecondaryColor(miuiClassicClockInfoBase9.getPrimaryColor());
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase10 = this.mClockStyleInfo;
                        miuiClassicClockInfoBase10.setSecondaryBlendColor(miuiClassicClockInfoBase10.getBlendColor());
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase11 = this.mClockStyleInfo;
                        miuiClassicClockInfoBase11.setAodSecondaryColor(miuiClassicClockInfoBase11.getAodPrimaryColor());
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase12 = this.mClockStyleInfo;
                        miuiClassicClockInfoBase12.setAodSecondaryBlendColor(miuiClassicClockInfoBase12.getAodBlendColor());
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase13 = this.mClockStyleInfo;
                        miuiClassicClockInfoBase13.setFullAodSecondaryColor(miuiClassicClockInfoBase13.getFullAodPrimaryColor());
                        MiuiClassicClockInfoBase miuiClassicClockInfoBase14 = this.mClockStyleInfo;
                        miuiClassicClockInfoBase14.setFullAodSecondaryBlendColor(miuiClassicClockInfoBase14.getFullAodBlendColor());
                    } else {
                        if (ClockEffectUtils.isBlurMixType(clockEffect) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
                            aodSecondaryColor = GlobalColorUtils.transformAodColor(this.mClockStyleInfo.getSecondaryBlendColor());
                            this.mClockStyleInfo.setSecondaryColor(color3);
                            this.mClockStyleInfo.setFullAodSecondaryColor(color3);
                            MiuiClassicClockInfoBase miuiClassicClockInfoBase15 = this.mClockStyleInfo;
                            miuiClassicClockInfoBase15.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(miuiClassicClockInfoBase15.getSecondaryBlendColor()));
                        } else {
                            aodSecondaryColor = this.mClockStyleInfo.getAodSecondaryColor();
                            MiuiClassicClockInfoBase miuiClassicClockInfoBase16 = this.mClockStyleInfo;
                            miuiClassicClockInfoBase16.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(miuiClassicClockInfoBase16.getSecondaryColor()));
                        }
                        this.mClockStyleInfo.setAodSecondaryColor(aodSecondaryColor);
                        this.mClockStyleInfo.setAodSecondaryBlendColor(aodSecondaryColor);
                    }
                    if (ClockEffectUtils.isBlurMixType(this.mClockStyleInfo.getClockEffect()) && !DeviceConfig.supportBackgroundBlur(this.mContext)) {
                        if (!this.mClockStyleInfo.isAutoPrimaryColor()) {
                            this.mClockStyleInfo.convertPrimaryColorByBlendColor(true);
                        }
                        if (!this.mClockStyleInfo.isAutoSecondaryColor()) {
                            this.mClockStyleInfo.convertSecondaryColorByBlendColor(true);
                        }
                    }
                }
            }
            if (this.mClockStyleInfo.isAutoPrimaryColor()) {
                MiuiClassicClockInfoBase miuiClassicClockInfoBase17 = this.mClockStyleInfo;
                if (z) {
                    primaryColor2 = (map == null || map.get("secondary30") == null) ? this.mClockStyleInfo.getPrimaryColor() : map.get("secondary30").intValue();
                }
                miuiClassicClockInfoBase17.setOriginMagazineColor(primaryColor2);
            } else if (ClockEffectUtils.isBlurMixType(clockEffect)) {
                MiuiClassicClockInfoBase miuiClassicClockInfoBase18 = this.mClockStyleInfo;
                miuiClassicClockInfoBase18.setOriginMagazineColor(miuiClassicClockInfoBase18.getBlendColor());
            }
            MiuiClassicClockInfoBase miuiClassicClockInfoBase19 = this.mClockStyleInfo;
            miuiClassicClockInfoBase19.setAnimationPrimaryColor(miuiClassicClockInfoBase19.getPrimaryColor());
            MiuiClassicClockInfoBase miuiClassicClockInfoBase20 = this.mClockStyleInfo;
            miuiClassicClockInfoBase20.setAnimationSecondaryColor(miuiClassicClockInfoBase20.getSecondaryColor());
            updateColor(this.mClockStyleInfo);
            Log.d(this.TAG, "setClockPalette [effectType:" + this.mClockStyleInfo.getClockEffect() + " isAod:" + this.mClockStyleInfo.getDisplayType() + " <isAutoPrimary:" + this.mClockStyleInfo.isAutoPrimaryColor() + " primaryColor:" + this.mClockStyleInfo.getPrimaryColor() + " blendColor:" + this.mClockStyleInfo.getBlendColor() + " aodPrimary:" + this.mClockStyleInfo.getAodPrimaryColor() + " aodPrimaryBlend:" + this.mClockStyleInfo.getAodBlendColor() + " animationPrimaryColor:" + this.mClockStyleInfo.getAnimationPrimaryColor() + " fullAodPrimaryColor:" + this.mClockStyleInfo.getFullAodPrimaryColor() + " fullAodBlendColor:" + this.mClockStyleInfo.getFullAodBlendColor() + "> <isAutoSecond:" + this.mClockStyleInfo.isAutoSecondaryColor() + " secondaryColor:" + this.mClockStyleInfo.getSecondaryColor() + " secondaryBlendColor:" + this.mClockStyleInfo.getSecondaryBlendColor() + " aodSecond:" + this.mClockStyleInfo.getAodSecondaryColor() + " aodSecondBlendColor:" + this.mClockStyleInfo.getAodSecondaryBlendColor() + " animationSecondColor:" + this.mClockStyleInfo.getAnimationSecondaryColor() + " fullAodSecondaryColor:" + this.mClockStyleInfo.getFullAodSecondaryColor() + " fullAodSecondaryBlendColor:" + this.mClockStyleInfo.getFullAodSecondaryBlendColor() + ">],[vision:25.8.19]");
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        this.mClockStyleInfo = (MiuiClassicClockInfoBase) clockStyleInfo;
        Log.d(this.TAG, "mClockStyleInfo=[" + this.mClockStyleInfo + "]");
        ClassicTextAreaView classicTextAreaView = this.mTextArea;
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        classicTextAreaView.initData(miuiClassicClockInfoBase, miuiClassicClockInfoBase.getClassicLine1());
        checkAndCorrectSignatureText(this.mSignatureText, this.mClockStyleInfo.getSignature(), 2);
        this.mSignatureText.setVisibility(isAODType() ? 8 : 4);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setCurrentUserId(int i) {
        this.mCurrentUserId = i;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setEditMode(boolean z) {
        this.mEditMode = z;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    public void setMarginTop(int i, int i2) {
        this.mConstraintSet.clone(this);
        this.mConstraintSet.setMargin(i, 3, i2);
        this.mConstraintSet.applyTo(this);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setSuperSaveOpen(boolean z) {
        super.setSuperSaveOpen(z);
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        if (miuiClassicClockInfoBase != null) {
            updateColor(miuiClassicClockInfoBase);
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        updateColor(this.mClockStyleInfo);
    }

    public void updateColor(ClockStyleInfo clockStyleInfo) {
        if (clockStyleInfo != null && (clockStyleInfo instanceof MiuiClassicClockInfoBase)) {
            MiuiClassicClockInfoBase miuiClassicClockInfoBase = (MiuiClassicClockInfoBase) clockStyleInfo;
            this.mClockStyleInfo = miuiClassicClockInfoBase;
            this.mTextArea.initData(miuiClassicClockInfoBase, miuiClassicClockInfoBase.getClassicLine1());
            this.mSignatureText.setTextColor(getMagazineColor());
        }
    }

    public void updateSignatureText() {
        this.mSignatureText.setTextSize(0, getDimensionFloat(R.dimen.classic_signature_text_size));
        this.mSignatureText.setTypeface(FontUtils.getMiSans(380));
        this.mSignatureText.setMaxWidth(getDimen(R.dimen.classic_text_signature_max_width));
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        this.mTextArea.updateTime();
    }
}
