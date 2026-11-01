package androidx.core.view;

import android.view.Menu;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
public final class MenuCompat {

    @RequiresApi
    public static class Api28Impl {
        @DoNotInline
        public static void setGroupDividerEnabled(Menu menu, boolean z) {
            menu.setGroupDividerEnabled(z);
        }
    }
}
