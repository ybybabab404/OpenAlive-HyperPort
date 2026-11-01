package com.miui.aod.components.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.google.gson.Gson;
import com.miui.aod.R;
import com.miui.aod.category.ClockPanelCategoryInfo;
import com.miui.aod.category.LinearTimeCategoryInfo;
import com.miui.aod.category.SmartCoverStyleInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.DrawableData;
import com.miui.aod.components.widget.ClockPanelSelectView;
import com.miui.aod.components.widget.ColorSelectView;
import com.miui.aod.resource.AodDrawables;
import com.miui.aod.util.AodUtils;
import com.miui.aod.widget.SlidingButtonWithMainSubTitle;
import com.miui.aod.widget.SunSelector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ClockStyleSelectView extends BaseStyleSelectView {
    private SlidingButtonWithMainSubTitle mChangeColorAlongTimeSb;
    private ClockPanelSelectView mClockPanelSelectView;
    private ColorSelectView mColorSelectView;
    private TextView mColorTitle;
    private ViewGroup mContainerView;
    private final Gson mGson;
    private TextView mPanelTitleTv;

    /* JADX INFO: renamed from: com.miui.aod.components.view.ClockStyleSelectView$1, reason: invalid class name */
    public class AnonymousClass1 implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ ViewGroup val$parent;

        public static /* synthetic */ void $r8$lambda$obLDbvdn1JdAOuxReYzjzRLr2CQ(AnonymousClass1 anonymousClass1, boolean z) {
            ClockStyleSelectView.this.updateStyleInfoForPreview();
            ClockStyleSelectView.this.updateColorSelectorState(z);
        }

        public AnonymousClass1(ViewGroup viewGroup) {
            this.val$parent = viewGroup;
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, final boolean z) {
            ClockStyleSelectView.this.mChangeColorAlongTimeSb.initContentdescription();
            ClockStyleSelectView.this.mStyleInfo.setChangeColorAlongTime(z);
            SunSelector.updateSunRiseTime(this.val$parent.getContext());
            ClockStyleSelectView.this.mChangeColorAlongTimeSb.post(new Runnable() { // from class: com.miui.aod.components.view.ClockStyleSelectView$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ClockStyleSelectView.AnonymousClass1.$r8$lambda$obLDbvdn1JdAOuxReYzjzRLr2CQ(this.f$0, z);
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$hc2pSatB5aNiev9D6HROtwDr2fk(ClockStyleSelectView clockStyleSelectView) {
        clockStyleSelectView.updateColorSelectorState(clockStyleSelectView.mChangeColorAlongTimeSb.getChecked());
        clockStyleSelectView.mStyleInfo.setChangeColorAlongTime(clockStyleSelectView.mChangeColorAlongTimeSb.getChecked());
    }

    public ClockStyleSelectView(Context context) {
        super(context);
        this.mGson = new Gson();
    }

    private boolean compareStyleParameter(String str, StyleInfo styleInfo) {
        return ((StyleInfo) this.mGson.fromJson(str, (Class) styleInfo.getClass())).compareToForPresetStyle(styleInfo);
    }

    private int findClockStyleByStyleInfo(List<String> list, StyleInfo styleInfo) {
        if (list == null) {
            return 0;
        }
        int size = list.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (compareStyleParameter(list.get(i2), styleInfo)) {
                i = i2;
            }
        }
        return i;
    }

    private int findDrawableById(List<DrawableData> list, String str) {
        if (list != null) {
            String strTranslateColorNameFromOldVersion = AodDrawables.translateColorNameFromOldVersion(str);
            if (!TextUtils.isEmpty(strTranslateColorNameFromOldVersion)) {
                str = strTranslateColorNameFromOldVersion;
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (list.get(i).mName.equals(str)) {
                    return i;
                }
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onColorSelect(int i, DrawableData drawableData) {
        String colorName = this.mStyleInfo.getColorName();
        if (TextUtils.isEmpty(drawableData.mName) || drawableData.mName.equals(colorName)) {
            return;
        }
        this.mStyleInfo.setColorName(drawableData.mName);
        StyleInfo styleInfo = this.mStyleInfo;
        if (styleInfo instanceof ClockPanelCategoryInfo) {
            ((ClockPanelCategoryInfo) styleInfo).setSecondaryColor(((DrawableData) AodDrawables.sSecondaryDrawableList.get(i)).mName);
        }
        updateStyleInfoForPreview();
    }

    private void updateColorSelectState(List<DrawableData> list, StyleInfo styleInfo) {
        updateColorSelectState(list, styleInfo, true);
    }

    private void updateColorSelectState(List<DrawableData> list, StyleInfo styleInfo, boolean z) {
        int iFindDrawableById = findDrawableById(list, styleInfo.getColorName());
        this.mColorSelectView.setItemSelected(iFindDrawableById);
        if (z) {
            ColorSelectView colorSelectView = this.mColorSelectView;
            colorSelectView.smoothScrollToPosition(colorSelectView.getSelection());
        }
        if (iFindDrawableById == 0) {
            onColorSelect(0, list.get(0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateColorSelectorState(boolean z) {
        if (z) {
            this.mColorSelectView.setAlpha(0.4f);
            this.mColorSelectView.setEnabled(false);
        } else {
            this.mColorSelectView.setAlpha(1.0f);
            this.mColorSelectView.setEnabled(true);
        }
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void inflateStyleSelectPanelView(ViewGroup viewGroup) {
        this.mLayoutInflater.inflate(R.layout.clock_panel_style_select_layout, viewGroup, true);
        this.mContainerView = (ViewGroup) viewGroup.getChildAt(0);
        ClockPanelSelectView clockPanelSelectView = (ClockPanelSelectView) this.mRootView.findViewById(R.id.clock_panel_select_view);
        this.mClockPanelSelectView = clockPanelSelectView;
        clockPanelSelectView.setOnItemClickListener(new ClockPanelSelectView.OnItemClickListener() { // from class: com.miui.aod.components.view.ClockStyleSelectView$$ExternalSyntheticLambda0
            @Override // com.miui.aod.components.widget.ClockPanelSelectView.OnItemClickListener
            public final void onItemClick(int i, String str, String str2) {
                this.f$0.onClockSelected(i, str, str2);
            }
        });
        this.mColorSelectView = (ColorSelectView) this.mRootView.findViewById(R.id.color_select_view);
        this.mColorTitle = (TextView) this.mRootView.findViewById(R.id.color_title);
        this.mColorSelectView.setOnItemClickListener(new ColorSelectView.OnItemClickListener() { // from class: com.miui.aod.components.view.ClockStyleSelectView$$ExternalSyntheticLambda1
            @Override // com.miui.aod.components.widget.ColorSelectView.OnItemClickListener
            public final void onItemClick(int i, DrawableData drawableData) {
                this.f$0.onColorSelect(i, drawableData);
            }
        });
        this.mPanelTitleTv = (TextView) this.mRootView.findViewById(R.id.panel_select_title);
        SlidingButtonWithMainSubTitle slidingButtonWithMainSubTitle = (SlidingButtonWithMainSubTitle) this.mRootView.findViewById(R.id.changecolor_switch_container);
        this.mChangeColorAlongTimeSb = slidingButtonWithMainSubTitle;
        slidingButtonWithMainSubTitle.init(this.mContext.getResources().getString(R.string.color_change_with_time_title), this.mContext.getResources().getString(R.string.color_change_with_time_description), new AnonymousClass1(viewGroup));
        this.mChangeColorAlongTimeSb.initContentdescription();
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void initStyleInfoSelectedInner(StyleInfo styleInfo) {
        String[] strArr;
        if ((styleInfo instanceof LinearTimeCategoryInfo) || (styleInfo instanceof SmartCoverStyleInfo)) {
            if (this.mColorSelectView != null) {
                this.mColorTitle.setVisibility(0);
                this.mColorSelectView.setVisibility(0);
                ArrayList arrayList = new ArrayList();
                arrayList.add(AodDrawables.getDrawableDataByName("aod_pure_color_1"));
                arrayList.addAll(AodDrawables.getGradientDrawables());
                this.mColorSelectView.setDrawableData(arrayList);
                styleInfo.setColorName(styleInfo.getColorName());
                updateColorSelectState(arrayList, styleInfo);
            }
        } else if ((styleInfo instanceof ClockPanelCategoryInfo) && this.mColorSelectView != null) {
            this.mColorTitle.setVisibility(0);
            this.mColorSelectView.setVisibility(0);
            ArrayList arrayList2 = new ArrayList();
            arrayList2.addAll(AodDrawables.getGradientDrawables());
            arrayList2.addAll(AodDrawables.getPureDrawables());
            this.mColorSelectView.setDrawableData(arrayList2);
            styleInfo.setColorName(styleInfo.getColorName());
            updateColorSelectState(arrayList2, styleInfo);
        }
        if (this.mClockPanelSelectView != null && styleInfo.getPresetStyle() != null && (strArr = styleInfo.getPresetStyle().mStyleParameters) != null) {
            ArrayList arrayList3 = new ArrayList(strArr.length);
            Collections.addAll(arrayList3, strArr);
            List<String> clusterData = AodUtils.getClusterData(arrayList3, styleInfo.getBgCluster());
            if (clusterData == null || clusterData.size() == 0) {
                this.mPanelTitleTv.setVisibility(8);
                ((ViewGroup.MarginLayoutParams) this.mContainerView.getLayoutParams()).topMargin = 0;
                this.mClockPanelSelectView.setVisibility(8);
            } else {
                this.mPanelTitleTv.setText(this.mContext.getString(styleInfo.getPresetStyle().mTitleResId));
                this.mClockPanelSelectView.setDate(this.mCateNameSelected, clusterData);
                int iFindClockStyleByStyleInfo = findClockStyleByStyleInfo(clusterData, styleInfo);
                this.mClockPanelSelectView.setItemSelected(iFindClockStyleByStyleInfo);
                this.mClockPanelSelectView.smoothScrollToPosition(iFindClockStyleByStyleInfo);
            }
        }
        if (!styleInfo.supportChangeColorAlongTime()) {
            this.mRootView.findViewById(R.id.changecolor_switch_container).setVisibility(8);
        }
        this.mChangeColorAlongTimeSb.setChecked(this.mStyleInfo.isChangeColorAlongTime());
        this.mChangeColorAlongTimeSb.post(new Runnable() { // from class: com.miui.aod.components.view.ClockStyleSelectView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ClockStyleSelectView.$r8$lambda$hc2pSatB5aNiev9D6HROtwDr2fk(this.f$0);
            }
        });
    }

    public void onClockSelected(int i, String str, String str2) {
        StyleInfo styleInfo = this.mStyleInfo;
        if (styleInfo instanceof ClockPanelCategoryInfo) {
            ClockPanelCategoryInfo clockPanelCategoryInfo = (ClockPanelCategoryInfo) this.mGson.fromJson(str2, ClockPanelCategoryInfo.class);
            if (TextUtils.equals(((ClockPanelCategoryInfo) styleInfo).getPanel(), clockPanelCategoryInfo.getPanel())) {
                return;
            } else {
                ((ClockPanelCategoryInfo) this.mStyleInfo).setPanel(clockPanelCategoryInfo.getPanel());
            }
        } else if (styleInfo instanceof LinearTimeCategoryInfo) {
            LinearTimeCategoryInfo linearTimeCategoryInfo = (LinearTimeCategoryInfo) styleInfo;
            LinearTimeCategoryInfo linearTimeCategoryInfo2 = (LinearTimeCategoryInfo) this.mGson.fromJson(str2, LinearTimeCategoryInfo.class);
            if (linearTimeCategoryInfo.isVertical() != linearTimeCategoryInfo2.isVertical()) {
                linearTimeCategoryInfo.setVertical(linearTimeCategoryInfo2.isVertical());
                linearTimeCategoryInfo.setLeftGravity(linearTimeCategoryInfo2.isLeftGravity());
            } else {
                if (linearTimeCategoryInfo.isLeftGravity() == linearTimeCategoryInfo2.isLeftGravity()) {
                    return;
                }
                linearTimeCategoryInfo.setLeftGravity(linearTimeCategoryInfo2.isLeftGravity());
                linearTimeCategoryInfo.setVertical(linearTimeCategoryInfo2.isVertical());
            }
        } else if (!(styleInfo instanceof SmartCoverStyleInfo)) {
            return;
        } else {
            ((SmartCoverStyleInfo) styleInfo).setStyle(((SmartCoverStyleInfo) this.mGson.fromJson(str2, SmartCoverStyleInfo.class)).getStyle());
        }
        updateStyleInfoForPreview();
        if (this.mColorSelectView.getData() == null || this.mColorSelectView.getData().size() <= 0) {
            return;
        }
        updateColorSelectState(this.mColorSelectView.getData(), this.mStyleInfo, false);
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView, com.miui.aod.components.StyleSelectContract.StyleSelectView
    public void updateTime() {
        super.updateTime();
        this.mClockPanelSelectView.updateTime();
    }
}
