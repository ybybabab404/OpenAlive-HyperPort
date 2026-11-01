package com.miui.aod.category;

import android.content.Context;
import android.view.ViewGroup;
import com.google.gson.annotations.SerializedName;
import com.miui.aod.Utils;
import com.miui.aod.widget.BigTimeClock;
import com.miui.aod.widget.IAodClock;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: BigTimeCategoryInfo.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class BigTimeCategoryInfo extends CategoryInfo {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    public static final String STYLE_1 = "clock_time_style_1";

    @NotNull
    public static final String STYLE_2 = "clock_time_style_2";

    @NotNull
    public static final String STYLE_3 = "clock_time_style_3";

    @NotNull
    public static final String STYLE_4 = "clock_time_style_4";

    @NotNull
    public static final String STYLE_5 = "clock_time_style_5";

    @NotNull
    public static final String STYLE_6 = "clock_time_style_6";

    @NotNull
    public static final String STYLE_7 = "clock_time_style_7";

    @NotNull
    public static final String STYLE_8 = "clock_time_style_8";
    public static final int UPDATE_POSITION_INTERVAL_IN_MILLIS = 30000;
    private int alpha;

    @SerializedName("title")
    @NotNull
    private String title;

    /* JADX INFO: compiled from: BigTimeCategoryInfo.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BigTimeCategoryInfo() {
        super(CategoryConstants.CATEGORY_BIG_TIME);
        this.title = "";
        this.alpha = 76;
    }

    @Override // com.miui.aod.common.StyleInfo
    @NotNull
    public IAodClock createAodClock(boolean z, int i) {
        return new BigTimeClock(z, i);
    }

    @Override // com.miui.aod.common.StyleInfo
    public void dealAodContainerLayoutParam(@Nullable ViewGroup.LayoutParams layoutParams, int i, @Nullable Context context, boolean z) {
        if (layoutParams == null || i != 3) {
            return;
        }
        layoutParams.width = -1;
        layoutParams.height = -1;
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(0, 0, 0, 0);
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public void dealAodContentContainerLayoutParams(@Nullable ViewGroup.LayoutParams layoutParams, int i) {
        if (layoutParams != null) {
            layoutParams.width = -1;
            layoutParams.height = -1;
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public void dealClockContentContainerLayoutParams(@Nullable ViewGroup.LayoutParams layoutParams, int i) {
        if (layoutParams != null) {
            layoutParams.width = -1;
            layoutParams.height = -1;
        }
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean dealNotificationWidgetLayoutParams() {
        return true;
    }

    public final int getAlpha() {
        return this.alpha;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean isLeftGravity() {
        return true;
    }

    public final boolean isPreviewEnable(@Nullable Context context) {
        return Utils.isAodEnable(context);
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean needInflateNotification() {
        return false;
    }

    public final void setAlpha(int i) {
        this.alpha = i;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    @Override // com.miui.aod.common.StyleInfo
    public boolean supportDateAndTime() {
        return true;
    }
}
