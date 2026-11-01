package com.miui.aod.components;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.miui.aod.AODStyleController;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.BaseStyleSelectView;

/* JADX INFO: loaded from: classes.dex */
public class BaseStyleSelectPresenterFactory {
    private static final String TAG = "BaseStyleSelectViewFactory";

    private BaseStyleSelectPresenterFactory() {
    }

    public static BaseStyleSelectPresenter createStyleSelectPresenter(Context context, Intent intent) {
        if (intent == null) {
            return null;
        }
        String stringExtra = intent.getStringExtra(BaseStyleSelectPresenter.KEY_CATE);
        if (TextUtils.isEmpty(stringExtra)) {
            stringExtra = Utils.getAodUsingCategoryName(context);
            intent.putExtra(BaseStyleSelectPresenter.KEY_CATE, stringExtra);
        }
        if (intent.getStringExtra(BaseStyleSelectPresenter.KEY_PARAMETER) == null || CategoryConstants.CATEGORY_LINKAGE.equals(stringExtra) || CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(stringExtra)) {
            intent.putExtra(BaseStyleSelectPresenter.KEY_PARAMETER, new Gson().toJson(AODStyleController.getStyleInfo(context, stringExtra)));
        }
        int intExtra = intent.getIntExtra(BaseStyleSelectPresenter.KEY_SOURCE_TYPE, 0);
        StyleInfo styleInfoCreateStyleInfo = CategoryFactory.createStyleInfo(context, stringExtra);
        BaseStyleSelectView baseStyleSelectViewCreateStyleSelectView = BaseStyleSelectViewFactory.createStyleSelectView(context, styleInfoCreateStyleInfo);
        if (baseStyleSelectViewCreateStyleSelectView == null) {
            return null;
        }
        String styleSelectPresenterName = styleInfoCreateStyleInfo.getStyleSelectPresenterName(intExtra);
        if (TextUtils.isEmpty(styleSelectPresenterName)) {
            return null;
        }
        styleSelectPresenterName.getClass();
        if (styleSelectPresenterName.equals("com.miui.aod.components.BaseStyleSelectPresenter")) {
            return new BaseStyleSelectPresenter(context, baseStyleSelectViewCreateStyleSelectView, intent);
        }
        if (styleSelectPresenterName.equals("com.miui.aod.components.ApplyInstanceStyleSelectPresenter")) {
            return new ApplyInstanceStyleSelectPresenter(context, baseStyleSelectViewCreateStyleSelectView, intent);
        }
        Log.wtf(TAG, "Could not inflate subclass " + styleSelectPresenterName);
        return null;
    }
}
