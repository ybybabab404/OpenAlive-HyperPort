package com.miui.aod.category;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.google.gson.annotations.SerializedName;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.ImageSelectorStyleSelectView;
import com.miui.aod.part.PartProvider;
import com.miui.aod.theme.beans.CustomImageStyleBean;
import com.miui.aod.theme.beans.ThemeBean;
import com.miui.aod.util.FileUtils;
import com.miui.aod.util.WindowUtils;
import com.miui.aod.utils.ContentProviderUtils;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.ImageSelectorClock;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.FileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class ImageSelectorCategoryInfo extends CategoryInfo {
    public static final String ALBUM_PATH = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera" + File.separator + ".aod_cache";
    public static final String CACHE_FILE = "/aod_cache";
    public static final int SHOW_STYLE_ABOVEIMAGE = 1;
    public static final int SHOW_STYLE_BELOWIMAGE = 2;
    public static final int SHOW_STYLE_NONE = 0;
    private static final String TAG = "ImageSelectorCategoryInfo";

    @SerializedName("is_invert")
    private boolean isInvert;

    @Nullable
    @SerializedName("content_container_location")
    private int[] mContentContainerLocation;

    @SerializedName("cropped_pic_uri")
    private String mCroppedUriString;

    @SerializedName("show_style")
    private int mExtraInfoShowStyle;

    @SerializedName("change_matrix")
    private float[] mMatrixValues;

    @Nullable
    @SerializedName("set_window_size")
    private Point mSetWindowSize;

    @SerializedName("theme_style_info")
    private CustomImageStyleBean mThemeStyleBean;

    @Nullable
    @SerializedName("pic_uri")
    private String mUriString;

    public ImageSelectorCategoryInfo() {
        super(CategoryConstants.CATEGORY_IMAGE_BG_SELECTOR);
        this.mExtraInfoShowStyle = 2;
    }

    private static String copyImageToAlbum(StyleInfo styleInfo) {
        if (!(styleInfo instanceof ImageSelectorCategoryInfo)) {
            return null;
        }
        String uriString = ((ImageSelectorCategoryInfo) styleInfo).getUriString();
        if (!new File(uriString).exists()) {
            return null;
        }
        String str = ALBUM_PATH;
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        String str2 = str + File.separator + FileUtils.generateRandomFilename();
        FileUtils.fileCopy(uriString, str2);
        return str2;
    }

    public static StyleInfo createStyleInfo(String str) {
        ImageSelectorCategoryInfo imageSelectorCategoryInfo = new ImageSelectorCategoryInfo();
        imageSelectorCategoryInfo.setUriString(str);
        return imageSelectorCategoryInfo;
    }

    public static List<String> fillImageUri(List<StyleInfo> list) {
        if (list == null || list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<StyleInfo> it = list.iterator();
        while (it.hasNext()) {
            String strCopyImageToAlbum = copyImageToAlbum(it.next());
            if (strCopyImageToAlbum != null) {
                arrayList.add(strCopyImageToAlbum);
            }
        }
        return arrayList;
    }

    public static String getImageCacheDir(Context context) {
        return context.getFilesDir() + CACHE_FILE;
    }

    private boolean isFileExist(Context context, String str) {
        if (UserHandleUtils.USER_CURRENT == 0) {
            return FileUtil.isFileExist(str);
        }
        try {
            Bundle bundleCall = context.getContentResolver().call(ContentProviderUtils.maybeAddUserId(PartProvider.URI_PART, UserHandleUtils.USER_CURRENT), "provideFile", str, (Bundle) null);
            return bundleCall != null && bundleCall.getBoolean("fileExist", false);
        } catch (Exception unused) {
            return false;
        }
    }

    public void copyImageToSystemUI(Context context) {
        try {
            if (TextUtils.isEmpty(this.mUriString)) {
                return;
            }
            String str = this.mUriString;
            if (!FileUtil.isGifImage(str) && !TextUtils.isEmpty(this.mCroppedUriString)) {
                str = this.mCroppedUriString;
            }
            int iLastIndexOf = str.lastIndexOf(File.separator) + 1;
            if (iLastIndexOf >= str.length()) {
                return;
            }
            String str2 = AODApplication.sysuiContext.getCacheDir().getAbsolutePath() + "/select_image/" + str.substring(iLastIndexOf);
            if (new File(str2).exists()) {
                return;
            }
            File file = new File(AODApplication.sysuiContext.getCacheDir().getAbsolutePath() + "/select_image/");
            file.mkdirs();
            if (file.listFiles() != null) {
                File[] fileArrListFiles = file.listFiles();
                Objects.requireNonNull(fileArrListFiles);
                for (File file2 : fileArrListFiles) {
                    file2.delete();
                }
            }
            FileUtil.fileCopyFromUri(AODApplication.sysuiContext, ContentProviderUtils.maybeAddUserId(Uri.parse("content://aod.part/select_image/" + str), UserHandleUtils.USER_CURRENT), str2);
        } catch (Exception e) {
            Log.i(TAG, "copy image error: " + e.getMessage());
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public IAodClock createAodClock(boolean z, int i) {
        return (i == 2 || i == 4) ? new ImageSelectorClock(i, false) : new ImageSelectorClock(i, z);
    }

    public String getCroppedUriString() {
        return this.mCroppedUriString;
    }

    public int getExtraInfoShowStyle() {
        return this.mExtraInfoShowStyle;
    }

    public Matrix getMatrixFromValues(@NonNull Context context, @Nullable int[] iArr) {
        Matrix matrix = new Matrix();
        float[] fArr = this.mMatrixValues;
        if (fArr != null) {
            float[] fArrCopyOf = Arrays.copyOf(fArr, fArr.length);
            WindowUtils windowUtils = WindowUtils.INSTANCE;
            Point windowSize = windowUtils.getWindowSize(context);
            Point point = this.mSetWindowSize;
            if (point != null && this.mContentContainerLocation != null && iArr != null && (!windowUtils.windowEquals(point, windowSize) || !Arrays.equals(this.mContentContainerLocation, iArr))) {
                PointF pointFScale = windowUtils.scale(this.mSetWindowSize, windowSize);
                float f = fArrCopyOf[2];
                int[] iArr2 = this.mContentContainerLocation;
                float f2 = f - iArr2[0];
                float f3 = fArrCopyOf[5] - iArr2[1];
                fArrCopyOf[2] = iArr[0] + (f2 * pointFScale.x);
                fArrCopyOf[5] = iArr[1] + (f3 * pointFScale.y);
            }
            matrix.setValues(fArrCopyOf);
        }
        return matrix;
    }

    @Override // com.miui.aod.common.StyleInfo
    public Map<String, Object> getParameterForAnalytic() {
        ArrayMap arrayMap = new ArrayMap();
        String styleNameForAnalytic = getStyleNameForAnalytic();
        arrayMap.put("style_event", "custom_image");
        arrayMap.put("style_name", "custom_image");
        arrayMap.put("style_type", styleNameForAnalytic);
        return arrayMap;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getPreviewTopPaddingResId() {
        return R.dimen.image_selector_preview_large_top_padding;
    }

    public String getStyleNameForAnalytic() {
        String str;
        int i = this.mExtraInfoShowStyle;
        if (i != 0) {
            str = i != 1 ? "SHOW_STYLE_BELOWIMAGE" : "SHOW_STYLE_ABOVEIMAGE";
        } else {
            str = "SHOW_STYLE_NONE";
        }
        return str.toLowerCase();
    }

    @Override // com.miui.aod.common.StyleInfo
    public String getStyleSelectViewName() {
        return ImageSelectorStyleSelectView.class.getName();
    }

    @Override // com.miui.aod.common.StyleInfo
    public CustomImageStyleBean getThemeBean() {
        return this.mThemeStyleBean;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTitleResId() {
        return R.string.custom_image;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTopMargin(Context context) {
        return 0;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTopMarginPreview(Context context) {
        return 0;
    }

    public String getUriString() {
        return this.mUriString;
    }

    public boolean isInvert() {
        return this.isInvert;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isSameHistoryRecord(StyleInfo styleInfo) {
        String str;
        return (styleInfo instanceof ImageSelectorCategoryInfo) && (str = this.mUriString) != null && str.equals(((ImageSelectorCategoryInfo) styleInfo).getUriString());
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isValidForShow(Context context) {
        if (DeviceUtil.INSTANCE.isGteAndroidW()) {
            if (!TextUtils.isEmpty(this.mUriString)) {
                copyImageToSystemUI(context);
                String str = this.mUriString;
                if (!FileUtil.isGifImage(str) && !TextUtils.isEmpty(this.mCroppedUriString)) {
                    str = this.mCroppedUriString;
                }
                int iLastIndexOf = str.lastIndexOf(File.separator) + 1;
                if (iLastIndexOf >= str.length()) {
                    return false;
                }
                return new File(context.getCacheDir().getAbsolutePath() + "/select_image/" + str.substring(iLastIndexOf)).exists();
            }
        } else {
            if (!TextUtils.isEmpty(this.mCroppedUriString) && isFileExist(context, this.mCroppedUriString)) {
                return true;
            }
            if (!TextUtils.isEmpty(this.mUriString)) {
                return isFileExist(context, this.mUriString);
            }
        }
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean needInflateNotification() {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void onDeleteHistoryRecord(Context context) {
        super.onDeleteHistoryRecord(context);
        String str = this.mCroppedUriString;
        if (str != null) {
            try {
                if (str.endsWith("cropped_image_select.jpg")) {
                    return;
                }
                FileUtils.deleteFileAll(new File(this.mCroppedUriString));
            } catch (Exception e) {
                Log.e(TAG, "error...", e);
            }
        }
    }

    public void setCroppedUriString(String str) {
        this.mCroppedUriString = str;
    }

    public void setExtraInfoShowStyle(int i) {
        this.mExtraInfoShowStyle = i;
    }

    public void setInvert(boolean z) {
        this.isInvert = z;
    }

    public void setMatrixValues(@Nullable float[] fArr, @NonNull Context context, @Nullable int[] iArr) {
        if (fArr == null) {
            this.mMatrixValues = null;
            this.mSetWindowSize = null;
            this.mContentContainerLocation = null;
        } else {
            this.mSetWindowSize = WindowUtils.INSTANCE.getWindowSize(context);
            this.mContentContainerLocation = iArr;
            this.mMatrixValues = fArr;
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setThemeBean(ThemeBean themeBean) {
        this.mThemeStyleBean = (CustomImageStyleBean) themeBean;
    }

    public void setUriString(@Nullable String str) {
        this.mUriString = str;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportStepComponent() {
        return true;
    }
}
