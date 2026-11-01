package miuix.appcompat.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatDialog;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.DefaultTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;
import androidx.core.view.ViewCompat;
import java.lang.reflect.InvocationTargetException;
import miuix.appcompat.R;
import miuix.appcompat.internal.widget.DialogParentPanel2;
import miuix.appcompat.widget.DialogAnimHelper;
import miuix.autodensity.DensityUtil;
import miuix.core.util.EnvStateManager;
import miuix.core.util.RomUtils;
import miuix.internal.widget.ActionSheet;
import miuix.reflect.ReflectionHelper;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

/* JADX INFO: loaded from: classes2.dex */
public class AlertDialog extends AppCompatDialog implements DialogInterface {
    public static final int[] DIALOG_CONTENT_LAYOUT = {R.layout.miuix_appcompat_alert_dialog_content, R.layout.miuix_appcompat_alert_dialog_content_land};
    public final AlertController mAlert;
    public Runnable mCheckDismissAction;
    public LifecycleOwnerCompat mLifecycleOwnerCompat;
    public DialogAnimHelper.OnDismiss mOnDismiss;
    public CharSequence mShowDescription;

    public static class Builder {
        public final AlertController.AlertParams P;
        public boolean mActionSheetEnabled;
        public final int mTheme;

        public Builder(Context context) {
            this(context, AlertDialog.resolveDialogTheme(context, 0));
        }

        public Builder(Context context, int i) {
            this.mActionSheetEnabled = false;
            this.P = new AlertController.AlertParams(new ContextThemeWrapper(context, AlertDialog.resolveDialogTheme(context, i)));
            this.mTheme = i;
        }

        public AlertDialog create() {
            AlertController.AlertParams alertParams = this.P;
            boolean z = false;
            boolean z2 = (alertParams.mItems == null || alertParams.mIsSingleChoice || alertParams.mIsMultiChoice) ? false : true;
            if (alertParams.mView == null && alertParams.mViewLayoutResId == 0) {
                z = true;
            }
            return (this.mActionSheetEnabled && z2 && z) ? createActionSheet() : createAlertDialog();
        }

        public final AlertDialog createActionSheet() {
            ActionSheet.Builder builder = new ActionSheet.Builder(this.P.mContext);
            if (!TextUtils.isEmpty(this.P.mMessage)) {
                builder.setMessage(this.P.mMessage);
            }
            if (!TextUtils.isEmpty(this.P.mTitle)) {
                builder.setMessage(this.P.mTitle);
            }
            AlertController.AlertParams alertParams = this.P;
            builder.setActionItems(alertParams.mItems, alertParams.mOnClickListener);
            if (!TextUtils.isEmpty(this.P.mNegativeButtonText)) {
                builder.setSeparateText(this.P.mNegativeButtonText);
            }
            DialogInterface.OnClickListener onClickListener = this.P.mNegativeButtonListener;
            if (onClickListener != null) {
                builder.setSeparateClickListener(onClickListener);
            }
            OnDialogShowAnimListener onDialogShowAnimListener = this.P.mOnDialogShowAnimListener;
            if (onDialogShowAnimListener != null) {
                builder.setShowAnimListener(onDialogShowAnimListener);
            }
            DialogInterface.OnShowListener onShowListener = this.P.mOnShowListener;
            if (onShowListener != null) {
                builder.setOnShowListener(onShowListener);
            }
            DialogInterface.OnDismissListener onDismissListener = this.P.mOnDismissListener;
            if (onDismissListener != null) {
                builder.setOnDismissListener(onDismissListener);
            }
            DialogInterface.OnKeyListener onKeyListener = this.P.mOnKeyListener;
            if (onKeyListener != null) {
                builder.setOnKeyListener(onKeyListener);
            }
            ListAdapter listAdapter = this.P.mAdapter;
            if (listAdapter != null) {
                builder.setListViewAdapter(listAdapter);
            }
            AccessibilityDelegateProvider accessibilityDelegateProvider = this.P.mItemsProvider;
            if (accessibilityDelegateProvider != null) {
                builder.setItemAccessibilityProvider(accessibilityDelegateProvider);
            }
            AlertDialog alertDialog = (AlertDialog) builder.create();
            if (this.P.mCancelable) {
                alertDialog.setCanceledOnTouchOutside(true);
            }
            DialogInterface.OnCancelListener onCancelListener = this.P.mOnCancelListener;
            if (onCancelListener != null) {
                alertDialog.setOnCancelListener(onCancelListener);
            }
            OnConfigurationChangedListener onConfigurationChangedListener = this.P.mConfigurationChangedListener;
            if (onConfigurationChangedListener != null) {
                alertDialog.setConfigurationChangedListener(onConfigurationChangedListener);
            }
            return alertDialog;
        }

