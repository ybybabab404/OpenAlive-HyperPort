package miuix.app;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.core.util.Consumer;
import java.util.ArrayList;
import java.util.List;
import miuix.core.R;
import miuix.core.util.EnvStateManager;
import miuix.core.util.HyperMaterialUtils;

/* JADX INFO: loaded from: classes2.dex */
public class Application extends android.app.Application {
    public ComponentCallbacksWrapper mComponentCallbacksWrapper;
    public LifecycleCallbacksWrapper mLifecycleCallbacksWrapper;
    public Object mLifecycleLock = new Object();
    public Object mComponentLock = new Object();

    public static class ComponentCallbacksWrapper implements ComponentCallbacks {
        public List mComponentSubCallbacks;
        public final Context mContext;

        public ComponentCallbacksWrapper(Context context) {
            this.mContext = context;
        }

        public final void forAllComponentCallbacks(Consumer consumer) {
            synchronized (this) {
                List list = this.mComponentSubCallbacks;
                if (list != null && !list.isEmpty()) {
                    int size = this.mComponentSubCallbacks.size();
                    ComponentCallbacks[] componentCallbacksArr = new ComponentCallbacks[size];
                    this.mComponentSubCallbacks.toArray(componentCallbacksArr);
                    for (int i = 0; i < size; i++) {
                        consumer.accept(componentCallbacksArr[i]);
                    }
                }
            }
        }

        @Override // android.content.ComponentCallbacks
        public void onConfigurationChanged(final Configuration configuration) {
            forAllComponentCallbacks(new Consumer() { // from class: miuix.app.Application$ComponentCallbacksWrapper$$ExternalSyntheticLambda1
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ((ComponentCallbacks) obj).onConfigurationChanged(configuration);
                }
            });
        }

        @Override // android.content.ComponentCallbacks
        public void onLowMemory() {
            forAllComponentCallbacks(new Consumer() { // from class: miuix.app.Application$ComponentCallbacksWrapper$$ExternalSyntheticLambda0
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ((ComponentCallbacks) obj).onLowMemory();
                }
            });
        }

        public void registerCallBacks(ComponentCallbacks componentCallbacks) {
            if (this.mComponentSubCallbacks == null) {
                this.mComponentSubCallbacks = new ArrayList();
            }
            this.mComponentSubCallbacks.add(componentCallbacks);
        }
    }

    public static class LifecycleCallbacksWrapper implements android.app.Application.ActivityLifecycleCallbacks {
        public ArrayList mActivitySubLifecycleCallbacks = new ArrayList();

        public boolean add(android.app.Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
            return this.mActivitySubLifecycleCallbacks.add(activityLifecycleCallbacks);
        }

        public final Object[] collectActivityLifecycleSubCallbacks() {
            Object[] array;
            synchronized (this.mActivitySubLifecycleCallbacks) {
                try {
                    array = this.mActivitySubLifecycleCallbacks.size() > 0 ? this.mActivitySubLifecycleCallbacks.toArray() : null;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return array;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
            Object[] objArrCollectActivityLifecycleSubCallbacks = collectActivityLifecycleSubCallbacks();
            if (objArrCollectActivityLifecycleSubCallbacks != null) {
                for (Object obj : objArrCollectActivityLifecycleSubCallbacks) {
                    ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityCreated(activity, bundle);
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            Object[] objArrCollectActivityLifecycleSubCallbacks = collectActivityLifecycleSubCallbacks();
            if (objArrCollectActivityLifecycleSubCallbacks != null) {
                for (Object obj : objArrCollectActivityLifecycleSubCallbacks) {
                    ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityDestroyed(activity);
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            Object[] objArrCollectActivityLifecycleSubCallbacks = collectActivityLifecycleSubCallbacks();
            if (objArrCollectActivityLifecycleSubCallbacks != null) {
                for (Object obj : objArrCollectActivityLifecycleSubCallbacks) {
                    ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPaused(activity);
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            Object[] objArrCollectActivityLifecycleSubCallbacks = collectActivityLifecycleSubCallbacks();
            if (objArrCollectActivityLifecycleSubCallbacks != null) {
                for (Object obj : objArrCollectActivityLifecycleSubCallbacks) {
                    ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityResumed(activity);
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            Object[] objArrCollectActivityLifecycleSubCallbacks = collectActivityLifecycleSubCallbacks();
            if (objArrCollectActivityLifecycleSubCallbacks != null) {
                for (Object obj : objArrCollectActivityLifecycleSubCallbacks) {
                    ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivitySaveInstanceState(activity, bundle);
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            Object[] objArrCollectActivityLifecycleSubCallbacks = collectActivityLifecycleSubCallbacks();
            if (objArrCollectActivityLifecycleSubCallbacks != null) {
                for (Object obj : objArrCollectActivityLifecycleSubCallbacks) {
                    ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityStarted(activity);
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            Object[] objArrCollectActivityLifecycleSubCallbacks = collectActivityLifecycleSubCallbacks();
            if (objArrCollectActivityLifecycleSubCallbacks != null) {
                for (Object obj : objArrCollectActivityLifecycleSubCallbacks) {
                    ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityStopped(activity);
                }
            }
        }
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        HyperMaterialUtils.clearFeatureEnable();
        EnvStateManager.markEnvStateDirty(this);
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Application
    public void onCreate() {
        EnvStateManager.init(this);
        super.onCreate();
        Resources resources = getApplicationContext().getResources();
        if (resources.getInteger(R.integer.miuix_theme_use_third_party_theme) == 2) {
            UiModeManager uiModeManager = (UiModeManager) getSystemService("uimode");
            uiModeManager.setApplicationNightMode(0);
            if (uiModeManager.getNightMode() == 1) {
                if (resources.getBoolean(R.bool.miuix_theme_use_light_theme_in_light)) {
                    return;
                }
                uiModeManager.setApplicationNightMode(2);
            } else if (resources.getBoolean(R.bool.miuix_theme_use_light_theme_in_dark)) {
                uiModeManager.setApplicationNightMode(1);
            }
        }
    }

    public void registerActivityLifecycleSubCallbacks(android.app.Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        synchronized (this.mLifecycleLock) {
            try {
                if (this.mLifecycleCallbacksWrapper == null) {
                    LifecycleCallbacksWrapper lifecycleCallbacksWrapper = new LifecycleCallbacksWrapper();
                    this.mLifecycleCallbacksWrapper = lifecycleCallbacksWrapper;
                    registerActivityLifecycleCallbacks(lifecycleCallbacksWrapper);
                }
                this.mLifecycleCallbacksWrapper.add(activityLifecycleCallbacks);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void registerComponentSubCallbacks(ComponentCallbacks componentCallbacks) {
        synchronized (this.mComponentLock) {
            try {
                if (this.mComponentCallbacksWrapper == null) {
                    ComponentCallbacksWrapper componentCallbacksWrapper = new ComponentCallbacksWrapper(this);
                    this.mComponentCallbacksWrapper = componentCallbacksWrapper;
                    registerComponentCallbacks(componentCallbacksWrapper);
                }
                this.mComponentCallbacksWrapper.registerCallBacks(componentCallbacks);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
