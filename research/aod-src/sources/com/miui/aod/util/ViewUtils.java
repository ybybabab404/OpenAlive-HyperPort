package com.miui.aod.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.window.embedding.ActivityEmbeddingController;
import androidx.window.embedding.SplitController;
import com.miui.aod.R;
import java.util.function.BooleanSupplier;
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
/* JADX INFO: compiled from: ViewUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ViewUtils {
    public static final ViewUtils INSTANCE = new ViewUtils();

    public static final void applyCoverageForAccessibility(View view, View view2) {
        if (view == null || view2 == null) {
            return;
        }
        if (!view.isLaidOut() || view.getWidth() == 0 || view.getHeight() == 0) {
            INSTANCE.applyCoverageRecursive(null, view2, false);
        } else {
            INSTANCE.applyCoverageRecursive(view.getVisibility() == 0 ? INSTANCE.getRect(view) : null, view2, false);
        }
    }

    public static final void createAccessibilityDelegateForView(View view, CharSequence role, BooleanSupplier isCheckedProvider) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(role, "role");
        Intrinsics.checkNotNullParameter(isCheckedProvider, "isCheckedProvider");
        createAccessibilityDelegateForView$default(view, role, isCheckedProvider, false, 8, null);
    }

    public static final void createAccessibilityDelegateForView(View view, final CharSequence role, final BooleanSupplier isCheckedProvider, final boolean z) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(role, "role");
        Intrinsics.checkNotNullParameter(isCheckedProvider, "isCheckedProvider");
        ViewCompat.setAccessibilityDelegate(view, new AccessibilityDelegateCompat() { // from class: com.miui.aod.util.ViewUtils$createAccessibilityDelegateForView$1$1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                Intrinsics.checkNotNullParameter(host, "host");
                Intrinsics.checkNotNullParameter(info, "info");
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setRoleDescription(role);
                info.setCheckable(z);
                info.setChecked(isCheckedProvider.getAsBoolean());
            }
        });
    }

    public static /* synthetic */ void createAccessibilityDelegateForView$default(View view, CharSequence charSequence, BooleanSupplier booleanSupplier, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        createAccessibilityDelegateForView(view, charSequence, booleanSupplier, z);
    }

    public static final boolean isActivityAlreadyEmbedded(Context activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        return (activity instanceof Activity) && isSplitEnabled(activity) && ActivityEmbeddingController.Companion.getInstance(activity).isActivityEmbedded((Activity) activity);
    }

    public static final boolean isSplitEnabled(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return Intrinsics.areEqual(SplitController.Companion.getInstance(context).getSplitSupportStatus(), SplitController.SplitSupportStatus.SPLIT_AVAILABLE);
    }

    public static final void setAccessibilityForView(View view, CharSequence content, CharSequence state) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(state, "state");
        view.setContentDescription(content);
        view.setStateDescription(state);
    }

    public final void applyCoverageRecursive(Rect rect, View view, boolean z) {
        if (view.getVisibility() == 0 && view.isLaidOut() && view.getWidth() != 0 && view.getHeight() != 0) {
            boolean z2 = z || (rect != null && isFullyCovered(rect, view));
            if (z2) {
                storeOriginalIfNeeded(view);
                view.setImportantForAccessibility(4);
            } else {
                restoreOriginalIfStored(view);
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = viewGroup.getChildAt(i);
                    Intrinsics.checkNotNullExpressionValue(childAt, "getChildAt(...)");
                    applyCoverageRecursive(rect, childAt, z2);
                }
            }
        }
    }

    public final Rect getRect(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i = iArr[0];
        return new Rect(i, iArr[1], view.getWidth() + i, iArr[1] + view.getHeight());
    }

    public final boolean isFullyCovered(Rect rect, View view) {
        return rect.contains(getRect(view));
    }

    public final void restoreOriginalIfStored(View view) {
        Object tag = view.getTag(R.id.tag_accessibility_prev);
        if (tag instanceof Integer) {
            view.setImportantForAccessibility(((Number) tag).intValue());
            view.setTag(R.id.tag_accessibility_prev, null);
        } else if (tag != null) {
            view.setTag(R.id.tag_accessibility_prev, null);
        }
    }

    public final void storeOriginalIfNeeded(View view) {
        if (view.getTag(R.id.tag_accessibility_prev) == null) {
            view.setTag(R.id.tag_accessibility_prev, Integer.valueOf(view.getImportantForAccessibility()));
        }
    }
}
