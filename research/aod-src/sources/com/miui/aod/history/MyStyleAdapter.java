package com.miui.aod.history;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.miui.aod.AODApplication;
import com.miui.aod.AODStyleController;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.aodannotation.JsonStringBinder;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.category.CategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.AodContainerView;
import com.miui.aod.db.HistoryEntity;
import com.miui.aod.stylelist.BaseViewHolder;
import com.miui.aod.stylelist.DataChangedCallback;
import com.miui.aod.stylelist.StyleCategoryAdapter;
import com.miui.aod.util.MiAnimUtils;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class MyStyleAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public AODStyleController mAODStyleController;
    public final Context mContext;
    public DataChangedCallback mDataChangedCallback;
    public List mDataList;
    public volatile List mEntityDataList;
    public HistorySubViewModel.HistorySubData mHistorySubData;
    public int mItemHeight;
    public int mItemWidth;

    public static class HistoryDiffResult {
        public final DiffUtil.DiffResult diffResult;
        public final List newList;

        public HistoryDiffResult(DiffUtil.DiffResult diffResult, List list) {
            this.diffResult = diffResult;
            this.newList = list;
        }
    }

    public class HistoryViewHolder extends BaseViewHolder implements View.OnClickListener {
        public HistoryViewHolder(View view) {
            super(view);
            MiAnimUtils.applyScaleStyle(view);
            if (view instanceof AodContainerView) {
                ((AodContainerView) view).setNeedDrawEdge(true);
            }
            view.setOnClickListener(this);
            view.setBackgroundResource(R.drawable.style_category_list_item_bg);
            view.setOutlineProvider(new RoundOutlineProvider(MyStyleAdapter.this.mContext.getResources().getDimensionPixelSize(R.dimen.aod_style_bg_radius)));
        }

        public void bind() {
            int bindingAdapterPosition = getBindingAdapterPosition();
            if (bindingAdapterPosition < 0 || bindingAdapterPosition >= MyStyleAdapter.this.mDataList.size()) {
                return;
            }
            StyleInfo styleInfo = (StyleInfo) MyStyleAdapter.this.mDataList.get(bindingAdapterPosition);
            String fullTitle = styleInfo.getFullTitle();
            if (!TextUtils.isEmpty(fullTitle)) {
                this.itemView.setContentDescription(fullTitle);
            }
            if (MyStyleAdapter.this.mAODStyleController == null) {
                MyStyleAdapter.this.mAODStyleController = new AODStyleController(2);
            }
            MyStyleAdapter.this.mAODStyleController.inflateView(this.itemView, styleInfo);
            MyStyleAdapter.this.mAODStyleController.handleUpdateTime(DateFormat.is24HourFormat(this.itemView.getContext()));
            MyStyleAdapter.this.mAODStyleController.updateAodBgGravitiy();
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int bindingAdapterPosition = getBindingAdapterPosition();
            if (bindingAdapterPosition < 0 || bindingAdapterPosition >= MyStyleAdapter.this.mDataList.size()) {
                return;
            }
            EventBus.getDefault().post(new StyleCategoryAdapter.ClickEvent(bindingAdapterPosition, view, (StyleInfo) MyStyleAdapter.this.mDataList.get(bindingAdapterPosition), bindingAdapterPosition == 0 ? "my_theme_custom" : "my_theme"));
        }
    }

    public static class MyStyleData {
        public final CategoryInfo mCateInfo;
        public final HistoryEntity mEntity;

        public MyStyleData(HistoryEntity historyEntity, CategoryInfo categoryInfo) {
            this.mEntity = historyEntity;
            this.mCateInfo = categoryInfo;
        }

        public boolean isHistory() {
            return this.mEntity != null;
        }
    }

    public class SuperWallpaperViewHolder extends HistoryViewHolder {
        public SuperWallpaperViewHolder(View view) {
            super(view);
        }

        @Override // com.miui.aod.history.MyStyleAdapter.HistoryViewHolder
        public void bind() {
            if (MyStyleAdapter.this.mAODStyleController == null) {
                MyStyleAdapter.this.mAODStyleController = new AODStyleController(2);
            }
            StyleInfo styleInfo = AODStyleController.getStyleInfo(MyStyleAdapter.this.mContext);
            this.itemView.setContentDescription(MyStyleAdapter.this.getSuperWallpaperTitle());
            MyStyleAdapter.this.mAODStyleController.inflateView(this.itemView, styleInfo);
            MyStyleAdapter.this.mAODStyleController.handleUpdateTime(DateFormat.is24HourFormat(this.itemView.getContext()));
        }

        @Override // com.miui.aod.history.MyStyleAdapter.HistoryViewHolder, android.view.View.OnClickListener
        public void onClick(View view) {
            EventBus.getDefault().post(new StyleCategoryAdapter.ClickEvent(0, view, (StyleInfo) MyStyleAdapter.this.mDataList.get(0), "my_super_wallpaper"));
        }
    }

    public static /* synthetic */ void $r8$lambda$yuk_KXHosgLoyvGLcx7vTheETtc(MyStyleAdapter myStyleAdapter, HistorySubViewModel.HistorySubData historySubData, ObservableEmitter observableEmitter) {
        myStyleAdapter.getClass();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Gson categoriesGson = CategoryFactory.getCategoriesGson();
        if (Utils.isUsingSuperWallpaperStyle(myStyleAdapter.mContext)) {
            CategoryInfo categoryInfo = (CategoryInfo) CategoryFactory.createStyleInfo(myStyleAdapter.mContext, Utils.getAodUsingCategoryName(myStyleAdapter.mContext));
            arrayList.add(categoryInfo);
            arrayList2.add(new MyStyleData(null, categoryInfo));
        }
        CategoryInfo categoryInfo2 = (CategoryInfo) CategoryFactory.createStyleInfo(myStyleAdapter.mContext, CategoryConstants.CATEGORY_LINKAGE);
        arrayList.add(categoryInfo2);
        arrayList2.add(new MyStyleData(null, categoryInfo2));
        if (historySubData != null) {
            for (HistoryEntity historyEntity : historySubData.mList) {
                String cateName = historyEntity.getCateName();
                String jsonParameter = historyEntity.getJsonParameter();
                Map<String, Class<?>> map = CategoryFactory.sCategoryMap;
                if (!map.containsKey(cateName)) {
                    cateName = CategoryConstants.getDefaultCategoryName(myStyleAdapter.mContext);
                    jsonParameter = CategoryConstants.DEFAULT_PARAM;
                }
                arrayList.add((CategoryInfo) categoriesGson.fromJson(jsonParameter, (Class) map.get(cateName)));
                arrayList2.add(new MyStyleData(historyEntity, null));
            }
        }
        DiffUtil.DiffResult diffResultCalculateDiff = DiffUtil.calculateDiff(new MyStyleDiffUtilCallback(myStyleAdapter.mEntityDataList, arrayList2), false);
        myStyleAdapter.mEntityDataList = arrayList2;
        observableEmitter.onNext(new HistoryDiffResult(diffResultCalculateDiff, arrayList));
        observableEmitter.onComplete();
    }

    public MyStyleAdapter(Context context) {
        JsonStringBinder.bind(this);
        this.mContext = context.getApplicationContext();
        this.mDataList = new ArrayList();
        setItemWidth();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mDataList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return (Utils.isUsingSuperWallpaperStyle(this.mContext) && i == 0) ? 24 : 23;
    }

    public final String getSuperWallpaperTitle() {
        WallpaperManager wallpaperManager = (WallpaperManager) AODApplication.sInstance.getSystemService("wallpaper");
        if (wallpaperManager == null) {
            Log.i("MyStyleAdapter", "mWallpaperManager == null");
            return null;
        }
        WallpaperInfo wallpaperInfo = wallpaperManager.getWallpaperInfo();
        return (wallpaperInfo == null || wallpaperInfo.getServiceInfo() == null || wallpaperInfo.getServiceInfo().metaData == null) ? AODApplication.sInstance.getString(R.string.aod_style) : wallpaperInfo.getServiceInfo().metaData.getString("title", AODApplication.sInstance.getString(R.string.aod_style));
    }

    public final boolean isNeedChanged(List list, List list2, HistorySubViewModel.HistorySubData historySubData) {
        if (list.isEmpty() || this.mHistorySubData == null) {
            return false;
        }
        return (list.size() == list2.size() && this.mHistorySubData == historySubData) ? false : true;
    }

    public final boolean isRemoveSuperWallpaperStyle(List list) {
        List list2 = this.mDataList;
        return (list2 == null || list2.isEmpty() || this.mDataList.get(0) == null || list == null || list.isEmpty() || list.get(0) == null || !CategoryConstants.CATEGORY_LINKAGE.equals(((CategoryInfo) list.get(0)).getName()) || !CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(((CategoryInfo) this.mDataList.get(0)).getName())) ? false : true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int i) {
        ((HistoryViewHolder) baseViewHolder).bind();
    }

    public void onChanged(final HistorySubViewModel.HistorySubData historySubData) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.aod.history.MyStyleAdapter$$ExternalSyntheticLambda0
            @Override // io.reactivex.rxjava3.core.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                MyStyleAdapter.$r8$lambda$yuk_KXHosgLoyvGLcx7vTheETtc(this.f$0, historySubData, observableEmitter);
            }
        }).subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<HistoryDiffResult>() { // from class: com.miui.aod.history.MyStyleAdapter.1
            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                Log.i("MyStyleAdapter", "onError: " + th.getMessage());
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(HistoryDiffResult historyDiffResult) {
                if (historyDiffResult != null) {
                    historyDiffResult.diffResult.dispatchUpdatesTo(MyStyleAdapter.this);
                    if (MyStyleAdapter.this.mDataChangedCallback != null) {
                        MyStyleAdapter myStyleAdapter = MyStyleAdapter.this;
                        if (myStyleAdapter.isNeedChanged(myStyleAdapter.mDataList, historyDiffResult.newList, historySubData)) {
                            Log.d("MyStyleAdapter", "mDataChangedCallback.onDataChanged");
                            MyStyleAdapter.this.mDataChangedCallback.onDataChanged(1, MyStyleAdapter.this.isRemoveSuperWallpaperStyle(historyDiffResult.newList));
                        }
                    }
                    MyStyleAdapter.this.mDataList = historyDiffResult.newList;
                    MyStyleAdapter.this.mHistorySubData = historySubData;
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
        if (i == 23) {
            View viewInflate = layoutInflaterFrom.inflate(R.layout.aod_style_category_item, viewGroup, false);
            viewInflate.getLayoutParams().width = this.mItemWidth;
            viewInflate.getLayoutParams().height = this.mItemHeight;
            return new HistoryViewHolder(viewInflate);
        }
        if (i != 24) {
            throw new IllegalArgumentException("wrong viewType");
        }
        View viewInflate2 = layoutInflaterFrom.inflate(R.layout.aod_style_category_item, viewGroup, false);
        viewInflate2.getLayoutParams().width = this.mItemWidth;
        viewInflate2.getLayoutParams().height = this.mItemHeight;
        return new SuperWallpaperViewHolder(viewInflate2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(BaseViewHolder baseViewHolder) {
        View view = baseViewHolder.itemView;
        if (view instanceof AodContainerView) {
            ((AodContainerView) view).releaseDrawable();
        }
        super.onViewRecycled(baseViewHolder);
    }

    public void removeSuperWallpaperItem() {
        List list = this.mDataList;
        if (list == null || list.isEmpty() || this.mDataList.get(0) == null || !CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(((CategoryInfo) this.mDataList.get(0)).getName())) {
            return;
        }
        this.mDataList.remove(0);
        if (this.mEntityDataList != null && !this.mEntityDataList.isEmpty()) {
            this.mEntityDataList.remove(0);
        }
        notifyItemRemoved(0);
        Log.i("MyStyleAdapter", "Super wallpaper item removed");
    }

    public void setDataChangedCallback(DataChangedCallback dataChangedCallback) {
        this.mDataChangedCallback = dataChangedCallback;
    }

    public final void setItemWidth() {
        this.mItemHeight = Utils.getSmallViewHeight(this.mContext);
        this.mItemWidth = Utils.getStyleItemWidth(this.mContext);
    }
}
