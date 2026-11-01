package androidx.window.area.reflectionguard;

import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public interface ExtensionWindowAreaStatusRequirements {
    @NonNull
    DisplayMetrics getWindowAreaDisplayMetrics();

    int getWindowAreaStatus();
}
