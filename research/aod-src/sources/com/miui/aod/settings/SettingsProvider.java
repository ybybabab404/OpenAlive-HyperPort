package com.miui.aod.settings;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.miui.aod.AODStyleController;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.BlankSuperWallpaperCategoryInfo;
import com.miui.aod.category.CategoriesItemInfo;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.LinkageClockCategoryInfo;
import com.miui.aod.category.MamlCategoryInfo;
import com.miui.aod.category.SmartCoverStyleInfo;
import com.miui.aod.category.SuperWallpaperCategoryInfo;
import com.miui.aod.category.SuperWallpaperInfo;
import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.DrawableData;
import com.miui.aod.components.view.AODBatteryMeterView;
import com.miui.aod.components.view.SquareVerticalTextView;
import com.miui.aod.icu.GetZoneNameTask;
import com.miui.aod.util.BatteryController;
import com.miui.aod.util.BitmapFactoryUtils;
import com.miui.aod.util.DeviceInfo;
import com.miui.aod.util.MMKVUtils;
import com.miui.aod.util.ScreenResolutionUtils;
import com.miui.aod.util.WallPaperUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.SignatureAodView;
import com.miui.aod.widget.SignatureClock;
import com.miui.aod.widget.SuperWallpaperClock;
import com.miui.aod.widget.notification.NotificationStyleManager;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class SettingsProvider extends ContentProvider {

    /* JADX INFO: renamed from: com.miui.aod.settings.SettingsProvider$1, reason: invalid class name */
    class AnonymousClass1 extends TypeToken<CategoriesItemInfo> {
    }

    public static String getShowModeTip(Context context) {
        CharSequence[] showStyleEntries = AODSettings.getShowStyleEntries(context);
        int iVerifyShowStyle = Utils.verifyShowStyle(context, Utils.getShowStyle(context));
        if (iVerifyShowStyle != 1) {
            int iTranslateShowStyleValue2Index = AODSettings.translateShowStyleValue2Index(iVerifyShowStyle);
            if (iTranslateShowStyleValue2Index < 0 || iTranslateShowStyleValue2Index >= showStyleEntries.length) {
                return null;
            }
            return showStyleEntries[iTranslateShowStyleValue2Index].toString();
        }
        Calendar calendar = new Calendar();
        String string = DateFormat.is24HourFormat(context) ? context.getResources().getString(R.string.scheduled_mode_dialog_msg_24) : context.getResources().getString(R.string.scheduled_mode_dialog_msg_12);
        long aodStartTime = Utils.getAodStartTime(context);
        long aodEndTime = Utils.getAodEndTime(context);
        calendar.set(18, (int) (aodStartTime / 3600000));
        calendar.set(20, (int) ((aodStartTime % 3600000) / 60000));
        String str = calendar.format(context, string);
        calendar.set(18, (int) (aodEndTime / 3600000));
        calendar.set(20, (int) ((aodEndTime % 3600000) / 60000));
        return str + '-' + calendar.format(context, string);
    }

    @Override // android.content.ContentProvider
    public Bundle call(String str, String str2, Bundle bundle) {
        if (bundle == null) {
            Log.d("SettingsProvider", "extras = null");
            return null;
        }
        Log.d("SettingsProvider", "call " + str + " " + getCallingPackage());
        MMKVUtils.initialize(getContext().getApplicationContext());
        str.getClass();
        switch (str) {
            case "getClockImageForSuperWallpaper":
                String string = bundle.getString("super_wallpaper_bean");
                Log.w("SettingsProvider", "getClockImageForSuperWallpaper call: " + string);
                bundle.putParcelable("uri", getClockImageForSuperWallpaper(string, bundle));
                break;
            case "getThumbnailForWallpaper":
                bundle.putParcelable("uri", getThumbnailForWallpaper(bundle));
                break;
            case "getAodModeTip":
                bundle.putString("modeTip", getAodModeTip());
                break;
            case "getAodThumbnail":
                bundle.putParcelable("thumbnail", getAodThumbnailBitmap());
                break;
            case "getShowModeTip":
                bundle.putString("modeTip", getShowModeTip(getContext()));
                break;
            case "getNotifyThumbnail":
                String selectedStyleName = NotificationStyleManager.getSelectedStyleName(getContext());
                bundle.putString("notifyStyleName", selectedStyleName);
                bundle.putParcelable("thumbnail", getNotifyThumbnailBitmap(selectedStyleName));
                break;
            case "getThumbnail":
                bundle.putParcelable("uri", getThumbnail());
                break;
        }
        Log.d("SettingsProvider", "call end " + str);
        return bundle;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public String getAodModeTip() {
        Context context;
        int i;
        if (Utils.isAodEnable(getContext())) {
            context = getContext();
            i = R.string.on;
        } else {
            context = getContext();
            i = R.string.off;
        }
        return context.getString(i);
    }

    public final Bitmap getAodThumbnailBitmap() {
        float f = getContext().getResources().getFloat(R.dimen.aod_item_ratio);
        int smallViewWidth = Utils.getSmallViewWidth(getContext());
        int i = (int) (smallViewWidth * f);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.aod_thumbnail_width);
        float f2 = dimensionPixelSize;
        int i2 = (int) (f2 * f);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
        View viewInflate = layoutInflaterFrom.inflate(R.layout.aod_style_category_item, (ViewGroup) null, false);
        viewInflate.setForeground(null);
        AODStyleController aODStyleController = new AODStyleController(2);
        StyleInfo styleInfo = AODStyleController.getStyleInfo(getContext());
        styleInfo.setLabel(null);
        if (styleInfo instanceof BlankSuperWallpaperCategoryInfo) {
            return WallPaperUtils.getBlankSuperWallpaperThumbnail();
        }
        if (styleInfo instanceof TextStyleCategoryInfo) {
            aODStyleController = new AODStyleController(3);
            ((TextStyleCategoryInfo) styleInfo).setCanShowExtraInfo(false);
            viewInflate = layoutInflaterFrom.inflate(R.layout.aod_mode_layout, (ViewGroup) null, false).findViewById(R.id.clock_container);
            aODStyleController.inflateView(viewInflate, styleInfo);
            smallViewWidth = getContext().getResources().getDimensionPixelOffset(R.dimen.aod_textstyle_width);
            i = (int) (smallViewWidth * f);
        } else if ((styleInfo instanceof MamlCategoryInfo) && Utils.isLayoutRtl()) {
            aODStyleController = new AODStyleController(6);
            aODStyleController.inflateView(viewInflate, styleInfo);
        } else if (styleInfo instanceof LinkageClockCategoryInfo) {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            aODStyleController = new AODStyleController(6);
            aODStyleController.inflateView(viewInflate, styleInfo);
        } else {
            aODStyleController.inflateView(viewInflate, styleInfo);
        }
        aODStyleController.handleUpdateTime(DateFormat.is24HourFormat(getContext()));
        viewInflate.measure(View.MeasureSpec.makeMeasureSpec(smallViewWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(i, 1073741824));
        viewInflate.layout(0, 0, smallViewWidth, i);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(dimensionPixelSize, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.scale((f2 * 1.0f) / smallViewWidth, (i2 * 1.0f) / i);
        viewInflate.draw(canvas);
        return bitmapCreateBitmap;
    }

    public final Uri getClockImageForSuperWallpaper(String str, Bundle bundle) {
        StyleInfo styleInfo;
        if (str == null) {
            return null;
        }
        Point realSize = DeviceInfo.getRealSize();
        int i = realSize.x;
        int i2 = realSize.y;
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.thumbnail_real_aod_layout, (ViewGroup) null);
        viewInflate.setForeground(null);
        AODStyleController aODStyleController = new AODStyleController(5);
        View viewFindViewById = viewInflate.findViewById(R.id.clock_container);
        try {
            if (CategoryConstants.CATEGORY_BLANK_SUPER_WALLPAPER.equals(((SuperWallpaperInfo) new Gson().fromJson(str, SuperWallpaperInfo.class)).getMAodCategory())) {
                return null;
            }
            styleInfo = (StyleInfo) new Gson().fromJson(str, SuperWallpaperCategoryInfo.class);
        } catch (JsonSyntaxException e) {
            Log.e("SettingsProvider", "getClockImageForSuperWallpaper: ", e);
            styleInfo = null;
        }
        if (styleInfo != null) {
            styleInfo.setLabel(null);
            styleInfo.switchOnDateAndTime(false);
            styleInfo.switchOnNotificationIcon(false);
            styleInfo.switchOnBatteryIcon(false);
        }
        IAodClock iAodClockInflateView = aODStyleController.inflateView(viewFindViewById, styleInfo);
        if (AODSettings.isDualClock(getContext()) && (iAodClockInflateView instanceof SuperWallpaperClock)) {
            final CountDownLatch countDownLatch = new CountDownLatch(2);
            ((SuperWallpaperClock) iAodClockInflateView).setGetZoneNameCompleteListener(new GetZoneNameTask.OnGetZoneNameCompleteListener() { // from class: com.miui.aod.settings.SettingsProvider$$ExternalSyntheticLambda0
                @Override // com.miui.aod.icu.GetZoneNameTask.OnGetZoneNameCompleteListener
                public final void onComplete(String str2) {
                    countDownLatch.countDown();
                }
            });
            try {
                countDownLatch.await(1L, TimeUnit.SECONDS);
            } catch (InterruptedException e2) {
                Log.e("SettingsProvider", "getClockImageForSuperWallpaper: ", e2);
            }
        }
        aODStyleController.handleUpdateTime(DateFormat.is24HourFormat(getContext()));
        View viewFindViewById2 = viewInflate.findViewById(R.id.aod_bg);
        if (viewFindViewById2 != null) {
            viewFindViewById2.setVisibility(4);
        }
        viewInflate.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
        viewInflate.layout(0, 0, i, i2);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        new Paint().setAntiAlias(true);
        viewInflate.draw(canvas);
        File file = new File(getContext().getFilesDir(), "images");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(file, "clock_img.png");
        try {
            BitmapFactoryUtils.INSTANCE.saveToFile(bitmapCreateBitmap, file2.getPath(), true);
            bundle.putParcelable("image", bitmapCreateBitmap);
        } catch (IOException e3) {
            Log.e("SettingsProvider", "error...", e3);
        } catch (OutOfMemoryError e4) {
            Log.e("SettingsProvider", "getClockImageForSuperWallpaper: ", e4);
        }
        Uri uriForFile = FileProvider.getUriForFile(getContext(), "com.miui.aod.fileprovider", file2);
        getContext().grantUriPermission(getCallingPackage(), uriForFile, 1);
        return uriForFile;
    }

    public final Bitmap getNotifyThumbnailBitmap(String str) {
        if (str == null) {
            Log.d("SettingsProvider", "notify thumbnail is null.");
            return null;
        }
        for (int i = 0; i < NotificationStyleManager.getStyleList().size(); i++) {
            DrawableData drawableData = NotificationStyleManager.getStyleList().get(i);
            if (str.equals(drawableData.mName)) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), drawableData.mResId);
                if (drawable instanceof BitmapDrawable) {
                    return ((BitmapDrawable) drawable).getBitmap();
                }
                if (!(drawable instanceof VectorDrawable) && !(drawable instanceof VectorDrawableCompat)) {
                    return null;
                }
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                return bitmapCreateBitmap;
            }
        }
        return null;
    }

    public final Uri getThumbnail() {
        float f = getContext().getResources().getFloat(R.dimen.aod_item_ratio);
        int smallViewWidth = Utils.getSmallViewWidth(getContext());
        int i = (int) (smallViewWidth * f);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.aod_thumbnail_width);
        float f2 = dimensionPixelSize;
        int i2 = (int) (f2 * f);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
        View viewInflate = layoutInflaterFrom.inflate(R.layout.aod_style_category_item, (ViewGroup) null, false);
        viewInflate.setForeground(null);
        AODStyleController aODStyleController = new AODStyleController(2);
        StyleInfo styleInfo = AODStyleController.getStyleInfo(getContext());
        styleInfo.setLabel(null);
        if (styleInfo instanceof TextStyleCategoryInfo) {
            aODStyleController = new AODStyleController(3);
            ((TextStyleCategoryInfo) styleInfo).setCanShowExtraInfo(false);
            viewInflate = layoutInflaterFrom.inflate(R.layout.aod_mode_layout, (ViewGroup) null, false).findViewById(R.id.clock_container);
            aODStyleController.inflateView(viewInflate, styleInfo);
            smallViewWidth = getContext().getResources().getDimensionPixelOffset(R.dimen.aod_textstyle_width);
            i = (int) (smallViewWidth * f);
        } else if ((styleInfo instanceof MamlCategoryInfo) && Utils.isLayoutRtl()) {
            aODStyleController = new AODStyleController(6);
            aODStyleController.inflateView(viewInflate, styleInfo);
        } else {
            aODStyleController.inflateView(viewInflate, styleInfo);
        }
        aODStyleController.handleUpdateTime(DateFormat.is24HourFormat(getContext()));
        viewInflate.measure(View.MeasureSpec.makeMeasureSpec(smallViewWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(i, 1073741824));
        viewInflate.layout(0, 0, smallViewWidth, i);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(dimensionPixelSize, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        float f3 = i2;
        RectF rectF = new RectF(0.0f, 0.0f, f2, f3);
        int dimensionPixelSize2 = getContext().getResources().getDimensionPixelSize(R.dimen.aod_thumbnail_corners_radius);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        float f4 = dimensionPixelSize2;
        canvas.drawRoundRect(rectF, f4, f4, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        int iSaveLayer = canvas.saveLayer(rectF, paint);
        canvas.scale((f2 * 1.0f) / smallViewWidth, (f3 * 1.0f) / i);
        viewInflate.draw(canvas);
        canvas.restoreToCount(iSaveLayer);
        Drawable drawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.aod_style_fg_n, null);
        if (drawable != null) {
            drawable.setBounds(new Rect(0, 0, dimensionPixelSize, i2));
            drawable.draw(canvas);
        }
        File file = new File(getContext().getFilesDir(), "images");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(file, "thumbnail.png");
        try {
            BitmapFactoryUtils.INSTANCE.saveToFile(bitmapCreateBitmap, file2.getPath(), true);
        } catch (IOException e) {
            Log.e("SettingsProvider", "error...", e);
        }
        Uri uriForFile = FileProvider.getUriForFile(getContext(), "com.miui.aod.fileprovider", file2);
        getContext().grantUriPermission(getCallingPackage(), uriForFile, 1);
        return uriForFile;
    }

    public final Uri getThumbnailForWallpaper(Bundle bundle) {
        ScreenResolutionUtils.cleanRealSize();
        Point realSize = DeviceInfo.getRealSize();
        int i = realSize.y;
        int i2 = realSize.x;
        float f = i2;
        int i3 = (int) (((i * 1.0f) / i2) * f);
        float f2 = (1.0f * f) / f;
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.thumbnail_real_aod_layout, (ViewGroup) null);
        viewInflate.setForeground(null);
        View viewFindViewById = viewInflate.findViewById(R.id.aod_content_container);
        AODStyleController aODStyleController = new AODStyleController(5);
        View viewFindViewById2 = viewInflate.findViewById(R.id.clock_container);
        StyleInfo styleInfo = AODStyleController.getStyleInfo(getContext());
        styleInfo.setLabel(null);
        styleInfo.switchOnNotificationIcon(false);
        boolean z = styleInfo instanceof LinkageClockCategoryInfo;
        if (z && Looper.myLooper() == null) {
            Looper.prepare();
        }
        IAodClock iAodClockInflateView = aODStyleController.inflateView(viewFindViewById2, styleInfo);
        AODBatteryMeterView aODBatteryMeterView = (AODBatteryMeterView) viewInflate.findViewById(R.id.aod_battery_layout);
        if (aODBatteryMeterView != null) {
            BatteryController.getInstance(getContext()).notifyBatteryStatus(getContext(), aODBatteryMeterView);
        }
        aODStyleController.handleUpdateTime(DateFormat.is24HourFormat(getContext()));
        viewInflate.measure(View.MeasureSpec.makeMeasureSpec(i2, 1073741824), View.MeasureSpec.makeMeasureSpec(i, 1073741824));
        viewInflate.layout(0, 0, i2, i);
        if (styleInfo instanceof TextStyleCategoryInfo) {
            SignatureAodView view = ((SignatureClock) iAodClockInflateView).getView();
            SquareVerticalTextView signatureContent = view.getSignatureContent();
            Rect rect = new Rect();
            signatureContent.onPreDraw();
            view.changeTextViewLayout(signatureContent.initDrawMask(rect));
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        RectF rectF = new RectF(0.0f, 0.0f, f, i3);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int iSaveLayer = canvas.saveLayer(rectF, paint);
        if ((!(styleInfo instanceof SuperWallpaperCategoryInfo) && !(styleInfo instanceof SmartCoverStyleInfo)) || z) {
            canvas.translate(0.0f, viewFindViewById != null ? (i - viewFindViewById.getHeight()) / 2 : 200);
        }
        canvas.scale(f2, f2);
        viewInflate.draw(canvas);
        canvas.restoreToCount(iSaveLayer);
        File file = new File(getContext().getFilesDir(), "images");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(file, "thumbnail_wallpaper.png");
        try {
            BitmapFactoryUtils.INSTANCE.saveToFile(bitmapCreateBitmap, file2.getPath(), true);
            bundle.putParcelable("image", bitmapCreateBitmap);
        } catch (IOException e) {
            Log.e("SettingsProvider", "error...", e);
        } catch (OutOfMemoryError e2) {
            Log.e("SettingsProvider", "getThumbnailForWallpaper: ", e2);
        }
        Uri uriForFile = FileProvider.getUriForFile(getContext(), "com.miui.aod.fileprovider", file2);
        getContext().grantUriPermission(getCallingPackage(), uriForFile, 1);
        return uriForFile;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
