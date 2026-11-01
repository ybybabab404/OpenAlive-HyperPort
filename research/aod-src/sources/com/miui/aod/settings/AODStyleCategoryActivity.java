package com.miui.aod.settings;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import com.google.gson.Gson;
import com.miui.aod.AODStyleController;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.BaseStyleSelectPresenter;
import com.miui.aod.resource.DrawableCache;
import java.util.TimeZone;
import miuix.appcompat.app.AppCompatActivity;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class AODStyleCategoryActivity extends AppCompatActivity {
    public boolean m24HourFormat;
    public AODStyleController mAodStyleController;
    public Handler mHandler;
    public ImageAdapter mImageAdapter;
    public LayoutInflater mInflater;
    public String mSelectCateName;
    public final Runnable mUpdateAodSelectionTask = new Runnable() { // from class: com.miui.aod.settings.AODStyleCategoryActivity$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.updateAODStyleSelected();
        }
    };
    public Runnable mUpdateRunnable;

    public class ImageAdapter extends BaseAdapter {
        public ImageAdapter() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return CategoryFactory.getAodCategoryCount();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = AODStyleCategoryActivity.this.mInflater.inflate(R.layout.aod_style_category_item, viewGroup, false);
            }
            AODStyleController aODStyleController = new AODStyleController(2);
            String aodCategoryName = CategoryFactory.getAodCategoryName(i);
            StyleInfo styleInfo = AODStyleController.getStyleInfo(view.getContext(), aodCategoryName);
            aODStyleController.inflateView(view, styleInfo);
            aODStyleController.handleUpdateTime(AODStyleCategoryActivity.this.m24HourFormat);
            view.setForeground(DrawableCache.getInstance(AODStyleCategoryActivity.this.getApplicationContext()).getDrawable((TextUtils.isEmpty(AODStyleCategoryActivity.this.mSelectCateName) || !AODStyleCategoryActivity.this.mSelectCateName.equals(aodCategoryName)) ? R.drawable.aod_unselected_rect : R.drawable.aod_selected_rect));
            view.setTag(styleInfo);
            return view;
        }
    }

    public static /* synthetic */ void $r8$lambda$FtuPBQ8qsN5hSVyBOkipDDVdVzU(AODStyleCategoryActivity aODStyleCategoryActivity) {
        aODStyleCategoryActivity.mAodStyleController.handleUpdateTime(aODStyleCategoryActivity.m24HourFormat);
        aODStyleCategoryActivity.mImageAdapter.notifyDataSetChanged();
        aODStyleCategoryActivity.mHandler.postDelayed(aODStyleCategoryActivity.mUpdateRunnable, 60000L);
    }

    /* JADX INFO: renamed from: $r8$lambda$sgl3-6JDSJqbvY9K6aKD7csIzu0, reason: not valid java name */
    public static /* synthetic */ void m437$r8$lambda$sgl36JDSJqbvY9K6aKD7csIzu0(AODStyleCategoryActivity aODStyleCategoryActivity, AdapterView adapterView, View view, int i, long j) {
        aODStyleCategoryActivity.getClass();
        aODStyleCategoryActivity.startAODStyleCategoryActivity((StyleInfo) view.getTag());
    }

    private void update() {
        boolean zIs24HourFormat = DateFormat.is24HourFormat(this);
        this.m24HourFormat = zIs24HourFormat;
        this.mAodStyleController.handleUpdateTime(zIs24HourFormat);
        this.mImageAdapter.notifyDataSetChanged();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 4626 && i2 == -1) {
            this.mSelectCateName = "";
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AodStyleCategoriesActivity.updateActivityTitle(this);
        this.mInflater = getLayoutInflater();
        this.mAodStyleController = new AODStyleController(1);
        Calendar calendar = new Calendar(TimeZone.getDefault());
        int i = calendar.get(21);
        int i2 = calendar.get(22);
        Window window = getWindow();
        window.clearFlags(201326592);
        window.getDecorView().setSystemUiVisibility(1280);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(0);
        window.setNavigationBarColor(-16777216);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.layoutInDisplayCutoutMode = 1;
        window.setAttributes(attributes);
        setContentView(R.layout.aod_style_category);
        ((ImageView) findViewById(R.id.aod_back)).setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.settings.AODStyleCategoryActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.finish();
            }
        });
        this.mSelectCateName = Utils.getAodUsingCategoryName(getBaseContext());
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setNumColumns(2);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.aod_cate_item_size);
        int verticalSpacing = gridView.getVerticalSpacing();
        ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
        layoutParams.width = (dimensionPixelSize * 2) + verticalSpacing;
        gridView.setLayoutParams(layoutParams);
        gridView.setVerticalFadingEdgeEnabled(false);
        gridView.setHorizontalFadingEdgeEnabled(false);
        gridView.setSelector(new ColorDrawable(0));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.miui.aod.settings.AODStyleCategoryActivity$$ExternalSyntheticLambda2
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i3, long j) {
                AODStyleCategoryActivity.m437$r8$lambda$sgl36JDSJqbvY9K6aKD7csIzu0(this.f$0, adapterView, view, i3, j);
            }
        });
        ImageAdapter imageAdapter = new ImageAdapter();
        this.mImageAdapter = imageAdapter;
        gridView.setAdapter((ListAdapter) imageAdapter);
        update();
        this.mHandler = new Handler();
        Runnable runnable = new Runnable() { // from class: com.miui.aod.settings.AODStyleCategoryActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                AODStyleCategoryActivity.$r8$lambda$FtuPBQ8qsN5hSVyBOkipDDVdVzU(this.f$0);
            }
        };
        this.mUpdateRunnable = runnable;
        this.mHandler.postDelayed(runnable, ((60 - i) * 1000) + (1000 - i2));
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacks(this.mUpdateRunnable);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.mHandler.removeCallbacks(this.mUpdateAodSelectionTask);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        update();
        if (TextUtils.isEmpty(this.mSelectCateName)) {
            this.mHandler.postDelayed(this.mUpdateAodSelectionTask, 300L);
        }
    }

    public final void startAODStyleCategoryActivity(StyleInfo styleInfo) {
        Gson gson = new Gson();
        Intent intent = new Intent(this, (Class<?>) AodStyleSelectActivity.class);
        intent.putExtra(BaseStyleSelectPresenter.KEY_CATE, styleInfo.getName());
        intent.putExtra(BaseStyleSelectPresenter.KEY_PARAMETER, gson.toJson(styleInfo));
        startActivityForResult(intent, 4626);
    }

    public final void updateAODStyleSelected() {
        String aodUsingCategoryName = Utils.getAodUsingCategoryName(getBaseContext());
        if (TextUtils.isEmpty(this.mSelectCateName) || !this.mSelectCateName.equals(aodUsingCategoryName)) {
            this.mImageAdapter.notifyDataSetChanged();
            this.mSelectCateName = aodUsingCategoryName;
        }
    }
}
