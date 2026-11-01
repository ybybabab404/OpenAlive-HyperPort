package com.miui.aod.widget;

import android.content.Context;
import android.util.Log;
import androidx.collection.ArrayMap;
import com.miui.aod.R;
import com.miui.aod.category.AnimateClockPanelCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.theme.beans.WaveClockStyleBean;
import com.miui.aod.theme.utils.ThemeUtils;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
public class FrameAnimationManager {
    public static final int[] CLOCK_PANEL_ANIMATION_FIRST_ARRAYS;
    public static final int[] CLOCK_PANEL_ANIMATION_FORTH_ARRAYS;
    public static final int[] CLOCK_PANEL_ANIMATION_SECOND_ARRAYS;
    public static final int[] CLOCK_PANEL_ANIMATION_THIRD_ARRAYS;
    public static final Map sFrameInfoMap;

    public static class FrameAnimationInfo {
        public final int mFps;
        public final String mResDirPath;
        public final String mResFileNameDot;
        public final String mResFileNameHour;
        public final String mResFileNameMin;
        public final String[] mResFilePathArrays;
        public final String mResFilePathBg;
        public final int[] mResIdArrays;
        public final int mResIdBg;
        public final int mResIdDot;
        public final int mResIdHour;
        public final int mResIdMin;

        public FrameAnimationInfo(int[] iArr, int i, int i2, int i3, int i4, int i5) {
            this.mFps = i2;
            this.mResIdArrays = iArr;
            this.mResIdBg = i;
            this.mResIdHour = i3;
            this.mResIdMin = i4;
            this.mResIdDot = i5;
            this.mResFilePathArrays = null;
            this.mResFilePathBg = null;
            this.mResFileNameHour = null;
            this.mResFileNameMin = null;
            this.mResDirPath = null;
            this.mResFileNameDot = null;
        }

        public FrameAnimationInfo(String[] strArr, String str, String str2, String str3, String str4, String str5) {
            this.mResIdArrays = null;
            this.mResIdBg = -1;
            this.mResIdHour = -1;
            this.mResIdMin = -1;
            this.mResIdDot = -1;
            this.mFps = 30;
            this.mResFilePathArrays = strArr;
            this.mResFilePathBg = str2;
            this.mResFileNameHour = str3;
            this.mResFileNameMin = str4;
            this.mResFileNameDot = str5;
            this.mResDirPath = str;
        }

        public String getResFileNameDot() {
            return this.mResDirPath + File.separator + this.mResFileNameDot;
        }

        public String getResFileNameHour() {
            return this.mResDirPath + File.separator + this.mResFileNameHour;
        }

        public String getResFileNameMin() {
            return this.mResDirPath + File.separator + this.mResFileNameMin;
        }

        public boolean resourceFromFile() {
            return this.mResDirPath != null;
        }
    }

