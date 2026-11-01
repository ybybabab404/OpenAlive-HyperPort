package com.miui.aod.components;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.aod.components.view.ClockStyleSelectView;
import com.miui.aod.components.view.DescriptionAndImageStyleSelectView;
import com.miui.aod.components.view.DescriptionStyleSelectView;
import com.miui.aod.components.view.GradientColorStyleSelectView;
import com.miui.aod.components.view.ImageSelectorStyleSelectView;
import com.miui.aod.components.view.LinkageStyleSelectView;
import com.miui.aod.components.view.PureColorAndImageStyleSelectView;
import com.miui.aod.components.view.SuperWallpaperStyleSelectView;
import com.miui.aod.components.view.TextStyleSelectView;
import com.miui.aod.components.view.ThemeStyleView;

/* JADX INFO: loaded from: classes.dex */
public class BaseStyleSelectViewFactory {
    private static final String TAG = "BaseStyleSelectViewFactory";

    private BaseStyleSelectViewFactory() {
    }

    public static BaseStyleSelectView createStyleSelectView(Context context, StyleInfo styleInfo) {
        if (styleInfo == null) {
            return null;
        }
        String styleSelectOrThemeViewName = styleInfo.getStyleSelectOrThemeViewName();
        if (!TextUtils.isEmpty(styleSelectOrThemeViewName)) {
            styleSelectOrThemeViewName.getClass();
            switch (styleSelectOrThemeViewName) {
                case "com.miui.aod.components.view.ThemeStyleView":
                    return new ThemeStyleView(context);
                case "com.miui.aod.components.view.LinkageStyleSelectView":
                    return new LinkageStyleSelectView(context);
                case "com.miui.aod.components.view.GradientColorStyleSelectView":
                    return new GradientColorStyleSelectView(context);
                case "com.miui.aod.components.view.PureColorAndImageStyleSelectView":
                    return new PureColorAndImageStyleSelectView(context);
                case "com.miui.aod.components.view.ImageSelectorStyleSelectView":
                    return new ImageSelectorStyleSelectView(context);
                case "com.miui.aod.components.view.DescriptionAndImageStyleSelectView":
                    return new DescriptionAndImageStyleSelectView(context);
                case "com.miui.aod.components.view.TextStyleSelectView":
                    return new TextStyleSelectView(context);
                case "com.miui.aod.components.view.SuperWallpaperStyleSelectView":
                    return new SuperWallpaperStyleSelectView(context);
                case "com.miui.aod.components.view.ClockStyleSelectView":
                    return new ClockStyleSelectView(context);
                case "com.miui.aod.components.view.DescriptionStyleSelectView":
                    return new DescriptionStyleSelectView(context);
                default:
                    Log.wtf(TAG, "Could not inflate subclass " + styleSelectOrThemeViewName);
                    break;
            }
        }
        return null;
    }
}
