package miuix.responsive.page.manager;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import com.xiaomi.onetrack.util.ac;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import miuix.core.R;
import miuix.reflect.Reflects;
import miuix.responsive.interfaces.IResponsive;
import miuix.responsive.interfaces.IViewResponsive;
import miuix.responsive.map.ResponsiveSpec;
import miuix.responsive.map.ResponsiveState;
import miuix.responsive.map.ResponsiveStateManager;
import miuix.responsive.map.ResponsiveViewSpec;
import miuix.responsive.map.ScreenSpec;
import miuix.responsive.wrapper.OnHierarchyChangeListenerWrapper;
import miuix.responsive.wrapper.WrapperHelper;

/* JADX INFO: loaded from: classes2.dex */
public abstract class BaseResponseStateManager extends BaseStateManager {
    public IResponsive mPageInfo;
    public ArrayMap mResponsiveMap;
    public ArrayMap mResponsiveMapChild;
    public ArrayMap mResponsiveViewGroup;
    public View mRootView;
    public ArrayMap mViewResponsives;

    public class ResponseLifecycleObserver implements LifecycleObserver {
        public BaseResponseStateManager mBaseResponseStateManager;

        public ResponseLifecycleObserver(BaseResponseStateManager baseResponseStateManager) {
            this.mBaseResponseStateManager = baseResponseStateManager;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        public void onCreate() {
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {
            this.mBaseResponseStateManager.destroy();
            this.mBaseResponseStateManager = null;
        }
    }

    public class ResponsiveView implements IResponsive<View> {
        public View mContainer;
        public IViewResponsive mIViewResponsive;

        public ResponsiveView(View view) {
            this.mContainer = view;
        }

        public final void doResponse(Configuration configuration, ScreenSpec screenSpec, boolean z) {
            List list = (List) BaseResponseStateManager.this.mResponsiveMapChild.get(this.mContainer);
            IViewResponsive iViewResponsive = this.mIViewResponsive;
            if (iViewResponsive == null || !iViewResponsive.onResponsiveLayout(configuration, screenSpec, z, list)) {
                int effectiveScreenOrientation = ((ResponsiveViewSpec) BaseResponseStateManager.this.mResponsiveViewGroup.get(Integer.valueOf(this.mContainer.getId()))).getEffectiveScreenOrientation();
                int i = configuration == null ? BaseResponseStateManager.this.getContext().getResources().getConfiguration().orientation : configuration.orientation;
                if (list == null || list.isEmpty()) {
                    return;
                }
                if (ResponsiveSpec.isScreenOrientationMatch(i, effectiveScreenOrientation)) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        ((ResponsiveViewSpec) it.next()).onResponsiveState(screenSpec);
                    }
                } else {
                    Iterator it2 = list.iterator();
                    while (it2.hasNext()) {
                        View view = ((ResponsiveViewSpec) it2.next()).getView();
                        if (view != null) {
                            view.setVisibility(0);
                        }
                    }
                }
            }
        }

        @Override // miuix.responsive.interfaces.IResponsive
        public View getResponsiveSubject() {
            return null;
        }

