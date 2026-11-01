package miuix.appcompat.app;

import android.R;
import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.content.ContextCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.xiaomi.onetrack.util.a;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import miuix.animation.Folme;
import miuix.appcompat.app.strategy.DialogButtonBehaviorImpl;
import miuix.appcompat.app.strategy.DialogPanelBehaviorImpl;
import miuix.appcompat.internal.widget.DialogButtonPanel;
import miuix.appcompat.internal.widget.DialogParentPanel2;
import miuix.appcompat.internal.widget.DialogRootView;
import miuix.appcompat.internal.widget.NestedScrollViewExpandContainer;
import miuix.appcompat.internal.widget.NestedScrollViewExpander;
import miuix.appcompat.widget.DialogAnimHelper;
import miuix.core.util.EnvStateManager;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiuiBlurUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.core.util.RomUtils;
import miuix.core.util.ScreenModeHelper;
import miuix.core.util.SystemProperties;
import miuix.core.util.WindowBaseInfo;
import miuix.core.util.WindowUtils;
import miuix.device.DeviceUtils;
import miuix.internal.util.AnimHelper;
import miuix.internal.util.AsyncInflateLayoutManager;
import miuix.internal.util.AttributeResolver;
import miuix.internal.util.LiteUtils;
import miuix.internal.util.ViewUtils;
import miuix.internal.widget.GroupButton;
import miuix.os.Build;
import miuix.os.DeviceHelper;
import miuix.reflect.ReflectionHelper;
import miuix.theme.token.BloomStrokeToken;
import miuix.theme.token.ColorBlendToken;
import miuix.theme.token.DimToken;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;
import miuix.view.CompatViewMethod;
import miuix.view.DensityChangedHelper;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;
import miuix.view.animation.CubicEaseInOutInterpolator;

/* JADX INFO: loaded from: classes2.dex */
class AlertController {
    public static final MaterialToken Default_Dialog_Dark;
    public static final MaterialToken Default_Dialog_Light;
    public static final MaterialDayNightToken Default_Dialog_Material;
    public boolean buildJustNow;
    public Configuration configurationAfterInstalled;
    public ListAdapter mAdapter;
    public final int mAlertDialogLayout;
    public boolean mAsyncInflatePanelEnabled;
    public boolean mButtonForceVertical;
    public final View.OnClickListener mButtonHandler;
    public Button mButtonNegative;
    public Message mButtonNegativeMessage;
    public CharSequence mButtonNegativeText;
    public Button mButtonNeutral;
    public Message mButtonNeutralMessage;
    public CharSequence mButtonNeutralText;
    public Button mButtonPositive;
    public Message mButtonPositiveMessage;
    public CharSequence mButtonPositiveText;
    public boolean mCancelable;
    public boolean mCanceledOnTouchOutside;
    public CharSequence mCheckBoxMessage;
    public int mCheckedItem;
    public CharSequence mComment;
    public float mCommentTextSize;
    public TextView mCommentView;
    public AlertDialog.OnConfigurationChangedListener mConfigurationChangedListener;
    public boolean mContentForceFullScroll;
    public final Context mContext;
    public final Thread mCreateThread;
    public int mCurrentDensityDpi;
    public float mCustomTitleTextSize;
    public TextView mCustomTitleTextView;
    public View mCustomTitleView;
    public TextWatcher mDefaultButtonsTextWatcher;
    public final AppCompatDialog mDialog;
    public final DialogAnimHelper mDialogAnimHelper;
    public int mDialogContentLayout;
    public DialogRootView mDialogRootView;
    public View mDimBg;
    public boolean mDiscardImeAnimEnabled;
    public boolean mDiscardNaviBarHeightEnabled;
    public Rect mDisplayCutoutSafeInsets;
    public final DialogDisplayStrategy mDisplayStrategy;
    public boolean mEnableEnterAnim;
    public List mExtraButtonList;
    public DisplayCutout mFlipCutout;
    public Handler mHandler;
    public boolean mHapticFeedbackEnabled;
    public boolean mHasPendingDismiss;
    public Drawable mIcon;
    public int mIconHeight;
    public int mIconId;
    public int mIconWidth;
    public View mInflatedView;
    public boolean mInsetsAnimationPlayed;
    public boolean mIsAssociatedActivityHideNavigationBar;
    public boolean mIsCarWithScreen;
    public boolean mIsChecked;
    public boolean mIsDialogAnimating;
    public boolean mIsEnableImmersive;
    public boolean mIsFlipTinyScreen;
    public boolean mIsFromRebuild;
    public boolean mIsSynergy;
    public boolean mIsWindowLandScape;
    public boolean mLandscapePanel;
    public final LayoutChangeListener mLayoutChangeListener;
    public AlertDialog.OnDialogLayoutReloadListener mLayoutReloadListener;
    public int mListItemLayout;
    public int mListLayout;
    public ListView mListView;
    public int mLiteVersion;
    public boolean mMarkLandscape;
    public boolean mMaterialEnabled;
    public CharSequence mMessage;
    public float mMessageTextSize;
    public TextView mMessageView;
    public int mMinCustomVisibleHeight;
    public int mMultiChoiceItemLayout;
    public boolean mNavigationBarHiddenEnabled;
    public long mNonImmersiveDialogShowAnimDuration;
    public int mPanelAndImeMargin;
    public int mPanelBottomMargin;
    public int mPanelFixedHeight;
    public boolean mPanelFixedSizeEnabled;
    public int mPanelFixedWidth;
    public int mPanelParamsHorizontalMargin;
    public int mPanelParamsWidth;
    public AlertDialog.OnPanelSizeChangedListener mPanelSizeChangedListener;
    public DialogParentPanel2 mParentPanel;
    public boolean mPreferLandscape;
    public boolean mPrimaryButtonFirstEnabled;
    public Point mRootViewSize;
    public Point mRootViewSizeDp;
    public int mScreenMinorSize;
    public int mScreenOrientation;
    public Point mScreenRealSize;
    public boolean mSetupWindowInsetsAnimation;
    public AlertDialog.OnDialogShowAnimListener mShowAnimListener;
    public AlertDialog.OnDialogShowAnimListener mShowAnimListenerWrapper;
    public final boolean mShowTitle;
    public int mSingleChoiceItemLayout;
    public boolean mSmallIcon;
    public CharSequence mTitle;
    public float mTitleTextSize;
    public TextView mTitleView;
    public boolean mUseForceFlipCutout;
    public View mView;
    public int mViewLayoutResId;
    public final Window mWindow;
    public WindowManager mWindowManager;
    public boolean mIsDebugEnabled = false;
    public int mExtraImeMargin = -1;
    public boolean mIsInFreeForm = false;
    public int mNonImmersiveDialogHeight = -2;
    public long mShowUpTimeMillis = 0;
    public final DialogContract.DimensConfig mDimensConfig = new DialogContract.DimensConfig();

    /* JADX INFO: renamed from: miuix.appcompat.app.AlertController$11, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass11 {
        static final /* synthetic */ int[] $SwitchMap$miuix$appcompat$app$AlertController$AlertParams$ItemType;

