package miuix.animation.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.onetrack.c.ag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import miuix.animation.IAnimTarget;
import miuix.animation.ValueTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimConfigLink;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ColorProperty;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IntValueProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.LinkNode;
import miuix.animation.utils.LogUtils;

/* JADX INFO: loaded from: classes2.dex */
class TransitionInfo extends LinkNode<TransitionInfo> implements DesignReview {
    public static final byte CANCEL = 3;
    public static final byte END = 4;
    public static final byte INIT = -1;
    public static final byte INVALID = -2;
    public static final byte PREPARE = 0;
    public static final byte SETUP = 1;
    public static final byte START = 2;
    private static final AtomicInteger sIdGenerator = new AtomicInteger();
    public List<AnimTask> animTasks;
    public volatile AnimConfig config;
    public long currentTime;
    public int frameCount;

    @Nullable
    public volatile AnimState from;
    public boolean hasOnStart;
    public boolean hasSendNotifyStart;
    public final int id;
    public volatile Object key;
    public Set<TransitionListener> listenerSetForNotify;
    private final AnimStats mInfoAnimStats;
    public long startTime;
    public byte state;
    public final Object tag;
    public final IAnimTarget target;

    @NonNull
    public volatile AnimState to;
    public List<UpdateInfo> updateList;
    public List<UpdateInfo> updateListForNotify;
    public Map<String, UpdateInfo> updateMap;

    public interface IUpdateInfoCreator {
        @NonNull
        UpdateInfo getUpdateInfo(FloatProperty floatProperty);
    }

    public TransitionInfo(IAnimTarget iAnimTarget, @Nullable AnimState animState, @NonNull AnimState animState2, AnimConfigLink animConfigLink) {
        int iIncrementAndGet = sIdGenerator.incrementAndGet();
        this.id = iIncrementAndGet;
        this.config = new AnimConfig();
        this.state = (byte) -1;
        this.updateMap = new HashMap();
        this.updateListForNotify = new ArrayList();
        this.listenerSetForNotify = new HashSet();
        this.animTasks = new ArrayList();
        this.mInfoAnimStats = new AnimStats();
        this.target = iAnimTarget;
        this.from = getState(animState);
        this.to = getState(animState2);
        Object tag = this.to.getTag();
        this.tag = tag;
        if (animState2.needDuplicate) {
            this.key = tag + String.valueOf(iIncrementAndGet);
        } else {
            this.key = tag;
        }
        this.updateList = null;
        initValueForColorProperty();
        this.config.copy(animState2.getConfig());
        if (animConfigLink != null) {
            animConfigLink.addTo(this.config);
        }
        iAnimTarget.getNotifier().addListeners(Integer.valueOf(iIncrementAndGet), this.config);
    }

    public static void decreasePrepareCountForDelayAnim(AnimStats animStats, AnimStats animStats2, UpdateInfo updateInfo, byte b) {
        int i;
        if (b != 1 || updateInfo.animInfo.delay <= 0 || (i = animStats.prepareCount) <= 0) {
            return;
        }
        animStats.prepareCount = i - 1;
        animStats2.prepareCount--;
    }

    private AnimState getState(AnimState animState) {
        if (animState == null || !animState.needDuplicate) {
            return animState;
        }
        AnimState animState2 = new AnimState();
        animState2.set(animState);
        return animState2;
    }

    private void initValueForColorProperty() {
        if (this.from == null) {
            return;
        }
        Iterator<Object> it = this.to.keySet().iterator();
        while (it.hasNext()) {
            FloatProperty tempProperty = this.to.getTempProperty(it.next());
            if ((tempProperty instanceof ColorProperty) && !AnimValueUtils.isValid(AnimValueUtils.getValueOfTarget(this.target, tempProperty, Double.MAX_VALUE))) {
                double d = this.from.get(this.target, tempProperty);
                if (AnimValueUtils.isValid(d)) {
                    this.target.setIntValue((ColorProperty) tempProperty, (int) d);
                }
            }
        }
    }

