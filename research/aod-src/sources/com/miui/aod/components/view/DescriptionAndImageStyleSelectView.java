package com.miui.aod.components.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.DrawableData;
import com.miui.aod.components.widget.ImageSelectView;
import com.miui.aod.util.AodUtils;
import com.miui.aod.widget.ScopeInfoManager;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DescriptionAndImageStyleSelectView extends BaseStyleSelectView {
    private static final String TAG = "DescriptionAndImageStyleSelectView";
    private ImageSelectView mBackgroundSelectView;
    private TextView mTitle;

    public DescriptionAndImageStyleSelectView(Context context) {
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
        if (TextUtils.equals(this.mStyleInfo.getClockBgName(), drawableData.mName)) {
            return;
        }
        Log.i(TAG, "onBackgroundSelect: " + drawableData.mName);
        this.mStyleInfo.setClockBgName(drawableData.mName);
        updateStyleInfoForPreview();
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void inflateStyleSelectPanelView(ViewGroup viewGroup) {
        this.mLayoutInflater.inflate(R.layout.description_and_image_style_select_layout, viewGroup, true);
        ImageSelectView imageSelectView = (ImageSelectView) this.mRootView.findViewById(R.id.bg_select_view);
        this.mBackgroundSelectView = imageSelectView;
        imageSelectView.setOnItemClickListener(new ImageSelectView.OnItemClickListener() { // from class: com.miui.aod.components.view.DescriptionAndImageStyleSelectView$$ExternalSyntheticLambda0
            @Override // com.miui.aod.components.widget.ImageSelectView.OnItemClickListener
            public final void onItemClick(int i, DrawableData drawableData) {
                this.f$0.onBackgroundSelect(i, drawableData);
            }
        });
        this.mTitle = (TextView) this.mRootView.findViewById(R.id.title);
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void initStyleInfoSelectedInner(StyleInfo styleInfo) {
        if (this.mBackgroundSelectView != null) {
            List<DrawableData> drawableListByCateName = ScopeInfoManager.getDrawableListByCateName(this.mCateNameSelected);
            List clusterData = AodUtils.getClusterData(drawableListByCateName, styleInfo.getBgCluster());
            this.mBackgroundSelectView.setDrawableData(clusterData);
            if (clusterData == null || clusterData.size() == 0) {
                this.mTitle.setVisibility(8);
            }
            this.mBackgroundSelectView.setItemSelected(findDrawableById(drawableListByCateName, styleInfo.getClockBgName()));
            ImageSelectView imageSelectView = this.mBackgroundSelectView;
            imageSelectView.smoothScrollToPosition(imageSelectView.getSelection());
        }
    }
}