        static {
            int[] iArr = new int[AlertParams.ItemType.values().length];
            $SwitchMap$miuix$appcompat$app$AlertController$AlertParams$ItemType = iArr;
            try {
                iArr[AlertParams.ItemType.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$miuix$appcompat$app$AlertController$AlertParams$ItemType[AlertParams.ItemType.CHOICE_SINGLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$miuix$appcompat$app$AlertController$AlertParams$ItemType[AlertParams.ItemType.CHOICE_MULTI.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.AlertController$2, reason: invalid class name */
    public class AnonymousClass2 implements AlertDialog.OnDialogShowAnimListener {
        public AnonymousClass2() {
        }

        @Override // miuix.appcompat.app.AlertDialog.OnDialogShowAnimListener
        public void onShowAnimComplete() {
            AlertController.this.mIsDialogAnimating = false;
            if (AlertController.this.mShowAnimListener != null) {
                AlertController.this.mShowAnimListener.onShowAnimComplete();
            }
            if (AlertController.this.mHasPendingDismiss) {
                AlertController alertController = AlertController.this;
                if (alertController.mDialog == null || alertController.mWindow == null) {
                    return;
                }
                AlertController.this.mWindow.getDecorView().post(new Runnable() { // from class: miuix.appcompat.app.AlertController$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AlertController.this.mDialog.dismiss();
                    }
                });
            }
        }

        @Override // miuix.appcompat.app.AlertDialog.OnDialogShowAnimListener
        public void onShowAnimStart() {
            AlertController.this.mIsDialogAnimating = true;
            if (AlertController.this.mShowAnimListener != null) {
                AlertController.this.mShowAnimListener.onShowAnimStart();
            }
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.AlertController$9, reason: invalid class name */
    public class AnonymousClass9 implements View.OnApplyWindowInsetsListener {
        public static /* synthetic */ void $r8$lambda$d7YB94WBn5fitIUReNmIbyAupgE(AnonymousClass9 anonymousClass9, View view) {
            anonymousClass9.getClass();
            WindowInsets rootWindowInsets = view.getRootWindowInsets();
            if (rootWindowInsets != null) {
                AlertController.this.updateDialogPanelByWindowInsets(rootWindowInsets);
            }
        }

        public AnonymousClass9() {
        }

        @Override // android.view.View.OnApplyWindowInsetsListener
        @RequiresApi
        public WindowInsets onApplyWindowInsets(final View view, WindowInsets windowInsets) {
            if (AlertController.this.mIsDebugEnabled) {
                Log.d("AlertController", "onApplyWindowInsets insets " + windowInsets);
            }
            AlertController alertController = AlertController.this;
            alertController.mPanelAndImeMargin = (int) (alertController.getDialogPanelMargin() + AlertController.this.mParentPanel.getTranslationY());
            if (view != null && windowInsets != null) {
                if (AlertController.this.mLayoutChangeListener != null) {
                    AlertController.this.mLayoutChangeListener.updateLayout(view);
                }
                AlertController.this.updateDialogPanelByWindowInsets(windowInsets);
                view.post(new Runnable() { // from class: miuix.appcompat.app.AlertController$9$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AlertController.AnonymousClass9.$r8$lambda$d7YB94WBn5fitIUReNmIbyAupgE(this.f$0, view);
                    }
                });
            }
            return WindowInsets.CONSUMED;
        }
    }

    public static class AlertParams {
        int iconHeight;
        int iconWidth;
        ListAdapter mAdapter;
        boolean mAsyncInflatePanelEnabled;
        boolean mButtonForceVertical;
        CharSequence mCheckBoxMessage;
        boolean[] mCheckedItems;
        CharSequence mComment;
        AlertDialog.OnConfigurationChangedListener mConfigurationChangedListener;
        final Context mContext;
        Cursor mCursor;
        View mCustomTitleView;
        boolean mDiscardImeAnimEnabled;
        boolean mEnableDialogImmersive;
        boolean mEnableEnterAnim;
        List<ButtonInfo> mExtraButtonList;
        boolean mHapticFeedbackEnabled;
        Drawable mIcon;
        final LayoutInflater mInflater;
        boolean mIsChecked;
        String mIsCheckedColumn;
        boolean mIsMultiChoice;
        boolean mIsSingleChoice;
        CharSequence[] mItems;
        AccessibilityDelegateProvider mItemsProvider;
        String mLabelColumn;
        int mLiteVersion;
        boolean mMaterialEnabled;
        CharSequence mMessage;
        DialogInterface.OnClickListener mNegativeButtonListener;
        CharSequence mNegativeButtonText;
        DialogInterface.OnClickListener mNeutralButtonListener;
        CharSequence mNeutralButtonText;
        int mNonImmersiveDialogHeight;
        DialogInterface.OnCancelListener mOnCancelListener;
        DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
        DialogInterface.OnClickListener mOnClickListener;
        AlertDialog.OnDialogShowAnimListener mOnDialogShowAnimListener;
        DialogInterface.OnDismissListener mOnDismissListener;
        AdapterView.OnItemSelectedListener mOnItemSelectedListener;
        DialogInterface.OnKeyListener mOnKeyListener;
        OnPrepareListViewListener mOnPrepareListViewListener;
        DialogInterface.OnShowListener mOnShowListener;
        AlertDialog.OnPanelSizeChangedListener mPanelSizeChangedListener;
        DialogInterface.OnClickListener mPositiveButtonListener;
        CharSequence mPositiveButtonText;
        boolean mPreferLandscape;
        boolean mSmallIcon;
        CharSequence mTitle;
        boolean mUseForceFlipCutout;
        View mView;
        int mViewLayoutResId;
        int mIconId = 0;
        int mIconAttrId = 0;
        int mCheckedItem = -1;
        int mMinCustomVisibleHeight = 0;
        boolean mPrimaryButtonFirstEnabled = false;
        boolean mCancelable = true;

        public enum ItemType {
            DEFAULT,
            CHOICE_SINGLE,
            CHOICE_MULTI
        }

        public interface OnPrepareListViewListener {
            void onPrepareListView(ListView listView);
        }

        public AlertParams(Context context) {
            this.mContext = context;
            this.mEnableDialogImmersive = (LiteUtils.isCommonLiteStrategy() || (Build.IS_FLIP && DeviceHelper.isTinyScreen(context))) ? false : true;
            this.mNonImmersiveDialogHeight = -2;
            int miuiLiteVersion = DeviceUtils.getMiuiLiteVersion();
            this.mLiteVersion = miuiLiteVersion;
            if (miuiLiteVersion < 0) {
                this.mLiteVersion = 0;
            }
            this.mEnableEnterAnim = true;
            this.mExtraButtonList = new ArrayList();
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        }

        private void createListView(final AlertController alertController) {
            final AlertController alertController2;
            final ListView listView;
            int i;
            ItemType itemType;
            AlertParams alertParams;
            ListAdapter listAdapter;
            ListAdapter listAdapter2;
            final ListView listView2 = (ListView) this.mInflater.inflate(alertController.mListLayout, (ViewGroup) null);
            if (!this.mIsMultiChoice) {
                alertController2 = alertController;
                listView = listView2;
                if (this.mIsSingleChoice) {
                    i = alertController2.mSingleChoiceItemLayout;
                    itemType = ItemType.CHOICE_SINGLE;
                } else {
                    i = alertController2.mListItemLayout;
                    itemType = ItemType.DEFAULT;
                }
                int i2 = i;
                final ItemType itemType2 = itemType;
                if (this.mCursor != null) {
                    alertParams = this;
                    listAdapter2 = new SimpleCursorAdapter(alertParams.mContext, i2, alertParams.mCursor, new String[]{alertParams.mLabelColumn}, new int[]{R.id.text1}) { // from class: miuix.appcompat.app.AlertController.AlertParams.4
                        @Override // android.widget.CursorAdapter, android.widget.Adapter
                        public View getView(int i3, View view, ViewGroup viewGroup) {
                            View view2 = super.getView(i3, view, viewGroup);
                            if (view == null) {
                                AnimHelper.addPressAnim(view2);
                            }
                            AlertParams.setAccessibilityDelegate(view2, itemType2, AlertParams.this.mItemsProvider);
                            return view2;
                        }
                    };
                } else {
                    alertParams = this;
                    ListAdapter listAdapter3 = alertParams.mAdapter;
                    if (listAdapter3 == null) {
                        listAdapter = listAdapter3;
                        CheckedItemAdapter checkedItemAdapter = new CheckedItemAdapter(alertParams.mContext, i2, R.id.text1, alertParams.mItems);
                        checkedItemAdapter.setItemsProvider(alertParams.mItemsProvider);
                        checkedItemAdapter.setItemType(itemType2);
                        listAdapter = checkedItemAdapter;
                    }
                    listAdapter = listAdapter3;
                    listAdapter2 = listAdapter;
                }
            } else if (this.mCursor == null) {
                ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(this.mContext, alertController.mMultiChoiceItemLayout, R.id.text1, this.mItems) { // from class: miuix.appcompat.app.AlertController.AlertParams.2
                    @Override // android.widget.ArrayAdapter, android.widget.Adapter
                    public View getView(int i3, View view, ViewGroup viewGroup) {
                        View view2 = super.getView(i3, view, viewGroup);
                        boolean[] zArr = AlertParams.this.mCheckedItems;
                        if (zArr != null && zArr[i3]) {
                            listView2.setItemChecked(i3, true);
                        }
                        CompatViewMethod.setForceDarkAllowed(view2, false);
                        AlertParams.setAccessibilityDelegate(view2, ItemType.CHOICE_MULTI, AlertParams.this.mItemsProvider);
                        return view2;
                    }
                };
                alertController2 = alertController;
                alertParams = this;
                listView = listView2;
                listAdapter2 = arrayAdapter;
            } else {
                CursorAdapter cursorAdapter = new CursorAdapter(this.mContext, this.mCursor, false) { // from class: miuix.appcompat.app.AlertController.AlertParams.3
                    private final int mIsCheckedIndex;
                    private final int mLabelIndex;

                    {
                        Cursor cursor = getCursor();
                        this.mLabelIndex = cursor.getColumnIndexOrThrow(AlertParams.this.mLabelColumn);
                        this.mIsCheckedIndex = cursor.getColumnIndexOrThrow(AlertParams.this.mIsCheckedColumn);
                    }

                    @Override // android.widget.CursorAdapter
                    public void bindView(View view, Context context, Cursor cursor) {
                        ((CheckedTextView) view.findViewById(R.id.text1)).setText(cursor.getString(this.mLabelIndex));
                        listView2.setItemChecked(cursor.getPosition(), cursor.getInt(this.mIsCheckedIndex) == 1);
                        AlertParams.setAccessibilityDelegate(view, ItemType.CHOICE_MULTI, AlertParams.this.mItemsProvider);
                    }

                    @Override // android.widget.CursorAdapter
                    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                        View viewInflate = AlertParams.this.mInflater.inflate(alertController.mMultiChoiceItemLayout, viewGroup, false);
                        CompatViewMethod.setForceDarkAllowed(viewInflate, false);
                        return viewInflate;
                    }
                };
                listView = listView2;
                alertController2 = alertController;
                alertParams = this;
                listAdapter2 = cursorAdapter;
            }
            OnPrepareListViewListener onPrepareListViewListener = alertParams.mOnPrepareListViewListener;
            if (onPrepareListViewListener != null) {
                onPrepareListViewListener.onPrepareListView(listView);
            }
            alertController2.mAdapter = listAdapter2;
            alertController2.mCheckedItem = alertParams.mCheckedItem;
            if (alertParams.mOnClickListener != null) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: miuix.appcompat.app.AlertController.AlertParams.5
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> adapterView, View view, int i3, long j) {
                        AlertParams.this.mOnClickListener.onClick(alertController2.mDialog, i3);
                        if (AlertParams.this.mIsSingleChoice) {
                            return;
                        }
                        alertController2.mDialog.dismiss();
                    }
                });
            } else if (alertParams.mOnCheckboxClickListener != null) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: miuix.appcompat.app.AlertController.AlertParams.6
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> adapterView, View view, int i3, long j) {
                        boolean[] zArr = AlertParams.this.mCheckedItems;
                        if (zArr != null) {
                            zArr[i3] = listView.isItemChecked(i3);
                        }
                        AlertParams.this.mOnCheckboxClickListener.onClick(alertController2.mDialog, i3, listView.isItemChecked(i3));
                    }
                });
            }
            AdapterView.OnItemSelectedListener onItemSelectedListener = alertParams.mOnItemSelectedListener;
            if (onItemSelectedListener != null) {
                listView.setOnItemSelectedListener(onItemSelectedListener);
            }
            if (alertParams.mIsSingleChoice) {
                listView.setChoiceMode(1);
            } else if (alertParams.mIsMultiChoice) {
                listView.setChoiceMode(2);
            }
            alertController2.mListView = listView;
        }

        private static AccessibilityDelegateCompat getDefaultAccessibilityDelegateCompat(final ItemType itemType) {
            return new AccessibilityDelegateCompat() { // from class: miuix.appcompat.app.AlertController.AlertParams.1
                @Override // androidx.core.view.AccessibilityDelegateCompat
                public void onInitializeAccessibilityNodeInfo(@NonNull View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                    int i = AnonymousClass11.$SwitchMap$miuix$appcompat$app$AlertController$AlertParams$ItemType[itemType.ordinal()];
                    if (i == 1) {
                        accessibilityNodeInfoCompat.addAction(16);
                        return;
                    }
                    if (i != 2) {
                        if (i != 3) {
                            return;
                        }
                        accessibilityNodeInfoCompat.setClassName(CheckBox.class.getName());
                        accessibilityNodeInfoCompat.addAction(16);
                        return;
                    }
                    accessibilityNodeInfoCompat.setCheckable(true);
                    accessibilityNodeInfoCompat.setClassName(RadioButton.class.getName());
                    if (view instanceof miuix.androidbasewidget.widget.CheckedTextView) {
                        boolean zIsChecked = ((miuix.androidbasewidget.widget.CheckedTextView) view).isChecked();
                        accessibilityNodeInfoCompat.setChecked(zIsChecked);
                        accessibilityNodeInfoCompat.setClickable(!zIsChecked);
                        if (zIsChecked) {
                            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
                        }
                    }
                }
            };
        }

        public static void setAccessibilityDelegate(View view, ItemType itemType, AccessibilityDelegateProvider accessibilityDelegateProvider) {
            AccessibilityDelegateCompat defaultAccessibilityDelegateCompat;
            if (accessibilityDelegateProvider != null) {
                defaultAccessibilityDelegateCompat = accessibilityDelegateProvider.getAccessibilityDelegate();
            } else {
                Log.i("AlertController", "type=" + itemType);
                defaultAccessibilityDelegateCompat = getDefaultAccessibilityDelegateCompat(itemType);
            }
            if (defaultAccessibilityDelegateCompat != null) {
                ViewCompat.setAccessibilityDelegate(view, defaultAccessibilityDelegateCompat);
            }
        }

        public void apply(AlertController alertController) {
            int i;
            View view = this.mCustomTitleView;
            if (view != null) {
                alertController.setCustomTitle(view);
            } else {
                CharSequence charSequence = this.mTitle;
                if (charSequence != null) {
                    alertController.setTitle(charSequence);
                }
                Drawable drawable = this.mIcon;
                if (drawable != null) {
                    alertController.setIcon(drawable);
                }
                int i2 = this.mIconId;
                if (i2 != 0) {
                    alertController.setIcon(i2);
                }
                int i3 = this.mIconAttrId;
                if (i3 != 0) {
                    alertController.setIcon(alertController.getIconAttributeResId(i3));
                }
                if (this.mSmallIcon) {
                    alertController.setUseSmallIcon(true);
                }
                int i4 = this.iconWidth;
                if (i4 != 0 && (i = this.iconHeight) != 0) {
                    alertController.setIconSize(i4, i);
                }
            }
            CharSequence charSequence2 = this.mMessage;
            if (charSequence2 != null) {
                alertController.setMessage(charSequence2);
            }
            CharSequence charSequence3 = this.mComment;
            if (charSequence3 != null) {
                alertController.setComment(charSequence3);
            }
            CharSequence charSequence4 = this.mPositiveButtonText;
            if (charSequence4 != null) {
                alertController.setButton(-1, charSequence4, this.mPositiveButtonListener, null);
            }
            CharSequence charSequence5 = this.mNegativeButtonText;
            if (charSequence5 != null) {
                alertController.setButton(-2, charSequence5, this.mNegativeButtonListener, null);
            }
            CharSequence charSequence6 = this.mNeutralButtonText;
            if (charSequence6 != null) {
                alertController.setButton(-3, charSequence6, this.mNeutralButtonListener, null);
            }
            if (this.mExtraButtonList != null) {
                alertController.mExtraButtonList = new ArrayList(this.mExtraButtonList);
            }
            if (this.mItems != null || this.mCursor != null || this.mAdapter != null) {
                createListView(alertController);
            }
            View view2 = this.mView;
            if (view2 != null) {
                alertController.setView(view2);
            } else {
                int i5 = this.mViewLayoutResId;
                if (i5 != 0) {
                    alertController.setView(i5);
                }
            }
            CharSequence charSequence7 = this.mCheckBoxMessage;
            if (charSequence7 != null) {
                alertController.setCheckBox(this.mIsChecked, charSequence7);
            }
            alertController.mHapticFeedbackEnabled = this.mHapticFeedbackEnabled;
            alertController.setEnableImmersive(this.mEnableDialogImmersive);
            alertController.setNonImmersiveDialogHeight(this.mNonImmersiveDialogHeight);
            alertController.setLiteVersion(this.mLiteVersion);
            alertController.setPreferLandscape(this.mPreferLandscape);
            alertController.setButtonForceVertical(this.mButtonForceVertical);
            alertController.setMinCustomVisibleHeight(this.mMinCustomVisibleHeight);
            alertController.setPrimaryButtonFirstEnabled(this.mPrimaryButtonFirstEnabled);
            alertController.setAsyncInflatePanelEnable(this.mAsyncInflatePanelEnabled);
            alertController.setDiscardImeAnimEnabled(this.mDiscardImeAnimEnabled);
            alertController.setPanelSizeChangedListener(this.mPanelSizeChangedListener);
            alertController.setConfigurationChangedListener(this.mConfigurationChangedListener);
            alertController.setEnableEnterAnim(this.mEnableEnterAnim);
            alertController.setUseForceFlipCutout(this.mUseForceFlipCutout);
            alertController.setMaterialEnabled(this.mMaterialEnabled);
        }
    }

    public static final class ButtonHandler extends Handler {
        private static final int MSG_DISMISS_DIALOG = -1651327837;
        private static final int MSG_RUN_ON_CLICK = -1651327821;
        private final WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialogInterface) {
            this.mDialog = new WeakReference<>(dialogInterface);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            DialogInterface dialogInterface = this.mDialog.get();
            int i = message.what;
            if (i == MSG_DISMISS_DIALOG) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            } else {
                Object obj = message.obj;
                if (obj instanceof DialogInterface.OnClickListener) {
                    ((DialogInterface.OnClickListener) obj).onClick(dialogInterface, i);
                }
            }
        }
    }

    public static class ButtonInfo {
        private GroupButton mButton;
        private Message mMsg;
        private final DialogInterface.OnClickListener mOnClickListener;
        private final int mStyle;
        private final CharSequence mText;
        private final int mWhich;

        public ButtonInfo(CharSequence charSequence, int i, DialogInterface.OnClickListener onClickListener, int i2) {
            this.mText = charSequence;
            this.mStyle = i;
            this.mMsg = null;
            this.mOnClickListener = onClickListener;
            this.mWhich = i2;
        }

        public ButtonInfo(CharSequence charSequence, int i, Message message) {
            this.mText = charSequence;
            this.mStyle = i;
            this.mMsg = message;
            this.mOnClickListener = null;
            this.mWhich = 0;
        }
    }

    public static class CheckedItemAdapter extends ArrayAdapter<CharSequence> {
        private AccessibilityDelegateProvider mItemsProvider;
        private AlertParams.ItemType mType;

        public CheckedItemAdapter(Context context, int i, int i2, CharSequence[] charSequenceArr) {
            super(context, i, i2, charSequenceArr);
            this.mType = AlertParams.ItemType.DEFAULT;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        @NonNull
        public View getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
            View view2 = super.getView(i, view, viewGroup);
            if (view == null) {
                AnimHelper.addPressAnim(view2);
            }
            AlertParams.setAccessibilityDelegate(view2, this.mType, this.mItemsProvider);
            return view2;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public boolean hasStableIds() {
            return true;
        }

        public void setItemType(AlertParams.ItemType itemType) {
            this.mType = itemType;
        }

        public void setItemsProvider(AccessibilityDelegateProvider accessibilityDelegateProvider) {
            this.mItemsProvider = accessibilityDelegateProvider;
        }
    }

    public static class LayoutChangeListener implements View.OnLayoutChangeListener {
        private final WeakReference<AlertController> mHost;
        private final Rect mCutoutInsets = new Rect();
        private final Rect mWindowVisibleFrame = new Rect();

        public LayoutChangeListener(AlertController alertController) {
            this.mHost = new WeakReference<>(alertController);
        }

        private void changeViewPadding(View view, int i, int i2) {
            view.setPadding(i, 0, i2, 0);
        }

        private void handleImeChange(View view, Rect rect, AlertController alertController) {
            int height = (view.getHeight() - alertController.getDialogPanelExtraBottomPadding()) - rect.bottom;
            int i = 0;
            if (height > 0) {
                int i2 = -height;
                WindowInsets rootWindowInsets = view.getRootWindowInsets();
                i = (rootWindowInsets != null ? rootWindowInsets.getInsets(WindowInsets.Type.systemBars()).bottom : 0) + i2;
                alertController.mDialogAnimHelper.cancelAnimator();
            }
            alertController.translateDialogPanel(i);
        }

        private void handleMultiWindowLandscapeChange(AlertController alertController, int i) {
            if (!MiuixUIUtils.isInMultiWindowMode(alertController.mContext)) {
                DialogRootView dialogRootView = alertController.mDialogRootView;
                if (dialogRootView.getPaddingLeft() > 0 || dialogRootView.getPaddingRight() > 0) {
                    changeViewPadding(dialogRootView, 0, 0);
                    return;
                }
                return;
            }
            boolean z = android.os.Build.VERSION.SDK_INT >= 36;
            Rect rect = this.mCutoutInsets;
            boolean z2 = (rect.left > 0 || rect.right > 0) && z;
            Rect rect2 = this.mWindowVisibleFrame;
            if (rect2.left <= 0 || z2) {
                changeViewPadding(alertController.mDialogRootView, 0, 0);
                return;
            }
            int iWidth = i - rect2.width();
            if (this.mWindowVisibleFrame.right == i) {
                changeViewPadding(alertController.mDialogRootView, iWidth, 0);
            } else {
                changeViewPadding(alertController.mDialogRootView, 0, iWidth);
            }
        }

        private void updateCutoutInsets(View view, Rect rect) {
            Insets insets;
            WindowInsets rootWindowInsets = view != null ? view.getRootWindowInsets() : null;
            if (rootWindowInsets == null || (insets = rootWindowInsets.getInsets(WindowInsets.Type.displayCutout())) == null) {
                return;
            }
            rect.left = insets.left;
            rect.top = insets.top;
            rect.right = insets.right;
            rect.bottom = insets.bottom;
        }

        public boolean hasNavigationBarHeightInMultiWindowMode() {
            WindowUtils.getScreenSize(this.mHost.get().mContext, this.mHost.get().mScreenRealSize);
            Rect rect = this.mWindowVisibleFrame;
            return (rect.left == 0 && rect.right == this.mHost.get().mScreenRealSize.x && this.mWindowVisibleFrame.top <= EnvStateManager.getStatusBarHeight(this.mHost.get().mContext, false)) ? false : true;
        }

        public boolean isInMultiScreenTop() {
            AlertController alertController = this.mHost.get();
            if (alertController != null) {
                WindowUtils.getScreenSize(alertController.mContext, alertController.mScreenRealSize);
                Rect rect = this.mWindowVisibleFrame;
                if (rect.left == 0 && rect.right == alertController.mScreenRealSize.x) {
                    int i = (int) (alertController.mScreenRealSize.y * 0.75f);
                    Rect rect2 = this.mWindowVisibleFrame;
                    if (rect2.top >= 0 && rect2.bottom <= i) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            AlertController alertController = this.mHost.get();
            if (alertController != null) {
                view.getWindowVisibleDisplayFrame(this.mWindowVisibleFrame);
                updateCutoutInsets(view, this.mCutoutInsets);
                handleMultiWindowLandscapeChange(alertController, i3);
            }
        }

        public void updateLayout(View view) {
            AlertController alertController = this.mHost.get();
            if (alertController != null) {
                view.getWindowVisibleDisplayFrame(this.mWindowVisibleFrame);
                updateCutoutInsets(view, this.mCutoutInsets);
                handleMultiWindowLandscapeChange(alertController, view.getWidth());
            }
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$Pw0qBqi6r3HY-5z-RHrBLgL5e20, reason: not valid java name */
    public static /* synthetic */ void m1296$r8$lambda$Pw0qBqi6r3HY5zRHrBLgL5e20(AlertController alertController, View view) {
        if (alertController.isCancelable() && alertController.isCanceledOnTouchOutside()) {
            alertController.mDialog.cancel();
        }
    }

    public static /* synthetic */ void $r8$lambda$jE6pvLNj4CZboVf3EARLR8dMFUM(AlertController alertController, View view) {
        if (alertController.isCancelable() && alertController.isCanceledOnTouchOutside()) {
            alertController.hideSoftIME();
            alertController.mDialog.cancel();
        }
    }

    public static /* synthetic */ void $r8$lambda$pRwptev8px0nmKWPnQSW7TMtyQo(AlertController alertController) {
        AlertDialog.OnPanelSizeChangedListener onPanelSizeChangedListener = alertController.mPanelSizeChangedListener;
        if (onPanelSizeChangedListener != null) {
            onPanelSizeChangedListener.onPanelSizeChanged((AlertDialog) alertController.mDialog, alertController.mParentPanel);
        }
    }

    static {
        MaterialToken materialTokenBuild = new MaterialToken.Builder(30, "dialog-default", "light").setColorBlend(ColorBlendToken.Pured_Regular_Glass_Light).setMaskBlur(60).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Light).build();
        Default_Dialog_Light = materialTokenBuild;
        MaterialToken materialTokenBuild2 = new MaterialToken.Builder(30, "dialog-default", "dark").setColorBlend(ColorBlendToken.Pured_Regular_Glass_Dark).setMaskBlur(60).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Dark).build();
        Default_Dialog_Dark = materialTokenBuild2;
        Default_Dialog_Material = new MaterialDayNightToken(materialTokenBuild, materialTokenBuild2);
    }

    public AlertController(Context context, AppCompatDialog appCompatDialog, Window window) {
        DialogDisplayStrategy dialogDisplayStrategy = new DialogDisplayStrategy();
        this.mDisplayStrategy = dialogDisplayStrategy;
        this.mPanelFixedWidth = -1;
        this.mPanelFixedHeight = -1;
        this.mPanelFixedSizeEnabled = false;
        this.mDefaultButtonsTextWatcher = new TextWatcher() { // from class: miuix.appcompat.app.AlertController.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (AlertController.this.mParentPanel != null) {
                    DialogParentPanel2 dialogParentPanel2 = AlertController.this.mParentPanel;
                    int i = miuix.appcompat.R.id.buttonPanel;
                    if (dialogParentPanel2.findViewById(i) != null) {
                        AlertController.this.mParentPanel.findViewById(i).requestLayout();
                    }
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        };
        this.mIconId = 0;
        this.mCustomTitleTextView = null;
        this.mCheckedItem = -1;
        this.mDialogAnimHelper = new DialogAnimHelper();
        this.mCancelable = true;
        this.mCanceledOnTouchOutside = true;
        this.mScreenOrientation = 0;
        this.mTitleTextSize = 18.0f;
        this.mMessageTextSize = 16.0f;
        this.mCommentTextSize = 13.0f;
        this.mCustomTitleTextSize = 18.0f;
        this.mRootViewSize = new Point();
        this.mRootViewSizeDp = new Point();
        this.mScreenRealSize = new Point();
        this.mDisplayCutoutSafeInsets = new Rect();
        this.mHasPendingDismiss = false;
        this.mUseForceFlipCutout = false;
        this.mNavigationBarHiddenEnabled = false;
        this.mIsAssociatedActivityHideNavigationBar = false;
        this.mDiscardNaviBarHeightEnabled = false;
        this.mShowAnimListenerWrapper = new AnonymousClass2();
        this.mMinCustomVisibleHeight = 0;
        this.mPrimaryButtonFirstEnabled = false;
        this.mMaterialEnabled = false;
        this.mDiscardImeAnimEnabled = false;
        this.mButtonHandler = new View.OnClickListener() { // from class: miuix.appcompat.app.AlertController.3
            /* JADX WARN: Code duplicated, block: B:43:0x0093  */
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                boolean z;
                int i = HapticFeedbackConstants.MIUI_TAP_LIGHT;
                AlertController alertController = AlertController.this;
                Message messageObtain = null;
                if (view == alertController.mButtonPositive) {
                    Message message = alertController.mButtonPositiveMessage;
                    messageObtain = message != null ? Message.obtain(message) : null;
                    i = HapticFeedbackConstants.MIUI_TAP_NORMAL;
                } else if (view == alertController.mButtonNegative) {
                    Message message2 = alertController.mButtonNegativeMessage;
                    if (message2 != null) {
                        messageObtain = Message.obtain(message2);
                    }
                } else if (view == alertController.mButtonNeutral) {
                    Message message3 = alertController.mButtonNeutralMessage;
                    if (message3 != null) {
                        messageObtain = Message.obtain(message3);
                    }
                } else {
                    if (alertController.mExtraButtonList != null && !AlertController.this.mExtraButtonList.isEmpty()) {
                        for (ButtonInfo buttonInfo : AlertController.this.mExtraButtonList) {
                            if (view == buttonInfo.mButton) {
                                Message messageObtain2 = buttonInfo.mMsg;
                                if (messageObtain2 != null) {
                                    messageObtain2 = Message.obtain(messageObtain2);
                                }
                                messageObtain = messageObtain2;
                                break;
                            }
                        }
                    }
                    if ((view instanceof GroupButton) && ((GroupButton) view).isPrimary()) {
                        i = HapticFeedbackConstants.MIUI_TAP_NORMAL;
                    }
                }
                HapticCompat.performHapticFeedbackAsync(view, HapticFeedbackConstants.MIUI_BUTTON_MIDDLE, i);
                if (messageObtain != null) {
                    messageObtain.sendToTarget();
                    Bundle data = messageObtain.getData();
                    if (data == null || !data.containsKey("BUTTON_CLICK_AUTO_DISMISSIBLE")) {
                        z = true;
                    } else {
                        z = data.getBoolean("BUTTON_CLICK_AUTO_DISMISSIBLE");
                    }
                } else {
                    z = true;
                }
                AlertController.this.mHandler.sendEmptyMessage(z ? -1651327837 : -1651327821);
            }
        };
        this.mInsetsAnimationPlayed = false;
        dialogDisplayStrategy.setPanelBehavior(new DialogPanelBehaviorImpl()).setButtonBehavior(new DialogButtonBehaviorImpl());
        this.mContext = context;
        this.mCurrentDensityDpi = context.getResources().getConfiguration().densityDpi;
        this.mDialog = appCompatDialog;
        this.mWindow = window;
        this.mEnableEnterAnim = true;
        this.mNonImmersiveDialogShowAnimDuration = context.getResources().getInteger(miuix.appcompat.R.integer.dialog_enter_duration);
        this.mHandler = new ButtonHandler(appCompatDialog);
        this.mLayoutChangeListener = new LayoutChangeListener(this);
        boolean z = Build.IS_FLIP && DeviceHelper.isTinyScreen(context);
        this.mIsFlipTinyScreen = z;
        this.mPanelBottomMargin = z ? context.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_width_small_margin) : context.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_ime_margin);
        this.mIsEnableImmersive = (LiteUtils.isCommonLiteStrategy() || this.mIsFlipTinyScreen) ? false : true;
        updateDisplayInfo(context);
        initScreenMinorSize(context);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, miuix.appcompat.R.styleable.AlertDialog, R.attr.alertDialogStyle, 0);
        this.mAlertDialogLayout = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.AlertDialog_layout, 0);
        this.mListLayout = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.AlertDialog_listLayout, 0);
        this.mMultiChoiceItemLayout = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.AlertDialog_multiChoiceItemLayout, 0);
        this.mSingleChoiceItemLayout = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.AlertDialog_singleChoiceItemLayout, 0);
        this.mListItemLayout = typedArrayObtainStyledAttributes.getResourceId(miuix.appcompat.R.styleable.AlertDialog_listItemLayout, 0);
        this.mShowTitle = typedArrayObtainStyledAttributes.getBoolean(miuix.appcompat.R.styleable.AlertDialog_showTitle, true);
        typedArrayObtainStyledAttributes.recycle();
        appCompatDialog.supportRequestWindowFeature(1);
        updateDimensConfig(context.getResources());
        this.mMarkLandscape = context.getResources().getBoolean(miuix.appcompat.R.bool.treat_as_land);
        this.mCreateThread = Thread.currentThread();
        isDialogImeDebugEnabled();
        if (this.mIsDebugEnabled) {
            Log.d("AlertController", "create Dialog mCurrentDensityDpi " + this.mCurrentDensityDpi);
        }
    }

    public static boolean canTextInput(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        while (childCount > 0) {
            childCount--;
            if (canTextInput(viewGroup.getChildAt(childCount))) {
                return true;
            }
        }
        return false;
    }

    public final void adjustHeight2WrapContent() {
        ViewGroup.LayoutParams layoutParams = this.mListView.getLayoutParams();
        layoutParams.height = -2;
        this.mListView.setLayoutParams(layoutParams);
    }

    public final void applyFullScrollBottomPaddingLayout(ViewGroup viewGroup, boolean z) {
        boolean z2;
        boolean z3;
        if (this.mLandscapePanel || viewGroup == null || this.mParentPanel == null) {
            return;
        }
        ViewParent parent = viewGroup.getParent();
        if (parent instanceof NestedScrollViewExpandContainer) {
            NestedScrollViewExpandContainer nestedScrollViewExpandContainer = (NestedScrollViewExpandContainer) parent;
            ViewGroup.LayoutParams layoutParams = nestedScrollViewExpandContainer.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_panel_vertical_padding);
                boolean z4 = true;
                if (!z) {
                    if (nestedScrollViewExpandContainer.getPaddingBottom() != 0) {
                        nestedScrollViewExpandContainer.setPadding(nestedScrollViewExpandContainer.getPaddingLeft(), nestedScrollViewExpandContainer.getPaddingTop(), nestedScrollViewExpandContainer.getPaddingRight(), 0);
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (!nestedScrollViewExpandContainer.getClipToPadding()) {
                        nestedScrollViewExpandContainer.setClipToPadding(true);
                        z2 = true;
                    }
                    if (marginLayoutParams.bottomMargin != 0) {
                        marginLayoutParams.bottomMargin = 0;
                        nestedScrollViewExpandContainer.setLayoutParams(marginLayoutParams);
                        z2 = true;
                    }
                    if (this.mParentPanel.getClipToPadding()) {
                        z4 = z2;
                    } else {
                        this.mParentPanel.setClipToPadding(true);
                    }
                    if (z4) {
                        this.mParentPanel.requestLayout();
                        return;
                    }
                    return;
                }
                if (nestedScrollViewExpandContainer.getPaddingBottom() != dimensionPixelSize) {
                    nestedScrollViewExpandContainer.setPadding(nestedScrollViewExpandContainer.getPaddingLeft(), nestedScrollViewExpandContainer.getPaddingTop(), nestedScrollViewExpandContainer.getPaddingRight(), dimensionPixelSize);
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (nestedScrollViewExpandContainer.getClipToPadding()) {
                    nestedScrollViewExpandContainer.setClipToPadding(false);
                    z3 = true;
                }
                int i = -dimensionPixelSize;
                if (marginLayoutParams.bottomMargin != i) {
                    marginLayoutParams.bottomMargin = i;
                    nestedScrollViewExpandContainer.setLayoutParams(marginLayoutParams);
                    z3 = true;
                }
                if (this.mParentPanel.getClipToPadding()) {
                    this.mParentPanel.setClipToPadding(false);
                } else {
                    z4 = z3;
                }
                if (z4) {
                    this.mParentPanel.requestLayout();
                }
            }
        }
    }

    public final boolean buttonNeedScrollable(DialogButtonPanel dialogButtonPanel, int i) {
        int buttonFullyVisibleHeight = dialogButtonPanel.getButtonFullyVisibleHeight();
        ViewGroup viewGroup = (ViewGroup) this.mParentPanel.findViewById(miuix.appcompat.R.id.topPanel);
        int height = viewGroup != null ? viewGroup.getHeight() : 0;
        int i2 = EnvStateManager.getWindowSize(this.mContext).y;
        boolean z = MiuixUIUtils.getFontLevel(this.mContext) == 2;
        DialogContract.ButtonScrollSpec buttonScrollSpec = new DialogContract.ButtonScrollSpec();
        buttonScrollSpec.updateData(buttonFullyVisibleHeight, dialogButtonPanel.getHeight(), i2, height, this.mIsFlipTinyScreen, getScreenOrientation(), i, this.mRootViewSizeDp.y, z, this.mListView != null);
        if (this.mIsDebugEnabled) {
            Log.d("AlertController", "buttonNeedScrollable: buttonScrollSpec = " + buttonScrollSpec);
        }
        return ifNeedMoveButtonToContentPanel(dialogButtonPanel, viewGroup) || this.mDisplayStrategy.isButtonScrollable(buttonScrollSpec);
    }

    public final void changeTitlePadding(TextView textView) {
        textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), 0);
    }

    public final void checkAndClearFocus() {
        View currentFocus = this.mWindow.getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.clearFocus();
        }
    }

    public final boolean checkThread() {
        return this.mCreateThread == Thread.currentThread();
    }

    public final void cleanWindowInsetsAnimation() {
        if (this.mSetupWindowInsetsAnimation) {
            this.mWindow.getDecorView().setWindowInsetsAnimationCallback(null);
            this.mWindow.getDecorView().setOnApplyWindowInsetsListener(null);
            this.mSetupWindowInsetsAnimation = false;
        }
    }

    public final void clearFitSystemWindow(View view) {
        if ((view instanceof DialogParentPanel2) || view == null) {
            return;
        }
        int i = 0;
        view.setFitsSystemWindows(false);
        if (!(view instanceof ViewGroup)) {
            return;
        }
        while (true) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (i >= viewGroup.getChildCount()) {
                return;
            }
            clearFitSystemWindow(viewGroup.getChildAt(i));
            i++;
        }
    }

    public final int computeParentPanelMaxHeight() {
        float f;
        boolean zIsPortrait = WindowUtils.isPortrait(this.mContext);
        boolean z = this.mIsFlipTinyScreen || this.mIsInFreeForm;
        Point screenSize = EnvStateManager.getScreenSize(this.mContext);
        int iPx2dp = MiuixUIUtils.px2dp(this.mContext, screenSize.y);
        if (z) {
            return screenSize.y;
        }
        if (!zIsPortrait && iPx2dp < 500) {
            f = screenSize.y * 0.9f;
        } else {
            int i = screenSize.y;
            f = i * 0.7f;
        }
        return (int) f;
    }

    public final void disableForceDark(View view) {
        CompatViewMethod.setForceDarkAllowed(view, false);
    }

    public void dismiss(DialogAnimHelper.OnDismiss onDismiss) {
        cleanWindowInsetsAnimation();
        if (this.mParentPanel == null) {
            if (onDismiss != null) {
                onDismiss.end();
                return;
            }
            return;
        }
        if (this.mDimBg != null) {
            updateDimBgBottomMargin(0);
        }
        if (!this.mParentPanel.isAttachedToWindow()) {
            Log.d("AlertController", "dialog is not attached to window when dismiss is invoked");
            try {
                ((AlertDialog) this.mDialog).realDismiss();
                return;
            } catch (IllegalArgumentException e) {
                Log.wtf("AlertController", "Not catch the dialog will throw the illegalArgumentException (In Case cause the crash , we expect it should be caught)", e);
                return;
            }
        }
        checkAndClearFocus();
        if (isTablet()) {
            hideSoftIME();
        } else {
            WindowBaseInfo windowInfo = EnvStateManager.getWindowInfo(this.mContext);
            if (ScreenModeHelper.isInFreeFormMode(windowInfo.windowMode) || ScreenModeHelper.isInSplitScreenMode(windowInfo.windowMode)) {
                hideSoftIME();
            }
        }
        this.mDialogAnimHelper.executeDismissAnim(this.mParentPanel, isTablet(), this.mDimBg, onDismiss);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return keyEvent.getKeyCode() == 82;
    }

    public final void expandListViewToFullHeight() {
        ListAdapter adapter = this.mListView.getAdapter();
        if (adapter == null) {
            return;
        }
        int width = this.mListView.getWidth();
        if (width <= 0) {
            this.mListView.post(new Runnable() { // from class: miuix.appcompat.app.AlertController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.expandListViewToFullHeight();
                }
            });
            return;
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, Integer.MIN_VALUE);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        int count = adapter.getCount();
        int dividerHeight = 0;
        for (int i = 0; i < count; i++) {
            View view = adapter.getView(i, null, this.mListView);
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            dividerHeight += view.getMeasuredHeight();
        }
        if (count > 1) {
            dividerHeight += this.mListView.getDividerHeight() * (count - 1);
        }
        ViewGroup.LayoutParams layoutParams = this.mListView.getLayoutParams();
        if (layoutParams.height != dividerHeight) {
            layoutParams.height = dividerHeight;
            this.mListView.setLayoutParams(layoutParams);
        }
        ViewCompat.setNestedScrollingEnabled(this.mListView, false);
    }

    public final Insets getAssociatedActivityInsets(int i) {
        WindowInsets rootWindowInsets;
        Activity associatedActivity = ((AlertDialog) this.mDialog).getAssociatedActivity();
        if (associatedActivity == null || (rootWindowInsets = associatedActivity.getWindow().getDecorView().getRootWindowInsets()) == null) {
            return null;
        }
        return rootWindowInsets.getInsets(i);
    }

    public final boolean getAssociatedActivitySystemBarVisibility(Activity activity, int i) {
        if (activity == null || activity.getWindow() == null) {
            return true;
        }
        View decorView = activity.getWindow().getDecorView();
        WindowInsets rootWindowInsets = decorView != null ? decorView.getRootWindowInsets() : null;
        if (rootWindowInsets != null) {
            return rootWindowInsets.isVisible(i);
        }
        return true;
    }

    public final Point getAvailableWindowSizeDp(WindowInsets windowInsets) {
        Point point = new Point();
        Point point2 = this.mRootViewSizeDp;
        int i = point2.x;
        int i2 = point2.y;
        new Rect();
        Rect naviBarInsets = getNaviBarInsets(windowInsets, true);
        if (this.mIsFlipTinyScreen) {
            Rect guaranteedCutout = getGuaranteedCutout(windowInsets, true);
            i -= guaranteedCutout.left + guaranteedCutout.right;
            i2 -= guaranteedCutout.top + guaranteedCutout.bottom;
        } else if (!isDialogImmersive()) {
            Insets associatedActivityInsets = getAssociatedActivityInsets(WindowInsets.Type.displayCutout());
            if (associatedActivityInsets != null) {
                Rect rectPx2dp = px2dp(new Rect(associatedActivityInsets.left, associatedActivityInsets.top, associatedActivityInsets.right, associatedActivityInsets.bottom));
                i -= rectPx2dp.left + rectPx2dp.right;
                i2 -= rectPx2dp.top + rectPx2dp.bottom;
            }
            if (this.mIsDebugEnabled) {
                Log.d("AlertController", "getAvailableWindowSizeDp: cutoutInsets = " + associatedActivityInsets);
            }
        }
        int i3 = i - (naviBarInsets.left + naviBarInsets.right);
        int i4 = i2 - (naviBarInsets.top + naviBarInsets.bottom);
        point.x = i3;
        point.y = i4;
        return point;
    }

    public Button getButton(int i) {
        if (i == -3) {
            return this.mButtonNeutral;
        }
        if (i == -2) {
            return this.mButtonNegative;
        }
        if (i == -1) {
            return this.mButtonPositive;
        }
        List list = this.mExtraButtonList;
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (ButtonInfo buttonInfo : this.mExtraButtonList) {
            if (buttonInfo.mWhich == i) {
                return buttonInfo.mButton;
            }
        }
        return null;
    }

    public final int getCutoutMode(int i, int i2) {
        if (i2 == 0) {
            return i == 2 ? 2 : 1;
        }
        return i2;
    }

    public final DisplayCutout getCutoutSafely() {
        if (showSystemAlertInFlip() && this.mFlipCutout != null) {
            printDebugMsg("getCutoutSafely", "show system alert in flip, use displayCutout by reflect, displayCutout = " + this.mFlipCutout, false);
            return this.mFlipCutout;
        }
        try {
            DisplayCutout cutout = this.mContext.getDisplay().getCutout();
            printDebugMsg("getCutoutSafely", "get displayCutout from context = " + cutout, false);
            return cutout;
        } catch (Exception e) {
            Log.e("AlertController", "context is not associated display info, please check the type of context, the exception info = " + Log.getStackTraceString(e));
            WindowManager windowManager = this.mWindowManager;
            Display defaultDisplay = windowManager != null ? windowManager.getDefaultDisplay() : null;
            if (defaultDisplay != null) {
                return defaultDisplay.getCutout();
            }
            return null;
        }
    }

    public final int getDialogPanelExtraBottomPadding() {
        return 0;
    }

    public final int getDialogPanelMargin() {
        int[] iArr = new int[2];
        this.mParentPanel.getLocationInWindow(iArr);
        if (this.mExtraImeMargin == -1) {
            this.mExtraImeMargin = this.mDimensConfig.extraImeMargin;
        }
        int height = this.mWindow.getDecorView().getHeight();
        int height2 = this.mParentPanel.getHeight();
        int iMax = iArr[1];
        WindowInsets rootWindowInsets = this.mWindow.getDecorView().getRootWindowInsets();
        boolean zIsVisible = rootWindowInsets != null ? rootWindowInsets.isVisible(WindowInsets.Type.ime()) : false;
        if (isTablet() && isDialogImmersive() && zIsVisible && (iMax = Math.max(0, (height - height2) / 2)) == 0) {
            iMax = iArr[1];
        }
        return (height - (iMax + height2)) - this.mExtraImeMargin;
    }

    public final Rect getDisplayCutout(boolean z) {
        Rect rect = new Rect();
        Insets associatedActivityInsets = getAssociatedActivityInsets(WindowInsets.Type.displayCutout());
        if (associatedActivityInsets != null) {
            rect.set(associatedActivityInsets.left, associatedActivityInsets.top, associatedActivityInsets.right, associatedActivityInsets.bottom);
            printDebugMsg("getDisplayCutout", "get cutout from host, cutout = " + rect.flattenToString(), false);
        } else {
            DisplayCutout cutoutSafely = getCutoutSafely();
            rect.left = cutoutSafely != null ? cutoutSafely.getSafeInsetLeft() : 0;
            rect.top = cutoutSafely != null ? cutoutSafely.getSafeInsetTop() : 0;
            rect.right = cutoutSafely != null ? cutoutSafely.getSafeInsetRight() : 0;
            rect.bottom = cutoutSafely != null ? cutoutSafely.getSafeInsetBottom() : 0;
        }
        return z ? px2dp(rect) : rect;
    }

    public final void getFlipCutout() {
        if (this.mIsFlipTinyScreen) {
            try {
                Display display = this.mContext.getDisplay();
                if (display != null) {
                    this.mFlipCutout = (DisplayCutout) ReflectionHelper.getMethod(display.getClass(), "getFlipFoldedCutout", new Class[0]).invoke(display, new Object[0]);
                } else {
                    this.mFlipCutout = null;
                }
            } catch (Exception unused) {
                printDebugMsg("getFlipCutout", "can't reflect from display to query cutout", false);
                this.mFlipCutout = null;
            }
        }
    }

    public final int getFreeFormAvoidSpace(Rect rect) {
        int i;
        int i2;
        if (rect != null) {
            i2 = rect.top;
            i = rect.bottom;
        } else {
            i = 0;
            i2 = 0;
        }
        if (i2 == 0 || i == 0) {
            Insets associatedActivityInsets = getAssociatedActivityInsets(WindowInsets.Type.captionBar());
            i2 = associatedActivityInsets != null ? associatedActivityInsets.top : 0;
            i = associatedActivityInsets != null ? associatedActivityInsets.bottom : 0;
        }
        if (i2 == 0) {
            if (isTablet()) {
                i2 = this.mDimensConfig.freeTabletCompactHeight;
            } else {
                DialogContract.DimensConfig dimensConfig = this.mDimensConfig;
                i2 = dimensConfig.freePhoneCompactHeight + dimensConfig.extraImeMargin;
            }
        }
        if (i == 0) {
            if (isTablet()) {
                i = this.mDimensConfig.freeTabletCompactHeight;
            } else {
                DialogContract.DimensConfig dimensConfig2 = this.mDimensConfig;
                i = dimensConfig2.freePhoneCompactHeight + dimensConfig2.extraImeMargin;
            }
        }
        return i2 + i;
    }

    public final int getGravity() {
        return isTablet() ? 17 : 81;
    }

    public final Rect getGuaranteedCutout(WindowInsets windowInsets, boolean z) {
        Rect rect = new Rect();
        if (windowInsets == null) {
            return getDisplayCutout(z);
        }
        Insets insets = windowInsets.getInsets(WindowInsets.Type.displayCutout());
        rect.set(insets.left, insets.top, insets.right, insets.bottom);
        if (z) {
            px2dp(rect);
        }
        return rect;
    }

    public int getIconAttributeResId(int i) {
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(i, typedValue, true);
        return typedValue.resourceId;
    }

    public final int getImeBottomByWindowInsets(WindowInsets windowInsets) {
        if (windowInsets == null) {
            windowInsets = this.mWindow.getDecorView().getRootWindowInsets();
        }
        if (windowInsets != null) {
            Insets insets = windowInsets.getInsets(WindowInsets.Type.ime());
            if (insets != null) {
                return insets.bottom;
            }
            return 0;
        }
        Insets associatedActivityInsets = getAssociatedActivityInsets(WindowInsets.Type.ime());
        if (associatedActivityInsets != null) {
            return associatedActivityInsets.bottom;
        }
        return 0;
    }

    public ListView getListView() {
        return this.mListView;
    }

    public final Rect getNaviBarInsets(WindowInsets windowInsets, boolean z) {
        Rect rect = new Rect();
        if (windowInsets == null) {
            windowInsets = this.mDialogRootView.getRootWindowInsets();
        }
        if (windowInsets != null) {
            Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars());
            rect.set(insetsIgnoringVisibility.left, insetsIgnoringVisibility.top, insetsIgnoringVisibility.right, insetsIgnoringVisibility.bottom);
            if (z) {
                return px2dp(rect);
            }
        } else {
            Insets associatedActivityInsets = getAssociatedActivityInsets(WindowInsets.Type.navigationBars());
            if (associatedActivityInsets == null) {
                int navigationBarHeight = EnvStateManager.getNavigationBarHeight(this.mContext, z);
                int rotationSafely = getRotationSafely();
                if (rotationSafely == 1) {
                    rect.right = navigationBarHeight;
                    return rect;
                }
                if (rotationSafely == 2) {
                    rect.top = navigationBarHeight;
                    return rect;
                }
                if (rotationSafely != 3) {
                    rect.bottom = navigationBarHeight;
                    return rect;
                }
                rect.left = navigationBarHeight;
                return rect;
            }
            rect.set(associatedActivityInsets.left, associatedActivityInsets.top, associatedActivityInsets.right, associatedActivityInsets.bottom);
            if (z) {
                return px2dp(rect);
            }
        }
        return rect;
    }

    public int getPanelBottomMargin() {
        return this.mPanelBottomMargin;
    }

    public final int getPanelMaxLimitHeight(Rect rect) {
        int i;
        int i2;
        int iMax;
        int i3 = EnvStateManager.getWindowSize(this.mContext).y;
        int i4 = this.mIsFlipTinyScreen ? this.mDimensConfig.widthSmallMargin : this.mDimensConfig.extraImeMargin;
        if (rect != null) {
            i2 = rect.top;
            i = rect.bottom;
        } else {
            Insets associatedActivityInsets = getAssociatedActivityInsets(WindowInsets.Type.systemBars());
            int i5 = associatedActivityInsets != null ? associatedActivityInsets.top : 0;
            i = associatedActivityInsets != null ? associatedActivityInsets.bottom : 0;
            i2 = i5;
        }
        if (isLhasa()) {
            int statusBarHeight = EnvStateManager.getStatusBarHeight(this.mContext, false);
            int navigationBarHeight = EnvStateManager.getNavigationBarHeight(this.mContext, false);
            int iMax2 = Math.max(i2, statusBarHeight);
            if (i > 0) {
                navigationBarHeight = i;
            }
            iMax = Math.max(0, (i3 - iMax2) - navigationBarHeight);
        } else {
            iMax = (i3 - Math.max(i2, i4)) - (i + i4);
        }
        if (this.mIsDebugEnabled) {
            Log.d("AlertController", "getPanelMaxLimitHeight: boundInset = " + rect + ", topInset = " + i2 + ", bottomInset = " + i + ", windowHeight = " + i3 + ", verticalMargin = " + i4 + ", panelMaxLimitHeight = " + iMax);
        }
        if (this.mIsInFreeForm) {
            iMax = i3 - getFreeFormAvoidSpace(rect);
        }
        return this.mIsFlipTinyScreen ? i3 - (Math.max(i2, EnvStateManager.getStatusBarHeight(this.mContext, false)) + i4) : iMax;
    }

    public final int getRotationSafely() {
        try {
            return this.mContext.getDisplay().getRotation();
        } catch (Exception e) {
            Log.e("AlertController", "context is not associated display info, please check the type of context, the exception info = " + Log.getStackTraceString(e));
            WindowManager windowManager = this.mWindowManager;
            Display defaultDisplay = windowManager != null ? windowManager.getDefaultDisplay() : null;
            if (defaultDisplay != null) {
                return defaultDisplay.getRotation();
            }
            return 0;
        }
    }

    public final int getScreenOrientation() {
        WindowManager windowManager = this.mWindowManager;
        if (windowManager == null) {
            return 0;
        }
        int rotation = windowManager.getDefaultDisplay().getRotation();
        return (rotation == 1 || rotation == 3) ? 2 : 1;
    }

    public int getSingleItemMinHeight() {
        return AttributeResolver.resolveDimensionPixelSize(this.mContext, miuix.appcompat.R.attr.dialogListPreferredItemHeight);
    }

    public final int getVisibleButtonCount() {
        Button button = this.mButtonPositive;
        int i = 1;
        if (button == null) {
            i = 1 ^ (TextUtils.isEmpty(this.mButtonPositiveText) ? 1 : 0);
        } else if (button.getVisibility() != 0) {
            i = 0;
        }
        Button button2 = this.mButtonNegative;
        if (button2 == null ? !TextUtils.isEmpty(this.mButtonNegativeText) : button2.getVisibility() == 0) {
            i++;
        }
        Button button3 = this.mButtonNeutral;
        if (button3 == null ? !TextUtils.isEmpty(this.mButtonNeutralText) : button3.getVisibility() == 0) {
            i++;
        }
        List list = this.mExtraButtonList;
        if (list != null && !list.isEmpty()) {
            Iterator it = this.mExtraButtonList.iterator();
            while (it.hasNext()) {
                GroupButton groupButton = ((ButtonInfo) it.next()).mButton;
                if (groupButton == null || groupButton.getVisibility() == 0) {
                    i++;
                }
            }
        }
        return i;
    }

    public boolean hasPendingDismiss() {
        return this.mHasPendingDismiss;
    }

    public final void hideSoftIME() {
        InputMethodManager inputMethodManager = (InputMethodManager) ContextCompat.getSystemService(this.mContext, InputMethodManager.class);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(this.mParentPanel.getWindowToken(), 0);
        }
    }

    public final boolean ifNeedMoveButtonToContentPanel(DialogButtonPanel dialogButtonPanel, ViewGroup viewGroup) {
        int height;
        DialogParentPanel2 dialogParentPanel2 = this.mParentPanel;
        if (dialogParentPanel2 == null || dialogButtonPanel == null || viewGroup == null || this.mLandscapePanel || (height = dialogParentPanel2.getHeight()) == 0) {
            return false;
        }
        int paddingTop = this.mParentPanel.getPaddingTop();
        int paddingBottom = this.mParentPanel.getPaddingBottom();
        int height2 = viewGroup.getHeight();
        int height3 = dialogButtonPanel.getHeight();
        ViewGroup.LayoutParams layoutParams = dialogButtonPanel.getLayoutParams();
        int i = layoutParams instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams).topMargin : 0;
        if (this.mIsDebugEnabled) {
            Log.d("AlertController", "ifNeedMoveButtonToContentPanel: topPanelHeight = " + height2 + ", buttonPanelHeight = " + height3 + ", buttonPanelMarginTop = " + i + ", parentPanelPaddingTop = " + paddingTop + ", parentPanelPaddingBottom = " + paddingBottom + ", parentPanelHeight = " + height);
        }
        return (((height2 + height3) + i) + paddingTop) + paddingBottom > height;
    }

    public final void inflateDialogLayout() {
        this.mLandscapePanel = false;
        int i = miuix.appcompat.R.layout.miuix_appcompat_alert_dialog_content;
        if (this.mPreferLandscape && shouldUseLandscapePanel()) {
            i = miuix.appcompat.R.layout.miuix_appcompat_alert_dialog_content_land;
            this.mLandscapePanel = true;
        }
        if (this.mDialogContentLayout != i) {
            this.mDialogContentLayout = i;
            DialogParentPanel2 dialogParentPanel2 = this.mParentPanel;
            if (dialogParentPanel2 != null) {
                this.mDialogRootView.removeView(dialogParentPanel2);
            }
            if (this.mAsyncInflatePanelEnabled) {
                this.mParentPanel = (DialogParentPanel2) AsyncInflateLayoutManager.getInstance().inflateViewById(Integer.valueOf(this.mDialogContentLayout), this.mContext, this.mDialogRootView, false);
                if (this.mIsDebugEnabled) {
                    Log.w("AlertController", "inflateDialogLayout: parentPanel.getParent = " + this.mParentPanel.getParent());
                    Log.w("AlertController", "inflateDialogLayout: mParentPanel.getTag = " + this.mParentPanel.getTag());
                }
                DialogParentPanel2 dialogParentPanel3 = this.mParentPanel;
                if (dialogParentPanel3 != null && dialogParentPanel3.getParent() != null) {
                    this.mParentPanel = (DialogParentPanel2) LayoutInflater.from(this.mContext).inflate(this.mDialogContentLayout, (ViewGroup) this.mDialogRootView, false);
                }
            } else {
                this.mParentPanel = (DialogParentPanel2) LayoutInflater.from(this.mContext).inflate(this.mDialogContentLayout, (ViewGroup) this.mDialogRootView, false);
            }
            if (this.mPanelFixedSizeEnabled) {
                this.mParentPanel.setPanelFixedWidth(this.mPanelFixedWidth);
                this.mParentPanel.setPanelFixedHeight(this.mPanelFixedHeight);
            }
            this.mParentPanel.setIsInTinyScreen(this.mIsFlipTinyScreen || isLhasa());
            this.mParentPanel.setIsDebugEnabled(this.mIsDebugEnabled);
            this.mParentPanel.setPanelMaxLimitHeight(getPanelMaxLimitHeight(null));
            this.mDialogRootView.addView(this.mParentPanel);
        }
    }

    public final void initScreenMinorSize(Context context) {
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        updateMinorScreenSize();
    }

    public void installContent(Bundle bundle) {
        this.mIsFromRebuild = bundle != null;
        this.mIsInFreeForm = MiuixUIUtils.isFreeformMode(this.mContext);
        getFlipCutout();
        this.mDialog.setContentView(this.mAlertDialogLayout);
        this.mDialogRootView = (DialogRootView) this.mWindow.findViewById(miuix.appcompat.R.id.dialog_root_view);
        this.mDimBg = this.mWindow.findViewById(miuix.appcompat.R.id.dialog_dim_bg);
        this.mDialogRootView.setConfigurationChangedCallback(new DialogRootView.ConfigurationChangedCallback() { // from class: miuix.appcompat.app.AlertController.4
            @Override // miuix.appcompat.internal.widget.DialogRootView.ConfigurationChangedCallback
            public void onConfigurationChanged(Configuration configuration, int i, int i2, int i3, int i4) {
                AlertController.this.onConfigurationChanged(configuration, false, false);
            }
        });
        Configuration configuration = this.mContext.getResources().getConfiguration();
        updateRootViewSize((Configuration) null);
        setupWindow();
        setupView();
        this.mParentPanel.setCornerRadius(MiuixUIUtils.getWidgetCornerRadius(this.mContext, this.mPanelBottomMargin));
        this.configurationAfterInstalled = configuration;
        this.buildJustNow = true;
        this.mDialogRootView.post(new Runnable() { // from class: miuix.appcompat.app.AlertController.5
            @Override // java.lang.Runnable
            public void run() {
                if (AlertController.this.isDialogImmersive()) {
                    AlertController alertController = AlertController.this;
                    alertController.updateRootViewSize(alertController.mDialogRootView);
                }
                ViewGroup viewGroup = (ViewGroup) AlertController.this.mParentPanel.findViewById(miuix.appcompat.R.id.contentPanel);
                ViewGroup viewGroup2 = (ViewGroup) AlertController.this.mParentPanel.findViewById(miuix.appcompat.R.id.buttonPanel);
                if (viewGroup2 == null || viewGroup == null || AlertController.this.shouldUseLandscapePanel()) {
                    return;
                }
                AlertController.this.updateButtons(viewGroup2, viewGroup);
            }
        });
    }

    public boolean isAsyncInflatePanelEnabled() {
        return this.mAsyncInflatePanelEnabled;
    }

    public final boolean isCancelable() {
        return this.mCancelable;
    }

    public boolean isCanceledOnTouchOutside() {
        return this.mCanceledOnTouchOutside;
    }

    public final boolean isConfigurationChanged(Configuration configuration) {
        Configuration configuration2 = this.configurationAfterInstalled;
        return (configuration2.uiMode != configuration.uiMode) || (configuration2.screenLayout != configuration.screenLayout) || (configuration2.orientation != configuration.orientation) || (configuration2.screenWidthDp != configuration.screenWidthDp) || (configuration2.screenHeightDp != configuration.screenHeightDp) || ((configuration2.fontScale > configuration.fontScale ? 1 : (configuration2.fontScale == configuration.fontScale ? 0 : -1)) != 0) || (configuration2.smallestScreenWidthDp != configuration.smallestScreenWidthDp) || (configuration2.keyboard != configuration.keyboard);
    }

    public final boolean isDialogImeDebugEnabled() {
        String str = "";
        try {
            String str2 = SystemProperties.get("log.tag.alertdialog.ime.debug.enable");
            if (str2 != null) {
                str = str2;
            }
        } catch (Exception e) {
            Log.i("AlertController", "can not access property log.tag.alertdialog.ime.enable, undebugable", e);
        }
        Log.d("AlertController", "Alert dialog ime debugEnable = " + str);
        boolean zEquals = TextUtils.equals(a.i, str);
        this.mIsDebugEnabled = zEquals;
        return zEquals;
    }

    public boolean isDialogImmersive() {
        return this.mIsEnableImmersive;
    }

    public boolean isFlipTinyScreen() {
        return this.mIsFlipTinyScreen;
    }

    public final boolean isFreeFormMode() {
        return EnvStateManager.isFreeFormMode(this.mContext);
    }

    public final boolean isLhasa() {
        return DeviceUtils.isLhasaDevice();
    }

    public final boolean isLhasaInternalScreen() {
        return isLhasa() && DeviceHelper.isInternalScreen(this.mContext);
    }

    public final boolean isLightTheme() {
        Context context = this.mContext;
        if (context == null) {
            return true;
        }
        boolean zIsNightMode = ViewUtils.isNightMode(context);
        TextView textView = this.mTitleView;
        if (textView != null) {
            return !MiuixUIUtils.isLightColor(textView.getCurrentTextColor());
        }
        TextView textView2 = this.mMessageView;
        if (textView2 != null) {
            return !MiuixUIUtils.isLightColor(textView2.getCurrentTextColor());
        }
        TextView textView3 = this.mCommentView;
        if (textView3 != null) {
            return !MiuixUIUtils.isLightColor(textView3.getCurrentTextColor());
        }
        TextView textView4 = this.mCustomTitleTextView;
        return textView4 != null ? !MiuixUIUtils.isLightColor(textView4.getCurrentTextColor()) : zIsNightMode;
    }

    public final boolean isNeedUpdateDialogPanelTranslationY() {
        byte b;
        boolean zIsInMultiWindowMode = MiuixUIUtils.isInMultiWindowMode(this.mContext);
        int i = this.mContext.getResources().getConfiguration().keyboard;
        boolean z = i == 2 || i == 3;
        boolean z2 = Build.IS_TABLET;
        if (!zIsInMultiWindowMode || isFreeFormMode()) {
            b = -1;
        } else {
            b = DeviceHelper.isWideScreen(this.mContext) ? (byte) 0 : (byte) 1;
        }
        if (this.mIsDialogAnimating) {
            return !(z2 && z) && b == 0;
        }
        return !(z2 && z) && this.mSetupWindowInsetsAnimation && (this.mInsetsAnimationPlayed || zIsInMultiWindowMode);
    }

    public boolean isShowingAnimation() {
        return this.mEnableEnterAnim && (this.mIsDialogAnimating || (!isDialogImmersive() && (Math.abs(this.mShowUpTimeMillis - SystemClock.uptimeMillis()) > this.mNonImmersiveDialogShowAnimDuration ? 1 : (Math.abs(this.mShowUpTimeMillis - SystemClock.uptimeMillis()) == this.mNonImmersiveDialogShowAnimDuration ? 0 : -1)) < 0));
    }

    public final boolean isSpecifiedDialogHeight() {
        return (isDialogImmersive() || this.mNonImmersiveDialogHeight == -2) ? false : true;
    }

    public final boolean isTablet() {
        return Build.IS_TABLET || this.mIsCarWithScreen;
    }

    public final boolean listViewIsNeedFullScroll() {
        int singleItemMinHeight = getSingleItemMinHeight();
        int count = this.mAdapter.getCount() * singleItemMinHeight;
        boolean z = MiuixUIUtils.getFontLevel(this.mContext) == 2;
        int iComputeParentPanelMaxHeight = computeParentPanelMaxHeight();
        if (this.mIsDebugEnabled) {
            Log.i("AlertController", "listViewIsNeedFullScroll: itemsMinHeight = " + count + ", singleItemMinHeight = " + singleItemMinHeight + ", panelMaxHeight = " + iComputeParentPanelMaxHeight);
        }
        if (!z || iComputeParentPanelMaxHeight <= 0) {
            return count > ((int) (((float) this.mRootViewSize.y) * 0.35f));
        }
        float f = (count * 1.0f) / iComputeParentPanelMaxHeight;
        if (this.mIsDebugEnabled) {
            Log.i("AlertController", "listViewIsNeedFullScroll: radioInMaxHeight = " + f);
        }
        return f >= 0.3f;
    }

    public void onAttachedToWindow() {
        reInitLandConfig();
        setupWindowInsetsAnimation();
    }

    public void onConfigurationChanged(Configuration configuration, boolean z, boolean z2) {
        boolean z3 = true;
        this.mIsFlipTinyScreen = Build.IS_FLIP && DeviceHelper.isTinyScreen(this.mContext);
        this.mIsInFreeForm = MiuixUIUtils.isFreeformMode(this.mContext);
        this.mPanelBottomMargin = this.mIsFlipTinyScreen ? this.mContext.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_width_small_margin) : this.mContext.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_ime_margin);
        updateDimensConfig(this.mContext.getResources());
        getFlipCutout();
        updateDisplayInfo(this.mContext);
        int i = configuration.densityDpi;
        float f = (i * 1.0f) / this.mCurrentDensityDpi;
        if (f != 1.0f) {
            this.mCurrentDensityDpi = i;
        }
        if (this.mIsDebugEnabled) {
            Log.d("AlertController", "onConfigurationChangednewDensityDpi " + this.mCurrentDensityDpi + " densityScale " + f);
        }
        if (!this.buildJustNow || isConfigurationChanged(configuration) || this.mIsFlipTinyScreen || z) {
            this.buildJustNow = false;
            this.mExtraImeMargin = -1;
            updateRootViewSize((Configuration) null);
            if (this.mIsDebugEnabled) {
                Log.d("AlertController", "onConfigurationChanged mRootViewSize " + this.mRootViewSize);
            }
            if (!checkThread()) {
                Log.w("AlertController", "dialog is created in thread:" + this.mCreateThread + ", but onConfigurationChanged is called from different thread:" + Thread.currentThread() + ", so this onConfigurationChanged call should be ignore");
                return;
            }
            if (isDialogImmersive()) {
                this.mWindow.getDecorView().removeOnLayoutChangeListener(this.mLayoutChangeListener);
            }
            if (this.mWindow.getDecorView().isAttachedToWindow()) {
                if (f != 1.0f) {
                    this.mDimensConfig.panelMaxWidth = this.mContext.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_max_width);
                    this.mDimensConfig.panelMaxWidthLand = this.mContext.getResources().getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_max_width_land);
                }
                reInitLandConfig();
                if (isDialogImmersive()) {
                    updateWindowCutoutMode();
                } else {
                    setupNonImmersiveWindow();
                }
                DialogParentPanel2 dialogParentPanel2 = this.mParentPanel;
                if (!this.mIsFlipTinyScreen && !isLhasa()) {
                    z3 = false;
                }
                dialogParentPanel2.setIsInTinyScreen(z3);
                this.mParentPanel.setIsDebugEnabled(this.mIsDebugEnabled);
                setupView(false, z, z2, f);
                this.mParentPanel.notifyConfigurationChanged();
                this.mParentPanel.setCornerRadius(MiuixUIUtils.getWidgetCornerRadius(this.mContext, this.mPanelBottomMargin));
            }
            if (isDialogImmersive()) {
                this.mLayoutChangeListener.updateLayout(this.mWindow.getDecorView());
                this.mWindow.getDecorView().addOnLayoutChangeListener(this.mLayoutChangeListener);
                WindowInsets rootWindowInsets = this.mWindow.getDecorView().getRootWindowInsets();
                if (rootWindowInsets != null) {
                    updateDialogPanelByWindowInsets(rootWindowInsets);
                }
                this.mDialogRootView.post(new Runnable() { // from class: miuix.appcompat.app.AlertController.10
                    @Override // java.lang.Runnable
                    public void run() {
                        WindowInsets rootWindowInsets2 = AlertController.this.mWindow.getDecorView().getRootWindowInsets();
                        if (rootWindowInsets2 != null) {
                            AlertController.this.updateDialogPanelByWindowInsets(rootWindowInsets2);
                        }
                    }
                });
            }
            this.mParentPanel.setPanelMaxLimitHeight(getPanelMaxLimitHeight(null));
            setupMaterial();
            AlertDialog.OnConfigurationChangedListener onConfigurationChangedListener = this.mConfigurationChangedListener;
            if (onConfigurationChangedListener != null) {
                onConfigurationChangedListener.onConfigurationChanged(this.mDialog, this.mParentPanel, configuration);
            }
        }
    }

    public void onDetachedFromWindow() {
        if (!AnimHelper.isDialogDebugInAndroidUIThreadEnabled()) {
            Folme.clean(this.mParentPanel, this.mDimBg);
            translateDialogPanel(0);
        }
        if (isDialogImmersive() && isAsyncInflatePanelEnabled()) {
            safeRemovePanelFromParent();
        }
    }

    public final void onLayoutReload() {
        ((AlertDialog) this.mDialog).onLayoutReload();
        AlertDialog.OnDialogLayoutReloadListener onDialogLayoutReloadListener = this.mLayoutReloadListener;
        if (onDialogLayoutReloadListener != null) {
            onDialogLayoutReloadListener.onLayoutReload();
        }
    }

    public void onStart() {
        if (isDialogImmersive()) {
            if (this.mDimBg != null) {
                updateDimBgBottomMargin(0);
            }
            reInitLandConfig();
            updateWindowCutoutMode();
            if (this.mIsFromRebuild || !this.mEnableEnterAnim || this.mDimBg == null) {
                this.mParentPanel.setTag(null);
                View view = this.mDimBg;
                if (view != null) {
                    view.setAlpha(ViewUtils.isNightMode(this.mContext) ? DimToken.DIM_DARK : DimToken.DIM_LIGHT);
                }
            } else {
                this.mDialogAnimHelper.setDiscardImeAnimEnabled(this.mDiscardImeAnimEnabled);
                this.mDialogAnimHelper.executeShowAnim(this.mParentPanel, this.mDimBg, isTablet(), this.mIsWindowLandScape, this.mShowAnimListenerWrapper);
            }
            this.mLayoutChangeListener.updateLayout(this.mWindow.getDecorView());
            this.mWindow.getDecorView().addOnLayoutChangeListener(this.mLayoutChangeListener);
        }
    }

    public void onStop() {
        if (isDialogImmersive()) {
            this.mWindow.getDecorView().removeOnLayoutChangeListener(this.mLayoutChangeListener);
        }
    }

    public final void printDebugMsg(String str, String str2, boolean z) {
        if (this.mIsDebugEnabled || z) {
            Log.d("AlertController", str + ": " + str2);
        }
    }

    public final Rect px2dp(Rect rect) {
        float f = this.mContext.getResources().getDisplayMetrics().densityDpi / 160.0f;
        rect.left = MiuixUIUtils.px2dp(f, rect.left);
        rect.top = MiuixUIUtils.px2dp(f, rect.top);
        rect.right = MiuixUIUtils.px2dp(f, rect.right);
        rect.bottom = MiuixUIUtils.px2dp(f, rect.bottom);
        return rect;
    }

    public final void reInitLandConfig() {
        this.mMarkLandscape = this.mContext.getResources().getBoolean(miuix.appcompat.R.bool.treat_as_land);
        updateMinorScreenSize();
    }

    public final void recordButtonTypeForOS3Display(ViewGroup viewGroup) {
        if (RomUtils.getHyperOsVersion() > 2 || this.mPrimaryButtonFirstEnabled) {
            boolean z = viewGroup instanceof DialogButtonPanel;
            if (z && (this.mButtonPositive instanceof GroupButton)) {
                DialogButtonPanel dialogButtonPanel = (DialogButtonPanel) viewGroup;
                dialogButtonPanel.clearPrimaryStyleButtonList();
                dialogButtonPanel.addPrimaryStyleButtons((GroupButton) this.mButtonPositive);
            }
            if (z && (this.mButtonNegative instanceof GroupButton)) {
                DialogButtonPanel dialogButtonPanel2 = (DialogButtonPanel) viewGroup;
                dialogButtonPanel2.clearNegativeStyleButtonList();
                dialogButtonPanel2.addNegativeStyleButtons((GroupButton) this.mButtonNegative);
            }
            if (z && (this.mButtonNeutral instanceof GroupButton)) {
                DialogButtonPanel dialogButtonPanel3 = (DialogButtonPanel) viewGroup;
                dialogButtonPanel3.clearNeutralStyleButtonList();
                dialogButtonPanel3.addNeutralStyleButtons((GroupButton) this.mButtonNeutral);
            }
            List list = this.mExtraButtonList;
            if (list == null || list.isEmpty()) {
                return;
            }
            for (ButtonInfo buttonInfo : this.mExtraButtonList) {
                if (buttonInfo != null && buttonInfo.mButton != null && z) {
                    if (buttonInfo.mStyle == 16843913 || buttonInfo.mStyle == miuix.appcompat.R.attr.buttonBarPrimaryButtonStyle) {
                        ((DialogButtonPanel) viewGroup).addPrimaryStyleButtons(buttonInfo.mButton);
                    } else {
                        if (buttonInfo.mStyle != 16843915) {
                            int i = buttonInfo.mStyle;
                            int i2 = miuix.appcompat.R.attr.buttonBarButtonStyle;
                            if (i != i2 && buttonInfo.mStyle != 16843567) {
                                if (buttonInfo.mStyle == 16843914 || buttonInfo.mStyle == i2) {
                                    ((DialogButtonPanel) viewGroup).addNeutralStyleButtons(buttonInfo.mButton);
                                }
                            }
                        }
                        ((DialogButtonPanel) viewGroup).addNegativeStyleButtons(buttonInfo.mButton);
                    }
                }
            }
        }
    }

    public final void resetListMaxHeight() {
        int i = (int) (this.mRootViewSize.y * 0.35f);
        int singleItemMinHeight = getSingleItemMinHeight();
        int i2 = singleItemMinHeight * (i / singleItemMinHeight);
        this.mListView.setMinimumHeight(i2);
        ViewGroup.LayoutParams layoutParams = this.mListView.getLayoutParams();
        layoutParams.height = i2;
        this.mListView.setLayoutParams(layoutParams);
    }

    public final void restoreListViewScroll() {
        ViewGroup.LayoutParams layoutParams = this.mListView.getLayoutParams();
        if (layoutParams != null && layoutParams.height != -2) {
            layoutParams.height = -2;
            this.mListView.setLayoutParams(layoutParams);
        }
        ViewCompat.setNestedScrollingEnabled(this.mListView, true);
    }

    public void safeMoveView(View view, ViewGroup viewGroup) {
        ViewGroup viewGroup2 = (ViewGroup) view.getParent();
        if (viewGroup2 == viewGroup) {
            return;
        }
        if (viewGroup2 != null) {
            viewGroup2.removeView(view);
        }
        viewGroup.addView(view);
    }

    public void safeRemoveFromParent(View view) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
    }

    public void safeRemovePanelFromParent() {
        if (this.mParentPanel == null) {
            return;
        }
        if (isAsyncInflatePanelEnabled()) {
            this.mParentPanel.setTag(null);
        }
        ViewGroup viewGroup = (ViewGroup) this.mParentPanel.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this.mParentPanel);
        }
    }

    public final void setAnimIfEditExistForNonImmersive(View view) {
        if (!this.mEnableEnterAnim || view == null || isTablet() || isDialogImmersive() || !canTextInput(view)) {
            return;
        }
        this.mWindow.setWindowAnimations(miuix.appcompat.R.style.Animation_Dialog_ExistIme);
    }

    public void setAsyncInflatePanelEnable(boolean z) {
        this.mAsyncInflatePanelEnabled = z;
    }

    public void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message) {
        if (message == null && onClickListener != null) {
            message = this.mHandler.obtainMessage(i, onClickListener);
        }
        if (message != null && message.getTarget() == null) {
            message.setTarget(this.mHandler);
        }
        if (i == -3) {
            this.mButtonNeutralText = charSequence;
            this.mButtonNeutralMessage = message;
        } else if (i == -2) {
            this.mButtonNegativeText = charSequence;
            this.mButtonNegativeMessage = message;
        } else {
            if (i != -1) {
                throw new IllegalArgumentException("Button does not exist");
            }
            this.mButtonPositiveText = charSequence;
            this.mButtonPositiveMessage = message;
        }
    }

    public void setButtonForceVertical(boolean z) {
        this.mButtonForceVertical = z;
    }

    public void setCancelable(boolean z) {
        this.mCancelable = z;
    }

    public void setCanceledOnTouchOutside(boolean z) {
        this.mCanceledOnTouchOutside = z;
    }

    public void setCheckBox(boolean z, CharSequence charSequence) {
        this.mIsChecked = z;
        this.mCheckBoxMessage = charSequence;
    }

    public void setComment(CharSequence charSequence) {
        this.mComment = charSequence;
        TextView textView = this.mCommentView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void setConfigurationChangedListener(AlertDialog.OnConfigurationChangedListener onConfigurationChangedListener) {
        this.mConfigurationChangedListener = onConfigurationChangedListener;
    }

    public void setContentForceFullScroll(boolean z) {
        this.mContentForceFullScroll = z;
    }

    public void setCustomTitle(View view) {
        this.mCustomTitleView = view;
    }

    public void setDiscardImeAnimEnabled(boolean z) {
        this.mDiscardImeAnimEnabled = z;
    }

    public void setDiscardNaviBarHeightEnabled(boolean z) {
        this.mDiscardNaviBarHeightEnabled = z;
    }

    public void setEnableEnterAnim(boolean z) {
        this.mEnableEnterAnim = z;
    }

    public void setEnableImmersive(boolean z) {
        this.mIsEnableImmersive = z;
    }

    public void setIcon(int i) {
        this.mIcon = null;
        this.mIconId = i;
    }

    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        this.mIconId = 0;
    }

    public void setIconSize(int i, int i2) {
        this.mIconWidth = i;
        this.mIconHeight = i2;
    }

    public void setLiteVersion(int i) {
        this.mLiteVersion = i;
    }

    public void setMaterialEnabled(boolean z) {
        this.mMaterialEnabled = z;
    }

    public void setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
        TextView textView = this.mMessageView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void setMinCustomVisibleHeight(int i) {
        this.mMinCustomVisibleHeight = i;
    }

    public void setNavigationHiddenEnabled(boolean z) {
        this.mNavigationBarHiddenEnabled = z;
    }

    public void setNonImmersiveDialogHeight(int i) {
        this.mNonImmersiveDialogHeight = i;
    }

    public void setPanelFixedHeight(int i) {
        this.mPanelFixedHeight = i;
    }

    public void setPanelFixedSizeEnabled(boolean z) {
        this.mPanelFixedSizeEnabled = z;
    }

    public void setPanelSizeChangedListener(AlertDialog.OnPanelSizeChangedListener onPanelSizeChangedListener) {
        this.mPanelSizeChangedListener = onPanelSizeChangedListener;
    }

    public void setPendingDismiss(boolean z) {
        this.mHasPendingDismiss = z;
    }

    public void setPreferLandscape(boolean z) {
        this.mPreferLandscape = z;
    }

    public void setPrimaryButtonFirstEnabled(boolean z) {
        this.mPrimaryButtonFirstEnabled = z;
    }

    public void setShowAnimListener(AlertDialog.OnDialogShowAnimListener onDialogShowAnimListener) {
        this.mShowAnimListener = onDialogShowAnimListener;
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void setUseForceFlipCutout(boolean z) {
        this.mUseForceFlipCutout = z;
    }

    public void setUseSmallIcon(boolean z) {
        this.mSmallIcon = z;
    }

    public void setView(int i) {
        this.mView = null;
        this.mViewLayoutResId = i;
    }

    public void setView(View view) {
        this.mView = view;
        this.mViewLayoutResId = 0;
    }

    public final void setWindowNavigationBarHidden() {
        View decorView = this.mWindow.getDecorView();
        if (decorView != null) {
            decorView.setSystemUiVisibility(4098);
            this.mIsAssociatedActivityHideNavigationBar = true;
        }
    }

    public final void setupButtons(ViewGroup viewGroup) {
        int i;
        Button button = (Button) viewGroup.findViewById(R.id.button1);
        this.mButtonPositive = button;
        button.setOnClickListener(this.mButtonHandler);
        this.mButtonPositive.removeTextChangedListener(this.mDefaultButtonsTextWatcher);
        this.mButtonPositive.addTextChangedListener(this.mDefaultButtonsTextWatcher);
        boolean z = true;
        if (TextUtils.isEmpty(this.mButtonPositiveText)) {
            this.mButtonPositive.setVisibility(8);
            i = 0;
        } else {
            this.mButtonPositive.setText(this.mButtonPositiveText);
            this.mButtonPositive.setVisibility(0);
            disableForceDark(this.mButtonPositive);
            i = 1;
        }
        Button button2 = (Button) viewGroup.findViewById(R.id.button2);
        this.mButtonNegative = button2;
        button2.setOnClickListener(this.mButtonHandler);
        this.mButtonNegative.removeTextChangedListener(this.mDefaultButtonsTextWatcher);
        this.mButtonNegative.addTextChangedListener(this.mDefaultButtonsTextWatcher);
        if (TextUtils.isEmpty(this.mButtonNegativeText)) {
            this.mButtonNegative.setVisibility(8);
        } else {
            this.mButtonNegative.setText(this.mButtonNegativeText);
            this.mButtonNegative.setVisibility(0);
            i++;
            disableForceDark(this.mButtonNegative);
        }
        Button button3 = (Button) viewGroup.findViewById(R.id.button3);
        this.mButtonNeutral = button3;
        button3.setOnClickListener(this.mButtonHandler);
        this.mButtonNeutral.removeTextChangedListener(this.mDefaultButtonsTextWatcher);
        this.mButtonNeutral.addTextChangedListener(this.mDefaultButtonsTextWatcher);
        if (TextUtils.isEmpty(this.mButtonNeutralText)) {
            this.mButtonNeutral.setVisibility(8);
        } else {
            this.mButtonNeutral.setText(this.mButtonNeutralText);
            this.mButtonNeutral.setVisibility(0);
            i++;
            disableForceDark(this.mButtonNeutral);
        }
        List list = this.mExtraButtonList;
        if (list != null && !list.isEmpty()) {
            for (ButtonInfo buttonInfo : this.mExtraButtonList) {
                if (buttonInfo.mButton != null) {
                    safeRemoveFromParent(buttonInfo.mButton);
                }
            }
            for (ButtonInfo buttonInfo2 : this.mExtraButtonList) {
                if (buttonInfo2.mButton == null) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
                    buttonInfo2.mButton = new GroupButton(this.mContext, null, buttonInfo2.mStyle);
                    buttonInfo2.mButton.setText(buttonInfo2.mText);
                    buttonInfo2.mButton.setOnClickListener(this.mButtonHandler);
                    buttonInfo2.mButton.setLayoutParams(layoutParams);
                    buttonInfo2.mButton.setMaxLines(1);
                    buttonInfo2.mButton.setGravity(17);
                }
                if (buttonInfo2.mMsg == null) {
                    buttonInfo2.mMsg = this.mHandler.obtainMessage(buttonInfo2.mWhich, buttonInfo2.mOnClickListener);
                }
                if (buttonInfo2.mButton.getVisibility() != 8) {
                    i++;
                    disableForceDark(buttonInfo2.mButton);
                }
                viewGroup.addView(buttonInfo2.mButton);
            }
        }
        if (viewGroup instanceof DialogButtonPanel) {
            DialogButtonPanel dialogButtonPanel = (DialogButtonPanel) viewGroup;
            dialogButtonPanel.setHorizontalPositionConfirmed(false);
            dialogButtonPanel.setVerticalPositionConfirmed(false);
        }
        recordButtonTypeForOS3Display(viewGroup);
        if (i == 0) {
            viewGroup.setVisibility(8);
        } else {
            ((DialogButtonPanel) viewGroup).setForceVertical(this.mButtonForceVertical || this.mLandscapePanel || (this.mIsFlipTinyScreen && WindowUtils.isPortrait(this.mContext)) || (MiuixUIUtils.getFontLevel(this.mContext) == 2));
        }
        Point point = new Point();
        WindowUtils.getScreenSize(this.mContext, point);
        int iMax = Math.max(point.x, point.y);
        ViewGroup viewGroup2 = (ViewGroup) this.mParentPanel.findViewById(miuix.appcompat.R.id.contentPanel);
        boolean zButtonNeedScrollable = buttonNeedScrollable((DialogButtonPanel) viewGroup, i);
        if (this.mRootViewSize.y > iMax * 0.33f && !zButtonNeedScrollable) {
            z = false;
        }
        if (this.mLandscapePanel) {
            return;
        }
        if (!z) {
            safeMoveView(viewGroup, this.mParentPanel);
        } else {
            safeMoveView(viewGroup, viewGroup2);
            ((NestedScrollViewExpander) viewGroup2).setExpandView(null);
        }
    }

    public final void setupCheckbox(ViewGroup viewGroup, ViewStub viewStub) {
        if (this.mCheckBoxMessage != null) {
            viewStub.inflate();
            CheckBox checkBox = (CheckBox) viewGroup.findViewById(R.id.checkbox);
            checkBox.setVisibility(0);
            checkBox.setChecked(this.mIsChecked);
            checkBox.setText(this.mCheckBoxMessage);
        }
        TextAlignLayout textAlignLayout = (TextAlignLayout) viewGroup.findViewById(miuix.appcompat.R.id.textAlign);
        if (textAlignLayout != null) {
            textAlignLayout.setDialogPanelHasCheckbox(this.mCheckBoxMessage != null);
        }
    }

    public final void setupContent(ViewGroup viewGroup, boolean z) {
        View childAt;
        FrameLayout frameLayout = (FrameLayout) viewGroup.findViewById(R.id.custom);
        boolean z2 = false;
        if (frameLayout != null) {
            if (z) {
                LayoutTransition layoutTransition = new LayoutTransition();
                layoutTransition.setDuration(0, 200L);
                layoutTransition.setInterpolator(0, new CubicEaseInOutInterpolator());
                frameLayout.setLayoutTransition(layoutTransition);
            } else {
                frameLayout.setLayoutTransition(null);
            }
        }
        if (this.mListView == null) {
            ViewGroup viewGroup2 = (ViewGroup) viewGroup.findViewById(miuix.appcompat.R.id.contentView);
            if (viewGroup2 != null) {
                setupContentView(viewGroup2);
            }
            if (frameLayout != null) {
                boolean z3 = setupCustomContent(frameLayout);
                if (z3 && (childAt = frameLayout.getChildAt(0)) != null) {
                    ViewCompat.setNestedScrollingEnabled(childAt, true);
                }
                z2 = z3;
            }
            NestedScrollViewExpander nestedScrollViewExpander = (NestedScrollViewExpander) viewGroup;
            if (!z2) {
                frameLayout = null;
            }
            nestedScrollViewExpander.setExpandView(frameLayout);
            return;
        }
        if (!(frameLayout != null ? setupCustomContent(frameLayout) : false)) {
            viewGroup.removeView(viewGroup.findViewById(miuix.appcompat.R.id.contentView));
            if (frameLayout != null) {
                safeRemoveFromParent(frameLayout);
            }
            safeRemoveFromParent(this.mListView);
            this.mListView.setMinimumHeight(getSingleItemMinHeight());
            ViewCompat.setNestedScrollingEnabled(this.mListView, true);
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-1, -2);
            if (getVisibleButtonCount() > 0 && !shouldUseLandscapePanel()) {
                marginLayoutParams.bottomMargin = this.mDimensConfig.listViewMarginBottom;
            }
            viewGroup.addView(this.mListView, 0, marginLayoutParams);
            ((NestedScrollViewExpander) viewGroup).setExpandView(this.mListView);
            return;
        }
        int i = miuix.appcompat.R.id.contentView;
        viewGroup.removeView(viewGroup.findViewById(i));
        safeRemoveFromParent(frameLayout);
        LinearLayout linearLayout = new LinearLayout(viewGroup.getContext());
        linearLayout.setOrientation(1);
        safeRemoveFromParent(this.mListView);
        ViewCompat.setNestedScrollingEnabled(this.mListView, true);
        linearLayout.addView(this.mListView, 0, new ViewGroup.MarginLayoutParams(-1, -2));
        boolean zListViewIsNeedFullScroll = listViewIsNeedFullScroll();
        if (zListViewIsNeedFullScroll) {
            resetListMaxHeight();
            linearLayout.addView(frameLayout, new LinearLayout.LayoutParams(-1, -2, 0.0f));
        } else {
            adjustHeight2WrapContent();
            linearLayout.addView(frameLayout, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
        viewGroup.addView(linearLayout, 0, new ViewGroup.MarginLayoutParams(-1, -2));
        ViewGroup viewGroup3 = (ViewGroup) viewGroup.findViewById(i);
        if (viewGroup3 != null) {
            setupContentView(viewGroup3);
        }
        ((NestedScrollViewExpander) viewGroup).setExpandView(zListViewIsNeedFullScroll ? null : linearLayout);
    }

    public final void setupContentView(ViewGroup viewGroup) {
        CharSequence charSequence;
        this.mMessageView = (TextView) viewGroup.findViewById(miuix.appcompat.R.id.message);
        this.mCommentView = (TextView) viewGroup.findViewById(miuix.appcompat.R.id.comment);
        TextView textView = this.mMessageView;
        if (textView == null || (charSequence = this.mMessage) == null) {
            if (this.mCheckBoxMessage == null) {
                safeRemoveFromParent(viewGroup);
                return;
            }
            View viewFindViewById = viewGroup.findViewById(miuix.appcompat.R.id.textAlign);
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(8);
                return;
            }
            return;
        }
        textView.setText(charSequence);
        TextView textView2 = this.mCommentView;
        if (textView2 != null) {
            CharSequence charSequence2 = this.mComment;
            if (charSequence2 != null) {
                textView2.setText(charSequence2);
            } else {
                textView2.setVisibility(8);
            }
        }
    }

    public final boolean setupCustomContent(ViewGroup viewGroup) {
        View view = this.mInflatedView;
        View viewInflate = null;
        if (view != null && view.getParent() != null) {
            safeRemoveFromParent(this.mInflatedView);
            this.mInflatedView = null;
        }
        View view2 = this.mView;
        if (view2 != null) {
            viewInflate = view2;
        } else if (this.mViewLayoutResId != 0) {
            viewInflate = LayoutInflater.from(this.mContext).inflate(this.mViewLayoutResId, viewGroup, false);
            this.mInflatedView = viewInflate;
        }
        boolean z = viewInflate != null;
        if (z && canTextInput(viewInflate)) {
            this.mWindow.clearFlags(131072);
        } else {
            this.mWindow.setFlags(131072, 131072);
        }
        setAnimIfEditExistForNonImmersive(viewInflate);
        if (z) {
            safeMoveView(viewInflate, viewGroup);
            return z;
        }
        safeRemoveFromParent(viewGroup);
        return z;
    }

    public final void setupImmersiveWindow() {
        this.mWindow.setLayout(-1, -1);
        this.mWindow.setBackgroundDrawableResource(miuix.appcompat.R.color.miuix_appcompat_transparent);
        this.mWindow.setDimAmount(0.0f);
        this.mWindow.setWindowAnimations(miuix.appcompat.R.style.Animation_Dialog_NoAnimation);
        this.mWindow.addFlags(-2147481344);
        Activity associatedActivity = ((AlertDialog) this.mDialog).getAssociatedActivity();
        if (associatedActivity != null) {
            this.mWindow.getAttributes().layoutInDisplayCutoutMode = getCutoutMode(getScreenOrientation(), associatedActivity.getWindow().getAttributes().layoutInDisplayCutoutMode);
        } else {
            this.mWindow.getAttributes().layoutInDisplayCutoutMode = getScreenOrientation() != 2 ? 1 : 2;
        }
        clearFitSystemWindow(this.mWindow.getDecorView());
        this.mWindow.getAttributes().setFitInsetsSides(0);
        Activity associatedActivity2 = ((AlertDialog) this.mDialog).getAssociatedActivity();
        boolean associatedActivitySystemBarVisibility = getAssociatedActivitySystemBarVisibility(associatedActivity2, WindowInsets.Type.statusBars());
        boolean z = (associatedActivity2 == null || (associatedActivity2.getWindow().getAttributes().flags & 1024) == 1024) ? false : true;
        if (z && (associatedActivitySystemBarVisibility || this.mIsInFreeForm)) {
            this.mWindow.clearFlags(1024);
        }
        boolean associatedActivitySystemBarVisibility2 = getAssociatedActivitySystemBarVisibility(associatedActivity2, WindowInsets.Type.navigationBars());
        if (this.mIsDebugEnabled) {
            Log.i("AlertController", "setupImmersiveWindow: statusBarIsVisible = " + associatedActivitySystemBarVisibility + ", windowExcludeFullScreenFlag = " + z + ", navigationBarIsVisible = " + associatedActivitySystemBarVisibility2);
        }
        if (associatedActivitySystemBarVisibility2 || this.mIsInFreeForm) {
            return;
        }
        setWindowNavigationBarHidden();
    }

    public final void setupMaterial() {
        if (this.mMaterialEnabled && HyperMaterialUtils.isFeatureEnable(this.mContext)) {
            if (MiuiBlurUtils.setPassWindowBlurEnabled(this.mParentPanel, true)) {
                this.mParentPanel.post(new Runnable() { // from class: miuix.appcompat.app.AlertController.7
                    @Override // java.lang.Runnable
                    public void run() {
                        MaterialConfig materialConfig;
                        if (AlertController.this.mParentPanel == null || ((ViewGroup) AlertController.this.mParentPanel.getParent()) == null || !MiuiBlurUtils.getPassWindowBlurEnabled(AlertController.this.mParentPanel)) {
                            return;
                        }
                        MaterialDayNightConfig materialDayNightConfigCreate = MaterialDayNightConfig.create(AlertController.Default_Dialog_Material);
                        if (materialDayNightConfigCreate != null && (materialConfig = materialDayNightConfigCreate.get(AlertController.this.isLightTheme())) != null) {
                            HyperMaterialUtils.applyContainer(AlertController.this.mParentPanel, materialConfig.getBlurConfig());
                            HyperMaterialUtils.applyElement(AlertController.this.mParentPanel, materialConfig);
                            Drawable background = AlertController.this.mParentPanel.getBackground();
                            if (background != null) {
                                background.setAlpha(0);
                            }
                        }
                        MiuiBlurUtils.setMiBlurWinType(AlertController.this.mParentPanel, 1);
                    }
                });
            }
        } else if (MiuiBlurUtils.getPassWindowBlurEnabled(this.mParentPanel)) {
            MiuiBlurUtils.setPassWindowBlurEnabled(this.mParentPanel, false);
            Drawable background = this.mParentPanel.getBackground();
            if (background != null) {
                background.setAlpha(255);
            }
        }
    }

    public final void setupNonImmersiveWindow() {
        Point availableWindowSizeDp = getAvailableWindowSizeDp(null);
        updateDialogPanelLayoutParams(availableWindowSizeDp);
        int iDp2px = this.mPanelParamsWidth;
        if (iDp2px == -1) {
            iDp2px = MiuixUIUtils.dp2px(this.mContext, availableWindowSizeDp.x) - (this.mPanelParamsHorizontalMargin * 2);
        }
        if (this.mIsDebugEnabled) {
            Log.d("AlertController", "setupNonImmersiveWindow: windowWidth = " + iDp2px);
            Log.d("AlertController", "setupNonImmersiveWindow: availableWindowSizeDp = " + availableWindowSizeDp);
            Log.d("AlertController", "setupNonImmersiveWindow: horizontalMargin = " + this.mPanelParamsHorizontalMargin);
        }
        int i = this.mNonImmersiveDialogHeight;
        int i2 = (i <= 0 || i < this.mRootViewSize.y) ? i : -1;
        int gravity = getGravity();
        this.mWindow.setGravity(gravity);
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        if ((gravity & 80) == 80) {
            int i3 = this.mIsFlipTinyScreen ? this.mDimensConfig.widthSmallMargin : this.mDimensConfig.extraImeMargin;
            boolean zIsShowNavigationHandle = MiuixUIUtils.isShowNavigationHandle(this.mContext);
            boolean z = MiuixUIUtils.isInMultiWindowMode(this.mContext) && !this.mIsInFreeForm && DeviceHelper.isWideScreen(this.mContext);
            if (this.mIsInFreeForm || (z && zIsShowNavigationHandle)) {
                Insets associatedActivityInsets = getAssociatedActivityInsets(WindowInsets.Type.captionBar());
                int i4 = this.mDimensConfig.freePhoneCompactHeight;
                int i5 = associatedActivityInsets != null ? associatedActivityInsets.bottom : 0;
                i3 = i5 == 0 ? i3 + i4 : i3 + i5;
            }
            int i6 = attributes.flags;
            if ((i6 & 134217728) != 0) {
                this.mWindow.clearFlags(134217728);
            }
            if ((i6 & 67108864) != 0) {
                this.mWindow.clearFlags(67108864);
            }
            attributes.verticalMargin = (i3 * 1.0f) / this.mRootViewSize.y;
        }
        attributes.layoutInDisplayCutoutMode = 2;
        this.mWindow.setAttributes(attributes);
        this.mWindow.addFlags(2);
        this.mWindow.addFlags(262144);
        this.mWindow.setDimAmount(ViewUtils.isNightMode(this.mContext) ? DimToken.DIM_DARK : DimToken.DIM_LIGHT);
        this.mWindow.setLayout(iDp2px, i2);
        this.mWindow.setBackgroundDrawableResource(miuix.appcompat.R.color.miuix_appcompat_transparent);
        DialogParentPanel2 dialogParentPanel2 = this.mParentPanel;
        if (dialogParentPanel2 != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) dialogParentPanel2.getLayoutParams();
            layoutParams.width = iDp2px;
            layoutParams.height = -2;
            if (isSpecifiedDialogHeight()) {
                layoutParams.gravity = getGravity();
            }
            this.mParentPanel.setLayoutParams(layoutParams);
            this.mParentPanel.setTag(null);
        }
        if (!this.mEnableEnterAnim) {
            this.mWindow.setWindowAnimations(0);
        } else if (isTablet()) {
            this.mWindow.setWindowAnimations(miuix.appcompat.R.style.Animation_Dialog_Center);
        }
    }

    public final void setupTitle(ViewGroup viewGroup) {
        ImageView imageView = (ImageView) this.mWindow.findViewById(R.id.icon);
        View view = this.mCustomTitleView;
        if (view != null) {
            safeRemoveFromParent(view);
            viewGroup.addView(this.mCustomTitleView, 0, new ViewGroup.LayoutParams(-1, -2));
            this.mWindow.findViewById(miuix.appcompat.R.id.alertTitle).setVisibility(8);
            imageView.setVisibility(8);
            return;
        }
        if (TextUtils.isEmpty(this.mTitle) || !this.mShowTitle) {
            this.mWindow.findViewById(miuix.appcompat.R.id.alertTitle).setVisibility(8);
            imageView.setVisibility(8);
            viewGroup.setVisibility(8);
            return;
        }
        TextView textView = (TextView) this.mWindow.findViewById(miuix.appcompat.R.id.alertTitle);
        this.mTitleView = textView;
        textView.setText(this.mTitle);
        int i = this.mIconId;
        if (i != 0) {
            imageView.setImageResource(i);
        } else {
            Drawable drawable = this.mIcon;
            if (drawable != null) {
                imageView.setImageDrawable(drawable);
            } else {
                this.mTitleView.setPadding(imageView.getPaddingLeft(), imageView.getPaddingTop(), imageView.getPaddingRight(), imageView.getPaddingBottom());
                imageView.setVisibility(8);
            }
        }
        if (this.mSmallIcon) {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            DialogContract.DimensConfig dimensConfig = this.mDimensConfig;
            layoutParams.width = dimensConfig.smallIconWidth;
            layoutParams.height = dimensConfig.smallIconHeight;
        }
        if (this.mIconWidth != 0 && this.mIconHeight != 0) {
            ViewGroup.LayoutParams layoutParams2 = imageView.getLayoutParams();
            layoutParams2.width = this.mIconWidth;
            layoutParams2.height = this.mIconHeight;
        }
        if (this.mMessage == null || viewGroup.getVisibility() == 8) {
            return;
        }
        changeTitlePadding(this.mTitleView);
    }

    public final void setupView() {
        setupView(true, false, false, 1.0f);
        storeCustomViewInitialTextSize();
    }

    public final void setupView(boolean z, boolean z2, boolean z3, final float f) {
        ListAdapter listAdapter;
        if (isDialogImmersive()) {
            this.mDimBg.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.AlertController$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlertController.m1296$r8$lambda$Pw0qBqi6r3HY5zRHrBLgL5e20(this.f$0, view);
                }
            });
            updateImmersiveDialogPanel();
        } else {
            if (isSpecifiedDialogHeight()) {
                this.mDialogRootView.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.AlertController$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AlertController.$r8$lambda$jE6pvLNj4CZboVf3EARLR8dMFUM(this.f$0, view);
                    }
                });
            }
            this.mDimBg.setVisibility(8);
        }
        if (z || z2 || this.mPreferLandscape) {
            ViewGroup viewGroup = (ViewGroup) this.mParentPanel.findViewById(miuix.appcompat.R.id.topPanel);
            ViewGroup viewGroup2 = (ViewGroup) this.mParentPanel.findViewById(miuix.appcompat.R.id.contentPanel);
            ViewGroup viewGroup3 = (ViewGroup) this.mParentPanel.findViewById(miuix.appcompat.R.id.buttonPanel);
            if (viewGroup2 instanceof NestedScrollViewExpander) {
                ((NestedScrollViewExpander) viewGroup2).setMinCustomVisibleHeight(this.mMinCustomVisibleHeight);
            }
            if (viewGroup2 != null) {
                setupContent(viewGroup2, z3);
            }
            if (viewGroup3 instanceof DialogButtonPanel) {
                DialogButtonPanel dialogButtonPanel = (DialogButtonPanel) viewGroup3;
                dialogButtonPanel.isContentLandscape(shouldUseLandscapePanel());
                dialogButtonPanel.setPrimaryButtonFirstEnabled(this.mPrimaryButtonFirstEnabled);
                setupButtons(viewGroup3);
            }
            if (viewGroup != null) {
                setupTitle(viewGroup);
            }
            if (viewGroup != null && viewGroup.getVisibility() != 8) {
                View viewFindViewById = (this.mMessage == null && this.mListView == null) ? null : viewGroup.findViewById(miuix.appcompat.R.id.titleDividerNoCustom);
                if (viewFindViewById != null) {
                    viewFindViewById.setVisibility(0);
                }
            }
            ListView listView = this.mListView;
            if (listView != null && (listAdapter = this.mAdapter) != null) {
                listView.setAdapter(listAdapter);
                int i = this.mCheckedItem;
                if (i > -1) {
                    listView.setItemChecked(i, true);
                    listView.setSelection(i);
                }
            }
            ViewStub viewStub = (ViewStub) this.mParentPanel.findViewById(miuix.appcompat.R.id.checkbox_stub);
            if (viewStub != null) {
                setupCheckbox(this.mParentPanel, viewStub);
            }
            setupMaterial();
            if (!z) {
                onLayoutReload();
            }
        } else {
            this.mParentPanel.post(new Runnable() { // from class: miuix.appcompat.app.AlertController.6
                @Override // java.lang.Runnable
                public void run() {
                    ViewGroup viewGroup4 = (ViewGroup) AlertController.this.mParentPanel.findViewById(miuix.appcompat.R.id.contentPanel);
                    ViewGroup viewGroup5 = (ViewGroup) AlertController.this.mParentPanel.findViewById(miuix.appcompat.R.id.buttonPanel);
                    if (viewGroup4 != null) {
                        AlertController.this.updateContent(viewGroup4);
                        if (viewGroup5 != null && !AlertController.this.mPreferLandscape) {
                            AlertController.this.updateButtons(viewGroup5, viewGroup4);
                        }
                    }
                    float f2 = f;
                    if (f2 != 1.0f) {
                        AlertController.this.updateViewOnDensityChanged(f2);
                    }
                }
            });
        }
        this.mParentPanel.post(new Runnable() { // from class: miuix.appcompat.app.AlertController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                AlertController.$r8$lambda$pRwptev8px0nmKWPnQSW7TMtyQo(this.f$0);
            }
        });
    }

    public final void setupWindow() {
        if (isDialogImmersive()) {
            setupImmersiveWindow();
        } else {
            setupNonImmersiveWindow();
        }
    }

    public final void setupWindowInsetsAnimation() {
        if (isDialogImmersive()) {
            this.mWindow.setSoftInputMode((this.mWindow.getAttributes().softInputMode & 15) | 48);
            WindowInsetsAnimation.Callback callback = new WindowInsetsAnimation.Callback(1) { // from class: miuix.appcompat.app.AlertController.8
                boolean isTablet = false;

                @Override // android.view.WindowInsetsAnimation.Callback
                public void onEnd(@NonNull WindowInsetsAnimation windowInsetsAnimation) {
                    super.onEnd(windowInsetsAnimation);
                    AlertController.this.mInsetsAnimationPlayed = true;
                    WindowInsets rootWindowInsets = AlertController.this.mWindow.getDecorView().getRootWindowInsets();
                    if (rootWindowInsets != null) {
                        Insets insets = rootWindowInsets.getInsets(WindowInsets.Type.ime());
                        if (insets.bottom <= 0 && AlertController.this.mParentPanel.getTranslationY() < 0.0f) {
                            AlertController.this.translateDialogPanel(0);
                        }
                        AlertController.this.updateParentPanelMarginByWindowInsets(rootWindowInsets);
                        if (this.isTablet) {
                            return;
                        }
                        AlertController.this.updateDimBgBottomMargin(insets.bottom);
                    }
                }

                @Override // android.view.WindowInsetsAnimation.Callback
                public void onPrepare(@NonNull WindowInsetsAnimation windowInsetsAnimation) {
                    super.onPrepare(windowInsetsAnimation);
                    if (windowInsetsAnimation != null && (windowInsetsAnimation.getTypeMask() & WindowInsets.Type.ime()) > 0) {
                        AlertController.this.mDialogAnimHelper.cancelAnimator();
                    }
                    AlertController.this.mInsetsAnimationPlayed = false;
                    this.isTablet = AlertController.this.isTablet();
                }

                @Override // android.view.WindowInsetsAnimation.Callback
                @NonNull
                @RequiresApi
                public WindowInsets onProgress(@NonNull WindowInsets windowInsets, @NonNull List<WindowInsetsAnimation> list) {
                    Insets insets = windowInsets.getInsets(WindowInsets.Type.ime());
                    Insets insets2 = windowInsets.getInsets(WindowInsets.Type.navigationBars());
                    int iMax = insets.bottom - Math.max(AlertController.this.mPanelAndImeMargin, insets2.bottom);
                    if (windowInsets.isVisible(WindowInsets.Type.ime())) {
                        if (AlertController.this.mIsDebugEnabled) {
                            Log.d("AlertController", "WindowInsetsAnimation onProgress mPanelAndImeMargin : " + AlertController.this.mPanelAndImeMargin);
                            Log.d("AlertController", "WindowInsetsAnimation onProgress ime : " + insets.bottom);
                            Log.d("AlertController", "WindowInsetsAnimation onProgress navigationBar : " + insets2.bottom);
                        }
                        AlertController.this.translateDialogPanel(-(iMax < 0 ? 0 : iMax));
                    }
                    if (!this.isTablet) {
                        AlertController.this.updateDimBgBottomMargin(iMax);
                    }
                    return windowInsets;
                }

                @Override // android.view.WindowInsetsAnimation.Callback
                @NonNull
                public WindowInsetsAnimation.Bounds onStart(@NonNull WindowInsetsAnimation windowInsetsAnimation, @NonNull WindowInsetsAnimation.Bounds bounds) {
                    AlertController alertController = AlertController.this;
                    alertController.mPanelAndImeMargin = (int) (alertController.getDialogPanelMargin() + AlertController.this.mParentPanel.getTranslationY());
                    if (AlertController.this.mIsDebugEnabled) {
                        Log.d("AlertController", "WindowInsetsAnimation onStart mPanelAndImeMargin : " + AlertController.this.mPanelAndImeMargin);
                    }
                    if (AlertController.this.mPanelAndImeMargin <= 0) {
                        AlertController.this.mPanelAndImeMargin = 0;
                    }
                    return super.onStart(windowInsetsAnimation, bounds);
                }
            };
            if (!(this.mDialog instanceof PairingDialog)) {
                this.mWindow.getDecorView().setWindowInsetsAnimationCallback(callback);
            }
            this.mWindow.getDecorView().setOnApplyWindowInsetsListener(new AnonymousClass9());
            this.mSetupWindowInsetsAnimation = true;
        }
    }

    public final boolean shouldUseLandscapePanel() {
        if (isLhasaInternalScreen() || getVisibleButtonCount() == 0) {
            return false;
        }
        Point point = this.mRootViewSize;
        int i = point.x;
        return i >= this.mDimensConfig.panelMaxWidthLand && i * 2 > point.y && this.mPreferLandscape;
    }

    public final boolean showSystemAlertInFlip() {
        int i = this.mWindow.getAttributes().type;
        return this.mIsFlipTinyScreen && ((i == 2038 || i == 2003) || this.mUseForceFlipCutout);
    }

    public final void storeCustomViewInitialTextSize() {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        float f = displayMetrics.scaledDensity;
        float f2 = displayMetrics.density;
        View view = this.mCustomTitleView;
        if (view != null) {
            this.mCustomTitleTextView = (TextView) view.findViewById(R.id.title);
        }
        TextView textView = this.mCustomTitleTextView;
        if (textView != null) {
            this.mCustomTitleTextSize = textView.getTextSize();
            int textSizeUnit = this.mCustomTitleTextView.getTextSizeUnit();
            if (textSizeUnit == 1) {
                this.mCustomTitleTextSize /= f2;
            } else if (textSizeUnit == 2) {
                this.mCustomTitleTextSize /= f;
            }
        }
    }

    public final void translateDialogPanel(int i) {
        if (this.mIsDebugEnabled) {
            Log.d("AlertController", "The DialogPanel transitionY for : " + i);
        }
        this.mParentPanel.animate().cancel();
        this.mParentPanel.setTranslationY(i);
    }

    public final void updateButtons(ViewGroup viewGroup, ViewGroup viewGroup2) {
        int visibleButtonCount = getVisibleButtonCount();
        Point point = new Point();
        WindowUtils.getScreenSize(this.mContext, point);
        DialogButtonPanel dialogButtonPanel = (DialogButtonPanel) viewGroup;
        boolean z = ((float) this.mRootViewSize.y) <= ((float) Math.max(point.x, point.y)) * 0.33f || buttonNeedScrollable(dialogButtonPanel, visibleButtonCount) || this.mContentForceFullScroll;
        dialogButtonPanel.setForceVertical(this.mButtonForceVertical || this.mLandscapePanel || (this.mIsFlipTinyScreen && (this.mContext.getResources().getConfiguration().orientation == 1)) || (MiuixUIUtils.getFontLevel(this.mContext) == 2));
        if (z) {
            safeMoveView(viewGroup, viewGroup2);
            ((NestedScrollViewExpander) viewGroup2).setExpandView(null);
            if (this.mListView != null) {
                expandListViewToFullHeight();
            }
        } else {
            safeMoveView(viewGroup, this.mParentPanel);
            if (this.mListView != null) {
                restoreListViewScroll();
            }
        }
        applyFullScrollBottomPaddingLayout(viewGroup2, z);
    }

    public final void updateContent(ViewGroup viewGroup) {
        FrameLayout frameLayout = (FrameLayout) viewGroup.findViewById(R.id.custom);
        boolean z = frameLayout != null && frameLayout.getChildCount() > 0;
        ListView listView = this.mListView;
        if (listView == null) {
            if (z) {
                ViewCompat.setNestedScrollingEnabled(frameLayout.getChildAt(0), true);
            }
            NestedScrollViewExpander nestedScrollViewExpander = (NestedScrollViewExpander) viewGroup;
            if (!z) {
                frameLayout = null;
            }
            nestedScrollViewExpander.setExpandView(frameLayout);
            return;
        }
        if (!z) {
            ((NestedScrollViewExpander) viewGroup).setExpandView(listView);
            return;
        }
        if (listViewIsNeedFullScroll()) {
            resetListMaxHeight();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
            layoutParams.height = -2;
            layoutParams.weight = 0.0f;
            frameLayout.setLayoutParams(layoutParams);
            ((NestedScrollViewExpander) viewGroup).setExpandView(null);
            viewGroup.requestLayout();
            return;
        }
        adjustHeight2WrapContent();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams2.height = 0;
        layoutParams2.weight = 1.0f;
        frameLayout.setLayoutParams(layoutParams2);
        ((NestedScrollViewExpander) viewGroup).setExpandView((View) frameLayout.getParent());
        viewGroup.requestLayout();
    }

    public final void updateDialogPanelByWindowInsets(WindowInsets windowInsets) {
        updateParentPanelMarginByWindowInsets(windowInsets);
        if (isNeedUpdateDialogPanelTranslationY()) {
            boolean zIsInMultiWindowMode = MiuixUIUtils.isInMultiWindowMode(this.mContext);
            Insets insets = windowInsets.getInsets(WindowInsets.Type.ime());
            Insets insets2 = windowInsets.getInsets(WindowInsets.Type.navigationBars());
            if (this.mIsDebugEnabled) {
                Log.d("AlertController", "======================Debug for checkTranslateDialogPanel======================");
                Log.d("AlertController", "The mPanelAndImeMargin: " + this.mPanelAndImeMargin);
                Log.d("AlertController", "The imeInsets info: " + insets.toString());
                Log.d("AlertController", "The navigationBarInsets info: " + insets2.toString());
                Log.d("AlertController", "The insets info: " + windowInsets);
            }
            boolean zIsTablet = isTablet();
            if (!zIsTablet) {
                updateDimBgBottomMargin(insets.bottom);
            }
            int i = insets.bottom;
            if (zIsInMultiWindowMode && !zIsTablet) {
                i -= insets2.bottom;
            }
            updateDialogPanelTranslationYByIme(i, zIsInMultiWindowMode, zIsTablet);
            if (this.mIsDebugEnabled) {
                Log.d("AlertController", "===================End of Debug for checkTranslateDialogPanel===================");
            }
        }
    }

    public final void updateDialogPanelLayoutParams(Point point) {
        if (point == null) {
            point = getAvailableWindowSizeDp(null);
        }
        DialogContract.OrientationSpec orientationSpec = new DialogContract.OrientationSpec();
        orientationSpec.mUsableWindowSizeDp.set(point.x, point.y);
        Point point2 = orientationSpec.mWindowSize;
        Point point3 = this.mRootViewSize;
        point2.set(point3.x, point3.y);
        WindowUtils.getScreenSize(this.mContext, orientationSpec.mRealScreenSize);
        orientationSpec.updateData(this.mMarkLandscape, this.mIsInFreeForm, getScreenOrientation(), this.mIsCarWithScreen, this.mIsSynergy);
        boolean zIsLandscapeWindow = this.mDisplayStrategy.isLandscapeWindow(orientationSpec);
        int i = point.x;
        boolean zShouldLimitPanelWidth = this.mDisplayStrategy.shouldLimitPanelWidth(i);
        if (this.mIsDebugEnabled) {
            Log.d("AlertController", "updateDialogPanelLayoutParams isLandScape " + zIsLandscapeWindow);
            Log.d("AlertController", "updateDialogPanelLayoutParams shouldLimitWidth " + zShouldLimitPanelWidth);
        }
        boolean z = false;
        int widthMargin = zShouldLimitPanelWidth ? 0 : this.mDisplayStrategy.getWidthMargin(this.mDimensConfig, i);
        this.mIsWindowLandScape = zIsLandscapeWindow;
        DialogContract.PanelWidthSpec panelWidthSpec = new DialogContract.PanelWidthSpec();
        if (this.mPreferLandscape && shouldUseLandscapePanel()) {
            z = true;
        }
        panelWidthSpec.updateData(z, zIsLandscapeWindow, this.mIsCarWithScreen, this.mMarkLandscape, i, this.mScreenMinorSize, this.mIsDebugEnabled);
        this.mPanelParamsWidth = this.mDisplayStrategy.getPanelWidth(panelWidthSpec, this.mDimensConfig);
        inflateDialogLayout();
        this.mPanelParamsHorizontalMargin = widthMargin;
    }

    public final void updateDialogPanelTranslationYByIme(int i, boolean z, boolean z2) {
        boolean z3 = false;
        if (i <= 0) {
            if (this.mIsDebugEnabled) {
                Log.d("AlertController", "updateDialogPanelTranslationYByIme imeBottom <= 0");
            }
            if (this.mParentPanel.getTranslationY() < 0.0f) {
                translateDialogPanel(0);
                return;
            }
            return;
        }
        int dialogPanelMargin = getDialogPanelMargin();
        int translationY = (int) (dialogPanelMargin + this.mParentPanel.getTranslationY());
        this.mPanelAndImeMargin = translationY;
        if (translationY <= 0) {
            this.mPanelAndImeMargin = 0;
        }
        if (this.mIsDebugEnabled) {
            Log.d("AlertController", "updateDialogPanelTranslationYByIme mPanelAndImeMargin " + this.mPanelAndImeMargin + " isMultiWindowMode " + z + " imeBottom " + i);
        }
        int i2 = (!z || z2) ? (-i) + this.mPanelAndImeMargin : -i;
        if (z2 && i < this.mPanelAndImeMargin) {
            i2 = 0;
        }
        if (this.mIsDialogAnimating) {
            if (this.mIsDebugEnabled) {
                Log.d("AlertController", "updateDialogPanelTranslationYByIme anim translateDialogPanel Y=" + i2);
            }
            this.mParentPanel.animate().cancel();
            this.mParentPanel.animate().setDuration(200L).translationY(i2).start();
            return;
        }
        if (this.mIsDebugEnabled) {
            Log.d("AlertController", "updateDialogPanelTranslationYByIme translateDialogPanel Y=" + i2);
        }
        int i3 = -(i - dialogPanelMargin);
        if (i2 < 0 && i3 < 0 && i2 < i3) {
            z3 = true;
        }
        if (z2 && !this.mIsInFreeForm && isDialogImmersive() && z3) {
            i2 = i3;
        }
        if (this.mIsDebugEnabled) {
            Log.d("AlertController", "updateDialogPanelTranslationYByIme: expectedTabletTranslationY = " + i3 + ", translationYUnexpected = " + z3 + ", bottom = " + i2 + ", mIsInFreeForm = " + this.mIsInFreeForm);
        }
        translateDialogPanel(i2);
    }

    public final void updateDimBgBottomMargin(int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mDimBg.getLayoutParams();
        if (marginLayoutParams.bottomMargin != i) {
            marginLayoutParams.bottomMargin = i;
            this.mDimBg.requestLayout();
        }
    }

    public final void updateDimensConfig(Resources resources) {
        this.mDimensConfig.panelMaxWidth = resources.getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_max_width);
        this.mDimensConfig.panelMaxWidthLand = resources.getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_max_width_land);
        this.mDimensConfig.listViewMarginBottom = resources.getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_list_view_margin_bottom);
        this.mDimensConfig.extraImeMargin = resources.getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_ime_margin);
        this.mDimensConfig.fakeLandScreenMinorSize = resources.getDimensionPixelSize(miuix.appcompat.R.dimen.fake_landscape_screen_minor_size);
        this.mDimensConfig.widthSmallMargin = resources.getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_width_small_margin);
        this.mDimensConfig.widthMargin = resources.getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_width_margin);
        this.mDimensConfig.freeTabletCompactHeight = resources.getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_freeform_bottom_height_tablet_t);
        this.mDimensConfig.freePhoneCompactHeight = resources.getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_freeform_bottom_height_phone_t);
        this.mDimensConfig.smallIconWidth = resources.getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_icon_drawable_width_small);
        this.mDimensConfig.smallIconHeight = resources.getDimensionPixelSize(miuix.appcompat.R.dimen.miuix_appcompat_dialog_icon_drawable_height_small);
    }

    public final void updateDisplayInfo(Context context) {
        boolean zIsCarWithScreen = DeviceHelper.isCarWithScreen(context, null);
        this.mIsCarWithScreen = zIsCarWithScreen;
        if (zIsCarWithScreen) {
            this.mIsSynergy = true;
        } else {
            this.mIsSynergy = DeviceHelper.isXiaomiSynergy(context);
        }
    }

    public final void updateImmersiveDialogPanel() {
        Point availableWindowSizeDp = getAvailableWindowSizeDp(null);
        updateDialogPanelLayoutParams(availableWindowSizeDp);
        int iDp2px = this.mPanelParamsWidth;
        if (iDp2px == -1) {
            iDp2px = MiuixUIUtils.dp2px(this.mContext, availableWindowSizeDp.x) - (this.mPanelParamsHorizontalMargin * 2);
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iDp2px, -2);
        layoutParams.gravity = getGravity();
        if (this.mPanelParamsWidth == -1) {
            int i = this.mPanelParamsHorizontalMargin;
            layoutParams.leftMargin = i;
            layoutParams.rightMargin = i;
        }
        this.mParentPanel.setLayoutParams(layoutParams);
    }

    public final void updateMinorScreenSize() {
        Configuration configuration = this.mContext.getResources().getConfiguration();
        int iMin = (int) (Math.min(configuration.screenWidthDp, configuration.screenHeightDp) * (configuration.densityDpi / 160.0f));
        if (iMin > 0) {
            this.mScreenMinorSize = iMin;
            return;
        }
        Point point = new Point();
        this.mWindowManager.getDefaultDisplay().getSize(point);
        this.mScreenMinorSize = Math.min(point.x, point.y);
    }

    public final void updateParentPanelMarginByWindowInsets(WindowInsets windowInsets) {
        int i;
        int i2;
        if (isTablet() || windowInsets == null) {
            return;
        }
        Insets insets = (this.mNavigationBarHiddenEnabled || this.mIsAssociatedActivityHideNavigationBar) ? windowInsets.getInsets(WindowInsets.Type.systemBars()) : windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        Rect rectInsetsToRect = DialogContract.insetsToRect(insets);
        boolean z = false;
        if (this.mDiscardNaviBarHeightEnabled) {
            rectInsetsToRect.bottom = 0;
        }
        Insets insets2 = windowInsets.getInsets(WindowInsets.Type.displayCutout());
        this.mDisplayCutoutSafeInsets.setEmpty();
        int width = this.mDialogRootView.getWidth();
        int height = this.mDialogRootView.getHeight();
        Point point = this.mRootViewSize;
        if (point.x == 0 || point.y == 0) {
            updateRootViewSize((Configuration) null);
            Point point2 = this.mRootViewSize;
            i = point2.x;
            height = point2.y;
        } else {
            i = width;
        }
        if (insets2 != null && !this.mIsInFreeForm) {
            this.mDisplayCutoutSafeInsets.set(insets2.left, insets2.top, insets2.right, insets2.bottom);
        }
        if (this.mIsFlipTinyScreen) {
            Rect guaranteedCutout = getGuaranteedCutout(windowInsets, false);
            this.mDisplayCutoutSafeInsets.set(guaranteedCutout.left, guaranteedCutout.top, guaranteedCutout.right, guaranteedCutout.bottom);
        }
        Rect rectMergeInsets = DialogContract.mergeInsets(rectInsetsToRect, this.mDisplayCutoutSafeInsets);
        if (this.mIsDebugEnabled) {
            Log.d("AlertController", "updateParentPanelMargin systemBarInsets: " + insets);
            Log.d("AlertController", "updateParentPanelMargin mDisplayCutoutSafeInsets: " + this.mDisplayCutoutSafeInsets);
            Log.d("AlertController", "updateParentPanelMargin boundInsets = " + rectMergeInsets);
        }
        Point point3 = this.mRootViewSize;
        Point point4 = new Point(point3.x, point3.y);
        if (i != 0 && i != point4.x) {
            point4.x = i;
            point4.y = height;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mParentPanel.getLayoutParams();
        DialogContract.OrientationSpec orientationSpec = new DialogContract.OrientationSpec();
        Point windowSize = EnvStateManager.getWindowSize(this.mContext);
        orientationSpec.updateData(this.mMarkLandscape, this.mIsInFreeForm, getScreenOrientation(), this.mIsCarWithScreen, this.mIsSynergy);
        orientationSpec.mWindowSize.set(windowSize.x, windowSize.y);
        WindowUtils.getScreenSize(this.mContext, orientationSpec.mRealScreenSize);
        float f = this.mContext.getResources().getDisplayMetrics().densityDpi / 160.0f;
        int i3 = (point4.x - rectMergeInsets.left) - rectMergeInsets.right;
        int i4 = (point4.y - rectMergeInsets.top) - rectMergeInsets.bottom;
        int iPx2dp = MiuixUIUtils.px2dp(f, i3);
        orientationSpec.mUsableWindowSizeDp.set(iPx2dp, MiuixUIUtils.px2dp(f, i4));
        boolean zIsLandscapeWindow = this.mDisplayStrategy.isLandscapeWindow(orientationSpec);
        DialogContract.PanelWidthSpec panelWidthSpec = new DialogContract.PanelWidthSpec();
        boolean z2 = true;
        panelWidthSpec.updateData(this.mPreferLandscape && shouldUseLandscapePanel(), zIsLandscapeWindow, this.mIsCarWithScreen, this.mMarkLandscape, iPx2dp, this.mScreenMinorSize, this.mIsDebugEnabled);
        int panelWidth = this.mDisplayStrategy.getPanelWidth(panelWidthSpec, this.mDimensConfig);
        DialogContract.PanelPosSpec panelPosSpec = new DialogContract.PanelPosSpec();
        panelPosSpec.mBoundInsets.set(rectMergeInsets.left, rectMergeInsets.top, rectMergeInsets.right, rectMergeInsets.bottom);
        panelPosSpec.updateData(this.mDialogRootView.getPaddingLeft(), this.mDialogRootView.getPaddingRight(), i, panelWidth, iPx2dp, i3, this.mRootViewSize.x, this.mIsFlipTinyScreen, this.mIsDebugEnabled);
        Rect rect = new Rect();
        int iUpdatePanelPosMargins = this.mDisplayStrategy.updatePanelPosMargins(panelPosSpec, this.mDimensConfig, rect);
        layoutParams.width = iUpdatePanelPosMargins;
        int i5 = rect.bottom;
        boolean z3 = MiuixUIUtils.isInMultiWindowMode(this.mContext) && !this.mIsInFreeForm && DeviceHelper.isWideScreen(this.mContext);
        if ((this.mIsInFreeForm || z3) && insets.bottom == 0) {
            Insets associatedActivityInsets = getAssociatedActivityInsets(WindowInsets.Type.captionBar());
            int i6 = this.mDimensConfig.freePhoneCompactHeight;
            int i7 = associatedActivityInsets != null ? associatedActivityInsets.bottom : 0;
            int i8 = i7 == 0 ? i6 + i5 : i5 + i7;
            if (getImeBottomByWindowInsets(windowInsets) <= 0) {
                i5 = i8;
            }
            if (this.mIsInFreeForm && this.mDiscardNaviBarHeightEnabled) {
                i5 = rect.bottom;
            }
        } else {
            boolean z4 = this.mIsFlipTinyScreen;
            if (!z4 || (i2 = this.mDisplayCutoutSafeInsets.bottom) <= 0) {
                i2 = z4 ? 0 : rectMergeInsets.bottom;
            }
            i5 += i2;
        }
        int i9 = layoutParams.topMargin;
        int i10 = rect.top;
        if (i9 != i10) {
            layoutParams.topMargin = i10;
            z = true;
        }
        if (layoutParams.bottomMargin != i5) {
            layoutParams.bottomMargin = i5;
            z = true;
        }
        int i11 = layoutParams.leftMargin;
        int i12 = rect.left;
        if (i11 != i12) {
            layoutParams.leftMargin = i12;
            z = true;
        }
        int i13 = layoutParams.rightMargin;
        int i14 = rect.right;
        if (i13 != i14) {
            layoutParams.rightMargin = i14;
            z = true;
        }
        if (panelWidth != iUpdatePanelPosMargins) {
            z = true;
        }
        int panelMaxLimitHeight = getPanelMaxLimitHeight(rectMergeInsets);
        if (panelMaxLimitHeight != this.mParentPanel.getPanelMaxLimitHeight()) {
            this.mParentPanel.setPanelMaxLimitHeight(panelMaxLimitHeight);
        } else {
            z2 = z;
        }
        if (z2) {
            this.mParentPanel.requestLayout();
        }
    }

    public final void updateRootViewSize(Configuration configuration) {
        WindowBaseInfo windowInfo = this.mIsFlipTinyScreen ? EnvStateManager.getWindowInfo(this.mContext) : EnvStateManager.getWindowInfo(this.mContext, configuration);
        Point point = this.mRootViewSizeDp;
        Point point2 = windowInfo.windowSizeDp;
        point.x = point2.x;
        point.y = point2.y;
        Point point3 = this.mRootViewSize;
        Point point4 = windowInfo.windowSize;
        point3.x = point4.x;
        point3.y = point4.y;
        if (this.mIsDebugEnabled) {
            Log.d("AlertController", "updateRootViewSize mRootViewSizeDp " + this.mRootViewSizeDp + " mRootViewSize " + this.mRootViewSize);
            if (configuration != null) {
                Log.d("AlertController", "configuration.density " + (configuration.densityDpi / 160.0f));
            }
        }
    }

    public final void updateRootViewSize(View view) {
        this.mRootViewSize.x = view.getWidth();
        this.mRootViewSize.y = view.getHeight();
        float f = this.mContext.getResources().getDisplayMetrics().density;
        Point point = this.mRootViewSizeDp;
        Point point2 = this.mRootViewSize;
        point.x = (int) (point2.x / f);
        point.y = (int) (point2.y / f);
        if (this.mIsDebugEnabled) {
            Log.d("AlertController", "updateRootViewSize by view mRootViewSizeDp " + this.mRootViewSizeDp + " mRootViewSize " + this.mRootViewSize + " configuration.density " + f);
        }
    }

    public final void updateViewOnDensityChanged(float f) {
        TextView textView;
        DialogParentPanel2 dialogParentPanel2 = this.mParentPanel;
        if (dialogParentPanel2 != null) {
            DensityChangedHelper.updateViewPadding(dialogParentPanel2, f);
        }
        TextView textView2 = this.mTitleView;
        if (textView2 != null) {
            DensityChangedHelper.updateTextSizeSpUnit(textView2, this.mTitleTextSize);
        }
        TextView textView3 = this.mMessageView;
        if (textView3 != null) {
            DensityChangedHelper.updateTextSizeSpUnit(textView3, this.mMessageTextSize);
        }
        TextView textView4 = this.mCommentView;
        if (textView4 != null) {
            DensityChangedHelper.updateTextSizeSpUnit(textView4, this.mCommentTextSize);
            DensityChangedHelper.updateViewPadding(this.mCommentView, f);
        }
        if (this.mCustomTitleView != null && (textView = this.mCustomTitleTextView) != null) {
            DensityChangedHelper.updateTextSizeDefaultUnit(textView, this.mCustomTitleTextSize);
        }
        View viewFindViewById = this.mWindow.findViewById(miuix.appcompat.R.id.buttonPanel);
        if (viewFindViewById != null) {
            DensityChangedHelper.updateViewMargin(viewFindViewById, f);
        }
        ViewGroup viewGroup = (ViewGroup) this.mWindow.findViewById(miuix.appcompat.R.id.topPanel);
        if (viewGroup != null) {
            DensityChangedHelper.updateViewPadding(viewGroup, f);
        }
        View viewFindViewById2 = this.mWindow.findViewById(miuix.appcompat.R.id.contentView);
        if (viewFindViewById2 != null) {
            DensityChangedHelper.updateViewMargin(viewFindViewById2, f);
        }
        CheckBox checkBox = (CheckBox) this.mWindow.findViewById(R.id.checkbox);
        if (checkBox != null) {
            DensityChangedHelper.updateViewMargin(checkBox, f);
        }
        ImageView imageView = (ImageView) this.mWindow.findViewById(R.id.icon);
        if (imageView != null) {
            DensityChangedHelper.updateViewSize(imageView, f);
            DensityChangedHelper.updateViewMargin(imageView, f);
        }
    }

    public final void updateWindowCutoutMode() {
        int screenOrientation = getScreenOrientation();
        if (this.mScreenOrientation != screenOrientation) {
            this.mScreenOrientation = screenOrientation;
            Activity associatedActivity = ((AlertDialog) this.mDialog).getAssociatedActivity();
            if (associatedActivity != null) {
                int cutoutMode = getCutoutMode(screenOrientation, associatedActivity.getWindow().getAttributes().layoutInDisplayCutoutMode);
                if (this.mWindow.getAttributes().layoutInDisplayCutoutMode != cutoutMode) {
                    this.mWindow.getAttributes().layoutInDisplayCutoutMode = cutoutMode;
                    View decorView = this.mWindow.getDecorView();
                    if (this.mDialog.isShowing() && decorView.isAttachedToWindow()) {
                        this.mWindowManager.updateViewLayout(this.mWindow.getDecorView(), this.mWindow.getAttributes());
                        return;
                    }
                    return;
                }
                return;
            }
            int i = getScreenOrientation() != 2 ? 1 : 2;
            if (this.mWindow.getAttributes().layoutInDisplayCutoutMode != i) {
                this.mWindow.getAttributes().layoutInDisplayCutoutMode = i;
                View decorView2 = this.mWindow.getDecorView();
                if (this.mDialog.isShowing() && decorView2.isAttachedToWindow()) {
                    this.mWindowManager.updateViewLayout(this.mWindow.getDecorView(), this.mWindow.getAttributes());
                }
            }
        }
    }
}
