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
import com.miui.clock.module.MiuiClassicSignatureClockInfo;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import java.util.Map;
import miuix.androidbasewidget.widget.ClearableEditText;

/* JADX INFO: loaded from: classes.dex */
public class ClassicClockSignatureView extends ClassicClockBaseView {
    public boolean mFirstInit;
    public int mScreenWidth;
    public ClearableEditText mSignatureTextInEditor;
    public TextPaint mStaticLayoutPaint;

    /* JADX INFO: renamed from: com.miui.clock.classic.ClassicClockSignatureView$1, reason: invalid class name */
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

    public ClassicClockSignatureView(Context context) {
        super(context);
        this.mFirstInit = true;
        this.mStaticLayoutPaint = new TextPaint();
    }

    public ClassicClockSignatureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFirstInit = true;
        this.mStaticLayoutPaint = new TextPaint();
    }

    public ClassicClockSignatureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFirstInit = true;
        this.mStaticLayoutPaint = new TextPaint();
    }

    private int getDimenPX(int i) {
        return getDimen(i);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
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
        return MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.classic_text_area_margin_top_has_face, true);
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
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
        return false;
    }

    @Override // com.miui.clock.MiuiBaseClock2, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int iWidth = DeviceConfig.getScreenRect(this.mContext).width();
        if (this.mScreenWidth != iWidth) {
            this.mScreenWidth = iWidth;
            onScreenSizeChange();
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
        ClearableEditText clearableEditText = (ClearableEditText) findViewById(R.id.signature_text_edit);
        this.mSignatureTextInEditor = clearableEditText;
        if (clearableEditText != null) {
            clearableEditText.setBackground(null);
            this.mSignatureTextInEditor.setPadding(0, 0, 0, 0);
            this.mSignatureTextInEditor.setGravity(8388659);
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

    public void onScreenSizeChange() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        int i = R.id.text_area;
        constraintSet.setMargin(i, 3, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.classic_text_area_margin_top_has_face, true));
        Context context = getContext();
        int i2 = R.dimen.classic_text_area_margin_start_end;
        constraintSet.setMargin(i, 6, MutiDeviceDimenAdapter.getMultiDeviceDimen(context, i2, true));
        constraintSet.setMargin(i, 7, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i2, true));
        int i3 = R.id.area1;
        constraintSet.setMargin(i3, 3, getDimenPX(isEditMode() ? R.dimen.classic_area2_margin_top_edit : R.dimen.classic_area2_margin_top));
        int i4 = R.id.signature_text_container;
        constraintSet.setMargin(i4, 3, getDimenPX(isEditMode() ? R.dimen.classic_signature_area_margin_top_edit : R.dimen.classic_signature_area_margin_top));
        Context context2 = getContext();
        int i5 = R.dimen.classic_text_signature_margin_start_end;
        constraintSet.setMargin(i4, 6, MutiDeviceDimenAdapter.getMultiDeviceDimen(context2, i5, true));
        constraintSet.setMargin(i4, 7, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i5, true));
        constraintSet.constrainWidth(i4, getDimenPX(R.dimen.classic_text_signature_max_width));
        int i6 = R.id.magazine_info_container;
        constraintSet.setMargin(i6, 6, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i5, true));
        constraintSet.setMargin(i6, 7, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i5, true));
        constraintSet.setMargin(i6, 3, getDimenPX(R.dimen.classic_gallery_area_margin_top));
        constraintSet.setGoneMargin(i6, 3, getDimenPX(R.dimen.classic_signature_area_margin_top));
        int i7 = R.dimen.classic_area_location_height;
        constraintSet.constrainHeight(i3, getDimenPX(i7));
        constraintSet.constrainHeight(R.id.area2, getDimenPX(i7));
        constraintSet.constrainHeight(R.id.area3, getDimenPX(i7));
        constraintSet.applyTo(this);
        this.mTextArea.onScreenSizeChange();
        updateSignatureText();
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
        this.mClockStyleInfo = (MiuiClassicSignatureClockInfo) clockStyleInfo;
        this.mSignatureText.setTextColor(getMagazineColor());
        this.mSignatureTextInEditor.setTextColor(getMagazineColor());
        if (this.mFirstInit) {
            this.mFirstInit = false;
            onScreenSizeChange();
        }
        if (this.mClockStyleInfo.getClassicLine5() == 0 || isAODType()) {
            this.mSignatureText.setVisibility(8);
            this.mSignatureText.setText("");
        } else {
            String signature = this.mClockStyleInfo.getSignature();
            if (TextUtils.isEmpty(signature)) {
                this.mSignatureText.setVisibility(8);
            } else if (isEditMode()) {
                this.mSignatureTextInEditor.setVisibility(0);
                this.mSignatureTextInEditor.setText(signature);
                checkAndCorrectSignatureText(this.mSignatureTextInEditor, signature, 2);
                ClearableEditText clearableEditText = this.mSignatureTextInEditor;
                clearableEditText.setSelection(clearableEditText.getText().length());
            } else {
                this.mSignatureText.setVisibility(0);
                checkAndCorrectSignatureText(this.mSignatureText, signature, 2);
            }
            onScreenSizeChange();
            Log.d(this.TAG, "set signature=[" + signature + "]");
        }
        updateTime();
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
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.setMargin(R.id.area1, 3, getDimenPX(isEditMode() ? R.dimen.classic_area2_margin_top_edit : R.dimen.classic_area2_margin_top));
        int i = R.id.signature_text_container;
        constraintSet.setMargin(i, 3, getDimenPX(z ? R.dimen.classic_signature_area_margin_top_edit : R.dimen.classic_signature_area_margin_top));
        Context context = getContext();
        int i2 = R.dimen.classic_text_signature_margin_start_end;
        constraintSet.setMargin(i, 6, MutiDeviceDimenAdapter.getMultiDeviceDimen(context, i2, true));
        constraintSet.setMargin(i, 7, MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i2, true));
        constraintSet.applyTo(this);
        ViewGroup.LayoutParams layoutParams = this.mTextArea.getLayoutParams();
        layoutParams.height = z ? getDimen(R.dimen.classic_plus_text_area_height) : -2;
        this.mTextArea.setLayoutParams(layoutParams);
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
        super.updateColor(clockStyleInfo);
        int originMagazineColor = this.mClockStyleInfo.getOriginMagazineColor();
        if (originMagazineColor != 0) {
            int alphaComponent = ColorUtils.setAlphaComponent(originMagazineColor, 153);
            this.mSignatureText.setTextColor(alphaComponent);
            this.mSignatureTextInEditor.setTextColor(alphaComponent);
        }
    }

    @Override // com.miui.clock.classic.ClassicClockBaseView
    public void updateSignatureText() {
        super.updateSignatureText();
        this.mSignatureTextInEditor.setTextSize(0, getDimensionFloat(R.dimen.classic_signature_text_size));
        this.mSignatureTextInEditor.setTypeface(FontUtils.getMiSans(380));
        this.mSignatureTextInEditor.setMaxWidth(getDimen(R.dimen.classic_text_signature_max_width));
    }
}
