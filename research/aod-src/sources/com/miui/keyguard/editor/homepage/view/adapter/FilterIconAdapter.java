package com.miui.keyguard.editor.homepage.view.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TransitionKt;
import com.miui.keyguard.editor.data.preset.WallpaperFilter;
import com.miui.keyguard.editor.view.FilterIconLayout;
import com.miui.keyguard.editor.view.WallpaperFilterSelectItemCallback;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AdapterAndHolderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FilterIconAdapter extends RecyclerView.Adapter<FilterIconViewHolder> {
    public final Typeface boldFont;
    public int currentIcon;
    public final List data;
    public CharSequence headTitle;
    public int itemWidth;
    public int lastIconResId;
    public int lastTitleResId;
    public final WallpaperFilterSelectItemCallback listener;
    public boolean needSelectAnim;
    public boolean needUnselectAnim;
    public final Typeface normalFont;
    public final int selectColor;
    public final int unselectColor;

    public FilterIconAdapter(List data, int i, int i2, int i3, WallpaperFilterSelectItemCallback listener, String boldFontName, String normalFontName, CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(listener, "listener");
        Intrinsics.checkNotNullParameter(boldFontName, "boldFontName");
        Intrinsics.checkNotNullParameter(normalFontName, "normalFontName");
        this.data = data;
        this.currentIcon = i;
        this.selectColor = i2;
        this.unselectColor = i3;
        this.listener = listener;
        this.headTitle = charSequence;
        this.lastIconResId = -1;
        this.lastTitleResId = -1;
        this.needSelectAnim = true;
        this.needUnselectAnim = true;
        Typeface typefaceCreate = Typeface.create(boldFontName, 1);
        Intrinsics.checkNotNullExpressionValue(typefaceCreate, "create(...)");
        this.boldFont = typefaceCreate;
        Typeface typefaceCreate2 = Typeface.create(normalFontName, 0);
        Intrinsics.checkNotNullExpressionValue(typefaceCreate2, "create(...)");
        this.normalFont = typefaceCreate2;
    }

    public /* synthetic */ FilterIconAdapter(List list, int i, int i2, int i3, WallpaperFilterSelectItemCallback wallpaperFilterSelectItemCallback, String str, String str2, CharSequence charSequence, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, i, i2, i3, wallpaperFilterSelectItemCallback, (i4 & 32) != 0 ? "mipro-demibold" : str, (i4 & 64) != 0 ? "mipro-medium" : str2, (i4 & 128) != 0 ? null : charSequence);
    }

    public static final void onBindViewHolder$lambda$1(int i, FilterIconAdapter filterIconAdapter, View view) {
        int i2 = filterIconAdapter.currentIcon;
        if (i != i2) {
            filterIconAdapter.lastIconResId = ((WallpaperFilter) filterIconAdapter.data.get(i2)).getIconsResId();
            filterIconAdapter.currentIcon = i;
            filterIconAdapter.notifyDataSetChanged();
            WallpaperFilterSelectItemCallback.DefaultImpls.onSelectItem$default(filterIconAdapter.listener, i, view, false, 4, null);
        }
    }

    public static final void onBindViewHolder$lambda$2(FilterIconAdapter filterIconAdapter, View view) {
        filterIconAdapter.itemWidth = view.getWidth();
    }

    public static /* synthetic */ void performOnSelectItem$default(FilterIconAdapter filterIconAdapter, int i, boolean z, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        filterIconAdapter.performOnSelectItem(i, z, z2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.data.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(FilterIconViewHolder holder, final int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        holder.setIsRecyclable(false);
        final View itemView = holder.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        FilterIconLayout filterIconLayout = (FilterIconLayout) itemView.findViewById(R.id.filter_icon_container);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.filter_icon);
        TextView textView = (TextView) itemView.findViewById(R.id.filter_icon_name);
        int iconsResId = ((WallpaperFilter) this.data.get(i)).getIconsResId();
        filterIconLayout.setSelected(false);
        imageView.setImageResource(iconsResId);
        if (i == this.currentIcon) {
            itemView.setSelected(true);
            filterIconLayout.changeSelect(true, this.needSelectAnim);
            this.needSelectAnim = true;
        } else if (this.lastIconResId == ((WallpaperFilter) this.data.get(i)).getIconsResId()) {
            itemView.setSelected(false);
            filterIconLayout.changeSelect(false, this.needUnselectAnim);
            this.needUnselectAnim = true;
            this.lastIconResId = -1;
        }
        textView.setText(((WallpaperFilter) this.data.get(i)).getIconNameResId());
        if (this.headTitle != null) {
            CharSequence text = textView.getText();
            CharSequence charSequence = this.headTitle;
            StringBuilder sb = new StringBuilder();
            sb.append((Object) text);
            sb.append((Object) charSequence);
            itemView.setContentDescription(sb.toString());
        }
        textView.setTypeface(this.normalFont);
        textView.setTextColor(this.unselectColor);
        if (i == this.currentIcon) {
            textView.setTypeface(this.boldFont);
            textView.setTextColor(this.selectColor);
        }
        itemView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.view.adapter.FilterIconAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FilterIconAdapter.onBindViewHolder$lambda$1(i, this, view);
            }
        });
        if (this.itemWidth == 0) {
            itemView.post(new Runnable() { // from class: com.miui.keyguard.editor.homepage.view.adapter.FilterIconAdapter$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FilterIconAdapter.onBindViewHolder$lambda$2(this.f$0, itemView);
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public FilterIconViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.kg_filter_icon_item, parent, false);
        Intrinsics.checkNotNull(viewInflate);
        TransitionKt.doTintAnim(viewInflate);
        this.listener.onCreatedItem(i, viewInflate);
        return new FilterIconViewHolder(viewInflate);
    }

    public final void onDragList() {
        this.lastIconResId = -1;
        this.lastTitleResId = -1;
    }

    public final void performOnSelectItem(int i, boolean z, boolean z2) {
        this.currentIcon = i;
        this.needSelectAnim = z2;
        this.needUnselectAnim = z2;
        notifyDataSetChanged();
        this.listener.onSelectItem(i, null, z);
    }

    public final void setCurrentIcon(int i) {
        if (i != this.currentIcon) {
            this.lastIconResId = ((WallpaperFilter) this.data.get(i)).getIconsResId();
            this.currentIcon = i;
        }
    }
}
