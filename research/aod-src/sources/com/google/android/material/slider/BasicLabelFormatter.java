package com.google.android.material.slider;

import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class BasicLabelFormatter implements LabelFormatter {
    @Override // com.google.android.material.slider.LabelFormatter
    public String getFormattedValue(float f) {
        if (f >= 1.0E12f) {
            return String.format(Locale.US, "%.1fT", Float.valueOf(f / 1.0E12f));
        }
        if (f >= 1.0E9f) {
            return String.format(Locale.US, "%.1fB", Float.valueOf(f / 1.0E9f));
        }
        if (f >= 1000000.0f) {
            return String.format(Locale.US, "%.1fM", Float.valueOf(f / 1000000.0f));
        }
        return f >= 1000.0f ? String.format(Locale.US, "%.1fK", Float.valueOf(f / 1000.0f)) : String.format(Locale.US, "%.0f", Float.valueOf(f));
    }
}
