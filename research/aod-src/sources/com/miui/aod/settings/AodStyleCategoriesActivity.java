package com.miui.aod.settings;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.gson.Gson;
import com.miui.aod.AODApplication;
import com.miui.aod.AODApplicationDelegate;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.analy.AnalyticManager;
import com.miui.aod.common.BaseActivity;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.widget.ITimeUpdate;
import com.miui.aod.history.MyStyleAdapter;
import com.miui.aod.resource.DrawableCache;
import com.miui.aod.stylelist.MyStyleListType;
import com.miui.aod.stylelist.StyleCategoryAdapter;
import com.miui.aod.stylelist.StyleListAdapter;
import com.miui.aod.stylelist.StyleListViewModel;
import com.miui.aod.template.data.template.base.ColorPickerCache;
import com.miui.aod.theme.OnLineStyleAdapter;
import com.miui.aod.theme.beans.OnLineAodBean;
import com.miui.aod.theme.utils.ThemeUtils;
import com.miui.aod.util.ActionBarHelper;
import com.miui.aod.util.DeviceInfo;
import com.miui.aod.util.ViewUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.keyguard.editor.utils.WideColorGamutInterface;
import com.miui.keyguard.editor.utils.WideColorGamutManager;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import java.lang.ref.WeakReference;
import miuix.nestedheader.widget.NestedHeaderLayout;
import miuix.recyclerview.widget.RecyclerView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes.dex */
public class AodStyleCategoriesActivity extends BaseActivity implements AODApplicationReceiver.AODReceiverAction, AODApplicationDelegate.OnWallPaperChangeListener {
    public TextView mFindMoreBtn;
    public NestedHeaderLayout mHeaderLayout;
    public boolean mIsDualClock;
    public int mScreenWidthDp;
    public StyleListAdapter mStyleListAdapter;
    public RecyclerView mStyleListView;
    public StyleListViewModel mStyleListViewModel;
    public int screenLayoutSize = Resources.getSystem().getConfiguration().screenLayout;
    public boolean mIsResume = false;
    public boolean mIsCategoryChanged = false;
    public boolean mIsSuperWallpaperCategory = false;
    public final WideColorGamutInterface wideColorGamutListener = new WideColorGamutInterface() { // from class: com.miui.aod.settings.AodStyleCategoriesActivity$$ExternalSyntheticLambda0
        @Override // com.miui.keyguard.editor.utils.WideColorGamutInterface
        public final void onColorModeChanged() {
            this.f$0.finish();
        }
    };
    public ContentObserver mContentObserver = new InnerContentObserver(this);
    public ContentObserver mCategoryContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.miui.aod.settings.AodStyleCategoriesActivity.2
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            MyStyleAdapter myStyleAdapter;
            super.onChange(z);
            Log.d("AodStyleCategoriesActivity", "category onChanged");
            if (Utils.isUsingLinkageStyle(AodStyleCategoriesActivity.this.getApplicationContext())) {
                AodStyleCategoriesActivity.this.mIsCategoryChanged = true;
            }
            if (!Utils.isSuperWallpaperCategory(AodStyleCategoriesActivity.this.getApplicationContext()) && AodStyleCategoriesActivity.this.mIsSuperWallpaperCategory && AodStyleCategoriesActivity.this.mStyleListAdapter != null && AodStyleCategoriesActivity.this.mStyleListAdapter.getShowType() == 0 && (myStyleAdapter = AodStyleCategoriesActivity.this.mStyleListAdapter.getMyStyleAdapter()) != null) {
                myStyleAdapter.removeSuperWallpaperItem();
                Log.d("AodStyleCategoriesActivity", "Removed super wallpaper from adapter");
            }
            AodStyleCategoriesActivity aodStyleCategoriesActivity = AodStyleCategoriesActivity.this;
            aodStyleCategoriesActivity.mIsSuperWallpaperCategory = Utils.isSuperWallpaperCategory(aodStyleCategoriesActivity.getApplicationContext());
        }
    };

    public static class InnerContentObserver extends ContentObserver {
        public WeakReference mRef;

        public InnerContentObserver(Handler handler) {
            super(handler);
        }

        public InnerContentObserver(AodStyleCategoriesActivity aodStyleCategoriesActivity) {
            this(new Handler(Looper.getMainLooper()));
            this.mRef = new WeakReference(aodStyleCategoriesActivity);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            AodStyleCategoriesActivity aodStyleCategoriesActivity = (AodStyleCategoriesActivity) this.mRef.get();
            if (aodStyleCategoriesActivity == null || uri == null) {
                return;
            }
            Uri uriFor = Settings.Secure.getUriFor("full_screen_aod_support");
            Uri uriFor2 = Settings.Secure.getUriFor("doze_always_on");
            if (uri.equals(uriFor) || uri.equals(uriFor2)) {
                aodStyleCategoriesActivity.refreshSettingsView();
            }
        }
    }

    public static class SafeRunnable implements Runnable {
        public final WeakReference activityRef;

        public SafeRunnable(AodStyleCategoriesActivity aodStyleCategoriesActivity) {
            this.activityRef = new WeakReference(aodStyleCategoriesActivity);
        }

        @Override // java.lang.Runnable
        public void run() {
            AodStyleCategoriesActivity aodStyleCategoriesActivity = (AodStyleCategoriesActivity) this.activityRef.get();
            if (aodStyleCategoriesActivity == null || aodStyleCategoriesActivity.isFinishing()) {
                return;
            }
            aodStyleCategoriesActivity.handleClickEventToSelect();
        }
    }

    public static class SpacesItemDecoration extends androidx.recyclerview.widget.RecyclerView.ItemDecoration {
        public final int mTopSpace = Utils.dp2Px(0);
        public final int mSpace = Utils.dp2Px(12);

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.State state) {
            if (recyclerView.getChildLayoutPosition(view) == 0) {
                rect.top = this.mTopSpace;
            }
            rect.bottom = this.mSpace;
        }
    }

    public static /* synthetic */ void $r8$lambda$wXdzoakWuoeUBnXcVxIFTYwS3V4(AodStyleCategoriesActivity aodStyleCategoriesActivity, View view) {
        aodStyleCategoriesActivity.getClass();
        ThemeUtils.startThemeHome(aodStyleCategoriesActivity, false);
    }

    public static void updateActivityTitle(Activity activity) {
        String stringFromSettings = Utils.getStringFromSettings("aod_settings_category_name");
        if (stringFromSettings != null) {
            activity.setTitle(stringFromSettings);
        }
    }

    public final void handleBanner(Configuration configuration) {
        try {
            if (this.screenLayoutSize != configuration.screenLayout) {
                ImageView imageView = (ImageView) this.mStyleListView.findViewById(R.id.banner_image);
                View viewFindViewById = this.mStyleListView.findViewById(R.id.image_container);
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                Bitmap bitmap = Utils.inLargeScreen(this) ? DrawableCache.getInstance(this).getBitmap(R.drawable.fod_more_online_aod_banner, 1) : DrawableCache.getInstance(this).getBitmap(R.drawable.more_online_aod_banner, 1);
                imageView.setImageBitmap(bitmap);
                if (bitmap != null && bitmap.getWidth() != 0) {
                    int dimensionPixelSize = viewFindViewById.getResources().getDisplayMetrics().widthPixels - (getResources().getDimensionPixelSize(R.dimen.list_margin_start) * 2);
                    layoutParams.width = dimensionPixelSize;
                    layoutParams.height = (int) (((dimensionPixelSize * bitmap.getHeight()) * 1.0f) / bitmap.getWidth());
                }
                this.screenLayoutSize = configuration.screenLayout;
            }
        } catch (Exception e) {
            Log.e("AodStyleCategoriesActivity", e.toString());
        }
    }

    public final void handleClickEventToSelect() {
        if (this.mIsResume && this.mIsCategoryChanged) {
            this.mStyleListViewModel.scheduleLoadData();
            this.mIsCategoryChanged = false;
        }
    }

    @Override // com.miui.aod.common.BaseActivity
    public boolean needHandleUpdateTime() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClickEvent(StyleCategoryAdapter.ClickEvent clickEvent) {
        StyleInfo styleInfo = clickEvent.mData;
        if (styleInfo == null) {
            ThemeUtils.startThemeHome(this, true);
        } else {
            AodStyleSelectActivity.start(this, styleInfo, clickEvent.mType, clickEvent.mView, new SafeRunnable(this));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClickEvent(OnLineStyleAdapter.ClickEvent clickEvent) {
        Bundle bundle = new Bundle();
        if (clickEvent.mData != null) {
            bundle.putString("aod_detail", new Gson().toJson(clickEvent.mData));
        } else {
            OnLineAodBean onLineAodBean = clickEvent.mAodBean;
            if (onLineAodBean == null) {
                Log.e("AodStyleCategoriesActivity", "onClickEvent startThemeDetail fail illegal bundle parameters");
                return;
            } else {
                bundle.putString("subject_uuid", onLineAodBean.getSubjectUuid());
                bundle.putString("track_id", clickEvent.mAodBean.getTrackId());
                bundle.putString("title", clickEvent.mAodBean.getTitle());
            }
        }
        ThemeUtils.startThemeDetail(getApplicationContext(), bundle, clickEvent.mData != null);
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (Utils.isPadDevice()) {
            return;
        }
        int i = this.mScreenWidthDp;
        int i2 = configuration.screenWidthDp;
        if (i == i2) {
            refreshLinkageItem();
            handleBanner(configuration);
        } else {
            this.mScreenWidthDp = i2;
            reLoad();
            handleBanner(configuration);
        }
    }

    @Override // com.miui.aod.common.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Utils.isPadDevice()) {
            startActivity(new Intent(this, (Class<?>) AodStyleCategoriesActivityForPad.class));
            finish();
            return;
        }
        if (Utils.isFoldDevice() && ViewUtils.isActivityAlreadyEmbedded(this)) {
            startActivity(new Intent(this, (Class<?>) AodStyleCategoriesActivity.class));
            finish();
            return;
        }
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.layoutInDisplayCutoutMode = 1;
        window.setAttributes(attributes);
        this.mIsDualClock = AODSettings.isDualClock(getApplicationContext());
        setContentView(R.layout.aod_style_categories_dual);
        NestedHeaderLayout nestedHeaderLayout = (NestedHeaderLayout) findViewById(R.id.nested_header_layout);
        this.mHeaderLayout = nestedHeaderLayout;
        nestedHeaderLayout.setEnableBlur(false);
        updateActivityTitle(this);
        this.mStyleListView = (RecyclerView) findViewById(R.id.categories_container);
        ActionBarHelper.setupOverlayActionBar(this, this.mHeaderLayout);
        updateStyleListViewParam();
        this.mStyleListView.setHasFixedSize(true);
        if (DeviceInfo.isLowMemDevice()) {
            this.mStyleListView.getRecycledViewPool().setMaxRecycledViews(com.miui.maml.R.styleable.AppCompatTheme_windowFixedWidthMinor, 0);
            this.mStyleListView.setItemViewCacheSize(0);
        }
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(1);
        this.mStyleListView.setLayoutManager(linearLayoutManager);
        this.mStyleListView.addOnScrollListener(new androidx.recyclerview.widget.RecyclerView.OnScrollListener() { // from class: com.miui.aod.settings.AodStyleCategoriesActivity.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(androidx.recyclerview.widget.RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (DeviceInfo.isLowMemDevice() || i == 0) {
                    AodStyleCategoriesActivity.this.refreshSettingsView();
                }
                if (i == 0) {
                    int iFindLastCompletelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    for (int iFindFirstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition(); iFindFirstCompletelyVisibleItemPosition <= iFindLastCompletelyVisibleItemPosition; iFindFirstCompletelyVisibleItemPosition++) {
                        androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = recyclerView.findViewHolderForAdapterPosition(iFindFirstCompletelyVisibleItemPosition);
                        if (viewHolderFindViewHolderForAdapterPosition != null) {
                            KeyEvent.Callback callback = viewHolderFindViewHolderForAdapterPosition.itemView;
                            if (callback instanceof ITimeUpdate) {
                                ((ITimeUpdate) callback).handleUpdateTime(DateFormat.is24HourFormat(AodStyleCategoriesActivity.this.getApplicationContext()));
                            }
                        }
                    }
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (AodStyleCategoriesActivity.this.mFindMoreBtn != null) {
                    if (linearLayoutManager.findFirstVisibleItemPosition() <= 0 || Utils.isFsAodSameToLock(AODApplication.aodSettingDensityContext)) {
                        AodStyleCategoriesActivity.this.mFindMoreBtn.setVisibility(8);
                    } else {
                        AodStyleCategoriesActivity.this.mFindMoreBtn.setVisibility(0);
                    }
                }
            }
        });
        this.mStyleListView.addItemDecoration(new SpacesItemDecoration());
        reLoad();
        if (ThemeUtils.supportAodTheme(this) && !Utils.isFoldDevice()) {
            TextView textView = (TextView) findViewById(R.id.aod_find_more);
            this.mFindMoreBtn = textView;
            if (textView != null) {
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.AodStyleCategoriesActivity$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AodStyleCategoriesActivity.$r8$lambda$wXdzoakWuoeUBnXcVxIFTYwS3V4(this.f$0, view);
                    }
                });
                this.mFindMoreBtn.setBackgroundResource(R.color.miuix_color_white_solid_40);
                this.mFindMoreBtn.setOutlineProvider(new RoundOutlineProvider(Utils.dp2Px(16)));
                this.mStyleListView.setPadding(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.aod_content_last_item_to_bottom_padding));
            }
        } else if (!this.mIsDualClock || Utils.isFoldDevice()) {
            Log.i("AodStyleCategoriesActivity", "Other mode,don't need set StyleListView.setPadding");
        } else {
            this.mStyleListView.setPadding(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.aod_content_last_item_to_bottom_padding));
        }
        AnalyticManager.getInstance(this).trackAodStylePage();
        getContentResolver().registerContentObserver(Settings.Secure.getUriFor("doze_always_on"), false, this.mContentObserver);
        getContentResolver().registerContentObserver(Settings.Secure.getUriFor("full_screen_aod_support"), false, this.mContentObserver);
        AODApplicationReceiver.addAction(this);
        this.mScreenWidthDp = getResources().getConfiguration().screenWidthDp;
        getContentResolver().registerContentObserver(Settings.Secure.getUriFor("aod_category_name"), false, this.mCategoryContentObserver);
        AODApplicationDelegate.addOnWallPaperChangeListener(this);
        this.mIsSuperWallpaperCategory = Utils.isSuperWallpaperCategory(getApplicationContext());
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Utils.cancelSuccessToast();
        getContentResolver().unregisterContentObserver(this.mContentObserver);
        this.mContentObserver = null;
        AODApplicationReceiver.removeAction(this);
        ColorPickerCache.INSTANCE.cacheMyColorData(null);
        getContentResolver().unregisterContentObserver(this.mCategoryContentObserver);
        this.mCategoryContentObserver = null;
        AODApplicationDelegate.removeOnWallPaperChangeListener(this);
        WideColorGamutManager wideColorGamutManager = WideColorGamutManager.INSTANCE;
        wideColorGamutManager.unregisterObservers(this);
        wideColorGamutManager.removeColorModeChangeListener(this.wideColorGamutListener);
    }

    @Override // com.miui.aod.common.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        Utils.cancelSuccessToast();
        this.mIsResume = false;
    }

    @Override // com.miui.aod.settings.AODApplicationReceiver.AODReceiverAction
    public void onReceiveAction(String str, Intent intent) {
        Log.d("AodStyleCategoriesActivity", "onAction: " + str);
        if ("miui.keyguard.editor.action.LINKAGE_STYLE_APPLY_STATE_CHANGED".equals(str)) {
            refreshLinkageItem();
        }
    }

    @Override // com.miui.aod.common.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        WideColorGamutManager wideColorGamutManager = WideColorGamutManager.INSTANCE;
        wideColorGamutManager.updateSupportWideColor(this);
        wideColorGamutManager.addColorModeChangeListener(this.wideColorGamutListener);
        getWindow().setColorMode(wideColorGamutManager.getDeviceMaxColorMode(this));
        this.mIsResume = true;
        boolean zIsDualClock = AODSettings.isDualClock(getApplicationContext());
        if (this.mIsDualClock != zIsDualClock) {
            this.mIsDualClock = zIsDualClock;
            updateStyleListViewParam();
        }
        setFindMoreBtn();
        refreshSettingsView();
        refreshLinkageItem();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStyleListTypeEvent(StyleListViewModel.StyleListType styleListType) {
        if (styleListType instanceof MyStyleListType) {
            startActivity(new Intent(this, (Class<?>) HistoryListActivity.class));
        } else if (styleListType instanceof StyleListViewModel.ButtonType) {
            ThemeUtils.startThemeHome(this, false);
        }
    }

    @Override // com.miui.aod.common.BaseActivity
    public void onTimeTick() {
        int childCount = this.mStyleListView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            KeyEvent.Callback childAt = this.mStyleListView.getChildAt(i);
            if (childAt instanceof ITimeUpdate) {
                ((ITimeUpdate) childAt).handleUpdateTime(DateFormat.is24HourFormat(getApplicationContext()));
            }
        }
    }

    @Override // com.miui.aod.AODApplicationDelegate.OnWallPaperChangeListener
    public void onWallPaperChange(String str, int i) {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        updateTemplateView();
    }

    public final void reLoad() {
        this.mStyleListViewModel = StyleListViewModel.newInstance(getApplicationContext());
        StyleListAdapter styleListAdapterNewInstance = StyleListAdapter.newInstance(this);
        this.mStyleListAdapter = styleListAdapterNewInstance;
        styleListAdapterNewInstance.observeLifecycle(this);
        this.mStyleListViewModel.observe(this, this.mStyleListAdapter);
        this.mStyleListView.setAdapter(this.mStyleListAdapter);
    }

    public final void refreshLinkageItem() {
        RecyclerView recyclerView = this.mStyleListView;
        if (recyclerView == null || recyclerView.getAdapter() == null || ((StyleListAdapter) this.mStyleListView.getAdapter()).getShowType() != 0) {
            return;
        }
        this.mStyleListView.getAdapter().notifyItemChanged(1, 1);
    }

    public final void refreshSettingsView() {
        View childAt = this.mStyleListView.getChildAt(0);
        if (childAt instanceof SettingsView) {
            ((SettingsView) childAt).updateState();
        }
    }

    public final void setFindMoreBtn() {
        TextView textView = this.mFindMoreBtn;
        if (textView != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
            int navigationBarHeight = Utils.getNavigationBarHeight(this);
            if (Utils.isFullScreenGestureNavigationMode(getApplicationContext())) {
                layoutParams.bottomMargin = navigationBarHeight + getResources().getDimensionPixelOffset(R.dimen.aod_online_find_more_bottom_margin_full);
            } else {
                layoutParams.bottomMargin = navigationBarHeight + getResources().getDimensionPixelOffset(R.dimen.aod_online_find_more_bottom_margin_nav);
            }
            this.mFindMoreBtn.setLayoutParams(layoutParams);
        }
    }

    public final void updateStyleListViewParam() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) findViewById(R.id.spring_back_layout).getLayoutParams();
        if (this.mIsDualClock) {
            marginLayoutParams.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.aod_content_dual_layout_bottom_margin);
            this.mHeaderLayout.setHeaderViewVisible(true);
        } else {
            marginLayoutParams.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.aod_content_layout_bottom_margin);
            this.mHeaderLayout.setHeaderViewVisible(false);
        }
    }

    public final void updateTemplateView() {
        View childAt = this.mStyleListView.getChildAt(1);
        if (childAt != null) {
            androidx.recyclerview.widget.RecyclerView.ViewHolder childViewHolder = this.mStyleListView.getChildViewHolder(childAt);
            if (childViewHolder instanceof StyleListAdapter.PreviewViewHolder) {
                ((StyleListAdapter.PreviewViewHolder) childViewHolder).showClockView();
            }
        }
    }

    @Override // com.miui.aod.common.BaseActivity
    public boolean useEventBus() {
        return true;
    }
}
