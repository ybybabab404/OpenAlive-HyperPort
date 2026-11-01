package miuix.internal.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ListAdapter;
import miuix.appcompat.app.AccessibilityDelegateProvider;
import miuix.appcompat.app.AlertDialog;
import miuix.core.util.EnvStateManager;
import miuix.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public class ActionSheet {

    public enum ActionSheetItemType {
        PRIMARY_ITEM,
        ERROR_ITEM
    }

    public enum ActionSheetMode {
        ALERT_MODE,
        ARROW_MODE
    }

    public enum ArrowMode {
        ARROW_TOP_MODE,
        ARROW_TOP_LEFT_MODE,
        ARROW_TOP_RIGHT_MODE,
        ARROW_LEFT_TOP_MODE,
        ARROW_LEFT_BOTTOM_MODE,
        ARROW_BOTTOM_MODE,
        ARROW_BOTTOM_LEFT_MODE,
        ARROW_BOTTOM_RIGHT_MODE,
        ARROW_RIGHT_TOP_MODE,
        ARROW_RIGHT_BOTTOM_MODE,
        ARROW_LEFT_MODE,
        ARROW_RIGHT_MODE,
        ARROW_MODE_NONE
    }

    public static class Builder {
        public ActionSheetItemType[] mActionItemTypes;
        public CharSequence[] mActionItems;
        public View mAnchor;
        public Point mArrowActionOffset;
        public ArrowMode mArrowMode;
        public boolean mCanceledOnTouchOutside;
        public AlertDialog.OnConfigurationChangedListener mConfigurationChangedListener;
        public Context mContext;
        public boolean mEnterAnimEnabled;
        public boolean mHapticFeedbackEnabled;
        public DialogInterface.OnClickListener mItemClickListener;
        public AccessibilityDelegateProvider mItemsProvider;
        public ListAdapter mListAdapter;
        public CharSequence mMessage;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public DialogInterface.OnShowListener mOnShowListener;
        public DialogInterface.OnClickListener mSeparateClickListener;
        public CharSequence mSeparateText;
        public AlertDialog.OnDialogShowAnimListener mShowAnimListener;
        public int mThemeResId;

        public Builder(Context context) {
            this(context, 0);
        }

        public Builder(Context context, int i) {
            this.mArrowMode = ArrowMode.ARROW_MODE_NONE;
            this.mEnterAnimEnabled = true;
            this.mCanceledOnTouchOutside = true;
            this.mHapticFeedbackEnabled = true;
            this.mContext = context;
            this.mThemeResId = i;
        }

        public IActionSheet create() {
            return (!ActionSheet.showArrowActionSheet(this.mContext) || this.mAnchor == null || this.mArrowMode == ArrowMode.ARROW_MODE_NONE) ? createAlertActionSheet() : createArrowActionSheet();
        }

        public final IActionSheet createAlertActionSheet() {
            AlertActionSheet alertActionSheet = new AlertActionSheet(this.mContext, this.mThemeResId);
            alertActionSheet.setMessage(this.mMessage);
            alertActionSheet.setActionItems(this.mActionItems, this.mActionItemTypes, this.mItemClickListener);
            alertActionSheet.setEnableEnterAnim(this.mEnterAnimEnabled);
            alertActionSheet.setCanceledOnTouchOutside(this.mCanceledOnTouchOutside);
            alertActionSheet.setHapticFeedbackEnabled(this.mHapticFeedbackEnabled);
            alertActionSheet.setSeparateButtonText(this.mSeparateText);
            alertActionSheet.setArrowActionAnchor(this.mAnchor);
            alertActionSheet.setArrowMode(this.mArrowMode);
            AlertDialog.OnDialogShowAnimListener onDialogShowAnimListener = this.mShowAnimListener;
            if (onDialogShowAnimListener != null) {
                alertActionSheet.setOnShowAnimListener(onDialogShowAnimListener);
            }
            DialogInterface.OnShowListener onShowListener = this.mOnShowListener;
            if (onShowListener != null) {
                alertActionSheet.setActionSheetOnShowListener(onShowListener);
            }
            DialogInterface.OnDismissListener onDismissListener = this.mOnDismissListener;
            if (onDismissListener != null) {
                alertActionSheet.setActionSheetOnDismissListener(onDismissListener);
            }
            DialogInterface.OnKeyListener onKeyListener = this.mOnKeyListener;
            if (onKeyListener != null) {
                alertActionSheet.setOnKeyListener(onKeyListener);
                alertActionSheet.setActionSheetOnKeyListener(this.mOnKeyListener);
            }
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                alertActionSheet.setListViewAdapter(listAdapter);
            }
            DialogInterface.OnClickListener onClickListener = this.mSeparateClickListener;
            if (onClickListener != null) {
                alertActionSheet.setSeparateClickListener(onClickListener);
            }
            DialogInterface.OnCancelListener onCancelListener = this.mOnCancelListener;
            if (onCancelListener != null) {
                alertActionSheet.setOnCancelListener(onCancelListener);
                alertActionSheet.setActionSheetOnCancelListener(this.mOnCancelListener);
            }
            AlertDialog.OnConfigurationChangedListener onConfigurationChangedListener = this.mConfigurationChangedListener;
            if (onConfigurationChangedListener != null) {
                alertActionSheet.setConfigurationChangedListener(onConfigurationChangedListener);
            }
            AccessibilityDelegateProvider accessibilityDelegateProvider = this.mItemsProvider;
            if (accessibilityDelegateProvider != null) {
                alertActionSheet.setItemAccessibilityProvider(accessibilityDelegateProvider);
            }
            return alertActionSheet;
        }

        public final IActionSheet createArrowActionSheet() {
            ArrowActionSheet arrowActionSheet = new ArrowActionSheet(this.mContext, this.mThemeResId, this.mAnchor);
            arrowActionSheet.setMessage(this.mMessage);
            arrowActionSheet.setActionItems(this.mActionItems, this.mActionItemTypes, this.mItemClickListener);
            arrowActionSheet.setArrowMode(this.mArrowMode);
            arrowActionSheet.setEnableEnterAnim(this.mEnterAnimEnabled);
            arrowActionSheet.setCanceledOnTouchOutside(this.mCanceledOnTouchOutside);
            arrowActionSheet.setHapticFeedbackEnabled(this.mHapticFeedbackEnabled);
            AlertDialog.OnDialogShowAnimListener onDialogShowAnimListener = this.mShowAnimListener;
            if (onDialogShowAnimListener != null) {
                arrowActionSheet.setOnShowAnimListener(onDialogShowAnimListener);
            }
            DialogInterface.OnShowListener onShowListener = this.mOnShowListener;
            if (onShowListener != null) {
                arrowActionSheet.setActionSheetOnShowListener(onShowListener);
            }
            DialogInterface.OnDismissListener onDismissListener = this.mOnDismissListener;
            if (onDismissListener != null) {
                arrowActionSheet.setActionSheetOnDismissListener(onDismissListener);
            }
            DialogInterface.OnKeyListener onKeyListener = this.mOnKeyListener;
            if (onKeyListener != null) {
                arrowActionSheet.setOnKeyListener(onKeyListener);
                arrowActionSheet.setActionSheetOnKeyListener(this.mOnKeyListener);
            }
            Point point = this.mArrowActionOffset;
            if (point != null) {
                arrowActionSheet.setOffset(point.x, point.y);
            }
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                arrowActionSheet.setListViewAdapter(listAdapter);
            }
            DialogInterface.OnCancelListener onCancelListener = this.mOnCancelListener;
            if (onCancelListener != null) {
                arrowActionSheet.setOnCancelListener(onCancelListener);
                arrowActionSheet.setActionSheetOnCancelListener(this.mOnCancelListener);
            }
            AlertDialog.OnConfigurationChangedListener onConfigurationChangedListener = this.mConfigurationChangedListener;
            if (onConfigurationChangedListener != null) {
                arrowActionSheet.setConfigurationChangedListener(onConfigurationChangedListener);
            }
            AccessibilityDelegateProvider accessibilityDelegateProvider = this.mItemsProvider;
            if (accessibilityDelegateProvider != null) {
                arrowActionSheet.setItemAccessibilityProvider(accessibilityDelegateProvider);
            }
            return arrowActionSheet;
        }

        public Builder setActionItems(CharSequence[] charSequenceArr, DialogInterface.OnClickListener onClickListener) {
            this.mActionItems = charSequenceArr;
            this.mItemClickListener = onClickListener;
            return this;
        }

        public Builder setItemAccessibilityProvider(AccessibilityDelegateProvider accessibilityDelegateProvider) {
            this.mItemsProvider = accessibilityDelegateProvider;
            return this;
        }

        public Builder setListViewAdapter(ListAdapter listAdapter) {
            this.mListAdapter = listAdapter;
            return this;
        }

        public Builder setMessage(CharSequence charSequence) {
            this.mMessage = charSequence;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setOnShowListener(DialogInterface.OnShowListener onShowListener) {
            this.mOnShowListener = onShowListener;
            return this;
        }

        public Builder setSeparateClickListener(DialogInterface.OnClickListener onClickListener) {
            this.mSeparateClickListener = onClickListener;
            return this;
        }

        public Builder setSeparateText(CharSequence charSequence) {
            this.mSeparateText = charSequence;
            return this;
        }

        public Builder setShowAnimListener(AlertDialog.OnDialogShowAnimListener onDialogShowAnimListener) {
            this.mShowAnimListener = onDialogShowAnimListener;
            return this;
        }
    }

    public interface ContentController {
        int calcContentPanelHeight(Context context, ViewGroup viewGroup, ViewGroup viewGroup2, int i, WindowInsets windowInsets);

        int calcContentPanelWidth(Context context, ViewGroup viewGroup, ViewGroup viewGroup2, int i, WindowInsets windowInsets);

        int[] calcHorizontalMargin(Context context, int i, WindowInsets windowInsets);

        int[] calcVerticalMargin(Context context, int i, WindowInsets windowInsets);

        Point computeContentPosition(Rect rect, Point point, Point point2, ViewGroup viewGroup, ViewGroup viewGroup2);

        View getArrowAnchor();

        ArrowMode getArrowMode();
    }

    public interface IActionSheet {
    }

    public static boolean showArrowActionSheet(Context context) {
        return Build.IS_TABLET && EnvStateManager.getWindowInfo(context).windowSizeDp.x >= 747;
    }
}
