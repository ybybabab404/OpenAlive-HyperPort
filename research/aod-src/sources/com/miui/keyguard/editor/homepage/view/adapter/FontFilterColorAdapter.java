package com.miui.keyguard.editor.homepage.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TransitionKt;
import com.miui.keyguard.editor.data.preset.FilterColor;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.data.preset.FontFilterSelectInfo;
import com.miui.keyguard.editor.view.ColorSelectView;
import com.miui.keyguard.editor.view.FontColorSelectItemCallback;
import com.miui.keyguard.editor.view.FontFilterViewStatus;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: AdapterAndHolderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FontFilterColorAdapter extends RecyclerView.Adapter<FilterColorViewHolder> {
    public static final Companion Companion = new Companion(null);
    public static final Integer[] fontFilterColorArray = {0, -1, -5066062, -146496, -11596, -7500, -2894, -2099533, -4460348, -4722705, -4007425, -2698755, -473377, -16777216};
    public FilterColor[] colorData;
    public Map colorDefines;
    public final Context context;
    public final FontFilterSelectInfo currentSelectFilter;
    public final FontFilterViewStatus filterViewStatus;
    public boolean isSelectChanged;
    public int lastSelectColorId;
    public final FontColorSelectItemCallback listener;
    public final int targetIndex;

    /* JADX INFO: compiled from: AdapterAndHolderImpl.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Integer[] getFontFilterColorArray() {
            return FontFilterColorAdapter.fontFilterColorArray;
        }
    }

    public FontFilterColorAdapter(int i, FontFilterSelectInfo currentSelectFilter, FontFilterViewStatus filterViewStatus, FontColorSelectItemCallback listener, Context context) {
        Intrinsics.checkNotNullParameter(currentSelectFilter, "currentSelectFilter");
        Intrinsics.checkNotNullParameter(filterViewStatus, "filterViewStatus");
        Intrinsics.checkNotNullParameter(listener, "listener");
        Intrinsics.checkNotNullParameter(context, "context");
        this.targetIndex = i;
        this.currentSelectFilter = currentSelectFilter;
        this.filterViewStatus = filterViewStatus;
        this.listener = listener;
        this.context = context;
        this.colorData = currentSelectFilter.getCurrentColorData(i);
        this.lastSelectColorId = currentSelectFilter.getCurrentColorId(i);
        this.isSelectChanged = true;
        this.colorDefines = MapsKt.mutableMapOf(TuplesKt.to(context.getString(R.string.kg_font_filter_color_default), 0), TuplesKt.to(context.getString(R.string.kg_font_filter_color_white), -1), TuplesKt.to(context.getString(R.string.kg_font_filter_color_grey), -5066062), TuplesKt.to(context.getString(R.string.kg_font_filter_color_pale_red), -146496), TuplesKt.to(context.getString(R.string.kg_font_filter_color_pale_orange_red), -11596), TuplesKt.to(context.getString(R.string.kg_font_filter_color_pale_orange), -7500), TuplesKt.to(context.getString(R.string.kg_font_filter_color_pale_yellow), -2894), TuplesKt.to(context.getString(R.string.kg_font_filter_color_light_green), -2099533), TuplesKt.to(context.getString(R.string.kg_font_filter_color_pale_green), -4460348), TuplesKt.to(context.getString(R.string.kg_font_filter_color_pale_blue), -4722705), TuplesKt.to(context.getString(R.string.kg_font_filter_color_pale_purple), -4007425), TuplesKt.to(context.getString(R.string.kg_font_filter_color_pale_pink_purple), -2698755), TuplesKt.to(context.getString(R.string.kg_font_filter_color_pale_pink), -473377), TuplesKt.to(context.getString(R.string.kg_font_filter_color_customize), -16777216));
    }

    public static final void onBindViewHolder$lambda$0(FontFilterColorAdapter fontFilterColorAdapter, int i, ColorSelectView colorSelectView, FilterColor filterColor, View view) {
        if (fontFilterColorAdapter.filterViewStatus.getColorLightAnimStatus() > 0) {
            return;
        }
        fontFilterColorAdapter.listener.onClickItem(i, colorSelectView);
        if (fontFilterColorAdapter.lastSelectColorId == filterColor.getColorId()) {
            fontFilterColorAdapter.isSelectChanged = false;
        } else {
            fontFilterColorAdapter.isSelectChanged = true;
            fontFilterColorAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.colorData.length;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(FilterColorViewHolder holder, final int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        View itemView = holder.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        final ColorSelectView colorSelectView = (ColorSelectView) itemView.findViewById(R.id.color_select_view);
        int currentColorId = this.currentSelectFilter.getCurrentColorId(this.targetIndex);
        final FilterColor filterColor = this.colorData[i];
        boolean supportSelect = filterColor.getSupportSelect();
        int colorType = filterColor.getColorType();
        if (colorType == 0) {
            colorSelectView.setColorSource(filterColor.getColorDrawable(), ColorSelectView.ColorSource.AUTO_PICK);
        } else if (colorType == 1) {
            colorSelectView.setColor(filterColor.getColor());
        } else if (colorType == 2) {
            colorSelectView.setColorSource(filterColor.getColorDrawable(), ColorSelectView.ColorSource.HSL);
            if (currentColorId == filterColor.getColorId() || filterColor.getColor() != FontFilterKt.getDEFAULT_FILTER_COLORS()[13].getColor()) {
                colorSelectView.setHslSolidColor(Integer.valueOf(filterColor.getColor()), true);
            } else {
                colorSelectView.setHslSolidColor(null, true);
            }
        }
        if (currentColorId == filterColor.getColorId()) {
            colorSelectView.changeSelect(true);
            if (this.isSelectChanged) {
                this.listener.onSelectItem(i, colorSelectView);
                this.isSelectChanged = false;
            }
        } else if (this.lastSelectColorId == filterColor.getColorId()) {
            colorSelectView.changeSelect(false);
            this.lastSelectColorId = currentColorId;
        } else {
            colorSelectView.changeSelect(false);
        }
        if (supportSelect) {
            colorSelectView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.view.adapter.FontFilterColorAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FontFilterColorAdapter.onBindViewHolder$lambda$0(this.f$0, i, colorSelectView, filterColor, view);
                }
            });
            colorSelectView.setEnabled(true);
            colorSelectView.setAlpha(1.0f);
        } else {
            colorSelectView.setEnabled(false);
            colorSelectView.setAlpha(0.5f);
        }
        if (colorSelectView == null || this.colorDefines.size() <= i || i < 0) {
            return;
        }
        colorSelectView.setContentDescription((CharSequence) CollectionsKt.toList(this.colorDefines.keySet()).get(i));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public FilterColorViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.kg_font_filter_color_item, parent, false);
        ColorSelectView colorSelectView = (ColorSelectView) viewInflate.findViewById(R.id.color_select_view);
        Intrinsics.checkNotNull(colorSelectView);
        TransitionKt.doTintAnim(colorSelectView);
        Intrinsics.checkNotNull(viewInflate);
        return new FilterColorViewHolder(viewInflate);
    }
}
