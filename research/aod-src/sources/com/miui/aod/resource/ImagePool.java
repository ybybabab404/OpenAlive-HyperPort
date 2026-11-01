package com.miui.aod.resource;

import androidx.collection.ArrayMap;
import com.miui.aod.R;
import com.miui.aod.components.DrawableGravityData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class ImagePool {
    public static final List sAlignTypeDrawableData;
    public static final Map sBgMap;

    static {
        ArrayMap arrayMap = new ArrayMap();
        sBgMap = arrayMap;
        ArrayList arrayList = new ArrayList();
        sAlignTypeDrawableData = arrayList;
        arrayMap.put("paint", Integer.valueOf(R.drawable.aod_bg_paint));
        arrayMap.put("tree", Integer.valueOf(R.drawable.aod_bg_tree));
        arrayMap.put("spirit", Integer.valueOf(R.drawable.aod_bg_spirit));
        arrayMap.put("succulent", Integer.valueOf(R.drawable.aod_bg_succulent));
        arrayMap.put("cactus", Integer.valueOf(R.drawable.aod_bg_cactus));
        arrayMap.put("shadow", Integer.valueOf(R.drawable.aod_bg_shadow));
        arrayMap.put("moonlight", Integer.valueOf(R.drawable.aod_bg_moonlight));
        arrayMap.put("ghost", Integer.valueOf(R.drawable.aod_bg_ghost));
        arrayMap.put("spaceman", Integer.valueOf(R.drawable.aod_bg_spaceman));
        arrayMap.put("imageselector", Integer.valueOf(R.drawable.image_selector_smallview));
        arrayList.add(new DrawableGravityData(R.drawable.text_style_vertical_left, 515, "align1", R.string.align_left_vertically));
        arrayList.add(new DrawableGravityData(R.drawable.text_style_vertical_center, 513, "align2", R.string.align_center_vertically));
        arrayList.add(new DrawableGravityData(R.drawable.text_style_vertical_right, 517, "align3", R.string.align_right_vertically));
        arrayList.add(new DrawableGravityData(R.drawable.text_style_horizontal_left, 1027, "align4", R.string.align_left_horizontally));
        arrayList.add(new DrawableGravityData(R.drawable.text_style_horizontal_center, 1025, "align5", R.string.align_center_horizontally));
        arrayList.add(new DrawableGravityData(R.drawable.text_style_horizontal_right, 1029, "align6", R.string.align_right_horizontally));
    }

    public static int getDrawableIndexByGravity(int i) {
        int i2 = 0;
        while (true) {
            List list = sAlignTypeDrawableData;
            if (i2 >= list.size()) {
                return -1;
            }
            if (((DrawableGravityData) list.get(i2)).gravity == i) {
                return i2;
            }
            i2++;
        }
    }

    public static Integer getImageRes(String str) {
        return (Integer) sBgMap.getOrDefault(str, 0);
    }

    public static Integer getImageThumbnailRes(String str) {
        return (Integer) sBgMap.getOrDefault(str, 0);
    }
}