    static {
        int[] iArr = {R.drawable.aod_clock_panel_animation_first_1, R.drawable.aod_clock_panel_animation_first_2, R.drawable.aod_clock_panel_animation_first_3, R.drawable.aod_clock_panel_animation_first_4, R.drawable.aod_clock_panel_animation_first_5, R.drawable.aod_clock_panel_animation_first_6, R.drawable.aod_clock_panel_animation_first_7, R.drawable.aod_clock_panel_animation_first_8, R.drawable.aod_clock_panel_animation_first_9, R.drawable.aod_clock_panel_animation_first_10, R.drawable.aod_clock_panel_animation_first_11, R.drawable.aod_clock_panel_animation_first_12, R.drawable.aod_clock_panel_animation_first_13, R.drawable.aod_clock_panel_animation_first_14, R.drawable.aod_clock_panel_animation_first_15, R.drawable.aod_clock_panel_animation_first_16, R.drawable.aod_clock_panel_animation_first_17, R.drawable.aod_clock_panel_animation_first_18, R.drawable.aod_clock_panel_animation_first_19, R.drawable.aod_clock_panel_animation_first_20, R.drawable.aod_clock_panel_animation_first_21, R.drawable.aod_clock_panel_animation_first_22, R.drawable.aod_clock_panel_animation_first_23, R.drawable.aod_clock_panel_animation_first_24, R.drawable.aod_clock_panel_animation_first_25, R.drawable.aod_clock_panel_animation_first_26, R.drawable.aod_clock_panel_animation_first_27, R.drawable.aod_clock_panel_animation_first_28, R.drawable.aod_clock_panel_animation_first_29, R.drawable.aod_clock_panel_animation_first_30, R.drawable.aod_clock_panel_animation_first_31, R.drawable.aod_clock_panel_animation_first_32, R.drawable.aod_clock_panel_animation_first_33, R.drawable.aod_clock_panel_animation_first_34, R.drawable.aod_clock_panel_animation_first_35, R.drawable.aod_clock_panel_animation_first_36, R.drawable.aod_clock_panel_animation_first_37, R.drawable.aod_clock_panel_animation_first_38, R.drawable.aod_clock_panel_animation_first_39, R.drawable.aod_clock_panel_animation_first_40, R.drawable.aod_clock_panel_animation_first_41, R.drawable.aod_clock_panel_animation_first_42, R.drawable.aod_clock_panel_animation_first_43, R.drawable.aod_clock_panel_animation_first_44, R.drawable.aod_clock_panel_animation_first_45, R.drawable.aod_clock_panel_animation_first_46, R.drawable.aod_clock_panel_animation_first_47, R.drawable.aod_clock_panel_animation_first_48, R.drawable.aod_clock_panel_animation_first_49, R.drawable.aod_clock_panel_animation_first_50, R.drawable.aod_clock_panel_animation_first_51, R.drawable.aod_clock_panel_animation_first_52, R.drawable.aod_clock_panel_animation_first_53, R.drawable.aod_clock_panel_animation_first_54, R.drawable.aod_clock_panel_animation_first_55, R.drawable.aod_clock_panel_animation_first_56, R.drawable.aod_clock_panel_animation_first_57, R.drawable.aod_clock_panel_animation_first_58, R.drawable.aod_clock_panel_animation_first_59, R.drawable.aod_clock_panel_animation_first_60};
        CLOCK_PANEL_ANIMATION_FIRST_ARRAYS = iArr;
        int[] iArr2 = {R.drawable.aod_clock_panel_animation_second_1, R.drawable.aod_clock_panel_animation_second_2, R.drawable.aod_clock_panel_animation_second_3, R.drawable.aod_clock_panel_animation_second_4, R.drawable.aod_clock_panel_animation_second_5, R.drawable.aod_clock_panel_animation_second_6, R.drawable.aod_clock_panel_animation_second_7, R.drawable.aod_clock_panel_animation_second_8, R.drawable.aod_clock_panel_animation_second_9, R.drawable.aod_clock_panel_animation_second_10, R.drawable.aod_clock_panel_animation_second_11, R.drawable.aod_clock_panel_animation_second_12, R.drawable.aod_clock_panel_animation_second_13, R.drawable.aod_clock_panel_animation_second_14, R.drawable.aod_clock_panel_animation_second_15, R.drawable.aod_clock_panel_animation_second_16, R.drawable.aod_clock_panel_animation_second_17, R.drawable.aod_clock_panel_animation_second_18, R.drawable.aod_clock_panel_animation_second_19, R.drawable.aod_clock_panel_animation_second_20, R.drawable.aod_clock_panel_animation_second_21, R.drawable.aod_clock_panel_animation_second_22, R.drawable.aod_clock_panel_animation_second_23, R.drawable.aod_clock_panel_animation_second_24, R.drawable.aod_clock_panel_animation_second_25, R.drawable.aod_clock_panel_animation_second_26, R.drawable.aod_clock_panel_animation_second_27, R.drawable.aod_clock_panel_animation_second_28, R.drawable.aod_clock_panel_animation_second_29, R.drawable.aod_clock_panel_animation_second_30, R.drawable.aod_clock_panel_animation_second_31, R.drawable.aod_clock_panel_animation_second_32, R.drawable.aod_clock_panel_animation_second_33, R.drawable.aod_clock_panel_animation_second_34, R.drawable.aod_clock_panel_animation_second_35, R.drawable.aod_clock_panel_animation_second_36, R.drawable.aod_clock_panel_animation_second_37, R.drawable.aod_clock_panel_animation_second_38, R.drawable.aod_clock_panel_animation_second_39, R.drawable.aod_clock_panel_animation_second_40, R.drawable.aod_clock_panel_animation_second_41, R.drawable.aod_clock_panel_animation_second_42, R.drawable.aod_clock_panel_animation_second_43, R.drawable.aod_clock_panel_animation_second_44, R.drawable.aod_clock_panel_animation_second_45, R.drawable.aod_clock_panel_animation_second_46, R.drawable.aod_clock_panel_animation_second_47, R.drawable.aod_clock_panel_animation_second_48, R.drawable.aod_clock_panel_animation_second_49, R.drawable.aod_clock_panel_animation_second_50, R.drawable.aod_clock_panel_animation_second_51, R.drawable.aod_clock_panel_animation_second_52, R.drawable.aod_clock_panel_animation_second_53, R.drawable.aod_clock_panel_animation_second_54, R.drawable.aod_clock_panel_animation_second_55, R.drawable.aod_clock_panel_animation_second_56, R.drawable.aod_clock_panel_animation_second_57, R.drawable.aod_clock_panel_animation_second_58, R.drawable.aod_clock_panel_animation_second_59, R.drawable.aod_clock_panel_animation_second_60};
        CLOCK_PANEL_ANIMATION_SECOND_ARRAYS = iArr2;
        int[] iArr3 = {R.drawable.aod_clock_panel_animation_third_1, R.drawable.aod_clock_panel_animation_third_2, R.drawable.aod_clock_panel_animation_third_3, R.drawable.aod_clock_panel_animation_third_4, R.drawable.aod_clock_panel_animation_third_5, R.drawable.aod_clock_panel_animation_third_6, R.drawable.aod_clock_panel_animation_third_7, R.drawable.aod_clock_panel_animation_third_8, R.drawable.aod_clock_panel_animation_third_9, R.drawable.aod_clock_panel_animation_third_10, R.drawable.aod_clock_panel_animation_third_11, R.drawable.aod_clock_panel_animation_third_12, R.drawable.aod_clock_panel_animation_third_13, R.drawable.aod_clock_panel_animation_third_14, R.drawable.aod_clock_panel_animation_third_15, R.drawable.aod_clock_panel_animation_third_16, R.drawable.aod_clock_panel_animation_third_17, R.drawable.aod_clock_panel_animation_third_18, R.drawable.aod_clock_panel_animation_third_19, R.drawable.aod_clock_panel_animation_third_20, R.drawable.aod_clock_panel_animation_third_21, R.drawable.aod_clock_panel_animation_third_22, R.drawable.aod_clock_panel_animation_third_23, R.drawable.aod_clock_panel_animation_third_24, R.drawable.aod_clock_panel_animation_third_25, R.drawable.aod_clock_panel_animation_third_26, R.drawable.aod_clock_panel_animation_third_27, R.drawable.aod_clock_panel_animation_third_28, R.drawable.aod_clock_panel_animation_third_29, R.drawable.aod_clock_panel_animation_third_30, R.drawable.aod_clock_panel_animation_third_31, R.drawable.aod_clock_panel_animation_third_32, R.drawable.aod_clock_panel_animation_third_33, R.drawable.aod_clock_panel_animation_third_34, R.drawable.aod_clock_panel_animation_third_35, R.drawable.aod_clock_panel_animation_third_36, R.drawable.aod_clock_panel_animation_third_37, R.drawable.aod_clock_panel_animation_third_38, R.drawable.aod_clock_panel_animation_third_39, R.drawable.aod_clock_panel_animation_third_40, R.drawable.aod_clock_panel_animation_third_41, R.drawable.aod_clock_panel_animation_third_42, R.drawable.aod_clock_panel_animation_third_43, R.drawable.aod_clock_panel_animation_third_44, R.drawable.aod_clock_panel_animation_third_45, R.drawable.aod_clock_panel_animation_third_46, R.drawable.aod_clock_panel_animation_third_47, R.drawable.aod_clock_panel_animation_third_48, R.drawable.aod_clock_panel_animation_third_49, R.drawable.aod_clock_panel_animation_third_50, R.drawable.aod_clock_panel_animation_third_51, R.drawable.aod_clock_panel_animation_third_52, R.drawable.aod_clock_panel_animation_third_53, R.drawable.aod_clock_panel_animation_third_54, R.drawable.aod_clock_panel_animation_third_55, R.drawable.aod_clock_panel_animation_third_56, R.drawable.aod_clock_panel_animation_third_57, R.drawable.aod_clock_panel_animation_third_58, R.drawable.aod_clock_panel_animation_third_59, R.drawable.aod_clock_panel_animation_third_60, R.drawable.aod_clock_panel_animation_third_61};
        CLOCK_PANEL_ANIMATION_THIRD_ARRAYS = iArr3;
        int[] iArr4 = {R.drawable.aod_clock_panel_animation_forth_1, R.drawable.aod_clock_panel_animation_forth_2, R.drawable.aod_clock_panel_animation_forth_3, R.drawable.aod_clock_panel_animation_forth_4, R.drawable.aod_clock_panel_animation_forth_5, R.drawable.aod_clock_panel_animation_forth_6, R.drawable.aod_clock_panel_animation_forth_7, R.drawable.aod_clock_panel_animation_forth_8, R.drawable.aod_clock_panel_animation_forth_9, R.drawable.aod_clock_panel_animation_forth_10, R.drawable.aod_clock_panel_animation_forth_11, R.drawable.aod_clock_panel_animation_forth_12, R.drawable.aod_clock_panel_animation_forth_13, R.drawable.aod_clock_panel_animation_forth_14, R.drawable.aod_clock_panel_animation_forth_15, R.drawable.aod_clock_panel_animation_forth_16, R.drawable.aod_clock_panel_animation_forth_17, R.drawable.aod_clock_panel_animation_forth_18, R.drawable.aod_clock_panel_animation_forth_19, R.drawable.aod_clock_panel_animation_forth_20, R.drawable.aod_clock_panel_animation_forth_21, R.drawable.aod_clock_panel_animation_forth_22, R.drawable.aod_clock_panel_animation_forth_23, R.drawable.aod_clock_panel_animation_forth_24, R.drawable.aod_clock_panel_animation_forth_25, R.drawable.aod_clock_panel_animation_forth_26, R.drawable.aod_clock_panel_animation_forth_27, R.drawable.aod_clock_panel_animation_forth_28, R.drawable.aod_clock_panel_animation_forth_29, R.drawable.aod_clock_panel_animation_forth_30, R.drawable.aod_clock_panel_animation_forth_31, R.drawable.aod_clock_panel_animation_forth_32, R.drawable.aod_clock_panel_animation_forth_33, R.drawable.aod_clock_panel_animation_forth_34, R.drawable.aod_clock_panel_animation_forth_35, R.drawable.aod_clock_panel_animation_forth_36, R.drawable.aod_clock_panel_animation_forth_37, R.drawable.aod_clock_panel_animation_forth_38, R.drawable.aod_clock_panel_animation_forth_39, R.drawable.aod_clock_panel_animation_forth_40, R.drawable.aod_clock_panel_animation_forth_41, R.drawable.aod_clock_panel_animation_forth_42, R.drawable.aod_clock_panel_animation_forth_43, R.drawable.aod_clock_panel_animation_forth_44, R.drawable.aod_clock_panel_animation_forth_45, R.drawable.aod_clock_panel_animation_forth_46, R.drawable.aod_clock_panel_animation_forth_47, R.drawable.aod_clock_panel_animation_forth_48, R.drawable.aod_clock_panel_animation_forth_49, R.drawable.aod_clock_panel_animation_forth_50, R.drawable.aod_clock_panel_animation_forth_51, R.drawable.aod_clock_panel_animation_forth_52, R.drawable.aod_clock_panel_animation_forth_53, R.drawable.aod_clock_panel_animation_forth_54, R.drawable.aod_clock_panel_animation_forth_55, R.drawable.aod_clock_panel_animation_forth_56, R.drawable.aod_clock_panel_animation_forth_57, R.drawable.aod_clock_panel_animation_forth_58, R.drawable.aod_clock_panel_animation_forth_59, R.drawable.aod_clock_panel_animation_forth_60, R.drawable.aod_clock_panel_animation_forth_61, R.drawable.aod_clock_panel_animation_forth_62, R.drawable.aod_clock_panel_animation_forth_63, R.drawable.aod_clock_panel_animation_forth_64, R.drawable.aod_clock_panel_animation_forth_65, R.drawable.aod_clock_panel_animation_forth_66, R.drawable.aod_clock_panel_animation_forth_67, R.drawable.aod_clock_panel_animation_forth_68, R.drawable.aod_clock_panel_animation_forth_69, R.drawable.aod_clock_panel_animation_forth_70, R.drawable.aod_clock_panel_animation_forth_71, R.drawable.aod_clock_panel_animation_forth_72, R.drawable.aod_clock_panel_animation_forth_73, R.drawable.aod_clock_panel_animation_forth_74, R.drawable.aod_clock_panel_animation_forth_75, R.drawable.aod_clock_panel_animation_forth_76, R.drawable.aod_clock_panel_animation_forth_77, R.drawable.aod_clock_panel_animation_forth_78, R.drawable.aod_clock_panel_animation_forth_79, R.drawable.aod_clock_panel_animation_forth_80, R.drawable.aod_clock_panel_animation_forth_81, R.drawable.aod_clock_panel_animation_forth_82, R.drawable.aod_clock_panel_animation_forth_83, R.drawable.aod_clock_panel_animation_forth_84, R.drawable.aod_clock_panel_animation_forth_85, R.drawable.aod_clock_panel_animation_forth_86, R.drawable.aod_clock_panel_animation_forth_87, R.drawable.aod_clock_panel_animation_forth_88, R.drawable.aod_clock_panel_animation_forth_89, R.drawable.aod_clock_panel_animation_forth_90, R.drawable.aod_clock_panel_animation_forth_91, R.drawable.aod_clock_panel_animation_forth_92, R.drawable.aod_clock_panel_animation_forth_93, R.drawable.aod_clock_panel_animation_forth_94, R.drawable.aod_clock_panel_animation_forth_95, R.drawable.aod_clock_panel_animation_forth_96, R.drawable.aod_clock_panel_animation_forth_97, R.drawable.aod_clock_panel_animation_forth_98, R.drawable.aod_clock_panel_animation_forth_99, R.drawable.aod_clock_panel_animation_forth_100, R.drawable.aod_clock_panel_animation_forth_101, R.drawable.aod_clock_panel_animation_forth_102, R.drawable.aod_clock_panel_animation_forth_103, R.drawable.aod_clock_panel_animation_forth_104, R.drawable.aod_clock_panel_animation_forth_105, R.drawable.aod_clock_panel_animation_forth_106, R.drawable.aod_clock_panel_animation_forth_107, R.drawable.aod_clock_panel_animation_forth_108, R.drawable.aod_clock_panel_animation_forth_109, R.drawable.aod_clock_panel_animation_forth_110, R.drawable.aod_clock_panel_animation_forth_112, R.drawable.aod_clock_panel_animation_forth_113, R.drawable.aod_clock_panel_animation_forth_114, R.drawable.aod_clock_panel_animation_forth_115, R.drawable.aod_clock_panel_animation_forth_116, R.drawable.aod_clock_panel_animation_forth_117, R.drawable.aod_clock_panel_animation_forth_118, R.drawable.aod_clock_panel_animation_forth_119, R.drawable.aod_clock_panel_animation_forth_120};
        CLOCK_PANEL_ANIMATION_FORTH_ARRAYS = iArr4;
        ArrayMap arrayMap = new ArrayMap();
        sFrameInfoMap = arrayMap;
        arrayMap.put("clock_panel_first", new FrameAnimationInfo(iArr, R.drawable.aod_clock_panel_animation_first_small, 30, R.drawable.clock_panel_hour_hand_first, R.drawable.clock_panel_minute_hand_first, R.drawable.clock_panel_dot_first));
        int i = 30;
        arrayMap.put("clock_panel_second", new FrameAnimationInfo(iArr2, R.drawable.aod_clock_panel_animation_second_small, i, R.drawable.clock_panel_hour_hand_second, R.drawable.clock_panel_minute_hand_second, R.drawable.clock_panel_dot_second));
        arrayMap.put("clock_panel_third", new FrameAnimationInfo(iArr3, R.drawable.aod_clock_panel_animation_third_small, i, R.drawable.clock_panel_hour_hand_third, R.drawable.clock_panel_minute_hand_third, R.drawable.clock_panel_dot_third));
        arrayMap.put("clock_panel_forth", new FrameAnimationInfo(iArr4, R.drawable.aod_clock_panel_animation_forth_small, 60, R.drawable.clock_panel_hour_hand_forth, R.drawable.clock_panel_minute_hand_forth, R.drawable.clock_panel_dot_forth));
    }

