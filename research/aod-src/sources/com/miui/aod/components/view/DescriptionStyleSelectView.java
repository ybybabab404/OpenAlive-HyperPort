package com.miui.aod.components.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.aod.R;

/* JADX INFO: loaded from: classes.dex */
public class DescriptionStyleSelectView extends BaseStyleSelectView {
    public DescriptionStyleSelectView(Context context) {
        super(context);
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void inflateStyleSelectPanelView(ViewGroup viewGroup) {
        this.mLayoutInflater.inflate(R.layout.description_style_select_layout, viewGroup, true);
        ((TextView) this.mRootView.findViewById(R.id.description_tv)).setText(R.string.deep_space_planet_des);
    }
}
