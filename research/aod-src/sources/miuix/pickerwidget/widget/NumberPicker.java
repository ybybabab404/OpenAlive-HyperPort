package miuix.pickerwidget.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatEditText;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import miuix.internal.util.ReflectUtil;
import miuix.pickerwidget.R;
import miuix.pickerwidget.internal.util.SimpleNumberFormatter;
import miuix.pickerwidget.internal.util.async.WorkerThreads;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

/* JADX INFO: loaded from: classes2.dex */
public class NumberPicker extends LinearLayout {
    public static int SELECTOR_MIDDLE_ITEM_INDEX;
    public static int SELECTOR_WHEEL_ITEM_COUNT;
    public final Scroller mAdjustScroller;
    public BeginSoftInputOnLongPressCommand mBeginSoftInputOnLongPressCommand;
    public int mBottomSelectionDividerBottom;
    public ChangeCurrentByOneFromLongPressCommand mChangeCurrentByOneFromLongPressCommand;
    public boolean mColumnWidthWrapContent;
    public final boolean mComputeMaxWidth;
    public int mContentMarginVertical;
    public int mCurrentScrollOffset;
    public boolean mDecrementVirtualButtonPressed;
    public String mDisplayedMaxText;
    public float mDisplayedMaxTextWidth;
    public String[] mDisplayedValues;
    public final Matrix mFadeMatrix;
    public Paint mFadePaint;
    public LinearGradient mFadeShader;
    public PorterDuffXfermode mFadeXfermode;
    public final Scroller mFlingScroller;
    public Paint.FontMetricsInt mFontMetricsInt;
    public Formatter mFormatter;
    public final boolean mHasSelectorWheel;
    public final int mId;
    public boolean mIgnoreMoveEvents;
    public boolean mIncrementVirtualButtonPressed;
    public int mInitialScrollOffset;
    public final EditText mInputText;
    public CharSequence mLabel;
    public float mLabelFontHeight;
    public float mLabelHeight;
    public int mLabelMarginBottom;
    public int mLabelMarginLeft;
    public Paint mLabelPaint;
    public int mLabelTextColor;
    public int mLabelTextSize;
    public float mLabelTextSizeThreshold;
    public float mLabelTextSizeTrimFactor;
    public long mLastDownEventTime;
    public float mLastDownEventY;
    public float mLastDownOrMoveEventY;
    public int mLastHandledDownDpadKeyCode;
    public long mLongPressUpdateInterval;
    public float mMaxFlingSpeedFactor;
    public int mMaxHeight;
    public final int mMaxPanelHeight;
    public final int mMaxPanelNoLabelHeight;
    public int mMaxValue;
    public int mMaxWidth;
    public int mMaximumFlingVelocity;
    public boolean mMeasureBackgroundEnabled;
    public final int mMinHeight;
    public int mMinValue;
    public final int mMinWidth;
    public int mMinimumFlingVelocity;
    public String mModDevice;
    public OnScrollListener mOnScrollListener;
    public OnValueChangeListener mOnValueChangeListener;
    public int mOriginLabelTextSize;
    public int mOriginTextSizeHighlight;
    public int mOriginTextSizeHint;
    public final PressedStateHelper mPressedStateHelper;
    public int mPreviousScrollerY;
    public int mScrollState;
    public final int mSelectionDividerHeight;
    public final int mSelectionDividersDistance;
    public int mSelectorElementHeight;
    public final SparseArray mSelectorIndexToStringCache;
    public int[] mSelectorIndices;
    public int mSelectorTextGapHeight;
    public int mSelectorWheelAppearance;
    public final Paint mSelectorWheelPaint;
    public SetSelectionCommand mSetSelectionCommand;
    public boolean mShowLabel;
    public boolean mShowSoftInputOnTap;
    public SoundPlayHandler mSoundPlayHandler;
    public int mTextColorHighlight;
    public int mTextColorHint;
    public int mTextColorMajor;
    public int mTextColorMedium;
    public int mTextColorMinor;
    public int mTextGapMajor;
    public int mTextGapMinor;
    public int mTextPadding;
    public final int mTextSize;
    public int mTextSizeHighlight;
    public int mTextSizeHint;
    public int mTextSizeMajor;
    public int mTextSizeMedium;
    public int mTextSizeMinor;
    public float mTextSizeThreshold;
    public float mTextSizeTrimFactor;
    public int mTopSelectionDividerTop;
    public int mTouchSlop;
    public String mUpdateText;
    public int mValue;
    public VelocityTracker mVelocityTracker;
    public boolean mWrapSelectorWheel;
    public static final int DEFAULT_LAYOUT_RESOURCE_ID = R.layout.miuix_appcompat_number_picker_layout;
    public static final AtomicInteger sIdGenerator = new AtomicInteger(0);
    public static final Formatter TWO_DIGIT_FORMATTER = new NumberFormatter(2);
    public static final int[] PRESSED_STATE_SET = {android.R.attr.state_pressed};
    public static final char[] DIGIT_CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public class AccessibilityNodeProviderImpl extends AccessibilityNodeProvider {
        public int mAccessibilityFocusedView;
        public final int[] mTempArray;
        public final Rect mTempRect;
        public final /* synthetic */ NumberPicker this$0;

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
            if (i == -1) {
                return createAccessibilityNodeInfoForNumberPicker(this.this$0.getScrollX(), this.this$0.getScrollY(), this.this$0.getScrollX() + (this.this$0.getRight() - this.this$0.getLeft()), this.this$0.getScrollY() + (this.this$0.getBottom() - this.this$0.getTop()));
            }
            if (i == 1) {
                return createAccessibilityNodeInfoForVirtualButton(1, getVirtualIncrementButtonText(), this.this$0.getScrollX(), this.this$0.mBottomSelectionDividerBottom - this.this$0.mSelectionDividerHeight, this.this$0.getScrollX() + (this.this$0.getRight() - this.this$0.getLeft()), this.this$0.getScrollY() + (this.this$0.getBottom() - this.this$0.getTop()));
            }
            if (i != 2) {
                return i != 3 ? super.createAccessibilityNodeInfo(i) : createAccessibilityNodeInfoForVirtualButton(3, getVirtualDecrementButtonText(), this.this$0.getScrollX(), this.this$0.getScrollY(), this.this$0.getScrollX() + (this.this$0.getRight() - this.this$0.getLeft()), this.this$0.mTopSelectionDividerTop + this.this$0.mSelectionDividerHeight);
            }
            return createAccessibiltyNodeInfoForInputText(this.this$0.getScrollX(), this.this$0.mTopSelectionDividerTop + this.this$0.mSelectionDividerHeight, this.this$0.getScrollX() + (this.this$0.getRight() - this.this$0.getLeft()), this.this$0.mBottomSelectionDividerBottom - this.this$0.mSelectionDividerHeight);
        }

        public final AccessibilityNodeInfo createAccessibilityNodeInfoForNumberPicker(int i, int i2, int i3, int i4) {
            AccessibilityNodeInfo accessibilityNodeInfoObtain = AccessibilityNodeInfo.obtain();
            accessibilityNodeInfoObtain.setClassName(NumberPicker.class.getName());
            accessibilityNodeInfoObtain.setPackageName(this.this$0.getContext().getPackageName());
            accessibilityNodeInfoObtain.setSource(this.this$0);
            if (hasVirtualDecrementButton()) {
                accessibilityNodeInfoObtain.addChild(this.this$0, 3);
            }
            accessibilityNodeInfoObtain.addChild(this.this$0, 2);
            if (hasVirtualIncrementButton()) {
                accessibilityNodeInfoObtain.addChild(this.this$0, 1);
            }
            accessibilityNodeInfoObtain.setParent((View) this.this$0.getParentForAccessibility());
            accessibilityNodeInfoObtain.setEnabled(this.this$0.isEnabled());
            accessibilityNodeInfoObtain.setScrollable(true);
            Rect rect = this.mTempRect;
            rect.set(i, i2, i3, i4);
            accessibilityNodeInfoObtain.setBoundsInParent(rect);
            accessibilityNodeInfoObtain.setVisibleToUser(this.this$0.getVisibility() == 0);
            int[] iArr = this.mTempArray;
            this.this$0.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            accessibilityNodeInfoObtain.setBoundsInScreen(rect);
            if (this.mAccessibilityFocusedView != -1) {
                accessibilityNodeInfoObtain.addAction(64);
            }
            if (this.mAccessibilityFocusedView == -1) {
                accessibilityNodeInfoObtain.addAction(128);
            }
            if (this.this$0.isEnabled()) {
                if (this.this$0.getWrapSelectorWheel() || this.this$0.getValue() < this.this$0.getMaxValue()) {
                    accessibilityNodeInfoObtain.addAction(AutoBottomSheetKt.ALIGN_SCREEN_TOP);
                }
                if (this.this$0.getWrapSelectorWheel() || this.this$0.getValue() > this.this$0.getMinValue()) {
                    accessibilityNodeInfoObtain.addAction(AutoBottomSheetKt.ALIGN_SCREEN_START);
                }
            }
            return accessibilityNodeInfoObtain;
        }

        public final AccessibilityNodeInfo createAccessibilityNodeInfoForVirtualButton(int i, String str, int i2, int i3, int i4, int i5) {
            AccessibilityNodeInfo accessibilityNodeInfoObtain = AccessibilityNodeInfo.obtain();
            accessibilityNodeInfoObtain.setClassName(Button.class.getName());
            accessibilityNodeInfoObtain.setPackageName(this.this$0.getContext().getPackageName());
            accessibilityNodeInfoObtain.setSource(this.this$0, i);
            accessibilityNodeInfoObtain.setParent(this.this$0);
            accessibilityNodeInfoObtain.setText(str);
            accessibilityNodeInfoObtain.setClickable(true);
            accessibilityNodeInfoObtain.setLongClickable(true);
            accessibilityNodeInfoObtain.setEnabled(this.this$0.isEnabled());
            Rect rect = this.mTempRect;
            rect.set(i2, i3, i4, i5);
            accessibilityNodeInfoObtain.setVisibleToUser(this.this$0.getVisibility() == 0);
            accessibilityNodeInfoObtain.setBoundsInParent(rect);
            int[] iArr = this.mTempArray;
            this.this$0.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            accessibilityNodeInfoObtain.setBoundsInScreen(rect);
            if (this.mAccessibilityFocusedView != i) {
                accessibilityNodeInfoObtain.addAction(64);
            }
            if (this.mAccessibilityFocusedView == i) {
                accessibilityNodeInfoObtain.addAction(128);
            }
            if (this.this$0.isEnabled()) {
                accessibilityNodeInfoObtain.addAction(16);
            }
            return accessibilityNodeInfoObtain;
        }

