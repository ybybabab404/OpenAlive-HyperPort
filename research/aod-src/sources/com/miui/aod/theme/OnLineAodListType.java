package com.miui.aod.theme;

import android.content.Context;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.aod.stylelist.StyleListViewModel;
import com.miui.aod.theme.beans.OnLineAodBean;
import com.miui.maml.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class OnLineAodListType implements StyleListViewModel.StyleListType {
    public final OnLineAodBean mOnLineAodBean;

    public OnLineAodListType(OnLineAodBean onLineAodBean) {
        this.mOnLineAodBean = onLineAodBean;
    }

    public static StyleListViewModel.StyleListType createStyleListType(OnLineAodBean onLineAodBean) {
        return new OnLineAodListType(onLineAodBean);
    }

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public RecyclerView.Adapter createAdapter(Context context, LifecycleOwner lifecycleOwner) {
        return new OnLineStyleAdapter(context, this.mOnLineAodBean);
    }

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public String getListType() {
        return this.mOnLineAodBean.getTitle();
    }

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public String getValueRight() {
        List<OnLineAodBean.ProductsBean> products = this.mOnLineAodBean.getProducts();
        return String.valueOf(products == null ? 0 : products.size());
    }

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public int getViewType() {
        return R.styleable.AppCompatTheme_windowFixedWidthMinor;
    }
}
