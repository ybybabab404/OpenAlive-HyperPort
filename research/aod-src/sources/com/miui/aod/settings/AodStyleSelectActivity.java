package com.miui.aod.settings;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.gson.Gson;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.analy.AnalyticManager;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.common.BaseActivity;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.ApplyInstanceStyleSelectPresenter;
import com.miui.aod.components.BaseStyleSelectPresenter;
import com.miui.aod.components.BaseStyleSelectPresenterFactory;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.aod.superwallpaper.WallpaperConnectionUtils;
import com.miui.aod.theme.MamlViewContainer;
import com.miui.aod.util.BitmapUtils;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import miuix.appcompat.app.AppCompatActivity;
import miuix.transition.ActivityOptionsHelper;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes.dex */
public class AodStyleSelectActivity extends BaseActivity {
    public static final Handler handler = new Handler(Looper.getMainLooper());
    public static ConcurrentHashMap mZoneMap;
    public WallpaperConnectionUtils mWallpaperConnectionUtils;
    public final List mPhotoAlbumPickerListeners = new ArrayList();
    public boolean mNeedShowWallpaper = false;
    public boolean mNeedFinishOnPause = false;
    public BaseStyleSelectPresenter mStyleSelectPresenter = null;
    public final Configuration mConfiguration = new Configuration();

    public interface PhotoAlbumPickerListener {
        default void onImageSelectCancel() {
        }

        void onImageSelected(String str);
    }

    public static /* synthetic */ WindowInsetsCompat $r8$lambda$Nfi93NMZen1tIUWizHNWTBeEITQ(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return windowInsetsCompat;
    }

    public static /* synthetic */ void $r8$lambda$WK5Hdyw99La_FjYB7wuNHcOXk8o(AodStyleSelectActivity aodStyleSelectActivity) {
        WallpaperConnectionUtils wallpaperConnectionUtils = aodStyleSelectActivity.mWallpaperConnectionUtils;
        if (wallpaperConnectionUtils != null) {
            wallpaperConnectionUtils.wallpaperConnect();
        }
    }

    public static void clearZoneMap() {
        ConcurrentHashMap concurrentHashMap = mZoneMap;
        if (concurrentHashMap != null) {
            concurrentHashMap.clear();
            mZoneMap = null;
        }
    }

    public static ConcurrentHashMap getZoneMap() {
        return mZoneMap;
    }

    public static void initZoneMap() {
        mZoneMap = new ConcurrentHashMap();
    }

    public static void start(AppCompatActivity appCompatActivity, StyleInfo styleInfo, String str) {
        start(appCompatActivity, styleInfo, str, null);
    }

    public static void start(AppCompatActivity appCompatActivity, StyleInfo styleInfo, String str, WeakReference weakReference) {
        start(appCompatActivity, styleInfo, str, weakReference, null);
    }

    public static void start(AppCompatActivity appCompatActivity, StyleInfo styleInfo, String str, WeakReference weakReference, Runnable runnable) {
        Gson gson = new Gson();
        Intent intent = new Intent(appCompatActivity, (Class<?>) AodStyleSelectActivity.class);
        if (styleInfo.getRealStyleInfo() != null) {
            intent.putExtra(BaseStyleSelectPresenter.KEY_CATE, styleInfo.getRealStyleInfo().getName());
            intent.putExtra(BaseStyleSelectPresenter.KEY_PARAMETER, gson.toJson(styleInfo.getRealStyleInfo()));
        } else {
            intent.putExtra(BaseStyleSelectPresenter.KEY_CATE, styleInfo.getName());
            intent.putExtra(BaseStyleSelectPresenter.KEY_PARAMETER, gson.toJson(styleInfo));
        }
        if (TextUtils.isEmpty(str)) {
            intent.putExtra("key_page_from", StyleInfo.DEFAULT_PARAMETER);
        } else {
            intent.putExtra("key_page_from", str);
        }
        intent.putExtra(BaseStyleSelectPresenter.KEY_SOURCE_TYPE, 1);
        if (weakReference == null || Utils.isFoldDevice() || !ActivityOptionsHelper.isSupportScaleUpDown()) {
            appCompatActivity.startActivityForResult(intent, 4626);
        } else {
            int[] iArr = new int[2];
            ((View) weakReference.get()).getLocationOnScreen(iArr);
            int dimension = (int) appCompatActivity.getResources().getDimension(R.dimen.aod_style_bg_radius);
            int dimension2 = (int) appCompatActivity.getResources().getDimension(R.dimen.rect_drawable_select_padding);
            Bitmap bitmapFromView = BitmapUtils.getBitmapFromView((View) weakReference.get(), dimension);
            if (bitmapFromView == null) {
                bitmapFromView = ActivityOptionsHelper.captureSnapshot((View) weakReference.get());
            }
            ActivityOptions activityOptionsMakeScaleUpAnim = ActivityOptionsHelper.makeScaleUpAnim((View) weakReference.get(), bitmapFromView, iArr[0] - dimension2, iArr[1] - dimension2, dimension, -16777216, 1.0f, handler, null, runnable, null, null, 103);
            if (activityOptionsMakeScaleUpAnim != null) {
                appCompatActivity.startActivityForResult(intent, 4626, activityOptionsMakeScaleUpAnim.toBundle());
            } else {
                appCompatActivity.startActivityForResult(intent, 4626);
            }
        }
        AnalyticManager.getInstance(appCompatActivity.getApplicationContext()).trackAodStyleClickAction(styleInfo);
    }

