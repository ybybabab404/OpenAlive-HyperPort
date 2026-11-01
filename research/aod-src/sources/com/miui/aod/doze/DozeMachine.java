package com.miui.aod.doze;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.core.util.Preconditions;
import com.android.systemui.plugins.DozeServicePlugin;
import com.miui.aod.DozeHost;
import com.miui.aod.Utils;
import com.miui.aod.flip.FlipLinkageStyleController;
import com.miui.aod.util.Assert;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.CommonUtils;
import com.miui.aod.widget.AODSettings;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"RestrictedApi"})
public class DozeMachine {
    public int mAodReason;
    public final Context mContext;
    public final Service mDozeService;
    public final DozeHost mHost;
    public Part[] mParts;
    public int mPulseReason;
    public final WakeLock mWakeLock;
    public final ArrayList mQueuedRequests = new ArrayList();
    public State mState = State.UNINITIALIZED;
    public boolean mWakeLockHeldForCurrentState = false;

    /* JADX INFO: renamed from: com.miui.aod.doze.DozeMachine$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$aod$doze$DozeMachine$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$com$miui$aod$doze$DozeMachine$State = iArr;
            try {
                iArr[State.DOZE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[State.DOZE_AOD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[State.DOZE_AOD_PAUSED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[State.DOZE_AOD_PAUSING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[State.DOZE_REQUEST_PULSE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[State.DOZE_PULSING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[State.UNINITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[State.INITIALIZED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[State.FINISH.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[State.DOZE_PULSE_DONE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[State.DOZE_INTO_COVERMODE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    public interface Part {
        default void destroy() {
        }

        default boolean onRequestFocusView(int i, View view, Bundle bundle) {
            return false;
        }

        default void onScreenState(int i) {
        }

        default Map onSystemUIAction(int i, Bundle bundle) {
            return null;
        }

        default void preDestroyForChange() {
        }

        default void rebindForChange() {
        }

        void transitionTo(State state, State state2);
    }

    public interface Service {

        public static class Delegate implements Service {
            public final Executor mBgExecutor;
            public final Service mDelegate;

            public Delegate(Service service, Executor executor) {
                this.mDelegate = service;
                this.mBgExecutor = executor;
            }

            @Override // com.miui.aod.doze.DozeMachine.Service
            public void fingerprintPressed(boolean z, boolean z2, Runnable runnable) {
                this.mDelegate.fingerprintPressed(z, z2, runnable);
            }

            @Override // com.miui.aod.doze.DozeMachine.Service
            public void finish() {
                this.mDelegate.finish();
            }

            @Override // com.miui.aod.doze.DozeMachine.Service
            public DozeMachine getDozeMachine() {
                return this.mDelegate.getDozeMachine();
            }

            @Override // com.miui.aod.doze.DozeMachine.Service
            public DozeHost getHost() {
                return this.mDelegate.getHost();
            }

            @Override // com.miui.aod.doze.DozeMachine.Service
            public DozeServicePlugin.RequestDoze getRequestDoze() {
                return this.mDelegate.getRequestDoze();
            }

            @Override // com.miui.aod.doze.DozeMachine.Service
            public ViewGroup getSysuiContainer() {
                return this.mDelegate.getSysuiContainer();
            }

            @Override // com.miui.aod.doze.DozeMachine.Service
            public boolean isFolded() {
                return this.mDelegate.isFolded();
            }

            @Override // com.miui.aod.doze.DozeMachine.Service
            public void requestState(State state) {
                this.mDelegate.requestState(state);
            }

            @Override // com.miui.aod.doze.DozeMachine.Service
            public void setDozeScreenBrightness(final int i) {
                this.mBgExecutor.execute(new Runnable() { // from class: com.miui.aod.doze.DozeMachine$Service$Delegate$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.mDelegate.setDozeScreenBrightness(i);
                    }
                });
            }

            @Override // com.miui.aod.doze.DozeMachine.Service
            public void setDozeScreenState(int i) {
                this.mDelegate.setDozeScreenState(i);
            }
        }

        default void fingerprintPressed(boolean z, boolean z2, Runnable runnable) {
        }

        void finish();

        DozeMachine getDozeMachine();

        DozeHost getHost();

        DozeServicePlugin.RequestDoze getRequestDoze();

        ViewGroup getSysuiContainer();

        boolean isFolded();

        void requestState(State state);

        void setDozeScreenBrightness(int i);

        void setDozeScreenState(int i);
    }

    public enum State {
        UNINITIALIZED,
        INITIALIZED,
        DOZE,
        DOZE_AOD,
        DOZE_REQUEST_PULSE,
        DOZE_PULSING,
        DOZE_PULSE_DONE,
        FINISH,
        DOZE_AOD_PAUSED,
        DOZE_AOD_PAUSING,
        DOZE_INTO_COVERMODE;

        public boolean canPulse() {
            int i = AnonymousClass1.$SwitchMap$com$miui$aod$doze$DozeMachine$State[ordinal()];
            return i == 1 || i == 2 || i == 3 || i == 4;
        }

        public int screenState() {
            switch (AnonymousClass1.$SwitchMap$com$miui$aod$doze$DozeMachine$State[ordinal()]) {
                case 1:
                case 3:
                    return 1;
                case 2:
                case 4:
                    return (Utils.isVideoScreenDevice() || Utils.isMiuiOptimizationDisabled()) ? 3 : 4;
                case 5:
                case 6:
                    return 2;
                case 7:
                case 8:
                    return AODSettings.needKeepScreenOnAtFirst() ? 2 : 1;
                default:
                    return 0;
            }
        }

        public boolean staysAwake() {
            int i = AnonymousClass1.$SwitchMap$com$miui$aod$doze$DozeMachine$State[ordinal()];
            return i == 5 || i == 6;
        }
    }

    public DozeMachine(Service service, WakeLock wakeLock, Context context) {
        this.mDozeService = service;
        this.mWakeLock = wakeLock;
        this.mContext = context;
        this.mHost = service.getHost();
    }

    public void destroy() {
        for (Part part : this.mParts) {
            part.destroy();
        }
        this.mParts = null;
    }

    public int getAodReason() {
        return this.mAodReason;
    }

    public int getPulseReason() {
        Assert.isMainThread();
        State state = this.mState;
        Preconditions.checkState(state == State.DOZE_REQUEST_PULSE || state == State.DOZE_PULSING || state == State.DOZE_PULSE_DONE, "must be in pulsing state, but is " + this.mState);
        return this.mPulseReason;
    }

    public State getState() {
        Assert.isMainThread();
        if (!isExecutingTransition()) {
            return this.mState;
        }
        throw new IllegalStateException("Cannot get state because there were pending transitions: " + this.mQueuedRequests.toString());
    }

    public final boolean isExecutingTransition() {
        return !this.mQueuedRequests.isEmpty();
    }

    public Map onRequestFocusView(int i, View view, Bundle bundle) {
        ArrayMap arrayMap = new ArrayMap();
        for (Part part : this.mParts) {
            arrayMap.put(part.getClass().getName(), Boolean.valueOf(part.onRequestFocusView(i, view, bundle)));
        }
        return arrayMap;
    }

    public void onScreenState(int i) {
        for (Part part : this.mParts) {
            part.onScreenState(i);
        }
    }

    public Map onSystemUIAction(int i, Bundle bundle) {
        ArrayMap arrayMap = new ArrayMap();
        for (Part part : this.mParts) {
            Map mapOnSystemUIAction = part.onSystemUIAction(i, bundle);
            if (mapOnSystemUIAction != null) {
                arrayMap.putAll(mapOnSystemUIAction);
            }
        }
        return arrayMap;
    }

    public final void performTransitionOnComponents(State state, State state2) {
        for (Part part : this.mParts) {
            part.transitionTo(state, state2);
        }
        if (AnonymousClass1.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state2.ordinal()] != 9) {
            return;
        }
        this.mDozeService.finish();
    }

    public void preDestroyForChange() {
        for (Part part : this.mParts) {
            part.preDestroyForChange();
        }
    }

    public void rebindForChange() {
        for (Part part : this.mParts) {
            part.rebindForChange();
        }
    }

    public void requestAod(int i) {
        Preconditions.checkState(!isExecutingTransition());
        requestState(State.DOZE_AOD, i);
    }

    public void requestPulse(int i) {
        Preconditions.checkState(!isExecutingTransition());
        requestState(State.DOZE_REQUEST_PULSE, i);
    }

    public void requestState(State state) {
        Preconditions.checkArgument(state != State.DOZE_REQUEST_PULSE);
        requestState(state, -1);
    }

    public final void requestState(State state, int i) {
        Assert.isMainThread();
        Log.i("DozeMachine", "request: current=" + this.mState + " req=" + state);
        boolean zIsExecutingTransition = isExecutingTransition();
        this.mQueuedRequests.add(state);
        if (zIsExecutingTransition) {
            return;
        }
        this.mWakeLock.acquire("DozeMachine#requestState");
        for (int i2 = 0; i2 < this.mQueuedRequests.size(); i2++) {
            transitionTo((State) this.mQueuedRequests.get(i2), i);
        }
        this.mQueuedRequests.clear();
        this.mWakeLock.release("DozeMachine#requestState");
    }

    public final void resolveIntermediateState(State state) {
        int i = AnonymousClass1.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state.ordinal()];
        if (i == 8 || i == 11) {
            boolean z = SettingsHolder.mDisplayReversion == 1;
            if (this.mHost.isSuppressAod() || z) {
                Log.w("DozeMachine", "resolveIntermediateState: isSuppressAod or displayReversion");
                transitionTo(State.DOZE, -1);
                return;
            }
            if (Utils.isGxzwSensor() && Utils.isShowFingerprintIcon(this.mContext, this.mHost) && Utils.isFodAodShowEnable(this.mContext) && Utils.isKeyGuardFodShowEnable(this.mContext)) {
                Log.w("DozeMachine", "resolveIntermediateState: DOZE_AOD for fod");
                this.mHost.requestDrawWakelock(3000L, "DozeMachine#resolveIntermediateState");
                transitionTo(State.DOZE_AOD, -1);
            } else {
                if (this.mHost.hasFodTurnedOnScreen()) {
                    Log.w("DozeMachine", "resolveIntermediateState: DOZE_AOD for systemui binder screenturnOnScreen ");
                    transitionTo(State.DOZE_AOD, 6);
                    return;
                }
                transitionTo(Utils.aodGone(this.mContext) ? State.DOZE : State.DOZE_AOD, -1);
                FlipLinkageStyleController flipLinkageStyleController = FlipLinkageStyleController.INSTANCE;
                if (flipLinkageStyleController.isUsingFlip(this.mContext) || !flipLinkageStyleController.isFlipped()) {
                    return;
                }
                transitionTo(State.FINISH, -1);
            }
        }
    }

    public void setParts(Part[] partArr) {
        Preconditions.checkState(this.mParts == null);
        this.mParts = partArr;
    }

    public final State transitionPolicy(State state) {
        State state2 = this.mState;
        State state3 = State.FINISH;
        if (state2 == state3) {
            return state3;
        }
        if ((state2 == State.DOZE_AOD_PAUSED || state2 == State.DOZE_AOD_PAUSING || state2 == State.DOZE_AOD || state2 == State.DOZE) && state == State.DOZE_PULSE_DONE) {
            Log.i("DozeMachine", "Dropping pulse done because current state is already done: " + this.mState);
            return this.mState;
        }
        if (state != State.DOZE_REQUEST_PULSE || state2.canPulse()) {
            return state;
        }
        Log.i("DozeMachine", "Dropping pulse request because current state can't pulse: " + this.mState);
        return this.mState;
    }

    public final void transitionTo(State state, int i) {
        State stateTransitionPolicy = transitionPolicy(state);
        Log.i("DozeMachine", "transition: old=" + this.mState + " req=" + state + " new=" + stateTransitionPolicy, new Throwable("here"));
        if (stateTransitionPolicy == this.mState) {
            return;
        }
        validateTransition(stateTransitionPolicy);
        State state2 = this.mState;
        this.mState = stateTransitionPolicy;
        DozeLog.traceState(stateTransitionPolicy);
        CommonUtils.traceAppCounter("doze_machine_state", stateTransitionPolicy.ordinal());
        updateStateReason(stateTransitionPolicy, state2, i);
        performTransitionOnComponents(state2, stateTransitionPolicy);
        updateWakeLockState(stateTransitionPolicy);
        resolveIntermediateState(stateTransitionPolicy);
    }

    public final void updateStateReason(State state, State state2, int i) {
        if (state == State.DOZE_REQUEST_PULSE) {
            this.mPulseReason = i;
        } else if (state2 == State.DOZE_PULSE_DONE) {
            this.mPulseReason = -1;
        }
        State state3 = State.DOZE_AOD;
        if (state == state3) {
            this.mAodReason = i;
        } else if (state2 == state3) {
            this.mAodReason = -1;
        }
    }

    public final void updateWakeLockState(State state) {
        boolean zStaysAwake = state.staysAwake();
        boolean z = this.mWakeLockHeldForCurrentState;
        if (z && !zStaysAwake) {
            this.mWakeLock.release("DozeMachine#heldForState");
            this.mWakeLockHeldForCurrentState = false;
        } else {
            if (z || !zStaysAwake) {
                return;
            }
            this.mWakeLock.acquire("DozeMachine#heldForState");
            this.mWakeLockHeldForCurrentState = true;
        }
    }

    public final void validateTransition(State state) {
        try {
            int[] iArr = AnonymousClass1.$SwitchMap$com$miui$aod$doze$DozeMachine$State;
            int i = iArr[this.mState.ordinal()];
            if (i == 7) {
                Preconditions.checkState(state == State.INITIALIZED);
            } else if (i == 9) {
                Preconditions.checkState(state == State.FINISH);
            }
            int i2 = iArr[state.ordinal()];
            if (i2 == 6) {
                Preconditions.checkState(this.mState == State.DOZE_REQUEST_PULSE);
                return;
            }
            if (i2 == 7) {
                throw new IllegalArgumentException("can't transition to UNINITIALIZED");
            }
            if (i2 == 8) {
                Preconditions.checkState(this.mState == State.UNINITIALIZED);
            } else {
                if (i2 != 10) {
                    return;
                }
                State state2 = this.mState;
                Preconditions.checkState(state2 == State.DOZE_REQUEST_PULSE || state2 == State.DOZE_PULSING);
            }
        } catch (RuntimeException e) {
            throw new IllegalStateException("Illegal Transition: " + this.mState + " -> " + state, e);
        }
    }
}
