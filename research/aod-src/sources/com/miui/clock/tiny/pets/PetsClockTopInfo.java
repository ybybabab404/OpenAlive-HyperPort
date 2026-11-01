package com.miui.clock.tiny.pets;

import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockBean;
import com.miui.clock.tiny.model.TinyClockInfo;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PetsClockTopInfo extends TinyClockInfo {
    public static final List mStyleList;
    public int mInfoColor;

    static {
        ArrayList arrayList = new ArrayList();
        mStyleList = arrayList;
        arrayList.add(new PetStyle(-1, -1));
    }

    public PetsClockTopInfo(TinyClockBean tinyClockBean, int i) {
        super(tinyClockBean, i);
        this.mInfoColor = -1;
        setClockStyle(tinyClockBean.getStyle());
        if (tinyClockBean.getScale() > 0.0f) {
            setScale(tinyClockBean.getScale());
        }
    }

    private int getLayoutIDNormal(int i) {
        return i != 2 ? R.layout.layout_pets_clock_top_view_camera_left : R.layout.layout_pets_clock_top_view_camera_right;
    }

    public int getInfoColor() {
        return this.mInfoColor;
    }

    @Override // com.miui.clock.tiny.model.TinyClockInfo
    public int getLayoutId(int i) {
        return getLayoutIDNormal(i);
    }
}
