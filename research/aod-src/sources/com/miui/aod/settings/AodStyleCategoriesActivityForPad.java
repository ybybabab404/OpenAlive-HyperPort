package com.miui.aod.settings;

import android.app.Activity;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.common.BaseActivity;
import com.miui.aod.stylelist.PadStyleListAdapter;
import com.miui.aod.stylelist.PadStyleListViewModel;
import com.miui.aod.util.ActionBarHelper;
import com.miui.aod.util.DeviceInfo;
import com.miui.keyguard.editor.utils.WideColorGamutInterface;
import com.miui.keyguard.editor.utils.WideColorGamutManager;
import java.lang.ref.WeakReference;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class AodStyleCategoriesActivityForPad extends BaseActivity {
    public ConstraintLayout mRootView;
    public RecyclerView mStyleListView;
    public PadStyleListViewModel mStyleListViewModel;
    public PadStyleListAdapter styleListAdapter;
    public int orientation = -1;
    public ContentObserver mContentObserver = new InnerContentObserver(this);
    public final WideColorGamutInterface wideColorGamutListener = new WideColorGamutInterface() { // from class: com.miui.aod.settings.AodStyleCategoriesActivityForPad$$ExternalSyntheticLambda0
        @Override // com.miui.keyguard.editor.utils.WideColorGamutInterface
        public final void onColorModeChanged() {
            this.f$0.finish();
        }
    };

    public static class InnerContentObserver extends ContentObserver {
        public WeakReference mRef;

        public InnerContentObserver(Handler handler) {
            super(handler);
        }

        public InnerContentObserver(AodStyleCategoriesActivityForPad aodStyleCategoriesActivityForPad) {
            this(new Handler(Looper.getMainLooper()));
            this.mRef = new WeakReference(aodStyleCategoriesActivityForPad);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            AodStyleCategoriesActivityForPad aodStyleCategoriesActivityForPad = (AodStyleCategoriesActivityForPad) this.mRef.get();
            if (aodStyleCategoriesActivityForPad == null || aodStyleCategoriesActivityForPad.mStyleListView == null || aodStyleCategoriesActivityForPad.styleListAdapter == null) {
                return;
            }
            aodStyleCategoriesActivityForPad.styleListAdapter.setData(aodStyleCategoriesActivityForPad.mStyleListViewModel.loadStyleListData());
            aodStyleCategoriesActivityForPad.mStyleListView.setAdapter(aodStyleCategoriesActivityForPad.styleListAdapter);
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

    private void reload() {
        setPadding();
        this.mStyleListViewModel = PadStyleListViewModel.newInstance(getApplicationContext());
        PadStyleListAdapter padStyleListAdapterNewInstance = PadStyleListAdapter.newInstance(this);
        this.styleListAdapter = padStyleListAdapterNewInstance;
        this.mStyleListView.setAdapter(padStyleListAdapterNewInstance);
        this.styleListAdapter.setData(this.mStyleListViewModel.loadStyleListData());
    }

    @Override // com.miui.aod.common.BaseActivity
    public boolean needHandleUpdateTime() {
        return true;
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        reload();
    }

    @Override // com.miui.aod.common.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.layoutInDisplayCutoutMode = 3;
        window.setAttributes(attributes);
        if (getAppCompatActionBar() != null) {
            getAppCompatActionBar().setExpandState(0);
            getAppCompatActionBar().setResizable(false);
        }
        setContentView(R.layout.aod_style_categories_for_pad);
        updateActivityTitle(this);
        this.mRootView = (ConstraintLayout) findViewById(R.id.root_view);
        setPadding();
        this.mStyleListViewModel = PadStyleListViewModel.newInstance(getApplicationContext());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.categories_container);
        this.mStyleListView = recyclerView;
        recyclerView.setHasFixedSize(true);
        if (DeviceInfo.isLowMemDevice()) {
            this.mStyleListView.getRecycledViewPool().setMaxRecycledViews(com.miui.maml.R.styleable.AppCompatTheme_windowFixedWidthMinor, 0);
            this.mStyleListView.setItemViewCacheSize(0);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(1);
        this.mStyleListView.setLayoutManager(linearLayoutManager);
        this.mStyleListView.addItemDecoration(new SpacesItemDecoration());
        PadStyleListAdapter padStyleListAdapterNewInstance = PadStyleListAdapter.newInstance(this);
        this.styleListAdapter = padStyleListAdapterNewInstance;
        this.mStyleListView.setAdapter(padStyleListAdapterNewInstance);
        this.styleListAdapter.setData(this.mStyleListViewModel.loadStyleListData());
        ActionBarHelper.setupOverlayActionBarForPad(this, this.mStyleListView);
        getContentResolver().registerContentObserver(Settings.Secure.getUriFor("doze_always_on"), false, this.mContentObserver);
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(this.mContentObserver);
        this.mContentObserver = null;
        WideColorGamutManager wideColorGamutManager = WideColorGamutManager.INSTANCE;
        wideColorGamutManager.unregisterObservers(this);
        wideColorGamutManager.removeColorModeChangeListener(this.wideColorGamutListener);
    }

    @Override // com.miui.aod.common.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        WideColorGamutManager wideColorGamutManager = WideColorGamutManager.INSTANCE;
        wideColorGamutManager.updateSupportWideColor(this);
        wideColorGamutManager.addColorModeChangeListener(this.wideColorGamutListener);
        getWindow().setColorMode(wideColorGamutManager.getDeviceMaxColorMode(this));
    }

    public final void setPadding() {
        int i;
        this.orientation = getResources().getConfiguration().orientation;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.aod_setting_padding_horizontal_pad_vertical);
        if (2 == this.orientation) {
            i = dimensionPixelSize;
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.aod_setting_padding_horizontal_pad);
        } else {
            i = 0;
        }
        this.mRootView.setPadding(dimensionPixelSize, 0, dimensionPixelSize, i);
    }

    public void updateActivityTitle(Activity activity) {
        String stringFromSettings = Utils.getStringFromSettings("aod_settings_category_name");
        if (stringFromSettings != null) {
            activity.setTitle(stringFromSettings);
        }
    }
}