    public static void processInitValue(IAnimTarget iAnimTarget, AnimState animState, FloatProperty floatProperty, UpdateInfo updateInfo, boolean z, boolean z2) {
        double init = animState.getInit(iAnimTarget, floatProperty);
        if (z2) {
            LogUtils.debug(" |---- processInitValue initValue=" + init + " property.name=" + floatProperty.getName() + " isCurValueValid=" + z, new Object[0]);
        }
        if (AnimValueUtils.isValid(init)) {
            if (!z) {
                updateInfo.animInfo.startValue = init;
                if (z2) {
                    LogUtils.debug(" |---- processInitValue set startValue with init op=" + ((int) updateInfo.animInfo.op) + " start-v=init-v=" + init + " value=" + updateInfo.animInfo.value, new Object[0]);
                    return;
                }
                return;
            }
            if (z2) {
                LogUtils.debug(" |---- processInitValue target.isIdle()=" + iAnimTarget.isIdle() + " target.isAnimRunning()=" + iAnimTarget.isAnimRunning(new FloatProperty[0]) + " target.isValidFlag()=" + iAnimTarget.isValidFlag(), new Object[0]);
            }
            if (iAnimTarget.animManager.isRunningAnimStateContainsProperty(updateInfo.property)) {
                return;
            }
            AnimInfo animInfo = updateInfo.animInfo;
            animInfo.setToValue = Double.MAX_VALUE;
            animInfo.startValue = init;
            animInfo.value = init;
            if (floatProperty instanceof IntValueProperty) {
                iAnimTarget.setValue(floatProperty, (int) init);
            } else {
                iAnimTarget.setValue(floatProperty, (float) init);
            }
            if (z2) {
                LogUtils.debug(" |---- processInitValue force set startValue / value with init when this property is not running, op=" + ((int) updateInfo.animInfo.op) + " start-v=init-v=" + init + " value=" + updateInfo.animInfo.value, new Object[0]);
            }
        }
    }

    public static void setupAllInfoToTarget(TransitionInfo transitionInfo) {
        IAnimTarget iAnimTarget = transitionInfo.target;
        do {
            iAnimTarget.animManager.setupTransition(transitionInfo);
            transitionInfo = transitionInfo.remove();
        } while (transitionInfo != null);
    }

    public static void tickOnFrame(TransitionInfo transitionInfo, long j) {
        if (transitionInfo.frameCount != 0 || transitionInfo.config.startImmediately) {
            transitionInfo.currentTime += j;
        }
        transitionInfo.frameCount++;
    }

    public boolean containsProperty(FloatProperty floatProperty) {
        return this.to.contains(floatProperty);
    }

    public boolean containsPropertyInUpdateList(FloatProperty floatProperty) {
        return this.updateMap.containsKey(floatProperty.getName());
    }

    public int getAnimCount() {
        return this.state >= 1 ? this.updateMap.size() : this.to.keySet().size();
    }

