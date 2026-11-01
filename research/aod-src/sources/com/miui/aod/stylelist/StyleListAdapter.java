package com.miui.aod.stylelist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.collection.ArrayMap;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.components.widget.RoundCornerImageView;
import com.miui.aod.history.MyStyleAdapter;
import com.miui.aod.resource.DrawableCache;
import com.miui.aod.settings.SettingsView;
import com.miui.aod.template.CurrentTemplateApi;
import com.miui.aod.template.FullAodTemplateView;
import com.miui.aod.template.FullAodTemplateViewKt;
import com.miui.aod.template.data.template.base.ColorPickerCache;
import com.miui.aod.util.MiAnimUtils;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.utils.DeviceUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import miuix.recyclerview.widget.MiuiDefaultItemAnimator;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class StyleListAdapter extends RecyclerView.Adapter<ViewHolder> implements Observer<List<StyleListViewModel.StyleListType>>, DefaultLifecycleObserver {
    public final Context mContext;
    public boolean mHasObserved;
    public final LayoutInflater mLayoutInflater;
    public LifecycleOwner mLifecycleOwner;
    public int mLastType = -1;
    public final Map mAdapterMap = new ArrayMap();
    public volatile List mDataList = new ArrayList();

    public class BannerViewHolder extends ViewHolder {
        public Bitmap bitmap;

        public BannerViewHolder(View view) {
            super(view);
            View viewFindViewById = view.findViewById(R.id.image_container);
            MiAnimUtils.applyPressedTintAndScaleStyle(viewFindViewById);
            viewFindViewById.setForeground(DrawableCache.getInstance(StyleListAdapter.this.mContext).getDrawable(R.drawable.aod_unselected_rect));
            viewFindViewById.setOnClickListener(this);
            ImageView imageView = (ImageView) view.findViewById(R.id.banner_image);
            if (imageView instanceof RoundCornerImageView) {
                ((RoundCornerImageView) imageView).setRectRoundRadius(StyleListAdapter.this.mContext.getResources().getDimensionPixelSize(R.dimen.select_drawable_corner_radius));
            }
            if (Utils.inLargeScreen(StyleListAdapter.this.mContext)) {
                this.bitmap = DrawableCache.getInstance(StyleListAdapter.this.mContext).getBitmap(R.drawable.fod_more_online_aod_banner, 1);
            } else {
                this.bitmap = DrawableCache.getInstance(StyleListAdapter.this.mContext).getBitmap(R.drawable.more_online_aod_banner, 1);
            }
            imageView.setImageBitmap(this.bitmap);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            int dimensionPixelSize = view.getResources().getDisplayMetrics().widthPixels - (StyleListAdapter.this.mContext.getResources().getDimensionPixelSize(R.dimen.list_margin_start) * 2);
            layoutParams.width = dimensionPixelSize;
            layoutParams.height = (int) (((dimensionPixelSize * this.bitmap.getHeight()) * 1.0f) / this.bitmap.getWidth());
        }
    }

    public class PreviewViewHolder extends ViewHolder {
        public CurrentTemplateApi currentTemplateApi;
        public String lastTemplateId;
        public FullAodTemplateView lastTemplateView;
        public TextView mPreviewHint;
        public FrameLayout mTemplateContainer;

        public static /* synthetic */ void $r8$lambda$2Midq_fxnziLX2iiZsCRUrJ9_9A(PreviewViewHolder previewViewHolder, ObservableEmitter observableEmitter) {
            if (previewViewHolder.currentTemplateApi == null) {
                previewViewHolder.currentTemplateApi = new CurrentTemplateApi(AODApplication.aodSettingDensityContext);
            }
            observableEmitter.onNext(previewViewHolder.currentTemplateApi.requireCurrentTemplate());
            observableEmitter.onComplete();
        }

        public static /* synthetic */ Unit $r8$lambda$K1j19nUY6J8aFLDvKk5uPr5oTaI(PreviewViewHolder previewViewHolder) {
            previewViewHolder.lastTemplateId = null;
            previewViewHolder.showClockView();
            return null;
        }

        public PreviewViewHolder(View view) {
            super(view);
            this.mTemplateContainer = (FrameLayout) view.findViewById(R.id.template_view);
            this.mPreviewHint = (TextView) view.findViewById(R.id.preview_hint);
            if (Utils.isFoldDevice()) {
                if (this.mPreviewHint != null && !Utils.isInnerScreenSupportFsAod()) {
                    this.mPreviewHint.setVisibility(0);
                }
                if (Utils.isCurrentScreenSupportFsAod(StyleListAdapter.this.mContext)) {
                    return;
                }
                TextView textView = (TextView) view.findViewById(R.id.preview_title);
                if (textView != null) {
                    textView.setVisibility(8);
                }
                FrameLayout frameLayout = this.mTemplateContainer;
                if (frameLayout != null) {
                    frameLayout.setVisibility(8);
                }
            }
        }

        @Override // com.miui.aod.stylelist.StyleListAdapter.ViewHolder
        public void bind(StyleListViewModel.StyleListType styleListType) {
            super.bind(styleListType);
            if (styleListType instanceof StyleListViewModel.PreviewType) {
                showClockView();
            }
        }

        public final void createOrLoadTemplateView(String str, TemplateConfig templateConfig) {
            WindowInsets rootWindowInsets;
            if (templateConfig != null) {
                Point point = new Point();
                if (StyleListAdapter.this.mContext.getDisplay() == null) {
                    return;
                }
                StyleListAdapter.this.mContext.getDisplay().getRealSize(point);
                if (point.x <= 0 || point.y <= 0) {
                    return;
                }
                int width = this.itemView.getWidth();
                if (width <= 0) {
                    width = point.x;
                }
                float dimensionPixelSize = StyleListAdapter.this.mContext.getResources().getDimensionPixelSize(Utils.isFoldDevice() ? R.dimen.aod_preview_img_width_fold : R.dimen.aod_preview_img_width) / width;
                int i = (int) (point.y * dimensionPixelSize);
                float[] fArr = null;
                ColorPickerCache.INSTANCE.cacheMyColorData(null);
                if (Utils.isFoldDevice() && (rootWindowInsets = this.itemView.getRootWindowInsets()) != null) {
                    fArr = new float[8];
                    int[] iArr = {0, 1, 2, 3};
                    for (int i2 = 0; i2 < 4; i2++) {
                        RoundedCorner roundedCorner = rootWindowInsets.getRoundedCorner(iArr[i2]);
                        float radius = roundedCorner != null ? roundedCorner.getRadius() : 0.0f;
                        int i3 = i2 * 2;
                        fArr[i3] = radius;
                        fArr[i3 + 1] = radius;
                    }
                }
                FullAodTemplateView fullAodTemplateViewCreateScaledFullAodTemplateView = FullAodTemplateViewKt.createScaledFullAodTemplateView(StyleListAdapter.this.mContext.createConfigurationContext(DeviceUtil.INSTANCE.initialConfiguration(AODApplication.aodSettingDensityContext)), str, new FrameLayout.LayoutParams(width, point.y, 1), dimensionPixelSize, dimensionPixelSize, templateConfig, fArr);
                int dimensionPixelSize2 = StyleListAdapter.this.mContext.getResources().getDimensionPixelSize(R.dimen.aod_preview_img_margin_vertical);
                ViewGroup.LayoutParams layoutParams = this.mTemplateContainer.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.height = (dimensionPixelSize2 * 2) + i;
                    this.mTemplateContainer.setLayoutParams(layoutParams);
                }
                if (fullAodTemplateViewCreateScaledFullAodTemplateView == null) {
                    return;
                }
                this.mTemplateContainer.removeView(this.lastTemplateView);
                this.lastTemplateView = fullAodTemplateViewCreateScaledFullAodTemplateView;
                this.mTemplateContainer.addView(fullAodTemplateViewCreateScaledFullAodTemplateView);
                this.lastTemplateId = str;
                fullAodTemplateViewCreateScaledFullAodTemplateView.loadData();
                fullAodTemplateViewCreateScaledFullAodTemplateView.registerWallpaperListener(new Function0() { // from class: com.miui.aod.stylelist.StyleListAdapter$PreviewViewHolder$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return StyleListAdapter.PreviewViewHolder.$r8$lambda$K1j19nUY6J8aFLDvKk5uPr5oTaI(this.f$0);
                    }
                });
                updatePreViewRootLayout(i);
            }
        }

        @Override // com.miui.aod.stylelist.StyleListAdapter.ViewHolder, android.view.View.OnClickListener
        public /* bridge */ /* synthetic */ void onClick(View view) {
            super.onClick(view);
        }

        public void showClockView() {
            if (Looper.getMainLooper().isCurrentThread()) {
                updateItemTopMargin();
                if (!Utils.isFoldDevice() || Utils.isCurrentScreenSupportFsAod(StyleListAdapter.this.mContext)) {
                    Observable.create(new ObservableOnSubscribe() { // from class: com.miui.aod.stylelist.StyleListAdapter$PreviewViewHolder$$ExternalSyntheticLambda1
                        @Override // io.reactivex.rxjava3.core.ObservableOnSubscribe
                        public final void subscribe(ObservableEmitter observableEmitter) {
                            StyleListAdapter.PreviewViewHolder.$r8$lambda$2Midq_fxnziLX2iiZsCRUrJ9_9A(this.f$0, observableEmitter);
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<TemplateConfig>() { // from class: com.miui.aod.stylelist.StyleListAdapter.PreviewViewHolder.1
                        @Override // io.reactivex.rxjava3.core.Observer
                        public void onComplete() {
                        }

                        @Override // io.reactivex.rxjava3.core.Observer
                        public void onError(Throwable th) {
                            Log.i("StyleListAdapter", "requireCurrentTemplate error:", th);
                        }

                        @Override // io.reactivex.rxjava3.core.Observer
                        public void onNext(TemplateConfig templateConfig) {
                            PreviewViewHolder.this.createOrLoadTemplateView(templateConfig.getClockInfo().getTemplateId(), templateConfig);
                        }
                    });
                } else {
                    this.itemView.post(new Runnable() { // from class: com.miui.aod.stylelist.StyleListAdapter$PreviewViewHolder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.updatePreViewRootLayout(0);
                        }
                    });
                }
            }
        }

        public final void updateItemTopMargin() {
            if (this.itemView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) this.itemView.getLayoutParams()).topMargin = (!Utils.isFoldDevice() || Utils.isCurrentScreenSupportFsAod(StyleListAdapter.this.mContext)) ? 0 : -Utils.dp2Px(12);
            }
        }

        public final void updatePreViewRootLayout(int i) {
            int measuredHeight;
            if (this.itemView.getWidth() <= 0 || this.itemView.getLayoutParams() == null) {
                return;
            }
            int dimensionPixelSize = StyleListAdapter.this.mContext.getResources().getDimensionPixelSize(R.dimen.aod_preview_title_min_height);
            TextView textView = (TextView) this.itemView.findViewById(R.id.preview_title);
            if (textView != null) {
                dimensionPixelSize = Math.max(dimensionPixelSize, textView.getHeight());
            }
            TextView textView2 = this.mPreviewHint;
            if (textView2 == null || textView2.getVisibility() != 0) {
                measuredHeight = 0;
            } else {
                this.mPreviewHint.measure(View.MeasureSpec.makeMeasureSpec(this.itemView.getWidth(), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
                measuredHeight = this.mPreviewHint.getMeasuredHeight();
                if (this.mPreviewHint.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    measuredHeight += ((ViewGroup.MarginLayoutParams) this.mPreviewHint.getLayoutParams()).topMargin;
                }
            }
            this.itemView.getLayoutParams().height = dimensionPixelSize + measuredHeight + (this.mTemplateContainer.getVisibility() != 8 ? this.mTemplateContainer.getLayoutParams().height : 0) + Utils.dp2Px(2);
        }
    }

    public static class StyleListDiffResult {
        public final DiffUtil.DiffResult diffResult;
        public final List newList;

        public StyleListDiffResult(DiffUtil.DiffResult diffResult, List list) {
            this.diffResult = diffResult;
            this.newList = list;
        }
    }

    public class StyleListViewHolder extends ViewHolder {
        public final StyleListItemView mItemView;

        public StyleListViewHolder(StyleListItemView styleListItemView) {
            super(styleListItemView);
            this.mItemView = styleListItemView;
        }

        @Override // com.miui.aod.stylelist.StyleListAdapter.ViewHolder
        public void bind(StyleListViewModel.StyleListType styleListType) {
            ArrayMap<String, Pair<Integer, Integer>> arrayMap = CategoryFactory.sCategoryNameMap;
            if (arrayMap.containsKey(styleListType.getListType())) {
                this.mItemView.setTitle(StyleListAdapter.this.mContext.getString(((Integer) arrayMap.get(styleListType.getListType()).first).intValue()));
            } else {
                this.mItemView.setTitle(styleListType.getListType());
            }
            this.mItemView.setArrowClickListener(styleListType.isClickEnable() ? this : null);
            this.mItemView.setRightValue(styleListType.getValueRight());
            this.mItemView.setAdapter(StyleListAdapter.this.getAdapter(styleListType));
            if ("my_aod_style".equals(styleListType.getListType())) {
                this.mItemView.setAnimator(new MiuiDefaultItemAnimator());
                if (this.mItemView.getAdapter() instanceof MyStyleAdapter) {
                    ((MyStyleAdapter) this.mItemView.getAdapter()).setDataChangedCallback(new DataChangedCallback(StyleListAdapter.this));
                }
            }
        }

        public void bindTitleInfo(StyleListViewModel.StyleListType styleListType) {
            ArrayMap<String, Pair<Integer, Integer>> arrayMap = CategoryFactory.sCategoryNameMap;
            if (arrayMap.containsKey(styleListType.getListType())) {
                this.mItemView.setTitle(StyleListAdapter.this.mContext.getString(((Integer) arrayMap.get(styleListType.getListType()).first).intValue()));
            } else {
                this.mItemView.setTitle(styleListType.getListType());
            }
            this.mItemView.setArrowClickListener(styleListType.isClickEnable() ? this : null);
            this.mItemView.setRightValue(styleListType.getValueRight());
        }

        public void recycle() {
            this.mItemView.recycle();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VIEW_TYPE {
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHolder(View view) {
            super(view);
        }

        public void bind(StyleListViewModel.StyleListType styleListType) {
        }

        public void onClick(View view) {
            int bindingAdapterPosition = getBindingAdapterPosition();
            if (bindingAdapterPosition < 0 || bindingAdapterPosition >= StyleListAdapter.this.mDataList.size()) {
                return;
            }
            EventBus.getDefault().post(StyleListAdapter.this.mDataList.get(bindingAdapterPosition));
        }
    }

    public static /* synthetic */ void $r8$lambda$iKQgFJGLD5_CynxRUvJ4odQ_Jak(StyleListAdapter styleListAdapter, List list, ObservableEmitter observableEmitter) {
        styleListAdapter.getClass();
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            arrayList.addAll(list);
        }
        observableEmitter.onNext(new StyleListDiffResult(DiffUtil.calculateDiff(new StyleListDiffUtilCallback(styleListAdapter.mDataList, arrayList), false), arrayList));
        observableEmitter.onComplete();
    }

    public StyleListAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RecyclerView.Adapter getAdapter(StyleListViewModel.StyleListType styleListType) {
        String listType = styleListType.getListType();
        if (this.mAdapterMap.containsKey(listType)) {
            return (RecyclerView.Adapter) this.mAdapterMap.get(listType);
        }
        RecyclerView.Adapter adapterCreateAdapter = styleListType.createAdapter(this.mContext, this.mLifecycleOwner);
        if (adapterCreateAdapter != null) {
            this.mAdapterMap.put(listType, adapterCreateAdapter);
        }
        return adapterCreateAdapter;
    }

    public static StyleListAdapter newInstance(Context context) {
        return new StyleListAdapter(context);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mDataList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (i < 0 || i >= this.mDataList.size()) {
            return 0;
        }
        return ((StyleListViewModel.StyleListType) this.mDataList.get(i)).getViewType();
    }

    public MyStyleAdapter getMyStyleAdapter() {
        for (RecyclerView.Adapter adapter : this.mAdapterMap.values()) {
            if (adapter instanceof MyStyleAdapter) {
                return (MyStyleAdapter) adapter;
            }
        }
        return null;
    }

    public int getShowType() {
        return this.mLastType;
    }

    public void observeLifecycle(LifecycleOwner lifecycleOwner) {
        if (this.mHasObserved) {
            return;
        }
        this.mHasObserved = true;
        this.mLifecycleOwner = lifecycleOwner;
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (i < 0 || i >= this.mDataList.size()) {
            return;
        }
        viewHolder.bind((StyleListViewModel.StyleListType) this.mDataList.get(i));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i, List list) {
        if (list.isEmpty()) {
            onBindViewHolder(viewHolder, i);
            return;
        }
        for (Object obj : list) {
            if ((obj instanceof Boolean) && (viewHolder instanceof StyleListViewHolder) && i >= 0 && i < this.mDataList.size()) {
                ((StyleListViewHolder) viewHolder).bindTitleInfo((StyleListViewModel.StyleListType) this.mDataList.get(i));
            }
            if (obj instanceof Integer) {
                View view = viewHolder.itemView;
                if (view instanceof StyleListItemView) {
                    RecyclerView.Adapter<?> adapter = ((StyleListItemView) view).getAdapter();
                    if (adapter.getItemViewType(0) == 24) {
                        adapter.notifyItemRangeChanged(0, 2);
                    } else {
                        adapter.notifyItemChanged(0);
                    }
                    if (((Integer) obj).intValue() == 2) {
                        ((StyleListItemView) viewHolder.itemView).notifyUpdateItemDecoration();
                    }
                }
            }
        }
    }

    @Override // androidx.lifecycle.Observer
    public void onChanged(final List list) {
        if (list == null || list.size() < 2) {
            return;
        }
        if (((StyleListViewModel.StyleListType) list.get(1)).getViewType() == 126 && this.mLastType == 1) {
            return;
        }
        if (((StyleListViewModel.StyleListType) list.get(1)).getViewType() == 123) {
            this.mLastType = 0;
        } else {
            this.mLastType = 1;
        }
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.aod.stylelist.StyleListAdapter$$ExternalSyntheticLambda0
            @Override // io.reactivex.rxjava3.core.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                StyleListAdapter.$r8$lambda$iKQgFJGLD5_CynxRUvJ4odQ_Jak(this.f$0, list, observableEmitter);
            }
        }).subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<StyleListDiffResult>() { // from class: com.miui.aod.stylelist.StyleListAdapter.1
            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                Log.i("StyleListAdapter", "onError: " + th.getMessage());
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(StyleListDiffResult styleListDiffResult) {
                if (styleListDiffResult != null) {
                    StyleListAdapter.this.mDataList = styleListDiffResult.newList;
                    styleListDiffResult.diffResult.dispatchUpdatesTo(StyleListAdapter.this);
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case com.miui.maml.R.styleable.AppCompatTheme_windowFixedWidthMinor /* 123 */:
                return new StyleListViewHolder((StyleListItemView) this.mLayoutInflater.inflate(R.layout.style_list_item, viewGroup, false));
            case com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMajor /* 124 */:
                View viewInflate = this.mLayoutInflater.inflate(R.layout.more_online_banner, viewGroup, false);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewInflate.getLayoutParams();
                int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.list_margin_start);
                marginLayoutParams.rightMargin = dimensionPixelSize;
                marginLayoutParams.leftMargin = dimensionPixelSize;
                return new BannerViewHolder(viewInflate);
            case com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMinor /* 125 */:
                return new ViewHolder((SettingsView) this.mLayoutInflater.inflate(R.layout.settings_item, viewGroup, false));
            case com.miui.maml.R.styleable.AppCompatTheme_windowNoTitle /* 126 */:
                return new PreviewViewHolder(this.mLayoutInflater.inflate(R.layout.fs_aod_preview, viewGroup, false));
            default:
                throw new IllegalArgumentException("wrong viewType");
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner lifecycleOwner) {
        this.mAdapterMap.clear();
        lifecycleOwner.getLifecycle().removeObserver(this);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(ViewHolder viewHolder) {
        super.onViewRecycled(viewHolder);
        if (viewHolder instanceof StyleListViewHolder) {
            ((StyleListViewHolder) viewHolder).recycle();
        }
    }
}
