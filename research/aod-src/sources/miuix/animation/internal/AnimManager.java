package miuix.animation.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import miuix.animation.IAnimTarget;
import miuix.animation.base.AnimConfigLink;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.ListenerNotifier;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.LogUtils;

/* JADX INFO: loaded from: classes2.dex */
public class AnimManager implements TransitionInfo.IUpdateInfoCreator {
    IAnimTarget mTarget;
    private List<UpdateInfo> mUpdateInfoDiff;
    final ConcurrentHashMap<Integer, Object> mPrepareInfo = new ConcurrentHashMap<>();
    final ConcurrentHashMap<Object, TransitionInfo> mRunningInfo = new ConcurrentHashMap<>();
    final ConcurrentHashMap<FloatProperty, UpdateInfo> mUpdateMap = new ConcurrentHashMap<>();
    final ConcurrentLinkedQueue<TransitionInfo> mWaitState = new ConcurrentLinkedQueue<>();
    final Map<Integer, TransitionInfo> mTempTransMap = new ConcurrentHashMap();
    final Map<Integer, TransitionInfo> mTempTransForUpdateMap = new ConcurrentHashMap();
    public ConcurrentHashMap<Long, TargetHandler> mObserverHandlerMap = new ConcurrentHashMap<>();
    private int mRunningAnimCount = 0;

    private void clearRunningInfo() {
        for (TransitionInfo transitionInfo : this.mRunningInfo.values()) {
            this.mTempTransMap.remove(Integer.valueOf(transitionInfo.id));
            this.mTempTransForUpdateMap.remove(Integer.valueOf(transitionInfo.id));
        }
        this.mRunningInfo.clear();
        this.mRunningAnimCount = 0;
    }

    private boolean containProperties(TransitionInfo transitionInfo, FloatProperty... floatPropertyArr) {
        for (FloatProperty floatProperty : floatPropertyArr) {
            if (transitionInfo.containsPropertyInUpdateList(floatProperty)) {
                return true;
            }
        }
        return false;
    }

    private boolean containPropertyInAnimState(TransitionInfo transitionInfo, FloatProperty floatProperty) {
        return transitionInfo.to.contains(floatProperty);
    }

    private static void doNotifyUpdate(IAnimTarget iAnimTarget, Object obj, Object obj2, List<UpdateInfo> list, Set<TransitionListener> set) {
        iAnimTarget.getNotifier().notifyUpdate(obj, obj2, list, set);
    }

    private boolean pendState(TransitionInfo transitionInfo) {
        if (!CommonUtils.hasFlags(transitionInfo.to.flags, 1L)) {
            return false;
        }
        this.mWaitState.add(transitionInfo);
        return true;
    }

    private int removeSameAnim(TransitionInfo transitionInfo) {
        int size = 0;
        for (TransitionInfo transitionInfo2 : this.mRunningInfo.values()) {
            if (transitionInfo2 != transitionInfo) {
                ArrayList<UpdateInfo> arrayList = new ArrayList(transitionInfo2.updateList);
                if (this.mUpdateInfoDiff == null) {
                    this.mUpdateInfoDiff = new ArrayList();
                }
                transitionInfo2.updateMap.clear();
                for (UpdateInfo updateInfo : arrayList) {
                    if (!transitionInfo.containsPropertyInUpdateList(updateInfo.property)) {
                        this.mUpdateInfoDiff.add(updateInfo);
                        transitionInfo2.updateMap.put(updateInfo.property.getName(), updateInfo);
                    }
                }
                if (this.mUpdateInfoDiff.isEmpty()) {
                    if (transitionInfo2.id != transitionInfo.id) {
                        if (LogUtils.isLogMainEnabled()) {
                            LogUtils.debug("----- notifyEndOrCancel-REPLACE in removeSameAnim, " + transitionInfo2, new Object[0]);
                        }
                        notifyTransitionEndOrCancel(transitionInfo2, 5, 4);
                    }
                } else if (this.mUpdateInfoDiff.size() != arrayList.size()) {
                    transitionInfo2.refreshTasks(this.mUpdateInfoDiff, false);
                    size = this.mUpdateInfoDiff.size() - arrayList.size();
                    this.mUpdateInfoDiff = null;
                } else {
                    this.mUpdateInfoDiff.clear();
                }
            }
        }
        return size;
    }

