package com.miui.aod;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.doze.DozeMachine;
import com.miui.aod.notification.NotificationViewModel;
import com.miui.aod.resource.CachedSettings;

/* JADX INFO: loaded from: classes.dex */
public class DozeLifecycleOwner implements LifecycleOwner, ViewModelStoreOwner, DozeMachine.Part {
    public static volatile DozeLifecycleOwner sInstance;
    public final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    public final ViewModelStore mViewModelStore = new ViewModelStore();
    public NotificationViewModel notificationViewModel;

    /* JADX INFO: renamed from: com.miui.aod.DozeLifecycleOwner$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$aod$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$miui$aod$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static DozeLifecycleOwner getInstance() {
        if (sInstance == null) {
            synchronized (DozeLifecycleOwner.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new DozeLifecycleOwner();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void destroy() {
        this.mViewModelStore.clear();
        CategoryFactory.cleanCache();
        CachedSettings.cleanCache();
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    public ViewModelStore getViewModelStore() {
        return this.mViewModelStore;
    }

    public void initState() {
        this.mLifecycleRegistry.setCurrentState(Lifecycle.State.INITIALIZED);
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int i = AnonymousClass1.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state2.ordinal()];
        if (i == 1) {
            NotificationViewModel notificationViewModel = (NotificationViewModel) new ViewModelProvider(sInstance).get(NotificationViewModel.class);
            this.notificationViewModel = notificationViewModel;
            notificationViewModel.observe(sInstance);
            this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
            return;
        }
        if (i == 2) {
            this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
            return;
        }
        if (i == 3) {
            this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        } else {
            if (i != 4) {
                return;
            }
            this.notificationViewModel.removeObserve();
            this.notificationViewModel = null;
            this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        }
    }
}
