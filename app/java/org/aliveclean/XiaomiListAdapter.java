package org.aliveclean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.function.BiConsumer;

/**
 * 给小米主题管理器风格列表用的最简 RecyclerView.Adapter：按固定布局 inflate 列表项，
 * 把绑定工作交给外部的 BiConsumer。
 *
 * <p>类和构造签名（Context,int,int,BiConsumer&lt;View,Integer&gt;）必须保持 public 且不能改，
 * 因为 XiaomiLibraryActivity 会在小米 UI 的 ClassLoader 里按这个签名反射构造它，
 * 再当作 RecyclerView$Adapter 交回给小米的列表控件。</p>
 */
public final class XiaomiListAdapter extends RecyclerView.Adapter<XiaomiListAdapter.Holder> {
    private final Context context;
    private final int layout,count;
    private final BiConsumer<View,Integer> binder;
    public XiaomiListAdapter(Context context,int layout,int count,BiConsumer<View,Integer> binder){this.context=context;this.layout=layout;this.count=count;this.binder=binder;}
    public static final class Holder extends RecyclerView.ViewHolder {public Holder(View view){super(view);}}
    @Override public Holder onCreateViewHolder(ViewGroup parent,int type){return new Holder(LayoutInflater.from(context).inflate(layout,parent,false));}
    @Override public void onBindViewHolder(Holder holder,int position){binder.accept(holder.itemView,position);}
    @Override public int getItemCount(){return count;}
}
