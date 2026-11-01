package com.miui.aod.category;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import androidx.collection.ArrayMap;
import com.google.gson.annotations.SerializedName;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.DrawableGravityData;
import com.miui.aod.components.view.TextStyleSelectView;
import com.miui.aod.components.view.VerticalTextView;
import com.miui.aod.resource.ImagePool;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.SignatureClock;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TextStyleCategoryInfo extends CategoryInfo {
    public static final int MAX_FONT_WEIGHT = 700;
    public static final float MAX_TEXTSIZE;
    public static final int MIN_FONT_WEIGHT = 150;
    public static final float MIN_TEXTSIZE = 15.0f;
    public static final int PURE_GRAVITY_MASK = 7;
    public static final int TEXTSTYLE_CENTER = 1;
    public static final int TEXTSTYLE_CENTERVERTICAL_MASK = 16;
    public static final int TEXTSTYLE_HORIZONTAL = 1024;
    public static final int TEXTSTYLE_LEFT = 3;
    public static final int TEXTSTYLE_RIGHT = 5;
    public static final int TEXTSTYLE_VERTICAL = 512;

    @SerializedName("content")
    private String mContent;

    @SerializedName("font_weight")
    private int mFontWeight;

    @SerializedName("gravity")
    private int mGravity;

    @SerializedName("letter_spacing")
    private float mLetterSpacing;

    @SerializedName("line_spacing")
    private float mLineSpacing;

    @SerializedName("showed_content")
    private String mShowedContent;
    private boolean mTempShowExtra;

    @SerializedName("text_format")
    private VerticalTextView.TextFormat mTextFormat;

    @SerializedName("text_size")
    private float mTextSize;

    public @interface IAlignType {
    }

    static {
        MAX_TEXTSIZE = Utils.isFoldDevice() ? 190.0f : 196.0f;
    }

    public TextStyleCategoryInfo() {
        super(CategoryConstants.CATEGORY_TEXTSTYLE);
        this.mGravity = 513;
        this.mFontWeight = 630;
        this.mTextSize = 46.0f;
        this.mLineSpacing = Float.MAX_VALUE;
        this.mLetterSpacing = 0.0f;
        this.mTempShowExtra = true;
    }

    public boolean canShowExtraInfo() {
        return this.mTempShowExtra;
    }

    @Override // com.miui.aod.common.StyleInfo
    public IAodClock createAodClock(boolean z, int i) {
        return new SignatureClock(z, i);
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getBatteryMask() {
        return getClockColor();
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getClockBgName() {
        return this.mClockBg;
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getColorName() {
        String colorName = super.getColorName();
        return TextUtils.isEmpty(colorName) ? "aod_gradient_4" : colorName;
    }

    public String getContent() {
        return this.mContent;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getDefaultClockBg() {
        return ImagePool.getImageRes(getDefaultClockBgName()).intValue();
    }

    public int getFontWeight() {
        return this.mFontWeight;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public float getLetterSpacing() {
        return this.mLetterSpacing;
    }

    public float getLineSpacing() {
        return this.mLineSpacing;
    }

    @Override // com.miui.aod.common.StyleInfo
    public Map<String, Object> getParameterForAnalytic() {
        String str;
        ArrayMap arrayMap = new ArrayMap();
        int gravity = getGravity();
        for (DrawableGravityData drawableGravityData : ImagePool.sAlignTypeDrawableData) {
            if (drawableGravityData.gravity == gravity) {
                str = drawableGravityData.mAlignNameForAnalytic;
                arrayMap.put("style_event", "signature");
                arrayMap.put("style_name", "signature");
                arrayMap.put("style_type", str);
                return arrayMap;
            }
        }
        str = "";
        arrayMap.put("style_event", "signature");
        arrayMap.put("style_name", "signature");
        arrayMap.put("style_type", str);
        return arrayMap;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getPreviewTopPaddingResId() {
        return R.dimen.aod_preview_middle_top_padding;
    }

    public int getPureGravity() {
        return (isVertical() ? 0 : 16) | (this.mGravity & 7);
    }

    public String getShowedContent() {
        return this.mShowedContent;
    }

    @Override // com.miui.aod.common.StyleInfo
    public long getStartDelay() {
        return 300L;
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getStyleSelectViewName() {
        return TextStyleSelectView.class.getName();
    }

    public VerticalTextView.TextFormat getTextFormat() {
        return this.mTextFormat;
    }

    public float getTextSize() {
        return this.mTextSize;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTitleResId() {
        return R.string.signature;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTopMargin(Context context) {
        return 0;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean hasAnimation() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void initConfig(Context context) {
        Context applicationContext = context.getApplicationContext();
        if (!Utils.isAodProcess()) {
            applicationContext = AODApplication.sInstance;
        }
        if (TextUtils.isEmpty(this.mContent)) {
            this.mContent = applicationContext.getResources().getString(R.string.signature_default_content);
        }
    }

    public boolean isGravityLeft() {
        return (this.mGravity & 3) == 3;
    }

    public boolean isGravityRight() {
        return (this.mGravity & 5) == 5;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isSameHistoryRecord(StyleInfo styleInfo) {
        String str;
        return (styleInfo instanceof TextStyleCategoryInfo) && (str = this.mContent) != null && str.equals(((TextStyleCategoryInfo) styleInfo).getContent());
    }

    public boolean isVertical() {
        return (this.mGravity & 512) != 0;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean needInflateNotification() {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setBg(View view, int i) {
        View viewFindViewById = view.findViewById(R.id.aod_bg);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(8);
        }
    }

    public void setCanShowExtraInfo(boolean z) {
        this.mTempShowExtra = z;
    }

    public void setContent(String str) {
        this.mContent = str;
    }

    public void setFontWeight(int i) {
        this.mFontWeight = i;
    }

    public void setGravity(int i) {
        this.mGravity = i;
    }

    public void setLetterSpacing(float f) {
        this.mLetterSpacing = f;
    }

    public void setLineSpacing(float f) {
        this.mLineSpacing = f;
    }

    public void setShowedContent(String str) {
        this.mShowedContent = str;
    }

    public void setTextFormat(VerticalTextView.TextFormat textFormat) {
        this.mTextFormat = textFormat;
    }

    public void setTextSize(float f) {
        this.mTextSize = f;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportBatteryIcon() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportChangeColorAlongTime() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportDateAndTime() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportHideClock() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportLunarTime(Context context) {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportNotificationIcon() {
        return true;
    }
}