        public final AccessibilityNodeInfo createAccessibiltyNodeInfoForInputText(int i, int i2, int i3, int i4) {
            AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = this.this$0.mInputText.createAccessibilityNodeInfo();
            accessibilityNodeInfoCreateAccessibilityNodeInfo.setSource(this.this$0, 2);
            if (this.mAccessibilityFocusedView != 2) {
                accessibilityNodeInfoCreateAccessibilityNodeInfo.addAction(64);
            }
            if (this.mAccessibilityFocusedView == 2) {
                accessibilityNodeInfoCreateAccessibilityNodeInfo.addAction(128);
            }
            Rect rect = this.mTempRect;
            rect.set(i, i2, i3, i4);
            accessibilityNodeInfoCreateAccessibilityNodeInfo.setVisibleToUser(this.this$0.getVisibility() == 0);
            accessibilityNodeInfoCreateAccessibilityNodeInfo.setBoundsInParent(rect);
            int[] iArr = this.mTempArray;
            this.this$0.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            accessibilityNodeInfoCreateAccessibilityNodeInfo.setBoundsInScreen(rect);
            return accessibilityNodeInfoCreateAccessibilityNodeInfo;
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public List findAccessibilityNodeInfosByText(String str, int i) {
            if (TextUtils.isEmpty(str)) {
                return Collections.EMPTY_LIST;
            }
            String lowerCase = str.toLowerCase();
            ArrayList arrayList = new ArrayList();
            if (i == -1) {
                findAccessibilityNodeInfosByTextInChild(lowerCase, 3, arrayList);
                findAccessibilityNodeInfosByTextInChild(lowerCase, 2, arrayList);
                findAccessibilityNodeInfosByTextInChild(lowerCase, 1, arrayList);
                return arrayList;
            }
            if (i != 1 && i != 2 && i != 3) {
                return super.findAccessibilityNodeInfosByText(str, i);
            }
            findAccessibilityNodeInfosByTextInChild(lowerCase, i, arrayList);
            return arrayList;
        }

        public final void findAccessibilityNodeInfosByTextInChild(String str, int i, List list) {
            if (i == 1) {
                String virtualIncrementButtonText = getVirtualIncrementButtonText();
                if (TextUtils.isEmpty(virtualIncrementButtonText) || !virtualIncrementButtonText.toString().toLowerCase().contains(str)) {
                    return;
                }
                list.add(createAccessibilityNodeInfo(1));
                return;
            }
            if (i != 2) {
                if (i != 3) {
                    return;
                }
                String virtualDecrementButtonText = getVirtualDecrementButtonText();
                if (TextUtils.isEmpty(virtualDecrementButtonText) || !virtualDecrementButtonText.toString().toLowerCase().contains(str)) {
                    return;
                }
                list.add(createAccessibilityNodeInfo(3));
                return;
            }
            Editable text = this.this$0.mInputText.getText();
            if (!TextUtils.isEmpty(text) && text.toString().toLowerCase().contains(str)) {
                list.add(createAccessibilityNodeInfo(2));
                return;
            }
            Editable text2 = this.this$0.mInputText.getText();
            if (TextUtils.isEmpty(text2) || !text2.toString().toLowerCase().contains(str)) {
                return;
            }
            list.add(createAccessibilityNodeInfo(2));
        }

        public final String getVirtualDecrementButtonText() {
            int wrappedSelectorIndex = this.this$0.mValue - 1;
            if (this.this$0.mWrapSelectorWheel) {
                wrappedSelectorIndex = this.this$0.getWrappedSelectorIndex(wrappedSelectorIndex);
            }
            if (wrappedSelectorIndex >= this.this$0.mMinValue) {
                return this.this$0.mDisplayedValues == null ? this.this$0.formatNumber(wrappedSelectorIndex) : this.this$0.mDisplayedValues[wrappedSelectorIndex - this.this$0.mMinValue];
            }
            return null;
        }

        public final String getVirtualIncrementButtonText() {
            int wrappedSelectorIndex = this.this$0.mValue + 1;
            if (this.this$0.mWrapSelectorWheel) {
                wrappedSelectorIndex = this.this$0.getWrappedSelectorIndex(wrappedSelectorIndex);
            }
            if (wrappedSelectorIndex <= this.this$0.mMaxValue) {
                return this.this$0.mDisplayedValues == null ? this.this$0.formatNumber(wrappedSelectorIndex) : this.this$0.mDisplayedValues[wrappedSelectorIndex - this.this$0.mMinValue];
            }
            return null;
        }

        public final boolean hasVirtualDecrementButton() {
            return this.this$0.getWrapSelectorWheel() || this.this$0.getValue() > this.this$0.getMinValue();
        }

        public final boolean hasVirtualIncrementButton() {
            return this.this$0.getWrapSelectorWheel() || this.this$0.getValue() < this.this$0.getMaxValue();
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public boolean performAction(int i, int i2, Bundle bundle) {
            if (i != -1) {
                if (i == 1) {
                    if (i2 == 16) {
                        if (!this.this$0.isEnabled()) {
                            return false;
                        }
                        this.this$0.changeValueByOne(true);
                        sendAccessibilityEventForVirtualView(i, 1);
                        return true;
                    }
                    if (i2 == 64) {
                        if (this.mAccessibilityFocusedView == i) {
                            return false;
                        }
                        this.mAccessibilityFocusedView = i;
                        sendAccessibilityEventForVirtualView(i, 32768);
                        NumberPicker numberPicker = this.this$0;
                        numberPicker.invalidate(0, numberPicker.mBottomSelectionDividerBottom, this.this$0.getRight(), this.this$0.getBottom());
                        return true;
                    }
                    if (i2 != 128 || this.mAccessibilityFocusedView != i) {
                        return false;
                    }
                    this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                    sendAccessibilityEventForVirtualView(i, 65536);
                    NumberPicker numberPicker2 = this.this$0;
                    numberPicker2.invalidate(0, numberPicker2.mBottomSelectionDividerBottom, this.this$0.getRight(), this.this$0.getBottom());
                    return true;
                }
                if (i == 2) {
                    if (i2 == 1) {
                        if (!this.this$0.isEnabled() || this.this$0.mInputText.isFocused()) {
                            return false;
                        }
                        return this.this$0.mInputText.requestFocus();
                    }
                    if (i2 == 2) {
                        if (!this.this$0.isEnabled() || !this.this$0.mInputText.isFocused()) {
                            return false;
                        }
                        this.this$0.mInputText.clearFocus();
                        return true;
                    }
                    if (i2 == 16) {
                        return this.this$0.isEnabled();
                    }
                    if (i2 == 64) {
                        if (this.mAccessibilityFocusedView == i) {
                            return false;
                        }
                        this.mAccessibilityFocusedView = i;
                        sendAccessibilityEventForVirtualView(i, 32768);
                        this.this$0.mInputText.invalidate();
                        return true;
                    }
                    if (i2 != 128) {
                        return this.this$0.mInputText.performAccessibilityAction(i2, bundle);
                    }
                    if (this.mAccessibilityFocusedView != i) {
                        return false;
                    }
                    this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                    sendAccessibilityEventForVirtualView(i, 65536);
                    this.this$0.mInputText.invalidate();
                    return true;
                }
                if (i == 3) {
                    if (i2 == 16) {
                        if (!this.this$0.isEnabled()) {
                            return false;
                        }
                        this.this$0.changeValueByOne(i == 1);
                        sendAccessibilityEventForVirtualView(i, 1);
                        return true;
                    }
                    if (i2 == 64) {
                        if (this.mAccessibilityFocusedView == i) {
                            return false;
                        }
                        this.mAccessibilityFocusedView = i;
                        sendAccessibilityEventForVirtualView(i, 32768);
                        NumberPicker numberPicker3 = this.this$0;
                        numberPicker3.invalidate(0, 0, numberPicker3.getRight(), this.this$0.mTopSelectionDividerTop);
                        return true;
                    }
                    if (i2 != 128 || this.mAccessibilityFocusedView != i) {
                        return false;
                    }
                    this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                    sendAccessibilityEventForVirtualView(i, 65536);
                    NumberPicker numberPicker4 = this.this$0;
                    numberPicker4.invalidate(0, 0, numberPicker4.getRight(), this.this$0.mTopSelectionDividerTop);
                    return true;
                }
            } else {
                if (i2 == 64) {
                    if (this.mAccessibilityFocusedView == i) {
                        return false;
                    }
                    this.mAccessibilityFocusedView = i;
                    return true;
                }
                if (i2 == 128) {
                    if (this.mAccessibilityFocusedView != i) {
                        return false;
                    }
                    this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                    return true;
                }
                if (i2 == 4096) {
                    if (!this.this$0.isEnabled() || (!this.this$0.getWrapSelectorWheel() && this.this$0.getValue() >= this.this$0.getMaxValue())) {
                        return false;
                    }
                    this.this$0.changeValueByOne(true);
                    return true;
                }
                if (i2 == 8192) {
                    if (!this.this$0.isEnabled() || (!this.this$0.getWrapSelectorWheel() && this.this$0.getValue() <= this.this$0.getMinValue())) {
                        return false;
                    }
                    this.this$0.changeValueByOne(false);
                    return true;
                }
            }
            return super.performAction(i, i2, bundle);
        }

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
        public final void sendAccessibilityEventForVirtualButton(int i, int i2, String str) {
            if (((AccessibilityManager) this.this$0.getContext().getSystemService("accessibility")).isEnabled()) {
                AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(i2);
                accessibilityEventObtain.setClassName(Button.class.getName());
                accessibilityEventObtain.setPackageName(this.this$0.getContext().getPackageName());
                accessibilityEventObtain.getText().add(str);
                accessibilityEventObtain.setEnabled(this.this$0.isEnabled());
                accessibilityEventObtain.setSource(this.this$0, i);
                NumberPicker numberPicker = this.this$0;
                numberPicker.requestSendAccessibilityEvent(numberPicker, accessibilityEventObtain);
            }
        }

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
        public final void sendAccessibilityEventForVirtualText(int i) {
            if (((AccessibilityManager) this.this$0.getContext().getSystemService("accessibility")).isEnabled()) {
                AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(i);
                this.this$0.mInputText.onInitializeAccessibilityEvent(accessibilityEventObtain);
                this.this$0.mInputText.onPopulateAccessibilityEvent(accessibilityEventObtain);
                accessibilityEventObtain.setSource(this.this$0, 2);
                NumberPicker numberPicker = this.this$0;
                numberPicker.requestSendAccessibilityEvent(numberPicker, accessibilityEventObtain);
            }
        }

        public void sendAccessibilityEventForVirtualView(int i, int i2) {
            if (i == 1) {
                if (hasVirtualIncrementButton()) {
                    sendAccessibilityEventForVirtualButton(i, i2, getVirtualIncrementButtonText());
                }
            } else if (i == 2) {
                sendAccessibilityEventForVirtualText(i2);
            } else if (i == 3 && hasVirtualDecrementButton()) {
                sendAccessibilityEventForVirtualButton(i, i2, getVirtualDecrementButtonText());
            }
        }
    }

    public class BeginSoftInputOnLongPressCommand implements Runnable {
        public BeginSoftInputOnLongPressCommand() {
        }

        @Override // java.lang.Runnable
        public void run() {
            NumberPicker.this.mIgnoreMoveEvents = true;
        }
    }

    public class ChangeCurrentByOneFromLongPressCommand implements Runnable {
        public boolean mIncrement;

        public ChangeCurrentByOneFromLongPressCommand() {
        }

        @Override // java.lang.Runnable
        public void run() {
            NumberPicker.this.changeValueByOne(this.mIncrement);
            NumberPicker numberPicker = NumberPicker.this;
            numberPicker.postDelayed(this, numberPicker.mLongPressUpdateInterval);
        }

        public final void setStep(boolean z) {
            this.mIncrement = z;
        }
    }

    public static class CustomEditText extends AppCompatEditText {
        public CustomEditText(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override // android.widget.TextView
        public void onEditorAction(int i) {
            super.onEditorAction(i);
            if (i == 6) {
                clearFocus();
            }
        }
    }

    public interface Formatter {
        String format(int i);
    }

    public class InputTextFilter extends NumberKeyListener {
        public InputTextFilter() {
        }

        @Override // android.text.method.NumberKeyListener, android.text.InputFilter
        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            if (NumberPicker.this.mDisplayedValues == null) {
                CharSequence charSequenceFilter = super.filter(charSequence, i, i2, spanned, i3, i4);
                if (charSequenceFilter == null) {
                    charSequenceFilter = charSequence.subSequence(i, i2);
                }
                String str = String.valueOf(spanned.subSequence(0, i3)) + ((Object) charSequenceFilter) + ((Object) spanned.subSequence(i4, spanned.length()));
                if ("".equals(str)) {
                    return str;
                }
                return (NumberPicker.this.getSelectedPos(str) > NumberPicker.this.mMaxValue || str.length() > String.valueOf(NumberPicker.this.mMaxValue).length()) ? "" : charSequenceFilter;
            }
            String strValueOf = String.valueOf(charSequence.subSequence(i, i2));
            if (TextUtils.isEmpty(strValueOf)) {
                return "";
            }
            String str2 = String.valueOf(spanned.subSequence(0, i3)) + ((Object) strValueOf) + ((Object) spanned.subSequence(i4, spanned.length()));
            String lowerCase = String.valueOf(str2).toLowerCase();
            for (String str3 : NumberPicker.this.mDisplayedValues) {
                if (str3.toLowerCase().startsWith(lowerCase)) {
                    NumberPicker.this.postSetSelectionCommand(str2.length(), str3.length());
                    return str3.subSequence(i3, str3.length());
                }
            }
            return "";
        }

        @Override // android.text.method.NumberKeyListener
        public char[] getAcceptedChars() {
            return NumberPicker.DIGIT_CHARACTERS;
        }

        @Override // android.text.method.KeyListener
        public int getInputType() {
            return 1;
        }
    }

    public static class NumberFormatter implements Formatter {
        public final int iWidth;

        public NumberFormatter() {
            this.iWidth = -1;
        }

        public NumberFormatter(int i) {
            this.iWidth = i;
        }

        @Override // miuix.pickerwidget.widget.NumberPicker.Formatter
        public String format(int i) {
            return SimpleNumberFormatter.format(this.iWidth, i);
        }
    }

    public interface OnScrollListener {
        void onScrollStateChange(NumberPicker numberPicker, int i);
    }

    public interface OnValueChangeListener {
        void onValueChange(NumberPicker numberPicker, int i, int i2);
    }

    public class PressedStateHelper implements Runnable {
        public final int MODE_PRESS = 1;
        public final int MODE_TAPPED = 2;
        public int mManagedButton;
        public int mMode;

        public PressedStateHelper() {
        }

        public void buttonPressDelayed(int i) {
            cancel();
            this.mMode = 1;
            this.mManagedButton = i;
            NumberPicker.this.postDelayed(this, ViewConfiguration.getTapTimeout());
        }

        public void buttonTapped(int i) {
            cancel();
            this.mMode = 2;
            this.mManagedButton = i;
            NumberPicker.this.post(this);
        }

        public void cancel() {
            this.mMode = 0;
            this.mManagedButton = 0;
            NumberPicker.this.removeCallbacks(this);
            if (NumberPicker.this.mIncrementVirtualButtonPressed) {
                NumberPicker.this.mIncrementVirtualButtonPressed = false;
                NumberPicker numberPicker = NumberPicker.this;
                numberPicker.invalidate(0, numberPicker.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
            }
            if (NumberPicker.this.mDecrementVirtualButtonPressed) {
                NumberPicker.this.mDecrementVirtualButtonPressed = false;
                NumberPicker numberPicker2 = NumberPicker.this;
                numberPicker2.invalidate(0, 0, numberPicker2.getRight(), NumberPicker.this.mTopSelectionDividerTop);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            int i = this.mMode;
            if (i == 1) {
                int i2 = this.mManagedButton;
                if (i2 == 1) {
                    NumberPicker.this.mIncrementVirtualButtonPressed = true;
                    NumberPicker numberPicker = NumberPicker.this;
                    numberPicker.invalidate(0, numberPicker.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                    return;
                } else {
                    if (i2 != 2) {
                        return;
                    }
                    NumberPicker.this.mDecrementVirtualButtonPressed = true;
                    NumberPicker numberPicker2 = NumberPicker.this;
                    numberPicker2.invalidate(0, 0, numberPicker2.getRight(), NumberPicker.this.mTopSelectionDividerTop);
                    return;
                }
            }
            if (i != 2) {
                return;
            }
            int i3 = this.mManagedButton;
            if (i3 == 1) {
                if (!NumberPicker.this.mIncrementVirtualButtonPressed) {
                    NumberPicker.this.postDelayed(this, ViewConfiguration.getPressedStateDuration());
                }
                NumberPicker.access$1180(NumberPicker.this, 1);
                NumberPicker numberPicker3 = NumberPicker.this;
                numberPicker3.invalidate(0, numberPicker3.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                return;
            }
            if (i3 != 2) {
                return;
            }
            if (!NumberPicker.this.mDecrementVirtualButtonPressed) {
                NumberPicker.this.postDelayed(this, ViewConfiguration.getPressedStateDuration());
            }
            NumberPicker.access$1380(NumberPicker.this, 1);
            NumberPicker numberPicker4 = NumberPicker.this;
            numberPicker4.invalidate(0, 0, numberPicker4.getRight(), NumberPicker.this.mTopSelectionDividerTop);
        }
    }

    public static class SelectorWheelAppearance {
    }

    public class SetSelectionCommand implements Runnable {
        public int mSelectionEnd;
        public int mSelectionStart;

        public SetSelectionCommand() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mSelectionEnd < NumberPicker.this.mInputText.length()) {
                NumberPicker.this.mInputText.setSelection(this.mSelectionStart, this.mSelectionEnd);
            }
        }
    }

    public static class SoundPlayHandler extends Handler {
        public static final SoundPlayerContainer sPlayerContainer = new SoundPlayerContainer();

        public static class SoundPlayerContainer {
            public long mPrevPlayTime;
            public final Set mRefs;
            public int mSoundId;
            public SoundPool mSoundPlayer;

            public SoundPlayerContainer() {
                this.mRefs = new CopyOnWriteArraySet();
            }

            public void init(Context context, int i) {
                if (this.mSoundPlayer == null) {
                    SoundPool soundPool = new SoundPool(1, 1, 0);
                    this.mSoundPlayer = soundPool;
                    this.mSoundId = soundPool.load(context, R.raw.number_picker_value_change, 1);
                }
                this.mRefs.add(Integer.valueOf(i));
            }

            public void play() {
                long jCurrentTimeMillis = System.currentTimeMillis();
                SoundPool soundPool = this.mSoundPlayer;
                if (soundPool == null || jCurrentTimeMillis - this.mPrevPlayTime <= 50) {
                    return;
                }
                soundPool.play(this.mSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
                this.mPrevPlayTime = jCurrentTimeMillis;
            }

            public void release(int i) {
                SoundPool soundPool;
                if (this.mRefs.remove(Integer.valueOf(i)) && this.mRefs.isEmpty() && (soundPool = this.mSoundPlayer) != null) {
                    soundPool.release();
                    this.mSoundPlayer = null;
                }
            }
        }

        public SoundPlayHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 0) {
                sPlayerContainer.init((Context) message.obj, message.arg1);
            } else if (i == 1) {
                sPlayerContainer.play();
            } else {
                if (i != 2) {
                    return;
                }
                sPlayerContainer.release(message.arg1);
            }
        }

        public void init(Context context, int i) {
            Message messageObtainMessage = obtainMessage(0, i, 0);
            messageObtainMessage.obj = context;
            sendMessage(messageObtainMessage);
        }

        public void play() {
            sendMessage(obtainMessage(1));
        }

        public void release(int i) {
            sendMessage(obtainMessage(2, i, 0));
        }

        public void stop() {
            removeMessages(1);
        }
    }

    public NumberPicker(Context context) {
        this(context, null);
    }

    public NumberPicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.numberPickerStyle);
    }

