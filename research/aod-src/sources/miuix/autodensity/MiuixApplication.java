package miuix.autodensity;

import miuix.app.Application;

/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public class MiuixApplication extends Application implements IDensity {
    @Override // miuix.app.Application, android.app.Application
    public void onCreate() {
        super.onCreate();
        AutoDensityConfig.init(this);
    }

    @Override // miuix.autodensity.IDensity
    public boolean shouldAdaptAutoDensity() {
        return true;
    }
}
