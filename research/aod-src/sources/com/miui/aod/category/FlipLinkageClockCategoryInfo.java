package com.miui.aod.category;

import android.content.Context;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import com.google.gson.Gson;
import com.miui.aod.flip.FlipClockInfo;
import com.miui.aod.flip.FlipLinkageTemplateId;
import com.miui.aod.widget.FlipLinkageClock;
import com.miui.aod.widget.IAodClock;
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
/* JADX INFO: compiled from: FlipLinkageClockCategoryInfo.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class FlipLinkageClockCategoryInfo extends LinkageClockCategoryInfo {

    @NotNull
    private FlipClockInfo clockinfo;

    /* JADX WARN: Multi-variable type inference failed */
    public FlipLinkageClockCategoryInfo() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlipLinkageClockCategoryInfo(@NotNull String name) {
        super(name);
        Intrinsics.checkNotNullParameter(name, "name");
        Object objFromJson = new Gson().fromJson(FlipLinkageClockCategoryInfoKt.getClockInfoString(), (Class<Object>) FlipClockInfo.class);
        Intrinsics.checkNotNullExpressionValue(objFromJson, "fromJson(...)");
        this.clockinfo = (FlipClockInfo) objFromJson;
    }

    public /* synthetic */ FlipLinkageClockCategoryInfo(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CategoryConstants.CATEGORY_LINKAGE_FLIP : str);
    }

    @Override // com.miui.aod.category.LinkageClockCategoryInfo, com.miui.aod.common.StyleInfo
    @NotNull
    public IAodClock createAodClock(boolean z, int i) {
        return new FlipLinkageClock(i);
    }

    @Override // com.miui.aod.category.LinkageClockCategoryInfo, com.miui.aod.common.StyleInfo
    public void dealAodContainerLayoutParam(@Nullable ViewGroup.LayoutParams layoutParams, int i, @NotNull Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.dealAodContainerLayoutParam(layoutParams, i, context, z);
        if (i == 3 && (layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginLayoutParams.width = -1;
            marginLayoutParams.height = -1;
            marginLayoutParams.setMargins(0, 0, 0, 0);
        }
    }

    @Override // com.miui.aod.category.LinkageClockCategoryInfo, com.miui.aod.common.StyleInfo
    public void dealAodContentContainerLayoutParams(@NotNull ViewGroup.LayoutParams layoutParams, int i) {
        Intrinsics.checkNotNullParameter(layoutParams, "layoutParams");
        layoutParams.height = -1;
        layoutParams.width = -1;
    }

    @Override // com.miui.aod.category.LinkageClockCategoryInfo, com.miui.aod.common.StyleInfo
    public void dealClockContentContainerLayoutParams(@NotNull ViewGroup.LayoutParams layoutParams, int i) {
        Intrinsics.checkNotNullParameter(layoutParams, "layoutParams");
        layoutParams.height = -1;
        layoutParams.width = -1;
    }

    @NotNull
    public final FlipClockInfo getClockInfo() {
        return this.clockinfo;
    }

    @Override // com.miui.aod.category.LinkageClockCategoryInfo, com.miui.aod.common.StyleInfo
    @NotNull
    public Map<String, Object> getParameterForAnalytic() {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("style_name", "flip_linkage");
        arrayMap.put("style_event", "flip_linkage");
        return arrayMap;
    }

    @Override // com.miui.aod.category.LinkageClockCategoryInfo, com.miui.aod.common.StyleInfo
    @NotNull
    public String getStyleSelectViewName() {
        String name = FlipLinkageClockCategoryInfo.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return name;
    }

    @NotNull
    public final FlipLinkageTemplateId getTemplate() {
        return this.clockinfo.getTemplate();
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isChangeColorAlongTime() {
        return false;
    }
}