        public final AlertDialog createAlertDialog() {
            AlertDialog alertDialog = new AlertDialog(this.P.mContext, this.mTheme);
            this.P.apply(alertDialog.mAlert);
            alertDialog.setCancelable(this.P.mCancelable);
            if (this.P.mCancelable) {
                alertDialog.setCanceledOnTouchOutside(true);
            }
            alertDialog.setOnCancelListener(this.P.mOnCancelListener);
            alertDialog.setOnDismissListener(this.P.mOnDismissListener);
            alertDialog.setOnShowListener(this.P.mOnShowListener);
            alertDialog.setOnShowAnimListener(this.P.mOnDialogShowAnimListener);
            DialogInterface.OnKeyListener onKeyListener = this.P.mOnKeyListener;
            if (onKeyListener != null) {
                alertDialog.setOnKeyListener(onKeyListener);
            }
            return alertDialog;
        }

        public Context getContext() {
            return this.P.mContext;
        }

        public Builder setAdapter(ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mAdapter = listAdapter;
            alertParams.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setButtonForceVertical(boolean z) {
            this.P.mButtonForceVertical = z;
            return this;
        }

        public Builder setCancelable(boolean z) {
            this.P.mCancelable = z;
            return this;
        }

        public Builder setCustomTitle(View view) {
            this.P.mCustomTitleView = view;
            return this;
        }

        public Builder setEnableDialogImmersive(boolean z) {
            this.P.mEnableDialogImmersive = z;
            return this;
        }

        public Builder setEnableEnterAnim(boolean z) {
            this.P.mEnableEnterAnim = z;
            return this;
        }

        public Builder setHapticFeedbackEnabled(boolean z) {
            this.P.mHapticFeedbackEnabled = z;
            return this;
        }

        public Builder setIcon(int i) {
            this.P.mIconId = i;
            return this;
        }

        public Builder setIcon(Drawable drawable) {
            this.P.mIcon = drawable;
            return this;
        }

        public Builder setItems(CharSequence[] charSequenceArr, DialogInterface.OnClickListener onClickListener) {
            if (RomUtils.getHyperOsVersion() > 2) {
                this.mActionSheetEnabled = true;
            }
            AlertController.AlertParams alertParams = this.P;
            alertParams.mItems = charSequenceArr;
            alertParams.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setMessage(int i) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mMessage = alertParams.mContext.getText(i);
            return this;
        }

        public Builder setMessage(CharSequence charSequence) {
            this.P.mMessage = charSequence;
            return this;
        }

        public Builder setMultiChoiceItems(CharSequence[] charSequenceArr, boolean[] zArr, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mItems = charSequenceArr;
            alertParams.mOnCheckboxClickListener = onMultiChoiceClickListener;
            alertParams.mCheckedItems = zArr;
            alertParams.mIsMultiChoice = true;
            return this;
        }

        public Builder setNegativeButton(int i, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mNegativeButtonText = alertParams.mContext.getText(i);
            this.P.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNegativeButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mNegativeButtonText = charSequence;
            alertParams.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.P.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.P.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.P.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setOnShowListener(DialogInterface.OnShowListener onShowListener) {
            this.P.mOnShowListener = onShowListener;
            return this;
        }

        public Builder setPositiveButton(int i, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mPositiveButtonText = alertParams.mContext.getText(i);
            this.P.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mPositiveButtonText = charSequence;
            alertParams.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setSingleChoiceItems(ListAdapter listAdapter, int i, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mAdapter = listAdapter;
            alertParams.mOnClickListener = onClickListener;
            alertParams.mCheckedItem = i;
            alertParams.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(CharSequence[] charSequenceArr, int i, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mItems = charSequenceArr;
            alertParams.mOnClickListener = onClickListener;
            alertParams.mCheckedItem = i;
            alertParams.mIsSingleChoice = true;
            return this;
        }

        public Builder setTitle(int i) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mTitle = alertParams.mContext.getText(i);
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.P.mTitle = charSequence;
            return this;
        }

        public Builder setView(View view) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mView = view;
            alertParams.mViewLayoutResId = 0;
            return this;
        }

        public AlertDialog show() {
            AlertDialog alertDialogCreate = create();
            alertDialogCreate.show();
            return alertDialogCreate;
        }
    }

    public class LifecycleOwnerCompat {
        public Object mOriginalExecutor;
        public TaskExecutor mSpecialUiExecutor;

        public LifecycleOwnerCompat() {
        }

        public final TaskExecutor createSpecialUiTaskExecutor() {
            return new DefaultTaskExecutor() { // from class: miuix.appcompat.app.AlertDialog.LifecycleOwnerCompat.1
                public final Object mLock = new Object();
                public volatile Handler mSpecialMainHandler;

                public final Handler createAsync(Looper looper) {
                    return Handler.createAsync(looper);
                }

                @Override // androidx.arch.core.executor.DefaultTaskExecutor, androidx.arch.core.executor.TaskExecutor
                public boolean isMainThread() {
                    return true;
                }

                @Override // androidx.arch.core.executor.DefaultTaskExecutor, androidx.arch.core.executor.TaskExecutor
                public void postToMainThread(Runnable runnable) {
                    if (this.mSpecialMainHandler == null) {
                        synchronized (this.mLock) {
                            try {
                                if (this.mSpecialMainHandler == null) {
                                    this.mSpecialMainHandler = createAsync(Looper.myLooper());
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                    this.mSpecialMainHandler.post(runnable);
                }
            };
        }

        public void onCreate() {
            try {
                Object fieldValue = ReflectionHelper.getFieldValue(ArchTaskExecutor.class, ArchTaskExecutor.getInstance(), "mDelegate");
                if (fieldValue != null) {
                    this.mOriginalExecutor = fieldValue;
                }
            } catch (IllegalAccessException e) {
                Log.d("MiuixDialog", "onCreate() taskExecutor get failed IllegalAccessException " + e);
            } catch (NoSuchMethodException e2) {
                Log.d("MiuixDialog", "onCreate() taskExecutor get failed NoSuchMethodException " + e2);
            } catch (InvocationTargetException e3) {
                Log.d("MiuixDialog", "onCreate() taskExecutor get failed InvocationTargetException " + e3);
            } finally {
                this.mSpecialUiExecutor = createSpecialUiTaskExecutor();
                ArchTaskExecutor.getInstance().setDelegate(this.mSpecialUiExecutor);
            }
        }

        public void onStopAfter() {
            if (this.mOriginalExecutor instanceof TaskExecutor) {
                ArchTaskExecutor.getInstance().setDelegate((TaskExecutor) this.mOriginalExecutor);
            }
        }

        /* JADX WARN: Code duplicated, block: B:27:0x0059  */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v10 */
        /* JADX WARN: Type inference failed for: r4v6 */
        /* JADX WARN: Type inference failed for: r4v7 */
        /* JADX WARN: Type inference failed for: r4v9 */
        public void onStopBefore() {
            try {
                try {
                    Object fieldValue = ReflectionHelper.getFieldValue(ArchTaskExecutor.class, ArchTaskExecutor.getInstance(), "mDelegate");
                    if (fieldValue != null && fieldValue != this.mOriginalExecutor) {
                        this.mOriginalExecutor = fieldValue;
                    }
                    if (fieldValue != this.mSpecialUiExecutor || !ArchTaskExecutor.getInstance().isMainThread()) {
                        this = this;
                        ArchTaskExecutor.getInstance().setDelegate(this.mSpecialUiExecutor);
                        return;
                    }
                } catch (IllegalAccessException e) {
                    Log.d("MiuixDialog", "onStop() taskExecutor get failed IllegalAccessException " + e);
                    if (this.mSpecialUiExecutor != null || !ArchTaskExecutor.getInstance().isMainThread()) {
                        this = this;
                        this = this;
                        this = this;
                        ArchTaskExecutor archTaskExecutor = ArchTaskExecutor.getInstance();
                        TaskExecutor taskExecutor = this.mSpecialUiExecutor;
                        archTaskExecutor.setDelegate(taskExecutor);
                        this = taskExecutor;
                    }
                } catch (NoSuchMethodException e2) {
                    Log.d("MiuixDialog", "onStop() taskExecutor get failed NoSuchMethodException " + e2);
                    if (this.mSpecialUiExecutor != null || !ArchTaskExecutor.getInstance().isMainThread()) {
                        this = this;
                        this = this;
                        this = this;
                        ArchTaskExecutor archTaskExecutor2 = ArchTaskExecutor.getInstance();
                        TaskExecutor taskExecutor2 = this.mSpecialUiExecutor;
                        archTaskExecutor2.setDelegate(taskExecutor2);
                        this = taskExecutor2;
                    }
                } catch (InvocationTargetException e3) {
                    Log.d("MiuixDialog", "onStop() taskExecutor get failed InvocationTargetException " + e3);
                    if (this.mSpecialUiExecutor != null || !ArchTaskExecutor.getInstance().isMainThread()) {
                        this = this;
                        this = this;
                        this = this;
                        ArchTaskExecutor archTaskExecutor3 = ArchTaskExecutor.getInstance();
                        TaskExecutor taskExecutor3 = this.mSpecialUiExecutor;
                        archTaskExecutor3.setDelegate(taskExecutor3);
                        this = taskExecutor3;
                    }
                }
                this = this;
                this = this;
                this = this;
                this = this;
            } catch (Throwable th) {
                if (this.mSpecialUiExecutor != null || !ArchTaskExecutor.getInstance().isMainThread()) {
                    ArchTaskExecutor.getInstance().setDelegate(this.mSpecialUiExecutor);
                }
                throw th;
            }
        }
    }

    public interface OnConfigurationChangedListener {
        void onConfigurationChanged(AppCompatDialog appCompatDialog, View view, Configuration configuration);
    }

    public interface OnDialogLayoutReloadListener {
        void onLayoutReload();
    }

    public interface OnDialogShowAnimListener {
        void onShowAnimComplete();

        void onShowAnimStart();
    }

    public interface OnPanelSizeChangedListener {
        void onPanelSizeChanged(AlertDialog alertDialog, DialogParentPanel2 dialogParentPanel2);
    }

    /* JADX INFO: renamed from: $r8$lambda$UVc2xUPAgOOfKzUqly8vz_ZEB-M, reason: not valid java name */
    public static /* synthetic */ void m1297$r8$lambda$UVc2xUPAgOOfKzUqly8vz_ZEBM(AlertDialog alertDialog) {
        if (alertDialog.mAlert.hasPendingDismiss()) {
            alertDialog.dismiss();
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$iUMZrLP-ak-1F3An7zG0mBgdfx4, reason: not valid java name */
    public static /* synthetic */ void m1298$r8$lambda$iUMZrLPak1F3An7zG0mBgdfx4(AlertDialog alertDialog) {
        View decorView;
        if (alertDialog.getWindow() == null || (decorView = alertDialog.getWindow().getDecorView()) == null || !decorView.isAttachedToWindow()) {
            return;
        }
        alertDialog.realDismiss();
    }

    public static /* synthetic */ void $r8$lambda$jo_FCVjpKmd5ScvHMNTrT_iCcPc(AlertDialog alertDialog, View view) {
        boolean z;
        if (alertDialog.mAlert.mIsDebugEnabled) {
            Log.d("MiuixAlertDialog", "prepareCheckDismissAction: dialog isShowing = " + alertDialog.isShowing() + ", decorView.isAttachedToWindow = " + view.isAttachedToWindow());
        }
        if (view.isAttachedToWindow()) {
            super.dismiss();
            z = true;
        } else {
            z = false;
        }
        if (alertDialog.mAlert.mIsDebugEnabled && z) {
            Log.d("MiuixAlertDialog", "prepareCheckDismissAction: ==> run super.dismiss()");
        }
    }

    public AlertDialog(Context context) {
        this(context, 0);
    }

    public AlertDialog(Context context, int i) {
        super(context, resolveDialogTheme(context, i));
        this.mCheckDismissAction = null;
        this.mOnDismiss = new DialogAnimHelper.OnDismiss() { // from class: miuix.appcompat.app.AlertDialog$$ExternalSyntheticLambda0
            @Override // miuix.appcompat.widget.DialogAnimHelper.OnDismiss
            public final void end() {
                AlertDialog.m1298$r8$lambda$iUMZrLPak1F3An7zG0mBgdfx4(this.f$0);
            }
        };
        Context context2 = parseContext(context);
        if (DensityUtil.findAutoDensityContextWrapper(context2) != null) {
            EnvStateManager.removeInfoOfContext(context);
        }
        this.mAlert = new AlertController(context2, this, getWindow());
        this.mLifecycleOwnerCompat = new LifecycleOwnerCompat();
        this.mShowDescription = context.getResources().getString(R.string.miuix_appcompat_show_dialog_description);
        prepareCheckDismissAction();
    }

    public static int resolveDialogTheme(Context context, int i) {
        if (((i >>> 24) & 255) >= 1) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.miuiAlertDialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        if (!this.mAlert.isDialogImmersive() && this.mAlert.isAsyncInflatePanelEnabled()) {
            this.mAlert.safeRemovePanelFromParent();
        }
        View decorView = getWindow().getDecorView();
        if (this.mAlert.isShowingAnimation()) {
            this.mAlert.setPendingDismiss(true);
            return;
        }
        this.mAlert.setPendingDismiss(false);
        if (DensityUtil.findAutoDensityContextWrapper(decorView.getContext()) != null) {
            EnvStateManager.removeInfoOfContext(decorView.getContext());
        }
        if (!this.mAlert.isDialogImmersive()) {
            dismissIfAttachedToWindow(decorView);
            return;
        }
        Activity associatedActivity = getAssociatedActivity();
        if (associatedActivity == null || !associatedActivity.isFinishing()) {
            dismissWithAnimationOrNot(decorView);
        } else {
            dismissIfAttachedToWindow(decorView);
        }
    }

    public void dismissIfAttachedToWindow(View view) {
        if (view == null || view.isAttachedToWindow()) {
            super.dismiss();
        } else {
            postCheckAndDismissSafely(view);
        }
    }

    public void dismissWithAnimationExistDecorView(View view) {
        if (Thread.currentThread() == view.getHandler().getLooper().getThread()) {
            this.mAlert.dismiss(this.mOnDismiss);
        } else {
            view.post(new Runnable() { // from class: miuix.appcompat.app.AlertDialog$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AlertDialog alertDialog = this.f$0;
                    alertDialog.mAlert.dismiss(alertDialog.mOnDismiss);
                }
            });
        }
    }

    public void dismissWithAnimationOrNot(View view) {
        if (view == null) {
            super.dismiss();
        } else if (view.getHandler() != null) {
            dismissWithAnimationExistDecorView(view);
        } else {
            dismissIfAttachedToWindow(view);
        }
    }

    public void dismissWithoutAnimation() {
        View decorView = getWindow().getDecorView();
        if (getWindow().getDecorView().isAttachedToWindow()) {
            if (this.mAlert.isShowingAnimation()) {
                this.mAlert.setPendingDismiss(true);
                return;
            }
            this.mAlert.setPendingDismiss(false);
            if (DensityUtil.findAutoDensityContextWrapper(decorView.getContext()) != null) {
                EnvStateManager.removeInfoOfContext(decorView.getContext());
            }
            realDismiss();
        }
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.mAlert.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public Activity getAssociatedActivity() {
        Activity ownerActivity = getOwnerActivity();
        Context context = getContext();
        while (ownerActivity == null && context != null) {
            if (context instanceof Activity) {
                ownerActivity = (Activity) context;
            } else {
                context = context instanceof ContextWrapper ? ((ContextWrapper) context).getBaseContext() : null;
            }
        }
        return ownerActivity;
    }

    public Button getButton(int i) {
        return this.mAlert.getButton(i);
    }

    public ListView getListView() {
        return this.mAlert.getListView();
    }

    public boolean isSystemSpecialUiThread() {
        return TextUtils.equals("android.ui", Thread.currentThread().getName()) || TextUtils.equals("android.imms", Thread.currentThread().getName()) || TextUtils.equals("system_server", Thread.currentThread().getName());
    }

    public boolean miuixSuperDispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View decorView = getWindow().getDecorView();
        if (decorView != null && this.mAlert.mHapticFeedbackEnabled) {
            HapticCompat.performHapticFeedbackAsync(decorView, HapticFeedbackConstants.MIUI_ALERT, HapticFeedbackConstants.MIUI_POPUP_NORMAL);
        }
        this.mAlert.onAttachedToWindow();
        setAccessibilityDelegate(decorView);
    }

    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        LifecycleOwnerCompat lifecycleOwnerCompat;
        if (isSystemSpecialUiThread() && (lifecycleOwnerCompat = this.mLifecycleOwnerCompat) != null) {
            lifecycleOwnerCompat.onCreate();
        }
        if (this.mAlert.isDialogImmersive() || !this.mAlert.mEnableEnterAnim) {
            getWindow().setWindowAnimations(0);
        }
        super.onCreate(bundle);
        this.mAlert.installContent(bundle);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAlert.onDetachedFromWindow();
        removeCheckDismissAction("onDetachedFromWindow");
    }

    public void onLayoutReload() {
    }

    @Override // androidx.activity.ComponentDialog, android.app.Dialog
    public void onStart() {
        super.onStart();
        this.mAlert.onStart();
    }

    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public void onStop() {
        LifecycleOwnerCompat lifecycleOwnerCompat;
        LifecycleOwnerCompat lifecycleOwnerCompat2;
        if (isSystemSpecialUiThread() && (lifecycleOwnerCompat2 = this.mLifecycleOwnerCompat) != null) {
            lifecycleOwnerCompat2.onStopBefore();
        }
        super.onStop();
        this.mAlert.onStop();
        if (!isSystemSpecialUiThread() || (lifecycleOwnerCompat = this.mLifecycleOwnerCompat) == null) {
            return;
        }
        lifecycleOwnerCompat.onStopAfter();
    }

    public final Context parseContext(Context context) {
        return (context != null && context.getClass() == ContextThemeWrapper.class) ? context : getContext();
    }

    public final void postCheckAndDismissSafely(View view) {
        if (view == null) {
            return;
        }
        try {
            if (this.mAlert.mIsDebugEnabled) {
                Log.d("MiuixAlertDialog", "postCheckAndDismissSafely: mCheckDismissAction = " + this.mCheckDismissAction);
            }
            Runnable runnable = this.mCheckDismissAction;
            if (runnable != null) {
                view.post(runnable);
            }
        } catch (Exception unused) {
            Log.d("MiuixAlertDialog", "postCheckAndDismissSafely: decorView.post dismiss failed");
        }
    }

    public final void prepareCheckDismissAction() {
        final View decorView;
        Window window = getWindow();
        if (window == null || (decorView = window.getDecorView()) == null) {
            return;
        }
        this.mCheckDismissAction = new Runnable() { // from class: miuix.appcompat.app.AlertDialog$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                AlertDialog.$r8$lambda$jo_FCVjpKmd5ScvHMNTrT_iCcPc(this.f$0, decorView);
            }
        };
    }

    public void realDismiss() {
        super.dismiss();
    }

    public final void removeCheckDismissAction(String str) {
        View decorView;
        if (this.mAlert.mIsDebugEnabled) {
            Log.d("MiuixAlertDialog", "removeCheckDismissAction: ==> remove action at: " + str);
        }
        Window window = getWindow();
        if (this.mCheckDismissAction == null || window == null || (decorView = window.getDecorView()) == null) {
            return;
        }
        decorView.removeCallbacks(this.mCheckDismissAction);
    }

    public void setAccessibilityDelegate(View view) {
        if (view == null) {
            return;
        }
        ViewCompat.setAccessibilityPaneTitle(view, this.mShowDescription);
    }

    public void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.mAlert.setButton(i, charSequence, onClickListener, null);
    }

    @Override // android.app.Dialog
    public void setCancelable(boolean z) {
        super.setCancelable(z);
        this.mAlert.setCancelable(z);
    }

    @Override // android.app.Dialog
    public void setCanceledOnTouchOutside(boolean z) {
        super.setCanceledOnTouchOutside(z);
        this.mAlert.setCanceledOnTouchOutside(z);
    }

    public void setConfigurationChangedListener(OnConfigurationChangedListener onConfigurationChangedListener) {
        this.mAlert.setConfigurationChangedListener(onConfigurationChangedListener);
    }

    public void setIcon(int i) {
        this.mAlert.setIcon(i);
    }

    public void setOnShowAnimListener(OnDialogShowAnimListener onDialogShowAnimListener) {
        this.mAlert.setShowAnimListener(onDialogShowAnimListener);
    }

    public void setPreferLandscape(boolean z) {
        this.mAlert.setPreferLandscape(z);
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        this.mAlert.setTitle(charSequence);
    }

    public void setView(View view) {
        this.mAlert.setView(view);
    }

    @Override // android.app.Dialog
    public void show() {
        removeCheckDismissAction("show");
        this.mAlert.mShowUpTimeMillis = SystemClock.uptimeMillis();
        super.show();
        if (getWindow() == null || this.mAlert.isDialogImmersive()) {
            return;
        }
        getWindow().getDecorView().postDelayed(new Runnable() { // from class: miuix.appcompat.app.AlertDialog$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AlertDialog.m1297$r8$lambda$UVc2xUPAgOOfKzUqly8vz_ZEBM(this.f$0);
            }
        }, this.mAlert.mNonImmersiveDialogShowAnimDuration);
    }

    public void superOnAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void superOnCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void superOnDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void superOnStart() {
        super.onStart();
    }

    public void superOnStop() {
        super.onStop();
    }

    public void superShow() {
        super.show();
    }
}
