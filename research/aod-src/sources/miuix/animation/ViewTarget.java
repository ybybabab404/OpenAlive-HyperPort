package miuix.animation;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;
import miuix.animation.internal.TargetHandler;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.property.ViewPropertyExt;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.LogUtils;

/* JADX INFO: loaded from: classes2.dex */
public class ViewTarget extends IAnimTarget<View> {
    private static volatile Set<ViewProperty> pViewPropertySet = new HashSet();
    public static final ITargetCreator<View> sCreator;
    public static final ITargetCreator<View> sSimpleCreator;
    private WeakReference<Context> mContextRef;
    private Runnable mHandlerInitTask;
    private boolean mIsSimple;
    private LifecycleCallbacks mLifecycleCallbacks;
    private Runnable mRegisterRunnable;
    private ViewLifecycleObserver mViewLifecycleObserver;
    private WeakReference<View> mViewRef;

    public class LifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        public LifecycleCallbacks() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(@NonNull Activity activity) {
            ViewTarget.this.cleanSelf();
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(@NonNull Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(@NonNull Activity activity) {
            ViewTarget.this.awakeSelf();
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(@NonNull Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(@NonNull Activity activity) {
            if (activity.isFinishing() || !Folme.enableSleep()) {
                return;
            }
            ViewTarget.this.sleepSelf();
        }
    }

    public class ViewLifecycleObserver implements LifecycleObserver {
        public ViewLifecycleObserver() {
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {
            ViewTarget.this.cleanSelf();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() {
            ViewTarget.this.awakeSelf();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void onStop() {
            if (Folme.enableSleep()) {
                ViewTarget.this.sleepSelf();
            }
        }
    }

    static {
        pViewPropertySet.add(ViewProperty.TRANSLATION_X);
        pViewPropertySet.add(ViewProperty.TRANSLATION_Y);
        pViewPropertySet.add(ViewProperty.TRANSLATION_Z);
        pViewPropertySet.add(ViewProperty.SCALE_X);
        pViewPropertySet.add(ViewProperty.SCALE_Y);
        pViewPropertySet.add(ViewProperty.ROTATION);
        pViewPropertySet.add(ViewProperty.ROTATION_X);
        pViewPropertySet.add(ViewProperty.ROTATION_Y);
        pViewPropertySet.add(ViewProperty.X);
        pViewPropertySet.add(ViewProperty.Y);
        pViewPropertySet.add(ViewProperty.Z);
        pViewPropertySet.add(ViewProperty.HEIGHT);
        pViewPropertySet.add(ViewProperty.WIDTH);
        pViewPropertySet.add(ViewProperty.ALPHA);
        pViewPropertySet.add(ViewProperty.AUTO_ALPHA);
        pViewPropertySet.add(ViewProperty.TRANSITION_ALPHA);
        pViewPropertySet.add(ViewProperty.SCROLL_X);
        pViewPropertySet.add(ViewProperty.SCROLL_Y);
        pViewPropertySet.add(ViewProperty.ELEVATION);
        pViewPropertySet.add(ViewProperty.BIG_VIEW_SCALE_X);
        pViewPropertySet.add(ViewProperty.BIG_VIEW_SCALE_Y);
        pViewPropertySet.add(ViewPropertyExt.BACKGROUND);
        pViewPropertySet.add(ViewPropertyExt.FOREGROUND);
        sCreator = new ITargetCreator<View>() { // from class: miuix.animation.ViewTarget.1
            @Override // miuix.animation.ITargetCreator
            public IAnimTarget createTarget(@NonNull View view) {
                ViewTarget viewTarget = new ViewTarget(view, view.getHandler() != null ? view.getHandler().getLooper() : null);
                viewTarget.init();
                return viewTarget;
            }
        };
        sSimpleCreator = new ITargetCreator<View>() { // from class: miuix.animation.ViewTarget.2
            @Override // miuix.animation.ITargetCreator
            public IAnimTarget createTarget(@NonNull View view) {
                ViewTarget viewTarget = new ViewTarget(view, view.getHandler() != null ? view.getHandler().getLooper() : null);
                viewTarget.mIsSimple = true;
                viewTarget.init();
                return viewTarget;
            }
        };
    }

    private ViewTarget(View view) {
        this.mHandlerInitTask = new Runnable() { // from class: miuix.animation.ViewTarget.3
            @Override // java.lang.Runnable
            public void run() {
                View view2 = (View) ViewTarget.this.mViewRef.get();
                if (view2 != null && view2.getHandler() != null) {
                    ViewTarget.this.handler = new TargetHandler(view2.getHandler().getLooper(), ViewTarget.this);
                    if (LogUtils.isLogMainEnabled()) {
                        LogUtils.debug(CommonUtils.TAG, "#ViewTarget execute handlerInitTask handler=" + ViewTarget.this.handler + " view=" + view2);
                    }
                }
                ViewTarget.this.mHandlerInitTask = null;
            }
        };
        this.mRegisterRunnable = new Runnable() { // from class: miuix.animation.ViewTarget.4
            @Override // java.lang.Runnable
            public void run() {
                ViewTarget.this.mHandlerInitTask.run();
                View view2 = (View) ViewTarget.this.mViewRef.get();
                if (view2 != null) {
                    ViewTarget.this.registerLifecycle(view2.getContext());
                }
                ViewTarget.this.mRegisterRunnable = null;
            }
        };
        this.mViewRef = new WeakReference<>(view);
        if (Folme.appContext == null) {
            Folme.appContext = view.getContext().getApplicationContext();
        }
    }

    private ViewTarget(View view, @Nullable Looper looper) {
        super(looper);
        this.mHandlerInitTask = new Runnable() { // from class: miuix.animation.ViewTarget.3
            @Override // java.lang.Runnable
            public void run() {
                View view2 = (View) ViewTarget.this.mViewRef.get();
                if (view2 != null && view2.getHandler() != null) {
                    ViewTarget.this.handler = new TargetHandler(view2.getHandler().getLooper(), ViewTarget.this);
                    if (LogUtils.isLogMainEnabled()) {
                        LogUtils.debug(CommonUtils.TAG, "#ViewTarget execute handlerInitTask handler=" + ViewTarget.this.handler + " view=" + view2);
                    }
                }
                ViewTarget.this.mHandlerInitTask = null;
            }
        };
        this.mRegisterRunnable = new Runnable() { // from class: miuix.animation.ViewTarget.4
            @Override // java.lang.Runnable
            public void run() {
                ViewTarget.this.mHandlerInitTask.run();
                View view2 = (View) ViewTarget.this.mViewRef.get();
                if (view2 != null) {
                    ViewTarget.this.registerLifecycle(view2.getContext());
                }
                ViewTarget.this.mRegisterRunnable = null;
            }
        };
        this.mViewRef = new WeakReference<>(view);
        if (Folme.appContext == null) {
            Folme.appContext = view.getContext().getApplicationContext();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void awakeSelf() {
        Folme.awake(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanSelf() {
        if (LogUtils.isLogMainEnabled()) {
            LogUtils.debug("ViewTarget.cleanSelf isSimple:" + this.mIsSimple + " " + this + LogUtils.getStackTrace(4), new Object[0]);
        }
        WeakReference<Context> weakReference = this.mContextRef;
        if (weakReference != null) {
            unRegisterLifecycle(weakReference.get());
        }
        setCorner(0.0f);
        if (this.mIsSimple) {
            clean();
        } else {
            Folme.clean(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doClean() {
        View view;
        if (isAnimRunning(new FloatProperty[0])) {
            cancelRunningAnim();
        }
        WeakReference<View> weakReference = this.mViewRef;
        if (weakReference != null && (view = weakReference.get()) != null) {
            Runnable runnable = this.mRegisterRunnable;
            if (runnable != null) {
                view.removeCallbacks(runnable);
                this.mRegisterRunnable = null;
            }
            Runnable runnable2 = this.mHandlerInitTask;
            if (runnable2 != null) {
                view.removeCallbacks(runnable2);
                this.mHandlerInitTask = null;
            }
        }
        this.animManager.clear();
        getNotifier().removeListeners();
        WeakReference<Context> weakReference2 = this.mContextRef;
        if (weakReference2 != null) {
            unRegisterLifecycle(weakReference2.get());
        }
    }

    private void executeTask(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            Log.w(CommonUtils.TAG, "ViewTarget.executeTask failed, " + getTargetObject(), e);
        }
    }

    public static FloatProperty getFloatProperty(String str) {
        for (ViewProperty viewProperty : pViewPropertySet) {
            if (viewProperty.getName().contentEquals(str)) {
                return viewProperty;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        View view = this.mViewRef.get();
        if (view != null) {
            if (this.mIsSimple) {
                this.mRegisterRunnable = null;
                if (this.handler == null) {
                    view.post(this.mHandlerInitTask);
                    return;
                }
                return;
            }
            if (this.handler == null) {
                view.post(this.mRegisterRunnable);
            } else {
                this.mRegisterRunnable.run();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initLayout(View view, Runnable runnable) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            int i = R.id.miuix_animation_tag_init_layout;
            view.setTag(i, Boolean.TRUE);
            ViewGroup viewGroup = (ViewGroup) parent;
            int left = viewGroup.getLeft();
            int top = viewGroup.getTop();
            int visibility = view.getVisibility();
            if (visibility == 8) {
                view.setVisibility(4);
            }
            viewGroup.measure(viewGroup.getWidth(), viewGroup.getHeight());
            viewGroup.layout(left, top, viewGroup.getWidth() + left, viewGroup.getHeight() + top);
            view.setVisibility(visibility);
            runnable.run();
            view.setTag(i, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v6, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    public boolean registerLifecycle(Context context) {
        while (context != 0) {
            if (context instanceof LifecycleOwner) {
                this.mContextRef = new WeakReference<>(context);
                if (this.mViewLifecycleObserver == null) {
                    this.mViewLifecycleObserver = new ViewLifecycleObserver();
                }
                ((LifecycleOwner) context).getLifecycle().addObserver(this.mViewLifecycleObserver);
                return true;
            }
            if (context instanceof Activity) {
                this.mContextRef = new WeakReference<>(context);
                if (this.mLifecycleCallbacks == null) {
                    this.mLifecycleCallbacks = new LifecycleCallbacks();
                }
                ((Activity) context).registerActivityLifecycleCallbacks(this.mLifecycleCallbacks);
                return true;
            }
            context = context instanceof ContextWrapper ? ((ContextWrapper) context).getBaseContext() : 0;
        }
        return false;
    }

    private void setCorner(float f) {
        View view = this.mViewRef.get();
        if (view != null) {
            view.setTag(R.id.miuix_animation_tag_view_hover_corners, Float.valueOf(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sleepSelf() {
        Folme.sleep(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean unRegisterLifecycle(Context context) {
        LifecycleCallbacks lifecycleCallbacks;
        if (context == 0) {
            return false;
        }
        if (context instanceof LifecycleOwner) {
            if (this.mViewLifecycleObserver != null) {
                ((LifecycleOwner) context).getLifecycle().removeObserver(this.mViewLifecycleObserver);
            }
            this.mViewLifecycleObserver = null;
            return true;
        }
        if (!(context instanceof Activity) || (lifecycleCallbacks = this.mLifecycleCallbacks) == null) {
            return false;
        }
        ((Activity) context).unregisterActivityLifecycleCallbacks(lifecycleCallbacks);
        this.mLifecycleCallbacks = null;
        return true;
    }

    @Override // miuix.animation.IAnimTarget
    public boolean allowAnimRun() {
        View targetObject = getTargetObject();
        return (targetObject == null || Folme.isInDraggingState(targetObject)) ? false : true;
    }

    @Override // miuix.animation.IAnimTarget
    public void clean() {
        executeTask(new Runnable() { // from class: miuix.animation.ViewTarget$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.doClean();
            }
        });
    }

    @Override // miuix.animation.IAnimTarget
    public TargetHandler createHandler(Looper looper) {
        if (looper == null) {
            if (!LogUtils.isLogMainEnabled()) {
                return null;
            }
            Log.w(CommonUtils.TAG, "warning!! the ViewTarget has created in a thread without Looper, the animation will do not work!!you should use HandlerThread instead of Thread, trace:" + Log.getStackTraceString(new Throwable()));
            return null;
        }
        if (looper != Looper.getMainLooper() && Folme.getUiLooperByTid(looper.getThread().getId()) == null) {
            if (LogUtils.isLogDetailEnable()) {
                LogUtils.debug("ViewTarget.createHandler registerUiLooper " + looper + " tid " + looper.getThread().getId(), new Object[0]);
            }
            Folme.registerUiLooper(looper);
        }
        return new TargetHandler(looper, this);
    }

    @Override // miuix.animation.IAnimTarget
    public void executeOnInitialized(final Runnable runnable) {
        final View view = this.mViewRef.get();
        if (view != null) {
            if (view.getVisibility() == 8 && !view.isLaidOut() && (view.getWidth() == 0 || view.getHeight() == 0)) {
                post(new Runnable() { // from class: miuix.animation.ViewTarget.5
                    @Override // java.lang.Runnable
                    public void run() {
                        ViewTarget.this.initLayout(view, runnable);
                    }
                });
            } else {
                post(runnable);
            }
        }
    }

    @Override // miuix.animation.IAnimTarget
    public double getDoubleValue(@NonNull FloatProperty floatProperty) {
        View targetObject;
        if (!(floatProperty instanceof ViewProperty) || (targetObject = getTargetObject()) == null) {
            return Double.MAX_VALUE;
        }
        return floatProperty.getValue(targetObject);
    }

    @Override // miuix.animation.IAnimTarget
    public void getLocationOnScreen(int[] iArr) {
        View view = this.mViewRef.get();
        if (view != null) {
            view.getLocationOnScreen(iArr);
        } else {
            iArr[1] = Integer.MAX_VALUE;
            iArr[0] = Integer.MAX_VALUE;
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // miuix.animation.IAnimTarget
    public View getTargetObject() {
        WeakReference<View> weakReference = this.mViewRef;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    @Override // miuix.animation.IAnimTarget
    public double getVelocity(String str) {
        return getVelocity(getFloatProperty(str));
    }

    @Override // miuix.animation.IAnimTarget
    public boolean isValid() {
        WeakReference<View> weakReference = this.mViewRef;
        return (weakReference == null || weakReference.get() == null) ? false : true;
    }

    @Override // miuix.animation.IAnimTarget
    public void onFrameEnd(boolean z) {
        View view = this.mViewRef.get();
        if (!z || view == null) {
            return;
        }
        view.setTag(R.id.miuix_animation_tag_set_height, null);
        view.setTag(R.id.miuix_animation_tag_set_width, null);
        view.setTag(R.id.miuix_animation_tag_view_hover_corners, Float.valueOf(0.0f));
    }

    @Override // miuix.animation.IAnimTarget
    public void post(Runnable runnable) {
        View targetObject = getTargetObject();
        if (targetObject == null) {
            return;
        }
        TargetHandler handler = getHandler();
        if (handler == null || handler.isInTargetThread() || !targetObject.isAttachedToWindow()) {
            executeTask(runnable);
        } else {
            targetObject.post(runnable);
        }
    }

    @Override // miuix.animation.IAnimTarget
    public void setVelocity(String str, double d) {
        FloatProperty floatProperty = getFloatProperty(str);
        if (floatProperty == null) {
            return;
        }
        setVelocity(floatProperty, d);
    }

    @Override // miuix.animation.IAnimTarget
    public boolean shouldUseIntValue(FloatProperty floatProperty) {
        if (floatProperty == ViewProperty.WIDTH || floatProperty == ViewProperty.HEIGHT || floatProperty == ViewProperty.SCROLL_X || floatProperty == ViewProperty.SCROLL_Y) {
            return true;
        }
        return super.shouldUseIntValue(floatProperty);
    }

    @Override // miuix.animation.IAnimTarget
    public String toString() {
        String resourcePackageName;
        StringBuilder sb = new StringBuilder(AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL);
        View targetObject = getTargetObject();
        sb.append('#');
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" ");
        sb.append(isValid() ? "valid" : "invalid");
        sb.append(" {");
        int id = targetObject != null ? targetObject.getId() : -1;
        if (id != -1) {
            sb.append(Integer.toHexString(id));
            Resources resources = targetObject.getResources();
            if (id > 0 && (id >>> 24) != 0 && resources != null) {
                int i = (-16777216) & id;
                if (i == 16777216) {
                    resourcePackageName = "android";
                } else if (i != 2130706432) {
                    try {
                        resourcePackageName = resources.getResourcePackageName(id);
                    } catch (Resources.NotFoundException unused) {
                    }
                } else {
                    resourcePackageName = "app";
                }
                String resourceTypeName = resources.getResourceTypeName(id);
                String resourceEntryName = resources.getResourceEntryName(id);
                sb.append(" ");
                sb.append(resourcePackageName);
                sb.append(":");
                sb.append(resourceTypeName);
                sb.append("/");
                sb.append(resourceEntryName);
            }
        } else {
            sb.append("NO_ID");
        }
        sb.append("/");
        if (targetObject != null) {
            sb.append(targetObject.getClass().getName());
        } else {
            sb.append("view reference is not available");
        }
        sb.append("}");
        return "View{" + ((Object) sb) + "}";
    }
}
