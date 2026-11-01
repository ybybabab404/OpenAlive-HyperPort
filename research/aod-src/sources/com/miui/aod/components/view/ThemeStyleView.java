package com.miui.aod.components.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.util.DeviceInfo;

/* JADX INFO: loaded from: classes.dex */
public class ThemeStyleView extends BaseStyleSelectView {
    private TextView mDescription;
    private View mDescriptionContainer;

    public ThemeStyleView(Context context) {
        super(context);
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void inflateStyleSelectPanelView(ViewGroup viewGroup) {
        this.mLayoutInflater.inflate(R.layout.description_style_select_layout, viewGroup, true);
        TextView textView = (TextView) this.mRootView.findViewById(R.id.description_tv);
        this.mDescription = textView;
        textView.setVisibility(8);
        View view = (View) this.mDescription.getParent();
        this.mDescriptionContainer = view;
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin = 0;
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void initStyleInfoSelectedInner(StyleInfo styleInfo) {
        super.initStyleInfoSelectedInner(styleInfo);
        if (styleInfo.getThemeBean() != null) {
            String localizedThemeDescription = styleInfo.getThemeBean().getLocalizedThemeDescription();
            if (!TextUtils.isEmpty(localizedThemeDescription)) {
                ((ViewGroup.MarginLayoutParams) this.mDescriptionContainer.getLayoutParams()).topMargin = DeviceInfo.dp2Px(30);
                this.mDescription.setVisibility(0);
                this.mDescription.setText(localizedThemeDescription);
            }
            this.mTitleTv.setText(styleInfo.getThemeBean().getLocalizedThemeName());
        }
    }
}
