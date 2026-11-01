package com.miui.clock.tiny.pets;

import android.graphics.Color;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockBean;
import com.miui.clock.tiny.model.TinyClockInfo;

/* JADX INFO: loaded from: classes.dex */
public class PetsClockInfo extends TinyClockInfo {
    public int mColonColor;
    public int mInfoColor;
    public int mTimeColor;
    public static final int DEFAULT_AOD_TIME_COLOR = Color.parseColor("#D7D7D7");
    public static final int TIME_STYLE1_COLOR = Color.parseColor("#FFFFFF");
    public static final int COLON_STYLE1_COLOR = Color.parseColor("#FFAAA4");
    public static final int TIME_STYLE2_COLOR = Color.parseColor("#FFC93D");
    public static final int COLON_STYLE2_COLOR = Color.parseColor("#7289FF");
    public static final int TIME_STYLE3_COLOR = Color.parseColor("#FFC93D");
    public static final int COLON_STYLE3_COLOR = Color.parseColor("#4176C5");
    public static final int TIME_STYLE4_COLOR = Color.parseColor("#FFC93D");
    public static final int COLON_STYLE4_COLOR = Color.parseColor("#D08053");
    public static final int TIME_STYLE5_COLOR = Color.parseColor("#FF685E");
    public static final int COLON_STYLE5_COLOR = Color.parseColor("#FFB46E");
    public static final int TIME_STYLE6_COLOR = Color.parseColor("#74F181");
    public static final int COLON_STYLE6_COLOR = Color.parseColor("#FFC93D");
    public static final int AOD_TIME_STYLE1_COLOR = Color.parseColor("#D7D7D7");
    public static final int AOD_COLON_STYLE1_COLOR = Color.parseColor("#FFAAA4");
    public static final int AOD_TIME_STYLE2_COLOR = Color.parseColor("#E9B738");
    public static final int AOD_COLON_STYLE2_COLOR = Color.parseColor("#7289FF");
    public static final int AOD_TIME_STYLE3_COLOR = Color.parseColor("#E9B738");
    public static final int AOD_COLON_STYLE3_COLOR = Color.parseColor("#4176C5");
    public static final int AOD_TIME_STYLE4_COLOR = Color.parseColor("#E9B738");
    public static final int AOD_COLON_STYLE4_COLOR = Color.parseColor("#D08053");
    public static final int AOD_TIME_STYLE5_COLOR = Color.parseColor("#EF675E");
    public static final int AOD_COLON_STYLE5_COLOR = Color.parseColor("#FFB46E");
    public static final int AOD_TIME_STYLE6_COLOR = Color.parseColor("#68D874");
    public static final int AOD_COLON_STYLE6_COLOR = Color.parseColor("#FFC93D");

    public PetsClockInfo(TinyClockBean tinyClockBean, int i) {
        super(tinyClockBean, i);
        this.mTimeColor = -1;
        this.mColonColor = -1;
        this.mInfoColor = -1;
        int style = tinyClockBean.getStyle();
        setClockStyle(style);
        if (i == 1) {
            setTimeColor(getTimeAodColor(style));
            setColonColor(getColonAodColor(style));
            setInfoColor(getInfoAodColor());
        } else {
            setTimeColor(getTimeNormalColor(style));
            setColonColor(getColonNormalColor(style));
            setInfoColor(getInfoNormalColor());
        }
        if (tinyClockBean.getScale() > 0.0f) {
            setScale(tinyClockBean.getScale());
        }
        setPrimaryColor(tinyClockBean.getPrimaryColor());
    }

    public static int getColonNormalColor(int i) {
        if (i == 1) {
            return COLON_STYLE1_COLOR;
        }
        if (i == 2) {
            return COLON_STYLE2_COLOR;
        }
        if (i == 3) {
            return COLON_STYLE3_COLOR;
        }
        if (i == 4) {
            return COLON_STYLE4_COLOR;
        }
        if (i == 5) {
            return COLON_STYLE5_COLOR;
        }
        return i == 6 ? COLON_STYLE6_COLOR : COLON_STYLE1_COLOR;
    }

    private int getLayoutIDAOD(int i) {
        return i != 2 ? R.layout.layout_pets_aod_clock_view_camera_left : R.layout.layout_pets_aod_clock_view_camera_right;
    }

    private int getLayoutIDNormal(int i) {
        return i != 2 ? R.layout.layout_pets_clock_view_camera_left : R.layout.layout_pets_clock_view_camera_right;
    }

    public int getColonAodColor(int i) {
        if (i == 1) {
            return AOD_COLON_STYLE1_COLOR;
        }
        if (i == 2) {
            return AOD_COLON_STYLE2_COLOR;
        }
        if (i == 3) {
            return AOD_COLON_STYLE3_COLOR;
        }
        if (i == 4) {
            return AOD_COLON_STYLE4_COLOR;
        }
        if (i == 5) {
            return AOD_COLON_STYLE5_COLOR;
        }
        return i == 6 ? AOD_COLON_STYLE6_COLOR : AOD_COLON_STYLE1_COLOR;
    }

    public int getColonColor() {
        return this.mColonColor;
    }

    public int getInfoAodColor() {
        return DEFAULT_AOD_TIME_COLOR;
    }

    public int getInfoColor() {
        return this.mInfoColor;
    }

    public int getInfoNormalColor() {
        return -1;
    }

    @Override // com.miui.clock.tiny.model.TinyClockInfo
    public int getLayoutId(int i) {
        return getType() != 1 ? getLayoutIDNormal(i) : getLayoutIDAOD(i);
    }

    public int getTimeAodColor(int i) {
        if (i == 1) {
            return AOD_TIME_STYLE1_COLOR;
        }
        if (i == 2) {
            return AOD_TIME_STYLE2_COLOR;
        }
        if (i == 3) {
            return AOD_TIME_STYLE3_COLOR;
        }
        if (i == 4) {
            return AOD_TIME_STYLE4_COLOR;
        }
        if (i == 5) {
            return AOD_TIME_STYLE5_COLOR;
        }
        return i == 6 ? AOD_TIME_STYLE6_COLOR : AOD_TIME_STYLE1_COLOR;
    }

    public int getTimeColor() {
        return this.mTimeColor;
    }

    public int getTimeNormalColor(int i) {
        if (i == 1) {
            return TIME_STYLE1_COLOR;
        }
        if (i == 2) {
            return TIME_STYLE2_COLOR;
        }
        if (i == 3) {
            return TIME_STYLE3_COLOR;
        }
        if (i == 4) {
            return TIME_STYLE4_COLOR;
        }
        if (i == 5) {
            return TIME_STYLE5_COLOR;
        }
        return i == 6 ? TIME_STYLE6_COLOR : TIME_STYLE1_COLOR;
    }

    public void setColonColor(int i) {
        this.mColonColor = i;
    }

    public void setInfoColor(int i) {
        this.mInfoColor = i;
    }

    public void setTimeColor(int i) {
        this.mTimeColor = i;
    }
}
