package miuix.animation.listener;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import miuix.animation.FolmeFactory;
import miuix.animation.IAnimTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.internal.FolmeEngine;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.ObjectPool;

/* JADX INFO: loaded from: classes2.dex */
public class ListenerNotifier {
    final Map<Object, List<TransitionListener>> mListenerMap = new ConcurrentHashMap();
    final IAnimTarget mTarget;
    static final BeginNotifier sBegin = new BeginNotifier();
    static final UpdateNotifier sUpdate = new UpdateNotifier();
    static final CancelNotifier sCancelAll = new CancelNotifier();
    static final EndNotifier sEndAll = new EndNotifier();

    public static class BeginNotifier implements INotifier {
        @Override // miuix.animation.listener.ListenerNotifier.INotifier
        public void doNotify(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
            transitionListener.onBegin(obj);
            if (collection != null) {
                transitionListener.onBegin(obj, collection);
            }
        }
    }

    public static class CancelNotifier implements INotifier {
        @Override // miuix.animation.listener.ListenerNotifier.INotifier
        public void doNotify(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
            transitionListener.onCancel(obj);
        }
    }

    public static class EndNotifier implements INotifier {
        @Override // miuix.animation.listener.ListenerNotifier.INotifier
        public void doNotify(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
            transitionListener.onComplete(obj);
        }
    }

    public interface INotifier {
        void doNotify(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo);
    }

    public static class MassUpdateNotifier implements INotifier {
        static final List<UpdateInfo> sEmptyList = new ArrayList();

        @Override // miuix.animation.listener.ListenerNotifier.INotifier
        public void doNotify(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
            transitionListener.onUpdate(obj, sEmptyList);
        }
    }

    public static class PropertyBeginNotifier implements INotifier {
        @Override // miuix.animation.listener.ListenerNotifier.INotifier
        public void doNotify(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
            transitionListener.onBegin(obj, collection);
        }
    }

    public static class UpdateNotifier implements INotifier {
        @Override // miuix.animation.listener.ListenerNotifier.INotifier
        public void doNotify(Object obj, TransitionListener transitionListener, Collection<UpdateInfo> collection, UpdateInfo updateInfo) {
            transitionListener.onUpdate(obj, collection);
        }
    }

    public ListenerNotifier(IAnimTarget iAnimTarget) {
        this.mTarget = iAnimTarget;
    }

    private List<TransitionListener> getListenerSet(Object obj) {
        List<TransitionListener> arrayList = this.mListenerMap.get(obj);
        if (arrayList == null) {
            FolmeEngine engine = FolmeFactory.getEngine();
            arrayList = engine != null ? (List) ObjectPool.acquire(engine.getObjPool(), ArrayList.class, new Object[0]) : new ArrayList<>();
            this.mListenerMap.put(obj, arrayList);
        }
        return arrayList;
    }

    private void notify(Object obj, Object obj2, INotifier iNotifier, Collection<UpdateInfo> collection, UpdateInfo updateInfo, Set<TransitionListener> set) {
        List<TransitionListener> list = this.mListenerMap.get(obj);
        if (list == null || list.isEmpty()) {
            return;
        }
        notifyListenerSet(obj2, list, iNotifier, collection, updateInfo, set);
    }

    private static void notifyListenerSet(Object obj, List<TransitionListener> list, INotifier iNotifier, Collection<UpdateInfo> collection, UpdateInfo updateInfo, Set<TransitionListener> set) {
        set.addAll(list);
        for (TransitionListener transitionListener : set) {
            if (transitionListener == null) {
                Log.e(CommonUtils.TAG, "listener null tag=" + obj);
            } else {
                iNotifier.doNotify(obj, transitionListener, collection, updateInfo);
            }
        }
        set.clear();
    }

    public boolean addListeners(Object obj, AnimConfig animConfig) {
        if (animConfig.listeners.isEmpty()) {
            return false;
        }
        CommonUtils.addTo(animConfig.listeners, getListenerSet(obj));
        return true;
    }

    public void notifyBegin(Object obj, Object obj2, Collection<UpdateInfo> collection, Set<TransitionListener> set) {
        notify(obj, obj2, sBegin, collection, null, set);
    }

    public void notifyCancelAll(Object obj, Object obj2, Set<TransitionListener> set) {
        notify(obj, obj2, sCancelAll, null, null, set);
    }

    public void notifyEndAll(Object obj, Object obj2, Set<TransitionListener> set) {
        notify(obj, obj2, sEndAll, null, null, set);
    }

    public void notifyUpdate(Object obj, Object obj2, Collection<UpdateInfo> collection, Set<TransitionListener> set) {
        notify(obj, obj2, sUpdate, collection, null, set);
    }

    public void removeListeners() {
        FolmeEngine engine = FolmeFactory.getEngine();
        if (engine != null) {
            ObjectPool.release(engine.getObjPool(), this.mListenerMap.values());
        }
        this.mListenerMap.clear();
    }

    public void removeListeners(Object obj) {
        List<TransitionListener> listRemove = this.mListenerMap.remove(obj);
        FolmeEngine engine = FolmeFactory.getEngine();
        if (engine != null) {
            ObjectPool.release(engine.getObjPool(), listRemove);
        }
    }
}
