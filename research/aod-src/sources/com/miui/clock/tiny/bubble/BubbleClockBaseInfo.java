package com.miui.clock.tiny.bubble;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.miui.clock.tiny.model.TinyClockBean;
import com.miui.clock.tiny.model.TinyClockInfo;
import com.miui.clock.tiny.utils.BitmapUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class BubbleClockBaseInfo extends TinyClockInfo {
    public int[] mAodResource;
    public String[] mLockResourceNames;
    public int mNotificationColor;
    public static final int DEFAULT_PRIMARY_COLOR = Color.parseColor("#000000");
    public static final int DEFAULT_SECONDARY_COLOR = Color.parseColor("#FFFFFF");
    public static final int DEFAULT_SECONDARY_BLEND_LIGHT_COLOR = Color.parseColor("#ABABAB");
    public static final int DEFAULT_SECONDARY_BLEND_DARK_COLOR = Color.parseColor("#FFFFFF");
    public static final int DATE_WEEK_STYLE2_COLOR = Color.parseColor("#2E006F");
    public static final int DATE_WEEK_STYLE3_COLOR = Color.parseColor("#6E1C00");
    public static final int DATE_WEEK_STYLE4_COLOR = Color.parseColor("#002920");
    public static final int DATE_WEEK_STYLE5_COLOR = Color.parseColor("#003E7D");
    public static final int DATE_WEEK_CLOCK_STYLE2_AOD_COLOR = Color.parseColor("#C3B3DB");
    public static final int DATE_WEEK_CLOCK_STYLE3_AOD_COLOR = Color.parseColor("#D7A493");
    public static final int DATE_WEEK_CLOCK_STYLE4_AOD_COLOR = Color.parseColor("#569184");
    public static final int DATE_WEEK_CLOCK_STYLE5_AOD_COLOR = Color.parseColor("#88B2DE");

    public BubbleClockBaseInfo(TinyClockBean tinyClockBean, int i) {
        super(tinyClockBean, i);
        this.mNotificationColor = -1;
        int style = tinyClockBean.getStyle();
        setClockStyle(style);
        if (tinyClockBean.getPrimaryColor() != 0) {
            setPrimaryColor(tinyClockBean.getPrimaryColor());
        } else {
            setPrimaryColor(DEFAULT_PRIMARY_COLOR);
        }
        setWallpaperDark0(tinyClockBean.isWallpaperDark());
        setWallpaperDark180(tinyClockBean.isWallpaperDark180());
        setSecondaryAndBlendColorByStyle(tinyClockBean);
        if (tinyClockBean.getScale() != 0.0f) {
            setScale(tinyClockBean.getScale());
        }
        setAODColor(getAodColorByStyle(style));
    }

    public final int getAodColorByStyle(int i) {
        if (i == 0 || i == 1) {
            return -1;
        }
        if (i == 2) {
            return DATE_WEEK_CLOCK_STYLE2_AOD_COLOR;
        }
        if (i == 3) {
            return DATE_WEEK_CLOCK_STYLE3_AOD_COLOR;
        }
        if (i == 4) {
            return DATE_WEEK_CLOCK_STYLE4_AOD_COLOR;
        }
        if (i == 5) {
            return DATE_WEEK_CLOCK_STYLE5_AOD_COLOR;
        }
        return -1;
    }

    public int[] getAodResource() {
        return this.mAodResource;
    }

    public Drawable getCLockDrawable(int i) {
        String[] strArr = this.mLockResourceNames;
        if (strArr == null) {
            Log.d("BubbleClockBaseInfo", "please initClockDigtalResources");
            return null;
        }
        if (i >= 0 && i < strArr.length) {
            return BitmapUtils.INSTANCE.getDrawable("/system/media/wallpaper/tiny_clock_resources/" + this.mLockResourceNames[i] + ".png", this.mLockResourceNames[i]);
        }
        Log.e("BubbleClockBaseInfo", "getCLockDrawable num: " + i + " len" + this.mLockResourceNames.length);
        return null;
    }

    public void initClockDigtalResources() {
        this.mAodResource = FontBubble.getAodResource();
        this.mLockResourceNames = initResourcesNames(getClockStyle());
    }

    public final String[] initResourcesNames(int i) {
        if (i == 0 || i == 1) {
            return FontBubble.get3DResourceNames();
        }
        if (i == 2) {
            return FontBubble.get3DSolidResourceNamesForStyle2();
        }
        if (i == 3) {
            return FontBubble.get3DSolidResourceNamesForStyle3();
        }
        if (i == 4) {
            return FontBubble.get3DSolidResourceNamesForStyle4();
        }
        return i == 5 ? FontBubble.get3DSolidResourceNamesForStyle5() : FontBubble.get3DResourceNames();
    }

    public final void setSecondaryAndBlendColorByStyle(TinyClockBean tinyClockBean) {
        int style = tinyClockBean.getStyle();
        if (style == 0 || style == 1) {
            if (tinyClockBean.getSecondaryColor0() != 0) {
                setSecondaryColor0(tinyClockBean.getSecondaryColor0());
            } else {
                setSecondaryColor0(DEFAULT_SECONDARY_COLOR);
            }
            if (tinyClockBean.getSecondaryColor180() != 0) {
                setSecondaryColor180(tinyClockBean.getSecondaryColor180());
            } else {
                setSecondaryColor180(DEFAULT_SECONDARY_COLOR);
            }
            boolean zIsWallpaperDark = tinyClockBean.isWallpaperDark();
            boolean zIsWallpaperDark180 = tinyClockBean.isWallpaperDark180();
            if (tinyClockBean.getSecondaryBlendColor0() != 0) {
                setSecondaryBlendColor0(tinyClockBean.getSecondaryBlendColor0());
            } else {
                setSecondaryBlendColor0(zIsWallpaperDark ? DEFAULT_SECONDARY_BLEND_LIGHT_COLOR : DEFAULT_SECONDARY_BLEND_DARK_COLOR);
            }
            if (tinyClockBean.getSecondaryBlendColor180() != 0) {
                setSecondaryBlendColor180(tinyClockBean.getSecondaryBlendColor180());
                return;
            } else {
                setSecondaryBlendColor180(zIsWallpaperDark180 ? DEFAULT_SECONDARY_BLEND_LIGHT_COLOR : DEFAULT_SECONDARY_BLEND_DARK_COLOR);
                return;
            }
        }
        if (style == 2) {
            int i = DATE_WEEK_STYLE2_COLOR;
            setSecondaryColor0(i);
            setSecondaryColor180(i);
            return;
        }
        if (style == 3) {
            int i2 = DATE_WEEK_STYLE3_COLOR;
            setSecondaryColor0(i2);
            setSecondaryColor180(i2);
        } else if (style == 4) {
            int i3 = DATE_WEEK_STYLE4_COLOR;
            setSecondaryColor0(i3);
            setSecondaryColor180(i3);
        } else if (style == 5) {
            int i4 = DATE_WEEK_STYLE5_COLOR;
            setSecondaryColor0(i4);
            setSecondaryColor180(i4);
        }
    }
}