    @Override // miuix.animation.internal.DesignReview
    public String getDesignInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        if (this.from != null) {
            sb.append("\"");
            sb.append("fromState");
            sb.append("\": ");
            sb.append(this.from.getDesignInfo());
            sb.append(", ");
        }
        sb.append("\"");
        sb.append("toState");
        sb.append("\": ");
        sb.append(this.to.getDesignInfo());
        sb.append(", ");
        sb.append("\"");
        sb.append(ag.a);
        sb.append("\": ");
        sb.append(this.config.getDesignInfo());
        sb.append('}');
        return sb.toString();
    }

    public AnimStats getInfoAnimStats() {
        this.mInfoAnimStats.clear();
        Iterator<AnimTask> it = this.animTasks.iterator();
        while (it.hasNext()) {
            AnimStats.add(this.mInfoAnimStats, it.next().animStats);
        }
        return this.mInfoAnimStats;
    }

    public boolean hasUpdateInfo() {
        List<UpdateInfo> list = this.updateList;
        return (list == null || list.isEmpty()) ? false : true;
    }

    public boolean initUpdateList(IUpdateInfoCreator iUpdateInfoCreator) {
        boolean z;
        Integer num;
        IUpdateInfoCreator iUpdateInfoCreator2 = iUpdateInfoCreator;
        long jNanoTime = System.nanoTime();
        this.startTime = jNanoTime;
        this.currentTime = jNanoTime;
        boolean z2 = false;
        this.frameCount = 0;
        AnimState animState = this.from;
        AnimState animState2 = this.to;
        boolean zIsLogMainEnabled = LogUtils.isLogMainEnabled();
        if (zIsLogMainEnabled) {
            LogUtils.debug("----- initUpdateList, id=" + this.id + ", key=" + this.key + "@" + this.key.hashCode() + ", start-t=" + this.startTime + ",\nf=" + animState + ",\nt=" + animState2 + ",\ntarget=" + this.target + ",\nconfig=" + this.config, new Object[0]);
        }
        ArrayList arrayList = new ArrayList();
        this.updateMap.clear();
        Iterator<Object> it = animState2.keySet().iterator();
        while (it.hasNext()) {
            FloatProperty property = animState2.getProperty(this.target, it.next());
            UpdateInfo updateInfo = iUpdateInfoCreator2.getUpdateInfo(property);
            boolean zHasFlags = CommonUtils.hasFlags(animState2.getConfigFlags(property), 8L);
            if (PredictTask.sCreator == iUpdateInfoCreator2 || ((num = updateInfo.preparedTransitionId) != null && num.intValue() == this.id)) {
                AnimState animState3 = animState;
                boolean zHandleSetToValue = AnimValueUtils.handleSetToValue(updateInfo);
                if (zIsLogMainEnabled) {
                    LogUtils.debug(" |---- start get", "update name=" + updateInfo.property.getName(), "id=" + updateInfo.hashCode(), "needInit=" + zHasFlags, "hasSetTo=" + zHandleSetToValue, " " + updateInfo);
                }
                arrayList.add(updateInfo);
                this.updateMap.put(property.getName(), updateInfo);
                if (updateInfo.animInfo.op == 5) {
                    updateInfo.animInfo.reuse();
                    if (zIsLogMainEnabled) {
                        LogUtils.debug(" |---- reset", "update name=" + updateInfo.property.getName(), "id=" + updateInfo.hashCode(), " " + updateInfo);
                    }
                }
                updateInfo.animInfo.targetValue = animState2.get(this.target, property);
                if (animState3 != null) {
                    updateInfo.animInfo.startValue = animState3.get(this.target, property);
                    it = it;
                    zIsLogMainEnabled = zIsLogMainEnabled;
                } else {
                    double d = updateInfo.animInfo.startValue;
                    double valueOfTarget = AnimValueUtils.getValueOfTarget(this.target, property, d);
                    boolean zIsValid = AnimValueUtils.isValid(valueOfTarget);
                    if (zIsValid) {
                        updateInfo.animInfo.startValue = valueOfTarget;
                    }
                    if (zHasFlags) {
                        processInitValue(this.target, animState2, property, updateInfo, zIsValid, zIsLogMainEnabled);
                    }
                    if (zIsLogMainEnabled) {
                        zIsLogMainEnabled = zIsLogMainEnabled;
                        zIsLogMainEnabled = zIsLogMainEnabled;
                        LogUtils.debug(" |---- f is null op=" + ((int) updateInfo.animInfo.op) + " start-v=" + d + " value=" + valueOfTarget, new Object[0]);
                    }
                }
                zIsLogMainEnabled = zIsLogMainEnabled;
                zIsLogMainEnabled = zIsLogMainEnabled;
                if (updateInfo.animInfo.op == 5) {
                    AnimInfo animInfo = updateInfo.animInfo;
                    animInfo.value = animInfo.startValue;
                    if (zIsLogMainEnabled) {
                        LogUtils.debug(" |---- after reset value <= start-v=" + updateInfo.animInfo.startValue + " " + updateInfo, new Object[0]);
                    }
                }
                updateInfo.preparedTransitionId = null;
                if (zIsLogMainEnabled) {
                    z = false;
                    LogUtils.debug(" |---- finish get " + zHandleSetToValue + ", op=" + ((int) updateInfo.animInfo.op), new Object[0]);
                } else {
                    z = false;
                }
                it = it;
                z2 = z;
                animState = animState3;
                iUpdateInfoCreator2 = iUpdateInfoCreator;
            } else {
                if (zHasFlags && updateInfo.preparedTransitionId != null) {
                    processInitValue(this.target, animState2, property, updateInfo, AnimValueUtils.isValid(AnimValueUtils.getValueOfTarget(this.target, property, updateInfo.animInfo.startValue)), zIsLogMainEnabled);
                }
                if (zIsLogMainEnabled) {
                    LogUtils.debug(" |---- init stop ", "update name=" + updateInfo.property.getName(), "id=" + updateInfo.hashCode(), "needInit=" + zHasFlags, "preparedTransitionId=" + updateInfo.preparedTransitionId, " continue");
                }
                animState = animState;
                z2 = false;
            }
        }
        boolean z3 = z2;
        if (arrayList.isEmpty()) {
            return z3;
        }
        refreshTasks(arrayList, true);
        return true;
    }

    public void refreshTasks(List<UpdateInfo> list, boolean z) {
        boolean zIsLogMainEnabled = LogUtils.isLogMainEnabled();
        this.updateList = list;
        int size = list.size();
        int iMax = Math.max(1, size / 100);
        int iCeil = (int) Math.ceil(size / iMax);
        if (this.animTasks.size() < iMax) {
            for (int size2 = this.animTasks.size(); size2 < iMax; size2++) {
                this.animTasks.add(new AnimTask());
            }
        } else {
            List<AnimTask> list2 = this.animTasks;
            list2.subList(iMax, list2.size()).clear();
        }
        int i = 0;
        for (AnimTask animTask : this.animTasks) {
            animTask.info = this;
            int i2 = i + iCeil > size ? size - i : iCeil;
            int i3 = 0;
            if (this.config.getFocusPropertyCount() > 0) {
                for (int i4 = i; i4 < i + i2; i4++) {
                    if (this.config.isFocusPropertyForComplete(this.updateList.get(i4).property)) {
                        i3++;
                    }
                }
            }
            if (zIsLogMainEnabled) {
                LogUtils.debug(" |---- refreshTasks startPos=" + i + " amount=" + i2 + " config.focusCount=" + this.config.getFocusPropertyCount() + " focusCount=" + i3, new Object[0]);
            }
            animTask.setup(i, i2, i3);
            if (z) {
                animTask.animStats.prepareCount = i2;
            } else {
                animTask.updateAnimStats();
            }
            i += i2;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TransInfo{id=");
        sb.append(this.id);
        sb.append(", key=");
        sb.append(this.key);
        sb.append("@");
        sb.append(this.key.hashCode());
        sb.append(", state=");
        sb.append((int) this.state);
        sb.append(", propSize=");
        sb.append(this.to.keySet().size());
        sb.append(", delay=");
        sb.append(this.config.delay);
        sb.append(", start-t=");
        sb.append(this.startTime);
        sb.append(", target=");
        IAnimTarget iAnimTarget = this.target;
        boolean z = iAnimTarget instanceof ValueTarget;
        Object targetObject = iAnimTarget;
        if (z) {
            targetObject = iAnimTarget.getTargetObject();
        }
        sb.append(targetObject);
        sb.append(", next=");
        sb.append(this.next);
        sb.append('}');
        return sb.toString();
    }
}