    public static FrameAnimationInfo createFrameAnimationInfoFromStyleInfo(Context context, StyleInfo styleInfo) {
        if (!(styleInfo instanceof AnimateClockPanelCategoryInfo)) {
            return null;
        }
        AnimateClockPanelCategoryInfo animateClockPanelCategoryInfo = (AnimateClockPanelCategoryInfo) styleInfo;
        if (animateClockPanelCategoryInfo.getThemeBean() != null) {
            return createFrameAnimationInfoFromThemeBean(context, animateClockPanelCategoryInfo.getThemeBean());
        }
        return (FrameAnimationInfo) sFrameInfoMap.get(animateClockPanelCategoryInfo.getPanelORDefault());
    }

    public static FrameAnimationInfo createFrameAnimationInfoFromThemeBean(Context context, WaveClockStyleBean waveClockStyleBean) {
        File[] fileArrListFiles;
        StringBuilder sb = new StringBuilder();
        sb.append(ThemeUtils.getRootDir(context));
        String str = File.separator;
        sb.append(str);
        sb.append(waveClockStyleBean.mThemeDirName);
        sb.append(str);
        sb.append("drawable");
        sb.append(str);
        sb.append(waveClockStyleBean.mPanelImageArraysDir);
        File file = new File(sb.toString());
        if (file.exists() && file.isDirectory() && (fileArrListFiles = file.listFiles()) != null && fileArrListFiles.length != 0) {
            try {
                Object[] array = Arrays.stream(fileArrListFiles).sorted(Comparator.comparing(new Function() { // from class: com.miui.aod.widget.FrameAnimationManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((File) obj).getName();
                    }
                })).map(new Function() { // from class: com.miui.aod.widget.FrameAnimationManager$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((File) obj).getAbsolutePath();
                    }
                }).toArray();
                int length = array.length;
                String[] strArr = new String[length];
                System.arraycopy(array, 0, strArr, 0, array.length);
                return new FrameAnimationInfo(strArr, ThemeUtils.getRootDir(context) + str + waveClockStyleBean.mThemeDirName + str + "drawable", strArr[length - 1], waveClockStyleBean.mHourHandImg, waveClockStyleBean.mMinHandImg, waveClockStyleBean.mPanelCenterImg);
            } catch (Exception e) {
                Log.e("FrameAnimationManager", "error...", e);
            }
        }
        return null;
    }
}
