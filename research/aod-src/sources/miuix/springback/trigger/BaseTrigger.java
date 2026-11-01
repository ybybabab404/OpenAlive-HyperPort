package miuix.springback.trigger;

import java.util.Comparator;
import java.util.List;
import miuix.springback.R;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseTrigger {
    public List mActions;
    public Action mUpAction;

    public static abstract class Action {
        public static final Comparator DISTANCE_COMPARATOR = new Comparator<Action>() { // from class: miuix.springback.trigger.BaseTrigger.Action.1
            @Override // java.util.Comparator
            public int compare(Action action, Action action2) {
                return Integer.compare(action.mEnterPoint, action2.mEnterPoint);
            }
        };
        public int mEnterPoint;
        public int mTriggerPoint;

        public void notifyActivated() {
            onActivated();
        }

        public void notifyEntered() {
            onEntered();
        }

        public void notifyExit() {
            onExit();
        }

        public void notifyFinished() {
            onFinished();
        }

        public void notifyTriggered() {
            onTriggered();
        }

        public abstract void onActivated();

        public abstract void onEntered();

        public abstract void onExit();

        public abstract void onFinished();

        public abstract void onTriggered();
    }

    public static abstract class IndeterminateAction extends Action {
        public static final int[] DEFAULT_TRIGGER_TEXTIDS = {R.string.miuix_sbl_tracking_progress_labe_pull_to_refresh, R.string.miuix_sbl_tracking_progress_labe_release_to_refresh, R.string.miuix_sbl_tracking_progress_labe_refreshing, R.string.miuix_sbl_tracking_progress_labe_refreshed};
        public String[] mTriggerTexts;

        public interface OnActionCompleteListener {
        }

        public interface OnIndeterminateActionViewListener {
            float getViewRestartOffsetPoint();

            void onViewActivated(int i);

            void onViewActivating(int i);

            void onViewEntered(int i);

            void onViewEntering(int i);

            void onViewExit(int i);

            void onViewFinished(int i);

            void onViewStart(int i);

            void onViewStarting(int i);

            void onViewTriggered(int i);
        }
    }

    public static abstract class IndeterminateUpAction extends Action {
        public static final int[] DEFAULT_TRIGGER_TEXTIDS = {R.string.miuix_sbl_tracking_progress_labe_up_refresh, R.string.miuix_sbl_tracking_progress_labe_up_refresh_fail, R.string.miuix_sbl_tracking_progress_labe_up_nodata, R.string.miuix_sbl_tracking_progress_labe_up_none};
        public int mCountNoData;
        public String[] mTriggerTexts;

        public interface OnIndeterminateUpActionViewListener {
            float getViewRestartOffsetPoint();

            void onViewActivated(int i);

            void onViewActivating(int i);

            void onViewEntered(int i);

            void onViewEntering(int i);

            void onViewExit(int i);

            void onViewFinished(int i);

            void onViewStart(int i);

            void onViewStarting(int i);

            void onViewTriggered(int i);
        }

        public interface OnUpActionDataListener {
        }

        public int getCountNoData() {
            return this.mCountNoData;
        }

        public boolean isNoData() {
            return this.mCountNoData > 0;
        }
    }

    public static abstract class SimpleAction extends Action {

        public interface OnSimpleActionViewListener {
            float getViewRestartOffsetPoint();

            void onViewActivated(int i);

            void onViewActivating(int i);

            void onViewEntered(int i);

            void onViewEntering(int i);

            void onViewExit(int i);

            void onViewFinished(int i);

            void onViewStart(int i);

            void onViewStarting(int i);

            void onViewTriggered(int i);
        }
    }

    public List getActions() {
        return this.mActions;
    }

    public IndeterminateAction getIndeterminateAction() {
        for (int i = 0; i < this.mActions.size(); i++) {
            Action action = (Action) this.mActions.get(i);
            if (action != null && (action instanceof IndeterminateAction)) {
                return (IndeterminateAction) action;
            }
        }
        return null;
    }

    public IndeterminateUpAction getIndeterminateUpAction() {
        return (IndeterminateUpAction) this.mUpAction;
    }
}
