package com.miui.aod.resource;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.miui.aod.R;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.components.DrawableData;
import com.miui.aod.widget.SunSelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class AodDrawables {
    public static Context context;
    public static List s24GradientDrawableList;
    public static final Map sDrawableMap;
    public static final List sGradientDrawableList;
    public static final List sImageDrawableList;
    public static ArrayMap sLabelDrawables;
    public static List sPureDrawableList;
    public static ArrayList sSecondaryDrawableList;

    static {
        ArrayList arrayList = new ArrayList();
        sGradientDrawableList = arrayList;
        s24GradientDrawableList = new ArrayList();
        sPureDrawableList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        sImageDrawableList = arrayList2;
        sDrawableMap = new ArrayMap();
        sLabelDrawables = new ArrayMap();
        sSecondaryDrawableList = new ArrayList();
        context = null;
        arrayList.add(new DrawableData(R.drawable.aod_gradient_1, "aod_gradient_1", R.string.aod_gradient_1));
        arrayList.add(new DrawableData(R.drawable.aod_gradient_2, "aod_gradient_2", R.string.aod_gradient_2));
        arrayList.add(new DrawableData(R.drawable.aod_gradient_3, "aod_gradient_3", R.string.aod_gradient_3));
        arrayList.add(new DrawableData(R.drawable.aod_gradient_4, "aod_gradient_4", R.string.aod_gradient_4));
        arrayList.add(new DrawableData(R.drawable.aod_gradient_5, "aod_gradient_5", R.string.aod_gradient_5));
        arrayList.add(new DrawableData(R.drawable.aod_gradient_5_1, "aod_gradient_5_1", R.string.aod_gradient_5_1));
        arrayList.add(new DrawableData(R.drawable.aod_gradient_6, "aod_gradient_6", R.string.aod_gradient_6));
        arrayList.add(new DrawableData(R.drawable.aod_gradient_6_1, "aod_gradient_6_1", R.string.aod_gradient_6_1));
        arrayList.add(new DrawableData(R.drawable.aod_gradient_7, "aod_gradient_7", R.string.aod_gradient_7));
        arrayList.add(new DrawableData(R.drawable.aod_gradient_8, "aod_gradient_8", R.string.aod_gradient_8));
        arrayList.add(new DrawableData(R.drawable.aod_gradient_9, "aod_gradient_9", R.string.aod_gradient_9));
        arrayList.add(new DrawableData(R.drawable.aod_gradient_10, "aod_gradient_10", R.string.aod_gradient_10));
        arrayList.add(new DrawableData(R.drawable.aod_gradient_11, "aod_gradient_11", R.string.aod_gradient_11));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_1, "aod_pure_color_1", R.string.aod_pure_color_1));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_12, "aod_pure_color_12", R.string.aod_pure_color_12));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_13, "aod_pure_color_13", R.string.aod_pure_color_13));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_14, "aod_pure_color_14", R.string.aod_pure_color_14));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_15, "aod_pure_color_15", R.string.aod_pure_color_15));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_16, "aod_pure_color_16", R.string.aod_pure_color_16));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_17, "aod_pure_color_17", R.string.aod_pure_color_17));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_18, "aod_pure_color_18", R.string.aod_pure_color_18));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_19, "aod_pure_color_19", R.string.aod_pure_color_19));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_20, "aod_pure_color_20", R.string.aod_pure_color_20));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_2, "aod_pure_color_2", R.string.aod_pure_color_2));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_3, "aod_pure_color_3", R.string.aod_pure_color_3));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_4, "aod_pure_color_4", R.string.aod_pure_color_4));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_5, "aod_pure_color_5", R.string.aod_pure_color_5));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_6, "aod_pure_color_6", R.string.aod_pure_color_6));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_7, "aod_pure_color_7", R.string.aod_pure_color_7));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_8, "aod_pure_color_8", R.string.aod_pure_color_8));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_9, "aod_pure_color_9", R.string.aod_pure_color_9));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_10, "aod_pure_color_10", R.string.aod_pure_color_10));
        sPureDrawableList.add(new DrawableData(R.color.aod_pure_color_11, "aod_pure_color_11", R.string.aod_pure_color_11));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_0));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_1));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_2));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_3));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_4));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_5));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_6));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_7));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_8));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_9));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_10));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_11));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_12));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_13));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_14));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_15));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_16));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_17));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_18));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_19));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_20));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_21));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_22));
        s24GradientDrawableList.add(new DrawableData(R.drawable.aod_24_gradient_23));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_1, "aod_secondary_color_1"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_2, "aod_secondary_color_2"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_3, "aod_secondary_color_3"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_4, "aod_secondary_color_4"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_5, "aod_secondary_color_5"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_6, "aod_secondary_color_6"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_7, "aod_secondary_color_7"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_8, "aod_secondary_color_8"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_9, "aod_secondary_color_9"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_10, "aod_secondary_color_10"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_11, "aod_secondary_color_11"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_12, "aod_secondary_color_12"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_13, "aod_secondary_color_13"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_14, "aod_secondary_color_14"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_15, "aod_secondary_color_15"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_16, "aod_secondary_color_16"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_17, "aod_secondary_color_17"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_18, "aod_secondary_color_18"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_19, "aod_secondary_color_19"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_20, "aod_secondary_color_20"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_21, "aod_secondary_color_21"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_22, "aod_secondary_color_22"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_23, "aod_secondary_color_23"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_24, "aod_secondary_color_24"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_25, "aod_secondary_color_25"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_26, "aod_secondary_color_26"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_27, "aod_secondary_color_27"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_28, "aod_secondary_color_28"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_29, "aod_secondary_color_29"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_30, "aod_secondary_color_30"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_31, "aod_secondary_color_31"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_32, "aod_secondary_color_32"));
        sSecondaryDrawableList.add(new DrawableData(R.color.aod_secondary_color_33, "aod_secondary_color_33"));
        arrayList2.add(new DrawableData(ImagePool.getImageRes("tree").intValue(), "tree"));
        arrayList2.add(new DrawableData(ImagePool.getImageRes("spirit").intValue(), "spirit"));
        arrayList2.add(new DrawableData(ImagePool.getImageRes("succulent").intValue(), "succulent"));
        arrayList2.add(new DrawableData(ImagePool.getImageRes("cactus").intValue(), "cactus"));
        arrayList2.add(new DrawableData(ImagePool.getImageRes("ghost").intValue(), "ghost"));
        arrayList2.add(new DrawableData(ImagePool.getImageRes("spaceman").intValue(), "spaceman"));
        arrayList2.add(new DrawableData(ImagePool.getImageRes("paint").intValue(), "paint"));
        arrayList2.add(new DrawableData(ImagePool.getImageRes("shadow").intValue(), "shadow"));
        arrayList2.add(new DrawableData(ImagePool.getImageRes("moonlight").intValue(), "moonlight"));
        sLabelDrawables.put(CategoryConstants.LABEL_SINGLE_MOTION, Integer.valueOf(R.drawable.label_single_motion));
        sLabelDrawables.put(CategoryConstants.LABEL_SINGLE_24H, Integer.valueOf(R.drawable.label_single_24h));
        fillMap(arrayList);
        fillMap(sPureDrawableList);
        fillMap(arrayList2);
        fillMap(sSecondaryDrawableList);
        HashSet hashSet = new HashSet();
        hashSet.add(new DrawableData(R.drawable.signature_thumbnail_0, "signature_thumbnail_0"));
        hashSet.add(new DrawableData(R.drawable.signature_thumbnail_1, "signature_thumbnail_1"));
        hashSet.add(new DrawableData(R.drawable.signature_thumbnail_2, "signature_thumbnail_2"));
        hashSet.add(new DrawableData(R.drawable.signature_thumbnail_3, "signature_thumbnail_3"));
        hashSet.add(new DrawableData(R.drawable.signature_thumbnail_4, "signature_thumbnail_4"));
        hashSet.add(new DrawableData(R.drawable.signature_thumbnail_international_0, "signature_thumbnail_international_0"));
        hashSet.add(new DrawableData(R.drawable.signature_thumbnail_international_1, "signature_thumbnail_international_1"));
        fillMap(hashSet);
    }

    public static boolean containsDrawableData(String str) {
        return sDrawableMap.containsKey(str);
    }

    public static void fillMap(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            DrawableData drawableData = (DrawableData) it.next();
            sDrawableMap.put(drawableData.mName, drawableData);
        }
    }

    public static int get24GradientDrawableRes() {
        return ((DrawableData) s24GradientDrawableList.get(SunSelector.getDrawableIndex())).mResId;
    }

    public static DrawableData getDrawableDataByName(String str) {
        return (DrawableData) sDrawableMap.get(str);
    }

    public static Drawable getDrawableForMask(Context context2, int i) {
        return DrawableCache.getInstance(context2).getDrawable(i);
    }

    public static List getDrawableListByCateName(String str) {
        ArrayList arrayList = new ArrayList();
        if (!CategoryConstants.CATEGORY_ONELINE.equals(str)) {
            if (CategoryConstants.CATEGORY_PAINT_VERTICAL.equals(str)) {
                arrayList.add(new DrawableData(ImagePool.getImageThumbnailRes("moonlight").intValue(), "moonlight"));
                arrayList.add(new DrawableData(ImagePool.getImageThumbnailRes("paint").intValue(), "paint"));
                arrayList.add(new DrawableData(ImagePool.getImageThumbnailRes("shadow").intValue(), "shadow"));
            }
            return arrayList;
        }
        arrayList.add(new DrawableData(ImagePool.getImageThumbnailRes("tree").intValue(), "tree"));
        arrayList.add(new DrawableData(ImagePool.getImageThumbnailRes("spaceman").intValue(), "spaceman"));
        arrayList.add(new DrawableData(ImagePool.getImageThumbnailRes("spirit").intValue(), "spirit"));
        arrayList.add(new DrawableData(ImagePool.getImageThumbnailRes("succulent").intValue(), "succulent"));
        arrayList.add(new DrawableData(ImagePool.getImageThumbnailRes("cactus").intValue(), "cactus"));
        arrayList.add(new DrawableData(ImagePool.getImageThumbnailRes("ghost").intValue(), "ghost"));
        return arrayList;
    }

    public static List getGradientDrawables() {
        return Collections.unmodifiableList(sGradientDrawableList);
    }

    public static List getPureDrawables() {
        return Collections.unmodifiableList(sPureDrawableList);
    }

    public static boolean isPureColor(int i) {
        Iterator it = sPureDrawableList.iterator();
        while (it.hasNext()) {
            if (((DrawableData) it.next()).mResId == i) {
                return true;
            }
        }
        return false;
    }

    public static String translateColorNameFromOldVersion(String str) {
        if (TextUtils.isEmpty(str) || str.equals("white")) {
            return "aod_pure_color_1";
        }
        if (str.equals("blue")) {
            return "aod_gradient_1";
        }
        if (str.equals("pink")) {
            return "aod_gradient_2";
        }
        return null;
    }
}
