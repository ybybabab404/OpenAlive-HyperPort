package miuix.transition;

import android.app.ActivityOptions;
import android.os.Bundle;

/* JADX INFO: loaded from: classes3.dex */
public class ActivityOptionsCompatImpl extends androidx.core.app.ActivityOptionsCompat {
    public final ActivityOptions mActivityOptions;

    @Override // androidx.core.app.ActivityOptionsCompat
    public Bundle toBundle() {
        return this.mActivityOptions.toBundle();
    }
}
