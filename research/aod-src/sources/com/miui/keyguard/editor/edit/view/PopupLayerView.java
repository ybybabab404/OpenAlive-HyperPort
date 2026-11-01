package com.miui.keyguard.editor.edit.view;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: PopupLayerView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class PopupLayerView extends FrameLayout {
    public static final Companion Companion = new Companion(null);
    public static final List MUTABLE_HEIGHT_VIEW_IDS = CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(R.id.signature_editor_container), Integer.valueOf(R.id.dual_color_selector_root_view), Integer.valueOf(R.id.single_color_selector_root_view)});
    public View anchorView;
    public PopupConfig config;
    public OnPopupListener popupListener;
    public View popupView;

    /* JADX INFO: compiled from: PopupLayerView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: PopupLayerView.kt */
    @Metadata
    public interface OnPopupListener {
    }

    /* JADX INFO: compiled from: PopupLayerView.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Gravity.values().length];
            try {
                iArr[Gravity.ABOVE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Gravity.TOP_TO_TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Gravity.CENTER_WITH_ANCHOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Gravity.START_TO_START.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[Gravity.END_TO_END.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[Gravity.START_TO_END.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[Gravity.END_TO_START.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupLayerView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        setBackground(null);
        setClipChildren(false);
    }

    public static /* synthetic */ void updateLayout$default(PopupLayerView popupLayerView, PopupConfig popupConfig, int i, Object obj) {
        if ((i & 1) != 0) {
            popupConfig = null;
        }
        popupLayerView.updateLayout(popupConfig);
    }

    public final int layoutLeft(PopupConfig popupConfig, int i, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[popupConfig.getHorizontalGravity().ordinal()];
        if (i2 == 3) {
            return (iArr4[0] - iArr[0]) + ((iArr3[0] - iArr2[0]) / 2);
        }
        if (i2 == 4) {
            return iArr4[0] - iArr[0];
        }
        if (i2 == 5) {
            return (iArr4[0] + iArr3[0]) - iArr2[0];
        }
        if (i2 != 6) {
            return i2 != 7 ? (getWidth() - iArr2[0]) / 2 : ((iArr4[0] - iArr[0]) - iArr2[0]) - i;
        }
        return iArr4[0] + popupConfig.getHorizontalOffset() + i + iArr3[0];
    }

    public final int layoutTop(PopupConfig popupConfig, int i, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
        int i2;
        int verticalOffset = popupConfig.getVerticalOffset();
        int i3 = WhenMappings.$EnumSwitchMapping$0[popupConfig.getVerticalGravity().ordinal()];
        if (i3 != 1) {
            i2 = i3 != 2 ? (iArr4[1] - iArr[1]) + iArr3[1] + i : iArr4[1];
        } else {
            i2 = ((iArr4[1] - iArr[1]) - iArr2[1]) - i;
        }
        return verticalOffset + i2;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getRight() <= 0 && childAt.getBottom() <= 0) {
                childAt.layout(0, 0, childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
            } else if (childAt.getHeight() == childAt.getMeasuredHeight() || !MUTABLE_HEIGHT_VIEW_IDS.contains(Integer.valueOf(childAt.getId()))) {
                childAt.layout(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom());
            } else {
                childAt.layout(childAt.getLeft(), childAt.getBottom() - childAt.getMeasuredHeight(), childAt.getRight(), childAt.getBottom());
            }
        }
    }

    public final void setPopupListener(@Nullable OnPopupListener onPopupListener) {
        this.popupListener = onPopupListener;
    }

    public final void updateLayout(View view, View view2, PopupConfig popupConfig) {
        if (view == null || popupConfig == null) {
            return;
        }
        this.popupView = view;
        this.anchorView = view2;
        this.config = popupConfig;
        int[] iArr = {view2 != null ? view2.getWidth() : 0, view2 != null ? view2.getHeight() : 0};
        int[] iArr2 = {view.getWidth(), view.getHeight()};
        int[] iArr3 = new int[2];
        if (view2 == null) {
            iArr3[0] = 0;
            iArr3[1] = getHeight();
        } else {
            view2.getLocationInWindow(iArr3);
        }
        int[] iArr4 = new int[2];
        getLocationOnScreen(iArr4);
        Integer fixedGap = popupConfig.getFixedGap();
        int iIntValue = fixedGap != null ? fixedGap.intValue() : getResources().getDimensionPixelOffset(R.dimen.kg_drawable_selector_popup_gap);
        int iLayoutTop = layoutTop(popupConfig, iIntValue, iArr4, iArr2, iArr, iArr3);
        int iLayoutLeft = layoutLeft(popupConfig, iIntValue, iArr4, iArr2, iArr, iArr3);
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int i = resourcesProvider.provideRealSize(context).y;
        if (view.getHeight() + iLayoutTop + popupConfig.getBottomMargin() > i) {
            iLayoutTop = (i - popupConfig.getBottomMargin()) - view.getHeight();
        }
        view.layout(iLayoutLeft, iLayoutTop, view.getWidth() + iLayoutLeft, view.getHeight() + iLayoutTop);
        view.requestLayout();
    }

    public final void updateLayout(PopupConfig popupConfig) {
        View view = this.popupView;
        View view2 = this.anchorView;
        if (popupConfig == null) {
            popupConfig = this.config;
        }
        updateLayout(view, view2, popupConfig);
    }
}
