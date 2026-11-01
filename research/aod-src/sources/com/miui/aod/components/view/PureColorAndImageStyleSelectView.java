package com.miui.aod.components.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.DrawableData;
import com.miui.aod.components.widget.ColorSelectView;
import com.miui.aod.components.widget.ImageSelectView;
import com.miui.aod.resource.AodDrawables;
import com.miui.aod.util.AodUtils;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PureColorAndImageStyleSelectView extends BaseStyleSelectView {
    private static final String TAG = "PureColorAndImageStyleS";
    private ImageSelectView mBackgroundSelectView;
    private TextView mBgSelectTitleTv;
    private ColorSelectView mColorSelectView;

    public PureColorAndImageStyleSelectView(Context context) {
        super(context);
    }

    private int findDrawableById(List<DrawableData> list, String str) {
        if (list == null) {
            return -1;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i).mName.equals(str)) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBackgroundSelect(int i, DrawableData drawableData) {
        if (this.mStyleInfo.getClockBgResID() != drawableData.mResId) {
            Log.i(TAG, "onBackgroundSelect: " + drawableData.mName);
            this.mStyleInfo.setClockBgName(drawableData.mName);
            updateStyleInfoForPreview();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onColorSelect(int i, DrawableData drawableData) {
        String colorName = this.mStyleInfo.getColorName();
        if (TextUtils.isEmpty(drawableData.mName) || drawableData.mName.equals(colorName)) {
            return;
        }
        Log.i(TAG, "onColorSelect: " + drawableData.mName);
        this.mStyleInfo.setColorName(drawableData.mName);
        updateStyleInfoForPreview();
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void inflateStyleSelectPanelView(ViewGroup viewGroup) {
        this.mLayoutInflater.inflate(R.layout.pure_color_and_image_style_select_layout, viewGroup, true);
        ColorSelectView colorSelectView = (ColorSelectView) this.mRootView.findViewById(R.id.color_select_view);
        this.mColorSelectView = colorSelectView;
        colorSelectView.setOnItemClickListener(new ColorSelectView.OnItemClickListener() { // from class: com.miui.aod.components.view.PureColorAndImageStyleSelectView$$ExternalSyntheticLambda0
            @Override // com.miui.aod.components.widget.ColorSelectView.OnItemClickListener
            public final void onItemClick(int i, DrawableData drawableData) {
                this.f$0.onColorSelect(i, drawableData);
            }
        });
        ImageSelectView imageSelectView = (ImageSelectView) this.mRootView.findViewById(R.id.bg_select_view);
        this.mBackgroundSelectView = imageSelectView;
        imageSelectView.setOnItemClickListener(new ImageSelectView.OnItemClickListener() { // from class: com.miui.aod.components.view.PureColorAndImageStyleSelectView$$ExternalSyntheticLambda1
            @Override // com.miui.aod.components.widget.ImageSelectView.OnItemClickListener
            public final void onItemClick(int i, DrawableData drawableData) {
                this.f$0.onBackgroundSelect(i, drawableData);
            }
        });
        this.mBgSelectTitleTv = (TextView) this.mRootView.findViewById(R.id.bg_select_title);
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void initStyleInfoSelectedInner(StyleInfo styleInfo) {
        if (this.mColorSelectView != null) {
            List<DrawableData> pureDrawables = AodDrawables.getPureDrawables();
            this.mColorSelectView.setDrawableData(pureDrawables);
            int iMax = Math.max(findDrawableById(pureDrawables, styleInfo.getColorName()), 0);
            this.mColorSelectView.setItemSelected(iMax);
            ColorSelectView colorSelectView = this.mColorSelectView;
            colorSelectView.smoothScrollToPosition(colorSelectView.getSelection());
            if (!pureDrawables.isEmpty()) {
                onColorSelect(iMax, pureDrawables.get(iMax));
            }
        }
        if (this.mBackgroundSelectView != null) {
            List<DrawableData> drawableListByCateName = AodDrawables.getDrawableListByCateName(this.mCateNameSelected);
            List clusterData = AodUtils.getClusterData(drawableListByCateName, styleInfo.getBgCluster());
            this.mBackgroundSelectView.setDrawableData(clusterData);
            if (clusterData == null || clusterData.size() == 0) {
                this.mBgSelectTitleTv.setVisibility(8);
            }
            this.mBackgroundSelectView.setItemSelected(findDrawableById(drawableListByCateName, styleInfo.getClockBgName()));
            ImageSelectView imageSelectView = this.mBackgroundSelectView;
            imageSelectView.smoothScrollToPosition(imageSelectView.getSelection());
        }
    }
}
