package androidx.window.embedding;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.WindowMetrics;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

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
/* JADX INFO: compiled from: SplitRule.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public class SplitRule extends EmbeddingRule {
    public final SplitAttributes defaultSplitAttributes;
    public final EmbeddingAspectRatio maxAspectRatioInLandscape;
    public final EmbeddingAspectRatio maxAspectRatioInPortrait;
    public final int minHeightDp;
    public final int minSmallestWidthDp;
    public final int minWidthDp;
    public static final Companion Companion = new Companion(null);
    public static final EmbeddingAspectRatio SPLIT_MAX_ASPECT_RATIO_PORTRAIT_DEFAULT = EmbeddingAspectRatio.Companion.ratio(1.4f);
    public static final EmbeddingAspectRatio SPLIT_MAX_ASPECT_RATIO_LANDSCAPE_DEFAULT = EmbeddingAspectRatio.ALWAYS_ALLOW;

    /* JADX INFO: compiled from: SplitRule.kt */
    @RequiresApi
    @Metadata
    public static final class Api30Impl {
        public static final Api30Impl INSTANCE = new Api30Impl();

        @DoNotInline
        @NotNull
        public final Rect getBounds(@NotNull WindowMetrics windowMetrics) {
            Intrinsics.checkNotNullParameter(windowMetrics, "windowMetrics");
            Rect bounds = windowMetrics.getBounds();
            Intrinsics.checkNotNullExpressionValue(bounds, "windowMetrics.bounds");
            return bounds;
        }
    }

    /* JADX INFO: compiled from: SplitRule.kt */
    @RequiresApi
    @Metadata
    public static final class Api34Impl {
        public static final Api34Impl INSTANCE = new Api34Impl();

        @DoNotInline
        public final float getDensity(@NotNull WindowMetrics windowMetrics, @NotNull Context context) {
            Intrinsics.checkNotNullParameter(windowMetrics, "windowMetrics");
            Intrinsics.checkNotNullParameter(context, "context");
            try {
                return windowMetrics.getDensity();
            } catch (NoSuchMethodError unused) {
                return context.getResources().getDisplayMetrics().density;
            }
        }
    }

    /* JADX INFO: compiled from: SplitRule.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: SplitRule.kt */
    @Metadata
    public static final class FinishBehavior {
        public final String description;
        public final int value;
        public static final Companion Companion = new Companion(null);
        public static final FinishBehavior NEVER = new FinishBehavior("NEVER", 0);
        public static final FinishBehavior ALWAYS = new FinishBehavior("ALWAYS", 1);
        public static final FinishBehavior ADJACENT = new FinishBehavior("ADJACENT", 2);

        /* JADX INFO: compiled from: SplitRule.kt */
        @Metadata
        public static final class Companion {
            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public FinishBehavior(String str, int i) {
            this.description = str;
            this.value = i;
        }

        public String toString() {
            return this.description;
        }
    }

    public final boolean checkParentBounds$window_release(float f, Rect bounds) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        int iWidth = bounds.width();
        int iHeight = bounds.height();
        if (iWidth != 0 && iHeight != 0) {
            int iConvertDpToPx = convertDpToPx(f, this.minWidthDp);
            int iConvertDpToPx2 = convertDpToPx(f, this.minHeightDp);
            int iConvertDpToPx3 = convertDpToPx(f, this.minSmallestWidthDp);
            boolean z = this.minWidthDp == 0 || iWidth >= iConvertDpToPx;
            boolean z2 = this.minHeightDp == 0 || iHeight >= iConvertDpToPx2;
            boolean z3 = this.minSmallestWidthDp == 0 || Math.min(iWidth, iHeight) >= iConvertDpToPx3;
            boolean z4 = iHeight < iWidth ? Intrinsics.areEqual(this.maxAspectRatioInLandscape, EmbeddingAspectRatio.ALWAYS_ALLOW) || (((float) iWidth) * 1.0f) / ((float) iHeight) <= this.maxAspectRatioInLandscape.getValue$window_release() : Intrinsics.areEqual(this.maxAspectRatioInPortrait, EmbeddingAspectRatio.ALWAYS_ALLOW) || (((float) iHeight) * 1.0f) / ((float) iWidth) <= this.maxAspectRatioInPortrait.getValue$window_release();
            if (z && z2 && z3 && z4) {
                return true;
            }
        }
        return false;
    }

    public final boolean checkParentMetrics$window_release(Context context, WindowMetrics parentMetrics) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(parentMetrics, "parentMetrics");
        int i = Build.VERSION.SDK_INT;
        return checkParentBounds$window_release(i <= 33 ? context.getResources().getDisplayMetrics().density : Api34Impl.INSTANCE.getDensity(parentMetrics, context), Api30Impl.INSTANCE.getBounds(parentMetrics));
    }

    public final int convertDpToPx(float f, int i) {
        return (int) ((i * f) + 0.5f);
    }

    @Override // androidx.window.embedding.EmbeddingRule
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SplitRule) || !super.equals(obj)) {
            return false;
        }
        SplitRule splitRule = (SplitRule) obj;
        return this.minWidthDp == splitRule.minWidthDp && this.minHeightDp == splitRule.minHeightDp && this.minSmallestWidthDp == splitRule.minSmallestWidthDp && Intrinsics.areEqual(this.maxAspectRatioInPortrait, splitRule.maxAspectRatioInPortrait) && Intrinsics.areEqual(this.maxAspectRatioInLandscape, splitRule.maxAspectRatioInLandscape) && Intrinsics.areEqual(this.defaultSplitAttributes, splitRule.defaultSplitAttributes);
    }

    public final SplitAttributes getDefaultSplitAttributes() {
        return this.defaultSplitAttributes;
    }

    public final EmbeddingAspectRatio getMaxAspectRatioInLandscape() {
        return this.maxAspectRatioInLandscape;
    }

    public final EmbeddingAspectRatio getMaxAspectRatioInPortrait() {
        return this.maxAspectRatioInPortrait;
    }

    public final int getMinHeightDp() {
        return this.minHeightDp;
    }

    public final int getMinSmallestWidthDp() {
        return this.minSmallestWidthDp;
    }

    public final int getMinWidthDp() {
        return this.minWidthDp;
    }

    @Override // androidx.window.embedding.EmbeddingRule
    public int hashCode() {
        return (((((((((((super.hashCode() * 31) + this.minWidthDp) * 31) + this.minHeightDp) * 31) + this.minSmallestWidthDp) * 31) + this.maxAspectRatioInPortrait.hashCode()) * 31) + this.maxAspectRatioInLandscape.hashCode()) * 31) + this.defaultSplitAttributes.hashCode();
    }

    public String toString() {
        return SplitRule.class.getSimpleName() + "{ tag=" + getTag() + ", defaultSplitAttributes=" + this.defaultSplitAttributes + ", minWidthDp=" + this.minWidthDp + ", minHeightDp=" + this.minHeightDp + ", minSmallestWidthDp=" + this.minSmallestWidthDp + ", maxAspectRatioInPortrait=" + this.maxAspectRatioInPortrait + ", maxAspectRatioInLandscape=" + this.maxAspectRatioInLandscape + '}';
    }
}
