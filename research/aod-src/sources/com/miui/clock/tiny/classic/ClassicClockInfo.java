package com.miui.clock.tiny.classic;

import android.graphics.Color;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockBean;
import com.miui.clock.tiny.model.TinyClockInfo;

/* JADX INFO: loaded from: classes.dex */
public class ClassicClockInfo extends TinyClockInfo {
    public static final int DEFAULT_PRIMARY_COLOR = Color.parseColor("#FFFFFF");
    public static final int DEFAULT_BLEND_LIGHT_COLOR = Color.parseColor("#ABABAB");
    public static final int DEFAULT_BLEND_DARK_COLOR = Color.parseColor("#FFFFFF");
    public static final int DEFAULT_AOD_PRIMARY_COLOR = Color.parseColor("#D7D7D7");

    public ClassicClockInfo(TinyClockBean tinyClockBean, int i) {
        super(tinyClockBean, i);
        if (tinyClockBean.getPrimaryColor() != 0) {
            setPrimaryColor0(tinyClockBean.getPrimaryColor());
        } else {
            setPrimaryColor0(DEFAULT_PRIMARY_COLOR);
        }
        if (tinyClockBean.getPrimaryColor180() != 0) {
            setPrimaryColor180(tinyClockBean.getPrimaryColor180());
        } else {
            setPrimaryColor180(DEFAULT_PRIMARY_COLOR);
        }
        if (tinyClockBean.getScale() != 0.0f) {
            setScale(tinyClockBean.getScale());
        }
        boolean zIsWallpaperDark = tinyClockBean.isWallpaperDark();
        setWallpaperDark0(zIsWallpaperDark);
        boolean zIsWallpaperDark180 = tinyClockBean.isWallpaperDark180();
        setWallpaperDark180(zIsWallpaperDark180);
        if (tinyClockBean.getBlendColor() != 0) {
            setBlendColor0(tinyClockBean.getBlendColor());
        } else {
            setBlendColor0(zIsWallpaperDark ? DEFAULT_BLEND_LIGHT_COLOR : DEFAULT_BLEND_DARK_COLOR);
        }
        if (tinyClockBean.getBlendColor180() != 0) {
            setBlendColor180(tinyClockBean.getBlendColor180());
        } else {
            setBlendColor180(zIsWallpaperDark180 ? DEFAULT_BLEND_LIGHT_COLOR : DEFAULT_BLEND_DARK_COLOR);
        }
        if (tinyClockBean.getAodColor() != 0) {
            setAODColor(tinyClockBean.getAodColor());
        } else {
            setAODColor(DEFAULT_AOD_PRIMARY_COLOR);
        }
    }

    public final int getLayoutIDAOD(int i) {
        return i != 2 ? R.layout.layout_classic_clock_view_aod_camera_left : R.layout.layout_classic_clock_view_aod_camera_right;
    }

    public final int getLayoutIDNormal(int i) {
        return i != 2 ? R.layout.layout_classic_clock_view_camera_left : R.layout.layout_classic_clock_view_camera_right;
    }

    @Override // com.miui.clock.tiny.model.TinyClockInfo
    public int getLayoutId(int i) {
        return getType() != 1 ? getLayoutIDNormal(i) : getLayoutIDAOD(i);
    }
}