    public void addToTransitionInfoList(List<TransitionInfo> list) {
        if (LogUtils.isLogMoreEnable()) {
            LogUtils.debug("++++ addToTransitionInfoList before add", "list.size=" + list.size(), "target=" + this.mTarget);
        }
        for (TransitionInfo transitionInfo : this.mRunningInfo.values()) {
            if (transitionInfo.hasUpdateInfo()) {
                list.add(transitionInfo);
            }
        }
        if (LogUtils.isLogMoreEnable()) {
            LogUtils.debug("++++ addToTransitionInfoList after add", "list.size=" + list.size(), "target=" + this.mTarget);
        }
    }

    public void cancel() {
        this.mWaitState.clear();
        Iterator<Object> it = this.mPrepareInfo.values().iterator();
        while (it.hasNext()) {
            cancelPrepareTransition((TransitionInfo) it.next());
        }
        Iterator<TransitionInfo> it2 = this.mRunningInfo.values().iterator();
        while (it2.hasNext()) {
            notifyTransitionEndOrCancel(it2.next(), 2, 4);
        }
    }

    public void cancelPrepareTransition(TransitionInfo transitionInfo) {
        this.mPrepareInfo.remove(Integer.valueOf(transitionInfo.id));
        if (!transitionInfo.hasSendNotifyStart) {
            notifyTransitionBegin(transitionInfo, transitionInfo.updateList, false);
        }
        AnimState animState = transitionInfo.to;
        Iterator<Object> it = animState.keySet().iterator();
        while (it.hasNext()) {
            getUpdateInfo(animState.getProperty(this.mTarget, it.next())).preparedTransitionId = null;
            transitionInfo.state = (byte) 3;
            transitionInfo.hasSendNotifyStart = false;
            transitionInfo.hasOnStart = false;
        }
        notifyTransitionEndOrCancel(transitionInfo, 2, 4);
    }

