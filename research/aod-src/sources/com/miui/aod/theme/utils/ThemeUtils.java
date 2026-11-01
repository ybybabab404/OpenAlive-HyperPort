package com.miui.aod.theme.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;
import androidx.collection.ArrayMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.miui.aod.AODApplication;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.db.ThemeEntity;
import com.miui.aod.db.ThemeProviderHelper;
import com.miui.aod.theme.ThemeInvalidException;
import com.miui.aod.theme.beans.ClockStyleBean;
import com.miui.aod.theme.beans.ImageStyleBean;
import com.miui.aod.theme.beans.MamlThemeBean;
import com.miui.aod.theme.beans.ThemeBean;
import com.miui.aod.theme.beans.WaveClockStyleBean;
import com.miui.aod.util.FileUtils;
import com.miui.aod.util.NetUtils;
import com.miui.aod.util.ZipUtils;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import miui.os.Build;
import miuix.appcompat.app.AppCompatActivity;
import org.greenrobot.greendao.DaoException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ThemeUtils {
    public static Bundle mCachedBundle;
    public static final List sIntegerPathInXml;
    public static final Map sThemeMap;

    static {
        ArrayList arrayList = new ArrayList();
        sIntegerPathInXml = arrayList;
        ArrayMap arrayMap = new ArrayMap();
        sThemeMap = arrayMap;
        arrayList.add("/MIUI_Theme_Values/lunar_calendar_enable");
        arrayList.add("/MIUI_Theme_Values/notification_enable");
        arrayList.add("/MIUI_Theme_Values/battery_enable");
        arrayList.add("/MIUI_Theme_Values/support_lunar_calendar");
        arrayList.add("/MIUI_Theme_Values/support_notification");
        arrayList.add("/MIUI_Theme_Values/support_battery");
        arrayList.add("/MIUI_Theme_Values/duration_expected");
        arrayMap.put("static_clock_style", new Pair(ClockStyleBean.class, CategoryConstants.CATEGORY_CLOCK_PANEL));
        arrayMap.put("wave_clock_style", new Pair(WaveClockStyleBean.class, CategoryConstants.CATEGORY_ANIMATE_CLOCK_PANEL));
        arrayMap.put("one_line_style", new Pair(ImageStyleBean.class, CategoryConstants.CATEGORY_ONELINE));
        arrayMap.put("paint_vertical_style", new Pair(ImageStyleBean.class, CategoryConstants.CATEGORY_PAINT_VERTICAL));
        arrayMap.put(CategoryConstants.CATEGORY_MAML_STYLE, new Pair(MamlThemeBean.class, CategoryConstants.CATEGORY_MAML));
    }

    public static StyleInfo addNewTheme(Context context, String str, String str2, String str3, List list, boolean z) throws ThemeInvalidException {
        File file = new File(str);
        if (!file.exists()) {
            throw new ThemeInvalidException("themeZipFile not exist " + file.getName());
        }
        if (!z) {
            try {
                ThemeEntity themeByIdentity = ThemeProviderHelper.getInstance(context).getThemeByIdentity(str2);
                if (themeByIdentity != null) {
                    ThemeProviderHelper.getInstance(context).delete(themeByIdentity);
                }
            } catch (DaoException e) {
                throw new ThemeInvalidException("error: query theme id:" + str2 + " " + e.getMessage());
            }
        }
        String strGenerateRandomFilename = FileUtils.generateRandomFilename();
        if (z) {
            strGenerateRandomFilename = "temporary_theme_" + strGenerateRandomFilename;
        }
        String str4 = strGenerateRandomFilename;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(getRootDir(context));
            String str5 = File.separator;
            sb.append(str5);
            sb.append(str4);
            ZipUtils.unzipFile(str, sb.toString());
            String str6 = getRootDir(context) + str5 + str4;
            String str7 = str6 + str5 + "aod_description.xml";
            if (!new File(str7).exists()) {
                throw new ThemeInvalidException("aod_description.xml doesn't exist");
            }
            ThemeBean theme = parseTheme(str7);
            if (theme == null) {
                throw new ThemeInvalidException("themeBean == null");
            }
            theme.mIdentity = str2;
            theme.mThemeDirName = str4;
            if ((theme instanceof MamlThemeBean) && list != null) {
                File fileCreateDrawableDir = createDrawableDir(str6);
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    String str8 = (String) it.next();
                    if ((str8 != null && str8.contains("preview_aod")) || str8.contains("preview_spaod")) {
                        Log.i("ThemeUtils", "addNewTheme: copyPreviewImage " + str8);
                        String strCopyPreviewImage = copyPreviewImage(context, fileCreateDrawableDir, str8);
                        if (str8.contains(CategoryConstants.CATEGORY_MAML_SMALL)) {
                            ((MamlThemeBean) theme).mMamlThumbnail = strCopyPreviewImage;
                        }
                    }
                }
            }
            String stringFromFilePath = null;
            if (!TextUtils.isEmpty(str3) && (Utils.isUri(str3) || new File(str3).exists())) {
                try {
                    stringFromFilePath = FileUtils.parseStringFromFilePath(context, str3);
                    ThemeMetaBean themeMetaBean = (ThemeMetaBean) new Gson().fromJson(stringFromFilePath, ThemeMetaBean.class);
                    if (themeMetaBean != null) {
                        if (themeMetaBean.getTitles() != null) {
                            if (TextUtils.isEmpty(themeMetaBean.getTitles().getZh_CN())) {
                                theme.mThemeName = themeMetaBean.getTitles().getFallback();
                            } else {
                                theme.mThemeName = themeMetaBean.getTitles().getZh_CN();
                            }
                        }
                        if (themeMetaBean.getDescriptions() != null) {
                            if (TextUtils.isEmpty(themeMetaBean.getDescriptions().getZh_CN())) {
                                theme.mThemeDescription = themeMetaBean.getDescriptions().getFallback();
                            } else {
                                theme.mThemeDescription = themeMetaBean.getDescriptions().getZh_CN();
                            }
                        }
                    }
                } catch (JsonSyntaxException e2) {
                    Log.e("ThemeUtils", "error...", e2);
                }
            }
            if (!TextUtils.isEmpty(stringFromFilePath)) {
                theme.mI18nThemeName = parseI18nMap(stringFromFilePath, "titles");
                theme.mI18nThemeDescription = parseI18nMap(stringFromFilePath, "descriptions");
            }
            theme.mTemporaryTheme = z ? 1 : 0;
            String json = new Gson().toJson(theme);
            StyleInfo styleInfoCreateStyleInfoFromThemeBean = createStyleInfoFromThemeBean(theme);
            if (!z) {
                ThemeProviderHelper.getInstance(context).insert(new ThemeEntity(null, str2, theme.mThemeType, json, str4, theme.mThemeName, theme.mThemeDescription));
            }
            return styleInfoCreateStyleInfoFromThemeBean;
        } catch (IOException e3) {
            Log.e("ThemeUtils", "error...", e3);
            throw new ThemeInvalidException("unZip failed: " + e3.getMessage());
        }
    }

    public static void clearThemeResource(Context context, Set set) {
        File[] fileArrListFiles;
        if (!getRootDir(context).startsWith(context.getDataDir().getAbsolutePath())) {
            throw new RuntimeException("ThemeUtils.getRootDir return error path");
        }
        File file = new File(getRootDir(context));
        if (set == null) {
            Log.i("ThemeUtils", "clearThemeResource: fileSetUsing == null");
        } else {
            Log.i("ThemeUtils", "ClearJob: count to reserve : " + set.size() + " rootDir: " + file.getName());
        }
        if (!file.exists() || (fileArrListFiles = file.listFiles()) == null) {
            return;
        }
        for (File file2 : fileArrListFiles) {
            if (set == null || !set.contains(file2.getName())) {
                file2.delete();
            }
        }
    }

    public static String copyPreviewImage(Context context, File file, String str) throws ThemeInvalidException {
        if (str == null) {
            throw new ThemeInvalidException("previewPath == null");
        }
        if (Utils.isUri(str)) {
            String strSubstring = str.substring(str.lastIndexOf(47) + 1);
            FileUtils.fileCopyFromUri(context, Uri.parse(str), file.getAbsolutePath() + File.separator + strSubstring);
            return strSubstring;
        }
        File file2 = new File(str);
        String name = file2.getName();
        if (!file2.exists()) {
            throw new ThemeInvalidException(str + " not exist");
        }
        FileUtils.fileCopy(str, file.getAbsolutePath() + File.separator + file2.getName());
        return name;
    }

    public static File createDrawableDir(String str) {
        File file = new File(str + File.separator + "drawable");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static StyleInfo createStyleInfoFromThemeBean(ThemeBean themeBean) {
        Map map = sThemeMap;
        if (!map.containsKey(themeBean.mThemeType)) {
            return null;
        }
        StyleInfo styleInfoCreateStyleInfo = CategoryFactory.createStyleInfo(AODApplication.sInstance, (String) ((Pair) map.get(themeBean.mThemeType)).second, CategoryConstants.DEFAULT_PARAM);
        styleInfoCreateStyleInfo.setThemeBean(themeBean);
        return styleInfoCreateStyleInfo;
    }

    public static Bitmap getBitmap(Context context, ThemeBean themeBean, String str) {
        return getBitmap(getBitmapPath(context, themeBean, str));
    }

    public static Bitmap getBitmap(String str) {
        Bitmap bitmapDecodeStream = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDensity = 440;
        options.inScaled = true;
        options.inSampleSize = 2;
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
            try {
                bitmapDecodeStream = BitmapFactory.decodeStream(bufferedInputStream, null, options);
                bufferedInputStream.close();
                return bitmapDecodeStream;
            } catch (Throwable th) {
                try {
                    bufferedInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e("ThemeUtils", "error...", e);
            return bitmapDecodeStream;
        }
    }

    public static String getBitmapPath(Context context, ThemeBean themeBean, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getRootDir(context));
        String str2 = File.separator;
        sb.append(str2);
        sb.append(themeBean.mThemeDirName);
        sb.append(str2);
        sb.append("drawable");
        sb.append(str2);
        sb.append(str);
        return sb.toString();
    }

    public static Bundle getCachedBundle() {
        return mCachedBundle;
    }

    public static String getCateNameByThemeType(String str) {
        Map map = sThemeMap;
        if (!map.containsKey(str) || map.get(str) == null) {
            return null;
        }
        return (String) ((Pair) map.get(str)).second;
    }

    public static String getMamlFilePath(Context context, ThemeBean themeBean, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(getRootDir(context));
        String str2 = File.separator;
        sb.append(str2);
        sb.append(themeBean.mThemeDirName);
        sb.append(str2);
        sb.append(str);
        return sb.toString();
    }

    public static String getRootDir(Context context) {
        return context.getDir("themes", 0).getAbsolutePath();
    }

    public static void notifyWithBroadcast(Context context) {
        Intent intent = new Intent("com.miui.aod.AOD_CHANGED");
        intent.setPackage("com.android.thememanager");
        if (!Utils.isUsingSuperWallpaperStyle(context) && CategoryConstants.CATEGORY_MAML.equals(Utils.getAodUsingCategoryName(context))) {
            StyleInfo styleInfoCreateStyleInfo = CategoryFactory.createStyleInfo(context, CategoryConstants.CATEGORY_MAML);
            if (styleInfoCreateStyleInfo.getThemeBean() != null) {
                intent.putExtra("identity", styleInfoCreateStyleInfo.getThemeBean().mIdentity);
            }
        }
        Log.i("ThemeUtils", "notifyWithBroadcast: " + intent);
        context.sendBroadcast(intent);
    }

    public static Map parseI18nMap(String str, String str2) {
        JsonElement jsonElement;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JsonElement string = JsonParser.parseString(str);
            if (string.isJsonObject() && (jsonElement = string.getAsJsonObject().get(str2)) != null && jsonElement.isJsonObject()) {
                ArrayMap arrayMap = new ArrayMap();
                for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                    if (entry.getValue().isJsonPrimitive()) {
                        arrayMap.put(entry.getKey(), entry.getValue().getAsString());
                    }
                }
                if (arrayMap.isEmpty()) {
                    return null;
                }
                return arrayMap;
            }
            return null;
        } catch (Exception e) {
            Log.e("ThemeUtils", "parseI18nMap error: " + str2, e);
            return null;
        }
    }

    public static ThemeBean parseTheme(String str) {
        return parseThemeFromJson((TextUtils.isEmpty(str) || !str.toLowerCase().endsWith(".xml")) ? FileUtils.parseStringFromFilePath(str) : parseThemeBeanJsonFromXmlString(FileUtils.parseStringFromFilePath(str)));
    }

    public static String parseThemeBeanJsonFromXmlString(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.i("ThemeUtils", "parseTheme: xmlStr empty: " + str);
            return null;
        }
        if (str.startsWith("\ufeff")) {
            str = str.substring(1);
        }
        XmlToJson.Builder builder = new XmlToJson.Builder(str);
        Iterator it = sIntegerPathInXml.iterator();
        while (it.hasNext()) {
            builder.forceIntegerForPath((String) it.next());
        }
        JSONObject json = builder.build().toJson();
        if (json != null) {
            try {
                return json.getString("MIUI_Theme_Values");
            } catch (JSONException e) {
                Log.wtf("ThemeUtils", "parseThemeBeanJsonFromXmlString: ", e);
            }
        }
        return null;
    }

    public static ThemeBean parseThemeFromJson(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.i("ThemeUtils", "parseTheme: jsonStr empty: " + str);
            return null;
        }
        JsonElement string = JsonParser.parseString(str);
        if (string.isJsonObject()) {
            JsonElement jsonElement = string.getAsJsonObject().get("theme_type");
            if (jsonElement == null) {
                Log.i("ThemeUtils", "parseTheme: themeElement empty");
                return null;
            }
            String asString = jsonElement.getAsString();
            Map map = sThemeMap;
            if (map.containsKey(asString)) {
                return (ThemeBean) new Gson().fromJson(str, (Class) ((Pair) map.get(asString)).first);
            }
            Log.i("ThemeUtils", "parseTheme: unsupport");
        }
        return null;
    }

    public static void setCachedBundle(Bundle bundle) {
        mCachedBundle = bundle;
    }

    public static void startThemeDetail(Context context, Bundle bundle, boolean z) {
        ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
        Bundle bundleCall = contentResolver.call(Uri.parse("content://com.android.thememanager.settings_onlinewallpaper_provider"), "getEnterIntent", z ? "detail" : null, bundle);
        Intent intent = bundleCall != null ? (Intent) bundleCall.getParcelable("android.intent.extra.INTENT", Intent.class) : null;
        if (intent != null) {
            context.startActivity(intent);
            return;
        }
        Log.w("ThemeUtils", "fail get Intent. use old method!");
        if (contentResolver.call(Uri.parse("content://com.android.thememanager.settings_onlinewallpaper_provider"), z ? "enterAODDetail" : "enterAODSubject", (String) null, bundle) == null) {
            Log.e("ThemeUtils", "finally, fail call startThemeDetail. " + z);
        }
    }

    public static void startThemeHome(AppCompatActivity appCompatActivity, boolean z) {
        String str;
        try {
            if (Utils.isFoldDevice()) {
                if (!NetUtils.INSTANCE.isNetSystemUsable(appCompatActivity)) {
                    Toast.makeText(appCompatActivity, "当前无网络连接，请连接网络后再次尝试！", 0).show();
                    return;
                }
                Intent intent = new Intent();
                intent.setAction("com.thememanager.action.REQUEST_CTA");
                intent.setPackage("com.android.thememanager");
                appCompatActivity.startActivity(intent);
                return;
            }
            if (z) {
                str = "theme://zhuti.xiaomi.com/mainpage?S.EXTRA_NAV_ID=resourcecategory&S.EXTRA_TAB_ID=aod&miref=aod_picker";
            } else {
                str = "theme://zhuti.xiaomi.com/mainpage?S.EXTRA_NAV_ID=homepage&S.EXTRA_TAB_ID=aod&miref=aod_picker";
            }
            appCompatActivity.startActivity(Intent.parseUri(str, 1));
        } catch (URISyntaxException e) {
            Log.e("ThemeUtils", "error...", e);
        }
    }

    public static boolean supportAodTheme(Context context) {
        return !Build.IS_INTERNATIONAL_BUILD && Utils.getVersionCode(context, "com.android.thememanager") >= 984;
    }
}
