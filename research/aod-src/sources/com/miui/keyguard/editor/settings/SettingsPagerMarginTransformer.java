package com.miui.keyguard.editor.settings;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: SettingsPagerMarginTransformer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SettingsPagerMarginTransformer implements ViewPager2.PageTransformer {
    public final String _tag;
    public final Context context;
    public float minScale;
    public RecyclerView recyclerViewInViewPager;

    public SettingsPagerMarginTransformer(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this._tag = "Keyguard-Editor-SettingsPagerMarginTransformer";
        this.minScale = 0.9f;
    }

    private final ViewPager2 requireViewPager(View view) {
        ViewParent parent = view.getParent();
        ViewParent parent2 = parent.getParent();
        if ((parent instanceof RecyclerView) && (parent2 instanceof ViewPager2)) {
            return (ViewPager2) parent2;
        }
        return null;
    }

    private final void transformPageHorizontally(View view, View view2, float f, int i) {
        float viewScaleWidth;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        boolean zIsRtl = deviceUtil.isRtl(context);
        float width = (view2.getWidth() * (1 - this.minScale)) / 2;
        SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
        Context context2 = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        float fProvideSettingsTemplateSpacing = width - settingsResourceProvider.provideSettingsTemplateSpacing(context2);
        RecyclerView recyclerView = this.recyclerViewInViewPager;
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = recyclerView != null ? recyclerView.findViewHolderForAdapterPosition(i + 1) : null;
        RecyclerView recyclerView2 = this.recyclerViewInViewPager;
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition2 = recyclerView2 != null ? recyclerView2.findViewHolderForAdapterPosition(i - 1) : null;
        if (f < -1.0f) {
            viewScaleWidth = -getViewScaleWidth(i + 1, viewHolderFindViewHolderForAdapterPosition);
        } else {
            viewScaleWidth = f > 1.0f ? getViewScaleWidth(i - 1, viewHolderFindViewHolderForAdapterPosition2) : 0.0f;
        }
        float f2 = (fProvideSettingsTemplateSpacing * f) + viewScaleWidth;
        Log.d(this._tag, "item position: " + i + " position: " + f + "  adjustPixels:" + viewScaleWidth + " translateX:" + fProvideSettingsTemplateSpacing + "  offset:" + f2 + "  right position: " + (i + 1));
        if (!zIsRtl) {
            f2 = -f2;
        }
        view.setTranslationX(f2);
    }

    public final float getViewScaleWidth(int i, RecyclerView.ViewHolder viewHolder) {
        BaseViewHolder baseViewHolder;
        View targetContentView;
        if (i >= 0 && (viewHolder instanceof BaseViewHolder) && (targetContentView = (baseViewHolder = (BaseViewHolder) viewHolder).getTargetContentView()) != null) {
            return ((1 - baseViewHolder.itemView.getScaleX()) * targetContentView.getWidth()) / 2;
        }
        return 0.0f;
    }

    @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.PageTransformer
    public void transformPage(View page, float f) {
        ViewPager2 viewPager2RequireViewPager;
        Intrinsics.checkNotNullParameter(page, "page");
        SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
        Context context = page.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.minScale = settingsResourceProvider.provideTemplateScale(context);
        Object tag = page.getTag(R.id.kg_template_holder);
        if (!(tag instanceof BaseViewHolder)) {
            Log.w(this._tag, "transformPageHorizontally failed: invalid pageHolder.");
            return;
        }
        BaseViewHolder baseViewHolder = (BaseViewHolder) tag;
        View targetContentView = baseViewHolder.getTargetContentView();
        if (targetContentView == null || (viewPager2RequireViewPager = requireViewPager(page)) == null || viewPager2RequireViewPager.getOrientation() != 0) {
            return;
        }
        View childAt = viewPager2RequireViewPager.getChildAt(0);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView");
        this.recyclerViewInViewPager = (RecyclerView) childAt;
        transformPageHorizontally(page, targetContentView, f, baseViewHolder.getLayoutPosition());
    }
}
