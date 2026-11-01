package com.miui.aod.components;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import com.google.gson.Gson;
import com.miui.aod.AODApplication;
import com.miui.aod.Utils;
import com.miui.aod.category.BlankSuperWallpaperCategoryInfo;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.SuperWallpaperCategoryInfo;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.aod.components.view.ImageSelectorStyleSelectView;
import com.miui.aod.settings.RouterActivity;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.dex */
public class ApplyInstanceStyleSelectPresenter extends BaseStyleSelectPresenter {
    public static /* synthetic */ void $r8$lambda$0z8u3oHjsitk2cHMyVRg6iX_ix8(ApplyInstanceStyleSelectPresenter applyInstanceStyleSelectPresenter, String str) {
        Utils.setAodUsingCategoryParam(applyInstanceStyleSelectPresenter.mContext, CategoryConstants.CATEGORY_SUPER_WALLPAPER, str);
        Utils.setUsingSuperWallpaperStyleState(applyInstanceStyleSelectPresenter.mContext, true);
        RouterActivity.tryToEnableAod(AODApplication.sInstance, "ApplyInstanceStyleSelectPresenter");
    }

    public static /* synthetic */ void $r8$lambda$zGf3gSxHEAdMqJhU8Afth3icf8E(ApplyInstanceStyleSelectPresenter applyInstanceStyleSelectPresenter, String str) {
        Utils.setAodUsingCategoryParam(applyInstanceStyleSelectPresenter.mContext, CategoryConstants.CATEGORY_BLANK_SUPER_WALLPAPER, str);
        Utils.setUsingSuperWallpaperStyleState(applyInstanceStyleSelectPresenter.mContext, true);
        RouterActivity.tryToEnableAod(AODApplication.sInstance, "ApplyInstanceStyleSelectPresenter");
    }

    public ApplyInstanceStyleSelectPresenter(Context context, BaseStyleSelectView baseStyleSelectView, Intent intent) {
        super(context, baseStyleSelectView, intent);
    }

    private void applyStyleParameterForBlankSuperWallpaper(final String str) {
        BackgroundThread.getHandler().post(new Runnable() { // from class: com.miui.aod.components.ApplyInstanceStyleSelectPresenter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ApplyInstanceStyleSelectPresenter.$r8$lambda$zGf3gSxHEAdMqJhU8Afth3icf8E(this.f$0, str);
            }
        });
    }

    private void applyStyleParameterForSuperWallpaper(final String str) {
        BackgroundThread.getHandler().post(new Runnable() { // from class: com.miui.aod.components.ApplyInstanceStyleSelectPresenter$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ApplyInstanceStyleSelectPresenter.$r8$lambda$0z8u3oHjsitk2cHMyVRg6iX_ix8(this.f$0, str);
            }
        });
    }

    @Override // com.miui.aod.components.BaseStyleSelectPresenter
    public boolean needApplyBtn() {
        return false;
    }

    @Override // com.miui.aod.components.BaseStyleSelectPresenter, com.miui.aod.components.StyleSelectContract.StyleSelectPresenter, androidx.lifecycle.DefaultLifecycleObserver
    public void onPause(@NonNull LifecycleOwner lifecycleOwner) {
        BaseStyleSelectView baseStyleSelectView = this.mStyleSelectView;
        if (baseStyleSelectView instanceof ImageSelectorStyleSelectView) {
            ((ImageSelectorStyleSelectView) baseStyleSelectView).applyStyleAsync();
        } else {
            StyleInfo styleInfo = baseStyleSelectView.getStyleInfo();
            if (styleInfo != null) {
                String json = new Gson().toJson(styleInfo);
                if (styleInfo instanceof SuperWallpaperCategoryInfo) {
                    applyStyleParameterForSuperWallpaper(json);
                } else if (styleInfo instanceof BlankSuperWallpaperCategoryInfo) {
                    applyStyleParameterForBlankSuperWallpaper(json);
                } else {
                    applyStyleParameter(json);
                }
            }
        }
        super.onPause(lifecycleOwner);
    }

    @Override // com.miui.aod.components.BaseStyleSelectPresenter, com.miui.aod.components.StyleSelectContract.StyleSelectPresenter, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onStart(@NotNull LifecycleOwner lifecycleOwner) {
        super.onStart(lifecycleOwner);
    }

    @Override // com.miui.aod.components.BaseStyleSelectPresenter, com.miui.aod.components.StyleSelectContract.StyleSelectPresenter, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onStop(@NotNull LifecycleOwner lifecycleOwner) {
        super.onStop(lifecycleOwner);
    }
}
