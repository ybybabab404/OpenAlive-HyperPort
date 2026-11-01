package com.miui.clock.tiny.splicing;

import android.graphics.Color;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockBean;
import com.miui.clock.tiny.model.TinyClockInfo;

/* JADX INFO: loaded from: classes.dex */
public class SplicingClockInfo extends TinyClockInfo {
    public static final int DEFAULT_PRIMARY_COLOR = Color.parseColor("#000000");
    public static final int DEFAULT_AOD_PRIMARY_COLOR = Color.parseColor("#D7D7D7");

    public SplicingClockInfo(TinyClockBean tinyClockBean, int i) {
        super(tinyClockBean, i);
        if (tinyClockBean.getPrimaryColor() != 0) {
            setPrimaryColor(tinyClockBean.getPrimaryColor());
        } else {
            setPrimaryColor(DEFAULT_PRIMARY_COLOR);
        }
        if (tinyClockBean.getScale() != 0.0f) {
            setScale(tinyClockBean.getScale());
        }
        if (tinyClockBean.getAodColor() != 0) {
            setAODColor(tinyClockBean.getAodColor());
        } else {
            setAODColor(DEFAULT_AOD_PRIMARY_COLOR);
        }
    }

    private int getLayoutIDAOD(int i) {
        return i != 2 ? R.layout.layout_splicing_clock_view_aod_camera_left : R.layout.layout_splicing_clock_view_aod_camera_right;
    }

    private int getLayoutIDNormal(int i) {
        return i != 2 ? R.layout.layout_splicing_clock_view_camera_left : R.layout.layout_splicing_clock_view_camera_right;
    }

    @Override // com.miui.clock.tiny.model.TinyClockInfo
    public int getLayoutId(int i) {
        return getType() != 1 ? getLayoutIDNormal(i) : getLayoutIDAOD(i);
    }
}
