package com.miui.aod.category;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.collection.ArrayMap;
import com.miui.aod.R;
import com.miui.aod.components.view.LinkageStyleSelectView;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.LinkageClock;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: LinkageClockCategoryInfo.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public class LinkageClockCategoryInfo extends CategoryInfo {
    /* JADX WARN: Multi-variable type inference failed */
    public LinkageClockCategoryInfo() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LinkageClockCategoryInfo(@NotNull String name) {
        super(name);
        Intrinsics.checkNotNullParameter(name, "name");
    }

    public /* synthetic */ LinkageClockCategoryInfo(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CategoryConstants.CATEGORY_LINKAGE : str);
    }

    @Override // com.miui.aod.common.StyleInfo
    @NotNull
    public IAodClock createAodClock(boolean z, int i) {
        return new LinkageClock(i);
    }

    @Override // com.miui.aod.common.StyleInfo
    public void dealAodContainerLayoutParam(@Nullable ViewGroup.LayoutParams layoutParams, int i, @NotNull Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (i == 1 || i == 3 || i == 5) {
            boolean z2 = layoutParams instanceof FrameLayout.LayoutParams;
            if (z2) {
                ((FrameLayout.LayoutParams) layoutParams).gravity = getClockGravity();
            }
            if (layoutParams != null) {
                layoutParams.height = -2;
            }
            if (layoutParams != null) {
                layoutParams.width = -1;
            }
            if (i == 1 && z2) {
                ((FrameLayout.LayoutParams) layoutParams).topMargin = -context.getResources().getDimensionPixelSize(R.dimen.linkage_large_top_margin);
            }
            if (i == 3) {
                Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                ((FrameLayout.LayoutParams) layoutParams).gravity = 48;
            }
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public void dealAodContentContainerLayoutParams(@NotNull ViewGroup.LayoutParams layoutParams, int i) {
        Intrinsics.checkNotNullParameter(layoutParams, "layoutParams");
        layoutParams.height = -2;
        layoutParams.width = -1;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void dealClockContentContainerLayoutParams(@NotNull ViewGroup.LayoutParams layoutParams, int i) {
        Intrinsics.checkNotNullParameter(layoutParams, "layoutParams");
        layoutParams.height = -2;
        layoutParams.width = -1;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void dealContentLayoutParams(@NotNull ViewGroup.LayoutParams layoutParams, int i) {
        Intrinsics.checkNotNullParameter(layoutParams, "layoutParams");
        if (i != 4) {
            layoutParams.height = -2;
            layoutParams.width = -1;
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    @NotNull
    public Map<String, Object> getParameterForAnalytic() {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("style_name", "linkage");
        arrayMap.put("style_event", "linkage");
        return arrayMap;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getPreviewTopPaddingResId() {
        return 0;
    }

    @Override // com.miui.aod.common.StyleInfo
    @NotNull
    public String getStyleSelectViewName() {
        String name = LinkageStyleSelectView.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return name;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTitleResId() {
        return R.string.linkage_select_title;
    }

    @Override // com.miui.aod.common.StyleInfo
    public int getTopMargin(@Nullable Context context) {
        return 0;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean needInflateBattery(@Nullable Context context) {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean needInflateNotification() {
        return false;
    }

    @Override // com.miui.aod.common.StyleInfo
    public void setBg(@NotNull View aodView, int i) {
        Intrinsics.checkNotNullParameter(aodView, "aodView");
        View viewFindViewById = aodView.findViewById(R.id.aod_bg);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(8);
        }
    }
}
