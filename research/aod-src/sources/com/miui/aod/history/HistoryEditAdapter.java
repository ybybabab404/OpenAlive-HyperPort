package com.miui.aod.history;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.aod.AODApplication;
import com.miui.aod.AODStyleController;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.components.widget.ITimeUpdate;
import com.miui.aod.util.MiAnimUtils;
import com.miui.aod.util.WindowUtils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.observers.DefaultObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import miuix.animation.Folme;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class HistoryEditAdapter extends RecyclerView.Adapter<HistoryViewHolder> implements Observer<List<HistoryEditViewModel.HistoryEditData>> {
    public int firstItemSpace;
    public int itemSpace;
    public AODStyleController mAODStyleController;
    public final Context mContext;
    public volatile List mDataList;
    public boolean mEditMode;
    public int mItemHeight;
    public int mItemWidth;
    public volatile List mOldDataList;
    public float mScale;

    public static class ClickEvent {
        public final HistoryEditViewModel.HistoryEditData mData;
        public final int mPosition;
        public final WeakReference mView;

        public ClickEvent(int i, View view, HistoryEditViewModel.HistoryEditData historyEditData) {
            this.mPosition = i;
            this.mView = new WeakReference(view);
            this.mData = historyEditData;
        }
    }

    public static class HistoryDiffResult {
        public final DiffUtil.DiffResult diffResult;
        public final List newList;

        public HistoryDiffResult(DiffUtil.DiffResult diffResult, List list) {
            this.diffResult = diffResult;
            this.newList = list;
        }
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder implements ITimeUpdate, View.OnLongClickListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        public final View mAodView;
        public final HistoryItemView mHistoryItemView;

        public HistoryViewHolder(HistoryItemView historyItemView) {
            super(historyItemView);
            historyItemView.setOnLongClickListener(this);
            historyItemView.setOnClickListener(this);
            this.mHistoryItemView = historyItemView;
            historyItemView.setOnCheckedChangeListener(this);
            this.mAodView = historyItemView.findViewById(R.id.aod_style_item);
        }

        public void bind() {
            int bindingAdapterPosition = getBindingAdapterPosition();
            if (bindingAdapterPosition < 0 || bindingAdapterPosition >= HistoryEditAdapter.this.mDataList.size()) {
                return;
            }
            HistoryEditViewModel.HistoryEditData historyEditData = (HistoryEditViewModel.HistoryEditData) HistoryEditAdapter.this.mDataList.get(bindingAdapterPosition);
            Folme.clean(this.itemView);
            MiAnimUtils.applyScaleStyle(this.itemView);
            String fullTitle = historyEditData.getCategoryInfo().getFullTitle();
            if (!TextUtils.isEmpty(fullTitle)) {
                this.mHistoryItemView.setContentDescription(fullTitle);
            }
            this.mHistoryItemView.setEditMode(HistoryEditAdapter.this.mEditMode, true);
            this.mHistoryItemView.setCheckBoxState(historyEditData.isSelected());
            HistoryEditAdapter.this.mAODStyleController.inflateView(this.mAodView, historyEditData.getCategoryInfo());
            HistoryEditAdapter.this.mAODStyleController.handleUpdateTime(DateFormat.is24HourFormat(this.mAodView.getContext()));
            HistoryEditAdapter.this.mAODStyleController.updateAodBgGravitiy();
        }

        @Override // com.miui.aod.components.widget.ITimeUpdate
        public void handleUpdateTime(boolean z) {
            KeyEvent.Callback callback = this.mAodView;
            if (callback instanceof ITimeUpdate) {
                ((ITimeUpdate) callback).handleUpdateTime(z);
            }
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            int bindingAdapterPosition = getBindingAdapterPosition();
            if (bindingAdapterPosition < 0 || bindingAdapterPosition >= HistoryEditAdapter.this.mDataList.size()) {
                return;
            }
            EventBus.getDefault().post(new HistoryEditViewModel.SelectEvent(bindingAdapterPosition, z));
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int bindingAdapterPosition = getBindingAdapterPosition();
            if (bindingAdapterPosition < 0 || bindingAdapterPosition >= HistoryEditAdapter.this.mDataList.size()) {
                return;
            }
            EventBus.getDefault().post(new ClickEvent(bindingAdapterPosition, view, (HistoryEditViewModel.HistoryEditData) HistoryEditAdapter.this.mDataList.get(bindingAdapterPosition)));
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            EventBus.getDefault().post(new HistoryEditViewModel.SwitchModeEvent(true));
            EventBus.getDefault().post(new HistoryEditViewModel.SelectEvent(getBindingAdapterPosition(), true));
            return true;
        }

        public void updateState(Bundle bundle) {
            int bindingAdapterPosition = getBindingAdapterPosition();
            if (bindingAdapterPosition < 0 || bindingAdapterPosition >= HistoryEditAdapter.this.mDataList.size()) {
                return;
            }
            HistoryEditViewModel.HistoryEditData historyEditData = (HistoryEditViewModel.HistoryEditData) HistoryEditAdapter.this.mDataList.get(bindingAdapterPosition);
            this.mHistoryItemView.setEditMode(HistoryEditAdapter.this.mEditMode, true);
            this.mHistoryItemView.setCheckBoxState(historyEditData.isSelected());
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$E_3hG_0PIPtvKAhSbD8-rlqdqYU, reason: not valid java name */
    public static /* synthetic */ void m396$r8$lambda$E_3hG_0PIPtvKAhSbD8rlqdqYU(HistoryEditAdapter historyEditAdapter, List list, ObservableEmitter observableEmitter) {
        historyEditAdapter.getClass();
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((HistoryEditViewModel.HistoryEditData) it.next()).copy());
            }
        }
        DiffUtil.DiffResult diffResultCalculateDiff = DiffUtil.calculateDiff(new HistoryEditDataDiffUtilCallback(historyEditAdapter.mOldDataList, arrayList), false);
        historyEditAdapter.mOldDataList = arrayList;
        observableEmitter.onNext(new HistoryDiffResult(diffResultCalculateDiff, arrayList));
        observableEmitter.onComplete();
    }

    public HistoryEditAdapter(Context context) {
        context = context == null ? AODApplication.sInstance : context;
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mDataList = new ArrayList();
        this.firstItemSpace = context.getResources().getDimensionPixelSize(Utils.inLargeScreen(applicationContext) ? R.dimen.history_first_item_space_large : R.dimen.history_first_item_space);
        this.itemSpace = context.getResources().getDimensionPixelSize(Utils.inLargeScreen(applicationContext) ? R.dimen.history_item_space_large : R.dimen.history_item_space);
        int widthPixel = WindowUtils.INSTANCE.getWidthPixel(context);
        int integer = context.getResources().getInteger(Utils.inLargeScreen(applicationContext) ? R.integer.history_span_count_large : R.integer.history_span_count);
        setItemWidth(((widthPixel - (this.firstItemSpace * 2)) - (this.itemSpace * (integer - 1))) / integer);
        this.mScale = this.mItemWidth > 0 ? (Utils.getStyleItemWidth(context) * 1.0f) / this.mItemWidth : 1.0f;
        this.mAODStyleController = new AODStyleController(2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mDataList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(HistoryViewHolder historyViewHolder, int i) {
        historyViewHolder.bind();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(HistoryViewHolder historyViewHolder, int i, List list) {
        if (list.isEmpty()) {
            onBindViewHolder(historyViewHolder, i);
            return;
        }
        for (Object obj : list) {
            if (obj instanceof Boolean) {
                historyViewHolder.handleUpdateTime(((Boolean) obj).booleanValue());
            } else if (obj instanceof Bundle) {
                historyViewHolder.updateState((Bundle) obj);
            }
        }
    }

    @Override // androidx.lifecycle.Observer
    public void onChanged(final List list) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.aod.history.HistoryEditAdapter$$ExternalSyntheticLambda0
            @Override // io.reactivex.rxjava3.core.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                HistoryEditAdapter.m396$r8$lambda$E_3hG_0PIPtvKAhSbD8rlqdqYU(this.f$0, list, observableEmitter);
            }
        }).subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<HistoryDiffResult>() { // from class: com.miui.aod.history.HistoryEditAdapter.1
            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                Log.i("HistoryEditAdapter", "onError: " + th.getMessage());
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(HistoryDiffResult historyDiffResult) {
                if (historyDiffResult != null) {
                    HistoryEditAdapter.this.mDataList = historyDiffResult.newList;
                    historyDiffResult.diffResult.dispatchUpdatesTo(new ListUpdateCallback() { // from class: com.miui.aod.history.HistoryEditAdapter.1.1
                        @Override // androidx.recyclerview.widget.ListUpdateCallback
                        public void onChanged(int i, int i2, Object obj) {
                            HistoryEditAdapter.this.notifyItemRangeChanged(i, i2, obj);
                        }

                        @Override // androidx.recyclerview.widget.ListUpdateCallback
                        public void onInserted(int i, int i2) {
                            HistoryEditAdapter.this.notifyItemRangeInserted(i, i2);
                        }

                        @Override // androidx.recyclerview.widget.ListUpdateCallback
                        public void onMoved(int i, int i2) {
                            HistoryEditAdapter.this.notifyItemMoved(i, i);
                        }

                        @Override // androidx.recyclerview.widget.ListUpdateCallback
                        public void onRemoved(int i, int i2) {
                            HistoryEditAdapter.this.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public HistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_history_item, viewGroup, false);
        float f = this.mScale;
        viewInflate.getLayoutParams().width = this.mItemWidth;
        viewInflate.getLayoutParams().height = this.mItemHeight;
        View viewFindViewById = viewInflate.findViewById(R.id.aod_style_item);
        viewFindViewById.getLayoutParams().width = (int) (this.mItemWidth * f);
        viewFindViewById.getLayoutParams().height = (int) (this.mItemHeight * f);
        float f2 = f - 1.0f;
        float f3 = ((-this.mItemWidth) * f2) / 2.0f;
        if (Utils.isLayoutRtl()) {
            f3 = -f3;
        }
        viewFindViewById.setTranslationX(f3);
        viewFindViewById.setTranslationY(((-this.mItemHeight) * f2) / 2.0f);
        float f4 = 1.0f / f;
        viewFindViewById.setScaleX(f4);
        viewFindViewById.setScaleY(f4);
        return new HistoryViewHolder((HistoryItemView) viewInflate);
    }

    public void onTimeTick() {
        notifyItemRangeChanged(0, getItemCount(), Boolean.valueOf(DateFormat.is24HourFormat(this.mContext)));
    }

    public final void setItemWidth(int i) {
        this.mItemWidth = i;
        if (!Utils.inLargeScreen(this.mContext)) {
            this.mItemHeight = (int) Math.ceil(((double) i) * 1.2d);
            return;
        }
        Point windowSize = WindowUtils.INSTANCE.getWindowSize(this.mContext);
        this.mItemHeight = (int) (this.mItemWidth * (windowSize.y / windowSize.x));
    }

    public void switchEditMode(boolean z) {
        if (z != this.mEditMode) {
            this.mEditMode = z;
            notifyItemRangeChanged(0, getItemCount(), new Bundle());
        }
    }
}
