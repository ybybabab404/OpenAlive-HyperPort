package com.miui.aod.category;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import androidx.collection.ArrayMap;
import com.google.gson.annotations.SerializedName;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.components.view.DescriptionStyleSelectView;
import com.miui.aod.theme.beans.MamlThemeBean;
import com.miui.aod.theme.beans.ThemeBean;
import com.miui.aod.theme.utils.ThemeUtils;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.MamlClock;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.FileUtil;
import java.io.File;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MamlCategoryInfo extends CategoryInfo {

    @SerializedName("theme_style_info")
    private MamlThemeBean mThemeStyleBean;

    public MamlCategoryInfo() {
        super(CategoryConstants.CATEGORY_MAML);
    }

    private void copyMamlInfoToSystemUI(MamlThemeBean mamlThemeBean) {
        try {
            Log.i("MamlViewContainer", "copy maml info: " + mamlThemeBean.mThemeDirName);
            String str = AODApplication.sysuiContext.getCacheDir().getAbsolutePath() + "/themes";
            FileUtil.fileCopyFromUri(AODApplication.sysuiContext, Uri.parse("content://aod.part/themes" + File.separator + mamlThemeBean.mThemeDirName), str);
            FileUtil.deleteFolder(ThemeUtils.getRootDir(AODApplication.sysuiContext));
            File file = new File(ThemeUtils.getRootDir(AODApplication.sysuiContext));
            File file2 = new File(str);
            FileUtil.unzip(file2, file, null);
            file2.delete();
            Log.i("MamlViewContainer", "copy maml info success");
        } catch (Exception e) {
            Log.i("MamlViewContainer", "copy maml info error: " + e.getMessage());
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public IAodClock createAodClock(boolean z, int i) {
        return new MamlClock(i);
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getFullTitle() {
        MamlThemeBean mamlThemeBean = this.mThemeStyleBean;
        return mamlThemeBean != null ? mamlThemeBean.getLocalizedThemeName() : AODApplication.sInstance.getResources().getString(R.string.standard_style);
    }

    @Override // com.miui.aod.common.StyleInfo
    public Map<String, Object> getParameterForAnalytic() {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("style_name", CategoryConstants.CATEGORY_MAML);
        arrayMap.put("style_event", CategoryConstants.CATEGORY_MAML);
        return arrayMap;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getPreviewTopPaddingResId() {
        return R.dimen.aod_preview_middle_top_padding;
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getStyleSelectViewName() {
        return DescriptionStyleSelectView.class.getName();
    }

    @Override // com.miui.aod.common.StyleInfo
    public MamlThemeBean getThemeBean() {
        return this.mThemeStyleBean;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTitleResId() {
        return R.string.deep_space_planet;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isBatterySwitchOn() {
        MamlThemeBean mamlThemeBean = this.mThemeStyleBean;
        if (mamlThemeBean == null) {
            return super.isBatterySwitchOn();
        }
        return mamlThemeBean.mBatteryEnable == 1;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isLunarSwitchOn(Context context) {
        MamlThemeBean mamlThemeBean = this.mThemeStyleBean;
        if (mamlThemeBean == null) {
            return super.isLunarSwitchOn(context);
        }
        return mamlThemeBean.mLunarCalendarEnable == 1;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isNotificationSwitchOn() {
        MamlThemeBean mamlThemeBean = this.mThemeStyleBean;
        if (mamlThemeBean == null) {
            return super.isNotificationSwitchOn();
        }
        return mamlThemeBean.mNotificationEnable == 1;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isValidForShow(Context context) {
        MamlThemeBean mamlThemeBean = this.mThemeStyleBean;
        if (mamlThemeBean == null) {
            return false;
        }
        String mamlFilePath = ThemeUtils.getMamlFilePath(context, mamlThemeBean, "content");
        if (DeviceUtil.INSTANCE.isGteAndroidW() && !Utils.isAodProcess() && !FileUtil.isFileExist(mamlFilePath)) {
            copyMamlInfoToSystemUI(this.mThemeStyleBean);
        }
        return FileUtil.isFileExist(mamlFilePath);
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean needInflateNotification() {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setBg(View view, int i) {
        View viewFindViewById = view.findViewById(R.id.aod_bg);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(8);
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setLunarSwitchOn(boolean z) {
        super.setLunarSwitchOn(z);
        MamlThemeBean mamlThemeBean = this.mThemeStyleBean;
        if (mamlThemeBean != null) {
            mamlThemeBean.mLunarCalendarEnable = z ? 1 : 0;
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setThemeBean(ThemeBean themeBean) {
        this.mThemeStyleBean = (MamlThemeBean) themeBean;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportBatteryIcon() {
        return getThemeBean() != null && getThemeBean().mSupportBattery == 1;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportChangeColorAlongTime() {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportDateAndTime() {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportHideClock() {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportLunarTime(Context context) {
        return getThemeBean() != null && getThemeBean().mSupportLunarCalendar == 1 && super.supportLunarTime(context);
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportNotificationIcon() {
        return getThemeBean() != null && getThemeBean().mSupportNotification == 1;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportSuperWallpaperMode() {
        return true;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void switchOnBatteryIcon(boolean z) {
        super.switchOnBatteryIcon(z);
        MamlThemeBean mamlThemeBean = this.mThemeStyleBean;
        if (mamlThemeBean != null) {
            mamlThemeBean.mBatteryEnable = z ? 1 : 0;
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public void switchOnNotificationIcon(boolean z) {
        super.switchOnNotificationIcon(z);
        MamlThemeBean mamlThemeBean = this.mThemeStyleBean;
        if (mamlThemeBean != null) {
            mamlThemeBean.mNotificationEnable = z ? 1 : 0;
        }
    }
}