        @Override // miuix.responsive.interfaces.IResponsive
        public void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z) {
            doResponse(configuration, screenSpec, z);
        }
    }

    public BaseResponseStateManager(IResponsive iResponsive) {
        this.mPageInfo = iResponsive;
        if (iResponsive.getResponsiveSubject() instanceof LifecycleOwner) {
            registerLifecycle((LifecycleOwner) this.mPageInfo.getResponsiveSubject());
        }
        this.mResponsiveMap = new ArrayMap();
        this.mResponsiveMapChild = new ArrayMap();
        this.mResponsiveViewGroup = new ArrayMap();
        this.mViewResponsives = new ArrayMap();
        WrapperHelper.setFactory2(LayoutInflater.from(getContext()), new ResponsiveFactory2Wrapper(this));
        this.mState = computeResponsiveState();
    }

    public void afterConfigurationChanged(Configuration configuration) {
        if (BaseStateManager.isSupportResponsive()) {
            this.mState = computeResponsiveStateFromConfig(configuration);
            onAfterConfigurationChanged(configuration);
            notifyResponseChange(configuration, this.mState, !isStateEquals(this.mState, this.mOldState));
        }
    }

    public void beforeConfigurationChanged(Configuration configuration) {
        if (BaseStateManager.isSupportResponsive()) {
            this.mOldState.setTo(this.mState);
            onBeforeConfigurationChanged(configuration);
        }
    }

    public final void bindResponseView(View view) {
        this.mResponsiveMap.remove(view);
        this.mResponsiveMap.put(view, new ResponsiveView(view));
        if (this.mResponsiveViewGroup.containsKey(Integer.valueOf(view.getId()))) {
            return;
        }
        ResponsiveViewSpec responsiveViewSpec = new ResponsiveViewSpec(view.getId());
        responsiveViewSpec.setEffectiveScreenOrientation(3);
        this.mResponsiveViewGroup.put(Integer.valueOf(view.getId()), responsiveViewSpec);
    }

    public void destroy() {
        onDestroy();
        this.mPageInfo = null;
        this.mResponsiveMap.clear();
        this.mResponsiveMapChild.clear();
    }

    public ResponsiveState getState() {
        return this.mState;
    }

    public final void injectOnHierarchyChangeListener(ViewGroup viewGroup) {
        viewGroup.setOnHierarchyChangeListener(new OnHierarchyChangeListenerWrapper() { // from class: miuix.responsive.page.manager.BaseResponseStateManager.1
            @Override // miuix.responsive.wrapper.OnHierarchyChangeListenerWrapper, android.view.ViewGroup.OnHierarchyChangeListener
            public void onChildViewAdded(View view, View view2) {
                if (((ViewGroup) view).getChildCount() == 1) {
                    ((ResponsiveViewSpec) BaseResponseStateManager.this.mResponsiveViewGroup.get(Integer.valueOf(view.getId()))).setView(view);
                }
                List<ResponsiveViewSpec> list = (List) BaseResponseStateManager.this.mResponsiveMapChild.get(view);
                if (list != null && !list.isEmpty()) {
                    for (ResponsiveViewSpec responsiveViewSpec : list) {
                        if (responsiveViewSpec.getViewId() == view2.getId()) {
                            responsiveViewSpec.setView(view2);
                        }
                    }
                }
                super.onChildViewAdded(view, view2);
            }
        });
    }

    public void notifyResponseChange(Configuration configuration, ResponsiveState responsiveState, boolean z) {
        ScreenSpec screenSpec = new ScreenSpec();
        if (responsiveState != null) {
            responsiveState.toScreenSpec(screenSpec);
        }
        this.mPageInfo.dispatchResponsiveLayout(configuration, screenSpec, z);
        Iterator it = this.mResponsiveMap.keySet().iterator();
        while (it.hasNext()) {
            IResponsive iResponsive = (IResponsive) this.mResponsiveMap.get((View) it.next());
            if (iResponsive != null) {
                iResponsive.dispatchResponsiveLayout(configuration, screenSpec, z);
            }
        }
        for (Integer num : this.mViewResponsives.keySet()) {
            IViewResponsive iViewResponsive = (IViewResponsive) this.mViewResponsives.get(num);
            if (iViewResponsive == null) {
                iViewResponsive = (IViewResponsive) this.mRootView.findViewById(num.intValue());
                this.mViewResponsives.put(num, iViewResponsive);
            }
            iViewResponsive.onResponsiveLayout(configuration, screenSpec, z);
        }
    }

    public void onDestroy() {
        ResponsiveStateManager.getInstance().remove(getContext());
    }

    public void parseResponsiveViews(Context context, View view, AttributeSet attributeSet, String str) {
        int resourceId;
        if (this.mRootView == null) {
            this.mRootView = view;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ResponsiveSpec);
        if (str.split(ac.a).length > 1 && IViewResponsive.class.isAssignableFrom(Reflects.forName(str)) && (resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.ResponsiveSpec_android_id, -1)) != -1) {
            this.mViewResponsives.put(Integer.valueOf(resourceId), null);
        }
        int integer = typedArrayObtainStyledAttributes.getInteger(R.styleable.ResponsiveSpec_effectiveScreenOrientation, 0);
        if (integer != 0) {
            int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.ResponsiveSpec_android_id, -1);
            if (resourceId2 != -1) {
                ResponsiveViewSpec responsiveViewSpec = new ResponsiveViewSpec(resourceId2);
                responsiveViewSpec.setEffectiveScreenOrientation(integer);
                this.mResponsiveViewGroup.put(Integer.valueOf(resourceId2), responsiveViewSpec);
            }
        } else {
            int integer2 = typedArrayObtainStyledAttributes.getInteger(R.styleable.ResponsiveSpec_hideInScreenMode, 0);
            if (integer2 != 0) {
                List arrayList = (List) this.mResponsiveMapChild.get(view);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    this.mResponsiveMapChild.put(view, arrayList);
                    bindResponseView(view);
                    injectOnHierarchyChangeListener((ViewGroup) view);
                }
                int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.ResponsiveSpec_android_id, -1);
                if (resourceId3 != -1) {
                    arrayList.add(new ResponsiveViewSpec(resourceId3, integer2));
                }
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public final void registerLifecycle(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(new ResponseLifecycleObserver(this));
    }

    public void testNotifyResponseChange(int i) {
        ScreenSpec screenSpec = new ScreenSpec();
        getState().toScreenSpec(screenSpec);
        screenSpec.screenMode = i;
        this.mPageInfo.dispatchResponsiveLayout(null, screenSpec, true);
        Iterator it = this.mResponsiveMap.keySet().iterator();
        while (it.hasNext()) {
            IResponsive iResponsive = (IResponsive) this.mResponsiveMap.get((View) it.next());
            if (iResponsive != null) {
                iResponsive.dispatchResponsiveLayout(null, screenSpec, true);
            }
        }
    }
}
