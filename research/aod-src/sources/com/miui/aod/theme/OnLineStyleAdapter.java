package com.miui.aod.theme;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.resource.DrawableCache;
import com.miui.aod.theme.beans.OnLineAodBean;
import com.miui.aod.util.MiAnimUtils;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class OnLineStyleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final Context mContext;
    public final List mDataList;
    public int mItemHeight;
    public int mItemWidth;
    public final OnLineAodBean mOnLineAodBean;

    public static class ClickEvent {
        public final OnLineAodBean mAodBean;
        public final OnLineAodBean.ProductsBean mData;
        public final int mPosition;

        public ClickEvent(int i, OnLineAodBean.ProductsBean productsBean) {
            this.mPosition = i;
            this.mData = productsBean;
            this.mAodBean = null;
        }

        public ClickEvent(int i, OnLineAodBean onLineAodBean) {
            this.mPosition = i;
            this.mAodBean = onLineAodBean;
            this.mData = null;
        }
    }

    public static class OnlineData {
        public final OnLineAodBean.ProductsBean mProductsBean;

        public OnlineData(OnLineAodBean.ProductsBean productsBean) {
            this.mProductsBean = productsBean;
        }
    }

    public class OnlineMoreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public OnlineMoreViewHolder(View view) {
            super(view);
            ImageView imageView = (ImageView) view;
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            MiAnimUtils.applyPressedTintAndScaleStyle(imageView);
            imageView.setImageDrawable(DrawableCache.getInstance(OnLineStyleAdapter.this.mContext).getDrawable(R.drawable.ic_theme_manager, 1));
            imageView.setContentDescription(OnLineStyleAdapter.this.mContext.getResources().getString(R.string.theme_manager_other));
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            EventBus.getDefault().post(new ClickEvent(getBindingAdapterPosition(), OnLineStyleAdapter.this.mOnLineAodBean));
        }
    }

    public class OnlineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mImageView;

        public OnlineViewHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view;
            MiAnimUtils.applyScaleStyle(view);
            view.setOnClickListener(this);
        }

        public void bind(OnLineAodBean.ProductsBean productsBean) {
            ThemeImageLoader.getInstance(OnLineStyleAdapter.this.mContext).loadImage(this.mImageView, productsBean.getImageUrl(), productsBean.getProductUuid());
            String name = productsBean.getName();
            if (TextUtils.isEmpty(name)) {
                return;
            }
            this.itemView.setContentDescription(name);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int bindingAdapterPosition = getBindingAdapterPosition();
            if (bindingAdapterPosition < 0 || bindingAdapterPosition >= OnLineStyleAdapter.this.mDataList.size()) {
                return;
            }
            EventBus.getDefault().post(new ClickEvent(bindingAdapterPosition, ((OnlineData) OnLineStyleAdapter.this.mDataList.get(bindingAdapterPosition)).mProductsBean));
        }
    }

    public OnLineStyleAdapter(Context context, OnLineAodBean onLineAodBean) {
        this.mContext = context.getApplicationContext();
        setItemWidth();
        this.mDataList = new ArrayList();
        this.mOnLineAodBean = onLineAodBean;
        if (onLineAodBean.getProducts() != null) {
            Iterator<OnLineAodBean.ProductsBean> it = onLineAodBean.getProducts().iterator();
            while (it.hasNext()) {
                this.mDataList.add(new OnlineData(it.next()));
            }
        }
        if (Utils.isFoldDevice()) {
            return;
        }
        this.mDataList.add(new OnlineData(null));
    }

    private void setItemWidth() {
        this.mItemHeight = Utils.getSmallViewHeight(this.mContext);
        this.mItemWidth = Utils.getStyleItemWidth(this.mContext);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mDataList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return (!Utils.isFoldDevice() && i == this.mDataList.size() + (-1)) ? 22 : 23;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof OnlineViewHolder) {
            ((OnlineViewHolder) viewHolder).bind(((OnlineData) this.mDataList.get(i)).mProductsBean);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        OnlineImageView onlineImageView = new OnlineImageView(this.mContext);
        onlineImageView.setLayoutParams(new ViewGroup.LayoutParams(this.mItemWidth, this.mItemHeight));
        RecyclerView.ViewHolder onlineViewHolder = new OnlineViewHolder(onlineImageView);
        if (22 == i) {
            onlineViewHolder = new OnlineMoreViewHolder(onlineImageView);
        }
        onlineImageView.setBackgroundResource(R.drawable.style_category_list_item_bg);
        onlineImageView.setOutlineProvider(new RoundOutlineProvider(this.mContext.getResources().getDimensionPixelSize(R.dimen.aod_style_bg_radius)));
        return onlineViewHolder;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        Glide.with(this.mContext).clear(viewHolder.itemView);
        super.onViewRecycled(viewHolder);
    }
}
