package miuix.appcompat.app;

import android.view.Menu;
import android.view.View;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes2.dex */
@RestrictTo
interface ActionBarDelegate extends IContentInsetState {
    ActionBar createActionBar();

    void invalidateOptionsMenu();

    void onPanelViewAdded(int i, View view, Menu menu, Menu menu2);
}