    public NumberPicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mId = sIdGenerator.incrementAndGet();
        this.mLabelMarginLeft = 0;
        this.mLabelMarginBottom = 0;
        this.mContentMarginVertical = 0;
        this.mMaxWidth = 400;
        this.mLongPressUpdateInterval = 300L;
        this.mSelectorIndexToStringCache = new SparseArray();
        this.mInitialScrollOffset = Integer.MIN_VALUE;
        this.mScrollState = 0;
        this.mLastHandledDownDpadKeyCode = -1;
        this.mFadePaint = new Paint();
        this.mFadeXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        this.mFadeShader = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, Shader.TileMode.CLAMP);
        this.mFadeMatrix = new Matrix();
        this.mTextSizeTrimFactor = 0.95f;
        this.mLabelTextSizeTrimFactor = 0.8f;
        this.mMaxFlingSpeedFactor = 1.0f;
        this.mMeasureBackgroundEnabled = true;
        float f = getResources().getDisplayMetrics().density;
        this.mLabelMarginBottom = getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_number_picker_label_margin_bottom);
        this.mContentMarginVertical = getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_number_picker_content_margin_vertical);
        parseStyle(attributeSet, i);
        notifySelectorWheelAppearanceChange();
        initSoundPlayer();
        this.mHasSelectorWheel = true;
        this.mSelectionDividerHeight = (int) TypedValue.applyDimension(1, 2.0f, getResources().getDisplayMetrics());
        this.mSelectionDividersDistance = (int) ((45.0f * f) + 0.5f);
        this.mMinHeight = -1;
        this.mMaxPanelHeight = (int) ((198.0f * f) + 0.5f);
        this.mMaxPanelNoLabelHeight = (int) ((f * 156.0f) + 0.5f);
        this.mMinWidth = -1;
        this.mMaxWidth = -1;
        this.mComputeMaxWidth = true;
        this.mPressedStateHelper = new PressedStateHelper();
        setWillNotDraw(!true);
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.miuix_appcompat_number_picker_layout, (ViewGroup) this, true);
        EditText editText = (EditText) findViewById(R.id.number_picker_input);
        this.mInputText = editText;
        initInputText();
        initThreshHolds();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity() / 8;
        this.mTextSize = (int) editText.getTextSize();
        this.mSelectorWheelPaint = initSelectorWheelPaint();
        initLabelPaint();
        this.mFlingScroller = new Scroller(getContext(), null, true);
        this.mAdjustScroller = new Scroller(getContext(), new DecelerateInterpolator(2.5f));
        updateInputTextView();
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        updateLabelState();
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [boolean, byte] */
    public static /* synthetic */ boolean access$1180(NumberPicker numberPicker, int i) {
        ?? r2 = (i ^ (numberPicker.mIncrementVirtualButtonPressed ? 1 : 0)) == true ? (byte) 1 : (byte) 0;
        numberPicker.mIncrementVirtualButtonPressed = r2;
        return r2;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [boolean, byte] */
    public static /* synthetic */ boolean access$1380(NumberPicker numberPicker, int i) {
        ?? r2 = (i ^ (numberPicker.mDecrementVirtualButtonPressed ? 1 : 0)) == true ? (byte) 1 : (byte) 0;
        numberPicker.mDecrementVirtualButtonPressed = r2;
        return r2;
    }

    public final int calculateFontHeight(Paint paint, float f) {
        paint.setTextSize(f);
        if (this.mFontMetricsInt == null) {
            this.mFontMetricsInt = new Paint.FontMetricsInt();
        }
        if (Build.VERSION.SDK_INT >= 35) {
            paint.getFontMetricsIntForLocale(this.mFontMetricsInt);
        } else {
            this.mFontMetricsInt = paint.getFontMetricsInt();
        }
        Paint.FontMetricsInt fontMetricsInt = this.mFontMetricsInt;
        return fontMetricsInt.bottom - fontMetricsInt.top;
    }

    public final void changeValueByOne(boolean z) {
        changeValueBySteps(z ? 1 : -1);
    }

    public final void changeValueBySteps(int i) {
        if (!this.mHasSelectorWheel) {
            setValueInternal(this.mValue + i, true);
            return;
        }
        this.mInputText.setVisibility(4);
        if (!moveToFinalScrollerPosition(this.mFlingScroller)) {
            moveToFinalScrollerPosition(this.mAdjustScroller);
        }
        this.mPreviousScrollerY = 0;
        this.mFlingScroller.startScroll(0, 0, 0, (-this.mSelectorElementHeight) * i, 300);
        invalidate();
    }

    @Override // android.view.View
    public void computeScroll() {
        Scroller scroller = this.mFlingScroller;
        if (scroller.isFinished()) {
            scroller = this.mAdjustScroller;
            if (scroller.isFinished()) {
                return;
            }
        }
        scroller.computeScrollOffset();
        int currY = scroller.getCurrY();
        if (this.mPreviousScrollerY == 0) {
            this.mPreviousScrollerY = scroller.getStartY();
        }
        scrollBy(0, currY - this.mPreviousScrollerY);
        this.mPreviousScrollerY = currY;
        if (scroller.isFinished()) {
            onScrollerFinished(scroller);
        } else {
            invalidate();
        }
    }

    public final void decrementSelectorIndices(int[] iArr) {
        if (iArr.length - 1 >= 0) {
            System.arraycopy(iArr, 0, iArr, 1, iArr.length - 1);
        }
        int i = iArr[1] - 1;
        if (this.mWrapSelectorWheel && i < this.mMinValue) {
            i = this.mMaxValue;
        }
        iArr[0] = i;
        ensureCachedScrollSelectorValue(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x004c, code lost:
    
        requestFocus();
        r5.mLastHandledDownDpadKeyCode = r0;
        removeAllCallbacks();
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x005a, code lost:
    
        if (r5.mFlingScroller.isFinished() == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x005c, code lost:
    
        if (r0 != 20) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x005e, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0060, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0061, code lost:
    
        changeValueByOne(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0064, code lost:
    
        return true;
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean dispatchKeyEvent(android.view.KeyEvent r6) {
        /*
            r5 = this;
            int r0 = r6.getKeyCode()
            r1 = 19
            r2 = 20
            if (r0 == r1) goto L19
            if (r0 == r2) goto L19
            r1 = 23
            if (r0 == r1) goto L15
            r1 = 66
            if (r0 == r1) goto L15
            goto L65
        L15:
            r5.removeAllCallbacks()
            goto L65
        L19:
            boolean r1 = r5.mHasSelectorWheel
            if (r1 != 0) goto L1e
            goto L65
        L1e:
            int r1 = r6.getAction()
            r3 = 1
            if (r1 == 0) goto L30
            if (r1 == r3) goto L28
            goto L65
        L28:
            int r1 = r5.mLastHandledDownDpadKeyCode
            if (r1 != r0) goto L65
            r6 = -1
            r5.mLastHandledDownDpadKeyCode = r6
            return r3
        L30:
            boolean r1 = r5.mWrapSelectorWheel
            if (r1 != 0) goto L42
            if (r0 != r2) goto L37
            goto L42
        L37:
            int r1 = r5.getValue()
            int r4 = r5.getMinValue()
            if (r1 <= r4) goto L65
            goto L4c
        L42:
            int r1 = r5.getValue()
            int r4 = r5.getMaxValue()
            if (r1 >= r4) goto L65
        L4c:
            r5.requestFocus()
            r5.mLastHandledDownDpadKeyCode = r0
            r5.removeAllCallbacks()
            android.widget.Scroller r6 = r5.mFlingScroller
            boolean r6 = r6.isFinished()
            if (r6 == 0) goto L64
            if (r0 != r2) goto L60
            r6 = r3
            goto L61
        L60:
            r6 = 0
        L61:
            r5.changeValueByOne(r6)
        L64:
            return r3
        L65:
            boolean r5 = super.dispatchKeyEvent(r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: miuix.pickerwidget.widget.NumberPicker.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        getParent().requestDisallowInterceptTouchEvent(true);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            removeAllCallbacks();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            removeAllCallbacks();
        }
        return super.dispatchTrackballEvent(motionEvent);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public final void drawFadeForWheel(Canvas canvas) {
        if (isSelectorWheelThreeLines() || this.mScrollState != 0) {
            this.mFadePaint.setXfermode(this.mFadeXfermode);
            int width = getWidth();
            int height = getHeight();
            float f = (this.mShowLabel ? this.mContentMarginVertical + this.mLabelHeight : this.mContentMarginVertical * 0.5f) - 1.0f;
            int i = this.mContentMarginVertical;
            float f2 = (height - (i * 0.5f)) + 1.0f;
            this.mFadeMatrix.setScale(1.0f, i);
            this.mFadeMatrix.postRotate(180.0f);
            this.mFadeMatrix.postTranslate(0.0f, f2);
            this.mFadeShader.setLocalMatrix(this.mFadeMatrix);
            this.mFadePaint.setShader(this.mFadeShader);
            float f3 = width;
            canvas.drawRect(0.0f, f2 - this.mContentMarginVertical, f3, f2, this.mFadePaint);
            this.mFadeMatrix.setScale(1.0f, this.mContentMarginVertical);
            this.mFadeMatrix.postTranslate(0.0f, f);
            this.mFadeShader.setLocalMatrix(this.mFadeMatrix);
            this.mFadePaint.setShader(this.mFadeShader);
            canvas.drawRect(0.0f, f, f3, f + this.mContentMarginVertical, this.mFadePaint);
        }
    }

    public final void drawLabelText(Canvas canvas, float f) {
        if (this.mShowLabel) {
            canvas.drawText(this.mLabel.toString(), f - (this.mLabelPaint.measureText(this.mLabel.toString()) / 2.0f), this.mContentMarginVertical - this.mLabelPaint.getFontMetrics().top, this.mLabelPaint);
        }
    }

    public final float drawScrollValue(Canvas canvas, float f, float f2) {
        float f3 = this.mShowLabel ? this.mContentMarginVertical + this.mLabelHeight : this.mContentMarginVertical * 0.5f;
        int iSave = canvas.save();
        canvas.clipRect(0.0f, f3, getWidth(), getHeight() - (this.mContentMarginVertical * 0.5f));
        float f4 = this.mCurrentScrollOffset;
        SparseArray sparseArray = this.mSelectorIndexToStringCache;
        for (int i : this.mSelectorIndices) {
            String str = (String) sparseArray.get(i);
            float fAbs = Math.abs(f2 - f4) / this.mSelectorElementHeight;
            int i2 = this.mTextSizeHighlight;
            float f5 = i2;
            float f6 = this.mTextSizeThreshold;
            if (f5 > f6) {
                i2 = (int) f6;
            } else {
                float width = getWidth() / this.mSelectorWheelPaint.measureText(str);
                if (width < 1.0f) {
                    i2 = (int) (this.mTextSizeHighlight * width);
                }
            }
            int i3 = this.mTextSizeHint;
            int i4 = (int) (i2 * 0.85f);
            if (i3 > i4) {
                i3 = i4;
            }
            float textSize = getTextSize(fAbs, i2, i3);
            this.mSelectorWheelPaint.setTextSize(textSize);
            this.mSelectorWheelPaint.setColor(getAlphaGradient(fAbs, this.mTextColorHint, false));
            float f7 = ((textSize - i3) / 2.0f) + f4;
            canvas.drawText(str, f, f7, this.mSelectorWheelPaint);
            if (fAbs < 1.0f) {
                this.mSelectorWheelPaint.setColor(getAlphaGradient(fAbs, this.mTextColorHighlight, true));
                canvas.drawText(str, f, f7, this.mSelectorWheelPaint);
            }
            f4 += this.mSelectorElementHeight;
        }
        canvas.restoreToCount(iSave);
        return f4;
    }

    public final float drawScrollValueOfFiveLines(Canvas canvas, float f, float f2) {
        int i;
        Canvas canvas2 = canvas;
        float f3 = getResources().getDisplayMetrics().density;
        float f4 = 12.0f * f3;
        float f5 = 8.0f * f3;
        int i2 = 0;
        int i3 = 1;
        float[] fArr = {f4, f5, f5, f4};
        float f6 = f3 * 18.0f;
        float f7 = this.mShowLabel ? this.mContentMarginVertical + this.mLabelHeight : this.mContentMarginVertical * 0.5f;
        int iSave = canvas2.save();
        canvas2.clipRect(0.0f, f7, getWidth(), getHeight() - (this.mContentMarginVertical * 0.5f));
        float f8 = (this.mShowLabel ? this.mContentMarginVertical + this.mLabelHeight : this.mContentMarginVertical * 0.5f) + f6 + (this.mCurrentScrollOffset - this.mInitialScrollOffset);
        float textSize = this.mSelectorWheelPaint.getTextSize();
        SparseArray sparseArray = this.mSelectorIndexToStringCache;
        int[] iArr = this.mSelectorIndices;
        int length = iArr.length;
        float fCalculateFontHeight = f8;
        int i4 = 0;
        while (i4 < length) {
            String str = (String) sparseArray.get(iArr[i4]);
            int i5 = i4;
            int i6 = i2;
            float textSizeOfFiveLines = getTextSizeOfFiveLines(i6, 0.0f, this.mTextSizeMajor, this.mTextSizeMinor, this.mTextSizeMedium);
            this.mSelectorWheelPaint.setTextSize(textSizeOfFiveLines);
            if (i6 == 0 || i6 == 4) {
                i = this.mTextColorMinor;
            } else {
                i = (i6 == i3 || i6 == 3) ? this.mTextColorMedium : this.mTextColorMajor;
            }
            this.mSelectorWheelPaint.setColor(i);
            Canvas canvas3 = canvas2;
            int i7 = i3;
            float f9 = fCalculateFontHeight;
            drawTextFromTop(canvas3, str, f, f9, this.mSelectorWheelPaint);
            fCalculateFontHeight = i6 < 4 ? f9 + calculateFontHeight(this.mSelectorWheelPaint, textSizeOfFiveLines) + fArr[i6] : f9;
            i2 = i6 + 1;
            i3 = i7;
            canvas2 = canvas3;
            i4 = i5 + 1;
        }
        float f10 = fCalculateFontHeight;
        this.mSelectorWheelPaint.setTextSize(textSize);
        canvas2.restoreToCount(iSave);
        return f10;
    }

    public final void drawTextFromTop(Canvas canvas, String str, float f, float f2, Paint paint) {
        canvas.drawText(str, f, f2 - paint.getFontMetrics().top, paint);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        tryComputeMaxWidth();
    }

    public final void ensureCachedScrollSelectorValue(int i) {
        String number;
        SparseArray sparseArray = this.mSelectorIndexToStringCache;
        if (((String) sparseArray.get(i)) != null) {
            return;
        }
        int i2 = this.mMinValue;
        if (i < i2 || i > this.mMaxValue) {
            number = "";
        } else {
            String[] strArr = this.mDisplayedValues;
            number = strArr != null ? strArr[i - i2] : formatNumber(i);
        }
        sparseArray.put(i, number);
    }

    public final boolean ensureScrollWheelAdjusted() {
        int i = this.mInitialScrollOffset - this.mCurrentScrollOffset;
        if (i == 0) {
            return false;
        }
        this.mPreviousScrollerY = 0;
        int iAbs = Math.abs(i);
        int i2 = this.mSelectorElementHeight;
        if (iAbs > i2 / 2) {
            if (i > 0) {
                i2 = -i2;
            }
            i += i2;
        }
        this.mAdjustScroller.startScroll(0, 0, 0, i, 800);
        invalidate();
        return true;
    }

    public final void fling(int i) {
        this.mPreviousScrollerY = 0;
        if (i > 0) {
            this.mFlingScroller.fling(0, 0, 0, i, 0, 0, 0, Integer.MAX_VALUE);
        } else {
            this.mFlingScroller.fling(0, Integer.MAX_VALUE, 0, i, 0, 0, 0, Integer.MAX_VALUE);
        }
        invalidate();
    }

    public final String formatNumber(int i) {
        Formatter formatter = this.mFormatter;
        return formatter != null ? formatter.format(i) : SimpleNumberFormatter.format(i);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return NumberPicker.class.getName();
    }

    public final int getAlphaGradient(float f, int i, boolean z) {
        if (f >= 1.0f) {
            return i;
        }
        return ((z ? (int) (((-f) * Color.alpha(i)) + Color.alpha(i)) : (int) (f * Color.alpha(i))) << 24) | (16777215 & i);
    }

    @Override // android.view.View
    public float getBottomFadingEdgeStrength() {
        return 0.9f;
    }

    public String getDisplayedMaxText() {
        String str = this.mDisplayedMaxText;
        return str == null ? "" : str;
    }

    public float getDisplayedMaxTextWidth() {
        float textSize = this.mSelectorWheelPaint.getTextSize();
        this.mSelectorWheelPaint.setTextSize(this.mOriginTextSizeHighlight);
        float fMeasureText = this.mSelectorWheelPaint.measureText(getDisplayedMaxText());
        this.mSelectorWheelPaint.setTextSize(textSize);
        return fMeasureText;
    }

    public String[] getDisplayedValues() {
        return this.mDisplayedValues;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public float getLabelWidth() {
        if (this.mShowLabel) {
            return this.mLabelPaint.measureText(this.mLabel.toString());
        }
        return 0.0f;
    }

    public int getMarginLabelLeft() {
        return this.mLabelMarginLeft;
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public int getOriginTextSizeHighlight() {
        return this.mOriginTextSizeHighlight;
    }

    public int getOriginTextSizeHint() {
        return this.mOriginTextSizeHint;
    }

    public float getOriginalLabelWidth() {
        if (!this.mShowLabel) {
            return 0.0f;
        }
        float textSize = this.mLabelPaint.getTextSize();
        this.mLabelPaint.setTextSize(this.mOriginLabelTextSize);
        float fMeasureText = this.mLabelPaint.measureText(this.mLabel.toString());
        this.mLabelPaint.setTextSize(textSize);
        return fMeasureText;
    }

    public final int getSelectedPos(String str) {
        try {
            if (this.mDisplayedValues == null) {
                return Integer.parseInt(str);
            }
            for (int i = 0; i < this.mDisplayedValues.length; i++) {
                str = str.toLowerCase();
                if (this.mDisplayedValues[i].toLowerCase().startsWith(str)) {
                    return this.mMinValue + i;
                }
            }
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return this.mMinValue;
        }
    }

    public final int getSelectorIndexOffsetForFiveLines(int i) {
        float f = getResources().getDisplayMetrics().density;
        float f2 = 12.0f * f;
        float f3 = 8.0f * f;
        float[] fArr = {f2, f3, f3, f2};
        float textSize = this.mSelectorWheelPaint.getTextSize();
        float[] fArr2 = new float[5];
        float[] fArr3 = new float[5];
        float f4 = f * 18.0f;
        for (int i2 = 0; i2 < 5; i2++) {
            float textSizeOfFiveLines = getTextSizeOfFiveLines(i2, 0.0f, this.mTextSizeMajor, this.mTextSizeMinor, this.mTextSizeMedium);
            this.mSelectorWheelPaint.setTextSize(textSizeOfFiveLines);
            int iCalculateFontHeight = calculateFontHeight(this.mSelectorWheelPaint, textSizeOfFiveLines);
            fArr2[i2] = f4;
            float f5 = iCalculateFontHeight;
            fArr3[i2] = f4 + f5;
            if (i2 < 4) {
                f4 += f5 + fArr[i2];
            }
        }
        this.mSelectorWheelPaint.setTextSize(textSize);
        float[] fArr4 = new float[4];
        int i3 = 0;
        while (i3 < 4) {
            int i4 = i3 + 1;
            fArr4[i3] = (fArr3[i3] + fArr2[i4]) / 2.0f;
            i3 = i4;
        }
        float f6 = i;
        if (f6 < fArr4[0]) {
            return -2;
        }
        if (f6 < fArr4[1]) {
            return -1;
        }
        if (f6 < fArr4[2]) {
            return 0;
        }
        return f6 < fArr4[3] ? 1 : 2;
    }

    public final float getTextSize(float f, int i, int i2) {
        return f >= 1.0f ? i2 : (f * (i2 - i)) + i;
    }

    public int getTextSizeHighlight() {
        return this.mTextSizeHighlight;
    }

    public int getTextSizeHint() {
        return this.mTextSizeHint;
    }

    public int getTextSizeMedium() {
        return this.mTextSizeMedium;
    }

    public final float getTextSizeOfFiveLines(int i, float f, int i2, int i3, int i4) {
        if (i == 0 || i == 4) {
            return i3;
        }
        return (i == 1 || i == 3) ? i4 : (f * (i3 - i2)) + i2;
    }

    @Override // android.view.View
    public float getTopFadingEdgeStrength() {
        return 0.9f;
    }

    public int getValue() {
        return this.mValue;
    }

    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    public final int getWrappedSelectorIndex(int i) {
        int i2 = this.mMaxValue;
        if (i > i2) {
            int i3 = this.mMinValue;
            return (i3 + ((i - i2) % (i2 - i3))) - 1;
        }
        int i4 = this.mMinValue;
        return i < i4 ? (i2 - ((i4 - i) % (i2 - i4))) + 1 : i;
    }

    public final void incrementSelectorIndices(int[] iArr) {
        if (iArr.length - 1 >= 0) {
            System.arraycopy(iArr, 1, iArr, 0, iArr.length - 1);
        }
        int i = iArr[iArr.length - 2] + 1;
        if (this.mWrapSelectorWheel && i > this.mMaxValue) {
            i = this.mMinValue;
        }
        iArr[iArr.length - 1] = i;
        ensureCachedScrollSelectorValue(i);
    }

    public final void initInputText() {
        this.mInputText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: miuix.pickerwidget.widget.NumberPicker.1
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    NumberPicker.this.mInputText.selectAll();
                } else {
                    NumberPicker.this.mInputText.setSelection(0, 0);
                    NumberPicker.this.validateInputTextView(view);
                }
            }
        });
        this.mInputText.setFilters(new InputFilter[]{new InputTextFilter()});
        this.mInputText.setRawInputType(2);
        this.mInputText.setImeOptions(6);
        this.mInputText.setVisibility(4);
        this.mInputText.setGravity(8388611);
        this.mInputText.setScaleX(0.0f);
        this.mInputText.setSaveEnabled(false);
        EditText editText = this.mInputText;
        editText.setPadding(this.mTextPadding, editText.getPaddingTop(), this.mTextPadding, this.mInputText.getPaddingRight());
    }

    public final void initLabelPaint() {
        Paint paint = new Paint();
        this.mLabelPaint = paint;
        paint.setAntiAlias(true);
        this.mLabelPaint.setTypeface(this.mInputText.getTypeface());
        this.mLabelPaint.setTextLocale(getContext().getResources().getConfiguration().locale);
        this.mLabelPaint.setColor(this.mLabelTextColor);
        this.mLabelFontHeight = calculateFontHeight(this.mLabelPaint, this.mLabelTextSize);
    }

    public final Paint initSelectorWheelPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(this.mTextSizeHighlight);
        paint.setTypeface(this.mInputText.getTypeface());
        paint.setColor(this.mInputText.getTextColors().getColorForState(LinearLayout.ENABLED_STATE_SET, -1));
        return paint;
    }

    public final void initSoundPlayer() {
        if (this.mSoundPlayHandler == null) {
            SoundPlayHandler soundPlayHandler = new SoundPlayHandler(WorkerThreads.acquireWorker("NumberPicker_sound_play"));
            this.mSoundPlayHandler = soundPlayHandler;
            soundPlayHandler.init(getContext().getApplicationContext(), this.mId);
        }
    }

    public final void initThreshHolds() {
        this.mLabelTextSizeThreshold = getContext().getResources().getDimensionPixelSize(R.dimen.miuix_label_text_size_small);
        this.mTextSizeThreshold = getContext().getResources().getDimensionPixelSize(R.dimen.miuix_text_size_small);
    }

    public final void initializeSelectorWheel() {
        initializeSelectorWheelIndices();
        int[] iArr = this.mSelectorIndices;
        if (isSelectorWheelThreeLines()) {
            float bottom = (((getBottom() - getTop()) - this.mLabelHeight) - (this.mContentMarginVertical * 2)) - (iArr.length * this.mTextSize);
            if (bottom < 0.0f) {
                bottom = 0.0f;
            }
            int length = (int) ((bottom / iArr.length) + 0.5f);
            this.mSelectorTextGapHeight = length;
            this.mSelectorElementHeight = this.mTextSize + length;
            int baseline = ((int) ((this.mInputText.getBaseline() + this.mInputText.getTop()) + this.mInputText.getTranslationY())) - (this.mSelectorElementHeight * SELECTOR_MIDDLE_ITEM_INDEX);
            this.mInitialScrollOffset = baseline;
            this.mCurrentScrollOffset = baseline;
            updateInputTextView();
            return;
        }
        float f = getResources().getDisplayMetrics().density;
        float textSize = this.mSelectorWheelPaint.getTextSize();
        int iCalculateFontHeight = calculateFontHeight(this.mSelectorWheelPaint, this.mTextSizeMinor);
        int iCalculateFontHeight2 = calculateFontHeight(this.mSelectorWheelPaint, this.mTextSizeMedium);
        int iCalculateFontHeight3 = (((((iCalculateFontHeight * 2) + (iCalculateFontHeight2 * 2)) + calculateFontHeight(this.mSelectorWheelPaint, this.mTextSizeMajor)) + ((int) (40.0f * f))) - iCalculateFontHeight) / (iArr.length - 1);
        this.mSelectorElementHeight = iCalculateFontHeight3;
        this.mSelectorTextGapHeight = iCalculateFontHeight3 / 2;
        this.mSelectorWheelPaint.setTextSize(textSize);
        float f2 = 18.0f * f;
        int i = ((int) (((((this.mShowLabel ? (this.mContentMarginVertical + this.mLabelHeight) + f2 : (this.mContentMarginVertical * 0.5f) + f2) + iCalculateFontHeight) + ((int) (12.0f * f))) + iCalculateFontHeight2) + ((int) (f * 8.0f)))) - (this.mSelectorElementHeight * SELECTOR_MIDDLE_ITEM_INDEX);
        this.mInitialScrollOffset = i;
        this.mCurrentScrollOffset = i;
        updateInputTextView();
    }

    public final void initializeSelectorWheelIndices() {
        this.mSelectorIndexToStringCache.clear();
        int[] iArr = this.mSelectorIndices;
        int value = getValue();
        for (int i = 0; i < this.mSelectorIndices.length; i++) {
            int wrappedSelectorIndex = (i - SELECTOR_MIDDLE_ITEM_INDEX) + value;
            if (this.mWrapSelectorWheel) {
                wrappedSelectorIndex = getWrappedSelectorIndex(wrappedSelectorIndex);
            }
            iArr[i] = wrappedSelectorIndex;
            ensureCachedScrollSelectorValue(wrappedSelectorIndex);
        }
    }

    public boolean isColumnWidthWrapContent() {
        return this.mColumnWidthWrapContent;
    }

    public final boolean isInternationalBuild() {
        if (this.mModDevice == null) {
            this.mModDevice = (String) ReflectUtil.callStaticObjectMethod(ReflectUtil.getClass("android.os.SystemProperties"), String.class, "get", new Class[]{String.class, String.class}, "ro.product.mod_device", "");
        }
        return this.mModDevice.endsWith("_global");
    }

    public boolean isScrolling() {
        return (this.mFlingScroller.isFinished() && this.mAdjustScroller.isFinished()) ? false : true;
    }

    public boolean isSelectorWheelThreeLines() {
        return this.mSelectorWheelAppearance == 0;
    }

    public final int makeMeasureSpec(int i, int i2) {
        if (i2 != -1) {
            int size = View.MeasureSpec.getSize(i);
            int mode = View.MeasureSpec.getMode(i);
            if (mode == Integer.MIN_VALUE) {
                return View.MeasureSpec.makeMeasureSpec(Math.min(size, i2), 1073741824);
            }
            if (mode == 0) {
                return View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
            }
            if (mode != 1073741824) {
                throw new IllegalArgumentException("Unknown measure mode: " + mode);
            }
        }
        return i;
    }

    public final boolean moveToFinalScrollerPosition(Scroller scroller) {
        scroller.forceFinished(true);
        int finalY = scroller.getFinalY() - scroller.getCurrY();
        int i = this.mInitialScrollOffset - ((this.mCurrentScrollOffset + finalY) % this.mSelectorElementHeight);
        if (i == 0) {
            return false;
        }
        int iAbs = Math.abs(i);
        int i2 = this.mSelectorElementHeight;
        if (iAbs > i2 / 2) {
            i = i > 0 ? i - i2 : i + i2;
        }
        scrollBy(0, finalY + i);
        return true;
    }

    public final void notifyChange(int i) {
        sendAccessibilityEvent(4);
        playSound();
        HapticCompat.performHapticFeedback(this, HapticFeedbackConstants.MIUI_GEAR_HEAVY, HapticFeedbackConstants.MIUI_MESH_NORMAL);
        OnValueChangeListener onValueChangeListener = this.mOnValueChangeListener;
        if (onValueChangeListener != null) {
            onValueChangeListener.onValueChange(this, i, this.mValue);
        }
    }

    public final void notifySelectorWheelAppearanceChange() {
        int i = isSelectorWheelThreeLines() ? 3 : 5;
        SELECTOR_WHEEL_ITEM_COUNT = i;
        SELECTOR_MIDDLE_ITEM_INDEX = i / 2;
        this.mSelectorIndices = new int[i];
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initSoundPlayer();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initThreshHolds();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseSoundPlayer();
        removeAllCallbacks();
        WorkerThreads.releaseWorker("NumberPicker_sound_play");
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onDraw(Canvas canvas) {
        if (!this.mHasSelectorWheel) {
            super.onDraw(canvas);
            return;
        }
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), null, 31);
        float right = (((getRight() - getLeft()) + getPaddingLeft()) - getPaddingRight()) / 2;
        drawLabelText(canvas, right);
        float f = this.mInitialScrollOffset + ((SELECTOR_WHEEL_ITEM_COUNT / 2) * this.mSelectorElementHeight);
        if (isSelectorWheelThreeLines()) {
            drawScrollValue(canvas, right, f);
        } else {
            drawScrollValueOfFiveLines(canvas, right, f);
        }
        drawFadeForWheel(canvas);
        canvas.restoreToCount(iSaveLayer);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (isEnabled()) {
            accessibilityNodeInfo.setScrollable(true);
            accessibilityNodeInfo.addAction(AutoBottomSheetKt.ALIGN_SCREEN_START);
            accessibilityNodeInfo.addAction(AutoBottomSheetKt.ALIGN_SCREEN_TOP);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
            accessibilityNodeInfo.setRangeInfo(AccessibilityNodeInfo.RangeInfo.obtain(0, this.mMinValue - 1, this.mMaxValue + 1, this.mValue));
            StringBuilder sb = new StringBuilder();
            String[] strArr = this.mDisplayedValues;
            sb.append(strArr == null ? formatNumber(this.mValue) : strArr[this.mValue - this.mMinValue]);
            sb.append(TextUtils.isEmpty(this.mLabel) ? "" : this.mLabel);
            accessibilityNodeInfo.setContentDescription(sb.toString());
            accessibilityNodeInfo.setStateDescription(getContext().getString(R.string.miuix_access_state_desc));
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.mHasSelectorWheel || !isEnabled() || motionEvent.getActionMasked() != 0) {
            return false;
        }
        removeAllCallbacks();
        this.mInputText.setVisibility(4);
        float y = motionEvent.getY() - (this.mShowLabel ? (int) (this.mContentMarginVertical + this.mLabelHeight) : 0);
        this.mLastDownEventY = y;
        this.mLastDownOrMoveEventY = y;
        this.mLastDownEventTime = motionEvent.getEventTime();
        this.mIgnoreMoveEvents = false;
        this.mShowSoftInputOnTap = false;
        float f = this.mLastDownEventY;
        if (f < this.mTopSelectionDividerTop) {
            if (this.mScrollState == 0) {
                this.mPressedStateHelper.buttonPressDelayed(2);
            }
        } else if (f > this.mBottomSelectionDividerBottom && this.mScrollState == 0) {
            this.mPressedStateHelper.buttonPressDelayed(1);
        }
        if (!this.mFlingScroller.isFinished()) {
            this.mFlingScroller.forceFinished(true);
            this.mAdjustScroller.forceFinished(true);
            onScrollStateChange(0);
        } else if (this.mAdjustScroller.isFinished()) {
            float f2 = this.mLastDownEventY;
            if (f2 < this.mTopSelectionDividerTop) {
                postChangeCurrentByOneFromLongPress(false, ViewConfiguration.getLongPressTimeout());
            } else if (f2 > this.mBottomSelectionDividerBottom) {
                postChangeCurrentByOneFromLongPress(true, ViewConfiguration.getLongPressTimeout());
            } else {
                this.mShowSoftInputOnTap = true;
                postBeginSoftInputOnLongPressCommand();
            }
        } else {
            this.mFlingScroller.forceFinished(true);
            this.mAdjustScroller.forceFinished(true);
        }
        return true;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!this.mHasSelectorWheel) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int measuredWidth2 = this.mInputText.getMeasuredWidth();
        int measuredHeight2 = this.mInputText.getMeasuredHeight();
        int i5 = (measuredWidth - measuredWidth2) / 2;
        int i6 = (measuredHeight - measuredHeight2) / 2;
        this.mInputText.layout(i5, i6, measuredWidth2 + i5, measuredHeight2 + i6);
        if (z) {
            initializeSelectorWheel();
            int height = getHeight();
            int i7 = this.mSelectionDividersDistance;
            int i8 = this.mSelectionDividerHeight;
            int i9 = (((height - i7) / 2) - i8) - (this.mShowLabel ? this.mLabelMarginBottom : 0);
            this.mTopSelectionDividerTop = i9;
            this.mBottomSelectionDividerBottom = i9 + (i8 * 2) + i7;
        }
        Drawable background = getBackground();
        int i10 = this.mMaxWidth + 20;
        if (this.mMeasureBackgroundEnabled && (background instanceof StateListDrawable)) {
            StateListDrawable stateListDrawable = (StateListDrawable) background;
            int stateCount = stateListDrawable.getStateCount();
            for (int i11 = 0; i11 < stateCount; i11++) {
                Drawable stateDrawable = stateListDrawable.getStateDrawable(i11);
                if (stateDrawable instanceof LayerDrawable) {
                    LayerDrawable layerDrawable = (LayerDrawable) stateDrawable;
                    int numberOfLayers = layerDrawable.getNumberOfLayers();
                    for (int i12 = 0; i12 < numberOfLayers; i12++) {
                        Drawable drawableFindDrawableByLayerId = layerDrawable.findDrawableByLayerId(layerDrawable.getId(i12));
                        if (drawableFindDrawableByLayerId instanceof GradientDrawable) {
                            ((GradientDrawable) drawableFindDrawableByLayerId).setSize(getWidth() > i10 ? i10 : getWidth(), getHeight());
                        }
                    }
                }
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        if (!this.mHasSelectorWheel) {
            super.onMeasure(i, i2);
            return;
        }
        if (isSelectorWheelThreeLines()) {
            super.onMeasure(makeMeasureSpec(i, this.mMaxWidth), makeMeasureSpec(i2, this.mMaxHeight));
            setMeasuredDimension(resolveSizeAndStateRespectingMinSize(this.mMinWidth, getMeasuredWidth(), i), resolveSizeAndStateRespectingMinSize(this.mMinHeight, getMeasuredHeight(), i2));
            return;
        }
        float f = getResources().getDisplayMetrics().density;
        float textSize = this.mSelectorWheelPaint.getTextSize();
        int i5 = (int) (40.0f * f);
        int i6 = (int) (f * 18.0f);
        int iCalculateFontHeight = (calculateFontHeight(this.mSelectorWheelPaint, this.mTextSizeMinor) * 2) + (calculateFontHeight(this.mSelectorWheelPaint, this.mTextSizeMedium) * 2) + calculateFontHeight(this.mSelectorWheelPaint, this.mTextSizeMajor);
        if (this.mShowLabel) {
            i3 = this.mContentMarginVertical;
            i4 = ((int) this.mLabelHeight) + i3;
        } else {
            i3 = this.mContentMarginVertical;
            i4 = (int) (i3 * 0.5f);
        }
        int i7 = i4 + i6 + iCalculateFontHeight + i5 + ((int) (i3 * 0.5f));
        this.mSelectorWheelPaint.setTextSize(textSize);
        super.onMeasure(makeMeasureSpec(i, this.mMaxWidth), View.MeasureSpec.makeMeasureSpec(i7, 1073741824));
    }

    public final void onScrollStateChange(int i) {
        if (this.mScrollState == i) {
            return;
        }
        if (i == 0) {
            String str = this.mUpdateText;
            if (str != null && !str.equals(this.mInputText.getText().toString())) {
                this.mInputText.setText(this.mUpdateText);
            }
            this.mUpdateText = null;
            stopSoundPlay();
        }
        this.mScrollState = i;
        OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChange(this, i);
        }
    }

    public final void onScrollerFinished(Scroller scroller) {
        if (scroller == this.mFlingScroller) {
            if (!ensureScrollWheelAdjusted()) {
                updateInputTextView();
            }
            onScrollStateChange(0);
        } else if (this.mScrollState != 1) {
            updateInputTextView();
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x006b  */
    /* JADX WARN: Code duplicated, block: B:32:0x0091  */
    /* JADX WARN: Code duplicated, block: B:35:0x009e  */
    /* JADX WARN: Code duplicated, block: B:36:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:52:0x00fb  */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int yVelocity;
        int iAbs;
        if (!isEnabled() || !this.mHasSelectorWheel) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int i = this.mShowLabel ? (int) (this.mContentMarginVertical + this.mLabelHeight) : 0;
        if (actionMasked == 1) {
            removeBeginSoftInputCommand();
            removeChangeCurrentByOneFromLongPress();
            this.mPressedStateHelper.cancel();
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
            yVelocity = (int) velocityTracker.getYVelocity();
            if (Math.abs(yVelocity) >= Math.abs(this.mMaximumFlingVelocity)) {
                yVelocity = (int) (yVelocity * this.mMaxFlingSpeedFactor);
            }
            if (Math.abs(yVelocity) > this.mMinimumFlingVelocity) {
                fling(yVelocity);
                onScrollStateChange(2);
            } else {
                int y = ((int) motionEvent.getY()) - i;
                iAbs = (int) Math.abs(y - this.mLastDownEventY);
                long eventTime = motionEvent.getEventTime() - this.mLastDownEventTime;
                if (iAbs <= this.mTouchSlop || eventTime >= ViewConfiguration.getLongPressTimeout()) {
                    ensureScrollWheelAdjusted();
                } else if (this.mShowSoftInputOnTap) {
                    this.mShowSoftInputOnTap = false;
                } else {
                    int selectorIndexOffsetForFiveLines = !isSelectorWheelThreeLines() ? getSelectorIndexOffsetForFiveLines(y) : (y / this.mSelectorElementHeight) - SELECTOR_MIDDLE_ITEM_INDEX;
                    if (selectorIndexOffsetForFiveLines > 0) {
                        changeValueBySteps(selectorIndexOffsetForFiveLines);
                        this.mPressedStateHelper.buttonTapped(1);
                    } else if (selectorIndexOffsetForFiveLines < 0) {
                        changeValueBySteps(selectorIndexOffsetForFiveLines);
                        this.mPressedStateHelper.buttonTapped(2);
                    } else {
                        ensureScrollWheelAdjusted();
                    }
                }
                onScrollStateChange(0);
            }
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        } else if (actionMasked != 2) {
            if (actionMasked == 3) {
                removeBeginSoftInputCommand();
                removeChangeCurrentByOneFromLongPress();
                this.mPressedStateHelper.cancel();
                VelocityTracker velocityTracker2 = this.mVelocityTracker;
                velocityTracker2.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                yVelocity = (int) velocityTracker2.getYVelocity();
                if (Math.abs(yVelocity) >= Math.abs(this.mMaximumFlingVelocity)) {
                    yVelocity = (int) (yVelocity * this.mMaxFlingSpeedFactor);
                }
                if (Math.abs(yVelocity) > this.mMinimumFlingVelocity) {
                    fling(yVelocity);
                    onScrollStateChange(2);
                } else {
                    int y2 = ((int) motionEvent.getY()) - i;
                    iAbs = (int) Math.abs(y2 - this.mLastDownEventY);
                    long eventTime2 = motionEvent.getEventTime() - this.mLastDownEventTime;
                    if (iAbs <= this.mTouchSlop) {
                        ensureScrollWheelAdjusted();
                    } else {
                        ensureScrollWheelAdjusted();
                    }
                    onScrollStateChange(0);
                }
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
        } else if (!this.mIgnoreMoveEvents) {
            float y3 = motionEvent.getY() - i;
            if (this.mScrollState == 1) {
                scrollBy(0, (int) (y3 - this.mLastDownOrMoveEventY));
                invalidate();
            } else if (((int) Math.abs(y3 - this.mLastDownEventY)) > this.mTouchSlop) {
                removeAllCallbacks();
                onScrollStateChange(1);
            }
            this.mLastDownOrMoveEventY = y3;
        }
        return true;
    }

    public final void parseStyle(AttributeSet attributeSet, int i) {
        Resources resources = getResources();
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.NumberPicker, i, R.style.Widget_NumberPicker_DayNight);
        this.mLabel = typedArrayObtainStyledAttributes.getText(R.styleable.NumberPicker_android_text);
        this.mTextSizeHighlight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_textSizeHighlight, resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_number_picker_text_size_highlight_normal));
        this.mTextSizeHint = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_textSizeHint, resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_number_picker_text_size_hint_normal));
        this.mLabelTextSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_android_labelTextSize, resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_number_picker_label_text_size));
        this.mTextColorHighlight = typedArrayObtainStyledAttributes.getColor(R.styleable.NumberPicker_android_textColorHighlight, resources.getColor(R.color.miuix_appcompat_default_number_picker_highlight_color));
        this.mTextColorHint = typedArrayObtainStyledAttributes.getColor(R.styleable.NumberPicker_android_textColorHint, resources.getColor(R.color.miuix_appcompat_default_number_picker_hint_color));
        this.mLabelTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.NumberPicker_labelTextColor, resources.getColor(R.color.miuix_appcompat_number_picker_label_color));
        this.mTextPadding = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_labelPadding, resources.getDimensionPixelOffset(R.dimen.miuix_appcompat_number_picker_label_padding));
        this.mSelectorWheelAppearance = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPicker_selectorWheelAppearance, 0);
        this.mTextSizeMajor = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_textSizeMajor, 0);
        this.mTextSizeMedium = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_textSizeMedium, 0);
        this.mTextSizeMinor = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_textSizeMinor, 0);
        this.mTextGapMajor = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_textGapMajor, 0);
        this.mTextGapMinor = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_textGapMinor, 0);
        this.mTextColorMajor = typedArrayObtainStyledAttributes.getColor(R.styleable.NumberPicker_textColorMajor, 0);
        this.mTextColorMedium = typedArrayObtainStyledAttributes.getColor(R.styleable.NumberPicker_textColorMedium, 0);
        this.mTextColorMinor = typedArrayObtainStyledAttributes.getColor(R.styleable.NumberPicker_textColorMinor, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.mOriginLabelTextSize = this.mLabelTextSize;
        this.mOriginTextSizeHighlight = this.mTextSizeHighlight;
        this.mOriginTextSizeHint = this.mTextSizeHint;
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        if (super.performAccessibilityAction(i, bundle)) {
            return true;
        }
        if (!isEnabled()) {
            return false;
        }
        if (i != 4096 && i != 8192) {
            return false;
        }
        changeValueByOne(i == 4096);
        return true;
    }

    public final void playSound() {
        SoundPlayHandler soundPlayHandler = this.mSoundPlayHandler;
        if (soundPlayHandler != null) {
            soundPlayHandler.play();
        }
    }

    public final void postBeginSoftInputOnLongPressCommand() {
        BeginSoftInputOnLongPressCommand beginSoftInputOnLongPressCommand = this.mBeginSoftInputOnLongPressCommand;
        if (beginSoftInputOnLongPressCommand == null) {
            this.mBeginSoftInputOnLongPressCommand = new BeginSoftInputOnLongPressCommand();
        } else {
            removeCallbacks(beginSoftInputOnLongPressCommand);
        }
        postDelayed(this.mBeginSoftInputOnLongPressCommand, ViewConfiguration.getLongPressTimeout());
    }

    public final void postChangeCurrentByOneFromLongPress(boolean z, long j) {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand == null) {
            this.mChangeCurrentByOneFromLongPressCommand = new ChangeCurrentByOneFromLongPressCommand();
        } else {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
        this.mChangeCurrentByOneFromLongPressCommand.setStep(z);
        postDelayed(this.mChangeCurrentByOneFromLongPressCommand, j);
    }

    public final void postSetSelectionCommand(int i, int i2) {
        SetSelectionCommand setSelectionCommand = this.mSetSelectionCommand;
        if (setSelectionCommand == null) {
            this.mSetSelectionCommand = new SetSelectionCommand();
        } else {
            removeCallbacks(setSelectionCommand);
        }
        this.mSetSelectionCommand.mSelectionStart = i;
        this.mSetSelectionCommand.mSelectionEnd = i2;
        post(this.mSetSelectionCommand);
    }

    public final void refreshWheel() {
        initializeSelectorWheelIndices();
        invalidate();
    }

    public final void releaseSoundPlayer() {
        SoundPlayHandler soundPlayHandler = this.mSoundPlayHandler;
        if (soundPlayHandler != null) {
            soundPlayHandler.release(this.mId);
            this.mSoundPlayHandler = null;
        }
    }

    public final void removeAllCallbacks() {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
        SetSelectionCommand setSelectionCommand = this.mSetSelectionCommand;
        if (setSelectionCommand != null) {
            removeCallbacks(setSelectionCommand);
        }
        BeginSoftInputOnLongPressCommand beginSoftInputOnLongPressCommand = this.mBeginSoftInputOnLongPressCommand;
        if (beginSoftInputOnLongPressCommand != null) {
            removeCallbacks(beginSoftInputOnLongPressCommand);
        }
        this.mPressedStateHelper.cancel();
    }

    public final void removeBeginSoftInputCommand() {
        BeginSoftInputOnLongPressCommand beginSoftInputOnLongPressCommand = this.mBeginSoftInputOnLongPressCommand;
        if (beginSoftInputOnLongPressCommand != null) {
            removeCallbacks(beginSoftInputOnLongPressCommand);
        }
    }

    public final void removeChangeCurrentByOneFromLongPress() {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
    }

    public final int resolveSizeAndStateRespectingMinSize(int i, int i2, int i3) {
        return i != -1 ? LinearLayout.resolveSizeAndState(Math.max(i, i2), i3, 0) : i2;
    }

    @Override // android.view.View
    public void scrollBy(int i, int i2) {
        int[] iArr = this.mSelectorIndices;
        boolean z = this.mWrapSelectorWheel;
        if (!z && i2 > 0 && iArr[SELECTOR_MIDDLE_ITEM_INDEX] <= this.mMinValue) {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
            return;
        }
        if (!z && i2 < 0 && iArr[SELECTOR_MIDDLE_ITEM_INDEX] >= this.mMaxValue) {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
            return;
        }
        this.mCurrentScrollOffset += i2;
        while (i2 > 0) {
            int i3 = this.mCurrentScrollOffset;
            if (i3 - this.mInitialScrollOffset <= this.mSelectorTextGapHeight) {
                break;
            }
            this.mCurrentScrollOffset = i3 - this.mSelectorElementHeight;
            decrementSelectorIndices(iArr);
            setValueInternal(iArr[SELECTOR_MIDDLE_ITEM_INDEX], true);
            if (!this.mWrapSelectorWheel && iArr[SELECTOR_MIDDLE_ITEM_INDEX] <= this.mMinValue) {
                this.mCurrentScrollOffset = this.mInitialScrollOffset;
            }
        }
        while (i2 < 0) {
            int i4 = this.mCurrentScrollOffset;
            if (i4 - this.mInitialScrollOffset >= (-this.mSelectorTextGapHeight)) {
                return;
            }
            this.mCurrentScrollOffset = i4 + this.mSelectorElementHeight;
            incrementSelectorIndices(iArr);
            setValueInternal(iArr[SELECTOR_MIDDLE_ITEM_INDEX], true);
            if (!this.mWrapSelectorWheel && iArr[SELECTOR_MIDDLE_ITEM_INDEX] >= this.mMaxValue) {
                this.mCurrentScrollOffset = this.mInitialScrollOffset;
            }
        }
    }

    public void setColumnWidthWrapContent(boolean z) {
        if (this.mColumnWidthWrapContent != z) {
            this.mColumnWidthWrapContent = z;
            int i = z ? 0 : this.mTextPadding;
            EditText editText = this.mInputText;
            editText.setPadding(i, editText.getPaddingTop(), i, this.mInputText.getPaddingRight());
        }
    }

    public void setDisplayedValues(String[] strArr) {
        if (this.mDisplayedValues == strArr) {
            return;
        }
        this.mDisplayedValues = strArr;
        if (strArr != null) {
            this.mInputText.setRawInputType(524289);
        } else {
            this.mInputText.setRawInputType(2);
        }
        updateInputTextView();
        initializeSelectorWheelIndices();
        tryComputeMaxWidth();
    }

    public void setFiveLinesAppearance(boolean z) {
        if (this.mSelectorWheelAppearance != z) {
            this.mSelectorWheelAppearance = z ? 1 : 0;
            notifySelectorWheelAppearanceChange();
            requestLayout();
        }
    }

    public void setFormatter(Formatter formatter) {
        if (formatter == this.mFormatter) {
            return;
        }
        this.mFormatter = formatter;
        initializeSelectorWheelIndices();
        updateInputTextView();
    }

    public void setLabel(String str) {
        CharSequence charSequence = this.mLabel;
        if ((charSequence != null || str == null) && (charSequence == null || charSequence.equals(str))) {
            return;
        }
        this.mLabel = str;
        updateLabelState();
        invalidate();
        requestLayout();
    }

    public void setLabelTextColor(@ColorInt int i) {
        if (this.mLabelTextColor != i) {
            this.mLabelTextColor = i;
            this.mLabelPaint.setColor(i);
            invalidate();
        }
    }

    public void setLabelTextSizeThreshold(float f) {
        this.mLabelTextSizeThreshold = Math.max(f, 0.0f);
    }

    public void setLabelTextSizeTrimFactor(float f) {
        if (f <= 0.0f || f >= 1.0f) {
            return;
        }
        this.mLabelTextSizeTrimFactor = f;
    }

    public void setMaxFlingSpeedFactor(float f) {
        if (f >= 0.0f) {
            this.mMaxFlingSpeedFactor = f;
        }
    }

    public void setMaxValue(int i) {
        if (this.mMaxValue == i) {
            return;
        }
        if (i < 0) {
            throw new IllegalArgumentException("maxValue must be >= 0");
        }
        this.mMaxValue = i;
        if (i < this.mValue) {
            this.mValue = i;
        }
        setWrapSelectorWheel(i - this.mMinValue > this.mSelectorIndices.length);
        initializeSelectorWheelIndices();
        updateInputTextView();
        tryComputeMaxWidth();
        invalidate();
    }

    public void setMeasureBackgroundEnabled(boolean z) {
        this.mMeasureBackgroundEnabled = z;
    }

    public void setMinValue(int i) {
        if (this.mMinValue == i) {
            return;
        }
        if (i < 0) {
            throw new IllegalArgumentException("minValue must be >= 0");
        }
        this.mMinValue = i;
        if (i > this.mValue) {
            this.mValue = i;
        }
        setWrapSelectorWheel(this.mMaxValue - i > this.mSelectorIndices.length);
        initializeSelectorWheelIndices();
        updateInputTextView();
        tryComputeMaxWidth();
        invalidate();
    }

    public void setOnLongPressUpdateInterval(long j) {
        this.mLongPressUpdateInterval = j;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setOnValueChangedListener(OnValueChangeListener onValueChangeListener) {
        this.mOnValueChangeListener = onValueChangeListener;
    }

    public void setTextColorHighlight(@ColorInt int i) {
        if (this.mTextColorHighlight != i) {
            this.mTextColorHighlight = i;
            invalidate();
        }
    }

    public void setTextSizeHighlight(int i) {
        this.mTextSizeHighlight = i;
        this.mSelectorWheelPaint.setTextSize(i);
        this.mDisplayedMaxTextWidth = this.mSelectorWheelPaint.measureText(this.mDisplayedMaxText);
        initializeSelectorWheel();
        invalidate();
    }

    public void setTextSizeHint(int i) {
        this.mTextSizeHint = i;
        invalidate();
    }

    public void setTextSizeMedium(int i) {
        if (this.mTextSizeMedium != i) {
            this.mTextSizeMedium = i;
            invalidate();
        }
    }

    public void setTextSizeTrimFactor(float f) {
        if (f <= 0.0f || f >= 1.0f) {
            return;
        }
        this.mTextSizeTrimFactor = f;
    }

    public void setValue(int i) {
        setValueInternal(i, false);
    }

    public final void setValueInternal(int i, boolean z) {
        int wrappedSelectorIndex = this.mWrapSelectorWheel ? getWrappedSelectorIndex(i) : Math.min(Math.max(i, this.mMinValue), this.mMaxValue);
        int i2 = this.mValue;
        if (i2 == wrappedSelectorIndex) {
            return;
        }
        this.mValue = wrappedSelectorIndex;
        updateInputTextView();
        if (z) {
            notifyChange(i2);
        }
        initializeSelectorWheelIndices();
        invalidate();
    }

    public void setWrapSelectorWheel(boolean z) {
        boolean z2 = this.mMaxValue - this.mMinValue >= this.mSelectorIndices.length;
        if ((!z || z2) && z != this.mWrapSelectorWheel) {
            this.mWrapSelectorWheel = z;
        }
        refreshWheel();
    }

    public void stopScrolling() {
        if (this.mFlingScroller.isFinished() && this.mAdjustScroller.isFinished()) {
            return;
        }
        this.mFlingScroller.forceFinished(true);
        this.mAdjustScroller.forceFinished(true);
        int i = this.mInitialScrollOffset - this.mCurrentScrollOffset;
        if (i != 0) {
            this.mPreviousScrollerY = 0;
            int iAbs = Math.abs(i);
            int i2 = this.mSelectorElementHeight;
            if (iAbs > i2 / 2) {
                if (i > 0) {
                    i2 = -i2;
                }
                i += i2;
            }
            scrollBy(0, i);
        }
        updateInputTextView();
        onScrollStateChange(0);
        invalidate();
    }

    public final void stopSoundPlay() {
        SoundPlayHandler soundPlayHandler = this.mSoundPlayHandler;
        if (soundPlayHandler != null) {
            soundPlayHandler.stop();
        }
    }

    public final void tryComputeMaxWidth() {
        String str;
        float f;
        if (this.mComputeMaxWidth) {
            this.mSelectorWheelPaint.setTextSize(this.mTextSizeHighlight);
            String[] strArr = this.mDisplayedValues;
            int i = 0;
            if (strArr == null) {
                float f2 = 0.0f;
                int i2 = 0;
                while (i < 9) {
                    float fMeasureText = this.mSelectorWheelPaint.measureText(String.valueOf(i));
                    if (fMeasureText > f2) {
                        i2 = i;
                        f2 = fMeasureText;
                    }
                    i++;
                }
                int length = formatNumber(this.mMaxValue).length();
                f = (int) (length * f2);
                char[] cArr = new char[length];
                Arrays.fill(cArr, (char) (i2 + 48));
                str = new String(cArr);
            } else {
                int length2 = strArr.length;
                str = null;
                float f3 = -1.0f;
                while (i < length2) {
                    String str2 = this.mDisplayedValues[i];
                    float fMeasureText2 = this.mSelectorWheelPaint.measureText(str2);
                    if (fMeasureText2 > f3) {
                        str = str2;
                        f3 = fMeasureText2;
                    }
                    i++;
                }
                f = f3;
            }
            this.mDisplayedMaxTextWidth = f;
            this.mDisplayedMaxText = str;
            float fMax = Math.max(f + this.mInputText.getPaddingLeft() + this.mInputText.getPaddingRight() + getPaddingLeft() + getPaddingRight(), getLabelWidth() + getPaddingLeft() + getPaddingRight());
            if (this.mMaxWidth != fMax) {
                int i3 = this.mMinWidth;
                if (fMax > i3) {
                    this.mMaxWidth = (int) fMax;
                } else {
                    this.mMaxWidth = i3;
                }
            }
        }
    }

    public final boolean updateInputTextView() {
        String displayedMaxText = getDisplayedMaxText();
        if (TextUtils.isEmpty(displayedMaxText)) {
            return false;
        }
        if (this.mScrollState != 0) {
            this.mUpdateText = displayedMaxText;
            return true;
        }
        if (displayedMaxText.equals(this.mInputText.getText().toString())) {
            return true;
        }
        this.mInputText.setText(displayedMaxText);
        return true;
    }

    public final void updateLabelState() {
        if (TextUtils.isEmpty(this.mLabel) || isInternationalBuild()) {
            this.mShowLabel = false;
            this.mLabelHeight = 0.0f;
            this.mMaxHeight = this.mMaxPanelNoLabelHeight;
        } else {
            this.mShowLabel = true;
            this.mLabelHeight = this.mLabelFontHeight;
            Log.d("PickerTest", "mLabelFontHeight=" + this.mLabelFontHeight);
            this.mMaxHeight = this.mMaxPanelHeight;
        }
        float f = this.mShowLabel ? this.mLabelMarginBottom : 0.0f;
        EditText editText = this.mInputText;
        if (editText != null) {
            editText.setTranslationY(f);
        }
    }

    public final void validateInputTextView(View view) {
        String strValueOf = String.valueOf(((TextView) view).getText());
        if (TextUtils.isEmpty(strValueOf)) {
            updateInputTextView();
        } else {
            setValueInternal(getSelectedPos(strValueOf.toString()), true);
        }
    }
}