    public void clear() {
        if (LogUtils.isLogMainEnabled()) {
            LogUtils.debug("- AnimManager.clear() " + this.mTarget + LogUtils.getStackTrace(5), new Object[0]);
        }
        this.mUpdateMap.clear();
        clearRunningInfo();
        this.mWaitState.clear();
        this.mPrepareInfo.clear();
        this.mTempTransMap.clear();
        this.mTempTransForUpdateMap.clear();
        this.mObserverHandlerMap.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void endPrepareTransition(TransitionInfo transitionInfo) {
        transitionInfo.initUpdateList(this);
        this.mPrepareInfo.remove(Integer.valueOf(transitionInfo.id));
        if (!transitionInfo.hasSendNotifyStart) {
            notifyTransitionBegin(transitionInfo, transitionInfo.updateList, false);
        }
        AnimState animState = transitionInfo.to;
        transitionInfo.updateListForNotify.clear();
        Iterator<Object> it = animState.keySet().iterator();
        while (it.hasNext()) {
            FloatProperty property = animState.getProperty(this.mTarget, it.next());
            UpdateInfo updateInfo = getUpdateInfo(property);
            double d = animState.get(this.mTarget, property);
            if (property instanceof IIntValueProperty) {
                this.mTarget.setIntValue((IIntValueProperty) property, (int) d);
            } else {
                this.mTarget.setValue(property, (float) d);
            }
            transitionInfo.updateListForNotify.add(updateInfo);
            updateInfo.preparedTransitionId = null;
            transitionInfo.state = (byte) 4;
            transitionInfo.hasSendNotifyStart = false;
            transitionInfo.hasOnStart = false;
        }
        notifyTransitionUpdate(transitionInfo, transitionInfo.updateListForNotify);
        transitionInfo.updateListForNotify.clear();
        notifyTransitionEndOrCancel(transitionInfo, 2, 3);
    }

    public TransitionInfo getPrepareTransInfoByToState(AnimState animState) {
        boolean zIsLogMoreEnable = LogUtils.isLogMoreEnable();
        Iterator<Object> it = this.mPrepareInfo.values().iterator();
        while (it.hasNext()) {
            TransitionInfo transitionInfo = (TransitionInfo) it.next();
            if (transitionInfo.to == animState) {
                if (zIsLogMoreEnable) {
                    LogUtils.debug("getPrepareTransInfoByToState info.to == toState: " + animState, new Object[0]);
                    return transitionInfo;
                }
            } else if (animState.needDuplicate && transitionInfo.to.getTag().equals(animState.getTag())) {
                if (zIsLogMoreEnable) {
                    LogUtils.debug("getPrepareTransInfoByToState info.to != toState: " + transitionInfo.to, new Object[0]);
                }
            }
            return transitionInfo;
        }
        return null;
    }

    @Nullable
    public TransitionInfo getRunningTransInfoByToState(AnimState animState) {
        boolean zIsLogMoreEnable = LogUtils.isLogMoreEnable();
        for (TransitionInfo transitionInfo : this.mRunningInfo.values()) {
            if (transitionInfo.to == animState) {
                if (zIsLogMoreEnable) {
                    LogUtils.debug("getRunningTransInfoByToState info.to == toState: " + animState, new Object[0]);
                    return transitionInfo;
                }
            } else if (animState.needDuplicate && transitionInfo.to.getTag().equals(animState.getTag())) {
                if (zIsLogMoreEnable) {
                    LogUtils.debug("getRunningTransInfoByToState info.to != toState: " + transitionInfo.to, new Object[0]);
                }
            }
            return transitionInfo;
        }
        return null;
    }

    public int getRunningTransitionCount() {
        return this.mRunningInfo.size();
    }

    public int getTotalAnimCount() {
        return this.mRunningAnimCount;
    }

    @Override // miuix.animation.internal.TransitionInfo.IUpdateInfoCreator
    @NonNull
    public synchronized UpdateInfo getUpdateInfo(FloatProperty floatProperty) {
        UpdateInfo updateInfo;
        UpdateInfo updateInfoPutIfAbsent;
        updateInfo = this.mUpdateMap.get(floatProperty);
        if (updateInfo == null && (updateInfoPutIfAbsent = this.mUpdateMap.putIfAbsent(floatProperty, (updateInfo = new UpdateInfo(floatProperty)))) != null) {
            updateInfo = updateInfoPutIfAbsent;
        }
        return updateInfo;
    }

    public double getVelocity(FloatProperty floatProperty) {
        return getUpdateInfo(floatProperty).velocity;
    }

    public boolean hasAnimSetup() {
        return !this.mPrepareInfo.isEmpty();
    }

    public boolean isAnimRunning(FloatProperty... floatPropertyArr) {
        if (CommonUtils.isArrayEmpty(floatPropertyArr) && (!this.mRunningInfo.isEmpty() || !this.mWaitState.isEmpty())) {
            return true;
        }
        Iterator<TransitionInfo> it = this.mRunningInfo.values().iterator();
        while (it.hasNext()) {
            if (containProperties(it.next(), floatPropertyArr)) {
                return true;
            }
        }
        return false;
    }

    public boolean isRunningAnimStateContainsProperty(FloatProperty floatProperty) {
        if (floatProperty == null) {
            return false;
        }
        Iterator<TransitionInfo> it = this.mRunningInfo.values().iterator();
        while (it.hasNext()) {
            if (containPropertyInAnimState(it.next(), floatProperty)) {
                return true;
            }
        }
        return false;
    }

    public void notifyRemoveWait() {
        if (LogUtils.isLogMoreEnable()) {
            LogUtils.debug("- notifyRemoveWait target:" + this.mTarget + LogUtils.getStackTrace(5), new Object[0]);
        }
        TargetHandler handler = this.mTarget.getHandler();
        if (handler == null || handler.isInTargetThread()) {
            onRemoveWait();
        } else {
            handler.sendEmptyMessage(3);
        }
    }

    public void notifyReplaceTargetListeners(TransitionInfo transitionInfo) {
        TargetHandler handler = this.mTarget.getHandler();
        if (handler == null || handler.isInTargetThread()) {
            onReplaceListeners(transitionInfo);
        } else {
            this.mTempTransMap.put(Integer.valueOf(transitionInfo.id), transitionInfo);
            handler.obtainMessage(4, transitionInfo.id, 0).sendToTarget();
        }
    }

    public void notifyTransitionBegin(TransitionInfo transitionInfo, @Nullable List<UpdateInfo> list, boolean z) {
        TargetHandler handler = this.mTarget.getHandler();
        transitionInfo.hasSendNotifyStart = true;
        transitionInfo.state = (byte) 2;
        if (transitionInfo.config.getObserverLooper() != null) {
            ArrayList arrayList = list == null ? null : new ArrayList(list);
            TargetHandler targetHandler = this.mObserverHandlerMap.get(Long.valueOf(transitionInfo.config.getObserverLooper().getThread().getId()));
            if (targetHandler != null) {
                this.mTempTransMap.put(Integer.valueOf(transitionInfo.id), transitionInfo);
                targetHandler.obtainMessage(0, transitionInfo.id, z ? 1 : 0, arrayList).sendToTarget();
                return;
            }
        }
        if (handler == null || handler.isInTargetThread()) {
            onStart(transitionInfo, list, z);
            return;
        }
        Object arrayList2 = list != null ? new ArrayList(list) : null;
        this.mTempTransMap.put(Integer.valueOf(transitionInfo.id), transitionInfo);
        handler.obtainMessage(0, transitionInfo.id, z ? 1 : 0, arrayList2).sendToTarget();
    }

    public void notifyTransitionEndOrCancel(final TransitionInfo transitionInfo, int i, int i2) {
        TargetHandler targetHandler;
        TargetHandler handler = this.mTarget.getHandler();
        if (transitionInfo.state < 2) {
            if (LogUtils.isLogMainEnabled()) {
                LogUtils.debug(String.format("------ do notifyTransEndOrCancel before start, msg=%d, op=%d, info=%s", Integer.valueOf(i), Integer.valueOf(i2), transitionInfo), new Object[0]);
            }
            removeRunningInfo(transitionInfo);
            this.mTarget.post(new Runnable() { // from class: miuix.animation.internal.AnimManager.1
                @Override // java.lang.Runnable
                public void run() {
                    if (LogUtils.isLogMoreEnable()) {
                        LogUtils.debug("------ do notifyTransEndOrCancel before start -> removeListeners info=" + transitionInfo, new Object[0]);
                    }
                    transitionInfo.hasSendNotifyStart = true;
                    ListenerNotifier notifier = AnimManager.this.mTarget.getNotifier();
                    Integer numValueOf = Integer.valueOf(transitionInfo.id);
                    TransitionInfo transitionInfo2 = transitionInfo;
                    notifier.notifyBegin(numValueOf, transitionInfo2.tag, null, transitionInfo2.listenerSetForNotify);
                    ListenerNotifier notifier2 = AnimManager.this.mTarget.getNotifier();
                    Integer numValueOf2 = Integer.valueOf(transitionInfo.id);
                    TransitionInfo transitionInfo3 = transitionInfo;
                    notifier2.notifyCancelAll(numValueOf2, transitionInfo3.tag, transitionInfo3.listenerSetForNotify);
                    AnimManager.this.mTarget.getNotifier().removeListeners(Integer.valueOf(transitionInfo.id));
                }
            });
            return;
        }
        if (LogUtils.isLogMainEnabled()) {
            LogUtils.debug(String.format("------ do notifyTransEndOrCancel after start, msg=%d, op=%d, info=%s", Integer.valueOf(i), Integer.valueOf(i2), transitionInfo), new Object[0]);
        }
        removeRunningInfo(transitionInfo);
        if (transitionInfo.config.getObserverLooper() != null && (targetHandler = this.mObserverHandlerMap.get(Long.valueOf(transitionInfo.config.getObserverLooper().getThread().getId()))) != null) {
            this.mTempTransMap.put(Integer.valueOf(transitionInfo.id), transitionInfo);
            targetHandler.obtainMessage(i, transitionInfo.id, i2, transitionInfo).sendToTarget();
        } else if (handler != null && !handler.isInTargetThread()) {
            this.mTempTransMap.put(Integer.valueOf(transitionInfo.id), transitionInfo);
            handler.obtainMessage(i, transitionInfo.id, i2, transitionInfo).sendToTarget();
        } else if (i == 5) {
            onReplaced(transitionInfo);
        } else {
            onEnd(transitionInfo, i2);
        }
    }

    public void notifyTransitionUpdate(TransitionInfo transitionInfo, @Nullable List<UpdateInfo> list) {
        TargetHandler handler = this.mTarget.getHandler();
        if (transitionInfo.config.getObserverLooper() != null) {
            ArrayList arrayList = list == null ? null : new ArrayList(list);
            TargetHandler targetHandler = this.mObserverHandlerMap.get(Long.valueOf(transitionInfo.config.getObserverLooper().getThread().getId()));
            if (targetHandler != null) {
                this.mTempTransForUpdateMap.put(Integer.valueOf(transitionInfo.id), transitionInfo);
                targetHandler.obtainMessage(1, transitionInfo.id, 0, arrayList).sendToTarget();
                return;
            }
        }
        if (handler == null || handler.isInTargetThread()) {
            onUpdate(transitionInfo, list);
            return;
        }
        ArrayList arrayList2 = list != null ? new ArrayList(list) : null;
        this.mTempTransForUpdateMap.put(Integer.valueOf(transitionInfo.id), transitionInfo);
        handler.obtainMessage(1, transitionInfo.id, 0, arrayList2).sendToTarget();
    }

    public void onEnd(final TransitionInfo transitionInfo, int i) {
        boolean zIsLogMainEnabled = LogUtils.isLogMainEnabled();
        if (zIsLogMainEnabled) {
            LogUtils.debug(">>> " + (i == 4 ? "onCancel" : "onEnd") + " info.id=" + transitionInfo.id, "info.key=" + transitionInfo.key + "@" + transitionInfo.key.hashCode(), "info.startTime=" + transitionInfo.startTime);
        }
        if (zIsLogMainEnabled) {
            LogUtils.debug(">>> " + (i == 4 ? "onCancel" : "onEnd") + " finish notify info.id=" + transitionInfo.id, "info.key=" + transitionInfo.key + "@" + transitionInfo.key.hashCode());
        }
        if (i == 4) {
            transitionInfo.target.getNotifier().notifyCancelAll(Integer.valueOf(transitionInfo.id), transitionInfo.tag, transitionInfo.listenerSetForNotify);
        } else {
            transitionInfo.target.getNotifier().notifyEndAll(Integer.valueOf(transitionInfo.id), transitionInfo.tag, transitionInfo.listenerSetForNotify);
        }
        this.mTarget.post(new Runnable() { // from class: miuix.animation.internal.AnimManager.2
            @Override // java.lang.Runnable
            public void run() {
                AnimManager.this.mTarget.getNotifier().removeListeners(Integer.valueOf(transitionInfo.id));
            }
        });
        transitionInfo.state = (byte) 4;
        transitionInfo.hasSendNotifyStart = false;
        transitionInfo.hasOnStart = false;
    }

    public void onRemoveWait() {
        if (LogUtils.isLogMainEnabled()) {
            LogUtils.debug(">>> onRemoveWaits", "mWaitState.size = " + this.mTarget.animManager.mWaitState.size());
        }
        this.mTarget.animManager.mWaitState.clear();
    }

    public void onReplaceListeners(TransitionInfo transitionInfo) {
        if (LogUtils.isLogMoreEnable()) {
            LogUtils.debug(">>> onReplaceListeners info.id=" + transitionInfo.id + ", info.key=" + transitionInfo.key, new Object[0]);
        }
        this.mTarget.getNotifier().removeListeners(Integer.valueOf(transitionInfo.id));
        this.mTarget.getNotifier().addListeners(Integer.valueOf(transitionInfo.id), transitionInfo.config);
    }

    public void onReplaced(final TransitionInfo transitionInfo) {
        if (LogUtils.isLogMainEnabled()) {
            LogUtils.debug(">>> onReplaced info.id=" + transitionInfo.id + ", info.key=" + transitionInfo.key + "@" + transitionInfo.key.hashCode() + ", info.startTime=" + transitionInfo.startTime, new Object[0]);
        }
        this.mTarget.getNotifier().notifyCancelAll(Integer.valueOf(transitionInfo.id), transitionInfo.tag, transitionInfo.listenerSetForNotify);
        this.mTarget.post(new Runnable() { // from class: miuix.animation.internal.AnimManager.3
            @Override // java.lang.Runnable
            public void run() {
                AnimManager.this.mTarget.getNotifier().removeListeners(Integer.valueOf(transitionInfo.id));
            }
        });
        transitionInfo.state = (byte) 3;
        transitionInfo.hasSendNotifyStart = false;
        transitionInfo.hasOnStart = false;
    }

    public void onStart(TransitionInfo transitionInfo, @Nullable List<UpdateInfo> list, boolean z) {
        boolean zIsLogMainEnabled = LogUtils.isLogMainEnabled();
        if (transitionInfo.hasOnStart) {
            if (zIsLogMainEnabled) {
                LogUtils.debug(">>> onStart warning!! this transition has notified start: info.id=" + transitionInfo.id + ", info.key=" + transitionInfo.key, new Object[0]);
                return;
            }
            return;
        }
        if (zIsLogMainEnabled) {
            LogUtils.debug(">>> onStart info.id=" + transitionInfo.id + ", info.key=" + transitionInfo.key + ", info.startTime=" + transitionInfo.startTime + ", mRunningInfo.contains=" + transitionInfo.target.animManager.mRunningInfo.contains(transitionInfo) + ", target=" + this.mTarget, new Object[0]);
        }
        transitionInfo.hasOnStart = true;
        transitionInfo.updateListForNotify.clear();
        if (list == null && transitionInfo.updateList != null) {
            list = new ArrayList<>(transitionInfo.updateList);
        }
        this.mTarget.getNotifier().notifyBegin(Integer.valueOf(transitionInfo.id), transitionInfo.tag, list, transitionInfo.listenerSetForNotify);
        if (z) {
            onUpdate(transitionInfo, list);
        }
    }

    public void onUpdate(TransitionInfo transitionInfo, @Nullable List<UpdateInfo> list) {
        boolean zIsLogMainEnabled = LogUtils.isLogMainEnabled();
        if (zIsLogMainEnabled) {
            LogUtils.debug(">>> onUpdate info.id=" + transitionInfo.id, "info.key=" + transitionInfo.key, "update.size=" + transitionInfo.updateList.size(), "target=" + this.mTarget);
        }
        if (list == null) {
            list = new ArrayList<>(transitionInfo.updateList);
            if (zIsLogMainEnabled) {
                LogUtils.debug(">>> onUpdate warning!! infoUpdateList is null!!info.id=" + transitionInfo.id, new Object[0]);
            }
        }
        doNotifyUpdate(transitionInfo.target, Integer.valueOf(transitionInfo.id), transitionInfo.tag, list, transitionInfo.listenerSetForNotify);
    }

    public boolean prepareAnim(TransitionInfo transitionInfo) {
        return prepareAnim(transitionInfo, false);
    }

    public boolean prepareAnim(TransitionInfo transitionInfo, boolean z) {
        if (LogUtils.isLogMoreEnable()) {
            LogUtils.debug("++ AnimManager.prepareAnim isQueue " + z + " of " + this.mTarget, new Object[0]);
        }
        if (!z && pendState(transitionInfo)) {
            if (LogUtils.isLogMoreEnable()) {
                LogUtils.debug("++ AnimManager.startAnim, pendState info.id=" + transitionInfo.id, new Object[0]);
            }
            return false;
        }
        this.mPrepareInfo.put(Integer.valueOf(transitionInfo.id), transitionInfo);
        transitionInfo.state = (byte) 0;
        Iterator<Object> it = transitionInfo.to.keySet().iterator();
        while (it.hasNext()) {
            getUpdateInfo(transitionInfo.to.getProperty(this.mTarget, it.next())).preparedTransitionId = Integer.valueOf(transitionInfo.id);
        }
        return true;
    }

    public void removeRunningInfo(TransitionInfo transitionInfo) {
        this.mRunningInfo.remove(Integer.valueOf(transitionInfo.id));
        this.mRunningAnimCount -= transitionInfo.getAnimCount();
        boolean zIsAnimRunning = isAnimRunning(new FloatProperty[0]);
        if (!zIsAnimRunning && !hasAnimSetup()) {
            this.mUpdateMap.clear();
        }
        if (LogUtils.isLogMoreEnable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("----- removeRunningInfo");
            sb.append(" ,id=");
            sb.append(transitionInfo.id);
            sb.append(" ,key=");
            sb.append(transitionInfo.key);
            sb.append(" ");
            sb.append(transitionInfo.key.hashCode());
            sb.append(" ,runningInfo.size=");
            sb.append(this.mRunningInfo.size());
            sb.append(" ,isAnimRunning=");
            sb.append(zIsAnimRunning);
            sb.append(" ,target=");
            sb.append(this.mTarget);
            if (LogUtils.isLogDetailEnable()) {
                sb.append('\n');
                if (this.mRunningInfo.size() > 0) {
                    for (TransitionInfo transitionInfo2 : this.mRunningInfo.values()) {
                        sb.append(" |-- after remove resetRunInfo = ");
                        sb.append(transitionInfo2);
                        sb.append('\n');
                    }
                }
            }
            LogUtils.debug(sb.toString(), new Object[0]);
        }
    }

    public void setTarget(IAnimTarget iAnimTarget) {
        this.mTarget = iAnimTarget;
    }

    public boolean setTo(AnimState animState, @Nullable AnimConfigLink animConfigLink) {
        return setTo(animState, animConfigLink, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public boolean setTo(AnimState animState, @Nullable AnimConfigLink animConfigLink, boolean z) {
        boolean zIsLogMainEnabled = LogUtils.isLogMainEnabled();
        if (zIsLogMainEnabled) {
            LogUtils.debug("setTo start target=" + this.mTarget, "\nto=\t" + animState + "\nconfig=" + animConfigLink);
        }
        Iterator<Object> it = animState.keySet().iterator();
        while (it.hasNext()) {
            FloatProperty tempProperty = animState.getTempProperty(it.next());
            double d = animState.get(this.mTarget, tempProperty);
            UpdateInfo updateInfo = getUpdateInfo(tempProperty);
            if (zIsLogMainEnabled) {
                LogUtils.debug("setTo setToValue=" + d + " " + tempProperty, new Object[0]);
            }
            AnimInfo animInfo = updateInfo.animInfo;
            animInfo.startValue = d;
            animInfo.setToValue = d;
            updateInfo.preparedTransitionId = null;
            if (tempProperty instanceof IIntValueProperty) {
                this.mTarget.doSetIntValue((IIntValueProperty) tempProperty, (int) d);
            } else {
                if (updateInfo.useInt) {
                    LogUtils.debug("setTo Warning!! the property is not intValueProperty but useInt is true:" + tempProperty, new Object[0]);
                }
                this.mTarget.doSetValue(tempProperty, (float) d);
            }
            if (z) {
                this.mTarget.setVelocity(tempProperty, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
            } else {
                this.mTarget.trackVelocity(tempProperty, d);
            }
        }
        this.mTarget.setToNotify(animState, animConfigLink);
        if (!zIsLogMainEnabled) {
            return true;
        }
        LogUtils.debug("setTo done", new Object[0]);
        return true;
    }

    public synchronized void setVelocity(FloatProperty floatProperty, float f) {
        getUpdateInfo(floatProperty).velocity = f;
    }

    public void setupTransition(TransitionInfo transitionInfo) {
        boolean zIsLogMainEnabled = LogUtils.isLogMainEnabled();
        if (zIsLogMainEnabled) {
            StringBuilder sb = new StringBuilder();
            sb.append("----- setupTransition ");
            sb.append(transitionInfo);
            if (LogUtils.isLogDetailEnable()) {
                sb.append(LogUtils.getStackTrace(7));
            }
            LogUtils.debug(sb.toString(), new Object[0]);
        }
        byte b = transitionInfo.state;
        if (b >= 3) {
            if (zIsLogMainEnabled) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("----- setupTransition return because this transition has cancel or end before start: " + ((int) transitionInfo.state));
                sb2.append(transitionInfo.config.getObserverLooper());
                LogUtils.debug(sb2.toString(), new Object[0]);
                return;
            }
            return;
        }
        boolean z = b == 2;
        if (b < 1) {
            this.mPrepareInfo.remove(Integer.valueOf(transitionInfo.id));
            if (transitionInfo.config.getObserverLooper() != null && !this.mObserverHandlerMap.containsKey(Long.valueOf(transitionInfo.config.getObserverLooper().getThread().getId()))) {
                this.mObserverHandlerMap.put(Long.valueOf(transitionInfo.config.getObserverLooper().getThread().getId()), new TargetHandler(transitionInfo.config.getObserverLooper(), this.mTarget));
                if (zIsLogMainEnabled) {
                    LogUtils.debug("----- setupTransition create TargetHandler for Looper " + transitionInfo.config.getObserverLooper(), new Object[0]);
                }
            }
        }
        if (!transitionInfo.initUpdateList(this)) {
            notifyTransitionEndOrCancel(transitionInfo, 2, 4);
            if (zIsLogMainEnabled) {
                LogUtils.debug("----- setupTransition info.id=" + transitionInfo.id + " has cancel after setup because all properties have handled setTo.", new Object[0]);
                return;
            }
            return;
        }
        this.mRunningInfo.put(Integer.valueOf(transitionInfo.id), transitionInfo);
        int animCount = this.mRunningAnimCount + transitionInfo.getAnimCount();
        this.mRunningAnimCount = animCount;
        if (transitionInfo.state < 1) {
            transitionInfo.state = (byte) 1;
        }
        this.mRunningAnimCount = animCount + removeSameAnim(transitionInfo);
        if (transitionInfo.config.listeners.isEmpty() || !z) {
            return;
        }
        notifyReplaceTargetListeners(transitionInfo);
    }
}
