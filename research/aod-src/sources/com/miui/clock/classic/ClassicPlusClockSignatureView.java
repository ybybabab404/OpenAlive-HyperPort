package com.miui.clock.classic;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.ColorUtils;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.MiuiClassicClockInfoBase;
import com.miui.clock.module.MiuiClassicPlusSignatureClockInfo;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import java.util.Map;
import miuix.androidbasewidget.widget.ClearableEditText;

/* JADX INFO: loaded from: classes.dex */
public class ClassicPlusClockSignatureView extends ClassicClockBaseView {
    public ConstraintSet mConstraintSet;
    public boolean mNeedShowTimeView2;
    public int mScreenWidth;
    public ClearableEditText mSignatureTextInEditor;
    public TextPaint mStaticLayoutPaint;

    /* JADX INFO: renamed from: com.miui.clock.classic.ClassicPlusClockSignatureView$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.SIGNATURE_AREA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.SIGNATURE_EDIT_AREA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public ClassicPlusClockSignatureView(Context context) {
        super(context);
        this.mStaticLayoutPaint = new TextPaint();
    }

    public ClassicPlusClockSignatureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStaticLayoutPaint = new TextPaint();
    }

    public ClassicPlusClockSignatureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mStaticLayoutPaint = new TextPaint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScreenSizeChange() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.setMargin(R.id.text_area, 3, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.classic_plus_text_area_margin_top, false));
        int i = R.id.area1;
        constraintSet.setMargin(i, 3, getDimen(R.dimen.classic_plus_area1_time1_margin_top));
        constraintSet.constrainHeight(i, getDimen(R.dimen.classic_plus_area1_time1_height));
        int i2 = R.id.signature_text_container;
        constraintSet.setMargin(i2, 3, getDimen(this.mNeedShowTimeView2 ? R.dimen.classic_plus_signature_margin_top_two_line : R.dimen.classic_plus_signature_margin_top_single_line));
        constraintSet.constrainWidth(i2, getDimen(R.dimen.classic_text_signature_max_width));
        constraintSet.applyTo(this);
        this.mTextArea.onScreenSizeChange();
        updateSignatureText();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public ClockStyleInfo getClockStyleInfo() {
        return this.mClockStyleInfo;
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getGalleryGravity() {
        return 1;
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i != 1) {
            return i != 2 ? super.getIClockView(clockViewType) : this.mSignatureTextInEditor;
        }
        return this.mSignatureText;
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return 0;
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return getDimen(R.dimen.classic_plus_text_area_margin_top);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2
    public void init(Context context) {
        super.init(context);
        this.mConstraintSet = new ConstraintSet();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView
    public boolean isAODType() {
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        return miuiClassicClockInfoBase != null && ClockStyleInfo.isAODType(miuiClassicClockInfoBase.getDisplayType());
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView
    public boolean isEditMode() {
        return this.mEditMode;
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView
    public boolean isPlus() {
        return true;
    }

    @Override // com.miui.clock.MiuiBaseClock2, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int iWidth = DeviceConfig.getScreenRect(this.mContext).width();
        if (this.mScreenWidth != iWidth && !this.mEditMode) {
            this.mScreenWidth = iWidth;
        }
        if (!this.mEditMode) {
            checkAndCorrectSignatureText(this.mSignatureText, this.mClockStyleInfo.getSignature(), 2);
        } else {
            ClearableEditText clearableEditText = this.mSignatureTextInEditor;
            checkAndCorrectSignatureText(clearableEditText, clearableEditText.getText().toString(), 2);
        }
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mScreenWidth = DeviceConfig.getScreenRect(this.mContext).width();
        ClearableEditText clearableEditText = (ClearableEditText) findViewById(R.id.signature_text_edit);
        this.mSignatureTextInEditor = clearableEditText;
        if (clearableEditText != null) {
            clearableEditText.setBackground(null);
            this.mSignatureTextInEditor.setPadding(0, 0, 0, 0);
        }
        ClassicClockBaseView.setLocalePreferredLineHeightForMinimumUsed(this.mSignatureTextInEditor, false);
        updateSignatureText();
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        if (this.mClockStyleInfo == null) {
            return;
        }
        updateSignatureText();
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        post(new Runnable() { // from class: com.miui.clock.classic.ClassicPlusClockSignatureView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onScreenSizeChange();
            }
        });
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        this.mSignatureTextInEditor.setHintTextColor(this.mContext.getResources().getColor(z ? R.color.classic_color_black_40 : R.color.classic_color_white_60));
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        MiuiClassicPlusSignatureClockInfo miuiClassicPlusSignatureClockInfo = (MiuiClassicPlusSignatureClockInfo) clockStyleInfo;
        this.mClockStyleInfo = miuiClassicPlusSignatureClockInfo;
        this.mNeedShowTimeView2 = miuiClassicPlusSignatureClockInfo.getClassicLine2() == 72;
        int originMagazineColor = this.mClockStyleInfo.getOriginMagazineColor();
        if (originMagazineColor != 0) {
            int alphaComponent = ColorUtils.setAlphaComponent(originMagazineColor, 153);
            this.mSignatureText.setTextColor(alphaComponent);
            this.mSignatureTextInEditor.setTextColor(alphaComponent);
        }
        clearColorEffect(false);
        if (this.mEditMode) {
            this.mSignatureText.setVisibility(8);
        } else {
            this.mSignatureText.setVisibility(0);
            this.mSignatureTextInEditor.setVisibility(8);
            onScreenSizeChange();
        }
        this.mSignatureText.setTextColor(this.mClockStyleInfo.getPrimaryColor());
        this.mSignatureTextInEditor.setTextColor(this.mClockStyleInfo.getPrimaryColor());
        if (this.mClockStyleInfo.getClassicLine5() == 0 || isAODType()) {
            this.mSignatureText.setVisibility(8);
            this.mSignatureText.setText("");
            return;
        }
        String signature = this.mClockStyleInfo.getSignature();
        if (TextUtils.isEmpty(signature)) {
            this.mSignatureText.setVisibility(8);
        } else if (isEditMode()) {
            this.mSignatureTextInEditor.setText(signature);
            checkAndCorrectSignatureText(this.mSignatureTextInEditor, signature, 2);
            ClearableEditText clearableEditText = this.mSignatureTextInEditor;
            clearableEditText.setSelection(clearableEditText.getText().length());
        } else {
            this.mSignatureText.setVisibility(0);
            checkAndCorrectSignatureText(this.mSignatureText, signature, 2);
        }
        Log.d("ClassicPlusClockSignatureView", "set signature=[" + signature + "]");
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setEditMode(boolean z) {
        super.setEditMode(z);
        if (isEditMode()) {
            this.mSignatureText.setVisibility(8);
            String signature = this.mClockStyleInfo.getSignature();
            initEditTextView(this.mSignatureTextInEditor, signature, 2);
            checkAndCorrectSignatureText(this.mSignatureTextInEditor, signature, 2);
            ViewGroup.LayoutParams layoutParams = this.mTextArea.getLayoutParams();
            layoutParams.height = z ? getDimen(R.dimen.classic_plus_text_area_height) : -2;
            this.mTextArea.setLayoutParams(layoutParams);
        }
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView
    public void updateColor(ClockStyleInfo clockStyleInfo) {
        int originMagazineColor;
        super.updateColor(clockStyleInfo);
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockStyleInfo;
        if (miuiClassicClockInfoBase == null || (originMagazineColor = miuiClassicClockInfoBase.getOriginMagazineColor()) == 0) {
            return;
        }
        int alphaComponent = ColorUtils.setAlphaComponent(originMagazineColor, 153);
        this.mSignatureText.setTextColor(alphaComponent);
        this.mSignatureTextInEditor.setTextColor(alphaComponent);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView
    public void updateSignatureText() {
        super.updateSignatureText();
        this.mSignatureTextInEditor.setTextSize(0, getDimensionFloat(R.dimen.classic_signature_text_size));
        this.mSignatureTextInEditor.setTypeface(FontUtils.getMiSans(380));
        this.mSignatureTextInEditor.setMaxWidth(getDimen(R.dimen.classic_text_signature_max_width));
    }
}
