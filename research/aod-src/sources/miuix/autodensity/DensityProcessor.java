package miuix.autodensity;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import miuix.core.util.EnvStateManager;
import miuix.core.util.WindowBaseInfo;

/* JADX INFO: loaded from: classes2.dex */
public abstract class DensityProcessor {
    protected HashMap<Integer, DensityCallback> mModifier;
    protected DisplayManager mDisplayManager = null;
    private final Handler mUiHandler = new Handler(Looper.getMainLooper());

    public class DensityCallback implements DisplayManager.DisplayListener, ComponentCallbacks {
        public WeakReference mActivityRefs;
        public WeakReference mDecorViewListener = null;
        public final DensityProcessor mDensityProcessor;

        public DensityCallback(Activity activity, DensityProcessor densityProcessor) {
            this.mActivityRefs = null;
            this.mActivityRefs = new WeakReference(activity);
            this.mDensityProcessor = densityProcessor;
        }

        public void addOnAttachStateChangeListener(View.OnAttachStateChangeListener onAttachStateChangeListener) {
            this.mDecorViewListener = new WeakReference(onAttachStateChangeListener);
        }

        public void clear() {
            WeakReference weakReference = this.mActivityRefs;
            if (weakReference != null) {
                weakReference.clear();
            }
            WeakReference weakReference2 = this.mDecorViewListener;
            if (weakReference2 != null) {
                weakReference2.clear();
            }
        }

        @Override // android.content.ComponentCallbacks
        public void onConfigurationChanged(Configuration configuration) {
            WeakReference weakReference = this.mActivityRefs;
            Activity activity = weakReference != null ? (Activity) weakReference.get() : null;
            if (activity != null) {
                this.mDensityProcessor.processBeforeActivityConfigChanged(activity, configuration);
            } else {
                DensityProcessor.this.unregisterDisplayListener(this);
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            WeakReference weakReference = this.mActivityRefs;
            Activity activity = weakReference == null ? null : (Activity) weakReference.get();
            if (activity != null) {
                this.mDensityProcessor.processOnActivityDisplayChanged(i, activity);
            } else {
                DensityProcessor.this.unregisterDisplayListener(this);
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
        }

        @Override // android.content.ComponentCallbacks
        public void onLowMemory() {
        }

        public void removeOnAttachStateChangeListener(Activity activity) {
            View.OnAttachStateChangeListener onAttachStateChangeListener;
            WeakReference weakReference = this.mDecorViewListener;
            if (weakReference == null || (onAttachStateChangeListener = (View.OnAttachStateChangeListener) weakReference.get()) == null) {
                return;
            }
            activity.getWindow().getDecorView().removeOnAttachStateChangeListener(onAttachStateChangeListener);
        }
    }

    public static class DensityProcessorLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        public final DensityProcessor mDensityProcessor;

        public DensityProcessorLifecycleCallbacks(DensityProcessor densityProcessor) {
            this.mDensityProcessor = densityProcessor;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
            this.mDensityProcessor.processOnActivityCreated(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            this.mDensityProcessor.processOnActivityDestroyed(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }
    }

    public void onDensityChangedBeforeActivityConfigChanged(Activity activity, Configuration configuration, WindowBaseInfo windowBaseInfo) {
        EnvStateManager.updateWindowSizeByConfig(configuration, windowBaseInfo);
        EnvStateManager.updateWindowMode(activity, windowBaseInfo);
        EnvStateManager.markEnvStateDirty(activity);
        EnvStateManager.markWindowInfoDirty(windowBaseInfo);
    }

    public void onDensityChangedOnActivityCreated(Activity activity) {
        registerCallback(activity);
    }

    public void onDensityChangedOnActivityDisplayChanged(int i, Activity activity) {
    }

    public void onDensityChangedOnAppConfigChanged(Application application) {
    }

    public abstract void processBeforeActivityConfigChanged(Activity activity, Configuration configuration);

    public abstract void processOnActivityCreated(Activity activity);

    public abstract void processOnActivityDestroyed(Activity activity);

    public abstract void processOnActivityDisplayChanged(int i, Activity activity);

    public void registerCallback(Activity activity) {
        if (this.mDisplayManager == null) {
            this.mDisplayManager = (DisplayManager) activity.getApplication().getSystemService("display");
        }
        if (this.mModifier == null) {
            this.mModifier = new HashMap<>();
        }
        int iHashCode = activity.hashCode();
        if (this.mModifier.get(Integer.valueOf(iHashCode)) == null) {
            DensityCallback densityCallback = new DensityCallback(activity, this);
            this.mModifier.put(Integer.valueOf(iHashCode), densityCallback);
            this.mDisplayManager.registerDisplayListener(densityCallback, this.mUiHandler);
            activity.getApplication().registerComponentCallbacks(densityCallback);
            activity.registerComponentCallbacks(densityCallback);
        }
    }

    public void unregisterCallback(Activity activity) {
        if (this.mModifier != null) {
            int iHashCode = activity.hashCode();
            DensityCallback densityCallback = this.mModifier.get(Integer.valueOf(iHashCode));
            if (DebugUtil.isEnableDebug()) {
                DebugUtil.printDensityLog("unregisterCallback obj: " + densityCallback);
            }
            if (densityCallback != null) {
                unregisterDisplayListener(densityCallback);
                activity.getApplication().unregisterComponentCallbacks(densityCallback);
                activity.unregisterComponentCallbacks(densityCallback);
                densityCallback.removeOnAttachStateChangeListener(activity);
                densityCallback.clear();
            }
            this.mModifier.remove(Integer.valueOf(iHashCode));
        }
    }

    public void unregisterDisplayListener(DensityCallback densityCallback) {
        DisplayManager displayManager = this.mDisplayManager;
        if (displayManager != null) {
            displayManager.unregisterDisplayListener(densityCallback);
        }
    }
}
