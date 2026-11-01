package com.google.android.material.color;

import android.app.Activity;

/* JADX INFO: loaded from: classes.dex */
public class DynamicColorsOptions {
    public static final DynamicColors.Precondition ALWAYS_ALLOW = new DynamicColors.Precondition() { // from class: com.google.android.material.color.DynamicColorsOptions.1
        @Override // com.google.android.material.color.DynamicColors.Precondition
        public boolean shouldApplyDynamicColors(Activity activity, int i) {
            return true;
        }
    };
    public static final DynamicColors.OnAppliedCallback NO_OP_CALLBACK = new DynamicColors.OnAppliedCallback() { // from class: com.google.android.material.color.DynamicColorsOptions.2
        @Override // com.google.android.material.color.DynamicColors.OnAppliedCallback
        public void onApplied(Activity activity) {
        }
    };
    public Integer contentBasedSeedColor;
    public final DynamicColors.OnAppliedCallback onAppliedCallback;
    public final DynamicColors.Precondition precondition;
    public final int themeOverlay;

    public static class Builder {
    }

    public Integer getContentBasedSeedColor() {
        return this.contentBasedSeedColor;
    }

    public DynamicColors.OnAppliedCallback getOnAppliedCallback() {
        return this.onAppliedCallback;
    }

    public DynamicColors.Precondition getPrecondition() {
        return this.precondition;
    }

    public int getThemeOverlay() {
        return this.themeOverlay;
    }
}
