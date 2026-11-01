package miuix.responsive.page.manager;

import android.content.Context;
import android.view.View;

/* JADX INFO: loaded from: classes2.dex */
public class ResponsiveViewStateManager extends BaseStateManager {
    public View mView;

    @Override // miuix.responsive.page.manager.BaseStateManager
    public Context getContext() {
        return this.mView.getContext();
    }
}
