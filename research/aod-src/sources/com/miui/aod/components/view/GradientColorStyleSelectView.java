package com.miui.aod.components.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.DrawableData;
import com.miui.aod.components.widget.ColorSelectView;
import com.miui.aod.resource.AodDrawables;
import com.miui.aod.widget.SunSelector;
import java.util.ArrayList;
import java.util.List;
import miuix.slidingwidget.widget.SlidingButton;

/* JADX INFO: loaded from: classes.dex */
public class GradientColorStyleSelectView extends BaseStyleSelectView {
    private SlidingButton mChangeColorAlongTimeSb;
    private ColorSelectView mColorSelectView;
    private StyleInfo mStyleInfo;

    /* JADX INFO: renamed from: $r8$lambda$nm-lFUb5sSvxQFRenULPNL2ZruE, reason: not valid java name */
    public static /* synthetic */ void m259$r8$lambda$nmlFUb5sSvxQFRenULPNL2ZruE(GradientColorStyleSelectView gradientColorStyleSelectView, ViewGroup viewGroup, CompoundButton compoundButton, boolean z) {
        gradientColorStyleSelectView.mStyleInfo.setChangeColorAlongTime(z);
        SunSelector.updateSunRiseTime(viewGroup.getContext());
        gradientColorStyleSelectView.updateStyleInfoForPreview();
        gradientColorStyleSelectView.updateColorSelectorState(z);
    }

    public GradientColorStyleSelectView(Context context) {
        super(context);
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
        updateStyleInfoForPreview();
    }

    private void updateColorSelectorState(boolean z) {
        if (z) {
            this.mColorSelectView.setAlpha(0.4f);
            this.mColorSelectView.setEnabled(false);
        } else {
            this.mColorSelectView.setAlpha(1.0f);
            this.mColorSelectView.setEnabled(true);
        }
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void inflateStyleSelectPanelView(final ViewGroup viewGroup) {
        this.mLayoutInflater.inflate(R.layout.gradient_style_select_layout, viewGroup, true);
        ((TextView) this.mRootView.findViewById(R.id.style_select_title)).setText(R.string.standard_style);
        ColorSelectView colorSelectView = (ColorSelectView) this.mRootView.findViewById(R.id.color_select_view);
        this.mColorSelectView = colorSelectView;
        colorSelectView.setOnItemClickListener(new ColorSelectView.OnItemClickListener() { // from class: com.miui.aod.components.view.GradientColorStyleSelectView$$ExternalSyntheticLambda0
            @Override // com.miui.aod.components.widget.ColorSelectView.OnItemClickListener
            public final void onItemClick(int i, DrawableData drawableData) {
                this.f$0.onColorSelect(i, drawableData);
            }
        });
        SlidingButton slidingButton = (SlidingButton) this.mRootView.findViewById(R.id.switch_button);
        this.mChangeColorAlongTimeSb = slidingButton;
        slidingButton.setOnPerformCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.aod.components.view.GradientColorStyleSelectView$$ExternalSyntheticLambda1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                GradientColorStyleSelectView.m259$r8$lambda$nmlFUb5sSvxQFRenULPNL2ZruE(this.f$0, viewGroup, compoundButton, z);
            }
        });
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void initStyleInfoSelectedInner(StyleInfo styleInfo) {
        this.mStyleInfo = styleInfo;
        if (this.mColorSelectView != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(AodDrawables.getDrawableDataByName("aod_pure_color_1"));
            arrayList.addAll(AodDrawables.getGradientDrawables());
            this.mColorSelectView.setDrawableData(arrayList);
            int iFindDrawableById = findDrawableById(arrayList, styleInfo.getColorName());
            this.mColorSelectView.setItemSelected(iFindDrawableById);
            ColorSelectView colorSelectView = this.mColorSelectView;
            colorSelectView.smoothScrollToPosition(colorSelectView.getSelection());
            if (iFindDrawableById == 0) {
                onColorSelect(0, arrayList.get(0));
            }
        }
        this.mChangeColorAlongTimeSb.setChecked(this.mStyleInfo.isChangeColorAlongTime());
        updateColorSelectorState(this.mStyleInfo.isChangeColorAlongTime());
    }
}