    public void addPhotoAlbumPicker(PhotoAlbumPickerListener photoAlbumPickerListener) {
        this.mPhotoAlbumPickerListeners.add(photoAlbumPickerListener);
    }

    public final void enterOrExitSuperPreview(boolean z) {
        final View viewFindViewById = findViewById(R.id.black_bg_view);
        if (z) {
            new Handler().postDelayed(new Runnable() { // from class: com.miui.aod.settings.AodStyleSelectActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    viewFindViewById.setAlpha(0.0f);
                }
            }, 800L);
        } else {
            viewFindViewById.setAlpha(1.0f);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1) {
            for (PhotoAlbumPickerListener photoAlbumPickerListener : this.mPhotoAlbumPickerListeners) {
                if (i2 == -1) {
                    photoAlbumPickerListener.onImageSelected(Utils.getRealPathFromUri(getApplicationContext(), intent.getData()));
                } else {
                    photoAlbumPickerListener.onImageSelectCancel();
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onApplyStyleEvent(BaseStyleSelectPresenter.ApplyStyleEvent applyStyleEvent) {
        setResult(-1);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onApplyStyleEvent(BaseStyleSelectView.ExitSelectViewEvent exitSelectViewEvent) {
        finish();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().getDecorView().post(new Runnable() { // from class: com.miui.aod.settings.AodStyleSelectActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AodStyleSelectActivity.$r8$lambda$WK5Hdyw99La_FjYB7wuNHcOXk8o(this.f$0);
            }
        });
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        BaseStyleSelectView view;
        super.onConfigurationChanged(configuration);
        BaseStyleSelectPresenter baseStyleSelectPresenter = this.mStyleSelectPresenter;
        if (baseStyleSelectPresenter == null || (view = baseStyleSelectPresenter.getView()) == null) {
            return;
        }
        boolean z = (this.mConfiguration.updateFrom(configuration) & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0;
        if (Utils.isFoldDevice() && z) {
            view.updateStyleInfoForPreview();
        }
        view.notifyConfigurationChanged();
    }

    @Override // com.miui.aod.common.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        AodStyleCategoriesActivity.updateActivityTitle(this);
        BaseStyleSelectPresenter baseStyleSelectPresenterCreateStyleSelectPresenter = BaseStyleSelectPresenterFactory.createStyleSelectPresenter(this, getIntent());
        this.mStyleSelectPresenter = baseStyleSelectPresenterCreateStyleSelectPresenter;
        if (baseStyleSelectPresenterCreateStyleSelectPresenter != null) {
            boolean z = CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(baseStyleSelectPresenterCreateStyleSelectPresenter.getStyleCategory()) || CategoryConstants.CATEGORY_BLANK_SUPER_WALLPAPER.equals(this.mStyleSelectPresenter.getStyleCategory());
            this.mNeedShowWallpaper = z;
            if (z) {
                setTheme(R.style.Theme_SuperWallpaperPreview);
            }
            if (this.mStyleSelectPresenter instanceof ApplyInstanceStyleSelectPresenter) {
                this.mNeedFinishOnPause = true;
            }
        }
        super.onCreate(bundle);
        if (this.mNeedShowWallpaper && Utils.isWallPaperSuperWallpaper()) {
            this.mWallpaperConnectionUtils = new WallpaperConnectionUtils(this);
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.layoutInDisplayCutoutMode = 1;
        getWindow().setAttributes(attributes);
        setContentView(R.layout.activity_aod_style_select);
        ImageView imageView = (ImageView) findViewById(R.id.aod_back);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.AodStyleSelectActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.finish();
            }
        });
        imageView.setContentDescription(getResources().getString(R.string.actionbar_button_up_description));
        if (this.mStyleSelectPresenter != null) {
            getLifecycle().addObserver(this.mStyleSelectPresenter);
            BaseStyleSelectView view = this.mStyleSelectPresenter.getView();
            if (view != null) {
                view.setPresenter(this.mStyleSelectPresenter);
                view.inflate((ViewGroup) findViewById(R.id.style_select_root));
            }
            this.mStyleSelectPresenter.initViewInfo(bundle);
        }
        if (this.mNeedShowWallpaper && this.mWallpaperConnectionUtils != null) {
            ((TextView) findViewById(R.id.style_select_title)).setText(this.mWallpaperConnectionUtils.getWallpaperTitle());
        }
        AnalyticManager.getInstance(this).trackAodSelectStylePage(getIntent().getStringExtra("key_page_from"));
        clearZoneMap();
        this.mConfiguration.updateFrom(getApplicationContext().getResources().getConfiguration());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.style_select_panel_container), new OnApplyWindowInsetsListener() { // from class: com.miui.aod.settings.AodStyleSelectActivity$$ExternalSyntheticLambda3
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                return AodStyleSelectActivity.$r8$lambda$Nfi93NMZen1tIUWizHNWTBeEITQ(view2, windowInsetsCompat);
            }
        });
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        MamlViewContainer mamlViewContainer = (MamlViewContainer) findViewById(R.id.maml_view_container);
        if (this.mStyleSelectPresenter != null) {
            getLifecycle().removeObserver(this.mStyleSelectPresenter);
        }
        if (mamlViewContainer != null) {
            mamlViewContainer.release();
        }
        WallpaperConnectionUtils wallpaperConnectionUtils = this.mWallpaperConnectionUtils;
        if (wallpaperConnectionUtils != null) {
            wallpaperConnectionUtils.wallpaperDisconnect();
            this.mWallpaperConnectionUtils = null;
        }
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        WallpaperConnectionUtils wallpaperConnectionUtils = this.mWallpaperConnectionUtils;
        if (wallpaperConnectionUtils != null) {
            wallpaperConnectionUtils.wallpaperDisconnect();
        }
    }

    @Override // com.miui.aod.common.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        if (this.mNeedShowWallpaper) {
            enterOrExitSuperPreview(false);
        }
        super.onPause();
        WallpaperConnectionUtils wallpaperConnectionUtils = this.mWallpaperConnectionUtils;
        if (wallpaperConnectionUtils != null) {
            wallpaperConnectionUtils.setEngineVisibility(false);
        }
        if (this.mNeedFinishOnPause) {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPreviewStateEvent(BaseStyleSelectView.PreviewStateEvent previewStateEvent) {
        setSystemUIVisible(!previewStateEvent.mIsPreview);
    }

    @Override // com.miui.aod.common.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.mNeedShowWallpaper) {
            enterOrExitSuperPreview(true);
        }
        WallpaperConnectionUtils wallpaperConnectionUtils = this.mWallpaperConnectionUtils;
        if (wallpaperConnectionUtils != null) {
            wallpaperConnectionUtils.setEngineVisibility(true);
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        BaseStyleSelectPresenter baseStyleSelectPresenter = this.mStyleSelectPresenter;
        if (baseStyleSelectPresenter != null) {
            baseStyleSelectPresenter.onSaveInstanceState(bundle);
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        clearZoneMap();
    }

    public final void setSystemUIVisible(boolean z) {
        Log.d("AodStyleSelect", "setSystemUIVisible: show=" + z);
        if (z) {
            this.mWindowInsetsController.show(WindowInsetsCompat.Type.statusBars());
            this.mWindowInsetsController.show(WindowInsetsCompat.Type.navigationBars());
        } else {
            this.mWindowInsetsController.hide(WindowInsetsCompat.Type.statusBars());
            this.mWindowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());
        }
    }

    @Override // com.miui.aod.common.BaseActivity
    public boolean useEventBus() {
        return true;
    }
}
