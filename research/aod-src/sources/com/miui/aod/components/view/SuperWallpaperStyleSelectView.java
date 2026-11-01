package com.miui.aod.components.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.miui.aod.R;

/* JADX INFO: loaded from: classes.dex */
public class SuperWallpaperStyleSelectView extends BaseStyleSelectView {
    public SuperWallpaperStyleSelectView(Context context) {
        super(context);
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void inflateStyleSelectPanelView(ViewGroup viewGroup) {
        this.mLayoutInflater.inflate(R.layout.description_style_select_layout, viewGroup, true);
        View viewFindViewById = this.mRootView.findViewById(R.id.description_tv);
        viewFindViewById.setVisibility(8);
        ((ViewGroup.MarginLayoutParams) ((View) viewFindViewById.getParent()).getLayoutParams()).topMargin = 0;
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView, com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
    public void onSlide(@NonNull View view, float f) {
    }
}
