package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.theme.MamlViewContainer;
import com.miui.aod.theme.beans.MamlThemeBean;
import com.miui.aod.theme.utils.ThemeUtils;
import com.miui.aod.util.DeviceInfo;
import com.miui.aod.util.WindowUtils;
import com.miui.getalpha.GetAlpha;
import com.miui.keyguard.editor.utils.DeviceUtil;

/* JADX INFO: loaded from: classes.dex */
public class MamlClock implements IAodClock {
    public int mLastSize;
    public StyleInfo mLastStyleInfo;
    public MamlViewContainer mMamlViewContainer;
    public final int mSize;

    public static /* synthetic */ void $r8$lambda$Qyx9iVf0QoTFvMYbi3vPJsVs_vI(MamlClock mamlClock) {
        mamlClock.mMamlViewContainer.getLayoutParams().height = mamlClock.mMamlViewContainer.getMeasuredWidth();
        mamlClock.mMamlViewContainer.requestLayout();
        mamlClock.mMamlViewContainer.invalidate();
    }

    public MamlClock(int i) {
        this.mSize = i;
    }

    public final void bindBaseView() {
        ViewGroup.LayoutParams layoutParams = this.mMamlViewContainer.getLayoutParams();
        if (Utils.isFoldDevice()) {
            int dimensionPixelOffset = this.mMamlViewContainer.getContext().getResources().getDimensionPixelOffset(R.dimen.maml_container_width);
            layoutParams.width = dimensionPixelOffset;
            if (dimensionPixelOffset == -1) {
                if (!Utils.isAodProcess() || this.mMamlViewContainer.getContext() == null) {
                    int i = DeviceInfo.getRealSize().x;
                    layoutParams.width = i;
                    layoutParams.height = i;
                } else {
                    int i2 = WindowUtils.INSTANCE.getWindowSize(this.mMamlViewContainer.getContext()).x;
                    layoutParams.width = i2;
                    layoutParams.height = i2;
                }
            } else if (dimensionPixelOffset == -2) {
                this.mMamlViewContainer.post(new Runnable() { // from class: com.miui.aod.widget.MamlClock$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MamlClock.$r8$lambda$Qyx9iVf0QoTFvMYbi3vPJsVs_vI(this.f$0);
                    }
                });
            } else {
                layoutParams.height = dimensionPixelOffset;
            }
        } else {
            int i3 = DeviceInfo.getRealSize().x;
            layoutParams.width = i3;
            layoutParams.height = i3;
        }
        this.mMamlViewContainer.setLayoutParams(layoutParams);
        int i4 = this.mSize;
        if (i4 == 1) {
            this.mMamlViewContainer.setForceDisableTouchDispatch(true);
            return;
        }
        if (i4 == 2 || i4 == 4) {
            this.mMamlViewContainer.setForceDisableTouchDispatch(true);
            scaleForSmallView(false);
        } else {
            if (i4 != 6) {
                return;
            }
            this.mMamlViewContainer.setForceDisableTouchDispatch(true);
            scaleForSmallView(true);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void bindView(View view) {
        MamlViewContainer mamlViewContainer = (MamlViewContainer) view.findViewById(R.id.maml_view_container);
        this.mMamlViewContainer = mamlViewContainer;
        if (mamlViewContainer == null) {
            return;
        }
        mamlViewContainer.setCallback(this);
        bindBaseView();
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        return R.layout.aod_content_maml;
    }

    public void reBindView() {
        if (this.mMamlViewContainer == null) {
            return;
        }
        bindBaseView();
    }

    @Override // com.miui.aod.widget.IAodClock
    public void releaseDrawable() {
        this.mMamlViewContainer.setVisibility(4);
        Log.i("MamlViewContainer", "releaseDrawable: INVISIBLE");
    }

    public void reload() {
        reBindView();
        update(this.mLastStyleInfo, this.mLastSize);
        this.mMamlViewContainer.setVisibility(0);
        Log.i("MamlViewContainer", "reload: VISIBLE");
    }

    public final void scaleForSmallView(boolean z) {
        int i = this.mMamlViewContainer.getLayoutParams().width;
        int i2 = this.mMamlViewContainer.getLayoutParams().height;
        int styleItemWidth = Utils.getStyleItemWidth(this.mMamlViewContainer.getContext());
        int smallViewHeight = Utils.getSmallViewHeight(this.mMamlViewContainer.getContext());
        float f = (styleItemWidth * 1.0f) / i;
        this.mMamlViewContainer.setScaleX(f);
        this.mMamlViewContainer.setScaleY(f);
        float f2 = (styleItemWidth - i) / 2.0f;
        StyleInfo styleInfo = this.mLastStyleInfo;
        if (styleInfo == null || !styleInfo.isForAodNotification()) {
            MamlViewContainer mamlViewContainer = this.mMamlViewContainer;
            if (Utils.isLayoutRtl() && !z) {
                f2 = -f2;
            }
            mamlViewContainer.setTranslationX(f2);
        } else {
            this.mMamlViewContainer.setTranslationX(0.0f);
        }
        this.mMamlViewContainer.setTranslationY((smallViewHeight - i2) / 2.0f);
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        Bitmap bitmap;
        if (this.mMamlViewContainer == null || styleInfo == null || !(styleInfo.getThemeBean() instanceof MamlThemeBean)) {
            return;
        }
        this.mLastStyleInfo = styleInfo;
        this.mLastSize = i;
        if (styleInfo.isForAodNotification()) {
            scaleForSmallView(false);
        }
        MamlThemeBean mamlThemeBean = (MamlThemeBean) styleInfo.getThemeBean();
        this.mMamlViewContainer.setContentDescription(mamlThemeBean.getLocalizedThemeName());
        Context context = AODApplication.sInstance;
        switch (i) {
            case 1:
                break;
            case 2:
            case 4:
            case 5:
            case 6:
                String str = mamlThemeBean.mMamlThumbnail;
                if (str != null) {
                    this.mMamlViewContainer.setBg(ThemeUtils.getBitmap(context, mamlThemeBean, str));
                    return;
                }
                return;
            case 3:
                if (mamlThemeBean.mMamlThumbnail != null && Utils.isNeedToGrayScale() && (bitmap = ThemeUtils.getBitmap(context, mamlThemeBean, mamlThemeBean.mMamlThumbnail)) != null) {
                    this.mMamlViewContainer.setAlpha(GetAlpha.GetAlphaFromBitmap(bitmap));
                }
                break;
            default:
                return;
        }
        String mamlFilePath = ThemeUtils.getMamlFilePath(this.mMamlViewContainer.getContext(), mamlThemeBean, "content");
        if (DeviceUtil.INSTANCE.isGteAndroidW() && !Utils.isAodProcess()) {
            mamlFilePath = ThemeUtils.getMamlFilePath(AODApplication.sysuiContext, mamlThemeBean, "content");
        }
        this.mMamlViewContainer.setLocalResourcePath(context, mamlFilePath, mamlThemeBean.mDurationExpected);
        this.mMamlViewContainer.setBatteryEnable(styleInfo.isBatterySwitchOn());
        MamlViewContainer mamlViewContainer = this.mMamlViewContainer;
        mamlViewContainer.setLunarCalenderEnable(styleInfo.isLunarSwitchOn(mamlViewContainer.getContext()));
        this.mMamlViewContainer.setNotificationEnable(styleInfo.isNotificationSwitchOn());
        this.mMamlViewContainer.setPreviewMode(i != 3);
        if (i != 3 || AODSettings.needKeepScreenOnAtFirst()) {
            this.mMamlViewContainer.startAnimation();
        } else {
            this.mMamlViewContainer.postDelayed(new Runnable() { // from class: com.miui.aod.widget.MamlClock$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.mMamlViewContainer.startAnimation();
                }
            }, 800L);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        this.mMamlViewContainer.handleUpdateTime(z);
    }
}
